package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class StringUtilsSubstringTest_Purified extends AbstractLangTest {

    private static final String FOO = "foo";

    private static final String BAR = "bar";

    private static final String BAZ = "baz";

    private static final String FOOBAR = "foobar";

    private static final String SENTENCE = "foo bar baz";

    @Test
    public void testCountMatches_char_1() {
        assertEquals(0, StringUtils.countMatches(null, 'D'));
    }

    @Test
    public void testCountMatches_char_2() {
        assertEquals(5, StringUtils.countMatches("one long someone sentence of one", ' '));
    }

    @Test
    public void testCountMatches_char_3() {
        assertEquals(6, StringUtils.countMatches("one long someone sentence of one", 'o'));
    }

    @Test
    public void testCountMatches_char_4() {
        assertEquals(4, StringUtils.countMatches("oooooooooooo", "ooo"));
    }

    @Test
    public void testCountMatches_String_1() {
        assertEquals(0, StringUtils.countMatches(null, null));
    }

    @Test
    public void testCountMatches_String_2() {
        assertEquals(0, StringUtils.countMatches("blah", null));
    }

    @Test
    public void testCountMatches_String_3() {
        assertEquals(0, StringUtils.countMatches(null, "DD"));
    }

    @Test
    public void testCountMatches_String_4() {
        assertEquals(0, StringUtils.countMatches("x", ""));
    }

    @Test
    public void testCountMatches_String_5() {
        assertEquals(0, StringUtils.countMatches("", ""));
    }

    @Test
    public void testCountMatches_String_6() {
        assertEquals(3, StringUtils.countMatches("one long someone sentence of one", "one"));
    }

    @Test
    public void testCountMatches_String_7() {
        assertEquals(0, StringUtils.countMatches("one long someone sentence of one", "two"));
    }

    @Test
    public void testCountMatches_String_8() {
        assertEquals(4, StringUtils.countMatches("oooooooooooo", "ooo"));
    }

    @Test
    public void testCountMatches_String_9() {
        assertEquals(0, StringUtils.countMatches(null, "?"));
    }

    @Test
    public void testCountMatches_String_10() {
        assertEquals(0, StringUtils.countMatches("", "?"));
    }

    @Test
    public void testCountMatches_String_11() {
        assertEquals(0, StringUtils.countMatches("abba", null));
    }

    @Test
    public void testCountMatches_String_12() {
        assertEquals(0, StringUtils.countMatches("abba", ""));
    }

    @Test
    public void testCountMatches_String_13() {
        assertEquals(2, StringUtils.countMatches("abba", "a"));
    }

    @Test
    public void testCountMatches_String_14() {
        assertEquals(1, StringUtils.countMatches("abba", "ab"));
    }

    @Test
    public void testCountMatches_String_15() {
        assertEquals(0, StringUtils.countMatches("abba", "xxx"));
    }

    @Test
    public void testCountMatches_String_16() {
        assertEquals(1, StringUtils.countMatches("ababa", "aba"));
    }

    @Test
    public void testLeft_String_1() {
        assertSame(null, StringUtils.left(null, -1));
    }

    @Test
    public void testLeft_String_2() {
        assertSame(null, StringUtils.left(null, 0));
    }

    @Test
    public void testLeft_String_3() {
        assertSame(null, StringUtils.left(null, 2));
    }

    @Test
    public void testLeft_String_4() {
        assertEquals("", StringUtils.left("", -1));
    }

    @Test
    public void testLeft_String_5() {
        assertEquals("", StringUtils.left("", 0));
    }

    @Test
    public void testLeft_String_6() {
        assertEquals("", StringUtils.left("", 2));
    }

    @Test
    public void testLeft_String_7() {
        assertEquals("", StringUtils.left(FOOBAR, -1));
    }

    @Test
    public void testLeft_String_8() {
        assertEquals("", StringUtils.left(FOOBAR, 0));
    }

    @Test
    public void testLeft_String_9() {
        assertEquals(FOO, StringUtils.left(FOOBAR, 3));
    }

    @Test
    public void testLeft_String_10() {
        assertSame(FOOBAR, StringUtils.left(FOOBAR, 80));
    }

    @Test
    public void testMid_String_1() {
        assertSame(null, StringUtils.mid(null, -1, 0));
    }

    @Test
    public void testMid_String_2() {
        assertSame(null, StringUtils.mid(null, 0, -1));
    }

    @Test
    public void testMid_String_3() {
        assertSame(null, StringUtils.mid(null, 3, 0));
    }

    @Test
    public void testMid_String_4() {
        assertSame(null, StringUtils.mid(null, 3, 2));
    }

    @Test
    public void testMid_String_5() {
        assertEquals("", StringUtils.mid("", 0, -1));
    }

    @Test
    public void testMid_String_6() {
        assertEquals("", StringUtils.mid("", 0, 0));
    }

    @Test
    public void testMid_String_7() {
        assertEquals("", StringUtils.mid("", 0, 2));
    }

    @Test
    public void testMid_String_8() {
        assertEquals("", StringUtils.mid(FOOBAR, 3, -1));
    }

    @Test
    public void testMid_String_9() {
        assertEquals("", StringUtils.mid(FOOBAR, 3, 0));
    }

    @Test
    public void testMid_String_10() {
        assertEquals("b", StringUtils.mid(FOOBAR, 3, 1));
    }

    @Test
    public void testMid_String_11() {
        assertEquals(FOO, StringUtils.mid(FOOBAR, 0, 3));
    }

    @Test
    public void testMid_String_12() {
        assertEquals(BAR, StringUtils.mid(FOOBAR, 3, 3));
    }

    @Test
    public void testMid_String_13() {
        assertEquals(FOOBAR, StringUtils.mid(FOOBAR, 0, 80));
    }

    @Test
    public void testMid_String_14() {
        assertEquals(BAR, StringUtils.mid(FOOBAR, 3, 80));
    }

    @Test
    public void testMid_String_15() {
        assertEquals("", StringUtils.mid(FOOBAR, 9, 3));
    }

    @Test
    public void testMid_String_16() {
        assertEquals(FOO, StringUtils.mid(FOOBAR, -1, 3));
    }

    @Test
    public void testRight_String_1() {
        assertSame(null, StringUtils.right(null, -1));
    }

    @Test
    public void testRight_String_2() {
        assertSame(null, StringUtils.right(null, 0));
    }

    @Test
    public void testRight_String_3() {
        assertSame(null, StringUtils.right(null, 2));
    }

    @Test
    public void testRight_String_4() {
        assertEquals("", StringUtils.right("", -1));
    }

    @Test
    public void testRight_String_5() {
        assertEquals("", StringUtils.right("", 0));
    }

    @Test
    public void testRight_String_6() {
        assertEquals("", StringUtils.right("", 2));
    }

    @Test
    public void testRight_String_7() {
        assertEquals("", StringUtils.right(FOOBAR, -1));
    }

    @Test
    public void testRight_String_8() {
        assertEquals("", StringUtils.right(FOOBAR, 0));
    }

    @Test
    public void testRight_String_9() {
        assertEquals(BAR, StringUtils.right(FOOBAR, 3));
    }

    @Test
    public void testRight_String_10() {
        assertSame(FOOBAR, StringUtils.right(FOOBAR, 80));
    }

    @Test
    public void testSubstring_StringInt_1() {
        assertNull(StringUtils.substring(null, 0));
    }

    @Test
    public void testSubstring_StringInt_2() {
        assertEquals("", StringUtils.substring("", 0));
    }

    @Test
    public void testSubstring_StringInt_3() {
        assertEquals("", StringUtils.substring("", 2));
    }

    @Test
    public void testSubstring_StringInt_4() {
        assertEquals("", StringUtils.substring(SENTENCE, 80));
    }

    @Test
    public void testSubstring_StringInt_5() {
        assertEquals(BAZ, StringUtils.substring(SENTENCE, 8));
    }

    @Test
    public void testSubstring_StringInt_6() {
        assertEquals(BAZ, StringUtils.substring(SENTENCE, -3));
    }

    @Test
    public void testSubstring_StringInt_7() {
        assertEquals(SENTENCE, StringUtils.substring(SENTENCE, 0));
    }

    @Test
    public void testSubstring_StringInt_8() {
        assertEquals("abc", StringUtils.substring("abc", -4));
    }

    @Test
    public void testSubstring_StringInt_9() {
        assertEquals("abc", StringUtils.substring("abc", -3));
    }

    @Test
    public void testSubstring_StringInt_10() {
        assertEquals("bc", StringUtils.substring("abc", -2));
    }

    @Test
    public void testSubstring_StringInt_11() {
        assertEquals("c", StringUtils.substring("abc", -1));
    }

    @Test
    public void testSubstring_StringInt_12() {
        assertEquals("abc", StringUtils.substring("abc", 0));
    }

    @Test
    public void testSubstring_StringInt_13() {
        assertEquals("bc", StringUtils.substring("abc", 1));
    }

    @Test
    public void testSubstring_StringInt_14() {
        assertEquals("c", StringUtils.substring("abc", 2));
    }

    @Test
    public void testSubstring_StringInt_15() {
        assertEquals("", StringUtils.substring("abc", 3));
    }

    @Test
    public void testSubstring_StringInt_16() {
        assertEquals("", StringUtils.substring("abc", 4));
    }

    @Test
    public void testSubstring_StringIntInt_1() {
        assertNull(StringUtils.substring(null, 0, 0));
    }

    @Test
    public void testSubstring_StringIntInt_2() {
        assertNull(StringUtils.substring(null, 1, 2));
    }

    @Test
    public void testSubstring_StringIntInt_3() {
        assertEquals("", StringUtils.substring("", 0, 0));
    }

    @Test
    public void testSubstring_StringIntInt_4() {
        assertEquals("", StringUtils.substring("", 1, 2));
    }

    @Test
    public void testSubstring_StringIntInt_5() {
        assertEquals("", StringUtils.substring("", -2, -1));
    }

    @Test
    public void testSubstring_StringIntInt_6() {
        assertEquals("", StringUtils.substring(SENTENCE, 8, 6));
    }

    @Test
    public void testSubstring_StringIntInt_7() {
        assertEquals(FOO, StringUtils.substring(SENTENCE, 0, 3));
    }

    @Test
    public void testSubstring_StringIntInt_8() {
        assertEquals("o", StringUtils.substring(SENTENCE, -9, 3));
    }

    @Test
    public void testSubstring_StringIntInt_9() {
        assertEquals(FOO, StringUtils.substring(SENTENCE, 0, -8));
    }

    @Test
    public void testSubstring_StringIntInt_10() {
        assertEquals("o", StringUtils.substring(SENTENCE, -9, -8));
    }

    @Test
    public void testSubstring_StringIntInt_11() {
        assertEquals(SENTENCE, StringUtils.substring(SENTENCE, 0, 80));
    }

    @Test
    public void testSubstring_StringIntInt_12() {
        assertEquals("", StringUtils.substring(SENTENCE, 2, 2));
    }

    @Test
    public void testSubstring_StringIntInt_13() {
        assertEquals("b", StringUtils.substring("abc", -2, -1));
    }

    @Test
    public void testSubstringAfter_StringInt_1() {
        assertNull(StringUtils.substringAfter(null, 0));
    }

    @Test
    public void testSubstringAfter_StringInt_2() {
        assertNull(StringUtils.substringAfter(null, 'X'));
    }

    @Test
    public void testSubstringAfter_StringInt_3() {
        assertEquals("", StringUtils.substringAfter("", 0));
    }

    @Test
    public void testSubstringAfter_StringInt_4() {
        assertEquals("", StringUtils.substringAfter("", 'X'));
    }

    @Test
    public void testSubstringAfter_StringInt_5() {
        assertEquals("", StringUtils.substringAfter("foo", 0));
    }

    @Test
    public void testSubstringAfter_StringInt_6() {
        assertEquals("ot", StringUtils.substringAfter("foot", 'o'));
    }

    @Test
    public void testSubstringAfter_StringInt_7() {
        assertEquals("bc", StringUtils.substringAfter("abc", 'a'));
    }

    @Test
    public void testSubstringAfter_StringInt_8() {
        assertEquals("cba", StringUtils.substringAfter("abcba", 'b'));
    }

    @Test
    public void testSubstringAfter_StringInt_9() {
        assertEquals("", StringUtils.substringAfter("abc", 'c'));
    }

    @Test
    public void testSubstringAfter_StringInt_10() {
        assertEquals("", StringUtils.substringAfter("abc", 'd'));
    }

    @Test
    public void testSubstringAfter_StringString_1() {
        assertEquals("barXXbaz", StringUtils.substringAfter("fooXXbarXXbaz", "XX"));
    }

    @Test
    public void testSubstringAfter_StringString_2() {
        assertNull(StringUtils.substringAfter(null, null));
    }

    @Test
    public void testSubstringAfter_StringString_3() {
        assertNull(StringUtils.substringAfter(null, ""));
    }

    @Test
    public void testSubstringAfter_StringString_4() {
        assertNull(StringUtils.substringAfter(null, "XX"));
    }

    @Test
    public void testSubstringAfter_StringString_5() {
        assertEquals("", StringUtils.substringAfter("", null));
    }

    @Test
    public void testSubstringAfter_StringString_6() {
        assertEquals("", StringUtils.substringAfter("", ""));
    }

    @Test
    public void testSubstringAfter_StringString_7() {
        assertEquals("", StringUtils.substringAfter("", "XX"));
    }

    @Test
    public void testSubstringAfter_StringString_8() {
        assertEquals("", StringUtils.substringAfter("foo", null));
    }

    @Test
    public void testSubstringAfter_StringString_9() {
        assertEquals("ot", StringUtils.substringAfter("foot", "o"));
    }

    @Test
    public void testSubstringAfter_StringString_10() {
        assertEquals("bc", StringUtils.substringAfter("abc", "a"));
    }

    @Test
    public void testSubstringAfter_StringString_11() {
        assertEquals("cba", StringUtils.substringAfter("abcba", "b"));
    }

    @Test
    public void testSubstringAfter_StringString_12() {
        assertEquals("", StringUtils.substringAfter("abc", "c"));
    }

    @Test
    public void testSubstringAfter_StringString_13() {
        assertEquals("abc", StringUtils.substringAfter("abc", ""));
    }

    @Test
    public void testSubstringAfter_StringString_14() {
        assertEquals("", StringUtils.substringAfter("abc", "d"));
    }

    @Test
    public void testSubstringAfterLast_StringInt_1() {
        assertNull(StringUtils.substringAfterLast(null, 0));
    }

    @Test
    public void testSubstringAfterLast_StringInt_2() {
        assertNull(StringUtils.substringAfterLast(null, 'X'));
    }

    @Test
    public void testSubstringAfterLast_StringInt_3() {
        assertEquals("", StringUtils.substringAfterLast("", 0));
    }

    @Test
    public void testSubstringAfterLast_StringInt_4() {
        assertEquals("", StringUtils.substringAfterLast("", 'a'));
    }

    @Test
    public void testSubstringAfterLast_StringInt_5() {
        assertEquals("", StringUtils.substringAfterLast("foo", 0));
    }

    @Test
    public void testSubstringAfterLast_StringInt_6() {
        assertEquals("", StringUtils.substringAfterLast("foo", 'b'));
    }

    @Test
    public void testSubstringAfterLast_StringInt_7() {
        assertEquals("t", StringUtils.substringAfterLast("foot", 'o'));
    }

    @Test
    public void testSubstringAfterLast_StringInt_8() {
        assertEquals("bc", StringUtils.substringAfterLast("abc", 'a'));
    }

    @Test
    public void testSubstringAfterLast_StringInt_9() {
        assertEquals("a", StringUtils.substringAfterLast("abcba", 'b'));
    }

    @Test
    public void testSubstringAfterLast_StringInt_10() {
        assertEquals("", StringUtils.substringAfterLast("abc", 'c'));
    }

    @Test
    public void testSubstringAfterLast_StringInt_11() {
        assertEquals("", StringUtils.substringAfterLast("", 'd'));
    }

    @Test
    public void testSubstringAfterLast_StringString_1() {
        assertEquals("baz", StringUtils.substringAfterLast("fooXXbarXXbaz", "XX"));
    }

    @Test
    public void testSubstringAfterLast_StringString_2() {
        assertNull(StringUtils.substringAfterLast(null, null));
    }

    @Test
    public void testSubstringAfterLast_StringString_3() {
        assertNull(StringUtils.substringAfterLast(null, ""));
    }

    @Test
    public void testSubstringAfterLast_StringString_4() {
        assertNull(StringUtils.substringAfterLast(null, "XX"));
    }

    @Test
    public void testSubstringAfterLast_StringString_5() {
        assertEquals("", StringUtils.substringAfterLast("", null));
    }

    @Test
    public void testSubstringAfterLast_StringString_6() {
        assertEquals("", StringUtils.substringAfterLast("", ""));
    }

    @Test
    public void testSubstringAfterLast_StringString_7() {
        assertEquals("", StringUtils.substringAfterLast("", "a"));
    }

    @Test
    public void testSubstringAfterLast_StringString_8() {
        assertEquals("", StringUtils.substringAfterLast("foo", null));
    }

    @Test
    public void testSubstringAfterLast_StringString_9() {
        assertEquals("", StringUtils.substringAfterLast("foo", "b"));
    }

    @Test
    public void testSubstringAfterLast_StringString_10() {
        assertEquals("t", StringUtils.substringAfterLast("foot", "o"));
    }

    @Test
    public void testSubstringAfterLast_StringString_11() {
        assertEquals("bc", StringUtils.substringAfterLast("abc", "a"));
    }

    @Test
    public void testSubstringAfterLast_StringString_12() {
        assertEquals("a", StringUtils.substringAfterLast("abcba", "b"));
    }

    @Test
    public void testSubstringAfterLast_StringString_13() {
        assertEquals("", StringUtils.substringAfterLast("abc", "c"));
    }

    @Test
    public void testSubstringAfterLast_StringString_14() {
        assertEquals("", StringUtils.substringAfterLast("", "d"));
    }

    @Test
    public void testSubstringAfterLast_StringString_15() {
        assertEquals("", StringUtils.substringAfterLast("abc", ""));
    }

    @Test
    public void testSubstringBefore_StringInt_1() {
        assertEquals("foo", StringUtils.substringBefore("fooXXbarXXbaz", 'X'));
    }

    @Test
    public void testSubstringBefore_StringInt_2() {
        assertNull(StringUtils.substringBefore(null, 0));
    }

    @Test
    public void testSubstringBefore_StringInt_3() {
        assertNull(StringUtils.substringBefore(null, 'X'));
    }

    @Test
    public void testSubstringBefore_StringInt_4() {
        assertEquals("", StringUtils.substringBefore("", 0));
    }

    @Test
    public void testSubstringBefore_StringInt_5() {
        assertEquals("", StringUtils.substringBefore("", 'X'));
    }

    @Test
    public void testSubstringBefore_StringInt_6() {
        assertEquals("foo", StringUtils.substringBefore("foo", 0));
    }

    @Test
    public void testSubstringBefore_StringInt_7() {
        assertEquals("foo", StringUtils.substringBefore("foo", 'b'));
    }

    @Test
    public void testSubstringBefore_StringInt_8() {
        assertEquals("f", StringUtils.substringBefore("foot", 'o'));
    }

    @Test
    public void testSubstringBefore_StringInt_9() {
        assertEquals("", StringUtils.substringBefore("abc", 'a'));
    }

    @Test
    public void testSubstringBefore_StringInt_10() {
        assertEquals("a", StringUtils.substringBefore("abcba", 'b'));
    }

    @Test
    public void testSubstringBefore_StringInt_11() {
        assertEquals("ab", StringUtils.substringBefore("abc", 'c'));
    }

    @Test
    public void testSubstringBefore_StringInt_12() {
        assertEquals("abc", StringUtils.substringBefore("abc", 0));
    }

    @Test
    public void testSubstringBefore_StringString_1() {
        assertEquals("foo", StringUtils.substringBefore("fooXXbarXXbaz", "XX"));
    }

    @Test
    public void testSubstringBefore_StringString_2() {
        assertNull(StringUtils.substringBefore(null, null));
    }

    @Test
    public void testSubstringBefore_StringString_3() {
        assertNull(StringUtils.substringBefore(null, ""));
    }

    @Test
    public void testSubstringBefore_StringString_4() {
        assertNull(StringUtils.substringBefore(null, "XX"));
    }

    @Test
    public void testSubstringBefore_StringString_5() {
        assertEquals("", StringUtils.substringBefore("", null));
    }

    @Test
    public void testSubstringBefore_StringString_6() {
        assertEquals("", StringUtils.substringBefore("", ""));
    }

    @Test
    public void testSubstringBefore_StringString_7() {
        assertEquals("", StringUtils.substringBefore("", "XX"));
    }

    @Test
    public void testSubstringBefore_StringString_8() {
        assertEquals("foo", StringUtils.substringBefore("foo", null));
    }

    @Test
    public void testSubstringBefore_StringString_9() {
        assertEquals("foo", StringUtils.substringBefore("foo", "b"));
    }

    @Test
    public void testSubstringBefore_StringString_10() {
        assertEquals("f", StringUtils.substringBefore("foot", "o"));
    }

    @Test
    public void testSubstringBefore_StringString_11() {
        assertEquals("", StringUtils.substringBefore("abc", "a"));
    }

    @Test
    public void testSubstringBefore_StringString_12() {
        assertEquals("a", StringUtils.substringBefore("abcba", "b"));
    }

    @Test
    public void testSubstringBefore_StringString_13() {
        assertEquals("ab", StringUtils.substringBefore("abc", "c"));
    }

    @Test
    public void testSubstringBefore_StringString_14() {
        assertEquals("", StringUtils.substringBefore("abc", ""));
    }

    @Test
    public void testSubstringBefore_StringString_15() {
        assertEquals("abc", StringUtils.substringBefore("abc", "X"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_1() {
        assertEquals("fooXXbar", StringUtils.substringBeforeLast("fooXXbarXXbaz", "XX"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_2() {
        assertNull(StringUtils.substringBeforeLast(null, null));
    }

    @Test
    public void testSubstringBeforeLast_StringString_3() {
        assertNull(StringUtils.substringBeforeLast(null, ""));
    }

    @Test
    public void testSubstringBeforeLast_StringString_4() {
        assertNull(StringUtils.substringBeforeLast(null, "XX"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_5() {
        assertEquals("", StringUtils.substringBeforeLast("", null));
    }

    @Test
    public void testSubstringBeforeLast_StringString_6() {
        assertEquals("", StringUtils.substringBeforeLast("", ""));
    }

    @Test
    public void testSubstringBeforeLast_StringString_7() {
        assertEquals("", StringUtils.substringBeforeLast("", "XX"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_8() {
        assertEquals("foo", StringUtils.substringBeforeLast("foo", null));
    }

    @Test
    public void testSubstringBeforeLast_StringString_9() {
        assertEquals("foo", StringUtils.substringBeforeLast("foo", "b"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_10() {
        assertEquals("fo", StringUtils.substringBeforeLast("foo", "o"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_11() {
        assertEquals("abc\r\n", StringUtils.substringBeforeLast("abc\r\n", "d"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_12() {
        assertEquals("abc", StringUtils.substringBeforeLast("abcdabc", "d"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_13() {
        assertEquals("abcdabc", StringUtils.substringBeforeLast("abcdabcd", "d"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_14() {
        assertEquals("a", StringUtils.substringBeforeLast("abc", "b"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_15() {
        assertEquals("abc ", StringUtils.substringBeforeLast("abc \n", "\n"));
    }

    @Test
    public void testSubstringBeforeLast_StringString_16() {
        assertEquals("a", StringUtils.substringBeforeLast("a", null));
    }

    @Test
    public void testSubstringBeforeLast_StringString_17() {
        assertEquals("a", StringUtils.substringBeforeLast("a", ""));
    }

    @Test
    public void testSubstringBeforeLast_StringString_18() {
        assertEquals("", StringUtils.substringBeforeLast("a", "a"));
    }

    @Test
    public void testSubstringBetween_StringString_1() {
        assertNull(StringUtils.substringBetween(null, "tag"));
    }

    @Test
    public void testSubstringBetween_StringString_2() {
        assertEquals("", StringUtils.substringBetween("", ""));
    }

    @Test
    public void testSubstringBetween_StringString_3() {
        assertNull(StringUtils.substringBetween("", "abc"));
    }

    @Test
    public void testSubstringBetween_StringString_4() {
        assertEquals("", StringUtils.substringBetween("    ", " "));
    }

    @Test
    public void testSubstringBetween_StringString_5() {
        assertNull(StringUtils.substringBetween("abc", null));
    }

    @Test
    public void testSubstringBetween_StringString_6() {
        assertEquals("", StringUtils.substringBetween("abc", ""));
    }

    @Test
    public void testSubstringBetween_StringString_7() {
        assertNull(StringUtils.substringBetween("abc", "a"));
    }

    @Test
    public void testSubstringBetween_StringString_8() {
        assertEquals("bc", StringUtils.substringBetween("abca", "a"));
    }

    @Test
    public void testSubstringBetween_StringString_9() {
        assertEquals("bc", StringUtils.substringBetween("abcabca", "a"));
    }

    @Test
    public void testSubstringBetween_StringString_10() {
        assertEquals("bar", StringUtils.substringBetween("\nbar\n", "\n"));
    }

    @Test
    public void testSubstringBetween_StringStringString_1() {
        assertNull(StringUtils.substringBetween(null, "", ""));
    }

    @Test
    public void testSubstringBetween_StringStringString_2() {
        assertNull(StringUtils.substringBetween("", null, ""));
    }

    @Test
    public void testSubstringBetween_StringStringString_3() {
        assertNull(StringUtils.substringBetween("", "", null));
    }

    @Test
    public void testSubstringBetween_StringStringString_4() {
        assertEquals("", StringUtils.substringBetween("", "", ""));
    }

    @Test
    public void testSubstringBetween_StringStringString_5() {
        assertEquals("", StringUtils.substringBetween("foo", "", ""));
    }

    @Test
    public void testSubstringBetween_StringStringString_6() {
        assertNull(StringUtils.substringBetween("foo", "", "]"));
    }

    @Test
    public void testSubstringBetween_StringStringString_7() {
        assertNull(StringUtils.substringBetween("foo", "[", "]"));
    }

    @Test
    public void testSubstringBetween_StringStringString_8() {
        assertEquals("", StringUtils.substringBetween("    ", " ", "  "));
    }

    @Test
    public void testSubstringBetween_StringStringString_9() {
        assertEquals("bar", StringUtils.substringBetween("<foo>bar</foo>", "<foo>", "</foo>"));
    }

    @Test
    public void testSubstringBetween_StringStringString_10() {
        assertEquals("abc", StringUtils.substringBetween("yabczyabcz", "y", "z"));
    }
}
