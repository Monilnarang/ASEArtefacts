package org.apache.commons.configuration2.plist;

import static org.apache.commons.configuration2.TempDirUtils.newFile;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationAssert;
import org.apache.commons.configuration2.ConfigurationComparator;
import org.apache.commons.configuration2.StrictConfigurationComparator;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TestXMLPropertyListConfiguration_Purified {

    private static void load(final XMLPropertyListConfiguration c, final File file) throws ConfigurationException {
        new FileHandler(c).load(file);
    }

    @TempDir
    public File tempFolder;

    private XMLPropertyListConfiguration config;

    private void checkArrayProperty(final List<?> expectedValues) throws ConfigurationException {
        final StringWriter out = new StringWriter();
        new FileHandler(config).save(out);
        final StringBuilder values = new StringBuilder();
        for (final Object v : expectedValues) {
            values.append("<string>").append(v).append("</string>");
        }
        final String content = out.toString().replaceAll("[ \n\r]", "");
        assertTrue(content.contains(String.format("<key>array</key><array>%s</array>", values)));
    }

    private void save(final File file) throws ConfigurationException {
        new FileHandler(config).save(file);
    }

    @BeforeEach
    public void setUp() throws Exception {
        config = new XMLPropertyListConfiguration();
        load(config, ConfigurationAssert.getTestFile("test.plist.xml"));
    }

    @Test
    public void testBoolean_1() throws Exception {
        assertTrue(config.getBoolean("boolean1"));
    }

    @Test
    public void testBoolean_2() throws Exception {
        assertFalse(config.getBoolean("boolean2"));
    }

    @Test
    public void testDictionary_1() {
        assertEquals("value1", config.getProperty("dictionary.key1"));
    }

    @Test
    public void testDictionary_2() {
        assertEquals("value2", config.getProperty("dictionary.key2"));
    }

    @Test
    public void testDictionary_3() {
        assertEquals("value3", config.getProperty("dictionary.key3"));
    }
}
