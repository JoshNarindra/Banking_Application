import java.util.Scanner;

public class BusinessAccount extends Account {
    public BusinessAccount(String accountNumber, String sortCode, float balance) {
        super(accountNumber, sortCode, balance);
    }

    //Menu function for business account.
    @Override
    public void accountMenu() {
        System.out.println("\n What would the customer like to do? \n 1. Check Balance \n 2. Make a Deposit \n 3. Make a withdrawal \n 4. Issue a check \n 9. Exit");

        Scanner s1 = new Scanner(System.in);
        int menu = s1.nextInt();

        switch (menu) {
            case 1 -> System.out.println("Placeholder for get Balance");
            case 2 -> System.out.println("Placeholder for make deposit");
            case 3 -> System.out.println("Placeholder for Withdrawal");
            case 4 -> System.out.println("Placeholder Issue a check");
            case 5 -> System.out.println("Placeholder Pay account annual charge");
            case 9 -> System.out.println("Placeholder Exit");
            default -> System.out.println("Invalid choice");

        }
    }
}
