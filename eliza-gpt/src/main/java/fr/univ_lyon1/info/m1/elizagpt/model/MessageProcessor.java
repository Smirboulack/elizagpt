package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Logic to process a message (and probably reply to it).
 */
public class MessageProcessor {
    // private List<HBox> messages;
    private String name;
    private final Random random = new Random();

    /**
     * Normlize the text: remove extra spaces, add a final dot if missing.
     * 
     * @param text
     * @return normalized text.
     */
    public String normalize(final String text) {
        return text.replaceAll("\\s+", " ")
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .replaceAll("[^\\.!?:]$", "$0.");
    }

    /**
     * Information about conjugation of a verb.
     */
    public static class Verb {
        private final String firstSingular;
        private final String secondPlural;

        public String getFirstSingular() {
            return firstSingular;
        }

        public String getSecondPlural() {
            return secondPlural;
        }

        Verb(final String firstSingular, final String secondPlural) {
            this.firstSingular = firstSingular;
            this.secondPlural = secondPlural;
        }
    }

    /**
     * List of 3rd group verbs and their correspondance from 1st person signular
     * (Je) to 2nd person plural (Vous).
     */
    protected static final List<Verb> VERBS = Arrays.asList(
            new Verb("suis", "êtes"),
            new Verb("vais", "allez"),
            new Verb("dis", "dites"),
            new Verb("ai", "avez"),
            new Verb("peux", "pouvez"),
            new Verb("dois", "devez"),
            new Verb("fais", "faites"),
            new Verb("sais", "savez"),
            new Verb("dois", "devez"));

    /**
     * Turn a 1st-person sentence (Je ...) into a plural 2nd person (Vous ...).
     * The result is not capitalized to allow forming a new sentence.
     *
     * TODO: does not deal with all 3rd group verbs.
     *
     * @param text
     * @return The 2nd-person sentence.
     */
    public String firstToSecondPerson(final String text) {
        String processedText = text
                .replaceAll("[Jj]e ([a-z]*)e ", "vous $1ez ");
        for (Verb v : VERBS) {
            processedText = processedText.replaceAll(
                    "[Jj]e " + v.getFirstSingular(),
                    "vous " + v.getSecondPlural());
        }
        processedText = processedText
                .replaceAll("[Jj]e ([a-z]*)s ", "vous $1ssez ")
                .replace("mon ", "votre ")
                .replace("ma ", "votre ")
                .replace("mes ", "vos ")
                .replace("moi", "vous");
        return processedText;
    }

    /**
     * Generate a response to the input text.
     * 
     * @param normalizedText
     * @return The response (String).
     */
    public String generateResponse(final String normalizedText) {
        Matcher matcher;
        String response;
        
        matcher = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE)
                .matcher(normalizedText);
        if (matcher.matches()) {
            name = (matcher.group(1));
            return "Bonjour " + matcher.group(1) + ".";
        }
        matcher = Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE)
                .matcher(normalizedText);
        if (matcher.matches()) {
            if (name != null) {
                response = "Votre nom est " + name + ".";
            } else {
                response = "Je ne connais pas votre nom.";
            }
            return response;
        }

        matcher = Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE)
                .matcher(normalizedText);
        if (matcher.matches()) {
            response = "Le plus " + matcher.group(1) + " est bien sûr votre enseignant de MIF01 !";
            return response;
        }

        matcher = Pattern.compile("(Je .*)\\.", Pattern.CASE_INSENSITIVE).matcher(normalizedText);
        if (matcher.matches()) {
            String startQuestion = pickRandom(new String[] {
                    "Pourquoi dites-vous que ",
                    "Pourquoi pensez-vous que ",
                    "Êtes-vous sûr que ",
            });
            response = startQuestion + firstToSecondPerson(matcher.group(1)) + " ?";
            return response;
        }

        matcher = Pattern.compile(".*\\?$", Pattern.CASE_INSENSITIVE).matcher(normalizedText);
        if (matcher.matches()) {
            String startQuestion = pickRandom(new String[] {
                    "Je vous renvoie la question.",
                    "Ici, c'est moi qui pose les questions."
            });
            response = startQuestion;
            return response;
        }

        // Matches "Au revoir"
        matcher = Pattern.compile("(?i)^au revoir\\.$", Pattern.CASE_INSENSITIVE)
                .matcher(normalizedText);
        if (matcher.matches()) {
            String str1 = "Au revoir";
            String str2 = "Oh non, c'est trop triste de se quitter !";
            if (getName() != null) {
                str1 += " " + getName() + ".";
                str2 = "Bon débarras, n'oublie pas de commit + push avant de partir "
                        + getName() + ".";
            }
            String startQuestion = pickRandom(new String[] {
                    str1,
                    str2
            });
            response = startQuestion;
            return response;
        }

        // Random responses
        String[] randomResponses = {
                "Il fait beau aujourd'hui, vous ne trouvez pas ?",
                "Je ne comprends pas.",
                "Hmmm, hmm ..."
        };
        for (String randomResponse : randomResponses) {
            if (getRandom().nextBoolean()) {
                response = randomResponse;
                return response;
            }
        }

        // Default answer
        if (name != null) {
            response = "Qu'est-ce qui vous fait dire cela, " + name + " ?";
        } else {
            response = "Qu'est-ce qui vous fait dire cela ?";
        }
        return response;
    }

    /** Pick an element randomly in the array. */
    public <T> T pickRandom(final T[] array) {
        return array[random.nextInt(array.length)];
    }

    /**
     * return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get random.
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Set the name.
     */
    public void setName(final String name) {
        this.name = name;
    }

}
