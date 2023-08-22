package com.example.jfx_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;



public class onlineDB {

    private static Connection conn =null;
    private static Statement statement=null;
    private static ResultSet res=null;

    public static Connection getConn() {
        return conn;
    }

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

    public static void closeConnection() {
        try {
            res.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Person> loadData() throws SQLException {
        //if(conn.isValid(5)) {
            conn.setAutoCommit(true);
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
                        pd.amountOrdered,\s
                        pd.status,\s
                        pd.info\s
                    FROM giraffe.persondata pd\s
                    join giraffe.species sp
                    on pd.speciesById = sp.idSpecies\s
                    order by pd.idPersonData;
                    """;

        String query2 = "select "
                + "pd.IdPersonData, "
                + "pd.postalCode, "
                + "pd.city, "
                + "pd.phoneNumber, "
                + "pd.surname, "
                + "pd.orderPlacementDate, "
                + "pd.orderReceiptDate, "
                + "sp.nameSpecies, "
                + "pd.amountOrdered, "
                + "pd.status, "
                + "pd.info "
                + "FROM giraffe.persondata pd "
                + "join giraffe.species sp "
                + "on pd.speciesById = sp.idSpeciess "
                + "order by pd.idPersonData;";


            ObservableList<Person> list = FXCollections.observableArrayList();

            try {
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
                    Integer queryAmount = res.getInt("amountOrdered");
                    Integer queryStatus = res.getInt("status");
                    String queryInfo = res.getString("info");

                    list.add(new Person(queryID, queryPostalCode, queryCity, queryPhoneNumber, querySurname, queryOrderPlacementDate, queryOrderReceiptDate, querySpecies, queryAmount, queryStatus, queryInfo));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //closeConnection();
            return list;
       // }
       // return null;
    }


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

    public static int deleteRecord(int id) throws SQLException {
        if(conn.isValid(5)) {
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

        return 0;
    }

    public static int updateRecord(int recordID, String recordPostalCode, String recordCity, String recordPhoneNumber, String recordSurname, Date recordOrderPlacementDate, Date recordOrderReceiptDate, int recordSpecies, int recordAmount, int recordStatus, String recordInfo) {
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
