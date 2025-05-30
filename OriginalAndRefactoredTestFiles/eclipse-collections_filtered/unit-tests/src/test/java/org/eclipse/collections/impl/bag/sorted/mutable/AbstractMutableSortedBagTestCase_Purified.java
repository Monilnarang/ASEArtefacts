package org.eclipse.collections.impl.bag.sorted.mutable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.MutableBagIterable;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.primitive.IntFunction;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.multimap.sortedbag.MutableSortedBagMultimap;
import org.eclipse.collections.api.partition.bag.sorted.PartitionMutableSortedBag;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.Counter;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.bag.mutable.MutableBagTestCase;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.factory.primitive.IntPredicates;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
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
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.stack.mutable.ArrayStack;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.test.domain.Person;
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

public abstract class AbstractMutableSortedBagTestCase_Purified extends MutableBagTestCase {

    @Override
    protected abstract <T> MutableSortedBag<T> newWith(T... littleElements);

    @Override
    protected <T> MutableSortedBag<T> newWithOccurrences(ObjectIntPair<T>... elementsWithOccurrences) {
        MutableSortedBag<T> bag = this.newWith();
        for (ObjectIntPair<T> itemToAdd : elementsWithOccurrences) {
            bag.addOccurrences(itemToAdd.getOne(), itemToAdd.getTwo());
        }
        return bag;
    }

    protected abstract <T> MutableSortedBag<T> newWith(Comparator<? super T> comparator, T... elements);

    @Override
    public void makeStringWithSeparator() {
        super.makeStringWithSeparator();
        assertEquals("3!2!-3", this.newWith(Comparators.reverseNaturalOrder(), 3, 2, -3).makeString("!"));
    }

    @Override
    public void makeStringWithSeparatorAndStartAndEnd() {
        super.makeStringWithSeparatorAndStartAndEnd();
        assertEquals("<1,2,3>", this.newWith(1, 2, 3).makeString("<", ",", ">"));
    }

    @Override
    public void appendStringWithSeparator() {
        super.appendStringWithSeparator();
        MutableSortedBag<Integer> bag = this.newWith(Comparators.reverseNaturalOrder(), 5, 5, 1, 2, 3);
        Appendable builder = new StringBuilder();
        bag.appendString(builder, ", ");
        assertEquals(bag.toString(), "[" + builder + "]");
        assertEquals("5, 5, 3, 2, 1", builder.toString());
    }

    @Override
    public void appendStringWithSeparatorAndStartAndEnd() {
        super.appendStringWithSeparatorAndStartAndEnd();
        MutableSortedBag<Integer> bag = this.newWith(Comparators.reverseNaturalOrder(), 5, 5, 1, 2, 3);
        Appendable builder = new StringBuilder();
        bag.appendString(builder, "[", ", ", "]");
        assertEquals(bag.toString(), builder.toString());
        assertEquals("[5, 5, 3, 2, 1]", builder.toString());
    }

    @Override
    public void selectInstancesOf() {
        MutableSortedBag<Number> numbers = this.newWith((Number o1, Number o2) -> Double.compare(o2.doubleValue(), o1.doubleValue()), 5, 4.0, 3, 2.0, 1, 1);
        MutableSortedBag<Integer> integers = numbers.selectInstancesOf(Integer.class);
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(Comparators.reverseNaturalOrder(), 5, 3, 1, 1), integers);
    }

    private void assertIteratorRemove(MutableSortedBag<Integer> bag, Iterator<Integer> iterator, MutableSortedBag<Integer> expected) {
        assertTrue(iterator.hasNext());
        Integer first = iterator.next();
        iterator.remove();
        expected.remove(first);
        Verify.assertSortedBagsEqual(expected, bag);
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    public static final class Holder {

        private static final Function2<Integer, Integer, Holder> FROM_INT_INT = (each, each2) -> new Holder(each + each2);

        private static final Function<Integer, MutableList<Holder>> FROM_LIST = object -> FastList.newListWith(new Holder(object), new Holder(object));

        private static final IntFunction<Holder> TO_NUMBER = holder -> holder.number;

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
    @Test
    public void toImmutable_1() {
        Verify.assertInstanceOf(MutableSortedBag.class, this.newWith());
    }

    @Override
    @Test
    public void toImmutable_2() {
        Verify.assertInstanceOf(ImmutableSortedBag.class, this.newWith().toImmutable());
    }

    @Override
    @Test
    public void toImmutable_3() {
        assertFalse(this.newWith().toImmutable() instanceof MutableSortedBag);
    }

    @Override
    @Test
    public void toImmutable_4() {
        assertEquals(SortedBags.immutable.with(2, 2, 3), this.newWith(2, 2, 3).toImmutable());
    }

    @Override
    @Test
    public void testToString_1() {
        assertEquals("[2, 1, 1]", this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2).toString());
    }

    @Override
    @Test
    public void testToString_2() {
        assertEquals("[3, 2, 1, 1]", this.newWith(Collections.reverseOrder(), 3, 1, 1, 2).toString());
    }

    @Override
    @Test
    public void testToString_3() {
        assertEquals("[-1, 2, 3]", this.newWith(3, -1, 2).toString());
    }

    @Override
    @Test
    public void select_1_testMerged_1() {
        MutableSortedBag<Integer> integers = this.newWith(3, 3, 2, 1, 4, 5);
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(3, 3, 2, 1), integers.select(Predicates.lessThanOrEqualTo(3)));
        Verify.assertEmpty(integers.select(Predicates.greaterThan(6)));
    }

    @Override
    @Test
    public void select_3() {
        MutableSortedBag<Integer> revInt = this.newWith(Collections.reverseOrder(), 1, 2, 4, 3, 3, 5);
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(Collections.reverseOrder(), 3, 3, 2, 1), revInt.select(Predicates.lessThan(4)));
    }

    @Override
    @Test
    public void reject_1_testMerged_1() {
        MutableSortedBag<Integer> integers = this.newWith(4, 4, 2, 1, 3, 5, 6, 6);
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(1, 2, 3, 4, 4), integers.reject(Predicates.greaterThan(4)));
        Verify.assertEmpty(integers.reject(Predicates.greaterThan(0)));
    }

    @Override
    @Test
    public void reject_3() {
        MutableSortedBag<Integer> revInt = this.newWith(Collections.reverseOrder(), 1, 2, 2, 4, 3, 5);
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(Collections.reverseOrder(), 3, 2, 2, 1), revInt.reject(Predicates.greaterThan(3)));
    }

    @Override
    @Test
    public void toStringOfItemToCount_1() {
        assertEquals("{}", this.newWith().toStringOfItemToCount());
    }

    @Override
    @Test
    public void toStringOfItemToCount_2() {
        assertEquals("{}", this.newWith(Comparators.reverseNaturalOrder()).toStringOfItemToCount());
    }

    @Override
    @Test
    public void toStringOfItemToCount_3() {
        assertEquals("{1=1, 2=2}", this.newWith(1, 2, 2).toStringOfItemToCount());
    }

    @Override
    @Test
    public void toStringOfItemToCount_4() {
        assertEquals("{2=2, 1=1}", this.newWith(Comparators.reverseNaturalOrder(), 1, 2, 2).toStringOfItemToCount());
    }

    @Test
    @Override
    public void getFirst_1() {
        assertEquals(Integer.valueOf(0), this.newWith(0, 0, 1, 1).getFirst());
    }

    @Test
    @Override
    public void getFirst_2() {
        assertEquals(Integer.valueOf(1), this.newWith(1, 1, 2, 3).getFirst());
    }

    @Test
    @Override
    public void getFirst_3() {
        assertEquals(Integer.valueOf(1), this.newWith(2, 1, 3, 2, 3).getFirst());
    }

    @Test
    @Override
    public void getFirst_4() {
        assertEquals(Integer.valueOf(3), this.newWith(Collections.reverseOrder(), 2, 2, 1, 3).getFirst());
    }

    @Test
    @Override
    public void getLast_1() {
        assertEquals(Integer.valueOf(1), this.newWith(0, 0, 1, 1).getLast());
    }

    @Test
    @Override
    public void getLast_2() {
        assertEquals(Integer.valueOf(3), this.newWith(1, 1, 2, 3).getLast());
    }

    @Test
    @Override
    public void getLast_3() {
        assertEquals(Integer.valueOf(3), this.newWith(3, 2, 3, 2, 3).getLast());
    }

    @Test
    @Override
    public void getLast_4() {
        assertEquals(Integer.valueOf(1), this.newWith(Collections.reverseOrder(), 2, 2, 1, 3).getLast());
    }

    @Override
    @Test
    public void addOccurrences_1_testMerged_1() {
        MutableSortedBag<Integer> bag = this.newWith();
        assertEquals(3, bag.addOccurrences(0, 3));
        assertEquals(0, bag.addOccurrences(2, 0));
        assertEquals(2, bag.addOccurrences(1, 2));
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(0, 0, 0, 1, 1), bag);
        assertEquals(6, bag.addOccurrences(0, 3));
        assertEquals(4, bag.addOccurrences(1, 2));
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(0, 0, 0, 0, 0, 0, 1, 1, 1, 1), bag);
    }

    @Override
    @Test
    public void addOccurrences_8_testMerged_2() {
        MutableSortedBag<Integer> revBag = this.newWith(Collections.reverseOrder());
        assertEquals(3, revBag.addOccurrences(2, 3));
        assertEquals(2, revBag.addOccurrences(3, 2));
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(Collections.reverseOrder(), 3, 3, 2, 2, 2), revBag);
        assertEquals(6, revBag.addOccurrences(2, 3));
        assertEquals(4, revBag.addOccurrences(3, 2));
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(Collections.reverseOrder(), 3, 3, 3, 3, 2, 2, 2, 2, 2, 2), revBag);
    }

    @Override
    @Test
    public void asUnmodifiable_1() {
        Verify.assertInstanceOf(UnmodifiableSortedBag.class, this.newWith().asUnmodifiable());
    }

    @Override
    @Test
    public void asUnmodifiable_2() {
        Verify.assertSortedBagsEqual(this.newWith(), this.newWith().asUnmodifiable());
    }

    @Test
    public void compareTo_1() {
        assertEquals(-1, this.newWith(1, 1, 2, 2).compareTo(this.newWith(1, 1, 2, 2, 2)));
    }

    @Test
    public void compareTo_2() {
        assertEquals(0, this.newWith(1, 1, 2, 2).compareTo(this.newWith(1, 1, 2, 2)));
    }

    @Test
    public void compareTo_3() {
        assertEquals(1, this.newWith(1, 1, 2, 2, 2).compareTo(this.newWith(1, 1, 2, 2)));
    }

    @Test
    public void compareTo_4() {
        assertEquals(-1, this.newWith(1, 1, 2, 2).compareTo(this.newWith(1, 1, 3, 3)));
    }

    @Test
    public void compareTo_5() {
        assertEquals(1, this.newWith(1, 1, 3, 3).compareTo(this.newWith(1, 1, 2, 2)));
    }

    @Test
    public void compareTo_6() {
        assertEquals(1, this.newWith(Comparators.reverseNaturalOrder(), 2, 2, 1, 1, 1).compareTo(this.newWith(Comparators.reverseNaturalOrder(), 2, 2, 1, 1)));
    }

    @Test
    public void compareTo_7() {
        assertEquals(1, this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 2).compareTo(this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 2)));
    }

    @Test
    public void compareTo_8() {
        assertEquals(0, this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 2).compareTo(this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 2)));
    }

    @Test
    public void compareTo_9() {
        assertEquals(-1, this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 2).compareTo(this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 2)));
    }

    @Test
    public void compareTo_10() {
        assertEquals(1, this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 2).compareTo(this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 3, 3)));
    }

    @Test
    public void compareTo_11() {
        assertEquals(-1, this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 3, 3).compareTo(this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 2)));
    }

    @Override
    @Test
    public void min_1() {
        assertEquals(Integer.valueOf(1), this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 3, 4).min());
    }

    @Override
    @Test
    public void min_2() {
        assertEquals(Integer.valueOf(4), this.newWith(Comparators.reverseNaturalOrder(), 1, 2, 3, 4).min(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void max_1() {
        assertEquals(Integer.valueOf(1), this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 2, 3, 4).min());
    }

    @Override
    @Test
    public void max_2() {
        assertEquals(Integer.valueOf(4), this.newWith(Comparators.reverseNaturalOrder(), 1, 1, 1, 2, 3, 4).min(Comparators.reverseNaturalOrder()));
    }

    @Test
    public void corresponds_1() {
        assertFalse(this.newWith(1, 2, 3, 4, 5).corresponds(this.newWith(1, 2, 3, 4), Predicates2.alwaysTrue()));
    }

    @Test
    public void corresponds_2_testMerged_2() {
        MutableSortedBag<Integer> integers1 = this.newWith(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        MutableSortedBag<Integer> integers2 = this.newWith(2, 3, 3, 4, 4, 4, 5, 5, 5, 5);
        assertTrue(integers1.corresponds(integers2, Predicates2.lessThan()));
        assertFalse(integers1.corresponds(integers2, Predicates2.greaterThan()));
        MutableSortedBag<Integer> integers3 = this.newWith(Comparators.reverseNaturalOrder(), 4, 4, 4, 4, 3, 3, 3, 2, 2, 1);
        assertFalse(integers3.corresponds(integers1, Predicates2.equal()));
    }

    @Test
    public void take_1_testMerged_1() {
        MutableSortedBag<Integer> integers1 = this.newWith(1, 1, 1, 2);
        assertEquals(SortedBags.mutable.empty(integers1.comparator()), integers1.take(0));
        assertSame(integers1.comparator(), integers1.take(0).comparator());
        assertEquals(this.newWith(integers1.comparator(), 1, 1, 1), integers1.take(3));
        assertSame(integers1.comparator(), integers1.take(3).comparator());
        assertEquals(this.newWith(integers1.comparator(), 1, 1, 1), integers1.take(integers1.size() - 1));
    }

    @Test
    public void take_6_testMerged_2() {
        MutableSortedBag<Integer> expectedBag = this.newWith(Comparators.reverseNaturalOrder(), 3, 3, 3, 2, 2, 1);
        MutableSortedBag<Integer> integers2 = this.newWith(Comparators.reverseNaturalOrder(), 3, 3, 3, 2, 2, 1);
        assertEquals(expectedBag, integers2.take(integers2.size()));
        assertEquals(expectedBag, integers2.take(10));
        assertEquals(expectedBag, integers2.take(Integer.MAX_VALUE));
    }
}
