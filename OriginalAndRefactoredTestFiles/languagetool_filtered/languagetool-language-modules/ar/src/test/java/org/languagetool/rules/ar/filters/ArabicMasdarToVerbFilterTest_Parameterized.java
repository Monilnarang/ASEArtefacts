package org.languagetool.rules.ar.filters;

import org.junit.Test;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.rules.FakeRule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.SimpleReplaceDataLoader;
import org.languagetool.rules.patterns.RuleFilter;
import org.languagetool.tagging.ar.ArabicTagger;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArabicMasdarToVerbFilterTest_Parameterized {

    private final RuleMatch match = new RuleMatch(new FakeRule(), null, 0, 10, "message");

    private final RuleFilter filter = new ArabicMasdarToVerbFilter();

    private final ArabicTagger tagger = new ArabicTagger();

    private static final String FILE_NAME = "/ar/arabic_masdar_verb.txt";

    final boolean debug = false;

    private void assertSuggestion(String word, String expectedSuggestion) throws IOException {
        String word1 = "يقوم";
        String word2 = "بال" + word;
        Map<String, String> args = new HashMap<>();
        args.put("verb", word1);
        args.put("noun", word2);
        List<AnalyzedTokenReadings> patternTokens = tagger.tag(asList(word1, word2));
        AnalyzedTokenReadings[] patternTokensArray = patternTokens.toArray(new AnalyzedTokenReadings[0]);
        RuleMatch ruleMatch = filter.acceptRuleMatch(match, args, -1, patternTokensArray, null);
        assertThat(ruleMatch, notNullValue());
        assertThat(ruleMatch.getSuggestedReplacements().size(), is(1));
        assertThat(ruleMatch.getSuggestedReplacements().get(0), is(expectedSuggestion));
    }

    protected static Map<String, List<String>> loadFromPath(String path) {
        Map<String, List<String>> list = new SimpleReplaceDataLoader().loadWords(path);
        return list;
    }

    @ParameterizedTest
    @MethodSource("Provider_testFilter_1to4")
    public void testFilter_1to4(String param1, String param2) throws IOException {
        assertSuggestion(param1, param2);
    }

    static public Stream<Arguments> Provider_testFilter_1to4() {
        return Stream.of(arguments("عمل", "يعمل"), arguments("إعمال", "يعمل"), arguments("سؤال", "يسأل"), arguments("أكل", "يأكل"));
    }
}
