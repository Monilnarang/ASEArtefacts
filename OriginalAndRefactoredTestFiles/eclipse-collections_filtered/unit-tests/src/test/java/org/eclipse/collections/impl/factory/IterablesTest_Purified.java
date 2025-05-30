package org.eclipse.collections.impl.factory;

import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.sorted.ImmutableSortedMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;

public class IterablesTest_Purified {

    public void assertEqualsAndInstanceOf(Object expected, Object actual, Class<?> clazz) {
        Verify.assertEqualsAndHashCode(expected, actual);
        Verify.assertInstanceOf(clazz, actual);
    }

    @Test
    public void immutableLists_1() {
        this.assertEqualsAndInstanceOf(FastList.newList().toImmutable(), Iterables.iList(), ImmutableList.class);
    }

    @Test
    public void immutableLists_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1), Iterables.iList(1), ImmutableList.class);
    }

    @Test
    public void immutableLists_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2), Iterables.iList(1, 2), ImmutableList.class);
    }

    @Test
    public void immutableLists_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3), Iterables.iList(1, 2, 3), ImmutableList.class);
    }

    @Test
    public void immutableLists_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4), Iterables.iList(1, 2, 3, 4), ImmutableList.class);
    }

    @Test
    public void immutableLists_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5), Iterables.iList(1, 2, 3, 4, 5), ImmutableList.class);
    }

    @Test
    public void immutableLists_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6), Iterables.iList(1, 2, 3, 4, 5, 6), ImmutableList.class);
    }

    @Test
    public void immutableLists_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7), Iterables.iList(1, 2, 3, 4, 5, 6, 7), ImmutableList.class);
    }

    @Test
    public void immutableLists_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8), Iterables.iList(1, 2, 3, 4, 5, 6, 7, 8), ImmutableList.class);
    }

    @Test
    public void immutableLists_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9), Iterables.iList(1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableList.class);
    }

    @Test
    public void immutableLists_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10), Iterables.iList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableList.class);
    }

    @Test
    public void immutableLists_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11), Iterables.iList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableList.class);
    }

    @Test
    public void mutableLists_1() {
        this.assertEqualsAndInstanceOf(FastList.newList(), Iterables.mList(), MutableList.class);
    }

    @Test
    public void mutableLists_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1), Iterables.mList(1), MutableList.class);
    }

    @Test
    public void mutableLists_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2), Iterables.mList(1, 2), MutableList.class);
    }

    @Test
    public void mutableLists_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3), Iterables.mList(1, 2, 3), MutableList.class);
    }

    @Test
    public void mutableLists_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4), Iterables.mList(1, 2, 3, 4), MutableList.class);
    }

    @Test
    public void mutableLists_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5), Iterables.mList(1, 2, 3, 4, 5), MutableList.class);
    }

    @Test
    public void mutableLists_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6), Iterables.mList(1, 2, 3, 4, 5, 6), MutableList.class);
    }

    @Test
    public void mutableLists_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7), Iterables.mList(1, 2, 3, 4, 5, 6, 7), MutableList.class);
    }

    @Test
    public void mutableLists_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8), Iterables.mList(1, 2, 3, 4, 5, 6, 7, 8), MutableList.class);
    }

    @Test
    public void mutableLists_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9), Iterables.mList(1, 2, 3, 4, 5, 6, 7, 8, 9), MutableList.class);
    }

    @Test
    public void mutableLists_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10), Iterables.mList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableList.class);
    }

    @Test
    public void mutableLists_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11), Iterables.mList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableList.class);
    }

    @Test
    public void immutableSets_1() {
        this.assertEqualsAndInstanceOf(UnifiedSet.newSet().toImmutable(), Iterables.iSet(), ImmutableSet.class);
    }

    @Test
    public void immutableSets_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSet(), Iterables.iSet(1), ImmutableSet.class);
    }

    @Test
    public void immutableSets_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSet(), Iterables.iSet(1, 2), ImmutableSet.class);
    }

    @Test
    public void immutableSets_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSet(), Iterables.iSet(1, 2, 3), ImmutableSet.class);
    }

    @Test
    public void immutableSets_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSet(), Iterables.iSet(1, 2, 3, 4), ImmutableSet.class);
    }

    @Test
    public void immutableSets_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSet(), Iterables.iSet(1, 2, 3, 4, 5), ImmutableSet.class);
    }

    @Test
    public void immutableSets_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSet(), Iterables.iSet(1, 2, 3, 4, 5, 6), ImmutableSet.class);
    }

    @Test
    public void immutableSets_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSet(), Iterables.iSet(1, 2, 3, 4, 5, 6, 7), ImmutableSet.class);
    }

    @Test
    public void immutableSets_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSet(), Iterables.iSet(1, 2, 3, 4, 5, 6, 7, 8), ImmutableSet.class);
    }

    @Test
    public void immutableSets_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSet(), Iterables.iSet(1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableSet.class);
    }

    @Test
    public void immutableSets_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSet(), Iterables.iSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableSet.class);
    }

    @Test
    public void immutableSets_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSet(), Iterables.iSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableSet.class);
    }

    @Test
    public void mutableSets_1() {
        this.assertEqualsAndInstanceOf(UnifiedSet.newSet(), Iterables.mSet(), MutableSet.class);
    }

    @Test
    public void mutableSets_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSet(), Iterables.mSet(1), MutableSet.class);
    }

    @Test
    public void mutableSets_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSet(), Iterables.mSet(1, 2), MutableSet.class);
    }

    @Test
    public void mutableSets_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSet(), Iterables.mSet(1, 2, 3), MutableSet.class);
    }

    @Test
    public void mutableSets_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSet(), Iterables.mSet(1, 2, 3, 4), MutableSet.class);
    }

    @Test
    public void mutableSets_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSet(), Iterables.mSet(1, 2, 3, 4, 5), MutableSet.class);
    }

    @Test
    public void mutableSets_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSet(), Iterables.mSet(1, 2, 3, 4, 5, 6), MutableSet.class);
    }

    @Test
    public void mutableSets_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSet(), Iterables.mSet(1, 2, 3, 4, 5, 6, 7), MutableSet.class);
    }

    @Test
    public void mutableSets_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSet(), Iterables.mSet(1, 2, 3, 4, 5, 6, 7, 8), MutableSet.class);
    }

    @Test
    public void mutableSets_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSet(), Iterables.mSet(1, 2, 3, 4, 5, 6, 7, 8, 9), MutableSet.class);
    }

    @Test
    public void mutableSets_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSet(), Iterables.mSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableSet.class);
    }

    @Test
    public void mutableSets_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSet(), Iterables.mSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableSet.class);
    }

    @Test
    public void mutableBags_1() {
        this.assertEqualsAndInstanceOf(HashBag.newBag(), Iterables.mBag(), MutableBag.class);
    }

    @Test
    public void mutableBags_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toBag(), Iterables.mBag(1), MutableBag.class);
    }

    @Test
    public void mutableBags_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toBag(), Iterables.mBag(1, 2), MutableBag.class);
    }

    @Test
    public void mutableBags_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toBag(), Iterables.mBag(1, 2, 3), MutableBag.class);
    }

    @Test
    public void mutableBags_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toBag(), Iterables.mBag(1, 2, 3, 4), MutableBag.class);
    }

    @Test
    public void mutableBags_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toBag(), Iterables.mBag(1, 2, 3, 4, 5), MutableBag.class);
    }

    @Test
    public void mutableBags_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toBag(), Iterables.mBag(1, 2, 3, 4, 5, 6), MutableBag.class);
    }

    @Test
    public void mutableBags_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toBag(), Iterables.mBag(1, 2, 3, 4, 5, 6, 7), MutableBag.class);
    }

    @Test
    public void mutableBags_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toBag(), Iterables.mBag(1, 2, 3, 4, 5, 6, 7, 8), MutableBag.class);
    }

    @Test
    public void mutableBags_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toBag(), Iterables.mBag(1, 2, 3, 4, 5, 6, 7, 8, 9), MutableBag.class);
    }

    @Test
    public void mutableBags_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toBag(), Iterables.mBag(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableBag.class);
    }

    @Test
    public void mutableBags_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toBag(), Iterables.mBag(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableBag.class);
    }

    @Test
    public void immutableBags_1() {
        this.assertEqualsAndInstanceOf(HashBag.newBag(), Iterables.iBag(), ImmutableBag.class);
    }

    @Test
    public void immutableBags_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toBag(), Iterables.iBag(1), ImmutableBag.class);
    }

    @Test
    public void immutableBags_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toBag(), Iterables.iBag(1, 2), ImmutableBag.class);
    }

    @Test
    public void immutableBags_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toBag(), Iterables.iBag(1, 2, 3), ImmutableBag.class);
    }

    @Test
    public void immutableBags_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toBag(), Iterables.iBag(1, 2, 3, 4), ImmutableBag.class);
    }

    @Test
    public void immutableBags_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toBag(), Iterables.iBag(1, 2, 3, 4, 5), ImmutableBag.class);
    }

    @Test
    public void immutableBags_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toBag(), Iterables.iBag(1, 2, 3, 4, 5, 6), ImmutableBag.class);
    }

    @Test
    public void immutableBags_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toBag(), Iterables.iBag(1, 2, 3, 4, 5, 6, 7), ImmutableBag.class);
    }

    @Test
    public void immutableBags_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toBag(), Iterables.iBag(1, 2, 3, 4, 5, 6, 7, 8), ImmutableBag.class);
    }

    @Test
    public void immutableBags_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toBag(), Iterables.iBag(1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableBag.class);
    }

    @Test
    public void immutableBags_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toBag(), Iterables.iBag(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableBag.class);
    }

    @Test
    public void immutableBags_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toBag(), Iterables.iBag(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableBag.class);
    }

    @Test
    public void immutableSortedSets_1() {
        this.assertEqualsAndInstanceOf(TreeSortedSet.newSet(), Iterables.iSortedSet(), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedSet(), Iterables.iSortedSet(1), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedSet(), Iterables.iSortedSet(1, 2), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedSet(), Iterables.iSortedSet(1, 2, 3), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedSet(), Iterables.iSortedSet(1, 2, 3, 4), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSortedSet(), Iterables.iSortedSet(1, 2, 3, 4, 5), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSortedSet(), Iterables.iSortedSet(1, 2, 3, 4, 5, 6), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSortedSet(), Iterables.iSortedSet(1, 2, 3, 4, 5, 6, 7), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSortedSet(), Iterables.iSortedSet(1, 2, 3, 4, 5, 6, 7, 8), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSortedSet(), Iterables.iSortedSet(1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSortedSet(), Iterables.iSortedSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSets_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSortedSet(), Iterables.iSortedSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_1() {
        this.assertEqualsAndInstanceOf(TreeSortedSet.newSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder()), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_1() {
        this.assertEqualsAndInstanceOf(TreeSortedSet.newSet(Comparators.reverseNaturalOrder()), Iterables.iSortedSet(Comparators.reverseNaturalOrder()), ImmutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSortedSet(Comparators.reverseNaturalOrder()), Iterables.mSortedSet(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_1() {
        this.assertEqualsAndInstanceOf(TreeSortedSet.newSet(), Iterables.mSortedSet(), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedSet(), Iterables.mSortedSet(1), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedSet(), Iterables.mSortedSet(1, 2), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedSet(), Iterables.mSortedSet(1, 2, 3), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedSet(), Iterables.mSortedSet(1, 2, 3, 4), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSortedSet(), Iterables.mSortedSet(1, 2, 3, 4, 5), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_7() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSortedSet(), Iterables.mSortedSet(1, 2, 3, 4, 5, 6), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_8() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSortedSet(), Iterables.mSortedSet(1, 2, 3, 4, 5, 6, 7), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_9() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSortedSet(), Iterables.mSortedSet(1, 2, 3, 4, 5, 6, 7, 8), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_10() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSortedSet(), Iterables.mSortedSet(1, 2, 3, 4, 5, 6, 7, 8, 9), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_11() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSortedSet(), Iterables.mSortedSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets_12() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSortedSet(), Iterables.mSortedSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedMaps_1() {
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(), Iterables.mSortedMap(), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMaps_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.mSortedMap(1, 1), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMaps_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.mSortedMap(1, 1, 2, 2), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMaps_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.mSortedMap(1, 1, 2, 2, 3, 3), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMaps_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.mSortedMap(1, 1, 2, 2, 3, 3, 4, 4), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMapsWithComparator_1() {
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(Comparators.reverseNaturalOrder()), Iterables.mSortedMap(Comparators.reverseNaturalOrder()), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMapsWithComparator_2() {
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(Comparators.reverseNaturalOrder()), Iterables.mSortedMap(null), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMapsWithComparator_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()), Iterables.mSortedMap(Comparators.reverseNaturalOrder(), 1, 1), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMapsWithComparator_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()), Iterables.mSortedMap(Comparators.reverseNaturalOrder(), 1, 1, 2, 2), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMapsWithComparator_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()), Iterables.mSortedMap(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMapsWithComparator_6() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()), Iterables.mSortedMap(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3, 4, 4), MutableSortedMap.class);
    }

    @Test
    public void immutableSortedMaps_1() {
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(), Iterables.iSortedMap(), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMaps_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.iSortedMap(1, 1), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMaps_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.iSortedMap(1, 1, 2, 2), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMaps_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.iSortedMap(1, 1, 2, 2, 3, 3), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMaps_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.iSortedMap(1, 1, 2, 2, 3, 3, 4, 4), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMapsWithComparator_1() {
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(Comparators.reverseNaturalOrder()), Iterables.iSortedMap(Comparators.reverseNaturalOrder()), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMapsWithComparator_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()), Iterables.iSortedMap(Comparators.reverseNaturalOrder(), 1, 1), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMapsWithComparator_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()), Iterables.iSortedMap(Comparators.reverseNaturalOrder(), 1, 1, 2, 2), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMapsWithComparator_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()), Iterables.iSortedMap(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMapsWithComparator_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()), Iterables.iSortedMap(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3, 4, 4), ImmutableSortedMap.class);
    }

    @Test
    public void mutableMaps_1() {
        this.assertEqualsAndInstanceOf(UnifiedMap.newMap(), Iterables.mMap(), MutableMap.class);
    }

    @Test
    public void mutableMaps_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.mMap(1, 1), MutableMap.class);
    }

    @Test
    public void mutableMaps_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.mMap(1, 1, 2, 2), MutableMap.class);
    }

    @Test
    public void mutableMaps_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.mMap(1, 1, 2, 2, 3, 3), MutableMap.class);
    }

    @Test
    public void mutableMaps_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.mMap(1, 1, 2, 2, 3, 3, 4, 4), MutableMap.class);
    }

    @Test
    public void immutableMaps_1() {
        this.assertEqualsAndInstanceOf(UnifiedMap.newMap(), Iterables.iMap(), ImmutableMap.class);
    }

    @Test
    public void immutableMaps_2() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.iMap(1, 1), ImmutableMap.class);
    }

    @Test
    public void immutableMaps_3() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.iMap(1, 1, 2, 2), ImmutableMap.class);
    }

    @Test
    public void immutableMaps_4() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.iMap(1, 1, 2, 2, 3, 3), ImmutableMap.class);
    }

    @Test
    public void immutableMaps_5() {
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toMap(Functions.getPassThru(), Functions.getPassThru()), Iterables.iMap(1, 1, 2, 2, 3, 3, 4, 4), ImmutableMap.class);
    }
}
