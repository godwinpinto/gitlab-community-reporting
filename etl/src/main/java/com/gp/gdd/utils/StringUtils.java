package com.gp.gdd.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class to format field values
 */
public class StringUtils {

    /**
     * identify if duration is specified in the string
     * 
     * @param word
     * @return true if the text as duration
     */
    public static boolean isDuration(String word) {
        if (word == null || word.equals("")) {
            return false;
        }
        String regexNumber = "\\d+";
        int wordLength = word.length();
        if (Character.isLetter(word.charAt(wordLength - 1))
                && (word.substring(0, wordLength - 1).matches(regexNumber))) {
            return true;
        } else if (word.endsWith("mo")
                && (word.substring(0, wordLength - 2).matches(regexNumber))) {
            return true;
        }
        return false;
    }

    /**
     * Extract time spent / estimate attribute from gitlab text
     * 
     * @param text
     * @return extracted time as string
     */
    public static String extractTime(String text) {
        List<String> wordsArray = new ArrayList<String>(Arrays.asList(text.split(" ")));
        long totalMinutes = 0;
        for (int i = 0; i < wordsArray.size();) {
            String word = wordsArray.get(i);
            if (isDuration(word)) {
                if (word.endsWith("mo")) {
                    totalMinutes += Long.parseLong(word.substring(0, word.length() - 2))
                            * ApplicationConstants.MONTHS_TO_MINUTES;
                    wordsArray.remove(i);
                    continue;
                } else if (word.endsWith("w")) {
                    totalMinutes += Long.parseLong(word.substring(0, word.length() - 1))
                            * ApplicationConstants.WEEKS_TO_MINUTES;
                    wordsArray.remove(i);
                    continue;
                } else if (word.endsWith("d")) {
                    totalMinutes += Long.parseLong(word.substring(0, word.length() - 1))
                            * ApplicationConstants.DAYS_TO_MINUTES;
                    wordsArray.remove(i);
                    continue;
                } else if (word.endsWith("h")) {
                    totalMinutes += Long.parseLong(word.substring(0, word.length() - 1)) * 60;
                    wordsArray.remove(i);
                    continue;
                } else if (word.endsWith("m")) {
                    totalMinutes += Long.parseLong(word.substring(0, word.length() - 1));
                }
                i++;
            } else {
                wordsArray.remove(i);
            }
        }
        String formatJavaTime = "" + totalMinutes;
        return formatJavaTime;
    }

    /**
     * retriebe time from string
     * 
     * @param text
     * @return time as long
     */
    public static long getTimeInMinutesFromString(String text) {
        String timeInString = extractTime(text);
        return Long.parseLong(timeInString);
    }

}
