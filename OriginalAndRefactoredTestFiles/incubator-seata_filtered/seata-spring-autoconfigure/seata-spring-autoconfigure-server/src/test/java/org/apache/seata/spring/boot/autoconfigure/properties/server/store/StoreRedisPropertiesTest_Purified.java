package org.apache.seata.spring.boot.autoconfigure.properties.server.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StoreRedisPropertiesTest_Purified {

    @Test
    public void testStoreRedisProperties_1_testMerged_1() {
        StoreRedisProperties storeRedisProperties = new StoreRedisProperties();
        storeRedisProperties.setMode("mode");
        storeRedisProperties.setType("type");
        storeRedisProperties.setPassword("pwd");
        storeRedisProperties.setDatabase(1);
        storeRedisProperties.setMaxConn(1);
        storeRedisProperties.setMinConn(1);
        storeRedisProperties.setQueryLimit(1);
        storeRedisProperties.setMaxTotal(1);
        Assertions.assertEquals("mode", storeRedisProperties.getMode());
        Assertions.assertEquals("type", storeRedisProperties.getType());
        Assertions.assertEquals("pwd", storeRedisProperties.getPassword());
        Assertions.assertEquals(1, storeRedisProperties.getDatabase());
        Assertions.assertEquals(1, storeRedisProperties.getMaxConn());
        Assertions.assertEquals(1, storeRedisProperties.getMinConn());
        Assertions.assertEquals(1, storeRedisProperties.getQueryLimit());
        Assertions.assertEquals(1, storeRedisProperties.getMaxTotal());
    }

    @Test
    public void testStoreRedisProperties_9_testMerged_2() {
        StoreRedisProperties.Single single = new StoreRedisProperties.Single();
        single.setHost("host");
        single.setPort(80);
        Assertions.assertEquals("host", single.getHost());
        Assertions.assertEquals(80, single.getPort());
    }

    @Test
    public void testStoreRedisProperties_11_testMerged_3() {
        StoreRedisProperties.Sentinel sentinel = new StoreRedisProperties.Sentinel();
        sentinel.setSentinelHosts("host");
        sentinel.setMasterName("master");
        sentinel.setSentinelPassword("pwd");
        Assertions.assertEquals("host", sentinel.getSentinelHosts());
        Assertions.assertEquals("master", sentinel.getMasterName());
        Assertions.assertEquals("pwd", sentinel.getSentinelPassword());
    }
}
