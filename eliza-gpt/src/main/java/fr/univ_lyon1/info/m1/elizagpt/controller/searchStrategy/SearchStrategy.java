package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

/**
 * Interface for the search strategies.
 */
public interface SearchStrategy {
    /**
     * Search for a string in a text.
     * 
     * @param text
     * @param searchString
     * @return
     */
    boolean search(String text, String searchString);

}
