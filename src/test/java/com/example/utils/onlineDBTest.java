package com.example.utils;

import com.example.jfx_project.Person;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
    Purpose of those tests is to check correctness
    of implemented queries, and compatibility with changes inside database possibly made in the future.
*/

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.SAME_THREAD)
class onlineDBTest {

    @Test
    @Order(1)
    void isAbleToConnect() {
        assertTrue(onlineDB.openConnection());
        onlineDB.closeConnection();
    }

    @Test
    @Order(2)
    void isDataLoadedCorrectly() {
        onlineDB.openConnection();
        List<Person> list = new ArrayList<>(onlineDB.loadData());
        assertArrayEquals(TestConstants.TEST_DATA_LIST.toArray(), list.toArray());

        onlineDB.closeConnection();
    }

    @Test
    @Order(3)
    void isDataAddedCorrectly() {
        onlineDB.openConnection();
        Person person = TestConstants.NEW_DATA;

        // adding record requires all Person attributes beside ID, because database will assign its own unique id to a new record
        onlineDB.addRecord(person.getPostalCode()
                ,person.getCity()
                ,person.getPhoneNumber()
                ,person.getSurname()
                ,person.getOrderPlacementDate()
                ,person.getOrderReceiptDate()
                ,2 // species ID, automatic conversion from name to id is yet to be implemented
                ,person.getAmount()
                ,person.getStatus()
                ,person.getInfo()
        );

        List<Person> list = new ArrayList<>(onlineDB.loadData()); // loading whole data from DB
        assertEquals(TestConstants.TEST_DATA_LIST.size()+1, list.size()); // checking if amount of data from DB was incremented by added record

        // Taking ID number of newly added record
        int newId = list.get(list.size()-1).getId();
        assertTrue(newId > 101);

        Person newPerson = new Person( newId
                ,person.getPostalCode()
                ,person.getCity()
                ,person.getPhoneNumber()
                ,person.getSurname()
                ,person.getOrderPlacementDate()
                ,person.getOrderReceiptDate()
                ,person.getSpecies()
                ,person.getPrice()
                ,person.getAmount()
                ,person.getStatus()
                ,person.getInfo()
        );
        // checking if the new record in database is in fact the same as the one we submitted
        assertEquals(list.get(list.size()-1), newPerson);

        onlineDB.closeConnection();
    }

    @Test
    @Order(4)
    void isDataUpdatedCorrectly() {
        onlineDB.openConnection();
        List<Person> list = new ArrayList<>(onlineDB.loadData());

        int newId = list.get(list.size()-1).getId();
        assertTrue(newId > 101);

        Person person = new Person( newId
                ,"pt-cod"
                ,"updateCity"
                ,"upd ate pho num"
                ,"testSurname"
                ,Date.valueOf("2023-09-06")
                ,Date.valueOf("2023-09-06")
                ,"Barbarie"// speciesId/recordSpecies = 4
                ,123
                ,10
                ,1
                ,"new info"
        );
        onlineDB.updateRecord( person.getId()
                ,person.getPostalCode()
                ,person.getCity()
                ,person.getPhoneNumber()
                ,person.getSurname()
                ,person.getOrderPlacementDate()
                ,person.getOrderReceiptDate()
                ,4
                ,person.getPrice()
                ,person.getAmount()
                ,person.getStatus()
                ,person.getInfo()
        );
        List<Person> updatedList = new ArrayList<>(onlineDB.loadData());
        assertEquals(updatedList.get(updatedList.size()-1), person);

        onlineDB.closeConnection();
    }

    @Test
    @Order(5)
    void isDataDeletedCorrectly() {
        onlineDB.openConnection();
        List<Person> list = new ArrayList<>(onlineDB.loadData());

        int newId = list.get(list.size()-1).getId();
        assertTrue(newId > 101);

        onlineDB.deleteRecord(newId);

        List<Person> listAfterDeletion = new ArrayList<>(onlineDB.loadData());

        assertArrayEquals(TestConstants.TEST_DATA_LIST.toArray(), listAfterDeletion.toArray());

        onlineDB.closeConnection();
    }


}