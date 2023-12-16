package fr.univ_lyon1.info.m1.elizagpt.model.verb;


/**
 * Information about conjugation of a verb.
 */
public class Verb {
    private final String firstSingular;
    private final String secondPlural;

    /**.
     * Constructor for Verb
     *
     * @param firstSingular le singulier de la 1ère personne
     * @param secondPlural le pluriel de la 2ème personne
     */
    public Verb(final String firstSingular, final String secondPlural) {
        this.firstSingular = firstSingular;
        this.secondPlural = secondPlural;
    }

    public String getFirstSingular() {
        return firstSingular;
    }

    public String getSecondPlural() {
        return secondPlural;
    }

}
