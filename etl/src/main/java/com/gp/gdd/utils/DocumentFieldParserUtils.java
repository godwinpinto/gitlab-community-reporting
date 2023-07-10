package com.gp.gdd.utils;

import org.bson.Document;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Used to parse fields generic fields from Gitlab response Json
 */
public class DocumentFieldParserUtils {

    private static final DateTimeZone indianTimeZone = DateTimeZone.forID("Asia/Kolkata");
    private static final DateTimeFormatter dateTimeFormat =
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withZone(DateTimeZone.UTC);
    private static final DateTimeFormatter dateFormat =
            DateTimeFormat.forPattern("yyyy-MM-dd").withZone(indianTimeZone);

    /**
     * Used to convert default date / time fields into mongo db compatible format
     * 
     * @param doc
     * @return Document
     * @throws Exception
     */
    public static Document parseDefaultFields(Document doc) throws Exception {
        if (doc == null) {
            return null;
        }

        if (doc.get("created_at") != null) {
            doc.put("created_at", dateTimeFormat.parseDateTime(doc.get("created_at").toString())
                    .withZone(indianTimeZone).toDate());
        }
        if (doc.get("updated_at") != null) {
            doc.put("updated_at", dateTimeFormat.parseDateTime(doc.get("updated_at").toString())
                    .withZone(indianTimeZone).toDate());
        }

        if (doc.get("closed_at") != null) {
            doc.put("closed_at", dateTimeFormat.parseDateTime(doc.get("closed_at").toString())
                    .withZone(indianTimeZone).toDate());
        }

        if (doc.get("due_date") != null) {
            try {
                doc.put("due_date", dateFormat.parseDateTime(doc.get("due_date").toString())
                        .withZone(indianTimeZone).toDate());
            } catch (Exception e) {
                try {
                    doc.put("due_date", dateTimeFormat.parseDateTime(doc.get("due_date").toString())
                            .withZone(indianTimeZone).toDate());
                } catch (Exception ee) {
                    doc.put("due_date", "PROBLEM_DATE" + doc.get("due_date").toString());
                }
            }
        }

        if (doc.get("start_date") != null) {
            doc.put("start_date", dateFormat.parseDateTime(doc.get("start_date").toString())
                    .withZone(indianTimeZone).toDate());
        }

        if (doc.get("committed_date") != null) {
            doc.put("committed_date",
                    dateTimeFormat.parseDateTime(doc.get("committed_date").toString())
                            .withZone(indianTimeZone).toDate());
        }
        return doc;

    }
}
