package org.apache.druid.segment.column;

import org.junit.Assert;
import org.junit.Test;

public class ColumnCapabilitiesTest_Purified {

    @Test
    public void testCapableAnd_1() {
        Assert.assertTrue(ColumnCapabilities.Capable.TRUE.and(ColumnCapabilities.Capable.TRUE).isTrue());
    }

    @Test
    public void testCapableAnd_2() {
        Assert.assertFalse(ColumnCapabilities.Capable.TRUE.and(ColumnCapabilities.Capable.FALSE).isTrue());
    }

    @Test
    public void testCapableAnd_3() {
        Assert.assertFalse(ColumnCapabilities.Capable.TRUE.and(ColumnCapabilities.Capable.UNKNOWN).isTrue());
    }

    @Test
    public void testCapableAnd_4() {
        Assert.assertFalse(ColumnCapabilities.Capable.FALSE.and(ColumnCapabilities.Capable.TRUE).isTrue());
    }

    @Test
    public void testCapableAnd_5() {
        Assert.assertFalse(ColumnCapabilities.Capable.FALSE.and(ColumnCapabilities.Capable.FALSE).isTrue());
    }

    @Test
    public void testCapableAnd_6() {
        Assert.assertFalse(ColumnCapabilities.Capable.FALSE.and(ColumnCapabilities.Capable.UNKNOWN).isTrue());
    }

    @Test
    public void testCapableAnd_7() {
        Assert.assertFalse(ColumnCapabilities.Capable.UNKNOWN.and(ColumnCapabilities.Capable.TRUE).isTrue());
    }

    @Test
    public void testCapableAnd_8() {
        Assert.assertFalse(ColumnCapabilities.Capable.UNKNOWN.and(ColumnCapabilities.Capable.FALSE).isTrue());
    }

    @Test
    public void testCapableAnd_9() {
        Assert.assertFalse(ColumnCapabilities.Capable.UNKNOWN.and(ColumnCapabilities.Capable.UNKNOWN).isTrue());
    }

    @Test
    public void testCapableOfBoolean_1() {
        Assert.assertEquals(ColumnCapabilities.Capable.TRUE, ColumnCapabilities.Capable.of(true));
    }

    @Test
    public void testCapableOfBoolean_2() {
        Assert.assertEquals(ColumnCapabilities.Capable.FALSE, ColumnCapabilities.Capable.of(false));
    }
}
