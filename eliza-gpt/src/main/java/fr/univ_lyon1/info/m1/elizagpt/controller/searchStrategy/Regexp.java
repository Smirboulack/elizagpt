package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

import java.util.regex.Pattern;

/**
 * Search strategy that search by regular expression.
 * 
 * @see SearchStrategy
 */
public class Regexp implements SearchStrategy {

    /**
     * Search for a regular expression.
     * 
     * @param text         The text to search in.
     * @param searchString The regular expression to search for.
     * @return true if the regular expression is found, false otherwise.
     */
    @Override
    public boolean search(final String text, final String searchString) {
        Pattern pattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(text).find();
    }
}
