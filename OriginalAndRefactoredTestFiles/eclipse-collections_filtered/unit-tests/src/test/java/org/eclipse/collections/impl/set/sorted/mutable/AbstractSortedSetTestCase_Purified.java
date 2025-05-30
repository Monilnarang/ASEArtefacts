package org.eclipse.collections.impl.set.sorted.mutable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.primitive.ObjectIntPredicate;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.sortedset.MutableSortedSetMultimap;
import org.eclipse.collections.api.partition.set.sorted.PartitionMutableSortedSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.set.sorted.SortedSetIterable;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.collection.mutable.AbstractCollectionTestCase;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.factory.Stacks;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.AddToList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.test.domain.Person;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractSortedSetTestCase_Purified extends AbstractCollectionTestCase {

    @Override
    protected abstract <T> MutableSortedSet<T> newWith(T... littleElements);

    protected abstract <T> MutableSortedSet<T> newWith(Comparator<? super T> comparator, T... elements);

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

    @Override
    public void selectInstancesOf() {
        MutableSortedSet<Number> numbers = this.newWith((o1, o2) -> Double.compare(o1.doubleValue(), o2.doubleValue()), 1, 2.0, 3, 4.0, 5);
        MutableSortedSet<Integer> integers = numbers.selectInstancesOf(Integer.class);
        assertEquals(UnifiedSet.newSetWith(1, 3, 5), integers);
        assertEquals(Lists.mutable.with(1, 3, 5), integers.toList());
    }

    protected void validateForEachWithIndexOnRange(MutableSortedSet<Integer> set, int from, int to, MutableSortedSet<Integer> expectedOutput) {
        MutableSortedSet<Integer> outputSet = SortedSets.mutable.of(Comparators.reverseNaturalOrder());
        set.forEach(from, to, outputSet::add);
        Verify.assertSortedSetsEqual(expectedOutput, outputSet);
    }

    @Test
    public void getFirstOptional_1() {
        assertEquals(Integer.valueOf(1), this.newWith(1, 2).getFirstOptional().get());
    }

    @Test
    public void getFirstOptional_2() {
        assertTrue(this.newWith(1, 2).getFirstOptional().isPresent());
    }

    @Test
    public void getFirstOptional_3() {
        assertFalse(this.newWith().getFirstOptional().isPresent());
    }

    @Test
    public void getLastOptional_1() {
        assertEquals(Integer.valueOf(2), this.newWith(1, 2).getLastOptional().get());
    }

    @Test
    public void getLastOptional_2() {
        assertTrue(this.newWith(1, 2).getLastOptional().isPresent());
    }

    @Test
    public void getLastOptional_3() {
        assertFalse(this.newWith().getLastOptional().isPresent());
    }
}
