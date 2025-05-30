package org.languagetool.rules.fa;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class PersianSpaceBeforeRuleTest_Purified {

    private PersianSpaceBeforeRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new PersianSpaceBeforeRule(TestTools.getEnglishMessages(), TestTools.getDemoLanguage());
        lt = new JLanguageTool(TestTools.getDemoLanguage());
    }

    private void assertMatches(String text, int expectedMatches) throws IOException {
        assertEquals(expectedMatches, rule.match(lt.getAnalyzedSentence(text)).length);
    }

    @Test
    public void testRules_1() throws IOException {
        assertMatches("به اینجا", 1);
    }

    @Test
    public void testRules_2() throws IOException {
        assertMatches("من به اینجا", 0);
    }

    @Test
    public void testRules_3() throws IOException {
        assertMatches("(به اینجا", 0);
    }
}
