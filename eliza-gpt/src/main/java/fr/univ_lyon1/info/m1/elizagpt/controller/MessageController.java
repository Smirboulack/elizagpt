package fr.univ_lyon1.info.m1.elizagpt.controller;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.util.Random;

/**
 * Main class of the controller.
 */
public class MessageController {
    private MessageProcessor model;
    private List<JfxView> views;
    private final Random random = new Random();

    /**
     * Constructor.
     * 
     * @param view
     */
    public MessageController(final List<JfxView> view) {
        this.model = new MessageProcessor();
        this.views = view;
        for (JfxView v : views) {
            v.setController(this);
        }
        // setupListeners();
    }

    /**
     * Process the sended message, generate a response and call the view to display
     * it.
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
        // create random string id
        String messageIdUser = String.valueOf(random.nextInt());
        String messageIdEliza = String.valueOf(random.nextInt());
        for (JfxView v : views) {
            v.displayMessage(input, "user", messageIdUser);
            v.displayMessage(response, "eliza", messageIdEliza);
        }
    }

    /**
     * Process the receveid message.
     * 
     * @param messageId
     */
    public void deleteMessageViews(final String messageId) {
        for (JfxView v : views) {
            v.removeMessage(messageId);
        }
    }

    private String generateResponse(final String normalizedText) {
        Matcher matcher;
        String response;
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

    private void searchText(final TextField text, final JfxView view) {
        String currentSearchText = text.getText();
        if (currentSearchText == null || currentSearchText.isEmpty()) {
            view.getSearchTextLabel().setText("No active search");
        } else {
            view.getSearchTextLabel().setText("Searching for: " + currentSearchText);
        }

        Pattern pattern = null;
        try {
            pattern = Pattern.compile(currentSearchText, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            // Handle invalid regular expression
            e.printStackTrace();
            return;
        }

        List<HBox> toDelete = new ArrayList<>();
        for (Node hBox : view.getDialog().getChildren()) {
            for (Node label : ((HBox) hBox).getChildren()) {
                String labelText = ((Label) label).getText();
                boolean matches = (pattern.matcher(labelText).find());
                if (!matches) {
                    toDelete.add((HBox) hBox);
                    break; // No need to check other labels within this HBox
                }
            }
        }
        view.getDialog().getChildren().removeAll(toDelete);
        // text.setText("");
    }

    /**
     * Save the current state of the chat.
     */
    public void saveChatState() {
        for (JfxView jfxView : views) {
            jfxView.setChatHistory(new ArrayList<>(jfxView.getDialog().getChildren().stream()
                    .filter(node -> node instanceof HBox)
                    .map(node -> (HBox) node)
                    .collect(Collectors.toList())));
        }
    }

    /**
     * Remove a HBox from the ChatHistory.
     */
    public void removeHBox(final HBox hBox) {
        for (JfxView jfxView : views) {
            jfxView.getChatHistory().remove(hBox);
        }
    }

    /**
     * Perform a search.
     * 
     * @param searchText
     */
    public void performSearch(final String searchText) {
        // On sauvegarde l'état actuel du Chat.
        saveChatState();
        // On effectue la recherche
        for (JfxView jfxView : views) {
            TextField textField = jfxView.getSearchText();
            textField.setText(searchText);
            searchText(textField, jfxView);
        }
    }

    /**
     * Undo the search.
     */
    public void undoSearch() {
        // On restaure l'état original de la vue
        for (JfxView jfxView : views) {
            if (jfxView.getChatHistory() != null) {
                jfxView.getDialog().getChildren().clear();
                jfxView.getSearchTextLabel().setText("");
                jfxView.getDialog().getChildren().addAll(jfxView.getChatHistory());
            }
            jfxView.getSearchText().setText("");
        }

    }

}
