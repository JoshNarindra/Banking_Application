/*
    Class BusinessAccount extends the abstract class Account and is used to model business accounts from the database as objects.
    An additional method makeAnnualPayment is included and is unique to BusinessAccount, as other accounts are free to set up.

    Two additional menu methods are included in addition to the accountMenu() method, to avoid cluttering the main menu:
        requestsMenu(),
        paymentsMenu().
 */

import java.sql.SQLException;

public class BusinessAccount extends Account
{
    private String name;

    // Constructor method BusinessAccount().
    public BusinessAccount(String accountNumber, String sortCode, float balance, float overdraft, String name)
    {
        super(accountNumber, sortCode, balance, overdraft);
        this.name = name;
    }

    // Method getName().
    public String getName()
    {
        return name;
    }

    // Method accountMenu() implements the abstract accountMenu() method from the Account class.
    // User is prompted to select a transaction, then the relevant method from Account or BusinessAccount is called.
    // In some cases, the checkFloatRange() method from Program is passed as an argument, in order to get further user input.
    @Override
    public void accountMenu() throws SQLException
    {
        while (true)
        {
            int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n1. Check balance. \n2. Make a deposit. \n3. Make a withdrawal. \n4. Make a transfer. \n5. Make a request. \n6. Make a payment. \n7. Bill annual payment. \n9. Exit", new int[] {1, 2, 3, 4, 5, 6, 9});

            switch (menu)
            {
                case 1 -> displayBalance();
                case 2 -> deposit(Program.checkFloatRange("\nEnter deposit amount: ", 0.01f, 20000.00f));
                case 3 -> withdraw(Program.checkFloatRange("\nEnter withdrawal amount: ", 0.01f, 20000.00f));
                case 4 -> payAccount(Program.checkFloatRange("\nEnter payment amount:", 0, 20000), retrieveRecipientAccountNumber());
                case 5 -> requestsMenu();
                case 6 -> paymentsMenu();
                case 7 -> makeAnnualPayment();
                case 9 -> Program.exitProgram();
            }
        }
    }

    // Method requestsMenu() prompts the user for an integer menu and carries out a function depending on the selection.
    // In some cases, the checkFloatRange() method from Program is passed as an argument, in order to get further user input.
    public void requestsMenu()
    {
        int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n1. Request chequebook. \n2. Request credit card. \n3. Request change to overdraft. \n4. Request a loan. \n8. Back. \n9. Exit", new int[] {1, 2, 3, 4, 8, 9});

        switch(menu)
        {
            case 1 -> System.out.println("Chequebook request logged.");
            case 2 -> System.out.println("Credit card request logged.");
            case 3 -> {
                        Program.checkFloatRange("\nEnter proposed overdraft amount: ", 0f, 10000.00f);
                        System.out.println("Change to overdraft request logged.");
                      }
            case 4 -> {
                        Program.checkFloatRange("\nEnter desired loan amount: ", 0f, 10000.00f);
                        System.out.println("Loan request logged.");
                      }
            case 9 -> Program.exitProgram();
        }
    }

    // Method paymentsMenu() prompts the user for an integer menu and carries out a function depending on the selection.
    // In some cases, the checkFloatRange() method from Program is passed as an argument, in order to get further user input.
    public void paymentsMenu()
    {
        while (true)
        {
            int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n\n1. Make direct debit payment. \n2. Make standing order payment. \n8. Back. \n9. Exit", new int[] {1, 2, 8, 9});

            switch(menu)
            {
                case 1, 2 -> payAccount(Program.checkFloatRange("\nEnter payment amount:", 0, 20000), retrieveRecipientAccountNumber());
                case 9 -> Program.exitProgram();
            }
        }
    }

    // Method makeAnnualPayment() calls the setBalance() method to reduce the account balance by 120, and calls updateDatabaseInformation() to alter the Balance in the database.
    public void makeAnnualPayment()
    {
        float newBalance = getBalance() - 120f;
        setBalance(newBalance);
        updateDatabaseInformation();
    }
}
