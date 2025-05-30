package org.apache.dubbo.spring.boot.autoconfigure;

import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationPostProcessor;
import org.apache.dubbo.config.spring.context.config.ConfigurationBeanBinder;
import org.apache.dubbo.config.spring.util.DubboBeanUtils;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ClassUtils;
import static org.apache.dubbo.spring.boot.util.DubboUtils.BASE_PACKAGES_BEAN_NAME;
import static org.apache.dubbo.spring.boot.util.DubboUtils.RELAXED_DUBBO_CONFIG_BINDER_BEAN_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DubboRelaxedBinding2AutoConfigurationTest.class, properties = { "dubbo.scan.basePackages = org.apache.dubbo.spring.boot.autoconfigure" })
@EnableAutoConfiguration
@PropertySource(value = "classpath:/dubbo.properties")
class DubboRelaxedBinding2AutoConfigurationTest_Purified {

    @Autowired
    @Qualifier(BASE_PACKAGES_BEAN_NAME)
    private Set<String> packagesToScan;

    @Autowired
    @Qualifier(RELAXED_DUBBO_CONFIG_BINDER_BEAN_NAME)
    private ConfigurationBeanBinder dubboConfigBinder;

    @Autowired
    private ObjectProvider<ServiceAnnotationPostProcessor> serviceAnnotationPostProcessor;

    @Autowired
    private Environment environment;

    @Autowired
    private Map<String, Environment> environments;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testBeans_1() {
        assertTrue(ClassUtils.isAssignableValue(BinderDubboConfigBinder.class, dubboConfigBinder));
    }

    @Test
    void testBeans_2() {
        assertNotNull(serviceAnnotationPostProcessor);
    }

    @Test
    void testBeans_3() {
        assertNotNull(serviceAnnotationPostProcessor.getIfAvailable());
    }

    @Test
    void testBeans_4() {
        ReferenceAnnotationBeanPostProcessor referenceAnnotationBeanPostProcessor = DubboBeanUtils.getReferenceAnnotationBeanPostProcessor(applicationContext);
        assertNotNull(referenceAnnotationBeanPostProcessor);
    }

    @Test
    void testBeans_5() {
        assertNotNull(environment);
    }

    @Test
    void testBeans_6() {
        assertNotNull(environments);
    }

    @Test
    void testBeans_7() {
        assertEquals(1, environments.size());
    }

    @Test
    void testBeans_8() {
        assertTrue(environments.containsValue(environment));
    }
}
