package dk.via;

import dk.via.model.ChatModel;
import dk.via.model.Model;
import dk.via.utility.Message;
import dk.via.view.ViewHandler;
import dk.via.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private ChatModel model;

    @Override
    public void start(Stage stage) throws IOException {
        model = new ChatModel();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler view = new ViewHandler(viewModelFactory);
        view.start(stage);
    }

    public static void startRegistry() throws RemoteException {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        } catch (ExportException e) {
            System.out.println("Registry already started?" + e.getMessage());
        }
    }

/*    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }*/

    public static void main(String[] args) throws IOException {
        startRegistry();

        // ( ͡ಠ ʖ̯ ͡ಠ)
        //it worked last time, fuck this security manager
        //this was meant to stay in the hand-in
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }

        launch();
    }

    @Override
    public void stop() throws Exception {
        try {
            model.disconnect();
        } catch (NullPointerException e) {
            //this happens if user quits when in login view
        }
        super.stop();
    }
}