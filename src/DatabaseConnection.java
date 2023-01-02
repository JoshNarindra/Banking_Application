import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    static String website = "jdbc:sqlserver://SQL8002.site4now.net;database=db_a8cc79_Ewahes";
    static String username =  "db_a8cc79_Ewahes_admin";
    static String password = "Fr43yX52kE71";
    public static Connection connection;

    //Method returns variable of type connection, which is SQL connection to server.
    public static void runQuery(String query, ArrayList columnNames) throws SQLException {
        try {
            connection = DriverManager.getConnection(website, username, password);

            var stmt = connection.prepareStatement(query);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                for (Object name:columnNames) {
                    System.out.println(rs.getString(name.toString()));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
