module fileAapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens fileApp to javafx.fxml;
    exports fileApp;
}