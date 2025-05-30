package org.eclipse.collections.impl.bag.sorted.mutable;

import java.util.Comparator;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.factory.SortedBags;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MutableSortedBagFactoryTest_Purified {

    @Test
    public void ofEmpty_1() {
        assertEquals(TreeBag.newBag(), SortedBags.mutable.of());
    }

    @Test
    public void ofEmpty_2() {
        assertEquals(TreeBag.newBag(Comparators.reverseNaturalOrder()), SortedBags.mutable.of(Comparators.reverseNaturalOrder()));
    }

    @Test
    public void withEmpty_1() {
        assertEquals(TreeBag.newBag(), SortedBags.mutable.with());
    }

    @Test
    public void withEmpty_2() {
        assertEquals(TreeBag.newBag(Comparators.reverseNaturalOrder()), SortedBags.mutable.with(Comparators.reverseNaturalOrder()));
    }

    @Test
    public void ofElements_1() {
        assertEquals(TreeBag.newBagWith(1, 1, 2), SortedBags.mutable.of(1, 1, 2));
    }

    @Test
    public void ofElements_2() {
        assertEquals(TreeBag.newBagWith(Comparators.reverseNaturalOrder(), 1, 1, 2), SortedBags.mutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2));
    }

    @Test
    public void withElements_1() {
        assertEquals(TreeBag.newBagWith(1, 1, 2), SortedBags.mutable.with(1, 1, 2));
    }

    @Test
    public void withElements_2() {
        assertEquals(TreeBag.newBagWith(Comparators.reverseNaturalOrder(), 1, 1, 2), SortedBags.mutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2));
    }

    @Test
    public void empty_1() {
        assertEquals(TreeBag.newBag(), SortedBags.mutable.empty());
    }

    @Test
    public void empty_2() {
        assertEquals(TreeBag.newBag(Comparator.reverseOrder()), SortedBags.mutable.empty(Comparator.reverseOrder()));
    }
}
