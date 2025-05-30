package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import java.util.Locale;
import static com.graphhopper.util.Helper.UTF_CS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HelperTest_Purified {

    @Test
    public void testGetLocale_1() {
        assertEquals(Locale.GERMAN, Helper.getLocale("de"));
    }

    @Test
    public void testGetLocale_2() {
        assertEquals(Locale.GERMANY, Helper.getLocale("de_DE"));
    }

    @Test
    public void testGetLocale_3() {
        assertEquals(Locale.GERMANY, Helper.getLocale("de-DE"));
    }

    @Test
    public void testGetLocale_4() {
        assertEquals(Locale.ENGLISH, Helper.getLocale("en"));
    }

    @Test
    public void testGetLocale_5() {
        assertEquals(Locale.US, Helper.getLocale("en_US"));
    }

    @Test
    public void testGetLocale_6() {
        assertEquals(Locale.US, Helper.getLocale("en_US.UTF-8"));
    }

    @Test
    public void testRound_1() {
        assertEquals(100.94, Helper.round(100.94, 2), 1e-7);
    }

    @Test
    public void testRound_2() {
        assertEquals(100.9, Helper.round(100.94, 1), 1e-7);
    }

    @Test
    public void testRound_3() {
        assertEquals(101.0, Helper.round(100.95, 1), 1e-7);
    }

    @Test
    public void testRound_4() {
        assertEquals(1040, Helper.round(1041.02, -1), 1.e-7);
    }

    @Test
    public void testRound_5() {
        assertEquals(1000, Helper.round(1041.02, -2), 1.e-7);
    }

    @Test
    public void testKeepIn_1() {
        assertEquals(2, Helper.keepIn(2, 1, 4), 1e-2);
    }

    @Test
    public void testKeepIn_2() {
        assertEquals(3, Helper.keepIn(2, 3, 4), 1e-2);
    }

    @Test
    public void testKeepIn_3() {
        assertEquals(3, Helper.keepIn(-2, 3, 4), 1e-2);
    }

    @Test
    public void testCamelCaseToUnderscore_1() {
        assertEquals("test_case", Helper.camelCaseToUnderScore("testCase"));
    }

    @Test
    public void testCamelCaseToUnderscore_2() {
        assertEquals("test_case_t_b_d", Helper.camelCaseToUnderScore("testCaseTBD"));
    }

    @Test
    public void testCamelCaseToUnderscore_3() {
        assertEquals("_test_case", Helper.camelCaseToUnderScore("TestCase"));
    }

    @Test
    public void testCamelCaseToUnderscore_4() {
        assertEquals("_test_case", Helper.camelCaseToUnderScore("_test_case"));
    }

    @Test
    public void testUnderscoreToCamelCase_1() {
        assertEquals("testCase", Helper.underScoreToCamelCase("test_case"));
    }

    @Test
    public void testUnderscoreToCamelCase_2() {
        assertEquals("testCaseTBD", Helper.underScoreToCamelCase("test_case_t_b_d"));
    }

    @Test
    public void testUnderscoreToCamelCase_3() {
        assertEquals("TestCase_", Helper.underScoreToCamelCase("_test_case_"));
    }
}
