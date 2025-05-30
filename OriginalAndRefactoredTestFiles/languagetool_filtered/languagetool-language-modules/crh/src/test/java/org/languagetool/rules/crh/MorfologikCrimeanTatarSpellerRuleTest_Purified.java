package org.languagetool.rules.crh;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.CrimeanTatar;

public class MorfologikCrimeanTatarSpellerRuleTest_Purified {

    private JLanguageTool langTool;

    private MorfologikCrimeanTatarSpellerRule rule;

    @Before
    public void init() throws IOException {
        rule = new MorfologikCrimeanTatarSpellerRule(TestTools.getMessages("crh"), new CrimeanTatar(), null, Collections.emptyList());
        langTool = new JLanguageTool(new CrimeanTatar());
    }

    @Test
    public void testMorfologikSpeller_1() throws IOException {
        assertEquals(Arrays.asList(), Arrays.asList(rule.match(langTool.getAnalyzedSentence("abadlarnı amutlarıñ!"))));
    }

    @Test
    public void testMorfologikSpeller_2() throws IOException {
        assertEquals(1, rule.match(langTool.getAnalyzedSentence("aaabadlarnı")).length);
    }

    @Test
    public void testMorfologikSpeller_3() throws IOException {
        assertEquals(0, rule.match(langTool.getAnalyzedSentence("abadanlaşırlar")).length);
    }

    @Test
    public void testMorfologikSpeller_4() throws IOException {
        assertEquals(0, rule.match(langTool.getAnalyzedSentence("meraba")).length);
    }
}
