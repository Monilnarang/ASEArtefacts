package org.apache.commons.configuration2;

import static org.apache.commons.configuration2.TempDirUtils.newFile;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.beans.beancontext.BeanContextServicesSupport;
import java.beans.beancontext.BeanContextSupport;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.configuration2.SynchronizerTestImpl.Methods;
import org.apache.commons.configuration2.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.combined.CombinedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.convert.DisabledListDelimiterHandler;
import org.apache.commons.configuration2.convert.LegacyListDelimiterHandler;
import org.apache.commons.configuration2.convert.ListDelimiterHandler;
import org.apache.commons.configuration2.event.ConfigurationEvent;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.DefaultFileSystem;
import org.apache.commons.configuration2.io.FileHandler;
import org.apache.commons.configuration2.io.FileSystem;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestPropertiesConfiguration_Purified {

    static class DummyLayout extends PropertiesConfigurationLayout {

        private int loadCalls;

        @Override
        public void load(final PropertiesConfiguration config, final Reader in) throws ConfigurationException {
            loadCalls++;
        }
    }

    static class MockHttpURLConnection extends HttpURLConnection {

        private final int returnCode;

        private final File outputFile;

        protected MockHttpURLConnection(final URL u, final int respCode, final File outFile) {
            super(u);
            returnCode = respCode;
            outputFile = outFile;
        }

        @Override
        public void connect() throws IOException {
        }

        @Override
        public void disconnect() {
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            return new FileOutputStream(outputFile);
        }

        @Override
        public int getResponseCode() throws IOException {
            return returnCode;
        }

        @Override
        public boolean usingProxy() {
            return false;
        }
    }

    static class MockHttpURLStreamHandler extends URLStreamHandler {

        private final int responseCode;

        private final File outputFile;

        private MockHttpURLConnection connection;

        public MockHttpURLStreamHandler(final int respCode, final File outFile) {
            responseCode = respCode;
            outputFile = outFile;
        }

        public MockHttpURLConnection getMockConnection() {
            return connection;
        }

        @Override
        protected URLConnection openConnection(final URL u) throws IOException {
            connection = new MockHttpURLConnection(u, responseCode, outputFile);
            return connection;
        }
    }

    private static final class PropertiesReaderTestImpl extends PropertiesConfiguration.PropertiesReader {

        private final int maxProperties;

        private int propertyCount;

        public PropertiesReaderTestImpl(final Reader reader, final int maxProps) {
            super(reader);
            maxProperties = maxProps;
        }

        @Override
        public String getPropertyName() {
            return PROP_NAME + propertyCount;
        }

        @Override
        public String getPropertyValue() {
            return PROP_VALUE + propertyCount;
        }

        @Override
        public boolean nextProperty() throws IOException {
            propertyCount++;
            return propertyCount <= maxProperties;
        }
    }

    private static final class PropertiesWriterTestImpl extends PropertiesConfiguration.PropertiesWriter {

        public PropertiesWriterTestImpl(final ListDelimiterHandler handler) throws IOException {
            super(new FileWriter(TEST_SAVE_PROPERTIES_FILE), handler);
        }
    }

    private static final String PROP_NAME = "testProperty";

    private static final String PROP_VALUE = "value";

    private static final String CR = System.lineSeparator();

    private static final String TEST_PROPERTIES = ConfigurationAssert.getTestFile("test.properties").getAbsolutePath();

    private static final String TEST_BASE_PATH = ConfigurationAssert.TEST_DIR.getAbsolutePath();

    private static final String TEST_BASE_PATH_2 = ConfigurationAssert.TEST_DIR.getParentFile().getAbsolutePath();

    private static final File TEST_SAVE_PROPERTIES_FILE = ConfigurationAssert.getOutFile("testsave.properties");

    private static FileHandler load(final PropertiesConfiguration pc, final String fileName) throws ConfigurationException {
        final FileHandler handler = new FileHandler(pc);
        handler.setFileName(fileName);
        handler.load();
        return handler;
    }

    private PropertiesConfiguration conf;

    @TempDir
    public File tempFolder;

    private void checkBackslashList(final String key) {
        final Object prop = conf.getProperty("test." + key);
        final List<?> list = assertInstanceOf(List.class, prop);
        final String prefix = "\\\\" + key;
        assertEquals(Arrays.asList(prefix + "a", prefix + "b"), list);
    }

    private void checkCopiedConfig(final Configuration copyConf) throws ConfigurationException {
        saveTestConfig();
        final PropertiesConfiguration checkConf = new PropertiesConfiguration();
        load(checkConf, TEST_SAVE_PROPERTIES_FILE.getAbsolutePath());
        for (final Iterator<String> it = copyConf.getKeys(); it.hasNext(); ) {
            final String key = it.next();
            assertEquals(checkConf.getProperty(key), copyConf.getProperty(key), "Wrong value for property " + key);
        }
    }

    private void checkEmpty(final String key) {
        final String empty = conf.getString(key);
        assertNotNull(empty, "Property not found: " + key);
        assertEquals("", empty, "Wrong value for property " + key);
    }

    private PropertiesConfiguration checkSavedConfig() throws ConfigurationException {
        final PropertiesConfiguration checkConfig = new PropertiesConfiguration();
        checkConfig.setListDelimiterHandler(new LegacyListDelimiterHandler(','));
        load(checkConfig, TEST_SAVE_PROPERTIES_FILE.getAbsolutePath());
        ConfigurationAssert.assertConfigurationEquals(conf, checkConfig);
        return checkConfig;
    }

    private void saveTestConfig() throws ConfigurationException {
        final FileHandler handler = new FileHandler(conf);
        handler.save(TEST_SAVE_PROPERTIES_FILE);
    }

    @BeforeEach
    public void setUp() throws Exception {
        conf = new PropertiesConfiguration();
        conf.setListDelimiterHandler(new LegacyListDelimiterHandler(','));
        load(conf, TEST_PROPERTIES);
        if (TEST_SAVE_PROPERTIES_FILE.exists()) {
            assertTrue(TEST_SAVE_PROPERTIES_FILE.delete());
        }
    }

    private Configuration setUpCopyConfig() {
        final int count = 25;
        final Configuration result = new BaseConfiguration();
        for (int i = 1; i <= count; i++) {
            result.addProperty("copyKey" + i, "copyValue" + i);
        }
        return result;
    }

    @Test
    public void testAppend_1() throws Exception {
        assertEquals("aaa", conf.getString("test.threesome.one"));
    }

    @Test
    public void testAppend_2() throws Exception {
        assertEquals("true", conf.getString("configuration.loaded"));
    }

    @Test
    public void testChangingListDelimiter_1() throws Exception {
        assertEquals("a^b^c", conf.getString("test.other.delimiter"));
    }

    @Test
    public void testChangingListDelimiter_2_testMerged_2() throws Exception {
        final PropertiesConfiguration pc2 = new PropertiesConfiguration();
        pc2.setListDelimiterHandler(new DefaultListDelimiterHandler('^'));
        load(pc2, TEST_PROPERTIES);
        assertEquals("a", pc2.getString("test.other.delimiter"));
        assertEquals(3, pc2.getList("test.other.delimiter").size());
    }

    @Test
    public void testClone_1_testMerged_1() throws ConfigurationException {
        final PropertiesConfiguration copy = (PropertiesConfiguration) conf.clone();
        assertNotSame(conf.getLayout(), copy.getLayout());
        assertEquals(1, conf.getEventListeners(ConfigurationEvent.ANY).size());
        assertEquals(1, copy.getEventListeners(ConfigurationEvent.ANY).size());
        assertSame(conf.getLayout(), conf.getEventListeners(ConfigurationEvent.ANY).iterator().next());
        assertSame(copy.getLayout(), copy.getEventListeners(ConfigurationEvent.ANY).iterator().next());
    }

    @Test
    public void testClone_6() throws ConfigurationException {
        final StringWriter outConf = new StringWriter();
        new FileHandler(conf).save(outConf);
        final StringWriter outCopy = new StringWriter();
        new FileHandler(copy).save(outCopy);
        assertEquals(outConf.toString(), outCopy.toString());
    }

    @Test
    public void testComment_1() {
        assertFalse(conf.containsKey("#comment"));
    }

    @Test
    public void testComment_2() {
        assertFalse(conf.containsKey("!comment"));
    }

    @Test
    public void testDisableListDelimiter_1() throws Exception {
        assertEquals(4, conf.getList("test.mixed.array").size());
    }

    @Test
    public void testDisableListDelimiter_2() throws Exception {
        final PropertiesConfiguration pc2 = new PropertiesConfiguration();
        load(pc2, TEST_PROPERTIES);
        assertEquals(2, pc2.getList("test.mixed.array").size());
    }

    @Test
    public void testEscapedKeyValueSeparator_1() {
        assertEquals("foo", conf.getProperty("test.separator=in.key"));
    }

    @Test
    public void testEscapedKeyValueSeparator_2() {
        assertEquals("bar", conf.getProperty("test.separator:in.key"));
    }

    @Test
    public void testEscapedKeyValueSeparator_3() {
        assertEquals("foo", conf.getProperty("test.separator\tin.key"));
    }

    @Test
    public void testEscapedKeyValueSeparator_4() {
        assertEquals("bar", conf.getProperty("test.separator\fin.key"));
    }

    @Test
    public void testEscapedKeyValueSeparator_5() {
        assertEquals("foo", conf.getProperty("test.separator in.key"));
    }

    @Test
    public void testIsCommentLine_1() {
        assertTrue(PropertiesConfiguration.isCommentLine("# a comment"));
    }

    @Test
    public void testIsCommentLine_2() {
        assertTrue(PropertiesConfiguration.isCommentLine("! a comment"));
    }

    @Test
    public void testIsCommentLine_3() {
        assertTrue(PropertiesConfiguration.isCommentLine("#a comment"));
    }

    @Test
    public void testIsCommentLine_4() {
        assertTrue(PropertiesConfiguration.isCommentLine("    ! a comment"));
    }

    @Test
    public void testIsCommentLine_5() {
        assertFalse(PropertiesConfiguration.isCommentLine("   a#comment"));
    }

    @Test
    public void testKeyValueSeparators_1() {
        assertEquals("foo", conf.getProperty("test.separator.equal"));
    }

    @Test
    public void testKeyValueSeparators_2() {
        assertEquals("foo", conf.getProperty("test.separator.colon"));
    }

    @Test
    public void testKeyValueSeparators_3() {
        assertEquals("foo", conf.getProperty("test.separator.tab"));
    }

    @Test
    public void testKeyValueSeparators_4() {
        assertEquals("foo", conf.getProperty("test.separator.formfeed"));
    }

    @Test
    public void testKeyValueSeparators_5() {
        assertEquals("foo", conf.getProperty("test.separator.whitespace"));
    }

    @Test
    public void testReadFooterComment_1() {
        assertEquals("\n# This is a foot comment\n", conf.getFooter());
    }

    @Test
    public void testReadFooterComment_2() {
        assertEquals("\nThis is a foot comment\n", conf.getLayout().getCanonicalFooterCooment(false));
    }

    @Test
    public void testSaveWithDataConfig_1() throws ConfigurationException {
        conf = new PropertiesConfiguration();
        assertEquals("bar", conf.getString("foo"));
    }

    @Test
    public void testSaveWithDataConfig_2() throws ConfigurationException {
        final PropertiesConfiguration config2 = new PropertiesConfiguration();
        load(config2, TEST_SAVE_PROPERTIES_FILE.getAbsolutePath());
        assertEquals("bar", config2.getString("foo"));
    }
}
