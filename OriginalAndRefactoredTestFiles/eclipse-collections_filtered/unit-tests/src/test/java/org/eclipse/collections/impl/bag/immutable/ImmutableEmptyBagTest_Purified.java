package org.eclipse.collections.impl.bag.immutable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.primitive.ImmutableBooleanBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.partition.bag.PartitionImmutableBag;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iBag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmutableEmptyBagTest_Purified extends ImmutableBagTestCase {

    public static final Predicate<String> ERROR_THROWING_PREDICATE = each -> {
        throw new AssertionError();
    };

    public static final Predicates2<String, Class<Integer>> ERROR_THROWING_PREDICATE_2 = new Predicates2<String, Class<Integer>>() {

        public boolean accept(String argument1, Class<Integer> argument2) {
            throw new AssertionError();
        }
    };

    @Override
    protected ImmutableBag<String> newBag() {
        return (ImmutableBag<String>) ImmutableEmptyBag.INSTANCE;
    }

    @Override
    protected int numKeys() {
        return 0;
    }

    @Override
    public void partition() {
        PartitionImmutableBag<String> partition = this.newBag().partition(Predicates.lessThan("0"));
        Verify.assertIterableEmpty(partition.getSelected());
        Verify.assertIterableEmpty(partition.getRejected());
    }

    @Override
    public void partitionWith() {
        PartitionImmutableBag<String> partition = this.newBag().partitionWith(Predicates2.lessThan(), "0");
        Verify.assertIterableEmpty(partition.getSelected());
        Verify.assertIterableEmpty(partition.getRejected());
    }

    @Override
    public void toStringOfItemToCount() {
        assertEquals("{}", Bags.immutable.of().toStringOfItemToCount());
    }

    @Override
    public void detectIfNone() {
        super.detectIfNone();
        assertEquals("Not Found", this.newBag().detectIfNone("2"::equals, new PassThruFunction0<>("Not Found")));
    }

    @Override
    @Test
    public void chunk_large_size_1() {
        assertEquals(this.newBag(), this.newBag().chunk(10));
    }

    @Override
    @Test
    public void chunk_large_size_2() {
        Verify.assertInstanceOf(ImmutableBag.class, this.newBag().chunk(10));
    }
}
