package org.languagetool.rules.de;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.TextLevelRule;

public class GermanRepeatedWordsRuleTest_Purified {

    private TextLevelRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new GermanRepeatedWordsRule(TestTools.getMessages("de"));
        lt = new JLanguageTool(Languages.getLanguageForShortCode("de"));
    }

    private RuleMatch[] getRuleMatches(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        return rule.match(lt.analyzeText(sentences), aText);
    }

    private void assertCorrectText(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        RuleMatch[] matches = rule.match(lt.analyzeText(sentences), aText);
        assertEquals(0, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrectText("Lauf so schnell wie möglich. Das ist MÖGLICH!");
    }

    @Test
    public void testRule_2_testMerged_2() throws IOException {
        RuleMatch[] matches = getRuleMatches("Das ist hervorragende Arbeit. Die Ergebnisse sind hervorragend.");
        assertEquals(1, matches.length);
        assertEquals("[brillant, exzellent, perfekt, fantastisch, traumhaft]", matches[0].getSuggestedReplacements().toString());
    }
}
