package dk.via.mediator;

import dk.via.utility.Message;

import java.io.IOException;

public interface ServerModel {
    public boolean connect() throws IOException;
    public void disconnect();
    public void sendMessage(Message message);
//    public void requestIP() throws IOException;
}
