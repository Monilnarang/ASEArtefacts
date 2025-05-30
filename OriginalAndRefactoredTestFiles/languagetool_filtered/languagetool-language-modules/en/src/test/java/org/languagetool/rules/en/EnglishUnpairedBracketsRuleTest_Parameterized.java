package org.languagetool.rules.en;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.TextLevelRule;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EnglishUnpairedBracketsRuleTest_Parameterized {

    private TextLevelRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new EnglishUnpairedBracketsRule(TestTools.getEnglishMessages(), Languages.getLanguageForShortCode("en"));
        lt = new JLanguageTool(Languages.getLanguageForShortCode("en"));
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(sentence)));
        assertEquals(0, matches.length);
    }

    private void assertCorrectText(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        RuleMatch[] matches = rule.match(lt.analyzeText(sentences), aText);
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(sentence)));
        assertEquals(1, matches.length);
    }

    private int getMatches(String input, JLanguageTool lt) throws IOException {
        return lt.check(input).size();
    }

    @Test
    public void testRule_24() throws IOException {
        assertCorrectText("\n\n" + "A) New York\n" + "B) Boston\n" + "C) Foo\n");
    }

    @Test
    public void testRule_32_testMerged_32() throws IOException {
        RuleMatch[] matches;
        matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence("(This is a test] sentence.")));
        assertEquals(2, matches.length);
        matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence("This [is (a test} sentence.")));
        assertEquals(3, matches.length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to20_26_30")
    public void testRule_1to20_26_30(String param1) throws IOException {
        assertCorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to20_26_30() {
        return Stream.of(arguments("(This is a test sentence)."), arguments("This is a word 'test'."), arguments("This is no smiley: (some more text)"), arguments("This is a sentence with a smiley :)"), arguments("This is a sentence with a smiley :("), arguments("This is a sentence with a smiley :-)"), arguments("This is a sentence with a smiley ;-) and so on..."), arguments("This is a [test] sentence..."), arguments("The plight of Tamil refugees caused a surge of support from most of the Tamil political parties.[90]"), arguments("(([20] [20] [20]))"), arguments("This is a \"special test\", right?"), arguments("We discussed this in Chapter 1)."), arguments("The jury recommended that: (1) Four additional deputies be employed."), arguments("We discussed this in section 1a)."), arguments("We discussed this in section iv)."), arguments("(Ketab fi Isti'mal al-'Adad al-Hindi)"), arguments("(al-'Adad al-Hindi)"), arguments("will-o'-the-wisp"), arguments("cat-o’-nine-tails"), arguments("a) item one\nb) item two\nc) item three"), arguments("This is not so (neither a nor b"), arguments("Some text (and some funny remark :-) with more text to follow"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_21to23")
    public void testRule_21to23(String param1, String param2, String param3) throws IOException {
        assertCorrectText(param2 + param3 + param1);
    }

    static public Stream<Arguments> Provider_testRule_21to23() {
        return Stream.of(arguments("b) Boston\n", "\n\n", "a) New York\n"), arguments("2.) Boston\n", "\n\n", "1.) New York\n"), arguments("XIII.) Boston\n", "\n\n", "XII.) New York\n"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_25_27to29_31")
    public void testRule_25_27to29_31(String param1) throws IOException {
        assertIncorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_25_27to29_31() {
        return Stream.of(arguments("(This is a test sentence."), arguments("This is not so (neither a nor b."), arguments("This is not so neither a nor b)"), arguments("This is not so neither foo nor bar)"), arguments("Some text (and some funny remark :-) with more text to follow!"));
    }
}
