package com.techreturners.roman_numeral_converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumeralConverterTest  {

    private RomanNumeralConverter rmc;

    @BeforeEach
    public void setup(){
        rmc = new RomanNumeralConverter();
    }

    @Test
    public void checkEmpty(){
        assertEquals(-1, rmc.convertNumeral(""));
    }

    @Test
    public void checkIUpperCase(){
        assertEquals(1,rmc.convertNumeral("I"));
    }

    @Test
    public void checkILowerCase(){
        assertEquals(1,rmc.convertNumeral("i"));
    }

    @Test
    public void checkNumerals1To10UpperCase(){
        String[] numerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

        for(int i = 0; i < numerals.length; i++)
            assertEquals(i+1, rmc.convertNumeral(numerals[i]));
    }

    @Test
    public void checkNumerals1To10LowerCase(){
        String[] numerals = {"i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x"};

        for(int i = 0; i < numerals.length; i++)
            assertEquals(i+1, rmc.convertNumeral(numerals[i]));
    }

    @Test
    @Disabled("Disabled until expected behaviour is decided")
    public void checkNumerals1To10Mixed(){
       //todo: what behaviour to expect when a numeral contains a mixture of uppercase and lowercase characters?
    }

    @Test
    public void checkNumeralsOutOfRange(){
        assertEquals(-1,rmc.convertNumeral("XX"));
    }

    @Test
    public void checkStringWithMultipleNumerals(){
        //program doesn't accept a list of numerals
        assertEquals(-1,rmc.convertNumeral("I II III IV V VI VII VIII IX X"));
    }

    @Test
    public void checkNonNumeral(){
        assertEquals(-1, rmc.convertNumeral("A"));
    }

    @Test
    public void checkString(){
        assertEquals(-1, rmc.convertNumeral("the quick brown fox jumps over the lazy dog"));
    }

    @Test
    public void checkStringStartingWithNumeral(){
        assertEquals(-1, rmc.convertNumeral("I the quick brown fox jumps over the lazy dog"));
    }

    @Test
    public void checkStringEndingWithNumeral(){
        assertEquals(-1, rmc.convertNumeral("the quick brown fox jumps over the lazy dog X"));
    }


}
