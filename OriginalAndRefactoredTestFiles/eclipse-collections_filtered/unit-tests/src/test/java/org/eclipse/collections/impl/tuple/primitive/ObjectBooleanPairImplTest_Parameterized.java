package org.eclipse.collections.impl.tuple.primitive;

import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ObjectBooleanPairImplTest_Parameterized {

    @Test
    public void testEqualsAndHashCode_1() {
        Verify.assertEqualsAndHashCode(PrimitiveTuples.pair("true", false), PrimitiveTuples.pair("true", false));
    }

    @Test
    public void testEqualsAndHashCode_2() {
        assertNotEquals(PrimitiveTuples.pair("false", true), PrimitiveTuples.pair("true", false));
    }

    @Test
    public void testEqualsAndHashCode_3() {
        assertEquals(Tuples.pair("true", false).hashCode(), PrimitiveTuples.pair("true", false).hashCode());
    }

    @Test
    public void getTwo_1() {
        assertTrue(PrimitiveTuples.pair("false", true).getTwo());
    }

    @Test
    public void getTwo_2() {
        assertFalse(PrimitiveTuples.pair("true", false).getTwo());
    }

    @Test
    public void compareTo_3() {
        assertEquals(-1, PrimitiveTuples.pair("true", false).compareTo(PrimitiveTuples.pair("true", true)));
    }

    @ParameterizedTest
    @MethodSource("Provider_getOne_1to2")
    public void getOne_1to2(boolean param1, boolean param2) {
        assertEquals(param1, PrimitiveTuples.pair(param2, false).getOne());
    }

    static public Stream<Arguments> Provider_getOne_1to2() {
        return Stream.of(arguments(true, true), arguments(false, false));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToString_1to2")
    public void testToString_1to2(String param1, boolean param2) {
        assertEquals(param1, PrimitiveTuples.pair(param2, false).toString());
    }

    static public Stream<Arguments> Provider_testToString_1to2() {
        return Stream.of(arguments("true:false", true), arguments("true:true", true));
    }

    @ParameterizedTest
    @MethodSource("Provider_compareTo_1to2")
    public void compareTo_1to2(int param1, boolean param2, boolean param3) {
        assertEquals(param1, PrimitiveTuples.pair(param2, param3).compareTo(PrimitiveTuples.pair("true", false)));
    }

    static public Stream<Arguments> Provider_compareTo_1to2() {
        return Stream.of(arguments(1, true, true), arguments(0, true, true));
    }
}
