package com.gp.gdd.collections.issues;

import java.util.List;
import org.bson.Document;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gp.gdd.repository.GitlabRepository;

/**
 * Class to write ggitlab issues data to mongodb
 */
@Component
public class IssuesItemWriter implements ItemWriter<Document> {

    @Autowired
    GitlabRepository gitlabRepository;
    
    /** 
     * mongodb writer
     * @param documents
     * @throws Exception
     */
    @Override
    public void write(List<? extends Document> documents) throws Exception {
        gitlabRepository.saveIssues(documents);
    }
}
