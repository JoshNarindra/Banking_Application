/*
Class AccountNumberGenerator generates an 8 digit UK Format Account Number.

The

 */

//Imports
import java.util.ArrayList;
import java.util.Random;

public class AccountNumberGeneration
{
    //Generate Account number.
    public String generateAccountNumber()
    {
        String newAccountNumber = generateEightDigitString();
        Queries q = new Queries();

        while (q.checkAccountExists(newAccountNumber))
        {
            newAccountNumber = generateEightDigitString();
        }

        return newAccountNumber;
    }

    //Method generate a string consisting of a concatenation of 8 random digits.
    public String generateEightDigitString()
    {
        Random rand = new Random();
        ArrayList<String> accountNumberArray = new ArrayList<String>();
        String generatedAccountNumber = "";

        //Generate random 8 digit array.
        for (int i=0; i<8; i++)
        {
            int randomDigit = rand.nextInt(10); //Generate random digit.
            accountNumberArray.add(Integer.toString(randomDigit)); //Add each digit to Arraylist.
        }

        //Generate 8 digit String.
        for (String digit: accountNumberArray)
        {
            generatedAccountNumber = generatedAccountNumber+digit;
        }

        return generatedAccountNumber;
    }
}
