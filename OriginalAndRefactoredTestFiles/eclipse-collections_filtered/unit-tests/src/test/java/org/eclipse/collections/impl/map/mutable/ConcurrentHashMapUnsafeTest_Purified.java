package org.eclipse.collections.impl.map.mutable;

import java.util.Collections;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.Function3;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ConcurrentMutableMap;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.partition.PartitionIterable;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.parallel.ParallelIterate;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.ImmutableEntry;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConcurrentHashMapUnsafeTest_Purified extends ConcurrentHashMapTestCase {

    public static final MutableMap<Integer, MutableBag<Integer>> SMALL_BAG_MUTABLE_MAP = Interval.oneTo(100).groupBy(each -> each % 10).toMap(Bags.mutable::of);

    @Override
    public <K, V> ConcurrentMutableMap<K, V> newMap() {
        return ConcurrentHashMapUnsafe.newMap();
    }

    @Override
    public <K, V> ConcurrentMutableMap<K, V> newMapWithKeyValue(K key, V value) {
        return ConcurrentHashMapUnsafe.<K, V>newMap().withKeyValue(key, value);
    }

    @Override
    public <K, V> ConcurrentMutableMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2) {
        return ConcurrentHashMapUnsafe.<K, V>newMap().withKeyValue(key1, value1).withKeyValue(key2, value2);
    }

    @Override
    public <K, V> ConcurrentMutableMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3) {
        return ConcurrentHashMapUnsafe.<K, V>newMap().withKeyValue(key1, value1).withKeyValue(key2, value2).withKeyValue(key3, value3);
    }

    @Override
    public <K, V> ConcurrentMutableMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4) {
        return ConcurrentHashMapUnsafe.<K, V>newMap().withKeyValue(key1, value1).withKeyValue(key2, value2).withKeyValue(key3, value3).withKeyValue(key4, value4);
    }

    private static class KeyTransformer implements Function2<Integer, Integer, Integer> {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer value(Integer key, Integer value) {
            return key;
        }
    }

    private static class ValueFactory implements Function3<Object, Object, Integer, Integer> {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer value(Object argument1, Object argument2, Integer key) {
            return key;
        }
    }

    @Test
    public void equalsEdgeCases_1() {
        assertNotEquals(ConcurrentHashMapUnsafe.newMap().withKeyValue(1, 1), ConcurrentHashMapUnsafe.newMap());
    }

    @Test
    public void equalsEdgeCases_2() {
        assertNotEquals(ConcurrentHashMapUnsafe.newMap().withKeyValue(1, 1), ConcurrentHashMapUnsafe.newMap().withKeyValue(1, 1).withKeyValue(2, 2));
    }
}
