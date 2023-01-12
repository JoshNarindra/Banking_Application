import java.sql.SQLException;

public class PersonalAccount extends Account
{
    public PersonalAccount(String accountNumber, String sortCode, float balance, float overdraft)
    {
        super(accountNumber, sortCode, balance, overdraft);
    }

    //Menu function for Personal Account.
    @Override
    public void accountMenu() throws SQLException
    {
        while (true)
        {
            int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n\n1. Check balance. \n2. Make a deposit. \n3. Make a withdrawal. \n4. Make a transfer. \n5. Set up a direct debit. \n6. Set up standing order.\n9. Exit", new int[] {1, 2, 3, 4, 5, 6, 9});

            switch (menu)
            {
                case 1 -> displayBalance();
                case 2 -> deposit(Program.checkFloatRange("\nEnter deposit amount: ", 0.01f, 20000.00f));
                case 3 -> withdraw(Program.checkFloatRange("\nEnter withdrawal amount: ", 0.01f, 20000.00f));
                case 4, 5, 6 -> payAccount(Program.checkFloatRange("\nEnter payment amount:", 0, 20000), retrieveRecipientAccountNumber());
                case 9 -> Program.exitProgram();
            }
        }
    }
}
