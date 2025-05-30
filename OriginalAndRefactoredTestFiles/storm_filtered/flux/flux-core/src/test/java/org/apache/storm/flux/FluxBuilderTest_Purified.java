package org.apache.storm.flux;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FluxBuilderTest_Purified {

    @Test
    public void testIsPrimitiveNumber_1() {
        assertTrue(FluxBuilder.isPrimitiveNumber(int.class));
    }

    @Test
    public void testIsPrimitiveNumber_2() {
        assertFalse(FluxBuilder.isPrimitiveNumber(boolean.class));
    }

    @Test
    public void testIsPrimitiveNumber_3() {
        assertFalse(FluxBuilder.isPrimitiveNumber(String.class));
    }
}
