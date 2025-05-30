package org.graylog2.plugin;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VersionTest_Purified {

    @Test
    public void testGetName_1() throws Exception {
        assertEquals("0.20.0", Version.from(0, 20, 0).toString());
    }

    @Test
    public void testGetName_2() throws Exception {
        assertEquals("1.0.0", Version.from(1, 0, 0).toString());
    }

    @Test
    public void testGetName_3() throws Exception {
        assertEquals("1.2.3", Version.from(1, 2, 3).toString());
    }

    @Test
    public void testGetName_4() throws Exception {
        assertEquals("0.0.7", Version.from(0, 0, 7).toString());
    }

    @Test
    public void testGetName_5() throws Exception {
        assertEquals("1.0.0-preview.1", Version.from(1, 0, 0, "preview.1").toString());
    }

    @Test
    public void testGetName_6() throws Exception {
        assertEquals("1.0.0-preview.1+deadbeef", Version.from(1, 0, 0, "preview.1", "deadbeef").toString());
    }

    @Test
    public void testEquals_1() throws Exception {
        assertTrue(Version.from(0, 20, 0).equals(Version.from(0, 20, 0)));
    }

    @Test
    public void testEquals_2() throws Exception {
        assertTrue(Version.from(0, 20, 0, "preview.1").equals(Version.from(0, 20, 0, "preview.1")));
    }

    @Test
    public void testEquals_3() throws Exception {
        assertTrue(Version.from(1, 2, 3).equals(Version.from(1, 2, 3)));
    }

    @Test
    public void testEquals_4_testMerged_4() throws Exception {
        Version v = Version.from(0, 20, 0);
        assertEquals(Version.from(0, 20, 0), v);
        assertFalse(Version.from(0, 20, 0).equals(Version.from(0, 20, 1)));
        assertFalse(Version.from(0, 20, 0, "preview.1").equals(Version.from(0, 20, 0, "preview.2")));
        assertFalse(Version.from(0, 20, 0).equals(null));
    }
}
