/*
Abstract class Account.
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

    public Account(String accountNumber, String sortCode, float balance, float overdraft)
    {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.balance = balance;
        this.overdraft = overdraft;
    }

    public static ArrayList retrieveCustomerInfo(String accountNumber) throws SQLException
    {
        Queries newQuery = new Queries();
        ArrayList<String> columns = new ArrayList<>(List.of("FirstName", "LastName", "DateOfBirth"));
        ArrayList<String> customerInfoResults = newQuery.readQuery("SELECT FirstName, LastName, DateOfBirth from Users where ID in (SELECT UserID from Accounts where AccountNumber = " + accountNumber + " )", columns);
        return customerInfoResults;
    }

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

    public static int retrieveUserID(String accountNumber) throws SQLException
    {
        Queries queries = new Queries();
        ArrayList<String> columnNames = new ArrayList<>(List.of("UserID"));
        ArrayList<String> results = queries.readQuery("SELECT UserID FROM Accounts0 WHERE AccountNumber = '" + accountNumber + "';", columnNames);
        return Integer.parseInt(results.get(0));
    }

    public float getBalance()
    {
        return balance;
    }

    public void setBalance(float balance)
    {
        this.balance = balance;
    }

    public void displayBalance()
    {
        System.out.println("Current balance: £" + String.format("%.2f", getBalance()));
    }

    // Function deposit which calls getBalance and setBalance to increment balance
    public void deposit(float increment) throws SQLException
    {
        float newBalance = getBalance() + increment;
        setBalance(newBalance);
        updateDatabaseInformation();
    }

    // Function withdraw which calls getBalance and setBalance to decrement balance
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

    // Function transfer which takes two accounts and an amount as an argument and transfers money between the two

    public void payAccount(float amount, String recipientAccountNumber) throws SQLException
    {
        Queries queries = new Queries();
        float newBalance = getBalance() - amount;

        if (newBalance + overdraft < 0)
        {
            System.out.println("Error. Insufficient funds. Try again.");
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
        }
    }

    public static String retrieveRecipientAccountNumber()
    {
        return Program.checkAccountNumber();
    }

    // Abstract method to display menu system for account.
    abstract void accountMenu() throws SQLException;

    //Method which updates the database so that changes made to the Account object are reflected in the relevant table
    //In other words the method synchronizes the program with the database
    public void updateDatabaseInformation() throws SQLException
    {
        Queries newQuery = new Queries();
        newQuery.updateQuery("UPDATE Accounts0 SET Balance = " + balance + ", Overdraft = " + overdraft + "WHERE AccountNumber = '" + accountNumber + "';");
    }
}
