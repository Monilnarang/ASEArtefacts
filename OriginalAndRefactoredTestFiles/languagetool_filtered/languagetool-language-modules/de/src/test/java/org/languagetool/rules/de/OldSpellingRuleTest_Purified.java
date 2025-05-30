package org.languagetool.rules.de;

import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class OldSpellingRuleTest_Purified {

    private static final Language germanDE = Languages.getLanguageForShortCode("de-DE");

    private static final OldSpellingRule rule = new OldSpellingRule(JLanguageTool.getMessageBundle(), germanDE);

    private static final JLanguageTool lt = new JLanguageTool(germanDE);

    private void assertMatch(String input) throws IOException {
        RuleMatch[] match = rule.match(lt.getAnalyzedSentence(input));
        assertThat(match.length, is(1));
    }

    private void assertNoMatch(String input) throws IOException {
        RuleMatch[] match = rule.match(lt.getAnalyzedSentence(input));
        if (match.length > 0) {
            fail("Unexpected match for '" + input + "': " + match[0]);
        }
    }

    @Test
    public void test_1_testMerged_1() throws IOException {
        AnalyzedSentence sentence1 = lt.getAnalyzedSentence("Ein Kuß");
        assertThat(rule.match(sentence1).length, is(1));
        assertThat(rule.match(sentence1)[0].getSuggestedReplacements().toString(), is("[Kuss]"));
        AnalyzedSentence sentence2 = lt.getAnalyzedSentence("Das Corpus delicti");
        assertThat(rule.match(sentence2).length, is(1));
        assertThat(rule.match(sentence2)[0].getSuggestedReplacements().toString(), is("[Corpus Delicti]"));
        AnalyzedSentence sentence3 = lt.getAnalyzedSentence("In Rußlands Weiten");
        assertThat(rule.match(sentence3).length, is(1));
        assertThat(rule.match(sentence3)[0].getSuggestedReplacements().toString(), is("[Russlands]"));
        AnalyzedSentence sentence4 = lt.getAnalyzedSentence("Hot pants");
        assertThat(rule.match(sentence4).length, is(1));
        assertThat(rule.match(sentence4)[0].getSuggestedReplacements().toString(), is("[Hotpants]"));
        AnalyzedSentence sentence5 = lt.getAnalyzedSentence("Ich muß los");
        assertThat(rule.match(sentence5).length, is(1));
        assertThat(rule.match(sentence5)[0].getSuggestedReplacements().toString(), is("[muss]"));
        AnalyzedSentence sentence6 = lt.getAnalyzedSentence("schwarzweißmalen");
        assertThat(rule.match(sentence6).length, is(1));
        assertThat(rule.match(sentence6)[0].getSuggestedReplacements().toString(), is("[schwarzweiß malen, schwarz-weiß malen]"));
        assertThat(rule.match(lt.getAnalyzedSentence("geschneuzt"))[0].getSuggestedReplacements().toString(), is("[geschnäuzt]"));
        assertThat(rule.match(lt.getAnalyzedSentence("naß machen"))[0].getSuggestedReplacements().toString(), is("[nassmachen]"));
        assertThat(rule.match(lt.getAnalyzedSentence("Midlife-crisis"))[0].getSuggestedReplacements().toString(), is("[Midlife-Crisis, Midlifecrisis]"));
        assertThat(rule.match(lt.getAnalyzedSentence("Schluß"))[0].getSuggestedReplacements().toString(), is("[Schluss]"));
        assertThat(rule.match(lt.getAnalyzedSentence("schluß")).length, is(0));
        assertThat(rule.match(lt.getAnalyzedSentence("Schloß"))[0].getSuggestedReplacements().toString(), is("[Schloss]"));
        assertThat(rule.match(lt.getAnalyzedSentence("radfahren"))[0].getSuggestedReplacements().toString(), is("[Rad fahren]"));
        assertThat(rule.match(lt.getAnalyzedSentence("Photo"))[0].getSuggestedReplacements().toString(), is("[Foto]"));
        assertThat(rule.match(lt.getAnalyzedSentence("Geschoß"))[0].getSuggestedReplacements().toString(), is("[Geschoss]"));
        assertThat(rule.match(lt.getAnalyzedSentence("Erdgeschoß"))[0].getSuggestedReplacements().toString(), is("[Erdgeschoss]"));
        assertThat(rule.match(lt.getAnalyzedSentence("Erdgeschoßes"))[0].getSuggestedReplacements().toString(), is("[Erdgeschosses]"));
    }

    @Test
    public void test_24() throws IOException {
        assertNoMatch("In Russland");
    }

    @Test
    public void test_25() throws IOException {
        assertNoMatch("In Russlands Weiten");
    }

    @Test
    public void test_26() throws IOException {
        assertNoMatch("Schlüsse");
    }

    @Test
    public void test_27() throws IOException {
        assertNoMatch("Schloß Holte");
    }

    @Test
    public void test_28() throws IOException {
        assertNoMatch("in Schloß Holte");
    }

    @Test
    public void test_29() throws IOException {
        assertNoMatch("Schloß Holte ist");
    }

    @Test
    public void test_30() throws IOException {
        assertNoMatch("Asse");
    }

    @Test
    public void test_31() throws IOException {
        assertNoMatch("Photons");
    }

    @Test
    public void test_32() throws IOException {
        assertNoMatch("Photon");
    }

    @Test
    public void test_33() throws IOException {
        assertNoMatch("Des Photons");
    }

    @Test
    public void test_34() throws IOException {
        assertNoMatch("Photons ");
    }

    @Test
    public void test_35() throws IOException {
        assertNoMatch("Hallo Herr Naß");
    }

    @Test
    public void test_36() throws IOException {
        assertNoMatch("Hallo Hr. Naß");
    }

    @Test
    public void test_37() throws IOException {
        assertNoMatch("Hallo Frau Naß");
    }

    @Test
    public void test_38() throws IOException {
        assertNoMatch("Hallo Fr. Naß");
    }

    @Test
    public void test_39() throws IOException {
        assertNoMatch("Fr. Naß");
    }

    @Test
    public void test_40() throws IOException {
        assertNoMatch("Dr. Naß");
    }

    @Test
    public void test_41() throws IOException {
        assertNoMatch("Prof. Naß");
    }

    @Test
    public void test_42() throws IOException {
        assertNoMatch("Bell Telephone");
    }

    @Test
    public void test_43() throws IOException {
        assertNoMatch("Telephone Company");
    }

    @Test
    public void test_44() throws IOException {
        assertMatch("Naß ist das Wasser");
    }

    @Test
    public void test_45() throws IOException {
        assertMatch("Läßt du das bitte");
    }

    @Test
    public void test_46() throws IOException {
        assertNoMatch("Das mögliche Bestehenbleiben");
    }

    @Test
    public void test_47() throws IOException {
        assertNoMatch("Das mögliche Bloßstrampeln verhindern.");
    }

    @Test
    public void test_48() throws IOException {
        assertMatch("Bloßstrampeln konnte er sich nicht.");
    }
}
