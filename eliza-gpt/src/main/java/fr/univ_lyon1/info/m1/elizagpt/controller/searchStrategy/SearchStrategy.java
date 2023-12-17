package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

/**
 * Interface for the search strategies.
 */
public interface SearchStrategy {
    /**
     * Search for a string in a text.
     * 
     * @param text the text
     * @param searchString the searched string
     * @return the boolean if the string exists
     */
    boolean search(String text, String searchString);

}
