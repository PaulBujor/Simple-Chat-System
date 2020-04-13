package dk.via.mediator;

import dk.via.utility.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClient extends Remote {
    public void receiveMessage(Message message) throws RemoteException;
    public void sendMessage(Message message) throws RemoteException;
    public void updateConnectedUsers(int connectedUsers) throws RemoteException;
    public boolean connect(String IP) throws RemoteException;
    public void disconnect(String IP) throws RemoteException;
}
