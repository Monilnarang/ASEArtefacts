package org.apache.dubbo.config.context;

import org.apache.dubbo.common.serialization.PreferSerializationProvider;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.MetricsConfig;
import org.apache.dubbo.config.ModuleConfig;
import org.apache.dubbo.config.MonitorConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;
import static org.apache.dubbo.common.constants.CommonConstants.DEFAULT_KEY;
import static org.apache.dubbo.common.constants.MetricsConstants.PROTOCOL_PROMETHEUS;
import static org.apache.dubbo.config.context.ConfigManager.DUBBO_CONFIG_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ConfigManagerTest_Purified {

    private ConfigManager configManager;

    private ModuleConfigManager moduleConfigManager;

    @BeforeEach
    public void init() {
        ApplicationModel.defaultModel().destroy();
        ApplicationModel applicationModel = ApplicationModel.defaultModel();
        configManager = applicationModel.getApplicationConfigManager();
        moduleConfigManager = applicationModel.getDefaultModule().getConfigManager();
        FrameworkModel.defaultModel().getBeanFactory().registerBean(TestPreferSerializationProvider.class);
    }

    static class CustomRegistryConfig extends RegistryConfig {

        CustomRegistryConfig(String id) {
            super();
            this.setId(id);
        }
    }

    public static class TestPreferSerializationProvider implements PreferSerializationProvider {

        @Override
        public String getPreferSerialization() {
            return "hessian2";
        }
    }

    @Test
    void testDefaultValues_1() {
        assertFalse(configManager.getApplication().isPresent());
    }

    @Test
    void testDefaultValues_2() {
        assertFalse(configManager.getMonitor().isPresent());
    }

    @Test
    void testDefaultValues_3() {
        assertFalse(configManager.getMetrics().isPresent());
    }

    @Test
    void testDefaultValues_4() {
        assertTrue(configManager.getProtocols().isEmpty());
    }

    @Test
    void testDefaultValues_5() {
        assertTrue(configManager.getDefaultProtocols().isEmpty());
    }

    @Test
    void testDefaultValues_6() {
        assertTrue(configManager.getRegistries().isEmpty());
    }

    @Test
    void testDefaultValues_7() {
        assertTrue(configManager.getDefaultRegistries().isEmpty());
    }

    @Test
    void testDefaultValues_8() {
        assertTrue(configManager.getConfigCenters().isEmpty());
    }

    @Test
    void testDefaultValues_9() {
        assertTrue(configManager.getMetadataConfigs().isEmpty());
    }

    @Test
    void testDefaultValues_10() {
        assertTrue(moduleConfigManager.getServices().isEmpty());
    }

    @Test
    void testDefaultValues_11() {
        assertTrue(moduleConfigManager.getReferences().isEmpty());
    }

    @Test
    void testDefaultValues_12() {
        assertFalse(moduleConfigManager.getModule().isPresent());
    }

    @Test
    void testDefaultValues_13() {
        assertFalse(moduleConfigManager.getDefaultProvider().isPresent());
    }

    @Test
    void testDefaultValues_14() {
        assertFalse(moduleConfigManager.getDefaultConsumer().isPresent());
    }

    @Test
    void testDefaultValues_15() {
        assertTrue(moduleConfigManager.getProviders().isEmpty());
    }

    @Test
    void testDefaultValues_16() {
        assertTrue(moduleConfigManager.getConsumers().isEmpty());
    }

    @Test
    void testAddConfig_1_testMerged_1() {
        configManager.addConfig(new ApplicationConfig("ConfigManagerTest"));
        configManager.addConfig(new ProtocolConfig());
        assertTrue(configManager.getApplication().isPresent());
        assertFalse(configManager.getProtocols().isEmpty());
    }

    @Test
    void testAddConfig_3() {
        moduleConfigManager.addConfig(new ProviderConfig());
        assertFalse(moduleConfigManager.getProviders().isEmpty());
    }

    @Test
    void testDefaultConfig_1() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setDefault(false);
        assertFalse(ConfigManager.isDefaultConfig(providerConfig));
    }

    @Test
    void testDefaultConfig_2() {
        ProviderConfig providerConfig1 = new ProviderConfig();
        assertNull(ConfigManager.isDefaultConfig(providerConfig1));
    }

    @Test
    void testDefaultConfig_3() {
        ProviderConfig providerConfig3 = new ProviderConfig();
        providerConfig3.setDefault(true);
        assertTrue(ConfigManager.isDefaultConfig(providerConfig3));
    }

    @Test
    void testDefaultConfig_4() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setDefault(false);
        assertFalse(ConfigManager.isDefaultConfig(protocolConfig));
    }
}
