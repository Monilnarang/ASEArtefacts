package org.eclipse.collections.impl.bag.immutable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.primitive.ImmutableBooleanBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.bag.ImmutableBagMultimap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.multimap.bag.HashBagMultimap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iBag;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmutableSingletonBagTest_Purified extends ImmutableBagTestCase {

    private static final String VAL = "1";

    private static final String NOT_VAL = "2";

    @Override
    protected ImmutableBag<String> newBag() {
        return new ImmutableSingletonBag<>(VAL);
    }

    private ImmutableBag<String> newBagWithNull() {
        return new ImmutableSingletonBag<>(null);
    }

    @Override
    protected int numKeys() {
        return 1;
    }

    @Override
    public void toStringOfItemToCount() {
        assertEquals("{1=1}", new ImmutableSingletonBag<>(VAL).toStringOfItemToCount());
    }

    @Override
    @Test
    public void newWithout_1() {
        assertEquals(Bags.immutable.of(VAL), this.newBag().newWithout(NOT_VAL));
    }

    @Override
    @Test
    public void newWithout_2() {
        assertEquals(Bags.immutable.of(), this.newBag().newWithout(VAL));
    }

    @Override
    @Test
    public void newWithoutAll_1() {
        assertEquals(Bags.immutable.of(VAL), this.newBag().newWithoutAll(FastList.newListWith(NOT_VAL)));
    }

    @Override
    @Test
    public void newWithoutAll_2() {
        assertEquals(Bags.immutable.of(), this.newBag().newWithoutAll(FastList.newListWith(VAL, NOT_VAL)));
    }

    @Override
    @Test
    public void newWithoutAll_3() {
        assertEquals(Bags.immutable.of(), this.newBag().newWithoutAll(FastList.newListWith(VAL)));
    }

    @Override
    @Test
    public void contains_1() {
        assertTrue(this.newBag().contains(VAL));
    }

    @Override
    @Test
    public void contains_2() {
        assertFalse(this.newBag().contains(NOT_VAL));
    }

    @Override
    @Test
    public void containsAllIterable_1() {
        assertTrue(this.newBag().containsAllIterable(FastList.newListWith()));
    }

    @Override
    @Test
    public void containsAllIterable_2() {
        assertTrue(this.newBag().containsAllIterable(FastList.newListWith(VAL)));
    }

    @Override
    @Test
    public void containsAllIterable_3() {
        assertFalse(this.newBag().containsAllIterable(FastList.newListWith(NOT_VAL)));
    }

    @Override
    @Test
    public void containsAllIterable_4() {
        assertFalse(this.newBag().containsAllIterable(FastList.newListWith(42)));
    }

    @Override
    @Test
    public void containsAllIterable_5() {
        assertFalse(this.newBag().containsAllIterable(FastList.newListWith(VAL, NOT_VAL)));
    }

    @Test
    public void testContainsAllArguments_1() {
        assertTrue(this.newBag().containsAllArguments());
    }

    @Test
    public void testContainsAllArguments_2() {
        assertTrue(this.newBag().containsAllArguments(VAL));
    }

    @Test
    public void testContainsAllArguments_3() {
        assertFalse(this.newBag().containsAllArguments(NOT_VAL));
    }

    @Test
    public void testContainsAllArguments_4() {
        assertFalse(this.newBag().containsAllArguments(42));
    }

    @Test
    public void testContainsAllArguments_5() {
        assertFalse(this.newBag().containsAllArguments(VAL, NOT_VAL));
    }

    @Test
    public void testOccurrencesOf_1() {
        assertEquals(1, this.newBag().occurrencesOf(VAL));
    }

    @Test
    public void testOccurrencesOf_2() {
        assertEquals(0, this.newBag().occurrencesOf(NOT_VAL));
    }
}
