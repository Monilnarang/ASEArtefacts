package org.eclipse.collections.impl.list.mutable;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.MaxSizeFunction;
import org.eclipse.collections.impl.block.function.MinSizeFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.block.procedure.CountProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.math.Sum;
import org.eclipse.collections.impl.math.SumProcedure;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.ClassComparer;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.LazyIterate;
import org.eclipse.collections.impl.utility.ListIterate;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.eclipse.collections.impl.factory.Iterables.mList;
import static org.eclipse.collections.impl.factory.Iterables.mSet;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FastListTest_Purified extends AbstractListTestCase {

    @Override
    protected <T> FastList<T> newWith(T... littleElements) {
        return FastList.newListWith(littleElements);
    }

    @Override
    @Test
    public void getFirst_1() {
        assertNull(FastList.<Integer>newList().getFirst());
    }

    @Override
    @Test
    public void getFirst_2() {
        assertEquals(Integer.valueOf(1), FastList.newListWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    public void getFirst_3() {
        assertNotEquals(Integer.valueOf(3), FastList.newListWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    public void getLast_1() {
        assertNull(FastList.<Integer>newList().getLast());
    }

    @Override
    @Test
    public void getLast_2() {
        assertNotEquals(Integer.valueOf(1), FastList.newListWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void getLast_3() {
        assertEquals(Integer.valueOf(3), FastList.newListWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(FastList.<Integer>newList());
    }

    @Override
    @Test
    public void isEmpty_2() {
        Verify.assertNotEmpty(FastList.newListWith(1, 2));
    }

    @Override
    @Test
    public void isEmpty_3() {
        assertTrue(FastList.newListWith(1, 2).notEmpty());
    }

    @Override
    @Test
    public void collectWith_1() {
        assertEquals(FastList.newListWith(2, 3, 4), FastList.newListWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1));
    }

    @Override
    @Test
    public void collectWith_2() {
        assertEquals(FastList.newListWith(2, 3, 4), FastList.newListWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, FastList.newList()));
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

    @Override
    @Test
    public void distinct_1() {
        Verify.assertListsEqual(this.newWith(5, 2, 3, 5, 4, 2).distinct(), this.newWith(5, 2, 3, 4));
    }

    @Override
    @Test
    public void distinct_2() {
        Verify.assertListsEqual(Interval.fromTo(1, 5).toList().distinct(), this.newWith(1, 2, 3, 4, 5));
    }
}
