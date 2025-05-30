package org.apache.skywalking.oap.server.library.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilTest_Parameterized {

    @Test
    public void testIsEmpty_1() {
        assertTrue(StringUtil.isEmpty(null));
    }

    @Test
    public void testIsEmpty_2() {
        assertTrue(StringUtil.isEmpty(""));
    }

    @Test
    public void testIsBlank_1() {
        assertTrue(StringUtil.isBlank(null));
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

    @ParameterizedTest
    @MethodSource("Provider_testIsEmpty_3to4")
    public void testIsEmpty_3to4(String param1) {
        assertFalse(StringUtil.isEmpty(param1));
    }

    static public Stream<Arguments> Provider_testIsEmpty_3to4() {
        return Stream.of(arguments("   "), arguments("A String"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsBlank_2to3")
    public void testIsBlank_2to3(String param1) {
        assertTrue(StringUtil.isBlank(param1));
    }

    static public Stream<Arguments> Provider_testIsBlank_2to3() {
        return Stream.of(arguments(""), arguments("   "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrim_1to4")
    public void testTrim_1to4(String param1, String param2, String param3) {
        assertEquals(StringUtil.trim(param2, param3), param1);
    }

    static public Stream<Arguments> Provider_testTrim_1to4() {
        return Stream.of(arguments("bcdef", "aaabcdefaaa", "a"), arguments("bcdef", "bcdef", "a"), arguments("bcdef", "abcdef", "a"), arguments("abcde", "abcdef", "f"));
    }
}
