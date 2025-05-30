package org.languagetool.rules.be;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BelarusianSpecificCaseRuleTest_Purified {

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("be"));

    private final BelarusianSpecificCaseRule rule = new BelarusianSpecificCaseRule(TestTools.getEnglishMessages());

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
        assertGood("Беларуская Народная Рэспубліка");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("Папа Рымскі");
    }

    @Test
    public void testRule_3() throws IOException {
        assertBad("дзяржаўны сцяг Рэспублікі Беларусь");
    }

    @Test
    public void testRule_4() throws IOException {
        assertBad("вярхоўны суд рэспублікі беларусь");
    }

    @Test
    public void testRule_5_testMerged_5() throws IOException {
        ;
        assertThat(matches1[0].getFromPos(), is(15));
        assertThat(matches1[0].getToPos(), is(25));
        assertThat(matches1[0].getSuggestedReplacements().toString(), is("[Air France]"));
        assertThat(matches1[0].getMessage(), is("Уласныя імёны і назвы пішуцца з вялікай літары."));
    }
}
