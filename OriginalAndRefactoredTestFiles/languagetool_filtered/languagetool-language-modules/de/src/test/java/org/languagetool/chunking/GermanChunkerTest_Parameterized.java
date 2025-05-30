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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GermanChunkerTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testChunking_1to58")
    public void testChunking_1to58(String param1) throws Exception {
        assertFullChunks(param1);
    }

    static public Stream<Arguments> Provider_testChunking_1to58() {
        return Stream.of(arguments("Ein/B Haus/I"), arguments("Ein/NPP Hund/NPP und/NPP eine/NPP Katze/NPP stehen dort"), arguments("Es war die/NPS größte/NPS und/NPS erfolgreichste/NPS Erfindung/NPS"), arguments("Geräte/B , deren/NPS Bestimmung/NPS und/NPS Funktion/NPS unklar sind."), arguments("Julia/NPP und/NPP Karsten/NPP sind alt"), arguments("Es ist die/NPS älteste/NPS und/NPS bekannteste/NPS Maßnahme/NPS"), arguments("Das ist eine/NPS Masseeinheit/NPS und/NPS keine/NPS Gewichtseinheit/NPS"), arguments("Sie fährt nur eins/NPS ihrer/NPS drei/NPS Autos/NPS"), arguments("Da sind er/NPP und/NPP seine/NPP Schwester/NPP"), arguments("Rekonstruktionen/NPP oder/NPP der/NPP Wiederaufbau/NPP sind das/NPS Ziel/NPS"), arguments("Isolation/NPP und/NPP ihre/NPP Überwindung/NPP ist das/NPS Thema/NPS"), arguments("Es gibt weder/NPP Gerechtigkeit/NPP noch/NPP Freiheit/NPP"), arguments("Da sitzen drei/NPP Katzen/NPP"), arguments("Der/NPS von/NPS der/NPS Regierung/NPS geprüfte/NPS Hund/NPS ist grün"), arguments("Herr/NPP und/NPP Frau/NPP Schröder/NPP sind betrunken"), arguments("Das sind 37/NPS Prozent/NPS"), arguments("Das sind 37/NPP Prozent/NPP"), arguments("Er will die/NPP Arbeitsplätze/NPP so umgestalten , dass/NPP sie/NPP wie/NPP ein/NPP Spiel/NPP sind."), arguments("So dass Knochenbrüche/NPP und/NPP Platzwunden/NPP die/NPP Regel/NPP sind"), arguments("Eine/NPS Veranstaltung/NPS ,/NPS die/NPS immer/NPS wieder/NPS ein/NPS kultureller/NPS Höhepunkt/NPS war"), arguments("Und die/NPS ältere/NPS der/NPS beiden/NPS Töchter/NPS ist 20."), arguments("Der/NPS Synthese/NPS organischer/NPS Verbindungen/NPS steht nichts im/PP Weg/NPS"), arguments("Aber/B die/NPP Kenntnisse/NPP der/NPP Sprache/NPP sind nötig."), arguments("Dort steht die/NPS Pyramide/NPS des/NPS Friedens/NPS und/NPS der/NPS Eintracht/NPS"), arguments("Und Teil/B der/NPS dort/NPS ausgestellten/NPS Bestände/NPS wurde privat finanziert."), arguments("Autor/NPS der/NPS ersten/NPS beiden/NPS Bücher/NPS ist Stephen King/NPS"), arguments("Autor/NPS der/NPS beiden/NPS Bücher/NPS ist Stephen King/NPS"), arguments("Teil/NPS der/NPS umfangreichen/NPS dort/NPS ausgestellten/NPS Bestände/NPS stammt von privat"), arguments("Ein/NPS Teil/NPS der/NPS umfangreichen/NPS dort/NPS ausgestellten/NPS Bestände/NPS stammt von privat"), arguments("Die/NPS Krankheit/NPS unserer/NPS heutigen/NPS Städte/NPS und/NPS Siedlungen/NPS ist der/NPS Verkehr/NPS"), arguments("Der/B Nil/I ist der/NPS letzte/NPS der/NPS vier/NPS großen/NPS Flüsse/NPS"), arguments("Der/NPS letzte/NPS der/NPS vier/NPS großen/NPS Flüsse/NPS ist der/B Nil/I"), arguments("Sie kennt eine/NPP Menge/NPP englischer/NPP Wörter/NPP"), arguments("Eine/NPP Menge/NPP englischer/NPP Wörter/NPP sind aus/PP dem/NPS Lateinischen/NPS abgeleitet."), arguments("Laut/PP den/PP meisten/PP Quellen/PP ist er 35 Jahre/B alt."), arguments("Bei/PP den/PP sehr/PP niedrigen/PP Oberflächentemperaturen/PP verbrennt nichts"), arguments("In/PP den/PP alten/PP Religionen/PP ,/PP Mythen/PP und/PP Sagen/PP tauchen Geister/B auf."), arguments("Die/B Straße/I ist wichtig für/PP die/PP Stadtteile/PP und/PP selbständigen/PP Ortsteile/PP"), arguments("Es herrscht gute/NPS Laune/NPS in/PP chemischen/PP Komplexverbindungen/PP"), arguments("Funktionen/NPP des/NPP Körpers/NPP einschließlich/PP der/PP biologischen/PP und/PP sozialen/PP Grundlagen/PP"), arguments("Das/NPS Dokument/NPS umfasst das für/PP Ärzte/PP und/PP Ärztinnen/PP festgestellte/PP Risikoprofil/PP"), arguments("In/PP den/PP darauf/PP folgenden/PP Wochen/PP ging es los."), arguments("In/PP nur/PP zwei/PP Wochen/PP geht es los."), arguments("Programme/B , in/PP deren/PP deutschen/PP Installationen/PP nichts funktioniert."), arguments("Nach/PP sachlichen/PP und/PP militärischen/PP Kriterien/PP war das unnötig."), arguments("Mit/PP über/PP 1000/PP Handschriften/PP ist es die/NPS größte/NPS Sammlung/NPS"), arguments("Es gab Beschwerden/NPP über/PP laufende/PP Sanierungsmaßnahmen/PP"), arguments("Gesteigerte/B Effizienz/I durch/PP Einsatz/PP größerer/PP Maschinen/PP und/PP bessere/PP Kapazitätsplanung/PP"), arguments("Bei/PP sehr/PP guten/PP Beobachtungsbedingungen/PP bin ich dabei"), arguments("Die/NPP Beziehungen/NPP zwischen/NPP Kanada/NPP und/NPP dem/NPP Iran/NPP sind unterkühlt"), arguments("Die/PP darauffolgenden/PP Jahre/PP war es kalt"), arguments("Die/NPP darauffolgenden/NPP Jahre/NPP waren kalt"), arguments("Die/PP letzten/PP zwei/PP Monate/PP war es kalt"), arguments("Letztes/PP Jahr/PP war kalt"), arguments("Letztes/PP Jahr/PP war es kalt"), arguments("Es sind Atome/NPP ,/NPP welche/NPP der/NPP Urstoff/NPP aller/NPP Körper/NPP sind"), arguments("Kommentare/NPP ,/NPP Korrekturen/NPP ,/NPP Kritik/NPP bitte nach /dev/null"), arguments("Einer/NPS der/NPS beiden/NPS Höfe/NPS war schön"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOpenNLPLikeChunking_1to23")
    public void testOpenNLPLikeChunking_1to23(String param1) throws Exception {
        assertBasicChunks(param1);
    }

    static public Stream<Arguments> Provider_testOpenNLPLikeChunking_1to23() {
        return Stream.of(arguments("Ein/B Haus/I"), arguments("Da steht ein/B Haus/I"), arguments("Da steht ein/B schönes/I Haus/I"), arguments("Da steht ein/B schönes/I großes/I Haus/I"), arguments("Da steht ein/B sehr/I großes/I Haus/I"), arguments("Da steht ein/B sehr/I schönes/I großes/I Haus/I"), arguments("Da steht ein/B sehr/I großes/I Haus/I mit Dach/B"), arguments("Da steht ein/B sehr/I großes/I Haus/I mit einem/B blauen/I Dach/I"), arguments("Eine/B leckere/I Lasagne/I"), arguments("Herr/B Meier/I isst eine/B leckere/I Lasagne/I"), arguments("Herr/B Schrödinger/I isst einen/B Kuchen/I"), arguments("Herr/B Schrödinger/I isst einen/B leckeren/I Kuchen/I"), arguments("Herr/B Karl/I Meier/I isst eine/B leckere/I Lasagne/I"), arguments("Herr/B Finn/I Westerwalbesloh/I isst eine/B leckere/I Lasagne/I"), arguments("Unsere/B schöne/I Heimat/I geht den/B Bach/I runter"), arguments("Er meint das/B Haus/I am grünen/B Hang/I"), arguments("Ich/B muss dem/B Hund/I Futter/I geben"), arguments("Das/B Wasser/I , das die/B Wärme/I überträgt"), arguments("Er mag das/B Wasser/I , das/B Meer/I und die/B Luft/I"), arguments("Schon mehr als zwanzig/B Prozent/I der/B Arbeiter/I sind im Streik/B"), arguments("Das/B neue/I Gesetz/I betrifft 1000 Bürger/B"), arguments("In zwei/B Wochen/I ist Weihnachten/B"), arguments("Eines ihrer/B drei/I Autos/I ist blau"));
    }
}
