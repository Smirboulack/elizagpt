package fr.univ_lyon1.info.m1.elizagpt.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.elizagpt.controller.MessageController;
import fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy.SearchStrategy;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView {
    private VBox dialog;
    private final VBox root;
    private List<Message> messages = new ArrayList<>();
    private List<Message> messagesSaved = new ArrayList<>();
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private ComboBox<SearchStrategy> strategySelect;
    private MessageController controller;
    private final ImageView imagePreview;
    private File selectedImageFile;
    private final Image trashImage;
    private Button themeToggleButton;
    private ImageView toggleImageView;

    /**
     * Main class of the View (GUI) of the application.
     */
    public JfxView(final Stage stage, final double width, final double height) {
        stage.setTitle("Eliza GPT");
        root = new VBox(10);
        root.setStyle("-fx-background-color: #FFFFFF;");
        this.strategySelect = new ComboBox<>();
        final Pane search = createSearchWidget();
        root.getChildren().addAll(search);

        ScrollPane dialogScroll = new ScrollPane();
        dialog = new VBox(10);
        dialog.setStyle("-fx-background-color: #FFFFFF;");
        dialogScroll.setContent(dialog);

        // scroll to bottom by default:
        dialogScroll.vvalueProperty().bind(dialog.heightProperty());
        root.getChildren().add(dialogScroll);
        dialogScroll.setFitToWidth(true);

        final Pane input = createInputWidget();
        root.getChildren().add(input);
        this.trashImage = new Image("file:src/main/resources/trash.png");
        this.displayMessage(new Message(1, "Bonjour.", "eliza", "Now", Message.ELIZA_STYLE));

        this.imagePreview = new ImageView();
        imagePreview.setFitHeight(100);
        imagePreview.setFitWidth(100);
        imagePreview.setPreserveRatio(true);
        root.getChildren().add(imagePreview);

        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        text.requestFocus();
        stage.show();
    }

    /**
     * Set the controller of the view.
     * 
     * @param controller the controller for the view
     */
    public void setController(final MessageController controller) {
        this.controller = controller;
    }

    /**
     * Display the messages in the dialog.
     */

    public void displayAllMessages() {
        dialog.getChildren().clear();
        for (Message message : messages) {
            dialog.getChildren().add(createMessageVisual(message));
        }
    }

    /**.
     * Display all messages
     * @param message the message to display
     */
    public void displayMessage(final Message message) {
        dialog.getChildren().add(createMessageVisual(message));
    }

    private VBox createMessageVisual(final Message message) {
        Label timeLabel = new Label(message.getDate());
        timeLabel.setStyle("-fx-text-fill: grey; -fx-font-size: 10px;");

        // HBox pour le message
        HBox outerHBox = new HBox();

        // Style et contenu du message
        HBox innerHBox = new HBox(5.0);
        innerHBox.setStyle(message.getStyle());
        Label messageLabel = new Label(message.getText());
        messageLabel.setWrapText(true);
        innerHBox.getChildren().add(messageLabel);

        outerHBox.getChildren().add(innerHBox);

        ImageView trashView = new ImageView(trashImage);
        trashView.setFitHeight(25);
        trashView.setFitWidth(25);
        Button deleteButton = new Button();
        deleteButton.setGraphic(trashView);
        deleteButton.setOnAction(event -> {
            controller.deleteMessageViews(message);
        });

        VBox messageContainer = new VBox(2);
        messageContainer.getChildren().add(timeLabel);
        messageContainer.setAlignment(message.getAuthor().equals("user") ? Pos.CENTER_RIGHT
                : Pos.CENTER_LEFT);

        HBox container = new HBox(5);
        container.setId(Integer.toString(message.getId()));
        container.getChildren().addAll(outerHBox, deleteButton);
        container.setAlignment(message.getAuthor()
                .equals("user") ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        messageContainer.getChildren().add(container);

        return messageContainer;

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
     * @param text the text
     */
    public void updateSearchLabel(final String text) {
        searchTextLabel.setText(text);
    }

    /**
     * Return the search text label.
     * 
     * @return searchTextLabel
     */
    public Label getSearchTextLabel() {
        return searchTextLabel;
    }

    /**.
     * return the text of the search text field.
     * @return text
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
     * @return the VBox dialog
     */
    public VBox getDialog() {
        return dialog;
    }

    public void setDialog(final VBox dialog) {
        this.dialog = dialog;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(final List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessagesSaved() {
        return messagesSaved;
    }

    public void setMessagesSaved(final List<Message> messagesSaved) {
        this.messagesSaved = messagesSaved;
    }

    public ComboBox<SearchStrategy> getComboBox() {
        return strategySelect;
    }

    public void setComboBox(final ComboBox<SearchStrategy> comboBox) {
        this.strategySelect = comboBox;
    }

    /**
     * Create the search widget.
     */
    private Pane createSearchWidget() {
        final HBox firstLine = new HBox(10);

        searchText = new TextField();
        searchText.setOnAction(e -> {
            controller.searchText(searchText.getText());
        });

        // Configuration du ComboBox
        strategySelect.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        controller.changeSearchStrategy(newValue);
                    }
                });

        firstLine.getChildren().addAll(searchText, strategySelect);

        final Button send = new Button("Search");
        send.setOnAction(e -> {
            controller.searchText(searchText.getText());
        });
        searchTextLabel = new Label();
        final Button undo = new Button("Undo search");
        undo.setOnAction(e -> {
            controller.undoSearch();
        });
        final HBox secondLine = new HBox(10);
        secondLine.setAlignment(Pos.BASELINE_LEFT);
        secondLine.getChildren().addAll(send, searchTextLabel, undo);

        final VBox input = new VBox(5);
        input.getChildren().addAll(firstLine, secondLine);
        return input;
    }

    /**
     * Create the input widget.
     */
    private Pane createInputWidget() {
        final HBox input = new HBox(10); // Espace entre les composants

        text = new TextField();
        text.setOnAction(e -> {
            controller.processUserInput(text.getText());
            this.text.setText("");
        });

        final Button send = new Button("Send");
        send.setOnAction(e -> {
            if (selectedImageFile != null) {
                // controller.processUserImage(selectedImageFile);
                selectedImageFile = null;
                imagePreview.setImage(null);
            }
            controller.processUserInput(text.getText());
            text.setText("");
        });

        // Création du bouton de sélection de fichier avec une icône
        Image fileIcon = new Image("file:src/main/resources/image_logo.png");
        ImageView iconView = new ImageView(fileIcon);
        iconView.setFitHeight(20);
        iconView.setFitWidth(20);

        final Button fileButton = new Button("", iconView);
        fileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("GIF", "*.gif"),
                    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                    new FileChooser.ExtensionFilter("SVG", "*.svg")

            );
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                selectedImageFile = file;
                Image image = new Image(file.toURI().toString());
                imagePreview.setImage(image);
            }
        });

        input.getChildren().addAll(text, send, fileButton);
        input.getChildren().add(darkModBox());
        return input;
    }

    private HBox darkModBox() {
        // Chargement des images
        Image moonImage = new Image("file:src/main/resources/moon.png");
        Image sunImage = new Image("file:src/main/resources/sun.png");

        // Créer le bouton avec l'image de la lune
        toggleImageView = new ImageView(moonImage);
        toggleImageView.setFitHeight(20);
        toggleImageView.setFitWidth(20);
        
        this.themeToggleButton = new Button("", toggleImageView);
        themeToggleButton.setText("Dark");

        themeToggleButton.setOnAction(event -> {
            // Changer le thème et l'image
            if (themeToggleButton.getText().equals("Dark")) {
                toggleImageView.setImage(sunImage);
                controller.changeViewsTheme("Dark");
            } else {
                toggleImageView.setImage(moonImage);
                controller.changeViewsTheme("Light");
            }
        });

        // Ajouter le bouton en haut à droite

        return new HBox(themeToggleButton);
    }

    /**.
     * Turn the application to the white theme
     */
    public void whiteTheme() {
        this.themeToggleButton.setText("Dark");
        this.toggleImageView.setImage(new Image("file:src/main/resources/moon.png"));
        this.root.setStyle(null);
        this.dialog.setStyle(null);
    }

    /**.
     * Turn the application to the dark theme
     */
    public void darkTheme() {
        this.themeToggleButton.setText("Light");
        this.toggleImageView.setImage(new Image("file:src/main/resources/sun.png"));
        this.root.setStyle("-fx-background-color: #000000;");
        this.dialog.setStyle("-fx-background-color: #323232;");
    }
}
