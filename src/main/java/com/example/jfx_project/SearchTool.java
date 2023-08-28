package com.example.jfx_project;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;

/**
 * Class for performing search operations on a list of persons using various criteria.
 */
public class SearchTool {
    /**
     * Filters and updates a FilteredList of persons based on search criteria.
     *
     * @param filteredList         The FilteredList to be filtered.
     * @param tableSearchBar       The text field containing the search keyword.
     * @param tableDateFrom        The starting date range for filtering.
     * @param tableDateTo          The ending date range for filtering.
     * @param tableSearchChoiceBox The choice box for selecting the search category.
     */
    public static void searchFunction(FilteredList<Person> filteredList, TextField tableSearchBar, DatePicker tableDateFrom, DatePicker tableDateTo, ChoiceBox<String> tableSearchChoiceBox) {
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
    }

}
