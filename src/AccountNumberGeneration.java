/*
Class AccountNumberGenerator generates an 8 digit UK Format Account Number.

The

 */

//Imports
import java.util.ArrayList;
import java.util.Random;

public class AccountNumberGeneration
{
    //Method creates a string consisting of a concatenation of 8 random digits.
    public String generateAccountNumber()
    {
        // First generate random 8-digit number
        Random rand = new Random();
        ArrayList<String> accountNumberArray = new ArrayList<String>();
        String generatedAccountNumber = "";

        for (int i=0; i<8; i++){
            //Generate random digit.
            int randomDigit = rand.nextInt(10);

            //Add each digit to Arraylist
            accountNumberArray.add(Integer.toString(randomDigit));
        }

        for (String digit: accountNumberArray) {
            generatedAccountNumber = generatedAccountNumber+digit;
        }

        System.out.println(generatedAccountNumber);

        // Then query the database for an account of that number
        DatabaseConnection connection = new DatabaseConnection();

        //String query = "select COUNT(1) from Accounts where AccountNumber = '" + generatedAccountNumber + "';";
        String query = "select COUNT(1) from Accounts where AccountNumber = '80000001';";

        try {
            var stmt = connection.getConnection().prepareStatement(query);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    System.out.println("Account Number already exists in database");
                } else {
                    System.out.println("Account Number does not exist in database.");
                }
            }
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        // If no such account exists then it's cool
        // If the account exists then generate another random 8-digit (could be done using while loop)


        return "placeholder";
    }
}
