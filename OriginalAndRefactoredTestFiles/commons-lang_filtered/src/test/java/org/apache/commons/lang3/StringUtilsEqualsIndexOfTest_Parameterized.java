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

public class StringUtilsEqualsIndexOfTest_Parameterized extends AbstractLangTest {

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
    public void testCompare_StringStringBoolean_1() {
        assertEquals(0, StringUtils.compare(null, null, false));
    }

    @Test
    public void testCompare_StringStringBoolean_6() {
        assertEquals(0, StringUtils.compare("abc", "abc", false));
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
    public void testCompareIgnoreCase_StringStringBoolean_1() {
        assertEquals(0, StringUtils.compareIgnoreCase(null, null, false));
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
    public void testIndexOfAnyBut_StringString_1() {
        assertEquals(-1, StringUtils.indexOfAnyBut(null, (String) null));
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
    public void testIndexOfIgnoreCase_StringInt_1() {
        assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", -1));
    }

    @Test
    public void testLastIndexOf_char_1() {
        assertEquals(-1, StringUtils.lastIndexOf(null, ' '));
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
    public void testLastIndexOfIgnoreCase_StringInt_19() {
        assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0));
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
    public void testOrdinalIndexOf_1() {
        assertEquals(-1, StringUtils.ordinalIndexOf(null, null, Integer.MIN_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_2() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", null, Integer.MIN_VALUE));
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
    public void testOrdinalIndexOf_36() {
        assertEquals(-1, StringUtils.ordinalIndexOf(null, null, Integer.MAX_VALUE));
    }

    @Test
    public void testOrdinalIndexOf_37() {
        assertEquals(-1, StringUtils.ordinalIndexOf("", null, Integer.MAX_VALUE));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompare_StringString_5to11")
    public void testCompare_StringString_5to11(int param1, String param2, String param3) {
        assertTrue(StringUtils.compare(param2, param3) < param1);
    }

    static public Stream<Arguments> Provider_testCompare_StringString_5to11() {
        return Stream.of(arguments(0, "a", "b"), arguments(0, "b", "a"), arguments(0, "a", "B"), arguments(0, "abc", "abd"), arguments(0, "ab", "abc"), arguments(0, "ab", "ab "), arguments(0, "abc", "ab "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompare_StringStringBoolean_2to3")
    public void testCompare_StringStringBoolean_2to3(int param1, String param2) {
        assertTrue(StringUtils.compare(param2, "a", true) < param1);
    }

    static public Stream<Arguments> Provider_testCompare_StringStringBoolean_2to3() {
        return Stream.of(arguments(0, "a"), arguments(0, "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompare_StringStringBoolean_4to5")
    public void testCompare_StringStringBoolean_4to5(int param1, String param2) {
        assertTrue(StringUtils.compare(param2, null, true) > param1);
    }

    static public Stream<Arguments> Provider_testCompare_StringStringBoolean_4to5() {
        return Stream.of(arguments(0, "a"), arguments(0, "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompare_StringStringBoolean_7to13")
    public void testCompare_StringStringBoolean_7to13(int param1, String param2, String param3) {
        assertTrue(StringUtils.compare(param2, param3, false) < param1);
    }

    static public Stream<Arguments> Provider_testCompare_StringStringBoolean_7to13() {
        return Stream.of(arguments(0, "a", "b"), arguments(0, "b", "a"), arguments(0, "a", "B"), arguments(0, "abc", "abd"), arguments(0, "ab", "abc"), arguments(0, "ab", "ab "), arguments(0, "abc", "ab "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareIgnoreCase_StringString_4to5")
    public void testCompareIgnoreCase_StringString_4to5(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.compareIgnoreCase(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompareIgnoreCase_StringString_4to5() {
        return Stream.of(arguments(0, "abc", "abc"), arguments(0, "abc", "ABC"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareIgnoreCase_StringString_6to13")
    public void testCompareIgnoreCase_StringString_6to13(int param1, String param2, String param3) {
        assertTrue(StringUtils.compareIgnoreCase(param2, param3) < param1);
    }

    static public Stream<Arguments> Provider_testCompareIgnoreCase_StringString_6to13() {
        return Stream.of(arguments(0, "a", "b"), arguments(0, "b", "a"), arguments(0, "a", "B"), arguments(0, "A", "b"), arguments(0, "abc", "ABD"), arguments(0, "ab", "ABC"), arguments(0, "ab", "AB "), arguments(0, "abc", "AB "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareIgnoreCase_StringStringBoolean_2to3")
    public void testCompareIgnoreCase_StringStringBoolean_2to3(int param1, String param2) {
        assertTrue(StringUtils.compareIgnoreCase(param2, "a", true) < param1);
    }

    static public Stream<Arguments> Provider_testCompareIgnoreCase_StringStringBoolean_2to3() {
        return Stream.of(arguments(0, "a"), arguments(0, "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareIgnoreCase_StringStringBoolean_4to5")
    public void testCompareIgnoreCase_StringStringBoolean_4to5(int param1, String param2) {
        assertTrue(StringUtils.compareIgnoreCase(param2, null, true) > param1);
    }

    static public Stream<Arguments> Provider_testCompareIgnoreCase_StringStringBoolean_4to5() {
        return Stream.of(arguments(0, "a"), arguments(0, "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareIgnoreCase_StringStringBoolean_6to7")
    public void testCompareIgnoreCase_StringStringBoolean_6to7(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.compareIgnoreCase(param2, param3, false));
    }

    static public Stream<Arguments> Provider_testCompareIgnoreCase_StringStringBoolean_6to7() {
        return Stream.of(arguments(0, "abc", "abc"), arguments(0, "abc", "ABC"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareIgnoreCase_StringStringBoolean_8to15")
    public void testCompareIgnoreCase_StringStringBoolean_8to15(int param1, String param2, String param3) {
        assertTrue(StringUtils.compareIgnoreCase(param2, param3, false) < param1);
    }

    static public Stream<Arguments> Provider_testCompareIgnoreCase_StringStringBoolean_8to15() {
        return Stream.of(arguments(0, "a", "b"), arguments(0, "b", "a"), arguments(0, "a", "B"), arguments(0, "A", "b"), arguments(0, "abc", "ABD"), arguments(0, "ab", "ABC"), arguments(0, "ab", "AB "), arguments(0, "abc", "AB "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOf_char_3_3to4_4to7")
    public void testIndexOf_char_3_3to4_4to7(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.indexOf(param2, param3));
    }

    static public Stream<Arguments> Provider_testIndexOf_char_3_3to4_4to7() {
        return Stream.of(arguments(0, "aabaabaa", "a"), arguments(2, "aabaabaa", "b"), arguments(0, "", ""), arguments(0, "aabaabaa", "a"), arguments(2, "aabaabaa", "b"), arguments(1, "aabaabaa", "ab"), arguments(0, "aabaabaa", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOf_char_5_8")
    public void testIndexOf_char_5_8(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.indexOf(new StringBuilder(param3), param2));
    }

    static public Stream<Arguments> Provider_testIndexOf_char_5_8() {
        return Stream.of(arguments(2, "b", "aabaabaa"), arguments(2, "b", "aabaabaa"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOf_StringInt_7_9to10_12to17_20to23")
    public void testIndexOf_StringInt_7_9to10_12to17_20to23(int param1, String param2, String param3, int param4) {
        assertEquals(param1, StringUtils.indexOf(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testIndexOf_StringInt_7_9to10_12to17_20to23() {
        return Stream.of(arguments(0, "", "", 0), arguments(0, "", "", 9), arguments(0, "abc", "", 0), arguments(3, "abc", "", 9), arguments(3, "abc", "", 3), arguments(0, "aabaabaa", "a", 0), arguments(2, "aabaabaa", "b", 0), arguments(1, "aabaabaa", "ab", 0), arguments(5, "aabaabaa", "b", 3), arguments(2, "aabaabaa", "", 2), arguments(7, 12345678, 8, 5), arguments(7, 12345678, 8, 6), arguments(7, 12345678, 8, 7));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOf_StringInt_8_11_19")
    public void testIndexOf_StringInt_8_11_19(int param1, String param2, String param3, int param4) {
        assertEquals(param1, StringUtils.indexOf(param2, param3, -param4));
    }

    static public Stream<Arguments> Provider_testIndexOf_StringInt_8_11_19() {
        return Stream.of(arguments(0, "", "", 1), arguments(0, "abc", "", 1), arguments(2, "aabaabaa", "b", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOf_StringInt_18_24")
    public void testIndexOf_StringInt_18_24(int param1, String param2, String param3, int param4) {
        assertEquals(-param1, StringUtils.indexOf(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testIndexOf_StringInt_18_24() {
        return Stream.of(arguments(1, "aabaabaa", "b", 9), arguments(1, 12345678, 8, 8));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAny_StringString_2to3")
    public void testIndexOfAny_StringString_2to3(int param1, String param2) {
        assertEquals(-param1, StringUtils.indexOfAny(param2, ""));
    }

    static public Stream<Arguments> Provider_testIndexOfAny_StringString_2to3() {
        return Stream.of(arguments(1, ""), arguments(1, "ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAny_StringString_4_7")
    public void testIndexOfAny_StringString_4_7(int param1, String param2) {
        assertEquals(-param1, StringUtils.indexOfAny(param2, (String) null));
    }

    static public Stream<Arguments> Provider_testIndexOfAny_StringString_4_7() {
        return Stream.of(arguments(1, ""), arguments(1, "zzabyycdxx"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAny_StringString_5to6_8_11")
    public void testIndexOfAny_StringString_5to6_8_11(int param1, String param2, String param3) {
        assertEquals(-param1, StringUtils.indexOfAny(param2, param3));
    }

    static public Stream<Arguments> Provider_testIndexOfAny_StringString_5to6_8_11() {
        return Stream.of(arguments(1, "", ""), arguments(1, "", "ab"), arguments(1, "zzabyycdxx", ""), arguments(1, "ab", "z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAny_StringString_9to10")
    public void testIndexOfAny_StringString_9to10(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.indexOfAny(param2, param3));
    }

    static public Stream<Arguments> Provider_testIndexOfAny_StringString_9to10() {
        return Stream.of(arguments(0, "zzabyycdxx", "za"), arguments(3, "zzabyycdxx", "by"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAnyBut_StringCharArray_4_7")
    public void testIndexOfAnyBut_StringCharArray_4_7(int param1, String param2) {
        assertEquals(-param1, StringUtils.indexOfAnyBut(param2, (char[]) null));
    }

    static public Stream<Arguments> Provider_testIndexOfAnyBut_StringCharArray_4_7() {
        return Stream.of(arguments(1, ""), arguments(1, "zzabyycdxx"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAnyBut_StringCharArray_5_8")
    public void testIndexOfAnyBut_StringCharArray_5_8(int param1, String param2) {
        assertEquals(-param1, StringUtils.indexOfAnyBut(param2));
    }

    static public Stream<Arguments> Provider_testIndexOfAnyBut_StringCharArray_5_8() {
        return Stream.of(arguments(1, ""), arguments(1, "zzabyycdxx"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAnyBut_StringCharArray_6_11")
    public void testIndexOfAnyBut_StringCharArray_6_11(int param1, String param2, String param3, String param4) {
        assertEquals(-param1, StringUtils.indexOfAnyBut(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testIndexOfAnyBut_StringCharArray_6_11() {
        return Stream.of(arguments(1, "", "a", "b"), arguments(1, "aba", "a", "b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAnyBut_StringCharArray_9to10")
    public void testIndexOfAnyBut_StringCharArray_9to10(int param1, String param2, String param3, String param4) {
        assertEquals(param1, StringUtils.indexOfAnyBut(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testIndexOfAnyBut_StringCharArray_9to10() {
        return Stream.of(arguments(3, "zzabyycdxx", "z", "a"), arguments(0, "zzabyycdxx", "b", "y"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAnyBut_StringCharArray_9to12")
    public void testIndexOfAnyBut_StringCharArray_9to12(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.indexOfAnyBut(param2, param3));
    }

    static public Stream<Arguments> Provider_testIndexOfAnyBut_StringCharArray_9to12() {
        return Stream.of(arguments(0, "aba", "z"), arguments(3, "zzabyycdxx", "za"), arguments(0, "zzabyycdxx", "by"), arguments(0, "ab", "z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAnyBut_StringString_2to3")
    public void testIndexOfAnyBut_StringString_2to3(int param1, String param2) {
        assertEquals(-param1, StringUtils.indexOfAnyBut(param2, ""));
    }

    static public Stream<Arguments> Provider_testIndexOfAnyBut_StringString_2to3() {
        return Stream.of(arguments(1, ""), arguments(1, "ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAnyBut_StringString_4_7")
    public void testIndexOfAnyBut_StringString_4_7(int param1, String param2) {
        assertEquals(-param1, StringUtils.indexOfAnyBut(param2, (String) null));
    }

    static public Stream<Arguments> Provider_testIndexOfAnyBut_StringString_4_7() {
        return Stream.of(arguments(1, ""), arguments(1, "zzabyycdxx"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfAnyBut_StringString_5to6_8")
    public void testIndexOfAnyBut_StringString_5to6_8(int param1, String param2, String param3) {
        assertEquals(-param1, StringUtils.indexOfAnyBut(param2, param3));
    }

    static public Stream<Arguments> Provider_testIndexOfAnyBut_StringString_5to6_8() {
        return Stream.of(arguments(1, "", ""), arguments(1, "", "ab"), arguments(1, "zzabyycdxx", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfIgnoreCase_String_4to11")
    public void testIndexOfIgnoreCase_String_4to11(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.indexOfIgnoreCase(param2, param3));
    }

    static public Stream<Arguments> Provider_testIndexOfIgnoreCase_String_4to11() {
        return Stream.of(arguments(0, "", ""), arguments(0, "aabaabaa", "a"), arguments(0, "aabaabaa", "A"), arguments(2, "aabaabaa", "b"), arguments(2, "aabaabaa", "B"), arguments(1, "aabaabaa", "ab"), arguments(1, "aabaabaa", "AB"), arguments(0, "aabaabaa", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfIgnoreCase_StringInt_2to6_11to12")
    public void testIndexOfIgnoreCase_StringInt_2to6_11to12(int param1, String param2, String param3, int param4) {
        assertEquals(param1, StringUtils.indexOfIgnoreCase(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testIndexOfIgnoreCase_StringInt_2to6_11to12() {
        return Stream.of(arguments(1, "aabaabaa", "AB", 0), arguments(1, "aabaabaa", "AB", 1), arguments(4, "aabaabaa", "AB", 2), arguments(4, "aabaabaa", "AB", 3), arguments(4, "aabaabaa", "AB", 4), arguments(1, "aab", "AB", 1), arguments(5, "aabaabaa", "", 5));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIndexOfIgnoreCase_StringInt_7to10_13to15")
    public void testIndexOfIgnoreCase_StringInt_7to10_13to15(int param1, String param2, String param3, int param4) {
        assertEquals(-param1, StringUtils.indexOfIgnoreCase(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testIndexOfIgnoreCase_StringInt_7to10_13to15() {
        return Stream.of(arguments(1, "aabaabaa", "AB", 5), arguments(1, "aabaabaa", "AB", 6), arguments(1, "aabaabaa", "AB", 7), arguments(1, "aabaabaa", "AB", 8), arguments(1, "ab", "AAB", 0), arguments(1, "aab", "AAB", 1), arguments(1, "abc", "", 9));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLANG1241_1_1_1to2_2to3_3to6_24to28_31to35_44to52_54to58_60to61_63to65")
    public void testLANG1241_1_1_1to2_2to3_3to6_24to28_31to35_44to52_54to58_60to61_63to65(int param1, String param2, String param3, int param4) {
        assertEquals(param1, StringUtils.ordinalIndexOf(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testLANG1241_1_1_1to2_2to3_3to6_24to28_31to35_44to52_54to58_60to61_63to65() {
        return Stream.of(arguments(0, "abaabaab", "ab", 1), arguments(3, "abaabaab", "ab", 2), arguments(6, "abaabaab", "ab", 3), arguments(0, "abababa", "aba", 1), arguments(2, "abababa", "aba", 2), arguments(4, "abababa", "aba", 3), arguments(0, "abababab", "abab", 1), arguments(2, "abababab", "abab", 2), arguments(4, "abababab", "abab", 3), arguments(0, "", "", 1), arguments(0, "aabaabaa", "a", 1), arguments(2, "aabaabaa", "b", 1), arguments(1, "aabaabaa", "ab", 1), arguments(0, "aabaabaa", "", 1), arguments(0, "", "", 2), arguments(1, "aabaabaa", "a", 2), arguments(5, "aabaabaa", "b", 2), arguments(4, "aabaabaa", "ab", 2), arguments(0, "aabaabaa", "", 2), arguments(0, "aaaaaaaaa", "a", 1), arguments(1, "aaaaaaaaa", "a", 2), arguments(2, "aaaaaaaaa", "a", 3), arguments(3, "aaaaaaaaa", "a", 4), arguments(4, "aaaaaaaaa", "a", 5), arguments(5, "aaaaaaaaa", "a", 6), arguments(6, "aaaaaaaaa", "a", 7), arguments(7, "aaaaaaaaa", "a", 8), arguments(8, "aaaaaaaaa", "a", 9), arguments(0, "aaaaaa", "aa", 1), arguments(1, "aaaaaa", "aa", 2), arguments(2, "aaaaaa", "aa", 3), arguments(3, "aaaaaa", "aa", 4), arguments(4, "aaaaaa", "aa", 5), arguments(0, "ababab", "aba", 1), arguments(2, "ababab", "aba", 2), arguments(0, "abababab", "abab", 1), arguments(2, "abababab", "abab", 2), arguments(4, "abababab", "abab", 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOf_char_2to3")
    public void testLastIndexOf_char_2to3(int param1, String param2, String param3) {
        assertEquals(-param1, StringUtils.lastIndexOf(param2, param3));
    }

    static public Stream<Arguments> Provider_testLastIndexOf_char_2to3() {
        return Stream.of(arguments(1, "", " "), arguments(1, "", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOf_char_3to4_4to8")
    public void testLastIndexOf_char_3to4_4to8(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.lastIndexOf(param2, param3));
    }

    static public Stream<Arguments> Provider_testLastIndexOf_char_3to4_4to8() {
        return Stream.of(arguments(7, "aabaabaa", "a"), arguments(5, "aabaabaa", "b"), arguments(0, "", ""), arguments(8, "aabaabaa", ""), arguments(7, "aabaabaa", "a"), arguments(5, "aabaabaa", "b"), arguments(4, "aabaabaa", "ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOf_char_5_9")
    public void testLastIndexOf_char_5_9(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.lastIndexOf(new StringBuilder(param3), param2));
    }

    static public Stream<Arguments> Provider_testLastIndexOf_char_5_9() {
        return Stream.of(arguments(5, "b", "aabaabaa"), arguments(4, "ab", "aabaabaa"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOf_StringInt_7_9to10_12to17_20_22to24_27to29")
    public void testLastIndexOf_StringInt_7_9to10_12to17_20_22to24_27to29(int param1, String param2, String param3, int param4) {
        assertEquals(param1, StringUtils.lastIndexOf(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testLastIndexOf_StringInt_7_9to10_12to17_20_22to24_27to29() {
        return Stream.of(arguments(0, "", "", 0), arguments(0, "", "", 9), arguments(0, "abc", "", 0), arguments(3, "abc", "", 9), arguments(7, "aabaabaa", "a", 8), arguments(5, "aabaabaa", "b", 8), arguments(4, "aabaabaa", "ab", 8), arguments(2, "aabaabaa", "b", 3), arguments(5, "aabaabaa", "b", 9), arguments(0, "aabaabaa", "a", 0), arguments(7, 12345678, 8, 9), arguments(7, 12345678, 8, 8), arguments(7, 12345678, 8, 7), arguments(2, "aabaabaa", "b", 2), arguments(2, "aabaabaa", "ba", 2), arguments(2, "aabaabaa", "ba", 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOf_StringInt_8_11_18_21")
    public void testLastIndexOf_StringInt_8_11_18_21(int param1, String param2, String param3, int param4) {
        assertEquals(-param1, StringUtils.lastIndexOf(param2, param3, -param4));
    }

    static public Stream<Arguments> Provider_testLastIndexOf_StringInt_8_11_18_21() {
        return Stream.of(arguments(1, "", "", 1), arguments(1, "abc", "", 1), arguments(1, "aabaabaa", "b", 1), arguments(1, "aabaabaa", "a", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOf_StringInt_19_25to26")
    public void testLastIndexOf_StringInt_19_25to26(int param1, String param2, String param3, int param4) {
        assertEquals(-param1, StringUtils.lastIndexOf(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testLastIndexOf_StringInt_19_25to26() {
        return Stream.of(arguments(1, "aabaabaa", "b", 0), arguments(1, 12345678, 8, 6), arguments(1, "aabaabaa", "b", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOfIgnoreCase_String_4_13")
    public void testLastIndexOfIgnoreCase_String_4_13(int param1, String param2, String param3) {
        assertEquals(-param1, StringUtils.lastIndexOfIgnoreCase(param2, param3));
    }

    static public Stream<Arguments> Provider_testLastIndexOfIgnoreCase_String_4_13() {
        return Stream.of(arguments(1, "", "a"), arguments(1, "ab", "AAB"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOfIgnoreCase_String_5to12_14")
    public void testLastIndexOfIgnoreCase_String_5to12_14(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.lastIndexOfIgnoreCase(param2, param3));
    }

    static public Stream<Arguments> Provider_testLastIndexOfIgnoreCase_String_5to12_14() {
        return Stream.of(arguments(0, "", ""), arguments(8, "aabaabaa", ""), arguments(7, "aabaabaa", "a"), arguments(7, "aabaabaa", "A"), arguments(5, "aabaabaa", "b"), arguments(5, "aabaabaa", "B"), arguments(4, "aabaabaa", "ab"), arguments(4, "aabaabaa", "AB"), arguments(0, "aab", "AAB"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOfIgnoreCase_StringInt_7_9to10_12to17_20to21")
    public void testLastIndexOfIgnoreCase_StringInt_7_9to10_12to17_20to21(int param1, String param2, String param3, int param4) {
        assertEquals(param1, StringUtils.lastIndexOfIgnoreCase(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testLastIndexOfIgnoreCase_StringInt_7_9to10_12to17_20to21() {
        return Stream.of(arguments(0, "", "", 0), arguments(0, "", "", 9), arguments(0, "abc", "", 0), arguments(3, "abc", "", 9), arguments(7, "aabaabaa", "A", 8), arguments(5, "aabaabaa", "B", 8), arguments(4, "aabaabaa", "AB", 8), arguments(2, "aabaabaa", "B", 3), arguments(5, "aabaabaa", "B", 9), arguments(0, "aabaabaa", "A", 0), arguments(1, "aab", "AB", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastIndexOfIgnoreCase_StringInt_8_11_18")
    public void testLastIndexOfIgnoreCase_StringInt_8_11_18(int param1, String param2, String param3, int param4) {
        assertEquals(-param1, StringUtils.lastIndexOfIgnoreCase(param2, param3, -param4));
    }

    static public Stream<Arguments> Provider_testLastIndexOfIgnoreCase_StringInt_8_11_18() {
        return Stream.of(arguments(1, "", "", 1), arguments(1, "abc", "", 1), arguments(1, "aabaabaa", "B", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLastOrdinalIndexOf_3to11")
    public void testLastOrdinalIndexOf_3to11(int param1, String param2, String param3, int param4) {
        assertEquals(param1, StringUtils.lastOrdinalIndexOf(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testLastOrdinalIndexOf_3to11() {
        return Stream.of(arguments(0, "", "", 42), arguments(7, "aabaabaa", "a", 1), arguments(6, "aabaabaa", "a", 2), arguments(5, "aabaabaa", "b", 1), arguments(2, "aabaabaa", "b", 2), arguments(4, "aabaabaa", "ab", 1), arguments(1, "aabaabaa", "ab", 2), arguments(8, "aabaabaa", "", 1), arguments(8, "aabaabaa", "", 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOrdinalIndexOf_3to7")
    public void testOrdinalIndexOf_3to7(int param1, String param2, String param3) {
        assertEquals(-param1, StringUtils.ordinalIndexOf(param2, param3, Integer.MIN_VALUE));
    }

    static public Stream<Arguments> Provider_testOrdinalIndexOf_3to7() {
        return Stream.of(arguments(1, "", ""), arguments(1, "aabaabaa", "a"), arguments(1, "aabaabaa", "b"), arguments(1, "aabaabaa", "ab"), arguments(1, "aabaabaa", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOrdinalIndexOf_10to14")
    public void testOrdinalIndexOf_10to14(int param1, String param2, String param3, int param4) {
        assertEquals(-param1, StringUtils.ordinalIndexOf(param2, param3, -param4));
    }

    static public Stream<Arguments> Provider_testOrdinalIndexOf_10to14() {
        return Stream.of(arguments(1, "", "", 1), arguments(1, "aabaabaa", "a", 1), arguments(1, "aabaabaa", "b", 1), arguments(1, "aabaabaa", "ab", 1), arguments(1, "aabaabaa", "", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOrdinalIndexOf_15_22_29")
    public void testOrdinalIndexOf_15_22_29(int param1, int param2) {
        assertEquals(-param1, StringUtils.ordinalIndexOf(param2, null, 0));
    }

    static public Stream<Arguments> Provider_testOrdinalIndexOf_15_22_29() {
        return Stream.of(arguments(1, 0), arguments(1, 1), arguments(1, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOrdinalIndexOf_16_23_30")
    public void testOrdinalIndexOf_16_23_30(int param1, String param2, int param3) {
        assertEquals(-param1, StringUtils.ordinalIndexOf(param2, param3, 0));
    }

    static public Stream<Arguments> Provider_testOrdinalIndexOf_16_23_30() {
        return Stream.of(arguments(1, "", 0), arguments(1, "", 1), arguments(1, "", 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOrdinalIndexOf_17to21_43_53_59_62_66")
    public void testOrdinalIndexOf_17to21_43_53_59_62_66(int param1, String param2, String param3, int param4) {
        assertEquals(-param1, StringUtils.ordinalIndexOf(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testOrdinalIndexOf_17to21_43_53_59_62_66() {
        return Stream.of(arguments(1, "", "", 0), arguments(1, "aabaabaa", "a", 0), arguments(1, "aabaabaa", "b", 0), arguments(1, "aabaabaa", "ab", 0), arguments(1, "aabaabaa", "", 0), arguments(1, "aaaaaaaaa", "a", 0), arguments(1, "aaaaaaaaa", "a", 10), arguments(1, "aaaaaa", "aa", 6), arguments(1, "ababab", "aba", 3), arguments(1, "abababab", "abab", 4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOrdinalIndexOf_38_42")
    public void testOrdinalIndexOf_38_42(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.ordinalIndexOf(param2, param3, Integer.MAX_VALUE));
    }

    static public Stream<Arguments> Provider_testOrdinalIndexOf_38_42() {
        return Stream.of(arguments(0, "", ""), arguments(0, "aabaabaa", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOrdinalIndexOf_39to41")
    public void testOrdinalIndexOf_39to41(int param1, String param2, String param3) {
        assertEquals(-param1, StringUtils.ordinalIndexOf(param2, param3, Integer.MAX_VALUE));
    }

    static public Stream<Arguments> Provider_testOrdinalIndexOf_39to41() {
        return Stream.of(arguments(1, "aabaabaa", "a"), arguments(1, "aabaabaa", "b"), arguments(1, "aabaabaa", "ab"));
    }
}
