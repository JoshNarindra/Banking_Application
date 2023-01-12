/*
    Class Program is the main class of the application, which contains the initial methods to be run.
    The main role of the Program class is to get customer information on which Account object is to be manipulated.
    An Account object is either created from existing data in the database (i.e. accessing an existing account) or from new data inputted by the user (i.e. creating a new account).
    Once the Account object is identified, the Account.accountMenu() method is called.

    Menu methods print a number of options to the console, take user input and call the appropriate method:
        existingCustomersMenu(),
        newCustomersMenu(),
        existingAccountsMenu(),
        createNewAccountMenu().

    Open account methods take user input by calling a check method, create a new Account object and database entry based on that input, and call the accountMenu() method of that Account object:
        openPersonalAccount(),
        openBusinessAccount(),
        openISAAccount().

    Check methods take user input, verify the input is of the correct type, value etc. and return the input to the stack:
        createUser(),
        checkTwoOptions(),
        checkMultipleOptions(),
        checkAccountNumber(),
        checkCredential()
        checkAlphabet(),
        checkIntegerRange(),
        checkFloatRange().

    The exitProgram() method can be called to terminate the program while running, equivalent to a logout.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.SQLException;

public class Program
{
    // Method main() which displays the opening menu to the user.
    // The method either calls the existingCustomersMenu() or newCustomersMenu() methods depending on user input.
    public static void main(String[] args) throws SQLException
    {
        System.out.println("\nWelcome to ACME Banking Solutions...\n");
        int menu = checkMultipleOptions("\nDoes the customer currently have an account with us? \n1. Yes. \n2. No. \n9. Exit.", new int[]{1, 2, 9});

        while (true)
        {
            switch (menu)
            {
                case 1 -> existingCustomersMenu();
                case 2 -> newCustomersMenu();
                case 9 -> exitProgram();
            }
        }
    }

    // Method existingCustomersMenu() which displays a menu to customers who already have an account registered.
    // Personal details are printed to the console in order to verify customer identity.
    // Finally, method calls the existingAccountsMenu() or createNewAccountMenu() methods depending on user input.
    public static void existingCustomersMenu() throws SQLException
    {
        Queries queries = new Queries();
        String accountNumber = checkAccountNumber();

        while (!queries.checkAccountExists(accountNumber))
        {
            System.out.println("No record of account. Try again.");
            accountNumber = checkAccountNumber();
        }

        ArrayList<String> customerInfo = Account.retrieveCustomerInfo(accountNumber);

        System.out.println("\nRetrieving account details...");
        System.out.println("\nName: " + customerInfo.get(0) + " " + customerInfo.get(1));
        System.out.println("D.O.B: " + customerInfo.get(2));

        boolean menu = checkTwoOptions("\nWould the customer like to access an existing account or open a new account? \n1. Access an existing account. \n2. Create a new account.");

        if (menu)
        {
            existingAccountsMenu(accountNumber);
        }
        else
        {
            createNewAccountMenu(Account.retrieveUserID(accountNumber));
        }
    }

    // Method newCustomersMenu() which displays a menu to new customers.
    // The method calls the createUser() method and passes its return value to one of the methods which creates a new account, depending on user input.
    public static void newCustomersMenu() throws SQLException
    {
        while (true)
        {
            System.out.println("\nOpen an account with ACME Banking Solutions...\n");
            int menu = checkMultipleOptions("1. Open a personal account. \n2. Open a business account. \n3. Open an ISA account. \n9. Exit.", new int[]{1, 2, 3, 8, 9});

            switch (menu)
            {
                case 1 -> openPersonalAccount(createUser());
                case 2 -> openBusinessAccount(createUser());
                case 3 -> openISAAccount(createUser());
                case 9 -> exitProgram();
            }
        }
    }

    // Method existingAccountsMenu() takes a String accountNumber as an argument and displays all other accounts associated with the same user.
    // The user is prompted to select an account to manage, with an Account object of the relevant type being created after user selection.
    // Finally, the accountMenu() method of the Account object is accessed.
    public static void existingAccountsMenu(String accountNumber) throws SQLException
    {
        Queries queries = new Queries();
        Scanner scanner = new Scanner(System.in);
        int numberOfAccounts = 0;
        int userInput = 0;

        System.out.println("\nSelect customer account: ");
        HashMap<String, String> accountList = Account.retrieveCustomerAccounts(accountNumber);

        for (HashMap.Entry<String,String> entry: accountList.entrySet())
        {
            numberOfAccounts++;
            System.out.println(numberOfAccounts + entry.getKey() + entry.getValue());
        }

        while (userInput < 1 || userInput > numberOfAccounts)
        {
            userInput = Integer.parseInt(scanner.nextLine());
        }

        String accountType = accountList.entrySet().toArray()[userInput - 1].toString().split("=")[1];

        switch (accountType)
        {
            case "Personal" ->
            {
                PersonalAccount personalAccount = queries.retrievePersonalAccount(accountList.entrySet().toArray()[userInput - 1].toString().split("=")[0]);
                personalAccount.accountMenu();
            }
            case "Business" ->
            {
                BusinessAccount businessAccount = queries.retrieveBusinessAccount(accountList.entrySet().toArray()[userInput - 1].toString().split("=")[0]);
                businessAccount.accountMenu();
            }
            case "ISA" ->
            {
                ISAAccount isaAccount = queries.retrieveISAAccount(accountList.entrySet().toArray()[userInput - 1].toString().split("=")[0]);
                isaAccount.accountMenu();
            }
        }
    }

    // Method createNewAccountMenu() takes an int userID as an argument and displays a menu.
    // The user is then prompted to choose which type of account to create.
    // Finally, the relevant method to open an account of the user's choosing is called, with the userID int passed as an argument.
    public static void createNewAccountMenu(int userID) throws SQLException
    {
        while (true)
        {
            int menu = checkMultipleOptions("\n1. Open a personal account. \n2. Open a business account. \n3. Open an ISA account. \n8. Back. \n9. Exit.", new int[] {1, 2, 3, 8, 9});

            switch(menu)
            {
                case 1 -> openPersonalAccount(userID);
                case 2 -> openBusinessAccount(userID);
                case 3 -> openISAAccount(userID);
                case 9 -> exitProgram();
            }
        }
    }

    // Method openPersonalAccount() takes an int userID as an argument and inserts a row into the Accounts0 table in the database.
    // The information entered into the table is dependent on the user's input and is linked to the userID passed to the method.
    // Finally, the PersonalAccount object's accountMenu() method is called.
    public static void openPersonalAccount(int userID) throws SQLException
    {
        Queries queries = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();
        checkCredential("\nDoes the customer have a valid personal ID? (Only driving licence or passport permitted.) \n1. Yes. \n2. No.", "Customer must have valid ID to open a personal account.");
        checkCredential("\nDoes the customer have a valid proof of address? (Utility bill, council letter, etc. permitted.) \n1. Yes. \n2. No.", "Customer must have a valid proof of address to open a personal account.");
        float openingBalance = checkFloatRange("\nEnter opening balance: ", 1.00f, 20000.00f);

        if (checkTwoOptions("\nConfirm account opening? \n 1. Yes \n 2. No"))
        {
            PersonalAccount personalAccount = queries.createPersonalAccount(generator.generateAccountNumber(), "02-12-20", userID, openingBalance, 0.00f);
            System.out.println("\nAccount creation successful.");
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
        Queries queries = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();
        String businessName = checkAlphabet("\nEnter business name: ");
        checkCredential("\nDoes the customer have valid business credentials? \n1. Yes. \n2. No.", "Customer must have valid business credentials to open a business account");
        checkCredential("\nDoes the customer have a valid business type? (No enterprises, public limited companies or charities are permitted.) \n1. Yes. \n2. No.", "Customer must have a valid business type to open a business account.");
        float openingBalance = checkFloatRange("\nEnter opening balance: ", 1.00f, 20000.00f);
        float overdraftAmount = checkFloatRange("\nEnter agreed overdraft amount: ", 0.00f, 10000.00f);

        if (checkTwoOptions("\nConfirm account opening? \n1. Yes. \n2. No"))
        {
            String accountNumber = generator.generateAccountNumber();
            BusinessAccount businessAccount = queries.createBusinessAccount(accountNumber, "02-12-20", userID, openingBalance, overdraftAmount, businessName);
            System.out.println("\nAccount creation successful.");
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
        Queries queries = new Queries();
        AccountNumberGeneration generator = new AccountNumberGeneration();
        checkCredential("\nDoes the customer have valid personal ID? (Only driving licence or passport permitted.) \n1. Yes. \n2. No.", "Customer must have valid ID to open an ISA account.");
        checkCredential("\nDoes the customer meet the age requirements for an ISA account? (16+) \n1. Yes. \n2. No.", "Customer must meet the age requirements to open an ISA account.");
        float openingBalance = checkFloatRange("\nEnter opening balance: ", 0.00f, 20000.00f);

        if (checkTwoOptions("\nConfirm account opening? \n1. Yes. \n2. No."))
        {
            ISAAccount isaAccount = queries.createISAAccount(generator.generateAccountNumber(), "02-12-20", userID, openingBalance, 0.00f);
            System.out.println("\nAccount creation successful.");
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
    public static int createUser()
    {
        Queries queries = new Queries();
        String firstName = checkAlphabet("\nEnter first name: ");
        String lastName = checkAlphabet("\nEnter last name: ");
        int birthDay = checkIntegerRange("\nEnter birth day: ", 1, 31);
        int birthMonth = checkIntegerRange("\nEnter birth month: ", 1, 12);
        int birthYear = checkIntegerRange("\nEnter birth year: ", 1900, 2007);
        String dateOfBirth = (birthYear + "-" + String.format("%02d", birthMonth) + "-" + String.format("%02d", birthDay));
        return queries.createUser(firstName, lastName, dateOfBirth);
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

        while (true)
        {
            int input = scanner.nextInt();

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
        System.out.println("\nSuccessfully logged out.");
        System.exit(0);
    }
}
