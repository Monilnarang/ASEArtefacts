package org.eclipse.collections.impl.bag.sorted.mutable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnmodifiableSortedBagTest_Purified extends AbstractMutableSortedBagTestCase {

    @Override
    protected <T> MutableSortedBag<T> newWith(T... elements) {
        return TreeBag.newBagWith(elements).asUnmodifiable();
    }

    @SafeVarargs
    @Override
    protected final <T> MutableSortedBag<T> newWithOccurrences(ObjectIntPair<T>... elementsWithOccurrences) {
        MutableSortedBag<T> bag = TreeBag.newBag();
        for (int i = 0; i < elementsWithOccurrences.length; i++) {
            ObjectIntPair<T> itemToAdd = elementsWithOccurrences[i];
            bag.addOccurrences(itemToAdd.getOne(), itemToAdd.getTwo());
        }
        return bag.asUnmodifiable();
    }

    @Override
    protected <T> MutableSortedBag<T> newWith(Comparator<? super T> comparator, T... elements) {
        return TreeBag.newBagWith(comparator, elements).asUnmodifiable();
    }

    @Override
    public void equalsAndHashCode() {
        super.equalsAndHashCode();
        Verify.assertInstanceOf(UnmodifiableSortedBag.class, SerializeTestHelper.serializeDeserialize(this.newWith(Comparators.reverseNaturalOrder(), 1, 2, 3)));
        Verify.assertInstanceOf(UnmodifiableSortedBag.class, SerializeTestHelper.serializeDeserialize(this.newWith(1, 2, 3)));
    }

    @Test
    public void testAsUnmodifiable_1() {
        Verify.assertInstanceOf(UnmodifiableSortedBag.class, this.newWith().asUnmodifiable());
    }

    @Test
    public void testAsUnmodifiable_2() {
        MutableSortedBag<Object> bag = this.newWith();
        assertSame(bag, bag.asUnmodifiable());
    }
}
