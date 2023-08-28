module com.example.jfx_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.jfx_project to javafx.fxml;
    exports com.example.jfx_project;
    exports com.example.utils;
    opens com.example.utils to javafx.fxml;
    exports com.example.constrollers;
    opens com.example.constrollers to javafx.fxml;
}