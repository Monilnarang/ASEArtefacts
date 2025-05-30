package org.languagetool.chunking;

import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;

public class GermanChunkerTest_Purified {

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("de-DE"));

    private final GermanChunker chunker = new GermanChunker();

    private void assertBasicChunks(String input) throws Exception {
        String plainInput = getPlainInput(input);
        AnalyzedSentence analyzedSentence = lt.getAnalyzedSentence(plainInput);
        AnalyzedTokenReadings[] result = analyzedSentence.getTokensWithoutWhitespace();
        List<ChunkTaggedToken> basicChunks = chunker.getBasicChunks(Arrays.asList(result));
        List<String> expectedChunks = getExpectedChunks(input);
        assertChunks(input, plainInput, basicChunks, expectedChunks);
    }

    private void assertFullChunks(String input) throws Exception {
        String plainInput = getPlainInput(input);
        AnalyzedSentence analyzedSentence = lt.getAnalyzedSentence(plainInput);
        AnalyzedTokenReadings[] result = analyzedSentence.getTokensWithoutWhitespace();
        chunker.addChunkTags(Arrays.asList(result));
        List<String> expectedChunks = getExpectedChunks(input);
        List<ChunkTaggedToken> result2 = new ArrayList<>();
        int i = 0;
        for (AnalyzedTokenReadings readings : result) {
            if (i > 0) {
                ChunkTaggedToken chunkTaggedToken = new ChunkTaggedToken(readings.getToken(), readings.getChunkTags(), readings);
                result2.add(chunkTaggedToken);
            }
            i++;
        }
        assertChunks(input, plainInput, result2, expectedChunks);
    }

    private String getPlainInput(String input) {
        return input.replaceAll("/[A-Z-]*", "").replace(" ,", ",");
    }

    private List<String> getExpectedChunks(String input) {
        List<String> expectedChunks = new ArrayList<>();
        String[] parts = input.split(" ");
        for (String part : parts) {
            String[] tokenParts = part.split("/");
            if (tokenParts.length == 2) {
                String chunk = tokenParts[1];
                if (chunk.equals("B")) {
                    expectedChunks.add("B-NP");
                } else if (chunk.equals("I")) {
                    expectedChunks.add("I-NP");
                } else if (chunk.equals("NPP")) {
                    expectedChunks.add("NPP");
                } else if (chunk.equals("NPS")) {
                    expectedChunks.add("NPS");
                } else if (chunk.equals("PP")) {
                    expectedChunks.add("PP");
                } else {
                    throw new RuntimeException("Unknown chunk type: '" + chunk + "'");
                }
            } else {
                expectedChunks.add("O");
            }
        }
        return expectedChunks;
    }

    private void assertChunks(String input, String plainInput, List<ChunkTaggedToken> chunks, List<String> expectedChunks) {
        int i = 0;
        for (String expectedChunk : expectedChunks) {
            ChunkTaggedToken outputChunksHere = chunks.get(i);
            if (!outputChunksHere.getChunkTags().contains(new ChunkTag(expectedChunk))) {
                fail("Expected '" + expectedChunk + "' but got '" + outputChunksHere + "' at position " + i + " for input:\n  " + input + "\nPlain input:\n  " + plainInput + "\nChunks:\n  " + chunks + "\nExpected:\n  " + expectedChunks);
            }
            i++;
        }
    }

    @Test
    public void testChunking_1() throws Exception {
        assertFullChunks("Ein/B Haus/I");
    }

    @Test
    public void testChunking_2() throws Exception {
        assertFullChunks("Ein/NPP Hund/NPP und/NPP eine/NPP Katze/NPP stehen dort");
    }

    @Test
    public void testChunking_3() throws Exception {
        assertFullChunks("Es war die/NPS größte/NPS und/NPS erfolgreichste/NPS Erfindung/NPS");
    }

    @Test
    public void testChunking_4() throws Exception {
        assertFullChunks("Geräte/B , deren/NPS Bestimmung/NPS und/NPS Funktion/NPS unklar sind.");
    }

    @Test
    public void testChunking_5() throws Exception {
        assertFullChunks("Julia/NPP und/NPP Karsten/NPP sind alt");
    }

    @Test
    public void testChunking_6() throws Exception {
        assertFullChunks("Es ist die/NPS älteste/NPS und/NPS bekannteste/NPS Maßnahme/NPS");
    }

    @Test
    public void testChunking_7() throws Exception {
        assertFullChunks("Das ist eine/NPS Masseeinheit/NPS und/NPS keine/NPS Gewichtseinheit/NPS");
    }

    @Test
    public void testChunking_8() throws Exception {
        assertFullChunks("Sie fährt nur eins/NPS ihrer/NPS drei/NPS Autos/NPS");
    }

    @Test
    public void testChunking_9() throws Exception {
        assertFullChunks("Da sind er/NPP und/NPP seine/NPP Schwester/NPP");
    }

    @Test
    public void testChunking_10() throws Exception {
        assertFullChunks("Rekonstruktionen/NPP oder/NPP der/NPP Wiederaufbau/NPP sind das/NPS Ziel/NPS");
    }

    @Test
    public void testChunking_11() throws Exception {
        assertFullChunks("Isolation/NPP und/NPP ihre/NPP Überwindung/NPP ist das/NPS Thema/NPS");
    }

    @Test
    public void testChunking_12() throws Exception {
        assertFullChunks("Es gibt weder/NPP Gerechtigkeit/NPP noch/NPP Freiheit/NPP");
    }

    @Test
    public void testChunking_13() throws Exception {
        assertFullChunks("Da sitzen drei/NPP Katzen/NPP");
    }

    @Test
    public void testChunking_14() throws Exception {
        assertFullChunks("Der/NPS von/NPS der/NPS Regierung/NPS geprüfte/NPS Hund/NPS ist grün");
    }

    @Test
    public void testChunking_15() throws Exception {
        assertFullChunks("Herr/NPP und/NPP Frau/NPP Schröder/NPP sind betrunken");
    }

    @Test
    public void testChunking_16() throws Exception {
        assertFullChunks("Das sind 37/NPS Prozent/NPS");
    }

    @Test
    public void testChunking_17() throws Exception {
        assertFullChunks("Das sind 37/NPP Prozent/NPP");
    }

    @Test
    public void testChunking_18() throws Exception {
        assertFullChunks("Er will die/NPP Arbeitsplätze/NPP so umgestalten , dass/NPP sie/NPP wie/NPP ein/NPP Spiel/NPP sind.");
    }

    @Test
    public void testChunking_19() throws Exception {
        assertFullChunks("So dass Knochenbrüche/NPP und/NPP Platzwunden/NPP die/NPP Regel/NPP sind");
    }

    @Test
    public void testChunking_20() throws Exception {
        assertFullChunks("Eine/NPS Veranstaltung/NPS ,/NPS die/NPS immer/NPS wieder/NPS ein/NPS kultureller/NPS Höhepunkt/NPS war");
    }

    @Test
    public void testChunking_21() throws Exception {
        assertFullChunks("Und die/NPS ältere/NPS der/NPS beiden/NPS Töchter/NPS ist 20.");
    }

    @Test
    public void testChunking_22() throws Exception {
        assertFullChunks("Der/NPS Synthese/NPS organischer/NPS Verbindungen/NPS steht nichts im/PP Weg/NPS");
    }

    @Test
    public void testChunking_23() throws Exception {
        assertFullChunks("Aber/B die/NPP Kenntnisse/NPP der/NPP Sprache/NPP sind nötig.");
    }

    @Test
    public void testChunking_24() throws Exception {
        assertFullChunks("Dort steht die/NPS Pyramide/NPS des/NPS Friedens/NPS und/NPS der/NPS Eintracht/NPS");
    }

    @Test
    public void testChunking_25() throws Exception {
        assertFullChunks("Und Teil/B der/NPS dort/NPS ausgestellten/NPS Bestände/NPS wurde privat finanziert.");
    }

    @Test
    public void testChunking_26() throws Exception {
        assertFullChunks("Autor/NPS der/NPS ersten/NPS beiden/NPS Bücher/NPS ist Stephen King/NPS");
    }

    @Test
    public void testChunking_27() throws Exception {
        assertFullChunks("Autor/NPS der/NPS beiden/NPS Bücher/NPS ist Stephen King/NPS");
    }

    @Test
    public void testChunking_28() throws Exception {
        assertFullChunks("Teil/NPS der/NPS umfangreichen/NPS dort/NPS ausgestellten/NPS Bestände/NPS stammt von privat");
    }

    @Test
    public void testChunking_29() throws Exception {
        assertFullChunks("Ein/NPS Teil/NPS der/NPS umfangreichen/NPS dort/NPS ausgestellten/NPS Bestände/NPS stammt von privat");
    }

    @Test
    public void testChunking_30() throws Exception {
        assertFullChunks("Die/NPS Krankheit/NPS unserer/NPS heutigen/NPS Städte/NPS und/NPS Siedlungen/NPS ist der/NPS Verkehr/NPS");
    }

    @Test
    public void testChunking_31() throws Exception {
        assertFullChunks("Der/B Nil/I ist der/NPS letzte/NPS der/NPS vier/NPS großen/NPS Flüsse/NPS");
    }

    @Test
    public void testChunking_32() throws Exception {
        assertFullChunks("Der/NPS letzte/NPS der/NPS vier/NPS großen/NPS Flüsse/NPS ist der/B Nil/I");
    }

    @Test
    public void testChunking_33() throws Exception {
        assertFullChunks("Sie kennt eine/NPP Menge/NPP englischer/NPP Wörter/NPP");
    }

    @Test
    public void testChunking_34() throws Exception {
        assertFullChunks("Eine/NPP Menge/NPP englischer/NPP Wörter/NPP sind aus/PP dem/NPS Lateinischen/NPS abgeleitet.");
    }

    @Test
    public void testChunking_35() throws Exception {
        assertFullChunks("Laut/PP den/PP meisten/PP Quellen/PP ist er 35 Jahre/B alt.");
    }

    @Test
    public void testChunking_36() throws Exception {
        assertFullChunks("Bei/PP den/PP sehr/PP niedrigen/PP Oberflächentemperaturen/PP verbrennt nichts");
    }

    @Test
    public void testChunking_37() throws Exception {
        assertFullChunks("In/PP den/PP alten/PP Religionen/PP ,/PP Mythen/PP und/PP Sagen/PP tauchen Geister/B auf.");
    }

    @Test
    public void testChunking_38() throws Exception {
        assertFullChunks("Die/B Straße/I ist wichtig für/PP die/PP Stadtteile/PP und/PP selbständigen/PP Ortsteile/PP");
    }

    @Test
    public void testChunking_39() throws Exception {
        assertFullChunks("Es herrscht gute/NPS Laune/NPS in/PP chemischen/PP Komplexverbindungen/PP");
    }

    @Test
    public void testChunking_40() throws Exception {
        assertFullChunks("Funktionen/NPP des/NPP Körpers/NPP einschließlich/PP der/PP biologischen/PP und/PP sozialen/PP Grundlagen/PP");
    }

    @Test
    public void testChunking_41() throws Exception {
        assertFullChunks("Das/NPS Dokument/NPS umfasst das für/PP Ärzte/PP und/PP Ärztinnen/PP festgestellte/PP Risikoprofil/PP");
    }

    @Test
    public void testChunking_42() throws Exception {
        assertFullChunks("In/PP den/PP darauf/PP folgenden/PP Wochen/PP ging es los.");
    }

    @Test
    public void testChunking_43() throws Exception {
        assertFullChunks("In/PP nur/PP zwei/PP Wochen/PP geht es los.");
    }

    @Test
    public void testChunking_44() throws Exception {
        assertFullChunks("Programme/B , in/PP deren/PP deutschen/PP Installationen/PP nichts funktioniert.");
    }

    @Test
    public void testChunking_45() throws Exception {
        assertFullChunks("Nach/PP sachlichen/PP und/PP militärischen/PP Kriterien/PP war das unnötig.");
    }

    @Test
    public void testChunking_46() throws Exception {
        assertFullChunks("Mit/PP über/PP 1000/PP Handschriften/PP ist es die/NPS größte/NPS Sammlung/NPS");
    }

    @Test
    public void testChunking_47() throws Exception {
        assertFullChunks("Es gab Beschwerden/NPP über/PP laufende/PP Sanierungsmaßnahmen/PP");
    }

    @Test
    public void testChunking_48() throws Exception {
        assertFullChunks("Gesteigerte/B Effizienz/I durch/PP Einsatz/PP größerer/PP Maschinen/PP und/PP bessere/PP Kapazitätsplanung/PP");
    }

    @Test
    public void testChunking_49() throws Exception {
        assertFullChunks("Bei/PP sehr/PP guten/PP Beobachtungsbedingungen/PP bin ich dabei");
    }

    @Test
    public void testChunking_50() throws Exception {
        assertFullChunks("Die/NPP Beziehungen/NPP zwischen/NPP Kanada/NPP und/NPP dem/NPP Iran/NPP sind unterkühlt");
    }

    @Test
    public void testChunking_51() throws Exception {
        assertFullChunks("Die/PP darauffolgenden/PP Jahre/PP war es kalt");
    }

    @Test
    public void testChunking_52() throws Exception {
        assertFullChunks("Die/NPP darauffolgenden/NPP Jahre/NPP waren kalt");
    }

    @Test
    public void testChunking_53() throws Exception {
        assertFullChunks("Die/PP letzten/PP zwei/PP Monate/PP war es kalt");
    }

    @Test
    public void testChunking_54() throws Exception {
        assertFullChunks("Letztes/PP Jahr/PP war kalt");
    }

    @Test
    public void testChunking_55() throws Exception {
        assertFullChunks("Letztes/PP Jahr/PP war es kalt");
    }

    @Test
    public void testChunking_56() throws Exception {
        assertFullChunks("Es sind Atome/NPP ,/NPP welche/NPP der/NPP Urstoff/NPP aller/NPP Körper/NPP sind");
    }

    @Test
    public void testChunking_57() throws Exception {
        assertFullChunks("Kommentare/NPP ,/NPP Korrekturen/NPP ,/NPP Kritik/NPP bitte nach /dev/null");
    }

    @Test
    public void testChunking_58() throws Exception {
        assertFullChunks("Einer/NPS der/NPS beiden/NPS Höfe/NPS war schön");
    }

    @Test
    public void testOpenNLPLikeChunking_1() throws Exception {
        assertBasicChunks("Ein/B Haus/I");
    }

    @Test
    public void testOpenNLPLikeChunking_2() throws Exception {
        assertBasicChunks("Da steht ein/B Haus/I");
    }

    @Test
    public void testOpenNLPLikeChunking_3() throws Exception {
        assertBasicChunks("Da steht ein/B schönes/I Haus/I");
    }

    @Test
    public void testOpenNLPLikeChunking_4() throws Exception {
        assertBasicChunks("Da steht ein/B schönes/I großes/I Haus/I");
    }

    @Test
    public void testOpenNLPLikeChunking_5() throws Exception {
        assertBasicChunks("Da steht ein/B sehr/I großes/I Haus/I");
    }

    @Test
    public void testOpenNLPLikeChunking_6() throws Exception {
        assertBasicChunks("Da steht ein/B sehr/I schönes/I großes/I Haus/I");
    }

    @Test
    public void testOpenNLPLikeChunking_7() throws Exception {
        assertBasicChunks("Da steht ein/B sehr/I großes/I Haus/I mit Dach/B");
    }

    @Test
    public void testOpenNLPLikeChunking_8() throws Exception {
        assertBasicChunks("Da steht ein/B sehr/I großes/I Haus/I mit einem/B blauen/I Dach/I");
    }

    @Test
    public void testOpenNLPLikeChunking_9() throws Exception {
        assertBasicChunks("Eine/B leckere/I Lasagne/I");
    }

    @Test
    public void testOpenNLPLikeChunking_10() throws Exception {
        assertBasicChunks("Herr/B Meier/I isst eine/B leckere/I Lasagne/I");
    }

    @Test
    public void testOpenNLPLikeChunking_11() throws Exception {
        assertBasicChunks("Herr/B Schrödinger/I isst einen/B Kuchen/I");
    }

    @Test
    public void testOpenNLPLikeChunking_12() throws Exception {
        assertBasicChunks("Herr/B Schrödinger/I isst einen/B leckeren/I Kuchen/I");
    }

    @Test
    public void testOpenNLPLikeChunking_13() throws Exception {
        assertBasicChunks("Herr/B Karl/I Meier/I isst eine/B leckere/I Lasagne/I");
    }

    @Test
    public void testOpenNLPLikeChunking_14() throws Exception {
        assertBasicChunks("Herr/B Finn/I Westerwalbesloh/I isst eine/B leckere/I Lasagne/I");
    }

    @Test
    public void testOpenNLPLikeChunking_15() throws Exception {
        assertBasicChunks("Unsere/B schöne/I Heimat/I geht den/B Bach/I runter");
    }

    @Test
    public void testOpenNLPLikeChunking_16() throws Exception {
        assertBasicChunks("Er meint das/B Haus/I am grünen/B Hang/I");
    }

    @Test
    public void testOpenNLPLikeChunking_17() throws Exception {
        assertBasicChunks("Ich/B muss dem/B Hund/I Futter/I geben");
    }

    @Test
    public void testOpenNLPLikeChunking_18() throws Exception {
        assertBasicChunks("Das/B Wasser/I , das die/B Wärme/I überträgt");
    }

    @Test
    public void testOpenNLPLikeChunking_19() throws Exception {
        assertBasicChunks("Er mag das/B Wasser/I , das/B Meer/I und die/B Luft/I");
    }

    @Test
    public void testOpenNLPLikeChunking_20() throws Exception {
        assertBasicChunks("Schon mehr als zwanzig/B Prozent/I der/B Arbeiter/I sind im Streik/B");
    }

    @Test
    public void testOpenNLPLikeChunking_21() throws Exception {
        assertBasicChunks("Das/B neue/I Gesetz/I betrifft 1000 Bürger/B");
    }

    @Test
    public void testOpenNLPLikeChunking_22() throws Exception {
        assertBasicChunks("In zwei/B Wochen/I ist Weihnachten/B");
    }

    @Test
    public void testOpenNLPLikeChunking_23() throws Exception {
        assertBasicChunks("Eines ihrer/B drei/I Autos/I ist blau");
    }
}
