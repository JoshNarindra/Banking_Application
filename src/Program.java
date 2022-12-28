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
        //Initial Menu
        System.out.println("Welcome to ACME banking solutions...");
        System.out.println("Does the customer currently have an account with us? \n 1. Yes. \n 2. No.");

        Scanner s1 = new Scanner(System.in);
        int menu = s1.nextInt();

        switch (menu) {
            case 1 -> customerInfo();
            case 2 -> createAccount();
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


    }

    public static void createAccount(){

    }


}
