package dk.via.view;

import java.io.IOException;

import dk.via.App;
import dk.via.viewmodel.ChatViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class ChatController {
    @FXML
    private Label chatIPLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField clientMessage;

    @FXML
    private Button sendButton;


    private Region root;
    private ViewHandler viewHandler;
    private ChatViewModel chatViewModel;


    public ChatController() {
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

    @FXML
    private void sendButtonPressed() {

    }
}