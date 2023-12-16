package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Arrays;

/**
 * Tests for MessageProcessor.
 */
public class MessageProcessorTest {
    @Test
    void testFirstToSecondPerson() {
        // Given
        Processor p = new Processor();

        // Then
        String[] sp = {"J'", "Je ", "M'"};
        String[] r = 
        {"Pourquoi dites-vous que ", "Pourquoi pensez-vous que ", "Êtes-vous sûr que "};

        // When & Then
        for (String st : sp) {
            String response = p.generateResponse(st);
            System.out.println("Input: " + st);
            System.out.println("Generated Response: " + response);

            boolean containsExpectedSubstring = 
            Arrays.stream(r)
            .anyMatch(expectedSubstring -> response != null && response
            .contains(expectedSubstring));

            assertThat(containsExpectedSubstring, is(true));
        }

        /* assertThat(p.firstToSecondPerson("Je pense à mon chien."),
                is("vous pensez à votre chien."));

        assertThat(p.firstToSecondPerson("Je suis heureux."),
                is("vous êtes heureux."));

        assertThat(p.firstToSecondPerson("Je dis bonjour."),
                is("vous dites bonjour."));

        assertThat(p.firstToSecondPerson("Je vais à la mer."),
                is("vous allez à la mer."));

        assertThat(p.firstToSecondPerson("Je finis mon travail."),
                is("vous finissez votre travail.")); */
    }

    /**
     * Not so relevant test, but here to give an example of non-trivial
     * hamcrest assertion.
     */
    @Test
    void testVerbList() {
        /* assertThat(MessageProcessor.VERBS, hasItem(
                allOf(
                        hasProperty("firstSingular", is("suis")),
                        hasProperty("secondPlural", is("êtes"))))); */
    }
}
