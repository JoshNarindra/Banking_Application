/*
Main Program Class.
 */

//imports
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;

public class Program
{
    // Method main() which displays the opening menu to the user.
    // The method either calls the existingCustomersMenu() or newCustomersMenu() methods depending on user input.
    public static void main(String[] args) throws SQLException
    {
        System.out.println("\nWelcome to ACME Banking Solutions...\n");
        int menu = checkMultipleOptions("Does the customer currently have an account with us? \n1. Yes. \n2. No. \n9. Exit.", new int[]{1, 2, 9});

        switch (menu)
        {
            case 1 -> existingCustomersMenu();
            case 2 -> newCustomersMenu();
            case 9 -> exitProgram();
        }
    }

    // Method existingCustomersMenu() which displays a menu to customers who already have an account registered.
    public static void existingCustomersMenu() throws SQLException
    {
        Queries queries = new Queries();
        String accountNumber = checkAccountNumber();

        while(!queries.checkAccountExists(accountNumber))
        {
            System.out.println("No record of account. Try again.");
            accountNumber = checkAccountNumber();
        }

        //Retrieve account details.
        System.out.println("\nRetrieving account details...");
        ArrayList<String> customerInfo = Account.retrieveCustomerInfo(accountNumber);

        //Display customer info - NEEDS FIXING TO DISPLAY INDIVIDUAL RESULTS.
        System.out.println("\nName: "+customerInfo.get(0)+" "+customerInfo.get(1));
        System.out.println("D.O.B: "+customerInfo.get(2));

        //Display customers accounts with bank - NEEDS FIXING.
        System.out.println("\nCustomer Accounts: ");


        ArrayList<String> accountList = Account.retrieveCustomerAccounts(accountNumber);
        for (String s: accountList)
        {
            System.out.println(s);
        }

        System.out.println("\nEnter the account number of the account that would you like to access:");


    }

    // Method newCustomersMenu() which displays a menu to new customers.
    // The method calls the createUser() method and passes its return value to one of the methods which creates a new account, depending on user input.
    public static void newCustomersMenu() throws SQLException
    {
        System.out.println("Open an account with ACME Banking Solutions...\n");
        int menu = checkMultipleOptions("1. Open a personal account. \n2. Open a business account. \n3. Open an ISA Account. \n9. Exit.", new int[] {1, 2, 3, 9});

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
    // Finally, the PersonalAccount object's accountMenu() method is called.
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
            exitProgram();
        }
    }

    // Method openBusinessAccount() takes an int userID as an argument and inserts a row into the Accounts0 table in the database.
    // The information entered into the table is dependent on the user's input and is linked to the userID passed to the method.
    // Finally, the BusinessAccount object's accountMenu() method is called.
    public static void openBusinessAccount(int userID) throws SQLException
    {
        Queries newQuery = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();
        String businessName = checkAlphabet("Enter business name: ");
        System.out.println(businessName);
        checkCredential("Does the customer have valid business credentials? \n1. Yes. \n2. No.", "Customer must have valid business credentials to open a business account");
        checkCredential("Does the customer have a valid business type? (No enterprises, public limited companies or charities are permitted.) \n1. Yes. \n2. No.", "Customer must have a valid business type to open a business account.");
        float openingBalance = checkFloatRange("Enter opening balance: ", 1.00f, 20000.00f);
        float overdraftAmount = checkFloatRange("Enter agreed overdraft amount: ", 0.00f, 10000.00f);

        if (checkTwoOptions("Confirm account opening? \n1. Yes. \n2. No"))
        {
            String accountNumber = generator.generateAccountNumber();
            BusinessAccount businessAccount = newQuery.createBusinessAccount(accountNumber, "12-20-02", userID, openingBalance, overdraftAmount, businessName);
            //createBusiness(businessName, accountNumber);
            System.out.println("Account creation successful.");
            businessAccount.accountMenu();
        }
        else
        {
            exitProgram();
        }
    }

    // Method openISAAccount() takes an int userID as an argument and inserts a row into the Accounts0 table in the database.
    // The information entered into the table is dependent on the user's input and is linked to the userID passed to the method.
    // Finally, the ISAAccount object's accountMenu() method is called.
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
    public static void createBusiness(String businessName, String accountNumber) throws SQLException
    {
        Queries newQuery = new Queries();
        newQuery.createBusiness(businessName, accountNumber);
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
            System.out.println("Invalid input. Try again.");
            input = scanner.nextInt();
        }

        return (input == 1);
    }

    // Method checkMultipleOptions() takes a String menuString and an array of integers options as arguments.
    // The options array contains all the numbers which can be selected by the user as options.
    // The user is prompted repeatedly until they enter an integer which is contained in the options array.
    // This integer inputted is finally returned.
    // This method can be used as a template for any situation in which the user is prompted to choose between multiple options.
    public static int checkMultipleOptions(String menuString, int[] options)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(menuString);
        int input = 0;

        while (true)
        {
            input = scanner.nextInt();

            for (int option : options)
            {
                if (input == option)
                {
                    return input;
                }
            }

            System.out.println("Invalid input. Try again.");
        }
    }

    // Method checkAccountNumber() prompts the user to enter an account number and checks that the number is valid.
    // The account number entered must be the correct length and contain only numbers, or the user is re-prompted.
    // The account number is returned as a String once the user has entered a valid number.
    public static String checkAccountNumber()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter account number: ");
        String accountNumber = scanner.next();

        while (accountNumber.length() != 8 && !accountNumber.matches("[0-9]+"))
        {
            System.out.println("Invalid input. Try again.");
            accountNumber = scanner.next();
        }

        return accountNumber;
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
            input = scanner.nextLine();
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
        System.out.println("Successfully logged out.");
        System.exit(0);
    }


}
