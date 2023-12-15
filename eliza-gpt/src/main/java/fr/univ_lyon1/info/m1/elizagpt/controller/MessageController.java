package fr.univ_lyon1.info.m1.elizagpt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        }
    }

    /**
     * Load all the search strategies.
     */
    public void chargerStrategies() {
        Reflections reflections =
         new Reflections("fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy");
        Set<Class<? extends SearchStrategy>> classes = 
        reflections.getSubTypesOf(SearchStrategy.class);

        for (Class<? extends SearchStrategy> classe : classes) {
            try {
                SearchStrategy strategy = classe.getDeclaredConstructor().newInstance();
                listeObservable.add(strategy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    public void processUserInput(final String input, final Image imageFile) {
            ImageView i = null;
            int idu = model.getRandom().nextInt();
            int ide = model.getRandom().nextInt();

            if (imageFile != null) { // S'il y a une image
                i = new ImageView(imageFile);
                ChatMessage message = new ChatMessage(idu, input, i, "user",
                        DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                        ChatMessage.USER_STYLE);
                ChatMessage response =
                        new ChatMessage(ide, model.generateResponseForImage(model.normalize(input), imageFile), null, "eliza",
                                DateUtils.addSeconds(new java.util.Date(), 0).toString(), ChatMessage.ELIZA_STYLE);
                for (JfxView v : views) {
                    v.getMessages().add(new ChatMessage(message));
                    v.getMessages().add(response);
                    v.displayMessages();
                }
            } else if (imageFile == null) { // S'il n'y a pas d'image
                ChatMessage message = new ChatMessage(idu, input, null, "user",
                        DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                        ChatMessage.USER_STYLE);
                ChatMessage response =
                        new ChatMessage(ide, model.generateResponse(model.normalize(input)), null, "eliza",
                                DateUtils.addSeconds(new java.util.Date(), 0).toString(), ChatMessage.ELIZA_STYLE);
                for (JfxView v : views) {
                    v.getMessages().add(message);
                    v.getMessages().add(response);
                    v.displayMessages();
                }
            }
    }


      /*public void processUserImage(final Image imageFile) {
      if (imageFile == null) {
      return;
      }
          int idu = model.getRandom().nextInt();
          int ide = model.getRandom().nextInt();
          ImageView i = new ImageView(imageFile);
          ChatMessage message = new ChatMessage(idu, "", i, "user",
                  DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                  ChatMessage.USER_STYLE);
          ChatMessage response =
                  new ChatMessage(ide, model.generateResponse(mode ), null, "eliza",
                          DateUtils.addSeconds(new java.util.Date(), 0).toString(), ChatMessage.ELIZA_STYLE);
          for (JfxView v : views) {
              v.getMessages().add(message);
              v.getMessages().add(response);
              v.displayMessages();
          }
      }*/


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

        // Supprimer le message de la liste des messages
        v.removeMessage(message);

        // Si l'état du chat est sauvegardé, supprimer le message de la liste sauvegardée
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
            view.displayMessages();
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
            view.displayMessages();
        }
        isChatStateSaved = false;
    }
}
