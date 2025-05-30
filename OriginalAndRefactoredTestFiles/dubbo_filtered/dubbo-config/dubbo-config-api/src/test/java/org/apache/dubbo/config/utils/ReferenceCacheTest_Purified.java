package org.apache.dubbo.config.utils;

import org.apache.dubbo.common.config.ReferenceCache;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.service.FooService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReferenceCacheTest_Purified {

    @BeforeEach
    public void setUp() throws Exception {
        DubboBootstrap.reset();
        MockReferenceConfig.setCounter(0);
        XxxMockReferenceConfig.setCounter(0);
        SimpleReferenceCache.CACHE_HOLDER.clear();
    }

    private MockReferenceConfig buildMockReferenceConfig(String service, String group, String version) {
        MockReferenceConfig config = new MockReferenceConfig();
        config.setApplication(new ApplicationConfig("cache"));
        config.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
        config.setCheck(false);
        config.setInterface(service);
        config.setGroup(group);
        config.setVersion(version);
        return config;
    }

    private XxxMockReferenceConfig buildXxxMockReferenceConfig(String service, String group, String version) {
        XxxMockReferenceConfig config = new XxxMockReferenceConfig();
        config.setApplication(new ApplicationConfig("cache"));
        config.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
        config.setInterface(service);
        config.setCheck(false);
        config.setGroup(group);
        config.setVersion(version);
        return config;
    }

    @Test
    void testGetCacheDiffReference_1_testMerged_1() throws Exception {
        MockReferenceConfig config = buildMockReferenceConfig("org.apache.dubbo.config.utils.service.FooService", "group1", "1.0.0");
        assertEquals(0L, config.getCounter());
        cache.get(config);
        assertEquals(1L, config.getCounter());
        assertTrue(config.isGetMethodRun());
    }

    @Test
    void testGetCacheDiffReference_5_testMerged_2() throws Exception {
        XxxMockReferenceConfig configCopy = buildXxxMockReferenceConfig("org.apache.dubbo.config.utils.service.XxxService", "group1", "1.0.0");
        assertEquals(0L, configCopy.getCounter());
        cache.get(configCopy);
        assertTrue(configCopy.isGetMethodRun());
        assertEquals(1L, configCopy.getCounter());
    }
}
