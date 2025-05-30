package org.apache.dubbo.spring.boot.autoconfigure;

import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationPostProcessor;
import org.apache.dubbo.config.spring.util.DubboBeanUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { CompatibleDubboAutoConfigurationTest.class }, properties = { "dubbo.scan.base-packages = org.apache.dubbo.spring.boot.autoconfigure" })
@EnableAutoConfiguration
@PropertySource(value = "classpath:/META-INF/dubbo.properties")
class CompatibleDubboAutoConfigurationTest_Purified {

    @Autowired
    private ObjectProvider<ServiceAnnotationPostProcessor> serviceAnnotationPostProcessor;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testBeans_1() {
        assertNotNull(serviceAnnotationPostProcessor);
    }

    @Test
    void testBeans_2() {
        assertNotNull(serviceAnnotationPostProcessor.getIfAvailable());
    }

    @Test
    void testBeans_3() {
        ReferenceAnnotationBeanPostProcessor referenceAnnotationBeanPostProcessor = DubboBeanUtils.getReferenceAnnotationBeanPostProcessor(applicationContext);
        assertNotNull(referenceAnnotationBeanPostProcessor);
    }
}
