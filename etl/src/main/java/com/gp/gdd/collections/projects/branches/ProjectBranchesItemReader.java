package com.gp.gdd.collections.projects.branches;

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
 * Class to read data related to project repositories
 */
@Component
public class ProjectBranchesItemReader implements ItemReader<Document> {

    @Autowired
    WebClientWrapper webClientWrapper;

    @Autowired
    GitlabRepository gitlabRepository;

    LinkedList<Document> listDocuments;

    List<Document> lstGetProjects;

    int pageNo = 1;
    int totalPages = 1;
    int projectIndex = 0;

    /**
     * This function calls the gitlab's paginated API, appends to listDocuments and on exhaustion
     * calls the API again untill total pages is reached
     * @return Document
     * @throws Exception
     */
    @Override
    public Document read() throws Exception {

        if (listDocuments == null) {
            gitlabRepository.deleteProjectBranches();
            listDocuments = new LinkedList<Document>();
            lstGetProjects = gitlabRepository.getAllProjects();
        }

        while (listDocuments.isEmpty()) {
            if (pageNo > totalPages) {
                pageNo = 1;
                totalPages = 1;
                projectIndex++;
            }

            if (lstGetProjects.size() <= projectIndex) {
                return null;
            }
            ServiceResponseVO serviceResponseVO = webClientWrapper.callGitlabService(pageNo,
                    "/projects/" + lstGetProjects.get(projectIndex).getInteger("id")
                            + "/repository/branches",
                    "");
            pageNo++;
            totalPages = serviceResponseVO.getTotalPages();

            if (serviceResponseVO.getResponseJson() != null
                    && serviceResponseVO.getResponseJson().isArray()
                    && serviceResponseVO.getResponseJson().size() != 0) {
                for (final JsonNode individualNode : serviceResponseVO.getResponseJson()) {
                    Document document = Document.parse(individualNode.toString());
                    document.append("project_id",
                            lstGetProjects.get(projectIndex).getInteger("id"));
                    listDocuments.add(document);
                }
                break;
            }
        }

        if (listDocuments.isEmpty()) {
            return null;
        }
        Document document = listDocuments.getFirst();
        listDocuments.removeFirst();
        return document;
    }
}
