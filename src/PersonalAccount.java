import java.sql.SQLException;

public class PersonalAccount extends Account
{
    private Boolean validPersonalID;
    private Boolean validAddressID;

    public PersonalAccount(String accountNumber, String sortCode, float balance, float overdraft, Boolean validPersonalID, Boolean validAddressID)
    {
        super(accountNumber, sortCode, balance, overdraft);
        this.validPersonalID = validPersonalID;
        this.validAddressID = validAddressID;
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
                case 2 -> deposit(Program.checkFloatRange("Enter deposit amount: ", 0.01f, 20000.00f));
                case 3 -> withdraw(Program.checkFloatRange("Enter withdrawal amount: ", 0.01f, 20000.00f));
                case 4, 5, 6 -> payAccount(0, retrieveRecipientAccountNumber());
                case 9 -> Program.exitProgram();
            }
        }
    }
}
