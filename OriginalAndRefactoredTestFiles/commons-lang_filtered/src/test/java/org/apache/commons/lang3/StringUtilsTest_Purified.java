package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.lang3.function.Suppliers;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.DefaultLocale;
import org.junitpioneer.jupiter.ReadsDefaultLocale;
import org.junitpioneer.jupiter.WritesDefaultLocale;

@SuppressWarnings("deprecation")
public class StringUtilsTest_Purified extends AbstractLangTest {

    static final String WHITESPACE;

    static final String NON_WHITESPACE;

    static final String HARD_SPACE;

    static final String TRIMMABLE;

    static final String NON_TRIMMABLE;

    static {
        final StringBuilder ws = new StringBuilder();
        final StringBuilder nws = new StringBuilder();
        final String hs = String.valueOf((char) 160);
        final StringBuilder tr = new StringBuilder();
        final StringBuilder ntr = new StringBuilder();
        for (int i = 0; i < Character.MAX_VALUE; i++) {
            if (Character.isWhitespace((char) i)) {
                ws.append((char) i);
                if (i > 32) {
                    ntr.append((char) i);
                }
            } else if (i < 40) {
                nws.append((char) i);
            }
        }
        for (int i = 0; i <= 32; i++) {
            tr.append((char) i);
        }
        WHITESPACE = ws.toString();
        NON_WHITESPACE = nws.toString();
        HARD_SPACE = hs;
        TRIMMABLE = tr.toString();
        NON_TRIMMABLE = ntr.toString();
    }

    private static final String[] ARRAY_LIST = { "foo", "bar", "baz" };

    private static final String[] EMPTY_ARRAY_LIST = {};

    private static final String[] NULL_ARRAY_LIST = { null };

    private static final Object[] NULL_TO_STRING_LIST = { new Object() {

        @Override
        public String toString() {
            return null;
        }
    } };

    private static final String[] MIXED_ARRAY_LIST = { null, "", "foo" };

    private static final Object[] MIXED_TYPE_LIST = { "foo", Long.valueOf(2L) };

    private static final long[] LONG_PRIM_LIST = { 1, 2 };

    private static final int[] INT_PRIM_LIST = { 1, 2 };

    private static final byte[] BYTE_PRIM_LIST = { 1, 2 };

    private static final short[] SHORT_PRIM_LIST = { 1, 2 };

    private static final char[] CHAR_PRIM_LIST = { '1', '2' };

    private static final float[] FLOAT_PRIM_LIST = { 1, 2 };

    private static final double[] DOUBLE_PRIM_LIST = { 1, 2 };

    private static final List<String> MIXED_STRING_LIST = Arrays.asList(null, "", "foo");

    private static final List<Object> MIXED_TYPE_OBJECT_LIST = Arrays.<Object>asList("foo", Long.valueOf(2L));

    private static final List<String> STRING_LIST = Arrays.asList("foo", "bar", "baz");

    private static final List<String> EMPTY_STRING_LIST = Collections.emptyList();

    private static final List<String> NULL_STRING_LIST = Collections.singletonList(null);

    private static final String SEPARATOR = ",";

    private static final char SEPARATOR_CHAR = ';';

    private static final char COMMA_SEPARATOR_CHAR = ',';

    private static final String TEXT_LIST = "foo,bar,baz";

    private static final String TEXT_LIST_CHAR = "foo;bar;baz";

    private static final String TEXT_LIST_NOSEP = "foobarbaz";

    private static final String FOO_UNCAP = "foo";

    private static final String FOO_CAP = "Foo";

    private static final String SENTENCE_UNCAP = "foo bar baz";

    private static final String SENTENCE_CAP = "Foo Bar Baz";

    private static final boolean[] EMPTY = {};

    private static final boolean[] ARRAY_FALSE_FALSE = { false, false };

    private static final boolean[] ARRAY_FALSE_TRUE = { false, true };

    private static final boolean[] ARRAY_FALSE_TRUE_FALSE = { false, true, false };

    private void innerTestSplit(final char separator, final String sepStr, final char noMatch) {
        final String msg = "Failed on separator hex(" + Integer.toHexString(separator) + "), noMatch hex(" + Integer.toHexString(noMatch) + "), sepStr(" + sepStr + ")";
        final String str = "a" + separator + "b" + separator + separator + noMatch + "c";
        String[] res;
        res = StringUtils.split(str, sepStr);
        assertEquals(3, res.length, msg);
        assertEquals("a", res[0]);
        assertEquals("b", res[1]);
        assertEquals(noMatch + "c", res[2]);
        final String str2 = separator + "a" + separator;
        res = StringUtils.split(str2, sepStr);
        assertEquals(1, res.length, msg);
        assertEquals("a", res[0], msg);
        res = StringUtils.split(str, sepStr, -1);
        assertEquals(3, res.length, msg);
        assertEquals("a", res[0], msg);
        assertEquals("b", res[1], msg);
        assertEquals(noMatch + "c", res[2], msg);
        res = StringUtils.split(str, sepStr, 0);
        assertEquals(3, res.length, msg);
        assertEquals("a", res[0], msg);
        assertEquals("b", res[1], msg);
        assertEquals(noMatch + "c", res[2], msg);
        res = StringUtils.split(str, sepStr, 1);
        assertEquals(1, res.length, msg);
        assertEquals(str, res[0], msg);
        res = StringUtils.split(str, sepStr, 2);
        assertEquals(2, res.length, msg);
        assertEquals("a", res[0], msg);
        assertEquals(str.substring(2), res[1], msg);
    }

    private void innerTestSplitPreserveAllTokens(final char separator, final String sepStr, final char noMatch) {
        final String msg = "Failed on separator hex(" + Integer.toHexString(separator) + "), noMatch hex(" + Integer.toHexString(noMatch) + "), sepStr(" + sepStr + ")";
        final String str = "a" + separator + "b" + separator + separator + noMatch + "c";
        String[] res;
        res = StringUtils.splitPreserveAllTokens(str, sepStr);
        assertEquals(4, res.length, msg);
        assertEquals("a", res[0], msg);
        assertEquals("b", res[1], msg);
        assertEquals("", res[2], msg);
        assertEquals(noMatch + "c", res[3], msg);
        final String str2 = separator + "a" + separator;
        res = StringUtils.splitPreserveAllTokens(str2, sepStr);
        assertEquals(3, res.length, msg);
        assertEquals("", res[0], msg);
        assertEquals("a", res[1], msg);
        assertEquals("", res[2], msg);
        res = StringUtils.splitPreserveAllTokens(str, sepStr, -1);
        assertEquals(4, res.length, msg);
        assertEquals("a", res[0], msg);
        assertEquals("b", res[1], msg);
        assertEquals("", res[2], msg);
        assertEquals(noMatch + "c", res[3], msg);
        res = StringUtils.splitPreserveAllTokens(str, sepStr, 0);
        assertEquals(4, res.length, msg);
        assertEquals("a", res[0], msg);
        assertEquals("b", res[1], msg);
        assertEquals("", res[2], msg);
        assertEquals(noMatch + "c", res[3], msg);
        res = StringUtils.splitPreserveAllTokens(str, sepStr, 1);
        assertEquals(1, res.length, msg);
        assertEquals(str, res[0], msg);
        res = StringUtils.splitPreserveAllTokens(str, sepStr, 2);
        assertEquals(2, res.length, msg);
        assertEquals("a", res[0], msg);
        assertEquals(str.substring(2), res[1], msg);
    }

    @Test
    public void testAppendIfMissing_1() {
        assertNull(StringUtils.appendIfMissing(null, null), "appendIfMissing(null,null)");
    }

    @Test
    public void testAppendIfMissing_2() {
        assertEquals("abc", StringUtils.appendIfMissing("abc", null), "appendIfMissing(abc,null)");
    }

    @Test
    public void testAppendIfMissing_3() {
        assertEquals("xyz", StringUtils.appendIfMissing("", "xyz"), "appendIfMissing(\"\",xyz)");
    }

    @Test
    public void testAppendIfMissing_4() {
        assertEquals("abcxyz", StringUtils.appendIfMissing("abc", "xyz"), "appendIfMissing(abc,xyz)");
    }

    @Test
    public void testAppendIfMissing_5() {
        assertEquals("abcxyz", StringUtils.appendIfMissing("abcxyz", "xyz"), "appendIfMissing(abcxyz,xyz)");
    }

    @Test
    public void testAppendIfMissing_6() {
        assertEquals("aXYZxyz", StringUtils.appendIfMissing("aXYZ", "xyz"), "appendIfMissing(aXYZ,xyz)");
    }

    @Test
    public void testAppendIfMissing_7() {
        assertNull(StringUtils.appendIfMissing(null, null, (CharSequence[]) null), "appendIfMissing(null,null,null)");
    }

    @Test
    public void testAppendIfMissing_8() {
        assertEquals("abc", StringUtils.appendIfMissing("abc", null, (CharSequence[]) null), "appendIfMissing(abc,null,null)");
    }

    @Test
    public void testAppendIfMissing_9() {
        assertEquals("xyz", StringUtils.appendIfMissing("", "xyz", (CharSequence[]) null), "appendIfMissing(\"\",xyz,null))");
    }

    @Test
    public void testAppendIfMissing_10() {
        assertEquals("abcxyz", StringUtils.appendIfMissing("abc", "xyz", (CharSequence) null), "appendIfMissing(abc,xyz,{null})");
    }

    @Test
    public void testAppendIfMissing_11() {
        assertEquals("abc", StringUtils.appendIfMissing("abc", "xyz", ""), "appendIfMissing(abc,xyz,\"\")");
    }

    @Test
    public void testAppendIfMissing_12() {
        assertEquals("abcxyz", StringUtils.appendIfMissing("abc", "xyz", "mno"), "appendIfMissing(abc,xyz,mno)");
    }

    @Test
    public void testAppendIfMissing_13() {
        assertEquals("abcxyz", StringUtils.appendIfMissing("abcxyz", "xyz", "mno"), "appendIfMissing(abcxyz,xyz,mno)");
    }

    @Test
    public void testAppendIfMissing_14() {
        assertEquals("abcmno", StringUtils.appendIfMissing("abcmno", "xyz", "mno"), "appendIfMissing(abcmno,xyz,mno)");
    }

    @Test
    public void testAppendIfMissing_15() {
        assertEquals("abcXYZxyz", StringUtils.appendIfMissing("abcXYZ", "xyz", "mno"), "appendIfMissing(abcXYZ,xyz,mno)");
    }

    @Test
    public void testAppendIfMissing_16() {
        assertEquals("abcMNOxyz", StringUtils.appendIfMissing("abcMNO", "xyz", "mno"), "appendIfMissing(abcMNO,xyz,mno)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_1() {
        assertNull(StringUtils.appendIfMissingIgnoreCase(null, null), "appendIfMissingIgnoreCase(null,null)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_2() {
        assertEquals("abc", StringUtils.appendIfMissingIgnoreCase("abc", null), "appendIfMissingIgnoreCase(abc,null)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_3() {
        assertEquals("xyz", StringUtils.appendIfMissingIgnoreCase("", "xyz"), "appendIfMissingIgnoreCase(\"\",xyz)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_4() {
        assertEquals("abcxyz", StringUtils.appendIfMissingIgnoreCase("abc", "xyz"), "appendIfMissingIgnoreCase(abc,xyz)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_5() {
        assertEquals("abcxyz", StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz"), "appendIfMissingIgnoreCase(abcxyz,xyz)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_6() {
        assertEquals("abcXYZ", StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz"), "appendIfMissingIgnoreCase(abcXYZ,xyz)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_7() {
        assertNull(StringUtils.appendIfMissingIgnoreCase(null, null, (CharSequence[]) null), "appendIfMissingIgnoreCase(null,null,null)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_8() {
        assertEquals("abc", StringUtils.appendIfMissingIgnoreCase("abc", null, (CharSequence[]) null), "appendIfMissingIgnoreCase(abc,null,null)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_9() {
        assertEquals("xyz", StringUtils.appendIfMissingIgnoreCase("", "xyz", (CharSequence[]) null), "appendIfMissingIgnoreCase(\"\",xyz,null)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_10() {
        assertEquals("abcxyz", StringUtils.appendIfMissingIgnoreCase("abc", "xyz", (CharSequence) null), "appendIfMissingIgnoreCase(abc,xyz,{null})");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_11() {
        assertEquals("abc", StringUtils.appendIfMissingIgnoreCase("abc", "xyz", ""), "appendIfMissingIgnoreCase(abc,xyz,\"\")");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_12() {
        assertEquals("abcxyz", StringUtils.appendIfMissingIgnoreCase("abc", "xyz", "mno"), "appendIfMissingIgnoreCase(abc,xyz,mno)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_13() {
        assertEquals("abcxyz", StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz", "mno"), "appendIfMissingIgnoreCase(abcxyz,xyz,mno)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_14() {
        assertEquals("abcmno", StringUtils.appendIfMissingIgnoreCase("abcmno", "xyz", "mno"), "appendIfMissingIgnoreCase(abcmno,xyz,mno)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_15() {
        assertEquals("abcXYZ", StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz", "mno"), "appendIfMissingIgnoreCase(abcXYZ,xyz,mno)");
    }

    @Test
    public void testAppendIfMissingIgnoreCase_16() {
        assertEquals("abcMNO", StringUtils.appendIfMissingIgnoreCase("abcMNO", "xyz", "mno"), "appendIfMissingIgnoreCase(abcMNO,xyz,mno)");
    }

    @Test
    public void testCapitalize_1() {
        assertNull(StringUtils.capitalize(null));
    }

    @Test
    public void testCapitalize_2() {
        assertEquals("", StringUtils.capitalize(""), "capitalize(empty-string) failed");
    }

    @Test
    public void testCapitalize_3() {
        assertEquals("X", StringUtils.capitalize("x"), "capitalize(single-char-string) failed");
    }

    @Test
    public void testCapitalize_4() {
        assertEquals(FOO_CAP, StringUtils.capitalize(FOO_CAP), "capitalize(String) failed");
    }

    @Test
    public void testCapitalize_5() {
        assertEquals(FOO_CAP, StringUtils.capitalize(FOO_UNCAP), "capitalize(string) failed");
    }

    @Test
    public void testCapitalize_6() {
        assertEquals("\u01C8", StringUtils.capitalize("\u01C9"), "capitalize(String) is not using TitleCase");
    }

    @Test
    public void testCapitalize_7() {
        assertNull(StringUtils.capitalize(null));
    }

    @Test
    public void testCapitalize_8() {
        assertEquals("", StringUtils.capitalize(""));
    }

    @Test
    public void testCapitalize_9() {
        assertEquals("Cat", StringUtils.capitalize("cat"));
    }

    @Test
    public void testCapitalize_10() {
        assertEquals("CAt", StringUtils.capitalize("cAt"));
    }

    @Test
    public void testCapitalize_11() {
        assertEquals("'cat'", StringUtils.capitalize("'cat'"));
    }

    @Test
    public void testCenter_StringInt_1() {
        assertNull(StringUtils.center(null, -1));
    }

    @Test
    public void testCenter_StringInt_2() {
        assertNull(StringUtils.center(null, 4));
    }

    @Test
    public void testCenter_StringInt_3() {
        assertEquals("    ", StringUtils.center("", 4));
    }

    @Test
    public void testCenter_StringInt_4() {
        assertEquals("ab", StringUtils.center("ab", 0));
    }

    @Test
    public void testCenter_StringInt_5() {
        assertEquals("ab", StringUtils.center("ab", -1));
    }

    @Test
    public void testCenter_StringInt_6() {
        assertEquals("ab", StringUtils.center("ab", 1));
    }

    @Test
    public void testCenter_StringInt_7() {
        assertEquals("    ", StringUtils.center("", 4));
    }

    @Test
    public void testCenter_StringInt_8() {
        assertEquals(" ab ", StringUtils.center("ab", 4));
    }

    @Test
    public void testCenter_StringInt_9() {
        assertEquals("abcd", StringUtils.center("abcd", 2));
    }

    @Test
    public void testCenter_StringInt_10() {
        assertEquals(" a  ", StringUtils.center("a", 4));
    }

    @Test
    public void testCenter_StringInt_11() {
        assertEquals("  a  ", StringUtils.center("a", 5));
    }

    @Test
    public void testCenter_StringIntChar_1() {
        assertNull(StringUtils.center(null, -1, ' '));
    }

    @Test
    public void testCenter_StringIntChar_2() {
        assertNull(StringUtils.center(null, 4, ' '));
    }

    @Test
    public void testCenter_StringIntChar_3() {
        assertEquals("    ", StringUtils.center("", 4, ' '));
    }

    @Test
    public void testCenter_StringIntChar_4() {
        assertEquals("ab", StringUtils.center("ab", 0, ' '));
    }

    @Test
    public void testCenter_StringIntChar_5() {
        assertEquals("ab", StringUtils.center("ab", -1, ' '));
    }

    @Test
    public void testCenter_StringIntChar_6() {
        assertEquals("ab", StringUtils.center("ab", 1, ' '));
    }

    @Test
    public void testCenter_StringIntChar_7() {
        assertEquals("    ", StringUtils.center("", 4, ' '));
    }

    @Test
    public void testCenter_StringIntChar_8() {
        assertEquals(" ab ", StringUtils.center("ab", 4, ' '));
    }

    @Test
    public void testCenter_StringIntChar_9() {
        assertEquals("abcd", StringUtils.center("abcd", 2, ' '));
    }

    @Test
    public void testCenter_StringIntChar_10() {
        assertEquals(" a  ", StringUtils.center("a", 4, ' '));
    }

    @Test
    public void testCenter_StringIntChar_11() {
        assertEquals("  a  ", StringUtils.center("a", 5, ' '));
    }

    @Test
    public void testCenter_StringIntChar_12() {
        assertEquals("xxaxx", StringUtils.center("a", 5, 'x'));
    }

    @Test
    public void testCenter_StringIntString_1() {
        assertNull(StringUtils.center(null, 4, null));
    }

    @Test
    public void testCenter_StringIntString_2() {
        assertNull(StringUtils.center(null, -1, " "));
    }

    @Test
    public void testCenter_StringIntString_3() {
        assertNull(StringUtils.center(null, 4, " "));
    }

    @Test
    public void testCenter_StringIntString_4() {
        assertEquals("    ", StringUtils.center("", 4, " "));
    }

    @Test
    public void testCenter_StringIntString_5() {
        assertEquals("ab", StringUtils.center("ab", 0, " "));
    }

    @Test
    public void testCenter_StringIntString_6() {
        assertEquals("ab", StringUtils.center("ab", -1, " "));
    }

    @Test
    public void testCenter_StringIntString_7() {
        assertEquals("ab", StringUtils.center("ab", 1, " "));
    }

    @Test
    public void testCenter_StringIntString_8() {
        assertEquals("    ", StringUtils.center("", 4, " "));
    }

    @Test
    public void testCenter_StringIntString_9() {
        assertEquals(" ab ", StringUtils.center("ab", 4, " "));
    }

    @Test
    public void testCenter_StringIntString_10() {
        assertEquals("abcd", StringUtils.center("abcd", 2, " "));
    }

    @Test
    public void testCenter_StringIntString_11() {
        assertEquals(" a  ", StringUtils.center("a", 4, " "));
    }

    @Test
    public void testCenter_StringIntString_12() {
        assertEquals("yayz", StringUtils.center("a", 4, "yz"));
    }

    @Test
    public void testCenter_StringIntString_13() {
        assertEquals("yzyayzy", StringUtils.center("a", 7, "yz"));
    }

    @Test
    public void testCenter_StringIntString_14() {
        assertEquals("  abc  ", StringUtils.center("abc", 7, null));
    }

    @Test
    public void testCenter_StringIntString_15() {
        assertEquals("  abc  ", StringUtils.center("abc", 7, ""));
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new StringUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = StringUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(StringUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(StringUtils.class.getModifiers()));
    }

    @Test
    public void testDefault_String_1() {
        assertEquals("", StringUtils.defaultString(null));
    }

    @Test
    public void testDefault_String_2() {
        assertEquals("", StringUtils.defaultString(""));
    }

    @Test
    public void testDefault_String_3() {
        assertEquals("abc", StringUtils.defaultString("abc"));
    }

    @Test
    public void testDefault_StringString_1() {
        assertEquals("NULL", StringUtils.defaultString(null, "NULL"));
    }

    @Test
    public void testDefault_StringString_2() {
        assertEquals("", StringUtils.defaultString("", "NULL"));
    }

    @Test
    public void testDefault_StringString_3() {
        assertEquals("abc", StringUtils.defaultString("abc", "NULL"));
    }

    @Test
    public void testDefaultIfBlank_CharBuffers_1() {
        assertEquals("NULL", StringUtils.defaultIfBlank(CharBuffer.wrap(""), CharBuffer.wrap("NULL")).toString());
    }

    @Test
    public void testDefaultIfBlank_CharBuffers_2() {
        assertEquals("NULL", StringUtils.defaultIfBlank(CharBuffer.wrap(" "), CharBuffer.wrap("NULL")).toString());
    }

    @Test
    public void testDefaultIfBlank_CharBuffers_3() {
        assertEquals("abc", StringUtils.defaultIfBlank(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL")).toString());
    }

    @Test
    public void testDefaultIfBlank_CharBuffers_4() {
        assertNull(StringUtils.defaultIfBlank(CharBuffer.wrap(""), (CharBuffer) null));
    }

    @Test
    public void testDefaultIfBlank_CharBuffers_5() {
        final CharBuffer s = StringUtils.defaultIfBlank(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL"));
        assertEquals("abc", s.toString());
    }

    @Test
    public void testDefaultIfBlank_StringBuffers_1() {
        assertEquals("NULL", StringUtils.defaultIfBlank(new StringBuffer(""), new StringBuffer("NULL")).toString());
    }

    @Test
    public void testDefaultIfBlank_StringBuffers_2() {
        assertEquals("NULL", StringUtils.defaultIfBlank(new StringBuffer(" "), new StringBuffer("NULL")).toString());
    }

    @Test
    public void testDefaultIfBlank_StringBuffers_3() {
        assertEquals("abc", StringUtils.defaultIfBlank(new StringBuffer("abc"), new StringBuffer("NULL")).toString());
    }

    @Test
    public void testDefaultIfBlank_StringBuffers_4() {
        assertNull(StringUtils.defaultIfBlank(new StringBuffer(""), (StringBuffer) null));
    }

    @Test
    public void testDefaultIfBlank_StringBuffers_5() {
        final StringBuffer s = StringUtils.defaultIfBlank(new StringBuffer("abc"), new StringBuffer("NULL"));
        assertEquals("abc", s.toString());
    }

    @Test
    public void testDefaultIfBlank_StringBuilders_1() {
        assertEquals("NULL", StringUtils.defaultIfBlank(new StringBuilder(""), new StringBuilder("NULL")).toString());
    }

    @Test
    public void testDefaultIfBlank_StringBuilders_2() {
        assertEquals("NULL", StringUtils.defaultIfBlank(new StringBuilder(" "), new StringBuilder("NULL")).toString());
    }

    @Test
    public void testDefaultIfBlank_StringBuilders_3() {
        assertEquals("abc", StringUtils.defaultIfBlank(new StringBuilder("abc"), new StringBuilder("NULL")).toString());
    }

    @Test
    public void testDefaultIfBlank_StringBuilders_4() {
        assertNull(StringUtils.defaultIfBlank(new StringBuilder(""), (StringBuilder) null));
    }

    @Test
    public void testDefaultIfBlank_StringBuilders_5() {
        final StringBuilder s = StringUtils.defaultIfBlank(new StringBuilder("abc"), new StringBuilder("NULL"));
        assertEquals("abc", s.toString());
    }

    @Test
    public void testDefaultIfBlank_StringString_1() {
        assertEquals("NULL", StringUtils.defaultIfBlank(null, "NULL"));
    }

    @Test
    public void testDefaultIfBlank_StringString_2() {
        assertEquals("NULL", StringUtils.defaultIfBlank("", "NULL"));
    }

    @Test
    public void testDefaultIfBlank_StringString_3() {
        assertEquals("NULL", StringUtils.defaultIfBlank(" ", "NULL"));
    }

    @Test
    public void testDefaultIfBlank_StringString_4() {
        assertEquals("abc", StringUtils.defaultIfBlank("abc", "NULL"));
    }

    @Test
    public void testDefaultIfBlank_StringString_5() {
        assertNull(StringUtils.defaultIfBlank("", (String) null));
    }

    @Test
    public void testDefaultIfBlank_StringString_6() {
        final String s = StringUtils.defaultIfBlank("abc", "NULL");
        assertEquals("abc", s);
    }

    @Test
    public void testDefaultIfEmpty_CharBuffers_1() {
        assertEquals("NULL", StringUtils.defaultIfEmpty(CharBuffer.wrap(""), CharBuffer.wrap("NULL")).toString());
    }

    @Test
    public void testDefaultIfEmpty_CharBuffers_2() {
        assertEquals("abc", StringUtils.defaultIfEmpty(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL")).toString());
    }

    @Test
    public void testDefaultIfEmpty_CharBuffers_3() {
        assertNull(StringUtils.defaultIfEmpty(CharBuffer.wrap(""), (CharBuffer) null));
    }

    @Test
    public void testDefaultIfEmpty_CharBuffers_4() {
        final CharBuffer s = StringUtils.defaultIfEmpty(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL"));
        assertEquals("abc", s.toString());
    }

    @Test
    public void testDefaultIfEmpty_StringBuffers_1() {
        assertEquals("NULL", StringUtils.defaultIfEmpty(new StringBuffer(""), new StringBuffer("NULL")).toString());
    }

    @Test
    public void testDefaultIfEmpty_StringBuffers_2() {
        assertEquals("abc", StringUtils.defaultIfEmpty(new StringBuffer("abc"), new StringBuffer("NULL")).toString());
    }

    @Test
    public void testDefaultIfEmpty_StringBuffers_3() {
        assertNull(StringUtils.defaultIfEmpty(new StringBuffer(""), (StringBuffer) null));
    }

    @Test
    public void testDefaultIfEmpty_StringBuffers_4() {
        final StringBuffer s = StringUtils.defaultIfEmpty(new StringBuffer("abc"), new StringBuffer("NULL"));
        assertEquals("abc", s.toString());
    }

    @Test
    public void testDefaultIfEmpty_StringBuilders_1() {
        assertEquals("NULL", StringUtils.defaultIfEmpty(new StringBuilder(""), new StringBuilder("NULL")).toString());
    }

    @Test
    public void testDefaultIfEmpty_StringBuilders_2() {
        assertEquals("abc", StringUtils.defaultIfEmpty(new StringBuilder("abc"), new StringBuilder("NULL")).toString());
    }

    @Test
    public void testDefaultIfEmpty_StringBuilders_3() {
        assertNull(StringUtils.defaultIfEmpty(new StringBuilder(""), (StringBuilder) null));
    }

    @Test
    public void testDefaultIfEmpty_StringBuilders_4() {
        final StringBuilder s = StringUtils.defaultIfEmpty(new StringBuilder("abc"), new StringBuilder("NULL"));
        assertEquals("abc", s.toString());
    }

    @Test
    public void testDefaultIfEmpty_StringString_1() {
        assertEquals("NULL", StringUtils.defaultIfEmpty(null, "NULL"));
    }

    @Test
    public void testDefaultIfEmpty_StringString_2() {
        assertEquals("NULL", StringUtils.defaultIfEmpty("", "NULL"));
    }

    @Test
    public void testDefaultIfEmpty_StringString_3() {
        assertEquals("abc", StringUtils.defaultIfEmpty("abc", "NULL"));
    }

    @Test
    public void testDefaultIfEmpty_StringString_4() {
        assertNull(StringUtils.getIfEmpty("", null));
    }

    @Test
    public void testDefaultIfEmpty_StringString_5() {
        final String s = StringUtils.defaultIfEmpty("abc", "NULL");
        assertEquals("abc", s);
    }

    @Test
    public void testDeleteWhitespace_String_1() {
        assertNull(StringUtils.deleteWhitespace(null));
    }

    @Test
    public void testDeleteWhitespace_String_2() {
        assertEquals("", StringUtils.deleteWhitespace(""));
    }

    @Test
    public void testDeleteWhitespace_String_3() {
        assertEquals("", StringUtils.deleteWhitespace("  \u000C  \t\t\u001F\n\n \u000B  "));
    }

    @Test
    public void testDeleteWhitespace_String_4() {
        assertEquals("", StringUtils.deleteWhitespace(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testDeleteWhitespace_String_5() {
        assertEquals(StringUtilsTest.NON_WHITESPACE, StringUtils.deleteWhitespace(StringUtilsTest.NON_WHITESPACE));
    }

    @Test
    public void testDeleteWhitespace_String_6() {
        assertEquals("\u00A0\u202F", StringUtils.deleteWhitespace("  \u00A0  \t\t\n\n \u202F  "));
    }

    @Test
    public void testDeleteWhitespace_String_7() {
        assertEquals("\u00A0\u202F", StringUtils.deleteWhitespace("\u00A0\u202F"));
    }

    @Test
    public void testDeleteWhitespace_String_8() {
        assertEquals("test", StringUtils.deleteWhitespace("\u000Bt  \t\n\u0009e\rs\n\n   \tt"));
    }

    @Test
    public void testDifference_StringString_1() {
        assertNull(StringUtils.difference(null, null));
    }

    @Test
    public void testDifference_StringString_2() {
        assertEquals("", StringUtils.difference("", ""));
    }

    @Test
    public void testDifference_StringString_3() {
        assertEquals("abc", StringUtils.difference("", "abc"));
    }

    @Test
    public void testDifference_StringString_4() {
        assertEquals("", StringUtils.difference("abc", ""));
    }

    @Test
    public void testDifference_StringString_5() {
        assertEquals("i am a robot", StringUtils.difference(null, "i am a robot"));
    }

    @Test
    public void testDifference_StringString_6() {
        assertEquals("i am a machine", StringUtils.difference("i am a machine", null));
    }

    @Test
    public void testDifference_StringString_7() {
        assertEquals("robot", StringUtils.difference("i am a machine", "i am a robot"));
    }

    @Test
    public void testDifference_StringString_8() {
        assertEquals("", StringUtils.difference("abc", "abc"));
    }

    @Test
    public void testDifference_StringString_9() {
        assertEquals("you are a robot", StringUtils.difference("i am a robot", "you are a robot"));
    }

    @Test
    public void testDifferenceAt_StringArray_1() {
        assertEquals(-1, StringUtils.indexOfDifference((String[]) null));
    }

    @Test
    public void testDifferenceAt_StringArray_2() {
        assertEquals(-1, StringUtils.indexOfDifference());
    }

    @Test
    public void testDifferenceAt_StringArray_3() {
        assertEquals(-1, StringUtils.indexOfDifference("abc"));
    }

    @Test
    public void testDifferenceAt_StringArray_4() {
        assertEquals(-1, StringUtils.indexOfDifference(null, null));
    }

    @Test
    public void testDifferenceAt_StringArray_5() {
        assertEquals(-1, StringUtils.indexOfDifference("", ""));
    }

    @Test
    public void testDifferenceAt_StringArray_6() {
        assertEquals(0, StringUtils.indexOfDifference("", null));
    }

    @Test
    public void testDifferenceAt_StringArray_7() {
        assertEquals(0, StringUtils.indexOfDifference("abc", null, null));
    }

    @Test
    public void testDifferenceAt_StringArray_8() {
        assertEquals(0, StringUtils.indexOfDifference(null, null, "abc"));
    }

    @Test
    public void testDifferenceAt_StringArray_9() {
        assertEquals(0, StringUtils.indexOfDifference("", "abc"));
    }

    @Test
    public void testDifferenceAt_StringArray_10() {
        assertEquals(0, StringUtils.indexOfDifference("abc", ""));
    }

    @Test
    public void testDifferenceAt_StringArray_11() {
        assertEquals(-1, StringUtils.indexOfDifference("abc", "abc"));
    }

    @Test
    public void testDifferenceAt_StringArray_12() {
        assertEquals(1, StringUtils.indexOfDifference("abc", "a"));
    }

    @Test
    public void testDifferenceAt_StringArray_13() {
        assertEquals(2, StringUtils.indexOfDifference("ab", "abxyz"));
    }

    @Test
    public void testDifferenceAt_StringArray_14() {
        assertEquals(2, StringUtils.indexOfDifference("abcde", "abxyz"));
    }

    @Test
    public void testDifferenceAt_StringArray_15() {
        assertEquals(0, StringUtils.indexOfDifference("abcde", "xyz"));
    }

    @Test
    public void testDifferenceAt_StringArray_16() {
        assertEquals(0, StringUtils.indexOfDifference("xyz", "abcde"));
    }

    @Test
    public void testDifferenceAt_StringArray_17() {
        assertEquals(7, StringUtils.indexOfDifference("i am a machine", "i am a robot"));
    }

    @Test
    public void testDifferenceAt_StringString_1() {
        assertEquals(-1, StringUtils.indexOfDifference(null, null));
    }

    @Test
    public void testDifferenceAt_StringString_2() {
        assertEquals(0, StringUtils.indexOfDifference(null, "i am a robot"));
    }

    @Test
    public void testDifferenceAt_StringString_3() {
        assertEquals(-1, StringUtils.indexOfDifference("", ""));
    }

    @Test
    public void testDifferenceAt_StringString_4() {
        assertEquals(0, StringUtils.indexOfDifference("", "abc"));
    }

    @Test
    public void testDifferenceAt_StringString_5() {
        assertEquals(0, StringUtils.indexOfDifference("abc", ""));
    }

    @Test
    public void testDifferenceAt_StringString_6() {
        assertEquals(0, StringUtils.indexOfDifference("i am a machine", null));
    }

    @Test
    public void testDifferenceAt_StringString_7() {
        assertEquals(7, StringUtils.indexOfDifference("i am a machine", "i am a robot"));
    }

    @Test
    public void testDifferenceAt_StringString_8() {
        assertEquals(-1, StringUtils.indexOfDifference("foo", "foo"));
    }

    @Test
    public void testDifferenceAt_StringString_9() {
        assertEquals(0, StringUtils.indexOfDifference("i am a robot", "you are a robot"));
    }

    @Test
    public void testEMPTY_1() {
        assertNotNull(StringUtils.EMPTY);
    }

    @Test
    public void testEMPTY_2() {
        assertEquals("", StringUtils.EMPTY);
    }

    @Test
    public void testEMPTY_3() {
        assertEquals(0, StringUtils.EMPTY.length());
    }

    @Test
    public void testEscapeSurrogatePairs_1() {
        assertEquals("\uD83D\uDE30", StringEscapeUtils.escapeCsv("\uD83D\uDE30"));
    }

    @Test
    public void testEscapeSurrogatePairs_2() {
        assertEquals("\uD800\uDC00", StringEscapeUtils.escapeCsv("\uD800\uDC00"));
    }

    @Test
    public void testEscapeSurrogatePairs_3() {
        assertEquals("\uD834\uDD1E", StringEscapeUtils.escapeCsv("\uD834\uDD1E"));
    }

    @Test
    public void testEscapeSurrogatePairs_4() {
        assertEquals("\uDBFF\uDFFD", StringEscapeUtils.escapeCsv("\uDBFF\uDFFD"));
    }

    @Test
    public void testEscapeSurrogatePairs_5() {
        assertEquals("\uDBFF\uDFFD", StringEscapeUtils.escapeHtml3("\uDBFF\uDFFD"));
    }

    @Test
    public void testEscapeSurrogatePairs_6() {
        assertEquals("\uDBFF\uDFFD", StringEscapeUtils.escapeHtml4("\uDBFF\uDFFD"));
    }

    @Test
    public void testEscapeSurrogatePairs_7() {
        assertEquals("\uDBFF\uDFFD", StringEscapeUtils.escapeXml("\uDBFF\uDFFD"));
    }

    @Test
    public void testEscapeSurrogatePairsLang858_1() {
        assertEquals("\\uDBFF\\uDFFD", StringEscapeUtils.escapeJava("\uDBFF\uDFFD"));
    }

    @Test
    public void testEscapeSurrogatePairsLang858_2() {
        assertEquals("\\uDBFF\\uDFFD", StringEscapeUtils.escapeEcmaScript("\uDBFF\uDFFD"));
    }

    @Test
    public void testGetBytes_Charset_1() {
        assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, StringUtils.getBytes(null, (Charset) null));
    }

    @Test
    public void testGetBytes_Charset_2() {
        assertArrayEquals(StringUtils.EMPTY.getBytes(), StringUtils.getBytes(StringUtils.EMPTY, (Charset) null));
    }

    @Test
    public void testGetBytes_Charset_3() {
        assertArrayEquals(StringUtils.EMPTY.getBytes(StandardCharsets.US_ASCII), StringUtils.getBytes(StringUtils.EMPTY, StandardCharsets.US_ASCII));
    }

    @Test
    public void testGetBytes_String_1() throws UnsupportedEncodingException {
        assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, StringUtils.getBytes(null, (String) null));
    }

    @Test
    public void testGetBytes_String_2() throws UnsupportedEncodingException {
        assertArrayEquals(StringUtils.EMPTY.getBytes(), StringUtils.getBytes(StringUtils.EMPTY, (String) null));
    }

    @Test
    public void testGetBytes_String_3() throws UnsupportedEncodingException {
        assertArrayEquals(StringUtils.EMPTY.getBytes(StandardCharsets.US_ASCII.name()), StringUtils.getBytes(StringUtils.EMPTY, StandardCharsets.US_ASCII.name()));
    }

    @Test
    public void testGetCommonPrefix_StringArray_1() {
        assertEquals("", StringUtils.getCommonPrefix((String[]) null));
    }

    @Test
    public void testGetCommonPrefix_StringArray_2() {
        assertEquals("", StringUtils.getCommonPrefix());
    }

    @Test
    public void testGetCommonPrefix_StringArray_3() {
        assertEquals("abc", StringUtils.getCommonPrefix("abc"));
    }

    @Test
    public void testGetCommonPrefix_StringArray_4() {
        assertEquals("", StringUtils.getCommonPrefix(null, null));
    }

    @Test
    public void testGetCommonPrefix_StringArray_5() {
        assertEquals("", StringUtils.getCommonPrefix("", ""));
    }

    @Test
    public void testGetCommonPrefix_StringArray_6() {
        assertEquals("", StringUtils.getCommonPrefix("", null));
    }

    @Test
    public void testGetCommonPrefix_StringArray_7() {
        assertEquals("", StringUtils.getCommonPrefix("abc", null, null));
    }

    @Test
    public void testGetCommonPrefix_StringArray_8() {
        assertEquals("", StringUtils.getCommonPrefix(null, null, "abc"));
    }

    @Test
    public void testGetCommonPrefix_StringArray_9() {
        assertEquals("", StringUtils.getCommonPrefix("", "abc"));
    }

    @Test
    public void testGetCommonPrefix_StringArray_10() {
        assertEquals("", StringUtils.getCommonPrefix("abc", ""));
    }

    @Test
    public void testGetCommonPrefix_StringArray_11() {
        assertEquals("abc", StringUtils.getCommonPrefix("abc", "abc"));
    }

    @Test
    public void testGetCommonPrefix_StringArray_12() {
        assertEquals("a", StringUtils.getCommonPrefix("abc", "a"));
    }

    @Test
    public void testGetCommonPrefix_StringArray_13() {
        assertEquals("ab", StringUtils.getCommonPrefix("ab", "abxyz"));
    }

    @Test
    public void testGetCommonPrefix_StringArray_14() {
        assertEquals("ab", StringUtils.getCommonPrefix("abcde", "abxyz"));
    }

    @Test
    public void testGetCommonPrefix_StringArray_15() {
        assertEquals("", StringUtils.getCommonPrefix("abcde", "xyz"));
    }

    @Test
    public void testGetCommonPrefix_StringArray_16() {
        assertEquals("", StringUtils.getCommonPrefix("xyz", "abcde"));
    }

    @Test
    public void testGetCommonPrefix_StringArray_17() {
        assertEquals("i am a ", StringUtils.getCommonPrefix("i am a machine", "i am a robot"));
    }

    @Test
    public void testGetDigits_1() {
        assertNull(StringUtils.getDigits(null));
    }

    @Test
    public void testGetDigits_2() {
        assertEquals("", StringUtils.getDigits(""));
    }

    @Test
    public void testGetDigits_3() {
        assertEquals("", StringUtils.getDigits("abc"));
    }

    @Test
    public void testGetDigits_4() {
        assertEquals("1000", StringUtils.getDigits("1000$"));
    }

    @Test
    public void testGetDigits_5() {
        assertEquals("12345", StringUtils.getDigits("123password45"));
    }

    @Test
    public void testGetDigits_6() {
        assertEquals("5417543010", StringUtils.getDigits("(541) 754-3010"));
    }

    @Test
    public void testGetDigits_7() {
        assertEquals("\u0967\u0968\u0969", StringUtils.getDigits("\u0967\u0968\u0969"));
    }

    @Test
    public void testGetFuzzyDistance_1() {
        assertEquals(0, StringUtils.getFuzzyDistance("", "", Locale.ENGLISH));
    }

    @Test
    public void testGetFuzzyDistance_2() {
        assertEquals(0, StringUtils.getFuzzyDistance("Workshop", "b", Locale.ENGLISH));
    }

    @Test
    public void testGetFuzzyDistance_3() {
        assertEquals(1, StringUtils.getFuzzyDistance("Room", "o", Locale.ENGLISH));
    }

    @Test
    public void testGetFuzzyDistance_4() {
        assertEquals(1, StringUtils.getFuzzyDistance("Workshop", "w", Locale.ENGLISH));
    }

    @Test
    public void testGetFuzzyDistance_5() {
        assertEquals(2, StringUtils.getFuzzyDistance("Workshop", "ws", Locale.ENGLISH));
    }

    @Test
    public void testGetFuzzyDistance_6() {
        assertEquals(4, StringUtils.getFuzzyDistance("Workshop", "wo", Locale.ENGLISH));
    }

    @Test
    public void testGetFuzzyDistance_7() {
        assertEquals(3, StringUtils.getFuzzyDistance("Apache Software Foundation", "asf", Locale.ENGLISH));
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_1() {
        assertEquals(0.93d, StringUtils.getJaroWinklerDistance("frog", "fog"));
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_2() {
        assertEquals(0.0d, StringUtils.getJaroWinklerDistance("fly", "ant"));
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_3() {
        assertEquals(0.44d, StringUtils.getJaroWinklerDistance("elephant", "hippo"));
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_4() {
        assertEquals(0.84d, StringUtils.getJaroWinklerDistance("dwayne", "duane"));
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_5() {
        assertEquals(0.93d, StringUtils.getJaroWinklerDistance("ABC Corporation", "ABC Corp"));
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_6() {
        assertEquals(0.95d, StringUtils.getJaroWinklerDistance("D N H Enterprises Inc", "D & H Enterprises, Inc."));
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_7() {
        assertEquals(0.92d, StringUtils.getJaroWinklerDistance("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_8() {
        assertEquals(0.88d, StringUtils.getJaroWinklerDistance("PENNSYLVANIA", "PENNCISYLVNIA"));
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_9() {
        assertEquals(0.63d, StringUtils.getJaroWinklerDistance("Haus Ingeborg", "Ingeborg Esser"));
    }

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

    @Test
    public void testGetLevenshteinDistance_StringStringInt_1() {
        assertEquals(0, StringUtils.getLevenshteinDistance("", "", 0));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_2() {
        assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", "", 8));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_3() {
        assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", "", 7));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_4() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("aaapppp", "", 6));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_5() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("b", "a", 0));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_6() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("a", "b", 0));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_7() {
        assertEquals(0, StringUtils.getLevenshteinDistance("aa", "aa", 0));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_8() {
        assertEquals(0, StringUtils.getLevenshteinDistance("aa", "aa", 2));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_9() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("aaa", "bbb", 2));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_10() {
        assertEquals(3, StringUtils.getLevenshteinDistance("aaa", "bbb", 3));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_11() {
        assertEquals(6, StringUtils.getLevenshteinDistance("aaaaaa", "b", 10));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_12() {
        assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", "b", 8));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_13() {
        assertEquals(3, StringUtils.getLevenshteinDistance("a", "bbb", 4));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_14() {
        assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", "b", 7));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_15() {
        assertEquals(3, StringUtils.getLevenshteinDistance("a", "bbb", 3));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_16() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("a", "bbb", 2));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_17() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("bbb", "a", 2));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_18() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("aaapppp", "b", 6));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_19() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("a", "bbb", 1));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_20() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("bbb", "a", 1));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_21() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("12345", "1234567", 1));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_22() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("1234567", "12345", 1));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_23() {
        assertEquals(1, StringUtils.getLevenshteinDistance("frog", "fog", 1));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_24() {
        assertEquals(3, StringUtils.getLevenshteinDistance("fly", "ant", 3));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_25() {
        assertEquals(7, StringUtils.getLevenshteinDistance("elephant", "hippo", 7));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_26() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("elephant", "hippo", 6));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_27() {
        assertEquals(7, StringUtils.getLevenshteinDistance("hippo", "elephant", 7));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_28() {
        assertEquals(-1, StringUtils.getLevenshteinDistance("hippo", "elephant", 6));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_29() {
        assertEquals(8, StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz", 8));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_30() {
        assertEquals(8, StringUtils.getLevenshteinDistance("zzzzzzzz", "hippo", 8));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_31() {
        assertEquals(1, StringUtils.getLevenshteinDistance("hello", "hallo", 1));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_32() {
        assertEquals(1, StringUtils.getLevenshteinDistance("frog", "fog", Integer.MAX_VALUE));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_33() {
        assertEquals(3, StringUtils.getLevenshteinDistance("fly", "ant", Integer.MAX_VALUE));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_34() {
        assertEquals(7, StringUtils.getLevenshteinDistance("elephant", "hippo", Integer.MAX_VALUE));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_35() {
        assertEquals(7, StringUtils.getLevenshteinDistance("hippo", "elephant", Integer.MAX_VALUE));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_36() {
        assertEquals(8, StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz", Integer.MAX_VALUE));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_37() {
        assertEquals(8, StringUtils.getLevenshteinDistance("zzzzzzzz", "hippo", Integer.MAX_VALUE));
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt_38() {
        assertEquals(1, StringUtils.getLevenshteinDistance("hello", "hallo", Integer.MAX_VALUE));
    }

    @Test
    public void testIsAllLowerCase_1() {
        assertFalse(StringUtils.isAllLowerCase(null));
    }

    @Test
    public void testIsAllLowerCase_2() {
        assertFalse(StringUtils.isAllLowerCase(StringUtils.EMPTY));
    }

    @Test
    public void testIsAllLowerCase_3() {
        assertFalse(StringUtils.isAllLowerCase("  "));
    }

    @Test
    public void testIsAllLowerCase_4() {
        assertTrue(StringUtils.isAllLowerCase("abc"));
    }

    @Test
    public void testIsAllLowerCase_5() {
        assertFalse(StringUtils.isAllLowerCase("abc "));
    }

    @Test
    public void testIsAllLowerCase_6() {
        assertFalse(StringUtils.isAllLowerCase("abc\n"));
    }

    @Test
    public void testIsAllLowerCase_7() {
        assertFalse(StringUtils.isAllLowerCase("abC"));
    }

    @Test
    public void testIsAllLowerCase_8() {
        assertFalse(StringUtils.isAllLowerCase("ab c"));
    }

    @Test
    public void testIsAllLowerCase_9() {
        assertFalse(StringUtils.isAllLowerCase("ab1c"));
    }

    @Test
    public void testIsAllLowerCase_10() {
        assertFalse(StringUtils.isAllLowerCase("ab/c"));
    }

    @Test
    public void testIsAllUpperCase_1() {
        assertFalse(StringUtils.isAllUpperCase(null));
    }

    @Test
    public void testIsAllUpperCase_2() {
        assertFalse(StringUtils.isAllUpperCase(StringUtils.EMPTY));
    }

    @Test
    public void testIsAllUpperCase_3() {
        assertFalse(StringUtils.isAllUpperCase("  "));
    }

    @Test
    public void testIsAllUpperCase_4() {
        assertTrue(StringUtils.isAllUpperCase("ABC"));
    }

    @Test
    public void testIsAllUpperCase_5() {
        assertFalse(StringUtils.isAllUpperCase("ABC "));
    }

    @Test
    public void testIsAllUpperCase_6() {
        assertFalse(StringUtils.isAllUpperCase("ABC\n"));
    }

    @Test
    public void testIsAllUpperCase_7() {
        assertFalse(StringUtils.isAllUpperCase("aBC"));
    }

    @Test
    public void testIsAllUpperCase_8() {
        assertFalse(StringUtils.isAllUpperCase("A C"));
    }

    @Test
    public void testIsAllUpperCase_9() {
        assertFalse(StringUtils.isAllUpperCase("A1C"));
    }

    @Test
    public void testIsAllUpperCase_10() {
        assertFalse(StringUtils.isAllUpperCase("A/C"));
    }

    @Test
    public void testIsMixedCase_1() {
        assertFalse(StringUtils.isMixedCase(null));
    }

    @Test
    public void testIsMixedCase_2() {
        assertFalse(StringUtils.isMixedCase(StringUtils.EMPTY));
    }

    @Test
    public void testIsMixedCase_3() {
        assertFalse(StringUtils.isMixedCase(" "));
    }

    @Test
    public void testIsMixedCase_4() {
        assertFalse(StringUtils.isMixedCase("A"));
    }

    @Test
    public void testIsMixedCase_5() {
        assertFalse(StringUtils.isMixedCase("a"));
    }

    @Test
    public void testIsMixedCase_6() {
        assertFalse(StringUtils.isMixedCase("/"));
    }

    @Test
    public void testIsMixedCase_7() {
        assertFalse(StringUtils.isMixedCase("A/"));
    }

    @Test
    public void testIsMixedCase_8() {
        assertFalse(StringUtils.isMixedCase("/b"));
    }

    @Test
    public void testIsMixedCase_9() {
        assertFalse(StringUtils.isMixedCase("abc"));
    }

    @Test
    public void testIsMixedCase_10() {
        assertFalse(StringUtils.isMixedCase("ABC"));
    }

    @Test
    public void testIsMixedCase_11() {
        assertTrue(StringUtils.isMixedCase("aBc"));
    }

    @Test
    public void testIsMixedCase_12() {
        assertTrue(StringUtils.isMixedCase("aBc "));
    }

    @Test
    public void testIsMixedCase_13() {
        assertTrue(StringUtils.isMixedCase("A c"));
    }

    @Test
    public void testIsMixedCase_14() {
        assertTrue(StringUtils.isMixedCase("aBc\n"));
    }

    @Test
    public void testIsMixedCase_15() {
        assertTrue(StringUtils.isMixedCase("A1c"));
    }

    @Test
    public void testIsMixedCase_16() {
        assertTrue(StringUtils.isMixedCase("a/C"));
    }

    @Test
    public void testJoin_ArrayCharSeparator_1() {
        assertNull(StringUtils.join((Object[]) null, ','));
    }

    @Test
    public void testJoin_ArrayCharSeparator_2() {
        assertEquals(TEXT_LIST_CHAR, StringUtils.join(ARRAY_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayCharSeparator_3() {
        assertEquals("", StringUtils.join(EMPTY_ARRAY_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayCharSeparator_4() {
        assertEquals(";;foo", StringUtils.join(MIXED_ARRAY_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayCharSeparator_5() {
        assertEquals("foo;2", StringUtils.join(MIXED_TYPE_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayCharSeparator_6() {
        assertNull(StringUtils.join((Object[]) null, ',', 0, 1));
    }

    @Test
    public void testJoin_ArrayCharSeparator_7() {
        assertEquals("/", StringUtils.join(MIXED_ARRAY_LIST, '/', 0, MIXED_ARRAY_LIST.length - 1));
    }

    @Test
    public void testJoin_ArrayCharSeparator_8() {
        assertEquals("foo", StringUtils.join(MIXED_TYPE_LIST, '/', 0, 1));
    }

    @Test
    public void testJoin_ArrayCharSeparator_9() {
        assertEquals("null", StringUtils.join(NULL_TO_STRING_LIST, '/', 0, 1));
    }

    @Test
    public void testJoin_ArrayCharSeparator_10() {
        assertEquals("foo/2", StringUtils.join(MIXED_TYPE_LIST, '/', 0, 2));
    }

    @Test
    public void testJoin_ArrayCharSeparator_11() {
        assertEquals("2", StringUtils.join(MIXED_TYPE_LIST, '/', 1, 2));
    }

    @Test
    public void testJoin_ArrayCharSeparator_12() {
        assertEquals("", StringUtils.join(MIXED_TYPE_LIST, '/', 2, 1));
    }

    @Test
    public void testJoin_ArrayOfBooleans_1() {
        assertNull(StringUtils.join((boolean[]) null, COMMA_SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfBooleans_2() {
        assertEquals("false;false", StringUtils.join(ARRAY_FALSE_FALSE, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfBooleans_3() {
        assertEquals("", StringUtils.join(EMPTY, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfBooleans_4() {
        assertEquals("false,true,false", StringUtils.join(ARRAY_FALSE_TRUE_FALSE, COMMA_SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfBooleans_5() {
        assertEquals("true", StringUtils.join(ARRAY_FALSE_TRUE, SEPARATOR_CHAR, 1, 2));
    }

    @Test
    public void testJoin_ArrayOfBooleans_6() {
        assertNull(StringUtils.join((boolean[]) null, SEPARATOR_CHAR, 0, 1));
    }

    @Test
    public void testJoin_ArrayOfBooleans_7() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(ARRAY_FALSE_FALSE, SEPARATOR_CHAR, 0, 0));
    }

    @Test
    public void testJoin_ArrayOfBooleans_8() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(ARRAY_FALSE_TRUE_FALSE, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfBytes_1() {
        assertNull(StringUtils.join((byte[]) null, ','));
    }

    @Test
    public void testJoin_ArrayOfBytes_2() {
        assertEquals("1;2", StringUtils.join(BYTE_PRIM_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfBytes_3() {
        assertEquals("2", StringUtils.join(BYTE_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
    }

    @Test
    public void testJoin_ArrayOfBytes_4() {
        assertNull(StringUtils.join((byte[]) null, SEPARATOR_CHAR, 0, 1));
    }

    @Test
    public void testJoin_ArrayOfBytes_5() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(BYTE_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
    }

    @Test
    public void testJoin_ArrayOfBytes_6() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(BYTE_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfChars_1() {
        assertNull(StringUtils.join((char[]) null, ','));
    }

    @Test
    public void testJoin_ArrayOfChars_2() {
        assertEquals("1;2", StringUtils.join(CHAR_PRIM_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfChars_3() {
        assertEquals("2", StringUtils.join(CHAR_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
    }

    @Test
    public void testJoin_ArrayOfChars_4() {
        assertNull(StringUtils.join((char[]) null, SEPARATOR_CHAR, 0, 1));
    }

    @Test
    public void testJoin_ArrayOfChars_5() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(CHAR_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
    }

    @Test
    public void testJoin_ArrayOfChars_6() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(CHAR_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfDoubles_1() {
        assertNull(StringUtils.join((double[]) null, ','));
    }

    @Test
    public void testJoin_ArrayOfDoubles_2() {
        assertEquals("1.0;2.0", StringUtils.join(DOUBLE_PRIM_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfDoubles_3() {
        assertEquals("2.0", StringUtils.join(DOUBLE_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
    }

    @Test
    public void testJoin_ArrayOfDoubles_4() {
        assertNull(StringUtils.join((double[]) null, SEPARATOR_CHAR, 0, 1));
    }

    @Test
    public void testJoin_ArrayOfDoubles_5() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(DOUBLE_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
    }

    @Test
    public void testJoin_ArrayOfDoubles_6() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(DOUBLE_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfFloats_1() {
        assertNull(StringUtils.join((float[]) null, ','));
    }

    @Test
    public void testJoin_ArrayOfFloats_2() {
        assertEquals("1.0;2.0", StringUtils.join(FLOAT_PRIM_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfFloats_3() {
        assertEquals("2.0", StringUtils.join(FLOAT_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
    }

    @Test
    public void testJoin_ArrayOfFloats_4() {
        assertNull(StringUtils.join((float[]) null, SEPARATOR_CHAR, 0, 1));
    }

    @Test
    public void testJoin_ArrayOfFloats_5() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(FLOAT_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
    }

    @Test
    public void testJoin_ArrayOfFloats_6() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(FLOAT_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfInts_1() {
        assertNull(StringUtils.join((int[]) null, ','));
    }

    @Test
    public void testJoin_ArrayOfInts_2() {
        assertEquals("1;2", StringUtils.join(INT_PRIM_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfInts_3() {
        assertEquals("2", StringUtils.join(INT_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
    }

    @Test
    public void testJoin_ArrayOfInts_4() {
        assertNull(StringUtils.join((int[]) null, SEPARATOR_CHAR, 0, 1));
    }

    @Test
    public void testJoin_ArrayOfInts_5() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(INT_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
    }

    @Test
    public void testJoin_ArrayOfInts_6() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(INT_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfLongs_1() {
        assertNull(StringUtils.join((long[]) null, ','));
    }

    @Test
    public void testJoin_ArrayOfLongs_2() {
        assertEquals("1;2", StringUtils.join(LONG_PRIM_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfLongs_3() {
        assertEquals("2", StringUtils.join(LONG_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
    }

    @Test
    public void testJoin_ArrayOfLongs_4() {
        assertNull(StringUtils.join((long[]) null, SEPARATOR_CHAR, 0, 1));
    }

    @Test
    public void testJoin_ArrayOfLongs_5() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(LONG_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
    }

    @Test
    public void testJoin_ArrayOfLongs_6() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(LONG_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayOfShorts_1() {
        assertNull(StringUtils.join((short[]) null, ','));
    }

    @Test
    public void testJoin_ArrayOfShorts_2() {
        assertEquals("1;2", StringUtils.join(SHORT_PRIM_LIST, SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_ArrayOfShorts_3() {
        assertEquals("2", StringUtils.join(SHORT_PRIM_LIST, SEPARATOR_CHAR, 1, 2));
    }

    @Test
    public void testJoin_ArrayOfShorts_4() {
        assertNull(StringUtils.join((short[]) null, SEPARATOR_CHAR, 0, 1));
    }

    @Test
    public void testJoin_ArrayOfShorts_5() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(SHORT_PRIM_LIST, SEPARATOR_CHAR, 0, 0));
    }

    @Test
    public void testJoin_ArrayOfShorts_6() {
        assertEquals(StringUtils.EMPTY, StringUtils.join(SHORT_PRIM_LIST, SEPARATOR_CHAR, 1, 0));
    }

    @Test
    public void testJoin_ArrayString_EmptyDelimiter_1() {
        assertNull(StringUtils.join((Object[]) null, null));
    }

    @Test
    public void testJoin_ArrayString_EmptyDelimiter_2() {
        assertEquals(TEXT_LIST_NOSEP, StringUtils.join(ARRAY_LIST, null));
    }

    @Test
    public void testJoin_ArrayString_EmptyDelimiter_3() {
        assertEquals(TEXT_LIST_NOSEP, StringUtils.join(ARRAY_LIST, ""));
    }

    @Test
    public void testJoin_ArrayString_EmptyDelimiter_4() {
        assertEquals("", StringUtils.join(NULL_ARRAY_LIST, null));
    }

    @Test
    public void testJoin_ArrayString_EmptyDelimiter_5() {
        assertEquals("", StringUtils.join(EMPTY_ARRAY_LIST, null));
    }

    @Test
    public void testJoin_ArrayString_EmptyDelimiter_6() {
        assertEquals("", StringUtils.join(EMPTY_ARRAY_LIST, ""));
    }

    @Test
    public void testJoin_ArrayString_EmptyDelimiter_7() {
        assertEquals("", StringUtils.join(MIXED_ARRAY_LIST, "", 0, MIXED_ARRAY_LIST.length - 1));
    }

    @Test
    public void testJoin_IterableChar_1() {
        assertNull(StringUtils.join((Iterable<?>) null, ','));
    }

    @Test
    public void testJoin_IterableChar_2() {
        assertEquals(TEXT_LIST_CHAR, StringUtils.join(Arrays.asList(ARRAY_LIST), SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_IterableChar_3() {
        assertEquals("", StringUtils.join(Arrays.asList(NULL_ARRAY_LIST), SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_IterableChar_4() {
        assertEquals("", StringUtils.join(Arrays.asList(EMPTY_ARRAY_LIST), SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_IterableChar_5() {
        assertEquals("foo", StringUtils.join(Collections.singleton("foo"), 'x'));
    }

    @Test
    public void testJoin_IterableString_1() {
        assertNull(StringUtils.join((Iterable<?>) null, null));
    }

    @Test
    public void testJoin_IterableString_2() {
        assertEquals(TEXT_LIST_NOSEP, StringUtils.join(Arrays.asList(ARRAY_LIST), null));
    }

    @Test
    public void testJoin_IterableString_3() {
        assertEquals(TEXT_LIST_NOSEP, StringUtils.join(Arrays.asList(ARRAY_LIST), ""));
    }

    @Test
    public void testJoin_IterableString_4() {
        assertEquals("foo", StringUtils.join(Collections.singleton("foo"), "x"));
    }

    @Test
    public void testJoin_IterableString_5() {
        assertEquals("foo", StringUtils.join(Collections.singleton("foo"), null));
    }

    @Test
    public void testJoin_IterableString_6() {
        assertEquals("", StringUtils.join(Arrays.asList(NULL_ARRAY_LIST), null));
    }

    @Test
    public void testJoin_IterableString_7() {
        assertEquals("", StringUtils.join(Arrays.asList(EMPTY_ARRAY_LIST), null));
    }

    @Test
    public void testJoin_IterableString_8() {
        assertEquals("", StringUtils.join(Arrays.asList(EMPTY_ARRAY_LIST), ""));
    }

    @Test
    public void testJoin_IterableString_9() {
        assertEquals("", StringUtils.join(Arrays.asList(EMPTY_ARRAY_LIST), SEPARATOR));
    }

    @Test
    public void testJoin_IterableString_10() {
        assertEquals(TEXT_LIST, StringUtils.join(Arrays.asList(ARRAY_LIST), SEPARATOR));
    }

    @Test
    public void testJoin_IteratorChar_1() {
        assertNull(StringUtils.join((Iterator<?>) null, ','));
    }

    @Test
    public void testJoin_IteratorChar_2() {
        assertEquals(TEXT_LIST_CHAR, StringUtils.join(Arrays.asList(ARRAY_LIST).iterator(), SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_IteratorChar_3() {
        assertEquals("", StringUtils.join(Arrays.asList(NULL_ARRAY_LIST).iterator(), SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_IteratorChar_4() {
        assertEquals("", StringUtils.join(Arrays.asList(EMPTY_ARRAY_LIST).iterator(), SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_IteratorChar_5() {
        assertEquals("foo", StringUtils.join(Collections.singleton("foo").iterator(), 'x'));
    }

    @Test
    public void testJoin_IteratorChar_6() {
        assertEquals("null", StringUtils.join(Arrays.asList(NULL_TO_STRING_LIST).iterator(), SEPARATOR_CHAR));
    }

    @Test
    public void testJoin_IteratorString_1() {
        assertNull(StringUtils.join((Iterator<?>) null, null));
    }

    @Test
    public void testJoin_IteratorString_2() {
        assertEquals(TEXT_LIST_NOSEP, StringUtils.join(Arrays.asList(ARRAY_LIST).iterator(), null));
    }

    @Test
    public void testJoin_IteratorString_3() {
        assertEquals(TEXT_LIST_NOSEP, StringUtils.join(Arrays.asList(ARRAY_LIST).iterator(), ""));
    }

    @Test
    public void testJoin_IteratorString_4() {
        assertEquals("foo", StringUtils.join(Collections.singleton("foo").iterator(), "x"));
    }

    @Test
    public void testJoin_IteratorString_5() {
        assertEquals("foo", StringUtils.join(Collections.singleton("foo").iterator(), null));
    }

    @Test
    public void testJoin_IteratorString_6() {
        assertEquals("", StringUtils.join(Arrays.asList(NULL_ARRAY_LIST).iterator(), null));
    }

    @Test
    public void testJoin_IteratorString_7() {
        assertEquals("", StringUtils.join(Arrays.asList(EMPTY_ARRAY_LIST).iterator(), null));
    }

    @Test
    public void testJoin_IteratorString_8() {
        assertEquals("", StringUtils.join(Arrays.asList(EMPTY_ARRAY_LIST).iterator(), ""));
    }

    @Test
    public void testJoin_IteratorString_9() {
        assertEquals("", StringUtils.join(Arrays.asList(EMPTY_ARRAY_LIST).iterator(), SEPARATOR));
    }

    @Test
    public void testJoin_IteratorString_10() {
        assertEquals(TEXT_LIST, StringUtils.join(Arrays.asList(ARRAY_LIST).iterator(), SEPARATOR));
    }

    @Test
    public void testJoin_IteratorString_11() {
        assertEquals("null", StringUtils.join(Arrays.asList(NULL_TO_STRING_LIST).iterator(), SEPARATOR));
    }

    @Test
    public void testJoin_List_CharDelimiter_1() {
        assertEquals("/", StringUtils.join(MIXED_STRING_LIST, '/', 0, MIXED_STRING_LIST.size() - 1));
    }

    @Test
    public void testJoin_List_CharDelimiter_2() {
        assertEquals("foo", StringUtils.join(MIXED_TYPE_OBJECT_LIST, '/', 0, 1));
    }

    @Test
    public void testJoin_List_CharDelimiter_3() {
        assertEquals("foo/2", StringUtils.join(MIXED_TYPE_OBJECT_LIST, '/', 0, 2));
    }

    @Test
    public void testJoin_List_CharDelimiter_4() {
        assertEquals("2", StringUtils.join(MIXED_TYPE_OBJECT_LIST, '/', 1, 2));
    }

    @Test
    public void testJoin_List_CharDelimiter_5() {
        assertEquals("", StringUtils.join(MIXED_TYPE_OBJECT_LIST, '/', 2, 1));
    }

    @Test
    public void testJoin_List_CharDelimiter_6() {
        assertNull(null, StringUtils.join((List<?>) null, '/', 0, 1));
    }

    @Test
    public void testJoin_List_EmptyDelimiter_1() {
        assertNull(StringUtils.join((List<String>) null, null));
    }

    @Test
    public void testJoin_List_EmptyDelimiter_2() {
        assertEquals(TEXT_LIST_NOSEP, StringUtils.join(STRING_LIST, null));
    }

    @Test
    public void testJoin_List_EmptyDelimiter_3() {
        assertEquals(TEXT_LIST_NOSEP, StringUtils.join(STRING_LIST, ""));
    }

    @Test
    public void testJoin_List_EmptyDelimiter_4() {
        assertEquals("", StringUtils.join(NULL_STRING_LIST, null));
    }

    @Test
    public void testJoin_List_EmptyDelimiter_5() {
        assertEquals("", StringUtils.join(EMPTY_STRING_LIST, null));
    }

    @Test
    public void testJoin_List_EmptyDelimiter_6() {
        assertEquals("", StringUtils.join(EMPTY_STRING_LIST, ""));
    }

    @Test
    public void testJoin_List_EmptyDelimiter_7() {
        assertEquals("", StringUtils.join(MIXED_STRING_LIST, "", 0, MIXED_STRING_LIST.size() - 1));
    }

    @Test
    public void testJoin_Objectarray_1() {
        assertNull(StringUtils.join((Object[]) null));
    }

    @Test
    public void testJoin_Objectarray_2() {
        assertEquals("", StringUtils.join());
    }

    @Test
    public void testJoin_Objectarray_3() {
        assertEquals("", StringUtils.join((Object) null));
    }

    @Test
    public void testJoin_Objectarray_4() {
        assertEquals("", StringUtils.join(EMPTY_ARRAY_LIST));
    }

    @Test
    public void testJoin_Objectarray_5() {
        assertEquals("", StringUtils.join(NULL_ARRAY_LIST));
    }

    @Test
    public void testJoin_Objectarray_6() {
        assertEquals("null", StringUtils.join(NULL_TO_STRING_LIST));
    }

    @Test
    public void testJoin_Objectarray_7() {
        assertEquals("abc", StringUtils.join("a", "b", "c"));
    }

    @Test
    public void testJoin_Objectarray_8() {
        assertEquals("a", StringUtils.join(null, "a", ""));
    }

    @Test
    public void testJoin_Objectarray_9() {
        assertEquals("foo", StringUtils.join(MIXED_ARRAY_LIST));
    }

    @Test
    public void testJoin_Objectarray_10() {
        assertEquals("foo2", StringUtils.join(MIXED_TYPE_LIST));
    }

    @Test
    public void testJoin_Objects_1() {
        assertEquals("abc", StringUtils.join("a", "b", "c"));
    }

    @Test
    public void testJoin_Objects_2() {
        assertEquals("a", StringUtils.join(null, "", "a"));
    }

    @Test
    public void testJoin_Objects_3() {
        assertNull(StringUtils.join((Object[]) null));
    }

    @Test
    public void testLang623_1() {
        assertEquals("t", StringUtils.replaceChars("\u00DE", '\u00DE', 't'));
    }

    @Test
    public void testLang623_2() {
        assertEquals("t", StringUtils.replaceChars("\u00FE", '\u00FE', 't'));
    }

    @Test
    public void testLANG666_1() {
        assertEquals("12", StringUtils.stripEnd("120.00", ".0"));
    }

    @Test
    public void testLANG666_2() {
        assertEquals("121", StringUtils.stripEnd("121.00", ".0"));
    }

    @Test
    public void testLeftPad_StringInt_1() {
        assertNull(StringUtils.leftPad(null, 5));
    }

    @Test
    public void testLeftPad_StringInt_2() {
        assertEquals("     ", StringUtils.leftPad("", 5));
    }

    @Test
    public void testLeftPad_StringInt_3() {
        assertEquals("  abc", StringUtils.leftPad("abc", 5));
    }

    @Test
    public void testLeftPad_StringInt_4() {
        assertEquals("abc", StringUtils.leftPad("abc", 2));
    }

    @Test
    public void testLeftPad_StringIntChar_1() {
        assertNull(StringUtils.leftPad(null, 5, ' '));
    }

    @Test
    public void testLeftPad_StringIntChar_2() {
        assertEquals("     ", StringUtils.leftPad("", 5, ' '));
    }

    @Test
    public void testLeftPad_StringIntChar_3() {
        assertEquals("  abc", StringUtils.leftPad("abc", 5, ' '));
    }

    @Test
    public void testLeftPad_StringIntChar_4() {
        assertEquals("xxabc", StringUtils.leftPad("abc", 5, 'x'));
    }

    @Test
    public void testLeftPad_StringIntChar_5() {
        assertEquals("\uffff\uffffabc", StringUtils.leftPad("abc", 5, '\uffff'));
    }

    @Test
    public void testLeftPad_StringIntChar_6() {
        assertEquals("abc", StringUtils.leftPad("abc", 2, ' '));
    }

    @Test
    public void testLeftPad_StringIntChar_7_testMerged_7() {
        final String str = StringUtils.leftPad("aaa", 10000, 'a');
        assertEquals(10000, str.length());
        assertTrue(StringUtils.containsOnly(str, 'a'));
    }

    @Test
    public void testLeftPad_StringIntString_1() {
        assertNull(StringUtils.leftPad(null, 5, "-+"));
    }

    @Test
    public void testLeftPad_StringIntString_2() {
        assertNull(StringUtils.leftPad(null, 5, null));
    }

    @Test
    public void testLeftPad_StringIntString_3() {
        assertEquals("     ", StringUtils.leftPad("", 5, " "));
    }

    @Test
    public void testLeftPad_StringIntString_4() {
        assertEquals("-+-+abc", StringUtils.leftPad("abc", 7, "-+"));
    }

    @Test
    public void testLeftPad_StringIntString_5() {
        assertEquals("-+~abc", StringUtils.leftPad("abc", 6, "-+~"));
    }

    @Test
    public void testLeftPad_StringIntString_6() {
        assertEquals("-+abc", StringUtils.leftPad("abc", 5, "-+~"));
    }

    @Test
    public void testLeftPad_StringIntString_7() {
        assertEquals("abc", StringUtils.leftPad("abc", 2, " "));
    }

    @Test
    public void testLeftPad_StringIntString_8() {
        assertEquals("abc", StringUtils.leftPad("abc", -1, " "));
    }

    @Test
    public void testLeftPad_StringIntString_9() {
        assertEquals("  abc", StringUtils.leftPad("abc", 5, null));
    }

    @Test
    public void testLeftPad_StringIntString_10() {
        assertEquals("  abc", StringUtils.leftPad("abc", 5, ""));
    }

    @Test
    public void testLength_CharBuffer_1() {
        assertEquals(0, StringUtils.length(CharBuffer.wrap("")));
    }

    @Test
    public void testLength_CharBuffer_2() {
        assertEquals(1, StringUtils.length(CharBuffer.wrap("A")));
    }

    @Test
    public void testLength_CharBuffer_3() {
        assertEquals(1, StringUtils.length(CharBuffer.wrap(" ")));
    }

    @Test
    public void testLength_CharBuffer_4() {
        assertEquals(8, StringUtils.length(CharBuffer.wrap("ABCDEFGH")));
    }

    @Test
    public void testLengthString_1() {
        assertEquals(0, StringUtils.length(null));
    }

    @Test
    public void testLengthString_2() {
        assertEquals(0, StringUtils.length(""));
    }

    @Test
    public void testLengthString_3() {
        assertEquals(0, StringUtils.length(StringUtils.EMPTY));
    }

    @Test
    public void testLengthString_4() {
        assertEquals(1, StringUtils.length("A"));
    }

    @Test
    public void testLengthString_5() {
        assertEquals(1, StringUtils.length(" "));
    }

    @Test
    public void testLengthString_6() {
        assertEquals(8, StringUtils.length("ABCDEFGH"));
    }

    @Test
    public void testLengthStringBuffer_1() {
        assertEquals(0, StringUtils.length(new StringBuffer("")));
    }

    @Test
    public void testLengthStringBuffer_2() {
        assertEquals(0, StringUtils.length(new StringBuffer(StringUtils.EMPTY)));
    }

    @Test
    public void testLengthStringBuffer_3() {
        assertEquals(1, StringUtils.length(new StringBuffer("A")));
    }

    @Test
    public void testLengthStringBuffer_4() {
        assertEquals(1, StringUtils.length(new StringBuffer(" ")));
    }

    @Test
    public void testLengthStringBuffer_5() {
        assertEquals(8, StringUtils.length(new StringBuffer("ABCDEFGH")));
    }

    @Test
    public void testLengthStringBuilder_1() {
        assertEquals(0, StringUtils.length(new StringBuilder("")));
    }

    @Test
    public void testLengthStringBuilder_2() {
        assertEquals(0, StringUtils.length(new StringBuilder(StringUtils.EMPTY)));
    }

    @Test
    public void testLengthStringBuilder_3() {
        assertEquals(1, StringUtils.length(new StringBuilder("A")));
    }

    @Test
    public void testLengthStringBuilder_4() {
        assertEquals(1, StringUtils.length(new StringBuilder(" ")));
    }

    @Test
    public void testLengthStringBuilder_5() {
        assertEquals(8, StringUtils.length(new StringBuilder("ABCDEFGH")));
    }

    @Test
    public void testLowerCase_1() {
        assertNull(StringUtils.lowerCase(null));
    }

    @Test
    public void testLowerCase_2() {
        assertNull(StringUtils.lowerCase(null, Locale.ENGLISH));
    }

    @Test
    public void testLowerCase_3() {
        assertEquals("foo test thing", StringUtils.lowerCase("fOo test THING"), "lowerCase(String) failed");
    }

    @Test
    public void testLowerCase_4() {
        assertEquals("", StringUtils.lowerCase(""), "lowerCase(empty-string) failed");
    }

    @Test
    public void testLowerCase_5() {
        assertEquals("foo test thing", StringUtils.lowerCase("fOo test THING", Locale.ENGLISH), "lowerCase(String, Locale) failed");
    }

    @Test
    public void testLowerCase_6() {
        assertEquals("", StringUtils.lowerCase("", Locale.ENGLISH), "lowerCase(empty-string, Locale) failed");
    }

    @Test
    public void testNormalizeSpace_1() {
        assertFalse(Character.isWhitespace('\u00A0'));
    }

    @Test
    public void testNormalizeSpace_2() {
        assertNull(StringUtils.normalizeSpace(null));
    }

    @Test
    public void testNormalizeSpace_3() {
        assertEquals("", StringUtils.normalizeSpace(""));
    }

    @Test
    public void testNormalizeSpace_4() {
        assertEquals("", StringUtils.normalizeSpace(" "));
    }

    @Test
    public void testNormalizeSpace_5() {
        assertEquals("", StringUtils.normalizeSpace("\t"));
    }

    @Test
    public void testNormalizeSpace_6() {
        assertEquals("", StringUtils.normalizeSpace("\n"));
    }

    @Test
    public void testNormalizeSpace_7() {
        assertEquals("", StringUtils.normalizeSpace("\u0009"));
    }

    @Test
    public void testNormalizeSpace_8() {
        assertEquals("", StringUtils.normalizeSpace("\u000B"));
    }

    @Test
    public void testNormalizeSpace_9() {
        assertEquals("", StringUtils.normalizeSpace("\u000C"));
    }

    @Test
    public void testNormalizeSpace_10() {
        assertEquals("", StringUtils.normalizeSpace("\u001C"));
    }

    @Test
    public void testNormalizeSpace_11() {
        assertEquals("", StringUtils.normalizeSpace("\u001D"));
    }

    @Test
    public void testNormalizeSpace_12() {
        assertEquals("", StringUtils.normalizeSpace("\u001E"));
    }

    @Test
    public void testNormalizeSpace_13() {
        assertEquals("", StringUtils.normalizeSpace("\u001F"));
    }

    @Test
    public void testNormalizeSpace_14() {
        assertEquals("", StringUtils.normalizeSpace("\f"));
    }

    @Test
    public void testNormalizeSpace_15() {
        assertEquals("", StringUtils.normalizeSpace("\r"));
    }

    @Test
    public void testNormalizeSpace_16() {
        assertEquals("a", StringUtils.normalizeSpace("  a  "));
    }

    @Test
    public void testNormalizeSpace_17() {
        assertEquals("a b c", StringUtils.normalizeSpace("  a  b   c  "));
    }

    @Test
    public void testNormalizeSpace_18() {
        assertEquals("a b c", StringUtils.normalizeSpace("a\t\f\r  b\u000B   c\n"));
    }

    @Test
    public void testNormalizeSpace_19() {
        assertEquals("a   b c", StringUtils.normalizeSpace("a\t\f\r  " + HARD_SPACE + HARD_SPACE + "b\u000B   c\n"));
    }

    @Test
    public void testNormalizeSpace_20() {
        assertEquals("b", StringUtils.normalizeSpace("\u0000b"));
    }

    @Test
    public void testNormalizeSpace_21() {
        assertEquals("b", StringUtils.normalizeSpace("b\u0000"));
    }

    @Test
    public void testOverlay_StringStringIntInt_1() {
        assertNull(StringUtils.overlay(null, null, 2, 4));
    }

    @Test
    public void testOverlay_StringStringIntInt_2() {
        assertNull(StringUtils.overlay(null, null, -2, -4));
    }

    @Test
    public void testOverlay_StringStringIntInt_3() {
        assertEquals("", StringUtils.overlay("", null, 0, 0));
    }

    @Test
    public void testOverlay_StringStringIntInt_4() {
        assertEquals("", StringUtils.overlay("", "", 0, 0));
    }

    @Test
    public void testOverlay_StringStringIntInt_5() {
        assertEquals("zzzz", StringUtils.overlay("", "zzzz", 0, 0));
    }

    @Test
    public void testOverlay_StringStringIntInt_6() {
        assertEquals("zzzz", StringUtils.overlay("", "zzzz", 2, 4));
    }

    @Test
    public void testOverlay_StringStringIntInt_7() {
        assertEquals("zzzz", StringUtils.overlay("", "zzzz", -2, -4));
    }

    @Test
    public void testOverlay_StringStringIntInt_8() {
        assertEquals("abef", StringUtils.overlay("abcdef", null, 2, 4));
    }

    @Test
    public void testOverlay_StringStringIntInt_9() {
        assertEquals("abef", StringUtils.overlay("abcdef", null, 4, 2));
    }

    @Test
    public void testOverlay_StringStringIntInt_10() {
        assertEquals("abef", StringUtils.overlay("abcdef", "", 2, 4));
    }

    @Test
    public void testOverlay_StringStringIntInt_11() {
        assertEquals("abef", StringUtils.overlay("abcdef", "", 4, 2));
    }

    @Test
    public void testOverlay_StringStringIntInt_12() {
        assertEquals("abzzzzef", StringUtils.overlay("abcdef", "zzzz", 2, 4));
    }

    @Test
    public void testOverlay_StringStringIntInt_13() {
        assertEquals("abzzzzef", StringUtils.overlay("abcdef", "zzzz", 4, 2));
    }

    @Test
    public void testOverlay_StringStringIntInt_14() {
        assertEquals("zzzzef", StringUtils.overlay("abcdef", "zzzz", -1, 4));
    }

    @Test
    public void testOverlay_StringStringIntInt_15() {
        assertEquals("zzzzef", StringUtils.overlay("abcdef", "zzzz", 4, -1));
    }

    @Test
    public void testOverlay_StringStringIntInt_16() {
        assertEquals("zzzzabcdef", StringUtils.overlay("abcdef", "zzzz", -2, -1));
    }

    @Test
    public void testOverlay_StringStringIntInt_17() {
        assertEquals("zzzzabcdef", StringUtils.overlay("abcdef", "zzzz", -1, -2));
    }

    @Test
    public void testOverlay_StringStringIntInt_18() {
        assertEquals("abcdzzzz", StringUtils.overlay("abcdef", "zzzz", 4, 10));
    }

    @Test
    public void testOverlay_StringStringIntInt_19() {
        assertEquals("abcdzzzz", StringUtils.overlay("abcdef", "zzzz", 10, 4));
    }

    @Test
    public void testOverlay_StringStringIntInt_20() {
        assertEquals("abcdefzzzz", StringUtils.overlay("abcdef", "zzzz", 8, 10));
    }

    @Test
    public void testOverlay_StringStringIntInt_21() {
        assertEquals("abcdefzzzz", StringUtils.overlay("abcdef", "zzzz", 10, 8));
    }

    @Test
    public void testPrependIfMissing_1() {
        assertNull(StringUtils.prependIfMissing(null, null), "prependIfMissing(null,null)");
    }

    @Test
    public void testPrependIfMissing_2() {
        assertEquals("abc", StringUtils.prependIfMissing("abc", null), "prependIfMissing(abc,null)");
    }

    @Test
    public void testPrependIfMissing_3() {
        assertEquals("xyz", StringUtils.prependIfMissing("", "xyz"), "prependIfMissing(\"\",xyz)");
    }

    @Test
    public void testPrependIfMissing_4() {
        assertEquals("xyzabc", StringUtils.prependIfMissing("abc", "xyz"), "prependIfMissing(abc,xyz)");
    }

    @Test
    public void testPrependIfMissing_5() {
        assertEquals("xyzabc", StringUtils.prependIfMissing("xyzabc", "xyz"), "prependIfMissing(xyzabc,xyz)");
    }

    @Test
    public void testPrependIfMissing_6() {
        assertEquals("xyzXYZabc", StringUtils.prependIfMissing("XYZabc", "xyz"), "prependIfMissing(XYZabc,xyz)");
    }

    @Test
    public void testPrependIfMissing_7() {
        assertNull(StringUtils.prependIfMissing(null, null, (CharSequence[]) null), "prependIfMissing(null,null null)");
    }

    @Test
    public void testPrependIfMissing_8() {
        assertEquals("abc", StringUtils.prependIfMissing("abc", null, (CharSequence[]) null), "prependIfMissing(abc,null,null)");
    }

    @Test
    public void testPrependIfMissing_9() {
        assertEquals("xyz", StringUtils.prependIfMissing("", "xyz", (CharSequence[]) null), "prependIfMissing(\"\",xyz,null)");
    }

    @Test
    public void testPrependIfMissing_10() {
        assertEquals("xyzabc", StringUtils.prependIfMissing("abc", "xyz", (CharSequence) null), "prependIfMissing(abc,xyz,{null})");
    }

    @Test
    public void testPrependIfMissing_11() {
        assertEquals("abc", StringUtils.prependIfMissing("abc", "xyz", ""), "prependIfMissing(abc,xyz,\"\")");
    }

    @Test
    public void testPrependIfMissing_12() {
        assertEquals("xyzabc", StringUtils.prependIfMissing("abc", "xyz", "mno"), "prependIfMissing(abc,xyz,mno)");
    }

    @Test
    public void testPrependIfMissing_13() {
        assertEquals("xyzabc", StringUtils.prependIfMissing("xyzabc", "xyz", "mno"), "prependIfMissing(xyzabc,xyz,mno)");
    }

    @Test
    public void testPrependIfMissing_14() {
        assertEquals("mnoabc", StringUtils.prependIfMissing("mnoabc", "xyz", "mno"), "prependIfMissing(mnoabc,xyz,mno)");
    }

    @Test
    public void testPrependIfMissing_15() {
        assertEquals("xyzXYZabc", StringUtils.prependIfMissing("XYZabc", "xyz", "mno"), "prependIfMissing(XYZabc,xyz,mno)");
    }

    @Test
    public void testPrependIfMissing_16() {
        assertEquals("xyzMNOabc", StringUtils.prependIfMissing("MNOabc", "xyz", "mno"), "prependIfMissing(MNOabc,xyz,mno)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_1() {
        assertNull(StringUtils.prependIfMissingIgnoreCase(null, null), "prependIfMissingIgnoreCase(null,null)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_2() {
        assertEquals("abc", StringUtils.prependIfMissingIgnoreCase("abc", null), "prependIfMissingIgnoreCase(abc,null)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_3() {
        assertEquals("xyz", StringUtils.prependIfMissingIgnoreCase("", "xyz"), "prependIfMissingIgnoreCase(\"\",xyz)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_4() {
        assertEquals("xyzabc", StringUtils.prependIfMissingIgnoreCase("abc", "xyz"), "prependIfMissingIgnoreCase(abc,xyz)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_5() {
        assertEquals("xyzabc", StringUtils.prependIfMissingIgnoreCase("xyzabc", "xyz"), "prependIfMissingIgnoreCase(xyzabc,xyz)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_6() {
        assertEquals("XYZabc", StringUtils.prependIfMissingIgnoreCase("XYZabc", "xyz"), "prependIfMissingIgnoreCase(XYZabc,xyz)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_7() {
        assertNull(StringUtils.prependIfMissingIgnoreCase(null, null, (CharSequence[]) null), "prependIfMissingIgnoreCase(null,null null)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_8() {
        assertEquals("abc", StringUtils.prependIfMissingIgnoreCase("abc", null, (CharSequence[]) null), "prependIfMissingIgnoreCase(abc,null,null)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_9() {
        assertEquals("xyz", StringUtils.prependIfMissingIgnoreCase("", "xyz", (CharSequence[]) null), "prependIfMissingIgnoreCase(\"\",xyz,null)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_10() {
        assertEquals("xyzabc", StringUtils.prependIfMissingIgnoreCase("abc", "xyz", (CharSequence) null), "prependIfMissingIgnoreCase(abc,xyz,{null})");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_11() {
        assertEquals("abc", StringUtils.prependIfMissingIgnoreCase("abc", "xyz", ""), "prependIfMissingIgnoreCase(abc,xyz,\"\")");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_12() {
        assertEquals("xyzabc", StringUtils.prependIfMissingIgnoreCase("abc", "xyz", "mno"), "prependIfMissingIgnoreCase(abc,xyz,mno)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_13() {
        assertEquals("xyzabc", StringUtils.prependIfMissingIgnoreCase("xyzabc", "xyz", "mno"), "prependIfMissingIgnoreCase(xyzabc,xyz,mno)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_14() {
        assertEquals("mnoabc", StringUtils.prependIfMissingIgnoreCase("mnoabc", "xyz", "mno"), "prependIfMissingIgnoreCase(mnoabc,xyz,mno)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_15() {
        assertEquals("XYZabc", StringUtils.prependIfMissingIgnoreCase("XYZabc", "xyz", "mno"), "prependIfMissingIgnoreCase(XYZabc,xyz,mno)");
    }

    @Test
    public void testPrependIfMissingIgnoreCase_16() {
        assertEquals("MNOabc", StringUtils.prependIfMissingIgnoreCase("MNOabc", "xyz", "mno"), "prependIfMissingIgnoreCase(MNOabc,xyz,mno)");
    }

    @Test
    public void testReCapitalize_1() {
        assertEquals(SENTENCE_UNCAP, StringUtils.uncapitalize(StringUtils.capitalize(SENTENCE_UNCAP)), "uncapitalize(capitalize(String)) failed");
    }

    @Test
    public void testReCapitalize_2() {
        assertEquals(SENTENCE_CAP, StringUtils.capitalize(StringUtils.uncapitalize(SENTENCE_CAP)), "capitalize(uncapitalize(String)) failed");
    }

    @Test
    public void testReCapitalize_3() {
        assertEquals(FOO_UNCAP, StringUtils.uncapitalize(StringUtils.capitalize(FOO_UNCAP)), "uncapitalize(capitalize(String)) failed");
    }

    @Test
    public void testReCapitalize_4() {
        assertEquals(FOO_CAP, StringUtils.capitalize(StringUtils.uncapitalize(FOO_CAP)), "capitalize(uncapitalize(String)) failed");
    }

    @Test
    public void testRemove_char_1() {
        assertNull(StringUtils.remove(null, null));
    }

    @Test
    public void testRemove_char_2() {
        assertNull(StringUtils.remove(null, 'a'));
    }

    @Test
    public void testRemove_char_3() {
        assertEquals("", StringUtils.remove("", null));
    }

    @Test
    public void testRemove_char_4() {
        assertEquals("", StringUtils.remove("", 'a'));
    }

    @Test
    public void testRemove_char_5() {
        assertEquals("qeed", StringUtils.remove("queued", 'u'));
    }

    @Test
    public void testRemove_char_6() {
        assertEquals("queued", StringUtils.remove("queued", 'z'));
    }

    @Test
    public void testRemove_String_1() {
        assertNull(StringUtils.remove(null, null));
    }

    @Test
    public void testRemove_String_2() {
        assertNull(StringUtils.remove(null, ""));
    }

    @Test
    public void testRemove_String_3() {
        assertNull(StringUtils.remove(null, "a"));
    }

    @Test
    public void testRemove_String_4() {
        assertEquals("", StringUtils.remove("", null));
    }

    @Test
    public void testRemove_String_5() {
        assertEquals("", StringUtils.remove("", ""));
    }

    @Test
    public void testRemove_String_6() {
        assertEquals("", StringUtils.remove("", "a"));
    }

    @Test
    public void testRemove_String_7() {
        assertNull(StringUtils.remove(null, null));
    }

    @Test
    public void testRemove_String_8() {
        assertEquals("", StringUtils.remove("", null));
    }

    @Test
    public void testRemove_String_9() {
        assertEquals("a", StringUtils.remove("a", null));
    }

    @Test
    public void testRemove_String_10() {
        assertNull(StringUtils.remove(null, ""));
    }

    @Test
    public void testRemove_String_11() {
        assertEquals("", StringUtils.remove("", ""));
    }

    @Test
    public void testRemove_String_12() {
        assertEquals("a", StringUtils.remove("a", ""));
    }

    @Test
    public void testRemove_String_13() {
        assertEquals("qd", StringUtils.remove("queued", "ue"));
    }

    @Test
    public void testRemove_String_14() {
        assertEquals("queued", StringUtils.remove("queued", "zz"));
    }

    @Test
    public void testRemoveEnd_1() {
        assertNull(StringUtils.removeEnd(null, null));
    }

    @Test
    public void testRemoveEnd_2() {
        assertNull(StringUtils.removeEnd(null, ""));
    }

    @Test
    public void testRemoveEnd_3() {
        assertNull(StringUtils.removeEnd(null, "a"));
    }

    @Test
    public void testRemoveEnd_4() {
        assertEquals(StringUtils.removeEnd("", null), "");
    }

    @Test
    public void testRemoveEnd_5() {
        assertEquals(StringUtils.removeEnd("", ""), "");
    }

    @Test
    public void testRemoveEnd_6() {
        assertEquals(StringUtils.removeEnd("", "a"), "");
    }

    @Test
    public void testRemoveEnd_7() {
        assertEquals(StringUtils.removeEnd("www.domain.com.", ".com"), "www.domain.com.");
    }

    @Test
    public void testRemoveEnd_8() {
        assertEquals(StringUtils.removeEnd("www.domain.com", ".com"), "www.domain");
    }

    @Test
    public void testRemoveEnd_9() {
        assertEquals(StringUtils.removeEnd("www.domain", ".com"), "www.domain");
    }

    @Test
    public void testRemoveEnd_10() {
        assertEquals(StringUtils.removeEnd("domain.com", ""), "domain.com");
    }

    @Test
    public void testRemoveEnd_11() {
        assertEquals(StringUtils.removeEnd("domain.com", null), "domain.com");
    }

    @Test
    public void testRemoveEndIgnoreCase_1() {
        assertNull(StringUtils.removeEndIgnoreCase(null, null), "removeEndIgnoreCase(null, null)");
    }

    @Test
    public void testRemoveEndIgnoreCase_2() {
        assertNull(StringUtils.removeEndIgnoreCase(null, ""), "removeEndIgnoreCase(null, \"\")");
    }

    @Test
    public void testRemoveEndIgnoreCase_3() {
        assertNull(StringUtils.removeEndIgnoreCase(null, "a"), "removeEndIgnoreCase(null, \"a\")");
    }

    @Test
    public void testRemoveEndIgnoreCase_4() {
        assertEquals(StringUtils.removeEndIgnoreCase("", null), "", "removeEndIgnoreCase(\"\", null)");
    }

    @Test
    public void testRemoveEndIgnoreCase_5() {
        assertEquals(StringUtils.removeEndIgnoreCase("", ""), "", "removeEndIgnoreCase(\"\", \"\")");
    }

    @Test
    public void testRemoveEndIgnoreCase_6() {
        assertEquals(StringUtils.removeEndIgnoreCase("", "a"), "", "removeEndIgnoreCase(\"\", \"a\")");
    }

    @Test
    public void testRemoveEndIgnoreCase_7() {
        assertEquals(StringUtils.removeEndIgnoreCase("www.domain.com.", ".com"), "www.domain.com.", "removeEndIgnoreCase(\"www.domain.com.\", \".com\")");
    }

    @Test
    public void testRemoveEndIgnoreCase_8() {
        assertEquals(StringUtils.removeEndIgnoreCase("www.domain.com", ".com"), "www.domain", "removeEndIgnoreCase(\"www.domain.com\", \".com\")");
    }

    @Test
    public void testRemoveEndIgnoreCase_9() {
        assertEquals(StringUtils.removeEndIgnoreCase("www.domain", ".com"), "www.domain", "removeEndIgnoreCase(\"www.domain\", \".com\")");
    }

    @Test
    public void testRemoveEndIgnoreCase_10() {
        assertEquals(StringUtils.removeEndIgnoreCase("domain.com", ""), "domain.com", "removeEndIgnoreCase(\"domain.com\", \"\")");
    }

    @Test
    public void testRemoveEndIgnoreCase_11() {
        assertEquals(StringUtils.removeEndIgnoreCase("domain.com", null), "domain.com", "removeEndIgnoreCase(\"domain.com\", null)");
    }

    @Test
    public void testRemoveEndIgnoreCase_12() {
        assertEquals(StringUtils.removeEndIgnoreCase("www.domain.com", ".COM"), "www.domain", "removeEndIgnoreCase(\"www.domain.com\", \".COM\")");
    }

    @Test
    public void testRemoveEndIgnoreCase_13() {
        assertEquals(StringUtils.removeEndIgnoreCase("www.domain.COM", ".com"), "www.domain", "removeEndIgnoreCase(\"www.domain.COM\", \".com\")");
    }

    @Test
    public void testRemoveIgnoreCase_String_1() {
        assertNull(StringUtils.removeIgnoreCase(null, null));
    }

    @Test
    public void testRemoveIgnoreCase_String_2() {
        assertNull(StringUtils.removeIgnoreCase(null, ""));
    }

    @Test
    public void testRemoveIgnoreCase_String_3() {
        assertNull(StringUtils.removeIgnoreCase(null, "a"));
    }

    @Test
    public void testRemoveIgnoreCase_String_4() {
        assertEquals("", StringUtils.removeIgnoreCase("", null));
    }

    @Test
    public void testRemoveIgnoreCase_String_5() {
        assertEquals("", StringUtils.removeIgnoreCase("", ""));
    }

    @Test
    public void testRemoveIgnoreCase_String_6() {
        assertEquals("", StringUtils.removeIgnoreCase("", "a"));
    }

    @Test
    public void testRemoveIgnoreCase_String_7() {
        assertNull(StringUtils.removeIgnoreCase(null, null));
    }

    @Test
    public void testRemoveIgnoreCase_String_8() {
        assertEquals("", StringUtils.removeIgnoreCase("", null));
    }

    @Test
    public void testRemoveIgnoreCase_String_9() {
        assertEquals("a", StringUtils.removeIgnoreCase("a", null));
    }

    @Test
    public void testRemoveIgnoreCase_String_10() {
        assertNull(StringUtils.removeIgnoreCase(null, ""));
    }

    @Test
    public void testRemoveIgnoreCase_String_11() {
        assertEquals("", StringUtils.removeIgnoreCase("", ""));
    }

    @Test
    public void testRemoveIgnoreCase_String_12() {
        assertEquals("a", StringUtils.removeIgnoreCase("a", ""));
    }

    @Test
    public void testRemoveIgnoreCase_String_13() {
        assertEquals("qd", StringUtils.removeIgnoreCase("queued", "ue"));
    }

    @Test
    public void testRemoveIgnoreCase_String_14() {
        assertEquals("queued", StringUtils.removeIgnoreCase("queued", "zz"));
    }

    @Test
    public void testRemoveIgnoreCase_String_15() {
        assertEquals("qd", StringUtils.removeIgnoreCase("quEUed", "UE"));
    }

    @Test
    public void testRemoveIgnoreCase_String_16() {
        assertEquals("queued", StringUtils.removeIgnoreCase("queued", "zZ"));
    }

    @Test
    public void testRemoveIgnoreCase_String_17() {
        assertEquals("\u0130", StringUtils.removeIgnoreCase("\u0130x", "x"));
    }

    @Test
    public void testRemovePattern_StringString_1() {
        assertNull(StringUtils.removePattern(null, ""));
    }

    @Test
    public void testRemovePattern_StringString_2() {
        assertEquals("any", StringUtils.removePattern("any", null));
    }

    @Test
    public void testRemovePattern_StringString_3() {
        assertEquals("", StringUtils.removePattern("", ""));
    }

    @Test
    public void testRemovePattern_StringString_4() {
        assertEquals("", StringUtils.removePattern("", ".*"));
    }

    @Test
    public void testRemovePattern_StringString_5() {
        assertEquals("", StringUtils.removePattern("", ".+"));
    }

    @Test
    public void testRemovePattern_StringString_6() {
        assertEquals("AB", StringUtils.removePattern("A<__>\n<__>B", "<.*>"));
    }

    @Test
    public void testRemovePattern_StringString_7() {
        assertEquals("AB", StringUtils.removePattern("A<__>\\n<__>B", "<.*>"));
    }

    @Test
    public void testRemovePattern_StringString_8() {
        assertEquals("", StringUtils.removePattern("<A>x\\ny</A>", "<A>.*</A>"));
    }

    @Test
    public void testRemovePattern_StringString_9() {
        assertEquals("", StringUtils.removePattern("<A>\nxy\n</A>", "<A>.*</A>"));
    }

    @Test
    public void testRemovePattern_StringString_10() {
        assertEquals("ABC123", StringUtils.removePattern("ABCabc123", "[a-z]"));
    }

    @Test
    public void testRemoveStartChar_1() {
        assertNull(StringUtils.removeStart(null, '\0'));
    }

    @Test
    public void testRemoveStartChar_2() {
        assertNull(StringUtils.removeStart(null, 'a'));
    }

    @Test
    public void testRemoveStartChar_3() {
        assertEquals(StringUtils.removeStart("", '\0'), "");
    }

    @Test
    public void testRemoveStartChar_4() {
        assertEquals(StringUtils.removeStart("", 'a'), "");
    }

    @Test
    public void testRemoveStartChar_5() {
        assertEquals(StringUtils.removeStart("/path", '/'), "path");
    }

    @Test
    public void testRemoveStartChar_6() {
        assertEquals(StringUtils.removeStart("path", '/'), "path");
    }

    @Test
    public void testRemoveStartChar_7() {
        assertEquals(StringUtils.removeStart("path", '\0'), "path");
    }

    @Test
    public void testRemoveStartIgnoreCase_1() {
        assertNull(StringUtils.removeStartIgnoreCase(null, null), "removeStartIgnoreCase(null, null)");
    }

    @Test
    public void testRemoveStartIgnoreCase_2() {
        assertNull(StringUtils.removeStartIgnoreCase(null, ""), "removeStartIgnoreCase(null, \"\")");
    }

    @Test
    public void testRemoveStartIgnoreCase_3() {
        assertNull(StringUtils.removeStartIgnoreCase(null, "a"), "removeStartIgnoreCase(null, \"a\")");
    }

    @Test
    public void testRemoveStartIgnoreCase_4() {
        assertEquals(StringUtils.removeStartIgnoreCase("", null), "", "removeStartIgnoreCase(\"\", null)");
    }

    @Test
    public void testRemoveStartIgnoreCase_5() {
        assertEquals(StringUtils.removeStartIgnoreCase("", ""), "", "removeStartIgnoreCase(\"\", \"\")");
    }

    @Test
    public void testRemoveStartIgnoreCase_6() {
        assertEquals(StringUtils.removeStartIgnoreCase("", "a"), "", "removeStartIgnoreCase(\"\", \"a\")");
    }

    @Test
    public void testRemoveStartIgnoreCase_7() {
        assertEquals(StringUtils.removeStartIgnoreCase("www.domain.com", "www."), "domain.com", "removeStartIgnoreCase(\"www.domain.com\", \"www.\")");
    }

    @Test
    public void testRemoveStartIgnoreCase_8() {
        assertEquals(StringUtils.removeStartIgnoreCase("domain.com", "www."), "domain.com", "removeStartIgnoreCase(\"domain.com\", \"www.\")");
    }

    @Test
    public void testRemoveStartIgnoreCase_9() {
        assertEquals(StringUtils.removeStartIgnoreCase("domain.com", ""), "domain.com", "removeStartIgnoreCase(\"domain.com\", \"\")");
    }

    @Test
    public void testRemoveStartIgnoreCase_10() {
        assertEquals(StringUtils.removeStartIgnoreCase("domain.com", null), "domain.com", "removeStartIgnoreCase(\"domain.com\", null)");
    }

    @Test
    public void testRemoveStartIgnoreCase_11() {
        assertEquals(StringUtils.removeStartIgnoreCase("www.domain.com", "WWW."), "domain.com", "removeStartIgnoreCase(\"www.domain.com\", \"WWW.\")");
    }

    @Test
    public void testRemoveStartString_1() {
        assertNull(StringUtils.removeStart(null, null));
    }

    @Test
    public void testRemoveStartString_2() {
        assertNull(StringUtils.removeStart(null, ""));
    }

    @Test
    public void testRemoveStartString_3() {
        assertNull(StringUtils.removeStart(null, "a"));
    }

    @Test
    public void testRemoveStartString_4() {
        assertEquals(StringUtils.removeStart("", null), "");
    }

    @Test
    public void testRemoveStartString_5() {
        assertEquals(StringUtils.removeStart("", ""), "");
    }

    @Test
    public void testRemoveStartString_6() {
        assertEquals(StringUtils.removeStart("", "a"), "");
    }

    @Test
    public void testRemoveStartString_7() {
        assertEquals(StringUtils.removeStart("www.domain.com", "www."), "domain.com");
    }

    @Test
    public void testRemoveStartString_8() {
        assertEquals(StringUtils.removeStart("domain.com", "www."), "domain.com");
    }

    @Test
    public void testRemoveStartString_9() {
        assertEquals(StringUtils.removeStart("domain.com", ""), "domain.com");
    }

    @Test
    public void testRemoveStartString_10() {
        assertEquals(StringUtils.removeStart("domain.com", null), "domain.com");
    }

    @Test
    public void testRepeat_CharInt_1() {
        assertEquals("zzz", StringUtils.repeat('z', 3));
    }

    @Test
    public void testRepeat_CharInt_2() {
        assertEquals("", StringUtils.repeat('z', 0));
    }

    @Test
    public void testRepeat_CharInt_3() {
        assertEquals("", StringUtils.repeat('z', -2));
    }

    @Test
    public void testRepeat_StringInt_1() {
        assertNull(StringUtils.repeat(null, 2));
    }

    @Test
    public void testRepeat_StringInt_2() {
        assertEquals("", StringUtils.repeat("ab", 0));
    }

    @Test
    public void testRepeat_StringInt_3() {
        assertEquals("", StringUtils.repeat("", 3));
    }

    @Test
    public void testRepeat_StringInt_4() {
        assertEquals("aaa", StringUtils.repeat("a", 3));
    }

    @Test
    public void testRepeat_StringInt_5() {
        assertEquals("", StringUtils.repeat("a", -2));
    }

    @Test
    public void testRepeat_StringInt_6() {
        assertEquals("ababab", StringUtils.repeat("ab", 3));
    }

    @Test
    public void testRepeat_StringInt_7() {
        assertEquals("abcabcabc", StringUtils.repeat("abc", 3));
    }

    @Test
    public void testRepeat_StringInt_8_testMerged_8() {
        final String str = StringUtils.repeat("a", 10000);
        assertEquals(10000, str.length());
        assertTrue(StringUtils.containsOnly(str, 'a'));
    }

    @Test
    public void testRepeat_StringStringInt_1() {
        assertNull(StringUtils.repeat(null, null, 2));
    }

    @Test
    public void testRepeat_StringStringInt_2() {
        assertNull(StringUtils.repeat(null, "x", 2));
    }

    @Test
    public void testRepeat_StringStringInt_3() {
        assertEquals("", StringUtils.repeat("", null, 2));
    }

    @Test
    public void testRepeat_StringStringInt_4() {
        assertEquals("", StringUtils.repeat("ab", "", 0));
    }

    @Test
    public void testRepeat_StringStringInt_5() {
        assertEquals("", StringUtils.repeat("", "", 2));
    }

    @Test
    public void testRepeat_StringStringInt_6() {
        assertEquals("xx", StringUtils.repeat("", "x", 3));
    }

    @Test
    public void testRepeat_StringStringInt_7() {
        assertEquals("?, ?, ?", StringUtils.repeat("?", ", ", 3));
    }

    @Test
    public void testReplace_StringStringString_1() {
        assertNull(StringUtils.replace(null, null, null));
    }

    @Test
    public void testReplace_StringStringString_2() {
        assertNull(StringUtils.replace(null, null, "any"));
    }

    @Test
    public void testReplace_StringStringString_3() {
        assertNull(StringUtils.replace(null, "any", null));
    }

    @Test
    public void testReplace_StringStringString_4() {
        assertNull(StringUtils.replace(null, "any", "any"));
    }

    @Test
    public void testReplace_StringStringString_5() {
        assertEquals("", StringUtils.replace("", null, null));
    }

    @Test
    public void testReplace_StringStringString_6() {
        assertEquals("", StringUtils.replace("", null, "any"));
    }

    @Test
    public void testReplace_StringStringString_7() {
        assertEquals("", StringUtils.replace("", "any", null));
    }

    @Test
    public void testReplace_StringStringString_8() {
        assertEquals("", StringUtils.replace("", "any", "any"));
    }

    @Test
    public void testReplace_StringStringString_9() {
        assertEquals("FOO", StringUtils.replace("FOO", "", "any"));
    }

    @Test
    public void testReplace_StringStringString_10() {
        assertEquals("FOO", StringUtils.replace("FOO", null, "any"));
    }

    @Test
    public void testReplace_StringStringString_11() {
        assertEquals("FOO", StringUtils.replace("FOO", "F", null));
    }

    @Test
    public void testReplace_StringStringString_12() {
        assertEquals("FOO", StringUtils.replace("FOO", null, null));
    }

    @Test
    public void testReplace_StringStringString_13() {
        assertEquals("", StringUtils.replace("foofoofoo", "foo", ""));
    }

    @Test
    public void testReplace_StringStringString_14() {
        assertEquals("barbarbar", StringUtils.replace("foofoofoo", "foo", "bar"));
    }

    @Test
    public void testReplace_StringStringString_15() {
        assertEquals("farfarfar", StringUtils.replace("foofoofoo", "oo", "ar"));
    }

    @Test
    public void testReplaceChars_StringCharChar_1() {
        assertNull(StringUtils.replaceChars(null, 'b', 'z'));
    }

    @Test
    public void testReplaceChars_StringCharChar_2() {
        assertEquals("", StringUtils.replaceChars("", 'b', 'z'));
    }

    @Test
    public void testReplaceChars_StringCharChar_3() {
        assertEquals("azcza", StringUtils.replaceChars("abcba", 'b', 'z'));
    }

    @Test
    public void testReplaceChars_StringCharChar_4() {
        assertEquals("abcba", StringUtils.replaceChars("abcba", 'x', 'z'));
    }

    @Test
    public void testReplaceChars_StringStringString_1() {
        assertNull(StringUtils.replaceChars(null, null, null));
    }

    @Test
    public void testReplaceChars_StringStringString_2() {
        assertNull(StringUtils.replaceChars(null, "", null));
    }

    @Test
    public void testReplaceChars_StringStringString_3() {
        assertNull(StringUtils.replaceChars(null, "a", null));
    }

    @Test
    public void testReplaceChars_StringStringString_4() {
        assertNull(StringUtils.replaceChars(null, null, ""));
    }

    @Test
    public void testReplaceChars_StringStringString_5() {
        assertNull(StringUtils.replaceChars(null, null, "x"));
    }

    @Test
    public void testReplaceChars_StringStringString_6() {
        assertEquals("", StringUtils.replaceChars("", null, null));
    }

    @Test
    public void testReplaceChars_StringStringString_7() {
        assertEquals("", StringUtils.replaceChars("", "", null));
    }

    @Test
    public void testReplaceChars_StringStringString_8() {
        assertEquals("", StringUtils.replaceChars("", "a", null));
    }

    @Test
    public void testReplaceChars_StringStringString_9() {
        assertEquals("", StringUtils.replaceChars("", null, ""));
    }

    @Test
    public void testReplaceChars_StringStringString_10() {
        assertEquals("", StringUtils.replaceChars("", null, "x"));
    }

    @Test
    public void testReplaceChars_StringStringString_11() {
        assertEquals("abc", StringUtils.replaceChars("abc", null, null));
    }

    @Test
    public void testReplaceChars_StringStringString_12() {
        assertEquals("abc", StringUtils.replaceChars("abc", null, ""));
    }

    @Test
    public void testReplaceChars_StringStringString_13() {
        assertEquals("abc", StringUtils.replaceChars("abc", null, "x"));
    }

    @Test
    public void testReplaceChars_StringStringString_14() {
        assertEquals("abc", StringUtils.replaceChars("abc", "", null));
    }

    @Test
    public void testReplaceChars_StringStringString_15() {
        assertEquals("abc", StringUtils.replaceChars("abc", "", ""));
    }

    @Test
    public void testReplaceChars_StringStringString_16() {
        assertEquals("abc", StringUtils.replaceChars("abc", "", "x"));
    }

    @Test
    public void testReplaceChars_StringStringString_17() {
        assertEquals("ac", StringUtils.replaceChars("abc", "b", null));
    }

    @Test
    public void testReplaceChars_StringStringString_18() {
        assertEquals("ac", StringUtils.replaceChars("abc", "b", ""));
    }

    @Test
    public void testReplaceChars_StringStringString_19() {
        assertEquals("axc", StringUtils.replaceChars("abc", "b", "x"));
    }

    @Test
    public void testReplaceChars_StringStringString_20() {
        assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yz"));
    }

    @Test
    public void testReplaceChars_StringStringString_21() {
        assertEquals("ayya", StringUtils.replaceChars("abcba", "bc", "y"));
    }

    @Test
    public void testReplaceChars_StringStringString_22() {
        assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yzx"));
    }

    @Test
    public void testReplaceChars_StringStringString_23() {
        assertEquals("abcba", StringUtils.replaceChars("abcba", "z", "w"));
    }

    @Test
    public void testReplaceChars_StringStringString_24() {
        assertSame("abcba", StringUtils.replaceChars("abcba", "z", "w"));
    }

    @Test
    public void testReplaceChars_StringStringString_25() {
        assertEquals("jelly", StringUtils.replaceChars("hello", "ho", "jy"));
    }

    @Test
    public void testReplaceChars_StringStringString_26() {
        assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yz"));
    }

    @Test
    public void testReplaceChars_StringStringString_27() {
        assertEquals("ayya", StringUtils.replaceChars("abcba", "bc", "y"));
    }

    @Test
    public void testReplaceChars_StringStringString_28() {
        assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yzx"));
    }

    @Test
    public void testReplaceChars_StringStringString_29() {
        assertEquals("bcc", StringUtils.replaceChars("abc", "ab", "bc"));
    }

    @Test
    public void testReplaceChars_StringStringString_30() {
        assertEquals("q651.506bera", StringUtils.replaceChars("d216.102oren", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789", "nopqrstuvwxyzabcdefghijklmNOPQRSTUVWXYZABCDEFGHIJKLM567891234"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_1() {
        assertNull(StringUtils.replaceIgnoreCase(null, null, null));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_2() {
        assertNull(StringUtils.replaceIgnoreCase(null, null, "any"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_3() {
        assertNull(StringUtils.replaceIgnoreCase(null, "any", null));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_4() {
        assertNull(StringUtils.replaceIgnoreCase(null, "any", "any"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_5() {
        assertEquals("", StringUtils.replaceIgnoreCase("", null, null));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_6() {
        assertEquals("", StringUtils.replaceIgnoreCase("", null, "any"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_7() {
        assertEquals("", StringUtils.replaceIgnoreCase("", "any", null));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_8() {
        assertEquals("", StringUtils.replaceIgnoreCase("", "any", "any"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_9() {
        assertEquals("FOO", StringUtils.replaceIgnoreCase("FOO", "", "any"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_10() {
        assertEquals("FOO", StringUtils.replaceIgnoreCase("FOO", null, "any"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_11() {
        assertEquals("FOO", StringUtils.replaceIgnoreCase("FOO", "F", null));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_12() {
        assertEquals("FOO", StringUtils.replaceIgnoreCase("FOO", null, null));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_13() {
        assertEquals("", StringUtils.replaceIgnoreCase("foofoofoo", "foo", ""));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_14() {
        assertEquals("barbarbar", StringUtils.replaceIgnoreCase("foofoofoo", "foo", "bar"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_15() {
        assertEquals("farfarfar", StringUtils.replaceIgnoreCase("foofoofoo", "oo", "ar"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_16() {
        assertEquals("", StringUtils.replaceIgnoreCase("foofoofoo", "FOO", ""));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_17() {
        assertEquals("barbarbar", StringUtils.replaceIgnoreCase("fooFOOfoo", "foo", "bar"));
    }

    @Test
    public void testReplaceIgnoreCase_StringStringString_18() {
        assertEquals("farfarfar", StringUtils.replaceIgnoreCase("foofOOfoo", "OO", "ar"));
    }

    @Test
    public void testReplaceOnce_StringStringString_1() {
        assertNull(StringUtils.replaceOnce(null, null, null));
    }

    @Test
    public void testReplaceOnce_StringStringString_2() {
        assertNull(StringUtils.replaceOnce(null, null, "any"));
    }

    @Test
    public void testReplaceOnce_StringStringString_3() {
        assertNull(StringUtils.replaceOnce(null, "any", null));
    }

    @Test
    public void testReplaceOnce_StringStringString_4() {
        assertNull(StringUtils.replaceOnce(null, "any", "any"));
    }

    @Test
    public void testReplaceOnce_StringStringString_5() {
        assertEquals("", StringUtils.replaceOnce("", null, null));
    }

    @Test
    public void testReplaceOnce_StringStringString_6() {
        assertEquals("", StringUtils.replaceOnce("", null, "any"));
    }

    @Test
    public void testReplaceOnce_StringStringString_7() {
        assertEquals("", StringUtils.replaceOnce("", "any", null));
    }

    @Test
    public void testReplaceOnce_StringStringString_8() {
        assertEquals("", StringUtils.replaceOnce("", "any", "any"));
    }

    @Test
    public void testReplaceOnce_StringStringString_9() {
        assertEquals("FOO", StringUtils.replaceOnce("FOO", "", "any"));
    }

    @Test
    public void testReplaceOnce_StringStringString_10() {
        assertEquals("FOO", StringUtils.replaceOnce("FOO", null, "any"));
    }

    @Test
    public void testReplaceOnce_StringStringString_11() {
        assertEquals("FOO", StringUtils.replaceOnce("FOO", "F", null));
    }

    @Test
    public void testReplaceOnce_StringStringString_12() {
        assertEquals("FOO", StringUtils.replaceOnce("FOO", null, null));
    }

    @Test
    public void testReplaceOnce_StringStringString_13() {
        assertEquals("foofoo", StringUtils.replaceOnce("foofoofoo", "foo", ""));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_1() {
        assertNull(StringUtils.replaceOnceIgnoreCase(null, null, null));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_2() {
        assertNull(StringUtils.replaceOnceIgnoreCase(null, null, "any"));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_3() {
        assertNull(StringUtils.replaceOnceIgnoreCase(null, "any", null));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_4() {
        assertNull(StringUtils.replaceOnceIgnoreCase(null, "any", "any"));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_5() {
        assertEquals("", StringUtils.replaceOnceIgnoreCase("", null, null));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_6() {
        assertEquals("", StringUtils.replaceOnceIgnoreCase("", null, "any"));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_7() {
        assertEquals("", StringUtils.replaceOnceIgnoreCase("", "any", null));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_8() {
        assertEquals("", StringUtils.replaceOnceIgnoreCase("", "any", "any"));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_9() {
        assertEquals("FOO", StringUtils.replaceOnceIgnoreCase("FOO", "", "any"));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_10() {
        assertEquals("FOO", StringUtils.replaceOnceIgnoreCase("FOO", null, "any"));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_11() {
        assertEquals("FOO", StringUtils.replaceOnceIgnoreCase("FOO", "F", null));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_12() {
        assertEquals("FOO", StringUtils.replaceOnceIgnoreCase("FOO", null, null));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_13() {
        assertEquals("foofoo", StringUtils.replaceOnceIgnoreCase("foofoofoo", "foo", ""));
    }

    @Test
    public void testReplaceOnceIgnoreCase_StringStringString_14() {
        assertEquals("Foofoo", StringUtils.replaceOnceIgnoreCase("FoOFoofoo", "foo", ""));
    }

    @Test
    public void testReplacePattern_StringStringString_1() {
        assertNull(StringUtils.replacePattern(null, "", ""));
    }

    @Test
    public void testReplacePattern_StringStringString_2() {
        assertEquals("any", StringUtils.replacePattern("any", null, ""));
    }

    @Test
    public void testReplacePattern_StringStringString_3() {
        assertEquals("any", StringUtils.replacePattern("any", "", null));
    }

    @Test
    public void testReplacePattern_StringStringString_4() {
        assertEquals("zzz", StringUtils.replacePattern("", "", "zzz"));
    }

    @Test
    public void testReplacePattern_StringStringString_5() {
        assertEquals("zzz", StringUtils.replacePattern("", ".*", "zzz"));
    }

    @Test
    public void testReplacePattern_StringStringString_6() {
        assertEquals("", StringUtils.replacePattern("", ".+", "zzz"));
    }

    @Test
    public void testReplacePattern_StringStringString_7() {
        assertEquals("z", StringUtils.replacePattern("<__>\n<__>", "<.*>", "z"));
    }

    @Test
    public void testReplacePattern_StringStringString_8() {
        assertEquals("z", StringUtils.replacePattern("<__>\\n<__>", "<.*>", "z"));
    }

    @Test
    public void testReplacePattern_StringStringString_9() {
        assertEquals("X", StringUtils.replacePattern("<A>\nxy\n</A>", "<A>.*</A>", "X"));
    }

    @Test
    public void testReplacePattern_StringStringString_10() {
        assertEquals("ABC___123", StringUtils.replacePattern("ABCabc123", "[a-z]", "_"));
    }

    @Test
    public void testReplacePattern_StringStringString_11() {
        assertEquals("ABC_123", StringUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "_"));
    }

    @Test
    public void testReplacePattern_StringStringString_12() {
        assertEquals("ABC123", StringUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", ""));
    }

    @Test
    public void testReplacePattern_StringStringString_13() {
        assertEquals("Lorem_ipsum_dolor_sit", StringUtils.replacePattern("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"));
    }

    @Test
    public void testReverse_String_1() {
        assertNull(StringUtils.reverse(null));
    }

    @Test
    public void testReverse_String_2() {
        assertEquals("", StringUtils.reverse(""));
    }

    @Test
    public void testReverse_String_3() {
        assertEquals("sdrawkcab", StringUtils.reverse("backwards"));
    }

    @Test
    public void testReverseDelimited_StringChar_1() {
        assertNull(StringUtils.reverseDelimited(null, '.'));
    }

    @Test
    public void testReverseDelimited_StringChar_2() {
        assertEquals("", StringUtils.reverseDelimited("", '.'));
    }

    @Test
    public void testReverseDelimited_StringChar_3() {
        assertEquals("c.b.a", StringUtils.reverseDelimited("a.b.c", '.'));
    }

    @Test
    public void testReverseDelimited_StringChar_4() {
        assertEquals("a b c", StringUtils.reverseDelimited("a b c", '.'));
    }

    @Test
    public void testReverseDelimited_StringChar_5() {
        assertEquals("", StringUtils.reverseDelimited("", '.'));
    }

    @Test
    public void testRightPad_StringInt_1() {
        assertNull(StringUtils.rightPad(null, 5));
    }

    @Test
    public void testRightPad_StringInt_2() {
        assertEquals("     ", StringUtils.rightPad("", 5));
    }

    @Test
    public void testRightPad_StringInt_3() {
        assertEquals("abc  ", StringUtils.rightPad("abc", 5));
    }

    @Test
    public void testRightPad_StringInt_4() {
        assertEquals("abc", StringUtils.rightPad("abc", 2));
    }

    @Test
    public void testRightPad_StringInt_5() {
        assertEquals("abc", StringUtils.rightPad("abc", -1));
    }

    @Test
    public void testRightPad_StringIntChar_1() {
        assertNull(StringUtils.rightPad(null, 5, ' '));
    }

    @Test
    public void testRightPad_StringIntChar_2() {
        assertEquals("     ", StringUtils.rightPad("", 5, ' '));
    }

    @Test
    public void testRightPad_StringIntChar_3() {
        assertEquals("abc  ", StringUtils.rightPad("abc", 5, ' '));
    }

    @Test
    public void testRightPad_StringIntChar_4() {
        assertEquals("abc", StringUtils.rightPad("abc", 2, ' '));
    }

    @Test
    public void testRightPad_StringIntChar_5() {
        assertEquals("abc", StringUtils.rightPad("abc", -1, ' '));
    }

    @Test
    public void testRightPad_StringIntChar_6() {
        assertEquals("abcxx", StringUtils.rightPad("abc", 5, 'x'));
    }

    @Test
    public void testRightPad_StringIntChar_7_testMerged_7() {
        final String str = StringUtils.rightPad("aaa", 10000, 'a');
        assertEquals(10000, str.length());
        assertTrue(StringUtils.containsOnly(str, 'a'));
    }

    @Test
    public void testRightPad_StringIntString_1() {
        assertNull(StringUtils.rightPad(null, 5, "-+"));
    }

    @Test
    public void testRightPad_StringIntString_2() {
        assertEquals("     ", StringUtils.rightPad("", 5, " "));
    }

    @Test
    public void testRightPad_StringIntString_3() {
        assertNull(StringUtils.rightPad(null, 8, null));
    }

    @Test
    public void testRightPad_StringIntString_4() {
        assertEquals("abc-+-+", StringUtils.rightPad("abc", 7, "-+"));
    }

    @Test
    public void testRightPad_StringIntString_5() {
        assertEquals("abc-+~", StringUtils.rightPad("abc", 6, "-+~"));
    }

    @Test
    public void testRightPad_StringIntString_6() {
        assertEquals("abc-+", StringUtils.rightPad("abc", 5, "-+~"));
    }

    @Test
    public void testRightPad_StringIntString_7() {
        assertEquals("abc", StringUtils.rightPad("abc", 2, " "));
    }

    @Test
    public void testRightPad_StringIntString_8() {
        assertEquals("abc", StringUtils.rightPad("abc", -1, " "));
    }

    @Test
    public void testRightPad_StringIntString_9() {
        assertEquals("abc  ", StringUtils.rightPad("abc", 5, null));
    }

    @Test
    public void testRightPad_StringIntString_10() {
        assertEquals("abc  ", StringUtils.rightPad("abc", 5, ""));
    }

    @Test
    public void testRotate_StringInt_1() {
        assertNull(StringUtils.rotate(null, 1));
    }

    @Test
    public void testRotate_StringInt_2() {
        assertEquals("", StringUtils.rotate("", 1));
    }

    @Test
    public void testRotate_StringInt_3() {
        assertEquals("abcdefg", StringUtils.rotate("abcdefg", 0));
    }

    @Test
    public void testRotate_StringInt_4() {
        assertEquals("fgabcde", StringUtils.rotate("abcdefg", 2));
    }

    @Test
    public void testRotate_StringInt_5() {
        assertEquals("cdefgab", StringUtils.rotate("abcdefg", -2));
    }

    @Test
    public void testRotate_StringInt_6() {
        assertEquals("abcdefg", StringUtils.rotate("abcdefg", 7));
    }

    @Test
    public void testRotate_StringInt_7() {
        assertEquals("abcdefg", StringUtils.rotate("abcdefg", -7));
    }

    @Test
    public void testRotate_StringInt_8() {
        assertEquals("fgabcde", StringUtils.rotate("abcdefg", 9));
    }

    @Test
    public void testRotate_StringInt_9() {
        assertEquals("cdefgab", StringUtils.rotate("abcdefg", -9));
    }

    @Test
    public void testRotate_StringInt_10() {
        assertEquals("efgabcd", StringUtils.rotate("abcdefg", 17));
    }

    @Test
    public void testRotate_StringInt_11() {
        assertEquals("defgabc", StringUtils.rotate("abcdefg", -17));
    }

    @Test
    public void testSplit_String_1() {
        assertNull(StringUtils.split(null));
    }

    @Test
    public void testSplit_String_2() {
        assertEquals(0, StringUtils.split("").length);
    }

    @Test
    public void testSplit_String_3_testMerged_3() {
        String str = "a b  .c";
        String[] res = StringUtils.split(str);
        assertEquals(3, res.length);
        assertEquals("a", res[0]);
        assertEquals("b", res[1]);
        assertEquals(".c", res[2]);
        str = " a ";
        res = StringUtils.split(str);
        assertEquals(1, res.length);
        str = "a" + WHITESPACE + "b" + NON_WHITESPACE + "c";
        assertEquals(2, res.length);
        assertEquals("b" + NON_WHITESPACE + "c", res[1]);
    }

    @Test
    public void testSplit_StringChar_1() {
        assertNull(StringUtils.split(null, '.'));
    }

    @Test
    public void testSplit_StringChar_2() {
        assertEquals(0, StringUtils.split("", '.').length);
    }

    @Test
    public void testSplit_StringChar_3_testMerged_3() {
        String str = "a.b.. c";
        String[] res = StringUtils.split(str, '.');
        assertEquals(3, res.length);
        assertEquals("a", res[0]);
        assertEquals("b", res[1]);
        assertEquals(" c", res[2]);
        str = ".a.";
        res = StringUtils.split(str, '.');
        assertEquals(1, res.length);
        str = "a b c";
        res = StringUtils.split(str, ' ');
        assertEquals("c", res[2]);
    }

    @Test
    public void testSwapCase_String_1() {
        assertNull(StringUtils.swapCase(null));
    }

    @Test
    public void testSwapCase_String_2() {
        assertEquals("", StringUtils.swapCase(""));
    }

    @Test
    public void testSwapCase_String_3() {
        assertEquals("  ", StringUtils.swapCase("  "));
    }

    @Test
    public void testSwapCase_String_4() {
        assertEquals("i", WordUtils.swapCase("I"));
    }

    @Test
    public void testSwapCase_String_5() {
        assertEquals("I", WordUtils.swapCase("i"));
    }

    @Test
    public void testSwapCase_String_6() {
        assertEquals("I AM HERE 123", StringUtils.swapCase("i am here 123"));
    }

    @Test
    public void testSwapCase_String_7() {
        assertEquals("i aM hERE 123", StringUtils.swapCase("I Am Here 123"));
    }

    @Test
    public void testSwapCase_String_8() {
        assertEquals("I AM here 123", StringUtils.swapCase("i am HERE 123"));
    }

    @Test
    public void testSwapCase_String_9() {
        assertEquals("i am here 123", StringUtils.swapCase("I AM HERE 123"));
    }

    @Test
    public void testSwapCase_String_10_testMerged_10() {
        final String test = "This String contains a TitleCase character: \u01C8";
        final String expect = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: \u01C9";
        assertEquals(expect, WordUtils.swapCase(test));
        assertEquals(expect, StringUtils.swapCase(test));
    }

    @Test
    @ReadsDefaultLocale
    @WritesDefaultLocale
    public void testToRootLowerCase_1() {
        assertNull(StringUtils.toRootLowerCase(null));
    }

    @Test
    @ReadsDefaultLocale
    @WritesDefaultLocale
    public void testToRootLowerCase_2() {
        assertEquals("a", StringUtils.toRootLowerCase("A"));
    }

    @Test
    @ReadsDefaultLocale
    @WritesDefaultLocale
    public void testToRootLowerCase_3() {
        assertEquals("a", StringUtils.toRootLowerCase("a"));
    }

    @Test
    @ReadsDefaultLocale
    @WritesDefaultLocale
    public void testToRootLowerCase_4_testMerged_4() {
        final Locale TURKISH = Locale.forLanguageTag("tr");
        assertNotEquals("title", "TITLE".toLowerCase(TURKISH));
        assertEquals("title", "TITLE".toLowerCase(Locale.ROOT));
    }

    @Test
    @ReadsDefaultLocale
    @WritesDefaultLocale
    public void testToRootLowerCase_6() {
        assertEquals("title", StringUtils.toRootLowerCase("TITLE"));
    }

    @Test
    public void testUnCapitalize_1() {
        assertNull(StringUtils.uncapitalize(null));
    }

    @Test
    public void testUnCapitalize_2() {
        assertEquals(FOO_UNCAP, StringUtils.uncapitalize(FOO_CAP), "uncapitalize(String) failed");
    }

    @Test
    public void testUnCapitalize_3() {
        assertEquals(FOO_UNCAP, StringUtils.uncapitalize(FOO_UNCAP), "uncapitalize(string) failed");
    }

    @Test
    public void testUnCapitalize_4() {
        assertEquals("", StringUtils.uncapitalize(""), "uncapitalize(empty-string) failed");
    }

    @Test
    public void testUnCapitalize_5() {
        assertEquals("x", StringUtils.uncapitalize("X"), "uncapitalize(single-char-string) failed");
    }

    @Test
    public void testUnCapitalize_6() {
        assertEquals("cat", StringUtils.uncapitalize("cat"));
    }

    @Test
    public void testUnCapitalize_7() {
        assertEquals("cat", StringUtils.uncapitalize("Cat"));
    }

    @Test
    public void testUnCapitalize_8() {
        assertEquals("cAT", StringUtils.uncapitalize("CAT"));
    }

    @Test
    public void testUnescapeSurrogatePairs_1() {
        assertEquals("\uD83D\uDE30", StringEscapeUtils.unescapeCsv("\uD83D\uDE30"));
    }

    @Test
    public void testUnescapeSurrogatePairs_2() {
        assertEquals("\uD800\uDC00", StringEscapeUtils.unescapeCsv("\uD800\uDC00"));
    }

    @Test
    public void testUnescapeSurrogatePairs_3() {
        assertEquals("\uD834\uDD1E", StringEscapeUtils.unescapeCsv("\uD834\uDD1E"));
    }

    @Test
    public void testUnescapeSurrogatePairs_4() {
        assertEquals("\uDBFF\uDFFD", StringEscapeUtils.unescapeCsv("\uDBFF\uDFFD"));
    }

    @Test
    public void testUnescapeSurrogatePairs_5() {
        assertEquals("\uDBFF\uDFFD", StringEscapeUtils.unescapeHtml3("\uDBFF\uDFFD"));
    }

    @Test
    public void testUnescapeSurrogatePairs_6() {
        assertEquals("\uDBFF\uDFFD", StringEscapeUtils.unescapeHtml4("\uDBFF\uDFFD"));
    }

    @Test
    public void testUnwrap_StringChar_1() {
        assertNull(StringUtils.unwrap(null, null));
    }

    @Test
    public void testUnwrap_StringChar_2() {
        assertNull(StringUtils.unwrap(null, CharUtils.NUL));
    }

    @Test
    public void testUnwrap_StringChar_3() {
        assertNull(StringUtils.unwrap(null, '1'));
    }

    @Test
    public void testUnwrap_StringChar_4() {
        assertEquals("abc", StringUtils.unwrap("abc", null));
    }

    @Test
    public void testUnwrap_StringChar_5() {
        assertEquals("a", StringUtils.unwrap("a", "a"));
    }

    @Test
    public void testUnwrap_StringChar_6() {
        assertEquals("", StringUtils.unwrap("aa", "a"));
    }

    @Test
    public void testUnwrap_StringChar_7() {
        assertEquals("abc", StringUtils.unwrap("\'abc\'", '\''));
    }

    @Test
    public void testUnwrap_StringChar_8() {
        assertEquals("abc", StringUtils.unwrap("AabcA", 'A'));
    }

    @Test
    public void testUnwrap_StringChar_9() {
        assertEquals("AabcA", StringUtils.unwrap("AAabcAA", 'A'));
    }

    @Test
    public void testUnwrap_StringChar_10() {
        assertEquals("abc", StringUtils.unwrap("abc", 'b'));
    }

    @Test
    public void testUnwrap_StringChar_11() {
        assertEquals("#A", StringUtils.unwrap("#A", '#'));
    }

    @Test
    public void testUnwrap_StringChar_12() {
        assertEquals("A#", StringUtils.unwrap("A#", '#'));
    }

    @Test
    public void testUnwrap_StringChar_13() {
        assertEquals("ABA", StringUtils.unwrap("AABAA", 'A'));
    }

    @Test
    public void testUnwrap_StringString_1() {
        assertNull(StringUtils.unwrap(null, null));
    }

    @Test
    public void testUnwrap_StringString_2() {
        assertNull(StringUtils.unwrap(null, ""));
    }

    @Test
    public void testUnwrap_StringString_3() {
        assertNull(StringUtils.unwrap(null, "1"));
    }

    @Test
    public void testUnwrap_StringString_4() {
        assertEquals("abc", StringUtils.unwrap("abc", null));
    }

    @Test
    public void testUnwrap_StringString_5() {
        assertEquals("abc", StringUtils.unwrap("abc", ""));
    }

    @Test
    public void testUnwrap_StringString_6() {
        assertEquals("a", StringUtils.unwrap("a", "a"));
    }

    @Test
    public void testUnwrap_StringString_7() {
        assertEquals("ababa", StringUtils.unwrap("ababa", "aba"));
    }

    @Test
    public void testUnwrap_StringString_8() {
        assertEquals("", StringUtils.unwrap("aa", "a"));
    }

    @Test
    public void testUnwrap_StringString_9() {
        assertEquals("abc", StringUtils.unwrap("\'abc\'", "\'"));
    }

    @Test
    public void testUnwrap_StringString_10() {
        assertEquals("abc", StringUtils.unwrap("\"abc\"", "\""));
    }

    @Test
    public void testUnwrap_StringString_11() {
        assertEquals("abc\"xyz", StringUtils.unwrap("\"abc\"xyz\"", "\""));
    }

    @Test
    public void testUnwrap_StringString_12() {
        assertEquals("abc\"xyz\"", StringUtils.unwrap("\"abc\"xyz\"\"", "\""));
    }

    @Test
    public void testUnwrap_StringString_13() {
        assertEquals("abc\'xyz\'", StringUtils.unwrap("\"abc\'xyz\'\"", "\""));
    }

    @Test
    public void testUnwrap_StringString_14() {
        assertEquals("\"abc\'xyz\'\"", StringUtils.unwrap("AA\"abc\'xyz\'\"AA", "AA"));
    }

    @Test
    public void testUnwrap_StringString_15() {
        assertEquals("\"abc\'xyz\'\"", StringUtils.unwrap("123\"abc\'xyz\'\"123", "123"));
    }

    @Test
    public void testUnwrap_StringString_16() {
        assertEquals("AA\"abc\'xyz\'\"", StringUtils.unwrap("AA\"abc\'xyz\'\"", "AA"));
    }

    @Test
    public void testUnwrap_StringString_17() {
        assertEquals("AA\"abc\'xyz\'\"AA", StringUtils.unwrap("AAA\"abc\'xyz\'\"AAA", "A"));
    }

    @Test
    public void testUnwrap_StringString_18() {
        assertEquals("\"abc\'xyz\'\"AA", StringUtils.unwrap("\"abc\'xyz\'\"AA", "AA"));
    }

    @Test
    public void testUpperCase_1() {
        assertNull(StringUtils.upperCase(null));
    }

    @Test
    public void testUpperCase_2() {
        assertNull(StringUtils.upperCase(null, Locale.ENGLISH));
    }

    @Test
    public void testUpperCase_3() {
        assertEquals("FOO TEST THING", StringUtils.upperCase("fOo test THING"), "upperCase(String) failed");
    }

    @Test
    public void testUpperCase_4() {
        assertEquals("", StringUtils.upperCase(""), "upperCase(empty-string) failed");
    }

    @Test
    public void testUpperCase_5() {
        assertEquals("FOO TEST THING", StringUtils.upperCase("fOo test THING", Locale.ENGLISH), "upperCase(String, Locale) failed");
    }

    @Test
    public void testUpperCase_6() {
        assertEquals("", StringUtils.upperCase("", Locale.ENGLISH), "upperCase(empty-string, Locale) failed");
    }

    @Test
    public void testWrap_StringChar_1() {
        assertNull(StringUtils.wrap(null, CharUtils.NUL));
    }

    @Test
    public void testWrap_StringChar_2() {
        assertNull(StringUtils.wrap(null, '1'));
    }

    @Test
    public void testWrap_StringChar_3() {
        assertEquals("", StringUtils.wrap("", CharUtils.NUL));
    }

    @Test
    public void testWrap_StringChar_4() {
        assertEquals("xabx", StringUtils.wrap("ab", 'x'));
    }

    @Test
    public void testWrap_StringChar_5() {
        assertEquals("\"ab\"", StringUtils.wrap("ab", '\"'));
    }

    @Test
    public void testWrap_StringChar_6() {
        assertEquals("\"\"ab\"\"", StringUtils.wrap("\"ab\"", '\"'));
    }

    @Test
    public void testWrap_StringChar_7() {
        assertEquals("'ab'", StringUtils.wrap("ab", '\''));
    }

    @Test
    public void testWrap_StringChar_8() {
        assertEquals("''abcd''", StringUtils.wrap("'abcd'", '\''));
    }

    @Test
    public void testWrap_StringChar_9() {
        assertEquals("'\"abcd\"'", StringUtils.wrap("\"abcd\"", '\''));
    }

    @Test
    public void testWrap_StringChar_10() {
        assertEquals("\"'abcd'\"", StringUtils.wrap("'abcd'", '\"'));
    }

    @Test
    public void testWrap_StringString_1() {
        assertNull(StringUtils.wrap(null, null));
    }

    @Test
    public void testWrap_StringString_2() {
        assertNull(StringUtils.wrap(null, ""));
    }

    @Test
    public void testWrap_StringString_3() {
        assertNull(StringUtils.wrap(null, "1"));
    }

    @Test
    public void testWrap_StringString_4() {
        assertNull(StringUtils.wrap(null, null));
    }

    @Test
    public void testWrap_StringString_5() {
        assertEquals("", StringUtils.wrap("", ""));
    }

    @Test
    public void testWrap_StringString_6() {
        assertEquals("ab", StringUtils.wrap("ab", null));
    }

    @Test
    public void testWrap_StringString_7() {
        assertEquals("xabx", StringUtils.wrap("ab", "x"));
    }

    @Test
    public void testWrap_StringString_8() {
        assertEquals("\"ab\"", StringUtils.wrap("ab", "\""));
    }

    @Test
    public void testWrap_StringString_9() {
        assertEquals("\"\"ab\"\"", StringUtils.wrap("\"ab\"", "\""));
    }

    @Test
    public void testWrap_StringString_10() {
        assertEquals("'ab'", StringUtils.wrap("ab", "'"));
    }

    @Test
    public void testWrap_StringString_11() {
        assertEquals("''abcd''", StringUtils.wrap("'abcd'", "'"));
    }

    @Test
    public void testWrap_StringString_12() {
        assertEquals("'\"abcd\"'", StringUtils.wrap("\"abcd\"", "'"));
    }

    @Test
    public void testWrap_StringString_13() {
        assertEquals("\"'abcd'\"", StringUtils.wrap("'abcd'", "\""));
    }

    @Test
    public void testWrapIfMissing_StringChar_1() {
        assertNull(StringUtils.wrapIfMissing(null, CharUtils.NUL));
    }

    @Test
    public void testWrapIfMissing_StringChar_2() {
        assertNull(StringUtils.wrapIfMissing(null, '1'));
    }

    @Test
    public void testWrapIfMissing_StringChar_3() {
        assertEquals("", StringUtils.wrapIfMissing("", CharUtils.NUL));
    }

    @Test
    public void testWrapIfMissing_StringChar_4() {
        assertEquals("xabx", StringUtils.wrapIfMissing("ab", 'x'));
    }

    @Test
    public void testWrapIfMissing_StringChar_5() {
        assertEquals("\"ab\"", StringUtils.wrapIfMissing("ab", '\"'));
    }

    @Test
    public void testWrapIfMissing_StringChar_6() {
        assertEquals("\"ab\"", StringUtils.wrapIfMissing("\"ab\"", '\"'));
    }

    @Test
    public void testWrapIfMissing_StringChar_7() {
        assertEquals("'ab'", StringUtils.wrapIfMissing("ab", '\''));
    }

    @Test
    public void testWrapIfMissing_StringChar_8() {
        assertEquals("'abcd'", StringUtils.wrapIfMissing("'abcd'", '\''));
    }

    @Test
    public void testWrapIfMissing_StringChar_9() {
        assertEquals("'\"abcd\"'", StringUtils.wrapIfMissing("\"abcd\"", '\''));
    }

    @Test
    public void testWrapIfMissing_StringChar_10() {
        assertEquals("\"'abcd'\"", StringUtils.wrapIfMissing("'abcd'", '\"'));
    }

    @Test
    public void testWrapIfMissing_StringChar_11() {
        assertEquals("/x/", StringUtils.wrapIfMissing("x", '/'));
    }

    @Test
    public void testWrapIfMissing_StringChar_12() {
        assertEquals("/x/y/z/", StringUtils.wrapIfMissing("x/y/z", '/'));
    }

    @Test
    public void testWrapIfMissing_StringChar_13() {
        assertEquals("/x/y/z/", StringUtils.wrapIfMissing("/x/y/z", '/'));
    }

    @Test
    public void testWrapIfMissing_StringChar_14() {
        assertEquals("/x/y/z/", StringUtils.wrapIfMissing("x/y/z/", '/'));
    }

    @Test
    public void testWrapIfMissing_StringChar_15() {
        assertSame("/", StringUtils.wrapIfMissing("/", '/'));
    }

    @Test
    public void testWrapIfMissing_StringChar_16() {
        assertSame("/x/", StringUtils.wrapIfMissing("/x/", '/'));
    }

    @Test
    public void testWrapIfMissing_StringString_1() {
        assertNull(StringUtils.wrapIfMissing(null, "\0"));
    }

    @Test
    public void testWrapIfMissing_StringString_2() {
        assertNull(StringUtils.wrapIfMissing(null, "1"));
    }

    @Test
    public void testWrapIfMissing_StringString_3() {
        assertEquals("", StringUtils.wrapIfMissing("", "\0"));
    }

    @Test
    public void testWrapIfMissing_StringString_4() {
        assertEquals("xabx", StringUtils.wrapIfMissing("ab", "x"));
    }

    @Test
    public void testWrapIfMissing_StringString_5() {
        assertEquals("\"ab\"", StringUtils.wrapIfMissing("ab", "\""));
    }

    @Test
    public void testWrapIfMissing_StringString_6() {
        assertEquals("\"ab\"", StringUtils.wrapIfMissing("\"ab\"", "\""));
    }

    @Test
    public void testWrapIfMissing_StringString_7() {
        assertEquals("'ab'", StringUtils.wrapIfMissing("ab", "\'"));
    }

    @Test
    public void testWrapIfMissing_StringString_8() {
        assertEquals("'abcd'", StringUtils.wrapIfMissing("'abcd'", "\'"));
    }

    @Test
    public void testWrapIfMissing_StringString_9() {
        assertEquals("'\"abcd\"'", StringUtils.wrapIfMissing("\"abcd\"", "\'"));
    }

    @Test
    public void testWrapIfMissing_StringString_10() {
        assertEquals("\"'abcd'\"", StringUtils.wrapIfMissing("'abcd'", "\""));
    }

    @Test
    public void testWrapIfMissing_StringString_11() {
        assertEquals("/x/", StringUtils.wrapIfMissing("x", "/"));
    }

    @Test
    public void testWrapIfMissing_StringString_12() {
        assertEquals("/x/y/z/", StringUtils.wrapIfMissing("x/y/z", "/"));
    }

    @Test
    public void testWrapIfMissing_StringString_13() {
        assertEquals("/x/y/z/", StringUtils.wrapIfMissing("/x/y/z", "/"));
    }

    @Test
    public void testWrapIfMissing_StringString_14() {
        assertEquals("/x/y/z/", StringUtils.wrapIfMissing("x/y/z/", "/"));
    }

    @Test
    public void testWrapIfMissing_StringString_15() {
        assertEquals("/", StringUtils.wrapIfMissing("/", "/"));
    }

    @Test
    public void testWrapIfMissing_StringString_16() {
        assertEquals("ab/ab", StringUtils.wrapIfMissing("/", "ab"));
    }

    @Test
    public void testWrapIfMissing_StringString_17() {
        assertSame("ab/ab", StringUtils.wrapIfMissing("ab/ab", "ab"));
    }

    @Test
    public void testWrapIfMissing_StringString_18() {
        assertSame("//x//", StringUtils.wrapIfMissing("//x//", "//"));
    }
}
