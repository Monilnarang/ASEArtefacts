package org.languagetool.rules.de;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.language.German;
import java.io.IOException;
import static org.junit.Assert.*;
import static org.languagetool.rules.patterns.StringMatcher.regexp;

public class CaseRuleTest_Purified {

    private CaseRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new CaseRule(TestTools.getMessages("de"), (German) Languages.getLanguageForShortCode("de-DE"));
        lt = new JLanguageTool(Languages.getLanguageForShortCode("de-DE"));
    }

    private void assertGood(String input) throws IOException {
        assertEquals("Did not expect error in: '" + input + "'", 0, rule.match(lt.getAnalyzedSentence(input)).length);
    }

    private void assertBad(String input) throws IOException {
        assertEquals("Did not find expected error in: '" + input + "'", 1, rule.match(lt.getAnalyzedSentence(input)).length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertGood("(Dauer, Raum, Anwesende)");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("Es gibt wenige Befragte.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertGood("Es gibt weniger Befragte, die das machen würden.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertGood("Es gibt mehr Befragte, die das machen würden.");
    }

    @Test
    public void testRule_5() throws IOException {
        assertGood("Das ist eine Abkehr von Gottes Geboten.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertGood("Dem Hund Futter geben");
    }

    @Test
    public void testRule_7() throws IOException {
        assertGood("Heute spricht Frau Stieg.");
    }

    @Test
    public void testRule_8() throws IOException {
        assertGood("So könnte es auch den Handwerksbetrieben gehen, die ausbilden und deren Ausbildung dann Industriebetrieben zugutekäme.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertGood("Die Firma Drosch hat nicht pünktlich geliefert.");
    }

    @Test
    public void testRule_10() throws IOException {
        assertGood("3.1 Technische Dokumentation");
    }

    @Test
    public void testRule_11() throws IOException {
        assertGood("Ein einfacher Satz zum Testen.");
    }

    @Test
    public void testRule_12() throws IOException {
        assertGood("Das Laufen fällt mir leicht.");
    }

    @Test
    public void testRule_13() throws IOException {
        assertGood("Das Winseln stört.");
    }

    @Test
    public void testRule_14() throws IOException {
        assertGood("Das schlägt nicht so zu Buche.");
    }

    @Test
    public void testRule_15() throws IOException {
        assertGood("Dirk Hetzel ist ein Name.");
    }

    @Test
    public void testRule_16() throws IOException {
        assertGood("Aber sie tat es, sodass unsere Klasse das sehen und fotografieren konnte.");
    }

    @Test
    public void testRule_17() throws IOException {
        assertGood("Sein Verhalten war okay.");
    }

    @Test
    public void testRule_18() throws IOException {
        assertGood("Hier ein Satz. \"Ein Zitat.\"");
    }

    @Test
    public void testRule_19() throws IOException {
        assertGood("Hier ein Satz. 'Ein Zitat.'");
    }

    @Test
    public void testRule_20() throws IOException {
        assertGood("Hier ein Satz. «Ein Zitat.»");
    }

    @Test
    public void testRule_21() throws IOException {
        assertGood("Hier ein Satz. »Ein Zitat.«");
    }

    @Test
    public void testRule_22() throws IOException {
        assertGood("Hier ein Satz. (Noch einer.)");
    }

    @Test
    public void testRule_23() throws IOException {
        assertGood("Hier geht es nach Tel Aviv.");
    }

    @Test
    public void testRule_24() throws IOException {
        assertGood("Unser Jüngster ist da.");
    }

    @Test
    public void testRule_25() throws IOException {
        assertGood("Alles Erfundene ist wahr.");
    }

    @Test
    public void testRule_26() throws IOException {
        assertGood("Sie hat immer ihr Bestes getan.");
    }

    @Test
    public void testRule_27() throws IOException {
        assertGood("Er wird etwas Verrücktes träumen.");
    }

    @Test
    public void testRule_28() throws IOException {
        assertGood("Er wird etwas schön Verrücktes träumen.");
    }

    @Test
    public void testRule_29() throws IOException {
        assertGood("Er wird etwas ganz schön Verrücktes träumen.");
    }

    @Test
    public void testRule_30() throws IOException {
        assertGood("Mit aufgewühltem Innerem.");
    }

    @Test
    public void testRule_31() throws IOException {
        assertGood("Mit völlig aufgewühltem Innerem.");
    }

    @Test
    public void testRule_32() throws IOException {
        assertGood("Er wird etwas so Verrücktes träumen.");
    }

    @Test
    public void testRule_33() throws IOException {
        assertGood("Tom ist etwas über dreißig.");
    }

    @Test
    public void testRule_34() throws IOException {
        assertGood("Diese Angriffe bleiben im Verborgenen.");
    }

    @Test
    public void testRule_35() throws IOException {
        assertGood("Ihr sollt mich das wissen lassen.");
    }

    @Test
    public void testRule_36() throws IOException {
        assertGood("Wenn er mich das rechtzeitig wissen lässt, gerne.");
    }

    @Test
    public void testRule_37() throws IOException {
        assertGood("Und sein völlig aufgewühltes Inneres erzählte von den Geschehnissen.");
    }

    @Test
    public void testRule_38() throws IOException {
        assertGood("Aber sein aufgewühltes Inneres erzählte von den Geschehnissen.");
    }

    @Test
    public void testRule_39() throws IOException {
        assertGood("Sein aufgewühltes Inneres erzählte von den Geschehnissen.");
    }

    @Test
    public void testRule_40() throws IOException {
        assertGood("Aber sein Inneres erzählte von den Geschehnissen.");
    }

    @Test
    public void testRule_41() throws IOException {
        assertGood("Ein Kaninchen, das zaubern kann.");
    }

    @Test
    public void testRule_42() throws IOException {
        assertGood("Keine Ahnung, wie ich das prüfen sollte.");
    }

    @Test
    public void testRule_43() throws IOException {
        assertGood("Und dann noch Strafrechtsdogmatikerinnen.");
    }

    @Test
    public void testRule_44() throws IOException {
        assertGood("Er kann ihr das bieten, was sie verdient.");
    }

    @Test
    public void testRule_45() throws IOException {
        assertGood("Das fragen sich mittlerweile viele.");
    }

    @Test
    public void testRule_46() throws IOException {
        assertGood("Ich habe gehofft, dass du das sagen würdest.");
    }

    @Test
    public void testRule_47() throws IOException {
        assertGood("Eigentlich hätte ich das wissen müssen.");
    }

    @Test
    public void testRule_48() throws IOException {
        assertGood("Mir tut es wirklich leid, Ihnen das sagen zu müssen.");
    }

    @Test
    public void testRule_49() throws IOException {
        assertGood("Der Wettkampf endete im Unentschieden.");
    }

    @Test
    public void testRule_50() throws IOException {
        assertGood("Er versuchte, Neues zu tun.");
    }

    @Test
    public void testRule_51() throws IOException {
        assertGood("Du musst das wissen, damit du die Prüfung bestehst");
    }

    @Test
    public void testRule_52() throws IOException {
        assertGood("Er kann ihr das bieten, was sie verdient.");
    }

    @Test
    public void testRule_53() throws IOException {
        assertGood("Er fragte, ob das gelingen wird.");
    }

    @Test
    public void testRule_54() throws IOException {
        assertGood("Er mag Obst, wie zum Beispel Apfelsinen.");
    }

    @Test
    public void testRule_55() throws IOException {
        assertGood("Er will die Ausgaben für Umweltschutz und Soziales kürzen.");
    }

    @Test
    public void testRule_56() throws IOException {
        assertGood("Die Musicalverfilmung „Die Schöne und das Biest“ bricht mehrere Rekorde.");
    }

    @Test
    public void testRule_57() throws IOException {
        assertGood("Joachim Sauer lobte Johannes Rau.");
    }

    @Test
    public void testRule_58() throws IOException {
        assertGood("Im Falle des Menschen ist dessen wirkendes Wollen gegeben.");
    }

    @Test
    public void testRule_59() throws IOException {
        assertGood("Szenario: 1) Zwei Galaxien verschmelzen.");
    }

    @Test
    public void testRule_60() throws IOException {
        assertGood("Existieren Außerirdische im Universum?");
    }

    @Test
    public void testRule_61() throws IOException {
        assertGood("Tom vollbringt Außerordentliches.");
    }

    @Test
    public void testRule_62() throws IOException {
        assertGood("Er führt Böses im Schilde.");
    }

    @Test
    public void testRule_63() throws IOException {
        assertGood("Es gab Überlebende.");
    }

    @Test
    public void testRule_64() throws IOException {
        assertGood("'Wir werden das stoppen.'");
    }

    @Test
    public void testRule_65() throws IOException {
        assertGood("Wahre Liebe muss das aushalten.");
    }

    @Test
    public void testRule_66() throws IOException {
        assertGood("Du kannst das machen.");
    }

    @Test
    public void testRule_67() throws IOException {
        assertGood("Vor dem Aus stehen.");
    }

    @Test
    public void testRule_68() throws IOException {
        assertGood("Ich Armer!");
    }

    @Test
    public void testRule_69() throws IOException {
        assertGood("Hallo Malte,");
    }

    @Test
    public void testRule_70() throws IOException {
        assertGood("Parks Vertraute Choi Soon Sil ist zu drei Jahren Haft verurteilt worden.");
    }

    @Test
    public void testRule_71() throws IOException {
        assertGood("Bei einer Veranstaltung Rechtsextremer passierte es.");
    }

    @Test
    public void testRule_72() throws IOException {
        assertGood("Eine Gruppe Betrunkener singt.");
    }

    @Test
    public void testRule_73() throws IOException {
        assertGood("Bei Betreten des Hauses.");
    }

    @Test
    public void testRule_74() throws IOException {
        assertGood("Das Aus für Italien ist bitter.");
    }

    @Test
    public void testRule_75() throws IOException {
        assertGood("Das Aus kam unerwartet.");
    }

    @Test
    public void testRule_76() throws IOException {
        assertGood("Anmeldung bis Fr. 1.12.");
    }

    @Test
    public void testRule_77() throws IOException {
        assertGood("Gibt es die Schuhe auch in Gr. 43?");
    }

    @Test
    public void testRule_78() throws IOException {
        assertGood("Weil er Unmündige sexuell missbraucht haben soll, wurde ein Lehrer verhaftet.");
    }

    @Test
    public void testRule_79() throws IOException {
        assertGood("Tausende Gläubige kamen.");
    }

    @Test
    public void testRule_80() throws IOException {
        assertGood("Es kamen Tausende Gläubige.");
    }

    @Test
    public void testRule_81() throws IOException {
        assertGood("Das schließen Forscher aus den gefundenen Spuren.");
    }

    @Test
    public void testRule_82() throws IOException {
        assertGood("Wieder Verletzter bei Unfall");
    }

    @Test
    public void testRule_83() throws IOException {
        assertGood("Eine Gruppe Aufständischer verwüstete die Bar.");
    }

    @Test
    public void testRule_84() throws IOException {
        assertGood("‚Dieser Satz.‘ Hier kommt der nächste Satz.");
    }

    @Test
    public void testRule_85() throws IOException {
        assertGood("Dabei werden im Wesentlichen zwei Prinzipien verwendet:");
    }

    @Test
    public void testRule_86() throws IOException {
        assertGood("Er fragte, ob das gelingen oder scheitern wird.");
    }

    @Test
    public void testRule_87() throws IOException {
        assertGood("Einen Tag nach Bekanntwerden des Skandals");
    }

    @Test
    public void testRule_88() throws IOException {
        assertGood("Das machen eher die Erwachsenen.");
    }

    @Test
    public void testRule_89() throws IOException {
        assertGood("Das ist ihr Zuhause.");
    }

    @Test
    public void testRule_90() throws IOException {
        assertGood("Das ist Sandras Zuhause.");
    }

    @Test
    public void testRule_91() throws IOException {
        assertGood("Das machen eher wohlhabende Leute.");
    }

    @Test
    public void testRule_92() throws IOException {
        assertGood("Als Erstes würde ich sofort die Struktur ändern.");
    }

    @Test
    public void testRule_93() throws IOException {
        assertGood("Er sagte: Als Erstes würde ich sofort die Struktur ändern.");
    }

    @Test
    public void testRule_94() throws IOException {
        assertGood("Das schaffen moderne E-Autos locker.");
    }

    @Test
    public void testRule_95() throws IOException {
        assertGood("Das schaffen moderne E-Autos schneller");
    }

    @Test
    public void testRule_96() throws IOException {
        assertGood("Das schaffen moderne und effizientere E-Autos schneller.");
    }

    @Test
    public void testRule_97() throws IOException {
        assertGood("Das verwalten User.");
    }

    @Test
    public void testRule_98() throws IOException {
        assertGood("Man kann das generalisieren");
    }

    @Test
    public void testRule_99() throws IOException {
        assertGood("Aber wie wir das machen und sicher gestalten, darauf konzentriert sich unsere Arbeit.");
    }

    @Test
    public void testRule_100() throws IOException {
        assertGood("Vielleicht kann man das erweitern");
    }

    @Test
    public void testRule_101() throws IOException {
        assertGood("Vielleicht soll er das generalisieren");
    }

    @Test
    public void testRule_102() throws IOException {
        assertGood("Wahrscheinlich müssten sie das überarbeiten");
    }

    @Test
    public void testRule_103() throws IOException {
        assertGood("Assistenzsysteme warnen rechtzeitig vor Gefahren.");
    }

    @Test
    public void testRule_104() throws IOException {
        assertGood("Jeremy Schulte rannte um sein Leben.");
    }

    @Test
    public void testRule_105() throws IOException {
        assertGood("Er arbeitet im Bereich Präsidiales.");
    }

    @Test
    public void testRule_106() throws IOException {
        assertGood("Er spricht Sunnitisch & Schiitisch.");
    }

    @Test
    public void testRule_107() throws IOException {
        assertGood("Er sagte, Geradliniges und Krummliniges sei unvergleichbar.");
    }

    @Test
    public void testRule_108() throws IOException {
        assertGood("Dort erfahren sie Kurioses und Erstaunliches zum Zusammenspiel von Mensch und Natur.");
    }

    @Test
    public void testRule_109() throws IOException {
        assertGood("Dabei unterscheidet die Shareware zwischen Privatem und Dienstlichem bei Fahrten ebenso wie bei Autos.");
    }

    @Test
    public void testRule_110() throws IOException {
        assertGood("Besucher erwartet Handegefertigtes, Leckeres und Informatives rund um den Hund.");
    }

    @Test
    public void testRule_111() throws IOException {
        assertGood("Der Unterschied zwischen Vorstellbarem und Machbarem war niemals geringer.");
    }

    @Test
    public void testRule_112() throws IOException {
        assertGood("Das war Fiete Lang.");
    }

    @Test
    public void testRule_113() throws IOException {
        assertGood("Wenn du an das glaubst, was du tust, kannst du Großes erreichen.");
    }

    @Test
    public void testRule_114() throws IOException {
        assertGood("Dann hat er Großes erreicht.");
    }

    @Test
    public void testRule_115() throws IOException {
        assertGood("Dann hat er Großes geleistet.");
    }

    @Test
    public void testRule_116() throws IOException {
        assertGood("Das Thema Datenaustauschverfahren ist mir wichtig.");
    }

    @Test
    public void testRule_117() throws IOException {
        assertGood("Ist das eine Frage ? Müsste das nicht anders sein?");
    }

    @Test
    public void testRule_118() throws IOException {
        assertGood("Das ist ein Satz !!! Das auch.");
    }

    @Test
    public void testRule_119() throws IOException {
        assertGood("Der russische Erdölmagnat Emanuel Nobel, der Erbauer des ersten Dieselmotorschiffes.");
    }

    @Test
    public void testRule_120() throws IOException {
        assertGood("Zur Versöhnung: Jüdische Gläubige sollen beten.");
    }

    @Test
    public void testRule_121() throws IOException {
        assertGood("Fast im Stundentakt wurden neue Infizierte gemeldet.");
    }

    @Test
    public void testRule_122() throws IOException {
        assertGood("Bert Van Den Brink");
    }

    @Test
    public void testRule_123() throws IOException {
        assertGood("“In den meisten Bundesländern werden solche Studien per se nicht durchgeführt.”");
    }

    @Test
    public void testRule_124() throws IOException {
        assertGood("Aber “in den meisten Bundesländern werden solche Studien per se nicht durchgeführt.”");
    }

    @Test
    public void testRule_125() throws IOException {
        assertGood("A) Das Haus");
    }

    @Test
    public void testRule_126() throws IOException {
        assertGood("Rabi und Polykarp Kusch an der Columbia-Universität");
    }

    @Test
    public void testRule_127() throws IOException {
        assertGood("Man geht davon aus, dass es sich dabei nicht um Reinigungsverhalten handelt.");
    }

    @Test
    public void testRule_128() throws IOException {
        assertGood("Wenn dort oft Gefahren lauern.");
    }

    @Test
    public void testRule_129() throws IOException {
        assertGood("3b) Den Bereich absichern");
    }

    @Test
    public void testRule_130() throws IOException {
        assertGood("@booba Da der Holger keine Zeit hat ...");
    }

    @Test
    public void testRule_131() throws IOException {
        assertGood("Es gibt infizierte Ärzt*innen.");
    }

    @Test
    public void testRule_132() throws IOException {
        assertGood("WUrzeln");
    }

    @Test
    public void testRule_133() throws IOException {
        assertGood("🙂 Übrigens finde ich dein neues Ordnungssystem richtig genial!");
    }

    @Test
    public void testRule_134() throws IOException {
        assertGood("Ein 10,4 Ah Lithium-Akku");
    }

    @Test
    public void testRule_135() throws IOException {
        assertGood("14:15 Uhr SpVgg Westheim");
    }

    @Test
    public void testRule_136() throws IOException {
        assertGood("Unser Wärmestrom-Tarif WärmeKompakt im Detail");
    }

    @Test
    public void testRule_137() throws IOException {
        assertGood("Autohaus Dornig GmbH");
    }

    @Test
    public void testRule_138() throws IOException {
        assertGood("Hans Pries GmbH");
    }

    @Test
    public void testRule_139() throws IOException {
        assertGood("Der Kund*innenservice war auch sehr kulant und persönlich.");
    }

    @Test
    public void testRule_140() throws IOException {
        assertGood(":D Auf dieses Frl.");
    }

    @Test
    public void testRule_141() throws IOException {
        assertGood("@b_fischer Der Bonussemester-Antrag oder der Widerspruch?");
    }

    @Test
    public void testRule_142() throws IOException {
        assertGood("Das Gedicht “Der Panther”.");
    }

    @Test
    public void testRule_143() throws IOException {
        assertGood("Klar, dass wir das brauchen.");
    }

    @Test
    public void testRule_144() throws IOException {
        assertGood("Das wird Scholz' engster Vertrauter Wolfgang Schmidt übernehmen.");
    }

    @Test
    public void testRule_145() throws IOException {
        assertGood("Bei der Fülle an Vorgaben kann das schnell vergessen werden.");
    }

    @Test
    public void testRule_146() throws IOException {
        assertGood("Majid ergänzte: ”Vorläufigen Analysen der Terrakottaröhren aus Ardais liegen ...");
    }

    @Test
    public void testRule_147() throws IOException {
        assertGood("Ist das eine Frage ? Müsste das nicht anders sein?");
    }

    @Test
    public void testRule_148() throws IOException {
        assertGood("Das ist ein Satz !!! Das auch.");
    }

    @Test
    public void testRule_149() throws IOException {
        assertGood("Liebe Kund:in");
    }

    @Test
    public void testRule_150() throws IOException {
        assertGood("Wir sollten das mal labeln.");
    }

    @Test
    public void testRule_151() throws IOException {
        assertGood("Teil 1: Der unaufhaltsame Aufstieg Bonapartes");
    }

    @Test
    public void testRule_152() throws IOException {
        assertGood("Der Absatz bestimmt, in welchem Maße diese Daten Dritten zugänglich gemacht werden.");
    }

    @Test
    public void testRule_153() throws IOException {
        assertGood("Der TN spricht Russisch - Muttersprache");
    }

    @Test
    public void testRule_154() throws IOException {
        assertGood("Ich musste das Video mehrmals stoppen, um mir über das Gesagte Gedanken zu machen.");
    }

    @Test
    public void testRule_155() throws IOException {
        assertGood("Während Besagtes Probleme verursachte.");
    }

    @Test
    public void testRule_156() throws IOException {
        assertGood("Während der Befragte Geschichten erzählte.");
    }

    @Test
    public void testRule_157() throws IOException {
        assertGood("Während ein Befragter Geschichten erzählte.");
    }

    @Test
    public void testRule_158() throws IOException {
        assertGood("... für welche ein Befragter Geld ausgegeben hat.");
    }

    @Test
    public void testRule_159() throws IOException {
        assertGood("Während die Befragte Geld verdiente.");
    }

    @Test
    public void testRule_160() throws IOException {
        assertGood("Während die Besagte Geschichten erzählte.");
    }

    @Test
    public void testRule_161() throws IOException {
        assertGood("Sind dem Zahlungspflichtigen Kosten entstanden?");
    }

    @Test
    public void testRule_162() throws IOException {
        assertGood("Jetzt, wo Protestierende und Politiker sich streiten");
    }

    @Test
    public void testRule_163() throws IOException {
        assertGood("Während die Besagte Geld verdiente.");
    }

    @Test
    public void testRule_164() throws IOException {
        assertGood("Die Nacht, die Liebe, dazu der Wein — zu nichts Gutem Ratgeber sein.");
    }

    @Test
    public void testRule_165() throws IOException {
        assertGood("Warum tun die Menschen Böses?");
    }

    @Test
    public void testRule_166() throws IOException {
        assertGood("Und das Vergangene Revue passieren lassen");
    }

    @Test
    public void testRule_167() throws IOException {
        assertGood("Seither ist das Französische Amtssprache in Frankreich.");
    }

    @Test
    public void testRule_168() throws IOException {
        assertGood("Für die Betreute Kontoauszüge holen.");
    }

    @Test
    public void testRule_169() throws IOException {
        assertGood("Das verstehen Deutsche halt nicht.");
    }

    @Test
    public void testRule_170() throws IOException {
        assertGood("12:00 - 13:00 Gemeinsames Mittagessen");
    }

    @Test
    public void testRule_171() throws IOException {
        assertGood("12:00 Gemeinsames Mittagessen");
    }

    @Test
    public void testRule_172() throws IOException {
        assertGood("Meld dich, wenn du Großes vorhast.");
    }

    @Test
    public void testRule_173() throws IOException {
        assertGood("Muss nicht der Einzelne Einschränkungen der Freiheit hinnehmen, wenn die Sicherheit der Menschen und des Staates mehr gefährdet sind?");
    }

    @Test
    public void testRule_174() throws IOException {
        assertGood("Wie reißt ein Einzelner Millionen aus ihren Sitzen?");
    }

    @Test
    public void testRule_175() throws IOException {
        assertGood("Der Aphorismus will nicht Dumme gescheit, sondern Gescheite nachdenklich machen.");
    }

    @Test
    public void testRule_176() throws IOException {
        assertGood("Während des Hochwassers den Eingeschlossenen Wasser und Nahrung bringen");
    }

    @Test
    public void testRule_177() throws IOException {
        assertGood("Aus dem Stein der Weisen macht ein Dummer Schotter.");
    }

    @Test
    public void testRule_178() throws IOException {
        assertGood("Auf dem Weg zu ihnen begegnet der Halbwüchsige Revolverhelden und Indianern.");
    }

    @Test
    public void testRule_179() throws IOException {
        assertBad("Während des Hochwassers den Eingeschlossenen Menschen Nahrung bringen");
    }

    @Test
    public void testRule_180() throws IOException {
        assertBad("Während Gefragte Menschen antworteten.");
    }

    @Test
    public void testRule_181() throws IOException {
        assertBad("Ich brauche eine Gratis App die Ohne WLAN.");
    }

    @Test
    public void testRule_182() throws IOException {
        assertBad("Alle Kommunikationsmedien die Meinem Widersacher dienen werden.");
    }

    @Test
    public void testRule_183() throws IOException {
        assertBad("Ich wünsche dir Alles Liebe.");
    }

    @Test
    public void testRule_184() throws IOException {
        assertBad("Das Auto Meines Vaters wird in Italien produziert.");
    }

    @Test
    public void testRule_185() throws IOException {
        assertBad("Nach Böhm-Bawerk steht die Allgemeine Profitrate und die Theorie der Produktionspreise im Widerspruch zum Wertgesetz des ersten Bandes.");
    }

    @Test
    public void testRule_186() throws IOException {
        assertBad("Ich sehe da keine Absolute Schranke.");
    }

    @Test
    public void testRule_187() throws IOException {
        assertBad("Manns und Fontanes Gesammelten Werken.");
    }

    @Test
    public void testRule_188() throws IOException {
        assertBad("Und das Neue Haus.");
    }

    @Test
    public void testRule_189() throws IOException {
        assertBad("Das sind die Die Lehrer.");
    }

    @Test
    public void testRule_190() throws IOException {
        assertBad("An der flachen Decke zeigt ein Großes Bildnis die Geburt Christi und die ewige Anbetung der Hirten.");
    }

    @Test
    public void testRule_191() throws IOException {
        assertBad("Und das Gesagte Wort.");
    }

    @Test
    public void testRule_192() throws IOException {
        assertBad("Und die Gesagten Wörter.");
    }

    @Test
    public void testRule_193() throws IOException {
        assertBad("Und meine Erzählte Geschichte.");
    }

    @Test
    public void testRule_194() throws IOException {
        assertBad("Und diese Erzählten Geschichten.");
    }

    @Test
    public void testRule_195() throws IOException {
        assertBad("Und eine Neue Zeit.");
    }

    @Test
    public void testRule_196() throws IOException {
        assertGood("▶︎ Dies ist ein Test");
    }

    @Test
    public void testRule_197() throws IOException {
        assertGood("▶ Dies ist ein Test");
    }

    @Test
    public void testRule_198() throws IOException {
        assertGood("* Dies ist ein Test");
    }

    @Test
    public void testRule_199() throws IOException {
        assertGood("- Dies ist ein Test");
    }

    @Test
    public void testRule_200() throws IOException {
        assertGood("• Dies ist ein Test");
    }

    @Test
    public void testRule_201() throws IOException {
        assertGood(":-) Dies ist ein Test");
    }

    @Test
    public void testRule_202() throws IOException {
        assertGood(";-) Dies ist ein Test");
    }

    @Test
    public void testRule_203() throws IOException {
        assertGood(":) Dies ist ein Test");
    }

    @Test
    public void testRule_204() throws IOException {
        assertGood(";) Dies ist ein Test");
    }

    @Test
    public void testRule_205() throws IOException {
        assertGood("..., die ins Nichts griff.");
    }

    @Test
    public void testRule_206() throws IOException {
        assertGood("Er fragte, was sie über das denken und zwinkerte ihnen zu.");
    }

    @Test
    public void testRule_207() throws IOException {
        assertGood("dem Ägyptischen, Berberischen, Semitischen, Kuschitischen, Omotischen und dem Tschadischen");
    }

    @Test
    public void testRule_208() throws IOException {
        assertGood("mit S-Bahn-ähnlichen Verkehrsmitteln");
    }

    @Test
    public void testRule_209() throws IOException {
        assertGood("mit U-Bahn-ähnlichen und günstigen Verkehrsmitteln");
    }

    @Test
    public void testRule_210() throws IOException {
        assertGood("mit Ü-Ei-großen, schweren Hagelkörnern");
    }

    @Test
    public void testRule_211() throws IOException {
        assertGood("mit E-Musik-artigen, komplizierten Harmonien");
    }

    @Test
    public void testRule_212() throws IOException {
        assertGood("eBay International AG");
    }

    @Test
    public void testRule_213() throws IOException {
        assertGood("Harald & Schön");
    }

    @Test
    public void testRule_214() throws IOException {
        assertGood("Nicholas and Stark");
    }

    @Test
    public void testRule_215() throws IOException {
        assertGood("Die Schweizerische Bewachungsgesellschaft");
    }

    @Test
    public void testRule_216() throws IOException {
        assertBad("Das machen der Töne ist schwierig.");
    }

    @Test
    public void testRule_217() throws IOException {
        assertBad("Sie Vertraute niemandem.");
    }

    @Test
    public void testRule_218() throws IOException {
        assertBad("Beten Lernt man in Nöten.");
    }

    @Test
    public void testRule_219() throws IOException {
        assertBad("Ich habe Heute keine Zeit.");
    }

    @Test
    public void testRule_220() throws IOException {
        assertBad("Er sagte, Geradliniges und krummliniges sei unvergleichbar.");
    }

    @Test
    public void testRule_221() throws IOException {
        assertBad("Er sagte, ein Geradliniges und Krummliniges Konzept ist nicht tragbar.");
    }

    @Test
    public void testRule_222() throws IOException {
        assertBad("Ä Was?");
    }

    @Test
    public void testRule_223() throws IOException {
        assertBad("… die preiswerte Variante unserer Topseller im Bereich Alternativ Mehle.");
    }

    @Test
    public void testRule_224() throws IOException {
        assertBad("…  jahrzehntelangen Mitstreitern und vielen Freunden aus Nah und Fern.");
    }

    @Test
    public void testRule_225() throws IOException {
        assertBad("Hi und Herzlich willkommen auf meiner Seite.");
    }

    @Test
    public void testRule_226() throws IOException {
        assertBad("Er ist Groß.");
    }

    @Test
    public void testRule_227() throws IOException {
        assertBad("Die Zahl ging auf Über 1.000 zurück.");
    }

    @Test
    public void testRule_228() throws IOException {
        assertBad("Er sammelt Große und kleine Tassen.");
    }

    @Test
    public void testRule_229() throws IOException {
        assertBad("Er sammelt Große, mittlere und kleine Tassen.");
    }

    @Test
    public void testRule_230() throws IOException {
        assertBad("Dann will sie mit London Über das Referendum verhandeln.");
    }

    @Test
    public void testRule_231() throws IOException {
        assertBad("Sie kann sich täglich Über vieles freuen.");
    }

    @Test
    public void testRule_232() throws IOException {
        assertBad("Der Vater (51) Fuhr nach Rom.");
    }

    @Test
    public void testRule_233() throws IOException {
        assertBad("Er müsse Überlegen, wie er das Problem löst.");
    }

    @Test
    public void testRule_234() throws IOException {
        assertBad("Er sagte, dass er Über einen Stein stolperte.");
    }

    @Test
    public void testRule_235() throws IOException {
        assertBad("Tom ist etwas über Dreißig.");
    }

    @Test
    public void testRule_236() throws IOException {
        assertBad("Unser warten wird sich lohnen.");
    }

    @Test
    public void testRule_237() throws IOException {
        assertBad("Tom kann mit fast Allem umgehen.");
    }

    @Test
    public void testRule_238() throws IOException {
        assertBad("Dabei Übersah er sie.");
    }

    @Test
    public void testRule_239() throws IOException {
        assertBad("Der Brief wird am Mittwoch in Brüssel Übergeben.");
    }

    @Test
    public void testRule_240() throws IOException {
        assertBad("Damit sollen sie die Versorgung in der Region Übernehmen.");
    }

    @Test
    public void testRule_241() throws IOException {
        assertBad("Die Unfallursache scheint geklärt, ein Lichtsignal wurde Überfahren.");
    }

    @Test
    public void testRule_242() throws IOException {
        assertBad("Der Lenker hatte die Höchstgeschwindigkeit um 76 km/h Überschritten.");
    }

    @Test
    public void testRule_243() throws IOException {
        assertBad("Das sind 10 Millionen Euro, Gleichzeitig und zusätzlich.");
    }

    @Test
    public void testRule_244() throws IOException {
        assertGood("Stets suchte er das Extreme.");
    }

    @Test
    public void testRule_245() throws IOException {
        assertGood("Ich möchte zwei Kilo Zwiebeln.");
    }

    @Test
    public void testRule_246() throws IOException {
        assertGood("Ein Menschenfreund.");
    }

    @Test
    public void testRule_247() throws IOException {
        assertGood("Der Nachfahre.");
    }

    @Test
    public void testRule_248() throws IOException {
        assertGood("Hier ein Satz, \"Ein Zitat.\"");
    }

    @Test
    public void testRule_249() throws IOException {
        assertGood("Hier ein Satz, \"ein Zitat.\"");
    }

    @Test
    public void testRule_250() throws IOException {
        assertGood("Schon Le Monde schrieb das.");
    }

    @Test
    public void testRule_251() throws IOException {
        assertGood("In Blubberdorf macht man das so.");
    }

    @Test
    public void testRule_252() throws IOException {
        assertGood("Der Thriller spielt zur Zeit des Zweiten Weltkriegs");
    }

    @Test
    public void testRule_253() throws IOException {
        assertGood("Anders als physikalische Konstanten werden mathematische Konstanten unabhängig von jedem physikalischen Maß definiert.");
    }

    @Test
    public void testRule_254() throws IOException {
        assertGood("Eine besonders einfache Klasse bilden die polylogarithmischen Konstanten.");
    }

    @Test
    public void testRule_255() throws IOException {
        assertGood("Das südlich von Berlin gelegene Dörfchen.");
    }

    @Test
    public void testRule_256() throws IOException {
        assertGood("Weil er das kommen sah, traf er Vorkehrungen.");
    }

    @Test
    public void testRule_257() throws IOException {
        assertGood("Sie werden im Allgemeinen gefasst.");
    }

    @Test
    public void testRule_258() throws IOException {
        assertGood("Sie werden im allgemeinen Fall gefasst.");
    }

    @Test
    public void testRule_259() throws IOException {
        assertBad("Sie werden im Allgemeinen Fall gefasst.");
    }

    @Test
    public void testRule_260() throws IOException {
        assertGood("Das sind Euroscheine.");
    }

    @Test
    public void testRule_261() throws IOException {
        assertGood("John Stallman isst.");
    }

    @Test
    public void testRule_262() throws IOException {
        assertGood("Das ist die neue Gesellschafterin hier.");
    }

    @Test
    public void testRule_263() throws IOException {
        assertGood("Das ist die neue Dienerin hier.");
    }

    @Test
    public void testRule_264() throws IOException {
        assertGood("Das ist die neue Geigerin hier.");
    }

    @Test
    public void testRule_265() throws IOException {
        assertGood("Die ersten Gespanne erreichen Köln.");
    }

    @Test
    public void testRule_266() throws IOException {
        assertGood("Er beschrieb den Angeklagten wie einen Schuldigen");
    }

    @Test
    public void testRule_267() throws IOException {
        assertGood("Er beschrieb den Angeklagten wie einen Schuldigen.");
    }

    @Test
    public void testRule_268() throws IOException {
        assertGood("Es dauerte bis ins neunzehnte Jahrhundert");
    }

    @Test
    public void testRule_269() throws IOException {
        assertGood("Das ist das Dümmste, was ich je gesagt habe.");
    }

    @Test
    public void testRule_270() throws IOException {
        assertBad("Das ist das Dümmste Kind.");
    }

    @Test
    public void testRule_271() throws IOException {
        assertGood("Wacht auf, Verdammte dieser Welt!");
    }

    @Test
    public void testRule_272() throws IOException {
        assertGood("Er sagt, dass Geistliche davon betroffen sind.");
    }

    @Test
    public void testRule_273() throws IOException {
        assertBad("Er sagt, dass Geistliche Würdenträger davon betroffen sind.");
    }

    @Test
    public void testRule_274() throws IOException {
        assertBad("Er sagt, dass Geistliche und weltliche Würdenträger davon betroffen sind.");
    }

    @Test
    public void testRule_275() throws IOException {
        assertBad("Er ist begeistert Von der Fülle.");
    }

    @Test
    public void testRule_276() throws IOException {
        assertBad("Er wohnt Über einer Garage.");
    }

    @Test
    public void testRule_277() throws IOException {
        assertBad("Die Anderen 90 Prozent waren krank.");
    }

    @Test
    public void testRule_278() throws IOException {
        assertGood("Man sagt, Liebe mache blind.");
    }

    @Test
    public void testRule_279() throws IOException {
        assertGood("Die Deutschen sind sehr listig.");
    }

    @Test
    public void testRule_280() throws IOException {
        assertGood("Der Lesestoff bestimmt die Leseweise.");
    }

    @Test
    public void testRule_281() throws IOException {
        assertGood("Ich habe nicht viel von einem Reisenden.");
    }

    @Test
    public void testRule_282() throws IOException {
        assertGood("Die Vereinigten Staaten");
    }

    @Test
    public void testRule_283() throws IOException {
        assertGood("Der Satz vom ausgeschlossenen Dritten.");
    }

    @Test
    public void testRule_284() throws IOException {
        assertGood("Die Ausgewählten werden gut betreut.");
    }

    @Test
    public void testRule_285() throws IOException {
        assertGood("Die ausgewählten Leute werden gut betreut.");
    }

    @Test
    public void testRule_286() throws IOException {
        assertBad("Die Ausgewählten Leute werden gut betreut.");
    }

    @Test
    public void testRule_287() throws IOException {
        assertBad("Er war dort Im März 2000.");
    }

    @Test
    public void testRule_288() throws IOException {
        assertBad("Er war dort Im Jahr 96.");
    }

    @Test
    public void testRule_289() throws IOException {
        assertGood("Die Schlinge zieht sich zu.");
    }

    @Test
    public void testRule_290() throws IOException {
        assertGood("Die Schlingen ziehen sich zu.");
    }

    @Test
    public void testRule_291() throws IOException {
        assertGood("Sie fällt auf durch ihre hilfsbereite Art. Zudem zeigt sie soziale Kompetenz.");
    }

    @Test
    public void testRule_292() throws IOException {
        assertGood("Die Lieferadresse ist Obere Brandstr. 4-7");
    }

    @Test
    public void testRule_293() throws IOException {
        assertGood("Das ist es: kein Satz.");
    }

    @Test
    public void testRule_294() throws IOException {
        assertGood("Werner Dahlheim: Die Antike.");
    }

    @Test
    public void testRule_295() throws IOException {
        assertGood("1993: Der talentierte Mr. Ripley");
    }

    @Test
    public void testRule_296() throws IOException {
        assertGood("Ian Kershaw: Der Hitler-Mythos: Führerkult und Volksmeinung.");
    }

    @Test
    public void testRule_297() throws IOException {
        assertBad("Das ist es: Kein Satz.");
    }

    @Test
    public void testRule_298() throws IOException {
        assertBad("Wen magst du lieber: Die Giants oder die Dragons?");
    }

    @Test
    public void testRule_299() throws IOException {
        assertGood("Das wirklich Wichtige ist dies:");
    }

    @Test
    public void testRule_300() throws IOException {
        assertGood("Das wirklich wichtige Verfahren ist dies:");
    }

    @Test
    public void testRule_301() throws IOException {
        assertBad("Das wirklich Wichtige Verfahren ist dies:");
    }

    @Test
    public void testRule_302() throws IOException {
        assertBad("Die Schöne Tür");
    }

    @Test
    public void testRule_303() throws IOException {
        assertBad("Das Blaue Auto.");
    }

    @Test
    public void testRule_304() throws IOException {
        assertBad("Ein Einfacher Satz zum Testen.");
    }

    @Test
    public void testRule_305() throws IOException {
        assertBad("Eine Einfache Frage zum Testen?");
    }

    @Test
    public void testRule_306() throws IOException {
        assertBad("Er kam Früher als sonst.");
    }

    @Test
    public void testRule_307() throws IOException {
        assertBad("Er rennt Schneller als ich.");
    }

    @Test
    public void testRule_308() throws IOException {
        assertBad("Das Winseln Stört.");
    }

    @Test
    public void testRule_309() throws IOException {
        assertBad("Sein verhalten war okay.");
    }

    @Test
    public void testRule_310() throws IOException {
        assertEquals(1, lt.check("Karten werden vom Auswahlstapel gezogen. Auch […] Der Auswahlstapel gehört zum Inhalt.").size());
    }

    @Test
    public void testRule_311() throws IOException {
        assertEquals(0, lt.check("Karten werden vom Auswahlstapel gezogen. […] Der Auswahlstapel gehört zum Inhalt.").size());
    }

    @Test
    public void testRule_312() throws IOException {
        assertGood("Im Norwegischen klingt das schöner.");
    }

    @Test
    public void testRule_313() throws IOException {
        assertGood("Übersetzt aus dem Norwegischen von Ingenieur Frederik Dingsbums.");
    }

    @Test
    public void testRule_314() throws IOException {
        assertGood("Dem norwegischen Ingenieur gelingt das gut.");
    }

    @Test
    public void testRule_315() throws IOException {
        assertBad("Dem Norwegischen Ingenieur gelingt das gut.");
    }

    @Test
    public void testRule_316() throws IOException {
        assertGood("Peter Peterson, dessen Namen auf Griechisch Stein bedeutet.");
    }

    @Test
    public void testRule_317() throws IOException {
        assertGood("Peter Peterson, dessen Namen auf Griechisch gut klingt.");
    }

    @Test
    public void testRule_318() throws IOException {
        assertGood("Das dabei Erlernte und Erlebte ist sehr nützlich.");
    }

    @Test
    public void testRule_319() throws IOException {
        assertBad("Das dabei erlernte und Erlebte Wissen ist sehr nützlich.");
    }

    @Test
    public void testRule_320() throws IOException {
        assertGood("Ein Kapitän verlässt als Letzter das sinkende Schiff.");
    }

    @Test
    public void testRule_321() throws IOException {
        assertBad("Diese Regelung wurde als Überholt bezeichnet.");
    }

    @Test
    public void testRule_322() throws IOException {
        assertBad("Die Dolmetscherin und Der Vorleser gehen spazieren.");
    }

    @Test
    public void testRule_323() throws IOException {
        assertGood("Es hilft, die Harmonie zwischen Führer und Geführten zu stützen.");
    }

    @Test
    public void testRule_324() throws IOException {
        assertGood("Das Gebäude des Auswärtigen Amts.");
    }

    @Test
    public void testRule_325() throws IOException {
        assertGood("Das Gebäude des Auswärtigen Amtes.");
    }

    @Test
    public void testRule_326() throws IOException {
        assertGood("   Im Folgenden beschreibe ich das Haus.");
    }

    @Test
    public void testRule_327() throws IOException {
        assertGood("\"Im Folgenden beschreibe ich das Haus.\"");
    }

    @Test
    public void testRule_328() throws IOException {
        assertGood("Gestern habe ich 10 Spieße gegessen.");
    }

    @Test
    public void testRule_329() throws IOException {
        assertGood("Die Verurteilten wurden mit dem Fallbeil enthauptet.");
    }

    @Test
    public void testRule_330() throws IOException {
        assertGood("Den Begnadigten kam ihre Reue zugute.");
    }

    @Test
    public void testRule_331() throws IOException {
        assertGood("Die Zahl Vier ist gerade.");
    }

    @Test
    public void testRule_332() throws IOException {
        assertGood("Ich glaube, dass das geschehen wird.");
    }

    @Test
    public void testRule_333() throws IOException {
        assertGood("Ich glaube, dass das geschehen könnte.");
    }

    @Test
    public void testRule_334() throws IOException {
        assertGood("Ich glaube, dass mir das gefallen wird.");
    }

    @Test
    public void testRule_335() throws IOException {
        assertGood("Ich glaube, dass mir das gefallen könnte.");
    }

    @Test
    public void testRule_336() throws IOException {
        assertGood("Alldem wohnte etwas faszinierend Rätselhaftes inne.");
    }

    @Test
    public void testRule_337() throws IOException {
        assertGood("Schau mich an, Kleine!");
    }

    @Test
    public void testRule_338() throws IOException {
        assertGood("Schau mich an, Süßer!");
    }

    @Test
    public void testRule_339() throws IOException {
        assertGood("Weißt du, in welchem Jahr das geschehen ist?");
    }

    @Test
    public void testRule_340() throws IOException {
        assertGood("Das wissen viele nicht.");
    }

    @Test
    public void testRule_341() throws IOException {
        assertBad("Das sagen haben hier viele.");
    }

    @Test
    public void testRule_342() throws IOException {
        assertGood("Die zum Tode Verurteilten wurden in den Hof geführt.");
    }

    @Test
    public void testRule_343() throws IOException {
        assertGood("Wenn Sie das schaffen, retten Sie mein Leben!");
    }

    @Test
    public void testRule_344() throws IOException {
        assertGood("Etwas Grünes, Schleimiges klebte an dem Stein.");
    }

    @Test
    public void testRule_345() throws IOException {
        assertGood("Er befürchtet Schlimmeres.");
    }

    @Test
    public void testRule_346() throws IOException {
        assertBad("Bis Bald!");
    }

    @Test
    public void testRule_347() throws IOException {
        assertGood("#4 Aktuelle Situation");
    }

    @Test
    public void testRule_348() throws IOException {
        assertGood("Er trinkt ein kühles Blondes.");
    }

    @Test
    public void testRule_349() throws IOException {
        assertGood("* [ ] Ein GitHub Markdown Listenpunkt");
    }

    @Test
    public void testRule_350() throws IOException {
        assertGood("Tom ist ein engagierter, gutaussehender Vierzigjähriger, der...");
    }

    @Test
    public void testRule_351() throws IOException {
        assertGood("a.) Im Zusammenhang mit ...");
    }

    @Test
    public void testRule_352() throws IOException {
        assertGood("✔︎ Weckt Aufmerksamkeit.");
    }

    @Test
    public void testRule_353() throws IOException {
        assertGood("Hallo Eckhart,");
    }

    @Test
    public void testRule_354() throws IOException {
        assertGood("Er kann Polnisch und Urdu.");
    }

    @Test
    public void testRule_355() throws IOException {
        assertGood("---> Der USB 3.0 Stecker");
    }

    @Test
    public void testRule_356() throws IOException {
        assertGood("Black Lives Matter");
    }

    @Test
    public void testRule_357() throws IOException {
        assertGood("== Schrittweise Erklärung");
    }

    @Test
    public void testRule_358() throws IOException {
        assertGood("Audi A5 Sportback 2.0 TDI");
    }

    @Test
    public void testRule_359() throws IOException {
        assertGood("§ 1 Allgemeine Bedingungen");
    }

    @Test
    public void testRule_360() throws IOException {
        assertGood("§1 Allgemeine Bedingungen");
    }

    @Test
    public void testRule_361() throws IOException {
        assertGood("[H3] Was ist Daytrading?");
    }

    @Test
    public void testRule_362() throws IOException {
        assertGood(" Das ist das Aus des Airbus A380.");
    }

    @Test
    public void testRule_363() throws IOException {
        assertGood("Wir sollten ihr irgendwas Erotisches schenken.");
    }

    @Test
    public void testRule_364() throws IOException {
        assertGood("Er trank ein paar Halbe.");
    }

    @Test
    public void testRule_365() throws IOException {
        assertGood("Sie/Er hat Schuld.");
    }

    @Test
    public void testRule_366() throws IOException {
        assertGood("Das war irgendein Irrer.");
    }

    @Test
    public void testRule_367() throws IOException {
        assertGood("Wir wagen Neues.");
    }

    @Test
    public void testRule_368() throws IOException {
        assertGood("Grundsätzlich gilt aber: Essen Sie, was die Einheimischen Essen.");
    }

    @Test
    public void testRule_369() throws IOException {
        assertGood("Vielleicht reden wir später mit ein paar Einheimischen.");
    }

    @Test
    public void testRule_370() throws IOException {
        assertBad("Das existiert im Jazz zunehmend nicht mehr Bei der weiteren Entwicklung des Jazz zeigt sich das.");
    }

    @Test
    public void testRule_371() throws IOException {
        assertGood("Das denken zwar viele, ist aber total falsch.");
    }

    @Test
    public void testRule_372() throws IOException {
        assertGood("Ich habe nix Besseres gefunden.");
    }

    @Test
    public void testRule_373() throws IOException {
        assertGood("Ich habe nichts Besseres gefunden.");
    }

    @Test
    public void testRule_374() throws IOException {
        assertGood("Ich habe noch Dringendes mitzuteilen.");
    }

    @Test
    public void testRule_375() throws IOException {
        assertGood("Er isst UV-bestrahltes Obst.");
    }

    @Test
    public void testRule_376() throws IOException {
        assertGood("Er isst Na-haltiges Obst.");
    }

    @Test
    public void testRule_377() throws IOException {
        assertGood("Er vertraut auf CO2-arme Wasserkraft");
    }

    @Test
    public void testRule_378() throws IOException {
        assertGood("Das Entweder-oder ist kein Problem.");
    }

    @Test
    public void testRule_379() throws IOException {
        assertGood("Er liebt ihre Makeup-freie Haut.");
    }

    @Test
    public void testRule_380() throws IOException {
        assertGood("Das ist eine Schreibweise.");
    }

    @Test
    public void testRule_381() throws IOException {
        assertBad("Das ist Eine Schreibweise.");
    }

    @Test
    public void testRule_382() throws IOException {
        assertGood("Das ist ein Mann.");
    }

    @Test
    public void testRule_383() throws IOException {
        assertBad("Das ist Ein Mann.");
    }

    @Test
    public void testRule_384() throws IOException {
        assertBad("Sie erhalten bald unsere Neuesten Insights.");
    }

    @Test
    public void testRule_385() throws IOException {
        assertBad("Auf eine Carvingschiene sollte die Kette schon im Kalten Zustand weit durchhängen.");
    }

    @Test
    public void testRule_386() throws IOException {
        assertGood("Du Ärmste!");
    }

    @Test
    public void testRule_387() throws IOException {
        assertGood("Ich habe nur Schlechtes über den Laden gehört.");
    }

    @Test
    public void testRule_388() throws IOException {
        assertGood("Du Ärmster, leg dich besser ins Bett.");
    }

    @Test
    public void testRule_389() throws IOException {
        assertGood("Er wohnt Am Hohen Hain 6a");
    }

    @Test
    public void testRule_390() throws IOException {
        assertGood("Das Bauvorhaben Am Wiesenhang 9");
    }

    @Test
    public void testRule_391() throws IOException {
        assertGood("... und das Zwischenmenschliche Hand in Hand.");
    }

    @Test
    public void testRule_392() throws IOException {
        assertGood("Der Platz auf dem die Ahnungslosen Kopf an Kopf stehen.");
    }

    @Test
    public void testRule_393() throws IOException {
        assertGood("4.)   Bei Beschäftigung von Hilfskräften: Schadenfälle durch Hilfskräfte");
    }

    @Test
    public void testRule_394() throws IOException {
        assertGood("Es besteht aus Schülern, Arbeitstätigen und Studenten.");
    }

    @Test
    public void testRule_395() throws IOException {
        assertGood("Sie starrt ständig ins Nichts.");
    }

    @Test
    public void testRule_396() throws IOException {
        assertGood("Sowas aber auch.\u2063Das Haus ist schön.");
    }

    @Test
    public void testRule_397() throws IOException {
        assertGood("\u2063Das Haus ist schön.");
    }

    @Test
    public void testRule_398() throws IOException {
        assertGood("\u2063\u2063Das Haus ist schön.");
    }

    @Test
    public void testRule_399() throws IOException {
        assertGood("Die Mannschaft ist eine gelungene Mischung aus alten Haudegen und jungen Wilden.");
    }

    @Test
    public void testRule_400() throws IOException {
        assertGood("Alleine durch die bloße Einwohnerzahl des Landes leben im Land zahlreiche Kulturschaffende, nach einer Schätzung etwa 30.000 Künstler.");
    }

    @Test
    public void testRule_401() throws IOException {
        assertGood("Ich hatte das offenbar vergessen oder nicht ganz verstanden.");
    }

    @Test
    public void testRule_402() throws IOException {
        assertGood("Ich hatte das vergessen oder nicht ganz verstanden.");
    }

    @Test
    public void testRule_403() throws IOException {
        assertGood("Das ist ein zwingendes Muss.");
    }

    @Test
    public void testRule_404() throws IOException {
        assertGood("Er hält eine Handbreit Abstand.");
    }

    @Test
    public void testRule_405() throws IOException {
        assertGood("Das ist das Debakel und Aus für Podolski.");
    }

    @Test
    public void testRule_406() throws IOException {
        assertGood("Ein Highlight für Klein und Groß!");
    }

    @Test
    public void testRule_407() throws IOException {
        assertGood("Der schwedische Psychologe Dan Katz, Autor von 'Angst kocht auch nur mit Wasser', sieht in der Corona-Krise dennoch nicht nur Negatives.");
    }

    @Test
    public void testSubstantivierteVerben_1() throws IOException {
        assertGood("Das fahrende Auto.");
    }

    @Test
    public void testSubstantivierteVerben_2() throws IOException {
        assertGood("Das können wir so machen.");
    }

    @Test
    public void testSubstantivierteVerben_3() throws IOException {
        assertGood("Denn das Fahren ist einfach.");
    }

    @Test
    public void testSubstantivierteVerben_4() throws IOException {
        assertGood("Das Fahren ist einfach.");
    }

    @Test
    public void testSubstantivierteVerben_5() throws IOException {
        assertGood("Das Gehen fällt mir leicht.");
    }

    @Test
    public void testSubstantivierteVerben_6() throws IOException {
        assertGood("Das Ernten der Kartoffeln ist mühsam.");
    }

    @Test
    public void testSubstantivierteVerben_7() throws IOException {
        assertGood("Entschuldige das späte Weiterleiten.");
    }

    @Test
    public void testSubstantivierteVerben_8() throws IOException {
        assertGood("Ich liebe das Lesen.");
    }

    @Test
    public void testSubstantivierteVerben_9() throws IOException {
        assertGood("Das Betreten des Rasens ist verboten.");
    }

    @Test
    public void testSubstantivierteVerben_10() throws IOException {
        assertGood("Das haben wir aus eigenem Antrieb getan.");
    }

    @Test
    public void testSubstantivierteVerben_11() throws IOException {
        assertGood("Das haben wir.");
    }

    @Test
    public void testSubstantivierteVerben_12() throws IOException {
        assertGood("Das haben wir schon.");
    }

    @Test
    public void testSubstantivierteVerben_13() throws IOException {
        assertGood("Das lesen sie doch sicher in einer Minute durch.");
    }

    @Test
    public void testSubstantivierteVerben_14() throws IOException {
        assertGood("Das lesen Sie doch sicher in einer Minute durch!");
    }

    @Test
    public void testSubstantivierteVerben_15() throws IOException {
        assertGood("Formationswasser, das oxidiert war.");
    }

    @Test
    public void testSubstantivierteVerben_16() throws IOException {
        assertGood("Um das herauszubekommen diskutieren zwei Experten.");
    }

    @Test
    public void testSubstantivierteVerben_17() throws IOException {
        assertGood("Ich würde ihn dann mal nach München schicken, damit die beiden das planen/entwickeln können.");
    }

    @Test
    public void testSubstantivierteVerben_18() throws IOException {
        assertGood("Das Lesen fällt mir schwer.");
    }

    @Test
    public void testSubstantivierteVerben_19() throws IOException {
        assertGood("Sie hörten ein starkes Klopfen.");
    }

    @Test
    public void testSubstantivierteVerben_20() throws IOException {
        assertGood("Wer erledigt das Fensterputzen?");
    }

    @Test
    public void testSubstantivierteVerben_21() throws IOException {
        assertGood("Viele waren am Zustandekommen des Vertrages beteiligt.");
    }

    @Test
    public void testSubstantivierteVerben_22() throws IOException {
        assertGood("Die Sache kam ins Stocken.");
    }

    @Test
    public void testSubstantivierteVerben_23() throws IOException {
        assertGood("Das ist zum Lachen.");
    }

    @Test
    public void testSubstantivierteVerben_24() throws IOException {
        assertGood("Euer Fernbleiben fiel uns auf.");
    }

    @Test
    public void testSubstantivierteVerben_25() throws IOException {
        assertGood("Uns half nur noch lautes Rufen.");
    }

    @Test
    public void testSubstantivierteVerben_26() throws IOException {
        assertGood("Die Mitbewohner begnügten sich mit Wegsehen und Schweigen.");
    }

    @Test
    public void testSubstantivierteVerben_27() throws IOException {
        assertGood("Sie wollte auf Biegen und Brechen gewinnen.");
    }

    @Test
    public void testSubstantivierteVerben_28() throws IOException {
        assertGood("Er klopfte mit Zittern und Zagen an.");
    }

    @Test
    public void testSubstantivierteVerben_29() throws IOException {
        assertGood("Ich nehme die Tabletten auf Anraten meiner Ärztin.");
    }

    @Test
    public void testSubstantivierteVerben_30() throws IOException {
        assertGood("Sie hat ihr Soll erfüllt.");
    }

    @Test
    public void testSubstantivierteVerben_31() throws IOException {
        assertGood("Dies ist ein absolutes Muss.");
    }

    @Test
    public void testSubstantivierteVerben_32() throws IOException {
        assertGood("Das Lesen fällt mir schwer.");
    }

    @Test
    public void testSubstantivierteVerben_33() throws IOException {
        assertBad("Das fahren ist einfach.");
    }

    @Test
    public void testSubstantivierteVerben_34() throws IOException {
        assertBad("Denn das fahren ist einfach.");
    }

    @Test
    public void testSubstantivierteVerben_35() throws IOException {
        assertBad("Denn das laufen ist einfach.");
    }

    @Test
    public void testSubstantivierteVerben_36() throws IOException {
        assertBad("Denn das essen ist einfach.");
    }

    @Test
    public void testSubstantivierteVerben_37() throws IOException {
        assertBad("Denn das gehen ist einfach.");
    }

    @Test
    public void testSubstantivierteVerben_38() throws IOException {
        assertBad("Das Große Auto wurde gewaschen.");
    }

    @Test
    public void testSubstantivierteVerben_39() throws IOException {
        assertBad("Ich habe ein Neues Fahrrad.");
    }

    @Test
    public void testPhraseExceptions_1() throws IOException {
        assertGood("Das gilt ohne Wenn und Aber.");
    }

    @Test
    public void testPhraseExceptions_2() throws IOException {
        assertGood("Ohne Wenn und Aber");
    }

    @Test
    public void testPhraseExceptions_3() throws IOException {
        assertGood("Das gilt ohne Wenn und Aber bla blubb.");
    }

    @Test
    public void testPhraseExceptions_4() throws IOException {
        assertGood("Das gilt ohne wenn");
    }

    @Test
    public void testPhraseExceptions_5() throws IOException {
        assertGood("Das gilt ohne wenn und");
    }

    @Test
    public void testPhraseExceptions_6() throws IOException {
        assertGood("wenn und aber");
    }

    @Test
    public void testPhraseExceptions_7() throws IOException {
        assertGood("und aber");
    }

    @Test
    public void testPhraseExceptions_8() throws IOException {
        assertGood("aber");
    }
}
