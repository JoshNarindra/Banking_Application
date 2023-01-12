/*
    Class ISAAccount extends the abstract class Account and is used to model ISA accounts from the database as objects.
    An additional method, interestPayment() is contained which is unique to ISAAccount, given the account pays interest.
    An override of the deposit() method is also contained and unique to ISAAccount.
 */

import java.sql.SQLException;

public class ISAAccount extends Account
{
    // Constructor method ISAAccount().
    public ISAAccount(String accountNumber, String sortCode, float balance, float overdraft)
    {
        super(accountNumber, sortCode, balance, overdraft);
    }

    // Method accountMenu() implements the abstract accountMenu() method from the Account class.
    // User is prompted to select a transaction, then the relevant method from Account or ISAAccount is called.
    // In some cases, the checkFloatRange() method from Program is passed as an argument, in order to get further user input.
    @Override
    public void accountMenu() throws SQLException
    {
        while (true)
        {
            int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n\n1. Check balance. \n2. Make a deposit. \n3. Make a withdrawal. \n4. Make a transfer. \n5. Receive interest payment. \n6. Make a direct debit payment. \n7. Make a standing order payment.\n9. Exit", new int[] {1, 2, 3, 4, 5, 6, 7, 9});

            switch (menu)
            {
                case 1 -> displayBalance();
                case 2 -> deposit(Program.checkFloatRange("\nEnter deposit amount: ", 0.01f, 20000.00f));
                case 3 -> withdraw(Program.checkFloatRange("\nEnter withdrawal amount: ", 0.01f, 20000.00f));
                case 4, 6, 7 -> payAccount(Program.checkFloatRange("\nEnter payment amount:", 0, 20000), retrieveRecipientAccountNumber());
                case 5 -> interestPayment(1.025f);
                case 9 -> Program.exitProgram();
            }
        }
    }

    // Method deposit() is an Override of the method in the Account class and takes a float increment as an argument.
    // The account's balance is incremented and the database updated by calling methods setBalance() and updateDatabaseInformation().
    // Additionally, a check is included to ensure that the new account balance does not exceed 20000, given this is a limit for ISA accounts (currently this is hard-coded).
    @Override
    public void deposit(float increment) throws SQLException
    {
        float newBalance = getBalance() + increment;

        if (newBalance > 20000f)
        {
            System.out.println("Amount exceeds maximum balance for ISA account. Try again.");
        }
        else
        {
            setBalance(newBalance);
            updateDatabaseInformation();
        }
    }

    // Method interestPayment() takes a float monthlyInterestMultiplier as an argument.
    // Methods setBalance() and updateDatabaseInformation() are then called to increase the balance by factor monthlyInterestMultiplier.
    public void interestPayment(float monthlyInterestMultiplier) throws SQLException
    {
        setBalance(getBalance() * monthlyInterestMultiplier);
        updateDatabaseInformation();
    }
}
