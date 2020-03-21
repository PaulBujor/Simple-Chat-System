module dk.via {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;

    opens dk.via to javafx.fxml;
    exports dk.via;
}