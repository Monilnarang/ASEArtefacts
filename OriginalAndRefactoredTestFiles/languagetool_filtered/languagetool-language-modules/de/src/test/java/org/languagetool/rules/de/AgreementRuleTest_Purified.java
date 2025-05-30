package org.languagetool.rules.de;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.*;
import org.languagetool.language.GermanyGerman;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static junit.framework.TestCase.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AgreementRuleTest_Purified {

    private AgreementRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new AgreementRule(TestTools.getMessages("de"), (GermanyGerman) Languages.getLanguageForShortCode("de-DE"));
        lt = new JLanguageTool(Languages.getLanguageForShortCode("de-DE"));
    }

    private void assertGood(String s) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertEquals("Found unexpected match in sentence '" + s + "': " + Arrays.toString(matches), 0, matches.length);
    }

    private void assertBad(String s, String... expectedSuggestions) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertEquals("Did not find one match in sentence '" + s + "'", 1, matches.length);
        if (expectedSuggestions.length > 0) {
            RuleMatch match = matches[0];
            List<String> suggestions = match.getSuggestedReplacements();
            assertThat(suggestions, is(Arrays.asList(expectedSuggestions)));
        }
    }

    private void assertBad1(String s, String expectedSuggestion) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertEquals("Did not find one match in sentence '" + s + "'", 1, matches.length);
        RuleMatch match = matches[0];
        List<String> suggestions = match.getSuggestedReplacements();
        boolean found = false;
        for (String suggestion : suggestions.subList(0, Math.min(4, suggestions.size()))) {
            if (suggestion.equals(expectedSuggestion)) {
                found = true;
                break;
            }
        }
        if (!found) {
            fail("Expected suggestion '" + expectedSuggestion + "' not found in first 5 suggestions for input '" + s + "'. " + "Suggestions found: " + suggestions);
        }
    }

    private void assertBadWithNoSuggestion(String s) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertEquals("Did not find one match in sentence '" + s + "'", 1, matches.length);
        RuleMatch match = matches[0];
        List<String> suggestions = match.getSuggestedReplacements();
        if (suggestions.size() != 0) {
            fail("Expected 0 suggestions for: " + s + ", got: " + suggestions);
        }
    }

    @Test
    public void testDetNounRule_1() throws IOException {
        assertGood("Bis zur Anfang Juni geplanten Eröffnung gebe es noch einiges zu tun.");
    }

    @Test
    public void testDetNounRule_2() throws IOException {
        assertGood("Der fließend Französisch sprechende Präsident dankt stilvoll ab.");
    }

    @Test
    public void testDetNounRule_3() throws IOException {
        assertGood("Inwiefern soll denn das romantische Hoffnungen begründen?");
    }

    @Test
    public void testDetNounRule_4() throws IOException {
        assertGood("Spricht der fließend Französisch?");
    }

    @Test
    public void testDetNounRule_5() throws IOException {
        assertGood("Spricht dieser fließend Französisch, muss er viel Geld verdienen.");
    }

    @Test
    public void testDetNounRule_6() throws IOException {
        assertGood("Der letzte Woche beschlossene Etat ist unwirksam.");
    }

    @Test
    public void testDetNounRule_7() throws IOException {
        assertGood("Die Einen sagen dies, die Anderen das.");
    }

    @Test
    public void testDetNounRule_8() throws IOException {
        assertGood("So ist es in den USA.");
    }

    @Test
    public void testDetNounRule_9() throws IOException {
        assertGood("Das ist der Tisch.");
    }

    @Test
    public void testDetNounRule_10() throws IOException {
        assertGood("Das ist das Haus.");
    }

    @Test
    public void testDetNounRule_11() throws IOException {
        assertGood("Das ist die Frau.");
    }

    @Test
    public void testDetNounRule_12() throws IOException {
        assertGood("Das ist das Auto der Frau.");
    }

    @Test
    public void testDetNounRule_13() throws IOException {
        assertGood("Das gehört dem Mann.");
    }

    @Test
    public void testDetNounRule_14() throws IOException {
        assertGood("Das Auto des Mannes.");
    }

    @Test
    public void testDetNounRule_15() throws IOException {
        assertGood("Das interessiert den Mann.");
    }

    @Test
    public void testDetNounRule_16() throws IOException {
        assertGood("Das interessiert die Männer.");
    }

    @Test
    public void testDetNounRule_17() throws IOException {
        assertGood("Das Auto von einem Mann.");
    }

    @Test
    public void testDetNounRule_18() throws IOException {
        assertGood("Das Auto eines Mannes.");
    }

    @Test
    public void testDetNounRule_19() throws IOException {
        assertGood("Des großen Mannes.");
    }

    @Test
    public void testDetNounRule_20() throws IOException {
        assertGood("Und nach der Nummerierung kommt die Überschrift.");
    }

    @Test
    public void testDetNounRule_21() throws IOException {
        assertGood("Sie wiesen dieselben Verzierungen auf.");
    }

    @Test
    public void testDetNounRule_22() throws IOException {
        assertGood("Die erwähnte Konferenz ist am Samstag.");
    }

    @Test
    public void testDetNounRule_23() throws IOException {
        assertGood("Sie erreichten 5 Prozent.");
    }

    @Test
    public void testDetNounRule_24() throws IOException {
        assertGood("Sie erreichten mehrere Prozent Zustimmung.");
    }

    @Test
    public void testDetNounRule_25() throws IOException {
        assertGood("Die Bestandteile, aus denen Schwefel besteht.");
    }

    @Test
    public void testDetNounRule_26() throws IOException {
        assertGood("Ich tat für ihn, was kein anderer Autor für ihn tat.");
    }

    @Test
    public void testDetNounRule_27() throws IOException {
        assertGood("Ich tat für ihn, was keine andere Autorin für ihn tat.");
    }

    @Test
    public void testDetNounRule_28() throws IOException {
        assertGood("Ich tat für ihn, was kein anderes Kind für ihn tat.");
    }

    @Test
    public void testDetNounRule_29() throws IOException {
        assertGood("Ich tat für ihn, was dieser andere Autor für ihn tat.");
    }

    @Test
    public void testDetNounRule_30() throws IOException {
        assertGood("Ich tat für ihn, was diese andere Autorin für ihn tat.");
    }

    @Test
    public void testDetNounRule_31() throws IOException {
        assertGood("Ich tat für ihn, was dieses andere Kind für ihn tat.");
    }

    @Test
    public void testDetNounRule_32() throws IOException {
        assertGood("Ich tat für ihn, was jener andere Autor für ihn tat.");
    }

    @Test
    public void testDetNounRule_33() throws IOException {
        assertGood("Ich tat für ihn, was jeder andere Autor für ihn tat.");
    }

    @Test
    public void testDetNounRule_34() throws IOException {
        assertGood("Ich tat für ihn, was jede andere Autorin für ihn tat.");
    }

    @Test
    public void testDetNounRule_35() throws IOException {
        assertGood("Ich tat für ihn, was jedes andere Kind für ihn tat.");
    }

    @Test
    public void testDetNounRule_36() throws IOException {
        assertGood("Klebe ein Preisschild auf jedes einzelne Produkt.");
    }

    @Test
    public void testDetNounRule_37() throws IOException {
        assertGood("Eine Stadt, in der zurzeit eine rege Bautätigkeit herrscht.");
    }

    @Test
    public void testDetNounRule_38() throws IOException {
        assertGood("... wo es zu einer regen Bautätigkeit kam.");
    }

    @Test
    public void testDetNounRule_39() throws IOException {
        assertGood("Mancher ausscheidende Politiker hinterlässt eine Lücke.");
    }

    @Test
    public void testDetNounRule_40() throws IOException {
        assertGood("Kern einer jeden Tragödie ist es, ..");
    }

    @Test
    public void testDetNounRule_41() throws IOException {
        assertGood("Das wenige Sekunden alte Baby schrie laut.");
    }

    @Test
    public void testDetNounRule_42() throws IOException {
        assertGood("Meistens sind das Frauen, die damit besser umgehen können.");
    }

    @Test
    public void testDetNounRule_43() throws IOException {
        assertGood("Er fragte, ob das Spaß macht.");
    }

    @Test
    public void testDetNounRule_44() throws IOException {
        assertGood("Das viele Geld wird ihr helfen.");
    }

    @Test
    public void testDetNounRule_45() throws IOException {
        assertGood("Er verspricht jedem hohe Gewinne.");
    }

    @Test
    public void testDetNounRule_46() throws IOException {
        assertGood("Er versprach allen Renditen jenseits von 15 Prozent.");
    }

    @Test
    public void testDetNounRule_47() throws IOException {
        assertGood("Sind das Eier aus Bodenhaltung?");
    }

    @Test
    public void testDetNounRule_48() throws IOException {
        assertGood("Sie sind sehr gute Kameraden, auf die Verlass ist.");
    }

    @Test
    public void testDetNounRule_49() throws IOException {
        assertGood("Dir macht doch irgendwas Sorgen.");
    }

    @Test
    public void testDetNounRule_50() throws IOException {
        assertGood("Sie fragte, ob das wirklich Kunst sei.");
    }

    @Test
    public void testDetNounRule_51() throws IOException {
        assertGood("Für ihn ist das Alltag.");
    }

    @Test
    public void testDetNounRule_52() throws IOException {
        assertGood("Für die Religiösen ist das Blasphemie und führt zu Aufständen.");
    }

    @Test
    public void testDetNounRule_53() throws IOException {
        assertGood("Das Orange ist schön.");
    }

    @Test
    public void testDetNounRule_54() throws IOException {
        assertGood("Dieses rötliche Orange gefällt mir am besten.");
    }

    @Test
    public void testDetNounRule_55() throws IOException {
        assertGood("Das ist ein super Tipp.");
    }

    @Test
    public void testDetNounRule_56() throws IOException {
        assertGood("Er nahm allen Mut zusammen und ging los.");
    }

    @Test
    public void testDetNounRule_57() throws IOException {
        assertGood("Sie kann einem Angst einjagen.");
    }

    @Test
    public void testDetNounRule_58() throws IOException {
        assertGood("Damit sollten zum einen neue Energien gefördert werden, zum anderen der Sozialbereich.");
    }

    @Test
    public void testDetNounRule_59() throws IOException {
        assertGood("Nichts ist mit dieser einen Nacht zu vergleichen.");
    }

    @Test
    public void testDetNounRule_60() throws IOException {
        assertGood("dann muss Schule dem Rechnung tragen.");
    }

    @Test
    public void testDetNounRule_61() throws IOException {
        assertGood("Das Dach von meinem Auto.");
    }

    @Test
    public void testDetNounRule_62() throws IOException {
        assertGood("Das Dach von meinen Autos.");
    }

    @Test
    public void testDetNounRule_63() throws IOException {
        assertGood("Da stellt sich die Frage: Ist das Science-Fiction oder moderne Mobilität?");
    }

    @Test
    public void testDetNounRule_64() throws IOException {
        assertGood("Er hat einen Post veröffentlicht.");
    }

    @Test
    public void testDetNounRule_65() throws IOException {
        assertGood("Eine lückenlose Aufklärung sämtlicher physiologischer Gehirnprozesse");
    }

    @Test
    public void testDetNounRule_66() throws IOException {
        assertGood("Sie fragte verwirrt: „Ist das Zucker?“");
    }

    @Test
    public void testDetNounRule_67() throws IOException {
        assertGood("Er versuchte sich vorzustellen, was sein Klient für ein Mensch sei.");
    }

    @Test
    public void testDetNounRule_68() throws IOException {
        assertGood("Sie legen ein Teilstück jenes Weges zurück, den die Tausenden Juden 1945 auf sich nehmen mussten.");
    }

    @Test
    public void testDetNounRule_69() throws IOException {
        assertGood("Aber das ignorierte Herr Grey bewusst.");
    }

    @Test
    public void testDetNounRule_70() throws IOException {
        assertGood("Aber das ignorierte Herr Müller bewusst.");
    }

    @Test
    public void testDetNounRule_71() throws IOException {
        assertGood("Ich werde mich zurücknehmen und mich frischen Ideen zuwenden.");
    }

    @Test
    public void testDetNounRule_72() throws IOException {
        assertGood("Das, plus ein eigener Firmenwagen.");
    }

    @Test
    public void testDetNounRule_73() throws IOException {
        assertGood("Dieses leise Summen stört nicht.");
    }

    @Test
    public void testDetNounRule_74() throws IOException {
        assertGood("Die Tiroler Küche");
    }

    @Test
    public void testDetNounRule_75() throws IOException {
        assertGood("Was ist denn das für ein ungewöhnlicher Name?");
    }

    @Test
    public void testDetNounRule_76() throws IOException {
        assertGood("Besonders reizen mich Fahrräder.");
    }

    @Test
    public void testDetNounRule_77() throws IOException {
        assertGood("Und nur, weil mich psychische Erkrankungen aus der Bahn werfen");
    }

    @Test
    public void testDetNounRule_78() throws IOException {
        assertGood("Das kostet dich Zinsen.");
    }

    @Test
    public void testDetNounRule_79() throws IOException {
        assertGood("Sie hatten keine Chance gegen das kleinere Preußen.");
    }

    @Test
    public void testDetNounRule_80() throws IOException {
        assertGood("Den 2019er Wert hatten sie geschätzt.");
    }

    @Test
    public void testDetNounRule_81() throws IOException {
        assertGood("Andere formale Systeme, deren Semantiken jeweils...");
    }

    @Test
    public void testDetNounRule_82() throws IOException {
        assertGood("Gesetz zur Änderung des Kündigungsrechts und anderer arbeitsrechtlicher Vorschriften");
    }

    @Test
    public void testDetNounRule_83() throws IOException {
        assertGood("Die dauerhafte Abgrenzung des später Niedersachsen genannten Gebietes von Westfalen begann im 12. Jahrhundert.");
    }

    @Test
    public void testDetNounRule_84() throws IOException {
        assertGood("Lieber jemanden, der einem Tipps gibt.");
    }

    @Test
    public void testDetNounRule_85() throws IOException {
        assertGood("Jainas ist sogar der Genuss jeglicher tierischer Nahrungsmittel strengstens untersagt.");
    }

    @Test
    public void testDetNounRule_86() throws IOException {
        assertGood("Es sind jegliche tierische Nahrungsmittel untersagt.");
    }

    @Test
    public void testDetNounRule_87() throws IOException {
        assertGood("Das reicht bis weit ins heutige Hessen.");
    }

    @Test
    public void testDetNounRule_88() throws IOException {
        assertGood("Die Customer Journey.");
    }

    @Test
    public void testDetNounRule_89() throws IOException {
        assertGood("Für dich gehört Radfahren zum perfekten Urlaub dazu?");
    }

    @Test
    public void testDetNounRule_90() throws IOException {
        assertGood(":D:D Leute, bitte!");
    }

    @Test
    public void testDetNounRule_91() throws IOException {
        assertGood("Es genügt, wenn ein Mann sein eigenes Geschäft versteht und sich nicht in das anderer Leute einmischt.");
    }

    @Test
    public void testDetNounRule_92() throws IOException {
        assertGood("Ich habe das einige Male versucht.");
    }

    @Test
    public void testDetNounRule_93() throws IOException {
        assertGood("Und keine Märchen erzählst, die dem anderen Hoffnungen machen können.");
    }

    @Test
    public void testDetNounRule_94() throws IOException {
        assertGood("Um diese Körpergrößen zu erreichen, war das Wachstum der Vertreter der Gattung Dinornis offenbar gegenüber dem anderer Moa-Gattungen beschleunigt");
    }

    @Test
    public void testDetNounRule_95() throws IOException {
        assertGood("Der Schädel entspricht in den Proportionen dem anderer Vulpes-Arten, besitzt aber sehr große Paukenhöhlen, ein typisches Merkmal von Wüstenbewohnern.");
    }

    @Test
    public void testDetNounRule_96() throws IOException {
        assertGood("Deuterium lässt sich aufgrund des großen Massenunterschieds leichter anreichern als die Isotope der anderer Elemente wie z. B. Uran.");
    }

    @Test
    public void testDetNounRule_97() throws IOException {
        assertGood("Unklar ist, ob er zwischen der Atemseele des Menschen und der anderer Lebewesen unterschied.");
    }

    @Test
    public void testDetNounRule_98() throws IOException {
        assertGood("Die Liechtensteiner Grenze ist im Verhältnis zu der anderer Länder kurz, da Liechtenstein ein eher kleines Land ist.");
    }

    @Test
    public void testDetNounRule_99() throws IOException {
        assertGood("Picassos Kunstwerke werden häufiger gestohlen als die anderer Künstler.");
    }

    @Test
    public void testDetNounRule_100() throws IOException {
        assertGood("Schreibe einen Artikel über deine Erfahrungen im Ausland oder die anderer Leute in deinem Land.");
    }

    @Test
    public void testDetNounRule_101() throws IOException {
        assertGood("Die Bevölkerungen Chinas und Indiens lassen die anderer Staaten als Zwerge erscheinen.");
    }

    @Test
    public void testDetNounRule_102() throws IOException {
        assertGood("Der eine mag Obst, ein anderer Gemüse, wieder ein anderer mag Fisch; allen kann man es nicht recht machen.");
    }

    @Test
    public void testDetNounRule_103() throws IOException {
        assertGood("Mittels eines Bootloaders und zugehöriger Software kann nach jedem Anstecken des Adapters eine andere Firmware-Varianten geladen werden");
    }

    @Test
    public void testDetNounRule_104() throws IOException {
        assertGood("Wenn sie eine andere Größe benötigen, teilen uns ihre speziellen Wünsche mit und wir unterbreiten ihnen ein Angebot über Preis und Lieferung.");
    }

    @Test
    public void testDetNounRule_105() throws IOException {
        assertGood("Dabei wird in einer Vakuumkammer eine einige Mikrometer dicke CVD-Diamantschicht auf den Substraten abgeschieden.");
    }

    @Test
    public void testDetNounRule_106() throws IOException {
        assertGood("1916 versuchte Gilbert Newton Lewis, die chemische Bindung durch Wechselwirkung der Elektronen eines Atoms mit einem anderen Atomen zu erklären.");
    }

    @Test
    public void testDetNounRule_107() throws IOException {
        assertGood("Vom einen Ende der Straße zum anderen.");
    }

    @Test
    public void testDetNounRule_108() throws IOException {
        assertGood("Er war müde vom vielen Laufen.");
    }

    @Test
    public void testDetNounRule_109() throws IOException {
        assertGood("Sind das echte Diamanten?");
    }

    @Test
    public void testDetNounRule_110() throws IOException {
        assertGood("Es wurde eine Verordnung erlassen, der zufolge jeder Haushalt Energie einsparen muss.");
    }

    @Test
    public void testDetNounRule_111() throws IOException {
        assertGood("Im Jahr 1922 verlieh ihm König George V. den erblichen Titel eines Baronet. ");
    }

    @Test
    public void testDetNounRule_112() throws IOException {
        assertGood("... der zu dieser Zeit aber ohnehin schon allen Einfluss verloren hatte.");
    }

    @Test
    public void testDetNounRule_113() throws IOException {
        assertGood("Ein Geschenk, das Maßstäbe setzt");
    }

    @Test
    public void testDetNounRule_114() throws IOException {
        assertGood("Einwohnerzahl stieg um das Zweieinhalbfache");
    }

    @Test
    public void testDetNounRule_115() throws IOException {
        assertGood("Die Müllers aus Hamburg.");
    }

    @Test
    public void testDetNounRule_116() throws IOException {
        assertGood("Es ist noch unklar, wann und für wen Impfungen vorgenommen werden könnten.");
    }

    @Test
    public void testDetNounRule_117() throws IOException {
        assertGood("Macht dir das Hoffnung?");
    }

    @Test
    public void testDetNounRule_118() throws IOException {
        assertGood("Mich fasziniert Macht.");
    }

    @Test
    public void testDetNounRule_119() throws IOException {
        assertGood("Der solchen Einsätzen gegenüber kritische Hitler wurde nicht im Voraus informiert.");
    }

    @Test
    public void testDetNounRule_120() throws IOException {
        assertGood("Gregor wählte die Gestalt des wenige Jahrzehnte zuvor verstorbenen Klostergründers.");
    }

    @Test
    public void testDetNounRule_121() throws IOException {
        assertGood("Wir machen das Januar.");
    }

    @Test
    public void testDetNounRule_122() throws IOException {
        assertGood("Wir teilen das Morgen mit.");
    }

    @Test
    public void testDetNounRule_123() throws IOException {
        assertGood("Wir präsentierten das vorletzten Sonnabend.");
    }

    @Test
    public void testDetNounRule_124() throws IOException {
        assertGood("Ich release das Vormittags.");
    }

    @Test
    public void testDetNounRule_125() throws IOException {
        assertGood("Sie aktualisieren das Montags.");
    }

    @Test
    public void testDetNounRule_126() throws IOException {
        assertGood("Kannst du das Mittags machen?");
    }

    @Test
    public void testDetNounRule_127() throws IOException {
        assertGood("Können Sie das nächsten Monat erledigen?");
    }

    @Test
    public void testDetNounRule_128() throws IOException {
        assertGood("Können Sie das auch nächsten Monat erledigen?");
    }

    @Test
    public void testDetNounRule_129() throws IOException {
        assertGood("War das Absicht?");
    }

    @Test
    public void testDetNounRule_130() throws IOException {
        assertGood("Alles Große und Edle ist einfacher Art.");
    }

    @Test
    public void testDetNounRule_131() throws IOException {
        assertGood("Dieser vereint Sprachprüfung, Thesaurus und Umformuliertool in einem.");
    }

    @Test
    public void testDetNounRule_132() throws IOException {
        assertGood("Das Dach meines Autos.");
    }

    @Test
    public void testDetNounRule_133() throws IOException {
        assertGood("Das Dach meiner Autos.");
    }

    @Test
    public void testDetNounRule_134() throws IOException {
        assertGood("Das Dach meines großen Autos.");
    }

    @Test
    public void testDetNounRule_135() throws IOException {
        assertGood("Das Dach meiner großen Autos.");
    }

    @Test
    public void testDetNounRule_136() throws IOException {
        assertGood("Dann schlug er so kräftig wie er konnte mit den Schwingen.");
    }

    @Test
    public void testDetNounRule_137() throws IOException {
        assertGood("Also wenn wir Glück haben, ...");
    }

    @Test
    public void testDetNounRule_138() throws IOException {
        assertGood("Wenn wir Pech haben, ...");
    }

    @Test
    public void testDetNounRule_139() throws IOException {
        assertGood("Ledorn öffnete eines der an ihr vorhandenen Fächer.");
    }

    @Test
    public void testDetNounRule_140() throws IOException {
        assertGood("Auf der einen Seite endlose Dünen");
    }

    @Test
    public void testDetNounRule_141() throws IOException {
        assertGood("In seinem Maul hielt er einen blutigen Fleischklumpen.");
    }

    @Test
    public void testDetNounRule_142() throws IOException {
        assertGood("Gleichzeitig dachte er intensiv an Nebelschwaden, aus denen Wolken ja bestanden.");
    }

    @Test
    public void testDetNounRule_143() throws IOException {
        assertGood("Warum stellte der bloß immer wieder dieselben Fragen?");
    }

    @Test
    public void testDetNounRule_144() throws IOException {
        assertGood("Bei der Hinreise.");
    }

    @Test
    public void testDetNounRule_145() throws IOException {
        assertGood("Schließlich tauchten in einem Waldstück unter ihnen Schienen auf.");
    }

    @Test
    public void testDetNounRule_146() throws IOException {
        assertGood("Das Wahlrecht, das Frauen damals zugesprochen bekamen.");
    }

    @Test
    public void testDetNounRule_147() throws IOException {
        assertGood("Es war Karl, dessen Leiche Donnerstag gefunden wurde.");
    }

    @Test
    public void testDetNounRule_148() throws IOException {
        assertGood("Erst recht ich Arbeiter.");
    }

    @Test
    public void testDetNounRule_149() throws IOException {
        assertGood("Erst recht wir Arbeiter.");
    }

    @Test
    public void testDetNounRule_150() throws IOException {
        assertGood("Erst recht wir fleißigen Arbeiter.");
    }

    @Test
    public void testDetNounRule_151() throws IOException {
        assertGood("Dann lud er Freunde ein.");
    }

    @Test
    public void testDetNounRule_152() throws IOException {
        assertGood("Dann lud sie Freunde ein.");
    }

    @Test
    public void testDetNounRule_153() throws IOException {
        assertGood("Aller Kommunikation liegt dies zugrunde.");
    }

    @Test
    public void testDetNounRule_154() throws IOException {
        assertGood("Pragmatisch wählt man solche Formeln als Axiome.");
    }

    @Test
    public void testDetNounRule_155() throws IOException {
        assertGood("Der eine Polizist rief dem anderen zu...");
    }

    @Test
    public void testDetNounRule_156() throws IOException {
        assertGood("Der eine große Polizist rief dem anderen zu...");
    }

    @Test
    public void testDetNounRule_157() throws IOException {
        assertGood("Das eine Kind rief dem anderen zu...");
    }

    @Test
    public void testDetNounRule_158() throws IOException {
        assertGood("Er wollte seine Interessen wahrnehmen.");
    }

    @Test
    public void testDetNounRule_159() throws IOException {
        assertBad("Denn die einzelnen sehen sich einer sehr verschieden starken Macht des...");
    }

    @Test
    public void testDetNounRule_160() throws IOException {
        assertBad("Es birgt für mich ein zu hohes juristische Risiko.");
    }

    @Test
    public void testDetNounRule_161() throws IOException {
        assertGood("Es birgt für mich ein zu hohes juristisches Risiko.");
    }

    @Test
    public void testDetNounRule_162() throws IOException {
        assertGood("... wo Krieg den Unschuldigen Leid und Tod bringt.");
    }

    @Test
    public void testDetNounRule_163() throws IOException {
        assertGood("Der Abschuss eines Papageien.");
    }

    @Test
    public void testDetNounRule_164() throws IOException {
        assertGood("Die Beibehaltung des Art. 1 ist geplant.");
    }

    @Test
    public void testDetNounRule_165() throws IOException {
        assertGood("Die Verschiebung des bisherigen Art. 1 ist geplant.");
    }

    @Test
    public void testDetNounRule_166() throws IOException {
        assertGood("In diesem Fall hatte das Vorteile.");
    }

    @Test
    public void testDetNounRule_167() throws IOException {
        assertGood("So hat das Konsequenzen.");
    }

    @Test
    public void testDetNounRule_168() throws IOException {
        assertGood("Ein für viele wichtiges Anliegen.");
    }

    @Test
    public void testDetNounRule_169() throws IOException {
        assertGood("Das weckte bei vielen ungute Erinnerungen.");
    }

    @Test
    public void testDetNounRule_170() throws IOException {
        assertGood("Etwas, das einem Angst macht.");
    }

    @Test
    public void testDetNounRule_171() throws IOException {
        assertGood("Einem geschenkten Gaul schaut man nicht ins Maul.");
    }

    @Test
    public void testDetNounRule_172() throws IOException {
        assertGood("Das erfordert Können.");
    }

    @Test
    public void testDetNounRule_173() throws IOException {
        assertGood("Ist das Kunst?");
    }

    @Test
    public void testDetNounRule_174() throws IOException {
        assertGood("Ist das Kunst oder Abfall?");
    }

    @Test
    public void testDetNounRule_175() throws IOException {
        assertGood("Die Zeitdauer, während der Wissen nützlich bleibt, wird kürzer.");
    }

    @Test
    public void testDetNounRule_176() throws IOException {
        assertGood("Es sollte nicht viele solcher Bilder geben");
    }

    @Test
    public void testDetNounRule_177() throws IOException {
        assertGood("In den 80er Jahren.");
    }

    @Test
    public void testDetNounRule_178() throws IOException {
        assertGood("Hast du etwas das Carina machen kann?");
    }

    @Test
    public void testDetNounRule_179() throws IOException {
        assertGood("Ein Artikel in den Ruhr Nachrichten.");
    }

    @Test
    public void testDetNounRule_180() throws IOException {
        assertGood("Ich wollte nur allen Hallo sagen.");
    }

    @Test
    public void testDetNounRule_181() throws IOException {
        assertGood("Ich habe deshalb allen Freund*innen Bescheid gegeben.");
    }

    @Test
    public void testDetNounRule_182() throws IOException {
        assertGood("Ich habe deshalb allen Freund_innen Bescheid gegeben.");
    }

    @Test
    public void testDetNounRule_183() throws IOException {
        assertGood("Ich habe deshalb allen Freund:innen Bescheid gegeben.");
    }

    @Test
    public void testDetNounRule_184() throws IOException {
        assertGood("Das betrifft auch eure Werkstudent:innen-Zielgruppe.");
    }

    @Test
    public void testDetNounRule_185() throws IOException {
        assertBad("Das betrifft auch eure Werkstudent:innen-Xihfrisfgds.");
    }

    @Test
    public void testDetNounRule_186() throws IOException {
        assertGood("Das betrifft auch eure Werkstudent:innenzielgruppe.");
    }

    @Test
    public void testDetNounRule_187() throws IOException {
        assertGood("Das betrifft auch eure Jurist:innenausbildung.");
    }

    @Test
    public void testDetNounRule_188() throws IOException {
        assertBad("Das betrifft auch eure Jurist:innenxyzdfsdf.");
    }

    @Test
    public void testDetNounRule_189() throws IOException {
        assertGood("Sein*e Mitarbeiter*in ist davon auch betroffen.");
    }

    @Test
    public void testDetNounRule_190() throws IOException {
        assertGood("Jede*r Mitarbeiter*in ist davon betroffen.");
    }

    @Test
    public void testDetNounRule_191() throws IOException {
        assertGood("Alle Professor*innen");
    }

    @Test
    public void testDetNounRule_192() throws IOException {
        assertGood("Gleichzeitig wünscht sich Ihr frostresistenter Mitbewohner einige Grad weniger im eigenen Zimmer?");
    }

    @Test
    public void testDetNounRule_193() throws IOException {
        assertGood("Ein Trainer, der zum einen Fußballspiele sehr gut lesen und analysieren kann");
    }

    @Test
    public void testDetNounRule_194() throws IOException {
        assertGood("Eine Massengrenze, bis zu der Lithium nachgewiesen werden kann.");
    }

    @Test
    public void testDetNounRule_195() throws IOException {
        assertGood("Bei uns im Krankenhaus betrifft das Operationssäle.");
    }

    @Test
    public void testDetNounRule_196() throws IOException {
        assertGood("Macht dir das Freude?");
    }

    @Test
    public void testDetNounRule_197() throws IOException {
        assertGood("Das macht jedem Angst.");
    }

    @Test
    public void testDetNounRule_198() throws IOException {
        assertGood("Dann macht das Sinn.");
    }

    @Test
    public void testDetNounRule_199() throws IOException {
        assertGood("Das sind beides Lichtschalter.");
    }

    @Test
    public void testDetNounRule_200() throws IOException {
        assertGood("Spielst du vielleicht auf das Bordell neben unserm Hotel an?");
    }

    @Test
    public void testDetNounRule_201() throws IOException {
        assertGood("Spielst du vielleicht auf das Bordell neben unsrem Hotel an?");
    }

    @Test
    public void testDetNounRule_202() throws IOException {
        assertGood("Dieses ungeahnt prophetische Wort");
    }

    @Test
    public void testDetNounRule_203() throws IOException {
        assertGood("Das bestätigte Regierungssprecher Steffen Hebestreit am Freitag");
    }

    @Test
    public void testDetNounRule_204() throws IOException {
        assertGood("Es kann gut sein, dass bei sowas Probleme erkannt werden.");
    }

    @Test
    public void testDetNounRule_205() throws IOException {
        assertGood("Das Recht, das Frauen eingeräumt wird.");
    }

    @Test
    public void testDetNounRule_206() throws IOException {
        assertGood("Der Mann, in dem quadratische Fische schwammen.");
    }

    @Test
    public void testDetNounRule_207() throws IOException {
        assertGood("Der Mann, durch den quadratische Fische schwammen.");
    }

    @Test
    public void testDetNounRule_208() throws IOException {
        assertGood("Gutenberg, der quadratische Mann.");
    }

    @Test
    public void testDetNounRule_209() throws IOException {
        assertGood("Die größte Stuttgarter Grünanlage ist der Friedhof.");
    }

    @Test
    public void testDetNounRule_210() throws IOException {
        assertGood("Die meisten Lebensmittel enthalten das.");
    }

    @Test
    public void testDetNounRule_211() throws IOException {
        assertBad("Gutenberg, die Genie.");
    }

    @Test
    public void testDetNounRule_212() throws IOException {
        assertBad("Wahrlich ein äußerst kritische Jury.", "eine äußerst kritische Jury");
    }

    @Test
    public void testDetNounRule_213() throws IOException {
        assertBad("Das ist ein enorm großer Auto.", "ein enorm großes Auto");
    }

    @Test
    public void testDetNounRule_214() throws IOException {
        assertGood("Die wärmsten Monate sind August und September, die kältesten Januar und Februar.");
    }

    @Test
    public void testDetNounRule_215() throws IOException {
        assertGood("Das Münchener Fest.");
    }

    @Test
    public void testDetNounRule_216() throws IOException {
        assertGood("Das Münchner Fest.");
    }

    @Test
    public void testDetNounRule_217() throws IOException {
        assertGood("Die Planung des Münchener Festes.");
    }

    @Test
    public void testDetNounRule_218() throws IOException {
        assertGood("Das Berliner Wetter.");
    }

    @Test
    public void testDetNounRule_219() throws IOException {
        assertGood("Den Berliner Arbeitern ist das egal.");
    }

    @Test
    public void testDetNounRule_220() throws IOException {
        assertGood("Das Haus des Berliner Arbeiters.");
    }

    @Test
    public void testDetNounRule_221() throws IOException {
        assertGood("Es gehört dem Berliner Arbeiter.");
    }

    @Test
    public void testDetNounRule_222() throws IOException {
        assertGood("Das Stuttgarter Auto.");
    }

    @Test
    public void testDetNounRule_223() throws IOException {
        assertGood("Das Bielefelder Radio.");
    }

    @Test
    public void testDetNounRule_224() throws IOException {
        assertGood("Das Gütersloher Radio.");
    }

    @Test
    public void testDetNounRule_225() throws IOException {
        assertGood("Das wirklich Wichtige kommt jetzt erst.");
    }

    @Test
    public void testDetNounRule_226() throws IOException {
        assertGood("Besonders wenn wir Wermut oder Absinth trinken.");
    }

    @Test
    public void testDetNounRule_227() throws IOException {
        assertGood("Ich wünsche dir alles Gute.");
    }

    @Test
    public void testDetNounRule_228() throws IOException {
        assertGood("Es ist nicht bekannt, mit welchem Alter Kinder diese Fähigkeit erlernen.");
    }

    @Test
    public void testDetNounRule_229() throws IOException {
        assertGood("Dieser ist nun in den Ortungsbereich des einen Roboters gefahren.");
    }

    @Test
    public void testDetNounRule_230() throws IOException {
        assertGood("Wenn dies großen Erfolg hat, werden wir es weiter fördern.");
    }

    @Test
    public void testDetNounRule_231() throws IOException {
        assertGood("Alles Gute!");
    }

    @Test
    public void testDetNounRule_232() throws IOException {
        assertGood("Das bedeutet nichts Gutes.");
    }

    @Test
    public void testDetNounRule_233() throws IOException {
        assertGood("Die Ereignisse dieses einen Jahres waren sehr schlimm.");
    }

    @Test
    public void testDetNounRule_234() throws IOException {
        assertGood("Er musste einen Hochwasser führenden Fluss nach dem anderen überqueren.");
    }

    @Test
    public void testDetNounRule_235() throws IOException {
        assertGood("Darf ich Ihren Füller für ein paar Minuten ausleihen?");
    }

    @Test
    public void testDetNounRule_236() throws IOException {
        assertGood("Bringen Sie diesen Gepäckaufkleber an Ihrem Gepäck an.");
    }

    @Test
    public void testDetNounRule_237() throws IOException {
        assertGood("Extras, die den Wert Ihres Autos erhöhen.");
    }

    @Test
    public void testDetNounRule_238() throws IOException {
        assertGood("Er hat einen 34-jährigen Sohn.");
    }

    @Test
    public void testDetNounRule_239() throws IOException {
        assertGood("Die Polizei erwischte die Diebin, weil diese Ausweis und Visitenkarte hinterließ.");
    }

    @Test
    public void testDetNounRule_240() throws IOException {
        assertGood("Dieses Versäumnis soll vertuscht worden sein - es wurde Anzeige erstattet.");
    }

    @Test
    public void testDetNounRule_241() throws IOException {
        assertGood("Die Firmen - nicht nur die ausländischen, auch die katalanischen - treibt diese Frage um.");
    }

    @Test
    public void testDetNounRule_242() throws IOException {
        assertGood("Stell dich dem Leben lächelnd!");
    }

    @Test
    public void testDetNounRule_243() throws IOException {
        assertGood("Die Messe wird auf das vor der Stadt liegende Ausstellungsgelände verlegt.");
    }

    @Test
    public void testDetNounRule_244() throws IOException {
        assertGood("Sie sind ein den Frieden liebendes Volk.");
    }

    @Test
    public void testDetNounRule_245() throws IOException {
        assertGood("Zum Teil sind das Krebsvorstufen.");
    }

    @Test
    public void testDetNounRule_246() throws IOException {
        assertGood("Er sagt, dass das Rache bedeutet.");
    }

    @Test
    public void testDetNounRule_247() throws IOException {
        assertGood("Wenn das Kühe sind, bin ich ein Elefant.");
    }

    @Test
    public void testDetNounRule_248() throws IOException {
        assertGood("Karl sagte, dass sie niemandem Bescheid gegeben habe.");
    }

    @Test
    public void testDetNounRule_249() throws IOException {
        assertGood("Es blieb nur dieser eine Satz.");
    }

    @Test
    public void testDetNounRule_250() throws IOException {
        assertGood("Oder ist das Mathematikern vorbehalten?");
    }

    @Test
    public void testDetNounRule_251() throws IOException {
        assertGood("Wenn hier einer Fragen stellt, dann ich.");
    }

    @Test
    public void testDetNounRule_252() throws IOException {
        assertGood("Wenn einer Katzen mag, dann meine Schwester.");
    }

    @Test
    public void testDetNounRule_253() throws IOException {
        assertGood("Ergibt das Sinn?");
    }

    @Test
    public void testDetNounRule_254() throws IOException {
        assertGood("Sie ist über die Maßen schön.");
    }

    @Test
    public void testDetNounRule_255() throws IOException {
        assertGood("Ich vertraue ganz auf die Meinen.");
    }

    @Test
    public void testDetNounRule_256() throws IOException {
        assertGood("Was nützt einem Gesundheit, wenn man sonst ein Idiot ist?");
    }

    @Test
    public void testDetNounRule_257() throws IOException {
        assertGood("Auch das hatte sein Gutes.");
    }

    @Test
    public void testDetNounRule_258() throws IOException {
        assertGood("Auch wenn es sein Gutes hatte, war es doch traurig.");
    }

    @Test
    public void testDetNounRule_259() throws IOException {
        assertGood("Er wollte doch nur jemandem Gutes tun.");
    }

    @Test
    public void testDetNounRule_260() throws IOException {
        assertGood("und das erst Jahrhunderte spätere Auftauchen der Legende");
    }

    @Test
    public void testDetNounRule_261() throws IOException {
        assertGood("Texas und New Mexico, beides spanische Kolonien, sind...");
    }

    @Test
    public void testDetNounRule_262() throws IOException {
        assertGood("Texas und New Mexico - beides spanische Kolonien - sind...");
    }

    @Test
    public void testDetNounRule_263() throws IOException {
        assertGood("Texas und New Mexico – beides spanische Kolonien – sind...");
    }

    @Test
    public void testDetNounRule_264() throws IOException {
        assertGood("Weitere Brunnen sind insbesondere der Wittelsbacher und der Vater-Rhein-Brunnen auf der Museumsinsel, beides Werke von Adolf von Hildebrand.");
    }

    @Test
    public void testDetNounRule_265() throws IOException {
        assertGood("Für manche ist das Anlass genug, darüber nicht weiter zu diskutieren.");
    }

    @Test
    public void testDetNounRule_266() throws IOException {
        assertGood("Vielleicht schreckt das Frauen ab.");
    }

    @Test
    public void testDetNounRule_267() throws IOException {
        assertGood("Unser Hund vergräbt seine Knochen im Garten.");
    }

    @Test
    public void testDetNounRule_268() throws IOException {
        assertGood("Ob das Mehrwert bringt?");
    }

    @Test
    public void testDetNounRule_269() throws IOException {
        assertGood("Warum das Sinn macht?");
    }

    @Test
    public void testDetNounRule_270() throws IOException {
        assertGood("Das hängt davon ab, ob die Deutsch sprechen");
    }

    @Test
    public void testDetNounRule_271() throws IOException {
        assertGood("Die meisten Coaches wissen nichts.");
    }

    @Test
    public void testDetNounRule_272() throws IOException {
        assertGood("Die Präsent AG.");
    }

    @Test
    public void testDetNounRule_273() throws IOException {
        assertGood("In New York war er der Titelheld in Richard III. und spielte den Mark Anton in Julius Cäsar.");
    }

    @Test
    public void testDetNounRule_274() throws IOException {
        assertGood("Vielen Dank fürs Bescheid geben.");
    }

    @Test
    public void testDetNounRule_275() throws IOException {
        assertGood("Welche Display Ads?");
    }

    @Test
    public void testDetNounRule_276() throws IOException {
        assertGood("Das letzte Mal war das Anfang der 90er Jahre des vergangenen Jahrhunderts");
    }

    @Test
    public void testDetNounRule_277() throws IOException {
        assertGood("Der vom Rat der Justizminister gefasste Beschluss zur Aufnahme von Vertriebenen...");
    }

    @Test
    public void testDetNounRule_278() throws IOException {
        assertGood("Der letzte Woche vom Rat der Justizminister gefasste Beschluss zur Aufnahme von Vertriebenen...");
    }

    @Test
    public void testDetNounRule_279() throws IOException {
        assertGood("Was war sie nur für eine dumme Person!");
    }

    @Test
    public void testDetNounRule_280() throws IOException {
        assertGood("Was war ich für ein Idiot!");
    }

    @Test
    public void testDetNounRule_281() throws IOException {
        assertGood("Was für ein Idiot!");
    }

    @Test
    public void testDetNounRule_282() throws IOException {
        assertGood("Was für eine blöde Kuh!");
    }

    @Test
    public void testDetNounRule_283() throws IOException {
        assertGood("Was ist sie nur für eine blöde Kuh!");
    }

    @Test
    public void testDetNounRule_284() throws IOException {
        assertGood("Wie viele Paar Stiefel brauche ich eigentlich?");
    }

    @Test
    public void testDetNounRule_285() throws IOException {
        assertGood("Dieses versuchten Mathematiker 400 Jahre lang vergeblich zu beweisen.");
    }

    @Test
    public void testDetNounRule_286() throws IOException {
        assertGood("Gemälde informieren uns über das Leben von den vergangenen Jahrhunderten…");
    }

    @Test
    public void testDetNounRule_287() throws IOException {
        assertGood("Die Partei, die bei den vorangegangenen Wahlen noch seine Politik unterstützt hatte.");
    }

    @Test
    public void testDetNounRule_288() throws IOException {
        assertGood("Bei Zunahme der aufgelösten Mineralstoffe, bei denen...");
    }

    @Test
    public void testDetNounRule_289() throws IOException {
        assertGood("Je mehr Muskelspindeln in einem Muskel vorhanden sind, desto feiner können die mit diesem verbundenen Bewegungen abgestimmt werden.");
    }

    @Test
    public void testDetNounRule_290() throws IOException {
        assertGood("Diese datentechnischen Operationen werden durch Computerprogramme ausgelöst, d. h. über entsprechende, in diesen enthaltene Befehle (als Teil eines implementierten Algorithmus') vorgegeben.");
    }

    @Test
    public void testDetNounRule_291() throws IOException {
        assertGood("Aus diesen resultierten Konflikte wie der Bauernkrieg und der Pfälzische Erbfolgekrieg.");
    }

    @Test
    public void testDetNounRule_292() throws IOException {
        assertGood("Die Staatshandlungen einer Mikronation und von dieser herausgegebene Ausweise, Urkunden und Dokumente gelten im Rechtsverkehr als unwirksam");
    }

    @Test
    public void testDetNounRule_293() throws IOException {
        assertGood("Auf der Hohen See und auf den mit dieser verbundenen Gewässern gelten die internationalen Kollisionsverhütungsregeln.");
    }

    @Test
    public void testDetNounRule_294() throws IOException {
        assertGood("Art. 11 Abs. 2 GGV setzt dem bestimmte Arten der außergemeinschaftlichen Zugänglichmachung gleich");
    }

    @Test
    public void testDetNounRule_295() throws IOException {
        assertGood("Grundsätzlich sind die Heilungschancen von Männern mit Brustkrebs nicht schlechter als die betroffener Frauen.");
    }

    @Test
    public void testDetNounRule_296() throws IOException {
        assertGood("In diesem Viertel bin ich aufgewachsen.");
    }

    @Test
    public void testDetNounRule_297() throws IOException {
        assertGood("Im November wurde auf dem Gelände der Wettbewerb ausgetragen.");
    }

    @Test
    public void testDetNounRule_298() throws IOException {
        assertGood("Er ist Eigentümer des gleichnamigen Schemas und stellt dieses interessierten Domänen zur Verfügung.");
    }

    @Test
    public void testDetNounRule_299() throws IOException {
        assertGood("Dort finden sie viele Informationen rund um die Themen Schwangerschaft, Geburt, Stillen, Babys und Kinder.");
    }

    @Test
    public void testDetNounRule_300() throws IOException {
        assertGood("Die Galerie zu den Bildern findet sich hier.");
    }

    @Test
    public void testDetNounRule_301() throws IOException {
        assertGood("Ganz im Gegensatz zu den Blättern des Brombeerstrauches.");
    }

    @Test
    public void testDetNounRule_302() throws IOException {
        assertGood("Er erzählte von den Leuten und den Dingen, die er auf seiner Reise gesehen hatte.");
    }

    @Test
    public void testDetNounRule_303() throws IOException {
        assertGood("Diese Partnerschaft wurde 1989 nach dem Massaker auf dem Platz des Himmlischen Friedens eingefroren.");
    }

    @Test
    public void testDetNounRule_304() throws IOException {
        assertGood("Die Feuergefahr hingegen war für für die Londoner Teil des Alltags.");
    }

    @Test
    public void testDetNounRule_305() throws IOException {
        assertGood("Was ist, wenn ein Projekt bei den Berliner Type Awards mit einem Diplom ausgezeichnet wird?");
    }

    @Test
    public void testDetNounRule_306() throws IOException {
        assertGood("Was ist mit dem Liechtensteiner Kulturleben los?");
    }

    @Test
    public void testDetNounRule_307() throws IOException {
        assertGood("Das ist der Mann den Präsident Xi Jinping verurteilte.");
    }

    @Test
    public void testDetNounRule_308() throws IOException {
        assertGood("Wie viele Kolleg/-innen haben sie?");
    }

    @Test
    public void testDetNounRule_309() throws IOException {
        assertGood("Die Ideen der neuen Kolleg/-innen sind gut!");
    }

    @Test
    public void testDetNounRule_310() throws IOException {
        assertBad("Ein Buch mit einem ganz ähnlichem Titel.");
    }

    @Test
    public void testDetNounRule_311() throws IOException {
        assertBad("Meiner Chef raucht.");
    }

    @Test
    public void testDetNounRule_312() throws IOException {
        assertBad("Er hat eine 34-jährigen Sohn.");
    }

    @Test
    public void testDetNounRule_313() throws IOException {
        assertBad("Es sind die Tisch.", "die Tische", "der Tisch", "den Tisch", "dem Tisch");
    }

    @Test
    public void testDetNounRule_314() throws IOException {
        assertBad("Es sind das Tisch.", "der Tisch", "den Tisch", "dem Tisch");
    }

    @Test
    public void testDetNounRule_315() throws IOException {
        assertBad("Es sind die Haus.", "das Haus", "dem Haus", "die Häuser");
    }

    @Test
    public void testDetNounRule_316() throws IOException {
        assertBad("Es sind der Haus.", "dem Haus", "das Haus", "der Häuser");
    }

    @Test
    public void testDetNounRule_317() throws IOException {
        assertBad("Es sind das Frau.", "die Frau", "der Frau");
    }

    @Test
    public void testDetNounRule_318() throws IOException {
        assertBad("Das Auto des Mann.", "der Mann", "den Mann", "dem Mann", "des Manns", "des Mannes");
    }

    @Test
    public void testDetNounRule_319() throws IOException {
        assertBad("Das interessiert das Mann.", "der Mann", "den Mann", "dem Mann");
    }

    @Test
    public void testDetNounRule_320() throws IOException {
        assertBad("Das interessiert die Mann.", "der Mann", "den Mann", "dem Mann", "die Männer");
    }

    @Test
    public void testDetNounRule_321() throws IOException {
        assertBad("Das Auto ein Mannes.", "ein Mann", "eines Mannes");
    }

    @Test
    public void testDetNounRule_322() throws IOException {
        assertBad("Das Auto einem Mannes.", "eines Mannes", "einem Mann");
    }

    @Test
    public void testDetNounRule_323() throws IOException {
        assertBad("Das Auto einer Mannes.", "eines Mannes");
    }

    @Test
    public void testDetNounRule_324() throws IOException {
        assertBad("Das Auto einen Mannes.", "eines Mannes", "einen Mann");
    }

    @Test
    public void testDetNounRule_325() throws IOException {
        assertBad("Die Galerie zu den Bilder findet sich hier.");
    }

    @Test
    public void testDetNounRule_326() throws IOException {
        assertBad("Ganz im Gegensatz zu den Blätter des Brombeerstrauches.");
    }

    @Test
    public void testDetNounRule_327() throws IOException {
        assertGood("Das erlaubt Forschern, neue Versuche durchzuführen.");
    }

    @Test
    public void testDetNounRule_328() throws IOException {
        assertGood("Dies ermöglicht Forschern, neue Versuche durchzuführen.");
    }

    @Test
    public void testDetNounRule_329() throws IOException {
        assertGood("Je länger zugewartet wird, desto schwieriger dürfte es werden, die Jungtiere von den Elterntieren zu unterscheiden.");
    }

    @Test
    public void testDetNounRule_330() throws IOException {
        assertGood("Er schrieb ein von 1237 bis 1358 reichendes Geschichtswerk, dessen Schwerpunkt auf den Ereignissen in der Lombardei liegt.");
    }

    @Test
    public void testDetNounRule_331() throws IOException {
        assertGood("Private Veranstaltungen waren, darauf hat die Strandhaus Norderstedt GmbH im Rahmen rechtlicher Klärungen selbst bestanden, nicht Bestandteil dieser Verträge.");
    }

    @Test
    public void testDetNounRule_332() throws IOException {
        assertBad("Die erwähnt Konferenz ist am Samstag.");
    }

    @Test
    public void testDetNounRule_333() throws IOException {
        assertBad("Die erwähntes Konferenz ist am Samstag.");
    }

    @Test
    public void testDetNounRule_334() throws IOException {
        assertBad("Die erwähnten Konferenz ist am Samstag.");
    }

    @Test
    public void testDetNounRule_335() throws IOException {
        assertBad("Die erwähnter Konferenz ist am Samstag.");
    }

    @Test
    public void testDetNounRule_336() throws IOException {
        assertBad("Die erwähntem Konferenz ist am Samstag.");
    }

    @Test
    public void testDetNounRule_337() throws IOException {
        assertBad("Die gemessen Werte werden in die länderspezifische Höhe über dem Meeresspiegel umgerechnet.");
    }

    @Test
    public void testDetNounRule_338() throws IOException {
        assertBad("Darüber hinaus haben wir das berechtigte Interessen, diese Daten zu verarbeiten.");
    }

    @Test
    public void testDetNounRule_339() throws IOException {
        assertBad("Eine Amnestie kann den Hingerichteten nicht das Leben und dem heimgesuchten Familien nicht das Glück zurückgeben.");
    }

    @Test
    public void testDetNounRule_340() throws IOException {
        assertBad("Z. B. therapeutisches Klonen, um aus den gewonnen Zellen in vitro Ersatzorgane für den Patienten zu erzeugen");
    }

    @Test
    public void testDetNounRule_341() throws IOException {
        assertBad("Die Partei, die bei den vorangegangen Wahlen noch seine Politik unterstützt hatte.");
    }

    @Test
    public void testDetNounRule_342() throws IOException {
        assertBad("Bei Zunahme der aufgelösten Mineralstoffen, bei denen...");
    }

    @Test
    public void testDetNounRule_343() throws IOException {
        assertBad("Durch die große Vielfalt der verschiedene Linien ist für jeden Anspruch die richtige Brille im Portfolio.");
    }

    @Test
    public void testDetNounRule_344() throws IOException {
        assertBad("In diesen Viertel bin ich aufgewachsen.");
    }

    @Test
    public void testDetNounRule_345() throws IOException {
        assertBad("Im November wurde auf den Gelände der Wettbewerb ausgetragen.");
    }

    @Test
    public void testDetNounRule_346() throws IOException {
        assertBad("Dort finden sie Testberichte und viele Informationen rund um das Themen Schwangerschaft, Geburt, Stillen, Babys und Kinder.");
    }

    @Test
    public void testDetNounRule_347() throws IOException {
        assertBad("Je länger zugewartet wird, desto schwieriger dürfte es werden, die Jungtiere von den Elterntiere zu unterscheiden.");
    }

    @Test
    public void testDetNounRule_348() throws IOException {
        assertBad("Er schrieb ein von 1237 bis 1358 reichendes Geschichtswerk, dessen Schwerpunkt auf den Ereignisse in der Lombardei liegt.");
    }

    @Test
    public void testDetNounRule_349() throws IOException {
        assertBad("Des großer Mannes.");
    }

    @Test
    public void testDetNounRule_350() throws IOException {
        assertBad("Er erzählte von den Leute und den Dingen, die er gesehen hatte.");
    }

    @Test
    public void testDetNounRule_351() throws IOException {
        assertBad("Diese Partnerschaft wurde 1989 nach den Massaker auf dem Platz des Himmlischen Friedens eingefroren.");
    }

    @Test
    public void testDetNounRule_352() throws IOException {
        assertBad("Das Dach von meine Auto.", "meinem Auto");
    }

    @Test
    public void testDetNounRule_353() throws IOException {
        assertBad("Das Dach von meinen Auto.", "meinem Auto", "meinen Autos");
    }

    @Test
    public void testDetNounRule_354() throws IOException {
        assertBad("Das Dach mein Autos.", "mein Auto", "meine Autos", "meines Autos", "meinen Autos", "meiner Autos");
    }

    @Test
    public void testDetNounRule_355() throws IOException {
        assertBad("Das Dach meinem Autos.", "meinem Auto", "meines Autos", "meine Autos", "meinen Autos", "meiner Autos");
    }

    @Test
    public void testDetNounRule_356() throws IOException {
        assertBad("Das Dach meinem großen Autos.");
    }

    @Test
    public void testDetNounRule_357() throws IOException {
        assertBad("Das Dach mein großen Autos.");
    }

    @Test
    public void testDetNounRule_358() throws IOException {
        assertBad("Das Klientel der Partei.", "Die Klientel", "Der Klientel");
    }

    @Test
    public void testDetNounRule_359() throws IOException {
        assertGood("Die Klientel der Partei.");
    }

    @Test
    public void testDetNounRule_360() throws IOException {
        assertBad("Der Haus ist groß", "Dem Haus", "Das Haus", "Der Häuser");
    }

    @Test
    public void testDetNounRule_361() throws IOException {
        assertBad("Aber der Haus ist groß", "dem Haus", "das Haus", "der Häuser");
    }

    @Test
    public void testDetNounRule_362() throws IOException {
        assertBad("Ich habe einen Feder gefunden.", "eine Feder", "einer Feder");
    }

    @Test
    public void testDetNounRule_363() throws IOException {
        assertGood("Wenn die Gott zugeschriebenen Eigenschaften stimmen, dann...");
    }

    @Test
    public void testDetNounRule_364() throws IOException {
        assertGood("Dieses Grünkern genannte Getreide ist aber nicht backbar.");
    }

    @Test
    public void testDetNounRule_365() throws IOException {
        assertGood("Außerdem unterstützt mich Herr Müller beim abheften");
    }

    @Test
    public void testDetNounRule_366() throws IOException {
        assertGood("Außerdem unterstützt mich Frau Müller beim abheften");
    }

    @Test
    public void testDetNounRule_367() throws IOException {
        assertBad("Der Zustand meiner Gehirns.");
    }

    @Test
    public void testDetNounRule_368() throws IOException {
        assertBad("Lebensmittel sind da, um den menschliche Körper zu ernähren.");
    }

    @Test
    public void testDetNounRule_369() throws IOException {
        assertBad("Geld ist da, um den menschliche Überleben sicherzustellen.");
    }

    @Test
    public void testDetNounRule_370() throws IOException {
        assertBad("Sie hatte das kleinen Kaninchen.");
    }

    @Test
    public void testDetNounRule_371() throws IOException {
        assertBad("Frau Müller hat das wichtigen Dokument gefunden.");
    }

    @Test
    public void testDetNounRule_372() throws IOException {
        assertBad("Ich gebe dir ein kleine Kaninchen.");
    }

    @Test
    public void testDetNounRule_373() throws IOException {
        assertBad("Ich gebe dir ein kleinen Kaninchen.");
    }

    @Test
    public void testDetNounRule_374() throws IOException {
        assertBad("Ich gebe dir ein kleinem Kaninchen.");
    }

    @Test
    public void testDetNounRule_375() throws IOException {
        assertBad("Ich gebe dir ein kleiner Kaninchen.");
    }

    @Test
    public void testDetNounRule_376() throws IOException {
        assertGood("Ich gebe dir ein kleines Kaninchen.");
    }

    @Test
    public void testDetNounRule_377() throws IOException {
        assertBad("Ich gebe dir das kleinen Kaninchen.");
    }

    @Test
    public void testDetNounRule_378() throws IOException {
        assertBad("Ich gebe dir das kleinem Kaninchen.");
    }

    @Test
    public void testDetNounRule_379() throws IOException {
        assertBad("Ich gebe dir das kleiner Kaninchen.");
    }

    @Test
    public void testDetNounRule_380() throws IOException {
        assertBad("Geprägt ist der Platz durch einen 142 Meter hoher Obelisken", "einen 142 Meter hohen Obelisken");
    }

    @Test
    public void testDetNounRule_381() throws IOException {
        assertBad("Es birgt für mich ein überraschend hohes juristische Risiko.", "ein überraschend hohes juristisches Risiko");
    }

    @Test
    public void testDetNounRule_382() throws IOException {
        assertBad("Es birgt für mich ein zu hohes juristische Risiko.", "ein zu hohes juristisches Risiko");
    }

    @Test
    public void testDetNounRule_383() throws IOException {
        assertGood("Ich gebe dir das kleine Kaninchen.");
    }

    @Test
    public void testDetNounRule_384() throws IOException {
        assertGood("Die Top 3 der Umfrage");
    }

    @Test
    public void testDetNounRule_385() throws IOException {
        assertGood("Dein Vorschlag befindet sich unter meinen Top 5.");
    }

    @Test
    public void testDetNounRule_386() throws IOException {
        assertGood("Unter diesen rief das großen Unmut hervor.");
    }

    @Test
    public void testDetNounRule_387() throws IOException {
        assertGood("Bei mir löste das Panik aus.");
    }

    @Test
    public void testDetNounRule_388() throws IOException {
        assertGood("Sie können das machen in dem sie die CAD.pdf öffnen.");
    }

    @Test
    public void testDetNounRule_389() throws IOException {
        assertGood("Ich mache eine Ausbildung zur Junior Digital Marketing Managerin.");
    }

    @Test
    public void testDetNounRule_390() throws IOException {
        assertGood("Dann wird das Konsequenzen haben.");
    }

    @Test
    public void testDetNounRule_391() throws IOException {
        assertGood("Dann hat das Konsequenzen.");
    }

    @Test
    public void testDetNounRule_392() throws IOException {
        assertGood("Sollte das Konsequenzen nach sich ziehen?");
    }

    @Test
    public void testDetNounRule_393() throws IOException {
        assertGood("Der Echo Show von Amazon");
    }

    @Test
    public void testDetNounRule_394() throws IOException {
        assertGood("Die BVG kommen immer zu spät.");
    }

    @Test
    public void testDetNounRule_395() throws IOException {
        assertGood("In der Frühe der Nacht.");
    }

    @Test
    public void testDetNounRule_396() throws IOException {
        assertGood("Der TV Steinfurt.");
    }

    @Test
    public void testDetNounRule_397() throws IOException {
        assertGood("Ein ID 3 von Volkswagen.");
    }

    @Test
    public void testDetNounRule_398() throws IOException {
        assertGood("Der ID.3 von Volkswagen.");
    }

    @Test
    public void testDetNounRule_399() throws IOException {
        assertGood("Der ID3 von Volkswagen.");
    }

    @Test
    public void testDetNounRule_400() throws IOException {
        assertGood("Das bedeutet Krieg!");
    }

    @Test
    public void testDetNounRule_401() throws IOException {
        assertGood("Im Tun zu sein verhindert Prokrastination.");
    }

    @Test
    public void testDetNounRule_402() throws IOException {
        assertBad("Hier steht Ihre Text.");
    }

    @Test
    public void testDetNounRule_403() throws IOException {
        assertBad("Hier steht ihre Text.");
    }

    @Test
    public void testDetNounRule_404() throws IOException {
        assertBad("Antje Last, Inhaberin des Berliner Kult Hotels Auberge, freute sich ebenfalls über die Gastronomenfamilie aus Bayern.");
    }

    @Test
    public void testDetNounRule_405() throws IOException {
        assertBad("Das ist doch lächerlich, was ist denn das für ein Klinik?");
    }

    @Test
    public void testDetNounRule_406() throws IOException {
        assertGood("Das ist doch lächerlich, was ist denn das für eine Klinik?");
    }

    @Test
    public void testDetNounRule_407() throws IOException {
        assertGood("Was ist denn das für ein Typ?");
    }

    @Test
    public void testDetNounRule_408() throws IOException {
        assertGood("Hier geht's zur Customer Journey.");
    }

    @Test
    public void testDetNounRule_409() throws IOException {
        assertGood("Das führt zur Verbesserung der gesamten Customer Journey.");
    }

    @Test
    public void testDetNounRule_410() throws IOException {
        assertGood("Meint er das wirklich Ernst?");
    }

    @Test
    public void testDetNounRule_411() throws IOException {
        assertGood("Meinen Sie das Ernst?");
    }

    @Test
    public void testDetNounRule_412() throws IOException {
        assertGood("Die können sich in unserer Hall of Fame verewigen.");
    }

    @Test
    public void testDetNounRule_413() throws IOException {
        assertGood("Die können sich in unserer neuen Hall of Fame verewigen.");
    }

    @Test
    public void testDetNounRule_414() throws IOException {
        assertGood("Auch, wenn das weite Teile der Bevölkerung betrifft.");
    }

    @Test
    public void testDetNounRule_415() throws IOException {
        assertGood("Hat das Einfluss auf Ihr Trinkverhalten?");
    }

    @Test
    public void testDetNounRule_416() throws IOException {
        assertGood("Ihr wisst aber schon, dass das Blödsinn ist.");
    }

    @Test
    public void testDetNounRule_417() throws IOException {
        assertBad("Ich weiß nicht mehr, was unser langweiligen Thema war.");
    }

    @Test
    public void testDetNounRule_418() throws IOException {
        assertGood("Aber mein Wissen über die Antike ist ausbaufähig.");
    }

    @Test
    public void testDetNounRule_419() throws IOException {
        assertBad("Er ging ins Küche.");
    }

    @Test
    public void testDetNounRule_420() throws IOException {
        assertBad("Er ging ans Luft.");
    }

    @Test
    public void testDetNounRule_421() throws IOException {
        assertBad("Eine Niereninsuffizienz führt zur Störungen des Wasserhaushalts.");
    }

    @Test
    public void testDetNounRule_422() throws IOException {
        assertBad("Er stieg durchs Fensters.");
    }

    @Test
    public void testDetNounRule_423() throws IOException {
        assertBad("Ich habe heute ein Krankenwagen gesehen.");
    }

    @Test
    public void testDetNounRule_424() throws IOException {
        assertGood("Sie werden merken, dass das echte Nutzer sind.");
    }

    @Test
    public void testDetNounRule_425() throws IOException {
        assertGood("Dieses neue Mac OS trug den Codenamen Rhapsody.");
    }

    @Test
    public void testDetNounRule_426() throws IOException {
        assertGood("Das Mac OS is besser als Windows.");
    }

    @Test
    public void testDetNounRule_427() throws IOException {
        assertGood("Damit steht das Porsche Museum wie kaum ein anderes Museum für Lebendigkeit und Abwechslung.");
    }

    @Test
    public void testDetNounRule_428() throws IOException {
        assertGood("Weitere Krankenhäuser sind dass Eastern Shore Memorial Hospital, IWK Health Centre, Nova Scotia Hospital und das Queen Elizabeth II Health Sciences Centre.");
    }

    @Test
    public void testDetNounRule_429() throws IOException {
        assertGood("Ich bin von Natur aus ein sehr neugieriger Mensch.");
    }

    @Test
    public void testDetNounRule_430() throws IOException {
        assertGood("Ich bin auf der Suche nach einer Junior Developerin.");
    }

    @Test
    public void testDetNounRule_431() throws IOException {
        assertGood("War das Eifersucht?");
    }

    @Test
    public void testDetNounRule_432() throws IOException {
        assertGood("Waren das schwierige Entscheidungen?");
    }

    @Test
    public void testDetNounRule_433() throws IOException {
        assertGood("Soll das Demokratie sein?");
    }

    @Test
    public void testDetNounRule_434() throws IOException {
        assertGood("Hat das Spaß gemacht?");
    }

    @Test
    public void testDetNounRule_435() throws IOException {
        assertBad("Funktioniert das Software auch mit Windows?");
    }

    @Test
    public void testDetNounRule_436() throws IOException {
        assertGood("Soll das Sinn stiften?");
    }

    @Test
    public void testDetNounRule_437() throws IOException {
        assertGood("Soll das Freude machen?");
    }

    @Test
    public void testDetNounRule_438() throws IOException {
        assertGood("Die Trial ist ausgelaufen.");
    }

    @Test
    public void testDetNounRule_439() throws IOException {
        assertGood("Ein geworbener Neukunde interagiert zusätzlich mit dem Unternehmen.");
    }

    @Test
    public void testDetNounRule_440() throws IOException {
        assertGood("Ich weiß, dass jeder LanguageTool benutzen sollte.");
    }

    @Test
    public void testDetNounRule_441() throws IOException {
        assertGood("1992 übernahm die damalige Ernst Klett Schulbuchverlag GmbH, Stuttgart, den reprivatisierten Verlag Haack Gotha");
    }

    @Test
    public void testDetNounRule_442() throws IOException {
        assertGood("Überlegst du dir einen ID.3 zu leasen?");
    }

    @Test
    public void testDetNounRule_443() throws IOException {
        assertGood("Der Deutsch Langhaar ist ein mittelgroßer Jagdhund");
    }

    @Test
    public void testDetNounRule_444() throws IOException {
        assertGood("Eine Lösung die Spaß macht");
    }

    @Test
    public void testDetNounRule_445() throws IOException {
        assertGood("Mir machte das Spaß.");
    }

    @Test
    public void testDetNounRule_446() throws IOException {
        assertGood("Wir möchten nicht, dass irgendjemand Fragen stellt.");
    }

    @Test
    public void testDetNounRule_447() throws IOException {
        assertGood("Die Multiple Sklerose hat 1000 Gesichter.");
    }

    @Test
    public void testDetNounRule_448() throws IOException {
        assertGood("Na ja, einige nennen das Freundschaft plus, aber das machen wir besser nicht.");
    }

    @Test
    public void testDetNounRule_449() throws IOException {
        assertGood("Vogue, eigentlich als B-Seite der letzten Like A Prayer-Auskopplung Keep It Together gedacht, wurde kurzfristig als eigenständige Single herausgebracht");
    }

    @Test
    public void testDetNounRule_450() throws IOException {
        assertGood("..., die laufend Gewaltsituationen ausgeliefert sind");
    }

    @Test
    public void testDetNounRule_451() throws IOException {
        assertGood("Dann folgte die Festnahme der dringend Tatverdächtigen.");
    }

    @Test
    public void testDetNounRule_452() throws IOException {
        assertGood("Von der ersten Spielminute an machten die Münsteraner Druck und ...");
    }

    @Test
    public void testDetNounRule_453() throws IOException {
        assertGood("Wenn diese Prognose bestätigt wird, wird empfohlen, dass Unternehmen die gefährliche Güter benötigen, die Transporte am Montag und Dienstag machen.");
    }

    @Test
    public void testDetNounRule_454() throws IOException {
        assertGood("Ich habe meine Projektidee (die riesiges finanzielles Potenzial hat) an einen Unternehmenspräsidenten geschickt.");
    }

    @Test
    public void testDetNounRule_455() throws IOException {
        assertGood("Als weitere Rechtsquelle gelten gelegentlich noch immer der Londoner Court of Appeal und das britische House of Lords.");
    }

    @Test
    public void testDetNounRule_456() throws IOException {
        assertGood("Die Evangelische Kirche befindet sich in der Bad Sodener Altstadt direkt neben dem Quellenpark.");
    }

    @Test
    public void testDetNounRule_457() throws IOException {
        assertGood("Der volle Windows 10 Treibersupport");
    }

    @Test
    public void testDetNounRule_458() throws IOException {
        assertGood("Zugleich stärkt es die renommierte Berliner Biodiversitätsforschung.");
    }

    @Test
    public void testDetNounRule_459() throws IOException {
        assertGood("Der Windows 10 Treibersupport");
    }

    @Test
    public void testDetNounRule_460() throws IOException {
        assertGood("Kennt irgendwer Tipps wie Kopfhörer länger halten?");
    }

    @Test
    public void testDetNounRule_461() throws IOException {
        assertGood("George Lucas 1999 über seine sechsteilige Star Wars Saga.");
    }

    @Test
    public void testDetNounRule_462() throws IOException {
        assertGood("… und von denen mehrere Gegenstand staatsanwaltlicher Ermittlungen waren.");
    }

    @Test
    public void testDetNounRule_463() throws IOException {
        assertGood("Natürlich ist das Quatsch!");
    }

    @Test
    public void testDetNounRule_464() throws IOException {
        assertGood("Die Xi Jinping Ära ist …");
    }

    @Test
    public void testDetNounRule_465() throws IOException {
        assertGood("Die letzte unter Windows 98 lauffähige Version ist 5.1.");
    }

    @Test
    public void testDetNounRule_466() throws IOException {
        assertGood("Das veranlasste Bürgermeister Adam, selbst tätig zu werden, denn er wollte es nicht zulassen, dass in seiner Stadt Notleidende ohne Hilfe dastehen.");
    }

    @Test
    public void testDetNounRule_467() throws IOException {
        assertGood("Die südlichste Düsseldorfer Rheinbrücke ist die Fleher Brücke, eine Schrägseilbrücke mit dem höchsten Brückenpylon in Deutschland und einer Vielzahl von fächerförmig angeordneten Seilen.");
    }

    @Test
    public void testDetNounRule_468() throws IOException {
        assertGood("Ein zeitweise wahres Stakkato an einschlägigen Patenten, das Benz & Cie.");
    }

    @Test
    public void testDetNounRule_469() throws IOException {
        assertGood("Wem Rugby nicht sehr geläufig ist, dem wird auch das Six Nations nicht viel sagen.");
    }

    @Test
    public void testDetNounRule_470() throws IOException {
        assertGood("Eine Boeing 767 der Air China stürzt beim Landeanflug in ein Waldgebiet.");
    }

    @Test
    public void testDetNounRule_471() throws IOException {
        assertGood("Wir sind immer offen für Mitarbeiter die Teil eines der traditionellsten Malerbetriebe auf dem Platz Zürich werden möchten.");
    }

    @Test
    public void testDetNounRule_472() throws IOException {
        assertGood("Gelingt das mit Erregern rechtzeitig, könnte das Infektionen sogar oft verhindern.");
    }

    @Test
    public void testDetNounRule_473() throws IOException {
        assertGood("In der aktuellen Niedrigzinsphase bedeutet das sehr geringe Zinsen, die aber deutlich ansteigen können.");
    }

    @Test
    public void testDetNounRule_474() throws IOException {
        assertGood("Es gibt viele Stock Screener.");
    }

    @Test
    public void testDetNounRule_475() throws IOException {
        assertBad("So soll er etwa Texte des linken Literaturwissenschaftler Helmut Lethen mit besonderem Interesse gelesen haben.");
    }

    @Test
    public void testDetNounRule_476() throws IOException {
        assertBad("Auf dieser Website werden allerdings keine solche Daten weiterverxxx.");
    }

    @Test
    public void testDetNounRule_477() throws IOException {
        assertBad("Bei größeren Gruppen und/oder mehrere Tagen gibts einen nennenswerten Nachlass.");
    }

    @Test
    public void testDetNounRule_478() throws IOException {
        assertGood("Wir gehen zur Learning Academy.");
    }

    @Test
    public void testDetNounRule_479() throws IOException {
        assertGood("Es ist ein stiller Bank Run.");
    }

    @Test
    public void testDetNounRule_480() throws IOException {
        assertGood("Whirlpool Badewanne der europäische Marke SPAtec Modell Infinity.");
    }

    @Test
    public void testDetNounRule_481() throws IOException {
        assertGood("1944 eroberte diese weite Teile von Südosteuropa.");
    }

    @Test
    public void testDetNounRule_482() throws IOException {
        assertGood("Auch die Monopolstellung des staatlichen All India Radio, das in 24 Sprachen sendet");
    }

    @Test
    public void testDetNounRule_483() throws IOException {
        assertGood("Das schwedischen Entwicklerstudio MachineGames hat uns vor drei Jahren mit Wolfenstein: The New Order positiv überrascht.");
    }

    @Test
    public void testDetNounRule_484() throws IOException {
        assertGood("In einem normalen Joint habe es etwa ein halbes Gramm Hanf.");
    }

    @Test
    public void testDetNounRule_485() throws IOException {
        assertGood("den leidenschaftlichen Lobpreis der texanischen Gateway Church aus");
    }

    @Test
    public void testDetNounRule_486() throws IOException {
        assertGood("die gegnerischen Shooting Guards");
    }

    @Test
    public void testDetNounRule_487() throws IOException {
        assertGood("Bald läppert sich das zu richtigem Geld zusammen.");
    }

    @Test
    public void testDetNounRule_488() throws IOException {
        assertGood("Die Weimarer Parks laden ja förmlich ein zu Fotos im öffentlichen Raum.");
    }

    @Test
    public void testDetNounRule_489() throws IOException {
        assertGood("Es is schwierig für mich, diese zu Sätzen zu verbinden.");
    }

    @Test
    public void testDetNounRule_490() throws IOException {
        assertGood("Es kam zum einen zu technischen Problemen, zum anderen wurde es unübersichtlich.");
    }

    @Test
    public void testDetNounRule_491() throws IOException {
        assertGood("Das Spiel wird durch den zu neuer Größe gewachsenen Torwart dominiert.");
    }

    @Test
    public void testDetNounRule_492() throws IOException {
        assertGood("Dort findet sich schlicht und einfach alles & das zu sagenhafter Hafenkulisse.");
    }

    @Test
    public void testDetNounRule_493() throws IOException {
        assertGood("Man darf gespannt sein, wen Müller für diese Aufgabe gewinnt.");
    }

    @Test
    public void testDetNounRule_494() throws IOException {
        assertGood("Das Vereinslokal in welchem Zusammenkünfte stattfinden.");
    }

    @Test
    public void testDetNounRule_495() throws IOException {
        assertGood("Er lässt niemanden zu Wort kommen.");
    }

    @Test
    public void testDetNounRule_496() throws IOException {
        assertGood("Es war eine alles in allem spannende Geschichte.");
    }

    @Test
    public void testDetNounRule_497() throws IOException {
        assertGood("Eine mehrere hundert Meter lange Startbahn.");
    }

    @Test
    public void testDetNounRule_498() throws IOException {
        assertGood("Wir müssen jetzt um ein vielfaches höhere Preise zahlen.");
    }

    @Test
    public void testDetNounRule_499() throws IOException {
        assertGood("Und eine von manchem geforderte Übergewinnsteuer.");
    }

    @Test
    public void testDetNounRule_500() throws IOException {
        assertGood("Sie hat niemandem wirkliches Leid zugefügt.");
    }

    @Test
    public void testDetNounRule_501() throws IOException {
        assertGood("Die Organe eines gerade Verstorbenen");
    }

    @Test
    public void testDetNounRule_502() throws IOException {
        assertGood("Da wusste keiner Bescheid bezüglich dieser Sache.");
    }

    @Test
    public void testDetNounRule_503() throws IOException {
        assertGood("Es braucht keiner Bescheid wissen.");
    }

    @Test
    public void testDetNounRule_504() throws IOException {
        assertGood("Das sind auch beides staatliche Organe.");
    }

    @Test
    public void testDetNounRule_505() throws IOException {
        assertGood("Ein Haus für die weniger Glücklichen.");
    }

    @Test
    public void testDetNounRule_506() throws IOException {
        assertGood("Wir können sowas Mittwoch machen.");
    }

    @Test
    public void testDetNounRule_507() throws IOException {
        assertGood("Den schlechter Verdienenden geht es schlecht.");
    }

    @Test
    public void testDetNounRule_508() throws IOException {
        assertGood("Mit der weit weniger bekannten Horrorkomödie begann ihre Karriere.");
    }

    @Test
    public void testDetNounRule_509() throws IOException {
        assertBad("Mit der weit weniger bekannte Horrorkomödie begann ihre Karriere.", "der weit weniger bekannten Horrorkomödie");
    }

    @Test
    public void testDetNounRule_510() throws IOException {
        assertGood("Die Adelmanns wohnen in Herford.");
    }

    @Test
    public void testDetNounRule_511() throws IOException {
        assertBad("Die Idee des Werbekaufmann kam gut an.");
    }

    @Test
    public void testDetNounRule_512() throws IOException {
        assertGood("Die Idee des Werbekaufmanns kam gut an.");
    }

    @Test
    public void testDetNounRule_513() throws IOException {
        assertGood("Solch harte Worte!");
    }

    @Test
    public void testDetNounRule_514() throws IOException {
        assertGood("Ich habe es an unseren amerikanischen Commercial Lawyer geschickt.");
    }

    @Test
    public void testDetNounRule_515() throws IOException {
        assertGood("Dieser eine Schritt hat gedauert.");
    }

    @Test
    public void testDetNounRule_516() throws IOException {
        assertGood("Es besteht durchaus die Gefahr, dass die Telekom eine solch starke monopolistische Stellung auf dem Markt hat, dass sich kaum Wettbewerb entfalten kann.");
    }

    @Test
    public void testDetNounRule_517() throws IOException {
        assertBad("Ich habe keine Zeit für solche kleinlichen Belangen.");
    }

    @Test
    public void testDetNounRule_518() throws IOException {
        assertGood("Wenn ein Tiger einen Menschen tötet, ist das Grausamkeit.");
    }

    @Test
    public void testDetNounRule_519() throws IOException {
        assertGood("Kombinieren Sie diese zu ganzen Bewegungsprogrammen");
    }

    @Test
    public void testDetNounRule_520() throws IOException {
        assertBad("Einen Dämonen wird er nicht aufhalten.");
    }

    @Test
    public void testDetNounRule_521() throws IOException {
        assertBad("Das versetzte den Kronprinz in Schrecken.");
    }

    @Test
    public void testDetNounRule_522() throws IOException {
        assertGood("Erst später wurde Kritik hauptsächlich an den Plänen zu einem Patriot Act II laut.");
    }

    @Test
    public void testDetNounRule_523() throws IOException {
        assertGood("Laut Charlie XCX selbst sind das Personen, die vielleicht eine ...");
    }

    @Test
    public void testDetNounRule_524() throws IOException {
        assertBad("Solche kleinen Anbietern nutzen dann eines der drei großen Mobilfunknetze Deutschlands.");
    }

    @Test
    public void testDetNounRule_525() throws IOException {
        assertGood("Solch frivolen Gedanken wollen wir gar nicht erst nachgehen.");
    }

    @Test
    public void testDetNounRule_526() throws IOException {
        assertGood("Er erwartete solch aggressives Verhalten.");
    }

    @Test
    public void testDetNounRule_527() throws IOException {
        assertGood("Eine solch schöne Frau.");
    }

    @Test
    public void testDetNounRule_528() throws IOException {
        assertGood("Einer solch schönen Frau.");
    }

    @Test
    public void testDetNounRule_529() throws IOException {
        assertGood("Ein solch schöner Tisch.");
    }

    @Test
    public void testDetNounRule_530() throws IOException {
        assertGood("Ein solch schöner neuer Tisch.");
    }

    @Test
    public void testDetNounRule_531() throws IOException {
        assertGood("Eine solch begnadete Fotografin mit dabei zu haben und Tipps für die Fotosession zu bekommen, wäre schon toll.");
    }

    @Test
    public void testDetNounRule_532() throws IOException {
        assertGood("Wie können wir als globale Gemeinschaft solch brennende Themen wie Klimawandel und Rezession in entwickelten Märkten in Angriff nehmen?");
    }

    @Test
    public void testDetNounRule_533() throws IOException {
        assertGood("Wir waren überrascht, von ihm solch beißende Bemerkungen über seinen besten Freund zu hören.");
    }

    @Test
    public void testDetNounRule_534() throws IOException {
        assertGood("Worten, wohl gewählt, wohnt solch große Macht inne.");
    }

    @Test
    public void testDetNounRule_535() throws IOException {
        assertGood("Warum gelingt es den Stuten die Hengste in solch großen Rennen zu schlagen.");
    }

    @Test
    public void testDetNounRule_536() throws IOException {
        assertGood("Eine solch gute Beratung kann natürlich nicht kostenlos sein, daher lassen Sie mich bitte wissen welche Kosten für die PKV und BU-Beratung auf mich zukommen werden.");
    }

    @Test
    public void testDetNounRule_537() throws IOException {
        assertGood("Ein solch großer Ausbruch außerhalb des Nahen Ostens ist eine neue Entwicklung, heißt es weiter.");
    }

    @Test
    public void testDetNounRule_538() throws IOException {
        assertGood("Natürlich dürfen unsere Sporteinheiten nicht fehlen, vor allem nicht bei solch gutem Essen.");
    }

    @Test
    public void testDetNounRule_539() throws IOException {
        assertGood("Wie konnten Sie solch harte Songs gegen Ihre Familie schreiben?");
    }

    @Test
    public void testDetNounRule_540() throws IOException {
        assertGood("Zur Krönung auf dem schönsten Aussichtsberg in der Ferienregion Tirol West gelegen, bietet die Venet Gipfelhütte alle Annehmlichkeiten, die man sich auf solch hohem Niveau (2.212 m) wünschen kann.");
    }

    @Test
    public void testDetNounRule_541() throws IOException {
        assertGood("Vor allem nicht einen, der sich ein solch hohen Ballbesitz organisierte und die Berliner nicht zur Entfaltung kommen ließ.");
    }

    @Test
    public void testDetNounRule_542() throws IOException {
        assertGood("Du solltest im Winter keinen solch hohen Berg besteigen.");
    }

    @Test
    public void testDetNounRule_543() throws IOException {
        assertGood("Man darf an dieser Stelle fragen, wie ein solch hoher Verlust zustande kommt, wo doch der Stuttgarter Weg aus Sparen bestand.");
    }

    @Test
    public void testDetNounRule_544() throws IOException {
        assertGood("Kann der Patient eine solch lange Operation überstehen?");
    }

    @Test
    public void testDetNounRule_545() throws IOException {
        assertGood("Er kennt sich aus mit solch monumentalen Projekten.");
    }

    @Test
    public void testDetNounRule_546() throws IOException {
        assertGood("Eine solch schöne hübsche Frau.");
    }

    @Test
    public void testDetNounRule_547() throws IOException {
        assertGood("Bisher hat Gül einen solch offenen Affront gegen die ErdoganRegierung vermieden.");
    }

    @Test
    public void testDetNounRule_548() throws IOException {
        assertBad("..., das heißt solche natürliche Personen, welche unsere Leistungen in Anspruch nehmen, ...");
    }

    @Test
    public void testDetNounRule_549() throws IOException {
        assertBad("Der Erwerb solcher kultureller Güter ist natürlich stark an das ökonomische Kapital gebunden.");
    }

    @Test
    public void testDetNounRule_550() throws IOException {
        assertGood("Umso dankbarer bin ich für Brüder, die klare Kante in theologischer Hinsicht zeigen und nachvollziehbar die Bibel auch in solch schwierigen unpopulären Themen auslegen.");
    }

    @Test
    public void testDetNounRule_551() throws IOException {
        assertBad("Wir haben das Abo beendet und des Betrag erstattet.");
    }

    @Test
    public void testZurReplacement_1() throws IOException {
        assertBad("Hier geht's zur Schrank.", "zum Schrank");
    }

    @Test
    public void testZurReplacement_2() throws IOException {
        assertBad("Hier geht's zur Schränken.", "zum Schränken", "zu Schränken");
    }

    @Test
    public void testZurReplacement_3() throws IOException {
        assertBad("Hier geht's zur Männern.", "zu Männern");
    }

    @Test
    public void testZurReplacement_4() throws IOException {
        assertBad("Hier geht's zur Portal.", "zum Portal");
    }

    @Test
    public void testZurReplacement_5() throws IOException {
        assertBad("Hier geht's zur Portalen.", "zu Portalen");
    }

    @Test
    public void testZurReplacement_6() throws IOException {
        assertBad("Sie gehen zur Frauen.", "zu Frauen", "zur Frau");
    }

    @Test
    public void testZurReplacement_7() throws IOException {
        assertBad("Niereninsuffizienz führt zur Störungen des Wasserhaushalts.", "zu Störungen", "zur Störung");
    }

    @Test
    public void testZurReplacement_8() throws IOException {
        assertBad("Das Motiv wird in der Klassik auch zur Darstellungen übernommen.", "zu Darstellungen", "zur Darstellung");
    }

    @Test
    public void testZurReplacement_9() throws IOException {
        assertGood("Hier geht's zur Sonne.");
    }

    @Test
    public void testZurReplacement_10() throws IOException {
        assertGood("Hier geht's zum Schrank.");
    }

    @Test
    public void testZurReplacement_11() throws IOException {
        assertGood("Niereninsuffizienz führt zu Störungen des Wasserhaushalts.");
    }

    @Test
    public void testZurReplacement_12() throws IOException {
        assertGood("Das hat der fließend Englisch sprechende Mitarbeiter veranlasst.");
    }

    @Test
    public void testVieleWenige_1() throws IOException {
        assertGood("Zusammenschluss mehrerer dörflicher Siedlungen an einer Furt");
    }

    @Test
    public void testVieleWenige_2() throws IOException {
        assertGood("Für einige markante Szenen");
    }

    @Test
    public void testVieleWenige_3() throws IOException {
        assertGood("Für einige markante Szenen baute Hitchcock ein Schloss.");
    }

    @Test
    public void testVieleWenige_4() throws IOException {
        assertGood("Haben Sie viele glückliche Erfahrungen in Ihrer Kindheit gemacht?");
    }

    @Test
    public void testVieleWenige_5() throws IOException {
        assertGood("Es gibt viele gute Sachen auf der Welt.");
    }

    @Test
    public void testVieleWenige_6() throws IOException {
        assertGood("Viele englische Wörter haben lateinischen Ursprung");
    }

    @Test
    public void testVieleWenige_7() throws IOException {
        assertGood("Ein Bericht über Fruchtsaft, einige ähnliche Erzeugnisse und Fruchtnektar");
    }

    @Test
    public void testVieleWenige_8() throws IOException {
        assertGood("Der Typ, der seit einiger Zeit immer wieder hierher kommt.");
    }

    @Test
    public void testVieleWenige_9() throws IOException {
        assertGood("Jede Schnittmenge abzählbar vieler offener Mengen");
    }

    @Test
    public void testVieleWenige_10() throws IOException {
        assertGood("Es kam zur Fusion der genannten und noch einiger weiterer Unternehmen.");
    }

    @Test
    public void testVieleWenige_11() throws IOException {
        assertGood("Zu dieser Fragestellung gibt es viele unterschiedliche Meinungen.");
    }

    @Test
    public void testVieleWenige_12() throws IOException {
        assertGood("Wir zeigen die Gründe auf, wieso noch nicht jeder solche Anschlüsse hat.");
    }

    @Test
    public void testDetAdjNounRule_1() throws IOException {
        assertGood("Die Übernahme der früher selbständigen Gesellschaft");
    }

    @Test
    public void testDetAdjNounRule_2() throws IOException {
        assertGood("Das ist, weil man oft bei anderen schreckliches Essen vorgesetzt bekommt.");
    }

    @Test
    public void testDetAdjNounRule_3() throws IOException {
        assertGood("Das ist der riesige Tisch.");
    }

    @Test
    public void testDetAdjNounRule_4() throws IOException {
        assertGood("Der riesige Tisch ist groß.");
    }

    @Test
    public void testDetAdjNounRule_5() throws IOException {
        assertGood("Die Kanten der der riesigen Tische.");
    }

    @Test
    public void testDetAdjNounRule_6() throws IOException {
        assertGood("Den riesigen Tisch mag er.");
    }

    @Test
    public void testDetAdjNounRule_7() throws IOException {
        assertGood("Es mag den riesigen Tisch.");
    }

    @Test
    public void testDetAdjNounRule_8() throws IOException {
        assertGood("Die Kante des riesigen Tisches.");
    }

    @Test
    public void testDetAdjNounRule_9() throws IOException {
        assertGood("Dem riesigen Tisch fehlt was.");
    }

    @Test
    public void testDetAdjNounRule_10() throws IOException {
        assertGood("Die riesigen Tische sind groß.");
    }

    @Test
    public void testDetAdjNounRule_11() throws IOException {
        assertGood("Der riesigen Tische wegen.");
    }

    @Test
    public void testDetAdjNounRule_12() throws IOException {
        assertGood("An der roten Ampel.");
    }

    @Test
    public void testDetAdjNounRule_13() throws IOException {
        assertGood("Dann hat das natürlich Nachteile.");
    }

    @Test
    public void testDetAdjNounRule_14() throws IOException {
        assertGood("Ihre erste Nr. 1");
    }

    @Test
    public void testDetAdjNounRule_15() throws IOException {
        assertGood("Wir bedanken uns bei allen Teams.");
    }

    @Test
    public void testDetAdjNounRule_16() throws IOException {
        assertGood("Als Heinrich versuchte, seinen Kandidaten für den Mailänder Bischofssitz durchzusetzen, reagierte der Papst sofort.");
    }

    @Test
    public void testDetAdjNounRule_17() throws IOException {
        assertGood("Den neuen Finanzierungsweg wollen sie daher Hand in Hand mit dem Leser gehen.");
    }

    @Test
    public void testDetAdjNounRule_18() throws IOException {
        assertGood("Lieber den Spatz in der Hand...");
    }

    @Test
    public void testDetAdjNounRule_19() throws IOException {
        assertGood("Wir wollen sein ein einzig Volk von Brüdern");
    }

    @Test
    public void testDetAdjNounRule_20() throws IOException {
        assertGood("Eine Zeitreise durch die 68er Revolte");
    }

    @Test
    public void testDetAdjNounRule_21() throws IOException {
        assertGood("Ich besitze ein Modell aus der 300er Reihe.");
    }

    @Test
    public void testDetAdjNounRule_22() throws IOException {
        assertGood("Aber ansonsten ist das erste Sahne");
    }

    @Test
    public void testDetAdjNounRule_23() throws IOException {
        assertGood("...damit diese ausreichend Sauerstoff geben.");
    }

    @Test
    public void testDetAdjNounRule_24() throws IOException {
        assertGood("...als auch die jedem zukommende Freiheit.");
    }

    @Test
    public void testDetAdjNounRule_25() throws IOException {
        assertGood("...als auch die daraus jedem zukommende Freiheit.");
    }

    @Test
    public void testDetAdjNounRule_26() throws IOException {
        assertGood("Damit zeigen wir, wie bedeutungsreich manche deutsche Begriffe sein können.");
    }

    @Test
    public void testDetAdjNounRule_27() throws IOException {
        assertGood("Damit zeigen wir, wie bedeutungsreich manche deutschen Begriffe sein können.");
    }

    @Test
    public void testDetAdjNounRule_28() throws IOException {
        assertGood("2009 gab es im Rathaus daher Bestrebungen ein leichter handhabbares Logo einzuführen.");
    }

    @Test
    public void testDetAdjNounRule_29() throws IOException {
        assertGood("Das ist eine leichter handhabbare Situation.");
    }

    @Test
    public void testDetAdjNounRule_30() throws IOException {
        assertGood("Es gibt viele verschiedene Stock Screener.");
    }

    @Test
    public void testDetAdjNounRule_31() throws IOException {
        assertGood("Die Ware umräumen, um einer anderen genügend Platz zu schaffen.");
    }

    @Test
    public void testDetAdjNounRule_32() throws IOException {
        assertBad("Er hatte ein anstrengenden Tag", "ein anstrengender Tag", "ein anstrengendes Tag", "einen anstrengenden Tag", "einem anstrengenden Tag");
    }

    @Test
    public void testDetAdjNounRule_33() throws IOException {
        assertBad("Es sind die riesigen Tisch.");
    }

    @Test
    public void testDetAdjNounRule_34() throws IOException {
        assertBad("Als die riesigen Tischs kamen.");
    }

    @Test
    public void testDetAdjNounRule_35() throws IOException {
        assertBad("Als die riesigen Tisches kamen.");
    }

    @Test
    public void testDetAdjNounRule_36() throws IOException {
        assertBad("Der riesigen Tisch und so.");
    }

    @Test
    public void testDetAdjNounRule_37() throws IOException {
        assertBad("An der roter Ampel.");
    }

    @Test
    public void testDetAdjNounRule_38() throws IOException {
        assertBad("An der rote Ampel.");
    }

    @Test
    public void testDetAdjNounRule_39() throws IOException {
        assertBad("An der rotes Ampel.");
    }

    @Test
    public void testDetAdjNounRule_40() throws IOException {
        assertBad("An der rotem Ampel.");
    }

    @Test
    public void testDetAdjNounRule_41() throws IOException {
        assertBad("Er hatte ihn aus dem 1,4 Meter tiefem Wasser gezogen.", "dem 1,4 Meter tiefen Wasser");
    }

    @Test
    public void testDetAdjNounRule_42() throws IOException {
        assertBad("Das ist ein sehr schönes Tisch.", "ein sehr schöner Tisch");
    }

    @Test
    public void testDetAdjNounRule_43() throws IOException {
        assertBad("Er hatte eine sehr schweren Infektion.");
    }

    @Test
    public void testDetAdjNounRule_44() throws IOException {
        assertBad("Ein fast 5 Meter hohem Haus.");
    }

    @Test
    public void testDetAdjNounRule_45() throws IOException {
        assertBad("Ein fünf Meter hohem Haus.");
    }

    @Test
    public void testDetAdjNounRule_46() throws IOException {
        assertBad("Es wurden Karavellen eingesetzt, da diese für die flachen Gewässern geeignet waren.");
    }

    @Test
    public void testDetAdjNounRule_47() throws IOException {
        assertBad("Wir bedanken uns bei allem Teams.");
    }

    @Test
    public void testDetAdjNounRule_48() throws IOException {
        assertBad("Dabei geht es um das altbekannte Frage der Dynamiken der Eigenbildung..");
    }

    @Test
    public void testDetAdjNounRule_49() throws IOException {
        assertBad("Den neue Finanzierungsweg wollen sie daher Hand in Hand mit dem Leser gehen.");
    }

    @Test
    public void testDetAdjNounRule_50() throws IOException {
        assertBad("Den neuen Finanzierungsweg wollen sie daher Hand in Hand mit dem Lesern gehen.");
    }

    @Test
    public void testDetAdjNounRule_51() throws IOException {
        assertBad("Ich widerrufe den mit Ihnen geschlossene Vertrag.", "der mit Ihnen geschlossene Vertrag", "den mit Ihnen geschlossenen Vertrag");
    }

    @Test
    public void testDetAdjNounRule_52() throws IOException {
        assertGood("Ich widerrufe den mit Ihnen geschlossenen Vertrag.");
    }

    @Test
    public void testDetAdjNounRule_53() throws IOException {
        assertBad("Er klagte auch gegen den ohne ihn verkündete Sachbeschluss.", "den ohne ihn verkündeten Sachbeschluss");
    }

    @Test
    public void testDetAdjNounRule_54() throws IOException {
        assertGood("Er klagte auch gegen den ohne ihn verkündeten Sachbeschluss.");
    }

    @Test
    public void testDetAdjNounRule_55() throws IOException {
        assertGood("Dieser relativ gesehen starke Mann.");
    }

    @Test
    public void testDetAdjNounRule_56() throws IOException {
        assertGood("Diese relativ gesehen starke Frau.");
    }

    @Test
    public void testDetAdjNounRule_57() throws IOException {
        assertGood("Dieses relativ gesehen starke Auto.");
    }

    @Test
    public void testDetAdjNounRule_58() throws IOException {
        assertGood("Es kann gut sein, dass bei sowas echte Probleme erkannt werden.");
    }

    @Test
    public void testDetAdjAdjNounRule_1() throws IOException {
        assertGood("Das verlangt reifliche Überlegung.");
    }

    @Test
    public void testDetAdjAdjNounRule_2() throws IOException {
        assertGood("Das bedeutet private Versicherungssummen ab 100€.");
    }

    @Test
    public void testDetAdjAdjNounRule_3() throws IOException {
        assertGood("Das erfordert einigen Mut.");
    }

    @Test
    public void testDetAdjAdjNounRule_4() throws IOException {
        assertGood("Die abnehmend aufwendige Gestaltung der Portale...");
    }

    @Test
    public void testDetAdjAdjNounRule_5() throws IOException {
        assertGood("Die strahlend roten Blumen.");
    }

    @Test
    public void testDetAdjAdjNounRule_6() throws IOException {
        assertGood("Der weiter vorhandene Widerstand konnte sich nicht durchsetzen.");
    }

    @Test
    public void testDetAdjAdjNounRule_7() throws IOException {
        assertGood("Das jetzige gemeinsame Ergebnis...");
    }

    @Test
    public void testDetAdjAdjNounRule_8() throws IOException {
        assertGood("Das früher übliche Abdecken mit elementarem Schwefel...");
    }

    @Test
    public void testDetAdjAdjNounRule_9() throws IOException {
        assertGood("Das einzig wirklich Schöne...");
    }

    @Test
    public void testDetAdjAdjNounRule_10() throws IOException {
        assertGood("Andere weniger bekannte Vorschläge waren „Konsistenter Empirismus“ oder...");
    }

    @Test
    public void testDetAdjAdjNounRule_11() throws IOException {
        assertGood("Werden mehrere solcher physikalischen Elemente zu einer Einheit zusammengesetzt...");
    }

    @Test
    public void testDetAdjAdjNounRule_12() throws IOException {
        assertGood("Aufgrund ihrer weniger guten Bonitätslage.");
    }

    @Test
    public void testDetAdjAdjNounRule_13() throws IOException {
        assertGood("Mit ihren teilweise eigenwilligen Außenformen...");
    }

    @Test
    public void testDetAdjAdjNounRule_14() throws IOException {
        assertGood("Die deutsche Kommasetzung bedarf einiger technischer Ausarbeitung.");
    }

    @Test
    public void testDetAdjAdjNounRule_15() throws IOException {
        assertGood("Die deutsche Kommasetzung bedarf einiger guter technischer Ausarbeitung.");
    }

    @Test
    public void testDetAdjAdjNounRule_16() throws IOException {
        assertBad("Das ist eine solides strategisches Fundament", "ein solides strategisches Fundament");
    }

    @Test
    public void testDetAdjAdjNounRule_17() throws IOException {
        assertBad("Das ist eine solide strategisches Fundament", "ein solides strategisches Fundament");
    }

    @Test
    public void testDetAdjAdjNounRule_18() throws IOException {
        assertBad1("Das ist eine solide strategische Fundament", "ein solides strategisches Fundament");
    }

    @Test
    public void testDetAdjAdjNounRule_19() throws IOException {
        assertBad("Das ist ein solide strategisches Fundament", "ein solides strategisches Fundament");
    }

    @Test
    public void testDetAdjAdjNounRule_20() throws IOException {
        assertBad("Das ist ein solides strategische Fundament", "ein solides strategisches Fundament");
    }

    @Test
    public void testDetAdjAdjNounRule_21() throws IOException {
        assertBad("Das ist ein solides strategisches Fundamente", "ein solides strategisches Fundament");
    }

    @Test
    public void testDetAdjAdjNounRule_22() throws IOException {
        assertBad("Das ist ein solides strategisches Fundaments", "ein solides strategisches Fundament");
    }

    @Test
    public void testDetAdjAdjNounRule_23() throws IOException {
        assertBad("Die deutsche Kommasetzung bedarf einiger technisches Ausarbeitung.");
    }

    @Test
    public void testDetAdjAdjNounRule_24() throws IOException {
        assertBad("Die deutsche Kommasetzung bedarf einiger guter technische Ausarbeitung.");
    }

    @Test
    public void testKonUntArtDefSub_1() throws IOException {
        assertGood("Wieso verstehst du nicht, dass das komplett verschiedene Dinge sind?");
    }

    @Test
    public void testKonUntArtDefSub_2() throws IOException {
        assertGood("Ich frage mich sehr, ob die wirklich zusätzliche Gebühren abdrücken wollen");
    }

    @Test
    public void testKonUntArtDefSub_3() throws IOException {
        assertBad("Dies wurde durchgeführt um das moderne Charakter zu betonen.", "den modernen Charakter");
    }

    @Test
    public void testKonUntArtDefSub_4() throws IOException {
        assertBad("Nur bei Topfpflanzung ist eine regelmäßige Düngung wichtig, da die normalen Bodenbildungsprozessen nicht stattfinden.", "die normalen Bodenbildungsprozesse", "den normalen Bodenbildungsprozessen");
    }

    @Test
    public void testKonUntArtDefSub_5() throws IOException {
        assertBad("Die Höhe kommt oft darauf an, ob die richtigen Leuten gut mit einen können oder nicht.");
    }

    @Test
    public void testBugFixes_1() throws IOException {
        assertBad("Denn die einzelnen sehen sich einer sehr verschieden starken Macht des...", "einer sehr verschiedenen starken Macht");
    }

    @Test
    public void testBugFixes_2() throws IOException {
        assertGood("Das passiert nur, wenn der zu Pflegende bereit ist.");
    }

    @Test
    public void testBugFixes_3() throws IOException {
        assertGood("Peter, iss nicht meine");
    }
}
