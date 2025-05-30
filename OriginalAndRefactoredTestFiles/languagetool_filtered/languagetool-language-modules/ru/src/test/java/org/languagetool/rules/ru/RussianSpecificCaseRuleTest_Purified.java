package org.languagetool.rules.ru;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RussianSpecificCaseRuleTest_Purified {

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("ru"));

    private final RussianSpecificCaseRule rule = new RussianSpecificCaseRule(TestTools.getEnglishMessages());

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
        assertGood("Рытый Банк");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("Центральный банк РФ");
    }

    @Test
    public void testRule_3() throws IOException {
        assertBad("Рытый банк");
    }

    @Test
    public void testRule_4() throws IOException {
        assertBad("центральный банк РФ");
    }

    @Test
    public void testRule_5_testMerged_5() throws IOException {
        ;
        assertThat(matches1[0].getFromPos(), is(7));
        assertThat(matches1[0].getToPos(), is(17));
        assertThat(matches1[0].getSuggestedReplacements().toString(), is("[Air France]"));
        assertThat(matches1[0].getMessage(), is("Для специальных наименований используйте начальную заглавную букву."));
    }
}
