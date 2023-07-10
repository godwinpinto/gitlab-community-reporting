package com.gp.gdd.collections.projects;

import java.util.LinkedList;
import org.bson.Document;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.gp.gdd.repository.GitlabRepository;
import com.gp.gdd.utils.WebClientWrapper;
import com.gp.gdd.vo.ServiceResponseVO;

/**
 * Class to read data related to gitlab projects
 */
@Component
public class ProjectsItemReader implements ItemReader<Document> {

    @Autowired
    WebClientWrapper webClientWrapper;

    @Autowired
    GitlabRepository gitlabRepository;

    LinkedList<Document> listDocuments;

    int pageNo = 1;
    int totalPages = 1;

    
    /** 
     * This function calls the gitlab's paginated API, appends to listDocuments and on exhaustion
     * calls the API again untill total pages is reached
     * @return Document
     * @throws Exception
     */
    @Override
    public Document read() throws Exception {

        if (listDocuments == null) {
            gitlabRepository.deleteProjects();
            listDocuments = new LinkedList<Document>();
        }

        if (listDocuments.isEmpty()) {
            if (pageNo > totalPages) {
                return null;
            }
            ServiceResponseVO serviceResponseVO =
                    webClientWrapper.callGitlabService(pageNo, "/projects","");
            pageNo++;
            totalPages = serviceResponseVO.getTotalPages();

            if (serviceResponseVO.getResponseJson() == null) {
                return null;
            } else if (serviceResponseVO.getResponseJson().isArray() && serviceResponseVO.getResponseJson().size()!=0) {
                for (final JsonNode individualNode : serviceResponseVO.getResponseJson()) {
                    Document document = Document.parse(individualNode.toString());
                    listDocuments.add(document);
                }
            } else {
                return null;
            }
        }

        Document document = listDocuments.getFirst();
        listDocuments.removeFirst();
        return document;
    }
}
