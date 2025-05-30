package org.apache.dubbo.spring.boot.autoconfigure;

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationPostProcessor;
import org.apache.dubbo.config.spring.util.DubboBeanUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompatibleDubboAutoConfigurationTestWithoutProperties.class, properties = { "dubbo.application.name=demo" })
@EnableAutoConfiguration
class CompatibleDubboAutoConfigurationTestWithoutProperties_Purified {

    @Autowired(required = false)
    private ServiceAnnotationPostProcessor serviceAnnotationPostProcessor;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void init() {
        DubboBootstrap.reset();
    }

    @AfterEach
    void destroy() {
        DubboBootstrap.reset();
    }

    @Test
    void testBeans_1() {
        assertNull(serviceAnnotationPostProcessor);
    }

    @Test
    void testBeans_2() {
        ReferenceAnnotationBeanPostProcessor referenceAnnotationBeanPostProcessor = DubboBeanUtils.getReferenceAnnotationBeanPostProcessor(applicationContext);
        assertNotNull(referenceAnnotationBeanPostProcessor);
    }
}
