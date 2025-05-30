package com.graphhopper.routing.weighting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightingTest_Purified {

    @Test
    public void testToString_1() {
        assertTrue(Weighting.isValidName("blup"));
    }

    @Test
    public void testToString_2() {
        assertTrue(Weighting.isValidName("blup_a"));
    }

    @Test
    public void testToString_3() {
        assertTrue(Weighting.isValidName("blup|a"));
    }

    @Test
    public void testToString_4() {
        assertFalse(Weighting.isValidName("Blup"));
    }

    @Test
    public void testToString_5() {
        assertFalse(Weighting.isValidName("Blup!"));
    }
}
