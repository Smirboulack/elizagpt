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

public class MainController {
    private Processor model;
    private List<MainView> views;
    private ObservableList<SearchStrategy> listeObservable = FXCollections.observableArrayList();
    private SearchStrategy currentSearchStrategy;
    private List<Message> messagesSaved;

    /**
     * Constructor.
     * 
     * @param view
     */
    public MainController(final List<MainView> view) {
        this.model = new Processor();
        this.views = view;
        this.chargerStrategies();
        this.messagesSaved = new ArrayList<>();
        Message helloMessage = new TxtMessage("0", "eliza", DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                "Bonjour.");
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

    private Message createMessage(String input, File imagePath) {
        String idu = Integer.toString(model.getRandom().nextInt());
        if (imagePath != null && !input.isEmpty()) {
            return new CombinedMessage(idu, "user", DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                    model.normalize(input), imagePath.getAbsolutePath());
        } else if (imagePath != null) {
            return new ImageMessage(idu, "user", DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                    imagePath.getAbsolutePath());
        } else {
            return new TxtMessage(idu, "user", DateUtils.addSeconds(new java.util.Date(), 0).toString(),
                    model.normalize(input));
        }
    }

    private Message createResponse(final String input, final File imagePath) {
        String ide = Integer.toString(model.getRandom().nextInt());
        String responseText = model.generateResponse(model.normalize(input));

        if (imagePath != null && !input.isEmpty()) {
            String concat = "text : " + model.normalize(input) + " image : " + imagePath.getAbsolutePath();
            responseText = model.generateResponse(concat);
        } else if (imagePath != null) {
            responseText = model.generateResponse(imagePath.getAbsolutePath());
        }

        return new TxtMessage(ide, "eliza", DateUtils.addSeconds(new java.util.Date(), 0).toString(), responseText);
    }

    private void updateViews(Message message, Message response) {
        for (MainView view : views) {
            if (message instanceof TxtMessage) {
                view.displayTxtMessage(message.parseMessageString());
                view.displayTxtMessage(response.parseMessageString());
            } else if (message instanceof ImageMessage) {
                view.displayImageMessage(message.parseMessageString());
                view.displayTxtMessage(response.parseMessageString());
            } else if (message instanceof CombinedMessage) {
                view.displayCombinedMessage(message.parseMessageString(), ((CombinedMessage) message).getImageUrl());
                view.displayTxtMessage(response.parseMessageString());
            }
        }
    }

    /**
     * Process the receveid message.
     * 
     * @param message
     */
    public void deleteMessageViews(final String messageId) {
        for (MainView v : views) {
            v.getDialog().getChildren().removeIf(node -> node instanceof VBox &&
                    node.getId().equals(messageId));
        }
        messagesSaved.removeIf(m -> m.getId().equals(messageId));
    }

    /**
     * Search a text in the chat.
     * 
     * @param text
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
     * Undo the search.
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
