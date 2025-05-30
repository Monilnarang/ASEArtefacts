package org.eclipse.collections.impl.map.immutable.primitive;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.map.primitive.ImmutableObjectBooleanMap;
import org.eclipse.collections.api.map.primitive.MutableObjectBooleanMap;
import org.eclipse.collections.impl.factory.primitive.ObjectBooleanMaps;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectBooleanHashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ObjectBooleanMapFactoryTest_Parameterized {

    @Test
    public void of_1() {
        assertEquals(new ObjectBooleanHashMap<>(), ObjectBooleanMaps.mutable.of());
    }

    @Test
    public void of_2() {
        assertEquals(ObjectBooleanMaps.mutable.of(), ObjectBooleanMaps.mutable.empty());
    }

    @Test
    public void of_3() {
        assertEquals(ObjectBooleanMaps.mutable.empty().toImmutable(), ObjectBooleanMaps.immutable.empty());
    }

    @Test
    public void of_4() {
        assertEquals(ObjectBooleanMaps.mutable.empty().toImmutable(), ObjectBooleanMaps.immutable.of());
    }

    @Test
    public void of_5() {
        assertTrue(ObjectBooleanMaps.immutable.empty() instanceof ImmutableObjectBooleanEmptyMap);
    }

    @Test
    public void of_6() {
        assertEquals(ObjectBooleanHashMap.newWithKeysValues("2", true).toImmutable(), ObjectBooleanMaps.immutable.of("2", true));
    }

    @Test
    public void of_7() {
        assertTrue(ObjectBooleanMaps.immutable.of("2", true) instanceof ImmutableObjectBooleanSingletonMap);
    }

    @Test
    public void of_8() {
        assertEquals(ObjectBooleanMaps.mutable.of("2", true), ObjectBooleanHashMap.newWithKeysValues("2", true));
    }

    @Test
    public void of_9() {
        assertEquals(ObjectBooleanMaps.mutable.of("2", true, 3, false), ObjectBooleanHashMap.newWithKeysValues("2", true, 3, false));
    }

    @Test
    public void of_10() {
        assertEquals(ObjectBooleanMaps.mutable.of("2", true, 3, false, 4, false), ObjectBooleanHashMap.newWithKeysValues("2", true, 3, false, 4, false));
    }

    @Test
    public void of_11() {
        assertEquals(ObjectBooleanMaps.mutable.of("2", true, 3, false, 4, false, 5, true), ObjectBooleanHashMap.newWithKeysValues("2", true, 3, false, 4, false, 5, true));
    }

    @Test
    public void with_1() {
        assertEquals(ObjectBooleanMaps.mutable.with(), ObjectBooleanMaps.mutable.empty());
    }

    @Test
    public void with_4() {
        assertEquals(ObjectBooleanMaps.mutable.with("2", true, 3, false), ObjectBooleanHashMap.newWithKeysValues("2", true, 3, false));
    }

    @Test
    public void with_5() {
        assertEquals(ObjectBooleanMaps.mutable.with("2", true, 3, false, 4, false), ObjectBooleanHashMap.newWithKeysValues("2", true, 3, false, 4, false));
    }

    @Test
    public void with_6() {
        assertEquals(ObjectBooleanMaps.mutable.with("2", true, 3, false, 4, false, 5, true), ObjectBooleanHashMap.newWithKeysValues("2", true, 3, false, 4, false, 5, true));
    }

    @Test
    public void ofAll_1() {
        assertEquals(ObjectBooleanMaps.mutable.empty(), ObjectBooleanMaps.mutable.ofAll(ObjectBooleanMaps.mutable.empty()));
    }

    @Test
    public void ofAll_2() {
        assertEquals(ObjectBooleanMaps.mutable.empty().toImmutable(), ObjectBooleanMaps.immutable.ofAll(ObjectBooleanMaps.mutable.empty()));
    }

    @Test
    public void ofAll_3() {
        assertSame(ObjectBooleanMaps.immutable.empty(), ObjectBooleanMaps.immutable.ofAll(ObjectBooleanMaps.immutable.empty()));
    }

    @Test
    public void ofAll_4() {
        assertEquals(ObjectBooleanHashMap.newWithKeysValues("2", true), ObjectBooleanMaps.mutable.ofAll(ObjectBooleanHashMap.newWithKeysValues("2", true)));
    }

    @Test
    public void ofAll_5() {
        assertEquals(ObjectBooleanHashMap.newWithKeysValues("2", true).toImmutable(), ObjectBooleanMaps.immutable.ofAll(ObjectBooleanHashMap.newWithKeysValues("2", true)));
    }

    @Test
    public void ofAll_6() {
        assertEquals(ObjectBooleanHashMap.newWithKeysValues("2", true, "3", false).toImmutable(), ObjectBooleanMaps.immutable.ofAll(ObjectBooleanHashMap.newWithKeysValues("2", true, "3", false)));
    }

    @ParameterizedTest
    @MethodSource("Provider_with_2to3")
    public void with_2to3(int param1, int param2) {
        assertEquals(ObjectBooleanMaps.mutable.with(param1, param2), ObjectBooleanHashMap.newWithKeysValues("2", false));
    }

    static public Stream<Arguments> Provider_with_2to3() {
        return Stream.of(arguments(2, 2), arguments(2, 2));
    }
}
