package org.apache.skywalking.oap.server.library.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_Purified {

    @Test
    public void testIsEmpty_1() {
        assertTrue(StringUtil.isEmpty(null));
    }

    @Test
    public void testIsEmpty_2() {
        assertTrue(StringUtil.isEmpty(""));
    }

    @Test
    public void testIsEmpty_3() {
        assertFalse(StringUtil.isEmpty("   "));
    }

    @Test
    public void testIsEmpty_4() {
        assertFalse(StringUtil.isEmpty("A String"));
    }

    @Test
    public void testIsBlank_1() {
        assertTrue(StringUtil.isBlank(null));
    }

    @Test
    public void testIsBlank_2() {
        assertTrue(StringUtil.isBlank(""));
    }

    @Test
    public void testIsBlank_3() {
        assertTrue(StringUtil.isBlank("   "));
    }

    @Test
    public void testIsBlank_4() {
        assertFalse(StringUtil.isBlank("A String"));
    }

    @Test
    public void testCut_1() {
        String str = "aaaaaaabswbswbbsbwbsbbwbsbwbsbwbbsbbebewewewewewewewewewewew";
        assertEquals(10, StringUtil.cut(str, 10).length());
    }

    @Test
    public void testCut_2() {
        String shortStr = "ab";
        assertEquals(2, StringUtil.cut(shortStr, 10).length());
    }

    @Test
    public void testTrim_1() {
        assertEquals(StringUtil.trim("aaabcdefaaa", 'a'), "bcdef");
    }

    @Test
    public void testTrim_2() {
        assertEquals(StringUtil.trim("bcdef", 'a'), "bcdef");
    }

    @Test
    public void testTrim_3() {
        assertEquals(StringUtil.trim("abcdef", 'a'), "bcdef");
    }

    @Test
    public void testTrim_4() {
        assertEquals(StringUtil.trim("abcdef", 'f'), "abcde");
    }
}
