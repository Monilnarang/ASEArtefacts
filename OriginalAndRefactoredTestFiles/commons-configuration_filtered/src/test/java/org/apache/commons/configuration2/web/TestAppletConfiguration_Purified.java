package org.apache.commons.configuration2.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.applet.Applet;
import java.util.Properties;
import org.apache.commons.configuration2.AbstractConfiguration;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.MapConfiguration;
import org.apache.commons.configuration2.TestAbstractConfiguration;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAppletConfiguration_Purified extends TestAbstractConfiguration {

    private boolean supportsApplet;

    @Override
    protected AbstractConfiguration getConfiguration() {
        final AbstractConfiguration config;
        final Properties parameters = new Properties();
        parameters.setProperty("key1", "value1");
        parameters.setProperty("key2", "value2");
        parameters.setProperty("list", "value1, value2");
        parameters.setProperty("listesc", "value1\\,value2");
        if (supportsApplet) {
            final Applet applet = new Applet() {

                private static final long serialVersionUID = 1L;

                @Override
                public String getParameter(final String key) {
                    return parameters.getProperty(key);
                }

                @Override
                public String[][] getParameterInfo() {
                    return new String[][] { { "key1", "String", "" }, { "key2", "String", "" }, { "list", "String[]", "" }, { "listesc", "String", "" } };
                }
            };
            config = new AppletConfiguration(applet);
        } else {
            config = new MapConfiguration(parameters);
        }
        config.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        return config;
    }

    @Override
    protected AbstractConfiguration getEmptyConfiguration() {
        if (supportsApplet) {
            return new AppletConfiguration(new Applet());
        }
        return new BaseConfiguration();
    }

    @BeforeEach
    public void setUp() throws Exception {
        try {
            new Applet();
            supportsApplet = true;
        } catch (final Exception ex) {
            supportsApplet = false;
        }
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
