import java.util.Scanner;

public class PersonalAccount extends Account{

    private Boolean validPersonalID;
    private Boolean validAddressID;

    public PersonalAccount(String accountNumber, String sortCode, float balance, Boolean validPersonalID, Boolean validAddressID){
        super(accountNumber, sortCode, balance);
        this.validPersonalID = validPersonalID;
        this.validAddressID = validAddressID;
    }

    //Method to create a personal account.
    @Override
    public void createAccount(){

    }

    public void accountMenu() {
        System.out.println("What would the customer like to do? \n 1. Check Balance \n 2. Make a Deposit \n 3. Make a withdrawal \n 4.Set up Direct Debit \n 5. Set Up Standing Order \n 6. Exit");

        Scanner s1 = new Scanner(System.in);
        int menu = s1.nextInt();

        switch (menu) {
            case 1 -> System.out.println("Placeholder for get Balance");
            case 2 -> System.out.println("Placeholder for make deposit");
            case 3 -> System.out.println("Placeholder for Withdrawal");
            case 4 -> System.out.println("Placeholder Direct Debit");
            case 5 -> System.out.println("Placeholder Standing order");
            case 6 -> System.out.println("Placeholder Exit");
            default -> System.out.println("Invalid choice");
        }
    }
}
