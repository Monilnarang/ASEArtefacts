package org.apache.commons.lang3;

import static org.apache.commons.lang3.Supplementary.CharU20000;
import static org.apache.commons.lang3.Supplementary.CharU20001;
import static org.apache.commons.lang3.Supplementary.CharUSuppCharLow;
import static org.apache.commons.lang3.Supplementary.CharUSuppCharHigh;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;
import org.junitpioneer.jupiter.ReadsDefaultLocale;
import org.junitpioneer.jupiter.WritesDefaultLocale;

public class StringUtilsContainsTest_Purified extends AbstractLangTest {

    @Test
    public void testContains_Char_1() {
        assertFalse(StringUtils.contains(null, ' '));
    }

    @Test
    public void testContains_Char_2() {
        assertFalse(StringUtils.contains("", ' '));
    }

    @Test
    public void testContains_Char_3() {
        assertFalse(StringUtils.contains("", null));
    }

    @Test
    public void testContains_Char_4() {
        assertFalse(StringUtils.contains(null, null));
    }

    @Test
    public void testContains_Char_5() {
        assertTrue(StringUtils.contains("abc", 'a'));
    }

    @Test
    public void testContains_Char_6() {
        assertTrue(StringUtils.contains("abc", 'b'));
    }

    @Test
    public void testContains_Char_7() {
        assertTrue(StringUtils.contains("abc", 'c'));
    }

    @Test
    public void testContains_Char_8() {
        assertFalse(StringUtils.contains("abc", 'z'));
    }

    @Test
    public void testContains_String_1() {
        assertFalse(StringUtils.contains(null, null));
    }

    @Test
    public void testContains_String_2() {
        assertFalse(StringUtils.contains(null, ""));
    }

    @Test
    public void testContains_String_3() {
        assertFalse(StringUtils.contains(null, "a"));
    }

    @Test
    public void testContains_String_4() {
        assertFalse(StringUtils.contains("", null));
    }

    @Test
    public void testContains_String_5() {
        assertTrue(StringUtils.contains("", ""));
    }

    @Test
    public void testContains_String_6() {
        assertFalse(StringUtils.contains("", "a"));
    }

    @Test
    public void testContains_String_7() {
        assertTrue(StringUtils.contains("abc", "a"));
    }

    @Test
    public void testContains_String_8() {
        assertTrue(StringUtils.contains("abc", "b"));
    }

    @Test
    public void testContains_String_9() {
        assertTrue(StringUtils.contains("abc", "c"));
    }

    @Test
    public void testContains_String_10() {
        assertTrue(StringUtils.contains("abc", "abc"));
    }

    @Test
    public void testContains_String_11() {
        assertFalse(StringUtils.contains("abc", "z"));
    }

    @Test
    public void testContains_StringWithBadSupplementaryChars_1() {
        assertFalse(StringUtils.contains(CharUSuppCharLow, CharU20001));
    }

    @Test
    public void testContains_StringWithBadSupplementaryChars_2() {
        assertFalse(StringUtils.contains(CharUSuppCharHigh, CharU20001));
    }

    @Test
    public void testContains_StringWithBadSupplementaryChars_3() {
        assertFalse(StringUtils.contains(CharU20001, CharUSuppCharLow));
    }

    @Test
    public void testContains_StringWithBadSupplementaryChars_4() {
        assertEquals(0, CharU20001.indexOf(CharUSuppCharHigh));
    }

    @Test
    public void testContains_StringWithBadSupplementaryChars_5() {
        assertTrue(StringUtils.contains(CharU20001, CharUSuppCharHigh));
    }

    @Test
    public void testContains_StringWithBadSupplementaryChars_6() {
        assertTrue(StringUtils.contains(CharU20001 + CharUSuppCharHigh + "a", "a"));
    }

    @Test
    public void testContains_StringWithBadSupplementaryChars_7() {
        assertTrue(StringUtils.contains(CharU20001 + CharUSuppCharLow + "a", "a"));
    }

    @Test
    public void testContains_StringWithSupplementaryChars_1() {
        assertTrue(StringUtils.contains(CharU20000 + CharU20001, CharU20000));
    }

    @Test
    public void testContains_StringWithSupplementaryChars_2() {
        assertTrue(StringUtils.contains(CharU20000 + CharU20001, CharU20001));
    }

    @Test
    public void testContains_StringWithSupplementaryChars_3() {
        assertTrue(StringUtils.contains(CharU20000, CharU20000));
    }

    @Test
    public void testContains_StringWithSupplementaryChars_4() {
        assertFalse(StringUtils.contains(CharU20000, CharU20001));
    }

    @Test
    public void testContainsAny_StringCharArrayWithBadSupplementaryChars_1() {
        assertFalse(StringUtils.containsAny(CharUSuppCharLow, CharU20001.toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithBadSupplementaryChars_2() {
        assertFalse(StringUtils.containsAny("abc" + CharUSuppCharLow + "xyz", CharU20001.toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithBadSupplementaryChars_3() {
        assertEquals(-1, CharUSuppCharHigh.indexOf(CharU20001));
    }

    @Test
    public void testContainsAny_StringCharArrayWithBadSupplementaryChars_4() {
        assertFalse(StringUtils.containsAny(CharUSuppCharHigh, CharU20001.toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithBadSupplementaryChars_5() {
        assertFalse(StringUtils.containsAny(CharU20001, CharUSuppCharLow.toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithBadSupplementaryChars_6() {
        assertEquals(0, CharU20001.indexOf(CharUSuppCharHigh));
    }

    @Test
    public void testContainsAny_StringCharArrayWithBadSupplementaryChars_7() {
        assertTrue(StringUtils.containsAny(CharU20001, CharUSuppCharHigh.toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_1() {
        assertTrue(StringUtils.containsAny(CharU20000 + CharU20001, CharU20000.toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_2() {
        assertTrue(StringUtils.containsAny("a" + CharU20000 + CharU20001, "a".toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_3() {
        assertTrue(StringUtils.containsAny(CharU20000 + "a" + CharU20001, "a".toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_4() {
        assertTrue(StringUtils.containsAny(CharU20000 + CharU20001 + "a", "a".toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_5() {
        assertTrue(StringUtils.containsAny(CharU20000 + CharU20001, CharU20001.toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_6() {
        assertTrue(StringUtils.containsAny(CharU20000, CharU20000.toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_7() {
        assertEquals(-1, CharU20000.indexOf(CharU20001));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_8() {
        assertEquals(0, CharU20000.indexOf(CharU20001.charAt(0)));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_9() {
        assertEquals(-1, CharU20000.indexOf(CharU20001.charAt(1)));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_10() {
        assertFalse(StringUtils.containsAny(CharU20000, CharU20001.toCharArray()));
    }

    @Test
    public void testContainsAny_StringCharArrayWithSupplementaryChars_11() {
        assertFalse(StringUtils.containsAny(CharU20001, CharU20000.toCharArray()));
    }

    @Test
    public void testContainsAny_StringString_1() {
        assertFalse(StringUtils.containsAny(null, (String) null));
    }

    @Test
    public void testContainsAny_StringString_2() {
        assertFalse(StringUtils.containsAny(null, ""));
    }

    @Test
    public void testContainsAny_StringString_3() {
        assertFalse(StringUtils.containsAny(null, "ab"));
    }

    @Test
    public void testContainsAny_StringString_4() {
        assertFalse(StringUtils.containsAny("", (String) null));
    }

    @Test
    public void testContainsAny_StringString_5() {
        assertFalse(StringUtils.containsAny("", ""));
    }

    @Test
    public void testContainsAny_StringString_6() {
        assertFalse(StringUtils.containsAny("", "ab"));
    }

    @Test
    public void testContainsAny_StringString_7() {
        assertFalse(StringUtils.containsAny("zzabyycdxx", (String) null));
    }

    @Test
    public void testContainsAny_StringString_8() {
        assertFalse(StringUtils.containsAny("zzabyycdxx", ""));
    }

    @Test
    public void testContainsAny_StringString_9() {
        assertTrue(StringUtils.containsAny("zzabyycdxx", "za"));
    }

    @Test
    public void testContainsAny_StringString_10() {
        assertTrue(StringUtils.containsAny("zzabyycdxx", "by"));
    }

    @Test
    public void testContainsAny_StringString_11() {
        assertTrue(StringUtils.containsAny("zzabyycdxx", "zy"));
    }

    @Test
    public void testContainsAny_StringString_12() {
        assertFalse(StringUtils.containsAny("ab", "z"));
    }

    @Test
    public void testContainsAny_StringWithBadSupplementaryChars_1() {
        assertFalse(StringUtils.containsAny(CharUSuppCharLow, CharU20001));
    }

    @Test
    public void testContainsAny_StringWithBadSupplementaryChars_2() {
        assertEquals(-1, CharUSuppCharHigh.indexOf(CharU20001));
    }

    @Test
    public void testContainsAny_StringWithBadSupplementaryChars_3() {
        assertFalse(StringUtils.containsAny(CharUSuppCharHigh, CharU20001));
    }

    @Test
    public void testContainsAny_StringWithBadSupplementaryChars_4() {
        assertFalse(StringUtils.containsAny(CharU20001, CharUSuppCharLow));
    }

    @Test
    public void testContainsAny_StringWithBadSupplementaryChars_5() {
        assertEquals(0, CharU20001.indexOf(CharUSuppCharHigh));
    }

    @Test
    public void testContainsAny_StringWithBadSupplementaryChars_6() {
        assertTrue(StringUtils.containsAny(CharU20001, CharUSuppCharHigh));
    }

    @Test
    public void testContainsAny_StringWithSupplementaryChars_1() {
        assertTrue(StringUtils.containsAny(CharU20000 + CharU20001, CharU20000));
    }

    @Test
    public void testContainsAny_StringWithSupplementaryChars_2() {
        assertTrue(StringUtils.containsAny(CharU20000 + CharU20001, CharU20001));
    }

    @Test
    public void testContainsAny_StringWithSupplementaryChars_3() {
        assertTrue(StringUtils.containsAny(CharU20000, CharU20000));
    }

    @Test
    public void testContainsAny_StringWithSupplementaryChars_4() {
        assertEquals(-1, CharU20000.indexOf(CharU20001));
    }

    @Test
    public void testContainsAny_StringWithSupplementaryChars_5() {
        assertEquals(0, CharU20000.indexOf(CharU20001.charAt(0)));
    }

    @Test
    public void testContainsAny_StringWithSupplementaryChars_6() {
        assertEquals(-1, CharU20000.indexOf(CharU20001.charAt(1)));
    }

    @Test
    public void testContainsAny_StringWithSupplementaryChars_7() {
        assertFalse(StringUtils.containsAny(CharU20000, CharU20001));
    }

    @Test
    public void testContainsAny_StringWithSupplementaryChars_8() {
        assertFalse(StringUtils.containsAny(CharU20001, CharU20000));
    }

    @Test
    public void testContainsIgnoreCase_StringString_1() {
        assertFalse(StringUtils.containsIgnoreCase(null, null));
    }

    @Test
    public void testContainsIgnoreCase_StringString_2() {
        assertFalse(StringUtils.containsIgnoreCase(null, ""));
    }

    @Test
    public void testContainsIgnoreCase_StringString_3() {
        assertFalse(StringUtils.containsIgnoreCase(null, "a"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_4() {
        assertFalse(StringUtils.containsIgnoreCase(null, "abc"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_5() {
        assertFalse(StringUtils.containsIgnoreCase("", null));
    }

    @Test
    public void testContainsIgnoreCase_StringString_6() {
        assertFalse(StringUtils.containsIgnoreCase("a", null));
    }

    @Test
    public void testContainsIgnoreCase_StringString_7() {
        assertFalse(StringUtils.containsIgnoreCase("abc", null));
    }

    @Test
    public void testContainsIgnoreCase_StringString_8() {
        assertTrue(StringUtils.containsIgnoreCase("", ""));
    }

    @Test
    public void testContainsIgnoreCase_StringString_9() {
        assertTrue(StringUtils.containsIgnoreCase("a", ""));
    }

    @Test
    public void testContainsIgnoreCase_StringString_10() {
        assertTrue(StringUtils.containsIgnoreCase("abc", ""));
    }

    @Test
    public void testContainsIgnoreCase_StringString_11() {
        assertFalse(StringUtils.containsIgnoreCase("", "a"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_12() {
        assertTrue(StringUtils.containsIgnoreCase("a", "a"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_13() {
        assertTrue(StringUtils.containsIgnoreCase("abc", "a"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_14() {
        assertFalse(StringUtils.containsIgnoreCase("", "A"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_15() {
        assertTrue(StringUtils.containsIgnoreCase("a", "A"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_16() {
        assertTrue(StringUtils.containsIgnoreCase("abc", "A"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_17() {
        assertFalse(StringUtils.containsIgnoreCase("", "abc"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_18() {
        assertFalse(StringUtils.containsIgnoreCase("a", "abc"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_19() {
        assertTrue(StringUtils.containsIgnoreCase("xabcz", "abc"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_20() {
        assertFalse(StringUtils.containsIgnoreCase("", "ABC"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_21() {
        assertFalse(StringUtils.containsIgnoreCase("a", "ABC"));
    }

    @Test
    public void testContainsIgnoreCase_StringString_22() {
        assertTrue(StringUtils.containsIgnoreCase("xabcz", "ABC"));
    }

    @Test
    public void testContainsNone_CharArrayWithBadSupplementaryChars_1() {
        assertTrue(StringUtils.containsNone(CharUSuppCharLow, CharU20001.toCharArray()));
    }

    @Test
    public void testContainsNone_CharArrayWithBadSupplementaryChars_2() {
        assertEquals(-1, CharUSuppCharHigh.indexOf(CharU20001));
    }

    @Test
    public void testContainsNone_CharArrayWithBadSupplementaryChars_3() {
        assertTrue(StringUtils.containsNone(CharUSuppCharHigh, CharU20001.toCharArray()));
    }

    @Test
    public void testContainsNone_CharArrayWithBadSupplementaryChars_4() {
        assertEquals(-1, CharU20001.indexOf(CharUSuppCharLow));
    }

    @Test
    public void testContainsNone_CharArrayWithBadSupplementaryChars_5() {
        assertTrue(StringUtils.containsNone(CharU20001, CharUSuppCharLow.toCharArray()));
    }

    @Test
    public void testContainsNone_CharArrayWithBadSupplementaryChars_6() {
        assertEquals(0, CharU20001.indexOf(CharUSuppCharHigh));
    }

    @Test
    public void testContainsNone_CharArrayWithBadSupplementaryChars_7() {
        assertFalse(StringUtils.containsNone(CharU20001, CharUSuppCharHigh.toCharArray()));
    }

    @Test
    public void testContainsNone_CharArrayWithSupplementaryChars_1() {
        assertFalse(StringUtils.containsNone(CharU20000 + CharU20001, CharU20000.toCharArray()));
    }

    @Test
    public void testContainsNone_CharArrayWithSupplementaryChars_2() {
        assertFalse(StringUtils.containsNone(CharU20000 + CharU20001, CharU20001.toCharArray()));
    }

    @Test
    public void testContainsNone_CharArrayWithSupplementaryChars_3() {
        assertFalse(StringUtils.containsNone(CharU20000, CharU20000.toCharArray()));
    }

    @Test
    public void testContainsNone_CharArrayWithSupplementaryChars_4() {
        assertEquals(-1, CharU20000.indexOf(CharU20001));
    }

    @Test
    public void testContainsNone_CharArrayWithSupplementaryChars_5() {
        assertEquals(0, CharU20000.indexOf(CharU20001.charAt(0)));
    }

    @Test
    public void testContainsNone_CharArrayWithSupplementaryChars_6() {
        assertEquals(-1, CharU20000.indexOf(CharU20001.charAt(1)));
    }

    @Test
    public void testContainsNone_CharArrayWithSupplementaryChars_7() {
        assertTrue(StringUtils.containsNone(CharU20000, CharU20001.toCharArray()));
    }

    @Test
    public void testContainsNone_CharArrayWithSupplementaryChars_8() {
        assertTrue(StringUtils.containsNone(CharU20001, CharU20000.toCharArray()));
    }

    @Test
    public void testContainsNone_String_1() {
        assertTrue(StringUtils.containsNone(null, (String) null));
    }

    @Test
    public void testContainsNone_String_2() {
        assertTrue(StringUtils.containsNone("", (String) null));
    }

    @Test
    public void testContainsNone_String_3() {
        assertTrue(StringUtils.containsNone(null, ""));
    }

    @Test
    public void testContainsNone_String_4_testMerged_4() {
        final String str1 = "a";
        final String str2 = "b";
        final String str3 = "ab.";
        final String chars1 = "b";
        final String chars2 = ".";
        final String chars3 = "cd";
        assertTrue(StringUtils.containsNone(str1, ""));
        assertTrue(StringUtils.containsNone("", chars1));
        assertTrue(StringUtils.containsNone(str1, chars1));
        assertTrue(StringUtils.containsNone(str1, chars2));
        assertTrue(StringUtils.containsNone(str1, chars3));
        assertFalse(StringUtils.containsNone(str2, chars1));
        assertTrue(StringUtils.containsNone(str2, chars2));
        assertTrue(StringUtils.containsNone(str2, chars3));
        assertFalse(StringUtils.containsNone(str3, chars1));
        assertFalse(StringUtils.containsNone(str3, chars2));
        assertTrue(StringUtils.containsNone(str3, chars3));
    }

    @Test
    public void testContainsNone_String_5() {
        assertTrue(StringUtils.containsNone("", ""));
    }

    @Test
    public void testContainsNone_StringWithBadSupplementaryChars_1() {
        assertTrue(StringUtils.containsNone(CharUSuppCharLow, CharU20001));
    }

    @Test
    public void testContainsNone_StringWithBadSupplementaryChars_2() {
        assertEquals(-1, CharUSuppCharHigh.indexOf(CharU20001));
    }

    @Test
    public void testContainsNone_StringWithBadSupplementaryChars_3() {
        assertTrue(StringUtils.containsNone(CharUSuppCharHigh, CharU20001));
    }

    @Test
    public void testContainsNone_StringWithBadSupplementaryChars_4() {
        assertEquals(-1, CharU20001.indexOf(CharUSuppCharLow));
    }

    @Test
    public void testContainsNone_StringWithBadSupplementaryChars_5() {
        assertTrue(StringUtils.containsNone(CharU20001, CharUSuppCharLow));
    }

    @Test
    public void testContainsNone_StringWithBadSupplementaryChars_6() {
        assertEquals(0, CharU20001.indexOf(CharUSuppCharHigh));
    }

    @Test
    public void testContainsNone_StringWithBadSupplementaryChars_7() {
        assertFalse(StringUtils.containsNone(CharU20001, CharUSuppCharHigh));
    }

    @Test
    public void testContainsNone_StringWithSupplementaryChars_1() {
        assertFalse(StringUtils.containsNone(CharU20000 + CharU20001, CharU20000));
    }

    @Test
    public void testContainsNone_StringWithSupplementaryChars_2() {
        assertFalse(StringUtils.containsNone(CharU20000 + CharU20001, CharU20001));
    }

    @Test
    public void testContainsNone_StringWithSupplementaryChars_3() {
        assertFalse(StringUtils.containsNone(CharU20000, CharU20000));
    }

    @Test
    public void testContainsNone_StringWithSupplementaryChars_4() {
        assertEquals(-1, CharU20000.indexOf(CharU20001));
    }

    @Test
    public void testContainsNone_StringWithSupplementaryChars_5() {
        assertEquals(0, CharU20000.indexOf(CharU20001.charAt(0)));
    }

    @Test
    public void testContainsNone_StringWithSupplementaryChars_6() {
        assertEquals(-1, CharU20000.indexOf(CharU20001.charAt(1)));
    }

    @Test
    public void testContainsNone_StringWithSupplementaryChars_7() {
        assertTrue(StringUtils.containsNone(CharU20000, CharU20001));
    }

    @Test
    public void testContainsNone_StringWithSupplementaryChars_8() {
        assertTrue(StringUtils.containsNone(CharU20001, CharU20000));
    }

    @Test
    public void testContainsOnly_String_1() {
        assertFalse(StringUtils.containsOnly(null, (String) null));
    }

    @Test
    public void testContainsOnly_String_2() {
        assertFalse(StringUtils.containsOnly("", (String) null));
    }

    @Test
    public void testContainsOnly_String_3() {
        assertFalse(StringUtils.containsOnly(null, ""));
    }

    @Test
    public void testContainsOnly_String_4_testMerged_4() {
        final String str1 = "a";
        final String str2 = "b";
        final String str3 = "ab";
        final String chars1 = "b";
        final String chars2 = "a";
        final String chars3 = "ab";
        assertFalse(StringUtils.containsOnly(str1, ""));
        assertTrue(StringUtils.containsOnly("", chars1));
        assertFalse(StringUtils.containsOnly(str1, chars1));
        assertTrue(StringUtils.containsOnly(str1, chars2));
        assertTrue(StringUtils.containsOnly(str1, chars3));
        assertTrue(StringUtils.containsOnly(str2, chars1));
        assertFalse(StringUtils.containsOnly(str2, chars2));
        assertTrue(StringUtils.containsOnly(str2, chars3));
        assertFalse(StringUtils.containsOnly(str3, chars1));
        assertFalse(StringUtils.containsOnly(str3, chars2));
        assertTrue(StringUtils.containsOnly(str3, chars3));
    }

    @Test
    public void testContainsOnly_String_5() {
        assertTrue(StringUtils.containsOnly("", ""));
    }

    @Test
    public void testContainsWhitespace_1() {
        assertFalse(StringUtils.containsWhitespace(""));
    }

    @Test
    public void testContainsWhitespace_2() {
        assertTrue(StringUtils.containsWhitespace(" "));
    }

    @Test
    public void testContainsWhitespace_3() {
        assertFalse(StringUtils.containsWhitespace("a"));
    }

    @Test
    public void testContainsWhitespace_4() {
        assertTrue(StringUtils.containsWhitespace("a "));
    }

    @Test
    public void testContainsWhitespace_5() {
        assertTrue(StringUtils.containsWhitespace(" a"));
    }

    @Test
    public void testContainsWhitespace_6() {
        assertTrue(StringUtils.containsWhitespace("a\t"));
    }

    @Test
    public void testContainsWhitespace_7() {
        assertTrue(StringUtils.containsWhitespace("\n"));
    }
}
