import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static String website = "jdbc:sqlserver://SQL8002.site4now.net;database=db_a8cc79_Ewahes";
    static String username = "db_a8cc79_Ewahes_admin";
    static String password = "Fr43yX52kE71";
    public static Connection connection;

    //Method returns variable of type connection, which is SQL connection to server.
    public static Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(website, username, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}