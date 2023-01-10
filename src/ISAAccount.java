import java.sql.SQLException;
import java.util.Scanner;

public class ISAAccount extends Account
{
    public ISAAccount(String accountNumber, String sortCode, float balance, float overdraft)
    {
        super(accountNumber, sortCode, balance, overdraft);
    }

    //Menu function for ISA account.
    @Override
    public void accountMenu() throws SQLException
    {
        int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n1. Check balance. \n2. Make a deposit. \n3. Make a withdrawal. \n4. Make a transfer. \n5. Receive interest payment. \n6. Set up a direct debit. \n7. Set up standing order.\n9. Exit", new int[] {1, 2, 3, 4, 5, 6, 7, 9});

        switch (menu)
        {
            case 1 -> displayBalance();
            case 2 -> deposit(Program.checkFloatRange("Enter deposit amount: ", 0.01f, 20000.00f));
            case 3 -> withdraw(Program.checkFloatRange("Enter withdrawal amount: ", 0.01f, 20000.00f));
            case 4 -> transfer(0, "placeholder", "placeholder");
            case 5 -> interestPayment(1.025f);
            case 6 -> directDebit(0, "placeholder", "placeholder");
            case 7 -> standingOrder(0, "placeholder", "placeholder");
            case 9 -> Program.exitProgram();
        }
    }

    public void interestPayment(float monthlyInterestMultiplier) throws SQLException
    {
        setBalance(getBalance() * monthlyInterestMultiplier);
        updateDatabaseInformation();
    }
}
