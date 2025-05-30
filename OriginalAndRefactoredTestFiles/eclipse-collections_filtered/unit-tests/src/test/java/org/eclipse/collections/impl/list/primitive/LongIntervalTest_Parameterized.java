package org.eclipse.collections.impl.list.primitive;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.eclipse.collections.api.LazyLongIterable;
import org.eclipse.collections.api.LongIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.procedure.primitive.LongProcedure;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.iterator.LongIterator;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.api.tuple.primitive.LongLongPair;
import org.eclipse.collections.api.tuple.primitive.LongObjectPair;
import org.eclipse.collections.impl.ThrowingAppendable;
import org.eclipse.collections.impl.bag.mutable.primitive.LongHashBag;
import org.eclipse.collections.impl.block.factory.primitive.LongPredicates;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.math.MutableLong;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LongIntervalTest_Parameterized {

    private final LongInterval longInterval = LongInterval.oneTo(3);

    private void assertLongIntervalContainsAll(LongInterval interval, int[] expectedValues) {
        for (int value : expectedValues) {
            assertTrue(interval.contains(value));
        }
    }

    private void denyLongIntervalContainsAny(LongInterval interval, int[] expectedValues) {
        for (int value : expectedValues) {
            assertFalse(interval.contains(value));
        }
    }

    @Test
    public void size_1() {
        Verify.assertSize(3, this.longInterval);
    }

    @Test
    public void size_2() {
        Verify.assertSize(10, LongInterval.zeroTo(9));
    }

    @Test
    public void size_3() {
        Verify.assertSize(2_000_000_000, LongInterval.oneTo(2_000_000_000));
    }

    @Test
    public void size_5() {
        Verify.assertSize(2_000_000_000, LongInterval.fromTo(2_000_000_000, 1).by(-1));
    }

    @Test
    public void size_8() {
        Verify.assertSize(2, LongInterval.fromToBy(Integer.MAX_VALUE - 10, Integer.MAX_VALUE, 8));
    }

    @Test
    public void size_9() {
        Verify.assertSize(10, LongInterval.zeroTo(-9));
    }

    @Test
    public void size_10() {
        Verify.assertSize(11, LongInterval.oneTo(-9));
    }

    @Test
    public void size_12() {
        Verify.assertSize(2_000_000_000, LongInterval.fromTo(-1, -2_000_000_000));
    }

    @Test
    public void size_14() {
        Verify.assertSize(2_000_000_000, LongInterval.fromTo(-2_000_000_000, -1).by(1));
    }

    @Test
    public void size_19() {
        Verify.assertSize(5, LongInterval.fromTo(-10, 10).by(5));
    }

    @Test
    public void empty_1() {
        assertTrue(this.longInterval.notEmpty());
    }

    @Test
    public void empty_2() {
        assertFalse(this.longInterval.isEmpty());
    }

    @Test
    public void empty_3() {
        Verify.assertNotEmpty(this.longInterval);
    }

    @Test
    public void count_1() {
        assertEquals(2L, LongInterval.zeroTo(2).count(LongPredicates.greaterThan(0)));
    }

    @Test
    public void count_2() {
        int count = LongInterval.fromToBy(Integer.MAX_VALUE - 10, Integer.MAX_VALUE, 8).count(LongPredicates.greaterThan(0));
        assertEquals(2, count);
    }

    @Test
    public void anySatisfy_1() {
        assertTrue(LongInterval.fromTo(-1, 2).anySatisfy(LongPredicates.greaterThan(0)));
    }

    @Test
    public void anySatisfy_2() {
        assertFalse(LongInterval.oneTo(2).anySatisfy(LongPredicates.equal(0)));
    }

    @Test
    public void allSatisfy_1() {
        assertFalse(LongInterval.zeroTo(2).allSatisfy(LongPredicates.greaterThan(0)));
    }

    @Test
    public void allSatisfy_2() {
        assertTrue(LongInterval.oneTo(3).allSatisfy(LongPredicates.greaterThan(0)));
    }

    @Test
    public void noneSatisfy_1() {
        assertFalse(LongInterval.zeroTo(2).noneSatisfy(LongPredicates.isEven()));
    }

    @Test
    public void noneSatisfy_2() {
        assertTrue(LongInterval.evensFromTo(2, 10).noneSatisfy(LongPredicates.isOdd()));
    }

    @Test
    public void detectIfNone_1() {
        assertEquals(1L, this.longInterval.detectIfNone(LongPredicates.lessThan(4), 0));
    }

    @Test
    public void detectIfNone_2() {
        assertEquals(0L, this.longInterval.detectIfNone(LongPredicates.greaterThan(3), 0));
    }

    @Test
    public void max_1() {
        assertEquals(9, LongInterval.oneTo(9).max());
    }

    @Test
    public void max_2() {
        assertEquals(5, LongInterval.fromTo(5, 1).max());
    }

    @Test
    public void max_3() {
        assertEquals(1, LongInterval.fromTo(-5, 1).max());
    }

    @Test
    public void max_4() {
        assertEquals(1, LongInterval.fromTo(1, -5).max());
    }

    @Test
    public void min_1() {
        assertEquals(1, LongInterval.oneTo(9).min());
    }

    @Test
    public void min_2() {
        assertEquals(1, LongInterval.fromTo(5, 1).min());
    }

    @Test
    public void min_3() {
        assertEquals(-5, LongInterval.fromTo(-5, 1).min());
    }

    @Test
    public void min_4() {
        assertEquals(-5, LongInterval.fromTo(1, -5).min());
    }

    @Test
    public void asLazy_1() {
        assertEquals(LongInterval.oneTo(5).toSet(), LongInterval.oneTo(5).asLazy().toSet());
    }

    @Test
    public void asLazy_2() {
        Verify.assertInstanceOf(LazyLongIterable.class, LongInterval.oneTo(5).asLazy());
    }

    @Test
    public void makeString_1() {
        assertEquals("1, 2, 3", this.longInterval.makeString());
    }

    @Test
    public void makeString_2() {
        assertEquals("1/2/3", this.longInterval.makeString("/"));
    }

    @Test
    public void makeString_3() {
        assertEquals(this.longInterval.toString(), this.longInterval.makeString("[", ", ", "]"));
    }

    @Test
    public void appendString_1() {
        StringBuilder appendable2 = new StringBuilder();
        this.longInterval.appendString(appendable2);
        assertEquals("1, 2, 3", appendable2.toString());
    }

    @Test
    public void appendString_2() {
        StringBuilder appendable3 = new StringBuilder();
        this.longInterval.appendString(appendable3, "/");
        assertEquals("1/2/3", appendable3.toString());
    }

    @Test
    public void appendString_3() {
        StringBuilder appendable4 = new StringBuilder();
        this.longInterval.appendString(appendable4, "[", ", ", "]");
        assertEquals(this.longInterval.toString(), appendable4.toString());
    }

    @Test
    public void intervalSize_12() {
        assertEquals(1, LongInterval.zero().size());
    }

    @Test
    public void intervalSize_13() {
        assertEquals(11, LongInterval.fromTo(0, -10).size());
    }

    @Test
    public void intervalSize_14() {
        assertEquals(3, LongInterval.evensFromTo(2, -2).size());
    }

    @Test
    public void intervalSize_15() {
        assertEquals(2, LongInterval.oddsFromTo(2, -2).size());
    }

    @Test
    public void intervalSize_17() {
        assertEquals(1, LongInterval.fromToBy(-1_000_000_000, -2_000_000_000, -1_500_000_000).size());
    }

    @Test
    public void contains_1() {
        assertTrue(LongInterval.zero().contains(0));
    }

    @Test
    public void contains_2() {
        assertTrue(LongInterval.oneTo(5).containsAll(1, 5));
    }

    @Test
    public void contains_3() {
        assertTrue(LongInterval.oneTo(5).containsNone(6, 7));
    }

    @Test
    public void contains_4() {
        assertFalse(LongInterval.oneTo(5).containsAll(1, 6));
    }

    @Test
    public void contains_5() {
        assertFalse(LongInterval.oneTo(5).containsNone(1, 6));
    }

    @Test
    public void contains_6() {
        assertFalse(LongInterval.oneTo(5).contains(0));
    }

    @Test
    public void contains_7() {
        assertTrue(LongInterval.fromTo(-1, -5).containsAll(-1, -5));
    }

    @Test
    public void contains_8() {
        assertFalse(LongInterval.fromTo(-1, -5).contains(1));
    }

    @Test
    public void contains_9() {
        assertTrue(LongInterval.zero().contains(Integer.valueOf(0)));
    }

    @Test
    public void contains_10() {
        assertFalse(LongInterval.oneTo(5).contains(Integer.valueOf(0)));
    }

    @Test
    public void contains_11() {
        assertFalse(LongInterval.fromTo(-1, -5).contains(Integer.valueOf(1)));
    }

    @Test
    public void contains_12_testMerged_12() {
        LongInterval bigLongInterval = LongInterval.fromToBy(Integer.MIN_VALUE, Integer.MAX_VALUE, 1_000_000);
        assertTrue(bigLongInterval.contains(Integer.MIN_VALUE + 1_000_000));
        assertFalse(bigLongInterval.contains(Integer.MIN_VALUE + 1_000_001));
        assertTrue(bigLongInterval.contains(Integer.MIN_VALUE + (1_000_000 * 10)));
        assertFalse(bigLongInterval.contains(Integer.MIN_VALUE + (1_000_001 * 10)));
        assertTrue(bigLongInterval.contains(Integer.MIN_VALUE + (1_000_000 * 100)));
        assertFalse(bigLongInterval.contains(Integer.MIN_VALUE + (1_000_001 * 100)));
        assertTrue(LongInterval.fromToBy(1_000_000_000, 2_000_000_000, 1_500_000_000).contains(1_000_000_000));
        assertTrue(LongInterval.fromToBy(-1_000_000_000, -2_000_000_000, -1_500_000_000).contains(-1_000_000_000));
        int minValue = -1_000_000_000;
        int maxValue = 1_000_000_000;
        LongInterval largeInterval = LongInterval.fromToBy(minValue, maxValue, 10);
        assertTrue(largeInterval.containsAll(maxValue - 10, maxValue - 100, maxValue - 1000, maxValue - 10000));
        assertTrue(largeInterval.contains(minValue + 10));
    }

    @Test
    public void getFirst_1() {
        assertEquals(10, LongInterval.fromTo(10, -10).by(-5).getFirst());
    }

    @Test
    public void getFirst_2() {
        assertEquals(-10, LongInterval.fromTo(-10, 10).by(5).getFirst());
    }

    @Test
    public void getFirst_3() {
        assertEquals(0, LongInterval.zero().getFirst());
    }

    @Test
    public void getLast_5() {
        assertEquals(0, LongInterval.zero().getLast());
    }

    @Test
    public void containsAll_1() {
        assertTrue(LongInterval.fromTo(1, 3).containsAll(1, 2, 3));
    }

    @Test
    public void containsAll_2() {
        assertFalse(LongInterval.fromTo(1, 3).containsAll(1, 2, 4));
    }

    @Test
    public void containsAllIterable_1() {
        assertTrue(LongInterval.fromTo(1, 3).containsAll(LongInterval.fromTo(1, 3)));
    }

    @Test
    public void containsAllIterable_2() {
        assertFalse(LongInterval.fromTo(1, 3).containsAll(LongInterval.fromTo(1, 4)));
    }

    @Test
    public void primitiveStream_1() {
        assertEquals(Lists.mutable.of(1L, 2L, 3L, 4L), LongInterval.oneTo(4L).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveStream_2() {
        assertEquals(Lists.mutable.of(0L, 2L, 4L), LongInterval.fromToBy(0L, 5L, 2L).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveStream_3() {
        assertEquals(Lists.mutable.of(5L, 3L, 1L), LongInterval.fromToBy(5L, 0L, -2L).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveStream_4() {
        assertEquals(Lists.mutable.of(10L, 15L, 20L, 25L, 30L), LongInterval.fromToBy(10L, 30L, 5L).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveStream_5() {
        assertEquals(Lists.mutable.of(30L, 25L, 20L, 15L, 10L), LongInterval.fromToBy(30L, 10L, -5L).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_1() {
        assertEquals(Lists.mutable.of(1L, 2L, 3L, 4L), LongInterval.oneTo(4).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_2() {
        assertEquals(Lists.mutable.of(0L, 2L, 4L), LongInterval.fromToBy(0, 5, 2).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_3() {
        assertEquals(Lists.mutable.of(5L, 3L, 1L, -1L, -3L), LongInterval.fromToBy(5, -4, -2).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_4() {
        assertEquals(Lists.mutable.of(10L, 15L, 20L, 25L, 30L), LongInterval.fromToBy(10, 30, 5).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_5() {
        assertEquals(Lists.mutable.of(30L, 25L, 20L, 15L, 10L), LongInterval.fromToBy(30, 10, -5).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_6() {
        assertEquals(Lists.mutable.of(-1L, 10L, 21L, 32L, 43L, 54L, 65L, 76L, 87L, 98L), LongInterval.fromToBy(-1, 100, 11).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_4_6to7")
    public void size_4_6to7(String param1, int param2, String param3) {
        Verify.assertSize(param1, LongInterval.oneTo(param3).by(param2));
    }

    static public Stream<Arguments> Provider_size_4_6to7() {
        return Stream.of(arguments("200_000_000", 10, "2_000_000_000"), arguments("500_000_000", 4, "2_000_000_000"), arguments("222_222_223", 9, "2_000_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_11_17_20")
    public void size_11_17_20(int param1, int param2, int param3) {
        Verify.assertSize(param1, LongInterval.fromTo(param2, -param3));
    }

    static public Stream<Arguments> Provider_size_11_17_20() {
        return Stream.of(arguments(10, 0, 9), arguments(21, 10, 10), arguments("2_000_000_001", "1_000_000_000", "1_000_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_13_15to16")
    public void size_13_15to16(String param1, int param2, int param3, String param4) {
        Verify.assertSize(param1, LongInterval.fromTo(-param3, -param4).by(-param2));
    }

    static public Stream<Arguments> Provider_size_13_15to16() {
        return Stream.of(arguments("200_000_000", 10, 1, "2_000_000_000"), arguments("500_000_000", 4, 1, "2_000_000_000"), arguments("222_222_223", 9, 1, "2_000_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_18_21")
    public void size_18_21(int param1, int param2, int param3, int param4) {
        Verify.assertSize(param1, LongInterval.fromTo(param2, -param4).by(-param3));
    }

    static public Stream<Arguments> Provider_size_18_21() {
        return Stream.of(arguments(5, 10, 5, 10), arguments("200_000_001", "1_000_000_000", 10, "1_000_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_select_1to2")
    public void select_1to2(int param1, int param2) {
        Verify.assertSize(param1, this.longInterval.select(LongPredicates.lessThan(param2)));
    }

    static public Stream<Arguments> Provider_select_1to2() {
        return Stream.of(arguments(3, 4), arguments(2, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_reject_1to2")
    public void reject_1to2(int param1, int param2) {
        Verify.assertSize(param1, this.longInterval.reject(LongPredicates.lessThan(param2)));
    }

    static public Stream<Arguments> Provider_reject_1to2() {
        return Stream.of(arguments(0, 4), arguments(1, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_median_1to2")
    public void median_1to2(double param1, double param2, int param3) {
        assertEquals(param1, LongInterval.oneTo(param3).median(), param2);
    }

    static public Stream<Arguments> Provider_median_1to2() {
        return Stream.of(arguments(2.5, 0.0, 4), arguments(3.0, 0.0, 5));
    }

    @ParameterizedTest
    @MethodSource("Provider_intervalSize_1_11")
    public void intervalSize_1_11(int param1, int param2, int param3) {
        assertEquals(param1, LongInterval.fromTo(param2, param3).size());
    }

    static public Stream<Arguments> Provider_intervalSize_1_11() {
        return Stream.of(arguments(100, 1, 100), arguments(11, 0, 10));
    }

    @ParameterizedTest
    @MethodSource("Provider_intervalSize_2to10_16")
    public void intervalSize_2to10_16(int param1, int param2, int param3, int param4) {
        assertEquals(param1, LongInterval.fromToBy(param2, param3, param4).size());
    }

    static public Stream<Arguments> Provider_intervalSize_2to10_16() {
        return Stream.of(arguments(50, 1, 100, 2), arguments(34, 1, 100, 3), arguments(25, 1, 100, 4), arguments(20, 1, 100, 5), arguments(17, 1, 100, 6), arguments(15, 1, 100, 7), arguments(13, 1, 100, 8), arguments(12, 1, 100, 9), arguments(10, 1, 100, 10), arguments(1, "1_000_000_000", "2_000_000_000", "1_500_000_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_getLast_1to2")
    public void getLast_1to2(int param1, int param2, int param3, int param4) {
        assertEquals(-param1, LongInterval.fromTo(param2, -param4).by(-param3).getLast());
    }

    static public Stream<Arguments> Provider_getLast_1to2() {
        return Stream.of(arguments(10, 10, 5, 10), arguments(10, 10, 5, 12));
    }

    @ParameterizedTest
    @MethodSource("Provider_getLast_3to4")
    public void getLast_3to4(int param1, int param2, int param3, int param4) {
        assertEquals(param1, LongInterval.fromTo(-param4, param3).by(param2).getLast());
    }

    static public Stream<Arguments> Provider_getLast_3to4() {
        return Stream.of(arguments(10, 5, 10, 10), arguments(10, 5, 12, 10));
    }
}
