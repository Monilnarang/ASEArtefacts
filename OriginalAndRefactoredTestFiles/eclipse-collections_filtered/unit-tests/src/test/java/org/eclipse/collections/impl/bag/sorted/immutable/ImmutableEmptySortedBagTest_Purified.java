package org.eclipse.collections.impl.bag.sorted.immutable;

import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.partition.bag.sorted.PartitionImmutableSortedBag;
import org.eclipse.collections.api.partition.bag.sorted.PartitionSortedBag;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.ByteHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.CharHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.DoubleHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.FloatHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.IntHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.LongHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.ShortHashBag;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.factory.Iterables;
import org.eclipse.collections.impl.factory.SortedBags;
import org.eclipse.collections.impl.factory.SortedMaps;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.factory.Stacks;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ByteArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.CharArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.FloatArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ShortArrayList;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmutableEmptySortedBagTest_Purified extends AbstractImmutableSortedBagTestCase {

    @Override
    protected ImmutableSortedBag<Integer> classUnderTest() {
        return SortedBags.immutable.empty();
    }

    @Override
    protected <T> MutableCollection<T> newMutable() {
        return SortedBags.mutable.empty();
    }

    @Override
    protected ImmutableSortedBag<Integer> classUnderTest(Comparator<? super Integer> comparator) {
        return SortedBags.immutable.empty(comparator);
    }

    @Override
    protected <T> ImmutableSortedBag<T> newWith(T... elements) {
        return (ImmutableSortedBag<T>) ImmutableEmptySortedBag.INSTANCE;
    }

    @Override
    protected <T> ImmutableSortedBag<T> newWith(Comparator<? super T> comparator, T... elements) {
        return SortedBags.immutable.empty(comparator);
    }

    @Override
    public void allSatisfyWith() {
        assertTrue(this.classUnderTest().allSatisfyWith(Predicates2.alwaysFalse(), "false"));
        assertTrue(this.classUnderTest(Comparators.reverseNaturalOrder()).allSatisfyWith(Predicates2.alwaysFalse(), false));
    }

    @Override
    public void anySatisfyWith() {
        assertFalse(this.classUnderTest().anySatisfyWith(Predicates2.alwaysFalse(), "false"));
        assertFalse(this.classUnderTest(Comparators.reverseNaturalOrder()).anySatisfyWith(Predicates2.alwaysFalse(), false));
    }

    @Override
    public void noneSatisfyWith() {
        assertTrue(this.classUnderTest().noneSatisfyWith(Predicates2.alwaysFalse(), "false"));
        assertTrue(this.classUnderTest(Comparators.reverseNaturalOrder()).noneSatisfyWith(Predicates2.alwaysFalse(), false));
    }

    @Override
    public void noneSatisfy() {
        assertTrue(this.classUnderTest().noneSatisfy(Predicates.alwaysFalse()));
        assertTrue(this.classUnderTest(Comparators.reverseNaturalOrder()).noneSatisfy(Predicates.alwaysFalse()));
    }

    @Override
    public void zipWithIndex() {
        ImmutableSortedBag<Integer> bag = this.classUnderTest(Comparator.reverseOrder());
        ImmutableSortedSet<Pair<Integer, Integer>> actual = bag.zipWithIndex();
        assertEquals(SortedSets.immutable.empty(), actual);
        assertSame(SortedSets.immutable.empty(Comparator.<Integer>reverseOrder()).comparator(), actual.comparator());
    }

    @Override
    public void detectIndex() {
        assertEquals(-1, this.classUnderTest().detectIndex(each -> each > 1));
    }

    @Override
    public void indexOf() {
        assertEquals(-1, this.classUnderTest().indexOf(1));
    }

    @Override
    @Test
    public void corresponds_1() {
        assertTrue(this.classUnderTest().corresponds(Lists.mutable.of(), Predicates2.alwaysFalse()));
    }

    @Override
    @Test
    public void corresponds_2() {
        ImmutableSortedBag<Integer> integers = this.classUnderTest().newWith(Integer.valueOf(1));
        assertFalse(this.classUnderTest().corresponds(integers, Predicates2.alwaysTrue()));
    }

    @Override
    @Test
    public void compareTo_1() {
        assertEquals(0, this.classUnderTest().compareTo(this.classUnderTest()));
    }

    @Override
    @Test
    public void compareTo_2() {
        assertEquals(0, this.classUnderTest(Comparator.reverseOrder()).compareTo(this.classUnderTest(Comparator.reverseOrder())));
    }

    @Override
    @Test
    public void compareTo_3() {
        assertEquals(0, this.classUnderTest(Comparator.naturalOrder()).compareTo(this.classUnderTest(Comparator.reverseOrder())));
    }

    @Override
    @Test
    public void compareTo_4() {
        assertEquals(-1, this.classUnderTest().compareTo(TreeBag.newBagWith(1)));
    }

    @Override
    @Test
    public void compareTo_5() {
        assertEquals(-1, this.classUnderTest(Comparator.reverseOrder()).compareTo(TreeBag.newBagWith(Comparator.reverseOrder(), 1)));
    }

    @Override
    @Test
    public void compareTo_6() {
        assertEquals(-5, this.classUnderTest().compareTo(TreeBag.newBagWith(1, 2, 2, 3, 4)));
    }

    @Override
    @Test
    public void compareTo_7() {
        assertEquals(0, this.classUnderTest().compareTo(TreeBag.newBag()));
    }

    @Override
    @Test
    public void compareTo_8() {
        assertEquals(0, this.classUnderTest().compareTo(TreeBag.newBag(Comparator.reverseOrder())));
    }

    @Override
    @Test
    public void contains_1() {
        assertFalse(this.classUnderTest().contains(1));
    }

    @Override
    @Test
    public void contains_2() {
        assertFalse(this.classUnderTest(Comparator.reverseOrder()).contains(1));
    }

    @Override
    @Test
    public void containsAllIterable_1() {
        assertFalse(this.classUnderTest().containsAllIterable(FastList.newListWith(1, 2, 3)));
    }

    @Override
    @Test
    public void containsAllIterable_2() {
        assertFalse(this.classUnderTest(Comparator.reverseOrder()).containsAllIterable(FastList.newListWith(1, 2, 3)));
    }

    @Override
    @Test
    public void containsAll_1() {
        assertFalse(this.classUnderTest().containsAll(FastList.newListWith(1, 2, 3)));
    }

    @Override
    @Test
    public void containsAll_2() {
        assertFalse(this.classUnderTest(Comparator.reverseOrder()).containsAll(FastList.newListWith(1, 2, 3)));
    }

    @Override
    @Test
    public void detectWith_1() {
        assertNull(this.classUnderTest().detectWith(Predicates2.greaterThan(), 3));
    }

    @Override
    @Test
    public void detectWith_2() {
        assertNull(this.classUnderTest(Comparators.reverseNaturalOrder()).detectWith(Predicates2.greaterThan(), 3));
    }

    @Override
    @Test
    public void toSortedBag_1() {
        assertEquals(TreeBag.newBag(), this.classUnderTest().toSortedBag());
    }

    @Override
    @Test
    public void toSortedBag_2() {
        assertEquals(TreeBag.newBag(Comparators.reverseNaturalOrder()), this.classUnderTest(Comparators.reverseNaturalOrder()).toSortedBag());
    }

    @Override
    @Test
    public void toStack_1() {
        assertEquals(Stacks.immutable.empty(), this.classUnderTest().toStack());
    }

    @Override
    @Test
    public void toStack_2() {
        assertEquals(Stacks.immutable.empty(), this.classUnderTest(Comparators.reverseNaturalOrder()).toStack());
    }

    @Override
    @Test
    public void toStringOfItemToCount_1() {
        assertEquals("{}", this.classUnderTest().toStringOfItemToCount());
    }

    @Override
    @Test
    public void toStringOfItemToCount_2() {
        assertEquals("{}", this.classUnderTest(Comparator.reverseOrder()).toStringOfItemToCount());
    }

    @Override
    @Test
    public void zip_1() {
        assertEquals(Lists.immutable.empty(), this.classUnderTest().zip(Iterables.iBag()));
    }

    @Override
    @Test
    public void zip_2() {
        assertEquals(Lists.immutable.empty(), this.classUnderTest().zip(Iterables.iBag(), FastList.newList()));
    }

    @Override
    @Test
    public void zip_3() {
        assertEquals(Lists.immutable.empty(), this.classUnderTest(Comparators.reverseNaturalOrder()).zip(Iterables.iBag()));
    }

    @Override
    @Test
    public void newWithTest_1() {
        assertEquals(SortedBags.immutable.of(1), this.classUnderTest().newWith(1));
    }

    @Override
    @Test
    public void newWithTest_2() {
        assertEquals(SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1), this.classUnderTest(Comparators.reverseNaturalOrder()).newWith(1));
    }

    @Override
    @Test
    public void newWithAll_1() {
        assertEquals(SortedBags.immutable.ofAll(FastList.newListWith(1, 2, 3, 3)), this.classUnderTest().newWithAll(FastList.newListWith(1, 2, 3, 3)));
    }

    @Override
    @Test
    public void newWithAll_2() {
        assertEquals(SortedBags.immutable.ofAll(Comparators.reverseNaturalOrder(), FastList.newListWith(1, 2, 3, 3)), this.classUnderTest(Comparators.reverseNaturalOrder()).newWithAll(FastList.newListWith(1, 2, 3, 3)));
    }

    @Override
    @Test
    public void newWithout_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().newWithout(1));
    }

    @Override
    @Test
    public void newWithout_2() {
        assertEquals(this.classUnderTest(Comparators.reverseNaturalOrder()), this.classUnderTest(Comparators.reverseNaturalOrder()).newWithout(1));
    }

    @Override
    @Test
    public void rejectWith_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().rejectWith(Predicates2.alwaysFalse(), 2));
    }

    @Override
    @Test
    public void rejectWith_2() {
        assertEquals(this.classUnderTest(Comparators.reverseNaturalOrder()), this.classUnderTest(Comparators.reverseNaturalOrder()).rejectWith(Predicates2.alwaysFalse(), 2));
    }

    @Override
    @Test
    public void rejectToTarget_1_testMerged_1() {
        ImmutableSortedBag<Integer> integers = this.classUnderTest();
        Verify.assertEmpty(integers.reject(Predicates.lessThan(integers.size() + 1), FastList.newList()));
        Verify.assertListsEqual(integers.toList(), integers.reject(Predicates.greaterThan(integers.size()), FastList.newList()));
    }

    @Override
    @Test
    public void rejectToTarget_3() {
        ImmutableSortedBag<Integer> integers2 = this.classUnderTest();
        Verify.assertEmpty(integers2.reject(Predicates.lessThan(integers2.size() + 1), new HashBag<>()));
    }

    @Override
    @Test
    public void equalsAndHashCode_1_testMerged_1() {
        ImmutableSortedBag<Integer> immutable = this.classUnderTest();
        Verify.assertEqualsAndHashCode(HashBag.newBag(), immutable);
        Verify.assertPostSerializedIdentity(immutable);
        assertNotEquals(Lists.mutable.empty(), immutable);
    }

    @Override
    @Test
    public void equalsAndHashCode_4_testMerged_2() {
        ImmutableSortedBag<Integer> bagWithComparator = this.classUnderTest(Comparators.reverseNaturalOrder());
        Verify.assertEqualsAndHashCode(HashBag.newBag(), bagWithComparator);
        Verify.assertPostSerializedEqualsAndHashCode(bagWithComparator);
    }

    @Override
    @Test
    public void getLast_1() {
        assertNull(this.classUnderTest().getLast());
    }

    @Override
    @Test
    public void getLast_2() {
        assertNull(this.classUnderTest(Comparators.reverseNaturalOrder()).getLast());
    }

    @Override
    @Test
    public void getFirst_1() {
        assertNull(this.classUnderTest().getFirst());
    }

    @Override
    @Test
    public void getFirst_2() {
        assertNull(this.classUnderTest(Comparators.reverseNaturalOrder()).getFirst());
    }

    @Override
    @Test
    public void isEmpty_1() {
        assertTrue(this.classUnderTest().isEmpty());
    }

    @Override
    @Test
    public void isEmpty_2() {
        assertTrue(this.classUnderTest(Comparators.reverseNaturalOrder()).isEmpty());
    }

    @Override
    @Test
    public void collectByte_target_1() {
        ImmutableSortedBag<Integer> bag = this.classUnderTest();
        assertEquals(new ByteArrayList(), bag.collectByte(PrimitiveFunctions.unboxIntegerToByte(), new ByteArrayList()));
    }

    @Override
    @Test
    public void collectByte_target_2() {
        ImmutableSortedBag<Integer> bag2 = this.classUnderTest();
        assertEquals(new ByteHashBag(), bag2.collectByte(PrimitiveFunctions.unboxIntegerToByte(), new ByteHashBag()));
    }

    @Override
    @Test
    public void collectChar_target_1() {
        ImmutableSortedBag<Integer> bag = this.classUnderTest();
        assertEquals(new CharArrayList(), bag.collectChar(PrimitiveFunctions.unboxIntegerToChar(), new CharArrayList()));
    }

    @Override
    @Test
    public void collectChar_target_2() {
        ImmutableSortedBag<Integer> bag2 = this.classUnderTest();
        assertEquals(new CharHashBag(), bag2.collectChar(PrimitiveFunctions.unboxIntegerToChar(), new CharHashBag()));
    }

    @Override
    @Test
    public void collectDouble_target_1() {
        ImmutableSortedBag<Integer> bag = this.classUnderTest();
        assertEquals(new DoubleArrayList(), bag.collectDouble(PrimitiveFunctions.unboxIntegerToDouble(), new DoubleArrayList()));
    }

    @Override
    @Test
    public void collectDouble_target_2() {
        ImmutableSortedBag<Integer> bag2 = this.classUnderTest();
        assertEquals(new DoubleHashBag(), bag2.collectDouble(PrimitiveFunctions.unboxIntegerToDouble(), new DoubleHashBag()));
    }

    @Override
    @Test
    public void collectFloat_target_1() {
        ImmutableSortedBag<Integer> bag = this.classUnderTest();
        assertEquals(new FloatArrayList(), bag.collectFloat(PrimitiveFunctions.unboxIntegerToFloat(), new FloatArrayList()));
    }

    @Override
    @Test
    public void collectFloat_target_2() {
        ImmutableSortedBag<Integer> bag2 = this.classUnderTest();
        assertEquals(new FloatHashBag(), bag2.collectFloat(PrimitiveFunctions.unboxIntegerToFloat(), new FloatHashBag()));
    }

    @Override
    @Test
    public void collectInt_target_1() {
        ImmutableSortedBag<Integer> bag = this.classUnderTest();
        assertEquals(new IntArrayList(), bag.collectInt(PrimitiveFunctions.unboxIntegerToInt(), new IntArrayList()));
    }

    @Override
    @Test
    public void collectInt_target_2() {
        ImmutableSortedBag<Integer> bag2 = this.classUnderTest();
        assertEquals(new IntHashBag(), bag2.collectInt(PrimitiveFunctions.unboxIntegerToInt(), new IntHashBag()));
    }

    @Override
    @Test
    public void collectLong_target_1() {
        ImmutableSortedBag<Integer> bag = this.classUnderTest();
        assertEquals(new LongArrayList(), bag.collectLong(PrimitiveFunctions.unboxIntegerToLong(), new LongArrayList()));
    }

    @Override
    @Test
    public void collectLong_target_2() {
        ImmutableSortedBag<Integer> bag2 = this.classUnderTest();
        assertEquals(new LongHashBag(), bag2.collectLong(PrimitiveFunctions.unboxIntegerToLong(), new LongHashBag()));
    }

    @Override
    @Test
    public void collectShort_target_1() {
        ImmutableSortedBag<Integer> bag = this.classUnderTest();
        assertEquals(new ShortArrayList(), bag.collectShort(PrimitiveFunctions.unboxIntegerToShort(), new ShortArrayList()));
    }

    @Override
    @Test
    public void collectShort_target_2() {
        ImmutableSortedBag<Integer> bag2 = this.classUnderTest();
        assertEquals(new ShortHashBag(), bag2.collectShort(PrimitiveFunctions.unboxIntegerToShort(), new ShortHashBag()));
    }
}
