/*
Abstract class Account.
 */


import java.sql.SQLException;
import java.util.ArrayList;
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
        ArrayList<String> customerInfo = new ArrayList<>(List.of("FirstName","LastName","DateOfBirth"));

        Queries newQuery = new Queries(); //Should move to Program? Only one instance needed.
        ArrayList<String> customerInfoResults = newQuery.readQuery("SELECT FirstName, LastName, DateOfBirth from Users where ID in (SELECT UserID from Accounts where AccountNumber = " + accountNumber + " )",customerInfo);

        return customerInfoResults;
    }

    public static ArrayList retrieveCustomerAccounts(String accountNumber)
    {
        Queries newQuery = new Queries();
        String query = ("SELECT AccountNumber, AccountType FROM Accounts WHERE UserID in (SELECT UserID FROM Accounts WHERE AccountNumber = " + accountNumber + ");");
        return null;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public String getSortCode()
    {
        return sortCode;
    }

    public float getBalance()
    {
        return balance;
    }

    public void setBalance(float balance)
    {
        this.balance = balance;
    }

    // Function deposit which calls getBalance and setBalance to increment balance
    public void deposit(float increment) throws SQLException
    {
        float newBalance = getBalance() + increment;
        setBalance(newBalance);
        this.updateDatabaseInformation();
    }

    // Function withdraw which calls getBalance and setBalance to decrement balance
    public void withdraw(float decrement) throws SQLException
    {
        float newBalance = getBalance() - decrement;
        setBalance(newBalance);
        this.updateDatabaseInformation();
    }

    // Function transfer which takes two accounts and an amount as an argument and transfers money between the two

    public void transfer(float amount, Account payee, Account recipient)
    {

    }


    // Abstract method to display menu system for account.
    abstract void accountMenu();

    //Abstract methods for creating accounts should use "override" in child classes for each type of account.
    // (Note Are these methods abstract as a result of class being abstract?)

    public void generateSortCode(){}

    //accidental duplicate
    public void createAccount(){}

    //Method which updates the database so that changes made to the Account object are reflected in the relevant table
    //In other words the method synchronizes the program with the database
    public void updateDatabaseInformation() throws SQLException
    {
        Queries newQuery = new Queries();
        newQuery.updateQuery("UPDATE Accounts SET Balance = " + balance + ", Overdraft = " + overdraft + "WHERE AccountNumber = " + accountNumber + ";");
    }
}
