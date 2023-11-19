package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import fr.univ_lyon1.info.m1.elizagpt.controller.MessageController;

/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView {
    private final VBox dialog;
    private Map<String, HBox> hBoxMap = new HashMap<>();
    private List<HBox> chatHistory;
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private MessageController controller;

    /**
     * Main class of the View (GUI) of the application.
     */
    public JfxView(final Stage stage, final double width, final double height) {
        stage.setTitle("Eliza GPT");
        final VBox root = new VBox(10);
        final Pane search = createSearchWidget();
        root.getChildren().add(search);

        ScrollPane dialogScroll = new ScrollPane();
        dialog = new VBox(10);
        dialogScroll.setContent(dialog);
        // scroll to bottom by default:
        dialogScroll.vvalueProperty().bind(dialog.heightProperty());
        root.getChildren().add(dialogScroll);
        dialogScroll.setFitToWidth(true);

        final Pane input = createInputWidget();
        root.getChildren().add(input);
        displayMessages("Bonjour", "eliza", "0");

        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        text.requestFocus();
        stage.show();
    }

    /**
     * Set the controller of the view.
     * 
     * @param controller
     */
    public void setController(final MessageController controller) {
        this.controller = controller;
    }

    static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;

    /**
     * Display a message according to its author.
     */
    public void displayMessages(final String message, final String author, final String messageId) {
        HBox hBox = new HBox();
        Label label = new Label(message);
        if (author.equals("user")) {
            label.setStyle(USER_STYLE);
            hBox.setAlignment(Pos.BASELINE_RIGHT);
        } else if (author.equals("eliza")) {
            label.setStyle(ELIZA_STYLE);
            hBox.setAlignment(Pos.BASELINE_LEFT);
        }
        hBox.getChildren().add(label);
        hBoxMap.put(messageId, hBox);
        dialog.getChildren().add(hBox);
        hBox.setOnMouseClicked(e -> {
            controller.deleteMessageViews(messageId);
        });
    }

    /**
     * Return the search text field.
     * 
     * @return the search text field.
     */
    public TextField getSearchText() {
        return searchText;
    }

    /**
     * Update the text of the search label.
     * 
     * @param text
     */
    public void updateSearchLabel(final String text) {
        searchTextLabel.setText(text);
    }

    /**
     * Return the search text label.
     * 
     * @return
     */
    public Label getSearchTextLabel() {
        return searchTextLabel;
    }

    /*
     * return the text of the search text field.
     */
    public String getInputText() {
        return text.getText();
    }

    /**
     * clear the search text field.
     */
    public void clearSearchText() {
        searchText.setText("");
    }

    /**
     * clear the input text field.
     */
    public void clearInputText() {
        text.setText("");
    }

    /**
     * Return the dialog.
     * 
     * @return
     */
    public VBox getDialog() {
        return dialog;
    }

    /**
     * return the chatHistory.
     */
    public List<HBox> getChatHistory() {
        return this.chatHistory;
    }

    /**
     * Set the chatHistory.
     *
     * @param chatHistory
     */
    public void setChatHistory(final List<HBox> chatHistory) {
        this.chatHistory = new ArrayList<>(chatHistory);
    }

    /**
     * Create the search widget.
     */
    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);
        searchText = new TextField();
        searchText.setOnAction(e -> {
            controller.searchText(searchText.getText());
        });
        firstLine.getChildren().add(searchText);
        final Button send = new Button("Search");
        send.setOnAction(e -> {
            controller.searchText(searchText.getText());
        });
        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        undo.setOnAction(e -> {
            controller.undoSearch();
        });
        secondLine.getChildren().addAll(send, searchTextLabel, undo);
        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine);
        return input;
    }

    /**
     * Create the input widget.
     */
    private Pane createInputWidget() {
        final Pane input = new HBox();
        text = new TextField();
        text.setOnAction(e -> {
            controller.processUserInput(text.getText());
            this.text.setText("");
        });
        final Button send = new Button("Send");
        send.setOnAction(e -> {
            controller.processUserInput(text.getText());
            this.text.setText("");
        });
        input.getChildren().addAll(text, send);
        return input;
    }

    public Map<String, HBox> getHBoxMap() {
        return hBoxMap;
    }
}
