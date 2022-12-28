/*
Abstract class Account.
 */
abstract class Account {
    private String accountNumber;
    private String sortCode;
    private float balance;

    public String getAccountNumber(){
        return accountNumber;
    }

    public String getSortCode(){
        return sortCode;
    }

    public float getBalance(){
        return getBalance();
    }

    public void setBalance(float balance){
        this.balance = balance;
    }

    //Abstract methods for creating accounts should use "override" in child classes for each type of account.
    // (Note Are these methods abstract as a result of class being abstract?)
    public void generateAccountNumber(){}

    public void generateSortCode(){}

    public void createAccount(){}
}
