package org.eclipse.collections.impl.map.mutable.primitive;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.ObjectBooleanMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.partition.PartitionIterable;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.ObjectBooleanPair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractObjectBooleanMapKeyValuesViewTestCase_Purified {

    public abstract <K> ObjectBooleanMap<K> newWithKeysValues(K key1, boolean value1, K key2, boolean value2, K key3, boolean value3);

    public abstract <K> ObjectBooleanMap<K> newWithKeysValues(K key1, boolean value1, K key2, boolean value2);

    public abstract <K> ObjectBooleanMap<K> newWithKeysValues(K key1, boolean value1);

    public abstract <K> ObjectBooleanMap<K> newEmpty();

    public RichIterable<ObjectBooleanPair<Object>> newWith() {
        return this.newEmpty().keyValuesView();
    }

    public <K> RichIterable<ObjectBooleanPair<K>> newWith(K key1, boolean value1) {
        return this.newWithKeysValues(key1, value1).keyValuesView();
    }

    public <K> RichIterable<ObjectBooleanPair<K>> newWith(K key1, boolean value1, K key2, boolean value2) {
        return this.newWithKeysValues(key1, value1, key2, value2).keyValuesView();
    }

    public <K> RichIterable<ObjectBooleanPair<K>> newWith(K key1, boolean value1, K key2, boolean value2, K key3, boolean value3) {
        return this.newWithKeysValues(key1, value1, key2, value2, key3, value3).keyValuesView();
    }

    @Test
    public void getFirst_1() {
        ObjectBooleanPair<Integer> first = this.newWith(1, true, 2, false, 3, true).getFirst();
        assertTrue(PrimitiveTuples.pair(Integer.valueOf(1), true).equals(first) || PrimitiveTuples.pair(Integer.valueOf(2), false).equals(first) || PrimitiveTuples.pair(Integer.valueOf(3), true).equals(first));
    }

    @Test
    public void getFirst_2() {
        assertEquals(PrimitiveTuples.pair(Integer.valueOf(1), true), this.newWith(1, true).getFirst());
    }

    @Test
    public void getLast_1() {
        ObjectBooleanPair<Integer> last = this.newWith(1, true, 2, false, 3, true).getLast();
        assertTrue(PrimitiveTuples.pair(Integer.valueOf(1), true).equals(last) || PrimitiveTuples.pair(Integer.valueOf(2), false).equals(last) || PrimitiveTuples.pair(Integer.valueOf(3), true).equals(last));
    }

    @Test
    public void getLast_2() {
        assertEquals(PrimitiveTuples.pair(Integer.valueOf(1), true), this.newWith(1, true).getLast());
    }

    @Test
    public void isEmpty_1() {
        Verify.assertIterableEmpty(this.newWith());
    }

    @Test
    public void isEmpty_2() {
        Verify.assertIterableNotEmpty(this.newWith(1, true));
    }

    @Test
    public void isEmpty_3() {
        assertTrue(this.newWith(1, true).notEmpty());
    }

    @Test
    public void empty_1() {
        Verify.assertIterableEmpty(this.newWith());
    }

    @Test
    public void empty_2() {
        assertTrue(this.newWith().isEmpty());
    }

    @Test
    public void empty_3() {
        assertFalse(this.newWith().notEmpty());
    }
}
