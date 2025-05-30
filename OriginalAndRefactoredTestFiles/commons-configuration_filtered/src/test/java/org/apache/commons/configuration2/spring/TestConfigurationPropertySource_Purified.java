package org.apache.commons.configuration2.spring;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class TestConfigurationPropertySource_Purified {

    @Configuration
    static class Config {

        @Bean
        public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(final ConfigurableEnvironment env) {
            final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
            final MutablePropertySources sources = new MutablePropertySources();
            sources.addLast(createConfigPropertySource());
            configurer.setPropertySources(sources);
            configurer.setEnvironment(env);
            return configurer;
        }
    }

    private static final String TEST_PROPERTY = "test.property";

    private static final String TEST_LIST_PROPERTY = "test.list.property";

    private static final String TEST_SYSTEM_PROPERTY = "test.system.property";

    private static final String TEST_NULL_PROPERTY = "test.null.property";

    private static final String TEST_EMPTY_PROPERTY = "test.empty.property";

    private static final String TEST_VALUE = "testVALUE";

    private static final String TEST_SYSTEM_VALUE = "testVALUEforSystemEnv";

    private static final String TEST_SYSTEM_PROPERTY_VALUE = "${sys:" + TEST_SYSTEM_PROPERTY + "}";

    private static final String[] TEST_LIST_PROPERTY_VALUE = { TEST_SYSTEM_PROPERTY_VALUE, TEST_VALUE };

    private static final String[] TEST_LIST_VALUE = { TEST_SYSTEM_VALUE, TEST_VALUE };

    private static ConfigurationPropertySource createConfigPropertySource() {
        final PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        propertiesConfiguration.addProperty(TEST_PROPERTY, TEST_VALUE);
        propertiesConfiguration.addProperty(TEST_LIST_PROPERTY, TEST_LIST_PROPERTY_VALUE);
        propertiesConfiguration.addProperty(TEST_SYSTEM_PROPERTY, TEST_SYSTEM_PROPERTY_VALUE);
        propertiesConfiguration.addProperty(TEST_NULL_PROPERTY, null);
        propertiesConfiguration.addProperty(TEST_EMPTY_PROPERTY, "");
        return new ConfigurationPropertySource("test configuration", propertiesConfiguration);
    }

    @BeforeAll
    public static void setUp() {
        System.setProperty(TEST_SYSTEM_PROPERTY, TEST_SYSTEM_VALUE);
    }

    @AfterAll
    public static void tearDown() {
        System.clearProperty(TEST_SYSTEM_PROPERTY);
    }

    @Value("${" + TEST_PROPERTY + "}")
    private String value;

    @Value("${" + TEST_LIST_PROPERTY + "}")
    private String[] listValue;

    @Value("${" + TEST_SYSTEM_PROPERTY + "}")
    private String systemPropertyValue;

    @Value("${" + TEST_NULL_PROPERTY + ":false}")
    private boolean booleanNullValueDefaultFalse;

    @Value("${" + TEST_NULL_PROPERTY + ":true}")
    private boolean booleanNullValueDefaultTrue;

    @Value("${" + TEST_EMPTY_PROPERTY + ":defaultShouldNotApply}")
    private String emptyPropertyValue;

    @Test
    public void testNullValueInjection_1() {
        assertFalse(booleanNullValueDefaultFalse);
    }

    @Test
    public void testNullValueInjection_2() {
        assertTrue(booleanNullValueDefaultTrue);
    }
}
