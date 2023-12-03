package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

import java.util.regex.Pattern;

/**
 * Search strategy that search for a complete word.
 */
public class MotComplet extends Substring {
    @Override
    public boolean search(final String text, final String searchString) {
        return Pattern.compile("\\b" + searchString + "\\b", Pattern.CASE_INSENSITIVE)
        .matcher(text).find();
    }
}
