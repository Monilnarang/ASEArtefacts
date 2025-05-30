package org.apache.seata.spring.boot.autoconfigure;

import org.apache.seata.spring.boot.autoconfigure.properties.LogProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.ShutdownProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.ThreadFactoryProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.TransportProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.registry.RegistryConsulProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.registry.RegistryCustomProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.registry.RegistryEtcd3Properties;
import org.apache.seata.spring.boot.autoconfigure.properties.registry.RegistryEurekaProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.registry.RegistryNacosProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.registry.RegistryProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.registry.RegistryRedisProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.registry.RegistrySofaProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.registry.RegistryZooKeeperProperties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CorePropertiesTest_Purified {

    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void initContext() {
        context = new AnnotationConfigApplicationContext("org.apache.seata.spring.boot.autoconfigure.properties");
    }

    @AfterAll
    public static void closeContext() {
        context.close();
    }

    @Test
    public void testThreadFactoryProperties_1() {
        assertEquals("NettyBoss", context.getBean(ThreadFactoryProperties.class).getBossThreadPrefix());
    }

    @Test
    public void testThreadFactoryProperties_2() {
        assertEquals("NettyServerNIOWorker", context.getBean(ThreadFactoryProperties.class).getWorkerThreadPrefix());
    }

    @Test
    public void testThreadFactoryProperties_3() {
        assertEquals("NettyServerBizHandler", context.getBean(ThreadFactoryProperties.class).getServerExecutorThreadPrefix());
    }

    @Test
    public void testThreadFactoryProperties_4() {
        assertFalse(context.getBean(ThreadFactoryProperties.class).isShareBossWorker());
    }

    @Test
    public void testThreadFactoryProperties_5() {
        assertEquals("NettyClientSelector", context.getBean(ThreadFactoryProperties.class).getClientSelectorThreadPrefix());
    }

    @Test
    public void testThreadFactoryProperties_6() {
        assertEquals(-1, context.getBean(ThreadFactoryProperties.class).getClientSelectorThreadSize());
    }

    @Test
    public void testThreadFactoryProperties_7() {
        assertEquals("NettyClientWorkerThread", context.getBean(ThreadFactoryProperties.class).getClientWorkerThreadPrefix());
    }

    @Test
    public void testThreadFactoryProperties_8() {
        assertEquals(1, context.getBean(ThreadFactoryProperties.class).getBossThreadSize());
    }

    @Test
    public void testThreadFactoryProperties_9() {
        assertEquals("Default", context.getBean(ThreadFactoryProperties.class).getWorkerThreadSize());
    }

    @Test
    public void testTransportProperties_1() {
        assertEquals("TCP", context.getBean(TransportProperties.class).getType());
    }

    @Test
    public void testTransportProperties_2() {
        assertEquals("NIO", context.getBean(TransportProperties.class).getServer());
    }

    @Test
    public void testTransportProperties_3() {
        assertTrue(context.getBean(TransportProperties.class).isHeartbeat());
    }

    @Test
    public void testTransportProperties_4() {
        assertEquals("seata", context.getBean(TransportProperties.class).getSerialization());
    }

    @Test
    public void testTransportProperties_5() {
        assertEquals("none", context.getBean(TransportProperties.class).getCompressor());
    }

    @Test
    public void testTransportProperties_6() {
        assertTrue(context.getBean(TransportProperties.class).isEnableClientBatchSendRequest());
    }

    @Test
    public void testTransportProperties_7() {
        assertFalse(context.getBean(TransportProperties.class).isEnableClientSharedEventLoop());
    }

    @Test
    public void testRegistryConsulProperties_1() {
        assertEquals("default", context.getBean(RegistryConsulProperties.class).getCluster());
    }

    @Test
    public void testRegistryConsulProperties_2() {
        assertEquals("127.0.0.1:8500", context.getBean(RegistryConsulProperties.class).getServerAddr());
    }

    @Test
    public void testRegistryEtcd3Properties_1() {
        assertEquals("default", context.getBean(RegistryEtcd3Properties.class).getCluster());
    }

    @Test
    public void testRegistryEtcd3Properties_2() {
        assertEquals("http://localhost:2379", context.getBean(RegistryEtcd3Properties.class).getServerAddr());
    }

    @Test
    public void testRegistryEurekaProperties_1() {
        assertEquals("default", context.getBean(RegistryEurekaProperties.class).getApplication());
    }

    @Test
    public void testRegistryEurekaProperties_2() {
        assertEquals("http://localhost:8761/eureka", context.getBean(RegistryEurekaProperties.class).getServiceUrl());
    }

    @Test
    public void testRegistryEurekaProperties_3() {
        assertEquals("1", context.getBean(RegistryEurekaProperties.class).getWeight());
    }

    @Test
    public void testRegistryNacosProperties_1() {
        assertEquals("localhost:8848", context.getBean(RegistryNacosProperties.class).getServerAddr());
    }

    @Test
    public void testRegistryNacosProperties_2() {
        assertNull(context.getBean(RegistryNacosProperties.class).getNamespace());
    }

    @Test
    public void testRegistryNacosProperties_3() {
        assertEquals("SEATA_GROUP", context.getBean(RegistryNacosProperties.class).getGroup());
    }

    @Test
    public void testRegistryNacosProperties_4() {
        assertEquals("default", context.getBean(RegistryNacosProperties.class).getCluster());
    }

    @Test
    public void testRegistryNacosProperties_5() {
        assertNull(context.getBean(RegistryNacosProperties.class).getUsername());
    }

    @Test
    public void testRegistryNacosProperties_6() {
        assertNull(context.getBean(RegistryNacosProperties.class).getPassword());
    }

    @Test
    public void testRegistryNacosProperties_7() {
        assertEquals("seata-server", context.getBean(RegistryNacosProperties.class).getApplication());
    }

    @Test
    public void testRegistryRedisProperties_1() {
        assertEquals("localhost:6379", context.getBean(RegistryRedisProperties.class).getServerAddr());
    }

    @Test
    public void testRegistryRedisProperties_2() {
        assertEquals(0, context.getBean(RegistryRedisProperties.class).getDb());
    }

    @Test
    public void testRegistryRedisProperties_3() {
        assertNull(context.getBean(RegistryRedisProperties.class).getPassword());
    }

    @Test
    public void testRegistryRedisProperties_4() {
        assertEquals("default", context.getBean(RegistryRedisProperties.class).getCluster());
    }

    @Test
    public void testRegistryRedisProperties_5() {
        assertEquals(0, context.getBean(RegistryRedisProperties.class).getTimeout());
    }

    @Test
    public void testRegistrySofaProperties_1() {
        assertEquals("127.0.0.1:9603", context.getBean(RegistrySofaProperties.class).getServerAddr());
    }

    @Test
    public void testRegistrySofaProperties_2() {
        assertEquals("default", context.getBean(RegistrySofaProperties.class).getApplication());
    }

    @Test
    public void testRegistrySofaProperties_3() {
        assertEquals("DEFAULT_ZONE", context.getBean(RegistrySofaProperties.class).getRegion());
    }

    @Test
    public void testRegistrySofaProperties_4() {
        assertEquals("DefaultDataCenter", context.getBean(RegistrySofaProperties.class).getDatacenter());
    }

    @Test
    public void testRegistrySofaProperties_5() {
        assertEquals("default", context.getBean(RegistrySofaProperties.class).getCluster());
    }

    @Test
    public void testRegistrySofaProperties_6() {
        assertEquals("SEATA_GROUP", context.getBean(RegistrySofaProperties.class).getGroup());
    }

    @Test
    public void testRegistrySofaProperties_7() {
        assertEquals("3000", context.getBean(RegistrySofaProperties.class).getAddressWaitTime());
    }

    @Test
    public void testRegistryZooKeeperProperties_1() {
        assertEquals("default", context.getBean(RegistryZooKeeperProperties.class).getCluster());
    }

    @Test
    public void testRegistryZooKeeperProperties_2() {
        assertEquals("127.0.0.1:2181", context.getBean(RegistryZooKeeperProperties.class).getServerAddr());
    }

    @Test
    public void testRegistryZooKeeperProperties_3() {
        assertEquals(6000L, context.getBean(RegistryZooKeeperProperties.class).getSessionTimeout());
    }

    @Test
    public void testRegistryZooKeeperProperties_4() {
        assertEquals(2000L, context.getBean(RegistryZooKeeperProperties.class).getConnectTimeout());
    }
}
