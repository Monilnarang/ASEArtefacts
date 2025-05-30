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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CompoundCoherencyRuleTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to9")
    public void testRule_1to9(String param1, String param2) throws IOException {
        assertOkay(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_1to9() {
        return Stream.of(arguments("Ein Jugendfoto.", "Und ein Jugendfoto."), arguments("Ein Jugendfoto.", "Der Rahmen eines Jugendfotos."), arguments("Der Rahmen eines Jugendfotos.", "Ein Jugendfoto."), arguments("Der Zahn-Ärzte-Verband.", "Der Zahn-Ärzte-Verband."), arguments("Der Zahn-Ärzte-Verband.", "Des Zahn-Ärzte-Verbands."), arguments("Der Zahn-Ärzte-Verband.", "Des Zahn-Ärzte-Verbandes."), arguments("Es gibt E-Mail.", "Und es gibt E-Mails."), arguments("Es gibt E-Mails.", "Und es gibt E-Mail."), arguments("Ein Jugend-Foto.", "Der Rahmen eines Jugend-Fotos."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_10to11_15to20")
    public void testRule_10to11_15to20(String param1, String param2, int param3, int param4, String param5) throws IOException {
        assertError(param1, param2, param3, param4, param5);
    }

    static public Stream<Arguments> Provider_testRule_10to11_15to20() {
        return Stream.of(arguments("Ein Jugendfoto.", "Und ein Jugend-Foto.", 23, 34, "Jugendfoto"), arguments("Ein Jugend-Foto.", "Und ein Jugendfoto.", 24, 34, "Jugend-Foto"), arguments("Der Zahn-Ärzte-Verband.", "Der Zahn-Ärzteverband.", 27, 44, "Zahn-Ärzte-Verband"), arguments("Der Zahn-Ärzte-Verband.", "Der Zahnärzte-Verband.", 27, 44, "Zahn-Ärzte-Verband"), arguments("Der Zahn-Ärzte-Verband.", "Der Zahnärzteverband.", 27, 43, "Zahn-Ärzte-Verband"), arguments("Der Zahn-Ärzteverband.", "Der Zahn-Ärzte-Verband.", 26, 44, "Zahn-Ärzteverband"), arguments("Der Zahnärzte-Verband.", "Der Zahn-Ärzte-Verband.", 26, 44, "Zahnärzte-Verband"), arguments("Der Zahnärzteverband.", "Der Zahn-Ärzte-Verband.", 25, 43, "Zahnärzteverband"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_12to14_21to23")
    public void testRule_12to14_21to23(String param1, String param2, int param3, int param4) throws IOException {
        assertError(param1, param2, param3, param4, null);
    }

    static public Stream<Arguments> Provider_testRule_12to14_21to23() {
        return Stream.of(arguments("Viele Zahn-Ärzte.", "Oder Zahnärzte.", 22, 31), arguments("Viele Zahn-Ärzte.", "Oder Zahnärzte.", 22, 31), arguments("Viele Zahn-Ärzte.", "Oder Zahnärzten.", 22, 32), arguments("Der Zahn-Ärzte-Verband.", "Des Zahn-Ärzteverbandes.", 27, 46), arguments("Der Zahn-Ärzte-Verband.", "Des Zahnärzte-Verbandes.", 27, 46), arguments("Der Zahn-Ärzte-Verband.", "Des Zahnärzteverbandes.", 27, 45));
    }
}
