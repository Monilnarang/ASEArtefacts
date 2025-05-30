package org.apache.commons.configuration2.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import org.apache.commons.configuration2.AbstractConfiguration;
import org.apache.commons.configuration2.TestAbstractConfiguration;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.junit.jupiter.api.Test;

public class TestServletFilterConfiguration_Purified extends TestAbstractConfiguration {

    private static final class MockFilterConfig implements FilterConfig {

        private final Properties parameters = new Properties();

        @Override
        public String getFilterName() {
            return null;
        }

        @Override
        public String getInitParameter(final String key) {
            return parameters.getProperty(key);
        }

        @Override
        public Enumeration<?> getInitParameterNames() {
            return parameters.keys();
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        public void setInitParameter(final String key, final String value) {
            parameters.setProperty(key, value);
        }
    }

    @Override
    protected AbstractConfiguration getConfiguration() {
        final MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("key1", "value1");
        config.setInitParameter("key2", "value2");
        config.setInitParameter("list", "value1, value2");
        config.setInitParameter("listesc", "value1\\,value2");
        final AbstractConfiguration resultConfig = new ServletFilterConfiguration(config);
        resultConfig.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        return resultConfig;
    }

    @Override
    protected AbstractConfiguration getEmptyConfiguration() {
        return new ServletFilterConfiguration(new MockFilterConfig());
    }

    @Override
    @Test
    public void testContainsValue_1() {
        assertFalse(getConfiguration().containsValue(null));
    }

    @Override
    @Test
    public void testContainsValue_2() {
        assertFalse(getConfiguration().containsValue(""));
    }
}
