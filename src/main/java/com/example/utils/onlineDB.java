package com.example.utils;

import com.example.jfx_project.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Utility class for handling database operations when connected to an online database.
 */
public class onlineDB {

    private static Connection conn =null;
    private static Statement statement=null;
    private static ResultSet res=null;

    /**
     * Returns the current database connection.
     *
     * @return The database connection.
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * Opens a connection to the database.
     *
     * @return True if the connection was successful, otherwise false.
     */
    public static boolean openConnection() {
        String url = "jdbc:mysql://localhost/giraffe";
        String uname = "root";
        String password = "password";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error! jdbc.Driver");
            e.printStackTrace();
            return false;
        }
        try {
            DriverManager.setLoginTimeout(6);
            conn = DriverManager.getConnection(url, uname, password);
        } catch (SQLException e) {
            System.out.println("Error! Cannot connect to database");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Closes the database connection, statement, and result set.
     */
    public static void closeConnection() {
        try {
            if (res != null) {
                res.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads data from the database and returns an ObservableList of Person objects.
     *
     * @return An ObservableList containing loaded Person objects.
     * @throws SQLException If a database error occurs.
     */
    public static ObservableList<Person> loadData() {
        //if(conn.isValid(5)) {
            //String query = "SELECT idPersonData, postalCode, city, phoneNumber, status, info FROM persondata;"; //old query
            String query = """
                    SELECT\s
                    	pd.idPersonData,\s
                        pd.postalCode,\s
                        pd.city,\s
                        pd.phoneNumber,\s
                        pd.surname,\s
                        pd.orderPlacementDate,\s
                        pd.orderReceiptDate,\s
                        sp.nameSpecies,\s
                        pd.price,\s
                        pd.amountOrdered,\s
                        pd.status,\s
                        pd.info\s
                    FROM giraffe.persondata pd\s
                    join giraffe.species sp
                    on pd.speciesById = sp.idSpecies\s
                    order by pd.idPersonData;
                    """;

            ObservableList<Person> list = FXCollections.observableArrayList();

            try {
                conn.setAutoCommit(true);

                statement = conn.createStatement();
                res = statement.executeQuery(query);

                while (res.next()) {
                    Integer queryID = res.getInt("idPersonData");
                    String queryPostalCode = res.getString("postalCode");
                    String queryCity = res.getString("city");
                    String queryPhoneNumber = res.getString("phoneNumber");
                    String querySurname = res.getString("surname");
                    Date queryOrderPlacementDate = res.getDate("orderPlacementDate");
                    Date queryOrderReceiptDate = res.getDate("orderReceiptDate");
                    String querySpecies = res.getString("nameSpecies");
                    Double queryPrice = res.getDouble("price");
                    Integer queryAmount = res.getInt("amountOrdered");
                    Integer queryStatus = res.getInt("status");
                    String queryInfo = res.getString("info");

                    list.add(new Person(queryID, queryPostalCode, queryCity, queryPhoneNumber, querySurname, queryOrderPlacementDate, queryOrderReceiptDate, querySpecies, queryPrice, queryAmount, queryStatus, queryInfo));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //closeConnection();
            return list;
       // }
       // return null;
    }


    /**
     * Adds new Record into database.
     *
     * @param recordPostalCode         The new postal code value.
     * @param recordCity               The new city value.
     * @param recordPhoneNumber        The new phone number value.
     * @param recordSurname            The new surname value.
     * @param recordOrderPlacementDate The new order placement date value.
     * @param recordOrderReceiptDate   The new order receipt date value.
     * @param recordSpecies            The new species ID value.
     * @param recordAmount             The new amount value.
     * @param recordStatus             The new status value.
     * @param recordInfo               The new info value.
     * @return A status code indicating the result of the update operation.
     */
    public static int addRecord(String recordPostalCode, String recordCity, String recordPhoneNumber, String recordSurname, Date recordOrderPlacementDate, Date recordOrderReceiptDate, int recordSpecies, int recordAmount, int recordStatus, String recordInfo) {
        try {
            if (conn.isValid(5)) {
                conn.setAutoCommit(true);

                String query = "insert into persondata (postalCode, city, phoneNumber, surname, orderPlacementDate, orderReceiptDate, speciesById, amountOrdered, status, info) values ("
                                + "\"" + recordPostalCode + "\","
                                + "\"" + recordCity + "\","
                                + "\"" + recordPhoneNumber + "\","
                                + "\"" + recordSurname + "\","
                                + "\"" + recordOrderPlacementDate + "\","
                                + "\"" + recordOrderReceiptDate + "\","
                                + recordSpecies + ","
                                + recordAmount + ","
                                + recordStatus + ","
                                + "\"" + recordInfo + "\");";

                try {
                    statement = conn.createStatement();
                    statement.executeUpdate(query);

                } catch (SQLException e) {
                    e.printStackTrace();
                    return -2;
                }
            } else
                return -1;
        }catch (SQLException e) {
            e.printStackTrace();
            return -5;
        }

        return 0;
    }

    /**
     * Deletes record from database, according to given unique ID number.
     *
     * @param id The unique orders ID number
     * @return A status code indicating the result of the update operation.
     * @throws SQLException If a database error occurs.
     */
    public static int deleteRecord(int id) {
        try {
            if (conn.isValid(5)) {
                conn.setAutoCommit(false);
                Savepoint savepoint = conn.setSavepoint();

                String query1 = "select idPersonData from giraffe.persondata where idPersondata = " + id + ";";
                try {
                    statement = conn.createStatement();
                    res = statement.executeQuery(query1);
                } catch (SQLException e) {
                    e.printStackTrace();
                    conn.rollback(savepoint);
                }

                if (!res.next()) {
                    conn.rollback(savepoint);
                    return -3;
                }

                String query2 = "delete from giraffe.persondata where idPersonData = " + id + ";";
                try {
                    statement = conn.createStatement();
                    statement.executeUpdate(query2);
                    conn.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                    conn.rollback(savepoint);
                    return -2;
                }
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -5;
        }
        return 0;
    }

    /**
     * Updates the status of a record in the database.
     *
     * @param recordID                 The ID of the record to be updated.
     * @param recordPostalCode         The new postal code value.
     * @param recordCity               The new city value.
     * @param recordPhoneNumber        The new phone number value.
     * @param recordSurname            The new surname value.
     * @param recordOrderPlacementDate The new order placement date value.
     * @param recordOrderReceiptDate   The new order receipt date value.
     * @param recordSpecies            The new species ID value.
     * @param recordPrice              The new price value.
     * @param recordAmount             The new amount value.
     * @param recordStatus             The new status value.
     * @param recordInfo               The new info value.
     * @return A status code indicating the result of the update operation.
     */
    public static int updateRecord(int recordID, String recordPostalCode, String recordCity, String recordPhoneNumber, String recordSurname, Date recordOrderPlacementDate, Date recordOrderReceiptDate, int recordSpecies, double recordPrice, int recordAmount, int recordStatus, String recordInfo) {
        try {
            if (conn.isValid(5)) {
                conn.setAutoCommit(false);
                Savepoint savepoint = conn.setSavepoint();
                String query1 = " select idPersonData from giraffe.persondata where idPersonData = " + recordID + ";";
                try {
                    statement = conn.createStatement();
                    res = statement.executeQuery(query1);

                } catch (SQLException e) {
                    System.out.println("rollback");
                    e.printStackTrace();
                    conn.rollback(savepoint);
                    return -4;
                }
                if (!res.next()) {
                    conn.rollback(savepoint);
                    return -3;
                }
                String query2 = "update giraffe.persondata set"
                                + " postalCode = \"" + recordPostalCode
                                + "\" ,city = \"" + recordCity
                                + "\" ,phoneNumber = \"" + recordPhoneNumber
                                + "\" ,surname = \"" + recordSurname
                                + "\" ,orderPlacementDate = \"" + recordOrderPlacementDate
                                + "\" ,orderReceiptDate = \"" + recordOrderReceiptDate
                                + "\" ,speciesById = " + recordSpecies
                                + " ,price = " + recordPrice
                                + " ,amountOrdered = " + recordAmount
                                + " ,status = " + recordStatus
                                + " ,info = \"" + recordInfo
                                + "\" where idPersonData = " + recordID
                                + " ;";
                try {
                    statement = conn.createStatement();
                    statement.executeUpdate(query2);
                    conn.commit();

                } catch (SQLException e) {
                    e.printStackTrace();
                    conn.rollback(savepoint);
                    return -2;
                }
            } else
                return -1;
        }catch (SQLException e) {
            e.printStackTrace();
            return -5;
        }
        return 0;
    }


}
