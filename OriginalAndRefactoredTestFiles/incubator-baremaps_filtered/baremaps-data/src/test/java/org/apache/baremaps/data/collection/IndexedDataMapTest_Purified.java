package org.apache.baremaps.data.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.baremaps.data.type.IntegerDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndexedDataMapTest_Purified {

    private IndexedDataMap<Integer> map;

    @BeforeEach
    void setUp() {
        map = IndexedDataMap.<Integer>builder().values(AppendOnlyLog.<Integer>builder().dataType(new IntegerDataType()).build()).build();
    }

    @AfterEach
    void tearDown() {
        map.clear();
        map = null;
    }

    @Test
    void containsKey_1() {
        assertFalse(map.containsKey(1));
    }

    @Test
    void containsKey_2() {
        assertFalse(map.containsKey(1L));
    }

    @Test
    void containsKey_3() {
        assertFalse(map.containsKey(1));
    }

    @Test
    void containsKey_4_testMerged_4() {
        map.put(1L, 1);
        assertFalse(map.containsKey(0L));
        assertTrue(map.containsKey(1L));
        assertFalse(map.containsKey(5L));
        assertFalse(map.containsKey(256L));
        assertFalse(map.containsKey(null));
    }

    @Test
    void size_1() {
        assertEquals(0, map.size());
    }

    @Test
    void size_2_testMerged_2() {
        map.put(0L, 1);
        map.put(1L, 1);
        assertEquals(2, map.size());
        map.put(1L, 2);
        map.put(256L, 1);
        assertEquals(3, map.size());
        map.put(600L, 6);
        assertEquals(4, map.size());
        map.put(549755813887L, 7);
        assertEquals(5, map.size());
    }

    @Test
    void clear_1() {
        assertTrue(map.isEmpty());
    }

    @Test
    void clear_2() {
        assertTrue(map.isEmpty());
    }

    @Test
    void clear_3_testMerged_3() {
        map.clear();
        map.put(1L, 1);
        assertEquals(1, map.size());
        map.put(324L, 1);
        assertEquals(2, map.size());
    }

    @Test
    void clear_5() {
        assertTrue(map.isEmpty());
    }

    @Test
    void isEmpty_1() {
        assertTrue(map.isEmpty());
    }

    @Test
    void isEmpty_2() {
        map.put(1L, 1);
        assertFalse(map.isEmpty());
    }

    @Test
    void isEmpty_3() {
        assertTrue(map.isEmpty());
    }
}
