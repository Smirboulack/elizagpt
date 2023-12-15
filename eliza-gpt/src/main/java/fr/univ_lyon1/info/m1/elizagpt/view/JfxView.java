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
import javafx.util.StringConverter;

/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView {
    private VBox dialog;
    private List<ChatMessage> messages = new ArrayList<>();
    private List<ChatMessage> messagesSaved = new ArrayList<>();
    private TextField text = null;
    private TextField searchText = null;
    private Label searchTextLabel = null;
    private ComboBox<SearchStrategy> comboBox;
    private MessageController controller;
    private ImageView imagePreview;
    private File selectedImageFile = null;
    private final Image trashImage;

    private Image im = null;

    /**
     * Main class of the View (GUI) of the application.
     */
    public JfxView(final Stage stage, final double width, final double height) {
        stage.setTitle("Eliza GPT");
        final VBox root = new VBox(10);
        this.comboBox = new ComboBox<>();
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
        this.trashImage = new Image("file:src/main/resources/trash.png");
        this.messages.add(new ChatMessage(1, "Bonjour.", null,"eliza", "Now", ChatMessage.ELIZA_STYLE));
        this.displayMessages();

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
     * @param controller
     */
    public void setController(final MessageController controller) {
        this.controller = controller;
        initializeComboBox();
    }

    private void initializeComboBox() {
        comboBox.setItems(controller.getListeObservable());
        comboBox.setConverter(new StringConverter<SearchStrategy>() {
            @Override
            public String toString(final SearchStrategy object) {
                return object.getClass().getSimpleName();
            }

            @Override
            public SearchStrategy fromString(final String string) {
                return null;
            }
        });
        comboBox.getSelectionModel().selectFirst();
    }

    /**
     * Display the messages in the dialog.
     */
    public void displayMessages() {
        dialog.getChildren().clear();
        for (ChatMessage message : messages) {
            Label timeLabel = new Label(message.getDate());
            timeLabel.setStyle("-fx-text-fill: grey; -fx-font-size: 10px;");

            // HBox pour le message
            HBox outerHBox = new HBox();

            // Style et contenu du message
            HBox innerHBox = new HBox(5.0);
            innerHBox.setStyle(message.getStyle());
            Label messageLabel = new Label();
            if (message.getImage() != null) {
                messageLabel.setGraphic(message.getImage());
            } else {
                messageLabel = new Label(message.getText());
            }
            messageLabel.setWrapText(true);
            innerHBox.getChildren().add(messageLabel);

            // Ajouter le message à la outerHBox
            outerHBox.getChildren().add(innerHBox);

            // Bouton de suppression avec icône de poubelle
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

            HBox container = new HBox(5); // Contient le message et le bouton de suppression
            container.setId(Integer.toString(message.getId()));
            container.getChildren().addAll(outerHBox, deleteButton);
            container.setAlignment(message.getAuthor()
                    .equals("user") ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

            messageContainer.getChildren().add(container);

            // Ajouter le messageContainer à dialog
            dialog.getChildren().add(messageContainer); // Ajouter messageContainer ici
        }
    }

    

    /*
     * public void displayMessages() {
     * dialog.getChildren().clear();
     * for (ChatMessage message : messages) {
     * HBox outerHBox = new HBox();
     * String idMessage = Integer.toString(message.getId());
     * outerHBox.setId(idMessage);
     * outerHBox
     * .setAlignment(message.getAuthor().equals("user") ? Pos.CENTER_RIGHT :
     * Pos.CENTER_LEFT);
     * 
     * HBox innerHBox = new HBox(5.0);
     * innerHBox.setStyle(message.getStyle());
     * 
     * Label messageLabel = new Label(message.getText());
     * messageLabel.setWrapText(true);
     * ImageView trashView = new ImageView(trashImage);
     * trashView.setFitHeight(20);
     * trashView.setFitWidth(20);
     * 
     * 
     * Button deleteButton = new Button();
     * deleteButton.setGraphic(trashView);
     * 
     * deleteButton.setOnAction(event -> {
     * controller.deleteMessageViews(message);
     * });
     * 
     * innerHBox.getChildren().addAll(messageLabel, deleteButton);
     * outerHBox.getChildren().add(innerHBox);
     * dialog.getChildren().add(outerHBox);
     * }
     * }
     */

    /**
     * Return the search text field.
     * 
     * @return the search text field.
     */
    public TextField getSearchText() {
        return searchText;
    }

    /**
     * Remove a message from the list of messages.
     * 
     */
    public void removeMessage(final ChatMessage message) {
        messages.removeIf(m -> m.getId() == message.getId());
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

    public void setDialog(final VBox dialog) {
        this.dialog = dialog;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(final List<ChatMessage> messages) {
        this.messages = messages;
    }

    public List<ChatMessage> getMessagesSaved() {
        return messagesSaved;
    }

    public void setMessagesSaved(final List<ChatMessage> messagesSaved) {
        this.messagesSaved = messagesSaved;
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
        comboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        controller.changeSearchStrategy(newValue);
                    }
                });

        firstLine.getChildren().addAll(searchText, comboBox);

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
            controller.processUserInput(text.getText(), null);
            this.text.setText("");
        });

        final Button send = new Button("Send");
        send.setOnAction(e -> {
            if (selectedImageFile != null) {
                im = new Image(selectedImageFile.toURI().toString());
                selectedImageFile = null;
                imagePreview.setImage(null);
                controller.processUserInput(text.getText(), im);
            } else if (selectedImageFile == null) {
                controller.processUserInput(text.getText(), null);
            }
            im = null;
            text.setText("");
        });

        // Création du bouton de sélection de fichier avec une icône
        Image fileIcon = new Image("file:src/main/resources/AML_pur.png");
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
        return input;
    }

    public void displayMessages(File imageFile, String user, String messageIdUser) {
    }
}
