package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.convert.DisabledListDelimiterHandler;
import org.apache.commons.configuration2.event.ConfigurationEvent;
import org.apache.commons.configuration2.event.EventListenerTestImpl;
import org.junit.jupiter.api.Test;

public class TestMapConfiguration_Purified extends TestAbstractConfiguration {

    private static final String KEY = "key1";

    private static final String SPACE_VALUE = "   Value with whitespace  ";

    private static final String TRIM_VALUE = SPACE_VALUE.trim();

    @Override
    protected AbstractConfiguration getConfiguration() {
        final Map<String, Object> map = new HashMap<>();
        map.put(KEY, "value1");
        map.put("key2", "value2");
        map.put("list", "value1, value2");
        map.put("listesc", "value1\\,value2");
        final MapConfiguration config = new MapConfiguration(map);
        config.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        return config;
    }

    @Override
    protected AbstractConfiguration getEmptyConfiguration() {
        return new MapConfiguration(new HashMap<>());
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
