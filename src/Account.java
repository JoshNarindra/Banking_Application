/*
    Abstract class Account is used model the accounts in the database as objects.
    Most importantly Account contains the abstract method accountMenu() which is used to implement the main menu for each account type.

    Functionality methods carry out functions which are common to all types of accounts:
        deposit(),
        withdraw(),
        payAccount(),
        accountMenu().

    Retrieve methods call methods from Queries to retrieve customer and account information; or methods from Program to obtain user input:
        retrieveCustomerInfo(),
        retrieveCustomerAccounts(),
        retrieveUserID(),
        retrieveRecipientAccountNumber().
 */

//Imports
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

abstract class Account
{
    private String accountNumber;
    private String sortCode;
    private float balance;
    private float overdraft;

    // Constructor method Account().
    public Account(String accountNumber, String sortCode, float balance, float overdraft)
    {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.balance = balance;
        this.overdraft = overdraft;
    }

    // Method retrieveCustomerInfo() takes a String accountNumber as its argument.
    // The readQuery() method of the Queries class is then called to return the first name, last name and date of birth of the customer as an ArrayList.
    public static ArrayList retrieveCustomerInfo(String accountNumber) throws SQLException
    {
        Queries newQuery = new Queries();
        ArrayList<String> columns = new ArrayList<>(List.of("FirstName", "LastName", "DateOfBirth"));
        ArrayList<String> customerInfoResults = newQuery.readQuery("SELECT FirstName, LastName, DateOfBirth from Users where ID in (SELECT UserID from Accounts where AccountNumber = " + accountNumber + " )", columns);
        return customerInfoResults;
    }

    // Method retrieveCustomerAccounts() takes a String accountNumber as its argument.
    // The readQuery() method of the Queries class is then called to return all accounts associated with the customer as a HashMap.
    public static HashMap retrieveCustomerAccounts(String accountNumber) throws SQLException
    {
        Queries queries = new Queries();
        ArrayList<String> columns = new ArrayList<>(List.of("AccountNumber", "AccountType"));
        ArrayList<String> results = queries.readQuery("SELECT AccountNumber, AccountType FROM Accounts0 WHERE UserID in (SELECT UserID FROM Accounts0 WHERE AccountNumber = " + accountNumber + ");", columns);
        HashMap<String, String> accountList = new HashMap<>();

        for (int i = 0; i < results.size(); i=i+2)
        {
            accountList.put(results.get(i), results.get(i+1));
        }

        return accountList;
    }

    // Method retrieveUserID() takes a String accountNumber as its argument.
    // The readQuery() method of the Queries class is then called to return the UserID integer in the database associated with the customer.
    public static int retrieveUserID(String accountNumber) throws SQLException
    {
        Queries queries = new Queries();
        ArrayList<String> columnNames = new ArrayList<>(List.of("UserID"));
        ArrayList<String> results = queries.readQuery("SELECT UserID FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", columnNames);
        return Integer.parseInt(results.get(0));
    }

    // Method getBalance().
    public float getBalance()
    {
        return balance;
    }

    // Method setBalance().
    public void setBalance(float balance)
    {
        this.balance = balance;
    }

    // Method displayBalance() prints the account's current balance to the console.
    public void displayBalance()
    {
        System.out.println("Current balance: £" + String.format("%.2f", getBalance()));
    }

    // Method deposit() takes a float increment as its argument.
    // The account's balance is then incremented and the database updated by calling methods setBalance() and updateDatabaseInformation().
    public void deposit(float increment) throws SQLException
    {
        float newBalance = getBalance() + increment;
        setBalance(newBalance);
        updateDatabaseInformation();
    }

    // Method withdraw() takes a float decrement as its argument.
    // A check is made to ensure that the account has sufficient funds to make the withdrawal.
    // If funds are sufficient, then the balance is decremented and the database updated, by calling methods setBalance() and updateDatabaseInformation().
    public void withdraw(float decrement) throws SQLException
    {
        float newBalance = getBalance() - decrement;

        if (newBalance + overdraft < 0)
        {
            System.out.println("Error. Insufficient funds. Try again.");
        }
        else
        {
            setBalance(newBalance);
            updateDatabaseInformation();
        }
    }

    // Method payAccount takes a float amount and String recipientAccountNumber as arguments.
    // Checks are made to make sure the account has sufficient funds to make a payment and that the recipient account exists.
    // If checks are passed, then the balance is decremented by amount by calling the setBalance() method.
    // Lastly, the database is updated by calling the updateDatabaseInformation() method and the updateQuery() method of the Queries class.
    public void payAccount(float amount, String recipientAccountNumber) throws SQLException
    {
        Queries queries = new Queries();
        float newBalance = getBalance() - amount;

        if (newBalance + overdraft < 0)
        {
            System.out.println("Insufficient funds. Try again.");
        }
        else if (!queries.checkAccountExists(recipientAccountNumber))
        {
            System.out.println("Recipient account not found. Try again.");
        }
        else
        {
            setBalance(newBalance);
            updateDatabaseInformation();
            queries.updateQuery("UPDATE Accounts0 SET Balance = Balance + " + amount + " WHERE AccountNumber = '" + recipientAccountNumber + "';");
            System.out.println("\nPayment successful.");
        }
    }

    // Method retrieveRecipientAccountNumber() calls the checkAccountNumber() of the Program class, and returns the user-inputted account number.
    public static String retrieveRecipientAccountNumber()
    {
        return Program.checkAccountNumber();
    }

    // Method updateDatabaseInformation() synchronises the account's balance and overdraft variables with the Balance and Overdraft columns in the database.
    public void updateDatabaseInformation() throws SQLException
    {
        Queries newQuery = new Queries();
        newQuery.updateQuery("UPDATE Accounts0 SET Balance = " + balance + ", Overdraft = " + overdraft + "WHERE AccountNumber = '" + accountNumber + "';");
    }

    // Abstract method accountMenu() is implemented in the daughter classes of Account.
    abstract void accountMenu() throws SQLException;
}
