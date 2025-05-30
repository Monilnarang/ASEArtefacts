package net.sf.marineapi.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileInputStream;
import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.parser.HDGTest;
import net.sf.marineapi.nmea.parser.HDMTest;
import net.sf.marineapi.nmea.parser.HDTTest;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.provider.event.HeadingEvent;
import net.sf.marineapi.provider.event.HeadingListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HeadingProviderTest_Purified implements HeadingListener {

    private SentenceFactory factory;

    private HeadingProvider instance;

    private HeadingEvent event;

    @Before
    public void setUp() throws Exception {
        factory = SentenceFactory.getInstance();
        File file = new File("target/test-classes/data/sample1.txt");
        FileInputStream str = new FileInputStream(file);
        SentenceReader r = new SentenceReader(str);
        instance = new HeadingProvider(r);
        instance.addListener(this);
        event = null;
    }

    @After
    public void tearDown() {
        instance.removeListener(this);
    }

    public void providerUpdate(HeadingEvent evt) {
        this.event = evt;
    }

    @Test
    public void testHDMSentenceRead_1() {
        assertNull(event);
    }

    @Test
    public void testHDMSentenceRead_2() {
        assertNotNull(event);
    }

    @Test
    public void testHDMSentenceRead_3() {
        assertEquals(90.0, event.getHeading(), 0.1);
    }

    @Test
    public void testHDMSentenceRead_4() {
        assertFalse(event.isTrue());
    }

    @Test
    public void testHDTSentenceRead_1() {
        assertNull(event);
    }

    @Test
    public void testHDTSentenceRead_2() {
        assertNotNull(event);
    }

    @Test
    public void testHDTSentenceRead_3() {
        assertEquals(90.1, event.getHeading(), 0.1);
    }

    @Test
    public void testHDTSentenceRead_4() {
        assertTrue(event.isTrue());
    }

    @Test
    public void testHDGSentenceRead_1() {
        assertNull(event);
    }

    @Test
    public void testHDGSentenceRead_2() {
        assertNotNull(event);
    }

    @Test
    public void testHDGSentenceRead_3() {
        assertEquals(123.4, event.getHeading(), 0.1);
    }

    @Test
    public void testHDGSentenceRead_4() {
        assertFalse(event.isTrue());
    }
}
