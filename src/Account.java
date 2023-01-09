/*
Abstract class Account.
 */

//Imports
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

    public static ArrayList retrieveCustomerAccounts(String accountNumber) throws SQLException
    {
        Queries newQuery = new Queries();
        String query = ("SELECT AccountNumber, AccountType FROM Accounts0 WHERE UserID in (SELECT UserID FROM Accounts0 WHERE AccountNumber = " + accountNumber + ");");

        DatabaseConnection connection = new DatabaseConnection();

        var stmt = connection.getConnection().prepareStatement(query);
        var rs = stmt.executeQuery();

        ArrayList<String> accountList = new ArrayList<>();
        int count = 0;

        while(rs.next()){
            count = count+1;
            accountList.add(count + ". " + rs.getString(1).toString() + " " + rs.getString(2).toString());
        }
        return accountList;
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

    public void transfer(float amount, String recipientAccountNumber, String recipientSortCode) throws SQLException
    {
        float newBalance = getBalance() - amount;

        if (newBalance + overdraft < 0)
        {
            System.out.println("Error. Insufficient funds. Try again.");
        }
        else
        {
            setBalance(newBalance);
            // IMPLEMENT FUNCTIONALITY HERE
            updateDatabaseInformation();
        }
    }

    public void directDebit(float amount, String recipientAccountNumber, String recipientSortCode) throws SQLException
    {
        float newBalance = getBalance() - amount;

        if (newBalance + overdraft < 0)
        {
            System.out.println("Error. Insufficient funds. Try again.");
        }
        else
        {
            setBalance(newBalance);
            // IMPLEMENT FUNCTIONALITY HERE
            updateDatabaseInformation();
        }
    }

    public void standingOrder(float amount, String recipientAccountNumber, String recipientSortCode) throws SQLException
    {
        float newBalance = getBalance() - amount;

        if (newBalance + overdraft < 0)
        {
            System.out.println("Error. Insufficient funds. Try again.");
        }
        else
        {
            setBalance(newBalance);
            // IMPLEMENT FUNCTIONALITY HERE
            updateDatabaseInformation();
        }
    }


    // Abstract method to display menu system for account.
    abstract void accountMenu() throws SQLException;

    //Abstract methods for creating accounts should use "override" in child classes for each type of account.
    // (Note Are these methods abstract as a result of class being abstract?)

    public static void generateSortCode()
    {

    }

    //Method which updates the database so that changes made to the Account object are reflected in the relevant table
    //In other words the method synchronizes the program with the database
    public void updateDatabaseInformation() throws SQLException
    {
        Queries newQuery = new Queries();
        newQuery.updateQuery("UPDATE Accounts SET Balance = " + balance + ", Overdraft = " + overdraft + "WHERE AccountNumber = " + accountNumber + ";");
    }
}
