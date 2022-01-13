module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.desktop;
    requires kotlin.stdlib.jdk7;
    requires kotlinx.coroutines.core.jvm;
    requires javafx.swing;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}