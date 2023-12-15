package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.*;

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

            boolean responseAttendu =
            Arrays.stream(r)
            .anyMatch(expectedSubstring -> response != null && response
            .contains(expectedSubstring));

            assertThat(responseAttendu, is(true));
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

    @Test
    void testWhatsNameQuestion() {
        Processor p = new Processor();

        String q = "Quel est mon nom ?";
        String[] re = {"Je ne connais pas votre nom.", "Votre nom est "+p.getName()+"."};

        String response = p.generateResponse(q);
        System.out.println("Input: " + q);
        System.out.println("Generated Response: " + response);

        boolean responseAttendu =
                Arrays.stream(re)
                        .anyMatch(expectedSubstring -> response != null && response
                                .contains(expectedSubstring));

        assertThat(responseAttendu, is(true));
    }

    @Test
    void testNameResponse() {
        Processor p = new Processor();

        String q = "Je m'appelle (.*)\\.";
        String attendu = "Bonjour $1\\."; // Utilisation de $1 pour faire référence au groupe capturé dans la regex
        String response = p.generateResponse(q);

        assertThat("Input: " + q + ", Generated Response: " + response,
                response, matchesRegex(attendu));
    }
    @Test
    void testQuestionResponse() {
        Processor p = new Processor();

        String q = ".*?.*";
        String response = p.generateResponse(q);
        String[] re = {"Ici, c'est moi qui pose les questions", "Je vous renvoie la question."};

        assertThat(Arrays.asList(re), hasItem(response));
    }
}
