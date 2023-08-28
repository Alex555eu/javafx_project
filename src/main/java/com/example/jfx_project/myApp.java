package com.example.jfx_project;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.Parent;

/**
 * The main class of the Notes application, extending JavaFX's Application class.
 */
public class myApp extends Application {

    /**
     * The entry point of the application, responsible for setting up and displaying the initial user interface.
     *
     * @param primaryStage The primary stage provided by the JavaFX runtime.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.getIcons().add(new Image(myApp.class.getResourceAsStream("/images/logo-removebg-preview.png")));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("check.fxml"));
        Parent root = loader.load();

        Rectangle2D rec = Screen.getPrimary().getVisualBounds();
        double width = rec.getWidth();
        double height = rec.getHeight();
        double x = width/1920;
        double y = height/1040;
        double z = 1;
        root.getTransforms().add(new Scale(x,y,z));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Notes");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args)   {
        launch();
    }
}