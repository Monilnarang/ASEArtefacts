package net.sf.marineapi.ais.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Latitude27Test_Purified {

    private static final double DELTA = 0.00001;

    @Test
    public void conversionToKnotsWorks_1() {
        assertEquals(-90.0, Latitude27.toDegrees(Double.valueOf(-90.0 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionToKnotsWorks_2() {
        assertEquals(-45.1, Latitude27.toDegrees(Double.valueOf(-45.1 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionToKnotsWorks_3() {
        assertEquals(0.0, Latitude27.toDegrees(0), 0.00001);
    }

    @Test
    public void conversionToKnotsWorks_4() {
        assertEquals(45.9, Latitude27.toDegrees(Double.valueOf(45.9 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionToKnotsWorks_5() {
        assertEquals(90.0, Latitude27.toDegrees(Double.valueOf(90.0 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues_1() {
        assertEquals(-101.1, Latitude27.toDegrees(Double.valueOf(-101.1 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues_2() {
        assertEquals(91.1, Latitude27.toDegrees(Double.valueOf(91.1 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues_3() {
        assertEquals(102.3, Latitude27.toDegrees(Double.valueOf(102.3 * 60 * 10000).intValue()), DELTA);
    }
}
