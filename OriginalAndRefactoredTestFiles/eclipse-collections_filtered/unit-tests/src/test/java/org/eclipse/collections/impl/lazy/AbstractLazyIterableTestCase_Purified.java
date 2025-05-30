package org.eclipse.collections.impl.lazy;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.collection.primitive.MutableByteCollection;
import org.eclipse.collections.api.collection.primitive.MutableCharCollection;
import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
import org.eclipse.collections.api.collection.primitive.MutableFloatCollection;
import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
import org.eclipse.collections.api.collection.primitive.MutableLongCollection;
import org.eclipse.collections.api.collection.primitive.MutableShortCollection;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.partition.PartitionIterable;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ByteArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.CharArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.FloatArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ShortArrayList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractLazyIterableTestCase_Purified {

    private final LazyIterable<Integer> lazyIterable = this.newWith(1, 2, 3, 4, 5, 6, 7);

    protected abstract <T> LazyIterable<T> newWith(T... elements);

    @Test
    public void contains_1() {
        assertTrue(this.lazyIterable.contains(3));
    }

    @Test
    public void contains_2() {
        assertFalse(this.lazyIterable.contains(8));
    }

    @Test
    public void containsAllIterable_1() {
        assertTrue(this.lazyIterable.containsAllIterable(FastList.newListWith(3)));
    }

    @Test
    public void containsAllIterable_2() {
        assertFalse(this.lazyIterable.containsAllIterable(FastList.newListWith(8)));
    }

    @Test
    public void containsAllArray_1() {
        assertTrue(this.lazyIterable.containsAllArguments(3));
    }

    @Test
    public void containsAllArray_2() {
        assertFalse(this.lazyIterable.containsAllArguments(8));
    }

    @Test
    public void anySatisfyWith_1() {
        assertFalse(this.lazyIterable.anySatisfyWith(Predicates2.instanceOf(), String.class));
    }

    @Test
    public void anySatisfyWith_2() {
        assertTrue(this.lazyIterable.anySatisfyWith(Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void noneSatisfyWith_1() {
        assertFalse(this.lazyIterable.noneSatisfyWith(Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void noneSatisfyWith_2() {
        assertTrue(this.lazyIterable.noneSatisfyWith(Predicates2.instanceOf(), String.class));
    }

    @Test
    public void getFirst_1() {
        assertEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getFirst());
    }

    @Test
    public void getFirst_2() {
        assertNotEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getFirst());
    }

    @Test
    public void getLast_1() {
        assertNotEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getLast());
    }

    @Test
    public void getLast_2() {
        assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getLast());
    }

    @Test
    public void isEmpty_1() {
        assertTrue(this.newWith().isEmpty());
    }

    @Test
    public void isEmpty_2() {
        assertTrue(this.newWith(1, 2).notEmpty());
    }
}
