package com.gp.gdd.collections.projects.branches;

import org.bson.Document;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.gp.gdd.utils.DocumentFieldParserUtils;

/**
 * Class to transform fields in documents related to projects repositories
 */
@Component
public class ProjectBranchesItemProcessor implements ItemProcessor<Document,Document>{

    /** 
     * Transformation of default date / time fields present in the document
     * @param document
     * @return Document
     * @throws Exception
     */
    public Document process(Document document) throws Exception{

        if(document.get("commit")!=null){
            Document docSubCommit=(Document)document.get("commit");
            docSubCommit=DocumentFieldParserUtils.parseDefaultFields(docSubCommit);
            document.put("commit", docSubCommit);
        }
        return DocumentFieldParserUtils.parseDefaultFields(document);
    }
    
}
