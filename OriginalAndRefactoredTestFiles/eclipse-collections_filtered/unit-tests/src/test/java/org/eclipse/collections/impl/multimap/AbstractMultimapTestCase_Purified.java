package org.eclipse.collections.impl.multimap;

import java.util.Set;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.multimap.set.SetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractMultimapTestCase_Purified {

    protected abstract <K, V> Multimap<K, V> newMultimap();

    protected abstract <K, V> Multimap<K, V> newMultimapWithKeyValue(K key, V value);

    protected abstract <K, V> Multimap<K, V> newMultimapWithKeysValues(K key1, V value1, K key2, V value2);

    protected abstract <K, V> Multimap<K, V> newMultimapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3);

    protected abstract <K, V> Multimap<K, V> newMultimapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4);

    protected abstract <K, V> Multimap<K, V> newMultimap(Pair<K, V>... pairs);

    protected abstract <K, V> Multimap<K, V> newMultimapFromPairs(Iterable<Pair<K, V>> inputIterable);

    protected abstract <V> MutableCollection<V> createCollection(V... args);

    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(this.newMultimap());
    }

    @Test
    public void isEmpty_2() {
        Verify.assertNotEmpty(this.newMultimapWithKeyValue(1, 1));
    }

    @Test
    public void isEmpty_3() {
        assertTrue(this.newMultimapWithKeyValue(1, 1).notEmpty());
    }

    @Test
    public void notEmpty_1() {
        assertTrue(this.newMultimap().isEmpty());
    }

    @Test
    public void notEmpty_2() {
        assertFalse(this.newMultimap().notEmpty());
    }

    @Test
    public void notEmpty_3() {
        assertTrue(this.newMultimapWithKeysValues(1, "1", 2, "2").notEmpty());
    }
}
