package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class GmtTimeZoneTest_Purified extends AbstractLangTest {

    @Test
    public void testGetID_1() {
        assertEquals("GMT+00:00", new GmtTimeZone(false, 0, 0).getID());
    }

    @Test
    public void testGetID_2() {
        assertEquals("GMT+01:02", new GmtTimeZone(false, 1, 2).getID());
    }

    @Test
    public void testGetID_3() {
        assertEquals("GMT+11:22", new GmtTimeZone(false, 11, 22).getID());
    }

    @Test
    public void testGetID_4() {
        assertEquals("GMT-01:02", new GmtTimeZone(true, 1, 2).getID());
    }

    @Test
    public void testGetID_5() {
        assertEquals("GMT-11:22", new GmtTimeZone(true, 11, 22).getID());
    }

    @Test
    public void testGetOffset_1() {
        assertEquals(0, new GmtTimeZone(false, 0, 0).getOffset(234304));
    }

    @Test
    public void testGetOffset_2() {
        assertEquals(-(6 * 60 + 30) * 60 * 1000, new GmtTimeZone(true, 6, 30).getOffset(1, 1, 1, 1, 1, 1));
    }
}
