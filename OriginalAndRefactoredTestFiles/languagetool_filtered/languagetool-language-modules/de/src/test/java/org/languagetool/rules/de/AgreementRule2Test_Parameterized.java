package org.languagetool.rules.de;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.GermanyGerman;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AgreementRule2Test_Parameterized {

    private final JLanguageTool lt = new JLanguageTool(GermanyGerman.getInstance());

    private final AgreementRule2 rule = new AgreementRule2(TestTools.getEnglishMessages(), GermanyGerman.getInstance());

    private void assertGood(String s) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertThat(matches.length, is(0));
    }

    private void assertBad(String s) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertThat(matches.length, is(1));
    }

    private void assertBad(String s, String suggestion) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertThat(matches.length, is(1));
        assertTrue("Got suggestions: " + matches[0].getSuggestedReplacements() + ", expected: " + suggestion, matches[0].getSuggestedReplacements().contains(suggestion));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1_3_5_9")
    public void testRule_1_3_5_9(String param1) throws IOException {
        assertBad(param1);
    }

    static public Stream<Arguments> Provider_testRule_1_3_5_9() {
        return Stream.of(arguments("Kleiner Haus am Waldesrand"), arguments("\"Kleiner Haus am Waldesrand\""), arguments("Wirtschaftlich Wachstum kommt ins Stocken"), arguments("Deutscher Taschenbuch"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to2_4_6_6to7_7to8_10to52")
    public void testRule_1to2_4_6_6to7_7to8_10to52(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to2_4_6_6to7_7to8_10to52() {
        return Stream.of(arguments("Kleines Haus am Waldesrand"), arguments("\"Kleines Haus am Waldesrand\""), arguments("Wirtschaftliches Wachstum kommt ins Stocken"), arguments("Unter Berücksichtigung des Übergangs"), arguments("Wirklich Frieden herrscht aber noch nicht"), arguments("Deutscher Taschenbuch Verlag expandiert"), arguments("Wohl Anfang 1725 begegnete Bach dem Dichter."), arguments("Weniger Personal wird im ganzen Land gebraucht."), arguments("National Board of Review"), arguments("International Management"), arguments("Gemeinsam Sportler anfeuern."), arguments("Viel Spaß beim Arbeiten"), arguments("Ganz Europa stand vor einer Neuordnung."), arguments("Gesetzlich Versicherte sind davon ausgenommen."), arguments("Ausreichend Bananen essen."), arguments("Nachhaltig Yoga praktizieren"), arguments("Überraschend Besuch bekommt er dann von ihr."), arguments("Ruhig Schlafen & Zentral Wohnen"), arguments("Voller Mitleid"), arguments("Voll Mitleid"), arguments("Einzig Fernschüsse brachten Erfolgsaussichten."), arguments("Gelangweilt Dinge sortieren hilft als Ablenkung."), arguments("Ganzjährig Garten pflegen"), arguments("Herzlich Willkommen bei unseren günstigen Rezepten!"), arguments("10-tägiges Rückgaberecht"), arguments("Angeblich Schüsse vor Explosionen gefallen"), arguments("Dickes Danke auch an Elena"), arguments("Dickes Dankeschön auch an Elena"), arguments("Echt Scheiße"), arguments("Entsprechende Automaten werden heute nicht mehr gebaut"), arguments("Existenziell Bedrohte kriegen einen Taschenrechner"), arguments("Flächendeckend Tempo 30"), arguments("Frei Klavier spielen lernen"), arguments("Ganz Eilige können es schaffen"), arguments("Gering Gebildete laufen Gefahr ..."), arguments("Ganz Ohr ist man hier"), arguments("Gleichzeitig Muskeln aufbauen und Fett verlieren"), arguments("Klar Schiff, Erster Offizier!"), arguments("Kostenlos Bewegung schnuppern"), arguments("Prinzipiell Anrecht auf eine Vertretung"), arguments("Regelrecht Modell gestanden haben Michel"), arguments("Weitgehend Konsens, auch über ..."), arguments("Alarmierte Polizeibeamte nahmen den Mann fest."), arguments("Anderen Brot und Arbeit ermöglichen - das ist ihr Ziel"), arguments("Diverse Unwesen, mit denen sich Hellboy beschäftigen muss, ..."), arguments("Gut Qualifizierte bekommen Angebote"), arguments("Liebe Mai, wie geht es dir?"), arguments("Willkommen Simpsons-Fan!"), arguments("Kleinem Haus am Waldesrand ..."), arguments("Junger Frau geht das Geld aus"), arguments("Junge Frau gewinnt im Lotto"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSuggestion_2to5_8to12")
    public void testSuggestion_2to5_8to12(String param1, String param2) throws IOException {
        assertBad(param1, param2);
    }

    static public Stream<Arguments> Provider_testSuggestion_2to5_8to12() {
        return Stream.of(arguments("Kleiner Haus am Waldesrand", "Kleines Haus"), arguments("Kleines Häuser am Waldesrand", "Kleine Häuser"), arguments("Kleinem Häuser am Waldesrand", "Kleine Häuser"), arguments("Kleines Tisch reicht auch", "Kleiner Tisch"), arguments("Junges Frau gewinnt im Lotto", "Junge Frau"), arguments("Jungem Frau gewinnt im Lotto", "Junge Frau"), arguments("Jung Frau gewinnt im Lotto", "Junge Frau"), arguments("Wirtschaftlich Wachstum kommt ins Stocken", "Wirtschaftliches Wachstum"), arguments("Wirtschaftlicher Wachstum kommt ins Stocken", "Wirtschaftliches Wachstum"));
    }
}
