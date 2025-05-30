package org.eclipse.collections.impl.utility;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.Function3;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.collection.primitive.MutableByteCollection;
import org.eclipse.collections.api.collection.primitive.MutableCharCollection;
import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
import org.eclipse.collections.api.collection.primitive.MutableFloatCollection;
import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
import org.eclipse.collections.api.collection.primitive.MutableLongCollection;
import org.eclipse.collections.api.collection.primitive.MutableShortCollection;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.ObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.ObjectLongMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.bag.BagMultimap;
import org.eclipse.collections.api.multimap.bag.MutableBagMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.multimap.sortedbag.MutableSortedBagMultimap;
import org.eclipse.collections.api.multimap.sortedset.MutableSortedSetMultimap;
import org.eclipse.collections.api.partition.PartitionIterable;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.ObjectIntProcedures;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.factory.StringFunctions;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.MaxSizeFunction;
import org.eclipse.collections.impl.block.function.MinSizeFunction;
import org.eclipse.collections.impl.block.predicate.PairPredicate;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.list.Interval;
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
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.math.Sum;
import org.eclipse.collections.impl.multimap.bag.HashBagMultimap;
import org.eclipse.collections.impl.multimap.bag.sorted.mutable.TreeBagMultimap;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.eclipse.collections.impl.multimap.set.UnifiedSetMultimap;
import org.eclipse.collections.impl.multimap.set.sorted.TreeSortedSetMultimap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iBag;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.eclipse.collections.impl.factory.Iterables.iSet;
import static org.eclipse.collections.impl.factory.Iterables.mList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IterateTest_Purified {

    private MutableList<Iterable<Integer>> iterables;

    @BeforeEach
    public void setUp() {
        this.iterables = Lists.mutable.of();
        this.iterables.add(Interval.oneTo(5).toList());
        this.iterables.add(Interval.oneTo(5).toSet());
        this.iterables.add(Interval.oneTo(5).toBag());
        this.iterables.add(Interval.oneTo(5).toSortedSet());
        this.iterables.add(Interval.oneTo(5).toSortedMap(i -> i, i -> i));
        this.iterables.add(Interval.oneTo(5).toMap(i -> i, i -> i));
        this.iterables.add(Interval.oneTo(5).addAllTo(new ArrayList<>(5)));
        this.iterables.add(Interval.oneTo(5).addAllTo(new LinkedList<>()));
        this.iterables.add(Collections.unmodifiableList(new ArrayList<>(Interval.oneTo(5))));
        this.iterables.add(Collections.unmodifiableCollection(new ArrayList<>(Interval.oneTo(5))));
        this.iterables.add(Interval.oneTo(5));
        this.iterables.add(Interval.oneTo(5).asLazy());
        this.iterables.add(new IterableAdapter<>(Interval.oneTo(5)));
    }

    public static final class ListContainer<T> {

        private final List<T> list;

        private ListContainer(List<T> list) {
            this.list = list;
        }

        private static <V> Function<ListContainer<V>, List<V>> getListFunction() {
            return anObject -> anObject.list;
        }

        public List<T> getList() {
            return this.list;
        }
    }

    private MutableSet<Integer> getIntegerSet() {
        return Interval.toSet(1, 5);
    }

    private void assertRemoveIfFromList(List<Integer> newIntegers) {
        assertTrue(Iterate.removeIf(newIntegers, IntegerPredicates.isEven()));
        assertFalse(Iterate.removeIf(FastList.newListWith(1, 3, 5), IntegerPredicates.isEven()));
        assertFalse(Iterate.removeIf(FastList.newList(), IntegerPredicates.isEven()));
        Verify.assertContainsAll(newIntegers, 1, 3, 5);
        Verify.assertSize(3, newIntegers);
    }

    public static MutableMap<String, Integer> createPretendIndex(int initialEntry) {
        return UnifiedMap.newWithKeysValues(String.valueOf(initialEntry), initialEntry);
    }

    private void basicTestDoubleSum(Sum newResult, Collection<Integer> newIntegers, Integer newParameter) {
        Function3<Sum, Integer, Integer, Sum> function = (sum, element, withValue) -> sum.add(element.intValue() * withValue.intValue());
        Sum sumOfDoubledValues = Iterate.injectIntoWith(newResult, newIntegers, function, newParameter);
        assertEquals(30, sumOfDoubledValues.getValue().intValue());
    }

    private static final class IterableAdapter<E> implements Iterable<E> {

        private final Iterable<E> iterable;

        private IterableAdapter(Iterable<E> newIterable) {
            this.iterable = newIterable;
        }

        @Override
        public Iterator<E> iterator() {
            return this.iterable.iterator();
        }
    }

    private void zip(Iterable<String> iterable) {
        List<Object> nulls = Collections.nCopies(Iterate.sizeOf(iterable), null);
        Collection<Pair<String, Object>> pairs = Iterate.zip(iterable, nulls);
        assertEquals(UnifiedSet.newSet(iterable), Iterate.collect(pairs, (Function<Pair<String, ?>, String>) Pair::getOne, UnifiedSet.newSet()));
        assertEquals(nulls, Iterate.collect(pairs, (Function<Pair<?, Object>, Object>) Pair::getTwo, Lists.mutable.of()));
    }

    private void zipWithIndex(Iterable<String> iterable) {
        Collection<Pair<String, Integer>> pairs = Iterate.zipWithIndex(iterable);
        assertEquals(UnifiedSet.newSet(iterable), Iterate.collect(pairs, (Function<Pair<String, ?>, String>) Pair::getOne, UnifiedSet.newSet()));
        assertEquals(Interval.zeroTo(Iterate.sizeOf(iterable) - 1).toSet(), Iterate.collect(pairs, (Function<Pair<?, Integer>, Integer>) Pair::getTwo, UnifiedSet.newSet()));
    }

    private static class WordToItsLetters implements Function<String, Set<Character>> {

        private static final long serialVersionUID = 1L;

        @Override
        public Set<Character> valueOf(String name) {
            return StringIterate.asUppercaseSet(name);
        }
    }

    private void aggregateByMutableResult(Iterable<Integer> iterable) {
        Function0<AtomicInteger> valueCreator = AtomicInteger::new;
        Procedure2<AtomicInteger, Integer> sumAggregator = AtomicInteger::addAndGet;
        MapIterable<String, AtomicInteger> aggregation = Iterate.aggregateInPlaceBy(iterable, String::valueOf, valueCreator, sumAggregator);
        if (iterable instanceof Set) {
            assertEquals(1, aggregation.get("1").intValue());
            assertEquals(2, aggregation.get("2").intValue());
            assertEquals(3, aggregation.get("3").intValue());
        } else {
            assertEquals(3, aggregation.get("1").intValue());
            assertEquals(4, aggregation.get("2").intValue());
            assertEquals(3, aggregation.get("3").intValue());
        }
    }

    private void aggregateByImmutableResult(Iterable<Integer> iterable) {
        Function0<Integer> valueCreator = () -> 0;
        Function2<Integer, Integer, Integer> sumAggregator = (aggregate, value) -> aggregate + value;
        MapIterable<String, Integer> aggregation = Iterate.aggregateBy(iterable, String::valueOf, valueCreator, sumAggregator);
        if (iterable instanceof Set) {
            assertEquals(1, aggregation.get("1").intValue());
            assertEquals(2, aggregation.get("2").intValue());
            assertEquals(3, aggregation.get("3").intValue());
        } else {
            assertEquals(3, aggregation.get("1").intValue());
            assertEquals(4, aggregation.get("2").intValue());
            assertEquals(3, aggregation.get("3").intValue());
        }
    }

    @Test
    public void sizeOf_1() {
        assertEquals(5, Iterate.sizeOf(Interval.oneTo(5)));
    }

    @Test
    public void sizeOf_2() {
        assertEquals(5, Iterate.sizeOf(Interval.oneTo(5).toList()));
    }

    @Test
    public void sizeOf_3() {
        assertEquals(5, Iterate.sizeOf(Interval.oneTo(5).asLazy()));
    }

    @Test
    public void sizeOf_4() {
        assertEquals(3, Iterate.sizeOf(UnifiedMap.newWithKeysValues(1, 1, 2, 2, 3, 3)));
    }

    @Test
    public void sizeOf_5() {
        assertEquals(5, Iterate.sizeOf(new IterableAdapter<>(Interval.oneTo(5))));
    }

    @Test
    public void contains_1() {
        assertTrue(Iterate.contains(FastList.newListWith(1, 2, 3), 1));
    }

    @Test
    public void contains_2() {
        assertFalse(Iterate.contains(FastList.newListWith(1, 2, 3), 4));
    }

    @Test
    public void contains_3() {
        assertTrue(Iterate.contains(FastList.newListWith(1, 2, 3).asLazy(), 1));
    }

    @Test
    public void contains_4() {
        assertTrue(Iterate.contains(UnifiedMap.newWithKeysValues(1, 1, 2, 2, 3, 3), 1));
    }

    @Test
    public void contains_5() {
        assertFalse(Iterate.contains(UnifiedMap.newWithKeysValues(1, 1, 2, 2, 3, 3), 4));
    }

    @Test
    public void contains_6() {
        assertTrue(Iterate.contains(Interval.oneTo(3), 1));
    }

    @Test
    public void contains_7() {
        assertFalse(Iterate.contains(Interval.oneTo(3), 4));
    }

    @Test
    public void getFirstAndLastInterval_1() {
        assertEquals(Integer.valueOf(1), Iterate.getFirst(Interval.oneTo(5)));
    }

    @Test
    public void getFirstAndLastInterval_2() {
        assertEquals(Integer.valueOf(5), Iterate.getLast(Interval.oneTo(5)));
    }

    @Test
    public void getFirstAndLastOnEmpty_1() {
        assertNull(Iterate.getFirst(mList()));
    }

    @Test
    public void getFirstAndLastOnEmpty_2() {
        assertNull(Iterate.getLast(mList()));
    }

    @Test
    public void isEmpty_1() {
        assertTrue(Iterate.isEmpty(null));
    }

    @Test
    public void isEmpty_2() {
        assertTrue(Iterate.isEmpty(Lists.fixedSize.of()));
    }

    @Test
    public void isEmpty_3() {
        assertFalse(Iterate.isEmpty(Lists.fixedSize.of("1")));
    }

    @Test
    public void isEmpty_4() {
        assertTrue(Iterate.isEmpty(Maps.fixedSize.of()));
    }

    @Test
    public void isEmpty_5() {
        assertFalse(Iterate.isEmpty(Maps.fixedSize.of("1", "1")));
    }

    @Test
    public void isEmpty_6() {
        assertTrue(Iterate.isEmpty(new IterableAdapter<>(Lists.fixedSize.of())));
    }

    @Test
    public void isEmpty_7() {
        assertFalse(Iterate.isEmpty(new IterableAdapter<>(Lists.fixedSize.of("1"))));
    }

    @Test
    public void isEmpty_8() {
        assertTrue(Iterate.isEmpty(Lists.fixedSize.of().asLazy()));
    }

    @Test
    public void isEmpty_9() {
        assertFalse(Iterate.isEmpty(Lists.fixedSize.of("1").asLazy()));
    }

    @Test
    public void notEmpty_1() {
        assertFalse(Iterate.notEmpty(null));
    }

    @Test
    public void notEmpty_2() {
        assertFalse(Iterate.notEmpty(Lists.fixedSize.of()));
    }

    @Test
    public void notEmpty_3() {
        assertTrue(Iterate.notEmpty(Lists.fixedSize.of("1")));
    }

    @Test
    public void notEmpty_4() {
        assertFalse(Iterate.notEmpty(Maps.fixedSize.of()));
    }

    @Test
    public void notEmpty_5() {
        assertTrue(Iterate.notEmpty(Maps.fixedSize.of("1", "1")));
    }

    @Test
    public void notEmpty_6() {
        assertFalse(Iterate.notEmpty(new IterableAdapter<>(Lists.fixedSize.of())));
    }

    @Test
    public void notEmpty_7() {
        assertTrue(Iterate.notEmpty(new IterableAdapter<>(Lists.fixedSize.of("1"))));
    }

    @Test
    public void notEmpty_8() {
        assertFalse(Iterate.notEmpty(Lists.fixedSize.of().asLazy()));
    }

    @Test
    public void notEmpty_9() {
        assertTrue(Iterate.notEmpty(Lists.fixedSize.of("1").asLazy()));
    }

    @Test
    public void minBy_1() {
        assertEquals(Integer.valueOf(1), Iterate.minBy(FastList.newListWith(1, 2, 3), Functions.getIntegerPassThru()));
    }

    @Test
    public void minBy_2() {
        assertEquals(Integer.valueOf(1), Iterate.minBy(FastList.newListWith(3, 2, 1), Functions.getIntegerPassThru()));
    }

    @Test
    public void minBy_3() {
        assertEquals(Integer.valueOf(1), Iterate.minBy(FastList.newListWith(1, 2, 3).asSynchronized(), Functions.getIntegerPassThru()));
    }

    @Test
    public void minBy_4() {
        assertEquals(Integer.valueOf(1), Iterate.minBy(FastList.newListWith(3, 2, 1).asSynchronized(), Functions.getIntegerPassThru()));
    }

    @Test
    public void minBy_5() {
        assertEquals(Integer.valueOf(1), Iterate.minBy(Arrays.asList(1, 2, 3), Functions.getIntegerPassThru()));
    }

    @Test
    public void minBy_6() {
        assertEquals(Integer.valueOf(1), Iterate.minBy(Arrays.asList(3, 2, 1), Functions.getIntegerPassThru()));
    }

    @Test
    public void minBy_7() {
        assertEquals(Integer.valueOf(1), Iterate.minBy(new LinkedList<>(Arrays.asList(1, 2, 3)), Functions.getIntegerPassThru()));
    }

    @Test
    public void minBy_8() {
        assertEquals(Integer.valueOf(1), Iterate.minBy(new LinkedList<>(Arrays.asList(3, 2, 1)), Functions.getIntegerPassThru()));
    }

    @Test
    public void maxBy_1() {
        assertEquals(Integer.valueOf(3), Iterate.maxBy(FastList.newListWith(1, 2, 3), Functions.getIntegerPassThru()));
    }

    @Test
    public void maxBy_2() {
        assertEquals(Integer.valueOf(3), Iterate.maxBy(FastList.newListWith(3, 2, 1), Functions.getIntegerPassThru()));
    }

    @Test
    public void maxBy_3() {
        assertEquals(Integer.valueOf(3), Iterate.maxBy(FastList.newListWith(1, 2, 3).asSynchronized(), Functions.getIntegerPassThru()));
    }

    @Test
    public void maxBy_4() {
        assertEquals(Integer.valueOf(3), Iterate.maxBy(FastList.newListWith(3, 2, 1).asSynchronized(), Functions.getIntegerPassThru()));
    }

    @Test
    public void maxBy_5() {
        assertEquals(Integer.valueOf(3), Iterate.maxBy(Arrays.asList(1, 2, 3), Functions.getIntegerPassThru()));
    }

    @Test
    public void maxBy_6() {
        assertEquals(Integer.valueOf(3), Iterate.maxBy(Arrays.asList(3, 2, 1), Functions.getIntegerPassThru()));
    }

    @Test
    public void maxBy_7() {
        assertEquals(Integer.valueOf(3), Iterate.maxBy(new LinkedList<>(Arrays.asList(1, 2, 3)), Functions.getIntegerPassThru()));
    }

    @Test
    public void maxBy_8() {
        assertEquals(Integer.valueOf(3), Iterate.maxBy(new LinkedList<>(Arrays.asList(3, 2, 1)), Functions.getIntegerPassThru()));
    }
}
