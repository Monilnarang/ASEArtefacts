package org.apache.commons.configuration2;

import static org.apache.commons.configuration2.TempDirUtils.newFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.configuration2.SynchronizerTestImpl.Methods;
import org.apache.commons.configuration2.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.sync.ReadWriteSynchronizer;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.NodeHandler;
import org.apache.commons.configuration2.tree.NodeNameMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestINIConfiguration_Purified {

    private static final class GlobalSectionTestThread extends Thread {

        private final INIConfiguration config;

        private volatile boolean error;

        public GlobalSectionTestThread(final INIConfiguration conf) {
            config = conf;
        }

        @Override
        public void run() {
            final int loopCount = 250;
            for (int i = 0; i < loopCount && !error; i++) {
                try {
                    config.getSection(null);
                } catch (final IllegalStateException istex) {
                    error = true;
                }
            }
        }
    }

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String INI_DATA = "[section1]" + LINE_SEPARATOR + "var1 = foo" + LINE_SEPARATOR + "var2 = 451" + LINE_SEPARATOR + LINE_SEPARATOR + "[section2]" + LINE_SEPARATOR + "var1 = 123.45" + LINE_SEPARATOR + "var2 = bar" + LINE_SEPARATOR + LINE_SEPARATOR + "[section3]" + LINE_SEPARATOR + "var1 = true" + LINE_SEPARATOR + "interpolated = ${section3.var1}" + LINE_SEPARATOR + "multi = foo" + LINE_SEPARATOR + "multi = bar" + LINE_SEPARATOR + LINE_SEPARATOR;

    private static final String INI_DATA2 = "[section4]" + LINE_SEPARATOR + "var1 = \"quoted value\"" + LINE_SEPARATOR + "var2 = \"quoted value\\nwith \\\"quotes\\\"\"" + LINE_SEPARATOR + "var3 = 123 ; comment" + LINE_SEPARATOR + "var4 = \"1;2;3\" ; comment" + LINE_SEPARATOR + "var5 = '\\'quoted\\' \"value\"' ; comment" + LINE_SEPARATOR + "var6 = \"\"" + LINE_SEPARATOR;

    private static final String INI_DATA3 = "[section5]" + LINE_SEPARATOR + "multiLine = one \\" + LINE_SEPARATOR + "    two      \\" + LINE_SEPARATOR + " three" + LINE_SEPARATOR + "singleLine = C:\\Temp\\" + LINE_SEPARATOR + "multiQuoted = one \\" + LINE_SEPARATOR + "\"  two  \" \\" + LINE_SEPARATOR + "  three" + LINE_SEPARATOR + "multiComment = one \\ ; a comment" + LINE_SEPARATOR + "two" + LINE_SEPARATOR + "multiQuotedComment = \" one \" \\ ; comment" + LINE_SEPARATOR + "two" + LINE_SEPARATOR + "noFirstLine = \\" + LINE_SEPARATOR + "  line 2" + LINE_SEPARATOR + "continueNoLine = one \\" + LINE_SEPARATOR;

    private static final String INI_DATA4 = "[section6]" + LINE_SEPARATOR + "key1{0}value1" + LINE_SEPARATOR + "key2{0}value2" + LINE_SEPARATOR + LINE_SEPARATOR + "[section7]" + LINE_SEPARATOR + "key3{0}value3" + LINE_SEPARATOR;

    private static final String INI_DATA5 = "[section4]" + LINE_SEPARATOR + "var1 = \"quoted value\"" + LINE_SEPARATOR + "var2 = \"quoted value\\nwith \\\"quotes\\\"\"" + LINE_SEPARATOR + "var3 = 123 # comment" + LINE_SEPARATOR + "var4 = \"1#2;3\" # comment" + LINE_SEPARATOR + "var5 = '\\'quoted\\' \"value\"' # comment" + LINE_SEPARATOR + "var6 = \"\"" + LINE_SEPARATOR;

    private static final String INI_DATA6 = "[section1]; main section" + LINE_SEPARATOR + "var1 = foo" + LINE_SEPARATOR + LINE_SEPARATOR + "[section11] ; sub-section related to [section1]" + LINE_SEPARATOR + "var1 = 123.45" + LINE_SEPARATOR;

    private static final String INI_DATA7 = "[section1]# main section" + LINE_SEPARATOR + "var1 = foo" + LINE_SEPARATOR + LINE_SEPARATOR + "[section11] # sub-section related to [section1]" + LINE_SEPARATOR + "var1 = 123.45" + LINE_SEPARATOR;

    private static final String INI_DATA_SEPARATORS = "[section]" + LINE_SEPARATOR + "var1 = value1" + LINE_SEPARATOR + "var2 : value2" + LINE_SEPARATOR + "var3=value3" + LINE_SEPARATOR + "var4:value4" + LINE_SEPARATOR + "var5 : value=5" + LINE_SEPARATOR + "var:6=value" + LINE_SEPARATOR + "var:7=\"value7\"" + LINE_SEPARATOR + "var:8 =  \"value8\"" + LINE_SEPARATOR;

    private static final String INI_DATA_GLOBAL_ONLY = "globalVar = testGlobal" + LINE_SEPARATOR + LINE_SEPARATOR;

    private static final String INI_DATA_GLOBAL = INI_DATA_GLOBAL_ONLY + INI_DATA;

    private static void load(final INIConfiguration instance, final String data) throws ConfigurationException {
        try (StringReader reader = new StringReader(data)) {
            instance.read(reader);
        } catch (final IOException e) {
            throw new ConfigurationException(e);
        }
    }

    private static Stream<Arguments> provideSectionsWithComments() {
        return Stream.of(Arguments.of(INI_DATA6, false, new String[] { null, "section11] ; sub-section related to [section1" }), Arguments.of(INI_DATA7, false, new String[] { null, "section11] # sub-section related to [section1" }), Arguments.of(INI_DATA6, true, new String[] { "section1", "section11" }), Arguments.of(INI_DATA7, true, new String[] { "section1", "section11" }));
    }

    private static Stream<Arguments> provideValuesWithComments() {
        return Stream.of(Arguments.of(INI_DATA2, "section4.var3", "123"), Arguments.of(INI_DATA2, "section4.var4", "1;2;3"), Arguments.of(INI_DATA2, "section4.var5", "'quoted' \"value\""), Arguments.of(INI_DATA5, "section4.var3", "123"), Arguments.of(INI_DATA5, "section4.var4", "1#2;3"), Arguments.of(INI_DATA5, "section4.var5", "'quoted' \"value\""));
    }

    private static String saveToString(final INIConfiguration config) throws ConfigurationException {
        final StringWriter writer = new StringWriter();
        try {
            config.write(writer);
        } catch (final IOException e) {
            throw new ConfigurationException(e);
        }
        return writer.toString();
    }

    private static INIConfiguration setUpConfig(final String data) throws ConfigurationException {
        return setUpConfig(data, false);
    }

    private static INIConfiguration setUpConfig(final String data, final boolean inLineCommentsAllowed) throws ConfigurationException {
        final INIConfiguration instance = INIConfiguration.builder().setSectionInLineCommentsAllowed(inLineCommentsAllowed).build();
        instance.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        load(instance, data);
        return instance;
    }

    @TempDir
    public File tempFolder;

    private void checkContent(final INIConfiguration instance) {
        assertEquals("foo", instance.getString("section1.var1"));
        assertEquals(451, instance.getInt("section1.var2"));
        assertEquals(123.45, instance.getDouble("section2.var1"), .001);
        assertEquals("bar", instance.getString("section2.var2"));
        assertTrue(instance.getBoolean("section3.var1"));
        assertEquals(new HashSet<>(Arrays.asList("section1", "section2", "section3")), instance.getSections());
    }

    private void checkLoad(final String data) throws ConfigurationException {
        final INIConfiguration instance = setUpConfig(data);
        checkContent(instance);
    }

    private void checkSave(final String content) throws ConfigurationException {
        final INIConfiguration config = setUpConfig(content);
        final String sOutput = saveToString(config);
        assertEquals(content, sOutput);
    }

    private void checkSectionNames(final INIConfiguration config, final String[] expected) {
        final Set<String> sectionNames = config.getSections();
        assertEquals(new HashSet<>(Arrays.asList(expected)), sectionNames);
    }

    private INIConfiguration checkSectionNames(final String data, final String[] expected) throws ConfigurationException {
        final INIConfiguration config = setUpConfig(data);
        checkSectionNames(config, expected);
        return config;
    }

    private File writeTestFile(final String content) throws IOException {
        final File file = newFile(tempFolder);
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println(content);
        }
        return file;
    }

    @Test
    public void testMergeDuplicateSection_1_testMerged_1() throws ConfigurationException, IOException {
        final String data = "[section]\nvar1 = sec1\n\n" + "[section]\nvar2 = sec2\n";
        final INIConfiguration config = setUpConfig(data);
        assertEquals("sec1", config.getString("section.var1"));
        assertEquals("sec2", config.getString("section.var2"));
        final HierarchicalConfiguration<ImmutableNode> sub = config.getSection("section");
        assertEquals("sec1", sub.getString("var1"));
        assertEquals("sec2", sub.getString("var2"));
    }

    @Test
    public void testMergeDuplicateSection_5_testMerged_2() throws ConfigurationException, IOException {
        final StringWriter writer = new StringWriter();
        config.write(writer);
        final String content = writer.toString();
        final int pos = content.indexOf("[section]");
        assertTrue(pos >= 0);
        assertTrue(content.indexOf("[section]", pos + 1) < 0);
    }

    @Test
    public void testSaveClearedSection_1() throws ConfigurationException, IOException {
        final String data = "[section]\ntest = failed\n";
        final INIConfiguration config = setUpConfig(data);
        SubnodeConfiguration sub = config.getSection("section");
        assertFalse(sub.isEmpty());
    }

    @Test
    public void testSaveClearedSection_2() throws ConfigurationException, IOException {
        final StringWriter writer = new StringWriter();
        config.write(writer);
        final HierarchicalConfiguration<?> config2 = setUpConfig(writer.toString());
        assertEquals("success", config2.getString("section.test"));
    }

    @Test
    public void testSaveWithDelimiterParsingDisabled_1() throws ConfigurationException {
        final INIConfiguration config = new INIConfiguration();
        load(config, data);
        assertEquals("1,2, 3", config.getString("section3.nolist"));
    }

    @Test
    public void testSaveWithDelimiterParsingDisabled_2() throws ConfigurationException {
        final INIConfiguration config2 = new INIConfiguration();
        load(config2, content);
        assertEquals("1,2, 3", config2.getString("section3.nolist"));
    }
}
