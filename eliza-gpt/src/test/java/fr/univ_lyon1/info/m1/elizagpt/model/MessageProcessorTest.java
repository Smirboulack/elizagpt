package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.net.URISyntaxException;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Tests for MessageProcessor.
 */
public class MessageProcessorTest {
    @Test
    void testFirstToSecondPerson() {
        // Given
        Processor p = new Processor();

        // Then
        String[] sp = {"J'", "Je "};
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
    }

    /**
     * Not so relevant test, but here to give an example of non-trivial
     * hamcrest assertion.
     */
    @Test
    void testVerbList() {
    }

    @Test
    void testWhatsNameQuestion() {
        Processor p = new Processor();

        String q = "Quel est mon nom ?";
        String[] re = {"Je ne connais pas votre nom.", "Votre nom est " + p.getName() + "."};

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
        String attendu = "Bonjour (.*)\\.";
        String response = p.generateResponse(q);

        assertThat("Input: " + q + ", Generated Response: " + response,
                response, matchesRegex(attendu));
    }
    @Test
    void testQuestionResponse() {
        Processor p = new Processor();

        String q = ".*?.*";
        String response = p.generateResponse(q);
        String[] re = {"Ici, c'est moi qui pose les questions.", "Je vous renvoie la question."};

        assertThat(Arrays.asList(re), hasItem(response));
    }

    @Test
    void testAnecdoteResponse() throws URISyntaxException, IOException {
        // Given
        Processor p = new Processor();

        // When
        String[] sp = {"Donne-moi une anecdote", "Une anecdote sur l'intelligence artificielle",
                "je veux une anecdote sur l'intelligence artificielle"};

        List<String> anecdotes = Files.readAllLines(Paths.get(getClass().getClassLoader()
                .getResource("anecdotes.txt").toURI()));
        Random random = new Random();

        // THen
        for (String request : sp) {
            String response = p.generateResponse(request);
            System.out.println("reque: " + request + " resp: " + response);
            assertThat(anecdotes, hasItem(response));
        }
    }

}
