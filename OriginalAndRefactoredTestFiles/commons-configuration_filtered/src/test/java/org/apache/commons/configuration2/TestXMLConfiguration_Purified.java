package org.apache.commons.configuration2;

import static org.apache.commons.configuration2.TempDirUtils.newFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.apache.commons.configuration2.SynchronizerTestImpl.Methods;
import org.apache.commons.configuration2.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.convert.DisabledListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.apache.commons.configuration2.resolver.CatalogResolver;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.NodeStructureHelper;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class TestXMLConfiguration_Purified {

    private static final class ReloadThread extends Thread {

        private final FileBasedConfigurationBuilder<?> builder;

        ReloadThread(final FileBasedConfigurationBuilder<?> confBulder) {
            builder = confBulder;
        }

        @Override
        public void run() {
            for (int i = 0; i < LOOP_COUNT; i++) {
                builder.resetResult();
            }
        }
    }

    private static final String CATALOG_FILES = ConfigurationAssert.getTestFile("catalog.xml").getAbsolutePath();

    static final String ENCODING = StandardCharsets.ISO_8859_1.name();

    static final String SYSTEM_ID = "properties.dtd";

    static final String PUBLIC_ID = "-//Commons Configuration//DTD Test Configuration 1.3//EN";

    static final String DOCTYPE_DECL = " PUBLIC \"" + PUBLIC_ID + "\" \"" + SYSTEM_ID + "\">";

    static final String DOCTYPE = "<!DOCTYPE ";

    static final String PROP_FACTORY = "javax.xml.transform.TransformerFactory";

    private static final int THREAD_COUNT = 5;

    private static final int LOOP_COUNT = 100;

    private static XMLConfiguration createFromFile(final String fileName) throws ConfigurationException {
        final XMLConfiguration config = new XMLConfiguration();
        config.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        load(config, fileName);
        return config;
    }

    private static void load(final XMLConfiguration config, final String fileName) throws ConfigurationException {
        final FileHandler handler = new FileHandler(config);
        handler.setFileName(fileName);
        handler.load();
    }

    @TempDir
    public File tempFolder;

    private final String testProperties = ConfigurationAssert.getTestFile("test.xml").getAbsolutePath();

    private final String testProperties2 = ConfigurationAssert.getTestFile("testDigesterConfigurationInclude1.xml").getAbsolutePath();

    private File testSaveConf;

    private File testSaveFile;

    private final String testFile2 = ConfigurationAssert.getTestFile("sample.xml").getAbsolutePath();

    private XMLConfiguration conf;

    private XMLConfiguration checkSavedConfig() throws ConfigurationException {
        return checkSavedConfig(testSaveConf);
    }

    private XMLConfiguration checkSavedConfig(final File saveFile) throws ConfigurationException {
        final XMLConfiguration config = createFromFile(saveFile.getAbsolutePath());
        ConfigurationAssert.assertConfigurationEquals(conf, config);
        return config;
    }

    private void checkSaveDelimiterParsingDisabled(final String key) throws ConfigurationException {
        conf.clear();
        conf.setListDelimiterHandler(new DisabledListDelimiterHandler());
        load(conf, testProperties);
        conf.setProperty(key, "C:\\Temp\\,C:\\Data\\");
        conf.addProperty(key, "a,b,c");
        saveTestConfig();
        final XMLConfiguration checkConf = new XMLConfiguration();
        checkConf.setListDelimiterHandler(conf.getListDelimiterHandler());
        load(checkConf, testSaveConf.getAbsolutePath());
        ConfigurationAssert.assertConfigurationEquals(conf, checkConf);
    }

    private DocumentBuilder createValidatingDocBuilder() throws ParserConfigurationException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        final DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new DefaultHandler() {

            @Override
            public void error(final SAXParseException ex) throws SAXException {
                throw ex;
            }
        });
        return builder;
    }

    private Document parseXml(final String xml) throws SAXException, IOException, ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    private void removeTestFile() {
        if (testSaveConf.exists()) {
            assertTrue(testSaveConf.delete());
        }
    }

    private void saveTestConfig() throws ConfigurationException {
        final FileHandler handler = new FileHandler(conf);
        handler.save(testSaveConf);
    }

    @BeforeEach
    public void setUp() throws Exception {
        testSaveConf = newFile("testsave.xml", tempFolder);
        testSaveFile = newFile("testsample2.xml", tempFolder);
        conf = createFromFile(testProperties);
        removeTestFile();
    }

    @Test
    public void testComplexNames_1() {
        assertEquals("Name with dot", conf.getString("complexNames.my..elem"));
    }

    @Test
    public void testComplexNames_2() {
        assertEquals("Another dot", conf.getString("complexNames.my..elem.sub..elem"));
    }

    @Test
    public void testEmptyElements_1() throws ConfigurationException {
        assertTrue(conf.containsKey("empty"));
    }

    @Test
    public void testEmptyElements_2() throws ConfigurationException {
        assertEquals("", conf.getString("empty"));
    }

    @Test
    public void testEmptyElements_3_testMerged_3() throws ConfigurationException {
        conf.addProperty("empty2", "");
        conf.setProperty("empty", "no more empty");
        conf = new XMLConfiguration();
        load(conf, testSaveConf.getAbsolutePath());
        assertEquals("no more empty", conf.getString("empty"));
        assertEquals("", conf.getProperty("empty2"));
    }

    @Test
    public void testListWithAttributes_1() {
        assertEquals(6, conf.getList("attrList.a").size());
    }

    @Test
    public void testListWithAttributes_2() {
        assertEquals("ABC", conf.getString("attrList.a(0)"));
    }

    @Test
    public void testListWithAttributes_3() {
        assertEquals("x", conf.getString("attrList.a(0)[@name]"));
    }

    @Test
    public void testListWithAttributes_4() {
        assertEquals(6, conf.getList("attrList.a[@name]").size());
    }

    @Test
    public void testLoadAndSaveFromFile_1() throws Exception {
        assertFalse(testSaveConf.exists());
    }

    @Test
    public void testLoadAndSaveFromFile_2_testMerged_2() throws Exception {
        final FileBasedConfigurationBuilder<XMLConfiguration> builder = new FileBasedConfigurationBuilder<>(XMLConfiguration.class, null, true);
        builder.configure(new FileBasedBuilderParametersImpl().setFile(testSaveConf));
        conf = builder.getConfiguration();
        assertTrue(conf.isEmpty());
        final XMLConfiguration checkConfig = createFromFile(testSaveConf.getAbsolutePath());
        assertEquals("yes", checkConfig.getString("test"));
    }

    @Test
    public void testPreserveSpace_1() {
        assertEquals(" ", conf.getString("space.blank"));
    }

    @Test
    public void testPreserveSpace_2() {
        assertEquals(" * * ", conf.getString("space.stars"));
    }

    @Test
    public void testPreserveSpaceOnElement_1() {
        assertEquals(" preserved ", conf.getString("spaceElement"));
    }

    @Test
    public void testPreserveSpaceOnElement_2() {
        assertEquals("   ", conf.getString("spaceBlankElement"));
    }

    @Test
    public void testSaveWindowsPath_1() throws ConfigurationException {
        final StringWriter writer = new StringWriter();
        new FileHandler(conf).save(writer);
        final String content = writer.toString();
        assertTrue(content.contains("<path>C:\\Temp</path>"), "Path not found: ");
    }

    @Test
    public void testSaveWindowsPath_2() throws ConfigurationException {
        final XMLConfiguration conf2 = new XMLConfiguration();
        load(conf2, testSaveConf.getAbsolutePath());
        assertEquals("C:\\Temp", conf2.getString("path"));
    }

    @Test
    public void testSaveWithDelimiterParsingDisabled_1_testMerged_1() throws ConfigurationException {
        conf = new XMLConfiguration();
        conf.setExpressionEngine(new XPathExpressionEngine());
        load(conf, testProperties);
        assertEquals("a,b,c", conf.getString("split/list3/@values"));
        assertEquals(0, conf.getMaxIndex("split/list3/@values"));
        assertEquals("a\\,b\\,c", conf.getString("split/list4/@values"));
        assertEquals("a,b,c", conf.getString("split/list1"));
        assertEquals(0, conf.getMaxIndex("split/list1"));
        assertEquals("a\\,b\\,c", conf.getString("split/list2"));
    }

    @Test
    public void testSaveWithDelimiterParsingDisabled_7_testMerged_2() throws ConfigurationException {
        XMLConfiguration config = new XMLConfiguration();
        load(config, testFile2);
        config.setProperty("Employee[@attr1]", "3,2,1");
        assertEquals("3,2,1", config.getString("Employee[@attr1]"));
        new FileHandler(config).save(testSaveFile);
        config = new XMLConfiguration();
        load(config, testSaveFile.getAbsolutePath());
        config.setProperty("Employee[@attr1]", "1,2,3");
        assertEquals("1,2,3", config.getString("Employee[@attr1]"));
        config.setProperty("Employee[@attr2]", "one, two, three");
        assertEquals("one, two, three", config.getString("Employee[@attr2]"));
        config.setProperty("Employee.text", "a,b,d");
        assertEquals("a,b,d", config.getString("Employee.text"));
        config.setProperty("Employee.Salary", "100,000");
        assertEquals("100,000", config.getString("Employee.Salary"));
    }

    @Test
    public void testSaveWithDelimiterParsingDisabled_12_testMerged_3() throws ConfigurationException {
        final XMLConfiguration checkConfig = new XMLConfiguration();
        checkConfig.setExpressionEngine(new XPathExpressionEngine());
        load(checkConfig, testSaveFile.getAbsolutePath());
        assertEquals("1,2,3", checkConfig.getString("Employee/@attr1"));
        assertEquals("one, two, three", checkConfig.getString("Employee/@attr2"));
        assertEquals("a,b,d", checkConfig.getString("Employee/text"));
        assertEquals("100,000", checkConfig.getString("Employee/Salary"));
    }

    @Test
    public void testSaveWithDoctype_1_testMerged_1() throws ConfigurationException {
        conf = new XMLConfiguration();
        load(conf, "testDtdPublic.xml");
        assertEquals(PUBLIC_ID, conf.getPublicID());
        assertEquals(SYSTEM_ID, conf.getSystemID());
    }

    @Test
    public void testSaveWithDoctype_3() throws ConfigurationException {
        final StringWriter out = new StringWriter();
        new FileHandler(conf).save(out);
        assertTrue(out.toString().contains(DOCTYPE));
    }

    @Test
    public void testSaveWithDoctypeIDs_1() throws ConfigurationException {
        assertNull(conf.getPublicID());
    }

    @Test
    public void testSaveWithDoctypeIDs_2() throws ConfigurationException {
        assertNull(conf.getSystemID());
    }

    @Test
    public void testSaveWithDoctypeIDs_3() throws ConfigurationException {
        final StringWriter out = new StringWriter();
        new FileHandler(conf).save(out);
        assertTrue(out.toString().contains(DOCTYPE + "testconfig" + DOCTYPE_DECL));
    }

    @Test
    public void testSaveWithRootAttributes_1() throws ConfigurationException {
        conf.setProperty("[@xmlns:ex]", "http://example.com/");
        assertEquals("http://example.com/", conf.getString("[@xmlns:ex]"));
    }

    @Test
    public void testSaveWithRootAttributes_2() throws ConfigurationException {
        final StringWriter out = new StringWriter();
        handler.save(out);
        assertTrue(out.toString().contains("testconfig xmlns:ex=\"http://example.com/\""), "Encoding was not written to file");
    }

    @Test
    public void testSaveWithRootAttributesByHand_1() throws ConfigurationException {
        conf = new XMLConfiguration();
        conf.addProperty("[@xmlns:foo]", "http://example.com/");
        assertEquals("http://example.com/", conf.getString("[@xmlns:foo]"));
    }

    @Test
    public void testSaveWithRootAttributesByHand_2() throws ConfigurationException {
        final StringWriter out = new StringWriter();
        handler.save(out);
        assertTrue(out.toString().contains("configuration xmlns:foo=\"http://example.com/\""), "Encoding was not written to file");
    }

    @Test
    public void testSetRootAttribute_1() throws ConfigurationException {
        conf.setProperty("[@test]", "true");
        assertEquals("true", conf.getString("[@test]"));
    }

    @Test
    public void testSetRootAttribute_2_testMerged_2() throws ConfigurationException {
        XMLConfiguration checkConf = checkSavedConfig();
        assertTrue(checkConf.containsKey("[@test]"));
        checkConf.setProperty("[@test]", "newValue");
        checkConf = checkSavedConfig();
        assertEquals("newValue", checkConf.getString("[@test]"));
    }

    @Test
    public void testSetRootNamespace_1() throws ConfigurationException {
        conf.addProperty("[@xmlns:foo]", "http://example.com/");
        conf.addProperty("foo:bar", "foobar");
        assertEquals("http://example.com/", conf.getString("[@xmlns:foo]"));
    }

    @Test
    public void testSetRootNamespace_2() throws ConfigurationException {
        final XMLConfiguration checkConf = checkSavedConfig();
        assertTrue(checkConf.containsKey("[@xmlns:foo]"));
    }

    @Test
    public void testSplitLists_1() {
        assertEquals("a,b,c", conf.getString("split.list3[@values]"));
    }

    @Test
    public void testSplitLists_2() {
        assertEquals(0, conf.getMaxIndex("split.list3[@values]"));
    }

    @Test
    public void testSplitLists_3() {
        assertEquals("a\\,b\\,c", conf.getString("split.list4[@values]"));
    }

    @Test
    public void testSplitLists_4() {
        assertEquals("a", conf.getString("split.list1"));
    }

    @Test
    public void testSplitLists_5() {
        assertEquals(2, conf.getMaxIndex("split.list1"));
    }

    @Test
    public void testSplitLists_6() {
        assertEquals("a,b,c", conf.getString("split.list2"));
    }
}
