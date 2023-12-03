package fr.univ_lyon1.info.m1.elizagpt.controller;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy.SearchStrategy;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import java.util.stream.Collectors;
import org.reflections.Reflections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.util.Set;

/**
 * Main class of the controller.
 */
public class MessageController {
    private MessageProcessor model;
    private List<JfxView> views;
    private ObservableList<SearchStrategy> listeObservable = FXCollections.observableArrayList();
    private SearchStrategy currentSearchStrategy;

    /**
     * Constructor.
     * 
     * @param view
     */
    public MessageController(final List<JfxView> view) {
        this.model = new MessageProcessor();
        this.views = view;
        this.chargerStrategies();
        for (JfxView v : views) {
            v.setController(this);
        }
    }

    public void chargerStrategies() {
        Reflections reflections = new Reflections("fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy");
        Set<Class<? extends SearchStrategy>> classes = reflections.getSubTypesOf(SearchStrategy.class);

        for (Class<? extends SearchStrategy> classe : classes) {
            try {
                SearchStrategy strategy = classe.getDeclaredConstructor().newInstance();
                listeObservable.add(strategy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changeSearchStrategy(SearchStrategy newValue) {
        this.currentSearchStrategy = newValue;
    }

    public ObservableList<SearchStrategy> getListeObservable() {
        return listeObservable;
    }

    /**
     * Process the sended message, generate a response and call the view to display
     * it.
     * 
     * @param input
     */
    public void processUserInput(final String input) {

        String response = model.generateResponse(model.normalize(input));
        // create random string id
        String messageIdUser = String.valueOf(model.getRandom().nextInt());
        String messageIdEliza = String.valueOf(model.getRandom().nextInt());
        for (JfxView v : views) {
            v.displayMessages(input, "user", messageIdUser);
            v.displayMessages(response, "eliza", messageIdEliza);
        }
    }

    /**
     * Process the receveid message.
     * 
     * @param messageId
     */
    public void deleteMessageViews(final String messageId) {
        for (JfxView v : views) {
            HBox hBox = v.getHBoxMap().get(messageId);
            if (hBox != null) {
                v.getDialog().getChildren().remove(hBox);
                v.getHBoxMap().remove(messageId);
            }
            if (v.getChatHistory() != null) {
                v.getChatHistory().remove(hBox);
            }
        }
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
     * Search for a text in the chat.
     */
    public void searchText(final String text) {
        saveChatState(); // On sauvegarde l'état actuel du Chat.
        for (JfxView view : views) {
            view.getSearchText().setText(text);
            String currentSearchText = view.getSearchText().getText();
            if (currentSearchText == null || currentSearchText.isEmpty()) {
                view.getSearchTextLabel().setText("No active search");
            } else {
                view.getSearchTextLabel().setText("Searching for: " + currentSearchText);
            }

            List<HBox> toDelete = new ArrayList<>();
            for (Node hBox : view.getDialog().getChildren()) {
                for (Node label : ((HBox) hBox).getChildren()) {
                    String labelText = ((Label) label).getText();
                    if (labelText != null && !labelText.isEmpty()) {
                        if (!currentSearchStrategy.search(labelText, currentSearchText)) {
                            toDelete.add((HBox) hBox);
                        }
                    }
                }
            }
            view.getDialog().getChildren().removeAll(toDelete);
            // text.setText("");
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
