package org.languagetool.rules.de;

import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.*;
import org.languagetool.rules.FakeRule;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AdaptSuggestionFilterTest_Parameterized {

    private final AdaptSuggestionFilter filter = new AdaptSuggestionFilter();

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("de"));

    private void runAcceptRuleMatch(String sentenceStr, String word, String origReplacement, String newReplacements) throws IOException {
        AnalyzedSentence sentence = lt.getAnalyzedSentence(sentenceStr);
        int fromPos = sentenceStr.indexOf(word);
        int toPos = fromPos + word.length();
        RuleMatch match = new RuleMatch(new FakeRule(), sentence, fromPos, toPos, "fake message");
        match.setSuggestedReplacement(origReplacement);
        int tokenPos = -1;
        int i = 0;
        for (AnalyzedTokenReadings tokens : sentence.getTokensWithoutWhitespace()) {
            if (tokens.getToken().equals(word)) {
                tokenPos = i;
                break;
            }
            i++;
        }
        if (i == -1) {
            throw new RuntimeException("Word '" + word + "' not found in sentence: '" + sentenceStr + "'");
        }
        Map<String, String> map = new HashMap<>();
        map.put("sub", "\\1");
        RuleMatch newMatch = filter.acceptRuleMatch(match, map, i, Arrays.copyOfRange(sentence.getTokensWithoutWhitespace(), tokenPos, tokenPos + 1), null);
        assertNotNull(newMatch);
        assertThat(newMatch.getSuggestedReplacements().toString(), is(newReplacements));
    }

    private void assertDet(AnalyzedToken detToken, String replWord, String expectedDet) {
        List<String> adaptedDet = filter.getAdaptedDet(new AnalyzedTokenReadings(detToken, 0), replWord);
        assertThat(adaptedDet.toString(), is(expectedDet));
    }

    private void assertDetAdj(AnalyzedToken detToken, AnalyzedToken adjToken, String replWord, String expectedDet) {
        List<String> adaptedDet = filter.getAdaptedDetAdj(new AnalyzedTokenReadings(detToken, 0), new AnalyzedTokenReadings(adjToken, 0), replWord);
        assertThat(adaptedDet.toString(), is(expectedDet));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAdaptedDet_1to7")
    public void testAdaptedDet_1to7(String param1, String param2, String param3, String param4, String param5) {
        assertDet(new AnalyzedToken(param3, param4, param5), param1, param2);
    }

    static public Stream<Arguments> Provider_testAdaptedDet_1to7() {
        return Stream.of(arguments("Mann", "[der]", "die", "ART:DEF:NOM:SIN:FEM", "der"), arguments("Frau", "[die]", "der", "ART:DEF:NOM:SIN:MAS", "der"), arguments("Kind", "[das]", "der", "ART:DEF:NOM:SIN:NEU", "der"), arguments("Plan", "[ein]", "eine", "ART:IND:NOM:SIN:FEM", "ein"), arguments("Plan", "[einen]", "eine", "ART:IND:AKK:SIN:FEM", "ein"), arguments("Plan", "[eines]", "einer", "ART:IND:GEN:SIN:FEM", "ein"), arguments("Plan", "[einem]", "einer", "ART:IND:DAT:SIN:FEM", "ein"));
    }
}
