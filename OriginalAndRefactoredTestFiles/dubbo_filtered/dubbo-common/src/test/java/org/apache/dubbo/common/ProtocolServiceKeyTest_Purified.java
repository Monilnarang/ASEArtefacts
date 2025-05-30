package org.apache.dubbo.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProtocolServiceKeyTest_Purified {

    @Test
    void test_1_testMerged_1() {
        ProtocolServiceKey protocolServiceKey = new ProtocolServiceKey("DemoService", "1.0.0", "group1", "protocol1");
        Assertions.assertEquals("DemoService", protocolServiceKey.getInterfaceName());
        Assertions.assertEquals("1.0.0", protocolServiceKey.getVersion());
        Assertions.assertEquals("group1", protocolServiceKey.getGroup());
        Assertions.assertEquals("protocol1", protocolServiceKey.getProtocol());
        Assertions.assertEquals("group1/DemoService:1.0.0:protocol1", protocolServiceKey.toString());
        Assertions.assertEquals("group1/DemoService:1.0.0", protocolServiceKey.getServiceKeyString());
        Assertions.assertEquals(protocolServiceKey, protocolServiceKey);
        ProtocolServiceKey protocolServiceKey1 = new ProtocolServiceKey("DemoService", "1.0.0", "group1", "protocol1");
        Assertions.assertEquals(protocolServiceKey, protocolServiceKey1);
        Assertions.assertEquals(protocolServiceKey.hashCode(), protocolServiceKey1.hashCode());
        ProtocolServiceKey protocolServiceKey2 = new ProtocolServiceKey("DemoService", "1.0.0", "group1", "protocol2");
        Assertions.assertNotEquals(protocolServiceKey, protocolServiceKey2);
        ProtocolServiceKey protocolServiceKey3 = new ProtocolServiceKey("DemoService", "1.0.0", "group2", "protocol1");
        Assertions.assertNotEquals(protocolServiceKey, protocolServiceKey3);
        ProtocolServiceKey protocolServiceKey4 = new ProtocolServiceKey("DemoService", "1.0.1", "group1", "protocol1");
        Assertions.assertNotEquals(protocolServiceKey, protocolServiceKey4);
        ProtocolServiceKey protocolServiceKey5 = new ProtocolServiceKey("DemoInterface", "1.0.0", "group1", "protocol1");
        Assertions.assertNotEquals(protocolServiceKey, protocolServiceKey5);
        ServiceKey serviceKey = new ServiceKey("DemoService", "1.0.0", "group1");
        Assertions.assertNotEquals(protocolServiceKey, serviceKey);
        Assertions.assertTrue(protocolServiceKey.isSameWith(protocolServiceKey));
        Assertions.assertTrue(protocolServiceKey.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group1", "")));
        Assertions.assertTrue(protocolServiceKey.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group1", null)));
        Assertions.assertFalse(protocolServiceKey.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group2", "protocol1")));
        Assertions.assertFalse(protocolServiceKey.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group2", "")));
        Assertions.assertFalse(protocolServiceKey.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group2", null)));
    }

    @Test
    void test_21_testMerged_2() {
        ProtocolServiceKey protocolServiceKey6 = new ProtocolServiceKey("DemoService", "1.0.0", "group1", null);
        Assertions.assertTrue(protocolServiceKey6.isSameWith(protocolServiceKey6));
        Assertions.assertTrue(protocolServiceKey6.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group1", "")));
        Assertions.assertTrue(protocolServiceKey6.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group1", "protocol1")));
        Assertions.assertTrue(protocolServiceKey6.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group1", "protocol2")));
    }

    @Test
    void test_25_testMerged_3() {
        ProtocolServiceKey protocolServiceKey7 = new ProtocolServiceKey("DemoService", "1.0.0", "group1", "*");
        Assertions.assertFalse(protocolServiceKey7.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group1", null)));
        Assertions.assertFalse(protocolServiceKey7.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group1", "")));
        Assertions.assertFalse(protocolServiceKey7.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group1", "protocol1")));
        Assertions.assertFalse(protocolServiceKey7.isSameWith(new ProtocolServiceKey("DemoService", "1.0.0", "group1", "protocol2")));
    }
}
