package org.jivesoftware.util;

import org.jivesoftware.util.Version.ReleaseStatus;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class VersionTest_Purified {

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testVersionComparisons_1_testMerged_1() {
        Version test123 = new Version("1.2.3");
        Version test321 = new Version("3.2.1");
        Version test322 = new Version("3.2.2");
        Version test333 = new Version("3.3.3");
        Version test300 = new Version("3.0.0");
        Version test3100 = new Version("3.10.0");
        Version test29999 = new Version("2.999.999");
        assertEquals(-1, test123.compareTo(test321));
        assertEquals(0, test123.compareTo(test123));
        assertEquals(1, test321.compareTo(test123));
        assertTrue(test322.isNewerThan(test321));
        assertFalse(test322.isNewerThan(test333));
        assertFalse(test300.isNewerThan(test321));
        assertTrue(test3100.isNewerThan(test333));
        assertTrue(test3100.isNewerThan(test29999));
        assertTrue(test300.isNewerThan(test29999));
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testVersionComparisons_10() {
        Version test3100Alpha = new Version("3.10.0 Alpha");
        Version test3100Beta = new Version("3.10.0 Beta");
        assertTrue(test3100Beta.isNewerThan(test3100Alpha));
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testVersionComparisons_11() {
        Version test3100Beta1 = new Version("3.10.0 Beta 1");
        Version test3100Beta2 = new Version("3.10.0 Beta 2");
        assertTrue(test3100Beta2.isNewerThan(test3100Beta1));
    }
}
