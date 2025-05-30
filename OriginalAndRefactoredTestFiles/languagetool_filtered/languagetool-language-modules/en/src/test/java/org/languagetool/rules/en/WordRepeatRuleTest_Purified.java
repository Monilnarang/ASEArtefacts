package org.languagetool.rules.en;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.WordRepeatRule;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class WordRepeatRuleTest_Purified {

    private final Language english = Languages.getLanguageForShortCode("en");

    private final WordRepeatRule rule = new WordRepeatRule(TestTools.getEnglishMessages(), english);

    private final JLanguageTool lt = new JLanguageTool(english);

    private void assertMatches(String input, int expectedMatches) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        assertEquals(expectedMatches, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertMatches("This is a test sentence.", 0);
    }

    @Test
    public void testRule_2() throws IOException {
        assertMatches("This is a test sentence...", 0);
    }

    @Test
    public void testRule_3() throws IOException {
        assertMatches("And side to side and top to bottom...", 0);
    }

    @Test
    public void testRule_4() throws IOException {
        assertMatches("This this is a test sentence.", 1);
    }

    @Test
    public void testRule_5() throws IOException {
        assertMatches("This is a test sentence sentence.", 1);
    }

    @Test
    public void testRule_6() throws IOException {
        assertMatches("This is is a a test sentence sentence.", 3);
    }
}
