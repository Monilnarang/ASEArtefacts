package org.apache.dubbo.spring.boot.autoconfigure.base;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ModuleConfig;
import org.apache.dubbo.config.MonitorConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = { "dubbo.application.name = dubbo-demo-single-application", "dubbo.module.name = dubbo-demo-module", "dubbo.registry.address = test://192.168.99.100:32770", "dubbo.protocol.name=dubbo", "dubbo.protocol.port=20880", "dubbo.monitor.address=test://127.0.0.1:32770", "dubbo.provider.host=127.0.0.1", "dubbo.consumer.client=netty" })
@SpringBootTest(classes = { DubboAutoConfigurationOnSingleConfigTest.class })
@EnableAutoConfiguration
@ComponentScan
class DubboAutoConfigurationOnSingleConfigTest_Purified {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private ModuleConfig moduleConfig;

    @Autowired
    private RegistryConfig registryConfig;

    @Autowired
    private MonitorConfig monitorConfig;

    @Autowired
    private ProviderConfig providerConfig;

    @Autowired
    private ConsumerConfig consumerConfig;

    @Autowired
    private ProtocolConfig protocolConfig;

    @BeforeEach
    void init() {
        DubboBootstrap.reset();
    }

    @AfterEach
    void destroy() {
        DubboBootstrap.reset();
    }

    @Test
    void testSingleConfig_1() {
        assertEquals("dubbo-demo-single-application", applicationConfig.getName());
    }

    @Test
    void testSingleConfig_2() {
        assertEquals("dubbo-demo-module", moduleConfig.getName());
    }

    @Test
    void testSingleConfig_3() {
        assertEquals("test://192.168.99.100:32770", registryConfig.getAddress());
    }

    @Test
    void testSingleConfig_4() {
        assertEquals("test://127.0.0.1:32770", monitorConfig.getAddress());
    }

    @Test
    void testSingleConfig_5() {
        assertEquals("dubbo", protocolConfig.getName());
    }

    @Test
    void testSingleConfig_6() {
        assertEquals(Integer.valueOf(20880), protocolConfig.getPort());
    }

    @Test
    void testSingleConfig_7() {
        assertEquals("netty", consumerConfig.getClient());
    }

    @Test
    void testSingleConfig_8() {
        assertEquals("127.0.0.1", providerConfig.getHost());
    }
}
