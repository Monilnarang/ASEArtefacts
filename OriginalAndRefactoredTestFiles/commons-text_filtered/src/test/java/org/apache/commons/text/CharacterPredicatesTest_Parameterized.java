package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CharacterPredicatesTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testArabicNumerals_1to3")
    public void testArabicNumerals_1to3(int param1) {
        assertTrue(CharacterPredicates.ARABIC_NUMERALS.test(param1));
    }

    static public Stream<Arguments> Provider_testArabicNumerals_1to3() {
        return Stream.of(arguments(0), arguments(1), arguments(9));
    }

    @ParameterizedTest
    @MethodSource("Provider_testArabicNumerals_4to6")
    public void testArabicNumerals_4to6(String param1) {
        assertFalse(CharacterPredicates.ARABIC_NUMERALS.test(param1));
    }

    static public Stream<Arguments> Provider_testArabicNumerals_4to6() {
        return Stream.of(arguments("/"), arguments(":"), arguments("a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAsciiAlphaNumerals_1to6")
    public void testAsciiAlphaNumerals_1to6(String param1) {
        assertTrue(CharacterPredicates.ASCII_ALPHA_NUMERALS.test(param1));
    }

    static public Stream<Arguments> Provider_testAsciiAlphaNumerals_1to6() {
        return Stream.of(arguments("a"), arguments("z"), arguments("A"), arguments("Z"), arguments(0), arguments(9));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAsciiAlphaNumerals_7to12")
    public void testAsciiAlphaNumerals_7to12(String param1) {
        assertFalse(CharacterPredicates.ASCII_ALPHA_NUMERALS.test(param1));
    }

    static public Stream<Arguments> Provider_testAsciiAlphaNumerals_7to12() {
        return Stream.of(arguments("`"), arguments("{"), arguments("@"), arguments("["), arguments("/"), arguments(":"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAsciiLetters_1to4")
    public void testAsciiLetters_1to4(String param1) {
        assertTrue(CharacterPredicates.ASCII_LETTERS.test(param1));
    }

    static public Stream<Arguments> Provider_testAsciiLetters_1to4() {
        return Stream.of(arguments("a"), arguments("z"), arguments("A"), arguments("Z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAsciiLetters_5to9")
    public void testAsciiLetters_5to9(int param1) {
        assertFalse(CharacterPredicates.ASCII_LETTERS.test(param1));
    }

    static public Stream<Arguments> Provider_testAsciiLetters_5to9() {
        return Stream.of(arguments(9), arguments("`"), arguments("{"), arguments("@"), arguments("["));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAsciiLowercaseLetters_1to2")
    public void testAsciiLowercaseLetters_1to2(String param1) {
        assertTrue(CharacterPredicates.ASCII_LOWERCASE_LETTERS.test(param1));
    }

    static public Stream<Arguments> Provider_testAsciiLowercaseLetters_1to2() {
        return Stream.of(arguments("a"), arguments("z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAsciiLowercaseLetters_3to7")
    public void testAsciiLowercaseLetters_3to7(int param1) {
        assertFalse(CharacterPredicates.ASCII_LOWERCASE_LETTERS.test(param1));
    }

    static public Stream<Arguments> Provider_testAsciiLowercaseLetters_3to7() {
        return Stream.of(arguments(9), arguments("A"), arguments("Z"), arguments("`"), arguments("{"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAsciiUppercaseLetters_1to2")
    public void testAsciiUppercaseLetters_1to2(String param1) {
        assertTrue(CharacterPredicates.ASCII_UPPERCASE_LETTERS.test(param1));
    }

    static public Stream<Arguments> Provider_testAsciiUppercaseLetters_1to2() {
        return Stream.of(arguments("A"), arguments("Z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAsciiUppercaseLetters_3to7")
    public void testAsciiUppercaseLetters_3to7(int param1) {
        assertFalse(CharacterPredicates.ASCII_UPPERCASE_LETTERS.test(param1));
    }

    static public Stream<Arguments> Provider_testAsciiUppercaseLetters_3to7() {
        return Stream.of(arguments(9), arguments("@"), arguments("["), arguments("a"), arguments("z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDigits_1to2")
    public void testDigits_1to2(int param1) {
        assertTrue(CharacterPredicates.DIGITS.test(param1));
    }

    static public Stream<Arguments> Provider_testDigits_1to2() {
        return Stream.of(arguments(0), arguments(9));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDigits_3to5")
    public void testDigits_3to5(String param1) {
        assertFalse(CharacterPredicates.DIGITS.test(param1));
    }

    static public Stream<Arguments> Provider_testDigits_3to5() {
        return Stream.of(arguments("-"), arguments("."), arguments("L"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLetters_1to2")
    public void testLetters_1to2(String param1) {
        assertTrue(CharacterPredicates.LETTERS.test(param1));
    }

    static public Stream<Arguments> Provider_testLetters_1to2() {
        return Stream.of(arguments("a"), arguments("Z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLetters_3to5")
    public void testLetters_3to5(int param1) {
        assertFalse(CharacterPredicates.LETTERS.test(param1));
    }

    static public Stream<Arguments> Provider_testLetters_3to5() {
        return Stream.of(arguments(1), arguments("?"), arguments("@"));
    }
}
