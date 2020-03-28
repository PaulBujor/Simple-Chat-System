package dk.via.viewmodel;

import dk.via.model.Model;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChatViewModel implements PropertyChangeListener {
    private Model model;
    private StringProperty username;
    private IntegerProperty connectedUsers;
    private StringProperty ip;


    public ChatViewModel(Model model) {
        this.model = model;
        username = new SimpleStringProperty();
        connectedUsers = new SimpleIntegerProperty();
        ip = new SimpleStringProperty();
        model.addListener(this);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public IntegerProperty connectedUsersProperty() {
        return connectedUsers;
    }

    public StringProperty ipProperty() {
        return ip;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("connectedUpdate")) {
            Platform.runLater(() -> connectedUsers.set(model.getConnectedUsers()));
        } else if(evt.getPropertyName().equals("loadData")) {
            System.out.println("loading data");
            Platform.runLater(() -> {
                connectedUsers.set(model.getConnectedUsers());
                username.set(model.getUsername());
                ip.set(model.getIP());
            });
        }

    }
}
