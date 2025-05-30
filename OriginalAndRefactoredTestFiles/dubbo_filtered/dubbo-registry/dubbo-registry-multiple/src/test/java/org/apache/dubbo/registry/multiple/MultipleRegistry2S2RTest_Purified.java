package org.apache.dubbo.registry.multiple;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.NotifyListener;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.zookeeper.ZookeeperRegistry;
import org.apache.dubbo.remoting.zookeeper.curator5.Curator5ZookeeperClient;
import org.apache.dubbo.remoting.zookeeper.curator5.ZookeeperClient;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MultipleRegistry2S2RTest_Purified {

    private static final String SERVICE_NAME = "org.apache.dubbo.registry.MultipleService2S2R";

    private static final String SERVICE2_NAME = "org.apache.dubbo.registry.MultipleService2S2R2";

    private static MultipleRegistry multipleRegistry;

    private static ZookeeperClient zookeeperClient;

    private static ZookeeperClient zookeeperClient2;

    private static ZookeeperRegistry zookeeperRegistry;

    private static ZookeeperRegistry zookeeperRegistry2;

    private static String zookeeperConnectionAddress1, zookeeperConnectionAddress2;

    @BeforeAll
    public static void beforeAll() {
        zookeeperConnectionAddress1 = System.getProperty("zookeeper.connection.address.1");
        zookeeperConnectionAddress2 = System.getProperty("zookeeper.connection.address.2");
        URL url = URL.valueOf("multiple://127.0.0.1?application=vic&enable-empty-protection=false&" + MultipleRegistry.REGISTRY_FOR_SERVICE + "=" + zookeeperConnectionAddress1 + "," + zookeeperConnectionAddress2 + "&" + MultipleRegistry.REGISTRY_FOR_REFERENCE + "=" + zookeeperConnectionAddress1 + "," + zookeeperConnectionAddress2);
        multipleRegistry = (MultipleRegistry) new MultipleRegistryFactory().createRegistry(url);
        zookeeperClient = new Curator5ZookeeperClient(URL.valueOf(zookeeperConnectionAddress1));
        zookeeperRegistry = MultipleRegistryTestUtil.getZookeeperRegistry(multipleRegistry.getServiceRegistries().values());
        zookeeperClient2 = new Curator5ZookeeperClient(URL.valueOf(zookeeperConnectionAddress2));
        zookeeperRegistry2 = MultipleRegistryTestUtil.getZookeeperRegistry(multipleRegistry.getServiceRegistries().values());
    }

    @Test
    void testParamConfig_1() {
        Assertions.assertEquals(2, multipleRegistry.origReferenceRegistryURLs.size());
    }

    @Test
    void testParamConfig_2() {
        Assertions.assertTrue(multipleRegistry.origReferenceRegistryURLs.contains(zookeeperConnectionAddress1));
    }

    @Test
    void testParamConfig_3() {
        Assertions.assertTrue(multipleRegistry.origReferenceRegistryURLs.contains(zookeeperConnectionAddress2));
    }

    @Test
    void testParamConfig_4() {
        Assertions.assertEquals(2, multipleRegistry.origServiceRegistryURLs.size());
    }

    @Test
    void testParamConfig_5() {
        Assertions.assertTrue(multipleRegistry.origServiceRegistryURLs.contains(zookeeperConnectionAddress1));
    }

    @Test
    void testParamConfig_6() {
        Assertions.assertTrue(multipleRegistry.origServiceRegistryURLs.contains(zookeeperConnectionAddress2));
    }

    @Test
    void testParamConfig_7() {
        Assertions.assertEquals(2, multipleRegistry.effectReferenceRegistryURLs.size());
    }

    @Test
    void testParamConfig_8() {
        Assertions.assertTrue(multipleRegistry.effectReferenceRegistryURLs.contains(zookeeperConnectionAddress1));
    }

    @Test
    void testParamConfig_9() {
        Assertions.assertTrue(multipleRegistry.effectReferenceRegistryURLs.contains(zookeeperConnectionAddress2));
    }

    @Test
    void testParamConfig_10() {
        Assertions.assertEquals(2, multipleRegistry.effectServiceRegistryURLs.size());
    }

    @Test
    void testParamConfig_11() {
        Assertions.assertTrue(multipleRegistry.effectServiceRegistryURLs.contains(zookeeperConnectionAddress1));
    }

    @Test
    void testParamConfig_12() {
        Assertions.assertTrue(multipleRegistry.effectServiceRegistryURLs.contains(zookeeperConnectionAddress2));
    }

    @Test
    void testParamConfig_13() {
        Assertions.assertTrue(multipleRegistry.getServiceRegistries().containsKey(zookeeperConnectionAddress1));
    }

    @Test
    void testParamConfig_14() {
        Assertions.assertTrue(multipleRegistry.getServiceRegistries().containsKey(zookeeperConnectionAddress2));
    }

    @Test
    void testParamConfig_15() {
        Assertions.assertEquals(2, multipleRegistry.getServiceRegistries().values().size());
    }

    @Test
    void testParamConfig_16() {
        Assertions.assertNotNull(MultipleRegistryTestUtil.getZookeeperRegistry(multipleRegistry.getServiceRegistries().values()));
    }

    @Test
    void testParamConfig_17() {
        Assertions.assertNotNull(MultipleRegistryTestUtil.getZookeeperRegistry(multipleRegistry.getReferenceRegistries().values()));
    }

    @Test
    void testParamConfig_18_testMerged_18() {
        Assertions.assertEquals(MultipleRegistryTestUtil.getZookeeperRegistry(multipleRegistry.getServiceRegistries().values()), MultipleRegistryTestUtil.getZookeeperRegistry(multipleRegistry.getReferenceRegistries().values()));
    }

    @Test
    void testParamConfig_20() {
        Assertions.assertEquals(multipleRegistry.getApplicationName(), "vic");
    }

    @Test
    void testParamConfig_21() {
        Assertions.assertTrue(multipleRegistry.isAvailable());
    }
}
