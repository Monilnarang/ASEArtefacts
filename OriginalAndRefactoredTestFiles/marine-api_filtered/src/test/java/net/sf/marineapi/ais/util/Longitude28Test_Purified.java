package net.sf.marineapi.ais.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Longitude28Test_Purified {

    private static final double DELTA = 0.00001;

    @Test
    public void conversionToKnotsWorks_1() {
        assertEquals(-180.0, Longitude28.toDegrees(Double.valueOf(-180.0 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionToKnotsWorks_2() {
        assertEquals(-45.1, Longitude28.toDegrees(Double.valueOf(-45.1 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionToKnotsWorks_3() {
        assertEquals(0.0, Longitude28.toDegrees(0), 0.00001);
    }

    @Test
    public void conversionToKnotsWorks_4() {
        assertEquals(45.9, Longitude28.toDegrees(Double.valueOf(45.9 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionToKnotsWorks_5() {
        assertEquals(180.0, Longitude28.toDegrees(Double.valueOf(180.0 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues_1() {
        assertEquals(-201.1, Longitude28.toDegrees(Double.valueOf(-201.1 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues_2() {
        assertEquals(181.1, Longitude28.toDegrees(Double.valueOf(181.1 * 60 * 10000).intValue()), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues_3() {
        assertEquals(202.3, Longitude28.toDegrees(Double.valueOf(202.3 * 60 * 10000).intValue()), DELTA);
    }
}
