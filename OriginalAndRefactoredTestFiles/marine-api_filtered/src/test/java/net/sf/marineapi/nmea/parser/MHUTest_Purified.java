package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.MHUSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MHUTest_Purified {

    public static final String EXAMPLE = "$IIMHU,66.0,5.0,3.0,C";

    private MHUSentence mhu;

    private MHUSentence empty;

    @Before
    public void setUp() {
        mhu = new MHUParser(EXAMPLE);
        empty = new MHUParser(TalkerId.II);
        assertEquals(4, mhu.getFieldCount(), 1);
    }

    @Test
    public void testEmptySentenceConstructor_1() {
        assertEquals(TalkerId.II, empty.getTalkerId());
    }

    @Test
    public void testEmptySentenceConstructor_2() {
        assertEquals(SentenceId.MHU.toString(), empty.getSentenceId());
    }

    @Test
    public void testEmptySentenceConstructor_3() {
        assertEquals(4, empty.getFieldCount());
    }

    @Test
    public void testEmptySentenceConstructor_4() {
        assertEquals('C', empty.getDewPointUnit());
    }
}
