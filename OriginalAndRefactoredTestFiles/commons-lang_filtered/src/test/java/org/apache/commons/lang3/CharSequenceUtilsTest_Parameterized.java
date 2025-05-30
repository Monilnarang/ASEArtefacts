package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CharSequenceUtilsTest_Parameterized extends AbstractLangTest {

    private abstract static class RunTest {

        abstract boolean invoke();

        void run(final TestData data, final String id) {
            if (data.throwable != null) {
                assertThrows(data.throwable, this::invoke, id + " Expected " + data.throwable);
            } else {
                final boolean stringCheck = invoke();
                assertEquals(data.expected, stringCheck, id + " Failed test " + data);
            }
        }
    }

    static class TestData {

        final String source;

        final boolean ignoreCase;

        final int toffset;

        final String other;

        final int ooffset;

        final int len;

        final boolean expected;

        final Class<? extends Throwable> throwable;

        TestData(final String source, final boolean ignoreCase, final int toffset, final String other, final int ooffset, final int len, final boolean expected) {
            this.source = source;
            this.ignoreCase = ignoreCase;
            this.toffset = toffset;
            this.other = other;
            this.ooffset = ooffset;
            this.len = len;
            this.expected = expected;
            this.throwable = null;
        }

        TestData(final String source, final boolean ignoreCase, final int toffset, final String other, final int ooffset, final int len, final Class<? extends Throwable> throwable) {
            this.source = source;
            this.ignoreCase = ignoreCase;
            this.toffset = toffset;
            this.other = other;
            this.ooffset = ooffset;
            this.len = len;
            this.expected = false;
            this.throwable = throwable;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(source).append("[").append(toffset).append("]");
            sb.append(ignoreCase ? " caseblind " : " samecase ");
            sb.append(other).append("[").append(ooffset).append("]");
            sb.append(" ").append(len).append(" => ");
            if (throwable != null) {
                sb.append(throwable);
            } else {
                sb.append(expected);
            }
            return sb.toString();
        }
    }

    static class WrapperString implements CharSequence {

        private final CharSequence inner;

        WrapperString(final CharSequence inner) {
            this.inner = inner;
        }

        @Override
        public char charAt(final int index) {
            return inner.charAt(index);
        }

        @Override
        public IntStream chars() {
            return inner.chars();
        }

        @Override
        public IntStream codePoints() {
            return inner.codePoints();
        }

        @Override
        public int length() {
            return inner.length();
        }

        @Override
        public CharSequence subSequence(final int start, final int end) {
            return inner.subSequence(start, end);
        }

        @Override
        public String toString() {
            return inner.toString();
        }
    }

    private static final TestData[] TEST_DATA = { new TestData("", true, -1, "", -1, -1, false), new TestData("", true, 0, "", 0, 1, false), new TestData("a", true, 0, "abc", 0, 0, true), new TestData("a", true, 0, "abc", 0, 1, true), new TestData("a", true, 0, null, 0, 0, NullPointerException.class), new TestData(null, true, 0, null, 0, 0, NullPointerException.class), new TestData(null, true, 0, "", 0, 0, NullPointerException.class), new TestData("Abc", true, 0, "abc", 0, 3, true), new TestData("Abc", false, 0, "abc", 0, 3, false), new TestData("Abc", true, 1, "abc", 1, 2, true), new TestData("Abc", false, 1, "abc", 1, 2, true), new TestData("Abcd", true, 1, "abcD", 1, 2, true), new TestData("Abcd", false, 1, "abcD", 1, 2, true) };

    static Stream<Arguments> lastIndexWithStandardCharSequence() {
        return Stream.of(arguments("abc", "b", 2, 1), arguments(new StringBuilder("abc"), "b", 2, 1), arguments(new StringBuffer("abc"), "b", 2, 1), arguments("abc", new StringBuilder("b"), 2, 1), arguments(new StringBuilder("abc"), new StringBuilder("b"), 2, 1), arguments(new StringBuffer("abc"), new StringBuffer("b"), 2, 1), arguments(new StringBuilder("abc"), new StringBuffer("b"), 2, 1));
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new CharSequenceUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = CharSequenceUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(CharSequenceUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(CharSequenceUtils.class.getModifiers()));
    }

    @Test
    public void testSubSequence_1() {
        assertNull(CharSequenceUtils.subSequence(null, -1));
    }

    @Test
    public void testSubSequence_4() {
        assertEquals(StringUtils.EMPTY, CharSequenceUtils.subSequence(StringUtils.EMPTY, 0));
    }

    @Test
    public void testSubSequence_8() {
        assertEquals(StringUtils.EMPTY, CharSequenceUtils.subSequence("012", 3));
    }

    @Test
    public void testToCharArray_1_testMerged_1() {
        final StringBuilder builder = new StringBuilder("abcdefg");
        final char[] expected = builder.toString().toCharArray();
        assertArrayEquals(expected, CharSequenceUtils.toCharArray(builder));
        assertArrayEquals(expected, CharSequenceUtils.toCharArray(builder.toString()));
    }

    @Test
    public void testToCharArray_3() {
        assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, CharSequenceUtils.toCharArray(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubSequence_2to3")
    public void testSubSequence_2to3(int param1) {
        assertNull(CharSequenceUtils.subSequence(param1, 0));
    }

    static public Stream<Arguments> Provider_testSubSequence_2to3() {
        return Stream.of(arguments(0), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubSequence_5to7")
    public void testSubSequence_5to7(int param1, int param2, int param3) {
        assertEquals(param1, CharSequenceUtils.subSequence(param2, param3));
    }

    static public Stream<Arguments> Provider_testSubSequence_5to7() {
        return Stream.of(arguments(012, 012, 0), arguments(12, 012, 1), arguments(2, 012, 2));
    }
}
