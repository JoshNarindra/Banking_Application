/*
    Class PersonalAccount extends the abstract class Account and is used to model personal accounts from the database as objects.
 */

public class PersonalAccount extends Account
{
    // Constructor method PersonalAccount().
    public PersonalAccount(String accountNumber, String sortCode, float balance, float overdraft)
    {
        super(accountNumber, sortCode, balance, overdraft);
    }

    // Method accountMenu() implements the abstract accountMenu() method from the Account class.
    // User is prompted to select a transaction, then the relevant method from Account is called.
    // In some cases, the checkFloatRange() method from Program is passed as an argument, in order to get further user input.
    @Override
    public void accountMenu()
    {
        while (true)
        {
            int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n\n1. Check balance. \n2. Make a deposit. \n3. Make a withdrawal. \n4. Make a transfer. \n5. Set up a direct debit. \n6. Set up standing order.\n9. Exit", new int[] {1, 2, 3, 4, 5, 6, 9});

            switch (menu)
            {
                case 1 -> displayBalance();
                case 2 -> deposit(Program.checkFloatRange("\nEnter deposit amount: ", Variables.personalAccountMinimumDeposit, Variables.personalAccountMaximumDeposit));
                case 3 -> withdraw(Program.checkFloatRange("\nEnter withdrawal amount: ", Variables.personalAccountMinimumWithdrawal, Variables.personalAccountMaximumWithdrawal));
                case 4, 5, 6 -> payAccount(Program.checkFloatRange("\nEnter payment amount:", Variables.personalAccountMinimumPayment, Variables.personalAccountMaximumPayment), retrieveRecipientAccountNumber());
                case 9 -> Program.exitProgram();
            }
        }
    }
}
