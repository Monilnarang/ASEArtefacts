package org.languagetool.rules.en;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.Languages;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EnglishWrongWordInContextRuleTest_Parameterized {

    private JLanguageTool lt;

    private EnglishWrongWordInContextRule rule;

    @Before
    public void setUp() throws IOException {
        Language english = Languages.getLanguageForShortCode("en-US");
        lt = new JLanguageTool(english);
        rule = new EnglishWrongWordInContextRule(null, english);
    }

    private void assertGood(String sentence) throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence(sentence)).length);
    }

    private void assertBad(String sentence) throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence(sentence)).length);
    }

    @Test
    public void testRule_5() throws IOException {
        assertEquals("prescribed", rule.match(lt.getAnalyzedSentence("I have proscribed you a course of antibiotics."))[0].getSuggestedReplacements().get(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1_4_6_9to10_13to14_17to18_20_23to24")
    public void testRule_1_4_6_9to10_13to14_17to18_20_23to24(String param1) throws IOException {
        assertBad(param1);
    }

    static public Stream<Arguments> Provider_testRule_1_4_6_9to10_13to14_17to18_20_23to24() {
        return Stream.of(arguments("I have proscribed you a course of antibiotics."), arguments("Name one country that does not prescribe theft."), arguments("We know that heroine is highly addictive."), arguments("A heroin is the principal female character in a novel."), arguments("What a bazaar behavior!"), arguments("The Saturday morning bizarre is worth seeing even if you buy nothing."), arguments("The bridle party waited on the lawn."), arguments("Each rider used his own bridal."), arguments("They have some great deserts on this menu."), arguments("They have some great marble statutes."), arguments("Protons and neurons"), arguments("The plane taxied to the hanger."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_2to3_7to8_11to12_15to16_19_21to22_25")
    public void testRule_2to3_7to8_11to12_15to16_19_21to22_25(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testRule_2to3_7to8_11to12_15to16_19_21to22_25() {
        return Stream.of(arguments("I have prescribed you a course of antibiotics."), arguments("Name one country that does not proscribe theft."), arguments("He wrote about his addiction to heroin."), arguments("A heroine is the principal female character in a novel."), arguments("I bought these books at the church bazaar."), arguments("She has a bizarre haircut."), arguments("Forgo the champagne treatment a bridal boutique often provides."), arguments("He sat there holding his horse by the bridle."), arguments("They have some great desserts on this menu."), arguments("They have a great marble statue."), arguments("Protons and neutrons"), arguments("The plane taxied to the hangar."));
    }
}
