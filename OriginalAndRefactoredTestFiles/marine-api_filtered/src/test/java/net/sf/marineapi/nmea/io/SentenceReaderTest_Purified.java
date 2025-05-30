package net.sf.marineapi.nmea.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import net.sf.marineapi.nmea.event.AbstractSentenceListener;
import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.parser.BODTest;
import net.sf.marineapi.nmea.parser.GGATest;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.parser.TXTTest;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TXTSentence;
import net.sf.marineapi.test.util.UDPServerMock;
import org.junit.Before;
import org.junit.Test;

public class SentenceReaderTest_Purified {

    public final static String TEST_DATA = "src/test/resources/data/Navibe-GM720.txt";

    private Sentence sentence;

    private SentenceReader reader;

    private SentenceListener dummyListener;

    private SentenceListener testListener;

    private boolean paused;

    private boolean started;

    private boolean stopped;

    private InputStream stream;

    @Before
    public void setUp() throws Exception {
        File file = new File(TEST_DATA);
        stream = new FileInputStream(file);
        reader = new SentenceReader(stream);
        dummyListener = new DummySentenceListener();
        testListener = new TestSentenceListener();
        reader.addSentenceListener(dummyListener);
        reader.addSentenceListener(testListener, SentenceId.GGA);
    }

    public class DummySentenceListener implements SentenceListener {

        public void readingPaused() {
        }

        public void readingStarted() {
        }

        public void readingStopped() {
        }

        public void sentenceRead(SentenceEvent event) {
        }
    }

    public class TestSentenceListener implements SentenceListener {

        public void readingPaused() {
            paused = true;
        }

        public void readingStarted() {
            started = true;
        }

        public void readingStopped() {
            stopped = true;
        }

        public void sentenceRead(SentenceEvent event) {
            sentence = event.getSentence();
        }
    }

    public class DummyDataReader extends AbstractDataReader {

        private String sentence;

        public DummyDataReader(String sentence) {
            this.sentence = sentence;
        }

        @Override
        public String read() throws Exception {
            return this.sentence;
        }
    }

    @Test
    public void testRemoveSentenceListener_1() {
        assertFalse(started);
    }

    @Test
    public void testRemoveSentenceListener_2() {
        assertFalse(started);
    }

    @Test
    public void testFireReadingPaused_1() {
        assertFalse(paused);
    }

    @Test
    public void testFireReadingPaused_2() {
        assertTrue(paused);
    }

    @Test
    public void testFireReadingStarted_1() {
        assertFalse(started);
    }

    @Test
    public void testFireReadingStarted_2() {
        assertTrue(started);
    }

    @Test
    public void testFireReadingStopped_1() {
        assertFalse(stopped);
    }

    @Test
    public void testFireReadingStopped_2() {
        assertTrue(stopped);
    }

    @Test
    public void testFireSentenceEventWithExpectedType_1() {
        assertNull(sentence);
    }

    @Test
    public void testFireSentenceEventWithExpectedType_2() {
        SentenceFactory sf = SentenceFactory.getInstance();
        Sentence s = sf.createParser(GGATest.EXAMPLE);
        reader.fireSentenceEvent(s);
        assertEquals(s, sentence);
    }

    @Test
    public void testFireSentenceEventWithUnexpectedType_1() {
        assertNull(sentence);
    }

    @Test
    public void testFireSentenceEventWithUnexpectedType_2() {
        assertNull(sentence);
    }
}
