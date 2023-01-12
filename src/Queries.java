/*
    Class Queries is responsible for executing all SQL queries made by the application.

    The three main methods in the class take SQL queries as arguments and execute the queries, returning results where applicable:
        updateQuery(),
        readQuery(),
        checkExistsQuery().

    Retrieve/check methods call the readQuery() or checkExistsQuery() methods and return relevant information:
        retrievePersonalAccount(),
        retrieveBusinessAccount(),
        retrieveISAAccount(),
        retrieveBusinessName(),
        checkAccountExists().

    Create methods call the updateQuery() method to alter database and the readQuery() method to return relevant information:
        createUser(),
        createPersonalAccount(),
        createBusinessAccount(),
        createISAAccount().
 */

import java.sql.SQLException;
import java.util.ArrayList;

public class Queries
{
    // Method updateQuery() takes a String query as an argument and executes the query in the SQL database.
    public static void updateQuery(String query)
    {
        try
        {
            var statement = DatabaseConnection.getConnection().prepareStatement(query);
            statement.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Method readQuery() takes a String query and an ArrayList of Strings columnNames as arguments.
    // The query is executed on the SQL database.
    // An ArrayList is then returned containing results of columnNames obtained from executing the query.
    public static ArrayList<String> readQuery(String query, ArrayList<String> columnNames)
    {
        ArrayList<String> results = new ArrayList<>();
        try
        {
            var statement = DatabaseConnection.getConnection().prepareStatement(query);
            var resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                for (String columnName : columnNames)
                {
                    results.add(resultSet.getString(columnName));
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

    // Method checkExistsQuery() takes a String query as an argument.
    // The query is executed on the SQL database.
    // A boolean true is then returned if the query returns results.
    public static boolean checkExistsQuery(String query)
    {
        try
        {
            var statement = DatabaseConnection.getConnection().prepareStatement(query);
            var resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getInt(1) == 1;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    // Method checkAccountExists() takes a String accountNumber as an argument and checks if the account number is in use in the database.
    // A boolean true is then returned if the account number already exists.
    public boolean checkAccountExists(String accountNumber)
    {
        return checkExistsQuery("SELECT COUNT(1) FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';");
    }

    // Method retrievePersonalAccount() takes a String accountNumber as an argument and creates a PersonalAccount object using information from the database.
    // This PersonalAccount object is then returned.
    public PersonalAccount retrievePersonalAccount(String accountNumber)
    {
        ArrayList<String> accountInformation;
        accountInformation = readQuery("SELECT * FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", Variables.accountsColumns);
        return new PersonalAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(4)), Float.parseFloat(accountInformation.get(5)));
    }

    // Method retrieveISAAccount() takes a String accountNumber as an argument and creates an ISAAccount object using information from the database.
    // This ISAAccount object is then returned.
    public ISAAccount retrieveISAAccount(String accountNumber)
    {
        ArrayList<String> accountInformation;
        accountInformation = readQuery("SELECT * FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", Variables.accountsColumns);
        return new ISAAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(4)), Float.parseFloat(accountInformation.get(5)));
    }

    // Method retrieveBusinessAccount takes a String account number as an argument and creates a BusinessAccount object using information from the database.
    // This BusinessAccount object is then returned.
    public BusinessAccount retrieveBusinessAccount(String accountNumber)
    {
        ArrayList<String> accountInformation;
        accountInformation = readQuery("SELECT * FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", Variables.accountsColumns);
        return new BusinessAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(4)), Float.parseFloat(accountInformation.get(5)), retrieveBusinessName(accountNumber));
    }

    // Method retrieveBusinessName() takes a String accountNumber as an argument and returns the business name associated with the account as a String.
    public String retrieveBusinessName(String accountNumber)
    {
        ArrayList<String> businessInformation;
        businessInformation = readQuery("SELECT * FROM Businesses0 WHERE AccountNumber = '" + accountNumber + "';", Variables.businessesColumns);
        return businessInformation.get(1);
    }

    // Method createUser() takes the name and date of birth of a person, creates a user in the Users table, and returns the UserID of that user.
    // This UserID can then be used as an argument for other methods.
    public int createUser(String firstName, String lastName, String dateOfBirth)
    {
        updateQuery("INSERT INTO Users0 (FirstName, LastName, DateOfBirth) VALUES ('" + firstName + "', '" + lastName + "', '" + dateOfBirth + "');");
        ArrayList<String> userInformation = readQuery("SELECT * FROM Users0 WHERE (FirstName = '" + firstName + "' AND LastName = '" + lastName + "' AND DateOfBirth = '" + dateOfBirth + "');", Variables.usersColumns);
        return Integer.parseInt(userInformation.get(0));
    }

    // Method createPersonalAccount() takes the relevant information and creates a personal account, before returning the account itself as a PersonalAccount object.
    public PersonalAccount createPersonalAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft)
    {
        updateQuery("INSERT INTO Accounts0 VALUES ('" + accountNumber + "', '" + sortCode + "', '" + userID + "', '" + "Personal" + "', " + balance + ", " + overdraft + ");");
        return retrievePersonalAccount(accountNumber);
    }

    // Method createISAAccount() takes the relevant information and creates an ISA account, before returning the account itself as an ISAAccount object.
    public ISAAccount createISAAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft)
    {
        updateQuery("INSERT INTO Accounts0 VALUES ('" + accountNumber + "', '" + sortCode + "', " + userID + ", '" + "ISA" + "', " + balance + ", " + overdraft + ");");
        return retrieveISAAccount(accountNumber);
    }

    // Method createBusinessAccount() takes the relevant information and creates a business account, before returning the account itself as a BusinessAccount object.
    public BusinessAccount createBusinessAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft, String businessName)
    {
        updateQuery("INSERT INTO Accounts0 VALUES ('" + accountNumber + "', '" + sortCode + "', " + userID + ", '" + "Business" + "', " + balance + ", " + overdraft + ");");
        updateQuery("INSERT INTO Businesses0 (Name, AccountNumber) VALUES ('" + businessName + "', '" + accountNumber + "');");
        return new BusinessAccount(accountNumber, sortCode, balance, overdraft, businessName);
    }
}
