package org.eclipse.collections.impl.list.immutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.HashingStrategy;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.collection.ImmutableCollection;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.collection.primitive.ImmutableBooleanCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.partition.list.PartitionImmutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.ObjectIntProcedures;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.collection.immutable.AbstractImmutableCollectionTestCase;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.IntSets;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.eclipse.collections.impl.utility.Iterate;
import org.eclipse.collections.impl.utility.ListIterate;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractImmutableListTestCase_Purified extends AbstractImmutableCollectionTestCase {

    @Override
    protected abstract ImmutableList<Integer> classUnderTest();

    @Override
    protected <T> MutableList<T> newMutable() {
        return FastList.newList();
    }

    @Test
    public void chunk_large_size_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().chunk(10).getFirst());
    }

    @Test
    public void chunk_large_size_2() {
        Verify.assertInstanceOf(ImmutableList.class, this.classUnderTest().chunk(10).getFirst());
    }

    @Test
    public void dropWhile_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().dropWhile(Predicates.lessThan(0)));
    }

    @Test
    public void dropWhile_2() {
        assertEquals(Lists.immutable.of(), this.classUnderTest().dropWhile(Predicates.greaterThan(0)));
    }
}
