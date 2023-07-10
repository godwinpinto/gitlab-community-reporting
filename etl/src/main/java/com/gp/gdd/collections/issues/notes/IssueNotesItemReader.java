package com.gp.gdd.collections.issues.notes;

import java.util.LinkedList;
import java.util.List;
import org.bson.Document;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.gp.gdd.repository.GitlabRepository;
import com.gp.gdd.utils.WebClientWrapper;
import com.gp.gdd.vo.ServiceResponseVO;

/**
 * Class to read data related to notes in issues
 */

@Component
public class IssueNotesItemReader implements ItemReader<Document> {

    @Autowired
    WebClientWrapper webClientWrapper;

    @Autowired
    GitlabRepository gitlabRepository;

    LinkedList<Document> listDocuments;

    List<Document> lstGetIssues;

    int pageNo = 1;
    int totalPages = 1;
    int issueIndex = 0;

    /**
     * This function calls the gitlab's paginated API, appends to listDocuments and on exhaustion
     * calls the API again untill total pages is reached
     * @return Document
     * @throws Exception
     */
    @Override
    public Document read() throws Exception {

        if (listDocuments == null) {
            gitlabRepository.deleteIssueNotes();
            listDocuments = new LinkedList<Document>();
            lstGetIssues = gitlabRepository.getAllIssues();
        }

        while (listDocuments.isEmpty()) {
            if (pageNo > totalPages) {
                pageNo = 1;
                totalPages = 1;
                issueIndex++;
            }

            if (lstGetIssues.size() <= issueIndex) {
                return null;
            }

            int issueIid = lstGetIssues.get(issueIndex).getInteger("iid");
            int issueUniqueId = lstGetIssues.get(issueIndex).getInteger("id");
            int projectId = lstGetIssues.get(issueIndex).getInteger("project_id");

            ServiceResponseVO serviceResponseVO = webClientWrapper.callGitlabService(pageNo,
                    "/projects/" + projectId + "/issues/" + issueIid + "/notes", "scope=all");
            pageNo++;
            totalPages = serviceResponseVO.getTotalPages();

            if (serviceResponseVO.getResponseJson() != null
                    && serviceResponseVO.getResponseJson().isArray()
                    && serviceResponseVO.getResponseJson().size() != 0) {
                for (final JsonNode individualNode : serviceResponseVO.getResponseJson()) {
                    Document document = Document.parse(individualNode.toString());
                    document.append("issue_id", issueUniqueId);
                    document.append("project_id", projectId);
                    listDocuments.add(document);
                }
                break;

            }
        }
        if (lstGetIssues.size() == issueIndex) {
            return null;
        }
        Document document = listDocuments.getFirst();
        listDocuments.removeFirst();
        return document;
    }
}
