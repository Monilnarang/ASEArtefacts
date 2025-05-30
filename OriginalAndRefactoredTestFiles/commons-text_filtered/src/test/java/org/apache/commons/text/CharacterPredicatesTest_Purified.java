package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CharacterPredicatesTest_Purified {

    @Test
    public void testArabicNumerals_1() {
        assertTrue(CharacterPredicates.ARABIC_NUMERALS.test('0'));
    }

    @Test
    public void testArabicNumerals_2() {
        assertTrue(CharacterPredicates.ARABIC_NUMERALS.test('1'));
    }

    @Test
    public void testArabicNumerals_3() {
        assertTrue(CharacterPredicates.ARABIC_NUMERALS.test('9'));
    }

    @Test
    public void testArabicNumerals_4() {
        assertFalse(CharacterPredicates.ARABIC_NUMERALS.test('/'));
    }

    @Test
    public void testArabicNumerals_5() {
        assertFalse(CharacterPredicates.ARABIC_NUMERALS.test(':'));
    }

    @Test
    public void testArabicNumerals_6() {
        assertFalse(CharacterPredicates.ARABIC_NUMERALS.test('a'));
    }

    @Test
    public void testAsciiAlphaNumerals_1() {
        assertTrue(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('a'));
    }

    @Test
    public void testAsciiAlphaNumerals_2() {
        assertTrue(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('z'));
    }

    @Test
    public void testAsciiAlphaNumerals_3() {
        assertTrue(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('A'));
    }

    @Test
    public void testAsciiAlphaNumerals_4() {
        assertTrue(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('Z'));
    }

    @Test
    public void testAsciiAlphaNumerals_5() {
        assertTrue(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('0'));
    }

    @Test
    public void testAsciiAlphaNumerals_6() {
        assertTrue(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('9'));
    }

    @Test
    public void testAsciiAlphaNumerals_7() {
        assertFalse(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('`'));
    }

    @Test
    public void testAsciiAlphaNumerals_8() {
        assertFalse(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('{'));
    }

    @Test
    public void testAsciiAlphaNumerals_9() {
        assertFalse(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('@'));
    }

    @Test
    public void testAsciiAlphaNumerals_10() {
        assertFalse(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('['));
    }

    @Test
    public void testAsciiAlphaNumerals_11() {
        assertFalse(CharacterPredicates.ASCII_ALPHA_NUMERALS.test('/'));
    }

    @Test
    public void testAsciiAlphaNumerals_12() {
        assertFalse(CharacterPredicates.ASCII_ALPHA_NUMERALS.test(':'));
    }

    @Test
    public void testAsciiLetters_1() {
        assertTrue(CharacterPredicates.ASCII_LETTERS.test('a'));
    }

    @Test
    public void testAsciiLetters_2() {
        assertTrue(CharacterPredicates.ASCII_LETTERS.test('z'));
    }

    @Test
    public void testAsciiLetters_3() {
        assertTrue(CharacterPredicates.ASCII_LETTERS.test('A'));
    }

    @Test
    public void testAsciiLetters_4() {
        assertTrue(CharacterPredicates.ASCII_LETTERS.test('Z'));
    }

    @Test
    public void testAsciiLetters_5() {
        assertFalse(CharacterPredicates.ASCII_LETTERS.test('9'));
    }

    @Test
    public void testAsciiLetters_6() {
        assertFalse(CharacterPredicates.ASCII_LETTERS.test('`'));
    }

    @Test
    public void testAsciiLetters_7() {
        assertFalse(CharacterPredicates.ASCII_LETTERS.test('{'));
    }

    @Test
    public void testAsciiLetters_8() {
        assertFalse(CharacterPredicates.ASCII_LETTERS.test('@'));
    }

    @Test
    public void testAsciiLetters_9() {
        assertFalse(CharacterPredicates.ASCII_LETTERS.test('['));
    }

    @Test
    public void testAsciiLowercaseLetters_1() {
        assertTrue(CharacterPredicates.ASCII_LOWERCASE_LETTERS.test('a'));
    }

    @Test
    public void testAsciiLowercaseLetters_2() {
        assertTrue(CharacterPredicates.ASCII_LOWERCASE_LETTERS.test('z'));
    }

    @Test
    public void testAsciiLowercaseLetters_3() {
        assertFalse(CharacterPredicates.ASCII_LOWERCASE_LETTERS.test('9'));
    }

    @Test
    public void testAsciiLowercaseLetters_4() {
        assertFalse(CharacterPredicates.ASCII_LOWERCASE_LETTERS.test('A'));
    }

    @Test
    public void testAsciiLowercaseLetters_5() {
        assertFalse(CharacterPredicates.ASCII_LOWERCASE_LETTERS.test('Z'));
    }

    @Test
    public void testAsciiLowercaseLetters_6() {
        assertFalse(CharacterPredicates.ASCII_LOWERCASE_LETTERS.test('`'));
    }

    @Test
    public void testAsciiLowercaseLetters_7() {
        assertFalse(CharacterPredicates.ASCII_LOWERCASE_LETTERS.test('{'));
    }

    @Test
    public void testAsciiUppercaseLetters_1() {
        assertTrue(CharacterPredicates.ASCII_UPPERCASE_LETTERS.test('A'));
    }

    @Test
    public void testAsciiUppercaseLetters_2() {
        assertTrue(CharacterPredicates.ASCII_UPPERCASE_LETTERS.test('Z'));
    }

    @Test
    public void testAsciiUppercaseLetters_3() {
        assertFalse(CharacterPredicates.ASCII_UPPERCASE_LETTERS.test('9'));
    }

    @Test
    public void testAsciiUppercaseLetters_4() {
        assertFalse(CharacterPredicates.ASCII_UPPERCASE_LETTERS.test('@'));
    }

    @Test
    public void testAsciiUppercaseLetters_5() {
        assertFalse(CharacterPredicates.ASCII_UPPERCASE_LETTERS.test('['));
    }

    @Test
    public void testAsciiUppercaseLetters_6() {
        assertFalse(CharacterPredicates.ASCII_UPPERCASE_LETTERS.test('a'));
    }

    @Test
    public void testAsciiUppercaseLetters_7() {
        assertFalse(CharacterPredicates.ASCII_UPPERCASE_LETTERS.test('z'));
    }

    @Test
    public void testDigits_1() {
        assertTrue(CharacterPredicates.DIGITS.test('0'));
    }

    @Test
    public void testDigits_2() {
        assertTrue(CharacterPredicates.DIGITS.test('9'));
    }

    @Test
    public void testDigits_3() {
        assertFalse(CharacterPredicates.DIGITS.test('-'));
    }

    @Test
    public void testDigits_4() {
        assertFalse(CharacterPredicates.DIGITS.test('.'));
    }

    @Test
    public void testDigits_5() {
        assertFalse(CharacterPredicates.DIGITS.test('L'));
    }

    @Test
    public void testLetters_1() {
        assertTrue(CharacterPredicates.LETTERS.test('a'));
    }

    @Test
    public void testLetters_2() {
        assertTrue(CharacterPredicates.LETTERS.test('Z'));
    }

    @Test
    public void testLetters_3() {
        assertFalse(CharacterPredicates.LETTERS.test('1'));
    }

    @Test
    public void testLetters_4() {
        assertFalse(CharacterPredicates.LETTERS.test('?'));
    }

    @Test
    public void testLetters_5() {
        assertFalse(CharacterPredicates.LETTERS.test('@'));
    }
}
