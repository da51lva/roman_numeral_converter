package com.techreturners.roman_numeral_converter;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RomanNumeralConverter {

    private static final Pattern NUMERAL_PATTERN = Pattern.compile("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    private static final Map<Character, Integer> basicSymbolValues = Map.of('I', 1, 'V', 5, 'X', 10, 'L', 50, 'C', 100, 'D', 500, 'M', 1000); //map containing the basic numeral symbols anb their values

    private static final String CONSOLE_MESSAGE = "Please enter a Roman Numeral to convert. Enter 'q' to quit the application";

    public static void main(String[] args) {

        String input = "";
        Scanner s = new Scanner(System.in);
        ;
        System.out.println(CONSOLE_MESSAGE);

        while (!(input = s.next()).equals("q")) {

            RomanNumeralConverter rmc = new RomanNumeralConverter();
            int output = rmc.convertNumeral(input);
            if (output == -1)
                System.out.println("Invalid input: Please enter a single Roman Numeral only containing the symbols 'I', 'V' and 'X'");
            else
                System.out.println(rmc.convertNumeral(input));

            System.out.println(CONSOLE_MESSAGE);
        }

        s.close();

    }

    public int convertNumeral(String numeral) {
        String cleanInput = numeral.toUpperCase().trim();
        if (validInput(cleanInput))
            return convertNumeralHelper(cleanInput);
        else
            return -1;
    }

    /**
     * Converts a Roman numeral to number. Assumes a correct input
     *
     * @param numeral
     * @return
     */
    private int convertNumeralHelper(String numeral) {

        char[] symbols = numeral.toCharArray();
        int calculation = basicSymbolValues.get(symbols[symbols.length - 1]);

        //iterate over the numeral backwards and cumulatively add or the value of the symbol that precedes it (not going backwards)
        for (int i = symbols.length - 1; i > 0; i--) {

            char currentsSymbol = symbols[i];
            char precedingSymbol = symbols[i - 1];
            int precedingSymbolValue = basicSymbolValues.get(precedingSymbol);
            int currentSymbolValue = basicSymbolValues.get(currentsSymbol);

            if (precedingSymbolValue >= currentSymbolValue)
                calculation += precedingSymbolValue;
            else
                calculation -= precedingSymbolValue;
        }

        return calculation;
    }

    /**
     * Checks whether an input string is valid. Only a single Roman Numeral is considered valid
     *
     * @param input
     * @return
     */
    private boolean validInput(String input) {
        if (input.isEmpty()) { //regex will match empty string
            return false;
        } else {
            Matcher matcher = NUMERAL_PATTERN.matcher(input);
            return matcher.find();
        }
    }


}
