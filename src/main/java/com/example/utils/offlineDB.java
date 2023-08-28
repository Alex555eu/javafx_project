package com.example.utils;

import com.example.jfx_project.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;

/**
 * Utility class for saving and loading a list of persons to/from a binary file in offline mode.
 */
public class offlineDB  {
    /**
     * The file path where the serialized data is stored.
     */
    static String file_path = "myseriafile.bin";

    /**
     * Saves a list of persons to a binary file.
     *
     * @param personList The list of persons to be saved.
     */
    public static void saveToFile(List<Person> personList)  {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file_path))) {
            outputStream.writeObject(personList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a list of persons from a binary file and returns it as an ObservableList.
     *
     * @return An ObservableList containing the loaded persons.
     */
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

    /**
     * Retrieves the current file path used for saving and loading.
     *
     * @return The current file path.
     */
    public static String getFile_path() {
        return file_path;
    }

    /**
     * Sets the file path for saving and loading.
     *
     * @param file_path The file path to be set.
     */
    public static void setFile_path(String file_path) {
        offlineDB.file_path = file_path;
    }
}
