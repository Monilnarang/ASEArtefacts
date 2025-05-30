package org.eclipse.collections.impl.map.fixed;

import java.util.NoSuchElementException;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.FixedSizeMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmptyMapTest_Purified extends AbstractMemoryEfficientMutableMapTest {

    @Override
    protected MutableMap<String, String> classUnderTest() {
        return new EmptyMap<>();
    }

    @Override
    protected MutableMap<String, Integer> mixedTypeClassUnderTest() {
        return new EmptyMap<>();
    }

    @Override
    protected <K, V> FixedSizeMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2) {
        return new EmptyMap<>();
    }

    @Override
    protected <K, V> FixedSizeMap<K, V> newMapWithKeysValues(K key1, V value1, K key2, V value2, K key3, V value3) {
        return new EmptyMap<>();
    }

    private static class StubProcedure<T> implements Procedure<T>, Procedure2<T, T>, ObjectIntProcedure<T> {

        private static final long serialVersionUID = 1L;

        private boolean called;

        @Override
        public void value(T each) {
            this.called = true;
        }

        @Override
        public void value(T argument1, T argument2) {
            this.called = true;
        }

        @Override
        public void value(T each, int index) {
            this.called = true;
        }
    }

    @Override
    public void withKeyValue() {
        MutableMap<Integer, String> map = new EmptyMap<Integer, String>().withKeyValue(1, "A");
        Verify.assertMapsEqual(UnifiedMap.newWithKeysValues(1, "A"), map);
        Verify.assertInstanceOf(SingletonMap.class, map);
    }

    @Override
    public void withAllKeyValueArguments() {
        MutableMap<Integer, String> map1 = new EmptyMap<Integer, String>().withAllKeyValueArguments(Tuples.pair(1, "A"));
        Verify.assertMapsEqual(UnifiedMap.newWithKeysValues(1, "A"), map1);
        Verify.assertInstanceOf(SingletonMap.class, map1);
        MutableMap<Integer, String> map2 = new EmptyMap<Integer, String>().withAllKeyValueArguments(Tuples.pair(1, "A"), Tuples.pair(2, "B"));
        Verify.assertMapsEqual(UnifiedMap.newWithKeysValues(1, "A", 2, "B"), map2);
        Verify.assertInstanceOf(DoubletonMap.class, map2);
    }

    @Override
    public void withoutKey() {
        MutableMap<Integer, String> map = new EmptyMap<>();
        MutableMap<Integer, String> mapWithout = map.withoutKey(1);
        assertSame(map, mapWithout);
    }

    @Override
    public void withoutAllKeys() {
        MutableMap<Integer, String> map = new EmptyMap<>();
        MutableMap<Integer, String> mapWithout = map.withoutAllKeys(FastList.newListWith(1, 2));
        assertSame(map, mapWithout);
    }

    @Test
    public void empty_1_testMerged_1() {
        Verify.assertEmpty(new EmptyMap<>());
    }

    @Test
    public void empty_2() {
        assertFalse(new EmptyMap<>().notEmpty());
    }

    @Test
    public void empty_4() {
        assertFalse(new EmptyMap<>().notEmpty());
    }

    @Test
    public void empty_5() {
        Verify.assertEmpty(Maps.fixedSize.of());
    }

    @Test
    public void empty_6() {
        assertFalse(Maps.fixedSize.of().notEmpty());
    }

    @Test
    public void viewsEmpty_1() {
        Verify.assertEmpty(new EmptyMap<>().entrySet());
    }

    @Test
    public void viewsEmpty_2() {
        Verify.assertEmpty(new EmptyMap<>().values());
    }

    @Test
    public void viewsEmpty_3() {
        Verify.assertEmpty(new EmptyMap<>().keySet());
    }

    @Test
    public void testReadResolve_1() {
        Verify.assertInstanceOf(EmptyMap.class, Maps.fixedSize.of());
    }

    @Test
    public void testReadResolve_2() {
        Verify.assertPostSerializedIdentity(Maps.fixedSize.of());
    }
}
