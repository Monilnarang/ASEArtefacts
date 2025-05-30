package org.eclipse.collections.impl.map.sorted.immutable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.sorted.ImmutableSortedMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.factory.SortedMaps;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmutableTreeMapTest_Purified extends ImmutableSortedMapTestCase {

    @Override
    protected ImmutableSortedMap<Integer, String> classUnderTest() {
        return SortedMaps.immutable.of(1, "1", 2, "2", 3, "3", 4, "4");
    }

    @Override
    protected ImmutableSortedMap<Integer, String> classUnderTest(Comparator<? super Integer> comparator) {
        return SortedMaps.immutable.of(comparator, 1, "1", 2, "2", 3, "3", 4, "4");
    }

    @Override
    protected <K, V> MapIterable<K, V> newMap() {
        return new ImmutableTreeMap<>(SortedMaps.mutable.of());
    }

    @Override
    protected <K, V> MapIterable<K, V> newMapWithKeyValue(K key1, V value1) {
        return SortedMaps.immutable.of(key1, value1);
    }

    @Override
    protected <K, V> MapIterable<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2) {
        return SortedMaps.immutable.of(key1, value1, key2, value2);
    }

    @Override
    protected <K, V> MapIterable<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3) {
        return SortedMaps.immutable.of(key1, value1, key2, value2, key3, value3);
    }

    @Override
    protected <K, V> MapIterable<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4) {
        return SortedMaps.immutable.of(key1, value1, key2, value2, key3, value3, key4, value4);
    }

    @Override
    protected int size() {
        return 4;
    }

    @Override
    public void entrySet() {
        super.entrySet();
        Interval interval = Interval.oneTo(100);
        LazyIterable<Pair<String, Integer>> pairs = interval.collect(Object::toString).zip(interval);
        MutableSortedMap<String, Integer> mutableSortedMap = new TreeSortedMap<>(pairs.toArray(new Pair[] {}));
        ImmutableSortedMap<String, Integer> immutableSortedMap = mutableSortedMap.toImmutable();
        MutableList<Map.Entry<String, Integer>> entries = FastList.newList(immutableSortedMap.castToSortedMap().entrySet());
        MutableList<Map.Entry<String, Integer>> sortedEntries = entries.toSortedListBy(Map.Entry::getKey);
        assertEquals(sortedEntries, entries);
    }

    @Override
    @Test
    public void testToString_1() {
        assertEquals("{1=1, 2=2, 3=3, 4=4}", this.classUnderTest().toString());
    }

    @Override
    @Test
    public void testToString_2() {
        assertEquals("{4=4, 3=3, 2=2, 1=1}", this.classUnderTest(Comparators.reverseNaturalOrder()).toString());
    }

    @Override
    @Test
    public void testToString_3() {
        assertEquals("{}", new ImmutableTreeMap<>(new TreeSortedMap<>()).toString());
    }

    @Test
    public void keySetIsEmpty_1() {
        assertFalse(new ImmutableTreeMap<>(SortedMaps.mutable.of(1, "1", 2, "2", 3, "3", 4, "4")).keySet().isEmpty());
    }

    @Test
    public void keySetIsEmpty_2() {
        assertTrue(new ImmutableTreeMap<Integer, String>(SortedMaps.mutable.of()).keySet().isEmpty());
    }

    @Test
    public void keySetToString_1() {
        assertEquals("[1, 2, 3, 4]", new ImmutableTreeMap<>(SortedMaps.mutable.of(1, "1", 2, "2", 3, "3", 4, "4")).keySet().toString());
    }

    @Test
    public void keySetToString_2() {
        assertEquals("[1, 2, 3, 4]", new ImmutableTreeMap<>(SortedMaps.mutable.of(4, "4", 3, "3", 2, "2", 1, "1")).keySet().toString());
    }

    @Test
    public void keySetToString_3() {
        assertEquals("[4, 3, 2, 1]", new ImmutableTreeMap<>(SortedMaps.mutable.of(Comparators.reverseNaturalOrder(), 4, "4", 3, "3", 2, "2", 1, "1")).keySet().toString());
    }
}
