package dk.via.model;

import dk.via.mediator.ChatClient;
import dk.via.utility.Message;
import javafx.beans.property.IntegerProperty;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class ChatModel implements Model {
    private PropertyChangeSupport property;
    private ArrayList<Message> messages;
    private ChatClient chatClient;
    private Thread chatThread;
    private String host;
    private int port;
    private String username;
    private int connectedUsers = 0;

    public ChatModel() throws IOException {
        messages = new ArrayList<Message>();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setConnectedUsers(int connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    public boolean connect(String host, int port, String username) throws IOException {
        setHost(host);
        setPort(port);
        setUsername(username);
        chatClient = new ChatClient(host, port, username, this);
        if (chatClient.connect()) {
            chatThread = new Thread(chatClient);
            chatThread.setDaemon(true);
            chatThread.start();
            return true;
        }
        return false;
    }

    public void disconnect() {
        chatClient.disconnect();
        chatThread.interrupt();
    }

    @Override
    public void receiveMessage(Message message) {
        if (message.isIPRequest()) {
            System.out.println(message.getMessage());
            //todo send to viewmodel
        } else if (message.isConnectedUpdate()) {
            setConnectedUsers(Integer.parseInt(message.getMessage()));
            System.out.println(connectedUsers);
            //todo send to viewmodel
        } else {
            messages.add(message);
            System.out.println(message);
//            property.firePropertyChange("message", 0, message);
        }
    }

    public void sendMessage(Message message) {
        chatClient.sendMessage(message);
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        property.removePropertyChangeListener(listener);
    }

    @Override
    public void addListener(String eventID, PropertyChangeListener listener) {
        property.addPropertyChangeListener(eventID, listener);
    }

    @Override
    public void removeListener(String eventID, PropertyChangeListener listener) {
        property.removePropertyChangeListener(eventID, listener);
    }
}
