package org.apache.rocketmq.proxy.config;

import org.apache.rocketmq.proxy.ProxyMode;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationManagerTest_Purified extends InitConfigTest {

    @Test
    public void testIntConfig_1() {
        assertThat(ConfigurationManager.getProxyConfig()).isNotNull();
    }

    @Test
    public void testIntConfig_2() {
        assertThat(ConfigurationManager.getProxyConfig().getProxyMode()).isEqualToIgnoringCase(ProxyMode.CLUSTER.toString());
    }

    @Test
    public void testIntConfig_3() {
        String brokerConfig = ConfigurationManager.getProxyConfig().getBrokerConfigPath();
        assertThat(brokerConfig).isEqualTo(ConfigurationManager.getProxyHome() + "/conf/broker.conf");
    }
}
