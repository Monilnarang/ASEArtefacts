package org.eclipse.collections.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.ByteIterable;
import org.eclipse.collections.api.CharIterable;
import org.eclipse.collections.api.DoubleIterable;
import org.eclipse.collections.api.FloatIterable;
import org.eclipse.collections.api.IntIterable;
import org.eclipse.collections.api.LongIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.ShortIterable;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.primitive.BooleanBag;
import org.eclipse.collections.api.bag.primitive.ByteBag;
import org.eclipse.collections.api.bag.primitive.CharBag;
import org.eclipse.collections.api.bag.primitive.DoubleBag;
import org.eclipse.collections.api.bag.primitive.FloatBag;
import org.eclipse.collections.api.bag.primitive.IntBag;
import org.eclipse.collections.api.bag.primitive.LongBag;
import org.eclipse.collections.api.bag.primitive.MutableBooleanBag;
import org.eclipse.collections.api.bag.primitive.MutableByteBag;
import org.eclipse.collections.api.bag.primitive.MutableCharBag;
import org.eclipse.collections.api.bag.primitive.MutableDoubleBag;
import org.eclipse.collections.api.bag.primitive.MutableFloatBag;
import org.eclipse.collections.api.bag.primitive.MutableIntBag;
import org.eclipse.collections.api.bag.primitive.MutableLongBag;
import org.eclipse.collections.api.bag.primitive.MutableShortBag;
import org.eclipse.collections.api.bag.primitive.ShortBag;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.procedure.Procedure;
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
import org.eclipse.collections.api.factory.SortedBags;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.ObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.ObjectLongMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.partition.PartitionIterable;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.MutableBooleanSet;
import org.eclipse.collections.api.set.primitive.MutableByteSet;
import org.eclipse.collections.api.set.primitive.MutableCharSet;
import org.eclipse.collections.api.set.primitive.MutableDoubleSet;
import org.eclipse.collections.api.set.primitive.MutableFloatSet;
import org.eclipse.collections.api.set.primitive.MutableIntSet;
import org.eclipse.collections.api.set.primitive.MutableLongSet;
import org.eclipse.collections.api.set.primitive.MutableShortSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.BooleanBooleanPair;
import org.eclipse.collections.api.tuple.primitive.ByteBytePair;
import org.eclipse.collections.api.tuple.primitive.CharCharPair;
import org.eclipse.collections.api.tuple.primitive.DoubleDoublePair;
import org.eclipse.collections.api.tuple.primitive.FloatFloatPair;
import org.eclipse.collections.api.tuple.primitive.IntIntPair;
import org.eclipse.collections.api.tuple.primitive.LongLongPair;
import org.eclipse.collections.api.tuple.primitive.ShortShortPair;
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
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.factory.primitive.BooleanBags;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.ByteBags;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.CharBags;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.eclipse.collections.impl.factory.primitive.DoubleBags;
import org.eclipse.collections.impl.factory.primitive.DoubleSets;
import org.eclipse.collections.impl.factory.primitive.FloatBags;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntBags;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongBags;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.factory.primitive.ShortBags;
import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.eclipse.collections.impl.list.Interval;
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
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.ByteHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.CharHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.DoubleHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.FloatHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.ShortHashSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractRichIterableTestCase_Purified {

    protected abstract <T> RichIterable<T> newWith(T... littleElements);

    private void forEach(Function<Collection<Integer>, Consumer<Integer>> adderProvider) {
        MutableList<Integer> result = Lists.mutable.of();
        RichIterable<Integer> template = this.newWith(1, 2, 3, 4);
        Consumer<Integer> adder = adderProvider.apply(result);
        if (adder instanceof Procedure<?>) {
            template.forEach((Procedure<Integer>) adder);
        } else {
            template.forEach(adder);
        }
        Verify.assertSize(4, result);
        Verify.assertContainsAll(result, 1, 2, 3, 4);
    }

    private void forEachProcedure() {
        this.forEach(CollectionAddProcedure::on);
    }

    private void forEachConsumer() {
        this.forEach(collection -> collection::add);
    }

    @Test
    public void equalsAndHashCode_1() {
        Verify.assertEqualsAndHashCode(this.newWith(1, 2, 3), this.newWith(1, 2, 3));
    }

    @Test
    public void equalsAndHashCode_2() {
        assertNotEquals(this.newWith(1, 2, 3), this.newWith(1, 2));
    }

    @Test
    public void containsAnyIterable_1_testMerged_1() {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        assertTrue(collection.containsAnyIterable(Lists.mutable.with(0, 1)));
        assertTrue(collection.containsAnyIterable(Arrays.asList(0, 1)));
        assertFalse(collection.containsAnyIterable(Lists.mutable.with(5, 6)));
        assertFalse(collection.containsAnyIterable(Arrays.asList(5, 6)));
        assertTrue(collection.containsAnyIterable(Interval.oneTo(100)));
        assertFalse(collection.containsAnyIterable(Interval.fromTo(5, 100)));
        assertTrue(Interval.oneTo(100).containsAnyIterable(collection));
        assertFalse(Interval.fromTo(5, 100).containsAnyIterable(collection));
    }

    @Test
    public void containsAnyIterable_9() {
        assertTrue(this.newWith(Interval.oneTo(100).toArray()).containsAnyIterable(Interval.oneTo(50)));
    }

    @Test
    public void containsAnyIterable_10() {
        assertFalse(this.newWith(Interval.fromTo(5, 100).toArray()).containsAnyIterable(Interval.fromTo(200, 250)));
    }

    @Test
    public void containsNoneIterable_1_testMerged_1() {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        assertTrue(collection.containsNoneIterable(Lists.mutable.with(0, 5, 6, 7)));
        assertTrue(collection.containsNoneIterable(Arrays.asList(0, 5, 6, 7)));
        assertFalse(collection.containsNoneIterable(Lists.mutable.with(0, 1, 5, 6)));
        assertFalse(collection.containsNoneIterable(Arrays.asList(0, 1, 5, 6)));
        assertFalse(collection.containsNoneIterable(Interval.oneTo(100)));
        assertTrue(collection.containsNoneIterable(Interval.fromTo(5, 100)));
        assertFalse(Interval.oneTo(100).containsNoneIterable(collection));
        assertTrue(Interval.fromTo(5, 100).containsNoneIterable(collection));
    }

    @Test
    public void containsNoneIterable_9() {
        assertFalse(this.newWith(Interval.oneTo(100).toArray()).containsNoneIterable(Interval.oneTo(50)));
    }

    @Test
    public void containsNoneIterable_10() {
        assertTrue(this.newWith(Interval.fromTo(5, 100).toArray()).containsNoneIterable(Interval.fromTo(200, 250)));
    }

    @Test
    public void containsAnyCollection_1_testMerged_1() {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        assertTrue(collection.containsAny(Lists.mutable.with(0, 1)));
        assertTrue(collection.containsAny(Arrays.asList(0, 1)));
        assertFalse(collection.containsAny(Lists.mutable.with(5, 6)));
        assertFalse(collection.containsAny(Arrays.asList(5, 6)));
        assertTrue(collection.containsAny(Interval.oneTo(100)));
        assertFalse(collection.containsAny(Interval.fromTo(5, 100)));
    }

    @Test
    public void containsAnyCollection_7() {
        assertTrue(this.newWith(Interval.oneTo(100).toArray()).containsAny(Interval.oneTo(50)));
    }

    @Test
    public void containsAnyCollection_8() {
        assertFalse(this.newWith(Interval.fromTo(5, 100).toArray()).containsAny(Interval.fromTo(200, 250)));
    }

    @Test
    public void containsNoneCollection_1_testMerged_1() {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        assertTrue(collection.containsNone(Lists.mutable.with(0, 5, 6, 7)));
        assertTrue(collection.containsNone(Arrays.asList(0, 5, 6, 7)));
        assertFalse(collection.containsNone(Lists.mutable.with(0, 1, 5, 6)));
        assertFalse(collection.containsNone(Arrays.asList(0, 1, 5, 6)));
        assertFalse(collection.containsNone(Interval.oneTo(100)));
        assertTrue(collection.containsNone(Interval.fromTo(5, 100)));
    }

    @Test
    public void containsNoneCollection_7() {
        assertFalse(this.newWith(Interval.oneTo(100).toArray()).containsNone(Interval.oneTo(50)));
    }

    @Test
    public void containsNoneCollection_8() {
        assertTrue(this.newWith(Interval.fromTo(5, 100).toArray()).containsNone(Interval.fromTo(200, 250)));
    }

    @Test
    public void select_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4, 5).select(Predicates.lessThan(3)), 1, 2);
    }

    @Test
    public void select_2_testMerged_2() {
        RichIterable<Integer> result = this.newWith(-1, 2, 3, 4, 5).select(Predicates.lessThan(3));
        Verify.assertNotContains(3, result);
        Verify.assertNotContains(4, result);
        Verify.assertNotContains(5, result);
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4, 5).select(Predicates.lessThan(3), UnifiedSet.newSet()), 1, 2);
    }

    @Test
    public void selectWith_1_testMerged_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3), 1, 2);
    }

    @Test
    public void selectWith_2_testMerged_2() {
        RichIterable<Integer> result = this.newWith(-1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3);
        Verify.assertNotContains(3, result);
        Verify.assertNotContains(4, result);
        Verify.assertNotContains(5, result);
    }

    @Test
    public void reject_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3)), 3, 4);
    }

    @Test
    public void reject_2() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3), UnifiedSet.newSet()), 3, 4);
    }

    @Test
    public void rejectWith_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3), 3, 4);
    }

    @Test
    public void rejectWith_2() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3, UnifiedSet.newSet()), 3, 4);
    }

    @Test
    public void rejectWith_target_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3, HashBag.newBag()), 3, 4);
    }

    @Test
    public void rejectWith_target_2() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3, UnifiedSet.newSet()), 3, 4);
    }

    @Test
    public void anySatisfyWith_1() {
        assertFalse(this.newWith(1, 2, 3).anySatisfyWith(Predicates2.instanceOf(), String.class));
    }

    @Test
    public void anySatisfyWith_2() {
        assertTrue(this.newWith(1, 2, 3).anySatisfyWith(Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void collectWith_target_1() {
        assertEquals(Bags.mutable.of(2, 3, 4), this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, Lists.mutable.empty()).toBag());
    }

    @Test
    public void collectWith_target_2() {
        assertEquals(Bags.mutable.of(2, 3, 4), Bags.mutable.withAll(this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, new ArrayList<>())));
    }

    @Test
    public void collectWith_target_3() {
        assertEquals(Bags.mutable.of(2, 3, 4), Bags.mutable.withAll(this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, new CopyOnWriteArrayList<>())));
    }

    @Test
    public void collectWith_target_4() {
        assertEquals(Bags.mutable.of(2, 3, 4), this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, Bags.mutable.empty()));
    }

    @Test
    public void collectWith_target_5() {
        assertEquals(Sets.mutable.of(2, 3, 4), this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, Sets.mutable.empty()));
    }

    @Test
    public void collectWith_target_6() {
        assertEquals(Sets.mutable.of(2, 3, 4), this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, new HashSet<>()));
    }

    @Test
    public void collectWith_target_7() {
        assertEquals(Sets.mutable.of(2, 3, 4), this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, new CopyOnWriteArraySet<>()));
    }

    @Test
    public void getAny_1() {
        RichIterable<Integer> distinctElements = this.newWith(1, 2, 3);
        assertTrue(distinctElements.contains(distinctElements.getAny()));
    }

    @Test
    public void getAny_2() {
        RichIterable<String> duplicateElements = this.newWith("a", "a", "b");
        assertTrue(duplicateElements.contains(duplicateElements.getAny()));
    }

    @Test
    public void getFirst_1() {
        assertEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getFirst());
    }

    @Test
    public void getFirst_2() {
        assertNotEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getFirst());
    }

    @Test
    public void getLast_1() {
        assertNotEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getLast());
    }

    @Test
    public void getLast_2() {
        assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getLast());
    }

    @Test
    public void getOnly_1() {
        assertEquals(Integer.valueOf(2), this.newWith(2).getOnly());
    }

    @Test
    public void getOnly_2() {
        assertNotEquals(Integer.valueOf(2), this.newWith(1).getOnly());
    }

    @Test
    public void isEmpty_1() {
        Verify.assertIterableEmpty(this.newWith());
    }

    @Test
    public void isEmpty_2() {
        Verify.assertIterableNotEmpty(this.newWith(1, 2));
    }

    @Test
    public void isEmpty_3() {
        assertTrue(this.newWith(1, 2).notEmpty());
    }

    @Test
    public void toImmutableBag_1() {
        ImmutableBag<Integer> bag = this.newWith(1, 2, 3, 4).toImmutableBag();
        assertEquals(Bags.mutable.with(1, 2, 3, 4), bag);
    }

    @Test
    public void toImmutableBag_2() {
        ImmutableBag<Integer> singletonBag = this.newWith(1).toImmutableBag();
        assertEquals(Bags.mutable.with(1), singletonBag);
    }

    @Test
    public void toImmutableBag_3() {
        ImmutableBag<Integer> emptyBag = this.<Integer>newWith().toImmutableBag();
        assertEquals(Bags.immutable.empty(), emptyBag);
    }

    @Test
    public void toImmutableSortedBag_natural_ordering_1() {
        ImmutableSortedBag<Integer> bag = this.newWith(4, 1, 2, 3).toImmutableSortedBag();
        assertEquals(SortedBags.mutable.with(1, 2, 3, 4), bag);
    }

    @Test
    public void toImmutableSortedBag_natural_ordering_2() {
        ImmutableSortedBag<Integer> singletonBag = this.newWith(1).toImmutableSortedBag();
        assertEquals(SortedBags.mutable.with(1), singletonBag);
    }

    @Test
    public void toImmutableSortedBag_natural_ordering_3() {
        ImmutableSortedBag<Integer> emptyBag = this.<Integer>newWith().toImmutableSortedBag();
        assertEquals(SortedBags.immutable.empty(), emptyBag);
    }

    @Test
    public void empty_1() {
        Verify.assertIterableEmpty(this.newWith());
    }

    @Test
    public void empty_2() {
        assertTrue(this.newWith().isEmpty());
    }

    @Test
    public void empty_3() {
        assertFalse(this.newWith().notEmpty());
    }
}
