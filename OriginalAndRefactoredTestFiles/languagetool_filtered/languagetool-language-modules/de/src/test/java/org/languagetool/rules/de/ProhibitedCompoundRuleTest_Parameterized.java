package org.languagetool.rules.de;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.languagetool.TestTools.getEnglishMessages;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.languagemodel.LuceneLanguageModel;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.ngrams.FakeLanguageModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ProhibitedCompoundRuleTest_Parameterized {

    private final static Map<String, Integer> map = new HashMap<>();

    static {
        map.put("Mietauto", 100);
        map.put("Leerzeile", 100);
        map.put("Urberliner", 100);
        map.put("Ureinwohner", 100);
        map.put("Wohnungsleerstand", 50);
        map.put("Xliseihflehrstand", 50);
        map.put("Eisensande", 100);
        map.put("Eisenstange", 101);
    }

    private final JLanguageTool testLt = new JLanguageTool(Languages.getLanguageForShortCode("de-DE"));

    private final ProhibitedCompoundRule testRule = new ProhibitedCompoundRule(getEnglishMessages(), new FakeLanguageModel(map), null, testLt.getLanguage());

    void assertMatches(String input, int expectedMatches) throws IOException {
        assertMatches(input, expectedMatches, testRule, testLt);
    }

    void assertMatches(String input, int expectedMatches, ProhibitedCompoundRule rule, JLanguageTool lt) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        assertThat("Got matches: " + Arrays.toString(matches), matches.length, is(expectedMatches));
    }

    void assertMatches(String input, String expectedMarkedText, String expectedSuggestions) throws IOException {
        RuleMatch[] matches = testRule.match(testLt.getAnalyzedSentence(input));
        assertThat("Got matches: " + Arrays.toString(matches), matches.length, is(1));
        String markedText = input.substring(matches[0].getFromPos(), matches[0].getToPos());
        assertThat(markedText, is(expectedMarkedText));
        assertThat(matches[0].getSuggestedReplacements().toString(), is("[" + expectedSuggestions + "]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to4_6to7_10to11_14to15_21to24")
    public void testRule_1to4_6to7_10to11_14to15_21to24(String param1, String param2, String param3) throws IOException {
        assertMatches(param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testRule_1to4_6to7_10to11_14to15_21to24() {
        return Stream.of(arguments("Er ist Uhrberliner.", "Uhrberliner", "Urberliner"), arguments("Er ist Uhr-Berliner.", "Uhr-Berliner", "Urberliner"), arguments("Das ist ein Mitauto.", "Mitauto", "Mietauto"), arguments("Das ist ein Mit-Auto.", "Mit-Auto", "Mietauto"), arguments("Hier leben die Uhreinwohner.", "Uhreinwohner", "Ureinwohner"), arguments("Hier leben die Uhr-Einwohner.", "Uhr-Einwohner", "Ureinwohner"), arguments("Eine Lehrzeile einfügen.", "Lehrzeile", "Leerzeile"), arguments("Eine Lehr-Zeile einfügen.", "Lehr-Zeile", "Leerzeile"), arguments("Viel Wohnungslehrstand.", "Wohnungslehrstand", "Wohnungsleerstand"), arguments("Viel Wohnungs-Lehrstand.", "Wohnungs-Lehrstand", "Wohnungsleerstand"), arguments("Den Lehrzeile-Test einfügen.", "Lehrzeile", "Leerzeile"), arguments("Die Test-Lehrzeile einfügen.", "Lehrzeile", "Leerzeile"), arguments("Die Versuchs-Test-Lehrzeile einfügen.", "Lehrzeile", "Leerzeile"), arguments("Den Versuchs-Lehrzeile-Test einfügen.", "Lehrzeile", "Leerzeile"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_5_8to9_12to13_16to20")
    public void testRule_5_8to9_12to13_16to20(String param1, int param2) throws IOException {
        assertMatches(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_5_8to9_12to13_16to20() {
        return Stream.of(arguments("Das ist Herr Mitauto.", 0), arguments("Eine Leerzeile einfügen.", 0), arguments("Eine Leer-Zeile einfügen.", 0), arguments("Viel Wohnungsleerstand.", 0), arguments("Viel Wohnungs-Leerstand.", 0), arguments("Viel Xliseihfleerstand.", 0), arguments("Viel Xliseihflehrstand.", 0), arguments("Ein kosmografischer Test", 0), arguments("Ein Elektrokardiograph", 0), arguments("Die Elektrokardiographen", 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemoveHyphensAndAdaptCase_1_6")
    public void testRemoveHyphensAndAdaptCase_1_6(String param1) {
        assertNull(testRule.removeHyphensAndAdaptCase(param1));
    }

    static public Stream<Arguments> Provider_testRemoveHyphensAndAdaptCase_1_6() {
        return Stream.of(arguments("Marathonläuse"), arguments("S-Bahn"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemoveHyphensAndAdaptCase_2to5")
    public void testRemoveHyphensAndAdaptCase_2to5(String param1, String param2) {
        assertThat(testRule.removeHyphensAndAdaptCase(param1), is(param2));
    }

    static public Stream<Arguments> Provider_testRemoveHyphensAndAdaptCase_2to5() {
        return Stream.of(arguments("Marathon-Läuse", "Marathonläuse"), arguments("Marathon-Läuse-Test", "Marathonläusetest"), arguments("Marathon-läuse-test", "Marathonläusetest"), arguments("viele-Läuse-Test", "vieleläusetest"));
    }
}
