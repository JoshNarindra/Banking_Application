/*
Abstract class Account.
 */
abstract class Account {
    private String accountNumber;
    private String sortCode;
    private float balance;

    public Account(String accountNumber, String sortCode, float balance) {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.balance = balance;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public String getSortCode(){
        return sortCode;
    }

    public float getBalance(){
        return balance;
    }

    public void setBalance(float balance){
        this.balance = balance;
    }

    // Function deposit which calls getBalance and setBalance to increment balance
    public void deposit(float increment) {
        float newBalance = getBalance() + increment;
        setBalance(newBalance);
    }

    // Function withdraw which calls getBalance and setBalance to decrement balance
    public void withdraw(float decrement) {
        float newBalance = getBalance() - decrement;
        setBalance(newBalance);
    }

    // Function transfer which takes two accounts and an amount as an argument and transfers money between the two
    public void transfer(float amount, Account payee, Account recipient)
    {

    }

    // Abstract method to display menu system for account.
    abstract void accountMenu();


    //Abstract methods for creating accounts should use "override" in child classes for each type of account.
    // (Note Are these methods abstract as a result of class being abstract?)
    public void generateAccountNumber(){}

    public void generateSortCode(){}

    //accidental duplicate
    public void createAccount(){}
}
