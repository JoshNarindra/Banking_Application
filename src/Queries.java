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
        ArrayList<String> accountInformation = new ArrayList<>(accountsColumns.size());
        accountInformation = connection.readQuery("SELECT * from Accounts where AccountNumber = " + accountNumber + ");", accountsColumns);
        PersonalAccount personalAccount = new PersonalAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(2)), Float.parseFloat(accountInformation.get(3)), true, true);
        return personalAccount;
    }

    //Method takes the account number provided and creates an ISAAccount object using information from the database
    public ISAAccount retrieveISAAccount(String accountNumber) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<String> accountInformation = new ArrayList<>(accountsColumns.size());
        accountInformation = connection.readQuery("SELECT * from Accounts where AccountNumber = " + accountNumber + ");", accountsColumns);
        ISAAccount isaAccount = new ISAAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(2)), Float.parseFloat(accountInformation.get(3)), true, true);
        return isaAccount;
    }

    //Method takes the account number provided and creates a BusinessAccount object using information from the database
    public BusinessAccount retrieveBusinessAccount(String accountNumber) throws SQLException
    {
        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<String> accountInformation = new ArrayList<>(accountsColumns.size());
        accountInformation = connection.readQuery("SELECT * from Accounts where AccountNumber = " + accountNumber + ");", accountsColumns);
        BusinessAccount businessAccount = new BusinessAccount(accountInformation.get(0), accountInformation.get(1), Float.parseFloat(accountInformation.get(2)), Float.parseFloat(accountInformation.get(3)), true, true);
        return businessAccount;
    }

    //Method takes the name and date of birth of a person, creates a user in the Users table, and returns the UserID of that user
    //This UserID can then be used as an argument for the createAccount method
    public static int createUser(String firstName, String lastName, int birthDay, int birthMonth, int birthYear)
    {

    }

    //Method takes the relevant information and creates a personal account, before returning the account itself as an object
    public static PersonalAccount createPersonalAccount(int userID, float balance, float overdraft)
    {

    }

    //Method takes the relevant information and creates an ISA account, before returning the account itself as an object
    public static ISAAccount createISAAccount(int userID, float balance, float overdraft)
    {

    }

    //Method takes the relevant information and creates a business account, before returning the account itself as an object
    public static BusinessAccount createBusinessAccount(int userID, float balance, float overdraft, String businessName)
    {

    }

    //Method takes a created BusinessAccount and uses it to create a new business in the Businesses table
    public static void createBusiness(BusinessAccount account)
    {

    }
}
