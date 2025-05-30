package org.eclipse.collections.impl.list.immutable;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.list.PartitionImmutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.block.factory.ObjectIntProcedures;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ImmutableEmptyListTest_Purified extends AbstractImmutableListTestCase {

    @Override
    protected ImmutableList<Integer> classUnderTest() {
        return Lists.immutable.empty();
    }

    @Override
    public void allSatisfyWith() {
        ImmutableList<Integer> integers = this.classUnderTest();
        assertTrue(integers.allSatisfyWith(ERROR_THROWING_PREDICATE_2, Integer.class));
    }

    @Override
    public void noneSatisfy() {
        ImmutableList<Integer> integers = this.classUnderTest();
        assertTrue(integers.noneSatisfy(ERROR_THROWING_PREDICATE));
    }

    @Override
    public void noneSatisfyWith() {
        ImmutableList<Integer> integers = this.classUnderTest();
        assertTrue(integers.noneSatisfyWith(ERROR_THROWING_PREDICATE_2, Integer.class));
    }

    @Override
    public void anySatisfyWith() {
        ImmutableList<Integer> integers = this.classUnderTest();
        assertFalse(integers.anySatisfyWith(ERROR_THROWING_PREDICATE_2, Integer.class));
    }

    @Test
    public void newWithout_1() {
        assertSame(Lists.immutable.empty(), Lists.immutable.empty().newWithout(1));
    }

    @Test
    public void newWithout_2() {
        assertSame(Lists.immutable.empty(), Lists.immutable.empty().newWithoutAll(Interval.oneTo(3)));
    }

    @Override
    @Test
    public void chunk_large_size_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().chunk(10));
    }

    @Override
    @Test
    public void chunk_large_size_2() {
        Verify.assertInstanceOf(ImmutableList.class, this.classUnderTest().chunk(10));
    }
}
