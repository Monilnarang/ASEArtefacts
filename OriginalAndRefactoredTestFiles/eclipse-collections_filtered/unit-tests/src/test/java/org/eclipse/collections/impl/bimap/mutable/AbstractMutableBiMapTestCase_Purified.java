package org.eclipse.collections.impl.bimap.mutable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.bimap.BiMap;
import org.eclipse.collections.api.bimap.ImmutableBiMap;
import org.eclipse.collections.api.bimap.MutableBiMap;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.IntegerWithCast;
import org.eclipse.collections.impl.factory.BiMaps;
import org.eclipse.collections.impl.map.mutable.MutableMapIterableTestCase;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.multimap.set.UnifiedSetMultimap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractMutableBiMapTestCase_Purified extends MutableMapIterableTestCase {

    public abstract MutableBiMap<Integer, Character> classUnderTest();

    public abstract MutableBiMap<Integer, Character> getEmptyMap();

    @Override
    protected abstract <K, V> MutableBiMap<K, V> newMap();

    @Override
    protected abstract <K, V> MutableBiMap<K, V> newMapWithKeyValue(K key, V value);

    @Override
    protected abstract <K, V> MutableBiMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2);

    @Override
    protected abstract <K, V> MutableBiMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3);

    @Override
    protected abstract <K, V> MutableBiMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4);

    public static void assertBiMapsEqual(BiMap<?, ?> expected, BiMap<?, ?> actual) {
        assertEquals(expected, actual);
        assertEquals(expected.inverse(), actual.inverse());
    }

    @Override
    public void toImmutable() {
        ImmutableBiMap<Integer, Character> expectedImmutableBiMap = BiMaps.immutable.of(null, 'b', 1, null, 3, 'c');
        ImmutableBiMap<Integer, Character> characters = this.classUnderTest().toImmutable();
        assertEquals(expectedImmutableBiMap, characters);
    }

    @Test
    public void size_1() {
        Verify.assertSize(3, this.classUnderTest());
    }

    @Test
    public void size_2() {
        Verify.assertSize(0, this.getEmptyMap());
    }

    @Test
    public void testToString_1() {
        assertEquals("{}", this.getEmptyMap().toString());
    }

    @Test
    public void testToString_2() {
        String actualString = HashBiMap.newWithKeysValues(1, null, 2, 'b').toString();
        assertTrue("{1=null, 2=b}".equals(actualString) || "{2=b, 1=null}".equals(actualString));
    }
}
