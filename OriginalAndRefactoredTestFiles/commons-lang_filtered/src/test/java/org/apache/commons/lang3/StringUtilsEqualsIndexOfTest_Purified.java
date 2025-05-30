package org.apache.commons.lang3;

import static org.apache.commons.lang3.Supplementary.CharU20000;
import static org.apache.commons.lang3.Supplementary.CharU20001;
import static org.apache.commons.lang3.Supplementary.CharUSuppCharHigh;
import static org.apache.commons.lang3.Supplementary.CharUSuppCharLow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.nio.CharBuffer;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StringUtilsEqualsIndexOfTest_Purified extends AbstractLangTest {

    private static final class CustomCharSequence implements CharSequence {

        private final CharSequence seq;

        CustomCharSequence(final CharSequence seq) {
            this.seq = seq;
        }

        @Override
        public char charAt(final int index) {
            return seq.charAt(index);
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof CustomCharSequence)) {
                return false;
            }
            final CustomCharSequence other = (CustomCharSequence) obj;
            return seq.equals(other.seq);
        }

        @Override
        public int hashCode() {
            return seq.hashCode();
        }

        @Override
        public int length() {
            return seq.length();
        }

        @Override
        public CharSequence subSequence(final int start, final int end) {
            return new CustomCharSequence(seq.subSequence(start, end));
        }

        @Override
        public String toString() {
            return seq.toString();
        }
    }

    private static final String BAR = "bar";

    private static final String FOO = "foo";

    private static final String FOOBAR = "foobar";

    private static final String[] FOOBAR_SUB_ARRAY = { "ob", "ba" };

    static Stream<Arguments> indexOfAnyBut_withSurrogateChars() {
        return Stream.of(arguments(CharU20000 + CharU20001, CharU20000, 2), arguments(CharU20000 + CharU20001, CharU20001, 0), arguments(CharU20000 + CharU20001, "abcd" + CharUSuppCharLow, 0), arguments(CharU20000 + CharU20001, "abcd" + CharUSuppCharHigh, 0), arguments(CharU20000, CharU20000, -1), arguments(CharU20000, CharU20001, 0), arguments(CharUSuppCharHigh + "aaaa", CharUSuppCharHigh + "abcd", -1), arguments(CharUSuppCharHigh + "baaa", CharUSuppCharHigh + "abcd", -1), arguments(CharUSuppCharHigh + "aaaa", CharU20000 + "abcd", 0), arguments("aaaa" + CharUSuppCharHigh, CharU20000 + "abcd", 4), arguments(CharUSuppCharLow + "aaaa", CharU20000 + "abcd", 0), arguments("aaaa" + CharUSuppCharLow, CharU20000 + "abcd", 4), arguments(CharU20000 + "aaaa", CharUSuppCharLow + "ab" + CharUSuppCharHigh + "cd", 0), arguments(CharU20000 + "aaaa", "abcd", 0), arguments(CharU20000 + "aaaa", "abcd" + CharUSuppCharHigh, 0), arguments(CharU20000 + "aaaa", "abcd" + CharUSuppCharLow, 0), arguments("aaaa" + CharU20000, CharU20000 + "abcd", -1));
    }

    @Test
    public void testCompare_StringString_1() {
        assertEquals(0, StringUtils.compare(null, null));
    }

    @Test
    public void testCompare_StringString_2() {
        assertTrue(StringUtils.compare(null, "a") < 0);
    }

    @Test
    public void testCompare_StringString_3() {
        assertTrue(StringUtils.compare("a", null) > 0);
    }

    @Test
    public void testCompare_StringString_4() {
        assertEquals(0, StringUtils.compare("abc", "abc"));
    }

    @Test
    public void testCompare_StringString_5() {
        assertTrue(StringUtils.compare("a", "b") < 0);
    }

    @Test
    public void testCompare_StringString_6() {
        assertTrue(StringUtils.compare("b", "a") > 0);
    }

    @Test
    public void testCompare_StringString_7() {
        assertTrue(StringUtils.compare("a", "B") > 0);
    }

    @Test
    public void testCompare_StringString_8() {
        assertTrue(StringUtils.compare("abc", "abd") < 0);
    }

    @Test
    public void testCompare_StringString_9() {
        assertTrue(StringUtils.compare("ab", "abc") < 0);
    }

    @Test
    public void testCompare_StringString_10() {
        assertTrue(StringUtils.compare("ab", "ab ") < 0);
    }

    @Test
    public void testCompare_StringString_11() {
        assertTrue(StringUtils.compare("abc", "ab ") > 0);
    }

    @Test
    public void testCompare_StringStringBoolean_1() {
        assertEquals(0, StringUtils.compare(null, null, false));
    }

    @Test
    public void testCompare_StringStringBoolean_2() {
        assertTrue(StringUtils.compare(null, "a", true) < 0);
    }

    @Test
    public void testCompare_StringStringBoolean_3() {
        assertTrue(StringUtils.compare(null, "a", false) > 0);
    }

    @Test
    public void testCompare_StringStringBoolean_4() {
        assertTrue(StringUtils.compare("a", null, true) > 0);
    }

    @Test
    public void testCompare_StringStringBoolean_5() {
        assertTrue(StringUtils.compare("a", null, false) < 0);
    }

    @Test
    public void testCompare_StringStringBoolean_6() {
        assertEquals(0, StringUtils.compare("abc", "abc", false));
    }

    @Test
    public void testCompare_StringStringBoolean_7() {
        assertTrue(StringUtils.compare("a", "b", false) < 0);
    }

    @Test
    public void testCompare_StringStringBoolean_8() {
        assertTrue(StringUtils.compare("b", "a", false) > 0);
    }

    @Test
    public void testCompare_StringStringBoolean_9() {
        assertTrue(StringUtils.compare("a", "B", false) > 0);
    }

    @Test
    public void testCompare_StringStringBoolean_10() {
        assertTrue(StringUtils.compare("abc", "abd", false) < 0);
    }

    @Test
    public void testCompare_StringStringBoolean_11() {
        assertTrue(StringUtils.compare("ab", "abc", false) < 0);
    }

    @Test
    public void testCompare_StringStringBoolean_12() {
        assertTrue(StringUtils.compare("ab", "ab ", false) < 0);
    }

    @Test
    public void testCompare_StringStringBoolean_13() {
        assertTrue(StringUtils.compare("abc", "ab ", false) > 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_1() {
        assertEquals(0, StringUtils.compareIgnoreCase(null, null));
    }

    @Test
    public void testCompareIgnoreCase_StringString_2() {
        assertTrue(StringUtils.compareIgnoreCase(null, "a") < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_3() {
        assertTrue(StringUtils.compareIgnoreCase("a", null) > 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_4() {
        assertEquals(0, StringUtils.compareIgnoreCase("abc", "abc"));
    }

    @Test
    public void testCompareIgnoreCase_StringString_5() {
        assertEquals(0, StringUtils.compareIgnoreCase("abc", "ABC"));
    }

    @Test
    public void testCompareIgnoreCase_StringString_6() {
        assertTrue(StringUtils.compareIgnoreCase("a", "b") < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_7() {
        assertTrue(StringUtils.compareIgnoreCase("b", "a") > 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_8() {
        assertTrue(StringUtils.compareIgnoreCase("a", "B") < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_9() {
        assertTrue(StringUtils.compareIgnoreCase("A", "b") < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_10() {
        assertTrue(StringUtils.compareIgnoreCase("abc", "ABD") < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_11() {
        assertTrue(StringUtils.compareIgnoreCase("ab", "ABC") < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_12() {
        assertTrue(StringUtils.compareIgnoreCase("ab", "AB ") < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringString_13() {
        assertTrue(StringUtils.compareIgnoreCase("abc", "AB ") > 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_1() {
        assertEquals(0, StringUtils.compareIgnoreCase(null, null, false));
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_2() {
        assertTrue(StringUtils.compareIgnoreCase(null, "a", true) < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_3() {
        assertTrue(StringUtils.compareIgnoreCase(null, "a", false) > 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_4() {
        assertTrue(StringUtils.compareIgnoreCase("a", null, true) > 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_5() {
        assertTrue(StringUtils.compareIgnoreCase("a", null, false) < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_6() {
        assertEquals(0, StringUtils.compareIgnoreCase("abc", "abc", false));
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_7() {
        assertEquals(0, StringUtils.compareIgnoreCase("abc", "ABC", false));
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_8() {
        assertTrue(StringUtils.compareIgnoreCase("a", "b", false) < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_9() {
        assertTrue(StringUtils.compareIgnoreCase("b", "a", false) > 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_10() {
        assertTrue(StringUtils.compareIgnoreCase("a", "B", false) < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_11() {
        assertTrue(StringUtils.compareIgnoreCase("A", "b", false) < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_12() {
        assertTrue(StringUtils.compareIgnoreCase("abc", "ABD", false) < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_13() {
        assertTrue(StringUtils.compareIgnoreCase("ab", "ABC", false) < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_14() {
        assertTrue(StringUtils.compareIgnoreCase("ab", "AB ", false) < 0);
    }

    @Test
    public void testCompareIgnoreCase_StringStringBoolean_15() {
        assertTrue(StringUtils.compareIgnoreCase("abc", "AB ", false) > 0);
    }

    @Test
    public void testCustomCharSequence_1() {
        assertNotEquals(FOO, new CustomCharSequence(FOO));
    }

    @Test
    public void testCustomCharSequence_2() {
        assertNotEquals(new CustomCharSequence(FOO), FOO);
    }

    @Test
    public void testCustomCharSequence_3() {
        assertEquals(new CustomCharSequence(FOO), new CustomCharSequence(FOO));
    }

    @Test
    public void testIndexOf_char_1() {
        assertEquals(-1, StringUtils.indexOf(null, ' '));
    }

    @Test
    public void testIndexOf_char_2() {
        assertEquals(-1, StringUtils.indexOf("", ' '));
    }

    @Test
    public void testIndexOf_char_3() {
        assertEquals(0, StringUtils.indexOf("aabaabaa", 'a'));
    }

    @Test
    public void testIndexOf_char_4() {
        assertEquals(2, StringUtils.indexOf("aabaabaa", 'b'));
    }

    @Test
    public void testIndexOf_char_5() {
        assertEquals(2, StringUtils.indexOf(new StringBuilder("aabaabaa"), 'b'));
    }

    @Test
    public void testIndexOf_char_6() {
        assertEquals(StringUtils.INDEX_NOT_FOUND, StringUtils.indexOf(new StringBuilder("aabaabaa"), -1738));
    }

    @Test
    public void testIndexOf_String_1() {
        assertEquals(-1, StringUtils.indexOf(null, null));
    }

    @Test
    public void testIndexOf_String_2() {
        assertEquals(-1, StringUtils.indexOf("", null));
    }

    @Test
    public void testIndexOf_String_3() {
        assertEquals(0, StringUtils.indexOf("", ""));
    }

    @Test
    public void testIndexOf_String_4() {
        assertEquals(0, StringUtils.indexOf("aabaabaa", "a"));
    }

    @Test
    public void testIndexOf_String_5() {
        assertEquals(2, StringUtils.indexOf("aabaabaa", "b"));
    }

    @Test
    public void testIndexOf_String_6() {
        assertEquals(1, StringUtils.indexOf("aabaabaa", "ab"));
    }

    @Test
    public void testIndexOf_String_7() {
        assertEquals(0, StringUtils.indexOf("aabaabaa", ""));
    }

    @Test
    public void testIndexOf_String_8() {
        assertEquals(2, StringUtils.indexOf(new StringBuilder("aabaabaa"), "b"));
    }

    @Test
    public void testIndexOf_StringInt_1() {
        assertEquals(-1, StringUtils.indexOf(null, null, 0));
    }

    @Test
    public void testIndexOf_StringInt_2() {
        assertEquals(-1, StringUtils.indexOf(null, null, -1));
    }

    @Test
    public void testIndexOf_StringInt_3() {
        assertEquals(-1, StringUtils.indexOf(null, "", 0));
    }

    @Test
    public void testIndexOf_StringInt_4() {
        assertEquals(-1, StringUtils.indexOf(null, "", -1));
    }

    @Test
    public void testIndexOf_StringInt_5() {
        assertEquals(-1, StringUtils.indexOf("", null, 0));
    }

    @Test
    public void testIndexOf_StringInt_6() {
        assertEquals(-1, StringUtils.indexOf("", null, -1));
    }

    @Test
    public void testIndexOf_StringInt_7() {
        assertEquals(0, StringUtils.indexOf("", "", 0));
    }

    @Test
    public void testIndexOf_StringInt_8() {
        assertEquals(0, StringUtils.indexOf("", "", -1));
    }

    @Test
    public void testIndexOf_StringInt_9() {
        assertEquals(0, StringUtils.indexOf("", "", 9));
    }

    @Test
    public void testIndexOf_StringInt_10() {
        assertEquals(0, StringUtils.indexOf("abc", "", 0));
    }

    @Test
    public void testIndexOf_StringInt_11() {
        assertEquals(0, StringUtils.indexOf("abc", "", -1));
    }

    @Test
    public void testIndexOf_StringInt_12() {
        assertEquals(3, StringUtils.indexOf("abc", "", 9));
    }

    @Test
    public void testIndexOf_StringInt_13() {
        assertEquals(3, StringUtils.indexOf("abc", "", 3));
    }

    @Test
    public void testIndexOf_StringInt_14() {
        assertEquals(0, StringUtils.indexOf("aabaabaa", "a", 0));
    }

    @Test
    public void testIndexOf_StringInt_15() {
        assertEquals(2, StringUtils.indexOf("aabaabaa", "b", 0));
    }

    @Test
    public void testIndexOf_StringInt_16() {
        assertEquals(1, StringUtils.indexOf("aabaabaa", "ab", 0));
    }

    @Test
    public void testIndexOf_StringInt_17() {
        assertEquals(5, StringUtils.indexOf("aabaabaa", "b", 3));
    }

    @Test
    public void testIndexOf_StringInt_18() {
        assertEquals(-1, StringUtils.indexOf("aabaabaa", "b", 9));
    }

    @Test
    public void testIndexOf_StringInt_19() {
        assertEquals(2, StringUtils.indexOf("aabaabaa", "b", -1));
    }

    @Test
    public void testIndexOf_StringInt_20() {
        assertEquals(2, StringUtils.indexOf("aabaabaa", "", 2));
    }

    @Test
    public void testIndexOf_StringInt_21() {
        assertEquals(7, StringUtils.indexOf("12345678", "8", 5));
    }

    @Test
    public void testIndexOf_StringInt_22() {
        assertEquals(7, StringUtils.indexOf("12345678", "8", 6));
    }

    @Test
    public void testIndexOf_StringInt_23() {
        assertEquals(7, StringUtils.indexOf("12345678", "8", 7));
    }

    @Test
    public void testIndexOf_StringInt_24() {
        assertEquals(-1, StringUtils.indexOf("12345678", "8", 8));
    }

    @Test
    public void testIndexOf_StringInt_25() {
        assertEquals(5, StringUtils.indexOf(new StringBuilder("aabaabaa"), "b", 3));
    }

    @Test
    public void testIndexOfAny_StringCharArrayWithSupplementaryChars_1() {
        assertEquals(0, StringUtils.indexOfAny(CharU20000 + CharU20001, CharU20000.toCharArray()));
    }

    @Test
    public void testIndexOfAny_StringCharArrayWithSupplementaryChars_2() {
        assertEquals(2, StringUtils.indexOfAny(CharU20000 + CharU20001, CharU20001.toCharArray()));
    }

    @Test
    public void testIndexOfAny_StringCharArrayWithSupplementaryChars_3() {
        assertEquals(0, StringUtils.indexOfAny(CharU20000, CharU20000.toCharArray()));
    }

    @Test
    public void testIndexOfAny_StringCharArrayWithSupplementaryChars_4() {
        assertEquals(-1, StringUtils.indexOfAny(CharU20000, CharU20001.toCharArray()));
    }

    @Test
    public void testIndexOfAny_StringString_1() {
        assertEquals(-1, StringUtils.indexOfAny(null, (String) null));
    }

    @Test
    public void testIndexOfAny_StringString_2() {
        assertEquals(-1, StringUtils.indexOfAny(null, ""));
    }

    @Test
    public void testIndexOfAny_StringString_3() {
        assertEquals(-1, StringUtils.indexOfAny(null, "ab"));
    }

    @Test
    public void testIndexOfAny_StringString_4() {
        assertEquals(-1, StringUtils.indexOfAny("", (String) null));
    }

    @Test
    public void testIndexOfAny_StringString_5() {
        assertEquals(-1, StringUtils.indexOfAny("", ""));
    }

    @Test
    public void testIndexOfAny_StringString_6() {
        assertEquals(-1, StringUtils.indexOfAny("", "ab"));
    }

    @Test
    public void testIndexOfAny_StringString_7() {
        assertEquals(-1, StringUtils.indexOfAny("zzabyycdxx", (String) null));
    }

    @Test
    public void testIndexOfAny_StringString_8() {
        assertEquals(-1, StringUtils.indexOfAny("zzabyycdxx", ""));
    }

    @Test
    public void testIndexOfAny_StringString_9() {
        assertEquals(0, StringUtils.indexOfAny("zzabyycdxx", "za"));
    }

    @Test
    public void testIndexOfAny_StringString_10() {
        assertEquals(3, StringUtils.indexOfAny("zzabyycdxx", "by"));
    }

    @Test
    public void testIndexOfAny_StringString_11() {
        assertEquals(-1, StringUtils.indexOfAny("ab", "z"));
    }

    @Test
    public void testIndexOfAny_StringStringWithSupplementaryChars_1() {
        assertEquals(0, StringUtils.indexOfAny(CharU20000 + CharU20001, CharU20000));
    }

    @Test
    public void testIndexOfAny_StringStringWithSupplementaryChars_2() {
        assertEquals(2, StringUtils.indexOfAny(CharU20000 + CharU20001, CharU20001));
    }

    @Test
    public void testIndexOfAny_StringStringWithSupplementaryChars_3() {
        assertEquals(0, StringUtils.indexOfAny(CharU20000, CharU20000));
    }

    @Test
    public void testIndexOfAny_StringStringWithSupplementaryChars_4() {
        assertEquals(-1, StringUtils.indexOfAny(CharU20000, CharU20001));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_1() {
        assertEquals(-1, StringUtils.indexOfAnyBut(null, (char[]) null));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_2() {
        assertEquals(-1, StringUtils.indexOfAnyBut(null));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_3() {
        assertEquals(-1, StringUtils.indexOfAnyBut(null, 'a', 'b'));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_4() {
        assertEquals(-1, StringUtils.indexOfAnyBut("", (char[]) null));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_5() {
        assertEquals(-1, StringUtils.indexOfAnyBut(""));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_6() {
        assertEquals(-1, StringUtils.indexOfAnyBut("", 'a', 'b'));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_7() {
        assertEquals(-1, StringUtils.indexOfAnyBut("zzabyycdxx", (char[]) null));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_8() {
        assertEquals(-1, StringUtils.indexOfAnyBut("zzabyycdxx"));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_9() {
        assertEquals(3, StringUtils.indexOfAnyBut("zzabyycdxx", 'z', 'a'));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_10() {
        assertEquals(0, StringUtils.indexOfAnyBut("zzabyycdxx", 'b', 'y'));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_11() {
        assertEquals(-1, StringUtils.indexOfAnyBut("aba", 'a', 'b'));
    }

    @Test
    public void testIndexOfAnyBut_StringCharArray_12() {
        assertEquals(0, StringUtils.indexOfAnyBut("aba", 'z'));
    }

    @Test
    public void testIndexOfAnyBut_StringString_1() {
        assertEquals(-1, StringUtils.indexOfAnyBut(null, (String) null));
    }

    @Test
    public void testIndexOfAnyBut_StringString_2() {
        assertEquals(-1, StringUtils.indexOfAnyBut(null, ""));
    }

    @Test
    public void testIndexOfAnyBut_StringString_3() {
        assertEquals(-1, StringUtils.indexOfAnyBut(null, "ab"));
    }

    @Test
    public void testIndexOfAnyBut_StringString_4() {
        assertEquals(-1, StringUtils.indexOfAnyBut("", (String) null));
    }

    @Test
    public void testIndexOfAnyBut_StringString_5() {
        assertEquals(-1, StringUtils.indexOfAnyBut("", ""));
    }

    @Test
    public void testIndexOfAnyBut_StringString_6() {
        assertEquals(-1, StringUtils.indexOfAnyBut("", "ab"));
    }

    @Test
    public void testIndexOfAnyBut_StringString_7() {
        assertEquals(-1, StringUtils.indexOfAnyBut("zzabyycdxx", (String) null));
    }

    @Test
    public void testIndexOfAnyBut_StringString_8() {
        assertEquals(-1, StringUtils.indexOfAnyBut("zzabyycdxx", ""));
    }

    @Test
    public void testIndexOfAnyBut_StringString_9() {
        assertEquals(3, StringUtils.indexOfAnyBut("zzabyycdxx", "za"));
    }

    @Test
    public void testIndexOfAnyBut_StringString_10() {
        assertEquals(0, StringUtils.indexOfAnyBut("zzabyycdxx", "by"));
    }

    @Test
    public void testIndexOfAnyBut_StringString_11() {
        assertEquals(0, StringUtils.indexOfAnyBut("ab", "z"));
    }

    @Test
    public void testIndexOfIgnoreCase_String_1() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase(null, null));
    }

    @Test
    public void testIndexOfIgnoreCase_String_2() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase(null, ""));
    }

    @Test
    public void testIndexOfIgnoreCase_String_3() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase("", null));
    }

    @Test
    public void testIndexOfIgnoreCase_String_4() {
        assertEquals(0, StringUtils.indexOfIgnoreCase("", ""));
    }

    @Test
    public void testIndexOfIgnoreCase_String_5() {
        assertEquals(0, StringUtils.indexOfIgnoreCase("aabaabaa", "a"));
    }

    @Test
    public void testIndexOfIgnoreCase_String_6() {
        assertEquals(0, StringUtils.indexOfIgnoreCase("aabaabaa", "A"));
    }

    @Test
    public void testIndexOfIgnoreCase_String_7() {
        assertEquals(2, StringUtils.indexOfIgnoreCase("aabaabaa", "b"));
    }

    @Test
    public void testIndexOfIgnoreCase_String_8() {
        assertEquals(2, StringUtils.indexOfIgnoreCase("aabaabaa", "B"));
    }

    @Test
    public void testIndexOfIgnoreCase_String_9() {
        assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "ab"));
    }

    @Test
    public void testIndexOfIgnoreCase_String_10() {
        assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB"));
    }

    @Test
    public void testIndexOfIgnoreCase_String_11() {
        assertEquals(0, StringUtils.indexOfIgnoreCase("aabaabaa", ""));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_1() {
        assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", -1));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_2() {
        assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_3() {
        assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 1));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_4() {
        assertEquals(4, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 2));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_5() {
        assertEquals(4, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 3));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_6() {
        assertEquals(4, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 4));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_7() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 5));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_8() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 6));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_9() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 7));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_10() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 8));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_11() {
        assertEquals(1, StringUtils.indexOfIgnoreCase("aab", "AB", 1));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_12() {
        assertEquals(5, StringUtils.indexOfIgnoreCase("aabaabaa", "", 5));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_13() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase("ab", "AAB", 0));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_14() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase("aab", "AAB", 1));
    }

    @Test
    public void testIndexOfIgnoreCase_StringInt_15() {
        assertEquals(-1, StringUtils.indexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void testLANG1241_1_1() {
        assertEquals(0, StringUtils.ordinalIndexOf("abaabaab", "ab", 1));
    }

    @Test
    public void testLANG1241_1_2() {
        assertEquals(3, StringUtils.ordinalIndexOf("abaabaab", "ab", 2));
    }

    @Test
    public void testLANG1241_1_3() {
        assertEquals(6, StringUtils.ordinalIndexOf("abaabaab", "ab", 3));
    }

    @Test
    public void testLANG1241_2_1() {
        assertEquals(0, StringUtils.ordinalIndexOf("abababa", "aba", 1));
    }

    @Test
    public void testLANG1241_2_2() {
        assertEquals(2, StringUtils.ordinalIndexOf("abababa", "aba", 2));
    }

    @Test
    public void testLANG1241_2_3() {
        assertEquals(4, StringUtils.ordinalIndexOf("abababa", "aba", 3));
    }

    @Test
    public void testLANG1241_2_4() {
        assertEquals(0, StringUtils.ordinalIndexOf("abababab", "abab", 1));
    }

    @Test
    public void testLANG1241_2_5() {
        assertEquals(2, StringUtils.ordinalIndexOf("abababab", "abab", 2));
    }

    @Test
    public void testLANG1241_2_6() {
        assertEquals(4, StringUtils.ordinalIndexOf("abababab", "abab", 3));
    }

    @Test
    public void testLastIndexOf_char_1() {
        assertEquals(-1, StringUtils.lastIndexOf(null, ' '));
    }

    @Test
    public void testLastIndexOf_char_2() {
        assertEquals(-1, StringUtils.lastIndexOf("", ' '));
    }

    @Test
    public void testLastIndexOf_char_3() {
        assertEquals(7, StringUtils.lastIndexOf("aabaabaa", 'a'));
    }

    @Test
    public void testLastIndexOf_char_4() {
        assertEquals(5, StringUtils.lastIndexOf("aabaabaa", 'b'));
    }

    @Test
    public void testLastIndexOf_char_5() {
        assertEquals(5, StringUtils.lastIndexOf(new StringBuilder("aabaabaa"), 'b'));
    }

    @Test
    public void testLastIndexOf_String_1() {
        assertEquals(-1, StringUtils.lastIndexOf(null, null));
    }

    @Test
    public void testLastIndexOf_String_2() {
        assertEquals(-1, StringUtils.lastIndexOf("", null));
    }

    @Test
    public void testLastIndexOf_String_3() {
        assertEquals(-1, StringUtils.lastIndexOf("", "a"));
    }

    @Test
    public void testLastIndexOf_String_4() {
        assertEquals(0, StringUtils.lastIndexOf("", ""));
    }

    @Test
    public void testLastIndexOf_String_5() {
        assertEquals(8, StringUtils.lastIndexOf("aabaabaa", ""));
    }

    @Test
    public void testLastIndexOf_String_6() {
        assertEquals(7, StringUtils.lastIndexOf("aabaabaa", "a"));
    }

    @Test
    public void testLastIndexOf_String_7() {
        assertEquals(5, StringUtils.lastIndexOf("aabaabaa", "b"));
    }

    @Test
    public void testLastIndexOf_String_8() {
        assertEquals(4, StringUtils.lastIndexOf("aabaabaa", "ab"));
    }

    @Test
    public void testLastIndexOf_String_9() {
        assertEquals(4, StringUtils.lastIndexOf(new StringBuilder("aabaabaa"), "ab"));
    }

    @Test
    public void testLastIndexOf_StringInt_1() {
        assertEquals(-1, StringUtils.lastIndexOf(null, null, 0));
    }

    @Test
    public void testLastIndexOf_StringInt_2() {
        assertEquals(-1, StringUtils.lastIndexOf(null, null, -1));
    }

    @Test
    public void testLastIndexOf_StringInt_3() {
        assertEquals(-1, StringUtils.lastIndexOf(null, "", 0));
    }

    @Test
    public void testLastIndexOf_StringInt_4() {
        assertEquals(-1, StringUtils.lastIndexOf(null, "", -1));
    }

    @Test
    public void testLastIndexOf_StringInt_5() {
        assertEquals(-1, StringUtils.lastIndexOf("", null, 0));
    }

    @Test
    public void testLastIndexOf_StringInt_6() {
        assertEquals(-1, StringUtils.lastIndexOf("", null, -1));
    }

    @Test
    public void testLastIndexOf_StringInt_7() {
        assertEquals(0, StringUtils.lastIndexOf("", "", 0));
    }

    @Test
    public void testLastIndexOf_StringInt_8() {
        assertEquals(-1, StringUtils.lastIndexOf("", "", -1));
    }

    @Test
    public void testLastIndexOf_StringInt_9() {
        assertEquals(0, StringUtils.lastIndexOf("", "", 9));
    }

    @Test
    public void testLastIndexOf_StringInt_10() {
        assertEquals(0, StringUtils.lastIndexOf("abc", "", 0));
    }

    @Test
    public void testLastIndexOf_StringInt_11() {
        assertEquals(-1, StringUtils.lastIndexOf("abc", "", -1));
    }

    @Test
    public void testLastIndexOf_StringInt_12() {
        assertEquals(3, StringUtils.lastIndexOf("abc", "", 9));
    }

    @Test
    public void testLastIndexOf_StringInt_13() {
        assertEquals(7, StringUtils.lastIndexOf("aabaabaa", "a", 8));
    }

    @Test
    public void testLastIndexOf_StringInt_14() {
        assertEquals(5, StringUtils.lastIndexOf("aabaabaa", "b", 8));
    }

    @Test
    public void testLastIndexOf_StringInt_15() {
        assertEquals(4, StringUtils.lastIndexOf("aabaabaa", "ab", 8));
    }

    @Test
    public void testLastIndexOf_StringInt_16() {
        assertEquals(2, StringUtils.lastIndexOf("aabaabaa", "b", 3));
    }

    @Test
    public void testLastIndexOf_StringInt_17() {
        assertEquals(5, StringUtils.lastIndexOf("aabaabaa", "b", 9));
    }

    @Test
    public void testLastIndexOf_StringInt_18() {
        assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "b", -1));
    }

    @Test
    public void testLastIndexOf_StringInt_19() {
        assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "b", 0));
    }

    @Test
    public void testLastIndexOf_StringInt_20() {
        assertEquals(0, StringUtils.lastIndexOf("aabaabaa", "a", 0));
    }

    @Test
    public void testLastIndexOf_StringInt_21() {
        assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "a", -1));
    }

    @Test
    public void testLastIndexOf_StringInt_22() {
        assertEquals(7, StringUtils.lastIndexOf("12345678", "8", 9));
    }

    @Test
    public void testLastIndexOf_StringInt_23() {
        assertEquals(7, StringUtils.lastIndexOf("12345678", "8", 8));
    }

    @Test
    public void testLastIndexOf_StringInt_24() {
        assertEquals(7, StringUtils.lastIndexOf("12345678", "8", 7));
    }

    @Test
    public void testLastIndexOf_StringInt_25() {
        assertEquals(-1, StringUtils.lastIndexOf("12345678", "8", 6));
    }

    @Test
    public void testLastIndexOf_StringInt_26() {
        assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "b", 1));
    }

    @Test
    public void testLastIndexOf_StringInt_27() {
        assertEquals(2, StringUtils.lastIndexOf("aabaabaa", "b", 2));
    }

    @Test
    public void testLastIndexOf_StringInt_28() {
        assertEquals(2, StringUtils.lastIndexOf("aabaabaa", "ba", 2));
    }

    @Test
    public void testLastIndexOf_StringInt_29() {
        assertEquals(2, StringUtils.lastIndexOf("aabaabaa", "ba", 3));
    }

    @Test
    public void testLastIndexOf_StringInt_30() {
        assertEquals(2, StringUtils.lastIndexOf(new StringBuilder("aabaabaa"), "b", 3));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_1() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, null));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_2() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", null));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_3() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, ""));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_4() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", "a"));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_5() {
        assertEquals(0, StringUtils.lastIndexOfIgnoreCase("", ""));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_6() {
        assertEquals(8, StringUtils.lastIndexOfIgnoreCase("aabaabaa", ""));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_7() {
        assertEquals(7, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "a"));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_8() {
        assertEquals(7, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A"));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_9() {
        assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "b"));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_10() {
        assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B"));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_11() {
        assertEquals(4, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "ab"));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_12() {
        assertEquals(4, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB"));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_13() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("ab", "AAB"));
    }

    @Test
    public void testLastIndexOfIgnoreCase_String_14() {
        assertEquals(0, StringUtils.lastIndexOfIgnoreCase("aab", "AAB"));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_1() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, null, 0));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_2() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, null, -1));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_3() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, "", 0));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_4() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, "", -1));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_5() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", null, 0));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_6() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", null, -1));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_7() {
        assertEquals(0, StringUtils.lastIndexOfIgnoreCase("", "", 0));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_8() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", "", -1));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_9() {
        assertEquals(0, StringUtils.lastIndexOfIgnoreCase("", "", 9));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_10() {
        assertEquals(0, StringUtils.lastIndexOfIgnoreCase("abc", "", 0));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_11() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("abc", "", -1));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_12() {
        assertEquals(3, StringUtils.lastIndexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_13() {
        assertEquals(7, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_14() {
        assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_15() {
        assertEquals(4, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_16() {
        assertEquals(2, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 3));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_17() {
        assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_18() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_19() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_20() {
        assertEquals(0, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 0));
    }

    @Test
    public void testLastIndexOfIgnoreCase_StringInt_21() {
        assertEquals(1, StringUtils.lastIndexOfIgnoreCase("aab", "AB", 1));
    }

    @Test
    public void testLastOrdinalIndexOf_1() {
        assertEquals(-1, StringUtils.lastOrdinalIndexOf(null, "*", 42));
    }

    @Test
    public void testLastOrdinalIndexOf_2() {
        assertEquals(-1, StringUtils.lastOrdinalIndexOf("*", null, 42));
    }

    @Test
    public void testLastOrdinalIndexOf_3() {
        assertEquals(0, StringUtils.lastOrdinalIndexOf("", "", 42));
    }

    @Test
    public void testLastOrdinalIndexOf_4() {
        assertEquals(7, StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 1));
    }

    @Test
    public void testLastOrdinalIndexOf_5() {
        assertEquals(6, StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 2));
    }

    @Test
    public void testLastOrdinalIndexOf_6() {
        assertEquals(5, StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 1));
    }

    @Test
    public void testLastOrdinalIndexOf_7() {
        assertEquals(2, StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 2));
    }

    @Test
    public void testLastOrdinalIndexOf_8() {
        assertEquals(4, StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1));
    }

    @Test
    public void testLastOrdinalIndexOf_9() {
        assertEquals(1, StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2));
    }

    @Test
    public void testLastOrdinalIndexOf_10() {
        assertEquals(8, StringUtils.lastOrdinalIndexOf("aabaabaa", "", 1));
    }

    @Test
    public void testLastOrdinalIndexOf_11() {
        assertEquals(8, StringUtils.lastOrdinalIndexOf("aabaabaa", "", 2));
    }

    @Test
    public void testOrdinalIndexOf_1() {
        assertEquals(-1, StringUtils.ordinalIndexOf(null, null, Integer.MIN_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_2() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", null, Integer.MIN_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_3() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", "", Integer.MIN_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_4() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "a", Integer.MIN_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_5() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "b", Integer.MIN_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_6() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "ab", Integer.MIN_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_7() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "", Integer.MIN_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_8() {
        assertEquals(-1, StringUtils.ordinalIndexOf(null, null, -1));
    }

    @Test
    public void testOrdinalIndexOf_9() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", null, -1));
    }

    @Test
    public void testOrdinalIndexOf_10() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", "", -1));
    }

    @Test
    public void testOrdinalIndexOf_11() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "a", -1));
    }

    @Test
    public void testOrdinalIndexOf_12() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "b", -1));
    }

    @Test
    public void testOrdinalIndexOf_13() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "ab", -1));
    }

    @Test
    public void testOrdinalIndexOf_14() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "", -1));
    }

    @Test
    public void testOrdinalIndexOf_15() {
        assertEquals(-1, StringUtils.ordinalIndexOf(null, null, 0));
    }

    @Test
    public void testOrdinalIndexOf_16() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", null, 0));
    }

    @Test
    public void testOrdinalIndexOf_17() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", "", 0));
    }

    @Test
    public void testOrdinalIndexOf_18() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "a", 0));
    }

    @Test
    public void testOrdinalIndexOf_19() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "b", 0));
    }

    @Test
    public void testOrdinalIndexOf_20() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "ab", 0));
    }

    @Test
    public void testOrdinalIndexOf_21() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "", 0));
    }

    @Test
    public void testOrdinalIndexOf_22() {
        assertEquals(-1, StringUtils.ordinalIndexOf(null, null, 1));
    }

    @Test
    public void testOrdinalIndexOf_23() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", null, 1));
    }

    @Test
    public void testOrdinalIndexOf_24() {
        assertEquals(0, StringUtils.ordinalIndexOf("", "", 1));
    }

    @Test
    public void testOrdinalIndexOf_25() {
        assertEquals(0, StringUtils.ordinalIndexOf("aabaabaa", "a", 1));
    }

    @Test
    public void testOrdinalIndexOf_26() {
        assertEquals(2, StringUtils.ordinalIndexOf("aabaabaa", "b", 1));
    }

    @Test
    public void testOrdinalIndexOf_27() {
        assertEquals(1, StringUtils.ordinalIndexOf("aabaabaa", "ab", 1));
    }

    @Test
    public void testOrdinalIndexOf_28() {
        assertEquals(0, StringUtils.ordinalIndexOf("aabaabaa", "", 1));
    }

    @Test
    public void testOrdinalIndexOf_29() {
        assertEquals(-1, StringUtils.ordinalIndexOf(null, null, 2));
    }

    @Test
    public void testOrdinalIndexOf_30() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", null, 2));
    }

    @Test
    public void testOrdinalIndexOf_31() {
        assertEquals(0, StringUtils.ordinalIndexOf("", "", 2));
    }

    @Test
    public void testOrdinalIndexOf_32() {
        assertEquals(1, StringUtils.ordinalIndexOf("aabaabaa", "a", 2));
    }

    @Test
    public void testOrdinalIndexOf_33() {
        assertEquals(5, StringUtils.ordinalIndexOf("aabaabaa", "b", 2));
    }

    @Test
    public void testOrdinalIndexOf_34() {
        assertEquals(4, StringUtils.ordinalIndexOf("aabaabaa", "ab", 2));
    }

    @Test
    public void testOrdinalIndexOf_35() {
        assertEquals(0, StringUtils.ordinalIndexOf("aabaabaa", "", 2));
    }

    @Test
    public void testOrdinalIndexOf_36() {
        assertEquals(-1, StringUtils.ordinalIndexOf(null, null, Integer.MAX_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_37() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", null, Integer.MAX_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_38() {
        assertEquals(0, StringUtils.ordinalIndexOf("", "", Integer.MAX_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_39() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "a", Integer.MAX_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_40() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "b", Integer.MAX_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_41() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "ab", Integer.MAX_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_42() {
        assertEquals(0, StringUtils.ordinalIndexOf("aabaabaa", "", Integer.MAX_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_43() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 0));
    }

    @Test
    public void testOrdinalIndexOf_44() {
        assertEquals(0, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 1));
    }

    @Test
    public void testOrdinalIndexOf_45() {
        assertEquals(1, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 2));
    }

    @Test
    public void testOrdinalIndexOf_46() {
        assertEquals(2, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 3));
    }

    @Test
    public void testOrdinalIndexOf_47() {
        assertEquals(3, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 4));
    }

    @Test
    public void testOrdinalIndexOf_48() {
        assertEquals(4, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 5));
    }

    @Test
    public void testOrdinalIndexOf_49() {
        assertEquals(5, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 6));
    }

    @Test
    public void testOrdinalIndexOf_50() {
        assertEquals(6, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 7));
    }

    @Test
    public void testOrdinalIndexOf_51() {
        assertEquals(7, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 8));
    }

    @Test
    public void testOrdinalIndexOf_52() {
        assertEquals(8, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 9));
    }

    @Test
    public void testOrdinalIndexOf_53() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 10));
    }

    @Test
    public void testOrdinalIndexOf_54() {
        assertEquals(0, StringUtils.ordinalIndexOf("aaaaaa", "aa", 1));
    }

    @Test
    public void testOrdinalIndexOf_55() {
        assertEquals(1, StringUtils.ordinalIndexOf("aaaaaa", "aa", 2));
    }

    @Test
    public void testOrdinalIndexOf_56() {
        assertEquals(2, StringUtils.ordinalIndexOf("aaaaaa", "aa", 3));
    }

    @Test
    public void testOrdinalIndexOf_57() {
        assertEquals(3, StringUtils.ordinalIndexOf("aaaaaa", "aa", 4));
    }

    @Test
    public void testOrdinalIndexOf_58() {
        assertEquals(4, StringUtils.ordinalIndexOf("aaaaaa", "aa", 5));
    }

    @Test
    public void testOrdinalIndexOf_59() {
        assertEquals(-1, StringUtils.ordinalIndexOf("aaaaaa", "aa", 6));
    }

    @Test
    public void testOrdinalIndexOf_60() {
        assertEquals(0, StringUtils.ordinalIndexOf("ababab", "aba", 1));
    }

    @Test
    public void testOrdinalIndexOf_61() {
        assertEquals(2, StringUtils.ordinalIndexOf("ababab", "aba", 2));
    }

    @Test
    public void testOrdinalIndexOf_62() {
        assertEquals(-1, StringUtils.ordinalIndexOf("ababab", "aba", 3));
    }

    @Test
    public void testOrdinalIndexOf_63() {
        assertEquals(0, StringUtils.ordinalIndexOf("abababab", "abab", 1));
    }

    @Test
    public void testOrdinalIndexOf_64() {
        assertEquals(2, StringUtils.ordinalIndexOf("abababab", "abab", 2));
    }

    @Test
    public void testOrdinalIndexOf_65() {
        assertEquals(4, StringUtils.ordinalIndexOf("abababab", "abab", 3));
    }

    @Test
    public void testOrdinalIndexOf_66() {
        assertEquals(-1, StringUtils.ordinalIndexOf("abababab", "abab", 4));
    }
}
