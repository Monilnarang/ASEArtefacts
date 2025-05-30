package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ArabicQuestionMarkWhitespaceRuleTest_Purified {

    private ArabicQuestionMarkWhitespaceRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new ArabicQuestionMarkWhitespaceRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Languages.getLanguageForShortCode("ar"));
    }

    private void assertMatches(String text, int expectedMatches) throws IOException {
        assertEquals(expectedMatches, rule.match(lt.getAnalyzedSentence(text)).length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertMatches("أهذه تجربة؟", 0);
    }

    @Test
    public void testRule_2() throws IOException {
        assertMatches("This is a test sentence?", 0);
    }

    @Test
    public void testRule_3() throws IOException {
        assertMatches("أهذه تجربة?", 0);
    }

    @Test
    public void testRule_4() throws IOException {
        assertMatches("This is a test sentence؟", 0);
    }

    @Test
    public void testRule_5() throws IOException {
        assertMatches("أهذه تجربة ؟", 1);
    }
}
