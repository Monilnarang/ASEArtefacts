package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Comparator;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class LongRangeTest_Purified extends AbstractLangTest {

    private static LongRange of(final int min, final int max) {
        return LongRange.of(min, max);
    }

    private static LongRange of(final Long min, final Long max) {
        return LongRange.of(min, max);
    }

    private LongRange range1;

    private LongRange range2;

    private LongRange range3;

    private LongRange rangeFull;

    @BeforeEach
    public void setUp() {
        range1 = of(10, 20);
        range2 = of(10, 20);
        range3 = of(-2, -1);
        rangeFull = of(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Test
    public void testContainsInt_1() {
        assertFalse(range1.contains(null));
    }

    @Test
    public void testContainsInt_2() {
        assertFalse(range1.contains(5L));
    }

    @Test
    public void testContainsInt_3() {
        assertTrue(range1.contains(10L));
    }

    @Test
    public void testContainsInt_4() {
        assertTrue(range1.contains(15L));
    }

    @Test
    public void testContainsInt_5() {
        assertTrue(range1.contains(20L));
    }

    @Test
    public void testContainsInt_6() {
        assertFalse(range1.contains(25L));
    }

    @Test
    public void testContainsLong_1() {
        assertFalse(range1.contains(null));
    }

    @Test
    public void testContainsLong_2() {
        assertTrue(rangeFull.contains(Long.MAX_VALUE));
    }

    @Test
    public void testContainsLong_3() {
        assertTrue(rangeFull.contains(Long.MIN_VALUE));
    }

    @Test
    public void testContainsLong_4() {
        assertTrue(rangeFull.contains((long) Integer.MAX_VALUE + 1));
    }

    @Test
    public void testContainsLong_5() {
        assertTrue(rangeFull.contains((long) Integer.MIN_VALUE - 1));
    }

    @Test
    public void testContainsLong_6() {
        assertTrue(rangeFull.contains((long) Integer.MAX_VALUE));
    }

    @Test
    public void testContainsLong_7() {
        assertTrue(rangeFull.contains((long) Integer.MIN_VALUE));
    }

    @Test
    public void testContainsLong_8() {
        assertFalse(range1.contains(5L));
    }

    @Test
    public void testContainsLong_9() {
        assertTrue(range1.contains(10L));
    }

    @Test
    public void testContainsLong_10() {
        assertTrue(range1.contains(15L));
    }

    @Test
    public void testContainsLong_11() {
        assertTrue(range1.contains(20L));
    }

    @Test
    public void testContainsLong_12() {
        assertFalse(range1.contains(25L));
    }

    @Test
    public void testContainsRange_1() {
        assertFalse(range1.containsRange(null));
    }

    @Test
    public void testContainsRange_2() {
        assertTrue(range1.containsRange(Range.of(12L, 18L)));
    }

    @Test
    public void testContainsRange_3() {
        assertTrue(range1.containsRange(of(12, 18)));
    }

    @Test
    public void testContainsRange_4() {
        assertFalse(range1.containsRange(Range.of(32L, 45L)));
    }

    @Test
    public void testContainsRange_5() {
        assertFalse(range1.containsRange(of(32, 45)));
    }

    @Test
    public void testContainsRange_6() {
        assertFalse(range1.containsRange(Range.of(2L, 8L)));
    }

    @Test
    public void testContainsRange_7() {
        assertFalse(range1.containsRange(of(2, 8)));
    }

    @Test
    public void testContainsRange_8() {
        assertTrue(range1.containsRange(Range.of(10L, 20L)));
    }

    @Test
    public void testContainsRange_9() {
        assertTrue(range1.containsRange(of(10, 20)));
    }

    @Test
    public void testContainsRange_10() {
        assertFalse(range1.containsRange(Range.of(9L, 14L)));
    }

    @Test
    public void testContainsRange_11() {
        assertFalse(range1.containsRange(of(9, 14)));
    }

    @Test
    public void testContainsRange_12() {
        assertFalse(range1.containsRange(Range.of(16L, 21L)));
    }

    @Test
    public void testContainsRange_13() {
        assertFalse(range1.containsRange(of(16, 21)));
    }

    @Test
    public void testContainsRange_14() {
        assertTrue(range1.containsRange(Range.of(10L, 19L)));
    }

    @Test
    public void testContainsRange_15() {
        assertTrue(range1.containsRange(of(10, 19)));
    }

    @Test
    public void testContainsRange_16() {
        assertFalse(range1.containsRange(Range.of(10L, 21L)));
    }

    @Test
    public void testContainsRange_17() {
        assertFalse(range1.containsRange(of(10, 21)));
    }

    @Test
    public void testContainsRange_18() {
        assertTrue(range1.containsRange(Range.of(11L, 20L)));
    }

    @Test
    public void testContainsRange_19() {
        assertTrue(range1.containsRange(of(11, 20)));
    }

    @Test
    public void testContainsRange_20() {
        assertFalse(range1.containsRange(Range.of(9L, 20L)));
    }

    @Test
    public void testContainsRange_21() {
        assertFalse(range1.containsRange(of(9, 20)));
    }

    @Test
    public void testContainsRange_22() {
        assertFalse(range1.containsRange(Range.of(-11L, -18L)));
    }

    @Test
    public void testContainsRange_23() {
        assertFalse(range1.containsRange(of(-11, -18)));
    }

    @Test
    public void testEqualsObject_1() {
        assertEquals(range1, range1);
    }

    @Test
    public void testEqualsObject_2() {
        assertEquals(range1, range2);
    }

    @Test
    public void testEqualsObject_3() {
        assertEquals(range2, range2);
    }

    @Test
    public void testEqualsObject_4() {
        assertEquals(range1, range1);
    }

    @Test
    public void testEqualsObject_5() {
        assertEquals(range2, range2);
    }

    @Test
    public void testEqualsObject_6() {
        assertEquals(range3, range3);
    }

    @Test
    public void testEqualsObject_7() {
        assertNotEquals(range2, range3);
    }

    @Test
    public void testEqualsObject_8() {
        assertNotEquals(null, range2);
    }

    @Test
    public void testEqualsObject_9() {
        assertNotEquals("Ni!", range2);
    }

    @Test
    public void testFit_1() {
        assertEquals(range1.getMinimum(), range1.fit((long) Integer.MIN_VALUE));
    }

    @Test
    public void testFit_2() {
        assertEquals(range1.getMinimum(), range1.fit(range1.getMinimum()));
    }

    @Test
    public void testFit_3() {
        assertEquals(range1.getMaximum(), range1.fit((long) Integer.MAX_VALUE));
    }

    @Test
    public void testFit_4() {
        assertEquals(range1.getMaximum(), range1.fit(range1.getMaximum()));
    }

    @Test
    public void testFit_5() {
        assertEquals(15, range1.fit(15L));
    }

    @Test
    public void testHashCode_1() {
        assertEquals(range1.hashCode(), range2.hashCode());
    }

    @Test
    public void testHashCode_2() {
        assertNotEquals(range1.hashCode(), range3.hashCode());
    }

    @Test
    public void testHashCode_3() {
        assertEquals(range1.hashCode(), range1.hashCode());
    }

    @Test
    public void testHashCode_4() {
        assertTrue(range1.hashCode() != 0);
    }

    @Test
    public void testIntersectionWith_1() {
        assertSame(range1, range1.intersectionWith(range1));
    }

    @Test
    public void testIntersectionWith_2() {
        assertEquals(Range.of(10L, 15L), range1.intersectionWith(Range.of(5L, 15L)));
    }

    @Test
    public void testIsAfter_1() {
        assertFalse(range1.isAfter(null));
    }

    @Test
    public void testIsAfter_2() {
        assertTrue(range1.isAfter(5L));
    }

    @Test
    public void testIsAfter_3() {
        assertFalse(range1.isAfter(10L));
    }

    @Test
    public void testIsAfter_4() {
        assertFalse(range1.isAfter(15L));
    }

    @Test
    public void testIsAfter_5() {
        assertFalse(range1.isAfter(20L));
    }

    @Test
    public void testIsAfter_6() {
        assertFalse(range1.isAfter(25L));
    }

    @Test
    public void testIsAfterRange_1() {
        assertFalse(range1.isAfterRange(null));
    }

    @Test
    public void testIsAfterRange_2() {
        assertTrue(range1.isAfterRange(Range.of(5L, 9L)));
    }

    @Test
    public void testIsAfterRange_3() {
        assertFalse(range1.isAfterRange(Range.of(5L, 10L)));
    }

    @Test
    public void testIsAfterRange_4() {
        assertFalse(range1.isAfterRange(Range.of(5L, 20L)));
    }

    @Test
    public void testIsAfterRange_5() {
        assertFalse(range1.isAfterRange(Range.of(5L, 25L)));
    }

    @Test
    public void testIsAfterRange_6() {
        assertFalse(range1.isAfterRange(Range.of(15L, 25L)));
    }

    @Test
    public void testIsAfterRange_7() {
        assertFalse(range1.isAfterRange(Range.of(21L, 25L)));
    }

    @Test
    public void testIsAfterRange_8() {
        assertFalse(range1.isAfterRange(Range.of(10L, 20L)));
    }

    @Test
    public void testIsBefore_1() {
        assertFalse(range1.isBefore(null));
    }

    @Test
    public void testIsBefore_2() {
        assertFalse(range1.isBefore(5L));
    }

    @Test
    public void testIsBefore_3() {
        assertFalse(range1.isBefore(10L));
    }

    @Test
    public void testIsBefore_4() {
        assertFalse(range1.isBefore(15L));
    }

    @Test
    public void testIsBefore_5() {
        assertFalse(range1.isBefore(20L));
    }

    @Test
    public void testIsBefore_6() {
        assertTrue(range1.isBefore(25L));
    }

    @Test
    public void testIsBeforeIntegerRange_1() {
        assertFalse(range1.isBeforeRange(null));
    }

    @Test
    public void testIsBeforeIntegerRange_2() {
        assertFalse(range1.isBeforeRange(of(5, 9)));
    }

    @Test
    public void testIsBeforeIntegerRange_3() {
        assertFalse(range1.isBeforeRange(of(5, 10)));
    }

    @Test
    public void testIsBeforeIntegerRange_4() {
        assertFalse(range1.isBeforeRange(of(5, 20)));
    }

    @Test
    public void testIsBeforeIntegerRange_5() {
        assertFalse(range1.isBeforeRange(of(5, 25)));
    }

    @Test
    public void testIsBeforeIntegerRange_6() {
        assertFalse(range1.isBeforeRange(of(15, 25)));
    }

    @Test
    public void testIsBeforeIntegerRange_7() {
        assertTrue(range1.isBeforeRange(of(21, 25)));
    }

    @Test
    public void testIsBeforeIntegerRange_8() {
        assertFalse(range1.isBeforeRange(of(10, 20)));
    }

    @Test
    public void testIsBeforeRange_1() {
        assertFalse(range1.isBeforeRange(null));
    }

    @Test
    public void testIsBeforeRange_2() {
        assertFalse(range1.isBeforeRange(Range.of(5L, 9L)));
    }

    @Test
    public void testIsBeforeRange_3() {
        assertFalse(range1.isBeforeRange(Range.of(5L, 10L)));
    }

    @Test
    public void testIsBeforeRange_4() {
        assertFalse(range1.isBeforeRange(Range.of(5L, 20L)));
    }

    @Test
    public void testIsBeforeRange_5() {
        assertFalse(range1.isBeforeRange(Range.of(5L, 25L)));
    }

    @Test
    public void testIsBeforeRange_6() {
        assertFalse(range1.isBeforeRange(Range.of(15L, 25L)));
    }

    @Test
    public void testIsBeforeRange_7() {
        assertTrue(range1.isBeforeRange(Range.of(21L, 25L)));
    }

    @Test
    public void testIsBeforeRange_8() {
        assertFalse(range1.isBeforeRange(Range.of(10L, 20L)));
    }

    @Test
    public void testIsEndedBy_1() {
        assertFalse(range1.isEndedBy(null));
    }

    @Test
    public void testIsEndedBy_2() {
        assertFalse(range1.isEndedBy(5L));
    }

    @Test
    public void testIsEndedBy_3() {
        assertFalse(range1.isEndedBy(10L));
    }

    @Test
    public void testIsEndedBy_4() {
        assertFalse(range1.isEndedBy(15L));
    }

    @Test
    public void testIsEndedBy_5() {
        assertTrue(range1.isEndedBy(20L));
    }

    @Test
    public void testIsEndedBy_6() {
        assertFalse(range1.isEndedBy(25L));
    }

    @Test
    public void testIsOverlappedByIntegerRange_1() {
        assertFalse(range1.isOverlappedBy(null));
    }

    @Test
    public void testIsOverlappedByIntegerRange_2() {
        assertTrue(range1.isOverlappedBy(of(12, 18)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_3() {
        assertFalse(range1.isOverlappedBy(of(32, 45)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_4() {
        assertFalse(range1.isOverlappedBy(of(2, 8)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_5() {
        assertTrue(range1.isOverlappedBy(of(10, 20)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_6() {
        assertTrue(range1.isOverlappedBy(of(9, 14)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_7() {
        assertTrue(range1.isOverlappedBy(of(16, 21)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_8() {
        assertTrue(range1.isOverlappedBy(of(10, 19)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_9() {
        assertTrue(range1.isOverlappedBy(of(10, 21)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_10() {
        assertTrue(range1.isOverlappedBy(of(11, 20)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_11() {
        assertTrue(range1.isOverlappedBy(of(9, 20)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_12() {
        assertFalse(range1.isOverlappedBy(of(-11, -18)));
    }

    @Test
    public void testIsOverlappedByIntegerRange_13() {
        assertTrue(range1.isOverlappedBy(of(9, 21)));
    }

    @Test
    public void testIsOverlappedByRange_1() {
        assertFalse(range1.isOverlappedBy(null));
    }

    @Test
    public void testIsOverlappedByRange_2() {
        assertTrue(range1.isOverlappedBy(Range.of(12L, 18L)));
    }

    @Test
    public void testIsOverlappedByRange_3() {
        assertFalse(range1.isOverlappedBy(Range.of(32L, 45L)));
    }

    @Test
    public void testIsOverlappedByRange_4() {
        assertFalse(range1.isOverlappedBy(Range.of(2L, 8L)));
    }

    @Test
    public void testIsOverlappedByRange_5() {
        assertTrue(range1.isOverlappedBy(Range.of(10L, 20L)));
    }

    @Test
    public void testIsOverlappedByRange_6() {
        assertTrue(range1.isOverlappedBy(Range.of(9L, 14L)));
    }

    @Test
    public void testIsOverlappedByRange_7() {
        assertTrue(range1.isOverlappedBy(Range.of(16L, 21L)));
    }

    @Test
    public void testIsOverlappedByRange_8() {
        assertTrue(range1.isOverlappedBy(Range.of(10L, 19L)));
    }

    @Test
    public void testIsOverlappedByRange_9() {
        assertTrue(range1.isOverlappedBy(Range.of(10L, 21L)));
    }

    @Test
    public void testIsOverlappedByRange_10() {
        assertTrue(range1.isOverlappedBy(Range.of(11L, 20L)));
    }

    @Test
    public void testIsOverlappedByRange_11() {
        assertTrue(range1.isOverlappedBy(Range.of(9L, 20L)));
    }

    @Test
    public void testIsOverlappedByRange_12() {
        assertFalse(range1.isOverlappedBy(Range.of(-11L, -18L)));
    }

    @Test
    public void testIsOverlappedByRange_13() {
        assertTrue(range1.isOverlappedBy(Range.of(9L, 21L)));
    }

    @Test
    public void testIsStartedBy_1() {
        assertFalse(range1.isStartedBy(null));
    }

    @Test
    public void testIsStartedBy_2() {
        assertFalse(range1.isStartedBy(5L));
    }

    @Test
    public void testIsStartedBy_3() {
        assertTrue(range1.isStartedBy(10L));
    }

    @Test
    public void testIsStartedBy_4() {
        assertFalse(range1.isStartedBy(15L));
    }

    @Test
    public void testIsStartedBy_5() {
        assertFalse(range1.isStartedBy(20L));
    }

    @Test
    public void testIsStartedBy_6() {
        assertFalse(range1.isStartedBy(25L));
    }

    @Test
    public void testToString_1() {
        assertNotNull(range1.toString());
    }

    @Test
    public void testToString_2() {
        final String str = range1.toString();
        assertEquals("[10..20]", str);
    }

    @Test
    public void testToString_3() {
        assertEquals("[-20..-10]", Range.of(-20, -10).toString());
    }
}
