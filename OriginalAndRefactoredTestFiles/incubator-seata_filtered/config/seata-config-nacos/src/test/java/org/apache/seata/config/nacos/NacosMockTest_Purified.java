package org.apache.seata.config.nacos;

import java.lang.reflect.UndeclaredThrowableException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.seata.config.Configuration;
import org.apache.seata.config.ConfigurationCache;
import org.apache.seata.config.ConfigurationChangeEvent;
import org.apache.seata.config.ConfigurationChangeListener;
import org.apache.seata.config.ConfigurationFactory;
import org.apache.seata.config.Dispose;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NacosMockTest_Purified {

    private static ConfigService configService;

    private static final String NACOS_ENDPOINT = "127.0.0.1:8848";

    private static final String NACOS_GROUP = "SEATA_GROUP";

    private static final String NACOS_DATAID = "seata-mock";

    private static final String SUB_NACOS_DATAID = "KEY";

    private ConfigurationChangeListener listener;

    @BeforeAll
    public static void setup() throws NacosException {
        System.setProperty("seataEnv", "mock");
        NacosConfiguration configuration = NacosConfiguration.getInstance();
        if (configuration instanceof Dispose) {
            ((Dispose) configuration).dispose();
        }
        ConfigurationFactory.reload();
        Properties properties = new Properties();
        properties.setProperty("serverAddr", NACOS_ENDPOINT);
        configService = NacosFactory.createConfigService(properties);
        configService.removeConfig(NACOS_DATAID, NACOS_GROUP);
    }

    @AfterEach
    public void afterEach() throws NacosException {
        configService.removeConfig(NACOS_DATAID, NACOS_GROUP);
        ConfigurationFactory.reload();
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    @Order(1)
    public void getInstance_1() {
        Assertions.assertNotNull(configService);
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    @Order(1)
    public void getInstance_2() {
        Assertions.assertNotNull(NacosConfiguration.getInstance());
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    @Order(1)
    public void getInstance_3() {
        Assertions.assertNotNull(ConfigurationFactory.getInstance());
    }
}
