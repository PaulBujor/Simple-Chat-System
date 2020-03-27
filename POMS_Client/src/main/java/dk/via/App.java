package dk.via;

import dk.via.mediator.ChatClient;
import dk.via.mediator.ServerModel;
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
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
      ChatModel model = new ChatModel();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler view = new ViewHandler(viewModelFactory);
        view.start(stage);
    }

/*    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }*/

    public static void main(String[] args) throws IOException {
       /* //todo temporary, delete everything after GUI made
        Scanner input = new Scanner(System.in);
        System.out.print("Server IP: ");
        String host = input.nextLine();
        System.out.print("Server PORT: ");
        int port = input.nextInt();
        input.nextLine();
        System.out.print("Username: ");
        String user = input.nextLine();

        Model model = new ChatModel();
        model.connect(host, port, user);

        while (true) {
            String msg = input.nextLine();
            Message send = null;
            if (msg.equals("/ip"))
                send = new Message(user, msg, true);
            else
                send = new Message(user, msg);
            model.sendMessage(send);
        }*/

       launch(); //todo don't delete this tho
    }
}