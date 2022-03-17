package com.techreturners.roman_numeral_converter;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RomanNumeralConverter {

    private static final Pattern NUMERAL_PATTERN = Pattern.compile("^[IVX]+$");
    private static final Map<Character, Integer> basicSymbolValues = Map.of('I', 1, 'V', 5, 'X', 10); //map containing the basic numeral symbols anb their values

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
        Matcher matcher = NUMERAL_PATTERN.matcher(input);
        return matcher.find();
    }


}
