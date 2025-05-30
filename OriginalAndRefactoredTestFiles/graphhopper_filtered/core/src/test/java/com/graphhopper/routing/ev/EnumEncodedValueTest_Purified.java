package com.graphhopper.routing.ev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumEncodedValueTest_Purified {

    @Test
    public void testSize_1() {
        assertEquals(3, 32 - Integer.numberOfLeadingZeros(7 - 1));
    }

    @Test
    public void testSize_2() {
        assertEquals(3, 32 - Integer.numberOfLeadingZeros(8 - 1));
    }

    @Test
    public void testSize_3() {
        assertEquals(4, 32 - Integer.numberOfLeadingZeros(9 - 1));
    }

    @Test
    public void testSize_4() {
        assertEquals(4, 32 - Integer.numberOfLeadingZeros(16 - 1));
    }

    @Test
    public void testSize_5() {
        assertEquals(5, 32 - Integer.numberOfLeadingZeros(17 - 1));
    }
}
