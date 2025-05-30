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

public class ProhibitedCompoundRuleTest_Purified {

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

    @Test
    public void testRule_1() throws IOException {
        assertMatches("Er ist Uhrberliner.", "Uhrberliner", "Urberliner");
    }

    @Test
    public void testRule_2() throws IOException {
        assertMatches("Er ist Uhr-Berliner.", "Uhr-Berliner", "Urberliner");
    }

    @Test
    public void testRule_3() throws IOException {
        assertMatches("Das ist ein Mitauto.", "Mitauto", "Mietauto");
    }

    @Test
    public void testRule_4() throws IOException {
        assertMatches("Das ist ein Mit-Auto.", "Mit-Auto", "Mietauto");
    }

    @Test
    public void testRule_5() throws IOException {
        assertMatches("Das ist Herr Mitauto.", 0);
    }

    @Test
    public void testRule_6() throws IOException {
        assertMatches("Hier leben die Uhreinwohner.", "Uhreinwohner", "Ureinwohner");
    }

    @Test
    public void testRule_7() throws IOException {
        assertMatches("Hier leben die Uhr-Einwohner.", "Uhr-Einwohner", "Ureinwohner");
    }

    @Test
    public void testRule_8() throws IOException {
        assertMatches("Eine Leerzeile einfügen.", 0);
    }

    @Test
    public void testRule_9() throws IOException {
        assertMatches("Eine Leer-Zeile einfügen.", 0);
    }

    @Test
    public void testRule_10() throws IOException {
        assertMatches("Eine Lehrzeile einfügen.", "Lehrzeile", "Leerzeile");
    }

    @Test
    public void testRule_11() throws IOException {
        assertMatches("Eine Lehr-Zeile einfügen.", "Lehr-Zeile", "Leerzeile");
    }

    @Test
    public void testRule_12() throws IOException {
        assertMatches("Viel Wohnungsleerstand.", 0);
    }

    @Test
    public void testRule_13() throws IOException {
        assertMatches("Viel Wohnungs-Leerstand.", 0);
    }

    @Test
    public void testRule_14() throws IOException {
        assertMatches("Viel Wohnungslehrstand.", "Wohnungslehrstand", "Wohnungsleerstand");
    }

    @Test
    public void testRule_15() throws IOException {
        assertMatches("Viel Wohnungs-Lehrstand.", "Wohnungs-Lehrstand", "Wohnungsleerstand");
    }

    @Test
    public void testRule_16() throws IOException {
        assertMatches("Viel Xliseihfleerstand.", 0);
    }

    @Test
    public void testRule_17() throws IOException {
        assertMatches("Viel Xliseihflehrstand.", 0);
    }

    @Test
    public void testRule_18() throws IOException {
        assertMatches("Ein kosmografischer Test", 0);
    }

    @Test
    public void testRule_19() throws IOException {
        assertMatches("Ein Elektrokardiograph", 0);
    }

    @Test
    public void testRule_20() throws IOException {
        assertMatches("Die Elektrokardiographen", 0);
    }

    @Test
    public void testRule_21() throws IOException {
        assertMatches("Den Lehrzeile-Test einfügen.", "Lehrzeile", "Leerzeile");
    }

    @Test
    public void testRule_22() throws IOException {
        assertMatches("Die Test-Lehrzeile einfügen.", "Lehrzeile", "Leerzeile");
    }

    @Test
    public void testRule_23() throws IOException {
        assertMatches("Die Versuchs-Test-Lehrzeile einfügen.", "Lehrzeile", "Leerzeile");
    }

    @Test
    public void testRule_24() throws IOException {
        assertMatches("Den Versuchs-Lehrzeile-Test einfügen.", "Lehrzeile", "Leerzeile");
    }

    @Test
    public void testRemoveHyphensAndAdaptCase_1() {
        assertNull(testRule.removeHyphensAndAdaptCase("Marathonläuse"));
    }

    @Test
    public void testRemoveHyphensAndAdaptCase_2() {
        assertThat(testRule.removeHyphensAndAdaptCase("Marathon-Läuse"), is("Marathonläuse"));
    }

    @Test
    public void testRemoveHyphensAndAdaptCase_3() {
        assertThat(testRule.removeHyphensAndAdaptCase("Marathon-Läuse-Test"), is("Marathonläusetest"));
    }

    @Test
    public void testRemoveHyphensAndAdaptCase_4() {
        assertThat(testRule.removeHyphensAndAdaptCase("Marathon-läuse-test"), is("Marathonläusetest"));
    }

    @Test
    public void testRemoveHyphensAndAdaptCase_5() {
        assertThat(testRule.removeHyphensAndAdaptCase("viele-Läuse-Test"), is("vieleläusetest"));
    }

    @Test
    public void testRemoveHyphensAndAdaptCase_6() {
        assertNull(testRule.removeHyphensAndAdaptCase("S-Bahn"));
    }
}
