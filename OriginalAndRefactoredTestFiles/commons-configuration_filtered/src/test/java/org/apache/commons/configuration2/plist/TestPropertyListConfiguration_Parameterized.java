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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestPropertyListConfiguration_Parameterized {

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
    public void testQuoteString_1() {
        assertNull(config.quoteString(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDictionary_1_1to2_2to3")
    public void testDictionary_1_1to2_2to3(String param1, String param2) {
        assertEquals(param1, config.getProperty(param2));
    }

    static public Stream<Arguments> Provider_testDictionary_1_1to2_2to3() {
        return Stream.of(arguments("bar1", "dictionary.foo1"), arguments("bar2", "dictionary.foo2"), arguments("string2", "quoted-string"), arguments("this is a string", "quoted-string2"), arguments("this is a \"complex\" string {(=,;)}", "complex-string"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testQuoteString_2to5")
    public void testQuoteString_2to5(String param1, String param2) {
        assertEquals(param1, config.quoteString(param2));
    }

    static public Stream<Arguments> Provider_testQuoteString_2to5() {
        return Stream.of(arguments("abcd", "abcd"), arguments("\"ab cd\"", "ab cd"), arguments("\"foo\\\"bar\"", "foo\"bar"), arguments("\"foo;bar\"", "foo;bar"));
    }
}
