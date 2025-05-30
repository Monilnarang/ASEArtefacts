package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.List;
import net.sf.marineapi.nmea.sentence.BODSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.test.util.BARParser;
import net.sf.marineapi.test.util.FOOParser;
import net.sf.marineapi.test.util.FOOSentence;
import net.sf.marineapi.test.util.VDMParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SentenceFactoryTest_Purified {

    private final SentenceFactory instance = SentenceFactory.getInstance();

    @Before
    public void setUp() throws Exception {
        instance.reset();
    }

    @After
    public void tearDown() throws Exception {
        instance.reset();
    }

    @Test
    public void testHasParser_1() {
        assertTrue(instance.hasParser("GLL"));
    }

    @Test
    public void testHasParser_2() {
        assertFalse(instance.hasParser("ABC"));
    }

    @Test
    public void testGetInstance_1() {
        assertNotNull(instance);
    }

    @Test
    public void testGetInstance_2() {
        assertTrue(instance == SentenceFactory.getInstance());
    }

    @Test
    public void testGetInstance_3() {
        assertEquals(instance, SentenceFactory.getInstance());
    }
}
