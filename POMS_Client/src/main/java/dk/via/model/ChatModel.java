package dk.via.model;

import dk.via.mediator.Client;
import dk.via.utility.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ChatModel implements Model {
    private PropertyChangeSupport property;
    private ArrayList<Message> messages;
    private Client remoteClient;
    private Thread chatThread;
    private String host;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
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
        property.firePropertyChange("connectedUpdate", 0, 1);
    }

    @Override
    public ArrayList<Message> getMessages(int number) {
        ArrayList<Message> result = new ArrayList<>();
        int limit = Math.min(number, messages.size());
        for (int i = messages.size() - 1; i > messages.size() - limit; i--) {
            result.add(messages.get(i));
        }
        return result;
    }

    public static String getMyIP() {
        try {
            URL aws = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    aws.openStream()));

            String ip = in.readLine();
            return ip;
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not get IP";
        }
    }

    public boolean connect(String host, String username) throws IOException {
        setHost(host);
        setIP(host);
        setUsername(username);
        remoteClient = new Client(this);
        remoteClient.start();
        //uses localhost when connecting to local server, and global ip when connecting to external server, will probably shit it's pants in some sitations
        if (remoteClient.connect(host.equals("localhost") || host.equals("127.0.0.1") ? "localhost" : getMyIP())) {
            property.firePropertyChange("loadData", 0, 1);
            return true;
        }
        return false;
    }


    public void disconnect() {
        try {
            remoteClient.disconnect(getIP());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(Message message) {
        messages.add(message);
        System.out.println(message);
        property.firePropertyChange("message", 0, message);
    }

    public void sendMessage(Message message) {
        try {
            remoteClient.sendMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
