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

@SuppressWarnings("ConstantConditions")
public class GermanTaggerTest_Purified {

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
    public void testAdjectivesFromSpellingTxt_1() throws IOException {
        assertEquals("abgemindert[abgemindert/PA2:PRD:GRU:VER]", toSortedString(tagger.lookup("abgemindert")));
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

    @Test
    public void testAdjectivesFromSpellingTxt_6() throws IOException {
        assertEquals("meistgewünscht[meistgewünscht/ADJ:PRD:GRU]", toSortedString(tagger.lookup("meistgewünscht")));
    }

    @Test
    public void testGenderGap_1() throws IOException {
        assertTrue(tagger.tag(Arrays.asList("viele", "Freund", "*", "innen")).get(1).hasPartialPosTag(":PLU:FEM"));
    }

    @Test
    public void testGenderGap_2() throws IOException {
        assertTrue(tagger.tag(Arrays.asList("viele", "Freund", "_", "innen")).get(1).hasPartialPosTag(":PLU:FEM"));
    }

    @Test
    public void testGenderGap_3() throws IOException {
        assertTrue(tagger.tag(Arrays.asList("viele", "Freund", ":", "innen")).get(1).hasPartialPosTag(":PLU:FEM"));
    }

    @Test
    public void testGenderGap_4() throws IOException {
        assertTrue(tagger.tag(Arrays.asList("viele", "Freund", "/", "innen")).get(1).hasPartialPosTag(":PLU:FEM"));
    }

    @Test
    public void testGenderGap_5() throws IOException {
        assertTrue(tagger.tag(Arrays.asList("jede", "*", "r", "Mitarbeiter", "*", "in")).get(0).hasPartialPosTag("PRO:IND:NOM:SIN:FEM"));
    }

    @Test
    public void testGenderGap_6() throws IOException {
        assertTrue(tagger.tag(Arrays.asList("jede", "*", "r", "Mitarbeiter", "*", "in")).get(0).hasPartialPosTag("PRO:IND:NOM:SIN:MAS"));
    }

    @Test
    public void testGenderGap_7() throws IOException {
        assertTrue(tagger.tag(Arrays.asList("jede", "*", "r", "Mitarbeiter", "*", "in")).get(3).hasPartialPosTag("SUB:NOM:SIN:FEM"));
    }

    @Test
    public void testGenderGap_8() throws IOException {
        assertTrue(tagger.tag(Arrays.asList("jede", "*", "r", "Mitarbeiter", "*", "in")).get(3).hasPartialPosTag("SUB:NOM:SIN:MAS"));
    }

    @Test
    public void testExtendedTagger_1() throws IOException {
        assertEquals("Kuß[Kuß/SUB:AKK:SIN:MAS, Kuß/SUB:DAT:SIN:MAS, Kuß/SUB:NOM:SIN:MAS]", toSortedString(tagger.lookup("Kuß")));
    }

    @Test
    public void testExtendedTagger_2() throws IOException {
        assertEquals("Kuss[Kuss/SUB:AKK:SIN:MAS, Kuss/SUB:DAT:SIN:MAS, Kuss/SUB:NOM:SIN:MAS]", toSortedString(tagger.lookup("Kuss")));
    }

    @Test
    public void testExtendedTagger_3() throws IOException {
        assertEquals("Haß[Haß/SUB:AKK:SIN:MAS, Haß/SUB:DAT:SIN:MAS, Haß/SUB:NOM:SIN:MAS]", toSortedString(tagger.lookup("Haß")));
    }

    @Test
    public void testExtendedTagger_4() throws IOException {
        assertEquals("Hass[Hass/SUB:AKK:SIN:MAS, Hass/SUB:DAT:SIN:MAS, Hass/SUB:NOM:SIN:MAS]", toSortedString(tagger.lookup("Hass")));
    }

    @Test
    public void testIsWeiseException_1() {
        assertFalse(tagger.isWeiseException("überweise"));
    }

    @Test
    public void testIsWeiseException_2() {
        assertFalse(tagger.isWeiseException("verweise"));
    }

    @Test
    public void testIsWeiseException_3() {
        assertFalse(tagger.isWeiseException("eimerweise"));
    }

    @Test
    public void testIsWeiseException_4() {
        assertFalse(tagger.isWeiseException("meterweise"));
    }

    @Test
    public void testIsWeiseException_5() {
        assertFalse(tagger.isWeiseException("literweise"));
    }

    @Test
    public void testIsWeiseException_6() {
        assertFalse(tagger.isWeiseException("blätterweise"));
    }

    @Test
    public void testIsWeiseException_7() {
        assertFalse(tagger.isWeiseException("erweise"));
    }

    @Test
    public void testIsWeiseException_8() {
        assertTrue(tagger.isWeiseException("lustigerweise"));
    }

    @Test
    public void testIsWeiseException_9() {
        assertTrue(tagger.isWeiseException("idealerweise"));
    }
}
