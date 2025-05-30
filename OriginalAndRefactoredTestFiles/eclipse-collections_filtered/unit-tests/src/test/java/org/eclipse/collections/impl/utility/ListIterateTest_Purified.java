package org.eclipse.collections.impl.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.Counter;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.ObjectIntProcedures;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.MaxSizeFunction;
import org.eclipse.collections.impl.block.function.MinSizeFunction;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.block.procedure.FastListSelectProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.eclipse.collections.impl.factory.Iterables.iSet;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ListIterateTest_Purified {

    private void assertDetectIndex(List<Integer> integerList) {
        assertEquals(3, ListIterate.detectIndex(integerList, Predicates.equal(2)));
        assertEquals(-1, ListIterate.detectIndex(integerList, Predicates.equal(20)));
    }

    private void assertDetectLastIndex(List<Integer> integerList) {
        assertEquals(4, ListIterate.detectLastIndex(integerList, IntegerPredicates.isPositive()));
        assertEquals(-1, ListIterate.detectLastIndex(integerList, IntegerPredicates.isNegative()));
    }

    private void assertDetectWithIndex(List<Integer> list) {
        assertEquals(4, Iterate.detectIndexWith(list, Object::equals, 1));
        assertEquals(0, Iterate.detectIndexWith(list, Object::equals, 5));
        assertEquals(-1, Iterate.detectIndexWith(iList(), Object::equals, 5));
        assertEquals(-1, Iterate.detectIndexWith(iSet(), Object::equals, 5));
    }

    private void assertCollect(List<Boolean> list) {
        MutableList<String> newCollection = ListIterate.collect(list, String::valueOf);
        Verify.assertListsEqual(newCollection, FastList.newListWith("true", "false", "null"));
        List<String> newCollection2 = ListIterate.collect(list, String::valueOf, new ArrayList<>());
        Verify.assertListsEqual(newCollection2, FastList.newListWith("true", "false", "null"));
    }

    private void assertCollectWithIndex(List<Boolean> list) {
        MutableList<ObjectIntPair<Boolean>> newCollection = ListIterate.collectWithIndex(list, PrimitiveTuples::pair);
        Verify.assertListsEqual(newCollection, Lists.mutable.with(PrimitiveTuples.pair(Boolean.TRUE, 0), PrimitiveTuples.pair(Boolean.FALSE, 1), PrimitiveTuples.pair(null, 2)));
        List<ObjectIntPair<Boolean>> newCollection2 = ListIterate.collectWithIndex(list, PrimitiveTuples::pair, new ArrayList<>());
        Verify.assertListsEqual(newCollection2, Lists.mutable.with(PrimitiveTuples.pair(Boolean.TRUE, 0), PrimitiveTuples.pair(Boolean.FALSE, 1), PrimitiveTuples.pair(null, 2)));
    }

    private void assertFlatten(List<MutableList<Boolean>> list) {
        MutableList<Boolean> newList = ListIterate.flatCollect(list, RichIterable::toList);
        Verify.assertListsEqual(FastList.newListWith(true, false, true, null), newList);
        MutableSet<Boolean> newSet = ListIterate.flatCollect(list, RichIterable::toSet, UnifiedSet.newSet());
        Verify.assertSetsEqual(UnifiedSet.newSetWith(true, false, null), newSet);
    }

    private void assertOccurrencesOfAttributeNamedOnList(List<Integer> list) {
        int result = ListIterate.count(list, Predicates.attributeEqual(Number::intValue, 3));
        assertEquals(1, result);
        int result2 = ListIterate.count(list, Predicates.attributeEqual(Number::intValue, 6));
        assertEquals(0, result2);
    }

    private MutableList<Integer> getIntegerList() {
        return Interval.toReverseList(1, 5);
    }

    private void assertForEachWithIndex(List<Integer> list) {
        Iterate.sortThis(list);
        ListIterate.forEachWithIndex(list, (object, index) -> assertEquals(index, object - 1));
    }

    private void assertForEachUsingFromTo(List<Integer> integers) {
        MutableList<Integer> results = Lists.mutable.of();
        ListIterate.forEach(integers, 0, 4, results::add);
        assertEquals(integers, results);
        MutableList<Integer> reverseResults = Lists.mutable.of();
        assertThrows(IndexOutOfBoundsException.class, () -> ListIterate.forEach(integers, 4, -1, reverseResults::add));
        assertThrows(IndexOutOfBoundsException.class, () -> ListIterate.forEach(integers, -1, 4, reverseResults::add));
        assertThrows(IndexOutOfBoundsException.class, () -> ListIterate.forEach(integers, 0, 5, reverseResults::add));
    }

    private void assertReverseForEachUsingFromTo(List<Integer> integers, MutableList<Integer> reverseResults, Procedure<Integer> procedure) {
        ListIterate.forEach(integers, 4, 0, procedure);
        assertEquals(ListIterate.reverseThis(integers), reverseResults);
    }

    private void assertForEachWithIndexUsingFromTo(List<Integer> integers) {
        MutableList<Integer> results = Lists.mutable.of();
        ListIterate.forEachWithIndex(integers, 0, 4, ObjectIntProcedures.fromProcedure(CollectionAddProcedure.on(results)));
        assertEquals(integers, results);
        MutableList<Integer> reverseResults = Lists.mutable.of();
        ObjectIntProcedure<Integer> objectIntProcedure = ObjectIntProcedures.fromProcedure(CollectionAddProcedure.on(reverseResults));
        assertThrows(IndexOutOfBoundsException.class, () -> ListIterate.forEachWithIndex(integers, 4, -1, objectIntProcedure));
        assertThrows(IndexOutOfBoundsException.class, () -> ListIterate.forEachWithIndex(integers, -1, 4, objectIntProcedure));
    }

    private void assertReverseForEachIndexUsingFromTo(List<Integer> integers, MutableList<Integer> reverseResults, ObjectIntProcedure<Integer> objectIntProcedure) {
        ListIterate.forEachWithIndex(integers, 4, 0, objectIntProcedure);
        assertEquals(ListIterate.reverseThis(integers), reverseResults);
    }

    private void assertSumOfInt(List<Integer> list) {
        assertEquals(150, ListIterate.sumOfInt(list, anObject -> anObject * 10));
        assertEquals(150, ListIterate.sumOfInt(new LinkedList<>(list), anObject -> anObject * 10));
    }

    private void assertGroupByEach(List<Integer> list) {
        FastListMultimap<Integer, Integer> multimap = ListIterate.groupByEach(list, each -> Lists.mutable.of(each, each));
        assertEquals(FastListMultimap.newMultimap(Tuples.pair(1, 1), Tuples.pair(1, 1), Tuples.pair(2, 2), Tuples.pair(2, 2), Tuples.pair(3, 3), Tuples.pair(3, 3)), multimap);
    }

    private void assertGroupByEachWithTarget(List<Integer> list) {
        FastListMultimap<Integer, Integer> multimap = ListIterate.groupByEach(list, each -> Lists.mutable.of(each, each), FastListMultimap.newMultimap());
        assertEquals(FastListMultimap.newMultimap(Tuples.pair(1, 1), Tuples.pair(1, 1), Tuples.pair(2, 2), Tuples.pair(2, 2), Tuples.pair(3, 3), Tuples.pair(3, 3)), multimap);
    }

    private void assertAllSatisfy(List<Integer> list) {
        assertFalse(ListIterate.allSatisfy(list, IntegerPredicates.isOdd()));
        assertTrue(ListIterate.allSatisfy(list, IntegerPredicates.isPositive()));
    }

    private void validateDetectWithOptional(List<Integer> list) {
        Optional<Integer> integerOptional = ListIterate.detectWithOptional(list, Predicates2.attributeEqual(Functions.getPassThru()), 3);
        assertTrue(integerOptional.isPresent());
        assertEquals((Integer) 3, integerOptional.get());
        assertFalse(ListIterate.detectWithOptional(list, Predicates2.attributeEqual(Functions.getPassThru()), 7).isPresent());
    }

    private void assertDetectOptional(List<Integer> list) {
        Optional<Integer> integerOptional = ListIterate.detectOptional(list, Predicates.equal(2));
        assertTrue(integerOptional.isPresent());
        assertEquals((Integer) 2, integerOptional.get());
        assertFalse(ListIterate.detectOptional(list, Predicates.alwaysFalse()).isPresent());
    }

    private void assertReverseForEachWithIndex(List<Integer> list) {
        Counter counter = new Counter();
        ListIterate.reverseForEachWithIndex(list, (object, index) -> {
            assertEquals(counter.getCount() + 1, object.longValue());
            assertEquals(4 - counter.getCount(), index);
            counter.increment();
        });
    }

    private void assertForEach(List<Integer> list) {
        FastList<Integer> target = FastList.newList();
        ListIterate.forEach(list, 1, 3, new FastListSelectProcedure<>(Predicates.alwaysTrue(), target));
        assertEquals(Lists.mutable.of(4, 3, 2), target);
        target.clear();
        ListIterate.forEach(list, 3, 1, new FastListSelectProcedure<>(Predicates.alwaysTrue(), target));
        assertEquals(Lists.mutable.of(2, 3, 4), target);
    }

    private void assertForEachInBoth(List<String> list1, List<String> list2) {
        List<Pair<String, String>> list = new ArrayList<>();
        ListIterate.forEachInBoth(list1, list2, (argument1, argument2) -> list.add(Tuples.twin(argument1, argument2)));
        assertEquals(FastList.newListWith(Tuples.twin("1", "a"), Tuples.twin("2", "b")), list);
    }

    private void assertDetectWith(List<Integer> list) {
        assertEquals(Integer.valueOf(1), ListIterate.detectWith(list, Object::equals, 1));
        MutableList<Integer> list2 = Lists.fixedSize.of(1, 2, 2);
        assertSame(list2.get(1), ListIterate.detectWith(list2, Object::equals, 2));
    }

    private void assertDistinct(List<Integer> list, List<Integer> integerList) {
        List<Integer> expectedList = Lists.mutable.with(5, 2, 3, 4);
        List<Integer> actualList = Lists.mutable.empty();
        Verify.assertListsEqual(expectedList, ListIterate.distinct(list, actualList));
        Verify.assertListsEqual(expectedList, ListIterate.distinct(list));
        Verify.assertListsEqual(expectedList, actualList);
        actualList.clear();
        Verify.assertListsEqual(integerList, ListIterate.distinct(integerList, actualList));
        Verify.assertListsEqual(integerList, ListIterate.distinct(integerList));
        Verify.assertListsEqual(integerList, actualList);
    }

    private void assertSelectAndRejectWith(List<Integer> list) {
        Twin<MutableList<Integer>> result = ListIterate.selectAndRejectWith(list, Predicates2.in(), Lists.fixedSize.of(1));
        Verify.assertSize(1, result.getOne());
        Verify.assertSize(4, result.getTwo());
    }

    private void assertAnySatisfy(List<Integer> list) {
        assertTrue(ListIterate.anySatisfy(list, IntegerPredicates.isPositive()));
        assertFalse(ListIterate.anySatisfy(list, IntegerPredicates.isNegative()));
    }

    private void assertAnySatisyWith(List<Integer> undertest) {
        assertTrue(ListIterate.anySatisfyWith(undertest, Predicates2.instanceOf(), Integer.class));
        assertFalse(ListIterate.anySatisfyWith(undertest, Predicates2.instanceOf(), Double.class));
    }

    private void assertAllSatisfyWith(List<Integer> list) {
        assertTrue(ListIterate.allSatisfyWith(list, Predicates2.instanceOf(), Integer.class));
        Predicate2<Integer, Integer> greaterThanPredicate = Predicates2.greaterThan();
        assertFalse(ListIterate.allSatisfyWith(list, greaterThanPredicate, 2));
    }

    private void assertNoneSatisfy(List<Integer> list) {
        assertTrue(ListIterate.noneSatisfy(list, IntegerPredicates.isZero()));
        assertFalse(ListIterate.noneSatisfy(list, IntegerPredicates.isEven()));
    }

    private void assertNoneSatisfyWith(List<Integer> list) {
        assertTrue(ListIterate.noneSatisfyWith(list, Predicates2.instanceOf(), String.class));
        Predicate2<Integer, Integer> greaterThanPredicate = Predicates2.greaterThan();
        assertTrue(ListIterate.noneSatisfyWith(list, greaterThanPredicate, 6));
    }

    private void assertCollectIf(List<Integer> integers) {
        Verify.assertContainsAll(ListIterate.collectIf(integers, Integer.class::isInstance, String::valueOf), "1", "2", "3");
        Verify.assertContainsAll(ListIterate.collectIf(integers, Integer.class::isInstance, String::valueOf, new ArrayList<>()), "1", "2", "3");
    }

    private void assertTake(List<Integer> integers) {
        Verify.assertEmpty(ListIterate.take(integers, 0));
        Verify.assertListsEqual(FastList.newListWith(5), ListIterate.take(integers, 1));
        Verify.assertListsEqual(FastList.newListWith(5, 4), ListIterate.take(integers, 2));
        Verify.assertListsEqual(FastList.newListWith(5, 4, 3, 2, 1), ListIterate.take(integers, 5));
        Verify.assertListsEqual(FastList.newListWith(5, 4, 3, 2, 1), ListIterate.take(integers, 10));
        Verify.assertListsEqual(FastList.newListWith(5, 4, 3, 2, 1), ListIterate.take(integers, 10, FastList.newList()));
        Verify.assertListsEqual(FastList.newListWith(5, 4, 3, 2), ListIterate.take(integers, integers.size() - 1));
        Verify.assertListsEqual(FastList.newListWith(5, 4, 3, 2, 1), ListIterate.take(integers, integers.size()));
        assertNotSame(integers, ListIterate.take(integers, integers.size()));
        Verify.assertEmpty(ListIterate.take(Lists.fixedSize.of(), 2));
    }

    private void assertDrop(List<Integer> integers) {
        Verify.assertListsEqual(FastList.newListWith(5, 4, 3, 2, 1), ListIterate.drop(integers, 0));
        assertNotSame(integers, ListIterate.drop(integers, 0));
        Verify.assertListsEqual(FastList.newListWith(3, 2, 1), ListIterate.drop(integers, 2));
        Verify.assertListsEqual(FastList.newListWith(1), ListIterate.drop(integers, integers.size() - 1));
        Verify.assertEmpty(ListIterate.drop(integers, 5));
        Verify.assertEmpty(ListIterate.drop(integers, 6));
        Verify.assertEmpty(ListIterate.drop(integers, 6, FastList.newList()));
        Verify.assertEmpty(ListIterate.drop(integers, integers.size()));
        Verify.assertSize(0, ListIterate.drop(Lists.fixedSize.of(), 2));
    }

    private void assertRemoveIfWithProcedureAndParameter(List<Integer> list) {
        MutableList<Integer> target = Lists.mutable.empty();
        assertTrue(ListIterate.removeIfWith(list, Predicates2.equal(), 3, CollectionAddProcedure.on(target)));
        MutableList<Integer> expected = Lists.mutable.of(1, 2, 4, 5);
        assertEquals(expected, list);
        assertEquals(Lists.mutable.of(3), target);
        target.clear();
        assertFalse(ListIterate.removeIfWith(list, Predicates2.equal(), 3, CollectionAddProcedure.on(target)));
        assertEquals(expected, list);
        Verify.assertEmpty(target);
    }

    private void assertPartitionWhile(List<Integer> integers) {
        PartitionMutableList<Integer> actual = ListIterate.partitionWhile(integers, Predicates.notEqual(3));
        assertEquals(Lists.mutable.of(5, 4), actual.getSelected());
        assertEquals(Lists.mutable.of(3, 2, 1), actual.getRejected());
    }

    @Test
    public void min_1() {
        assertEquals((Integer) 3, ListIterate.min(Lists.mutable.of(2, 1, 3), Comparators.reverseNaturalOrder()));
    }

    @Test
    public void min_2() {
        assertEquals((Integer) 1, ListIterate.min(Lists.mutable.of(2, 1, 3)));
    }

    @Test
    public void min_3() {
        assertEquals((Integer) 3, ListIterate.min(new LinkedList<>(Lists.mutable.of(2, 1, 3)), Comparators.reverseNaturalOrder()));
    }

    @Test
    public void min_4() {
        assertEquals((Integer) 1, ListIterate.min(new LinkedList<>(Lists.mutable.of(2, 1, 3))));
    }

    @Test
    public void max_1() {
        assertEquals((Integer) 1, ListIterate.max(Lists.mutable.of(2, 1, 3), Comparators.reverseNaturalOrder()));
    }

    @Test
    public void max_2() {
        assertEquals((Integer) 3, ListIterate.max(Lists.mutable.of(2, 1, 3)));
    }

    @Test
    public void max_3() {
        assertEquals((Integer) 1, ListIterate.max(new LinkedList<>(Lists.mutable.of(2, 1, 3)), Comparators.reverseNaturalOrder()));
    }

    @Test
    public void max_4() {
        assertEquals((Integer) 3, ListIterate.max(new LinkedList<>(Lists.mutable.of(2, 1, 3))));
    }

    @Test
    public void countWith_1() {
        assertEquals(5, ListIterate.countWith(this.getIntegerList(), Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void countWith_2() {
        assertEquals(5, ListIterate.countWith(new LinkedList<>(this.getIntegerList()), Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void reverseThis_1() {
        assertEquals(FastList.newListWith(2, 3, 1), ListIterate.reverseThis(Lists.fixedSize.of(1, 3, 2)));
    }

    @Test
    public void reverseThis_2() {
        assertEquals(FastList.newListWith(2, 3, 1), ListIterate.reverseThis(new LinkedList<>(Lists.fixedSize.of(1, 3, 2))));
    }
}
