package com.gp.gdd.collections.users;

import org.bson.Document;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.gp.gdd.utils.DocumentFieldParserUtils;

/**
 * Class to transform fields in documents related to gitlab's users
 */
@Component
public class UsersItemProcessor implements ItemProcessor<Document,Document>{

    /** 
     * Transformation of default date / time fields present in the document
     * @param document
     * @return Document
     * @throws Exception
     */
    public Document process(Document document) throws Exception{
        return DocumentFieldParserUtils.parseDefaultFields(document);
    }
    
}
