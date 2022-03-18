package com.techreturners.roman_numeral_converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class RomanNumeralConverterTest {

    private static final String NUMERALS_FILENAME = "numerals-1-to-3888.csv";

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
    public void checkCorrectNumerals1To38UpperCase() {
        String[] numerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV",
                             "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI",
                             "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI",
                             "XXXVII", "XXXVIII"};

        for (int i = 0; i < numerals.length; i++)
            assertEquals(i + 1, rmc.convertNumeral(numerals[i]));
    }

    @Test
    public void checkCorrectNumerals1To3888() {

        Scanner s = null;

        try {

            //load test resource
            URL url =  getClass().getClassLoader().getResource(NUMERALS_FILENAME);
            if (url == null)
                throw new FileNotFoundException();

            File numeralsFile = new File(url.getFile());
            s = new Scanner(numeralsFile);
            s.useDelimiter(",");

            //test every numeral in the test file ascending
            int count = 1;
            while (s.hasNext()) {
                assertEquals(count, rmc.convertNumeral(s.next())); //test resource contains numerals in ascending order from 1-3888
                count++;
            }

        } catch (FileNotFoundException e) {
            fail("Error loading test resource file: "+NUMERALS_FILENAME);
            e.printStackTrace();
        } finally {
            if(s != null)
                s.close();
        }
    }

    @Test
    public void checkCorrectNumerals1To10LowerCase() {
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

    @Test
    public void checkCorrectNumeralWithLeadingAndTrailingWhiteSpaces() {
        assertEquals(1, rmc.convertNumeral(" I"));
        assertEquals(10, rmc.convertNumeral("X "));
        assertEquals(5, rmc.convertNumeral(" V "));
        assertEquals(1, rmc.convertNumeral("     I"));
        assertEquals(1, rmc.convertNumeral(" I       "));
    }

    @Test
    public void checkNumeralsOutOfRange() {
        assertEquals(-1, rmc.convertNumeral("MMMM"));
        assertEquals(-1, rmc.convertNumeral("MMMDCCCLXXXVIIII"));
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
     * Correct roman numeral syntax does not allow V, L, D to be repeated in succession.
     * Source: https://www.math-only-math.com/rules-for-formation-of-roman-numerals.html
     */
    @Test
    public void checkRNSyntaxVOrLOrDNotRepeated() {
        assertEquals(-1, rmc.convertNumeral("VV"));
        assertEquals(-1, rmc.convertNumeral("VVV"));
        assertEquals(-1, rmc.convertNumeral("VVXX"));
        assertEquals(-1, rmc.convertNumeral("MDD"));
        assertEquals(-1, rmc.convertNumeral("CCLLLX"));
        assertEquals(-1, rmc.convertNumeral("LL"));
        assertEquals(-1, rmc.convertNumeral("MCCCLLXXVV"));
        assertEquals(-1, rmc.convertNumeral("XCLLL"));
    }

    /**
     * Correct roman numeral syntax does not allow I, X, C or M to be repeated more than 3 times in succession Source:
     * https://www.math-only-math.com/rules-for-formation-of-roman-numerals.html
     */
    @Test
    public void checkRNSyntaxNoMoreThanThreeRepeated() {
        assertEquals(-1, rmc.convertNumeral("XXXX"));
        assertEquals(-1, rmc.convertNumeral("IIIII"));
        assertEquals(-1, rmc.convertNumeral("XIIII"));
        assertEquals(-1, rmc.convertNumeral("MMMM"));
        assertEquals(-1, rmc.convertNumeral("CCCCCCCCCCCCCCC"));
        assertEquals(-1, rmc.convertNumeral("MCCCCCC"));
        assertEquals(-1, rmc.convertNumeral("CCCXXXX"));
        assertEquals(-1, rmc.convertNumeral("MDCLXXXVIIII"));
    }


    /**
     * V, L or D should never be subtracted (stand before a numeral of higher value)
     * Source: https://www.math-only-math.com/rules-for-formation-of-roman-numerals.html
     */
    @Test
    public void checkRNSyntaxVNotSubtracted() {
        assertEquals(-1, rmc.convertNumeral("VX"));
        assertEquals(-1, rmc.convertNumeral("VXX"));
        assertEquals(-1, rmc.convertNumeral("VIX"));
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
        assertEquals(-1, rmc.convertNumeral("CCM"));
        assertEquals(-1, rmc.convertNumeral("XXC"));
        assertEquals(-1, rmc.convertNumeral("XXL"));
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
        assertEquals(-1, rmc.convertNumeral("CMC"));
        assertEquals(-1, rmc.convertNumeral("XCXXX"));
        assertEquals(-1, rmc.convertNumeral("XLX"));
        assertEquals(-1, rmc.convertNumeral("CDCC"));
    }

    /**
     * A numeral of lower value, should not stand in front of a pair of numerals in subtractive notation.
     * E.g For VIX, V (5) stands before IX (9) so is invalid.
     * Source: https://www.numere-romane.ro/
     */
    @Test
    public void checkRNSyntaxCorrectSubtractiveNotation() {
        assertEquals(-1, rmc.convertNumeral("VIX"));
        assertEquals(-1, rmc.convertNumeral("VIIX"));
        assertEquals(-1, rmc.convertNumeral("VIXX"));
        assertEquals(-1, rmc.convertNumeral("XCM"));
        assertEquals(-1, rmc.convertNumeral("IXL"));
        assertEquals(-1, rmc.convertNumeral("CCM"));
        assertEquals(-1, rmc.convertNumeral("CCD"));
    }

    /**
     * After grouping any numerals in subtractive notation the numeral weights should be equal or sorted in descending
     * order for additive notation
     * E.g. IXX - (IX) is in subtractive notation, so the result is XI (9) then X (10) which is ascending. So this is
     * invalid
     * E.g. XIX - (IX) is in subtractive notation, so the result is X (10) then IX (9) which is descending. So this is
     * valid
     */
    @Test
    public void checkRNSyntaxCorrectAdditiveNotation() {
        assertEquals(-1, rmc.convertNumeral("IXX"));
        assertEquals(-1, rmc.convertNumeral("IXXX"));
        assertEquals(-1, rmc.convertNumeral("XIXX"));
        assertEquals(-1, rmc.convertNumeral("XXL"));
        assertEquals(-1, rmc.convertNumeral("IXC"));
        assertEquals(-1, rmc.convertNumeral("ICMM"));
    }

    /**
     * This test ensures for subtraction, a smaller numeral is only placed in front of the two numerals closest to it.
     * For example IV, or IX is valid, but IL, IC, IM are not.
     * Source: https://www.dictionary.com/e/roman-numerals/
     */
    @Test
    public void checkRNSyntaxSubtractiveSmallerNumeralInFrontOfTwoClosestNumerals() {
        assertEquals(-1, rmc.convertNumeral("IL"));
        assertEquals(-1, rmc.convertNumeral("IC"));
        assertEquals(-1, rmc.convertNumeral("ID"));
        assertEquals(-1, rmc.convertNumeral("IM"));
        assertEquals(-1, rmc.convertNumeral("XD"));
        assertEquals(-1, rmc.convertNumeral("XM"));

    }
}
