//Separate class for all queries which need to be executed before an instance of an Account is created
//For example cannot call functions inside of Account, BusinessAccount etc. until a query made is made to create an instance
//Also separates the queries from the connection itself which looks more tidy I think

public class Queries {

    //Method takes the account number provided and creates a PersonalAccount object using information from the database
    public static PersonalAccount retrievePersonalAccount(String accountNumber)
    {
        DatabaseConnection connection = new DatabaseConnection();
        connection.runQuery("SELECT * from Accounts where AccountNumber = " + accountNumber + ");", accountInfo);
    }

    //Method takes the account number provided and creates an ISAAccount object using information from the database
    public static ISAAccount retrieveISAAccount(String accountNumber)
    {

    }

    //Method takes the account number provided and creates a BusinessAccount object using information from the database
    public static BusinessAccount retrievePersonalAccount(String accountNumber)
    {

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
