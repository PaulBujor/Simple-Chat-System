package dk.via.viewmodel;

import dk.via.model.Model;
import dk.via.utility.Message;
import dk.via.view.TableRowData;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ChatViewModel implements PropertyChangeListener {
    private Model model;
    private StringProperty username;
    private IntegerProperty connectedUsers;
    private StringProperty ip;
    private ObservableList<TableRowData> list;
    private StringProperty clientMessage;

    public ChatViewModel(Model model) {
        this.model = model;
        username = new SimpleStringProperty(model.getUsername());
        connectedUsers = new SimpleIntegerProperty(model.getConnectedUsers());
        ip = new SimpleStringProperty(model.getIP());
        clientMessage = new SimpleStringProperty();
        list = createList();
        model.addListener(this);
    }
public StringProperty getClientMessage(){
        return clientMessage;
}

    public void sendMessage(){
        model.sendMessage(new Message(model.getUsername(),clientMessage.get()));
        clientMessage.set(null);
    }
    public StringProperty usernameProperty() {
        return username;
    }

    public IntegerProperty connectedUsersProperty() {
        return connectedUsers;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("connectedUpdate")) {
            Platform.runLater(() -> connectedUsers.set(model.getConnectedUsers()));
        } else if (evt.getPropertyName().equals("loadData")) {
            System.out.println("loading data");
            Platform.runLater(() -> {
                connectedUsers.set(model.getConnectedUsers());
                username.set(model.getUsername());
                ip.set(model.getIP());
            });
        } else if (evt.getPropertyName().equals("message")) {
            addToList((Message) evt.getNewValue());
        }

    }

    private ObservableList<TableRowData> createList() {
        ObservableList<TableRowData> list = FXCollections.observableArrayList();

        ArrayList<Message> messages = new ArrayList<>();
        messages.addAll(model.getMessages(50));

        for (int i = 0; i < messages.size(); i++) {
            list.add(new TableRowData(messages.get(i)));
        }
        return list;
    }

    public ObservableList<TableRowData> getList() {
        return list;
    }

    public StringProperty ipProperty() {
        return ip;
    }


    public void addToList(Message message) {
        Platform.runLater(() -> {
            list.add(0, new TableRowData(message));
            if (list.size() > 50) {
                list.remove(list.size() - 1);
            }
        });
    }
}

