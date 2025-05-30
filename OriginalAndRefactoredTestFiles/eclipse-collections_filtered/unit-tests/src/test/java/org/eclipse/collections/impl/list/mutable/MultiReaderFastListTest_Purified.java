package org.eclipse.collections.impl.list.mutable;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.MaxSizeFunction;
import org.eclipse.collections.impl.block.function.MinSizeFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.ListIterate;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiReaderFastListTest_Purified extends AbstractListTestCase {

    @Override
    protected <T> MultiReaderFastList<T> newWith(T... littleElements) {
        return MultiReaderFastList.newListWith(littleElements);
    }

    private MutableList<Integer> getIntegerList() {
        return MultiReaderFastList.newList(Interval.toReverseList(1, 5));
    }

    private void assertIteratorThrows(Iterator<?> iterator) {
        assertThrows(NullPointerException.class, iterator::hasNext);
    }

    private void assertIteratorThrows(MutableList<?> list) {
        assertThrows(NullPointerException.class, list::iterator);
    }

    private void verifyDelegateIsUnmodifiable(MutableList<Integer> delegate) {
        assertThrows(UnsupportedOperationException.class, () -> delegate.add(2));
        assertThrows(UnsupportedOperationException.class, () -> delegate.remove(0));
    }

    @Override
    @Test
    public void newEmpty_1() {
        Verify.assertInstanceOf(MultiReaderFastList.class, MultiReaderFastList.newList().newEmpty());
    }

    @Override
    @Test
    public void newEmpty_2() {
        Verify.assertEmpty(MultiReaderFastList.<Integer>newListWith(null, null).newEmpty());
    }

    @Override
    @Test
    public void getFirst_1() {
        assertNull(MultiReaderFastList.newList().getFirst());
    }

    @Override
    @Test
    public void getFirst_2() {
        assertEquals(Integer.valueOf(1), MultiReaderFastList.newListWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    public void getLast_1() {
        assertNull(MultiReaderFastList.newList().getLast());
    }

    @Override
    @Test
    public void getLast_2() {
        assertNotEquals(Integer.valueOf(1), MultiReaderFastList.newListWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void getLast_3() {
        assertEquals(Integer.valueOf(3), MultiReaderFastList.newListWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(MultiReaderFastList.newList());
    }

    @Override
    @Test
    public void isEmpty_2() {
        Verify.assertNotEmpty(MultiReaderFastList.newListWith(1, 2));
    }

    @Override
    @Test
    public void isEmpty_3() {
        assertTrue(MultiReaderFastList.newListWith(1, 2).notEmpty());
    }

    @Override
    @Test
    public void reject_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3)), 3, 4);
    }

    @Override
    @Test
    public void reject_2() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3), UnifiedSet.newSet()), 3, 4);
    }
}
