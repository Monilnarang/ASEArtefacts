package org.eclipse.collections.impl.set.sorted.mutable;

import java.util.Collections;
import java.util.TreeSet;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.collection.mutable.AbstractSynchronizedCollectionTestCase;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SynchronizedSortedSetTest_Purified extends AbstractSynchronizedCollectionTestCase {

    @Override
    protected <T> MutableSortedSet<T> newWith(T... littleElements) {
        return new SynchronizedSortedSet<>(SortedSetAdapter.adapt(new TreeSet<>(FastList.newListWith(littleElements))));
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
