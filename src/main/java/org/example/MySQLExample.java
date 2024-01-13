package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MySQLExample {

    public static void main(String[] args) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://db4free.net:3306/telran40";
        //String localhost = "jdbc:mysql://localhost:3306/telran40";
        String user = "telran40";
        String password = "telran40";
        String db_name = nameGenerator();

        try {
            //Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Establish a connection: where DataBase is(localhost/url)
            Connection connection = DriverManager.getConnection(url, user, password);

            /* //create a statement  = cursor
            Statement statement = connection.createStatement();
            statement.execute("SHOW DATABASES");
            boolean x = statement.execute("USE telran40");
            //System.out.println(x);*/

            // First of all do Connection useDB(connection);
            String createTableSQL = " (id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "first_name VARCHAR(50),"
                    + "last_name VARCHAR(50),"
                    + "email VARCHAR(100)"
                    + ")";
            createTable(connection, db_name, createTableSQL);
            showDatabases(connection);
            showTables(connection);
            insertData(connection, db_name, "oleg" + db_name, "sher" + db_name);
            selectData(connection, db_name);

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void useDB(Connection connection) throws SQLException {
//        Statement statement = connection.createStatement();
//        boolean x = statement.execute("USE telran40");
//        System.out.println(x);
//    }


    public static String nameGenerator() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        String formattedDateTime = currentDateTime.format(formatter);
        return "sDIs_" + formattedDateTime;
    }

    public static void createTable(Connection connection, String db_name, String params) throws SQLException {
        String createDatabaseQuery = "CREATE TABLE " + db_name.replace("'", "") + params;
        Statement cr = connection.createStatement();
        cr.executeUpdate(createDatabaseQuery);
        System.out.println("Table created successfully: " + db_name);
        cr.close();
    }


    private static void showDatabases(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW DATABASES;");
        while (resultSet.next()) {
            String dbName = resultSet.getString(1);
            System.out.println(dbName);
        }
        resultSet.close();
        statement.close();

    }

    private static void showTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW TABLES;");
        while (resultSet.next()) {
            String tableName = resultSet.getString(1);
            System.out.println(tableName);
        }
        resultSet.close();
        statement.close();

    }

    private static void insertData(Connection connection, String table, String firstName, String lastName) throws SQLException {
        String sql = "INSERT INTO " + table + " (first_name, last_name) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully");
            } else {
                System.out.println("Failed to insert data");
            }

        }
    }


    private static void selectData(Connection connection, String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                // Retrieve data from the result set
                int id = resultSet.getInt("id"); // Assuming the column name is "id"
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");

                // Do something with the retrieved data
                System.out.println("ID: " + id + ", Name: " + first_name + ", Last Name: " + last_name);
            }
            resultSet.close();
            statement.close();
        }
    }


}


