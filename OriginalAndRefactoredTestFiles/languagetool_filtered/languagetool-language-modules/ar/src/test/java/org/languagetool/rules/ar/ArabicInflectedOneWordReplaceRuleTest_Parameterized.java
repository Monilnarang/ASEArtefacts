package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArabicInflectedOneWordReplaceRuleTest_Parameterized {

    private ArabicInflectedOneWordReplaceRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new ArabicInflectedOneWordReplaceRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Languages.getLanguageForShortCode("ar"));
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertNotEquals(matches.length, 0);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to2")
    public void testRule_1to2(String param1) throws IOException {
        assertCorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to2() {
        return Stream.of(arguments("أجريت بحوثا في المخبر"), arguments("وجعل لكم من أزواجكم بنين وحفدة"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_3to4")
    public void testRule_3to4(String param1) throws IOException {
        assertIncorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_3to4() {
        return Stream.of(arguments("أجريت أبحاثا في المخبر"), arguments("وجعل لكم من أزواجكم بنين وأحفاد"));
    }
}
