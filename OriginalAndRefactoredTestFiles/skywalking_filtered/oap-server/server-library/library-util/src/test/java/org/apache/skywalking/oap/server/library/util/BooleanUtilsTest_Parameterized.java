package org.apache.skywalking.oap.server.library.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BooleanUtilsTest_Parameterized {

    @Test
    public void testBooleanToValue_1() {
        assertTrue(BooleanUtils.valueToBoolean(1));
    }

    @Test
    public void testBooleanToValue_2() {
        assertFalse(BooleanUtils.valueToBoolean(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValueToBoolean_1to2")
    public void testValueToBoolean_1to2(int param1) {
        assertEquals(param1, BooleanUtils.booleanToValue(true));
    }

    static public Stream<Arguments> Provider_testValueToBoolean_1to2() {
        return Stream.of(arguments(1), arguments(0));
    }
}
