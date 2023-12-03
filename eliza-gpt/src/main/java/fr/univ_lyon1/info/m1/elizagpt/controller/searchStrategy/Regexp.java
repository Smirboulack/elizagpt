package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

import java.util.regex.Pattern;

/**
 * Search strategy that search by regular expression.
 */
public class Regexp implements SearchStrategy {
    @Override
    public boolean search(final String text, final String searchString) {
        Pattern pattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(text).find();
    }
}
