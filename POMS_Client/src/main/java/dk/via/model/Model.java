package dk.via.model;

import dk.via.utility.Message;
import dk.via.utility.PropertyChangeSubject;

import java.io.IOException;
import java.util.ArrayList;

public interface Model extends PropertyChangeSubject {
    public void receiveMessage(Message message);

    public void setHost(String host);

    public void setPort(int port);

    public void setUsername(String username);

    public String getUsername();

    public int getConnectedUsers();

    public void setIP(String ip);

    public String getIP();

    public boolean connect(String host, int port, String username) throws IOException;

    public void sendMessage(Message message);

    void setConnectedUsers(int users);
    public ArrayList<Message> getMessages(int number);
}
