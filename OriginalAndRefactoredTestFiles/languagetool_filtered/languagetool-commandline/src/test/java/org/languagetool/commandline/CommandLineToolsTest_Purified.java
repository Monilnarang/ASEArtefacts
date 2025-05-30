package org.languagetool.commandline;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.rules.WordRepeatRule;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandLineToolsTest_Purified {

    private ByteArrayOutputStream out;

    private PrintStream stdout;

    private PrintStream stderr;

    @Before
    public void setUp() {
        this.stdout = System.out;
        this.stderr = System.err;
        this.out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.out));
        System.setErr(new PrintStream(err));
    }

    @After
    public void tearDown() {
        System.setOut(this.stdout);
        System.setErr(this.stderr);
    }

    @Test
    public void testCheck_1_testMerged_1() throws IOException {
        String output = new String(this.out.toByteArray());
        assertEquals(0, output.indexOf("Time:"));
        output = new String(this.out.toByteArray());
        assertTrue(output.contains("Rule ID: WORD_REPEAT_RULE"));
    }

    @Test
    public void testCheck_2_testMerged_2() throws IOException {
        JLanguageTool tool = new JLanguageTool(TestTools.getDemoLanguage());
        int matches = CommandLineTools.checkText("Foo.", tool);
        assertEquals(0, matches);
        tool.disableRule("test_unification_with_negation");
        tool.addRule(new WordRepeatRule(TestTools.getEnglishMessages(), TestTools.getDemoLanguage()));
        matches = CommandLineTools.checkText("To jest problem problem.", tool);
        assertEquals(1, matches);
    }
}
