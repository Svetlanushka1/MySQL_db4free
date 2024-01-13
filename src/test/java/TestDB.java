import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.*;

public class TestDB {
    String url = "jdbc:mysql://db4free.net:3306/telran401";
    String user = "telran40";
    String password = "telran40";

    Connection connection = DriverManager.getConnection(url, user, password);

    public TestDB() throws SQLException {
    }
    @Test
    public void selectTest() throws SQLException {
        String table = "OlegSher_20240110_2228";
        String sql = "SELECT * FROM " + table;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Assert that the resultSet is not null
        Assert.assertNotNull(resultSet, "Result set should not be null");

        // Assert that the resultSet contains at least one row
        Assert.assertTrue(resultSet.next(), "Result set should have at least one row");

        // Extract data from the first row for further assertions
        String first_name = resultSet.getString("first_name");
        String last_name  = resultSet.getString("last_name");

        resultSet.close();
        statement.close();

        // Assert on specific data values or conditions
        Assert.assertEquals(first_name, "olegOlegSher_20240110_2228", "First name should match");
        Assert.assertEquals(last_name, "sherOlegSher_20240110_2228", "Last name should match");

    }


    @Test
    public void isTableExists() throws SQLException {
        String tableName = "OlegSher_20240110_2228"; // Replace with the actual table name
        String sql = "SHOW TABLES LIKE '" + tableName + "'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Assert that the resultSet is not null
        Assert.assertNotNull(resultSet, "Result set should not be null");

        // Assert that the resultSet contains at least one row (indicating the table exists)
        Assert.assertTrue(resultSet.next(), "Table '" + tableName + "' exists");

        resultSet.close();
        statement.close();
    }

}
