import java.sql.SQLException;

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
        while (true)
        {
            int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n\n1. Check balance. \n2. Make a deposit. \n3. Make a withdrawal. \n4. Make a transfer. \n5. Receive interest payment. \n6. Make a direct debit payment. \n7. Make a standing order payment.\n9. Exit", new int[] {1, 2, 3, 4, 5, 6, 7, 9});

            switch (menu)
            {
                case 1 -> displayBalance();
                case 2 -> deposit(Program.checkFloatRange("Enter deposit amount: ", 0.01f, 20000.00f));
                case 3 -> withdraw(Program.checkFloatRange("Enter withdrawal amount: ", 0.01f, 20000.00f));
                case 4, 6, 7 -> payAccount(Program.checkFloatRange("\nEnter payment amount:", 0, 20000), retrieveRecipientAccountNumber());
                case 5 -> interestPayment(1.025f);
                case 9 -> Program.exitProgram();
            }
        }
    }

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

    public void interestPayment(float monthlyInterestMultiplier) throws SQLException
    {
        setBalance(getBalance() * monthlyInterestMultiplier);
        updateDatabaseInformation();
    }
}
