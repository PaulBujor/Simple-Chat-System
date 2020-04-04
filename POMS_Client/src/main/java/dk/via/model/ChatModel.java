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
    private String clientIP;

    public ChatModel() throws IOException {
        messages = new ArrayList<Message>();
        property = new PropertyChangeSupport(this);
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

    public String getUsername()
    {
        return this.username;
    }

    public int getConnectedUsers() {
        return this.connectedUsers;
    }

    @Override
    public void setIP(String ip) {
        clientIP = ip;
    }

    @Override
    public String getIP() {
        return clientIP;
    }

    public void setConnectedUsers(int connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    @Override public ArrayList<Message> getMessages(int number)
    {
        ArrayList<Message> result = new ArrayList<>();
        int limit = Math.min(number,messages.size());
        for(int i = messages.size()-1;i> messages.size()-limit;i++){
            result.add(messages.get(i));
        }
        return result;
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
            sendMessage(new Message(username, "", true));
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
            clientIP = message.getMessage();
            property.firePropertyChange("loadData", 0, 1);
        } else if (message.isConnectedUpdate()) {
            setConnectedUsers(Integer.parseInt(message.getMessage()));
            property.firePropertyChange("connectedUpdate", 0, 1);
        } else {
            messages.add(message);
            System.out.println(message);
            property.firePropertyChange("message", 0, message);
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
    //TODO TEST
}
