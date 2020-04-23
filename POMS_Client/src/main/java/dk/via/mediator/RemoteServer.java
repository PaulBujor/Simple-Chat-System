package dk.via.mediator;

import dk.via.utility.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteServer extends Remote {
    public void sendMessage(Message message) throws RemoteException;
    public void connect(RemoteClient client) throws RemoteException;
    public void disconnect(RemoteClient client) throws RemoteException;
}
