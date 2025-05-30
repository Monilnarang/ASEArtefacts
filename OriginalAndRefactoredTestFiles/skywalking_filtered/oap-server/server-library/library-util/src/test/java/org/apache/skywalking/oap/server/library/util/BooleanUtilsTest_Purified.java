package org.apache.skywalking.oap.server.library.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanUtilsTest_Purified {

    @Test
    public void testValueToBoolean_1() {
        assertEquals(1, BooleanUtils.booleanToValue(true));
    }

    @Test
    public void testValueToBoolean_2() {
        assertEquals(0, BooleanUtils.booleanToValue(false));
    }

    @Test
    public void testBooleanToValue_1() {
        assertTrue(BooleanUtils.valueToBoolean(1));
    }

    @Test
    public void testBooleanToValue_2() {
        assertFalse(BooleanUtils.valueToBoolean(0));
    }
}
