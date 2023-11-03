package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import fr.univ_lyon1.info.m1.elizagpt.controller.MessageController;

/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView {
    private final VBox dialog;
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private MessageController controller;

    /**
     * Main class of the View (GUI) of the application.
     */
    public JfxView(final Stage stage, final int width, final int height) {
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
        displayElizaMessage("Bonjour");

        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        text.requestFocus();
        stage.show();
    }

    /**
     * Set the controller of the view.
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
     * Send a message to Eliza.
     * @param text
     */
    private void sendMessage(final String text) {
        controller.processUserInput(text);
        this.text.setText("");
    }

    /**
     * Display message from user.
     * @param message
     */
    public void displayUserMessage(final String message) {
        // Logic to display user's message on the view
        HBox hBox = createUserMessageHBox(message);
        dialog.getChildren().add(hBox);
    }

    /**
     * Display message from Eliza.
     * @param message
     */
    public void displayElizaMessage(final String message) {
        // Logic to display Eliza's response on the view
        HBox hBox = createElizaMessageHBox(message);
        dialog.getChildren().add(hBox);
    }

    private HBox createUserMessageHBox(final String message) {
        HBox hBox = new HBox();
        Label label = new Label(message);
        label.setStyle(USER_STYLE);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.getChildren().add(label);
        // Add an event handler to handle clicks on the HBox
        hBox.setOnMouseClicked(event -> {
            // Remove the HBox (the message) on click
            dialog.getChildren().remove(hBox);
        });
        System.out.println("createUserMessageHBox");
        return hBox;
    }

    private HBox createElizaMessageHBox(final String message) {
        HBox hBox = new HBox();
        Label label = new Label(message);
        label.setStyle(ELIZA_STYLE);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.getChildren().add(label);
        hBox.setOnMouseClicked(e -> dialog.getChildren().remove(hBox));
        // Add any additional styling or behavior
        System.out.println("createElizaMessageHBox");
        return hBox;
    }

    public TextField getSearchText() {
        return searchText;
    }

    /**
     * Update the messages displayed in the dialog.
     * @param messages
     */
    public void updateMessages(final List<HBox> messages) {
        dialog.getChildren().clear();
        dialog.getChildren().addAll(messages);
    }

    /**
     * Update the text of the search label.
     * @param text
     */
    public void updateSearchLabel(final String text) {
        searchTextLabel.setText(text);
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
     * Extract the name of the user from the dialog.
     * TODO: this totally breaks the MVC pattern, never, ever, EVER do that.
     *
     * @return
     */
    private Pane createSearchWidget() {
        final HBox firstLine = new HBox();
        final HBox secondLine = new HBox();
        firstLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.setAlignment(Pos.BASELINE_LEFT);
        searchText = new TextField();
        searchText.setOnAction(e -> {
            searchText(searchText);
        });
        firstLine.getChildren().add(searchText);
        final Button send = new Button("Search");
        send.setOnAction(e -> {
            searchText(searchText);
        });
        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        undo.setOnAction(e -> {
            throw new UnsupportedOperationException("TODO: implement undo for search");
        });
        secondLine.getChildren().addAll(send, searchTextLabel, undo);
        final VBox input = new VBox();
        input.getChildren().addAll(firstLine, secondLine);
        return input;
    }

    private void searchText(final TextField text) {
        String currentSearchText = text.getText();
        if (currentSearchText == null || currentSearchText.isEmpty()) {
            searchTextLabel.setText("No active search");
        } else {
            searchTextLabel.setText("Searching for: " + currentSearchText);
        }

        List<HBox> toDelete = new ArrayList<>();
        Pattern pattern = null;

        try {
            pattern = Pattern.compile(currentSearchText, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            // Handle invalid regular expression
            e.printStackTrace();
            return;
        }

        for (Node hBox : dialog.getChildren()) {
            for (Node label : ((HBox) hBox).getChildren()) {
                String labelText = ((Label) label).getText();
                boolean matches = (pattern.matcher(labelText).find());
                if (!matches) {
                    toDelete.add((HBox) hBox);
                    break; // No need to check other labels within this HBox
                }
            }
        }

        dialog.getChildren().removeAll(toDelete);
        text.setText("");
    }

    private Pane createInputWidget() {
        final Pane input = new HBox();
        text = new TextField();
        text.setOnAction(e -> {
            sendMessage(text.getText());
            text.setText("");
        });
        final Button send = new Button("Send");
        send.setOnAction(e -> {
            sendMessage(text.getText());
            text.setText("");
        });
        input.getChildren().addAll(text, send);
        return input;
    }
}
