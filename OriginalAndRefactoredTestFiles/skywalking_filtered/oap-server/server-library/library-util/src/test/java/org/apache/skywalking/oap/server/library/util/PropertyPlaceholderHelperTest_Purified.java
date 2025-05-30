package org.apache.skywalking.oap.server.library.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.yaml.snakeyaml.Yaml;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SystemStubsExtension.class)
public class PropertyPlaceholderHelperTest_Purified {

    private PropertyPlaceholderHelper placeholderHelper;

    private final Properties properties = new Properties();

    private final Yaml yaml = new Yaml();

    @SystemStub
    private final EnvironmentVariables justForSideEffect = new EnvironmentVariables().set("REST_PORT", "12801");

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() throws FileNotFoundException {
        Reader applicationReader = ResourceUtils.read("application.yml");
        Map<String, Map<String, Object>> moduleConfig = yaml.loadAs(applicationReader, Map.class);
        if (CollectionUtils.isNotEmpty(moduleConfig)) {
            moduleConfig.forEach((moduleName, providerConfig) -> {
                selectConfig(providerConfig);
                if (providerConfig.size() > 0) {
                    providerConfig.forEach((name, config) -> {
                        final Map<String, ?> propertiesConfig = (Map<String, ?>) config;
                        if (propertiesConfig != null) {
                            propertiesConfig.forEach((key, value) -> properties.put(key, value));
                        }
                    });
                }
            });
        }
        placeholderHelper = PropertyPlaceholderHelper.INSTANCE;
    }

    private void selectConfig(final Map<String, Object> configuration) {
        if (configuration.size() <= 1) {
            return;
        }
        if (configuration.containsKey("selector")) {
            final String selector = (String) configuration.get("selector");
            final String resolvedSelector = PropertyPlaceholderHelper.INSTANCE.replacePlaceholders(selector, System.getProperties());
            configuration.entrySet().removeIf(e -> !resolvedSelector.equals(e.getKey()));
        }
    }

    @Test
    public void testDataType_1() {
        assertEquals("grpc.skywalking.apache.org", yaml.load(placeholderHelper.replacePlaceholders(properties.getProperty("gRPCHost"), properties)));
    }

    @Test
    public void testDataType_2() {
        assertEquals("0.0.0.0", yaml.load(placeholderHelper.replacePlaceholders(properties.getProperty("restHost"), properties)));
    }

    @Test
    public void testDataType_3() {
        assertEquals((Integer) 12801, yaml.load(placeholderHelper.replacePlaceholders(properties.getProperty("restPort"), properties)));
    }
}
