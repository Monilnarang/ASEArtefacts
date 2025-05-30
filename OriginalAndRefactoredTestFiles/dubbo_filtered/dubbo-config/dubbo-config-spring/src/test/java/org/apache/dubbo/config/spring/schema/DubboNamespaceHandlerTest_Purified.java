package org.apache.dubbo.config.spring.schema;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MetricsConfig;
import org.apache.dubbo.config.ModuleConfig;
import org.apache.dubbo.config.MonitorConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfigBase;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.config.context.ModuleConfigManager;
import org.apache.dubbo.config.spring.ServiceBean;
import org.apache.dubbo.config.spring.api.DemoService;
import org.apache.dubbo.config.spring.impl.DemoServiceImpl;
import org.apache.dubbo.rpc.model.ApplicationModel;
import java.util.Collection;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.apache.dubbo.common.constants.MetricsConstants.PROTOCOL_PROMETHEUS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DubboNamespaceHandlerTest_Purified {

    private static String resourcePath = "org.apache.dubbo.config.spring".replace('.', '/');

    @BeforeEach
    public void setUp() {
        DubboBootstrap.reset();
    }

    @AfterEach
    public void tearDown() {
        DubboBootstrap.reset();
    }

    @Configuration
    @PropertySource("classpath:/META-INF/demo-provider.properties")
    @ImportResource(locations = "classpath:/org/apache/dubbo/config/spring/demo-provider.xml")
    static class XmlConfiguration {
    }

    @Test
    void testMetricsPrometheus_1_testMerged_1() {
        ConfigManager configManager = ApplicationModel.defaultModel().getApplicationConfigManager();
        MetricsConfig metrics = configManager.getMetrics().get();
        assertEquals(metrics.getProtocol(), PROTOCOL_PROMETHEUS);
        assertEquals(metrics.getPrometheus().getExporter().getEnabled(), true);
        assertEquals(metrics.getPrometheus().getExporter().getEnableHttpServiceDiscovery(), true);
        assertEquals(metrics.getPrometheus().getExporter().getHttpServiceDiscoveryUrl(), "localhost:8080");
        assertEquals(metrics.getPrometheus().getPushgateway().getEnabled(), true);
        assertEquals(metrics.getPrometheus().getPushgateway().getBaseUrl(), "localhost:9091");
        assertEquals(metrics.getPrometheus().getPushgateway().getPushInterval(), 30);
        assertEquals(metrics.getPrometheus().getPushgateway().getUsername(), "username");
        assertEquals(metrics.getPrometheus().getPushgateway().getPassword(), "password");
        assertEquals(metrics.getPrometheus().getPushgateway().getJob(), "job");
    }

    @Test
    void testMetricsPrometheus_11_testMerged_2() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(resourcePath + "/metrics-prometheus.xml");
        ctx.start();
        MetricsConfig metricsBean = ctx.getBean(MetricsConfig.class);
        assertEquals(metricsBean.getProtocol(), PROTOCOL_PROMETHEUS);
        assertEquals(metricsBean.getPrometheus().getExporter().getEnabled(), true);
        assertEquals(metricsBean.getPrometheus().getExporter().getEnableHttpServiceDiscovery(), true);
        assertEquals(metricsBean.getPrometheus().getExporter().getHttpServiceDiscoveryUrl(), "localhost:8080");
        assertEquals(metricsBean.getPrometheus().getPushgateway().getEnabled(), true);
        assertEquals(metricsBean.getPrometheus().getPushgateway().getBaseUrl(), "localhost:9091");
        assertEquals(metricsBean.getPrometheus().getPushgateway().getPushInterval(), 30);
        assertEquals(metricsBean.getPrometheus().getPushgateway().getUsername(), "username");
        assertEquals(metricsBean.getPrometheus().getPushgateway().getPassword(), "password");
        assertEquals(metricsBean.getPrometheus().getPushgateway().getJob(), "job");
    }
}
