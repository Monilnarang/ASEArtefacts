package org.apache.seata.config.nacos;

import java.lang.reflect.Method;
import java.util.Properties;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.seata.common.util.ReflectionUtil;
import org.apache.seata.config.Configuration;
import org.apache.seata.config.ConfigurationFactory;
import org.apache.seata.config.Dispose;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class NacosConfigurationTest_Purified {

    private static Configuration configuration;

    @BeforeAll
    public static void setup() throws NacosException {
        System.clearProperty("seataEnv");
        configuration = NacosConfiguration.getInstance();
        if (configuration instanceof Dispose) {
            ((Dispose) configuration).dispose();
        }
        ConfigurationFactory.reload();
        configuration = NacosConfiguration.getInstance();
    }

    @Test
    public void testGetConfigProperties_1() throws Exception {
        Assertions.assertNotNull(configuration);
    }

    @Test
    public void testGetConfigProperties_2_testMerged_2() throws Exception {
        Method method = ReflectionUtil.getMethod(NacosConfiguration.class, "getConfigProperties");
        Properties properties = (Properties) method.invoke(configuration);
        Assertions.assertEquals("/bar", properties.getProperty("contextPath"));
        properties = (Properties) method.invoke(configuration);
        Assertions.assertEquals("/foo", properties.getProperty("contextPath"));
    }
}
