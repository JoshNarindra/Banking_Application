import java.util.Scanner;

public class ISAAccount extends Account {
    public ISAAccount(String accountNumber, String sortCode, float balance, float overdraft) {
        super(accountNumber, sortCode, balance, overdraft);
    }

        //Menu function for ISA account.
        @Override
        public void accountMenu() {
            System.out.println("\n What would the customer like to do? \n 1. Check Balance \n 2. Make a Deposit \n 3. Make a withdrawal \n 9. Exit");

            Scanner s1 = new Scanner(System.in);
            int menu = s1.nextInt();

            switch (menu) {
                case 1 -> System.out.println("Placeholder for get Balance");
                case 2 -> System.out.println("Placeholder for make deposit");
                case 3 -> System.out.println("Placeholder for Withdrawal");
                case 9 -> System.out.println("Placeholder Exit");
                default -> System.out.println("Invalid choice");

            }
        }
    }
