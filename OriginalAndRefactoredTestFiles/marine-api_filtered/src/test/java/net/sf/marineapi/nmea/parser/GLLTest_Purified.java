package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.*;
import org.junit.Before;
import org.junit.Test;

public class GLLTest_Purified {

    public static final String EXAMPLE = "$GPGLL,6011.552,N,02501.941,E,120045,A*26";

    private GLLParser empty;

    private GLLParser instance;

    @Before
    public void setUp() {
        try {
            empty = new GLLParser(TalkerId.GP);
            instance = new GLLParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetStatus_1() {
        assertEquals(DataStatus.ACTIVE, instance.getStatus());
    }

    @Test
    public void testSetStatus_2() {
        instance.setStatus(DataStatus.VOID);
        assertEquals(DataStatus.VOID, instance.getStatus());
    }
}
