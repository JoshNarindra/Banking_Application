/*
Main Program Class.
 */

//imports
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        accountExists();
    }

    public static void accountExists(){
        //Initial Menu.
        System.out.println("Welcome to ACME Banking Solutions...");
        System.out.println("Does the customer currently have an account with us? \n 1. Yes. \n 2. No.");

        //Scanner to read bank tellers input for user choice.
        Scanner s1 = new Scanner(System.in);
        int menu = s1.nextInt();

        //Switch-Case statement for initial menu.
        switch (menu) {
            case 1 -> customerInfo();
            case 2 -> opennewAccount();
            default -> System.out.println("Invalid choice");
        }
    }

    public static void customerInfo(){
        //Input account number.
        System.out.println("Enter account number:");
        Scanner s2 = new Scanner(System.in);
        String accountNumber = s2.nextLine();
        System.out.println(accountNumber);

        //Retrieve account details.
        System.out.println("Retrieving account details...");

        //Display customer info.
        System.out.println("Name: ");
        System.out.println("D.O.B: ");

        //Display customers accounts with bank.
        System.out.println("Customer Accounts:");

        //Option to allow current customer to open new account.

    }

    public static void opennewAccount(){
        //Menu to create an account for new customer.
        System.out.println("Open an account with ACME Banking");
        System.out.println(" 1. Open a personal account. \n 2. Open a business account. \n 3. Open a ISA Account2");

        //Scanner to read bank tellers input for user menu choice.
        Scanner s3 = new Scanner(System.in);
        int menu = s3.nextInt();

        //Switch-Case statement for initial menu.
        switch (menu) {
            case 1 -> System.out.println("Placeholder for personal account creation");
            case 2 -> System.out.println("Placeholder for business account creation");
            case 3 -> System.out.println("Placeholder for ISA account creation");
        }
    }


}
