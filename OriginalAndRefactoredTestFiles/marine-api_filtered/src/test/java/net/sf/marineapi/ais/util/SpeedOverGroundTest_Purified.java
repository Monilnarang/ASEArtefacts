package net.sf.marineapi.ais.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SpeedOverGroundTest_Purified {

    private static final double DELTA = 0.001;

    @Test
    public void conversionToKnotsWorks_1() {
        assertEquals(0.0, SpeedOverGround.toKnots(0), DELTA);
    }

    @Test
    public void conversionToKnotsWorks_2() {
        assertEquals(0.1, SpeedOverGround.toKnots(1), DELTA);
    }

    @Test
    public void conversionToKnotsWorks_3() {
        assertEquals(90.9, SpeedOverGround.toKnots(909), DELTA);
    }

    @Test
    public void conversionToKnotsWorks_4() {
        assertEquals(102.2, SpeedOverGround.toKnots(1022), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues_1() {
        assertEquals(-10.1, SpeedOverGround.toKnots(-101), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues_2() {
        assertEquals(102.3, SpeedOverGround.toKnots(1023), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues_3() {
        assertEquals(4567.8, SpeedOverGround.toKnots(45678), DELTA);
    }
}
