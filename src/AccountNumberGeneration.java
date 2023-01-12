/*
    Class AccountNumberGeneration is responsible for generating account numbers for new accounts registered.

    The class has two methods:
        generateAccountNumber(),
        generateEightDigitString().
 */

import java.util.ArrayList;
import java.util.Random;

public class AccountNumberGeneration
{
    // Method generateAccountNumber() calls the generateEightDigitString() method to create a String newAccountNumber.
    // The method then calls queries.checkAccountExists() to verify that the newAccountNumber does not belong to an account already.
    // newAccountNumber is then returned if it is free, or regenerated and returned if it is in use.
    public String generateAccountNumber()
    {
        Queries queries = new Queries();
        String newAccountNumber = generateEightDigitString();

        while (queries.checkAccountExists(newAccountNumber))
        {
            newAccountNumber = generateEightDigitString();
        }

        return newAccountNumber;
    }

    // Method generateEightDigitString() returns a String of 8 random digits.
    // Digits are randomly generated and added to an ArrayList, with the contents of the ArrayList then concatenated into a single String.
    // The single String is then returned.
    public String generateEightDigitString()
    {
        Random rand = new Random();
        ArrayList<String> accountNumberArray = new ArrayList<>();
        StringBuilder generatedAccountNumber = new StringBuilder();

        for (int i = 0; i < 8; i++)
        {
            int randomDigit = rand.nextInt(10);
            accountNumberArray.add(Integer.toString(randomDigit));
        }

        for (String digit: accountNumberArray)
        {
            generatedAccountNumber.append(digit);
        }

        return generatedAccountNumber.toString();
    }
}
