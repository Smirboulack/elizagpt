package fr.univ_lyon1.info.m1.elizagpt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.reflections.Reflections;

import fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy.SearchStrategy;
import fr.univ_lyon1.info.m1.elizagpt.model.Processor;
import fr.univ_lyon1.info.m1.elizagpt.view.Message;
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
    private final Processor model;
    private final List<JfxView> views;
    private final ObservableList<SearchStrategy> listeObservable
            = FXCollections.observableArrayList();
    private SearchStrategy currentSearchStrategy;
    private boolean isChatStateSaved = false;

    /**
     * Constructor.
     * 
     * @param view the view to assigne
     */
    public MessageController(final List<JfxView> view) {
        this.model = new Processor();
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
        Reflections reflections =
                new Reflections("fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy");
        Set<Class<? extends SearchStrategy>> classes
                = reflections.getSubTypesOf(SearchStrategy.class);

        for (Class<? extends SearchStrategy> classe : classes) {
            try {
                SearchStrategy strategy = classe.getDeclaredConstructor().newInstance();
                listeObservable.add(strategy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**.
     *
     * @param view the view ti initialize
     */
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
     * @param newValue the new strategy used to search
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
     * @param input the message
     */
    public void processUserInput(final String input) {
        if (input == null || input.isEmpty()) {
            return;
        }
        int idu = model.getRandom().nextInt();
        int ide = model.getRandom().nextInt();
        Message message = new Message(idu, input, "user",
                DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                Message.USER_STYLE);
        Message response = new Message(ide, model.generateResponse(model.normalize(input)), "eliza",
                DateUtils.addSeconds(new java.util.Date(), 0).toString(), Message.ELIZA_STYLE);
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
     * @param message the message to delete
     */
    public void deleteMessageViews(final Message message) {
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
     * @param text the text to search in the history chat
     */
    public void searchText(final String text) {
        if (text == null || text.isEmpty() || currentSearchStrategy == null) {
            return;
        }
        saveChatState();
        for (JfxView view : views) {
            List<Message> filteredMessages = view.getMessages().stream()
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

    /**.
     *
     * @param theme the dark or white theme selected
     */
    public void changeViewsTheme(final String theme) {
        for (JfxView view : views) {
            if (theme.equals("Light")) {
                view.whiteTheme();
            } else {
                view.darkTheme();
            }
        }
    }
}
