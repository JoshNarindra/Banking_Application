/*
    Method DatabaseConnection simply implements a connection to the database using the getConnection() method.
 */

import java.sql.*;

public class DatabaseConnection
{
    public static Connection connection;

    // Method getConnection() connects to the database using the details stored in the Variables class.
    // The connection is then returned as an object.
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
