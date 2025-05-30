package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Comparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("boxing")
public class RangeTest_Parameterized extends AbstractLangTest {

    abstract static class AbstractComparable implements Comparable<AbstractComparable> {

        @Override
        public int compareTo(final AbstractComparable o) {
            return 0;
        }
    }

    static final class DerivedComparableA extends AbstractComparable {
    }

    static final class DerivedComparableB extends AbstractComparable {
    }

    private Range<Byte> byteRange;

    private Range<Byte> byteRange2;

    private Range<Byte> byteRange3;

    private Range<Double> doubleRange;

    private Range<Float> floatRange;

    private Range<Integer> intRange;

    private Range<Long> longRange;

    @BeforeEach
    public void setUp() {
        byteRange = Range.of((byte) 0, (byte) 5);
        byteRange2 = Range.of((byte) 0, (byte) 5);
        byteRange3 = Range.of((byte) 0, (byte) 10);
        intRange = Range.of(10, 20);
        longRange = Range.of(10L, 20L);
        floatRange = Range.of((float) 10, (float) 20);
        doubleRange = Range.of((double) 10, (double) 20);
    }

    @Test
    public void testContains_1() {
        assertFalse(intRange.contains(null));
    }

    @Test
    public void testContainsRange_1() {
        assertFalse(intRange.containsRange(null));
    }

    @Test
    public void testContainsRange_12() {
        assertFalse(intRange.containsRange(Range.between(-11, -18)));
    }

    @Test
    public void testEqualsObject_1() {
        assertEquals(byteRange, byteRange);
    }

    @Test
    public void testEqualsObject_2() {
        assertEquals(byteRange, byteRange2);
    }

    @Test
    public void testEqualsObject_3() {
        assertEquals(byteRange2, byteRange2);
    }

    @Test
    public void testEqualsObject_4() {
        assertEquals(byteRange, byteRange);
    }

    @Test
    public void testEqualsObject_5() {
        assertEquals(byteRange2, byteRange2);
    }

    @Test
    public void testEqualsObject_6() {
        assertEquals(byteRange3, byteRange3);
    }

    @Test
    public void testEqualsObject_7() {
        assertNotEquals(byteRange2, byteRange3);
    }

    @Test
    public void testEqualsObject_8() {
        assertNotEquals(null, byteRange2);
    }

    @Test
    public void testEqualsObject_9() {
        assertNotEquals("Ni!", byteRange2);
    }

    @Test
    public void testFit_1() {
        assertEquals(intRange.getMinimum(), intRange.fit(Integer.MIN_VALUE));
    }

    @Test
    public void testFit_2() {
        assertEquals(intRange.getMinimum(), intRange.fit(intRange.getMinimum()));
    }

    @Test
    public void testFit_3() {
        assertEquals(intRange.getMaximum(), intRange.fit(Integer.MAX_VALUE));
    }

    @Test
    public void testFit_4() {
        assertEquals(intRange.getMaximum(), intRange.fit(intRange.getMaximum()));
    }

    @Test
    public void testFit_5() {
        assertEquals(15, intRange.fit(15));
    }

    @Test
    public void testGetMaximum_1() {
        assertEquals(20, (int) intRange.getMaximum());
    }

    @Test
    public void testGetMaximum_2() {
        assertEquals(20L, (long) longRange.getMaximum());
    }

    @Test
    public void testGetMaximum_3() {
        assertEquals(20f, floatRange.getMaximum(), 0.00001f);
    }

    @Test
    public void testGetMaximum_4() {
        assertEquals(20d, doubleRange.getMaximum(), 0.00001d);
    }

    @Test
    public void testGetMinimum_1() {
        assertEquals(10, (int) intRange.getMinimum());
    }

    @Test
    public void testGetMinimum_2() {
        assertEquals(10L, (long) longRange.getMinimum());
    }

    @Test
    public void testGetMinimum_3() {
        assertEquals(10f, floatRange.getMinimum(), 0.00001f);
    }

    @Test
    public void testGetMinimum_4() {
        assertEquals(10d, doubleRange.getMinimum(), 0.00001d);
    }

    @Test
    public void testHashCode_1() {
        assertEquals(byteRange.hashCode(), byteRange2.hashCode());
    }

    @Test
    public void testHashCode_2() {
        assertNotEquals(byteRange.hashCode(), byteRange3.hashCode());
    }

    @Test
    public void testHashCode_3() {
        assertEquals(intRange.hashCode(), intRange.hashCode());
    }

    @Test
    public void testHashCode_4() {
        assertTrue(intRange.hashCode() != 0);
    }

    @Test
    public void testIntersectionWith_1() {
        assertSame(intRange, intRange.intersectionWith(intRange));
    }

    @Test
    public void testIntersectionWith_2() {
        assertSame(byteRange, byteRange.intersectionWith(byteRange));
    }

    @Test
    public void testIntersectionWith_3() {
        assertSame(longRange, longRange.intersectionWith(longRange));
    }

    @Test
    public void testIntersectionWith_4() {
        assertSame(floatRange, floatRange.intersectionWith(floatRange));
    }

    @Test
    public void testIntersectionWith_5() {
        assertSame(doubleRange, doubleRange.intersectionWith(doubleRange));
    }

    @Test
    public void testIntersectionWith_6() {
        assertEquals(Range.between(10, 15), intRange.intersectionWith(Range.between(5, 15)));
    }

    @Test
    public void testIsAfter_1() {
        assertFalse(intRange.isAfter(null));
    }

    @Test
    public void testIsAfter_2() {
        assertTrue(intRange.isAfter(5));
    }

    @Test
    public void testIsAfterRange_1() {
        assertFalse(intRange.isAfterRange(null));
    }

    @Test
    public void testIsAfterRange_2() {
        assertTrue(intRange.isAfterRange(Range.between(5, 9)));
    }

    @Test
    public void testIsBefore_1() {
        assertFalse(intRange.isBefore(null));
    }

    @Test
    public void testIsBefore_6() {
        assertTrue(intRange.isBefore(25));
    }

    @Test
    public void testIsBeforeRange_1() {
        assertFalse(intRange.isBeforeRange(null));
    }

    @Test
    public void testIsBeforeRange_7() {
        assertTrue(intRange.isBeforeRange(Range.between(21, 25)));
    }

    @Test
    public void testIsEndedBy_1() {
        assertFalse(intRange.isEndedBy(null));
    }

    @Test
    public void testIsEndedBy_5() {
        assertTrue(intRange.isEndedBy(20));
    }

    @Test
    public void testIsOverlappedBy_1() {
        assertFalse(intRange.isOverlappedBy(null));
    }

    @Test
    public void testIsOverlappedBy_12() {
        assertFalse(intRange.isOverlappedBy(Range.between(-11, -18)));
    }

    @Test
    public void testIsStartedBy_1() {
        assertFalse(intRange.isStartedBy(null));
    }

    @Test
    public void testIsStartedBy_3() {
        assertTrue(intRange.isStartedBy(10));
    }

    @Test
    public void testToString_1() {
        assertNotNull(byteRange.toString());
    }

    @Test
    public void testToString_2_testMerged_2() {
        final String str = intRange.toString();
        assertEquals("[10..20]", str);
        assertEquals("[-20..-10]", Range.between(-20, -10).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testContains_2_6")
    public void testContains_2_6(int param1) {
        assertFalse(intRange.contains(param1));
    }

    static public Stream<Arguments> Provider_testContains_2_6() {
        return Stream.of(arguments(5), arguments(25));
    }

    @ParameterizedTest
    @MethodSource("Provider_testContains_3to5")
    public void testContains_3to5(int param1) {
        assertTrue(intRange.contains(param1));
    }

    static public Stream<Arguments> Provider_testContains_3to5() {
        return Stream.of(arguments(10), arguments(15), arguments(20));
    }

    @ParameterizedTest
    @MethodSource("Provider_testContainsRange_2_5_8_10")
    public void testContainsRange_2_5_8_10(int param1, int param2) {
        assertTrue(intRange.containsRange(Range.between(param1, param2)));
    }

    static public Stream<Arguments> Provider_testContainsRange_2_5_8_10() {
        return Stream.of(arguments(12, 18), arguments(10, 20), arguments(10, 19), arguments(11, 20));
    }

    @ParameterizedTest
    @MethodSource("Provider_testContainsRange_3to4_6to7_9_11")
    public void testContainsRange_3to4_6to7_9_11(int param1, int param2) {
        assertFalse(intRange.containsRange(Range.between(param1, param2)));
    }

    static public Stream<Arguments> Provider_testContainsRange_3to4_6to7_9_11() {
        return Stream.of(arguments(32, 45), arguments(2, 8), arguments(9, 14), arguments(16, 21), arguments(10, 21), arguments(9, 20));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAfter_3to6")
    public void testIsAfter_3to6(int param1) {
        assertFalse(intRange.isAfter(param1));
    }

    static public Stream<Arguments> Provider_testIsAfter_3to6() {
        return Stream.of(arguments(10), arguments(15), arguments(20), arguments(25));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAfterRange_3to8")
    public void testIsAfterRange_3to8(int param1, int param2) {
        assertFalse(intRange.isAfterRange(Range.between(param1, param2)));
    }

    static public Stream<Arguments> Provider_testIsAfterRange_3to8() {
        return Stream.of(arguments(5, 10), arguments(5, 20), arguments(5, 25), arguments(15, 25), arguments(21, 25), arguments(10, 20));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsBefore_2to5")
    public void testIsBefore_2to5(int param1) {
        assertFalse(intRange.isBefore(param1));
    }

    static public Stream<Arguments> Provider_testIsBefore_2to5() {
        return Stream.of(arguments(5), arguments(10), arguments(15), arguments(20));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsBeforeRange_2to6_8")
    public void testIsBeforeRange_2to6_8(int param1, int param2) {
        assertFalse(intRange.isBeforeRange(Range.between(param1, param2)));
    }

    static public Stream<Arguments> Provider_testIsBeforeRange_2to6_8() {
        return Stream.of(arguments(5, 9), arguments(5, 10), arguments(5, 20), arguments(5, 25), arguments(15, 25), arguments(10, 20));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsEndedBy_2to4_6")
    public void testIsEndedBy_2to4_6(int param1) {
        assertFalse(intRange.isEndedBy(param1));
    }

    static public Stream<Arguments> Provider_testIsEndedBy_2to4_6() {
        return Stream.of(arguments(5), arguments(10), arguments(15), arguments(25));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsOverlappedBy_2_5to11_13")
    public void testIsOverlappedBy_2_5to11_13(int param1, int param2) {
        assertTrue(intRange.isOverlappedBy(Range.between(param1, param2)));
    }

    static public Stream<Arguments> Provider_testIsOverlappedBy_2_5to11_13() {
        return Stream.of(arguments(12, 18), arguments(10, 20), arguments(9, 14), arguments(16, 21), arguments(10, 19), arguments(10, 21), arguments(11, 20), arguments(9, 20), arguments(9, 21));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsOverlappedBy_3to4")
    public void testIsOverlappedBy_3to4(int param1, int param2) {
        assertFalse(intRange.isOverlappedBy(Range.between(param1, param2)));
    }

    static public Stream<Arguments> Provider_testIsOverlappedBy_3to4() {
        return Stream.of(arguments(32, 45), arguments(2, 8));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsStartedBy_2_4to6")
    public void testIsStartedBy_2_4to6(int param1) {
        assertFalse(intRange.isStartedBy(param1));
    }

    static public Stream<Arguments> Provider_testIsStartedBy_2_4to6() {
        return Stream.of(arguments(5), arguments(15), arguments(20), arguments(25));
    }
}
