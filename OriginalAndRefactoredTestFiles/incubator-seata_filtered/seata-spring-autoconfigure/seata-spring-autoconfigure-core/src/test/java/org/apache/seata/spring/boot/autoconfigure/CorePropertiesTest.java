/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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


public class CorePropertiesTest {
    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void initContext() {
        context = new AnnotationConfigApplicationContext("org.apache.seata.spring.boot.autoconfigure.properties");
    }


    @Test
    public void testThreadFactoryProperties() {
        assertEquals("NettyBoss", context.getBean(ThreadFactoryProperties.class).getBossThreadPrefix());
        assertEquals("NettyServerNIOWorker", context.getBean(ThreadFactoryProperties.class).getWorkerThreadPrefix());
        assertEquals("NettyServerBizHandler", context.getBean(ThreadFactoryProperties.class).getServerExecutorThreadPrefix());
        assertFalse(context.getBean(ThreadFactoryProperties.class).isShareBossWorker());
        assertEquals("NettyClientSelector", context.getBean(ThreadFactoryProperties.class).getClientSelectorThreadPrefix());
        assertEquals(-1, context.getBean(ThreadFactoryProperties.class).getClientSelectorThreadSize());
        assertEquals("NettyClientWorkerThread", context.getBean(ThreadFactoryProperties.class).getClientWorkerThreadPrefix());
        assertEquals(1, context.getBean(ThreadFactoryProperties.class).getBossThreadSize());
        assertEquals("Default", context.getBean(ThreadFactoryProperties.class).getWorkerThreadSize());
    }

    @Test
    public void testTransportProperties() {
        assertEquals("TCP", context.getBean(TransportProperties.class).getType());
        assertEquals("NIO", context.getBean(TransportProperties.class).getServer());
        assertTrue(context.getBean(TransportProperties.class).isHeartbeat());
        assertEquals("seata", context.getBean(TransportProperties.class).getSerialization());
        assertEquals("none", context.getBean(TransportProperties.class).getCompressor());
        assertTrue(context.getBean(TransportProperties.class).isEnableClientBatchSendRequest());
        assertFalse(context.getBean(TransportProperties.class).isEnableClientSharedEventLoop());
    }

    @Test
    public void testShutdownProperties() {
        assertEquals(13L, context.getBean(ShutdownProperties.class).getWait());
    }

    @Test
    public void testLogProperties() {
        assertEquals(100, context.getBean(LogProperties.class).getExceptionRate());
    }


    @Test
    public void testRegistryConsulProperties() {
        assertEquals("default", context.getBean(RegistryConsulProperties.class).getCluster());
        assertEquals("127.0.0.1:8500", context.getBean(RegistryConsulProperties.class).getServerAddr());
    }

    @Test
    public void testRegistryEtcd3Properties() {
        assertEquals("default", context.getBean(RegistryEtcd3Properties.class).getCluster());
        assertEquals("http://localhost:2379", context.getBean(RegistryEtcd3Properties.class).getServerAddr());
    }

    @Test
    public void testRegistryEurekaProperties() {
        assertEquals("default", context.getBean(RegistryEurekaProperties.class).getApplication());
        assertEquals("http://localhost:8761/eureka", context.getBean(RegistryEurekaProperties.class).getServiceUrl());
        assertEquals("1", context.getBean(RegistryEurekaProperties.class).getWeight());
    }

    @Test
    public void testRegistryNacosProperties() {
        assertEquals("localhost:8848", context.getBean(RegistryNacosProperties.class).getServerAddr());
        assertNull(context.getBean(RegistryNacosProperties.class).getNamespace());
        assertEquals("SEATA_GROUP", context.getBean(RegistryNacosProperties.class).getGroup());
        assertEquals("default", context.getBean(RegistryNacosProperties.class).getCluster());
        assertNull(context.getBean(RegistryNacosProperties.class).getUsername());
        assertNull(context.getBean(RegistryNacosProperties.class).getPassword());
        assertEquals("seata-server", context.getBean(RegistryNacosProperties.class).getApplication());
    }

    @Test
    public void testRegistryProperties() {
        assertEquals("file", context.getBean(RegistryProperties.class).getType());
    }


    @Test
    public void testRegistryRedisProperties() {
        assertEquals("localhost:6379", context.getBean(RegistryRedisProperties.class).getServerAddr());
        assertEquals(0, context.getBean(RegistryRedisProperties.class).getDb());
        assertNull(context.getBean(RegistryRedisProperties.class).getPassword());
        assertEquals("default", context.getBean(RegistryRedisProperties.class).getCluster());
        assertEquals(0, context.getBean(RegistryRedisProperties.class).getTimeout());
    }

    @Test
    public void testRegistrySofaProperties() {
        assertEquals("127.0.0.1:9603", context.getBean(RegistrySofaProperties.class).getServerAddr());
        assertEquals("default", context.getBean(RegistrySofaProperties.class).getApplication());
        assertEquals("DEFAULT_ZONE", context.getBean(RegistrySofaProperties.class).getRegion());
        assertEquals("DefaultDataCenter", context.getBean(RegistrySofaProperties.class).getDatacenter());
        assertEquals("default", context.getBean(RegistrySofaProperties.class).getCluster());
        assertEquals("SEATA_GROUP", context.getBean(RegistrySofaProperties.class).getGroup());
        assertEquals("3000", context.getBean(RegistrySofaProperties.class).getAddressWaitTime());
    }

    @Test
    public void testRegistryZooKeeperProperties() {
        assertEquals("default", context.getBean(RegistryZooKeeperProperties.class).getCluster());
        assertEquals("127.0.0.1:2181", context.getBean(RegistryZooKeeperProperties.class).getServerAddr());
        assertEquals(6000L, context.getBean(RegistryZooKeeperProperties.class).getSessionTimeout());
        assertEquals(2000L, context.getBean(RegistryZooKeeperProperties.class).getConnectTimeout());
    }

    @Test
    public void testRegistryCustomProperties() {
        assertNull(context.getBean(RegistryCustomProperties.class).getName());
    }


    @AfterAll
    public static void closeContext() {
        context.close();
    }
}
