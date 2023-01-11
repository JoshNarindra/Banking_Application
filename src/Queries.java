//Separate class for all queries which need to be executed before an instance of an Account is created
//For example cannot call functions inside of Account, BusinessAccount etc. until a query made is made to create an instance
//Also separates the queries from the connection itself which looks more tidy I think

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Queries
{
    public ArrayList<String> accountsColumns = new ArrayList<>(List.of("AccountNumber", "SortCode", "UserID", "AccountType", "Balance", "Overdraft"));
    public ArrayList<String> usersColumns = new ArrayList<>(List.of("ID", "FirstName", "LastName", "DateOfBirth"));
    public ArrayList<String> businessesColumns = new ArrayList<>(List.of("ID", "Name", "AccountNumber"));

    //Method which runs update queries on the database
    public static void updateQuery(String query) throws SQLException
    {
        try
        {
            DatabaseConnection connection = new DatabaseConnection();
            var stmt = connection.getConnection().prepareStatement(query);
            stmt.execute();
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
        ArrayList<String> results = new ArrayList<>();
        try
        {
            DatabaseConnection connection = new DatabaseConnection();
            var stmt = connection.getConnection().prepareStatement(query);
            var rs = stmt.executeQuery();

            while (rs.next())
            {
                for (int i = 0; i < columnNames.size(); i++)
                {
                    results.add(rs.getString(columnNames.get(i).toString()));
                }
            }

            return results;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return results;
    }

    public static boolean checkExistsQuery(String query)
    {
        try
        {
            DatabaseConnection connection = new DatabaseConnection();
            var stmt = connection.getConnection().prepareStatement(query);
            var rs = stmt.executeQuery();

            while(rs.next())
            {
                if (rs.getInt(1) == 1)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    // Method queries database to check if an account number exists in database. Returns true if account exists and false otherwise. -CHECK IF CAN USE PREEXISTING METHOD.
    public boolean checkAccountExists(String accountNumber)
    {
        return checkExistsQuery("SELECT COUNT(1) FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';");
    }

    //Method takes the account number provided and creates a PersonalAccount object using information from the database
    public PersonalAccount retrievePersonalAccount(String accountNumber) throws SQLException
    {
        ArrayList<String> accountInformation;
        accountInformation = readQuery("SELECT * FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", accountsColumns);
        PersonalAccount personalAccount = new PersonalAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(4)), Float.parseFloat(accountInformation.get(5)), true, true);
        return personalAccount;
    }

    //Method takes the account number provided and creates an ISAAccount object using information from the database
    public ISAAccount retrieveISAAccount(String accountNumber) throws SQLException
    {
        ArrayList<String> accountInformation;
        accountInformation = readQuery("SELECT * FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", accountsColumns);
        ISAAccount isaAccount = new ISAAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(4)), Float.parseFloat(accountInformation.get(5)));
        return isaAccount;
    }

    //Method takes the account number provided and creates a BusinessAccount object using information from the database
    public BusinessAccount retrieveBusinessAccount(String accountNumber) throws SQLException
    {
        ArrayList<String> accountInformation;
        accountInformation = readQuery("SELECT * FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", accountsColumns);
        BusinessAccount businessAccount = new BusinessAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(4)), Float.parseFloat(accountInformation.get(5)), retrieveBusinessName(accountNumber));
        return businessAccount;
    }

    public String retrieveBusinessName(String accountNumber) throws SQLException
    {
        ArrayList<String> businessInformation;
        businessInformation = readQuery("SELECT * FROM Businesses0 WHERE AccountNumber = '" + accountNumber + "';", businessesColumns);
        String businessName = businessInformation.get(1);
        return businessName;
    }

    //Method takes the name and date of birth of a person, creates a user in the Users table, and returns the UserID of that user
    //This UserID can then be used as an argument for the createAccount method
    public int createUser(String firstName, String lastName, String dateOfBirth) throws SQLException
    {
        updateQuery("INSERT INTO Users0 (FirstName, LastName, DateOfBirth) VALUES ('" + firstName + "', '" + lastName + "', '" + dateOfBirth + "');");
        ArrayList<String> userInformation = readQuery("SELECT * FROM Users0 WHERE (FirstName = '" + firstName + "' AND LastName = '" + lastName + "' AND DateOfBirth = '" + dateOfBirth + "');", usersColumns);
        int userID = Integer.parseInt(userInformation.get(0));
        return userID;
    }

    //Method takes the relevant information and creates a personal account, before returning the account itself as an object
    public PersonalAccount createPersonalAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft) throws SQLException
    {
        updateQuery("INSERT INTO Accounts0 VALUES ('" + accountNumber + "', '" + sortCode + "', '" + userID + "', '" + "Personal" + "', " + balance + ", " + overdraft + ");");
        PersonalAccount personalAccount = retrievePersonalAccount(accountNumber);
        return personalAccount;
    }

    //Method takes the relevant information and creates an ISA account, before returning the account itself as an object
    public ISAAccount createISAAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft) throws SQLException
    {
        updateQuery("INSERT INTO Accounts0 VALUES ('" + accountNumber + "', '" + sortCode + "', " + userID + ", '" + "ISA" + "', " + balance + ", " + overdraft + ");");
        ISAAccount isaAccount = retrieveISAAccount(accountNumber);
        return isaAccount;
    }

    //Method takes the relevant information and creates a business account, before returning the account itself as an object
    public BusinessAccount createBusinessAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft, String businessName) throws SQLException
    {
        updateQuery("INSERT INTO Accounts0 VALUES ('" + accountNumber + "', '" + sortCode + "', " + userID + ", '" + "Business" + "', " + balance + ", " + overdraft + ");");
        updateQuery("INSERT INTO Businesses0 (Name, AccountNumber) VALUES ('" + businessName + "', '" + accountNumber + "');");
        BusinessAccount businessAccount = new BusinessAccount(accountNumber, sortCode, balance, overdraft, businessName);
        return businessAccount;
    }
}
