package org.eclipse.collections.impl.bag.mutable;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MultiReaderBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.PartitionMutableCollection;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.factory.primitive.IntPredicates;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.parallel.ParallelIterate;
import org.eclipse.collections.impl.set.mutable.MultiReaderMutableCollectionTestCase;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiReaderHashBagTest_Purified extends MultiReaderMutableCollectionTestCase {

    @Override
    protected <T> MultiReaderHashBag<T> newWith(T... littleElements) {
        return MultiReaderHashBag.newBagWith(littleElements);
    }

    private void verifyDelegateIsUnmodifiable(MutableBag<Integer> delegate) {
        assertThrows(UnsupportedOperationException.class, () -> delegate.add(2));
        assertThrows(UnsupportedOperationException.class, () -> delegate.remove(0));
    }

    @Override
    @Test
    public void newEmpty_1() {
        Verify.assertInstanceOf(MultiReaderHashBag.class, MultiReaderHashBag.newBag().newEmpty());
    }

    @Override
    @Test
    public void newEmpty_2() {
        Verify.assertEmpty(MultiReaderHashBag.<Integer>newBagWith(null, null).newEmpty());
    }

    @Override
    @Test
    public void reject_1() {
        Verify.assertContainsAll(MultiReaderHashBag.newBagWith(1, 1, 2, 3, 4).reject(Predicates.lessThan(3)), 3, 4);
    }

    @Override
    @Test
    public void reject_2() {
        Verify.assertContainsAll(MultiReaderHashBag.newBagWith(1, 2, 3, 3, 4).reject(Predicates.lessThan(3), HashBag.newBag()), 3, 4);
    }

    @Test
    public void toStringOfItemToCount_1() {
        assertEquals("{}", MultiReaderHashBag.newBagWith().toStringOfItemToCount());
    }

    @Test
    public void toStringOfItemToCount_2() {
        assertEquals("{1=3}", MultiReaderHashBag.newBagWith(1, 1, 1).toStringOfItemToCount());
    }

    @Test
    public void toStringOfItemToCount_3() {
        String actual = MultiReaderHashBag.newBagWith(1, 2, 2).toStringOfItemToCount();
        assertTrue("{1=1, 2=2}".equals(actual) || "{2=2, 1=1}".equals(actual));
    }

    @Override
    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(MultiReaderHashBag.newBag());
    }

    @Override
    @Test
    public void isEmpty_2() {
        Verify.assertNotEmpty(MultiReaderHashBag.newBagWith(1, 1));
    }
}
