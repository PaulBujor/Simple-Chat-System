package dk.via.view;

import java.io.IOException;

import dk.via.App;
import dk.via.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class LoginView {
    private Region root;
    private ViewHandler viewHandler;
    private LoginViewModel loginViewModel;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.loginViewModel = loginViewModel;
        this.root = root;
    }

    public void reset() {
    }

    public Region getRoot() {
        return root;
    }
}
