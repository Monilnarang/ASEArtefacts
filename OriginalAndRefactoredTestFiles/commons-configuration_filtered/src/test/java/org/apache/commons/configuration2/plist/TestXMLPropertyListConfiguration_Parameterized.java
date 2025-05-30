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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestXMLPropertyListConfiguration_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testDictionary_1to3")
    public void testDictionary_1to3(String param1, String param2) {
        assertEquals(param1, config.getProperty(param2));
    }

    static public Stream<Arguments> Provider_testDictionary_1to3() {
        return Stream.of(arguments("value1", "dictionary.key1"), arguments("value2", "dictionary.key2"), arguments("value3", "dictionary.key3"));
    }
}
