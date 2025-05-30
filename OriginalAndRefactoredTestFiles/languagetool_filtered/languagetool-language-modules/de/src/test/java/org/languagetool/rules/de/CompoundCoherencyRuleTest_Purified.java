package org.languagetool.rules.de;

import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.Arrays;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CompoundCoherencyRuleTest_Purified {

    private final CompoundCoherencyRule rule = new CompoundCoherencyRule(TestTools.getEnglishMessages());

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("de"));

    private void assertOkay(String s1, String s2) throws IOException {
        RuleMatch[] matches = getMatches(s1, s2);
        assertThat("Got " + Arrays.toString(matches), matches.length, is(0));
    }

    private void assertError(String s1, String s2, int fromPos, int toPos, String suggestion) throws IOException {
        RuleMatch[] matches = getMatches(s1, s2);
        assertThat("Got " + Arrays.toString(matches), matches.length, is(1));
        assertThat(matches[0].getFromPos(), is(fromPos));
        assertThat(matches[0].getToPos(), is(toPos));
        if (suggestion == null) {
            assertThat("Did not expect suggestion, but got: " + matches[0].getSuggestedReplacements(), matches[0].getSuggestedReplacements().size(), is(0));
        } else {
            assertThat("Expected suggestion: " + suggestion + ", got: " + matches[0].getSuggestedReplacements(), matches[0].getSuggestedReplacements(), is(Arrays.asList(suggestion)));
        }
    }

    private RuleMatch[] getMatches(String s1, String s2) throws IOException {
        AnalyzedSentence sent1 = lt.getAnalyzedSentence(s1);
        AnalyzedSentence sent2 = lt.getAnalyzedSentence(s2);
        return rule.match(Arrays.asList(sent1, sent2));
    }

    @Test
    public void testRule_1() throws IOException {
        assertOkay("Ein Jugendfoto.", "Und ein Jugendfoto.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertOkay("Ein Jugendfoto.", "Der Rahmen eines Jugendfotos.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertOkay("Der Rahmen eines Jugendfotos.", "Ein Jugendfoto.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertOkay("Der Zahn-Ärzte-Verband.", "Der Zahn-Ärzte-Verband.");
    }

    @Test
    public void testRule_5() throws IOException {
        assertOkay("Der Zahn-Ärzte-Verband.", "Des Zahn-Ärzte-Verbands.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertOkay("Der Zahn-Ärzte-Verband.", "Des Zahn-Ärzte-Verbandes.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertOkay("Es gibt E-Mail.", "Und es gibt E-Mails.");
    }

    @Test
    public void testRule_8() throws IOException {
        assertOkay("Es gibt E-Mails.", "Und es gibt E-Mail.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertOkay("Ein Jugend-Foto.", "Der Rahmen eines Jugend-Fotos.");
    }

    @Test
    public void testRule_10() throws IOException {
        assertError("Ein Jugendfoto.", "Und ein Jugend-Foto.", 23, 34, "Jugendfoto");
    }

    @Test
    public void testRule_11() throws IOException {
        assertError("Ein Jugend-Foto.", "Und ein Jugendfoto.", 24, 34, "Jugend-Foto");
    }

    @Test
    public void testRule_12() throws IOException {
        assertError("Viele Zahn-Ärzte.", "Oder Zahnärzte.", 22, 31, null);
    }

    @Test
    public void testRule_13() throws IOException {
        assertError("Viele Zahn-Ärzte.", "Oder Zahnärzte.", 22, 31, null);
    }

    @Test
    public void testRule_14() throws IOException {
        assertError("Viele Zahn-Ärzte.", "Oder Zahnärzten.", 22, 32, null);
    }

    @Test
    public void testRule_15() throws IOException {
        assertError("Der Zahn-Ärzte-Verband.", "Der Zahn-Ärzteverband.", 27, 44, "Zahn-Ärzte-Verband");
    }

    @Test
    public void testRule_16() throws IOException {
        assertError("Der Zahn-Ärzte-Verband.", "Der Zahnärzte-Verband.", 27, 44, "Zahn-Ärzte-Verband");
    }

    @Test
    public void testRule_17() throws IOException {
        assertError("Der Zahn-Ärzte-Verband.", "Der Zahnärzteverband.", 27, 43, "Zahn-Ärzte-Verband");
    }

    @Test
    public void testRule_18() throws IOException {
        assertError("Der Zahn-Ärzteverband.", "Der Zahn-Ärzte-Verband.", 26, 44, "Zahn-Ärzteverband");
    }

    @Test
    public void testRule_19() throws IOException {
        assertError("Der Zahnärzte-Verband.", "Der Zahn-Ärzte-Verband.", 26, 44, "Zahnärzte-Verband");
    }

    @Test
    public void testRule_20() throws IOException {
        assertError("Der Zahnärzteverband.", "Der Zahn-Ärzte-Verband.", 25, 43, "Zahnärzteverband");
    }

    @Test
    public void testRule_21() throws IOException {
        assertError("Der Zahn-Ärzte-Verband.", "Des Zahn-Ärzteverbandes.", 27, 46, null);
    }

    @Test
    public void testRule_22() throws IOException {
        assertError("Der Zahn-Ärzte-Verband.", "Des Zahnärzte-Verbandes.", 27, 46, null);
    }

    @Test
    public void testRule_23() throws IOException {
        assertError("Der Zahn-Ärzte-Verband.", "Des Zahnärzteverbandes.", 27, 45, null);
    }
}
