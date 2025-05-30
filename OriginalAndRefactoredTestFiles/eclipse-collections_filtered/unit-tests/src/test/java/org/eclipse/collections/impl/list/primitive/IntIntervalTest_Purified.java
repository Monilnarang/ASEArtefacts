package org.eclipse.collections.impl.list.primitive;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.eclipse.collections.api.IntIterable;
import org.eclipse.collections.api.LazyIntIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.procedure.primitive.IntProcedure;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.iterator.IntIterator;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.tuple.primitive.IntIntPair;
import org.eclipse.collections.api.tuple.primitive.IntObjectPair;
import org.eclipse.collections.impl.ThrowingAppendable;
import org.eclipse.collections.impl.bag.mutable.primitive.IntHashBag;
import org.eclipse.collections.impl.block.factory.primitive.IntPredicates;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.math.MutableInteger;
import org.eclipse.collections.impl.math.MutableLong;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
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

public class IntIntervalTest_Purified {

    private final IntInterval intInterval = IntInterval.oneTo(3);

    private void assertIntIntervalContainsAll(IntInterval interval, int[] expectedValues) {
        for (int value : expectedValues) {
            assertTrue(interval.contains(value));
        }
    }

    private void denyIntIntervalContainsAny(IntInterval interval, int[] expectedValues) {
        for (int value : expectedValues) {
            assertFalse(interval.contains(value));
        }
    }

    @Test
    public void size_1() {
        Verify.assertSize(3, this.intInterval);
    }

    @Test
    public void size_2() {
        Verify.assertSize(10, IntInterval.zeroTo(9));
    }

    @Test
    public void size_3() {
        Verify.assertSize(2_000_000_000, IntInterval.oneTo(2_000_000_000));
    }

    @Test
    public void size_4() {
        Verify.assertSize(200_000_000, IntInterval.oneTo(2_000_000_000).by(10));
    }

    @Test
    public void size_5() {
        Verify.assertSize(2_000_000_000, IntInterval.fromTo(2_000_000_000, 1).by(-1));
    }

    @Test
    public void size_6() {
        Verify.assertSize(500_000_000, IntInterval.oneTo(2_000_000_000).by(4));
    }

    @Test
    public void size_7() {
        Verify.assertSize(222_222_223, IntInterval.oneTo(2_000_000_000).by(9));
    }

    @Test
    public void size_8() {
        Verify.assertSize(2, IntInterval.fromToBy(Integer.MAX_VALUE - 10, Integer.MAX_VALUE, 8));
    }

    @Test
    public void size_9() {
        Verify.assertSize(10, IntInterval.zeroTo(-9));
    }

    @Test
    public void size_10() {
        Verify.assertSize(11, IntInterval.oneTo(-9));
    }

    @Test
    public void size_11() {
        Verify.assertSize(10, IntInterval.fromTo(0, -9));
    }

    @Test
    public void size_12() {
        Verify.assertSize(2_000_000_000, IntInterval.fromTo(-1, -2_000_000_000));
    }

    @Test
    public void size_13() {
        Verify.assertSize(200_000_000, IntInterval.fromTo(-1, -2_000_000_000).by(-10));
    }

    @Test
    public void size_14() {
        Verify.assertSize(2_000_000_000, IntInterval.fromTo(-2_000_000_000, -1).by(1));
    }

    @Test
    public void size_15() {
        Verify.assertSize(500_000_000, IntInterval.fromTo(-1, -2_000_000_000).by(-4));
    }

    @Test
    public void size_16() {
        Verify.assertSize(222_222_223, IntInterval.fromTo(-1, -2_000_000_000).by(-9));
    }

    @Test
    public void size_17() {
        Verify.assertSize(21, IntInterval.fromTo(10, -10));
    }

    @Test
    public void size_18() {
        Verify.assertSize(5, IntInterval.fromTo(10, -10).by(-5));
    }

    @Test
    public void size_19() {
        Verify.assertSize(5, IntInterval.fromTo(-10, 10).by(5));
    }

    @Test
    public void size_20() {
        Verify.assertSize(2_000_000_001, IntInterval.fromTo(1_000_000_000, -1_000_000_000));
    }

    @Test
    public void size_21() {
        Verify.assertSize(200_000_001, IntInterval.fromTo(1_000_000_000, -1_000_000_000).by(-10));
    }

    @Test
    public void empty_1() {
        assertTrue(this.intInterval.notEmpty());
    }

    @Test
    public void empty_2() {
        Verify.assertNotEmpty(this.intInterval);
    }

    @Test
    public void count_1() {
        assertEquals(2L, IntInterval.zeroTo(2).count(IntPredicates.greaterThan(0)));
    }

    @Test
    public void count_2() {
        int count = IntInterval.fromToBy(Integer.MAX_VALUE - 10, Integer.MAX_VALUE, 8).count(IntPredicates.greaterThan(0));
        assertEquals(2, count);
    }

    @Test
    public void anySatisfy_1() {
        assertTrue(IntInterval.fromTo(-1, 2).anySatisfy(IntPredicates.greaterThan(0)));
    }

    @Test
    public void anySatisfy_2() {
        assertFalse(IntInterval.oneTo(2).anySatisfy(IntPredicates.equal(0)));
    }

    @Test
    public void allSatisfy_1() {
        assertFalse(IntInterval.zeroTo(2).allSatisfy(IntPredicates.greaterThan(0)));
    }

    @Test
    public void allSatisfy_2() {
        assertTrue(IntInterval.oneTo(3).allSatisfy(IntPredicates.greaterThan(0)));
    }

    @Test
    public void noneSatisfy_1() {
        assertFalse(IntInterval.zeroTo(2).noneSatisfy(IntPredicates.isEven()));
    }

    @Test
    public void noneSatisfy_2() {
        assertTrue(IntInterval.evensFromTo(2, 10).noneSatisfy(IntPredicates.isOdd()));
    }

    @Test
    public void select_1() {
        Verify.assertSize(3, this.intInterval.select(IntPredicates.lessThan(4)));
    }

    @Test
    public void select_2() {
        Verify.assertSize(2, this.intInterval.select(IntPredicates.lessThan(3)));
    }

    @Test
    public void reject_1() {
        Verify.assertSize(0, this.intInterval.reject(IntPredicates.lessThan(4)));
    }

    @Test
    public void reject_2() {
        Verify.assertSize(1, this.intInterval.reject(IntPredicates.lessThan(3)));
    }

    @Test
    public void detectIfNone_1() {
        assertEquals(1L, this.intInterval.detectIfNone(IntPredicates.lessThan(4), 0));
    }

    @Test
    public void detectIfNone_2() {
        assertEquals(0L, this.intInterval.detectIfNone(IntPredicates.greaterThan(3), 0));
    }

    @Test
    public void max_1() {
        assertEquals(9, IntInterval.oneTo(9).max());
    }

    @Test
    public void max_2() {
        assertEquals(5, IntInterval.fromTo(5, 1).max());
    }

    @Test
    public void max_3() {
        assertEquals(1, IntInterval.fromTo(-5, 1).max());
    }

    @Test
    public void max_4() {
        assertEquals(1, IntInterval.fromTo(1, -5).max());
    }

    @Test
    public void min_1() {
        assertEquals(1, IntInterval.oneTo(9).min());
    }

    @Test
    public void min_2() {
        assertEquals(1, IntInterval.fromTo(5, 1).min());
    }

    @Test
    public void min_3() {
        assertEquals(-5, IntInterval.fromTo(-5, 1).min());
    }

    @Test
    public void min_4() {
        assertEquals(-5, IntInterval.fromTo(1, -5).min());
    }

    @Test
    public void sum_1() {
        assertEquals(10L, IntInterval.oneTo(4).sum());
    }

    @Test
    public void sum_2() {
        assertEquals(5L, IntInterval.oneToBy(4, 3).sum());
    }

    @Test
    public void sum_3() {
        assertEquals(4L, IntInterval.oneToBy(4, 2).sum());
    }

    @Test
    public void sum_4() {
        assertEquals(-10L, IntInterval.fromTo(-1, -4).sum());
    }

    @Test
    public void sum_5() {
        assertEquals(-15L, IntInterval.fromToBy(-2, -10, -3).sum());
    }

    @Test
    public void sum_6() {
        assertEquals(-7L, IntInterval.fromToBy(-10, 10, 3).sum());
    }

    @Test
    public void sum_7() {
        assertEquals(3L * ((long) Integer.MAX_VALUE * 2L - 2L) / 2L, IntInterval.fromTo(Integer.MAX_VALUE - 2, Integer.MAX_VALUE).sum());
    }

    @Test
    public void average_1() {
        assertEquals(2.5, IntInterval.oneTo(4).average(), 0.0);
    }

    @Test
    public void average_2() {
        assertEquals(5.0, IntInterval.oneToBy(9, 2).average(), 0.0);
    }

    @Test
    public void average_3() {
        assertEquals(5.0, IntInterval.oneToBy(10, 2).average(), 0.0);
    }

    @Test
    public void average_4() {
        assertEquals(-5.0, IntInterval.fromToBy(-1, -9, -2).average(), 0.0);
    }

    @Test
    public void average_5() {
        assertEquals((double) Integer.MAX_VALUE - 1.5, IntInterval.fromTo(Integer.MAX_VALUE - 3, Integer.MAX_VALUE).average(), 0.0);
    }

    @Test
    public void median_1() {
        assertEquals(2.5, IntInterval.oneTo(4).median(), 0.0);
    }

    @Test
    public void median_2() {
        assertEquals(5.0, IntInterval.oneToBy(9, 2).median(), 0.0);
    }

    @Test
    public void median_3() {
        assertEquals(5.0, IntInterval.oneToBy(10, 2).median(), 0.0);
    }

    @Test
    public void median_4() {
        assertEquals(-5.0, IntInterval.fromToBy(-1, -9, -2).median(), 0.0);
    }

    @Test
    public void median_5() {
        assertEquals((double) Integer.MAX_VALUE - 1.5, IntInterval.fromTo(Integer.MAX_VALUE - 3, Integer.MAX_VALUE).median(), 0.0);
    }

    @Test
    public void asLazy_1() {
        assertEquals(IntInterval.oneTo(5).toSet(), IntInterval.oneTo(5).asLazy().toSet());
    }

    @Test
    public void asLazy_2() {
        Verify.assertInstanceOf(LazyIntIterable.class, IntInterval.oneTo(5).asLazy());
    }

    @Test
    public void makeString_1() {
        assertEquals("1, 2, 3", this.intInterval.makeString());
    }

    @Test
    public void makeString_2() {
        assertEquals("1/2/3", this.intInterval.makeString("/"));
    }

    @Test
    public void makeString_3() {
        assertEquals(this.intInterval.toString(), this.intInterval.makeString("[", ", ", "]"));
    }

    @Test
    public void appendString_1() {
        StringBuilder appendable2 = new StringBuilder();
        this.intInterval.appendString(appendable2);
        assertEquals("1, 2, 3", appendable2.toString());
    }

    @Test
    public void appendString_2() {
        StringBuilder appendable3 = new StringBuilder();
        this.intInterval.appendString(appendable3, "/");
        assertEquals("1/2/3", appendable3.toString());
    }

    @Test
    public void appendString_3() {
        StringBuilder appendable4 = new StringBuilder();
        this.intInterval.appendString(appendable4, "[", ", ", "]");
        assertEquals(this.intInterval.toString(), appendable4.toString());
    }

    @Test
    public void intervalSize_1() {
        assertEquals(100, IntInterval.fromTo(1, 100).size());
    }

    @Test
    public void intervalSize_2() {
        assertEquals(50, IntInterval.fromToBy(1, 100, 2).size());
    }

    @Test
    public void intervalSize_3() {
        assertEquals(34, IntInterval.fromToBy(1, 100, 3).size());
    }

    @Test
    public void intervalSize_4() {
        assertEquals(25, IntInterval.fromToBy(1, 100, 4).size());
    }

    @Test
    public void intervalSize_5() {
        assertEquals(20, IntInterval.fromToBy(1, 100, 5).size());
    }

    @Test
    public void intervalSize_6() {
        assertEquals(17, IntInterval.fromToBy(1, 100, 6).size());
    }

    @Test
    public void intervalSize_7() {
        assertEquals(15, IntInterval.fromToBy(1, 100, 7).size());
    }

    @Test
    public void intervalSize_8() {
        assertEquals(13, IntInterval.fromToBy(1, 100, 8).size());
    }

    @Test
    public void intervalSize_9() {
        assertEquals(12, IntInterval.fromToBy(1, 100, 9).size());
    }

    @Test
    public void intervalSize_10() {
        assertEquals(10, IntInterval.fromToBy(1, 100, 10).size());
    }

    @Test
    public void intervalSize_11() {
        assertEquals(11, IntInterval.fromTo(0, 10).size());
    }

    @Test
    public void intervalSize_12() {
        assertEquals(1, IntInterval.zero().size());
    }

    @Test
    public void intervalSize_13() {
        assertEquals(11, IntInterval.fromTo(0, -10).size());
    }

    @Test
    public void intervalSize_14() {
        assertEquals(3, IntInterval.evensFromTo(2, -2).size());
    }

    @Test
    public void intervalSize_15() {
        assertEquals(2, IntInterval.oddsFromTo(2, -2).size());
    }

    @Test
    public void intervalSize_16() {
        assertEquals(1, IntInterval.fromToBy(1_000_000_000, 2_000_000_000, 1_500_000_000).size());
    }

    @Test
    public void intervalSize_17() {
        assertEquals(1, IntInterval.fromToBy(-1_000_000_000, -2_000_000_000, -1_500_000_000).size());
    }

    @Test
    public void contains_1() {
        assertTrue(IntInterval.zero().contains(0));
    }

    @Test
    public void contains_2() {
        assertTrue(IntInterval.oneTo(5).containsAll(1, 5));
    }

    @Test
    public void contains_3() {
        assertTrue(IntInterval.oneTo(5).containsNone(6, 7));
    }

    @Test
    public void contains_4() {
        assertFalse(IntInterval.oneTo(5).containsAll(1, 6));
    }

    @Test
    public void contains_5() {
        assertFalse(IntInterval.oneTo(5).containsNone(1, 6));
    }

    @Test
    public void contains_6() {
        assertFalse(IntInterval.oneTo(5).contains(0));
    }

    @Test
    public void contains_7() {
        assertTrue(IntInterval.fromTo(-1, -5).containsAll(-1, -5));
    }

    @Test
    public void contains_8() {
        assertFalse(IntInterval.fromTo(-1, -5).contains(1));
    }

    @Test
    public void contains_9() {
        assertTrue(IntInterval.zero().contains(Integer.valueOf(0)));
    }

    @Test
    public void contains_10() {
        assertFalse(IntInterval.oneTo(5).contains(Integer.valueOf(0)));
    }

    @Test
    public void contains_11() {
        assertFalse(IntInterval.fromTo(-1, -5).contains(Integer.valueOf(1)));
    }

    @Test
    public void contains_12_testMerged_12() {
        IntInterval bigIntInterval = IntInterval.fromToBy(Integer.MIN_VALUE, Integer.MAX_VALUE, 1_000_000);
        assertTrue(bigIntInterval.contains(Integer.MIN_VALUE + 1_000_000));
        assertFalse(bigIntInterval.contains(Integer.MIN_VALUE + 1_000_001));
        assertTrue(bigIntInterval.contains(Integer.MIN_VALUE + (1_000_000 * 10)));
        assertFalse(bigIntInterval.contains(Integer.MIN_VALUE + (1_000_001 * 10)));
        assertTrue(bigIntInterval.contains(Integer.MIN_VALUE + (1_000_000 * 100)));
        assertFalse(bigIntInterval.contains(Integer.MIN_VALUE + (1_000_001 * 100)));
        assertTrue(IntInterval.fromToBy(1_000_000_000, 2_000_000_000, 1_500_000_000).contains(1_000_000_000));
        assertTrue(IntInterval.fromToBy(-1_000_000_000, -2_000_000_000, -1_500_000_000).contains(-1_000_000_000));
        int minValue = -1_000_000_000;
        int maxValue = 1_000_000_000;
        IntInterval largeInterval = IntInterval.fromToBy(minValue, maxValue, 10);
        assertTrue(largeInterval.containsAll(maxValue - 10, maxValue - 100, maxValue - 1000, maxValue - 10000));
        assertTrue(largeInterval.contains(minValue + 10));
    }

    @Test
    public void getFirst_1() {
        assertEquals(10, IntInterval.fromTo(10, -10).by(-5).getFirst());
    }

    @Test
    public void getFirst_2() {
        assertEquals(-10, IntInterval.fromTo(-10, 10).by(5).getFirst());
    }

    @Test
    public void getFirst_3() {
        assertEquals(0, IntInterval.zero().getFirst());
    }

    @Test
    public void getLast_1() {
        assertEquals(-10, IntInterval.fromTo(10, -10).by(-5).getLast());
    }

    @Test
    public void getLast_2() {
        assertEquals(-10, IntInterval.fromTo(10, -12).by(-5).getLast());
    }

    @Test
    public void getLast_3() {
        assertEquals(10, IntInterval.fromTo(-10, 10).by(5).getLast());
    }

    @Test
    public void getLast_4() {
        assertEquals(10, IntInterval.fromTo(-10, 12).by(5).getLast());
    }

    @Test
    public void getLast_5() {
        assertEquals(0, IntInterval.zero().getLast());
    }

    @Test
    public void containsAll_1() {
        assertTrue(IntInterval.fromTo(1, 3).containsAll(1, 2, 3));
    }

    @Test
    public void containsAll_2() {
        assertFalse(IntInterval.fromTo(1, 3).containsAll(1, 2, 4));
    }

    @Test
    public void containsAllIterable_1() {
        assertTrue(IntInterval.fromTo(1, 3).containsAll(IntInterval.fromTo(1, 3)));
    }

    @Test
    public void containsAllIterable_2() {
        assertFalse(IntInterval.fromTo(1, 3).containsAll(IntInterval.fromTo(1, 4)));
    }

    @Test
    public void primitiveStream_1() {
        assertEquals(Lists.mutable.of(1, 2, 3, 4), IntInterval.oneTo(4).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveStream_2() {
        assertEquals(Lists.mutable.of(0, 2, 4), IntInterval.fromToBy(0, 5, 2).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveStream_3() {
        assertEquals(Lists.mutable.of(5, 3, 1), IntInterval.fromToBy(5, 0, -2).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveStream_4() {
        assertEquals(Lists.mutable.of(10, 15, 20, 25, 30), IntInterval.fromToBy(10, 30, 5).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveStream_5() {
        assertEquals(Lists.mutable.of(30, 25, 20, 15, 10), IntInterval.fromToBy(30, 10, -5).primitiveStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_1() {
        assertEquals(Lists.mutable.of(1, 2, 3, 4), IntInterval.oneTo(4).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_2() {
        assertEquals(Lists.mutable.of(0, 2, 4), IntInterval.fromToBy(0, 5, 2).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_3() {
        assertEquals(Lists.mutable.of(5, 3, 1, -1, -3), IntInterval.fromToBy(5, -4, -2).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_4() {
        assertEquals(Lists.mutable.of(10, 15, 20, 25, 30), IntInterval.fromToBy(10, 30, 5).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_5() {
        assertEquals(Lists.mutable.of(30, 25, 20, 15, 10), IntInterval.fromToBy(30, 10, -5).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }

    @Test
    public void primitiveParallelStream_6() {
        assertEquals(Lists.mutable.of(-1, 10, 21, 32, 43, 54, 65, 76, 87, 98), IntInterval.fromToBy(-1, 100, 11).primitiveParallelStream().boxed().collect(Collectors.toList()));
    }
}
