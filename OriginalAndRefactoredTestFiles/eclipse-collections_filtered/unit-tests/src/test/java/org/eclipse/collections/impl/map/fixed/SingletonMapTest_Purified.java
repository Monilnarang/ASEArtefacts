package org.eclipse.collections.impl.map.fixed;

import java.util.Map;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.FixedSizeMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SingletonMapTest_Purified extends AbstractMemoryEfficientMutableMapTest {

    @Override
    protected MutableMap<String, String> classUnderTest() {
        return new SingletonMap<>("1", "One");
    }

    @Override
    protected MutableMap<String, Integer> mixedTypeClassUnderTest() {
        return new SingletonMap<>("1", 1);
    }

    @Override
    protected <K, V> FixedSizeMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2) {
        return new SingletonMap<>(key1, value1);
    }

    @Override
    protected <K, V> FixedSizeMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3) {
        return new SingletonMap<>(key1, value1);
    }

    @Override
    @Test
    public void testToString_1() {
        assertEquals("{1=One}", new SingletonMap<>(1, "One").toString());
    }

    @Override
    @Test
    public void testToString_2() {
        assertEquals("{1=null}", new SingletonMap<Integer, String>(1, null).toString());
    }

    @Override
    @Test
    public void testToString_3() {
        assertEquals("{null=One}", new SingletonMap<Integer, String>(null, "One").toString());
    }
}
