package dk.via.viewmodel;

import dk.via.model.Model;
import javafx.application.Platform;
import javafx.beans.property.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LoginViewModel {
    private StringProperty userName;
    private StringProperty host;
    private StringProperty error;

    private BooleanProperty connected;

    private Model model;

    public LoginViewModel(Model model) {
        this.model = model;
        userName = new SimpleStringProperty();
        host = new SimpleStringProperty();
        connected = new SimpleBooleanProperty(false);
        error = new SimpleStringProperty();
    }

    public void connect() throws IOException {
        try {
            connectedProperty().setValue(model.connect(hostProperty().get(), userNameProperty().get()));
        } catch (UnknownHostException e) {
            error.set("Could not find host");
        } catch (SocketException e) {
            error.set("An error has occured");
        }
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty errorProperty() {
        return error;
    }


    public StringProperty hostProperty() {
        return host;
    }

    public BooleanProperty connectedProperty() {
        return connected;
    }
}
