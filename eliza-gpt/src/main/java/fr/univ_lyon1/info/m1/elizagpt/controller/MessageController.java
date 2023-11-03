package fr.univ_lyon1.info.m1.elizagpt.controller;

import java.util.List;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.layout.HBox;
import java.util.Random;

/**
 * Main class of the controller.
 */
public class MessageController {
    private MessageProcessor model;
    private JfxView view;
    private final Random random = new Random();

    /**
     * Constructor.
     * 
     * @param model
     * @param view
     */
    public MessageController(final MessageProcessor model, final JfxView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        setupListeners();
    }

    private void setupListeners() {
        view.getSearchText().setOnAction(e -> handleSearch());
    }

    private void handleSearch() {
        String searchPattern = view.getSearchText().toString();
        List<HBox> filteredMessages = model.filterMessagesByPattern(searchPattern);
        view.updateMessages(filteredMessages);
        view.updateSearchLabel("Searching for: " + searchPattern);
        view.clearSearchText();
    }

    /**
     * Process the user input.
     * 
     * @param input
     */
    public void processUserInput(final String input) {
        Matcher nameMatcher = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE)
                .matcher(input);
        if (nameMatcher.matches()) {
            model.setName(nameMatcher.group(1)); // Update the user's name in the model
        }

        String response = generateResponse(model.normalize(input));
        view.displayUserMessage(input);
        System.out.println(response);
        view.displayElizaMessage(response);
        System.out.println("l'affichage est fini");
    }

    private String generateResponse(final String normalizedText) {
        Matcher matcher;
        String response;
        System.out.println(model.getName());
        matcher = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE)
                .matcher(normalizedText);
        if (matcher.matches()) {
            return "Bonjour " + matcher.group(1) + ".";
        }
        matcher = Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE)
                .matcher(normalizedText);
        if (matcher.matches()) {
            String name = model.getName();
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
            String startQuestion = model.pickRandom(new String[] {
                    "Pourquoi dites-vous que ",
                    "Pourquoi pensez-vous que ",
                    "Êtes-vous sûr que ",
            });
            response = startQuestion + model.firstToSecondPerson(matcher.group(1)) + " ?";
            return response;
        }

        matcher = Pattern.compile(".*\\?$", Pattern.CASE_INSENSITIVE).matcher(normalizedText);
        if (matcher.matches()) {
            String startQuestion = model.pickRandom(new String[] {
                    "Je vous renvoie la question.",
                    "Ici, c'est moi qui pose les questions."
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
            if (random.nextBoolean()) {
                response = randomResponse;
                return response;
            }
        }

        // Default answer
        String name = model.getName();
        if (name != null) {
            response = "Qu'est-ce qui vous fait dire cela, " + name + " ?";
        } else {
            response = "Qu'est-ce qui vous fait dire cela ?";
        }
        return response;
    }

}
