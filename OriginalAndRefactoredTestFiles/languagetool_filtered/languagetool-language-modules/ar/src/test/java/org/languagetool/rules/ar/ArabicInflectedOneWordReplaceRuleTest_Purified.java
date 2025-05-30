package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ArabicInflectedOneWordReplaceRuleTest_Purified {

    private ArabicInflectedOneWordReplaceRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new ArabicInflectedOneWordReplaceRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Languages.getLanguageForShortCode("ar"));
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertNotEquals(matches.length, 0);
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("أجريت بحوثا في المخبر");
    }

    @Test
    public void testRule_2() throws IOException {
        assertCorrect("وجعل لكم من أزواجكم بنين وحفدة");
    }

    @Test
    public void testRule_3() throws IOException {
        assertIncorrect("أجريت أبحاثا في المخبر");
    }

    @Test
    public void testRule_4() throws IOException {
        assertIncorrect("وجعل لكم من أزواجكم بنين وأحفاد");
    }
}
