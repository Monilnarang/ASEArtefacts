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

public class AgreementRule2Test_Purified {

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

    @Test
    public void testRule_1() throws IOException {
        assertBad("Kleiner Haus am Waldesrand");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("Kleines Haus am Waldesrand");
    }

    @Test
    public void testRule_3() throws IOException {
        assertBad("\"Kleiner Haus am Waldesrand\"");
    }

    @Test
    public void testRule_4() throws IOException {
        assertGood("\"Kleines Haus am Waldesrand\"");
    }

    @Test
    public void testRule_5() throws IOException {
        assertBad("Wirtschaftlich Wachstum kommt ins Stocken");
    }

    @Test
    public void testRule_6() throws IOException {
        assertGood("Wirtschaftliches Wachstum kommt ins Stocken");
    }

    @Test
    public void testRule_7() throws IOException {
        assertGood("Unter Berücksichtigung des Übergangs");
    }

    @Test
    public void testRule_8() throws IOException {
        assertGood("Wirklich Frieden herrscht aber noch nicht");
    }

    @Test
    public void testRule_9() throws IOException {
        assertBad("Deutscher Taschenbuch");
    }

    @Test
    public void testRule_10() throws IOException {
        assertGood("Deutscher Taschenbuch Verlag expandiert");
    }

    @Test
    public void testRule_11() throws IOException {
        assertGood("Wohl Anfang 1725 begegnete Bach dem Dichter.");
    }

    @Test
    public void testRule_12() throws IOException {
        assertGood("Weniger Personal wird im ganzen Land gebraucht.");
    }

    @Test
    public void testRule_13() throws IOException {
        assertGood("National Board of Review");
    }

    @Test
    public void testRule_14() throws IOException {
        assertGood("International Management");
    }

    @Test
    public void testRule_15() throws IOException {
        assertGood("Gemeinsam Sportler anfeuern.");
    }

    @Test
    public void testRule_16() throws IOException {
        assertGood("Viel Spaß beim Arbeiten");
    }

    @Test
    public void testRule_17() throws IOException {
        assertGood("Ganz Europa stand vor einer Neuordnung.");
    }

    @Test
    public void testRule_18() throws IOException {
        assertGood("Gesetzlich Versicherte sind davon ausgenommen.");
    }

    @Test
    public void testRule_19() throws IOException {
        assertGood("Ausreichend Bananen essen.");
    }

    @Test
    public void testRule_20() throws IOException {
        assertGood("Nachhaltig Yoga praktizieren");
    }

    @Test
    public void testRule_21() throws IOException {
        assertGood("Überraschend Besuch bekommt er dann von ihr.");
    }

    @Test
    public void testRule_22() throws IOException {
        assertGood("Ruhig Schlafen & Zentral Wohnen");
    }

    @Test
    public void testRule_23() throws IOException {
        assertGood("Voller Mitleid");
    }

    @Test
    public void testRule_24() throws IOException {
        assertGood("Voll Mitleid");
    }

    @Test
    public void testRule_25() throws IOException {
        assertGood("Einzig Fernschüsse brachten Erfolgsaussichten.");
    }

    @Test
    public void testRule_26() throws IOException {
        assertGood("Gelangweilt Dinge sortieren hilft als Ablenkung.");
    }

    @Test
    public void testRule_27() throws IOException {
        assertGood("Ganzjährig Garten pflegen");
    }

    @Test
    public void testRule_28() throws IOException {
        assertGood("Herzlich Willkommen bei unseren günstigen Rezepten!");
    }

    @Test
    public void testRule_29() throws IOException {
        assertGood("10-tägiges Rückgaberecht");
    }

    @Test
    public void testRule_30() throws IOException {
        assertGood("Angeblich Schüsse vor Explosionen gefallen");
    }

    @Test
    public void testRule_31() throws IOException {
        assertGood("Dickes Danke auch an Elena");
    }

    @Test
    public void testRule_32() throws IOException {
        assertGood("Dickes Dankeschön auch an Elena");
    }

    @Test
    public void testRule_33() throws IOException {
        assertGood("Echt Scheiße");
    }

    @Test
    public void testRule_34() throws IOException {
        assertGood("Entsprechende Automaten werden heute nicht mehr gebaut");
    }

    @Test
    public void testRule_35() throws IOException {
        assertGood("Existenziell Bedrohte kriegen einen Taschenrechner");
    }

    @Test
    public void testRule_36() throws IOException {
        assertGood("Flächendeckend Tempo 30");
    }

    @Test
    public void testRule_37() throws IOException {
        assertGood("Frei Klavier spielen lernen");
    }

    @Test
    public void testRule_38() throws IOException {
        assertGood("Ganz Eilige können es schaffen");
    }

    @Test
    public void testRule_39() throws IOException {
        assertGood("Gering Gebildete laufen Gefahr ...");
    }

    @Test
    public void testRule_40() throws IOException {
        assertGood("Ganz Ohr ist man hier");
    }

    @Test
    public void testRule_41() throws IOException {
        assertGood("Gleichzeitig Muskeln aufbauen und Fett verlieren");
    }

    @Test
    public void testRule_42() throws IOException {
        assertGood("Klar Schiff, Erster Offizier!");
    }

    @Test
    public void testRule_43() throws IOException {
        assertGood("Kostenlos Bewegung schnuppern");
    }

    @Test
    public void testRule_44() throws IOException {
        assertGood("Prinzipiell Anrecht auf eine Vertretung");
    }

    @Test
    public void testRule_45() throws IOException {
        assertGood("Regelrecht Modell gestanden haben Michel");
    }

    @Test
    public void testRule_46() throws IOException {
        assertGood("Weitgehend Konsens, auch über ...");
    }

    @Test
    public void testRule_47() throws IOException {
        assertGood("Alarmierte Polizeibeamte nahmen den Mann fest.");
    }

    @Test
    public void testRule_48() throws IOException {
        assertGood("Anderen Brot und Arbeit ermöglichen - das ist ihr Ziel");
    }

    @Test
    public void testRule_49() throws IOException {
        assertGood("Diverse Unwesen, mit denen sich Hellboy beschäftigen muss, ...");
    }

    @Test
    public void testRule_50() throws IOException {
        assertGood("Gut Qualifizierte bekommen Angebote");
    }

    @Test
    public void testRule_51() throws IOException {
        assertGood("Liebe Mai, wie geht es dir?");
    }

    @Test
    public void testRule_52() throws IOException {
        assertGood("Willkommen Simpsons-Fan!");
    }

    @Test
    public void testSuggestion_1() throws IOException {
        assertGood("Kleinem Haus am Waldesrand ...");
    }

    @Test
    public void testSuggestion_2() throws IOException {
        assertBad("Kleiner Haus am Waldesrand", "Kleines Haus");
    }

    @Test
    public void testSuggestion_3() throws IOException {
        assertBad("Kleines Häuser am Waldesrand", "Kleine Häuser");
    }

    @Test
    public void testSuggestion_4() throws IOException {
        assertBad("Kleinem Häuser am Waldesrand", "Kleine Häuser");
    }

    @Test
    public void testSuggestion_5() throws IOException {
        assertBad("Kleines Tisch reicht auch", "Kleiner Tisch");
    }

    @Test
    public void testSuggestion_6() throws IOException {
        assertGood("Junger Frau geht das Geld aus");
    }

    @Test
    public void testSuggestion_7() throws IOException {
        assertGood("Junge Frau gewinnt im Lotto");
    }

    @Test
    public void testSuggestion_8() throws IOException {
        assertBad("Junges Frau gewinnt im Lotto", "Junge Frau");
    }

    @Test
    public void testSuggestion_9() throws IOException {
        assertBad("Jungem Frau gewinnt im Lotto", "Junge Frau");
    }

    @Test
    public void testSuggestion_10() throws IOException {
        assertBad("Jung Frau gewinnt im Lotto", "Junge Frau");
    }

    @Test
    public void testSuggestion_11() throws IOException {
        assertBad("Wirtschaftlich Wachstum kommt ins Stocken", "Wirtschaftliches Wachstum");
    }

    @Test
    public void testSuggestion_12() throws IOException {
        assertBad("Wirtschaftlicher Wachstum kommt ins Stocken", "Wirtschaftliches Wachstum");
    }
}
