package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

/**
 * Interface for the search strategies by substring.
 */
public class Substring implements SearchStrategy {
    @Override
    public boolean search(final String text, final String searchString) {
        return text.contains(searchString);
    }
}
