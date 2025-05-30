package org.languagetool.rules.de;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.language.German;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VerbAgreementRuleTest_Purified {

    private JLanguageTool lt;

    private VerbAgreementRule rule;

    @Before
    public void setUp() {
        lt = new JLanguageTool(Languages.getLanguageForShortCode("de-DE"));
        rule = new VerbAgreementRule(TestTools.getMessages("de"), (German) Languages.getLanguageForShortCode("de-DE"));
    }

    private void assertGood(String s) throws IOException {
        RuleMatch[] matches = rule.match(lt.analyzeText(s));
        if (matches.length != 0) {
            fail("Got > 0 matches for '" + s + "': " + Arrays.toString(matches));
        }
    }

    private void assertBad(String s, int n) throws IOException {
        assertEquals(n, rule.match(lt.analyzeText(s)).length);
    }

    private void assertBad(String s) throws IOException {
        assertBad(s, 1);
    }

    private void assertBad(String s, String expectedErrorSubstring) throws IOException {
        assertEquals(1, rule.match(lt.analyzeText(s)).length);
        final String errorMessage = rule.match(lt.analyzeText(s))[0].getMessage();
        assertTrue("Got error '" + errorMessage + "', expected substring '" + expectedErrorSubstring + "'", errorMessage.contains(expectedErrorSubstring));
    }

    private void assertBad(String input, int expectedMatches, String... expectedSuggestions) throws IOException {
        RuleMatch[] matches = rule.match(lt.analyzeText(input));
        assertEquals("Did not find " + expectedMatches + " match(es) in sentence '" + input + "'", expectedMatches, matches.length);
        if (expectedSuggestions.length > 0) {
            RuleMatch match = matches[0];
            if (matches.length > 1 && match.getSuggestedReplacements().isEmpty()) {
                match = matches[1];
            }
            List<String> suggestions = match.getSuggestedReplacements();
            assertThat(suggestions, is(Arrays.asList(expectedSuggestions)));
        }
    }

    private void assertBad(String s, String... expectedSuggestions) throws IOException {
        assertBad(s, 1, expectedSuggestions);
    }

    @Test
    public void testWrongVerb_1() throws IOException {
        assertGood("*runterguck* das ist aber tief");
    }

    @Test
    public void testWrongVerb_2() throws IOException {
        assertGood("Weder Peter noch ich wollen das.");
    }

    @Test
    public void testWrongVerb_3() throws IOException {
        assertGood("Du bist in dem Moment angekommen, als ich gegangen bin.");
    }

    @Test
    public void testWrongVerb_4() throws IOException {
        assertGood("Kümmere du dich mal nicht darum!");
    }

    @Test
    public void testWrongVerb_5() throws IOException {
        assertGood("Ich weiß, was ich tun werde, falls etwas geschehen sollte.");
    }

    @Test
    public void testWrongVerb_6() throws IOException {
        assertGood("...die dreißig Jahre jünger als ich ist.");
    }

    @Test
    public void testWrongVerb_7() throws IOException {
        assertGood("Ein Mann wie ich braucht einen Hut.");
    }

    @Test
    public void testWrongVerb_8() throws IOException {
        assertGood("Egal, was er sagen wird, ich habe meine Entscheidung getroffen.");
    }

    @Test
    public void testWrongVerb_9() throws IOException {
        assertGood("Du Beharrst darauf, dein Wörterbuch hätte recht, hast aber von den Feinheiten des Japanischen keine Ahnung!");
    }

    @Test
    public void testWrongVerb_10() throws IOException {
        assertGood("Bin gleich wieder da.");
    }

    @Test
    public void testWrongVerb_11() throws IOException {
        assertGood("Wobei ich äußerst vorsichtig bin.");
    }

    @Test
    public void testWrongVerb_12() throws IOException {
        assertGood("Es ist klar, dass ich äußerst vorsichtig mit den Informationen umgehe");
    }

    @Test
    public void testWrongVerb_13() throws IOException {
        assertGood("Es ist klar, dass ich äußerst vorsichtig bin.");
    }

    @Test
    public void testWrongVerb_14() throws IOException {
        assertGood("Wobei er äußerst selten darüber spricht.");
    }

    @Test
    public void testWrongVerb_15() throws IOException {
        assertGood("Wobei er äußerst selten über seine erste Frau spricht.");
    }

    @Test
    public void testWrongVerb_16() throws IOException {
        assertGood("Das Wort „schreibst“ ist schön.");
    }

    @Test
    public void testWrongVerb_17() throws IOException {
        assertGood("Die Jagd nach bin Laden.");
    }

    @Test
    public void testWrongVerb_18() throws IOException {
        assertGood("Die Unterlagen solltet ihr gründlich durcharbeiten.");
    }

    @Test
    public void testWrongVerb_19() throws IOException {
        assertGood("Er reagierte äußerst negativ.");
    }

    @Test
    public void testWrongVerb_20() throws IOException {
        assertGood("Max und ich sollten das machen.");
    }

    @Test
    public void testWrongVerb_21() throws IOException {
        assertGood("Osama bin Laden stammt aus Saudi-Arabien.");
    }

    @Test
    public void testWrongVerb_22() throws IOException {
        assertGood("Solltet ihr das machen?");
    }

    @Test
    public void testWrongVerb_23() throws IOException {
        assertGood("Dann beende du den Auftrag und bring sie ihrem Vater.");
    }

    @Test
    public void testWrongVerb_24() throws IOException {
        assertGood("- Wirst du ausflippen?");
    }

    @Test
    public void testWrongVerb_25() throws IOException {
        assertGood("Ein Geschenk, das er einst von Aphrodite erhalten hatte.");
    }

    @Test
    public void testWrongVerb_26() throws IOException {
        assertGood("Wenn ich sterben sollte, wer würde sich dann um die Katze kümmern?");
    }

    @Test
    public void testWrongVerb_27() throws IOException {
        assertGood("Wenn er sterben sollte, wer würde sich dann um die Katze kümmern?");
    }

    @Test
    public void testWrongVerb_28() throws IOException {
        assertGood("Wenn sie sterben sollte, wer würde sich dann um die Katze kümmern?");
    }

    @Test
    public void testWrongVerb_29() throws IOException {
        assertGood("Wenn es sterben sollte, wer würde sich dann um die Katze kümmern?");
    }

    @Test
    public void testWrongVerb_30() throws IOException {
        assertGood("Wenn ihr sterben solltet, wer würde sich dann um die Katze kümmern?");
    }

    @Test
    public void testWrongVerb_31() throws IOException {
        assertGood("Wenn wir sterben sollten, wer würde sich dann um die Katze kümmern?");
    }

    @Test
    public void testWrongVerb_32() throws IOException {
        assertGood("Dafür erhielten er sowie der Hofgoldschmied Theodor Heiden einen Preis.");
    }

    @Test
    public void testWrongVerb_33() throws IOException {
        assertGood("Probst wurde deshalb in den Medien gefeiert.");
    }

    @Test
    public void testWrongVerb_34() throws IOException {
        assertGood("/usr/bin/firefox");
    }

    @Test
    public void testWrongVerb_35() throws IOException {
        assertGood("Das sind Leute, die viel mehr als ich wissen.");
    }

    @Test
    public void testWrongVerb_36() throws IOException {
        assertGood("Das ist mir nicht klar, kannst ja mal beim Kunden nachfragen.");
    }

    @Test
    public void testWrongVerb_37() throws IOException {
        assertGood("So tes\u00ADtest Du das mit dem soft hyphen.");
    }

    @Test
    public void testWrongVerb_38() throws IOException {
        assertGood("Viele Brunnen in Italiens Hauptstadt sind bereits abgeschaltet.");
    }

    @Test
    public void testWrongVerb_39() throws IOException {
        assertGood("„Werde ich tun!“");
    }

    @Test
    public void testWrongVerb_40() throws IOException {
        assertGood("Könntest dir mal eine Scheibe davon abschneiden!");
    }

    @Test
    public void testWrongVerb_41() throws IOException {
        assertGood("Müsstest dir das mal genauer anschauen.");
    }

    @Test
    public void testWrongVerb_42() throws IOException {
        assertGood("Kannst ein neues Release machen.");
    }

    @Test
    public void testWrongVerb_43() throws IOException {
        assertGood("Sie fragte: „Muss ich aussagen?“");
    }

    @Test
    public void testWrongVerb_44() throws IOException {
        assertGood("„Können wir bitte das Thema wechseln, denn ich möchte ungern darüber reden?“");
    }

    @Test
    public void testWrongVerb_45() throws IOException {
        assertGood("Er sagt: „Willst du behaupten, dass mein Sohn euch liebt?“");
    }

    @Test
    public void testWrongVerb_46() throws IOException {
        assertGood("Kannst mich gerne anrufen.");
    }

    @Test
    public void testWrongVerb_47() throws IOException {
        assertGood("Kannst ihn gerne anrufen.");
    }

    @Test
    public void testWrongVerb_48() throws IOException {
        assertGood("Kannst sie gerne anrufen.");
    }

    @Test
    public void testWrongVerb_49() throws IOException {
        assertGood("Aber wie ich sehe, benötigt ihr Nachschub.");
    }

    @Test
    public void testWrongVerb_50() throws IOException {
        assertGood("Wie ich sehe, benötigt ihr Nachschub.");
    }

    @Test
    public void testWrongVerb_51() throws IOException {
        assertGood("Einer wie du kennt doch bestimmt viele Studenten.");
    }

    @Test
    public void testWrongVerb_52() throws IOException {
        assertGood("Für Sie mache ich eine Ausnahme.");
    }

    @Test
    public void testWrongVerb_53() throws IOException {
        assertGood("Ohne sie hätte ich das nicht geschafft.");
    }

    @Test
    public void testWrongVerb_54() throws IOException {
        assertGood("Ohne Sie hätte ich das nicht geschafft.");
    }

    @Test
    public void testWrongVerb_55() throws IOException {
        assertGood("Ich hoffe du auch.");
    }

    @Test
    public void testWrongVerb_56() throws IOException {
        assertGood("Ich hoffe ihr auch.");
    }

    @Test
    public void testWrongVerb_57() throws IOException {
        assertGood("Wird hoffen du auch.");
    }

    @Test
    public void testWrongVerb_58() throws IOException {
        assertGood("Hab einen schönen Tag!");
    }

    @Test
    public void testWrongVerb_59() throws IOException {
        assertGood("Tom traue ich mehr als Maria.");
    }

    @Test
    public void testWrongVerb_60() throws IOException {
        assertGood("Tom kenne ich nicht besonders gut, dafür aber seine Frau.");
    }

    @Test
    public void testWrongVerb_61() throws IOException {
        assertGood("Tom habe ich heute noch nicht gesehen.");
    }

    @Test
    public void testWrongVerb_62() throws IOException {
        assertGood("Tom bezahle ich gut.");
    }

    @Test
    public void testWrongVerb_63() throws IOException {
        assertGood("Tom werde ich nicht noch mal um Hilfe bitten.");
    }

    @Test
    public void testWrongVerb_64() throws IOException {
        assertGood("Tom konnte ich überzeugen, nicht aber Maria.");
    }

    @Test
    public void testWrongVerb_65() throws IOException {
        assertGood("Mach du mal!");
    }

    @Test
    public void testWrongVerb_66() throws IOException {
        assertGood("Das bekomme ich nicht hin.");
    }

    @Test
    public void testWrongVerb_67() throws IOException {
        assertGood("Dies betreffe insbesondere Nietzsches Aussagen zu Kant und der Evolutionslehre.");
    }

    @Test
    public void testWrongVerb_68() throws IOException {
        assertGood("❌Du fühlst Dich unsicher?");
    }

    @Test
    public void testWrongVerb_69() throws IOException {
        assertGood("Bringst nicht einmal so etwas Einfaches zustande!");
    }

    @Test
    public void testWrongVerb_70() throws IOException {
        assertGood("Bekommst sogar eine Sicherheitszulage");
    }

    @Test
    public void testWrongVerb_71() throws IOException {
        assertGood("Dallun sagte nur, dass er gleich kommen wird und legte wieder auf.");
    }

    @Test
    public void testWrongVerb_72() throws IOException {
        assertGood("Tinne, Elvis und auch ich werden gerne wiederkommen!");
    }

    @Test
    public void testWrongVerb_73() throws IOException {
        assertGood("Du bist Lehrer und weißt diese Dinge nicht?");
    }

    @Test
    public void testWrongVerb_74() throws IOException {
        assertGood("Die Frage lautet: Bist du bereit zu helfen?");
    }

    @Test
    public void testWrongVerb_75() throws IOException {
        assertGood("Ich will nicht so wie er enden.");
    }

    @Test
    public void testWrongVerb_76() throws IOException {
        assertGood("Das heißt, wir geben einander oft nach als gute Freunde, ob wir gleich nicht einer Meinung sind.");
    }

    @Test
    public void testWrongVerb_77() throws IOException {
        assertGood("Wir seh'n uns in Berlin.");
    }

    @Test
    public void testWrongVerb_78() throws IOException {
        assertGood("Bist du bereit, darüber zu sprechen?");
    }

    @Test
    public void testWrongVerb_79() throws IOException {
        assertGood("Bist du schnell eingeschlafen?");
    }

    @Test
    public void testWrongVerb_80() throws IOException {
        assertGood("Im Gegenzug bin ich bereit, beim Türkischlernen zu helfen.");
    }

    @Test
    public void testWrongVerb_81() throws IOException {
        assertGood("Das habe ich lange gesucht.");
    }

    @Test
    public void testWrongVerb_82() throws IOException {
        assertGood("Dann solltest du schnell eine Nummer der sexy Omas wählen.");
    }

    @Test
    public void testWrongVerb_83() throws IOException {
        assertGood("Vielleicht würdest du bereit sein, ehrenamtlich zu helfen.");
    }

    @Test
    public void testWrongVerb_84() throws IOException {
        assertGood("Werde nicht alt, egal wie lange du lebst.");
    }

    @Test
    public void testWrongVerb_85() throws IOException {
        assertGood("Du bist hingefallen und hast dir das Bein gebrochen.");
    }

    @Test
    public void testWrongVerb_86() throws IOException {
        assertGood("Mögest du lange leben!");
    }

    @Test
    public void testWrongVerb_87() throws IOException {
        assertGood("Planst du lange hier zu bleiben?");
    }

    @Test
    public void testWrongVerb_88() throws IOException {
        assertGood("Du bist zwischen 11 und 12 Jahren alt und spielst gern Fußball bzw. möchtest damit anfangen?");
    }

    @Test
    public void testWrongVerb_89() throws IOException {
        assertGood("Ein großer Hadithwissenschaftler, Scheich Şemseddin Mehmed bin Muhammed-ül Cezri, kam in der Zeit von Mirza Uluğ Bey nach Semerkant.");
    }

    @Test
    public void testWrongVerb_90() throws IOException {
        assertGood("Die Prüfbescheinigung bekommst du gleich nach der bestanden Prüfung vom Prüfer.");
    }

    @Test
    public void testWrongVerb_91() throws IOException {
        assertGood("Du bist sehr schön und brauchst überhaupt gar keine Schminke zu verwenden.");
    }

    @Test
    public void testWrongVerb_92() throws IOException {
        assertGood("Ist das so schnell, wie du gehen kannst?");
    }

    @Test
    public void testWrongVerb_93() throws IOException {
        assertGood("Egal wie lange du versuchst, die Leute davon zu überzeugen");
    }

    @Test
    public void testWrongVerb_94() throws IOException {
        assertGood("Du bist verheiratet und hast zwei Kinder.");
    }

    @Test
    public void testWrongVerb_95() throws IOException {
        assertGood("Du bist aus Berlin und wohnst in Bonn.");
    }

    @Test
    public void testWrongVerb_96() throws IOException {
        assertGood("Sie befestigen die Regalbretter vermittelst dreier Schrauben.");
    }

    @Test
    public void testWrongVerb_97() throws IOException {
        assertGood("Meine Familie & ich haben uns ein neues Auto gekauft.");
    }

    @Test
    public void testWrongVerb_98() throws IOException {
        assertGood("Der Bescheid lasse im übrigen die Abwägungen vermissen, wie die Betriebsprüfung zu den Sachverhaltsbeurteilungen gelange, die den von ihr bekämpften Bescheiden zugrundegelegt worden seien.");
    }

    @Test
    public void testWrongVerb_99() throws IOException {
        assertGood("Die Bildung des Samens erfolgte laut Alkmaion im Gehirn, von wo aus er durch die Adern in den Hoden gelange.");
    }

    @Test
    public void testWrongVerb_100() throws IOException {
        assertGood("Michael Redmond (geb. 1963, USA).");
    }

    @Test
    public void testWrongVerb_101() throws IOException {
        assertGood("Würd mich sehr freuen drüber.");
    }

    @Test
    public void testWrongVerb_102() throws IOException {
        assertGood("Es würd' ein jeder Doktor sein, wenn's Wissen einging wie der Wein.");
    }

    @Test
    public void testWrongVerb_103() throws IOException {
        assertGood("Bald merkte er, dass er dank seines Talents nichts mehr in der österreichischen Jazzszene lernen konnte.");
    }

    @Test
    public void testWrongVerb_104() throws IOException {
        assertGood("»Alles, was wir dank dieses Projektes sehen werden, wird für uns neu sein«, so der renommierte Bienenforscher.");
    }

    @Test
    public void testWrongVerb_105() throws IOException {
        assertGood("Und da wir äußerst Laissez-faire sind, kann man das auch machen.");
    }

    @Test
    public void testWrongVerb_106() throws IOException {
        assertGood("Duzen, jemanden mit Du anreden, eine Sitte, die bei allen alten Völkern üblich war.");
    }

    @Test
    public void testWrongVerb_107() throws IOException {
        assertGood("Schreibtischtäter wie Du sind doch eher selten.");
    }

    @Test
    public void testWrongVerb_108() throws IOException {
        assertGood("Nee, geh du!");
    }

    @Test
    public void testWrongVerb_109() throws IOException {
        assertBad("Als Borcarbid weißt es eine hohe Härte auf.");
    }

    @Test
    public void testWrongVerb_110() throws IOException {
        assertBad("Das greift auf Vorläuferinstitutionen bist auf die Zeit von 1234 zurück.");
    }

    @Test
    public void testWrongVerb_111() throws IOException {
        assertBad("Die Eisenbahn dienst überwiegend dem Güterverkehr.");
    }

    @Test
    public void testWrongVerb_112() throws IOException {
        assertBad("Die Unterlagen solltest ihr gründlich durcharbeiten.");
    }

    @Test
    public void testWrongVerb_113() throws IOException {
        assertBad("Peter bin nett.");
    }

    @Test
    public void testWrongVerb_114() throws IOException {
        assertBad("Weiter befindest sich im Osten die Gemeinde Dorf.");
    }

    @Test
    public void testWrongVerb_115() throws IOException {
        assertBad("Ich geht jetzt nach Hause, weil ich schon zu spät bin.");
    }

    @Test
    public void testWrongVerb_116() throws IOException {
        assertBad("„Du muss gehen.“");
    }

    @Test
    public void testWrongVerb_117() throws IOException {
        assertBad("Du weiß es doch.");
    }

    @Test
    public void testWrongVerb_118() throws IOException {
        assertBad("Sie sagte zu mir: „Du muss gehen.“");
    }

    @Test
    public void testWrongVerb_119() throws IOException {
        assertBad("„Ich müsst alles machen.“");
    }

    @Test
    public void testWrongVerb_120() throws IOException {
        assertBad("„Ich könnt mich sowieso nicht verstehen.“");
    }

    @Test
    public void testWrongVerb_121() throws IOException {
        assertBad("Er sagte düster: Ich brauchen mich nicht böse angucken.");
    }

    @Test
    public void testWrongVerb_122() throws IOException {
        assertBad("David sagte düster: Ich brauchen mich nicht böse angucken.");
    }

    @Test
    public void testWrongVerb_123() throws IOException {
        assertBad("Ich setzet mich auf den weichen Teppich und kreuzte die Unterschenkel wie ein Japaner.");
    }

    @Test
    public void testWrongVerb_124() throws IOException {
        assertBad("Ich brauchen einen Karren mit zwei Ochsen.");
    }

    @Test
    public void testWrongVerb_125() throws IOException {
        assertBad("Ich haben meinen Ohrring fallen lassen.");
    }

    @Test
    public void testWrongVerb_126() throws IOException {
        assertBad("Ich stehen Ihnen gerne für Rückfragen zur Verfügung.");
    }

    @Test
    public void testWrongVerbSubject_1() throws IOException {
        assertGood("Auch morgen lebe ich.");
    }

    @Test
    public void testWrongVerbSubject_2() throws IOException {
        assertGood("Auch morgen leben wir noch.");
    }

    @Test
    public void testWrongVerbSubject_3() throws IOException {
        assertGood("Auch morgen lebst du.");
    }

    @Test
    public void testWrongVerbSubject_4() throws IOException {
        assertGood("Auch morgen lebt er.");
    }

    @Test
    public void testWrongVerbSubject_5() throws IOException {
        assertGood("Auch wenn du leben möchtest.");
    }

    @Test
    public void testWrongVerbSubject_6() throws IOException {
        assertGood("auf der er sieben Jahre blieb.");
    }

    @Test
    public void testWrongVerbSubject_7() throws IOException {
        assertGood("Das absolute Ich ist nicht mit dem individuellen Geist zu verwechseln.");
    }

    @Test
    public void testWrongVerbSubject_8() throws IOException {
        assertGood("Das Ich ist keine Einbildung");
    }

    @Test
    public void testWrongVerbSubject_9() throws IOException {
        assertGood("Das lyrische Ich ist verzweifelt.");
    }

    @Test
    public void testWrongVerbSubject_10() throws IOException {
        assertGood("Den Park, von dem er äußerst genaue Karten zeichnete.");
    }

    @Test
    public void testWrongVerbSubject_11() throws IOException {
        assertGood("Der auffälligste Ring ist der erster Ring, obwohl er verglichen mit den anderen Ringen sehr schwach erscheint.");
    }

    @Test
    public void testWrongVerbSubject_12() throws IOException {
        assertGood("Der Fehler, falls er bestehen sollte, ist schwerwiegend.");
    }

    @Test
    public void testWrongVerbSubject_13() throws IOException {
        assertGood("Der Vorfall, bei dem er einen Teil seines Vermögens verloren hat, ist lange vorbei.");
    }

    @Test
    public void testWrongVerbSubject_14() throws IOException {
        assertGood("Diese Lösung wurde in der 64'er beschrieben, kam jedoch nie.");
    }

    @Test
    public void testWrongVerbSubject_15() throws IOException {
        assertGood("Die Theorie, mit der ich arbeiten konnte.");
    }

    @Test
    public void testWrongVerbSubject_16() throws IOException {
        assertGood("Du bist nett.");
    }

    @Test
    public void testWrongVerbSubject_17() throws IOException {
        assertGood("Du kannst heute leider nicht kommen.");
    }

    @Test
    public void testWrongVerbSubject_18() throws IOException {
        assertGood("Du lebst.");
    }

    @Test
    public void testWrongVerbSubject_19() throws IOException {
        assertGood("Du wünschst dir so viel.");
    }

    @Test
    public void testWrongVerbSubject_20() throws IOException {
        assertGood("Er geht zu ihr.");
    }

    @Test
    public void testWrongVerbSubject_21() throws IOException {
        assertGood("Er ist nett.");
    }

    @Test
    public void testWrongVerbSubject_22() throws IOException {
        assertGood("Er kann heute leider nicht kommen.");
    }

    @Test
    public void testWrongVerbSubject_23() throws IOException {
        assertGood("Er lebt.");
    }

    @Test
    public void testWrongVerbSubject_24() throws IOException {
        assertGood("Er wisse nicht, ob er lachen oder weinen solle.");
    }

    @Test
    public void testWrongVerbSubject_25() throws IOException {
        assertGood("Er und du leben.");
    }

    @Test
    public void testWrongVerbSubject_26() throws IOException {
        assertGood("Er und ich leben.");
    }

    @Test
    public void testWrongVerbSubject_27() throws IOException {
        assertGood("Falls er bestehen sollte, gehen sie weg.");
    }

    @Test
    public void testWrongVerbSubject_28() throws IOException {
        assertGood("Heere, des Gottes der Schlachtreihen Israels, den du verhöhnt hast.");
    }

    @Test
    public void testWrongVerbSubject_29() throws IOException {
        assertGood("Ich bin");
    }

    @Test
    public void testWrongVerbSubject_30() throws IOException {
        assertGood("Ich bin Frankreich!");
    }

    @Test
    public void testWrongVerbSubject_31() throws IOException {
        assertGood("Ich bin froh, dass ich arbeiten kann.");
    }

    @Test
    public void testWrongVerbSubject_32() throws IOException {
        assertGood("Ich bin nett.");
    }

    @Test
    public void testWrongVerbSubject_33() throws IOException {
        assertGood("‚ich bin tot‘");
    }

    @Test
    public void testWrongVerbSubject_34() throws IOException {
        assertGood("Ich kann heute leider nicht kommen.");
    }

    @Test
    public void testWrongVerbSubject_35() throws IOException {
        assertGood("Ich lebe.");
    }

    @Test
    public void testWrongVerbSubject_36() throws IOException {
        assertGood("Lebst du?");
    }

    @Test
    public void testWrongVerbSubject_37() throws IOException {
        assertGood("Morgen kommen du und ich.");
    }

    @Test
    public void testWrongVerbSubject_38() throws IOException {
        assertGood("Morgen kommen er, den ich sehr mag, und ich.");
    }

    @Test
    public void testWrongVerbSubject_39() throws IOException {
        assertGood("Morgen kommen er und ich.");
    }

    @Test
    public void testWrongVerbSubject_40() throws IOException {
        assertGood("Morgen kommen ich und sie.");
    }

    @Test
    public void testWrongVerbSubject_41() throws IOException {
        assertGood("Morgen kommen wir und sie.");
    }

    @Test
    public void testWrongVerbSubject_42() throws IOException {
        assertGood("nachdem er erfahren hatte");
    }

    @Test
    public void testWrongVerbSubject_43() throws IOException {
        assertGood("Nett bin ich.");
    }

    @Test
    public void testWrongVerbSubject_44() throws IOException {
        assertGood("Nett bist du.");
    }

    @Test
    public void testWrongVerbSubject_45() throws IOException {
        assertGood("Nett ist er.");
    }

    @Test
    public void testWrongVerbSubject_46() throws IOException {
        assertGood("Nett sind wir.");
    }

    @Test
    public void testWrongVerbSubject_47() throws IOException {
        assertGood("Niemand ahnte, dass er gewinnen könne.");
    }

    @Test
    public void testWrongVerbSubject_48() throws IOException {
        assertGood("Sie lebt und wir leben.");
    }

    @Test
    public void testWrongVerbSubject_49() throws IOException {
        assertGood("Sie und er leben.");
    }

    @Test
    public void testWrongVerbSubject_50() throws IOException {
        assertGood("Sind ich und Peter nicht nette Kinder?");
    }

    @Test
    public void testWrongVerbSubject_51() throws IOException {
        assertGood("Sodass ich sagen möchte, dass unsere schönen Erinnerungen gut sind.");
    }

    @Test
    public void testWrongVerbSubject_52() throws IOException {
        assertGood("Wann ich meinen letzten Film drehen werde, ist unbekannt.");
    }

    @Test
    public void testWrongVerbSubject_53() throws IOException {
        assertGood("Was ich tun muss.");
    }

    @Test
    public void testWrongVerbSubject_54() throws IOException {
        assertGood("Welche Aufgaben er dabei tatsächlich übernehmen könnte");
    }

    @Test
    public void testWrongVerbSubject_55() throws IOException {
        assertGood("wie er beschaffen war");
    }

    @Test
    public void testWrongVerbSubject_56() throws IOException {
        assertGood("Wir gelangen zu dir.");
    }

    @Test
    public void testWrongVerbSubject_57() throws IOException {
        assertGood("Wir können heute leider nicht kommen.");
    }

    @Test
    public void testWrongVerbSubject_58() throws IOException {
        assertGood("Wir leben noch.");
    }

    @Test
    public void testWrongVerbSubject_59() throws IOException {
        assertGood("Wir sind nett.");
    }

    @Test
    public void testWrongVerbSubject_60() throws IOException {
        assertGood("Wobei wir benutzt haben, dass der Satz gilt.");
    }

    @Test
    public void testWrongVerbSubject_61() throws IOException {
        assertGood("Wünschst du dir mehr Zeit?");
    }

    @Test
    public void testWrongVerbSubject_62() throws IOException {
        assertGood("Wyrjtjbst du?");
    }

    @Test
    public void testWrongVerbSubject_63() throws IOException {
        assertGood("Wenn ich du wäre, würde ich das nicht machen.");
    }

    @Test
    public void testWrongVerbSubject_64() throws IOException {
        assertGood("Er sagte: „Darf ich bitten, mir zu folgen?“");
    }

    @Test
    public void testWrongVerbSubject_65() throws IOException {
        assertGood("Ja sind ab morgen dabei.");
    }

    @Test
    public void testWrongVerbSubject_66() throws IOException {
        assertGood("Oh bin überfragt.");
    }

    @Test
    public void testWrongVerbSubject_67() throws IOException {
        assertGood("Angenommen, du wärst ich.");
    }

    @Test
    public void testWrongVerbSubject_68() throws IOException {
        assertGood("Ich denke, dass das Haus, in das er gehen will, heute Morgen gestrichen worden ist.");
    }

    @Test
    public void testWrongVerbSubject_69() throws IOException {
        assertGood("Ich hab mein Leben, leb du deines!");
    }

    @Test
    public void testWrongVerbSubject_70() throws IOException {
        assertGood("Da freut er sich, wenn er schlafen geht und was findet.");
    }

    @Test
    public void testWrongVerbSubject_71() throws IOException {
        assertGood("John nimmt weiter an einem Abendkurs über Journalismus teil.");
    }

    @Test
    public void testWrongVerbSubject_72() throws IOException {
        assertGood("Viele nahmen an der Aktion teil und am Ende des rAAd-Events war die Tafel zwar bunt, aber leider überwogen die roten Kärtchen sehr deutlich.");
    }

    @Test
    public void testWrongVerbSubject_73() throws IOException {
        assertGood("Musst also nichts machen.");
    }

    @Test
    public void testWrongVerbSubject_74() throws IOException {
        assertGood("Eine Situation, wo der Stadtrat gleich mal zum Du übergeht.");
    }

    @Test
    public void testWrongVerbSubject_75() throws IOException {
        assertGood("Machen wir, sobald wir frische und neue Akkus haben.");
    }

    @Test
    public void testWrongVerbSubject_76() throws IOException {
        assertGood("Darfst nicht so reden, Franz!");
    }

    @Test
    public void testWrongVerbSubject_77() throws IOException {
        assertGood("Finde du den Jungen.");
    }

    @Test
    public void testWrongVerbSubject_78() throws IOException {
        assertGood("Finde Du den Jungen.");
    }

    @Test
    public void testWrongVerbSubject_79() throws IOException {
        assertGood("Kümmerst dich ja gar nicht um sie.");
    }

    @Test
    public void testWrongVerbSubject_80() throws IOException {
        assertGood("Könntest was erfinden, wie dein Papa.");
    }

    @Test
    public void testWrongVerbSubject_81() throws IOException {
        assertGood("Siehst aus wie ein Wachhund.");
    }

    @Test
    public void testWrongVerbSubject_82() throws IOException {
        assertGood("Solltest es mal in seinem Büro versuchen.");
    }

    @Test
    public void testWrongVerbSubject_83() throws IOException {
        assertGood("Stehst einfach nicht zu mir.");
    }

    @Test
    public void testWrongVerbSubject_84() throws IOException {
        assertGood("Stellst für deinen Dad etwas zu Essen bereit.");
    }

    @Test
    public void testWrongVerbSubject_85() throws IOException {
        assertGood("Springst weit, oder?");
    }

    @Test
    public void testWrongVerbSubject_86() throws IOException {
        assertGood("Wirst groß, was?");
    }

    @Test
    public void testWrongVerbSubject_87() throws IOException {
        assertBad("Auch morgen leben du.");
    }

    @Test
    public void testWrongVerbSubject_88() throws IOException {
        assertBad("Du weiß noch, dass du das gestern gesagt hast.");
    }

    @Test
    public void testWrongVerbSubject_89() throws IOException {
        assertBad("Auch morgen leben du");
    }

    @Test
    public void testWrongVerbSubject_90() throws IOException {
        assertBad("Auch morgen leben er.");
    }

    @Test
    public void testWrongVerbSubject_91() throws IOException {
        assertBad("Auch morgen leben ich.");
    }

    @Test
    public void testWrongVerbSubject_92() throws IOException {
        assertBad("Auch morgen lebte wir noch.");
    }

    @Test
    public void testWrongVerbSubject_93() throws IOException {
        assertBad("Du bin nett.", 2);
    }

    @Test
    public void testWrongVerbSubject_94() throws IOException {
        assertBad("Du können heute leider nicht kommen.");
    }

    @Test
    public void testWrongVerbSubject_95() throws IOException {
        assertBad("Du können heute leider nicht kommen.", "Du könnest", "Du kannst", "Du könntest", "Wir können", "Sie können", "Du konntest");
    }

    @Test
    public void testWrongVerbSubject_96() throws IOException {
        assertBad("Du leben.");
    }

    @Test
    public void testWrongVerbSubject_97() throws IOException {
        assertBad("Du wünscht dir so viel.");
    }

    @Test
    public void testWrongVerbSubject_98() throws IOException {
        assertBad("Er bin nett.", 2);
    }

    @Test
    public void testWrongVerbSubject_99() throws IOException {
        assertBad("Er gelangst zu ihr.", 2);
    }

    @Test
    public void testWrongVerbSubject_100() throws IOException {
        assertBad("Er können heute leider nicht kommen.", "Subjekt (Er) und Prädikat (können)");
    }

    @Test
    public void testWrongVerbSubject_101() throws IOException {
        assertBad("Er lebst.", 2);
    }

    @Test
    public void testWrongVerbSubject_102() throws IOException {
        assertBad("Ich bist nett.", 2);
    }

    @Test
    public void testWrongVerbSubject_103() throws IOException {
        assertBad("Ich kannst heute leider nicht kommen.", 2);
    }

    @Test
    public void testWrongVerbSubject_104() throws IOException {
        assertBad("Ich leben.");
    }

    @Test
    public void testWrongVerbSubject_105() throws IOException {
        assertBad("Ich leben.", "Ich lebe", "Ich lebte", "Wir leben", "Sie leben");
    }

    @Test
    public void testWrongVerbSubject_106() throws IOException {
        assertBad("Lebe du?");
    }

    @Test
    public void testWrongVerbSubject_107() throws IOException {
        assertBad("Lebe du?", "Lebest du", "Lebst du", "Lebe er", "Lebe es", "Lebtest du", "Lebe ich", "Lebe sie");
    }

    @Test
    public void testWrongVerbSubject_108() throws IOException {
        assertBad("Leben du?");
    }

    @Test
    public void testWrongVerbSubject_109() throws IOException {
        assertBad("Nett bist ich nicht.", 2);
    }

    @Test
    public void testWrongVerbSubject_110() throws IOException {
        assertBad("Nett bist ich nicht.", 2, "bin ich", "bist du", "sei ich", "wäre ich", "war ich");
    }

    @Test
    public void testWrongVerbSubject_111() throws IOException {
        assertBad("Nett sind du.");
    }

    @Test
    public void testWrongVerbSubject_112() throws IOException {
        assertBad("Nett sind er.");
    }

    @Test
    public void testWrongVerbSubject_113() throws IOException {
        assertBad("Nett sind er.", "sind wir", "sei er", "ist er", "sind sie", "wäre er", "war er");
    }

    @Test
    public void testWrongVerbSubject_114() throws IOException {
        assertBad("Nett warst wir.", 2);
    }

    @Test
    public void testWrongVerbSubject_115() throws IOException {
        assertBad("Wir bin nett.", 2);
    }

    @Test
    public void testWrongVerbSubject_116() throws IOException {
        assertBad("Wir gelangst zu ihr.", 2);
    }

    @Test
    public void testWrongVerbSubject_117() throws IOException {
        assertBad("Wir könnt heute leider nicht kommen.");
    }

    @Test
    public void testWrongVerbSubject_118() throws IOException {
        assertBad("Wünscht du dir mehr Zeit?", "Subjekt (du) und Prädikat (Wünscht)");
    }

    @Test
    public void testWrongVerbSubject_119() throws IOException {
        assertBad("Wir lebst noch.", 2);
    }

    @Test
    public void testWrongVerbSubject_120() throws IOException {
        assertBad("Wir lebst noch.", 2, "Wir leben", "Wir lebten", "Du lebst");
    }

    @Test
    public void testWrongVerbSubject_121() throws IOException {
        assertBad("Er sagte düster: „Ich brauchen mich nicht schuldig fühlen.“", 1, "Ich brauche", "Ich brauchte", "Ich bräuchte", "Wir brauchen", "Sie brauchen");
    }

    @Test
    public void testWrongVerbSubject_122() throws IOException {
        assertBad("Er sagte: „Ich brauchen mich nicht schuldig fühlen.“", 1, "Ich brauche", "Ich brauchte", "Ich bräuchte", "Wir brauchen", "Sie brauchen");
    }
}
