package org.apache.rocketmq.proxy.config;

import org.junit.Assert;
import org.junit.Test;

public class MetricCollectorModeTest_Purified {

    @Test
    public void testGetEnumByOrdinal_1() {
        Assert.assertEquals(MetricCollectorMode.OFF, MetricCollectorMode.getEnumByString("off"));
    }

    @Test
    public void testGetEnumByOrdinal_2() {
        Assert.assertEquals(MetricCollectorMode.ON, MetricCollectorMode.getEnumByString("on"));
    }

    @Test
    public void testGetEnumByOrdinal_3() {
        Assert.assertEquals(MetricCollectorMode.PROXY, MetricCollectorMode.getEnumByString("proxy"));
    }

    @Test
    public void testGetEnumByOrdinal_4() {
        Assert.assertEquals(MetricCollectorMode.OFF, MetricCollectorMode.getEnumByString("OFF"));
    }

    @Test
    public void testGetEnumByOrdinal_5() {
        Assert.assertEquals(MetricCollectorMode.ON, MetricCollectorMode.getEnumByString("ON"));
    }

    @Test
    public void testGetEnumByOrdinal_6() {
        Assert.assertEquals(MetricCollectorMode.PROXY, MetricCollectorMode.getEnumByString("PROXY"));
    }
}
