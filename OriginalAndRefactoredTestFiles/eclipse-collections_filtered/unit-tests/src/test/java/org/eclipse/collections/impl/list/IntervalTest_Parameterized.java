package org.eclipse.collections.impl.list;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.ThrowingAppendable;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.math.MutableInteger;
import org.eclipse.collections.impl.math.MutableLong;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class IntervalTest_Parameterized {

    private void compareRangeAndInterval(int start, int end) {
        ImmutableList<Integer> rangeList = Lists.immutable.fromStream(IntStream.range(start, end).boxed());
        ImmutableList<Integer> intervalList = Lists.immutable.fromStream(Interval.fromToExclusive(start, end).stream());
        Verify.assertEqualsAndHashCode(rangeList, intervalList);
    }

    private void assertIntervalContainsAll(Interval interval, int[] expectedValues) {
        for (int value : expectedValues) {
            Verify.assertContains(value, interval);
        }
    }

    private void denyIntervalContainsAny(Interval interval, int[] expectedValues) {
        for (int value : expectedValues) {
            Verify.assertNotContains(value, interval);
        }
    }

    private static final class AddParametersProcedure implements ObjectIntProcedure<Integer> {

        private final MutableList<Integer> forwardResult;

        private AddParametersProcedure(MutableList<Integer> forwardResult) {
            this.forwardResult = forwardResult;
        }

        @Override
        public void value(Integer each, int index) {
            this.forwardResult.add(each + index);
        }
    }

    @Test
    public void size_14() {
        Verify.assertSize(1, Interval.zero());
    }

    @Test
    public void size_17() {
        Verify.assertSize(3, Interval.evensFromTo(2, -2));
    }

    @Test
    public void size_18() {
        Verify.assertSize(2, Interval.oddsFromTo(2, -2));
    }

    @Test
    public void size_19() {
        Verify.assertSize(10, Interval.zeroTo(9));
    }

    @Test
    public void size_20() {
        Verify.assertSize(2_000_000_000, Interval.oneTo(2_000_000_000));
    }

    @Test
    public void size_22() {
        Verify.assertSize(2_000_000_000, Interval.fromTo(2_000_000_000, 1).by(-1));
    }

    @Test
    public void size_23() {
        Verify.assertSize(2_000_000_000, Interval.fromToExclusive(2_000_000_000, 0).by(-1));
    }

    @Test
    public void size_26() {
        Verify.assertSize(10, Interval.zeroTo(-9));
    }

    @Test
    public void size_27() {
        Verify.assertSize(11, Interval.oneTo(-9));
    }

    @Test
    public void size_29() {
        Verify.assertSize(2_000_000_000, Interval.fromTo(-1, -2_000_000_000));
    }

    @Test
    public void size_31() {
        Verify.assertSize(2_000_000_000, Interval.fromTo(-2_000_000_000, -1).by(1));
    }

    @Test
    public void size_35() {
        Verify.assertSize(2_000_000_000, Interval.fromToExclusive(-1, -2_000_000_001));
    }

    @Test
    public void size_42() {
        Verify.assertSize(5, Interval.fromTo(-10, 10).by(5));
    }

    @Test
    public void size_51() {
        Verify.assertSize(1, Interval.fromToBy(-1_000_000_000, -2_000_000_000, -1_500_000_000));
    }

    @Test
    public void contains_1() {
        Verify.assertContains(0, Interval.zero());
    }

    @Test
    public void contains_2() {
        assertTrue(Interval.oneTo(5).containsAll(1, 5));
    }

    @Test
    public void contains_3() {
        assertTrue(Interval.oneTo(5).containsNone(6, 7));
    }

    @Test
    public void contains_4() {
        assertFalse(Interval.oneTo(5).containsAll(1, 6));
    }

    @Test
    public void contains_5() {
        assertFalse(Interval.oneTo(5).containsNone(1, 6));
    }

    @Test
    public void contains_6() {
        Verify.assertNotContains(0, Interval.oneTo(5));
    }

    @Test
    public void contains_7() {
        assertTrue(Interval.fromTo(-1, -5).containsAll(-1, -5));
    }

    @Test
    public void contains_8() {
        assertTrue(Interval.fromToExclusive(-1, -5).containsAll(-1, -4));
    }

    @Test
    public void contains_9() {
        Verify.assertNotContains(-5, Interval.fromToExclusive(-1, -5));
    }

    @Test
    public void contains_10() {
        Verify.assertNotContains(0, Interval.fromToExclusive(-1, -5));
    }

    @Test
    public void contains_11() {
        Verify.assertNotContains(1, Interval.fromTo(-1, -5));
    }

    @Test
    public void contains_12() {
        Verify.assertContains(Integer.valueOf(0), Interval.zero());
    }

    @Test
    public void contains_13() {
        Verify.assertNotContains(Integer.valueOf(0), Interval.oneTo(5));
    }

    @Test
    public void contains_14() {
        Verify.assertNotContains(Integer.valueOf(1), Interval.fromTo(-1, -5));
    }

    @Test
    public void contains_15() {
        Verify.assertNotContains(Integer.valueOf(1), Interval.fromToExclusive(-1, -5));
    }

    @Test
    public void contains_16() {
        Verify.assertNotContains(new Object(), Interval.zeroTo(5));
    }

    @Test
    public void contains_17_testMerged_17() {
        Interval bigInterval = Interval.fromToBy(Integer.MIN_VALUE, Integer.MAX_VALUE, 1_000_000);
        assertTrue(bigInterval.contains(Integer.MIN_VALUE + 1_000_000));
        assertFalse(bigInterval.contains(Integer.MIN_VALUE + 1_000_001));
        assertTrue(bigInterval.contains(Integer.MIN_VALUE + (1_000_000 * 10)));
        assertFalse(bigInterval.contains(Integer.MIN_VALUE + (1_000_001 * 10)));
        assertTrue(bigInterval.contains(Integer.MIN_VALUE + (1_000_000 * 100)));
        assertFalse(bigInterval.contains(Integer.MIN_VALUE + (1_000_001 * 100)));
        assertTrue(Interval.fromToBy(1_000_000_000, 2_000_000_000, 1_500_000_000).contains(1_000_000_000));
        assertTrue(Interval.fromToBy(-1_000_000_000, -2_000_000_000, -1_500_000_000).contains(-1_000_000_000));
        int minValue = -1_000_000_000;
        int maxValue = 1_000_000_000;
        Interval largeInterval = Interval.fromToBy(minValue, maxValue, 10);
        assertTrue(largeInterval.containsAll(maxValue - 10, maxValue - 100, maxValue - 1000, maxValue - 10000));
        assertTrue(largeInterval.contains(minValue + 10));
    }

    @Test
    public void product_1() {
        assertEquals(0, Interval.zero().product().intValue());
    }

    @Test
    public void product_2() {
        assertEquals(0, Interval.fromTo(-1, 1).product().intValue());
    }

    @Test
    public void product_3() {
        assertEquals(2, Interval.fromTo(-2, -1).product().intValue());
    }

    @Test
    public void product_4() {
        assertEquals(-6, Interval.fromTo(-3, -1).product().intValue());
    }

    @Test
    public void product_5() {
        assertEquals(0, Interval.fromToExclusive(-1, 1).product().intValue());
    }

    @Test
    public void product_6() {
        assertEquals(-2, Interval.fromToExclusive(-2, -1).product().intValue());
    }

    @Test
    public void product_7() {
        assertEquals(6, Interval.fromToExclusive(-3, -1).product().intValue());
    }

    @Test
    public void product_8() {
        assertEquals(200, Interval.fromToBy(10, 20, 10).product().intValue());
    }

    @Test
    public void product_9() {
        assertEquals(200, Interval.fromToBy(-10, -20, -10).product().intValue());
    }

    @Test
    public void product_10() {
        assertEquals(-6000, Interval.fromToBy(-10, -30, -10).product().intValue());
    }

    @Test
    public void product_11() {
        assertEquals(6000, Interval.fromToBy(30, 10, -10).product().intValue());
    }

    @Test
    public void product_12() {
        assertEquals(6000, Interval.fromToBy(30, 10, -10).reverseThis().product().intValue());
    }

    @Test
    public void getFirst_1() {
        assertEquals(Integer.valueOf(10), Interval.fromTo(10, -10).by(-5).getFirst());
    }

    @Test
    public void getFirst_2() {
        assertEquals(Integer.valueOf(-10), Interval.fromTo(-10, 10).by(5).getFirst());
    }

    @Test
    public void getFirst_3() {
        assertEquals(Integer.valueOf(0), Interval.zero().getFirst());
    }

    @Test
    public void getLast_5() {
        assertEquals(Integer.valueOf(0), Interval.zero().getLast());
    }

    @Test
    public void containsAll_1() {
        assertTrue(Interval.fromTo(1, 3).containsAll(FastList.newListWith(1, 2, 3)));
    }

    @Test
    public void containsAll_2() {
        assertFalse(Interval.fromTo(1, 3).containsAll(FastList.newListWith(1, 2, 4)));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_1_12")
    public void size_1_12(int param1, int param2, int param3) {
        Verify.assertSize(param1, Interval.fromTo(param2, param3));
    }

    static public Stream<Arguments> Provider_size_1_12() {
        return Stream.of(arguments(100, 1, 100), arguments(11, 0, 10));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_2_13")
    public void size_2_13(int param1, int param2, int param3) {
        Verify.assertSize(param1, Interval.fromToExclusive(param2, param3));
    }

    static public Stream<Arguments> Provider_size_2_13() {
        return Stream.of(arguments(100, 1, 101), arguments(11, 0, 11));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_3to11_50")
    public void size_3to11_50(int param1, int param2, int param3, int param4) {
        Verify.assertSize(param1, Interval.fromToBy(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_size_3to11_50() {
        return Stream.of(arguments(50, 1, 100, 2), arguments(34, 1, 100, 3), arguments(25, 1, 100, 4), arguments(20, 1, 100, 5), arguments(17, 1, 100, 6), arguments(15, 1, 100, 7), arguments(13, 1, 100, 8), arguments(12, 1, 100, 9), arguments(10, 1, 100, 10), arguments(1, "1_000_000_000", "2_000_000_000", "1_500_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_15_28_40_43")
    public void size_15_28_40_43(int param1, int param2, int param3) {
        Verify.assertSize(param1, Interval.fromTo(param2, -param3));
    }

    static public Stream<Arguments> Provider_size_15_28_40_43() {
        return Stream.of(arguments(11, 0, 10), arguments(10, 0, 9), arguments(21, 10, 10), arguments("2_000_000_001", "1_000_000_000", "1_000_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_16_34_45_48")
    public void size_16_34_45_48(int param1, int param2, int param3) {
        Verify.assertSize(param1, Interval.fromToExclusive(param2, -param3));
    }

    static public Stream<Arguments> Provider_size_16_34_45_48() {
        return Stream.of(arguments(9, 0, 9), arguments(10, 0, 10), arguments(21, 10, 11), arguments("2_000_000_001", "1_000_000_000", "1_000_000_001"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_21_24to25")
    public void size_21_24to25(String param1, int param2, String param3) {
        Verify.assertSize(param1, Interval.oneTo(param3).by(param2));
    }

    static public Stream<Arguments> Provider_size_21_24to25() {
        return Stream.of(arguments("200_000_000", 10, "2_000_000_000"), arguments("500_000_000", 4, "2_000_000_000"), arguments("222_222_223", 9, "2_000_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_30_32to33")
    public void size_30_32to33(String param1, int param2, int param3, String param4) {
        Verify.assertSize(param1, Interval.fromTo(-param3, -param4).by(-param2));
    }

    static public Stream<Arguments> Provider_size_30_32to33() {
        return Stream.of(arguments("200_000_000", 10, 1, "2_000_000_000"), arguments("500_000_000", 4, 1, "2_000_000_000"), arguments("222_222_223", 9, 1, "2_000_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_36_38to39")
    public void size_36_38to39(String param1, int param2, int param3, String param4) {
        Verify.assertSize(param1, Interval.fromToExclusive(-param3, -param4).by(-param2));
    }

    static public Stream<Arguments> Provider_size_36_38to39() {
        return Stream.of(arguments("200_000_000", 10, 1, "2_000_000_000"), arguments("500_000_000", 4, 1, "2_000_000_000"), arguments("222_222_223", 9, 1, "2_000_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_37_47")
    public void size_37_47(String param1, int param2, int param3, String param4) {
        Verify.assertSize(param1, Interval.fromToExclusive(-param4, param3).by(param2));
    }

    static public Stream<Arguments> Provider_size_37_47() {
        return Stream.of(arguments("2_000_000_000", 1, 0, "2_000_000_000"), arguments(5, 5, 11, 10));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_41_44")
    public void size_41_44(int param1, int param2, int param3, int param4) {
        Verify.assertSize(param1, Interval.fromTo(param2, -param4).by(-param3));
    }

    static public Stream<Arguments> Provider_size_41_44() {
        return Stream.of(arguments(5, 10, 5, 10), arguments("200_000_001", "1_000_000_000", 10, "1_000_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_46_49")
    public void size_46_49(int param1, int param2, int param3, int param4) {
        Verify.assertSize(param1, Interval.fromToExclusive(param2, -param4).by(-param3));
    }

    static public Stream<Arguments> Provider_size_46_49() {
        return Stream.of(arguments(5, 10, 5, 11), arguments("200_000_001", "1_000_000_000", 10, "1_000_000_001"));
    }

    @ParameterizedTest
    @MethodSource("Provider_getLast_1to2")
    public void getLast_1to2(int param1, int param2, int param3, int param4) {
        assertEquals(Integer.valueOf(-param1), Interval.fromTo(param2, -param4).by(-param3).getLast());
    }

    static public Stream<Arguments> Provider_getLast_1to2() {
        return Stream.of(arguments(10, 10, 5, 10), arguments(10, 10, 5, 12));
    }

    @ParameterizedTest
    @MethodSource("Provider_getLast_3to4")
    public void getLast_3to4(int param1, int param2, int param3, int param4) {
        assertEquals(Integer.valueOf(param1), Interval.fromTo(-param4, param3).by(param2).getLast());
    }

    static public Stream<Arguments> Provider_getLast_3to4() {
        return Stream.of(arguments(10, 5, 10, 10), arguments(10, 5, 12, 10));
    }
}
