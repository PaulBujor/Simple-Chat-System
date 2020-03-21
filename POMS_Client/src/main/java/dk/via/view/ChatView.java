package dk.via.view;

import java.io.IOException;

import dk.via.App;
import javafx.fxml.FXML;

public class ChatView {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}