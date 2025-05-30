package org.apache.druid.common.config;

import org.junit.Assert;
import org.junit.Test;

public class ConfigsTest_Purified {

    @Test
    public void testValueOrDefault_1() {
        Assert.assertEquals(10, Configs.valueOrDefault((Integer) 10, 11));
    }

    @Test
    public void testValueOrDefault_2() {
        Assert.assertEquals(11, Configs.valueOrDefault((Integer) null, 11));
    }

    @Test
    public void testValueOrDefault_3() {
        Assert.assertEquals(10, Configs.valueOrDefault((Long) 10L, 11L));
    }

    @Test
    public void testValueOrDefault_4() {
        Assert.assertEquals(11, Configs.valueOrDefault(null, 11L));
    }

    @Test
    public void testValueOrDefault_5() {
        Assert.assertFalse(Configs.valueOrDefault((Boolean) false, true));
    }

    @Test
    public void testValueOrDefault_6() {
        Assert.assertTrue(Configs.valueOrDefault(null, true));
    }

    @Test
    public void testValueOrDefault_7() {
        Assert.assertEquals("abc", Configs.valueOrDefault("abc", "def"));
    }

    @Test
    public void testValueOrDefault_8() {
        Assert.assertEquals("def", Configs.valueOrDefault(null, "def"));
    }
}
