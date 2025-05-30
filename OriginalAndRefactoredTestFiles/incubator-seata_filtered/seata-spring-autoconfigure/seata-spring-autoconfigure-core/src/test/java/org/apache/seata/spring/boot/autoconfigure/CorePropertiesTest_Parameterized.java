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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CorePropertiesTest_Parameterized {

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
    public void testRegistryEurekaProperties_2() {
        assertEquals("http://localhost:8761/eureka", context.getBean(RegistryEurekaProperties.class).getServiceUrl());
    }

    @Test
    public void testRegistryEurekaProperties_3() {
        assertEquals("1", context.getBean(RegistryEurekaProperties.class).getWeight());
    }

    @Test
    public void testRegistryNacosProperties_2() {
        assertNull(context.getBean(RegistryNacosProperties.class).getNamespace());
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
    public void testRegistryRedisProperties_2() {
        assertEquals(0, context.getBean(RegistryRedisProperties.class).getDb());
    }

    @Test
    public void testRegistryRedisProperties_3() {
        assertNull(context.getBean(RegistryRedisProperties.class).getPassword());
    }

    @Test
    public void testRegistryRedisProperties_5() {
        assertEquals(0, context.getBean(RegistryRedisProperties.class).getTimeout());
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
    public void testRegistrySofaProperties_7() {
        assertEquals("3000", context.getBean(RegistrySofaProperties.class).getAddressWaitTime());
    }

    @Test
    public void testRegistryZooKeeperProperties_3() {
        assertEquals(6000L, context.getBean(RegistryZooKeeperProperties.class).getSessionTimeout());
    }

    @Test
    public void testRegistryZooKeeperProperties_4() {
        assertEquals(2000L, context.getBean(RegistryZooKeeperProperties.class).getConnectTimeout());
    }

    @ParameterizedTest
    @MethodSource("Provider_testRegistryConsulProperties_1_1_1_4_4to5")
    public void testRegistryConsulProperties_1_1_1_4_4to5(String param1) {
        assertEquals(param1, context.getBean(RegistryConsulProperties.class).getCluster());
    }

    static public Stream<Arguments> Provider_testRegistryConsulProperties_1_1_1_4_4to5() {
        return Stream.of(arguments("default"), arguments("default"), arguments("default"), arguments("default"), arguments("default"), arguments("default"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRegistryConsulProperties_1_1_1to2_2_2")
    public void testRegistryConsulProperties_1_1_1to2_2_2(String param1) {
        assertEquals(param1, context.getBean(RegistryConsulProperties.class).getServerAddr());
    }

    static public Stream<Arguments> Provider_testRegistryConsulProperties_1_1_1to2_2_2() {
        return Stream.of(arguments("127.0.0.1:8500"), arguments("http://localhost:2379"), arguments("localhost:8848"), arguments("localhost:6379"), arguments("127.0.0.1:9603"), arguments("127.0.0.1:2181"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRegistryEurekaProperties_1to2_7")
    public void testRegistryEurekaProperties_1to2_7(String param1) {
        assertEquals(param1, context.getBean(RegistryEurekaProperties.class).getApplication());
    }

    static public Stream<Arguments> Provider_testRegistryEurekaProperties_1to2_7() {
        return Stream.of(arguments("default"), arguments("seata-server"), arguments("default"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRegistryNacosProperties_3_6")
    public void testRegistryNacosProperties_3_6(String param1) {
        assertEquals(param1, context.getBean(RegistryNacosProperties.class).getGroup());
    }

    static public Stream<Arguments> Provider_testRegistryNacosProperties_3_6() {
        return Stream.of(arguments("SEATA_GROUP"), arguments("SEATA_GROUP"));
    }
}
