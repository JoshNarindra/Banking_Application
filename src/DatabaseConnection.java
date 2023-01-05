import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection
{
    static String website = "jdbc:sqlserver://SQL8002.site4now.net;database=db_a8cc79_Ewahes";
    static String username = "db_a8cc79_Ewahes_admin";
    static String password = "Fr43yX52kE71";
    public static Connection connection;

    //Method returns variable of type connection, which is SQL connection to server.
    public static Connection getConnection(){
        try {
            connection = DriverManager.getConnection(website, username, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    //Method which runs update queries on the database
    public static void updateQuery(String query) throws SQLException
    {
        try
        {
            connection = DriverManager.getConnection(website, username, password);

            var stmt = connection.prepareStatement(query);
            var rs = stmt.executeQuery();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Method which runs read queries on the database
    //Returns results an ArrayList of strings which contain the results from the table
    //If connection fails null is returned
    public static ArrayList<String> readQuery(String query, ArrayList columnNames) throws SQLException
    {
        try
        {
            ArrayList<String> results = new ArrayList<>(columnNames.size());
            connection = DriverManager.getConnection(website, username, password);

            var stmt = connection.prepareStatement(query);
            var rs = stmt.executeQuery();

            while (rs.next())
            {
                for (int i = 0; i < columnNames.size(); i++)
                {
                    results.set(i, rs.getString(columnNames.get(i).toString()));
                }
            }

            return results;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
