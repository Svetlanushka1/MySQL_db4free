import org.testng.annotations.Test;

import java.sql.*;


public class ShowDB_tests {
    @Test
    public void startConnection(){
            String url = "jdbc:mysql://db4free.net:3306/telran40";
            //String localhost = "jdbc:mysql://localhost:3306/telran40";
            String user = "telran40";
            String password = "telran40";

//TODO check is password correct?
            try {
                //Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                //Establish a connection: where DataBase is(localhost/url)
                Connection connection = DriverManager.getConnection(url, user, password);

           //create a statement  = cursor
            Statement statement = connection.createStatement();
            statement.execute("SHOW DATABASES");
            boolean x = statement.execute("USE telran40");
            System.out.println(x);
            showAllDataBases(connection);

                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void showAllDataBases(Connection connection) throws SQLException {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SHOW DATABASES;");
                while (resultSet.next()) {
                    String dbName = resultSet.getString(1);
                    System.out.println(dbName);
                }
                resultSet.close();
                statement.close();

            }




}
