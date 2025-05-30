package org.apache.dubbo.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ServiceKeyTest_Purified {

    @Test
    void test_1_testMerged_1() {
        ServiceKey serviceKey = new ServiceKey("DemoService", "1.0.0", "group1");
        Assertions.assertEquals("DemoService", serviceKey.getInterfaceName());
        Assertions.assertEquals("1.0.0", serviceKey.getVersion());
        Assertions.assertEquals("group1", serviceKey.getGroup());
        Assertions.assertEquals("group1/DemoService:1.0.0", serviceKey.toString());
        Assertions.assertEquals(serviceKey, serviceKey);
        ServiceKey serviceKey1 = new ServiceKey("DemoService", "1.0.0", "group1");
        Assertions.assertEquals(serviceKey, serviceKey1);
        Assertions.assertEquals(serviceKey.hashCode(), serviceKey1.hashCode());
        ServiceKey serviceKey2 = new ServiceKey("DemoService", "1.0.0", "group2");
        Assertions.assertNotEquals(serviceKey, serviceKey2);
        ServiceKey serviceKey3 = new ServiceKey("DemoService", "1.0.1", "group1");
        Assertions.assertNotEquals(serviceKey, serviceKey3);
        ServiceKey serviceKey4 = new ServiceKey("DemoInterface", "1.0.0", "group1");
        Assertions.assertNotEquals(serviceKey, serviceKey4);
        ProtocolServiceKey protocolServiceKey = new ProtocolServiceKey("DemoService", "1.0.0", "group1", "protocol1");
        Assertions.assertNotEquals(serviceKey, protocolServiceKey);
    }

    @Test
    void test_5() {
        Assertions.assertEquals("DemoService", new ServiceKey("DemoService", null, null).toString());
    }

    @Test
    void test_6() {
        Assertions.assertEquals("DemoService:1.0.0", new ServiceKey("DemoService", "1.0.0", null).toString());
    }

    @Test
    void test_7() {
        Assertions.assertEquals("group1/DemoService", new ServiceKey("DemoService", null, "group1").toString());
    }
}
