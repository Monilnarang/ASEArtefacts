package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.MDASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

public class MDATest_Purified {

    public static final String EXAMPLE = "$IIMDA,99700.0,P,1.00,B,3.2,C,,C,,,,C,295.19,T,,M,5.70,N,2.93,M*08";

    public static final String EXAMPLE2 = "$IIMDA,30.0,I,1.0149,B,26.8,C,,C,64.2,16.4,19.5,C,,T,38.7,M,10.88,N,5.60,M*36";

    private MDASentence mda;

    private MDASentence mda2;

    @Before
    public void setUp() throws Exception {
        mda = new MDAParser(EXAMPLE);
        mda2 = new MDAParser(EXAMPLE2);
    }

    @Test
    public void testGetMagneticWindDirection_1() {
        assertTrue(Double.isNaN(mda.getMagneticWindDirection()));
    }

    @Test
    public void testGetMagneticWindDirection_2() {
        assertEquals(38.7, mda2.getMagneticWindDirection(), 0.1);
    }

    @Test
    public void testGetTrueWindDirection_1() {
        assertEquals(295.19, mda.getTrueWindDirection(), 0.1);
    }

    @Test
    public void testGetTrueWindDirection_2() {
        assertTrue(Double.isNaN(mda2.getTrueWindDirection()));
    }

    @Test
    public void testGetWindSpeed_1() {
        assertEquals(2.93, mda.getWindSpeed(), 0.1);
    }

    @Test
    public void testGetWindSpeed_2() {
        assertEquals(5.6, mda2.getWindSpeed(), 0.1);
    }

    @Test
    public void testGetWindSpeedKnots_1() {
        assertEquals(5.7, mda.getWindSpeedKnots(), 0.1);
    }

    @Test
    public void testGetWindSpeedKnots_2() {
        assertEquals(10.88, mda2.getWindSpeedKnots(), 0.1);
    }

    @Test
    public void testGetAbsoluteHumidity_1() {
        assertTrue(Double.isNaN(mda.getAbsoluteHumidity()));
    }

    @Test
    public void testGetAbsoluteHumidity_2() {
        assertEquals(16.4, mda2.getAbsoluteHumidity(), 0.1);
    }

    @Test
    public void testGetAirTemperature_1() {
        assertEquals(3.2, mda.getAirTemperature(), 0.1);
    }

    @Test
    public void testGetAirTemperature_2() {
        assertEquals(26.8, mda2.getAirTemperature(), 0.1);
    }

    @Test
    public void testGetSecondaryBarometricPressure_1() {
        assertEquals(1.0, mda.getSecondaryBarometricPressure(), 0.1);
    }

    @Test
    public void testGetSecondaryBarometricPressure_2() {
        assertEquals('B', mda.getSecondaryBarometricPressureUnit());
    }

    @Test
    public void testGetSecondaryBarometricPressure_3() {
        assertEquals(1.0, mda2.getSecondaryBarometricPressure(), 0.1);
    }

    @Test
    public void testGetSecondaryBarometricPressure_4() {
        assertEquals('B', mda2.getSecondaryBarometricPressureUnit());
    }

    @Test
    public void testGetPrimaryBarometricPressure_1() {
        assertEquals(99700.0, mda.getPrimaryBarometricPressure(), 0.1);
    }

    @Test
    public void testGetPrimaryBarometricPressure_2() {
        assertEquals('P', mda.getPrimaryBarometricPressureUnit());
    }

    @Test
    public void testGetPrimaryBarometricPressure_3() {
        assertEquals(30.0, mda2.getPrimaryBarometricPressure(), 0.1);
    }

    @Test
    public void testGetPrimaryBarometricPressure_4() {
        assertEquals('I', mda2.getPrimaryBarometricPressureUnit());
    }

    @Test
    public void testGetRelativeHumidity_1() {
        assertTrue(Double.isNaN(mda.getRelativeHumidity()));
    }

    @Test
    public void testGetRelativeHumidity_2() {
        assertEquals(64.2, mda2.getRelativeHumidity(), 0.1);
    }

    @Test
    public void testGetDewPoint_1() {
        assertTrue(Double.isNaN(mda.getDewPoint()));
    }

    @Test
    public void testGetDewPoint_2() {
        assertEquals(19.5, mda2.getDewPoint(), 0.1);
    }

    @Test
    public void testGetWaterTemperature_1() {
        assertTrue(Double.isNaN(mda.getWaterTemperature()));
    }

    @Test
    public void testGetWaterTemperature_2() {
        assertTrue(Double.isNaN(mda2.getWaterTemperature()));
    }
}
