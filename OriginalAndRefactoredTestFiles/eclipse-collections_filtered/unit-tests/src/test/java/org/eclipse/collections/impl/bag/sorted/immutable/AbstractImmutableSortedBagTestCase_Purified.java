package org.eclipse.collections.impl.bag.sorted.immutable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.multimap.sortedbag.ImmutableSortedBagMultimap;
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
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.factory.primitive.IntPredicates;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.collection.immutable.AbstractImmutableCollectionTestCase;
import org.eclipse.collections.impl.factory.SortedBags;
import org.eclipse.collections.impl.factory.SortedSets;
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
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.multimap.bag.sorted.mutable.TreeBagMultimap;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.stack.mutable.ArrayStack;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractImmutableSortedBagTestCase_Purified extends AbstractImmutableCollectionTestCase {

    @Override
    protected abstract ImmutableSortedBag<Integer> classUnderTest();

    protected abstract ImmutableSortedBag<Integer> classUnderTest(Comparator<? super Integer> comparator);

    protected <T> ImmutableSortedBag<T> newWithOccurrences(ObjectIntPair<T>... elementsWithOccurrences) {
        TreeBag<T> bag = TreeBag.newBag();
        for (int i = 0; i < elementsWithOccurrences.length; i++) {
            ObjectIntPair<T> itemToAdd = elementsWithOccurrences[i];
            bag.addOccurrences(itemToAdd.getOne(), itemToAdd.getTwo());
        }
        return bag.toImmutable();
    }

    protected abstract <T> ImmutableSortedBag<T> newWith(T... elements);

    protected abstract <T> ImmutableSortedBag<T> newWith(Comparator<? super T> comparator, T... elements);

    private static final class Holder {

        private final int number;

        private Holder(int i) {
            this.number = i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Holder holder = (Holder) o;
            return this.number == holder.number;
        }

        @Override
        public int hashCode() {
            return this.number;
        }

        @Override
        public String toString() {
            return String.valueOf(this.number);
        }
    }

    @Test
    public void equalsAndHashCode_1_testMerged_1() {
        ImmutableSortedBag<Integer> immutable = this.classUnderTest();
        MutableSortedBag<Integer> mutable = TreeBag.newBag(immutable);
        Verify.assertEqualsAndHashCode(mutable, immutable);
        Verify.assertPostSerializedEqualsAndHashCode(immutable);
        assertNotEquals(FastList.newList(mutable), immutable);
    }

    @Test
    public void equalsAndHashCode_4() {
        ImmutableSortedBag<Integer> bag1 = SortedBags.immutable.of(1, 1, 1, 4);
        ImmutableSortedBag<Integer> bag2 = SortedBags.immutable.of(1, 1, 1, 3);
        assertNotEquals(bag1, bag2);
    }

    @Test
    public void compareTo_1() {
        assertEquals(-1, SortedBags.immutable.of(1, 1, 2, 2).compareTo(SortedBags.immutable.of(1, 1, 2, 2, 2)));
    }

    @Test
    public void compareTo_2() {
        assertEquals(0, SortedBags.immutable.of(1, 1, 2, 2).compareTo(SortedBags.immutable.of(1, 1, 2, 2)));
    }

    @Test
    public void compareTo_3() {
        assertEquals(1, SortedBags.immutable.of(1, 1, 2, 2, 2).compareTo(SortedBags.immutable.of(1, 1, 2, 2)));
    }

    @Test
    public void compareTo_4() {
        assertEquals(-1, SortedBags.immutable.of(1, 1, 2, 2).compareTo(SortedBags.immutable.of(1, 1, 3, 3)));
    }

    @Test
    public void compareTo_5() {
        assertEquals(1, SortedBags.immutable.of(1, 1, 3, 3).compareTo(SortedBags.immutable.of(1, 1, 2, 2)));
    }

    @Test
    public void compareTo_6() {
        assertEquals(1, SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 2, 2, 1, 1, 1).compareTo(SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 2, 2, 1, 1)));
    }

    @Test
    public void compareTo_7() {
        assertEquals(1, SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2, 2).compareTo(SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 2)));
    }

    @Test
    public void compareTo_8() {
        assertEquals(0, SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2, 2).compareTo(SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2, 2)));
    }

    @Test
    public void compareTo_9() {
        assertEquals(-1, SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 2).compareTo(SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2, 2)));
    }

    @Test
    public void compareTo_10() {
        assertEquals(1, SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2, 2).compareTo(SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 3, 3)));
    }

    @Test
    public void compareTo_11() {
        assertEquals(-1, SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 3, 3).compareTo(SortedBags.immutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2, 2)));
    }

    @Test
    public void toStringOfItemToCount_1() {
        assertEquals("{}", SortedBags.immutable.empty().toStringOfItemToCount());
    }

    @Test
    public void toStringOfItemToCount_2() {
        assertEquals("{1=3, 2=1}", this.classUnderTest().toStringOfItemToCount());
    }

    @Test
    public void toStringOfItemToCount_3() {
        assertEquals("{2=1, 1=3}", this.classUnderTest(Comparator.reverseOrder()).toStringOfItemToCount());
    }

    @Test
    public void chunk_large_size_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().chunk(10).getFirst());
    }

    @Test
    public void chunk_large_size_2() {
        Verify.assertInstanceOf(ImmutableSortedBag.class, this.classUnderTest().chunk(10).getFirst());
    }

    @Override
    @Test
    public void getFirst_1() {
        ImmutableSortedBag<Integer> integers = this.classUnderTest();
        assertEquals(Integer.valueOf(1), integers.getFirst());
    }

    @Override
    @Test
    public void getFirst_2() {
        ImmutableSortedBag<Integer> revInt = this.classUnderTest(Collections.reverseOrder());
        assertEquals(Integer.valueOf(2), revInt.getFirst());
    }

    @Override
    @Test
    public void getLast_1() {
        ImmutableSortedBag<Integer> integers = this.classUnderTest();
        assertEquals(Integer.valueOf(2), integers.getLast());
    }

    @Override
    @Test
    public void getLast_2() {
        ImmutableSortedBag<Integer> revInt = this.classUnderTest(Collections.reverseOrder());
        assertEquals(Integer.valueOf(1), revInt.getLast());
    }

    @Test
    public void take_1_testMerged_1() {
        ImmutableSortedBag<Integer> integers1 = this.classUnderTest();
        assertEquals(SortedBags.immutable.empty(integers1.comparator()), integers1.take(0));
        assertSame(integers1.comparator(), integers1.take(0).comparator());
        assertEquals(this.newWith(integers1.comparator(), 1, 1, 1), integers1.take(3));
        assertSame(integers1.comparator(), integers1.take(3).comparator());
        assertEquals(this.newWith(integers1.comparator(), 1, 1, 1), integers1.take(integers1.size() - 1));
    }

    @Test
    public void take_6_testMerged_2() {
        ImmutableSortedBag<Integer> integers2 = this.newWith(Comparators.reverseNaturalOrder(), 3, 3, 3, 2, 2, 1);
        assertSame(integers2, integers2.take(integers2.size()));
        assertSame(integers2, integers2.take(10));
        assertSame(integers2, integers2.take(Integer.MAX_VALUE));
    }
}
