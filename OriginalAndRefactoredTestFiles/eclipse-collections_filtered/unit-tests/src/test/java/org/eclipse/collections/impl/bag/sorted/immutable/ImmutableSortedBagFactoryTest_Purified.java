package org.eclipse.collections.impl.bag.sorted.immutable;

import java.util.Comparator;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.factory.SortedBags;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImmutableSortedBagFactoryTest_Purified {

    @Test
    public void empty_1() {
        assertEquals(TreeBag.newBag(), SortedBags.immutable.empty());
    }

    @Test
    public void empty_2() {
        Verify.assertInstanceOf(ImmutableSortedBag.class, SortedBags.immutable.empty());
    }

    @Test
    public void empty_3() {
        assertEquals(TreeBag.newBag(Comparators.reverseNaturalOrder()), SortedBags.immutable.empty(Comparators.reverseNaturalOrder()));
    }

    @Test
    public void empty_4() {
        Verify.assertInstanceOf(ImmutableSortedBag.class, SortedBags.immutable.empty(Comparators.reverseNaturalOrder()));
    }

    @Test
    public void ofAll_1() {
        assertEquals(new ImmutableSortedBagImpl<>(SortedBags.mutable.of(1, 1, 2)), SortedBags.immutable.ofAll(new ImmutableSortedBagImpl<>(TreeBag.newBagWith(1, 1, 2))));
    }

    @Test
    public void ofAll_2() {
        assertEquals(new ImmutableSortedBagImpl<>(SortedBags.mutable.of(1, 1, 2)), SortedBags.immutable.ofAll(FastList.newListWith(1, 1, 2)));
    }

    @Test
    public void ofAll_3() {
        assertEquals(new ImmutableSortedBagImpl<>(SortedBags.mutable.of(Comparators.reverseNaturalOrder(), 1, 1, 2)), SortedBags.immutable.ofAll(Comparators.reverseNaturalOrder(), FastList.newListWith(1, 1, 2)));
    }

    @Test
    public void ofSortedBag_1() {
        assertEquals(new ImmutableSortedBagImpl<>(SortedBags.immutable.of(1)), SortedBags.immutable.ofSortedBag(new ImmutableSortedBagImpl<>(TreeBag.newBagWith(1))));
    }

    @Test
    public void ofSortedBag_2() {
        assertEquals(new ImmutableSortedBagImpl<>(SortedBags.immutable.of(1)), SortedBags.immutable.ofSortedBag(TreeBag.newBagWith(1)));
    }

    @Test
    public void ofSortedBag_3() {
        assertEquals(SortedBags.immutable.of(Comparators.reverseNaturalOrder()), SortedBags.immutable.ofSortedBag(TreeBag.newBag(Comparators.reverseNaturalOrder())));
    }
}
