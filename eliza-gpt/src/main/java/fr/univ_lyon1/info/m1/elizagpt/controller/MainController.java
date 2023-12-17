package fr.univ_lyon1.info.m1.elizagpt.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.reflections.Reflections;

import fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy.SearchStrategy;
import fr.univ_lyon1.info.m1.elizagpt.model.Processor;
import fr.univ_lyon1.info.m1.elizagpt.model.message.CombinedMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.message.ImageMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.message.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.message.TxtMessage;
import fr.univ_lyon1.info.m1.elizagpt.view.MainView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

/**
 * .
 * The MainController class coordinates interactions between views end the
 * model.
 * It manages every input and update views.
 */
public class MainController {
    private final Processor model;
    private final List<MainView> views;
    private final ObservableList<SearchStrategy> listeObservable; 
    private SearchStrategy currentSearchStrategy;
    private final List<Message> messagesSaved;

    /**
     * Constructor for the MainController.
     * It initialize the model and the views.
     * 
     * @param view the list of views associated to this controller
     */
    public MainController(final List<MainView> view) {
        this.model = new Processor();
        this.views = view;
        this.listeObservable = FXCollections.observableArrayList();
        this.chargerStrategies();
        this.messagesSaved = new ArrayList<>();
        Message helloMessage = new TxtMessage("0", "eliza",
                DateUtils.addSeconds(new java.util.Date(), 0).toString(), "Bonjour.");
        this.messagesSaved.add(helloMessage);
        for (MainView mainView : views) {
            mainView.setController(this);
            mainView.setMessageVisual();
            mainView.displayTxtMessage(helloMessage.parseMessageString());
            mainView.initializeComboBox(listeObservable);
        }
    }

    /**
     * Load all the search strategies.
     * 
     * @throws Exception the exception
     * @see Reflections
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
     * @param newValue the new searchStrategy
     */
    public void changeSearchStrategy(final SearchStrategy newValue) {
        this.currentSearchStrategy = newValue;
    }

    /**
     * .
     * Getter for listeObservable
     * 
     * @return listeObservable
     */
    public ObservableList<SearchStrategy> getListeObservable() {
        return listeObservable;
    }

    /**
     * .
     * The process which executed after user inputs it message
     * 
     * @param input     the text message
     * @param imagePath the path for the image (if an image is send)
     */
    public void processUserInput(final String input, final File imagePath) {
        if (input.isEmpty() && imagePath == null) {
            return;
        }

        Message message = createMessage(input, imagePath);
        Message response = createResponse(input, imagePath);

        this.messagesSaved.add(message);
        this.messagesSaved.add(response);

        updateViews(message, response);
    }

    /**
     * .
     * Create the appropriate message according what is send
     * 
     * @param input     the text message
     * @param imagePath the path for the image (if an image is send)
     * @return the appropriate Message
     */
    private Message createMessage(final String input, final File imagePath) {
        String idu = Integer.toString(model.getRandom().nextInt());
        if (imagePath != null && !input.isEmpty()) {
            return new CombinedMessage(idu, "user",
                    DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                    model.normalize(input), imagePath.getAbsolutePath());
        } else if (imagePath != null) {
            return new ImageMessage(idu, "user",
                    DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                    imagePath.getAbsolutePath());
        } else {
            return new TxtMessage(idu, "user",
                    DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                    model.normalize(input));
        }
    }

    /**
     * .
     * Create the appropriate response according to the user message
     * 
     * @param input     the text message
     * @param imagePath the path for the image (if an image is send)
     * @return the response Message
     */
    private Message createResponse(final String input, final File imagePath) {
        String ide = Integer.toString(model.getRandom().nextInt());
        String responseText = model.generateResponse(model.normalize(input));

        if (imagePath != null && !input.isEmpty()) {
            String concat = "text : " + model.normalize(input)
                    + " image : " + imagePath.getAbsolutePath();
            responseText = model.generateResponse(concat);
        } else if (imagePath != null) {
            responseText = model.generateResponse(imagePath.getAbsolutePath());
        }

        return new TxtMessage(ide, "eliza",
                DateUtils.addSeconds(new java.util.Date(), 0).toString(), responseText);
    }

    /**
     * .
     * Updating every views after a message and it response
     * 
     * @param message  the message of the user
     * @param response the response for the previous message
     */
    private void updateViews(final Message message, final Message response) {
        for (MainView view : views) {
            if (message instanceof TxtMessage) {
                view.displayTxtMessage(message.parseMessageString());
                view.displayTxtMessage(response.parseMessageString());
            } else if (message instanceof ImageMessage) {
                view.displayImageMessage(message.parseMessageString());
                view.displayTxtMessage(response.parseMessageString());
            } else if (message instanceof CombinedMessage) {
                view.displayCombinedMessage(message.parseMessageString(),
                        ((CombinedMessage) message).getImageUrl());
                view.displayTxtMessage(response.parseMessageString());
            }
        }
    }

    /**
     * Deletes the message with the given id.
     * 
     * @param messageId the id message to delete
     */
    public void deleteMessageViews(final String messageId) {
        for (MainView v : views) {
            v.getDialog().getChildren().removeIf(node -> node instanceof VBox
                    && node.getId().equals(messageId));
        }
        messagesSaved.removeIf(m -> m.getId().equals(messageId));
    }

    /**
     * Search a text in the chat.
     * 
     * @param text the text to search
     * @see SearchStrategy
     */
    public void searchText(final String text) {
        // saveChatState();
        for (MainView view : views) {
            view.getDialog().getChildren().clear();
            for (Message message : messagesSaved) {
                if (message instanceof TxtMessage) {
                    String txtmessage = ((TxtMessage) message).getText();
                    if (currentSearchStrategy.search(txtmessage, text)) {
                        view.displayTxtMessage(message.parseMessageString());
                    }
                }
            }
        }
    }

    /**
     * Undo the search and display the chat as it was before the search.
     */
    public void undoSearch() {
        for (MainView view : views) {
            view.getDialog().getChildren().clear();
            for (Message message : messagesSaved) {
                if (message instanceof TxtMessage) {
                    view.displayTxtMessage(message.parseMessageString());
                } else if (message instanceof ImageMessage) {
                    view.displayImageMessage(message.parseMessageString());
                } else if (message instanceof CombinedMessage) {
                    view.displayCombinedMessage(message.parseMessageString(),
                            ((CombinedMessage) message).getImageUrl());
                }
            }
        }
    }

    /**
     * .
     * Switch from dark to white theme and vice versa
     * 
     * @param theme the theme to switch
     */
    public void changeViewsTheme(final String theme) {
        for (MainView view : views) {
            if (theme.equals("Light")) {
                view.whiteTheme();
            } else {
                view.darkTheme();
            }
        }
    }
}
