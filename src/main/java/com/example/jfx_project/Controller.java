package com.example.jfx_project;

import com.example.jfx_project.Person;
import com.example.jfx_project.SearchTool;
import com.example.utils.PopUp;
import com.example.utils.offlineDB;
import com.example.utils.onlineDB;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;


public class Controller implements Initializable {

    /* Left wing segment. Variables representing tables columns, search elements and the table itself */
    @FXML
    private TableView<Person> myTable;
    @FXML
    private TableColumn<Person, Integer> tableID;
    @FXML
    private TableColumn<Person, String> tablePostalCode;
    @FXML
    private TableColumn<Person, String> tableCity;
    @FXML
    private TableColumn<Person, String> tablePrice;
    @FXML
    private TableColumn<Person, String> tableSurname;
    @FXML
    private TableColumn<Person, Date> tableOrderReceiptDate;
    @FXML
    private TableColumn<Person, String> tableProduct;
    @FXML
    private TableColumn<Person, Integer> tableAmount;
    @FXML
    private TableColumn<Person, Integer> tableStatus;
    @FXML
    private TextField tableSearchBar;
    @FXML
    private DatePicker tableDateFrom;
    @FXML
    private DatePicker tableDateTo;
    @FXML
    private Label orderedSumLabel; // Label displaying sum of ordered products after filtering

    /* Right wing segment */
    @FXML
    private TextField idTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker orderPlacementDatePicker;
    @FXML
    private DatePicker orderReceiptDatePicker;
    @FXML
    private TextField productTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField statusTextField;
    @FXML
    private TextArea infoTextArea;
    @FXML
    private ChoiceBox<String> tableSearchChoiceBox;
    private final String[] choiceBoxSearchOptions = {"Wszystko", "Nr.id", "Kod pocztowy", "Miasto", "Nr.telefonu", "Nazwisko", "Nazwa", "Status", "Opis"};
    @FXML
    private Button modifyButton; // Button directing app to 'modify state'
    @FXML
    private Button cancelButton; // Button cancelling modification of old or adding new element
    @FXML
    private Button acceptButton; // Button accepting modification of old or adding new element
    @FXML
    private Button deleteButton; // Button deleting selected record from database

    /* Upper side (toolbar) segment */
    @FXML
    private Button addButton;    // Button directing app to a 'new record state'
    @FXML
    private Button saveButton;   // Button for serialization of a current orders list (back up file)

    @FXML
    private ChoiceBox<String> productChoiceBox;
    private final Map<String, Integer> productOptions = new TreeMap<>(Map.of("Ges Biala",1, "Ges Szara",2, "Pekin",3, "Barbarie",4, "Mulard",5)); //TODO: make app fetch product items by itself from database
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    private final Map<String, Integer> statusOptions = new TreeMap<>(Map.of("AKTYWNE",1, "ZAKOŃCZONE",2, "UWAGA I",3, "UWAGA II",4));

    private final Map<Integer, String> colorOptions = new TreeMap<>(Map.of(1, "-fx-text-fill: #63C05F;", 2, "-fx-text-fill: #4CCEF6;", 3, "-fx-text-fill: #c6c300;", 4, "-fx-text-fill: #FE6666;"));

    /**
     * Main container holding data from database.
     */
    private ObservableList<Person> myTableObservableList;
    private FilteredList<Person> filteredList; // List pointing to the location of data, used for filtrating purposes.
    private SortedList<Person> sortedList; // List pointing to the location of ata, used for binding data to UI component built-in sorting functions.

    /**
     * Function updating sum of product amount from ACTIVE orders.
     */
    private void orderedSumUpdate() {
        int sum=0;
        for(Person person : sortedList) {
            if(person.getStatus() == 1) {
                sum += person.getAmount();
            }
        }
        orderedSumLabel.setText(String.valueOf(sum));
    }

    /**
     * Handles the click event of the "saveButton" button.
     * Function uses offlineDB`s saveToFile function to serialize all objects which currently hold our data.
     */
    @FXML
    private void offlineSave() {
        saveButton.setDisable(true);
        offlineDB.saveToFile(new ArrayList<>(myTableObservableList));
        saveButton.setDisable(false);
    }

    /**
     * Handles the 'On Mouse Entered' event of the buttons.
     * Function changes the background color of buttons, when cursor hovers over them.
     *
     * @param event
     */
    @FXML
    private void hoverOverButtonEnter(MouseEvent event) {
        if (event.getSource() == addButton) {
            addButton.setStyle(addButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #63C05F;"));

        } else if(event.getSource() == saveButton) {
            saveButton.setStyle(saveButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #4CCEF6;"));

        } else if(event.getSource() == modifyButton) {
            modifyButton.setStyle(modifyButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color:  #ce5c20;"));

        } else if(event.getSource() == deleteButton) {
            deleteButton.setStyle(deleteButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #FE6666;"));

        } else if(event.getSource() == cancelButton) {
            cancelButton.setStyle(cancelButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #FE6666;"));

        } else if(event.getSource() == acceptButton) {
            acceptButton.setStyle(acceptButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #63C05F;"));
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
        if (event.getSource() == addButton) {
            addButton.setStyle(addButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == saveButton) {
            saveButton.setStyle(saveButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == modifyButton) {
            modifyButton.setStyle(modifyButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == deleteButton) {
            deleteButton.setStyle(deleteButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == cancelButton) {
            cancelButton.setStyle(cancelButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == acceptButton) {
            acceptButton.setStyle(acceptButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));
        }
    }

    /**
     * Function creates a pop-up window to confirm deletion of selected record, after which (if acceptBtn clicked) proceeds to delete it from database.
     *
     * @param event The event triggered by clicking "deleteButton" button.
     */
    @FXML
    private void deleteDataAction(ActionEvent event) {
        Stage confstage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Window from which action has been called

        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(confstage);

        HBox dialogButtonsHbox = new HBox(10);
        dialogButtonsHbox.setAlignment(Pos.CENTER);

        Button acceptBtn = new Button("Tak");
        acceptBtn.setStyle("-fx-background-color: #63C05F");
        acceptBtn.setPrefSize(83, 33);

        Button cancelBtn = new Button("Anuluj");
        cancelBtn.setStyle("-fx-background-color:  #FE6666");
        cancelBtn.setPrefSize(83, 33);

        acceptBtn.setOnAction(e -> {
            int index = myTable.getSelectionModel().getSelectedIndex();
            try {
                if(onlineDB.deleteRecord(myTableObservableList.get(index).getId()) == 0) {
                    myTableObservableList.remove(index);
                }

            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            stage.close();
        });

        cancelBtn.setOnAction(e -> {
            stage.close();
        });

        dialogButtonsHbox.getChildren().addAll(acceptBtn, cancelBtn);

        Text dialogText = new Text("Usunąć ?");
        dialogText.setFont(Font.font("Verdana", 16));

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().addAll(dialogText, dialogButtonsHbox);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        stage.setScene(dialogScene);
        stage.showAndWait();

        searchFunction();   // refreshes tableView
        orderedSumUpdate(); // updates sum of ordered products
    }

    /**
     * Puts the application's UI into 'modify state'.
     * In this state, certain UI components are disabled, hidden, or made editable
     * to allow the user to modify the data of a selected record.
     * This method is triggered by an action, such as clicking a "modifyButton" button.
     *
     *
     * Note: This method assumes the availability of various UI components (e.g., myTable, tableSearchBar, etc.).
     */
    @FXML
    private void enterModifyState() {
        myTable.setDisable(true);
        tableSearchBar.setDisable(true);
        tableDateFrom.setDisable(true);
        tableDateTo.setDisable(true);

        modifyButton.setVisible(false);
        acceptButton.setVisible(true);
        cancelButton.setVisible(true);
        productChoiceBox.setVisible(true);
        statusChoiceBox.setVisible(true);
        deleteButton.setVisible(false);

        cityTextField.setEditable(true);
        postalCodeTextField.setEditable(true);
        cityTextField.setEditable(true);
        phoneNumberTextField.setEditable(true);
        surnameTextField.setEditable(true);
        orderPlacementDatePicker.setEditable(true);
        orderReceiptDatePicker.setEditable(true);
        amountTextField.setEditable(true);
        priceTextField.setEditable(true);
        statusTextField.setEditable(true);
        infoTextArea.setEditable(true);
    }

    /**
     * Function updates existing or adds new record, based on action preceding it (e.g. "enterModifyState", "enterNewDataState").
     *
     * @param event The event triggered by clicking an "acceptButton" button.
     */
    @FXML
    private void acceptDataAction(ActionEvent event) {
        // Extract data from UI components
        int id = Integer.parseInt(idTextField.getText());
        String pc = postalCodeTextField.getText();
        String city = cityTextField.getText();
        String pn = phoneNumberTextField.getText();
        String surn = surnameTextField.getText();
        Date opd = Date.valueOf(orderPlacementDatePicker.getValue());
        Date ord = Date.valueOf(orderReceiptDatePicker.getValue());
        String spec = productTextField.getText();
        int amt = Integer.parseInt(amountTextField.getText());
        double pr = Double.parseDouble((priceTextField.getText()));
        String stat = statusTextField.getText();
        String inf = infoTextArea.getText();

        // Perform data update or insertion based on record ID
        if(id > 0){
            // Update existing record
            if (onlineDB.updateRecord(id, pc, city, pn, surn, opd, ord, productOptions.get(spec), pr, amt, statusOptions.get(stat), inf) == 0) {
                // Update the corresponding record in the ObservableList. Operation aimed for limiting data exchange between application and database
                for (Person obj : myTableObservableList) {
                    if (obj.getId() == id) {
                        obj.updateAll(pc, city, pn, surn, opd, ord, spec, pr, amt, statusOptions.get(stat), inf);
                    }
                }
            } else {
                PopUp.announcementPopUp(event, "Blad modyfikacji");
            }

        } else {
            // Insert new record
            if (onlineDB.addRecord(pc, city, pn, surn, opd, ord, productOptions.get(spec), amt, statusOptions.get(stat), inf) == 0) {
                try{
                    // Downloads data from database in order to receive a valid ID number - necessary after creation of new record.
                    myTableObservableList.setAll(onlineDB.loadData());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                PopUp.announcementPopUp(event, "Blad dodania");
            }

        }
        // Refresh the table view, update sum, and exit modification state
        searchFunction();
        orderedSumUpdate();
        exitModifyState();
    }

    /**
     * Lets the application's UI to exit 'modify state'.
     * This method is triggered by an action, such as clicking a "cancelButton" button or simply after finishing of adding, updating or deletion of record.
     *
     * Note: This method assumes the availability of various UI components (e.g., myTable, tableSearchBar, etc.).
     */
    @FXML
    private void exitModifyState() {
        handleRowSelect();
        myTable.setDisable(false);
        tableSearchBar.setDisable(false);
        tableDateFrom.setDisable(false);
        tableDateTo.setDisable(false);

        modifyButton.setVisible(true);
        acceptButton.setVisible(false);
        cancelButton.setVisible(false);
        deleteButton.setVisible(true);
        productChoiceBox.setVisible(false);
        statusChoiceBox.setVisible(false);

        idTextField.setEditable(false);
        cityTextField.setEditable(false);
        postalCodeTextField.setEditable(false);
        cityTextField.setEditable(false);
        phoneNumberTextField.setEditable(false);
        surnameTextField.setEditable(false);
        orderPlacementDatePicker.setEditable(false);
        orderReceiptDatePicker.setEditable(false);
        productTextField.setEditable(false);
        amountTextField.setEditable(false);
        priceTextField.setEditable(false);
        statusTextField.setEditable(false);
        infoTextArea.setEditable(false);

    }

    /**
     * Handles the click event of "addButton" button.
     * Function uses "enterModifyState" function and sets default values for elements.
     */
    @FXML
    private void enterNewDataState() {

        enterModifyState();

        idTextField.setText(String.valueOf(-1));
        cityTextField.setText("n/a");
        postalCodeTextField.setText("n/a");
        phoneNumberTextField.setText("n/a");
        surnameTextField.setText("n/a");
        orderPlacementDatePicker.setValue(LocalDate.now());
        orderReceiptDatePicker.setValue(LocalDate.now());
        productTextField.setText("n/a");
        amountTextField.setText(String.valueOf(0));
        priceTextField.setText((String.valueOf(0)));
        statusTextField.setText("n/a");
        statusTextField.setStyle(statusTextField.getStyle().replaceAll("-fx-text-fill:[^;]+;", "-fx-text-fill: #000000;"));;
        infoTextArea.setText("n/a");

    }

    /**
     * Handles selection of a record from 'left wing' of app, and displays it`s complete record information on 'right wing'.
     */
    @FXML
    private void handleRowSelect() {
        int index = myTable.getSelectionModel().getSelectedIndex();
        if(index <= -1)
            return ;

        Person person = myTable.getSelectionModel().getSelectedItem();

        if(onlineDB.getConn() != null) {
            deleteButton.setDisable(false);
            modifyButton.setDisable(false);
        }

        idTextField.setText(String.valueOf(person.getId()));
        cityTextField.setText(person.getCity());
        postalCodeTextField.setText(person.getPostalCode());
        phoneNumberTextField.setText(person.getPhoneNumber());
        surnameTextField.setText(person.getSurname());
        orderPlacementDatePicker.setValue(person.getOrderPlacementDate().toLocalDate());
        orderReceiptDatePicker.setValue(person.getOrderReceiptDate().toLocalDate());
        productTextField.setText(person.getSpecies());
        amountTextField.setText(String.valueOf(person.getAmount()));
        priceTextField.setText((String.valueOf(person.getPrice())));
        statusTextField.setText(person.getStringStatus());
        statusTextField.setStyle(statusTextField.getStyle().replaceAll("-fx-text-fill:[^;]+;", colorOptions.get(person.getStatus())));
        infoTextArea.setText(person.getInfo());

    }

    /**
     * Handles event in which one of the search elements (e.g. tableSearchBar) has been updated.
     */
    @FXML
    private void searchFunction() {
        SearchTool.searchFunction(filteredList, tableSearchBar, tableDateFrom, tableDateTo, tableSearchChoiceBox);
        orderedSumUpdate();
    }

    /**
     * Initializes the application's user interface upon startup.
     * This method is automatically called by JavaFX after the FXML file is loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resource The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resource)  {
        productChoiceBox.getItems().addAll(productOptions.keySet());
        statusChoiceBox.getItems().addAll(statusOptions.keySet());

        try {
            productChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                productTextField.setText(newValue);
            });

            statusChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                statusTextField.setText(newValue);
            });

            acceptButton.setVisible(false);
            cancelButton.setVisible(false);
            productChoiceBox.setVisible(false);
            statusChoiceBox.setVisible(false);
            deleteButton.setDisable(true);
            modifyButton.setDisable(true);

            tableSearchChoiceBox.getItems().addAll(choiceBoxSearchOptions);
            tableSearchChoiceBox.setValue(choiceBoxSearchOptions[0]);

            tableID.setCellValueFactory(new PropertyValueFactory<>("id"));
            tablePostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            tableCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            tableSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
            tableOrderReceiptDate.setCellValueFactory(new PropertyValueFactory<>("orderReceiptDate"));
            tableProduct.setCellValueFactory(new PropertyValueFactory<>("species"));
            tableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableStatus.setCellValueFactory(new PropertyValueFactory<>("stringStatus"));

            if(onlineDB.getConn() != null) {
                myTableObservableList = onlineDB.loadData();
            } else {
                myTableObservableList = offlineDB.loadFromFile();
                modifyButton.setDisable(true);
                addButton.setDisable(true);
                saveButton.setDisable(true);

            }
            myTable.setItems(myTableObservableList);
            filteredList = new FilteredList<>(myTableObservableList, p -> true);
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(myTable.comparatorProperty());
            myTable.setItems(sortedList);
            orderedSumUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}