package fr.univ_lyon1.info.m1.elizagpt.view;

import fr.univ_lyon1.info.m1.elizagpt.controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MessageVisual {
    private static MessageVisual instance;

    private VBox messageVisual;
    private final Image trashImage;
    private ImageView imageView;
    private final MainController controller;

    public static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    public static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    public static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;

    // Le constructeur est privé
    private MessageVisual(final MainController controller) {
        this.messageVisual = new VBox();
        this.imageView = null;
        this.trashImage = new Image("file:src/main/resources/trash.png");
        this.controller = controller;
    }

    // Méthode statique pour obtenir l'instance
    public static synchronized MessageVisual getInstance(final MainController controller) {
        if (instance == null) {
            instance = new MessageVisual(controller);
        }
        return instance;
    }

    public VBox getMessageVisual() {
        return messageVisual;
    }

    public void setMessageVisual(final VBox messageVisual) {
        this.messageVisual = messageVisual;
    }

    public void createTxtMessageVisual(final String message[]) {

        Label timeLabel = new Label(message[2]);
        timeLabel.setStyle("-fx-text-fill: grey; -fx-font-size: 10px;");

        // HBox pour le message
        HBox outerHBox = new HBox();

        // Style et contenu du message
        HBox innerHBox = new HBox(5.0);
        innerHBox.setStyle(message[1].equals("user") ? USER_STYLE : ELIZA_STYLE);
        Label messageLabel = new Label(message[3]);
        messageLabel.setWrapText(true);
        innerHBox.getChildren().add(messageLabel);

        outerHBox.getChildren().add(innerHBox);

        ImageView trashView = new ImageView(trashImage);
        trashView.setFitHeight(25);
        trashView.setFitWidth(25);
        Button deleteButton = new Button();
        deleteButton.setGraphic(trashView);
        deleteButton.setOnAction(event -> {
            controller.deleteMessageViews(message[0]);
        });

        VBox messageContainer = new VBox(2);
        messageContainer.getChildren().add(timeLabel);
        messageContainer.setAlignment(message[1].equals("user") ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        HBox container = new HBox(5);
        messageContainer.setId(message[0]);
        container.getChildren().addAll(outerHBox, deleteButton);
        container.setAlignment(message[1].equals("user") ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        messageContainer.getChildren().add(container);

        messageVisual = messageContainer;

    }

    public void createImageMessageVisual(final String message[]) {
        Label timeLabel = new Label(message[2]);
        timeLabel.setStyle("-fx-text-fill: grey; -fx-font-size: 10px;");

        // Créer un ImageView pour l'image
        Image image = new Image("file:" + message[3]);
        this.imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        // HBox pour le message
        HBox outerHBox = new HBox();

        // Style et contenu du message
        HBox innerHBox = new HBox(5.0);
        innerHBox.setStyle(message[1].equals("user") ? USER_STYLE : ELIZA_STYLE);
        innerHBox.getChildren().add(imageView);

        outerHBox.getChildren().add(innerHBox);

        ImageView trashView = new ImageView(trashImage);
        trashView.setFitHeight(25);
        trashView.setFitWidth(25);
        Button deleteButton = new Button();
        deleteButton.setGraphic(trashView);
        deleteButton.setOnAction(event -> {
            controller.deleteMessageViews(message[0]);
        });

        VBox messageContainer = new VBox(2);
        messageContainer.getChildren().add(timeLabel);
        messageContainer.setAlignment(message[1].equals("user") ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        HBox container = new HBox(5);
        messageContainer.setId(message[0]);
        container.getChildren().addAll(outerHBox, deleteButton);
        container.setAlignment(message[1].equals("user") ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        messageContainer.getChildren().add(container);

        messageVisual = messageContainer;
    }

    public void createCombinedMessageVisual(final String message[], final String messageUrl) {
        Label timeLabel = new Label(message[2]);
        timeLabel.setStyle("-fx-text-fill: grey; -fx-font-size: 10px;");

        // Créer un ImageView pour l'image
        Image image = new Image("file:" + messageUrl);
        this.imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        // HBox pour le message
        HBox outerHBox = new HBox();

        // Style et contenu du message
        HBox innerHBox = new HBox(5.0);
        innerHBox.setStyle(message[1].equals("user") ? USER_STYLE : ELIZA_STYLE);
        Label messageLabel = new Label(message[3]);
        messageLabel.setWrapText(true);
        innerHBox.getChildren().add(messageLabel);
        innerHBox.getChildren().add(imageView);

        outerHBox.getChildren().add(innerHBox);

        ImageView trashView = new ImageView(trashImage);
        trashView.setFitHeight(25);
        trashView.setFitWidth(25);
        Button deleteButton = new Button();
        deleteButton.setGraphic(trashView);
        deleteButton.setOnAction(event -> {
            controller.deleteMessageViews(message[0]);
        });

        VBox messageContainer = new VBox(2);
        messageContainer.getChildren().add(timeLabel);
        messageContainer.setAlignment(message[1].equals("user") ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        HBox container = new HBox(5);
        messageContainer.setId(message[0]);
        container.getChildren().addAll(outerHBox, deleteButton);
        container.setAlignment(message[1].equals("user") ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        messageContainer.getChildren().add(container);

        messageVisual = messageContainer;
    }

}
