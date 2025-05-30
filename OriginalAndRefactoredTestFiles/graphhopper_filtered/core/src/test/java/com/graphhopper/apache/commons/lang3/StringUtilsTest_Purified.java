package com.graphhopper.apache.commons.lang3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringUtilsTest_Purified {

    @Test
    public void testGetLevenshteinDistance_StringString_1() {
        assertEquals(0, StringUtils.getLevenshteinDistance("", ""));
    }

    @Test
    public void testGetLevenshteinDistance_StringString_2() {
        assertEquals(1, StringUtils.getLevenshteinDistance("", "a"));
    }

    @Test
    public void testGetLevenshteinDistance_StringString_3() {
        assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", ""));
    }

    @Test
    public void testGetLevenshteinDistance_StringString_4() {
        assertEquals(1, StringUtils.getLevenshteinDistance("frog", "fog"));
    }

    @Test
    public void testGetLevenshteinDistance_StringString_5() {
        assertEquals(3, StringUtils.getLevenshteinDistance("fly", "ant"));
    }

    @Test
    public void testGetLevenshteinDistance_StringString_6() {
        assertEquals(7, StringUtils.getLevenshteinDistance("elephant", "hippo"));
    }

    @Test
    public void testGetLevenshteinDistance_StringString_7() {
        assertEquals(7, StringUtils.getLevenshteinDistance("hippo", "elephant"));
    }

    @Test
    public void testGetLevenshteinDistance_StringString_8() {
        assertEquals(8, StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz"));
    }

    @Test
    public void testGetLevenshteinDistance_StringString_9() {
        assertEquals(8, StringUtils.getLevenshteinDistance("zzzzzzzz", "hippo"));
    }

    @Test
    public void testGetLevenshteinDistance_StringString_10() {
        assertEquals(1, StringUtils.getLevenshteinDistance("hello", "hallo"));
    }
}
