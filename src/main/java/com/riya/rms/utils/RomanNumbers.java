package com.riya.rms.utils;

public class RomanNumbers {
    private static final String[] ROMANS = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII",
            "IX", "X", "XI", "XII"
    };

    public static String toRoman(int number) {
        if (number >= 1 && number <= ROMANS.length) {
            return ROMANS[number - 1];
        }
        return String.valueOf(number);
    }
}
