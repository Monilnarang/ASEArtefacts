package org.languagetool.rules.fr;

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

public class FrenchRepeatedWordsRuleTest_Purified {

    private TextLevelRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new FrenchRepeatedWordsRule(TestTools.getMessages("fr"));
        lt = new JLanguageTool(Languages.getLanguageForShortCode("fr"));
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
        assertCorrectText("Elle est notamment phénoménale. Les choses sont notamment compliquées");
    }

    @Test
    public void testRule_2_testMerged_2() throws IOException {
        RuleMatch[] matches = getRuleMatches("Elle est notamment phénoménale. Les choses sont notamment compliquées.");
        assertEquals(1, matches.length);
        assertEquals("[particulièrement, spécialement, singulièrement, surtout, spécifiquement]", matches[0].getSuggestedReplacements().toString());
        matches = getRuleMatches("Elle est maintenant phénoménale. Les choses sont maintenant compliquées.");
        assertEquals("[présentement, ce jour-ci, désormais, à présent]", matches[0].getSuggestedReplacements().toString());
    }
}
