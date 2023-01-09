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
    }

    public static void accountExists() throws SQLException
    {

        //Initial Menu.
        System.out.println(" Welcome to ACME Banking Solutions...");
        System.out.println("\n Does the customer currently have an account with us? \n1. Yes. \n2. No. \n9. Exit.");

        //Scanner to read bank tellers input for user choice.
        Scanner s1 = new Scanner(System.in);
        int menu = s1.nextInt();

        //Re-prompting the teller for input until either 1 or 2 is inputted.
        while (menu != 1 && menu != 2 && menu != 9) {
            System.out.println(" Invalid input. Try again.");
            menu = s1.nextInt();
        }

        //Switch-Case statement for initial menu.
        switch (menu)
        {
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
        while (accountNumber.length() != 8)
        {
            System.out.println(" Invalid input. Try again.");
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

    public static void openNewAccount() throws SQLException
    {
        //Menu to create an account for new customer.
        System.out.println("Open an account with ACME Banking");
        System.out.println("1. Open a personal account. \n2. Open a business account. \n3. Open an ISA Account. \n9. Exit.");

        //Scanner to read bank tellers input for user menu choice.
        Scanner s1 = new Scanner(System.in);
        int menu = s1.nextInt();

        //Re-prompting the teller for input until either 1, 2 or 3 is inputted.
        while (menu != 1 && menu != 2 && menu != 3 && menu != 9)
        {
            System.out.println(" Invalid input. Try again.");
            menu = s1.nextInt();
        }

        //Switch-Case statement for initial menu. - IMPLEMENT FUNCTIONALITY
        switch (menu)
        {
            case 1 -> openPersonalAccount(createUser());
            case 2 -> openBusinessAccount(createUser());
            case 3 -> openISAAccount(createUser());
            case 9 -> exitProgram();
        }
    }

    // Method openPersonalAccount() takes an int userID as an argument and inserts a row into the Accounts0 table in the database.
    // The information entered into the table is dependent on the user's input and is linked to the userID passed to the method.
    public static void openPersonalAccount(int userID) throws SQLException
    {
        Queries newQuery = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();
        checkCredential("Does the customer have a valid personal ID? (Only driving licence or passport permitted.) \n1. Yes. \n2. No.", "Customer must have valid ID to open a personal account.");
        checkCredential("Does the customer have a valid proof of address? (Utility bill, council letter, etc. permitted.) \n1. Yes. \n2. No.", "Customer must have a valid proof of address to open a personal account.");
        float openingBalance = checkFloatRange("Enter opening balance: ", 1.00f, 20000.00f);

        if (checkTwoOptions(" Confirm account opening? \n 1. Yes \n 2. No"))
        {
            PersonalAccount personalAccount = newQuery.createPersonalAccount(generator.generateAccountNumber(), "12-20-02", userID, openingBalance, 0.00f);
            System.out.println(" Account creation successful.");
            personalAccount.accountMenu();
        }
        else
        {
            accountExists();
        }
    }

    // Method openBusinessAccount() takes an int userID as an argument and inserts a row into the Accounts0 table in the database.
    // The information entered into the table is dependent on the user's input and is linked to the userID passed to the method.
    public static void openBusinessAccount(int userID) throws SQLException
    {
        Queries newQuery = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();
        String businessName = checkAlphabet("Enter business name: ");
        checkCredential("Does the customer have valid business credentials? \n1. Yes. \n2. No.", "Customer must have valid business credentials to open a business account");
        checkCredential("Does the customer have a valid business type? (No enterprises, public limited companies or charities are permitted.) \n1. Yes. \n2. No.", "Customer must have a valid business type to open a business account.");
        float openingBalance = checkFloatRange("Enter opening balance: ", 1.00f, 20000.00f);
        float overdraftAmount = checkFloatRange("Enter agreed overdraft amount: ", 0.00f, 10000.00f);

        if (checkTwoOptions("Confirm account opening? \n1. Yes. \n2.No"))
        {
            BusinessAccount businessAccount = newQuery.createBusinessAccount(generator.generateAccountNumber(), "12-20-02", userID, openingBalance, overdraftAmount, businessName);
            System.out.println("Account creation successful.");
            createBusiness(businessAccount);
            businessAccount.accountMenu();
        }
        else
        {
            exitProgram();
        }
    }

    // Method openISAAccount() takes an int userID as an argument and inserts a row into the Accounts0 table in the database.
    // The information entered into the table is dependent on the user's input and is linked to the userID passed to the method.
    public static void openISAAccount(int userID) throws SQLException
    {
        Queries newQuery = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();
        checkCredential("Does the customer have valid personal ID? (Only driving licence or passport permitted.) \n1. Yes. \n2. No.", "Customer must have valid ID to open an ISA account.");
        checkCredential("Does the customer meet the age requirements for an ISA account? (16+) \n1. Yes. \n2. No.", "Customer must meet the age requirements to open an ISA account.");
        float openingBalance = checkFloatRange("Enter opening balance: ", 0.00f, 20000.00f);

        if (checkTwoOptions("Confirm account opening? \n1. Yes. \n2. No."))
        {
            ISAAccount isaAccount = newQuery.createISAAccount(generator.generateAccountNumber(), "12-20-02", userID, openingBalance, 0.00f);
            System.out.println("Account creation successful.");
            isaAccount.accountMenu();
        }
        else
        {
            exitProgram();
        }
    }

    // Method createUser() prompts the user for information which is then fed to the method createUser() in the Queries class.
    // The result is that a new row is added to the Users0 table in the database.
    // Finally, an integer representing the relevant ID in the Users0 table is returned.
    public static int createUser() throws SQLException
    {
        Queries newQuery = new Queries();
        String firstName = checkAlphabet("Enter first name: ");
        String lastName = checkAlphabet("Enter last name: ");
        int birthDay = checkIntegerRange("Enter birth day: ", 1, 31);
        int birthMonth = checkIntegerRange("Enter birth month: ", 1, 12);
        int birthYear = checkIntegerRange("Enter birth year: ", 1900, 2007);
        String dateOfBirth = (birthYear + "-" + String.format("%02d", birthMonth) + "-" + String.format("%02d", birthDay));
        return newQuery.createUser(firstName, lastName, dateOfBirth);
    }

    // Method createBusiness() takes a BusinessAccount object as its argument and calls the method createBusiness() from the Queries class.
    // The result is that a new row is inserted into the Businesses0 table based on the newly created business account.
    public static void createBusiness(BusinessAccount businessAccount) throws SQLException
    {
        Queries newQuery = new Queries();
        newQuery.createBusiness(businessAccount);
    }

    // Method checkTwoOptions() takes a String menuString (the sentence to be printed to the console) as an argument and returns a boolean.
    // The boolean returned is based on the comparison between the user's input and the integer 1.
    // This method can be used as a template for any situation in which the user is prompted to choose between two options.
    public static boolean checkTwoOptions(String menuString)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(menuString);
        int input = scanner.nextInt();

        while (input != 1 && input != 2)
        {
            System.out.println(" Invalid input. Try again.");
            input = scanner.nextInt();
        }

        return (input == 1);
    }

    // Method checkCredential() takes a String menuString and a String exitString as arguments.
    // The user is prompted for an integer repeatedly until 1 or 2 is entered.
    // If the user enters 1 the method passes, while if the user enters 2 the method exitProgram() is called to end the session.
    // This method can be used as a template for any situation in which the user is prompted to verify customer credentials.
    public static void checkCredential(String menuString, String exitString)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(menuString);
        int input = scanner.nextInt();

        while (input != 1 && input != 2)
        {
            input = scanner.nextInt();
        }

        if (input == 2)
        {
            System.out.println(exitString);
            exitProgram();
        }
    }

    // Method checkAlphabet() takes a String menuString as an argument and returns a String based on the user's input.
    // The user is prompted repeatedly until an input of the correct type (only letters) is submitted.
    // This method can be used as a template for any situation in which the user is prompted to enter a name or written response.
    public static String checkAlphabet(String menuString)
    {
        Scanner scanner = new Scanner(System.in);
        String input = "-";

        while (!input.matches("[a-z, A-Z]+"))
        {
            System.out.println(menuString);
            input = scanner.next();
        }

        return input;
    }

    // Method checkIntegerRange takes a String menuString, int lowerBound and int upperBound as arguments.
    // The user is prompted to enter an integer which must fall between the lowerBound and upperBound (inclusive).
    // This method can be used as a template for any situation in which the user is prompted to enter a date.
    public static int checkIntegerRange(String menuString, int lowerBound, int upperBound)
    {
        Scanner scanner = new Scanner(System.in);
        int input = lowerBound - 1;

        while (input < lowerBound || input > upperBound)
        {
            System.out.println(menuString);
            input = scanner.nextInt();
        }

        return input;
    }

    // Method checkFloatRange takes a String menuString, float lowerBound and float upperBound as arguments.
    // The user is prompted to enter a float which must fall between the lowerBound and upperBound (inclusive).
    // This method can be used as a template for any situation in which the user is prompted to enter a monetary amount.
    public static float checkFloatRange(String menuString, float lowerBound, float upperBound)
    {
        Scanner scanner = new Scanner(System.in);
        float input = lowerBound - 1.00f;

        while (input < lowerBound || input > upperBound)
        {
            System.out.println(menuString);
            input = scanner.nextFloat();
        }

        return input;
    }

    // Method exitProgram() terminates the program running to end the session when called.
    public static void exitProgram()
    {
        System.out.println(" Successfully logged out.");
        System.exit(0);
    }


}
