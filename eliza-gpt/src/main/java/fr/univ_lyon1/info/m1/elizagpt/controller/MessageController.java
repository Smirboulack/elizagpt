package fr.univ_lyon1.info.m1.elizagpt.controller;

import java.util.List;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;

import javafx.scene.layout.HBox;

public class MessageController {
    private MessageProcessor model;
    private JfxView view;

    public MessageController(MessageProcessor model, JfxView view) {
        this.model = model;
        this.view = view;
        setupListeners();
    }

    private void setupListeners() {
        view.getSearchText().setOnAction(e -> handleSearch());

    }

    private void handleSearch() {
        String searchPattern = view.getSearchText().toString();
        List<HBox> filteredMessages = model.filterMessagesByPattern(searchPattern);
        view.updateMessages(filteredMessages);
        view.updateSearchLabel("Searching for: " + searchPattern);
        view.clearSearchText();
    }

}
