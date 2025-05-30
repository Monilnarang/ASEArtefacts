package org.eclipse.collections.impl.set.sorted.immutable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.partition.set.sorted.PartitionImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.factory.Stacks;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.AddToList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ByteArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.CharArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.FloatArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ShortArrayList;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmutableEmptySortedSetTest_Purified extends AbstractImmutableSortedSetTestCase {

    @Override
    protected ImmutableSortedSet<Integer> classUnderTest() {
        return SortedSets.immutable.of();
    }

    @Override
    protected ImmutableSortedSet<Integer> classUnderTest(Comparator<? super Integer> comparator) {
        return SortedSets.immutable.of(comparator);
    }

    @Override
    public void toStack() {
        ImmutableSortedSet<Integer> set = this.classUnderTest(Comparators.reverseNaturalOrder());
        assertEquals(Stacks.mutable.with(), set.toStack());
    }

    @Test
    public void testContainsAll_1() {
        assertTrue(this.classUnderTest().containsAllIterable(UnifiedSet.<Integer>newSet()));
    }

    @Test
    public void testContainsAll_2() {
        assertFalse(this.classUnderTest().containsAllIterable(UnifiedSet.newSetWith(1)));
    }

    @Test
    public void testNewSortedSet_1() {
        assertSame(SortedSets.immutable.of(), SortedSets.immutable.ofAll(FastList.newList()));
    }

    @Test
    public void testNewSortedSet_2() {
        assertSame(SortedSets.immutable.of(), SortedSets.immutable.ofSortedSet(TreeSortedSet.newSet()));
    }

    @Test
    public void testNewSortedSet_3() {
        assertNotSame(SortedSets.immutable.of(), SortedSets.immutable.ofSortedSet(TreeSortedSet.newSet(Comparators.reverseNaturalOrder())));
    }

    @Override
    @Test
    public void newWith_1() {
        assertEquals(UnifiedSet.newSetWith(1), this.classUnderTest().newWith(1));
    }

    @Override
    @Test
    public void newWith_2() {
        assertSame(SortedSets.immutable.empty(), SortedSets.immutable.of(FastList.newList().toArray()));
    }

    @Override
    @Test
    public void newWith_3() {
        assertEquals(SortedSets.immutable.empty(), SortedSets.immutable.of(Comparators.naturalOrder(), FastList.newList().toArray()));
    }

    @Override
    @Test
    public void newWith_4() {
        assertEquals(Comparators.<Integer>reverseNaturalOrder(), this.classUnderTest(Comparators.reverseNaturalOrder()).newWith(1).comparator());
    }

    @Override
    @Test
    public void newWith_5() {
        Verify.assertSortedSetsEqual(TreeSortedSet.newSetWith(Comparators.reverseNaturalOrder(), 1, 2), this.classUnderTest(Comparators.reverseNaturalOrder()).newWith(1).newWith(2).castToSortedSet());
    }

    @Override
    @Test
    public void newWithout_1() {
        assertEquals(SortedSets.immutable.empty(), SortedSets.immutable.empty().newWithout(1));
    }

    @Override
    @Test
    public void newWithout_2() {
        assertSame(SortedSets.immutable.empty(), SortedSets.immutable.empty().newWithout(1));
    }

    @Override
    @Test
    public void newWithout_3() {
        assertEquals(SortedSets.immutable.empty(), SortedSets.immutable.empty().newWithoutAll(Interval.oneTo(3)));
    }

    @Override
    @Test
    public void newWithout_4() {
        assertSame(SortedSets.immutable.empty(), SortedSets.immutable.empty().newWithoutAll(Interval.oneTo(3)));
    }

    @Override
    @Test
    public void newWithout_5() {
        assertEquals(Comparators.<Integer>reverseNaturalOrder(), this.classUnderTest(Comparators.reverseNaturalOrder()).newWithout(1).comparator());
    }

    @Override
    @Test
    public void isEmpty_1() {
        Verify.assertIterableEmpty(this.classUnderTest());
    }

    @Override
    @Test
    public void isEmpty_2() {
        assertFalse(this.classUnderTest().notEmpty());
    }

    @Override
    @Test
    public void union_1() {
        Verify.assertSortedSetsEqual(TreeSortedSet.newSetWith("a", "b", "c"), SortedSets.immutable.<String>empty().union(UnifiedSet.newSetWith("a", "b", "c")).castToSortedSet());
    }

    @Override
    @Test
    public void union_2() {
        Verify.assertListsEqual(FastList.newListWith(3, 2, 1), this.classUnderTest(Comparators.reverseNaturalOrder()).union(UnifiedSet.newSetWith(1, 2, 3)).toList());
    }

    @Override
    @Test
    public void intersect_1() {
        assertEquals(UnifiedSet.<String>newSet(), SortedSets.immutable.<String>empty().intersect(UnifiedSet.newSetWith("1", "2", "3")));
    }

    @Override
    @Test
    public void intersect_2() {
        assertEquals(Comparators.<Integer>reverseNaturalOrder(), this.classUnderTest(Comparators.reverseNaturalOrder()).intersect(UnifiedSet.newSetWith(1, 2, 3)).comparator());
    }

    @Override
    @Test
    public void difference_1() {
        assertEquals(UnifiedSet.<String>newSet(), SortedSets.immutable.<String>empty().difference(UnifiedSet.newSetWith("not present")));
    }

    @Override
    @Test
    public void difference_2() {
        assertEquals(Comparators.<Integer>reverseNaturalOrder(), this.classUnderTest(Comparators.reverseNaturalOrder()).difference(UnifiedSet.newSetWith(1, 2, 3)).comparator());
    }

    @Override
    @Test
    public void symmetricDifference_1() {
        assertEquals(UnifiedSet.newSetWith("not present"), SortedSets.immutable.<String>empty().symmetricDifference(UnifiedSet.newSetWith("not present")));
    }

    @Override
    @Test
    public void symmetricDifference_2() {
        Verify.assertSortedSetsEqual(TreeSortedSet.newSetWith(Comparators.reverseNaturalOrder(), 1, 2, 3, 4), SortedSets.immutable.of(Comparators.<Integer>reverseNaturalOrder()).symmetricDifference(UnifiedSet.newSetWith(1, 3, 2, 4)).castToSortedSet());
    }

    @Test
    public void compareTo_1() {
        assertEquals(0, (long) this.classUnderTest().compareTo(this.classUnderTest()));
    }

    @Test
    public void compareTo_2() {
        assertEquals(-1, this.classUnderTest().compareTo(TreeSortedSet.newSetWith(1)));
    }

    @Test
    public void compareTo_3() {
        assertEquals(-4, this.classUnderTest().compareTo(TreeSortedSet.newSetWith(1, 2, 3, 4)));
    }

    @Override
    @Test
    public void cartesianProduct_1() {
        LazyIterable<Pair<Integer, Integer>> emptyProduct = this.classUnderTest().cartesianProduct(SortedSets.immutable.of(1, 2, 3));
        Verify.assertEmpty(emptyProduct.toList());
    }

    @Override
    @Test
    public void cartesianProduct_2() {
        LazyIterable<Pair<Integer, Integer>> empty2 = this.classUnderTest().cartesianProduct(TreeSortedSet.newSet());
        Verify.assertEmpty(empty2.toList());
    }

    @Override
    @Test
    public void powerSet_1() {
        Verify.assertSize(1, this.classUnderTest().powerSet().castToSortedSet());
    }

    @Override
    @Test
    public void powerSet_2() {
        assertEquals(SortedSets.immutable.of(SortedSets.immutable.<Integer>empty()), this.classUnderTest().powerSet());
    }
}
