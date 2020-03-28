package dk.via.view;

import java.io.IOException;

import dk.via.App;
import dk.via.utility.StringIntegerConverter;
import dk.via.viewmodel.LoginViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class LoginController {
    @FXML
    private TextField IPAddressField;

    @FXML
    private TextField portLoginField;

    @FXML
    private TextField userNameLoginField;

    @FXML
    private Button connectButton;

    @FXML
    private Label errorLabel = null;

    private Region root;
    private ViewHandler viewHandler;
    private LoginViewModel loginViewModel;

    public LoginController() {
    }

    public void init(ViewHandler viewHandler, LoginViewModel loginViewModel,
                     Region root) {
        this.viewHandler = viewHandler;
        this.loginViewModel = loginViewModel;
        this.root = root;
        IPAddressField.textProperty()
                .bindBidirectional(loginViewModel.hostProperty());

        Bindings.bindBidirectional(portLoginField.textProperty(),
                loginViewModel.portProperty(), new StringIntegerConverter(0));

        loginViewModel.connectedProperty().addListener((evt) -> {
            if (loginViewModel.connectedProperty().get() == true)
                viewHandler.openView("chat");
            else
                errorLabel.setText("Connection to the server failed...");
        }));
    }

    public void reset() {
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void connectButtonPressed() throws IOException {
        loginViewModel.connect();
    }

}
