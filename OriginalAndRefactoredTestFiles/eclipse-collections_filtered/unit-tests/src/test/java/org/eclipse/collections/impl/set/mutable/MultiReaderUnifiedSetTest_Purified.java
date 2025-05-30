package org.eclipse.collections.impl.set.mutable;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.UnsortedSetIterable;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiReaderUnifiedSetTest_Purified extends MultiReaderMutableCollectionTestCase {

    @Override
    protected <T> MutableSet<T> newWith(T... littleElements) {
        return MultiReaderUnifiedSet.newSetWith(littleElements);
    }

    private void assertIsEmpty(boolean isEmpty, MultiReaderUnifiedSet<?> set) {
        assertEquals(isEmpty, set.isEmpty());
        assertEquals(!isEmpty, set.notEmpty());
    }

    private void verifyDelegateIsUnmodifiable(MutableSet<Integer> delegate) {
        assertThrows(UnsupportedOperationException.class, () -> delegate.add(2));
        assertThrows(UnsupportedOperationException.class, () -> delegate.remove(0));
    }

    @Override
    @Test
    public void select_1() {
        Verify.assertContainsAll(MultiReaderUnifiedSet.newSetWith(1, 2, 3, 4, 5).select(Predicates.lessThan(3)), 1, 2);
    }

    @Override
    @Test
    public void select_2() {
        Verify.assertContainsAll(MultiReaderUnifiedSet.newSetWith(-1, 2, 3, 4, 5).select(Predicates.lessThan(3), FastList.newList()), -1, 2);
    }

    @Override
    @Test
    public void reject_1() {
        Verify.assertContainsAll(MultiReaderUnifiedSet.newSetWith(1, 2, 3, 4).reject(Predicates.lessThan(3)), 3, 4);
    }

    @Override
    @Test
    public void reject_2() {
        Verify.assertContainsAll(MultiReaderUnifiedSet.newSetWith(1, 2, 3, 4).reject(Predicates.lessThan(3), FastList.newList()), 3, 4);
    }

    @Override
    @Test
    public void getFirst_1() {
        assertNotNull(this.newWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    public void getFirst_2() {
        assertNull(this.newWith().getFirst());
    }

    @Override
    @Test
    public void getLast_1() {
        assertNotNull(this.newWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void getLast_2() {
        assertNull(this.newWith().getLast());
    }
}
