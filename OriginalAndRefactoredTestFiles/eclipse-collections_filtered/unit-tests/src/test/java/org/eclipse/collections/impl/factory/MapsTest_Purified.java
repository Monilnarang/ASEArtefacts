package org.eclipse.collections.impl.factory;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.collections.api.factory.map.FixedSizeMapFactory;
import org.eclipse.collections.api.factory.map.ImmutableMapFactory;
import org.eclipse.collections.api.factory.map.MutableMapFactory;
import org.eclipse.collections.api.factory.map.sorted.MutableSortedMapFactory;
import org.eclipse.collections.api.map.FixedSizeMap;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.test.domain.Key;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class MapsTest_Purified {

    private void assertPresizedMapSizeEquals(int initialCapacity, UnifiedMap<String, String> map) {
        try {
            Field tableField = UnifiedMap.class.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] table = (Object[]) tableField.get(map);
            int size = (int) Math.ceil(initialCapacity / 0.75);
            int capacity = 1;
            while (capacity < size) {
                capacity <<= 1;
            }
            capacity <<= 1;
            assertEquals(capacity, table.length);
        } catch (SecurityException ignored) {
            fail("Unable to modify the visibility of the table on UnifiedMap");
        } catch (NoSuchFieldException ignored) {
            fail("No field named table UnifiedMap");
        } catch (IllegalAccessException ignored) {
            fail("No access the field table in UnifiedMap");
        }
    }

    @Test
    public void copyMap_1() {
        assertEquals(Maps.fixedSize.of(1, "One"), Maps.immutable.ofAll(UnifiedMap.newWithKeysValues(1, "One")));
    }

    @Test
    public void copyMap_2() {
        Verify.assertInstanceOf(ImmutableMap.class, Maps.immutable.ofAll(UnifiedMap.newWithKeysValues(1, "One")));
    }

    @Test
    public void copyMap_3() {
        assertEquals(Maps.fixedSize.of(1, "One", 2, "Dos"), Maps.immutable.ofAll(UnifiedMap.newWithKeysValues(1, "One", 2, "Dos")));
    }

    @Test
    public void copyMap_4() {
        Verify.assertInstanceOf(ImmutableMap.class, Maps.immutable.ofAll(UnifiedMap.newWithKeysValues(1, "One", 2, "Dos")));
    }

    @Test
    public void copyMap_5() {
        assertEquals(Maps.fixedSize.of(1, "One", 2, "Dos", 3, "Drei"), Maps.immutable.ofAll(UnifiedMap.newWithKeysValues(1, "One", 2, "Dos", 3, "Drei")));
    }

    @Test
    public void copyMap_6() {
        Verify.assertInstanceOf(ImmutableMap.class, Maps.immutable.ofAll(UnifiedMap.newWithKeysValues(1, "One", 2, "Dos", 3, "Drei")));
    }

    @Test
    public void copyMap_7() {
        assertEquals(UnifiedMap.newWithKeysValues(1, "One", 2, "Dos", 3, "Drei", 4, "Quatro"), Maps.immutable.ofAll(UnifiedMap.newWithKeysValues(1, "One", 2, "Dos", 3, "Drei", 4, "Quatro")));
    }

    @Test
    public void copyMap_8() {
        Verify.assertInstanceOf(ImmutableMap.class, Maps.immutable.ofAll(UnifiedMap.newWithKeysValues(1, "One", 2, "Dos", 3, "Drei", 4, "Quatro")));
    }

    @Test
    public void duplicates_1() {
        assertEquals(Maps.immutable.of(0, 0), Maps.immutable.of(0, 0, 0, 0));
    }

    @Test
    public void duplicates_2() {
        assertEquals(Maps.immutable.of(0, 0), Maps.immutable.of(0, 0, 0, 0, 0, 0));
    }

    @Test
    public void duplicates_3() {
        assertEquals(Maps.immutable.of(0, 0), Maps.immutable.of(0, 0, 0, 0, 0, 0, 0, 0));
    }

    @Test
    public void duplicates_4() {
        assertEquals(Maps.immutable.of(0, 0, 1, 1), Maps.immutable.of(1, 1, 0, 0, 0, 0));
    }

    @Test
    public void duplicates_5() {
        assertEquals(Maps.immutable.of(0, 0, 2, 2), Maps.immutable.of(0, 0, 2, 2, 0, 0));
    }

    @Test
    public void duplicates_6() {
        assertEquals(Maps.immutable.of(0, 0, 3, 3), Maps.immutable.of(0, 0, 0, 0, 3, 3));
    }

    @Test
    public void duplicates_7() {
        assertEquals(Maps.immutable.of(0, 0, 1, 1), Maps.immutable.of(1, 1, 0, 0, 0, 0, 0, 0));
    }

    @Test
    public void duplicates_8() {
        assertEquals(Maps.immutable.of(0, 0, 2, 2), Maps.immutable.of(0, 0, 2, 2, 0, 0, 0, 0));
    }

    @Test
    public void duplicates_9() {
        assertEquals(Maps.immutable.of(0, 0, 3, 3), Maps.immutable.of(0, 0, 0, 0, 3, 3, 0, 0));
    }

    @Test
    public void duplicates_10() {
        assertEquals(Maps.immutable.of(0, 0, 4, 4), Maps.immutable.of(0, 0, 0, 0, 0, 0, 4, 4));
    }

    @Test
    public void duplicates_11() {
        assertEquals(Maps.immutable.of(0, 0, 2, 2, 3, 3, 4, 4), Maps.immutable.of(0, 0, 2, 2, 3, 3, 4, 4));
    }

    @Test
    public void duplicates_12() {
        assertEquals(Maps.immutable.of(0, 0, 1, 1, 3, 3, 4, 4), Maps.immutable.of(1, 1, 0, 0, 3, 3, 4, 4));
    }

    @Test
    public void duplicates_13() {
        assertEquals(Maps.immutable.of(0, 0, 1, 1, 2, 2, 4, 4), Maps.immutable.of(1, 1, 2, 2, 0, 0, 4, 4));
    }

    @Test
    public void duplicates_14() {
        assertEquals(Maps.immutable.of(0, 0, 1, 1, 2, 2, 3, 3), Maps.immutable.of(1, 1, 2, 2, 3, 3, 0, 0));
    }

    @Test
    public void duplicates_15() {
        assertEquals(Maps.immutable.of(0, 0, 3, 3, 4, 4), Maps.immutable.of(0, 0, 0, 0, 3, 3, 4, 4));
    }

    @Test
    public void duplicates_16() {
        assertEquals(Maps.immutable.of(0, 0, 2, 2, 4, 4), Maps.immutable.of(0, 0, 2, 2, 0, 0, 4, 4));
    }

    @Test
    public void duplicates_17() {
        assertEquals(Maps.immutable.of(0, 0, 2, 2, 3, 3), Maps.immutable.of(0, 0, 2, 2, 3, 3, 0, 0));
    }

    @Test
    public void duplicates_18() {
        assertEquals(Maps.immutable.of(0, 0, 1, 1, 4, 4), Maps.immutable.of(1, 1, 0, 0, 0, 0, 4, 4));
    }

    @Test
    public void duplicates_19() {
        assertEquals(Maps.immutable.of(0, 0, 1, 1, 3, 3), Maps.immutable.of(1, 1, 0, 0, 3, 3, 0, 0));
    }

    @Test
    public void duplicates_20() {
        assertEquals(Maps.immutable.of(0, 0, 1, 1, 2, 2), Maps.immutable.of(1, 1, 2, 2, 0, 0, 0, 0));
    }
}
