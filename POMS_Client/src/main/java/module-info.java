module dk.via {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens dk.via to javafx.fxml;
    exports dk.via;
}