package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Before;
import org.junit.Test;
import net.sf.marineapi.nmea.sentence.DTASentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

public class DTATest_Purified {

    public static final String EXAMPLE_MC = "$GFDTA,1,1.5,99,600,11067,2002/03/01 00:30:28,HF-1xxx,1*3C";

    public static final String EXAMPLE2 = "$GFDTA,7.7,98,600,5527,2011/01/27 13:29:28,HFH2O-1xxx,1*2B";

    private DTASentence gasFinderMC;

    private DTASentence gasFinder2;

    @Before
    public void setUp() throws Exception {
        gasFinderMC = new DTAParser(EXAMPLE_MC);
        gasFinder2 = new DTAParser(EXAMPLE2);
    }

    @Test
    public void testGetChannelNumber_1() {
        assertEquals(1, gasFinderMC.getChannelNumber());
    }

    @Test
    public void testGetChannelNumber_2() {
        assertEquals(1, gasFinder2.getChannelNumber());
    }

    @Test
    public void testGetGasConcentration_1() {
        assertEquals(1.5, gasFinderMC.getGasConcentration(), 0.1);
    }

    @Test
    public void testGetGasConcentration_2() {
        assertEquals(7.7, gasFinder2.getGasConcentration(), 0.1);
    }

    @Test
    public void testGetConfidenceFactorR2_1() {
        assertEquals(99, gasFinderMC.getConfidenceFactorR2());
    }

    @Test
    public void testGetConfidenceFactorR2_2() {
        assertEquals(98, gasFinder2.getConfidenceFactorR2());
    }

    @Test
    public void testGetDistance_1() {
        assertEquals(600, gasFinderMC.getDistance(), 0.1);
    }

    @Test
    public void testGetDistance_2() {
        assertEquals(600, gasFinder2.getDistance(), 0.1);
    }

    @Test
    public void testGetLightLevel_1() {
        assertEquals(11067, gasFinderMC.getLightLevel());
    }

    @Test
    public void testGetLightLevel_2() {
        assertEquals(5527, gasFinder2.getLightLevel());
    }

    @Test
    public void testGetSerialNumber_1() {
        assertEquals("HF-1xxx", gasFinderMC.getSerialNumber());
    }

    @Test
    public void testGetSerialNumber_2() {
        assertEquals("HFH2O-1xxx", gasFinder2.getSerialNumber());
    }

    @Test
    public void testGetStatusCode_1() {
        assertEquals(1, gasFinderMC.getStatusCode());
    }

    @Test
    public void testGetStatusCode_2() {
        assertEquals(1, gasFinder2.getStatusCode());
    }
}
