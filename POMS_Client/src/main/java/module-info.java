module dk.via {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires java.rmi;
    opens dk.via to javafx.fxml;
    opens dk.via.view to javafx.fxml;
    exports dk.via;
    exports dk.via.mediator to java.rmi;

}