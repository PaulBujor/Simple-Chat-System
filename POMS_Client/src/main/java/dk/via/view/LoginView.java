package dk.via.view;

import java.io.IOException;

import dk.via.App;
import javafx.fxml.FXML;

public class LoginView {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
