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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MapsTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_duplicates_4to6")
    public void duplicates_4to6(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9, int param10) {
        assertEquals(Maps.immutable.of(param1, param2, param3, param4), Maps.immutable.of(param5, param6, param7, param8, param9, param10));
    }

    static public Stream<Arguments> Provider_duplicates_4to6() {
        return Stream.of(arguments(0, 0, 1, 1, 1, 1, 0, 0, 0, 0), arguments(0, 0, 2, 2, 0, 0, 2, 2, 0, 0), arguments(0, 0, 3, 3, 0, 0, 0, 0, 3, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_duplicates_7to10")
    public void duplicates_7to10(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9, int param10, int param11, int param12) {
        assertEquals(Maps.immutable.of(param1, param2, param3, param4), Maps.immutable.of(param5, param6, param7, param8, param9, param10, param11, param12));
    }

    static public Stream<Arguments> Provider_duplicates_7to10() {
        return Stream.of(arguments(0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0), arguments(0, 0, 2, 2, 0, 0, 2, 2, 0, 0, 0, 0), arguments(0, 0, 3, 3, 0, 0, 0, 0, 3, 3, 0, 0), arguments(0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 4, 4));
    }

    @ParameterizedTest
    @MethodSource("Provider_duplicates_11to14")
    public void duplicates_11to14(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9, int param10, int param11, int param12, int param13, int param14, int param15, int param16) {
        assertEquals(Maps.immutable.of(param1, param2, param3, param4, param5, param6, param7, param8), Maps.immutable.of(param9, param10, param11, param12, param13, param14, param15, param16));
    }

    static public Stream<Arguments> Provider_duplicates_11to14() {
        return Stream.of(arguments(0, 0, 2, 2, 3, 3, 4, 4, 0, 0, 2, 2, 3, 3, 4, 4), arguments(0, 0, 1, 1, 3, 3, 4, 4, 1, 1, 0, 0, 3, 3, 4, 4), arguments(0, 0, 1, 1, 2, 2, 4, 4, 1, 1, 2, 2, 0, 0, 4, 4), arguments(0, 0, 1, 1, 2, 2, 3, 3, 1, 1, 2, 2, 3, 3, 0, 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_duplicates_15to20")
    public void duplicates_15to20(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9, int param10, int param11, int param12, int param13, int param14) {
        assertEquals(Maps.immutable.of(param1, param2, param3, param4, param5, param6), Maps.immutable.of(param7, param8, param9, param10, param11, param12, param13, param14));
    }

    static public Stream<Arguments> Provider_duplicates_15to20() {
        return Stream.of(arguments(0, 0, 3, 3, 4, 4, 0, 0, 0, 0, 3, 3, 4, 4), arguments(0, 0, 2, 2, 4, 4, 0, 0, 2, 2, 0, 0, 4, 4), arguments(0, 0, 2, 2, 3, 3, 0, 0, 2, 2, 3, 3, 0, 0), arguments(0, 0, 1, 1, 4, 4, 1, 1, 0, 0, 0, 0, 4, 4), arguments(0, 0, 1, 1, 3, 3, 1, 1, 0, 0, 3, 3, 0, 0), arguments(0, 0, 1, 1, 2, 2, 1, 1, 2, 2, 0, 0, 0, 0));
    }
}
