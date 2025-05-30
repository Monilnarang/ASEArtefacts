package org.apache.commons.configuration2.beanutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.MapConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestConfigurationDynaBean_Purified {

    private ConfigurationDynaBean bean;

    private final String[] properties = { "booleanProperty", "booleanSecond", "doubleProperty", "floatProperty", "intProperty", "longProperty", "mappedProperty.key1", "mappedProperty.key2", "mappedProperty.key3", "mappedIntProperty.key1", "shortProperty", "stringProperty", "byteProperty", "charProperty" };

    private final Object[] values = { Boolean.TRUE, Boolean.TRUE, Double.MAX_VALUE, Float.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, "First Value", "Second Value", "Third Value", Integer.MAX_VALUE, Short.MAX_VALUE, "This is a string", Byte.MAX_VALUE, Character.MAX_VALUE };

    private final int[] intArray = { 0, 10, 20, 30, 40 };

    private final boolean[] booleanArray = { true, false, true, false, true };

    private final char[] charArray = { 'a', 'b', 'c', 'd', 'e' };

    private final byte[] byteArray = { 0, 10, 20, 30, 40 };

    private final long[] longArray = { 0, 10, 20, 30, 40 };

    private final short[] shortArray = { 0, 10, 20, 30, 40 };

    private final float[] floatArray = { 0, 10, 20, 30, 40 };

    private final double[] doubleArray = { 0.0, 10.0, 20.0, 30.0, 40.0 };

    private final String[] stringArray = { "String 0", "String 1", "String 2", "String 3", "String 4" };

    protected Configuration createConfiguration() {
        return new BaseConfiguration();
    }

    @BeforeEach
    public void setUp() throws Exception {
        final Configuration configuration = createConfiguration();
        for (int i = 0; i < properties.length; i++) {
            configuration.setProperty(properties[i], values[i]);
        }
        for (final int element : intArray) {
            configuration.addProperty("intIndexed", element);
        }
        for (final String element : stringArray) {
            configuration.addProperty("stringIndexed", element);
        }
        final List<String> list = Arrays.asList(stringArray);
        configuration.addProperty("listIndexed", list);
        bean = new ConfigurationDynaBean(configuration);
        bean.set("listIndexed", list);
        bean.set("intArray", intArray);
        bean.set("booleanArray", booleanArray);
        bean.set("charArray", charArray);
        bean.set("longArray", longArray);
        bean.set("shortArray", shortArray);
        bean.set("floatArray", floatArray);
        bean.set("doubleArray", doubleArray);
        bean.set("byteArray", byteArray);
        bean.set("stringArray", stringArray);
    }

    @Test
    public void testMappedContains_1() {
        assertTrue(bean.contains("mappedProperty", "key1"));
    }

    @Test
    public void testMappedContains_2() {
        assertFalse(bean.contains("mappedProperty", "Unknown Key"));
    }

    @Test
    public void testMappedRemove_1() {
        assertTrue(bean.contains("mappedProperty", "key1"));
    }

    @Test
    public void testMappedRemove_2_testMerged_2() {
        bean.remove("mappedProperty", "key1");
        assertFalse(bean.contains("mappedProperty", "key1"));
        assertFalse(bean.contains("mappedProperty", "key4"));
    }
}
