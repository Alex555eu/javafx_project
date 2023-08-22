package com.example.jfx_project;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
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
    private ProgressIndicator lodingProgressIndicator;


    @FXML
    private void hoverOverButtonEnter(MouseEvent event) {
        if (event.getSource() == offlineButton) {
            offlineButton.setStyle(offlineButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #d6916c;"));
        } else if(event.getSource() == onlineButton) {
            onlineButton.setStyle(onlineButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #253762;"));
        }
    }

    @FXML
    private void hoverOverButtonExit(MouseEvent event) {
        if (event.getSource() == offlineButton) {
            offlineButton.setStyle(offlineButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #ce5c20;"));
        } else if(event.getSource() == onlineButton) {
            onlineButton.setStyle(onlineButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #001648;"));
        }
    }

    private void loadApp(Event event) throws IOException {
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("my-view.fxml"));
        Parent root;

        root = loader.load();

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

    @FXML
    private void connectOnlineDB(ActionEvent event) {
        lodingProgressIndicator.setVisible(true);
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
            lodingProgressIndicator.setVisible(false);
            announcementPopUp(event, "Blad sieci");
        }
    }

    @FXML
    private void connectOfflineDB(ActionEvent event) {
        lodingProgressIndicator.setVisible(true);
        offlineButton.setDisable(true);
        onlineButton.setDisable(true);
        try {
            loadApp(event);

        }  catch (IOException e) {
            e.printStackTrace();
            offlineButton.setDisable(false);
            onlineButton.setDisable(false);
            lodingProgressIndicator.setVisible(false);
            announcementPopUp(event, "Blad wczytywania");
        }
    }


    private void announcementPopUp(ActionEvent event, String announcement) {
        Stage confstage = (Stage)((Node)event.getSource()).getScene().getWindow();

        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(confstage);

        Text dialogText = new Text(announcement);
        dialogText.setFont(Font.font("Verdana", 16));

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().addAll(dialogText);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        stage.setScene(dialogScene);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lodingProgressIndicator.setVisible(false);

    }




}
