package dk.via.view;

import dk.via.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ViewHandler {
    private ViewModelFactory viewModelFactory;
    private LoginView loginController;
    private ChatView chatController;
    private Scene currentScene;
    private Stage primaryStage;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
        currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("login");
    }

    public void openView(String id) {
        Region root = null;
        switch (id) {
            case "login":
                root = loadLoginView("login.fxml");
                break;
            case "chat":
                root = loadChatView("chat.fxml");
                break;
        }
        currentScene.setRoot(root);
        String title = "Chat";
        if (root.getUserData() != null) {
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    public void closeView() {
        primaryStage.close();
    }

    private Region loadLoginView(String fxmlFile) {
        Region root = null;
        if (loginController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                root = loader.load();
                loginController = loader.getController();
                loginController.init(this, viewModelFactory.getLoginViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            loginController.reset();
        }
        return loginController.getRoot();
    }

    private Region loadChatView(String fxmlFile) {
        Region root = null;
        if (chatController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                root = loader.load();
                chatController = loader.getController();
                chatController.init(this,viewModelFactory.getChatViewModel(), root);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else  {
            chatController.reset();
        }
        return  chatController.getRoot();
    }
}