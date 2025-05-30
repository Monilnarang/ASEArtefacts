package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.DBTSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

public class DBTTest_Purified {

    public static final String EXAMPLE = "$IIDBT,013.4,f,04.1,M,02.2,F*12";

    private DBTSentence dbt;

    private DBTSentence empty;

    @Before
    public void setUp() throws Exception {
        empty = new DBTParser(TalkerId.II);
        dbt = new DBTParser(EXAMPLE);
    }

    @Test
    public void testConstructor_1() {
        assertEquals("DBT", empty.getSentenceId());
    }

    @Test
    public void testConstructor_2() {
        assertEquals(TalkerId.II, empty.getTalkerId());
    }

    @Test
    public void testConstructor_3() {
        assertEquals(6, empty.getFieldCount());
    }
}
