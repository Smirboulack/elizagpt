package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

import java.util.regex.Pattern;

/**
 * Search strategy that search for a complete word.
 * 
 * @see SearchStrategy
 */
public class MotComplet implements SearchStrategy {

    /**
     * Search for a complete word.
     * 
     * @param text         The text to search in.
     * @param searchString The word to search for.
     * @return true if the word is found, false otherwise.
     */
    @Override
    public boolean search(final String text, final String searchString) {
        return Pattern.compile("\\b" + searchString + "\\b", Pattern.CASE_INSENSITIVE)
                .matcher(text).find();
    }
}
