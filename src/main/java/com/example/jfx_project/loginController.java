package com.example.jfx_project;

import com.example.utils.PopUp;
import com.example.utils.onlineDB;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML
    private Button offlineButton;
    @FXML
    private Button onlineButton;
    @FXML
    private ProgressIndicator lodingProgressIndicator; //TODO: add threading

    /**
     * Handles the 'On Mouse Entered' event of the buttons.
     * Changes the background color of buttons, when cursor hovers over them.
     *
     * @param event The event triggered by "On Mouse Entered" action.
     */
    @FXML
    private void hoverOverButtonEnter(MouseEvent event) {
        if (event.getSource() == offlineButton) {
            offlineButton.setStyle(offlineButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #d6916c;"));
        } else if(event.getSource() == onlineButton) {
            onlineButton.setStyle(onlineButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #253762;"));
        }
    }

    /**
     * Handles the 'On Mouse Exited' event of the buttons.
     * Changes the background color of buttons, when cursor stops hovering over them.
     *
     * @param event The event triggered by "On Mouse Exited" action.
     */
    @FXML
    private void hoverOverButtonExit(MouseEvent event) {
        if (event.getSource() == offlineButton) {
            offlineButton.setStyle(offlineButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #ce5c20;"));
        } else if(event.getSource() == onlineButton) {
            onlineButton.setStyle(onlineButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #001648;"));
        }
    }

    /**
     * Loads and displays the main application user interface.
     *
     * @param event The event that triggers the loading of the application interface.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    private void loadApp(Event event) throws IOException {
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("my-view.fxml"));
        Parent root = loader.load();

        Rectangle2D rec = Screen.getPrimary().getVisualBounds();
        double width = rec.getWidth();
        double height = rec.getHeight();
        double x = width/1920;
        double y = height/1040;
        double z = 1;
        root.getTransforms().add(new Scale(x,y,z));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Notes");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Triggers the loadApp function, if connection with database is established.
     *
     * @param event The event triggered by "onlineButton" button;
     */
    @FXML
    private void connectOnlineDB(ActionEvent event) {
        //lodingProgressIndicator.setVisible(true);
        offlineButton.setDisable(true);
        onlineButton.setDisable(true);

        if (onlineDB.openConnection()) {
            try {
                loadApp(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            offlineButton.setDisable(false);
            onlineButton.setDisable(false);
            //lodingProgressIndicator.setVisible(false);
            PopUp.announcementPopUp(event, "Blad sieci");
        }
    }

    /**
     * Handles the action of connecting to the offline database and loading the application interface.
     *
     * @param event The event triggered by "offlineButton" button.
     */
    @FXML
    private void connectOfflineDB(ActionEvent event) {
        //lodingProgressIndicator.setVisible(true);
        offlineButton.setDisable(true);
        onlineButton.setDisable(true);
        try {
            loadApp(event);
        }  catch (IOException e) {
            e.printStackTrace();
            offlineButton.setDisable(false);
            onlineButton.setDisable(false);
            //lodingProgressIndicator.setVisible(false);
            PopUp.announcementPopUp(event, "Blad wczytywania pliku");
        }
    }
    /**
     * Initializes the application's user interface upon startup.
     * This method is automatically called by JavaFX after the FXML file is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resource The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resource) {
        lodingProgressIndicator.setVisible(false);
    }
}
