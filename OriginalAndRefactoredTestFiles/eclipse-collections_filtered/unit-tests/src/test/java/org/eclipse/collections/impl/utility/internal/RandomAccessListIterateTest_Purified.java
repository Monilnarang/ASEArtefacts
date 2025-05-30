package org.eclipse.collections.impl.utility.internal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.Procedures2;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.MaxSizeFunction;
import org.eclipse.collections.impl.block.function.MinSizeFunction;
import org.eclipse.collections.impl.block.procedure.DoNothingProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.AddToList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.eclipse.collections.impl.factory.Iterables.mList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RandomAccessListIterateTest_Purified {

    private void assertCollectWithIndex(List<Boolean> list) {
        MutableList<ObjectIntPair<Boolean>> newCollection = RandomAccessListIterate.collectWithIndex(list, PrimitiveTuples::pair);
        Verify.assertListsEqual(newCollection, Lists.mutable.with(PrimitiveTuples.pair(Boolean.TRUE, 0), PrimitiveTuples.pair(Boolean.FALSE, 1), PrimitiveTuples.pair(null, 2)));
        List<ObjectIntPair<Boolean>> newCollection2 = RandomAccessListIterate.collectWithIndex(list, PrimitiveTuples::pair, new ArrayList<>());
        Verify.assertListsEqual(newCollection2, Lists.mutable.with(PrimitiveTuples.pair(Boolean.TRUE, 0), PrimitiveTuples.pair(Boolean.FALSE, 1), PrimitiveTuples.pair(null, 2)));
    }

    private MutableList<Integer> getIntegerList() {
        return Interval.toReverseList(1, 5);
    }

    private static class FailProcedure2 implements Procedure2<Object, Integer> {

        private static final long serialVersionUID = 1L;

        @Override
        public void value(Object argument1, Integer argument2) {
            fail();
        }
    }

    @Test
    public void removeIf_1() {
        assertTrue(RandomAccessListIterate.removeIf(FastList.newListWith(1, 2, 3), Predicates.greaterThan(1)));
    }

    @Test
    public void removeIf_2() {
        assertTrue(RandomAccessListIterate.removeIf(FastList.newListWith(1, 2, 3), Predicates.greaterThan(0)));
    }

    @Test
    public void removeIf_3() {
        assertFalse(RandomAccessListIterate.removeIf(FastList.newListWith(1, 2, 3), Predicates.greaterThan(4)));
    }

    @Test
    public void removeIf_4() {
        assertFalse(RandomAccessListIterate.removeIf(FastList.newList(), Predicates.greaterThan(4)));
    }

    @Test
    public void removeIfWith_1() {
        assertTrue(RandomAccessListIterate.removeIfWith(FastList.newListWith(1, 2, 3), Predicates2.greaterThan(), 1));
    }

    @Test
    public void removeIfWith_2() {
        assertTrue(RandomAccessListIterate.removeIfWith(FastList.newListWith(1, 2, 3), Predicates2.greaterThan(), 0));
    }

    @Test
    public void removeIfWith_3() {
        assertFalse(RandomAccessListIterate.removeIfWith(FastList.newListWith(1, 2, 3), Predicates2.greaterThan(), 4));
    }

    @Test
    public void removeIfWith_4() {
        assertFalse(RandomAccessListIterate.removeIfWith(FastList.newList(), Predicates2.greaterThan(), 1));
    }
}
