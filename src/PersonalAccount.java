public class PersonalAccount extends Account{
    public PersonalAccount(String accountNumber, String sortCode, float balance){
        super(accountNumber, sortCode, balance);
    }

    //Method to create a personal account.
    @Override
    public void createAccount(){

    }

    public void accountMenu() {
        System.out.println("What would the customer like to do? \n 1. Make a Deposit \n 2. Make a withdrawal \n 3. ");
    }
}
