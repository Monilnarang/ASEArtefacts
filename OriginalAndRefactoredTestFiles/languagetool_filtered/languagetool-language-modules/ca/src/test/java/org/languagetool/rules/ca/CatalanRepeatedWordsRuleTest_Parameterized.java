package org.languagetool.rules.ca;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.TextLevelRule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CatalanRepeatedWordsRuleTest_Parameterized {

    private TextLevelRule rule;

    private JLanguageTool lt;

    private Language lang;

    @Before
    public void setUp() {
        lang = Languages.getLanguageForShortCode("ca");
        lt = new JLanguageTool(lang);
        rule = new CatalanRepeatedWordsRule(TestTools.getMessages("ca"), lang);
    }

    private RuleMatch[] getRuleMatches(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        return rule.match(lt.analyzeText(sentences), aText);
    }

    private void assertCorrectText(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        RuleMatch[] matches = rule.match(lt.analyzeText(sentences), aText);
        assertEquals(0, matches.length);
    }

    @Test
    public void testRule_3_testMerged_3() throws IOException {
        RuleMatch[] matches = getRuleMatches("Realitzaven una cosa inesperada. Llavors en van realitzar una altra.");
        assertEquals(1, matches.length);
        assertEquals("fer", matches[0].getSuggestedReplacements().get(0));
        assertEquals("dur a terme", matches[0].getSuggestedReplacements().get(1));
        assertEquals("portar a cap", matches[0].getSuggestedReplacements().get(2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to2")
    public void testRule_1to2(String param1) throws IOException {
        assertCorrectText(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to2() {
        return Stream.of(arguments("Abans de fer això. Abans, va fer allò"), arguments("Tema 4: L'alta edat mitjana. Tema 5: La baixa edat mitjana."));
    }
}
