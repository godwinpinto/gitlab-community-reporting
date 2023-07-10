package com.gp.gdd.collections.groups.users;

import java.util.List;
import org.bson.Document;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gp.gdd.repository.GitlabRepository;

/**
 * Class to write group users data to mongodb
 */
@Component
public class GroupUsersItemWriter implements ItemWriter<Document> {

    @Autowired
    GitlabRepository gitlabRepository;
    
    /** 
     * mongodb writer
     * @param documents
     * @throws Exception
     */
    @Override
    public void write(List<? extends Document> documents) throws Exception {
        gitlabRepository.saveGroupUsers(documents);
    }
}
