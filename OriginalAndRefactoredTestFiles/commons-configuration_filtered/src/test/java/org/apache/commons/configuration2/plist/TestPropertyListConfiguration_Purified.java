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
import java.io.StringReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationAssert;
import org.apache.commons.configuration2.ConfigurationComparator;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.StrictConfigurationComparator;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.NodeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TestPropertyListConfiguration_Purified {

    private static List<ImmutableNode> getNamedChildren(final HierarchicalConfiguration<ImmutableNode> config, final String name) {
        final NodeHandler<ImmutableNode> handler = config.getNodeModel().getNodeHandler();
        return handler.getChildren(handler.getRootNode(), name);
    }

    private static void load(final PropertyListConfiguration c, final File f) throws ConfigurationException {
        new FileHandler(c).load(f);
    }

    @TempDir
    public File tempFolder;

    private PropertyListConfiguration config;

    private final File testProperties = ConfigurationAssert.getTestFile("test.plist");

    private void saveConfig(final File file) throws ConfigurationException {
        new FileHandler(config).save(file);
    }

    @BeforeEach
    public void setUp() throws Exception {
        config = new PropertyListConfiguration();
        load(config, testProperties);
    }

    @Test
    public void testDictionary_1() {
        assertEquals("bar1", config.getProperty("dictionary.foo1"));
    }

    @Test
    public void testDictionary_2() {
        assertEquals("bar2", config.getProperty("dictionary.foo2"));
    }

    @Test
    public void testQuotedString_1() {
        assertEquals("string2", config.getProperty("quoted-string"));
    }

    @Test
    public void testQuotedString_2() {
        assertEquals("this is a string", config.getProperty("quoted-string2"));
    }

    @Test
    public void testQuotedString_3() {
        assertEquals("this is a \"complex\" string {(=,;)}", config.getProperty("complex-string"));
    }

    @Test
    public void testQuoteString_1() {
        assertNull(config.quoteString(null));
    }

    @Test
    public void testQuoteString_2() {
        assertEquals("abcd", config.quoteString("abcd"));
    }

    @Test
    public void testQuoteString_3() {
        assertEquals("\"ab cd\"", config.quoteString("ab cd"));
    }

    @Test
    public void testQuoteString_4() {
        assertEquals("\"foo\\\"bar\"", config.quoteString("foo\"bar"));
    }

    @Test
    public void testQuoteString_5() {
        assertEquals("\"foo;bar\"", config.quoteString("foo;bar"));
    }
}
