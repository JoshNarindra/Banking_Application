//Separate class for all queries which need to be executed before an instance of an Account is created
//For example cannot call functions inside of Account, BusinessAccount etc. until a query made is made to create an instance
//Also separates the queries from the connection itself which looks more tidy I think

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Queries
{
    public ArrayList<String> accountsColumns = new ArrayList<String>(List.of("AccountNumber", "SortCode", "UserID", "AccountType", "Balance", "Overdraft"));
    public ArrayList<String> usersColumns = new ArrayList<String>(List.of("ID", "FirstName", "LastName", "DateOfBirth"));
    public ArrayList<String> businessesColumns = new ArrayList<String>(List.of("ID", "Name", "AccountNumber"));

    //Method takes the account number provided and creates a PersonalAccount object using information from the database
    public PersonalAccount retrievePersonalAccount(String accountNumber) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<String> accountInformation;
        accountInformation = connection.readQuery("SELECT * FROM Accounts where AccountNumber = " + accountNumber + ");", accountsColumns);
        PersonalAccount personalAccount = new PersonalAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(2)), Float.parseFloat(accountInformation.get(3)), true, true);
        return personalAccount;
    }

    //Method takes the account number provided and creates an ISAAccount object using information from the database
    public ISAAccount retrieveISAAccount(String accountNumber) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<String> accountInformation;
        accountInformation = connection.readQuery("SELECT * FROM Accounts where AccountNumber = " + accountNumber + ");", accountsColumns);
        ISAAccount isaAccount = new ISAAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(2)), Float.parseFloat(accountInformation.get(3)));
        return isaAccount;
    }

    //Method takes the account number provided and creates a BusinessAccount object using information from the database
    public BusinessAccount retrieveBusinessAccount(String accountNumber) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<String> accountInformation;
        accountInformation = connection.readQuery("SELECT * FROM Accounts where AccountNumber = " + accountNumber + ");", accountsColumns);
        BusinessAccount businessAccount = new BusinessAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(2)), Float.parseFloat(accountInformation.get(3)), retrieveBusinessName(accountNumber));
        return businessAccount;
    }

    public String retrieveBusinessName(String accountNumber) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<String> businessInformation;
        businessInformation = connection.readQuery("SELECT * FROM Businesses where AccountNumber = " + accountNumber + ");", businessesColumns);
        String businessName = businessInformation.get(1);
        return businessName;
    }

    //Method takes the name and date of birth of a person, creates a user in the Users table, and returns the UserID of that user
    //This UserID can then be used as an argument for the createAccount method
    public int createUser(String firstName, String lastName, String dateOfBirth) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<String> userInformation = null;
        connection.updateQuery("INSERT INTO Users (FirstName, LastName, DateOfBirth) VALUES (" + firstName + ", " + lastName + ", " + dateOfBirth + ");");
        connection.readQuery("SELECT * FROM Accounts WHERE FirstName = " + firstName + ", LastName = " + lastName + ", DateOfBirth = " + dateOfBirth + ");", accountsColumns);
        int userID = Integer.parseInt(userInformation.get(0));
        return userID;
    }

    //Method takes a created BusinessAccount and uses it to create a new business in the Businesses table
    public static void createBusiness(BusinessAccount account) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        connection.updateQuery("INSERT INTO Businesses (Name, AccountNumber) VALUES (" + account.getName() + ", " + account.getAccountNumber() + ");");
    }

    //Method takes the relevant information and creates a personal account, before returning the account itself as an object
    public PersonalAccount createPersonalAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        connection.updateQuery("INSERT INTO Accounts VALUES (" + accountNumber + ", " + sortCode + ", " + userID + ", " + "Personal" + ", " + balance + ", " + overdraft + ");");
        PersonalAccount personalAccount = retrievePersonalAccount(accountNumber);
        return personalAccount;
    }

    //Method takes the relevant information and creates an ISA account, before returning the account itself as an object
    public ISAAccount createISAAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        connection.updateQuery("INSERT INTO Accounts VALUES (" + accountNumber + ", " + sortCode + ", " + userID + ", " + "ISA" + ", " + balance + ", " + overdraft + ");");
        ISAAccount isaAccount = retrieveISAAccount(accountNumber);
        return isaAccount;
    }

    //Method takes the relevant information and creates a business account, before returning the account itself as an object
    public BusinessAccount createBusinessAccount(String accountNumber, String sortCode, int userID, float balance, float overdraft, String businessName) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        connection.updateQuery("INSERT INTO Accounts VALUES (" + accountNumber + ", " + sortCode + ", " + userID + ", " + "Business" + ", " + balance + ", " + overdraft + ");");
        BusinessAccount businessAccount = retrieveBusinessAccount(accountNumber);
        return businessAccount;
    }

    public void deleteAccount(String accountNumber)
    {

    }

    public void deleteUser(int userID)
    {

    }

    public void deleteBusiness(int businessID)
    {

    }


}
