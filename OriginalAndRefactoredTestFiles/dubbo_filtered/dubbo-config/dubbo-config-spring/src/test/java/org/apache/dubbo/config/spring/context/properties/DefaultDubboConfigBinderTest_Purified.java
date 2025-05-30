package org.apache.dubbo.config.spring.context.properties;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:/dubbo-binder.properties")
@ContextConfiguration(classes = DefaultDubboConfigBinder.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class DefaultDubboConfigBinderTest_Purified {

    @BeforeAll
    public static void setUp() {
        DubboBootstrap.reset();
    }

    @Autowired
    private DubboConfigBinder dubboConfigBinder;

    @Test
    void testBinder_1_testMerged_1() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        dubboConfigBinder.bind("dubbo.application", applicationConfig);
        Assertions.assertEquals("hello", applicationConfig.getName());
        Assertions.assertEquals("world", applicationConfig.getOwner());
    }

    @Test
    void testBinder_3() {
        RegistryConfig registryConfig = new RegistryConfig();
        dubboConfigBinder.bind("dubbo.registry", registryConfig);
        Assertions.assertEquals("10.20.153.17", registryConfig.getAddress());
    }

    @Test
    void testBinder_4() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        dubboConfigBinder.bind("dubbo.protocol", protocolConfig);
        Assertions.assertEquals(Integer.valueOf(20881), protocolConfig.getPort());
    }
}
