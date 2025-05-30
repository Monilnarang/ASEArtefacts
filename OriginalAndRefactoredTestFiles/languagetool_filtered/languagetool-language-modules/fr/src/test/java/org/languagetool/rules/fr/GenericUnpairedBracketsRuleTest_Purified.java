package org.languagetool.rules.fr;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.language.French;
import org.languagetool.rules.GenericUnpairedBracketsRule;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class GenericUnpairedBracketsRuleTest_Purified {

    private GenericUnpairedBracketsRule rule;

    private JLanguageTool lt;

    private void assertMatches(String input, int expectedMatches) throws IOException {
        final RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(input)));
        assertEquals(expectedMatches, matches.length);
    }

    @Test
    public void testFrenchRule_1() throws IOException {
        assertMatches("(Qu'est ce que c'est ?)", 0);
    }

    @Test
    public void testFrenchRule_2() throws IOException {
        assertMatches("Harper's Dictionary of Classical Antiquities", 0);
    }

    @Test
    public void testFrenchRule_3() throws IOException {
        assertMatches("Harper’s Dictionary of Classical Antiquities", 0);
    }

    @Test
    public void testFrenchRule_4() throws IOException {
        assertMatches("(Qu'est ce que c'est ?", 1);
    }
}
