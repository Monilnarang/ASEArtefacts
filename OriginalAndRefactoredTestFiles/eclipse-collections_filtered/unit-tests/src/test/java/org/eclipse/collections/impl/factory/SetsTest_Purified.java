package org.eclipse.collections.impl.factory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.factory.set.FixedSizeSetFactory;
import org.eclipse.collections.api.factory.set.ImmutableSetFactory;
import org.eclipse.collections.api.factory.set.MultiReaderSetFactory;
import org.eclipse.collections.api.factory.set.MutableSetFactory;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.FixedSizeSet;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MultiReaderSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.MultiReaderUnifiedSet;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.test.domain.Key;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.mSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SetsTest_Purified {

    private final MutableList<UnifiedSet<String>> uniqueSets = Lists.mutable.with(this.newUnsortedSet("Tom", "Dick", "Harry", null), this.newUnsortedSet("Jane", "Sarah", "Mary"), this.newUnsortedSet("Fido", "Spike", "Spuds"));

    private final MutableList<UnifiedSet<String>> overlappingSets = Lists.mutable.with(this.newUnsortedSet("Tom", "Dick", "Harry"), this.newUnsortedSet("Larry", "Tom", "Dick"), this.newUnsortedSet("Dick", "Larry", "Paul", null));

    private final MutableList<UnifiedSet<String>> identicalSets = Lists.mutable.with(this.newUnsortedSet("Tom", null, "Dick", "Harry"), this.newUnsortedSet(null, "Harry", "Tom", "Dick"), this.newUnsortedSet("Dick", "Harry", "Tom", null));

    private final MutableList<TreeSet<String>> uniqueSortedSets = Lists.mutable.with(this.newSortedSet("Tom", "Dick", "Harry"), this.newSortedSet("Jane", "Sarah", "Mary"), this.newSortedSet("Fido", "Spike", "Spuds"));

    private final MutableList<TreeSet<String>> overlappingSortedSets = Lists.mutable.with(this.newSortedSet("Tom", "Dick", "Harry"), this.newSortedSet("Larry", "Tom", "Dick"), this.newSortedSet("Dick", "Larry", "Paul"));

    private final MutableList<TreeSet<String>> identicalSortedSets = Lists.mutable.with(this.newSortedSet("Tom", "Dick", "Harry"), this.newSortedSet("Harry", "Tom", "Dick"), this.newSortedSet("Dick", "Harry", "Tom"));

    private final MutableList<TreeSet<String>> uniqueReverseSortedSets = Lists.mutable.with(this.newReverseSortedSet("Tom", "Dick", "Harry"), this.newReverseSortedSet("Jane", "Sarah", "Mary"), this.newReverseSortedSet("Fido", "Spike", "Spuds"));

    private final MutableList<TreeSet<String>> overlappingReverseSortedSets = Lists.mutable.with(this.newReverseSortedSet("Tom", "Dick", "Harry"), this.newReverseSortedSet("Larry", "Tom", "Dick"), this.newReverseSortedSet("Dick", "Larry", "Paul"));

    private final MutableList<TreeSet<String>> identicalReverseSortedSets = Lists.mutable.with(this.newReverseSortedSet("Tom", "Dick", "Harry"), this.newReverseSortedSet("Harry", "Tom", "Dick"), this.newReverseSortedSet("Dick", "Harry", "Tom"));

    private <E> UnifiedSet<E> newUnsortedSet(E... elements) {
        return UnifiedSet.newSetWith(elements);
    }

    private <E> TreeSet<E> newSortedSet(E... elements) {
        return new TreeSet<>(UnifiedSet.newSetWith(elements));
    }

    private <E> TreeSet<E> newReverseSortedSet(E... elements) {
        TreeSet<E> set = new TreeSet<>(Collections.reverseOrder());
        set.addAll(UnifiedSet.newSetWith(elements));
        return set;
    }

    private <E> void assertUnionProperties(Procedure2<Set<E>, E[]> setContainsProcedure, Procedure2<Set<E>, Set<E>> setsEqualProcedure, Set<E> setA, Set<E> setB, Set<E> setC, E... elements) {
        Function2<Set<E>, Set<E>, Set<E>> union = Sets::union;
        Function2<Set<E>, Set<E>, Set<E>> intersect = Sets::intersect;
        this.assertCommutativeProperty(setContainsProcedure, union, setA, setB, setC, elements);
        this.assertAssociativeProperty(setContainsProcedure, union, setA, setB, setC, elements);
        this.assertDistributiveProperty(setsEqualProcedure, union, intersect, setA, setB, setC);
        this.assertIdentityProperty(setContainsProcedure, union, setA, setB, setC, elements);
    }

    private <E> void assertIntersectionProperties(Procedure2<Set<E>, E[]> setContainsProcedure, Procedure2<Set<E>, Set<E>> setsEqualProcedure, Set<E> setA, Set<E> setB, Set<E> setC, E... elements) {
        Function2<Set<E>, Set<E>, Set<E>> intersect = Sets::intersect;
        Function2<Set<E>, Set<E>, Set<E>> union = Sets::union;
        this.assertCommutativeProperty(setContainsProcedure, intersect, setA, setB, setC, elements);
        this.assertAssociativeProperty(setContainsProcedure, intersect, setA, setB, setC, elements);
        this.assertDistributiveProperty(setsEqualProcedure, intersect, union, setA, setB, setC);
    }

    private <E> void assertCommutativeProperty(Procedure2<Set<E>, E[]> setContainsProcedure, Function2<Set<E>, Set<E>, Set<E>> function, Set<E> setA, Set<E> setB, Set<E> setC, E... elements) {
        Set<E> aXbXc = function.value(function.value(setA, setB), setC);
        Set<E> aXcXb = function.value(function.value(setA, setC), setB);
        Set<E> bXaXc = function.value(function.value(setB, setA), setC);
        Set<E> bXcXa = function.value(function.value(setB, setC), setA);
        Set<E> cXaXb = function.value(function.value(setC, setA), setB);
        Set<E> cXbXa = function.value(function.value(setC, setB), setA);
        this.assertAllContainExactly(setContainsProcedure, Lists.mutable.with(aXbXc, aXcXb, bXaXc, bXcXa, cXaXb, cXbXa), elements);
    }

    private <E> void assertAssociativeProperty(Procedure2<Set<E>, E[]> setContainsProcedure, Function2<Set<E>, Set<E>, Set<E>> function, Set<E> setA, Set<E> setB, Set<E> setC, E... elements) {
        setContainsProcedure.value(function.value(function.value(setA, setB), setC), elements);
        setContainsProcedure.value(function.value(setA, function.value(setB, setC)), elements);
    }

    private <E> void assertDistributiveProperty(Procedure2<Set<E>, Set<E>> setsEqualProcedure, Function2<Set<E>, Set<E>, Set<E>> function1, Function2<Set<E>, Set<E>, Set<E>> function2, Set<E> setA, Set<E> setB, Set<E> setC) {
    }

    private <E> void assertIdentityProperty(Procedure2<Set<E>, E[]> setContainsProcedure, Function2<Set<E>, Set<E>, Set<E>> function, Set<E> setA, Set<E> setB, Set<E> setC, E... elements) {
        Set<E> aXbXc = function.value(function.value(setA, setB), setC);
        Set<E> empty = new TreeSet<>();
        setContainsProcedure.value(function.value(aXbXc, empty), elements);
        setContainsProcedure.value(function.value(empty, aXbXc), elements);
    }

    private <E> void assertAllContainExactly(Procedure2<Set<E>, E[]> setContainsProcedure, Collection<Set<E>> sets, E... elements) {
        for (Set<E> set : sets) {
            setContainsProcedure.value(set, elements);
        }
    }

    private <E> void assertSetsEqualAndSorted(Set<E> setA, Set<E> setB) {
        Verify.assertSetsEqual(setA, setB);
        Object[] expectedItems = setB.toArray((E[]) new Object[setB.size()]);
        assertEquals(Lists.mutable.with(expectedItems), FastList.newList(setA));
    }

    private <E> void assertForwardAndBackward(Procedure2<Set<E>, E[]> setContainsProcedure, Function2<Set<E>, Set<E>, Set<E>> function, Set<E> setA, Set<E> setB, E[] forwardResults, E[] backwardResults) {
        Set<E> results1 = function.value(setA, setB);
        setContainsProcedure.value(results1, forwardResults);
        Set<E> results2 = function.value(setB, setA);
        setContainsProcedure.value(results2, backwardResults);
    }

    private <E> Procedure2<Set<E>, E[]> containsExactlyProcedure() {
        return (set, elements) -> assertEquals(UnifiedSet.newSetWith(elements), set);
    }

    private <E> Procedure2<Set<E>, E[]> containsExactlyInOrderProcedure() {
        return (set, elements) -> assertEquals(Lists.mutable.with((Object[]) elements), FastList.newList(set));
    }

    private void assertPresizedSetSizeEquals(int initialCapacity, UnifiedSet<String> set) {
        try {
            Field tableField = UnifiedSet.class.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] table = (Object[]) tableField.get(set);
            int size = (int) Math.ceil(initialCapacity / 0.75f);
            int capacity = 1;
            while (capacity < size) {
                capacity <<= 1;
            }
            assertEquals(capacity, table.length);
        } catch (SecurityException ignored) {
            fail("Unable to modify the visibility of the field 'table' on UnifiedSet");
        } catch (NoSuchFieldException ignored) {
            fail("No field named 'table' in UnifiedSet");
        } catch (IllegalAccessException ignored) {
            fail("No access to the field 'table' in UnifiedSet");
        }
    }

    @Test
    public void emptySet_1() {
        assertTrue(Sets.immutable.empty().isEmpty());
    }

    @Test
    public void emptySet_2() {
        assertSame(Sets.immutable.empty(), Sets.immutable.empty());
    }

    @Test
    public void emptySet_3() {
        Verify.assertPostSerializedIdentity(Sets.immutable.empty());
    }

    @Test
    public void newSetWith_1() {
        assertSame(Sets.immutable.empty(), Sets.immutable.of(Sets.immutable.empty().toArray()));
    }

    @Test
    public void newSetWith_2() {
        Verify.assertSize(1, Sets.immutable.of(1).castToSet());
    }

    @Test
    public void newSetWith_3() {
        Verify.assertSize(1, Sets.immutable.of(1, 1).castToSet());
    }

    @Test
    public void newSetWith_4() {
        Verify.assertSize(1, Sets.immutable.of(1, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_5() {
        Verify.assertSize(1, Sets.immutable.of(1, 1, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_6() {
        Verify.assertSize(1, Sets.immutable.of(1, 1, 1, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_7() {
        Verify.assertSize(2, Sets.immutable.of(1, 1, 1, 1, 2).castToSet());
    }

    @Test
    public void newSetWith_8() {
        Verify.assertSize(2, Sets.immutable.of(2, 1, 1, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_9() {
        Verify.assertSize(2, Sets.immutable.of(1, 2, 1, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_10() {
        Verify.assertSize(2, Sets.immutable.of(1, 1, 2, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_11() {
        Verify.assertSize(2, Sets.immutable.of(1, 1, 1, 2, 1).castToSet());
    }

    @Test
    public void newSetWith_12() {
        Verify.assertSize(2, Sets.immutable.of(1, 1, 1, 2).castToSet());
    }

    @Test
    public void newSetWith_13() {
        Verify.assertSize(2, Sets.immutable.of(2, 1, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_14() {
        Verify.assertSize(2, Sets.immutable.of(1, 2, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_15() {
        Verify.assertSize(2, Sets.immutable.of(1, 1, 2, 1).castToSet());
    }

    @Test
    public void newSetWith_16() {
        Verify.assertSize(2, Sets.immutable.of(1, 1, 2).castToSet());
    }

    @Test
    public void newSetWith_17() {
        Verify.assertSize(2, Sets.immutable.of(2, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_18() {
        Verify.assertSize(2, Sets.immutable.of(1, 2, 1).castToSet());
    }

    @Test
    public void newSetWith_19() {
        Verify.assertSize(2, Sets.immutable.of(1, 2).castToSet());
    }

    @Test
    public void newSetWith_20() {
        Verify.assertSize(3, Sets.immutable.of(1, 2, 3).castToSet());
    }

    @Test
    public void newSetWith_21() {
        Verify.assertSize(3, Sets.immutable.of(1, 2, 3, 1).castToSet());
    }

    @Test
    public void newSetWith_22() {
        Verify.assertSize(3, Sets.immutable.of(2, 1, 3, 1).castToSet());
    }

    @Test
    public void newSetWith_23() {
        Verify.assertSize(3, Sets.immutable.of(2, 3, 1, 1).castToSet());
    }

    @Test
    public void newSetWith_24() {
        Verify.assertSize(3, Sets.immutable.of(2, 1, 1, 3).castToSet());
    }

    @Test
    public void newSetWith_25() {
        Verify.assertSize(3, Sets.immutable.of(1, 1, 2, 3).castToSet());
    }

    @Test
    public void newSetWith_26() {
        Verify.assertSize(4, Sets.immutable.of(1, 2, 3, 4).castToSet());
    }

    @Test
    public void newSetWith_27() {
        Verify.assertSize(4, Sets.immutable.of(1, 2, 3, 4, 1).castToSet());
    }
}
