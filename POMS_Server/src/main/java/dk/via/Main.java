package dk.via;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

public class Main {
    public static Registry startRegistry() throws RemoteException {
        Registry reg = null;
        try {
            reg = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        } catch (ExportException e) {
            System.out.println("Registry already started?\n" + e.getMessage());
        }
        return reg;
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
//        if(System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }

        Server server = new Server();
        Registry registry = startRegistry();
        registry.bind("ChatServer", server);
        System.out.println("Server started...");
    }
}
