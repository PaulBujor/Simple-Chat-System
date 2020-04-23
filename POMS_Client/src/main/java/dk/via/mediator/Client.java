package dk.via.mediator;

import dk.via.model.Model;
import dk.via.utility.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client implements RemoteClient {
    private RemoteServer server;
    private Model model;

    public Client(Model model) {
        this.model = model;
    }

    public void start() throws RemoteException, MalformedURLException {
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("ChatClient", this);
    }

    public boolean connect(String IP) throws RemoteException{
        try {
            server = (RemoteServer) Naming.lookup("rmi://" + IP + ":1099/ChatServer");
            server.connect(this);
            return true;
        } catch (Exception e) {
            System.out.println("Could not find server");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void sendMessage(Message message) throws RemoteException{
        server.sendMessage(message);
    }

    @Override
    public void receiveMessage(Message message) throws RemoteException{
        model.receiveMessage(message);
    }

    @Override
    public void updateConnectedUsers(int connectedUsers) throws RemoteException{
        model.setConnectedUsers(connectedUsers);
    }

    public void disconnect() throws RemoteException {
        server.disconnect(this);
    }
}
