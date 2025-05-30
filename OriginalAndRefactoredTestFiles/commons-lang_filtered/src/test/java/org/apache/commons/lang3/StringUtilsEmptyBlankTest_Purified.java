package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class StringUtilsEmptyBlankTest_Purified extends AbstractLangTest {

    @Test
    public void testFirstNonBlank_1() {
        assertNull(StringUtils.firstNonBlank());
    }

    @Test
    public void testFirstNonBlank_2() {
        assertNull(StringUtils.firstNonBlank((String[]) null));
    }

    @Test
    public void testFirstNonBlank_3() {
        assertNull(StringUtils.firstNonBlank(null, null, null));
    }

    @Test
    public void testFirstNonBlank_4() {
        assertNull(StringUtils.firstNonBlank(null, "", " "));
    }

    @Test
    public void testFirstNonBlank_5() {
        assertNull(StringUtils.firstNonBlank(null, null, " "));
    }

    @Test
    public void testFirstNonBlank_6() {
        assertEquals("a", StringUtils.firstNonBlank(null, "a"));
    }

    @Test
    public void testFirstNonBlank_7() {
        assertEquals("zz", StringUtils.firstNonBlank(null, "zz"));
    }

    @Test
    public void testFirstNonBlank_8() {
        assertEquals("abc", StringUtils.firstNonBlank("abc"));
    }

    @Test
    public void testFirstNonBlank_9() {
        assertEquals("xyz", StringUtils.firstNonBlank(null, "xyz"));
    }

    @Test
    public void testFirstNonBlank_10() {
        assertEquals("xyz", StringUtils.firstNonBlank(null, "xyz", "abc"));
    }

    @Test
    public void testFirstNonEmpty_1() {
        assertNull(StringUtils.firstNonEmpty());
    }

    @Test
    public void testFirstNonEmpty_2() {
        assertNull(StringUtils.firstNonEmpty((String[]) null));
    }

    @Test
    public void testFirstNonEmpty_3() {
        assertNull(StringUtils.firstNonEmpty(null, null, null));
    }

    @Test
    public void testFirstNonEmpty_4() {
        assertEquals(" ", StringUtils.firstNonEmpty(null, "", " "));
    }

    @Test
    public void testFirstNonEmpty_5() {
        assertNull(StringUtils.firstNonEmpty(null, null, ""));
    }

    @Test
    public void testFirstNonEmpty_6() {
        assertEquals("a", StringUtils.firstNonEmpty(null, "a"));
    }

    @Test
    public void testFirstNonEmpty_7() {
        assertEquals("zz", StringUtils.firstNonEmpty(null, "zz"));
    }

    @Test
    public void testFirstNonEmpty_8() {
        assertEquals("abc", StringUtils.firstNonEmpty("abc"));
    }

    @Test
    public void testFirstNonEmpty_9() {
        assertEquals("xyz", StringUtils.firstNonEmpty(null, "xyz"));
    }

    @Test
    public void testFirstNonEmpty_10() {
        assertEquals("xyz", StringUtils.firstNonEmpty(null, "xyz", "abc"));
    }

    @Test
    public void testIsAllBlank_1() {
        assertTrue(StringUtils.isAllBlank((String) null));
    }

    @Test
    public void testIsAllBlank_2() {
        assertTrue(StringUtils.isAllBlank((String[]) null));
    }

    @Test
    public void testIsAllBlank_3() {
        assertTrue(StringUtils.isAllBlank(null, null));
    }

    @Test
    public void testIsAllBlank_4() {
        assertTrue(StringUtils.isAllBlank(null, " "));
    }

    @Test
    public void testIsAllBlank_5() {
        assertFalse(StringUtils.isAllBlank(null, "foo"));
    }

    @Test
    public void testIsAllBlank_6() {
        assertFalse(StringUtils.isAllBlank(null, "a"));
    }

    @Test
    public void testIsAllBlank_7() {
        assertFalse(StringUtils.isAllBlank("", "bar"));
    }

    @Test
    public void testIsAllBlank_8() {
        assertFalse(StringUtils.isAllBlank("bob", ""));
    }

    @Test
    public void testIsAllBlank_9() {
        assertFalse(StringUtils.isAllBlank("  bob  ", null));
    }

    @Test
    public void testIsAllBlank_10() {
        assertFalse(StringUtils.isAllBlank(" ", "bar"));
    }

    @Test
    public void testIsAllBlank_11() {
        assertFalse(StringUtils.isAllBlank("foo", "bar"));
    }

    @Test
    public void testIsAllEmpty_1() {
        assertTrue(StringUtils.isAllEmpty());
    }

    @Test
    public void testIsAllEmpty_2() {
        assertTrue(StringUtils.isAllEmpty());
    }

    @Test
    public void testIsAllEmpty_3() {
        assertTrue(StringUtils.isAllEmpty((String) null));
    }

    @Test
    public void testIsAllEmpty_4() {
        assertTrue(StringUtils.isAllEmpty((String[]) null));
    }

    @Test
    public void testIsAllEmpty_5() {
        assertFalse(StringUtils.isAllEmpty(null, "foo"));
    }

    @Test
    public void testIsAllEmpty_6() {
        assertFalse(StringUtils.isAllEmpty(null, "a"));
    }

    @Test
    public void testIsAllEmpty_7() {
        assertFalse(StringUtils.isAllEmpty("", "bar"));
    }

    @Test
    public void testIsAllEmpty_8() {
        assertFalse(StringUtils.isAllEmpty("bob", ""));
    }

    @Test
    public void testIsAllEmpty_9() {
        assertFalse(StringUtils.isAllEmpty("  bob  ", null));
    }

    @Test
    public void testIsAllEmpty_10() {
        assertFalse(StringUtils.isAllEmpty(" ", "bar"));
    }

    @Test
    public void testIsAllEmpty_11() {
        assertFalse(StringUtils.isAllEmpty("foo", "bar"));
    }

    @Test
    public void testIsAllEmpty_12() {
        assertTrue(StringUtils.isAllEmpty("", null));
    }

    @Test
    public void testIsAnyBlank_1() {
        assertTrue(StringUtils.isAnyBlank((String) null));
    }

    @Test
    public void testIsAnyBlank_2() {
        assertFalse(StringUtils.isAnyBlank((String[]) null));
    }

    @Test
    public void testIsAnyBlank_3() {
        assertTrue(StringUtils.isAnyBlank(null, "foo"));
    }

    @Test
    public void testIsAnyBlank_4() {
        assertTrue(StringUtils.isAnyBlank(null, null));
    }

    @Test
    public void testIsAnyBlank_5() {
        assertTrue(StringUtils.isAnyBlank("", "bar"));
    }

    @Test
    public void testIsAnyBlank_6() {
        assertTrue(StringUtils.isAnyBlank("bob", ""));
    }

    @Test
    public void testIsAnyBlank_7() {
        assertTrue(StringUtils.isAnyBlank("  bob  ", null));
    }

    @Test
    public void testIsAnyBlank_8() {
        assertTrue(StringUtils.isAnyBlank(" ", "bar"));
    }

    @Test
    public void testIsAnyBlank_9() {
        assertFalse(StringUtils.isAnyBlank("foo", "bar"));
    }

    @Test
    public void testIsAnyEmpty_1() {
        assertTrue(StringUtils.isAnyEmpty((String) null));
    }

    @Test
    public void testIsAnyEmpty_2() {
        assertFalse(StringUtils.isAnyEmpty((String[]) null));
    }

    @Test
    public void testIsAnyEmpty_3() {
        assertTrue(StringUtils.isAnyEmpty(null, "foo"));
    }

    @Test
    public void testIsAnyEmpty_4() {
        assertTrue(StringUtils.isAnyEmpty(null, "a"));
    }

    @Test
    public void testIsAnyEmpty_5() {
        assertTrue(StringUtils.isAnyEmpty("", "bar"));
    }

    @Test
    public void testIsAnyEmpty_6() {
        assertTrue(StringUtils.isAnyEmpty("bob", ""));
    }

    @Test
    public void testIsAnyEmpty_7() {
        assertTrue(StringUtils.isAnyEmpty("  bob  ", null));
    }

    @Test
    public void testIsAnyEmpty_8() {
        assertFalse(StringUtils.isAnyEmpty(" ", "bar"));
    }

    @Test
    public void testIsAnyEmpty_9() {
        assertFalse(StringUtils.isAnyEmpty("foo", "bar"));
    }

    @Test
    public void testIsBlank_1() {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    public void testIsBlank_2() {
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    public void testIsBlank_3() {
        assertTrue(StringUtils.isBlank(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testIsBlank_4() {
        assertFalse(StringUtils.isBlank("a"));
    }

    @Test
    public void testIsBlank_5() {
        assertFalse(StringUtils.isBlank("foo"));
    }

    @Test
    public void testIsBlank_6() {
        assertFalse(StringUtils.isBlank("  foo  "));
    }

    @Test
    public void testIsEmpty_1() {
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    public void testIsEmpty_2() {
        assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    public void testIsEmpty_3() {
        assertFalse(StringUtils.isEmpty(" "));
    }

    @Test
    public void testIsEmpty_4() {
        assertFalse(StringUtils.isEmpty("a"));
    }

    @Test
    public void testIsEmpty_5() {
        assertFalse(StringUtils.isEmpty("foo"));
    }

    @Test
    public void testIsEmpty_6() {
        assertFalse(StringUtils.isEmpty("  foo  "));
    }

    @Test
    public void testIsNoneBlank_1() {
        assertFalse(StringUtils.isNoneBlank((String) null));
    }

    @Test
    public void testIsNoneBlank_2() {
        assertTrue(StringUtils.isNoneBlank((String[]) null));
    }

    @Test
    public void testIsNoneBlank_3() {
        assertFalse(StringUtils.isNoneBlank(null, "a"));
    }

    @Test
    public void testIsNoneBlank_4() {
        assertFalse(StringUtils.isNoneBlank(null, "foo"));
    }

    @Test
    public void testIsNoneBlank_5() {
        assertFalse(StringUtils.isNoneBlank(null, null));
    }

    @Test
    public void testIsNoneBlank_6() {
        assertFalse(StringUtils.isNoneBlank("", "bar"));
    }

    @Test
    public void testIsNoneBlank_7() {
        assertFalse(StringUtils.isNoneBlank("a", ""));
    }

    @Test
    public void testIsNoneBlank_8() {
        assertFalse(StringUtils.isNoneBlank("bob", ""));
    }

    @Test
    public void testIsNoneBlank_9() {
        assertFalse(StringUtils.isNoneBlank("  bob  ", null));
    }

    @Test
    public void testIsNoneBlank_10() {
        assertFalse(StringUtils.isNoneBlank(" ", "bar"));
    }

    @Test
    public void testIsNoneBlank_11() {
        assertTrue(StringUtils.isNoneBlank("foo", "bar"));
    }

    @Test
    public void testIsNoneEmpty_1() {
        assertFalse(StringUtils.isNoneEmpty((String) null));
    }

    @Test
    public void testIsNoneEmpty_2() {
        assertTrue(StringUtils.isNoneEmpty((String[]) null));
    }

    @Test
    public void testIsNoneEmpty_3() {
        assertFalse(StringUtils.isNoneEmpty(null, "a"));
    }

    @Test
    public void testIsNoneEmpty_4() {
        assertFalse(StringUtils.isNoneEmpty(null, "foo"));
    }

    @Test
    public void testIsNoneEmpty_5() {
        assertFalse(StringUtils.isNoneEmpty("", "bar"));
    }

    @Test
    public void testIsNoneEmpty_6() {
        assertFalse(StringUtils.isNoneEmpty("bob", ""));
    }

    @Test
    public void testIsNoneEmpty_7() {
        assertFalse(StringUtils.isNoneEmpty("a", ""));
    }

    @Test
    public void testIsNoneEmpty_8() {
        assertFalse(StringUtils.isNoneEmpty("  bob  ", null));
    }

    @Test
    public void testIsNoneEmpty_9() {
        assertTrue(StringUtils.isNoneEmpty(" ", "bar"));
    }

    @Test
    public void testIsNoneEmpty_10() {
        assertTrue(StringUtils.isNoneEmpty("foo", "bar"));
    }

    @Test
    public void testIsNotBlank_1() {
        assertFalse(StringUtils.isNotBlank(null));
    }

    @Test
    public void testIsNotBlank_2() {
        assertFalse(StringUtils.isNotBlank(""));
    }

    @Test
    public void testIsNotBlank_3() {
        assertFalse(StringUtils.isNotBlank(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testIsNotBlank_4() {
        assertTrue(StringUtils.isNotBlank("a"));
    }

    @Test
    public void testIsNotBlank_5() {
        assertTrue(StringUtils.isNotBlank("foo"));
    }

    @Test
    public void testIsNotBlank_6() {
        assertTrue(StringUtils.isNotBlank("  foo  "));
    }

    @Test
    public void testIsNotEmpty_1() {
        assertFalse(StringUtils.isNotEmpty(null));
    }

    @Test
    public void testIsNotEmpty_2() {
        assertFalse(StringUtils.isNotEmpty(""));
    }

    @Test
    public void testIsNotEmpty_3() {
        assertTrue(StringUtils.isNotEmpty(" "));
    }

    @Test
    public void testIsNotEmpty_4() {
        assertTrue(StringUtils.isNotEmpty("a"));
    }

    @Test
    public void testIsNotEmpty_5() {
        assertTrue(StringUtils.isNotEmpty("foo"));
    }

    @Test
    public void testIsNotEmpty_6() {
        assertTrue(StringUtils.isNotEmpty("  foo  "));
    }
}
