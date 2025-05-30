package org.languagetool.rules.ro;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Romanian;
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
    public void testRomanianRule_1() throws IOException {
        assertMatches("A fost plecat (pentru puțin timp).", 0);
    }

    @Test
    public void testRomanianRule_2() throws IOException {
        assertMatches("Nu's de prin locurile astea.", 0);
    }

    @Test
    public void testRomanianRule_3() throws IOException {
        assertMatches("A fost plecat pentru „puțin timp”.", 0);
    }

    @Test
    public void testRomanianRule_4() throws IOException {
        assertMatches("A fost plecat „pentru... puțin timp”.", 0);
    }

    @Test
    public void testRomanianRule_5() throws IOException {
        assertMatches("A fost plecat „pentru... «puțin» timp”.", 0);
    }

    @Test
    public void testRomanianRule_6() throws IOException {
        assertMatches("A fost plecat \"pentru puțin timp.", 0);
    }

    @Test
    public void testRomanianRule_7() throws IOException {
        assertMatches("A fost )plecat( pentru (puțin timp).", 2);
    }

    @Test
    public void testRomanianRule_8() throws IOException {
        assertMatches("A fost {plecat) pentru (puțin timp}.", 4);
    }

    @Test
    public void testRomanianRule_9() throws IOException {
        assertMatches("A fost plecat „pentru... puțin timp.", 1);
    }

    @Test
    public void testRomanianRule_10() throws IOException {
        assertMatches("A fost plecat «puțin.", 1);
    }

    @Test
    public void testRomanianRule_11() throws IOException {
        assertMatches("A fost plecat „pentru «puțin timp”.", 1);
    }

    @Test
    public void testRomanianRule_12() throws IOException {
        assertMatches("A fost plecat „pentru puțin» timp”.", 1);
    }

    @Test
    public void testRomanianRule_13() throws IOException {
        assertMatches("A fost plecat „pentru... puțin» timp”.", 1);
    }

    @Test
    public void testRomanianRule_14() throws IOException {
        assertMatches("A fost plecat „pentru... «puțin” timp».", 4);
    }
}
