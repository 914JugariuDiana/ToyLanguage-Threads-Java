module com.example.aa7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.compiler;


    opens com.example.aa7 to javafx.fxml;
    exports com.example.aa7;
}