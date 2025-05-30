package org.apache.commons.configuration2;

import static org.apache.commons.configuration2.TempDirUtils.newFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.configuration2.io.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TestHierarchicalXMLConfiguration_Purified {

    private static final String TEST_DIR = "conf";

    private static final String TEST_FILENAME = "testHierarchicalXMLConfiguration.xml";

    private static final String TEST_FILENAME2 = "testHierarchicalXMLConfiguration2.xml";

    private static final String TEST_FILE = ConfigurationAssert.getTestFile(TEST_FILENAME).getAbsolutePath();

    private static final String TEST_FILE2 = ConfigurationAssert.getTestFile(TEST_FILENAME2).getAbsolutePath();

    private static final String TEST_FILE3 = ConfigurationAssert.getTestFile("test.xml").getAbsolutePath();

    private static final String TEST_SAVENAME = "testhierarchicalsave.xml";

    @TempDir
    public File tempFolder;

    private XMLConfiguration config;

    private void configTest(final XMLConfiguration config) {
        assertEquals(1, config.getMaxIndex("tables.table"));
        assertEquals("system", config.getProperty("tables.table(0)[@tableType]"));
        assertEquals("application", config.getProperty("tables.table(1)[@tableType]"));
        assertEquals("users", config.getProperty("tables.table(0).name"));
        assertEquals("documents", config.getProperty("tables.table(1).name"));
        Object prop = config.getProperty("tables.table.fields.field.name");
        Collection<?> collection = assertInstanceOf(Collection.class, prop);
        assertEquals(10, collection.size());
        prop = config.getProperty("tables.table(0).fields.field.type");
        collection = assertInstanceOf(Collection.class, prop);
        assertEquals(5, collection.size());
        prop = config.getProperty("tables.table(1).fields.field.type");
        collection = assertInstanceOf(Collection.class, prop);
        assertEquals(5, collection.size());
    }

    @BeforeEach
    public void setUp() throws Exception {
        config = new XMLConfiguration();
    }

    @Test
    public void testRootElement_1() throws Exception {
        assertEquals("configuration", config.getRootElementName());
    }

    @Test
    public void testRootElement_2() throws Exception {
        config.setRootElementName("newRootName");
        assertEquals("newRootName", config.getRootElementName());
    }

    @Test
    public void testSaveModified_1() throws Exception {
        assertTrue(config.getString("mean").startsWith("This is\n A long story..."));
    }

    @Test
    public void testSaveModified_2() throws Exception {
        assertTrue(config.getString("mean").indexOf("And even longer") > 0);
    }

    @Test
    public void testSaveModified_3_testMerged_3() throws Exception {
        config.clearProperty("test.entity[@name]");
        config.setProperty("element", "new value");
        config.setProperty("test(0)", "A <new> value");
        config.addProperty("test(1).int", Integer.valueOf(9));
        config.addProperty("list(1).sublist.item", "seven");
        config.setProperty("clear", "yes");
        config.setProperty("mean", "now it's simple");
        config.addProperty("[@topattr]", "available");
        config.addProperty("[@topattr_other]", "successfull");
        config = new XMLConfiguration();
        assertFalse(config.containsKey("test.entity[@name]"));
        assertEquals("1<2", config.getProperty("test.entity"));
        assertEquals("new value", config.getString("element"));
        assertEquals("A <new> value", config.getProperty("test(0)"));
        assertEquals((short) 8, config.getShort("test(1).short"));
        assertEquals(9, config.getInt("test(1).int"));
        assertEquals("six", config.getProperty("list(1).sublist.item(1)"));
        assertEquals("seven", config.getProperty("list(1).sublist.item(2)"));
        assertEquals("yes", config.getProperty("clear"));
        assertEquals("now it's simple", config.getString("mean"));
        assertEquals("available", config.getString("[@topattr](0)"));
        assertEquals("successfull", config.getString("[@topattr_other]"));
    }
}
