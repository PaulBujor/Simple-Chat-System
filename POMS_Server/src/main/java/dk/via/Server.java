package dk.via;

import dk.via.mediator.RemoteClient;
import dk.via.mediator.RemoteServer;
import dk.via.utility.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements RemoteServer {
    private ArrayList<RemoteClient> clients;
    private int connectedClients;

    public Server() {
        clients = new ArrayList<RemoteClient>();
        connectedClients = 0;
    }

    public static String getIP() {
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

    public void start() throws RemoteException, MalformedURLException {
        System.out.printf("Server IP: %s\n", getIP());
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("ChatServer", this); //permission error
    }

    @Override
    public synchronized void sendMessage(Message message) throws RemoteException {
        for (RemoteClient client : clients) {
            client.receiveMessage(message);
        }
    }

    public void connect(String IP) throws RemoteException {
        try {
            RemoteClient remoteClient = (RemoteClient) Naming.lookup("rmi://" + IP + ":1099/ChatClient");
            System.out.println(IP + " connected");
            clients.add(remoteClient);
            connectedClients++;
            for (RemoteClient client : clients) {
                client.updateConnectedUsers(connectedClients);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect(String IP) throws RemoteException {
        connectedClients--;
        try {
            for (RemoteClient client : clients) {
                client.updateConnectedUsers(connectedClients);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
