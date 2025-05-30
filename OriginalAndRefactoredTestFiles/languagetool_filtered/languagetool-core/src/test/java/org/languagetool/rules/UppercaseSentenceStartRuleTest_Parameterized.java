package org.languagetool.rules;

import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UppercaseSentenceStartRuleTest_Parameterized {

    private final UppercaseSentenceStartRule rule = new UppercaseSentenceStartRule(TestTools.getEnglishMessages(), TestTools.getDemoLanguage(), Example.wrong("<marker>a</marker> sentence."), Example.fixed("<marker>A</marker> sentence."));

    private final JLanguageTool lt = new JLanguageTool(TestTools.getDemoLanguage());

    private void assertGood(String s) throws IOException {
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        RuleMatch[] matches = rule.match(analyzedSentences);
        assertEquals(0, matches.length);
    }

    @Test
    public void testRule_15_testMerged_15() throws IOException {
        RuleMatch[] matches = rule.match(lt.analyzeText("this is a test sentence."));
        assertEquals(1, matches.length);
        assertEquals(0, matches[0].getFromPos());
        assertEquals(4, matches[0].getToPos());
        RuleMatch[] matches2 = rule.match(lt.analyzeText("this!"));
        assertEquals(1, matches2.length);
        assertEquals(0, matches2[0].getFromPos());
        assertEquals(4, matches2[0].getToPos());
        RuleMatch[] matches3 = rule.match(lt.analyzeText("'this is a sentence'."));
        assertEquals(1, matches3.length);
        RuleMatch[] matches4 = rule.match(lt.analyzeText("\"this is a sentence.\""));
        assertEquals(1, matches4.length);
        RuleMatch[] matches5 = rule.match(lt.analyzeText("„this is a sentence."));
        assertEquals(1, matches5.length);
        RuleMatch[] matches6 = rule.match(lt.analyzeText("«this is a sentence."));
        assertEquals(1, matches6.length);
        RuleMatch[] matches7 = rule.match(lt.analyzeText("‘this is a sentence."));
        assertEquals(1, matches7.length);
        RuleMatch[] matches8 = rule.match(lt.analyzeText("¿esto es una pregunta?"));
        assertEquals(1, matches8.length);
        RuleMatch[] matches9 = rule.match(lt.analyzeText("¿Esto es una pregunta? ¿y esto?"));
        assertEquals(1, matches9.length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to14")
    public void testRule_1to14(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to14() {
        return Stream.of(arguments("this"), arguments("a) This is a test sentence."), arguments("iv. This is a test sentence..."), arguments("\"iv. This is a test sentence...\""), arguments("»iv. This is a test sentence..."), arguments("This"), arguments("This is"), arguments("This is a test sentence"), arguments(""), arguments("http://www.languagetool.org"), arguments("eBay can be at sentence start in lowercase."), arguments("¿Esto es una pregunta?"), arguments("¿Esto es una pregunta?, ¿y esto?"), arguments("ø This is a test sentence with a wrong bullet character."));
    }
}
