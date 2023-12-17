package fr.univ_lyon1.info.m1.elizagpt.model.verb;

/**
 * Information about conjugation of a verb.
 * This class is immutable.
 * 
 * @see VerbList
 */
public class Verb {
    private final String firstSingular;
    private final String secondPlural;

    /**
     * .
     * Constructor for Verb
     *
     * @param firstSingular the 1st person singular (french grammar)
     * @param secondPlural  the 2nd person plural (french grammar)
     */
    public Verb(final String firstSingular, final String secondPlural) {
        this.firstSingular = firstSingular;
        this.secondPlural = secondPlural;
    }

    /**
     * Get the 1st person singular.
     *
     * @return the 1st person singular
     */
    public String getFirstSingular() {
        return firstSingular;
    }

    /**
     * Get the 2nd person plural.
     *
     * @return the 2nd person plural
     */
    public String getSecondPlural() {
        return secondPlural;
    }

}
