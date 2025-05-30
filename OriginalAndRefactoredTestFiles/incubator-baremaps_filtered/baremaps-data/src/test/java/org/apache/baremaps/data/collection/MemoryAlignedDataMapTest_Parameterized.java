package org.apache.baremaps.data.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.baremaps.data.memory.OnHeapMemory;
import org.apache.baremaps.data.type.IntegerDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MemoryAlignedDataMapTest_Parameterized {

    private MemoryAlignedDataMap<Integer> map;

    @BeforeEach
    void setUp() {
        map = MemoryAlignedDataMap.<Integer>builder().dataType(new IntegerDataType()).memory(new OnHeapMemory(1024)).build();
    }

    @AfterEach
    void tearDown() {
        map = null;
    }

    @Test
    void containsKey_4_testMerged_4() {
        map.put(1L, 1);
        assertTrue(map.containsKey(0L));
        assertTrue(map.containsKey(1L));
        assertTrue(map.containsKey(5L));
        assertFalse(map.containsKey(256L));
        map.put(500L, 1);
        assertTrue(map.containsKey(257L));
        assertTrue(map.containsKey(258L));
        assertTrue(map.containsKey(511L));
        assertFalse(map.containsKey(null));
    }

    @Test
    void containsValue_3_testMerged_3() {
        map.put(1L, 1);
        assertTrue(map.containsValue(0));
        assertTrue(map.containsValue(1));
        assertFalse(map.containsValue(2));
        assertFalse(map.containsValue(255));
        map.put(256L, 1);
        assertFalse(map.containsValue(256));
        assertFalse(map.containsValue(null));
    }

    @Test
    void size_1() {
        assertEquals(0, map.size());
    }

    @Test
    void size_2_testMerged_2() {
        map.put(0L, 1);
        map.put(1L, 1);
        assertEquals(256, map.size());
        map.put(256L, 1);
        assertEquals(512, map.size());
        map.put(600L, 6);
        assertEquals(768, map.size());
    }

    @ParameterizedTest
    @MethodSource("Provider_containsKey_1to3")
    void containsKey_1to3(int param1) {
        assertFalse(map.containsKey(param1));
    }

    static public Stream<Arguments> Provider_containsKey_1to3() {
        return Stream.of(arguments(1), arguments(1L), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_containsValue_1to2")
    void containsValue_1to2(int param1) {
        assertFalse(map.containsValue(param1));
    }

    static public Stream<Arguments> Provider_containsValue_1to2() {
        return Stream.of(arguments(0), arguments(1));
    }
}
