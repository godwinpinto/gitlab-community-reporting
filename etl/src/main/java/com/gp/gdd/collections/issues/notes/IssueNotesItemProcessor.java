package com.gp.gdd.collections.issues.notes;

import org.bson.Document;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.gp.gdd.utils.DocumentFieldParserUtils;
import com.gp.gdd.utils.StringUtils;

/**
 * Class to transform fields in documents related to notes mapped to issues
 */
@Component
public class IssueNotesItemProcessor implements ItemProcessor<Document, Document> {

    /**
     * Transformation of default date / time fields and extract gitlab specific time format (e.x. 3d4m2s) into numbers present in the document
     * @param document
     * @return Document
     * @throws Exception
     */
    public Document process(Document document) throws Exception {

        String notesBody = document.getString("body");
        if (notesBody.contains("time spent")) {
            document.append("action_type", "TIME_SPENT");
            document.append("time", StringUtils.getTimeInMinutesFromString(notesBody));
        } else if (notesBody.contains("removed time estimate")) {
            document.append("action_type", "TIME_ESTIMATE_REMOVED");
            document.append("time", 0);
        } else if (notesBody.contains("time estimate")) {
            document.append("action_type", "TIME_ESTIMATE");
            document.append("time", StringUtils.getTimeInMinutesFromString(notesBody));
        }
        if (notesBody.contains("¯\\＿(ツ)＿/¯")) {
            document.append("penalty", 1);
        }
        return DocumentFieldParserUtils.parseDefaultFields(document);
    }

}
