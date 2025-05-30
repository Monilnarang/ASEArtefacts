package org.languagetool.rules.en;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EnglishSpecificCaseRuleTest_Purified {

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("en"));

    private final EnglishSpecificCaseRule rule = new EnglishSpecificCaseRule(TestTools.getEnglishMessages());

    private void assertGood(String input) throws IOException {
        assertThat(rule.match(lt.getAnalyzedSentence(input)).length, is(0));
    }

    private RuleMatch[] assertBad(String input) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        assertThat(matches.length, is(1));
        return matches;
    }

    @Test
    public void testRule_1() throws IOException {
        assertGood("Harry Potter");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("I like Harry Potter.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertGood("I like HARRY POTTER.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertBad("harry potter");
    }

    @Test
    public void testRule_5() throws IOException {
        assertBad("harry Potter");
    }

    @Test
    public void testRule_6() throws IOException {
        assertBad("Harry potter");
    }

    @Test
    public void testRule_7_testMerged_7() throws IOException {
        ;
        assertThat(matches1[0].getFromPos(), is(7));
        assertThat(matches1[0].getToPos(), is(19));
        assertThat(matches1[0].getSuggestedReplacements().toString(), is("[Harry Potter]"));
        assertThat(matches1[0].getMessage(), is("If the term is a proper noun, use initial capitals."));
        assertThat(matches2[0].getMessage(), is("If the term is a proper noun, use the suggested capitalization."));
        assertThat(matches3[0].getFromPos(), is(7));
        assertThat(matches3[0].getToPos(), is(20));
    }
}
