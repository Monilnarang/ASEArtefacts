package org.apache.dubbo.config.spring.beans.factory.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@PropertySource(name = "yaml-source", value = { "classpath:/META-INF/dubbo.yml" }, factory = YamlPropertySourceFactory.class)
@Configuration
@ContextConfiguration(classes = YamlPropertySourceFactoryTest.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class YamlPropertySourceFactoryTest_Purified {

    @Autowired
    private Environment environment;

    @Value("${dubbo.consumer.default}")
    private Boolean isDefault;

    @Value("${dubbo.consumer.client}")
    private String client;

    @Value("${dubbo.consumer.threadpool}")
    private String threadPool;

    @Value("${dubbo.consumer.corethreads}")
    private Integer coreThreads;

    @Value("${dubbo.consumer.threads}")
    private Integer threads;

    @Value("${dubbo.consumer.queues}")
    private Integer queues;

    @Test
    void testProperty_1() {
        Assertions.assertEquals(isDefault, environment.getProperty("dubbo.consumer.default", Boolean.class));
    }

    @Test
    void testProperty_2() {
        Assertions.assertEquals(client, environment.getProperty("dubbo.consumer.client", String.class));
    }

    @Test
    void testProperty_3() {
        Assertions.assertEquals(threadPool, environment.getProperty("dubbo.consumer.threadpool", String.class));
    }

    @Test
    void testProperty_4() {
        Assertions.assertEquals(coreThreads, environment.getProperty("dubbo.consumer.corethreads", Integer.class));
    }

    @Test
    void testProperty_5() {
        Assertions.assertEquals(threads, environment.getProperty("dubbo.consumer.threads", Integer.class));
    }

    @Test
    void testProperty_6() {
        Assertions.assertEquals(queues, environment.getProperty("dubbo.consumer.queues", Integer.class));
    }
}
