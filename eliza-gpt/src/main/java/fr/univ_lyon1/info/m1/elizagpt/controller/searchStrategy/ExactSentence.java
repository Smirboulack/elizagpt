package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

/**
 * Class representing a search strategy for exact sentence matching.
 * 
 * @see SearchStrategy
 * @see Substring
 */
public class ExactSentence implements SearchStrategy {
    /**
     * Search for an exact sentence.
     * 
     * @param text         The text to search in.
     * @param searchString The sentence to search for.
     * @return true if the sentence is found, false otherwise.
     */
    @Override
    public boolean search(final String text, final String searchString) {
        return text.equals(searchString);
    }
}
