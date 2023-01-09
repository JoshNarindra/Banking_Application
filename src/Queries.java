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
            stmt.executeQuery();
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

    // Method queries database to check if an account number exists in database. Returns true if account exists and false otherwise. -CHECK IF CAN USE PREEXISTING METHOD.
    public Boolean checkAccountExists(String AccountNumber)
    {
        DatabaseConnection connection = new DatabaseConnection();
        Boolean accountExists=null;
        String query = "select COUNT(1) from Accounts0 where AccountNumber = '" + AccountNumber + "';";
        try
        {
            var stmt = connection.getConnection().prepareStatement(query);
            var rs = stmt.executeQuery();
            while (rs.next())
            {
                if (rs.getInt(1) == 1)
                {
                    //System.out.println("Account Number already exists in database");
                    accountExists = true;
                }
                else
                {
                    //System.out.println("Account Number does not exist in database.");
                    accountExists = false;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return accountExists;
    }

    //Method takes the account number provided and creates a PersonalAccount object using information from the database
    public PersonalAccount retrievePersonalAccount(String accountNumber) throws SQLException
    {
        ArrayList<String> accountInformation;
        accountInformation = this.readQuery("SELECT * FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", accountsColumns);
        PersonalAccount personalAccount = new PersonalAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(4)), Float.parseFloat(accountInformation.get(5)), true, true);
        return personalAccount;
    }

    //Method takes the account number provided and creates an ISAAccount object using information from the database
    public ISAAccount retrieveISAAccount(String accountNumber) throws SQLException
    {
        ArrayList<String> accountInformation;
        accountInformation = this.readQuery("SELECT * FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", accountsColumns);
        ISAAccount isaAccount = new ISAAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(4)), Float.parseFloat(accountInformation.get(5)));
        return isaAccount;
    }

    //Method takes the account number provided and creates a BusinessAccount object using information from the database
    public BusinessAccount retrieveBusinessAccount(String accountNumber) throws SQLException
    {
        ArrayList<String> accountInformation;
        accountInformation = this.readQuery("SELECT * FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", accountsColumns);
        BusinessAccount businessAccount = new BusinessAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(4)), Float.parseFloat(accountInformation.get(5)), retrieveBusinessName(accountNumber));
        return businessAccount;
    }

    public String retrieveBusinessName(String accountNumber) throws SQLException
    {
        ArrayList<String> businessInformation;
        businessInformation = this.readQuery("SELECT * FROM Businesses0 WHERE AccountNumber = '" + accountNumber + "';", businessesColumns);
        String businessName = businessInformation.get(1);
        return businessName;
    }

    //Method takes the name and date of birth of a person, creates a user in the Users table, and returns the UserID of that user
    //This UserID can then be used as an argument for the createAccount method
    public int createUser(String firstName, String lastName, String dateOfBirth) throws SQLException
    {
        this.updateQuery("INSERT INTO Users0 (FirstName, LastName, DateOfBirth) VALUES ('" + firstName + "', '" + lastName + "', '" + dateOfBirth + "');");
        ArrayList<String> userInformation = this.readQuery("SELECT * FROM Users0 WHERE (FirstName = '" + firstName + "' AND LastName = '" + lastName + "' AND DateOfBirth = '" + dateOfBirth + "');", usersColumns);
        int userID = Integer.parseInt(userInformation.get(0));
        return userID;
    }

    //Method takes a created BusinessAccount and uses it to create a new business in the Businesses table
    public void createBusiness(BusinessAccount account) throws SQLException
    {
        this.updateQuery("INSERT INTO Businesses0 (Name, AccountNumber) VALUES ('" + account.getName() + "', '" + account.getAccountNumber() + "');");
    }

    //Method takes the relevant information and creates a personal account, before returning the account itself as an object
    public PersonalAccount createPersonalAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft) throws SQLException
    {
        this.updateQuery("INSERT INTO Accounts0 VALUES ('" + accountNumber + "', '" + sortCode + "', '" + userID + "', '" + "Personal" + "', " + balance + ", " + overdraft + ");");
        PersonalAccount personalAccount = retrievePersonalAccount(accountNumber);
        return personalAccount;
    }

    //Method takes the relevant information and creates an ISA account, before returning the account itself as an object
    public ISAAccount createISAAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft) throws SQLException
    {
        this.updateQuery("INSERT INTO Accounts0 VALUES ('" + accountNumber + "', '" + sortCode + "', " + userID + ", '" + "ISA" + "', " + balance + ", " + overdraft + ");");
        ISAAccount isaAccount = retrieveISAAccount(accountNumber);
        return isaAccount;
    }

    //Method takes the relevant information and creates a business account, before returning the account itself as an object
    public BusinessAccount createBusinessAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft, String businessName) throws SQLException
    {
        this.updateQuery("INSERT INTO Accounts0 VALUES ('" + accountNumber + "', '" + sortCode + "', " + userID + ", '" + "Business" + "', " + balance + ", " + overdraft + ");");
        BusinessAccount businessAccount = retrieveBusinessAccount(accountNumber);
        return businessAccount;
    }

    //Method takes an accountNumber String, and deletes the relevant entry from the Accounts table
    public void deleteAccount(String accountNumber) throws SQLException
    {
        this.updateQuery("DELETE FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';");
    }

    //Method takes a userID integer and deletes the relevant entry from the Users table
    public void deleteUser(int userID) throws SQLException
    {
        this.updateQuery("DELETE FROM Users0 WHERE UserID = " + userID + ";");
    }

    //Method takes a businessID integer and deletes the relevant entry from the Businesses table
    public void deleteBusiness(int businessID) throws SQLException
    {
        this.updateQuery("DELETE FROM Businesses0 WHERE ID = " + businessID + ";");
    }


}
