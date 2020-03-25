package dk.via.view;

import java.io.IOException;

import dk.via.App;
import dk.via.viewmodel.ChatViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class ChatView {
    private Region root;
    private ViewHandler viewHandler;
    private ChatViewModel chatViewModel;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void init(ViewHandler viewHandler, ChatViewModel chatViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.chatViewModel = chatViewModel;
        this.root = root;
    }

    public void reset() {
    }

    public Region getRoot() {
        return root;
    }
}