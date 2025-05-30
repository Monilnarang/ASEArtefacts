package org.languagetool.rules.en;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.WordRepeatRule;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WordRepeatRuleTest_Parameterized {

    private final Language english = Languages.getLanguageForShortCode("en");

    private final WordRepeatRule rule = new WordRepeatRule(TestTools.getEnglishMessages(), english);

    private final JLanguageTool lt = new JLanguageTool(english);

    private void assertMatches(String input, int expectedMatches) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        assertEquals(expectedMatches, matches.length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to6")
    public void testRule_1to6(String param1, int param2) throws IOException {
        assertMatches(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_1to6() {
        return Stream.of(arguments("This is a test sentence.", 0), arguments("This is a test sentence...", 0), arguments("And side to side and top to bottom...", 0), arguments("This this is a test sentence.", 1), arguments("This is a test sentence sentence.", 1), arguments("This is is a a test sentence sentence.", 3));
    }
}
