import java.sql.SQLException;
import java.util.Scanner;

public class BusinessAccount extends Account
{
    private String name;

    public BusinessAccount(String accountNumber, String sortCode, float balance, float overdraft, String name)
    {
        super(accountNumber, sortCode, balance, overdraft);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    //Menu function for business account.
    @Override
    public void accountMenu() throws SQLException
    {
        while (true)
        {
            int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n1. Check balance. \n2. Make a deposit. \n3. Make a withdrawal. \n4. Make a transfer. \n5. Make a request. \n6. Set up a payment. \n7. Bill annual payment. \n9. Exit", new int[] {1, 2, 3, 4, 5, 6, 9});

            switch (menu)
            {
                case 1 -> displayBalance();
                case 2 -> deposit(Program.checkFloatRange("Enter deposit amount: ", 0.01f, 20000.00f));
                case 3 -> withdraw(Program.checkFloatRange("Enter withdrawal amount: ", 0.01f, 20000.00f));
                case 4 -> transfer(0, "placeholder", "placeholder");
                case 5 -> requestsMenu();
                case 6 -> paymentsMenu();
                case 7 -> makeAnnualPayment();
                case 9 -> Program.exitProgram();
            }
        }
    }

    public void requestsMenu()
    {
        int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n1. Request chequebook. \n2. Request credit card. \n3. Request change to overdraft. \n4. Request a loan. \n8. Back. \n9. Exit", new int[] {1, 2, 3, 4, 8, 9});

        switch(menu)
        {
            case 1 -> System.out.println("Placeholder request chequebook");
            case 2 -> System.out.println("Placeholder request credit card");
            case 3 -> System.out.println("Placeholder request change to overdraft");
            case 4 -> System.out.println("Placeholder request a loan");
            case 9 -> Program.exitProgram();
        }
    }

    public void paymentsMenu() throws SQLException
    {
        while (true)
        {
            int menu = Program.checkMultipleOptions("\nWhat would the customer like to do? \n\n1. Set up a direct debit. \n2. Set up a standing order. \n8. Back. \n9. Exit", new int[] {1, 2, 8, 9});

            switch(menu)
            {
                case 1 -> directDebit(0, "placeholder", "placeholder");
                case 2 -> standingOrder(0, "placeholder", "placeholder");
                case 9 -> Program.exitProgram();
            }
        }
    }

    public void makeAnnualPayment() throws SQLException
    {
        float newBalance = getBalance() - 120f;
        setBalance(newBalance);
        updateDatabaseInformation();
    }
}
