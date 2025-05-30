package net.sf.marineapi.provider;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.File;
import java.io.FileInputStream;
import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.parser.GGATest;
import net.sf.marineapi.nmea.parser.GLLTest;
import net.sf.marineapi.nmea.parser.RMCTest;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.provider.event.PositionEvent;
import net.sf.marineapi.provider.event.PositionListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PositionProviderTest_Purified implements PositionListener {

    PositionEvent event;

    PositionProvider instance;

    @Before
    public void setUp() throws Exception {
        File f = new File("target/test-classes/data/Navibe-GM720.txt");
        FileInputStream str = new FileInputStream(f);
        SentenceReader r = new SentenceReader(str);
        instance = new PositionProvider(r);
        instance.addListener(this);
    }

    @After
    public void tearDown() throws Exception {
        instance.removeListener(this);
    }

    public void providerUpdate(PositionEvent evt) {
        event = evt;
    }

    @Test
    public void testSentenceReadWithGGA_1() {
        assertNull(event);
    }

    @Test
    public void testSentenceReadWithGGA_2() {
        assertNull(event);
    }

    @Test
    public void testSentenceReadWithGGA_3() {
        assertNull(event);
    }

    @Test
    public void testSentenceReadWithGGA_4() {
        assertNotNull(event);
    }

    @Test
    public void testSentenceReadWithGLL_1() {
        assertNull(event);
    }

    @Test
    public void testSentenceReadWithGLL_2() {
        assertNull(event);
    }

    @Test
    public void testSentenceReadWithGLL_3() {
        assertNotNull(event);
    }

    @Test
    public void testSentenceReadWithLegacyRMC_1() {
        assertNull(event);
    }

    @Test
    public void testSentenceReadWithLegacyRMC_2() {
        assertNull(event);
    }

    @Test
    public void testSentenceReadWithLegacyRMC_3() {
        assertNotNull(event);
    }
}
