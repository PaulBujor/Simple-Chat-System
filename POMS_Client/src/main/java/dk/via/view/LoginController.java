package dk.via.view;

import java.awt.*;
import java.io.IOException;

import dk.via.App;
import dk.via.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class LoginController {
    @FXML
    private TextField IPAddressField;

    @FXML
    private TextField portLoginField;

    @FXML
    private TextField userNameLoginField;

    @FXML
    private Button connectButton;

    private Region root;
    private ViewHandler viewHandler;
    private LoginViewModel loginViewModel;

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

    @FXML
    private void connectButtonPressed() {
        //todo if model.connect returns true change view, else show error
        viewHandler.openView("chat");
    }

}
