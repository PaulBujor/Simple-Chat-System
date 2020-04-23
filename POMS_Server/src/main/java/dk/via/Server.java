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

    public Server() throws RemoteException {
        clients = new ArrayList<RemoteClient>();
        connectedClients = 0;
        UnicastRemoteObject.exportObject(this, 0);
        System.out.printf("Server IP: %s\n", getIP());
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

    @Override
    public synchronized void sendMessage(Message message) throws RemoteException {
        for (RemoteClient client : clients) {
            client.receiveMessage(message);
        }
    }

    public void connect(RemoteClient client) throws RemoteException {
        try {
            System.out.println(client + " connected");
            clients.add(client);
            connectedClients++;
            for (RemoteClient connectedClient : clients) {
                connectedClient.updateConnectedUsers(connectedClients);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect(RemoteClient client) throws RemoteException {
        System.out.println(client + " disconnected");
        clients.remove(client);
        connectedClients--;
        try {
            for (RemoteClient connectedClient : clients) {
                connectedClient.updateConnectedUsers(connectedClients);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
