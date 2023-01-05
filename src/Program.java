/*
Main Program Class.
 */

//imports
import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Program
{

    public static void main(String[] args) throws SQLException
    {
        accountExists();

//        AccountNumberGeneration x = new AccountNumberGeneration();
//        x.generateAccountNumber();
    }

    public static void accountExists() throws SQLException
    {

        //Initial Menu.
        System.out.println("Welcome to ACME Banking Solutions...");
        System.out.println("\n Does the customer currently have an account with us? \n 1. Yes. \n 2. No. \n 9. Exit.");

        //Scanner to read bank tellers input for user choice.
        Scanner s1 = new Scanner(System.in);
        int menu = s1.nextInt();

        //Re-prompting the teller for input until either 1 or 2 is inputted.
        while (menu != 1 && menu != 2 && menu != 9) {
            System.out.println("Invalid input. Try again.");
            menu = s1.nextInt();
        }

        //Switch-Case statement for initial menu.
        switch (menu) {
            case 1 -> customerInfo();
            case 2 -> openNewAccount();
            case 9 -> exitProgram();
        }
    }

    public static void customerInfo() throws SQLException
    {

        //Input account number with same means as above.
        System.out.println("\n Enter account number:");
        Scanner s2 = new Scanner(System.in);
        String accountNumber = s2.nextLine();

        //Re-prompting the teller for input until an account number of correct length is inputted.
        while (accountNumber.length() != 8) {
            System.out.println("\n Invalid input. Try again.");
            accountNumber = s2.nextLine();
        }
        System.out.println(accountNumber);

        //Retrieve account details.
        System.out.println("\n Retrieving account details...");
        Account.retrieveCustomerInfo(accountNumber);

        //Display customer info - NEEDS FIXING TO DISPLAY INDIVIDUAL RESULTS.
        System.out.println("\n Name: ");
        System.out.println("\n D.O.B: ");

        //Display customers accounts with bank - NEEDS FIXING.
        System.out.println("\n Customer Accounts: ");

        //Option to allow current customer to open new account. - NEEDS REMOVING
        PersonalAccount x = new PersonalAccount("12312434", "090109", 45.45f, 0.00f, true, true);
        x.accountMenu();
    }

    public static void openNewAccount() throws SQLException {
        //Menu to create an account for new customer.
        System.out.println("Open an account with ACME Banking");
        System.out.println(" 1. Open a personal account. \n 2. Open a business account. \n 3. Open an ISA Account. \n 9. Exit.");

        //Scanner to read bank tellers input for user menu choice.
        Scanner s1 = new Scanner(System.in);
        int menu = s1.nextInt();

        //Re-prompting the teller for input until either 1, 2 or 3 is inputted.
        while (menu != 1 && menu != 2 && menu != 3 && menu != 9) {
            System.out.println("Invalid input. Try again.");
            menu = s1.nextInt();
        }

        //Switch-Case statement for initial menu. - IMPLEMENT FUNCTIONALITY
        switch (menu) {
            case 1 -> openPersonalAccount(createUser());
            case 2 -> openBusinessAccount(createUser());
            case 3 -> openISAAccount(createUser());
            case 9 -> exitProgram();
        }
    }

    public static void openPersonalAccount(int userID) throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        Queries newQuery = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();

        float openingBalance;

        if (!(checkTwoOptions("Does the customer have a valid personal ID? \n 1. Yes \n 2. No") && checkTwoOptions("Does the customer have a valid address? \n 1. Yes \n 2. No")))
        {
            System.out.println("Customer must have valid ID and address to open a personal account.");
            accountExists();
        }

        System.out.println("Enter opening balance: ");
        openingBalance = scanner.nextFloat();

        while (openingBalance < 1.00f)
        {
            System.out.println("Customer must have at least £1.00 to open an account.");
            accountExists();
        }

        if (checkTwoOptions("Confirm account opening? \n 1. Yes \n 2. No"))
        {
            PersonalAccount personalAccount = newQuery.createPersonalAccount(generator.generateAccountNumber(), "12-20-02", userID, openingBalance, 0.00f);
            System.out.println("Account creation successful.");
            personalAccount.accountMenu();
        }
        else
        {
            accountExists();
        }
    }

    public static void openBusinessAccount(int userID) throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        Queries newQuery = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();

        float openingBalance;
        float overdraftAmount;
        String businessName = "-";

        while (!businessName.matches("[a-z, A-Z]+"))
        {
            System.out.println("Enter business name: ");
            businessName = scanner.next();
        }

        if (!(checkTwoOptions("Does the customer have valid business credentials? \n 1. Yes \n 2. No") && checkTwoOptions("Does the customer have a valid business type? \n 1. Yes \n 2. No")))
        {
            System.out.println("Customer must have valid business credentials of a valid business type to open a business account.");
            accountExists();
        }

        System.out.println("Enter opening balance: ");
        openingBalance = scanner.nextFloat();

        while (openingBalance < 1.00f)
        {
            System.out.println("Customer must have at least £1.00 to open an account.");
            accountExists();
        }

        System.out.println("Enter agreed overdraft amount: ");
        overdraftAmount = scanner.nextFloat();

        while (overdraftAmount < 0.00f || overdraftAmount > 10000.00f)
        {
            System.out.println("Please enter a valid overdraft amount.");
            overdraftAmount = scanner.nextFloat();
        }

        if (checkTwoOptions("Confirm account opening? \n 1. Yes \n 2. No"))
        {
            BusinessAccount businessAccount = newQuery.createBusinessAccount(generator.generateAccountNumber(), "12-20-02", userID, openingBalance, overdraftAmount, businessName);
            System.out.println("Account creation successful.");
            createBusiness(businessAccount);
            businessAccount.accountMenu();
        }
        else
        {
            accountExists();
        }
    }

    public static void openISAAccount(int userID) throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        Queries newQuery = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();

        float openingBalance;

        if (!(checkTwoOptions("Does the customer have a valid personal ID? \n 1. Yes \n 2. No") && checkTwoOptions("Does the customer meet the age requirements for an ISA account? \n 1. Yes \n 2. No")))
        {
            System.out.println("Customer must have valid ID and meet the age requirements to open an ISA account.");
            accountExists();
        }

        System.out.println("Enter opening balance: ");
        openingBalance = scanner.nextFloat();

        while (openingBalance < 0.00f)
        {
            System.out.println("Invalid input. Try again.");
            accountExists();
        }

        if (checkTwoOptions("Confirm account opening? \n 1. Yes \n 2. No"))
        {
            ISAAccount isaAccount = newQuery.createISAAccount(generator.generateAccountNumber(), "12-20-02", userID, openingBalance, 0.00f);
            System.out.println("Account creation successful.");
            isaAccount.accountMenu();
        }
        else
        {
            accountExists();
        }
    }

    public static int createUser() throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        Queries newQuery = new Queries();

        String firstName = "-";
        String lastName = "-";
        int birthYear = 0;
        int birthMonth = 0;
        int birthDay = 0;
        String dateOfBirth = (birthYear + "-" + String.format("%02d", birthMonth) + "-" + String.format("%02d", birthDay));

        while (!firstName.matches("[a-z, A-Z]+"))
        {
            System.out.println("Enter first name: ");
            firstName = scanner.next();
        }

        while (!lastName.matches("[a-z, A-Z]+"))
        {
            System.out.println("Enter last name: ");
            lastName = scanner.next();
        }

        while (birthDay < 1 || birthMonth > 31)
        {
            System.out.println("Enter birth day: ");
            birthDay = scanner.nextInt();
        }

        while (birthMonth < 1 || birthMonth > 12)
        {
            System.out.println("Enter birth month: ");
            birthMonth = scanner.nextInt();
        }

        while (birthYear < 1900 || birthYear > 2007)
        {
            System.out.println("Enter birth year: ");
            birthYear = scanner.nextInt();
        }

        return newQuery.createUser(firstName, lastName, dateOfBirth);
    }

    public static void createBusiness(BusinessAccount businessAccount) throws SQLException
    {
        Queries newQuery = new Queries();
        newQuery.createBusiness(businessAccount);
    }

    public static boolean checkTwoOptions(String menuString)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println(menuString);
        int confirmStatus = scanner.nextInt();

        while (confirmStatus != 1 && confirmStatus != 2)
        {
            System.out.println("Invalid input. Try again.");
            confirmStatus = scanner.nextInt();
        }

        return (confirmStatus == 1);
    }

    public static void exitProgram()
    {
        System.out.println("Successfully logged out.");
        System.exit(0);
    }


}
