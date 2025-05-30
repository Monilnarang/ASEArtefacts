package org.languagetool.rules.el;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GreekSpecificCaseRuleTest_Purified {

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("el"));

    private final GreekSpecificCaseRule rule = new GreekSpecificCaseRule(TestTools.getEnglishMessages());

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
        assertGood("Ηνωμένες Πολιτείες");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("Κατοικώ στις Ηνωμένες Πολιτείες.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertGood("Κατοικώ στις ΗΝΩΜΕΝΕΣ ΠΟΛΙΤΕΙΕΣ.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertBad("ηνωμένες πολιτείες");
    }

    @Test
    public void testRule_5() throws IOException {
        assertBad("ηνωμένες Πολιτείες");
    }

    @Test
    public void testRule_6() throws IOException {
        assertBad("Ηνωμένες πολιτείες");
    }

    @Test
    public void testRule_7_testMerged_7() throws IOException {
        ;
        assertThat(matches1[0].getFromPos(), is(13));
        assertThat(matches1[0].getToPos(), is(31));
        assertThat(matches1[0].getSuggestedReplacements().toString(), is("[Ηνωμένες Πολιτείες]"));
        assertThat(matches1[0].getMessage(), is("Οι λέξεις της συγκεκριμένης έκφρασης χρείαζεται να ξεκινούν με κεφαλαία γράμματα."));
        assertThat(matches3[0].getFromPos(), is(13));
        assertThat(matches3[0].getToPos(), is(32));
    }
}
