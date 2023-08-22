package com.example.jfx_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;

public class offlineDB  {

    static String file_path = "myseriafile.bin";

    public static void saveToFile(List<Person> personList)  {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file_path))) {
            outputStream.writeObject(personList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Person> loadFromFile() {
        List<Person> personList = null;
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file_path))) {
            personList = (List<Person>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<Person> ptr = FXCollections.observableArrayList();
        ptr.addAll(personList);
        return ptr;
    }

    public static String getFile_path() {
        return file_path;
    }

    public static void setFile_path(String file_path) {
        offlineDB.file_path = file_path;
    }
}
