import java.sql.*;

public class DatabaseConnection
{
    public static Connection connection;

    // Method returns variable of type connection, which is SQL connection to server.
    public static Connection getConnection()
    {
        try
        {
            connection = DriverManager.getConnection(Variables.getServerURL(), Variables.getServerUsername(), Variables.getServerPassword());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }
}
