package org.eclipse.collections.impl.set.immutable;

import java.util.Collections;
import java.util.List;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.collection.ImmutableCollection;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.set.ImmutableSetMultimap;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.UnsortedSetIterable;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.collection.immutable.AbstractImmutableCollectionTestCase;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.multimap.set.UnifiedSetMultimap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractImmutableSetTestCase_Purified extends AbstractImmutableCollectionTestCase {

    @Override
    protected abstract ImmutableSet<Integer> classUnderTest();

    @Override
    protected <T> MutableSet<T> newMutable() {
        return UnifiedSet.newSet();
    }

    @Test
    public void chunk_large_size_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().chunk(10).getFirst());
    }

    @Test
    public void chunk_large_size_2() {
        Verify.assertInstanceOf(ImmutableSet.class, this.classUnderTest().chunk(10).getFirst());
    }
}
