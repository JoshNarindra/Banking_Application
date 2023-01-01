/*
Main Program Class.
 */

//imports
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Program {

    static String website = "jdbc:sqlserver://SQL8002.site4now.net;database=db_a8cc79_Ewahes";
    static String username = "db_a8cc79_Ewahes_admin";
    static String password = "Fr43yX52kE71";
    static String sql = "select * from Users";

    public static void main(String[] args) {
        accountExists();
    }

    public static void accountExists(){

        //Initial Menu.
        System.out.println("Welcome to ACME Banking Solutions...");
        System.out.println("\n Does the customer currently have an account with us? \n 1. Yes. \n 2. No. \n 9. Exit.");

        //Scanner to read bank tellers input for user choice.
        Scanner s1 = new Scanner(System.in);
        int menu = s1.nextInt();

        //Re-prompting the teller for input until either 1 or 2 is inputted.
        while (menu != 1 && menu != 2 && menu != 9){
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

    public static void customerInfo(){

        //Input account number with same means as above.
        System.out.println("\n Enter account number:");
        Scanner s2 = new Scanner(System.in);
        String accountNumber = s2.nextLine();

        //Re-prompting the teller for input until an account number of correct length is inputted.
        while (accountNumber.length() != 8)
        {
            System.out.println("\n Invalid input. Try again.");
            accountNumber = s2.nextLine();
        }
        System.out.println(accountNumber);

        //Retrieve account details.
        System.out.println("\n Retrieving account details...");

        //Display customer info.
        System.out.println("\n Name: ");
        System.out.println("\n D.O.B: ");

        //Display customers accounts with bank.
        System.out.println("\n Customer Accounts: ");

        //Option to allow current customer to open new account.
        PersonalAccount x = new PersonalAccount("12312434","090109", 45.45f,true,true);
        x.accountMenu();
    }

    public static void openNewAccount(){

        //Menu to create an account for new customer.
        System.out.println("Open an account with ACME Banking");
        System.out.println(" 1. Open a personal account. \n 2. Open a business account. \n 3. Open an ISA Account. \n 9. Exit.");

        //Scanner to read bank tellers input for user menu choice.
        Scanner s3 = new Scanner(System.in);
        int menu = s3.nextInt();

        //Re-prompting the teller for input until either 1, 2 or 3 is inputted.
        while (menu != 1 && menu != 2 && menu != 3 && menu != 9)
        {
            System.out.println("Invalid input. Try again.");
            menu = s3.nextInt();
        }

        //Switch-Case statement for initial menu.
        switch (menu) {
            case 1 -> System.out.println("Placeholder for personal account creation");
            case 2 -> System.out.println("Placeholder for business account creation");
            case 3 -> System.out.println("Placeholder for ISA account creation");
            case 9 -> exitProgram();
        }
    }

    public static void exitProgram(){
        System.out.println("Successfully logged out.");
        System.exit(0);
    }


}
