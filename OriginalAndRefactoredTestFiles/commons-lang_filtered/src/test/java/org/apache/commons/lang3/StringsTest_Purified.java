package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class StringsTest_Purified {

    public static Stream<Strings> stringsFactory() {
        return Stream.of(Strings.CS, Strings.CI);
    }

    @Test
    public void testBuilder_1() {
        assertTrue(Strings.builder().setIgnoreCase(false).get().isCaseSensitive());
    }

    @Test
    public void testBuilder_2() {
        assertFalse(Strings.builder().setIgnoreCase(true).get().isCaseSensitive());
    }

    @Test
    public void testBuilder_3() {
        assertTrue(Strings.builder().setNullIsLess(false).get().isCaseSensitive());
    }

    @Test
    public void testBuilder_4() {
        assertTrue(Strings.builder().setNullIsLess(true).get().isCaseSensitive());
    }

    @Test
    public void testCaseInsensitiveConstant_1() {
        assertNotNull(Strings.CI);
    }

    @Test
    public void testCaseInsensitiveConstant_2() {
        assertFalse(Strings.CI.isCaseSensitive());
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_1() {
        assertFalse(Strings.CI.startsWithAny(null, (String[]) null));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_2() {
        assertFalse(Strings.CI.startsWithAny(null, "aBc"));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_3() {
        assertFalse(Strings.CI.startsWithAny("AbCxYz", (String[]) null));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_4() {
        assertFalse(Strings.CI.startsWithAny("AbCxYz"));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_5() {
        assertTrue(Strings.CI.startsWithAny("AbCxYz", "aBc"));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_6() {
        assertTrue(Strings.CI.startsWithAny("AbCxYz", null, "XyZ", "aBc"));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_7() {
        assertFalse(Strings.CI.startsWithAny("AbCxYz", null, "XyZ", "aBcD"));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_8() {
        assertTrue(Strings.CI.startsWithAny("AbCxYz", ""));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_9() {
        assertTrue(Strings.CI.startsWithAny("abcxyz", null, "XyZ", "ABCX"));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_10() {
        assertTrue(Strings.CI.startsWithAny("ABCXYZ", null, "XyZ", "abc"));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_11() {
        assertTrue(Strings.CI.startsWithAny("AbCxYz", new StringBuilder("XyZ"), new StringBuffer("aBc")));
    }

    @Test
    public void testCaseInsensitiveStartsWithAny_12() {
        assertTrue(Strings.CI.startsWithAny(new StringBuffer("AbCxYz"), new StringBuilder("XyZ"), new StringBuffer("abc")));
    }

    @Test
    public void testCaseSensitiveConstant_1() {
        assertNotNull(Strings.CS);
    }

    @Test
    public void testCaseSensitiveConstant_2() {
        assertTrue(Strings.CS.isCaseSensitive());
    }
}
