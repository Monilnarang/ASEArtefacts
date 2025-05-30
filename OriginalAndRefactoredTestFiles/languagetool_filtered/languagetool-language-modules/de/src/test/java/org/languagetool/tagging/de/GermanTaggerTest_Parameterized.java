package org.languagetool.tagging.de;

import morfologik.stemming.Dictionary;
import morfologik.stemming.DictionaryLookup;
import morfologik.stemming.WordData;
import org.junit.Test;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import java.io.IOException;
import java.util.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("ConstantConditions")
public class GermanTaggerTest_Parameterized {

    private final GermanTagger tagger = new GermanTagger();

    public static String toSortedString(AnalyzedTokenReadings tokenReadings) {
        StringBuilder sb = new StringBuilder(tokenReadings.getToken());
        Set<String> elements = new TreeSet<>();
        sb.append('[');
        for (AnalyzedToken reading : tokenReadings) {
            elements.add(reading.toString());
        }
        sb.append(String.join(", ", elements));
        sb.append(']');
        return sb.toString();
    }

    @Test
    public void testAdjectivesFromSpellingTxt_2() throws IOException {
        assertEquals("abgemindertes[abgemindert/PA2:AKK:SIN:NEU:GRU:IND:VER, " + "abgemindert/PA2:AKK:SIN:NEU:GRU:SOL:VER, abgemindert/PA2:NOM:SIN:NEU:GRU:IND:VER, " + "abgemindert/PA2:NOM:SIN:NEU:GRU:SOL:VER]", toSortedString(tagger.lookup("abgemindertes")));
    }

    @Test
    public void testAdjectivesFromSpellingTxt_3() throws IOException {
        assertNull(tagger.lookup("fünftjüngste"));
    }

    @Test
    public void testAdjectivesFromSpellingTxt_4() throws IOException {
        assertEquals("meistgewünschtes[meistgewünscht/ADJ:AKK:SIN:NEU:GRU:IND, meistgewünscht/ADJ:AKK:SIN:NEU:GRU:SOL, " + "meistgewünscht/ADJ:NOM:SIN:NEU:GRU:IND, meistgewünscht/ADJ:NOM:SIN:NEU:GRU:SOL]", toSortedString(tagger.lookup("meistgewünschtes")));
    }

    @Test
    public void testAdjectivesFromSpellingTxt_5() throws IOException {
        assertEquals("meistgewünschter[meistgewünscht/ADJ:DAT:SIN:FEM:GRU:SOL, meistgewünscht/ADJ:GEN:PLU:FEM:GRU:SOL, " + "meistgewünscht/ADJ:GEN:PLU:MAS:GRU:SOL, meistgewünscht/ADJ:GEN:PLU:NEU:GRU:SOL, " + "meistgewünscht/ADJ:GEN:SIN:FEM:GRU:SOL, meistgewünscht/ADJ:NOM:SIN:MAS:GRU:IND, " + "meistgewünscht/ADJ:NOM:SIN:MAS:GRU:SOL]", toSortedString(tagger.lookup("meistgewünschter")));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAdjectivesFromSpellingTxt_1_1to4_6")
    public void testAdjectivesFromSpellingTxt_1_1to4_6(String param1, String param2) throws IOException {
        assertEquals(param1, toSortedString(tagger.lookup(param2)));
    }

    static public Stream<Arguments> Provider_testAdjectivesFromSpellingTxt_1_1to4_6() {
        return Stream.of(arguments("abgemindert[abgemindert/PA2:PRD:GRU:VER]", "abgemindert"), arguments("meistgewünscht[meistgewünscht/ADJ:PRD:GRU]", "meistgewünscht"), arguments("Kuß[Kuß/SUB:AKK:SIN:MAS, Kuß/SUB:DAT:SIN:MAS, Kuß/SUB:NOM:SIN:MAS]", "Kuß"), arguments("Kuss[Kuss/SUB:AKK:SIN:MAS, Kuss/SUB:DAT:SIN:MAS, Kuss/SUB:NOM:SIN:MAS]", "Kuss"), arguments("Haß[Haß/SUB:AKK:SIN:MAS, Haß/SUB:DAT:SIN:MAS, Haß/SUB:NOM:SIN:MAS]", "Haß"), arguments("Hass[Hass/SUB:AKK:SIN:MAS, Hass/SUB:DAT:SIN:MAS, Hass/SUB:NOM:SIN:MAS]", "Hass"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGenderGap_1to4")
    public void testGenderGap_1to4(String param1, int param2, String param3, String param4, String param5, String param6) throws IOException {
        assertTrue(tagger.tag(Arrays.asList(param3, param4, param5, param6)).get(param2).hasPartialPosTag(param1));
    }

    static public Stream<Arguments> Provider_testGenderGap_1to4() {
        return Stream.of(arguments(":PLU:FEM", 1, "viele", "Freund", "*", "innen"), arguments(":PLU:FEM", 1, "viele", "Freund", "_", "innen"), arguments(":PLU:FEM", 1, "viele", "Freund", ":", "innen"), arguments(":PLU:FEM", 1, "viele", "Freund", "/", "innen"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGenderGap_5to8")
    public void testGenderGap_5to8(String param1, int param2, String param3, String param4, String param5, String param6, String param7, String param8) throws IOException {
        assertTrue(tagger.tag(Arrays.asList(param3, param4, param5, param6, param7, param8)).get(param2).hasPartialPosTag(param1));
    }

    static public Stream<Arguments> Provider_testGenderGap_5to8() {
        return Stream.of(arguments("PRO:IND:NOM:SIN:FEM", 0, "jede", "*", "r", "Mitarbeiter", "*", "in"), arguments("PRO:IND:NOM:SIN:MAS", 0, "jede", "*", "r", "Mitarbeiter", "*", "in"), arguments("SUB:NOM:SIN:FEM", 3, "jede", "*", "r", "Mitarbeiter", "*", "in"), arguments("SUB:NOM:SIN:MAS", 3, "jede", "*", "r", "Mitarbeiter", "*", "in"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsWeiseException_1to7")
    public void testIsWeiseException_1to7(String param1) {
        assertFalse(tagger.isWeiseException(param1));
    }

    static public Stream<Arguments> Provider_testIsWeiseException_1to7() {
        return Stream.of(arguments("überweise"), arguments("verweise"), arguments("eimerweise"), arguments("meterweise"), arguments("literweise"), arguments("blätterweise"), arguments("erweise"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsWeiseException_8to9")
    public void testIsWeiseException_8to9(String param1) {
        assertTrue(tagger.isWeiseException(param1));
    }

    static public Stream<Arguments> Provider_testIsWeiseException_8to9() {
        return Stream.of(arguments("lustigerweise"), arguments("idealerweise"));
    }
}
