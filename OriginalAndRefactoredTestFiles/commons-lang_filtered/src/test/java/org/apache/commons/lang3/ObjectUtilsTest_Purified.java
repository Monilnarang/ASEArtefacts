package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import org.apache.commons.lang3.exception.CloneFailedException;
import org.apache.commons.lang3.function.Suppliers;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.text.StrBuilder;
import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation")
public class ObjectUtilsTest_Purified extends AbstractLangTest {

    static final class CharSequenceComparator implements Comparator<CharSequence> {

        @Override
        public int compare(final CharSequence o1, final CharSequence o2) {
            return o1.toString().compareTo(o2.toString());
        }
    }

    static final class CloneableString extends MutableObject<String> implements Cloneable {

        private static final long serialVersionUID = 1L;

        CloneableString(final String s) {
            super(s);
        }

        @Override
        public CloneableString clone() throws CloneNotSupportedException {
            return (CloneableString) super.clone();
        }
    }

    static final class NonComparableCharSequence implements CharSequence {

        final String value;

        NonComparableCharSequence(final String value) {
            Validate.notNull(value);
            this.value = value;
        }

        @Override
        public char charAt(final int arg0) {
            return value.charAt(arg0);
        }

        @Override
        public int length() {
            return value.length();
        }

        @Override
        public CharSequence subSequence(final int arg0, final int arg1) {
            return value.subSequence(arg0, arg1);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    static final class UncloneableString extends MutableObject<String> implements Cloneable {

        private static final long serialVersionUID = 1L;

        UncloneableString(final String s) {
            super(s);
        }
    }

    private static final Supplier<?> NULL_SUPPLIER = null;

    private static final String FOO = "foo";

    private static final String BAR = "bar";

    private static final String[] NON_EMPTY_ARRAY = { FOO, BAR };

    private static final List<String> NON_EMPTY_LIST = Arrays.asList(NON_EMPTY_ARRAY);

    private static final Set<String> NON_EMPTY_SET = new HashSet<>(NON_EMPTY_LIST);

    private static final Map<String, String> NON_EMPTY_MAP = new HashMap<>();

    static {
        NON_EMPTY_MAP.put(FOO, BAR);
    }

    @Test
    public void testAllNull_1() {
        assertTrue(ObjectUtils.allNull());
    }

    @Test
    public void testAllNull_2() {
        assertTrue(ObjectUtils.allNull((Object) null));
    }

    @Test
    public void testAllNull_3() {
        assertTrue(ObjectUtils.allNull((Object[]) null));
    }

    @Test
    public void testAllNull_4() {
        assertTrue(ObjectUtils.allNull(null, null, null));
    }

    @Test
    public void testAllNull_5() {
        assertFalse(ObjectUtils.allNull(FOO));
    }

    @Test
    public void testAllNull_6() {
        assertFalse(ObjectUtils.allNull(null, FOO, null));
    }

    @Test
    public void testAllNull_7() {
        assertFalse(ObjectUtils.allNull(null, null, null, null, FOO, BAR));
    }

    @Test
    public void testAnyNotNull_1() {
        assertFalse(ObjectUtils.anyNotNull());
    }

    @Test
    public void testAnyNotNull_2() {
        assertFalse(ObjectUtils.anyNotNull((Object) null));
    }

    @Test
    public void testAnyNotNull_3() {
        assertFalse(ObjectUtils.anyNotNull((Object[]) null));
    }

    @Test
    public void testAnyNotNull_4() {
        assertFalse(ObjectUtils.anyNotNull(null, null, null));
    }

    @Test
    public void testAnyNotNull_5() {
        assertTrue(ObjectUtils.anyNotNull(FOO));
    }

    @Test
    public void testAnyNotNull_6() {
        assertTrue(ObjectUtils.anyNotNull(null, FOO, null));
    }

    @Test
    public void testAnyNotNull_7() {
        assertTrue(ObjectUtils.anyNotNull(null, null, null, null, FOO, BAR));
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new ObjectUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = ObjectUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(ObjectUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(ObjectUtils.class.getModifiers()));
    }

    @Test
    public void testEquals_1() {
        assertTrue(ObjectUtils.equals(null, null), "ObjectUtils.equals(null, null) returned false");
    }

    @Test
    public void testEquals_2() {
        assertFalse(ObjectUtils.equals(FOO, null), "ObjectUtils.equals(\"foo\", null) returned true");
    }

    @Test
    public void testEquals_3() {
        assertFalse(ObjectUtils.equals(null, BAR), "ObjectUtils.equals(null, \"bar\") returned true");
    }

    @Test
    public void testEquals_4() {
        assertFalse(ObjectUtils.equals(FOO, BAR), "ObjectUtils.equals(\"foo\", \"bar\") returned true");
    }

    @Test
    public void testEquals_5() {
        assertTrue(ObjectUtils.equals(FOO, FOO), "ObjectUtils.equals(\"foo\", \"foo\") returned false");
    }

    @Test
    public void testFirstNonNull_1() {
        assertEquals("", ObjectUtils.firstNonNull(null, ""));
    }

    @Test
    public void testFirstNonNull_2_testMerged_2() {
        final String firstNonNullGenerics = ObjectUtils.firstNonNull(null, null, "123", "456");
        assertEquals("123", firstNonNullGenerics);
        assertEquals("123", ObjectUtils.firstNonNull("123", null, "456", null));
        assertSame(Boolean.TRUE, ObjectUtils.firstNonNull(Boolean.TRUE));
        assertNull(ObjectUtils.firstNonNull());
        assertNull(ObjectUtils.firstNonNull(null, null));
        assertNull(ObjectUtils.firstNonNull((Object) null));
        assertNull(ObjectUtils.firstNonNull((Object[]) null));
    }

    @Test
    public void testHashCode_1() {
        assertEquals(0, ObjectUtils.hashCode(null));
    }

    @Test
    public void testHashCode_2() {
        assertEquals("a".hashCode(), ObjectUtils.hashCode("a"));
    }

    @Test
    public void testMax_1_testMerged_1() {
        final Calendar calendar = Calendar.getInstance();
        final Date nonNullComparable1 = calendar.getTime();
        final Date nonNullComparable2 = calendar.getTime();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        final Date minComparable = calendar.getTime();
        assertNotSame(nonNullComparable1, nonNullComparable2);
        assertSame(nonNullComparable1, ObjectUtils.max(null, nonNullComparable1));
        assertSame(nonNullComparable1, ObjectUtils.max(nonNullComparable1, null));
        assertSame(nonNullComparable1, ObjectUtils.max(null, nonNullComparable1, null));
        assertSame(nonNullComparable1, ObjectUtils.max(nonNullComparable1, nonNullComparable2));
        assertSame(nonNullComparable2, ObjectUtils.max(nonNullComparable2, nonNullComparable1));
        assertSame(nonNullComparable1, ObjectUtils.max(nonNullComparable1, minComparable));
        assertSame(nonNullComparable1, ObjectUtils.max(minComparable, nonNullComparable1));
        assertSame(nonNullComparable1, ObjectUtils.max(null, minComparable, null, nonNullComparable1));
    }

    @Test
    public void testMax_2() {
        assertNull(ObjectUtils.max((String) null));
    }

    @Test
    public void testMax_3() {
        final String[] nullArray = null;
        assertNull(ObjectUtils.max(nullArray));
    }

    @Test
    public void testMax_12() {
        assertNull(ObjectUtils.max(null, null));
    }

    @Test
    public void testMedian_1() {
        assertEquals("foo", ObjectUtils.median("foo"));
    }

    @Test
    public void testMedian_2() {
        assertEquals("bar", ObjectUtils.median("foo", "bar"));
    }

    @Test
    public void testMedian_3() {
        assertEquals("baz", ObjectUtils.median("foo", "bar", "baz"));
    }

    @Test
    public void testMedian_4() {
        assertEquals("baz", ObjectUtils.median("foo", "bar", "baz", "blah"));
    }

    @Test
    public void testMedian_5() {
        assertEquals("blah", ObjectUtils.median("foo", "bar", "baz", "blah", "wah"));
    }

    @Test
    public void testMedian_6() {
        assertEquals(Integer.valueOf(5), ObjectUtils.median(Integer.valueOf(1), Integer.valueOf(5), Integer.valueOf(10)));
    }

    @Test
    public void testMedian_7() {
        assertEquals(Integer.valueOf(7), ObjectUtils.median(Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9)));
    }

    @Test
    public void testMedian_8() {
        assertEquals(Integer.valueOf(6), ObjectUtils.median(Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8)));
    }

    @Test
    public void testMin_1_testMerged_1() {
        final Calendar calendar = Calendar.getInstance();
        final Date nonNullComparable1 = calendar.getTime();
        final Date nonNullComparable2 = calendar.getTime();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        final Date minComparable = calendar.getTime();
        assertNotSame(nonNullComparable1, nonNullComparable2);
        assertSame(nonNullComparable1, ObjectUtils.min(null, nonNullComparable1));
        assertSame(nonNullComparable1, ObjectUtils.min(nonNullComparable1, null));
        assertSame(nonNullComparable1, ObjectUtils.min(null, nonNullComparable1, null));
        assertSame(nonNullComparable1, ObjectUtils.min(nonNullComparable1, nonNullComparable2));
        assertSame(nonNullComparable2, ObjectUtils.min(nonNullComparable2, nonNullComparable1));
        assertSame(minComparable, ObjectUtils.min(nonNullComparable1, minComparable));
        assertSame(minComparable, ObjectUtils.min(minComparable, nonNullComparable1));
        assertSame(minComparable, ObjectUtils.min(null, nonNullComparable1, null, minComparable));
    }

    @Test
    public void testMin_2() {
        assertNull(ObjectUtils.min((String) null));
    }

    @Test
    public void testMin_3() {
        final String[] nullArray = null;
        assertNull(ObjectUtils.min(nullArray));
    }

    @Test
    public void testMin_12() {
        assertNull(ObjectUtils.min(null, null));
    }

    @Test
    public void testMode_1() {
        assertNull(ObjectUtils.mode((Object[]) null));
    }

    @Test
    public void testMode_2() {
        assertNull(ObjectUtils.mode());
    }

    @Test
    public void testMode_3() {
        assertNull(ObjectUtils.mode("foo", "bar", "baz"));
    }

    @Test
    public void testMode_4() {
        assertNull(ObjectUtils.mode("foo", "bar", "baz", "foo", "bar"));
    }

    @Test
    public void testMode_5() {
        assertEquals("foo", ObjectUtils.mode("foo", "bar", "baz", "foo"));
    }

    @Test
    public void testMode_6() {
        assertEquals(Integer.valueOf(9), ObjectUtils.mode("foo", "bar", "baz", Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(9)));
    }

    @Test
    public void testNotEqual_1() {
        assertFalse(ObjectUtils.notEqual(null, null), "ObjectUtils.notEqual(null, null) returned false");
    }

    @Test
    public void testNotEqual_2() {
        assertTrue(ObjectUtils.notEqual(FOO, null), "ObjectUtils.notEqual(\"foo\", null) returned true");
    }

    @Test
    public void testNotEqual_3() {
        assertTrue(ObjectUtils.notEqual(null, BAR), "ObjectUtils.notEqual(null, \"bar\") returned true");
    }

    @Test
    public void testNotEqual_4() {
        assertTrue(ObjectUtils.notEqual(FOO, BAR), "ObjectUtils.notEqual(\"foo\", \"bar\") returned true");
    }

    @Test
    public void testNotEqual_5() {
        assertFalse(ObjectUtils.notEqual(FOO, FOO), "ObjectUtils.notEqual(\"foo\", \"foo\") returned false");
    }

    @SuppressWarnings("cast")
    @Test
    public void testNull_1() {
        assertNotNull(ObjectUtils.NULL);
    }

    @SuppressWarnings("cast")
    @Test
    public void testNull_2() {
        assertInstanceOf(ObjectUtils.Null.class, ObjectUtils.NULL);
    }

    @SuppressWarnings("cast")
    @Test
    public void testNull_3() {
        assertSame(ObjectUtils.NULL, SerializationUtils.clone(ObjectUtils.NULL));
    }

    @Test
    public void testToString_Object_1() {
        assertEquals("", ObjectUtils.toString(null));
    }

    @Test
    public void testToString_Object_2() {
        assertEquals(Boolean.TRUE.toString(), ObjectUtils.toString(Boolean.TRUE));
    }

    @Test
    public void testToString_Object_String_1() {
        assertEquals(BAR, ObjectUtils.toString(null, BAR));
    }

    @Test
    public void testToString_Object_String_2() {
        assertEquals(Boolean.TRUE.toString(), ObjectUtils.toString(Boolean.TRUE, BAR));
    }
}
