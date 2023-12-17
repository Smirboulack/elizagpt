package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

/**
 * Interface for the search strategies by substring.
 * 
 * @see SearchStrategy
 */
public class Substring implements SearchStrategy {

    /**.
     * Checks if the given text contains the specified substring.
     *
     * @param text          The text to be searched.
     * @param searchString  The substring to search for within the text.
     * @return              if the text contains the substring.
     */
    @Override
    public boolean search(final String text, final String searchString) {
        return text.contains(searchString);
    }
}
