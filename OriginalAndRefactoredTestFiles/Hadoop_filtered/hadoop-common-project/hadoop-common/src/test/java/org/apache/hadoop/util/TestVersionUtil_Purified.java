package org.apache.hadoop.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestVersionUtil_Purified {

    private static void assertExpectedValues(String lower, String higher) {
        assertTrue(VersionUtil.compareVersions(lower, higher) < 0);
        assertTrue(VersionUtil.compareVersions(higher, lower) > 0);
    }

    @Test
    public void testCompareVersions_1() {
        assertEquals(0, VersionUtil.compareVersions("2.0.0", "2.0.0"));
    }

    @Test
    public void testCompareVersions_2() {
        assertEquals(0, VersionUtil.compareVersions("2.0.0a", "2.0.0a"));
    }

    @Test
    public void testCompareVersions_3() {
        assertEquals(0, VersionUtil.compareVersions("2.0.0-SNAPSHOT", "2.0.0-SNAPSHOT"));
    }

    @Test
    public void testCompareVersions_4() {
        assertEquals(0, VersionUtil.compareVersions("1", "1"));
    }

    @Test
    public void testCompareVersions_5() {
        assertEquals(0, VersionUtil.compareVersions("1", "1.0"));
    }

    @Test
    public void testCompareVersions_6() {
        assertEquals(0, VersionUtil.compareVersions("1", "1.0.0"));
    }

    @Test
    public void testCompareVersions_7() {
        assertEquals(0, VersionUtil.compareVersions("1.0", "1"));
    }

    @Test
    public void testCompareVersions_8() {
        assertEquals(0, VersionUtil.compareVersions("1.0", "1.0"));
    }

    @Test
    public void testCompareVersions_9() {
        assertEquals(0, VersionUtil.compareVersions("1.0", "1.0.0"));
    }

    @Test
    public void testCompareVersions_10() {
        assertEquals(0, VersionUtil.compareVersions("1.0.0", "1"));
    }

    @Test
    public void testCompareVersions_11() {
        assertEquals(0, VersionUtil.compareVersions("1.0.0", "1.0"));
    }

    @Test
    public void testCompareVersions_12() {
        assertEquals(0, VersionUtil.compareVersions("1.0.0", "1.0.0"));
    }

    @Test
    public void testCompareVersions_13() {
        assertEquals(0, VersionUtil.compareVersions("1.0.0-alpha-1", "1.0.0-a1"));
    }

    @Test
    public void testCompareVersions_14() {
        assertEquals(0, VersionUtil.compareVersions("1.0.0-alpha-2", "1.0.0-a2"));
    }

    @Test
    public void testCompareVersions_15() {
        assertEquals(0, VersionUtil.compareVersions("1.0.0-alpha1", "1.0.0-alpha-1"));
    }

    @Test
    public void testCompareVersions_16() {
        assertEquals(0, VersionUtil.compareVersions("1a0", "1.0.0-alpha-0"));
    }

    @Test
    public void testCompareVersions_17() {
        assertEquals(0, VersionUtil.compareVersions("1a0", "1-a0"));
    }

    @Test
    public void testCompareVersions_18() {
        assertEquals(0, VersionUtil.compareVersions("1.a0", "1-a0"));
    }

    @Test
    public void testCompareVersions_19() {
        assertEquals(0, VersionUtil.compareVersions("1.a0", "1.0.0-alpha-0"));
    }

    @Test
    public void testCompareVersions_20() {
        assertExpectedValues("1", "2.0.0");
    }

    @Test
    public void testCompareVersions_21() {
        assertExpectedValues("1.0.0", "2");
    }

    @Test
    public void testCompareVersions_22() {
        assertExpectedValues("1.0.0", "2.0.0");
    }

    @Test
    public void testCompareVersions_23() {
        assertExpectedValues("1.0", "2.0.0");
    }

    @Test
    public void testCompareVersions_24() {
        assertExpectedValues("1.0.0", "2.0.0");
    }

    @Test
    public void testCompareVersions_25() {
        assertExpectedValues("1.0.0", "1.0.0a");
    }

    @Test
    public void testCompareVersions_26() {
        assertExpectedValues("1.0.0.0", "2.0.0");
    }

    @Test
    public void testCompareVersions_27() {
        assertExpectedValues("1.0.0", "1.0.0-dev");
    }

    @Test
    public void testCompareVersions_28() {
        assertExpectedValues("1.0.0", "1.0.1");
    }

    @Test
    public void testCompareVersions_29() {
        assertExpectedValues("1.0.0", "1.0.2");
    }

    @Test
    public void testCompareVersions_30() {
        assertExpectedValues("1.0.0", "1.1.0");
    }

    @Test
    public void testCompareVersions_31() {
        assertExpectedValues("2.0.0", "10.0.0");
    }

    @Test
    public void testCompareVersions_32() {
        assertExpectedValues("1.0.0", "1.0.0a");
    }

    @Test
    public void testCompareVersions_33() {
        assertExpectedValues("1.0.2a", "1.0.10");
    }

    @Test
    public void testCompareVersions_34() {
        assertExpectedValues("1.0.2a", "1.0.2b");
    }

    @Test
    public void testCompareVersions_35() {
        assertExpectedValues("1.0.2a", "1.0.2ab");
    }

    @Test
    public void testCompareVersions_36() {
        assertExpectedValues("1.0.0a1", "1.0.0a2");
    }

    @Test
    public void testCompareVersions_37() {
        assertExpectedValues("1.0.0a2", "1.0.0a10");
    }

    @Test
    public void testCompareVersions_38() {
        assertExpectedValues("1.0", "1.a");
    }

    @Test
    public void testCompareVersions_39() {
        assertExpectedValues("1.a0", "1.0");
    }

    @Test
    public void testCompareVersions_40() {
        assertExpectedValues("1a0", "1.0");
    }

    @Test
    public void testCompareVersions_41() {
        assertExpectedValues("1.0.1-alpha-1", "1.0.1-alpha-2");
    }

    @Test
    public void testCompareVersions_42() {
        assertExpectedValues("1.0.1-beta-1", "1.0.1-beta-2");
    }

    @Test
    public void testCompareVersions_43() {
        assertExpectedValues("1.0-SNAPSHOT", "1.0");
    }

    @Test
    public void testCompareVersions_44() {
        assertExpectedValues("1.0.0-SNAPSHOT", "1.0");
    }

    @Test
    public void testCompareVersions_45() {
        assertExpectedValues("1.0.0-SNAPSHOT", "1.0.0");
    }

    @Test
    public void testCompareVersions_46() {
        assertExpectedValues("1.0.0", "1.0.1-SNAPSHOT");
    }

    @Test
    public void testCompareVersions_47() {
        assertExpectedValues("1.0.1-SNAPSHOT", "1.0.1");
    }

    @Test
    public void testCompareVersions_48() {
        assertExpectedValues("1.0.1-SNAPSHOT", "1.0.2");
    }

    @Test
    public void testCompareVersions_49() {
        assertExpectedValues("1.0.1-alpha-1", "1.0.1-SNAPSHOT");
    }

    @Test
    public void testCompareVersions_50() {
        assertExpectedValues("1.0.1-beta-1", "1.0.1-SNAPSHOT");
    }

    @Test
    public void testCompareVersions_51() {
        assertExpectedValues("1.0.1-beta-2", "1.0.1-SNAPSHOT");
    }
}
