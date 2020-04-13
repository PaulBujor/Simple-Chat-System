package dk.via;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

public class Main {
    public static void startRegistry() throws RemoteException {
        try{
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        } catch (ExportException e) {
            System.out.println("Registry already started?\n" + e.getMessage());
        }
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        startRegistry();

//        if(System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }

        Server server = new Server();
        server.start();
        System.out.println("Server started...");
    }
}
