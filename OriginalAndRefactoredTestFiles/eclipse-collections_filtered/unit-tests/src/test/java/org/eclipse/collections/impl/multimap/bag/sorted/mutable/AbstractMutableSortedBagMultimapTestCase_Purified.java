package org.eclipse.collections.impl.multimap.bag.sorted.mutable;

import java.util.Comparator;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.bag.BagMultimap;
import org.eclipse.collections.api.multimap.bag.MutableBagMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.multimap.sortedbag.ImmutableSortedBagMultimap;
import org.eclipse.collections.api.multimap.sortedbag.MutableSortedBagMultimap;
import org.eclipse.collections.api.multimap.sortedbag.SortedBagMultimap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.multimap.AbstractMutableMultimapTestCase;
import org.eclipse.collections.impl.multimap.bag.HashBagMultimap;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public abstract class AbstractMutableSortedBagMultimapTestCase_Purified extends AbstractMutableMultimapTestCase {

    protected abstract <K, V> MutableSortedBagMultimap<K, V> newMultimap(Comparator<V> comparator);

    @Override
    protected abstract <K, V> MutableSortedBagMultimap<K, V> newMultimap();

    @Override
    protected abstract <K, V> MutableSortedBagMultimap<K, V> newMultimapWithKeyValue(K key, V value);

    @Override
    protected abstract <K, V> MutableSortedBagMultimap<K, V> newMultimapWithKeysValues(K key1, V value1, K key2, V value2);

    @Override
    protected abstract <K, V> MutableSortedBagMultimap<K, V> newMultimapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3);

    @Override
    protected abstract <K, V> MutableSortedBagMultimap<K, V> newMultimapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4);

    @Override
    protected abstract <K, V> MutableSortedBagMultimap<K, V> newMultimap(Pair<K, V>... pairs);

    @Override
    protected abstract <K, V> MutableSortedBagMultimap<K, V> newMultimapFromPairs(Iterable<Pair<K, V>> inputIterable);

    @Override
    protected abstract <V> MutableSortedBag<V> createCollection(V... args);

    @Override
    public void clear() {
        MutableMultimap<Integer, String> multimap = this.newMultimapWithKeysValues(1, "One", 2, "Two", 3, "Three");
        multimap.clear();
        Verify.assertEmpty(multimap);
    }

    @Override
    @Test
    public void toImmutable_1() {
        Verify.assertInstanceOf(MutableSortedBagMultimap.class, this.newMultimap());
    }

    @Override
    @Test
    public void toImmutable_2() {
        Verify.assertInstanceOf(ImmutableSortedBagMultimap.class, this.newMultimap().toImmutable());
    }

    @Override
    @Test
    public void toImmutable_3() {
        assertFalse(this.newMultimap().toImmutable() instanceof MutableSortedBagMultimap);
    }
}
