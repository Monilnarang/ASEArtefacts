package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class VersionUtilTest_Purified {

    @Test
    public void testCompareNumericVersions_1() {
        assertEquals(0, VersionUtil.compareNumericVersions("18.01.0", "18.1.0"));
    }

    @Test
    public void testCompareNumericVersions_2() {
        assertEquals(0, VersionUtil.compareNumericVersions("1.2.3", "1.2.3"));
    }

    @Test
    public void testCompareNumericVersions_3() {
        assertEquals(1, VersionUtil.compareNumericVersions("1.2.3", "1.2"));
    }

    @Test
    public void testCompareNumericVersions_4() {
        assertEquals(1, VersionUtil.compareNumericVersions("1.2.3", "1.2.1"));
    }

    @Test
    public void testCompareNumericVersions_5() {
        assertEquals(-1, VersionUtil.compareNumericVersions("1", "1.2.1"));
    }

    @Test
    public void testCompareNumericVersions_6() {
        assertEquals(-1, VersionUtil.compareNumericVersions("1.2.0", "1.2.1"));
    }

    @Test
    public void testCompareRawVersions_1() {
        assertEquals(0, VersionUtil.compareRawVersions("18.01.0-ce", "v18.1.0"));
    }

    @Test
    public void testCompareRawVersions_2() {
        assertEquals(0, VersionUtil.compareRawVersions("v1.2.3", "1.2.3-abcde1234"));
    }

    @Test
    public void testCompareRawVersions_3() {
        assertEquals(1, VersionUtil.compareRawVersions("ver1.2.3", "1.2"));
    }

    @Test
    public void testCompareRawVersions_4() {
        assertEquals(1, VersionUtil.compareRawVersions("version1.2.3", "v1.2.1"));
    }

    @Test
    public void testCompareRawVersions_5() {
        assertEquals(-1, VersionUtil.compareRawVersions("rel1", "1.2.1-qe"));
    }

    @Test
    public void testCompareRawVersions_6() {
        assertEquals(-1, VersionUtil.compareRawVersions("sth1.2.0", "1.2.1sth"));
    }
}
