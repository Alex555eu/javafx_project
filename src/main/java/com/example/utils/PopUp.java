package com.example.utils;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Utility class for displaying pop-up announcements.
 */
public class PopUp {

    /**
     * Displays a pop-up announcement with the provided message.
     *
     * @param event        The event triggering the pop-up.
     * @param announcement The announcement message to be displayed.
     */
    public static void announcementPopUp(ActionEvent event, String announcement) {
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

}
