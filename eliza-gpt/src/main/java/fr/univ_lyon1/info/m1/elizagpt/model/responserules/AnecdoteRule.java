package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * Rule that generates a response with an anecdote about artificial
 * intelligence.
 * 
 * @see IResponseRule
 */
public class AnecdoteRule implements IResponseRule {

    /**
     * Check if the rule applies to the input.
     *
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        boolean pattern1 = Pattern
                .compile("Donne-moi une anecdote", Pattern.CASE_INSENSITIVE)
                .matcher(input).find();
        boolean pattern2 = Pattern
                .compile("Une anecdote sur l'intelligence artificiel", Pattern.CASE_INSENSITIVE)
                .matcher(input).find();
        boolean pattern3 = Pattern
                .compile("je veux une anecdote sur l'intelligence artificiel",
                        Pattern.CASE_INSENSITIVE)
                .matcher(input).find();

        return pattern1 || pattern2 || pattern3;
    }

    /**
     * Generate a response from a list of anecdotes.
     *
     * @param input     The input to generate a response for.
     * @param processor the model
     * @return The generated response.
     */
    @Override
    public String generateResponse(final String input, final Processor processor) {
        try {
            List<String> anecdotes = Files.readAllLines(Paths.get(getClass().getClassLoader()
                    .getResource("anecdotes.txt").toURI()));
            Random random = new Random();
            return anecdotes.get(random.nextInt(anecdotes.size()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return "Désolé, je ne peux pas charger d'anecdote pour le moment.";
        }
    }

}
