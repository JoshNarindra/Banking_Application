import java.util.ArrayList;
import java.util.List;

public class Variables
{
    public static float personalAccountMinimumOpeningBalance = 1f;
    public static float personalAccountMaximumOpeningBalance = 20000f;
    public static float personalAccountDefaultOverdraft = 0f;
    public static float personalAccountMinimumDeposit = 1f;
    public static float personalAccountMaximumDeposit = 20000f;
    public static float personalAccountMinimumWithdrawal = 1f;
    public static float personalAccountMaximumWithdrawal = 5000f;
    public static float personalAccountMinimumPayment = 0.01f;
    public static float personalAccountMaximumPayment = 100000f;

    public static float businessAccountMinimumOpeningBalance = 1f;
    public static float businessAccountMaximumOpeningBalance = 50000f;
    public static float businessAccountMinimumOverdraft = 0f;
    public static float businessAccountMaximumOverdraft = 10000f;
    public static float businessAccountMinimumDeposit = 1f;
    public static float businessAccountMaximumDeposit = 50000f;
    public static float businessAccountMinimumWithdrawal = 1f;
    public static float businessAccountMaximumWithdrawal = 20000f;
    public static float businessAccountMinimumPayment = 0.01f;
    public static float businessAccountMaximumPayment = 1000000f;
    public static float businessAccountMinimumLoanRequest = 100f;
    public static float businessAccountMaximumLoanRequest = 10000f;
    public static float businessAccountAnnualPaymentAmount = 120f;

    public static float isaAccountMinimumOpeningBalance = 1f;
    public static float isaAccountMaximumOpeningBalance = 20000f;
    public static float isaAccountMaximumBalance = 20000f;
    public static float isaAccountDefaultOverdraft = 0f;
    public static float isaAccountMinimumDeposit = 1f;
    public static float isaAccountMaximumDeposit = 20000f;
    public static float isaAccountMinimumWithdrawal = 1f;
    public static float isaAccountMaximumWithdrawal = 20000f;
    public static float isaAccountMinimumPayment = 0.01f;
    public static float isaAccountMaximumPayment = 20000f;
    public static float isaAccountYearlyInterestFactor = 1.03f;

    public static int earliestBirthYear = 1900;
    public static int latestBirthYear = 2007;

    public static String branchSortCode = "02-12-20";
    public static ArrayList<String> accountsColumns = new ArrayList<>(List.of("AccountNumber", "SortCode", "UserID", "AccountType", "Balance", "Overdraft"));
    public static ArrayList<String> usersColumns = new ArrayList<>(List.of("ID", "FirstName", "LastName", "DateOfBirth"));
    public static ArrayList<String> businessesColumns = new ArrayList<>(List.of("ID", "Name", "AccountNumber"));

    public static String getServerURL()
    {
        return "jdbc:sqlserver://SQL8002.site4now.net;database=db_a8cc79_Ewahes";
    }

    public static String getServerUsername()
    {
        return "db_a8cc79_Ewahes_admin";
    }

    public static String getServerPassword()
    {
        return "Fr43yX52kE71";
    }
}
