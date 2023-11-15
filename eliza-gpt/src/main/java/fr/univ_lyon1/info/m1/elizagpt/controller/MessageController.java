package fr.univ_lyon1.info.m1.elizagpt.controller;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
     * @param model
     * @param view
     */
    public MessageController(final MessageProcessor model, final List<JfxView> view) {
        this.model = model;
        this.views = view;
        setupListeners();
    }

    private void setupListeners() {
        for (JfxView v : views) {
            v.getSearch().setOnAction(e -> {
                performSearch(v.getSearchText().getText());
            });
            v.getText().setOnAction(e -> {
                processUserInput(v.getText().getText());
                v.getText().setText("");
            });
            v.getSend().setOnAction(e -> {
                processUserInput(v.getText().getText());
                v.getText().setText("");
            });
        }
    }

    private void handleSearch() {
        /* String searchPattern = view.getSearchText().toString(); */
        for (JfxView v : views) {
            String searchPattern = v.getSearchText().toString();
            List<HBox> filteredMessages = model.filterMessagesByPattern(searchPattern);
            v.updateMessages(filteredMessages);
            v.updateSearchLabel("Searching for: " + searchPattern);
            v.clearSearchText();
        }
        /*
         * List<HBox> filteredMessages = model.filterMessagesByPattern(searchPattern);
         * view.updateMessages(filteredMessages);
         * view.updateSearchLabel("Searching for: " + searchPattern);
         * view.clearSearchText();
         */
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
        for (JfxView v : views) {
            v.displayUserMessage(input);
            v.displayElizaMessage(response);
        }

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

    private void searchText(final TextField text, final List<JfxView> views) {
        String currentSearchText = text.getText();
        if (currentSearchText == null || currentSearchText.isEmpty()) {
            for (JfxView view : views) {
                view.getSearchTextLabel().setText("No active search");
            }
        } else {
            for (JfxView view : views) {
                view.getSearchTextLabel().setText("Searching for: " + currentSearchText);
            }
        }
    
        Pattern pattern = null;
        try {
            pattern = Pattern.compile(currentSearchText, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            // Handle invalid regular expression
            e.printStackTrace();
            return;
        }
    
        for (JfxView view : views) {
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
        }
        text.setText("");
    }
    
    
    /**
     * Perform a search.
     * 
     * @param searchText
     */
    public void performSearch(final String searchText) {

        for (JfxView jfxView : views) {
            TextField textField = jfxView.getSearchText();
            textField.setText(searchText);
            searchText(textField, views);
        }

        /* // Presuming 'views' is a list of JfxView objects
        for (JfxView view : views) {
            TextField textField = view.getSearchText();
            textField.setText(searchText);
    
            List<HBox> searchResults = searchText(textField, view);
            if (searchText == null || searchText.isEmpty()) {
                view.displaySearchResults(Collections.emptyList(), "");
            } else {
                view.displaySearchResults(searchResults, searchText);
            }
        } */
    }
    
    /*
     * public void performSearch(final String searchText) {
     * // Compile the pattern once, outside of the loop
     * Pattern pattern = null;
     * if (searchText != null && !searchText.isEmpty()) {
     * try {
     * pattern = Pattern.compile(searchText, Pattern.CASE_INSENSITIVE);
     * } catch (PatternSyntaxException e) {
     * // Handle invalid regular expression
     * e.printStackTrace();
     * for (JfxView view : views) {
     * view.displaySearchResults(Collections.emptyList(), searchText);
     * }
     * return;
     * }
     * }
     * 
     * // Perform the search if the pattern is not null
     * List<HBox> searchResults = new ArrayList<>();
     * if (pattern != null) {
     * for (Node hBox : views.get(1).getDialog().getChildren()) {
     * for (Node label : ((HBox) hBox).getChildren()) {
     * String labelText = ((Label) label).getText();
     * if (pattern.matcher(labelText).find()) {
     * searchResults.add((HBox) hBox);
     * break; // Found a match, no need to check other labels within this HBox
     * }
     * }
     * }
     * }
     * 
     * // Display the search results on all views
     * for (JfxView view : views) {
     * if (searchText == null || searchText.isEmpty()) {
     * view.displaySearchResults(Collections.emptyList(), "");
     * } else {
     * view.displaySearchResults(searchResults, searchText);
     * }
     * }
     * }
     */

}
