package com.techreturners.roman_numeral_converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumeralConverterTest {

    private RomanNumeralConverter rmc;

    @BeforeEach
    public void setup() {
        rmc = new RomanNumeralConverter();
    }

    @Test
    public void checkEmpty() {
        assertEquals(-1, rmc.convertNumeral(""));
    }

    @Test
    public void checkIUpperCase() {
        assertEquals(1, rmc.convertNumeral("I"));
    }

    @Test
    public void checkILowerCase() {
        assertEquals(1, rmc.convertNumeral("i"));
    }

    @Test
    public void checkNumerals1To38UpperCase() {
        String[] numerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV",
                             "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI",
                             "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI",
                             "XXXVII", "XXXVIII"};

        for (int i = 0; i < numerals.length; i++)
            assertEquals(i + 1, rmc.convertNumeral(numerals[i]));
    }

    @Test
    public void checkNumerals1To10LowerCase() {
        String[] numerals = {"i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x"};

        for (int i = 0; i < numerals.length; i++)
            assertEquals(i + 1, rmc.convertNumeral(numerals[i]));
    }

    @Test
    public void checkNumerals1To10Mixed() {
        String[] numerals = {"i", "iI", "iiI", "Iv", "V", "vI", "vIi", "vIiI", "iX", "x"};

        for (int i = 0; i < numerals.length; i++)
            assertEquals(i + 1, rmc.convertNumeral(numerals[i]));
    }

    //todo check 1- 38

    @Test
    public void checkNumeralsOutOfRange() {
        assertEquals(-1, rmc.convertNumeral("L"));
    }

    @Test
    public void checkStringWithMultipleNumerals() {
        //program doesn't accept a list of numerals
        assertEquals(-1, rmc.convertNumeral("I II III IV V VI VII VIII IX X"));
    }

    @Test
    public void checkNonNumeral() {
        assertEquals(-1, rmc.convertNumeral("A"));
    }

    @Test
    public void checkString() {
        assertEquals(-1, rmc.convertNumeral("the quick brown fox jumps over the lazy dog"));
    }

    @Test
    public void checkStringStartingWithNumeral() {
        assertEquals(-1, rmc.convertNumeral("I the quick brown fox jumps over the lazy dog"));
    }

    @Test
    public void checkStringEndingWithNumeral() {
        assertEquals(-1, rmc.convertNumeral("the quick brown fox jumps over the lazy dog X"));
    }

    /**
     * Correct roman numeral syntax does not allow V to be repeated.
     * Source: https://www.math-only-math.com/rules-for-formation-of-roman-numerals.html
     */
    @Test
    public void checkRNSyntaxVNotRepeated() {
        assertEquals(-1, rmc.convertNumeral("VV"));
        assertEquals(-1, rmc.convertNumeral("VVV"));
        assertEquals(-1, rmc.convertNumeral("VVXX"));
        assertEquals(-1, rmc.convertNumeral("XXXVV"));
    }

    /**
     * Correct roman numeral syntax does not allow I or X to be repeated more than 3 times in succession Source:
     * https://www.math-only-math.com/rules-for-formation-of-roman-numerals.html
     */
    @Test
    public void checkRNSyntaxNoMoreThanThreeRepeated() {
        assertEquals(-1, rmc.convertNumeral("XXXX"));
        assertEquals(-1, rmc.convertNumeral("IIIII"));
        assertEquals(-1, rmc.convertNumeral("XIIII"));
        assertEquals(-1, rmc.convertNumeral("IIIIIIX"));
    }


    /**
     * Correct roman numeral syntax only allows one smaller number in front of a larger one Source:
     * https://www.dictionary.com/e/roman-numerals/
     */
    @Test
    public void checkRNSyntaxOnlyOneSmallerNumeralInFrontOfALarger() {
        assertEquals(-1, rmc.convertNumeral("IIX"));
        assertEquals(-1, rmc.convertNumeral("IIIX"));
        assertEquals(-1, rmc.convertNumeral("IIV"));
        assertEquals(-1, rmc.convertNumeral("IIIV"));
        assertEquals(-1, rmc.convertNumeral("VVX"));
    }

    /**
     * The same numeral should not be added and subtracted.
     * E.g. IXI would equal 10, however the correct notation for 10
     * is X.
     */
    @Test
    public void checkRNSyntaxSameNumberSubtractedAndAdded() {
        assertEquals(-1, rmc.convertNumeral("IVI"));
        assertEquals(-1, rmc.convertNumeral("IXI"));
        assertEquals(-1, rmc.convertNumeral("IVII"));
        assertEquals(-1, rmc.convertNumeral("IXII"));
    }

    /**
     * V should never stand before a numeral of higher value
     * Source: https://www.math-only-math.com/rules-for-formation-of-roman-numerals.html
     */
    @Test
    public void checkRNSyntaxVNotSubtracted() {
        assertEquals(-1, rmc.convertNumeral("VX"));
        assertEquals(-1, rmc.convertNumeral("VXX"));
        assertEquals(-1, rmc.convertNumeral("VIX"));
    }

    /**
     * A numeral of lower value, should not stand in front of a pair of numerals in subtractive notation E.g For VIX, V
     * (5) stands before IX (9) so is invalid.
     * Source: https://www.numere-romane.ro/
     */
    @Test
    public void checkRNSyntaxCorrectSubtractiveNotation() {
        assertEquals(-1, rmc.convertNumeral("VIX"));
        assertEquals(-1, "VIIX");
        assertEquals(-1, rmc.convertNumeral("VIXX"));
    }

    /**
     * This test ensures for subtraction, a smaller numeral is only placed in front of the two numerals closest to it.
     * For example IV, or IX is valid, but IL, IC, IM are not.
     * Source: https://www.dictionary.com/e/roman-numerals/
     */
    @Test
    @Disabled("Disabled until larger numerals are introduced as there is no way to test this currently")
    public void checkRNSyntaxSubtractiveSmallerNumeralInFrontOfTwoClosestNumerals() {

    }
}
