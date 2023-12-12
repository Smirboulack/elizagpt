package fr.univ_lyon1.info.m1.elizagpt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.reflections.Reflections;

import fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy.SearchStrategy;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.view.ChatMessage;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * Main class of the controller.
 */
public class MessageController {
    private MessageProcessor model;
    private List<JfxView> views;
    private ObservableList<SearchStrategy> listeObservable = FXCollections.observableArrayList();
    private SearchStrategy currentSearchStrategy;
    private boolean isChatStateSaved = false;

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
            initializeComboBox(v);
        }
    }

    /**
     * Load all the search strategies.
     */
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

    public void initializeComboBox(final JfxView view) {
        view.getComboBox().setItems(listeObservable);
        view.getComboBox().setConverter(new StringConverter<SearchStrategy>() {

            @Override
            public String toString(final SearchStrategy object) {
                return object.getClass().getSimpleName();
            }

            @Override
            public SearchStrategy fromString(final String string) {
                return null;
            }
        });
        view.getComboBox().getSelectionModel().selectFirst();
    }

    /**
     * Change the current search strategy.
     * 
     * @param newValue
     */
    public void changeSearchStrategy(final SearchStrategy newValue) {
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
        if (input == null || input.isEmpty()) {
            return;
        }
        int idu = model.getRandom().nextInt();
        int ide = model.getRandom().nextInt();
        ChatMessage message = new ChatMessage(idu, input, "user",
                DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                ChatMessage.USER_STYLE);
        ChatMessage response = new ChatMessage(ide, model.generateResponse(model.normalize(input)), "eliza",
                DateUtils.addSeconds(new java.util.Date(), 0).toString(), ChatMessage.ELIZA_STYLE);
        for (JfxView v : views) {
            v.getMessages().add(message);
            v.getMessages().add(response);
            v.displayMessage(message);
            v.displayMessage(response);
        }
    }

    /**
     * Process the receveid message.
     * 
     * @param message
     */
    public void deleteMessageViews(final ChatMessage message) {
        for (JfxView v : views) {
            // Supprimer le messageContainer de dialog
            v.getDialog().getChildren().removeIf(node -> {
                if (node instanceof VBox) {
                    VBox messageContainer = (VBox) node;
                    if (!messageContainer.getChildren().isEmpty()
                            && messageContainer.getChildren().get(1) instanceof HBox) {
                        HBox container = (HBox) messageContainer.getChildren().get(1);
                        return container.getId().equals(Integer.toString(message.getId()));
                    }
                }
                return false;
            });
            
            v.getMessages().removeIf(m -> m.getId() == message.getId());

            if (this.isChatStateSaved) {
                v.getMessagesSaved().removeIf(m -> m.getId() == message.getId());
            }
        }
    }

    /**
     * Save the current chat state.
     */
    public void saveChatState() {
        if (isChatStateSaved) {
            return;
        }
        for (JfxView view : views) {
            view.setMessagesSaved(new ArrayList<>(view.getMessages()));
        }
        isChatStateSaved = true;
    }

    /**
     * Search a text in the chat.
     * 
     * @param text
     */
    public void searchText(final String text) {
        if (text == null || text.isEmpty() || currentSearchStrategy == null) {
            return;
        }
        saveChatState();
        for (JfxView view : views) {
            List<ChatMessage> filteredMessages = view.getMessages().stream()
                    .filter(m -> currentSearchStrategy.search(m.getText(), text))
                    .collect(Collectors.toList());
            view.setMessages(filteredMessages);
            view.displayAllMessages();
        }
    }

    /**
     * Undo the search.
     */
    public void undoSearch() {
        if (!isChatStateSaved) {
            return;
        }
        for (JfxView view : views) {
            view.setMessages(new ArrayList<>(view.getMessagesSaved()));
            view.displayAllMessages();
        }
        isChatStateSaved = false;
    }
}
