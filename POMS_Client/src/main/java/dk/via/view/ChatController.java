package dk.via.view;

import java.io.IOException;

import dk.via.App;
import dk.via.utility.StringIntegerConverter;
import dk.via.viewmodel.ChatViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

public class ChatController {
    @FXML
    private Label chatIPLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label onlineUsers;

    @FXML
    private TextField clientMessage;

    @FXML
    private TableView<TableRowData> chatTable;

    @FXML
    private TableColumn<TableRowData, String> messageColumn;

    private Region root;
    private ViewHandler viewHandler;
    private ChatViewModel chatViewModel;

    public ChatController() {
    }

    public void init(ViewHandler viewHandler, ChatViewModel chatViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.chatViewModel = chatViewModel;
        this.root = root;

        userNameLabel.textProperty().bind(chatViewModel.usernameProperty());
        Bindings.bindBidirectional(onlineUsers.textProperty(), chatViewModel.connectedUsersProperty(), new StringIntegerConverter(0));
        chatIPLabel.textProperty().bind(chatViewModel.ipProperty());

        clientMessage.textProperty().bindBidirectional(chatViewModel.getClientMessage());

        messageColumn.setCellValueFactory(cellData -> cellData.getValue().getUserMessage());
chatTable.setItems(chatViewModel.getList());



    }

    public void reset() {
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void sendButtonPressed() {
chatViewModel.sendMessage();
    }
}