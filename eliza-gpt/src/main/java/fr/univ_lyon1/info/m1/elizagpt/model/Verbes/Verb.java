package fr.univ_lyon1.info.m1.elizagpt.model.Verbes;

import java.util.Objects;

/**
 * Information about conjugation of a verb.
 */
public class Verb {
    private final String firstSingular;
    private final String secondPlural;

    /**
     * Constructor.
     * 
     * @param firstSingular
     * @param secondPlural
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

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Verb verb = (Verb) o;
        return firstSingular.equals(verb.firstSingular) && secondPlural.equals(verb.secondPlural);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstSingular, secondPlural);
    }

    @Override
    public String toString() {
        return "Verb{"
                + "firstSingular='" + firstSingular + '\''
                + ", secondPlural='" + secondPlural + '\''
                + '}';
    }
}
