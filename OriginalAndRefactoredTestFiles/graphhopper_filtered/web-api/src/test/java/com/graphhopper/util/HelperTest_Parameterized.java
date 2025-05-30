package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import java.util.Locale;
import static com.graphhopper.util.Helper.UTF_CS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HelperTest_Parameterized {

    @Test
    public void testGetLocale_1() {
        assertEquals(Locale.GERMAN, Helper.getLocale("de"));
    }

    @Test
    public void testGetLocale_4() {
        assertEquals(Locale.ENGLISH, Helper.getLocale("en"));
    }

    @Test
    public void testKeepIn_3() {
        assertEquals(3, Helper.keepIn(-2, 3, 4), 1e-2);
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetLocale_2to3")
    public void testGetLocale_2to3(String param1) {
        assertEquals(Locale.GERMANY, Helper.getLocale(param1));
    }

    static public Stream<Arguments> Provider_testGetLocale_2to3() {
        return Stream.of(arguments("de_DE"), arguments("de-DE"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetLocale_5to6")
    public void testGetLocale_5to6(String param1) {
        assertEquals(Locale.US, Helper.getLocale(param1));
    }

    static public Stream<Arguments> Provider_testGetLocale_5to6() {
        return Stream.of(arguments("en_US"), arguments("en_US.UTF-8"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRound_1to3")
    public void testRound_1to3(double param1, double param2, double param3, int param4) {
        assertEquals(param1, Helper.round(param3, param4), param2);
    }

    static public Stream<Arguments> Provider_testRound_1to3() {
        return Stream.of(arguments(100.94, 1e-7, 100.94, 2), arguments(100.9, 1e-7, 100.94, 1), arguments(101.0, 1e-7, 100.95, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRound_4to5")
    public void testRound_4to5(int param1, double param2, double param3, int param4) {
        assertEquals(param1, Helper.round(param3, -param4), param2);
    }

    static public Stream<Arguments> Provider_testRound_4to5() {
        return Stream.of(arguments(1040, 1.e-7, 1041.02, 1), arguments(1000, 1.e-7, 1041.02, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testKeepIn_1to2")
    public void testKeepIn_1to2(int param1, double param2, int param3, int param4, int param5) {
        assertEquals(param1, Helper.keepIn(param3, param4, param5), param2);
    }

    static public Stream<Arguments> Provider_testKeepIn_1to2() {
        return Stream.of(arguments(2, 1e-2, 2, 1, 4), arguments(3, 1e-2, 2, 3, 4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCamelCaseToUnderscore_1to4")
    public void testCamelCaseToUnderscore_1to4(String param1, String param2) {
        assertEquals(param1, Helper.camelCaseToUnderScore(param2));
    }

    static public Stream<Arguments> Provider_testCamelCaseToUnderscore_1to4() {
        return Stream.of(arguments("test_case", "testCase"), arguments("test_case_t_b_d", "testCaseTBD"), arguments("_test_case", "TestCase"), arguments("_test_case", "_test_case"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnderscoreToCamelCase_1to3")
    public void testUnderscoreToCamelCase_1to3(String param1, String param2) {
        assertEquals(param1, Helper.underScoreToCamelCase(param2));
    }

    static public Stream<Arguments> Provider_testUnderscoreToCamelCase_1to3() {
        return Stream.of(arguments("testCase", "test_case"), arguments("testCaseTBD", "test_case_t_b_d"), arguments("TestCase_", "_test_case_"));
    }
}
