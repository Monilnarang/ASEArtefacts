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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OldSpellingRuleTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_test_24to43_46to47")
    public void test_24to43_46to47(String param1) throws IOException {
        assertNoMatch(param1);
    }

    static public Stream<Arguments> Provider_test_24to43_46to47() {
        return Stream.of(arguments("In Russland"), arguments("In Russlands Weiten"), arguments("Schlüsse"), arguments("Schloß Holte"), arguments("in Schloß Holte"), arguments("Schloß Holte ist"), arguments("Asse"), arguments("Photons"), arguments("Photon"), arguments("Des Photons"), arguments("Photons "), arguments("Hallo Herr Naß"), arguments("Hallo Hr. Naß"), arguments("Hallo Frau Naß"), arguments("Hallo Fr. Naß"), arguments("Fr. Naß"), arguments("Dr. Naß"), arguments("Prof. Naß"), arguments("Bell Telephone"), arguments("Telephone Company"), arguments("Das mögliche Bestehenbleiben"), arguments("Das mögliche Bloßstrampeln verhindern."));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_44to45_48")
    public void test_44to45_48(String param1) throws IOException {
        assertMatch(param1);
    }

    static public Stream<Arguments> Provider_test_44to45_48() {
        return Stream.of(arguments("Naß ist das Wasser"), arguments("Läßt du das bitte"), arguments("Bloßstrampeln konnte er sich nicht."));
    }
}
