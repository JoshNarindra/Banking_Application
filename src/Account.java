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
}
