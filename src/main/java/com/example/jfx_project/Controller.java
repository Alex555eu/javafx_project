package com.example.jfx_project;

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
import javafx.scene.layout.AnchorPane;
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

    @FXML
    private AnchorPane myPane;
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
    private Label orderedSumLabel;
    @FXML
    private DatePicker tableDateFrom;
    @FXML
    private DatePicker tableDateTo;
    @FXML
    private ChoiceBox<String> tableSearchChoiceBox;
    private final String[] choiceBoxSearchOptions = {"Wszystko", "Nr.id", "Kod pocztowy", "Miasto", "Nr.telefonu", "Nazwisko", "Nazwa", "Status", "Opis"};
    @FXML
    private Button tableModifyButton;
    @FXML
    private Button tableCancelButton;
    @FXML
    private Button tableAcceptButton;
    @FXML
    private Button tableDeleteButton;
    @FXML
    private Button tableAddButton;
    @FXML
    private Button tableSaveButton;
    @FXML
    private ChoiceBox<String> productChoiceBox;
    private final Map<String, Integer> speciesOptions = new TreeMap<>(Map.of("Ges Biala",1, "Ges Szara",2, "Pekin",3, "Barbarie",4, "Mulard",5));
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    private final Map<String, Integer> statusOptions = new TreeMap<>(Map.of("AKTYWNE",1, "ZAKOŃCZONE",2, "UWAGA I",3, "UWAGA II",4));
    private final Map<Integer, String> colorOptions = new TreeMap<>(Map.of(1, "-fx-text-fill: #63C05F;", 2, "-fx-text-fill: #4CCEF6;", 3, "-fx-text-fill: #c6c300;", 4, "-fx-text-fill: #FE6666;"));

    private ObservableList<Person> myTableObservableList;
    private FilteredList<Person> filteredList;
    private SortedList<Person> sortedList;



    private void orderedSumUpdate() {
        int sum=0;
        for(Person person : sortedList) {
            if(person.getStatus() == 1) {
                sum += person.getAmount();
            }
        }
        orderedSumLabel.setText(String.valueOf(sum));
    }

    @FXML
    private void offlineSave() {
        tableSaveButton.setDisable(true);
        offlineDB.saveToFile(new ArrayList<>(myTableObservableList));
        tableSaveButton.setDisable(false);
    }

    @FXML
    private void hoverOverButtonEnter(MouseEvent event) {
        if (event.getSource() == tableAddButton) {
            tableAddButton.setStyle(tableAddButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #63C05F;"));

        } else if(event.getSource() == tableSaveButton) {
            tableSaveButton.setStyle(tableSaveButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #4CCEF6;"));

        } else if(event.getSource() == tableModifyButton) {
            tableModifyButton.setStyle(tableModifyButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color:  #ce5c20;"));

        } else if(event.getSource() == tableDeleteButton) {
            tableDeleteButton.setStyle(tableDeleteButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #FE6666;"));

        } else if(event.getSource() == tableCancelButton) {
            tableCancelButton.setStyle(tableCancelButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #FE6666;"));

        } else if(event.getSource() == tableAcceptButton) {
            tableAcceptButton.setStyle(tableAcceptButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: #63C05F;"));
        }
    }

    @FXML
    private void hoverOverButtonExit(MouseEvent event) {
        if (event.getSource() == tableAddButton) {
            tableAddButton.setStyle(tableAddButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == tableSaveButton) {
            tableSaveButton.setStyle(tableSaveButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == tableModifyButton) {
            tableModifyButton.setStyle(tableModifyButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == tableDeleteButton) {
            tableDeleteButton.setStyle(tableDeleteButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == tableCancelButton) {
            tableCancelButton.setStyle(tableCancelButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));

        } else if(event.getSource() == tableAcceptButton) {
            tableAcceptButton.setStyle(tableAcceptButton.getStyle().replaceAll("-fx-background-color:[^;]+;", "-fx-background-color: white;"));
        }
    }

    @FXML
    private void deleteDataAction(ActionEvent event) {
        Stage confstage = (Stage)((Node)event.getSource()).getScene().getWindow();

        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(confstage);

        HBox dialogButtonsHbox = new HBox(10);
        dialogButtonsHbox.setAlignment(Pos.CENTER);

        Button acceptButton = new Button("Tak");
        acceptButton.setStyle("-fx-background-color: #63C05F");
        acceptButton.setPrefSize(83, 33);

        Button cancelButton = new Button("Anuluj");
        cancelButton.setStyle("-fx-background-color:  #FE6666");
        cancelButton.setPrefSize(83, 33);

        acceptButton.setOnAction(e -> {
            int index = myTable.getSelectionModel().getSelectedIndex();
            try {
                if(onlineDB.deleteRecord(myTableObservableList.get(index).getId()) == 0) {
                    myTableObservableList.remove(index);
                    searchFunction();
                }

            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            stage.close();
        });

        cancelButton.setOnAction(e -> {
            stage.close();
        });

        dialogButtonsHbox.getChildren().addAll(acceptButton, cancelButton);

        Text dialogText = new Text("Czy chcesz kontynuować ?");
        dialogText.setFont(Font.font("Verdana", 16));

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().addAll(dialogText, dialogButtonsHbox);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        stage.setScene(dialogScene);
        stage.showAndWait();
        orderedSumUpdate();
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

    @FXML
    private void modifyDataAction() {
        myTable.setDisable(true);
        tableSearchBar.setDisable(true);
        tableDateFrom.setDisable(true);
        tableDateTo.setDisable(true);

        tableModifyButton.setVisible(false);
        tableAcceptButton.setVisible(true);
        tableCancelButton.setVisible(true);
        productChoiceBox.setVisible(true);
        statusChoiceBox.setVisible(true);
        tableDeleteButton.setVisible(false);

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

    @FXML
    private void acceptDataAction(ActionEvent event) {
        int id = Integer.parseInt(idTextField.getText());
        String pc = postalCodeTextField.getText();
        String city = cityTextField.getText();
        String pn = phoneNumberTextField.getText();
        String surn = surnameTextField.getText();
        Date opd = Date.valueOf(orderPlacementDatePicker.getValue());
        Date ord = Date.valueOf(orderReceiptDatePicker.getValue());
        String spec = productTextField.getText();//speciesChoiceBox.getValue();
        int amt = Integer.parseInt(amountTextField.getText());
        double pr = Double.parseDouble((priceTextField.getText()));
        String stat = statusTextField.getText();
        String inf = infoTextArea.getText();

        if(id > 0){
            if (onlineDB.updateRecord(id, pc, city, pn, surn, opd, ord, speciesOptions.get(spec), pr, amt, statusOptions.get(stat), inf) == 0) {
                for (Person obj : myTableObservableList) {
                    if (obj.getId() == id) {
                        obj.updateAll(pc, city, pn, surn, opd, ord, spec, pr, amt, statusOptions.get(stat), inf);
                        searchFunction(); //refreshes tableView
                    }
                }
            } else {
                announcementPopUp(event, "Blad modyfikacji");
            }

        } else {
            if (onlineDB.addRecord(pc, city, pn, surn, opd, ord, speciesOptions.get(spec), amt, statusOptions.get(stat), inf) == 0) {
                try{
                    myTableObservableList.setAll(onlineDB.loadData());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                announcementPopUp(event, "Blad dodania");
            }

        }
        orderedSumUpdate(); // updates sum of all active orders
        cancelDataAction(); // exits 'modify state' of the table
    }

    @FXML
    private void cancelDataAction() { // todo: change name to smth like: finish modifying data action
        handleRowSelect();
        myTable.setDisable(false);
        tableSearchBar.setDisable(false);
        tableDateFrom.setDisable(false);
        tableDateTo.setDisable(false);

        tableModifyButton.setVisible(true);
        tableAcceptButton.setVisible(false);
        tableCancelButton.setVisible(false);
        tableDeleteButton.setVisible(true);
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

    @FXML
    private void addDataAction() {

        modifyDataAction();

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

    @FXML
    private void handleRowSelect() {
        int index = myTable.getSelectionModel().getSelectedIndex();
        if(index <= -1)
            return ;

        Person person = myTable.getSelectionModel().getSelectedItem();

        if(onlineDB.getConn() != null) {
            tableDeleteButton.setDisable(false);
            tableModifyButton.setDisable(false);
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

    @FXML
    private void searchFunction() {

        filteredList.setPredicate(person -> {

            String searchBarInput = tableSearchBar.getText().toLowerCase();
            boolean dateVar0 = false;
            boolean dateVar1 = false;

            if(tableDateFrom.getValue() == null)
                dateVar0 = true;
            else if(person.getOrderReceiptDate().compareTo(Date.valueOf(tableDateFrom.getValue())) >= 0)
                dateVar0 = true;

            if(tableDateTo.getValue() == null)
                dateVar1 = true;
            else if(person.getOrderReceiptDate().compareTo(Date.valueOf(tableDateTo.getValue())) <= 0)
                dateVar1 = true;


            if((tableSearchChoiceBox.getValue() == "Wszystko" || tableSearchChoiceBox.getValue() == "Nr.id") && (Integer.toString(person.getId()).indexOf(searchBarInput) > -1) && dateVar0 && dateVar1){
                return true;
            }else if((tableSearchChoiceBox.getValue() == "Wszystko" || tableSearchChoiceBox.getValue() == "Kod pocztowy") && (person.getPostalCode().toLowerCase().indexOf(searchBarInput) > -1) && dateVar0 && dateVar1){
                return true;
            }else if((tableSearchChoiceBox.getValue() == "Wszystko" || tableSearchChoiceBox.getValue() == "Miasto") && (person.getCity().toLowerCase().indexOf(searchBarInput) > -1) && dateVar0 && dateVar1){
                return true;
            }else if((tableSearchChoiceBox.getValue() == "Wszystko" || tableSearchChoiceBox.getValue() == "Nr.telefonu") && (person.getPhoneNumber().toLowerCase().indexOf(searchBarInput) > -1) && dateVar0 && dateVar1){
                return true;
            }else if((tableSearchChoiceBox.getValue() == "Wszystko" || tableSearchChoiceBox.getValue() == "Status") && (person.getStringStatus().toLowerCase().indexOf(searchBarInput) > -1) && dateVar0 && dateVar1){
                return true;
            }else if((tableSearchChoiceBox.getValue() == "Wszystko" || tableSearchChoiceBox.getValue() == "Nazwisko") && (person.getSurname().toLowerCase().indexOf(searchBarInput) > -1) && dateVar0 && dateVar1){
                return true;
            }else if((tableSearchChoiceBox.getValue() == "Wszystko" || tableSearchChoiceBox.getValue() == "Gatunek") && (person.getSpecies().toLowerCase().indexOf(searchBarInput) > -1) && dateVar0 && dateVar1){
                return true;
            }else if((tableSearchChoiceBox.getValue() == "Wszystko" || tableSearchChoiceBox.getValue() == "Opis") && (person.getInfo().toLowerCase().indexOf(searchBarInput) > -1) && dateVar0 && dateVar1){
                return true;
            }else {
                return false;
            }
        });
        orderedSumUpdate();

    }



    @Override
    public void initialize(URL url, ResourceBundle resource)  {

        productChoiceBox.getItems().addAll(speciesOptions.keySet());
        statusChoiceBox.getItems().addAll(statusOptions.keySet());

        try {

            productChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                productTextField.setText(newValue);
            });

            statusChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                statusTextField.setText(newValue);
            });


            tableAcceptButton.setVisible(false);
            tableCancelButton.setVisible(false);
            productChoiceBox.setVisible(false);
            statusChoiceBox.setVisible(false);
            tableDeleteButton.setDisable(true);
            tableModifyButton.setDisable(true);


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
                tableModifyButton.setDisable(true);
                tableAddButton.setDisable(true);
                tableSaveButton.setDisable(true);

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