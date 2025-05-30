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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class VerbAgreementRuleTest_Parameterized {

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
    public void testWrongVerbSubject_107() throws IOException {
        assertBad("Lebe du?", "Lebest du", "Lebst du", "Lebe er", "Lebe es", "Lebtest du", "Lebe ich", "Lebe sie");
    }

    @ParameterizedTest
    @MethodSource("Provider_testWrongVerb_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12_12to13_13to14_14to15_15to16_16to17_17to18_18to19_19to20_20to21_21to22_22to23_23to24_24to25_25to26_26to27_27to28_28to29_29to30_30to31_31to32_32to33_33to34_34to35_35to36_36to37_37to38_38to39_39to40_40to41_41to42_42to43_43to44_44to45_45to46_46to47_47to48_48to49_49to50_50to51_51to52_52to53_53to54_54to55_55to56_56to57_57to58_58to59_59to60_60to61_61to62_62to63_63to64_64to65_65to66_66to67_67to68_68to69_69to70_70to71_71to72_72to73_73to74_74to75_75to76_76to77_77to78_78to79_79to80_80to81_81to82_82to83_83to84_84to85_85to86_86to108")
    public void testWrongVerb_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12_12to13_13to14_14to15_15to16_16to17_17to18_18to19_19to20_20to21_21to22_22to23_23to24_24to25_25to26_26to27_27to28_28to29_29to30_30to31_31to32_32to33_33to34_34to35_35to36_36to37_37to38_38to39_39to40_40to41_41to42_42to43_43to44_44to45_45to46_46to47_47to48_48to49_49to50_50to51_51to52_52to53_53to54_54to55_55to56_56to57_57to58_58to59_59to60_60to61_61to62_62to63_63to64_64to65_65to66_66to67_67to68_68to69_69to70_70to71_71to72_72to73_73to74_74to75_75to76_76to77_77to78_78to79_79to80_80to81_81to82_82to83_83to84_84to85_85to86_86to108(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testWrongVerb_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12_12to13_13to14_14to15_15to16_16to17_17to18_18to19_19to20_20to21_21to22_22to23_23to24_24to25_25to26_26to27_27to28_28to29_29to30_30to31_31to32_32to33_33to34_34to35_35to36_36to37_37to38_38to39_39to40_40to41_41to42_42to43_43to44_44to45_45to46_46to47_47to48_48to49_49to50_50to51_51to52_52to53_53to54_54to55_55to56_56to57_57to58_58to59_59to60_60to61_61to62_62to63_63to64_64to65_65to66_66to67_67to68_68to69_69to70_70to71_71to72_72to73_73to74_74to75_75to76_76to77_77to78_78to79_79to80_80to81_81to82_82to83_83to84_84to85_85to86_86to108() {
        return Stream.of(arguments("*runterguck* das ist aber tief"), arguments("Weder Peter noch ich wollen das."), arguments("Du bist in dem Moment angekommen, als ich gegangen bin."), arguments("Kümmere du dich mal nicht darum!"), arguments("Ich weiß, was ich tun werde, falls etwas geschehen sollte."), arguments("...die dreißig Jahre jünger als ich ist."), arguments("Ein Mann wie ich braucht einen Hut."), arguments("Egal, was er sagen wird, ich habe meine Entscheidung getroffen."), arguments("Du Beharrst darauf, dein Wörterbuch hätte recht, hast aber von den Feinheiten des Japanischen keine Ahnung!"), arguments("Bin gleich wieder da."), arguments("Wobei ich äußerst vorsichtig bin."), arguments("Es ist klar, dass ich äußerst vorsichtig mit den Informationen umgehe"), arguments("Es ist klar, dass ich äußerst vorsichtig bin."), arguments("Wobei er äußerst selten darüber spricht."), arguments("Wobei er äußerst selten über seine erste Frau spricht."), arguments("Das Wort „schreibst“ ist schön."), arguments("Die Jagd nach bin Laden."), arguments("Die Unterlagen solltet ihr gründlich durcharbeiten."), arguments("Er reagierte äußerst negativ."), arguments("Max und ich sollten das machen."), arguments("Osama bin Laden stammt aus Saudi-Arabien."), arguments("Solltet ihr das machen?"), arguments("Dann beende du den Auftrag und bring sie ihrem Vater."), arguments("- Wirst du ausflippen?"), arguments("Ein Geschenk, das er einst von Aphrodite erhalten hatte."), arguments("Wenn ich sterben sollte, wer würde sich dann um die Katze kümmern?"), arguments("Wenn er sterben sollte, wer würde sich dann um die Katze kümmern?"), arguments("Wenn sie sterben sollte, wer würde sich dann um die Katze kümmern?"), arguments("Wenn es sterben sollte, wer würde sich dann um die Katze kümmern?"), arguments("Wenn ihr sterben solltet, wer würde sich dann um die Katze kümmern?"), arguments("Wenn wir sterben sollten, wer würde sich dann um die Katze kümmern?"), arguments("Dafür erhielten er sowie der Hofgoldschmied Theodor Heiden einen Preis."), arguments("Probst wurde deshalb in den Medien gefeiert."), arguments("/usr/bin/firefox"), arguments("Das sind Leute, die viel mehr als ich wissen."), arguments("Das ist mir nicht klar, kannst ja mal beim Kunden nachfragen."), arguments("So tes\u00ADtest Du das mit dem soft hyphen."), arguments("Viele Brunnen in Italiens Hauptstadt sind bereits abgeschaltet."), arguments("„Werde ich tun!“"), arguments("Könntest dir mal eine Scheibe davon abschneiden!"), arguments("Müsstest dir das mal genauer anschauen."), arguments("Kannst ein neues Release machen."), arguments("Sie fragte: „Muss ich aussagen?“"), arguments("„Können wir bitte das Thema wechseln, denn ich möchte ungern darüber reden?“"), arguments("Er sagt: „Willst du behaupten, dass mein Sohn euch liebt?“"), arguments("Kannst mich gerne anrufen."), arguments("Kannst ihn gerne anrufen."), arguments("Kannst sie gerne anrufen."), arguments("Aber wie ich sehe, benötigt ihr Nachschub."), arguments("Wie ich sehe, benötigt ihr Nachschub."), arguments("Einer wie du kennt doch bestimmt viele Studenten."), arguments("Für Sie mache ich eine Ausnahme."), arguments("Ohne sie hätte ich das nicht geschafft."), arguments("Ohne Sie hätte ich das nicht geschafft."), arguments("Ich hoffe du auch."), arguments("Ich hoffe ihr auch."), arguments("Wird hoffen du auch."), arguments("Hab einen schönen Tag!"), arguments("Tom traue ich mehr als Maria."), arguments("Tom kenne ich nicht besonders gut, dafür aber seine Frau."), arguments("Tom habe ich heute noch nicht gesehen."), arguments("Tom bezahle ich gut."), arguments("Tom werde ich nicht noch mal um Hilfe bitten."), arguments("Tom konnte ich überzeugen, nicht aber Maria."), arguments("Mach du mal!"), arguments("Das bekomme ich nicht hin."), arguments("Dies betreffe insbesondere Nietzsches Aussagen zu Kant und der Evolutionslehre."), arguments("❌Du fühlst Dich unsicher?"), arguments("Bringst nicht einmal so etwas Einfaches zustande!"), arguments("Bekommst sogar eine Sicherheitszulage"), arguments("Dallun sagte nur, dass er gleich kommen wird und legte wieder auf."), arguments("Tinne, Elvis und auch ich werden gerne wiederkommen!"), arguments("Du bist Lehrer und weißt diese Dinge nicht?"), arguments("Die Frage lautet: Bist du bereit zu helfen?"), arguments("Ich will nicht so wie er enden."), arguments("Das heißt, wir geben einander oft nach als gute Freunde, ob wir gleich nicht einer Meinung sind."), arguments("Wir seh'n uns in Berlin."), arguments("Bist du bereit, darüber zu sprechen?"), arguments("Bist du schnell eingeschlafen?"), arguments("Im Gegenzug bin ich bereit, beim Türkischlernen zu helfen."), arguments("Das habe ich lange gesucht."), arguments("Dann solltest du schnell eine Nummer der sexy Omas wählen."), arguments("Vielleicht würdest du bereit sein, ehrenamtlich zu helfen."), arguments("Werde nicht alt, egal wie lange du lebst."), arguments("Du bist hingefallen und hast dir das Bein gebrochen."), arguments("Mögest du lange leben!"), arguments("Planst du lange hier zu bleiben?"), arguments("Du bist zwischen 11 und 12 Jahren alt und spielst gern Fußball bzw. möchtest damit anfangen?"), arguments("Ein großer Hadithwissenschaftler, Scheich Şemseddin Mehmed bin Muhammed-ül Cezri, kam in der Zeit von Mirza Uluğ Bey nach Semerkant."), arguments("Die Prüfbescheinigung bekommst du gleich nach der bestanden Prüfung vom Prüfer."), arguments("Du bist sehr schön und brauchst überhaupt gar keine Schminke zu verwenden."), arguments("Ist das so schnell, wie du gehen kannst?"), arguments("Egal wie lange du versuchst, die Leute davon zu überzeugen"), arguments("Du bist verheiratet und hast zwei Kinder."), arguments("Du bist aus Berlin und wohnst in Bonn."), arguments("Sie befestigen die Regalbretter vermittelst dreier Schrauben."), arguments("Meine Familie & ich haben uns ein neues Auto gekauft."), arguments("Der Bescheid lasse im übrigen die Abwägungen vermissen, wie die Betriebsprüfung zu den Sachverhaltsbeurteilungen gelange, die den von ihr bekämpften Bescheiden zugrundegelegt worden seien."), arguments("Die Bildung des Samens erfolgte laut Alkmaion im Gehirn, von wo aus er durch die Adern in den Hoden gelange."), arguments("Michael Redmond (geb. 1963, USA)."), arguments("Würd mich sehr freuen drüber."), arguments("Es würd' ein jeder Doktor sein, wenn's Wissen einging wie der Wein."), arguments("Bald merkte er, dass er dank seines Talents nichts mehr in der österreichischen Jazzszene lernen konnte."), arguments("»Alles, was wir dank dieses Projektes sehen werden, wird für uns neu sein«, so der renommierte Bienenforscher."), arguments("Und da wir äußerst Laissez-faire sind, kann man das auch machen."), arguments("Duzen, jemanden mit Du anreden, eine Sitte, die bei allen alten Völkern üblich war."), arguments("Schreibtischtäter wie Du sind doch eher selten."), arguments("Nee, geh du!"), arguments("Auch morgen lebe ich."), arguments("Auch morgen leben wir noch."), arguments("Auch morgen lebst du."), arguments("Auch morgen lebt er."), arguments("Auch wenn du leben möchtest."), arguments("auf der er sieben Jahre blieb."), arguments("Das absolute Ich ist nicht mit dem individuellen Geist zu verwechseln."), arguments("Das Ich ist keine Einbildung"), arguments("Das lyrische Ich ist verzweifelt."), arguments("Den Park, von dem er äußerst genaue Karten zeichnete."), arguments("Der auffälligste Ring ist der erster Ring, obwohl er verglichen mit den anderen Ringen sehr schwach erscheint."), arguments("Der Fehler, falls er bestehen sollte, ist schwerwiegend."), arguments("Der Vorfall, bei dem er einen Teil seines Vermögens verloren hat, ist lange vorbei."), arguments("Diese Lösung wurde in der 64'er beschrieben, kam jedoch nie."), arguments("Die Theorie, mit der ich arbeiten konnte."), arguments("Du bist nett."), arguments("Du kannst heute leider nicht kommen."), arguments("Du lebst."), arguments("Du wünschst dir so viel."), arguments("Er geht zu ihr."), arguments("Er ist nett."), arguments("Er kann heute leider nicht kommen."), arguments("Er lebt."), arguments("Er wisse nicht, ob er lachen oder weinen solle."), arguments("Er und du leben."), arguments("Er und ich leben."), arguments("Falls er bestehen sollte, gehen sie weg."), arguments("Heere, des Gottes der Schlachtreihen Israels, den du verhöhnt hast."), arguments("Ich bin"), arguments("Ich bin Frankreich!"), arguments("Ich bin froh, dass ich arbeiten kann."), arguments("Ich bin nett."), arguments("‚ich bin tot‘"), arguments("Ich kann heute leider nicht kommen."), arguments("Ich lebe."), arguments("Lebst du?"), arguments("Morgen kommen du und ich."), arguments("Morgen kommen er, den ich sehr mag, und ich."), arguments("Morgen kommen er und ich."), arguments("Morgen kommen ich und sie."), arguments("Morgen kommen wir und sie."), arguments("nachdem er erfahren hatte"), arguments("Nett bin ich."), arguments("Nett bist du."), arguments("Nett ist er."), arguments("Nett sind wir."), arguments("Niemand ahnte, dass er gewinnen könne."), arguments("Sie lebt und wir leben."), arguments("Sie und er leben."), arguments("Sind ich und Peter nicht nette Kinder?"), arguments("Sodass ich sagen möchte, dass unsere schönen Erinnerungen gut sind."), arguments("Wann ich meinen letzten Film drehen werde, ist unbekannt."), arguments("Was ich tun muss."), arguments("Welche Aufgaben er dabei tatsächlich übernehmen könnte"), arguments("wie er beschaffen war"), arguments("Wir gelangen zu dir."), arguments("Wir können heute leider nicht kommen."), arguments("Wir leben noch."), arguments("Wir sind nett."), arguments("Wobei wir benutzt haben, dass der Satz gilt."), arguments("Wünschst du dir mehr Zeit?"), arguments("Wyrjtjbst du?"), arguments("Wenn ich du wäre, würde ich das nicht machen."), arguments("Er sagte: „Darf ich bitten, mir zu folgen?“"), arguments("Ja sind ab morgen dabei."), arguments("Oh bin überfragt."), arguments("Angenommen, du wärst ich."), arguments("Ich denke, dass das Haus, in das er gehen will, heute Morgen gestrichen worden ist."), arguments("Ich hab mein Leben, leb du deines!"), arguments("Da freut er sich, wenn er schlafen geht und was findet."), arguments("John nimmt weiter an einem Abendkurs über Journalismus teil."), arguments("Viele nahmen an der Aktion teil und am Ende des rAAd-Events war die Tafel zwar bunt, aber leider überwogen die roten Kärtchen sehr deutlich."), arguments("Musst also nichts machen."), arguments("Eine Situation, wo der Stadtrat gleich mal zum Du übergeht."), arguments("Machen wir, sobald wir frische und neue Akkus haben."), arguments("Darfst nicht so reden, Franz!"), arguments("Finde du den Jungen."), arguments("Finde Du den Jungen."), arguments("Kümmerst dich ja gar nicht um sie."), arguments("Könntest was erfinden, wie dein Papa."), arguments("Siehst aus wie ein Wachhund."), arguments("Solltest es mal in seinem Büro versuchen."), arguments("Stehst einfach nicht zu mir."), arguments("Stellst für deinen Dad etwas zu Essen bereit."), arguments("Springst weit, oder?"), arguments("Wirst groß, was?"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testWrongVerb_87to92_94_96to97_104_106_108to111_111to112_112to117_117to126")
    public void testWrongVerb_87to92_94_96to97_104_106_108to111_111to112_112to117_117to126(String param1) throws IOException {
        assertBad(param1);
    }

    static public Stream<Arguments> Provider_testWrongVerb_87to92_94_96to97_104_106_108to111_111to112_112to117_117to126() {
        return Stream.of(arguments("Als Borcarbid weißt es eine hohe Härte auf."), arguments("Das greift auf Vorläuferinstitutionen bist auf die Zeit von 1234 zurück."), arguments("Die Eisenbahn dienst überwiegend dem Güterverkehr."), arguments("Die Unterlagen solltest ihr gründlich durcharbeiten."), arguments("Peter bin nett."), arguments("Weiter befindest sich im Osten die Gemeinde Dorf."), arguments("Ich geht jetzt nach Hause, weil ich schon zu spät bin."), arguments("„Du muss gehen.“"), arguments("Du weiß es doch."), arguments("Sie sagte zu mir: „Du muss gehen.“"), arguments("„Ich müsst alles machen.“"), arguments("„Ich könnt mich sowieso nicht verstehen.“"), arguments("Er sagte düster: Ich brauchen mich nicht böse angucken."), arguments("David sagte düster: Ich brauchen mich nicht böse angucken."), arguments("Ich setzet mich auf den weichen Teppich und kreuzte die Unterschenkel wie ein Japaner."), arguments("Ich brauchen einen Karren mit zwei Ochsen."), arguments("Ich haben meinen Ohrring fallen lassen."), arguments("Ich stehen Ihnen gerne für Rückfragen zur Verfügung."), arguments("Auch morgen leben du."), arguments("Du weiß noch, dass du das gestern gesagt hast."), arguments("Auch morgen leben du"), arguments("Auch morgen leben er."), arguments("Auch morgen leben ich."), arguments("Auch morgen lebte wir noch."), arguments("Du können heute leider nicht kommen."), arguments("Du leben."), arguments("Du wünscht dir so viel."), arguments("Ich leben."), arguments("Lebe du?"), arguments("Leben du?"), arguments("Nett sind du."), arguments("Nett sind er."), arguments("Wir könnt heute leider nicht kommen."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testWrongVerbSubject_93_98to103_109_114to116_118to119")
    public void testWrongVerbSubject_93_98to103_109_114to116_118to119(String param1, int param2) throws IOException {
        assertBad(param1, param2);
    }

    static public Stream<Arguments> Provider_testWrongVerbSubject_93_98to103_109_114to116_118to119() {
        return Stream.of(arguments("Du bin nett.", 2), arguments("Er bin nett.", 2), arguments("Er gelangst zu ihr.", 2), arguments("Er können heute leider nicht kommen.", "Subjekt (Er) und Prädikat (können)"), arguments("Er lebst.", 2), arguments("Ich bist nett.", 2), arguments("Ich kannst heute leider nicht kommen.", 2), arguments("Nett bist ich nicht.", 2), arguments("Nett warst wir.", 2), arguments("Wir bin nett.", 2), arguments("Wir gelangst zu ihr.", 2), arguments("Wünscht du dir mehr Zeit?", "Subjekt (du) und Prädikat (Wünscht)"), arguments("Wir lebst noch.", 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testWrongVerbSubject_95_110_113_121to122")
    public void testWrongVerbSubject_95_110_113_121to122(String param1, String param2, String param3, String param4, String param5, String param6, String param7) throws IOException {
        assertBad(param1, param2, param3, param4, param5, param6, param7);
    }

    static public Stream<Arguments> Provider_testWrongVerbSubject_95_110_113_121to122() {
        return Stream.of(arguments("Du können heute leider nicht kommen.", "Du könnest", "Du kannst", "Du könntest", "Wir können", "Sie können", "Du konntest"), arguments("Nett bist ich nicht.", 2, "bin ich", "bist du", "sei ich", "wäre ich", "war ich"), arguments("Nett sind er.", "sind wir", "sei er", "ist er", "sind sie", "wäre er", "war er"), arguments("Er sagte düster: „Ich brauchen mich nicht schuldig fühlen.“", 1, "Ich brauche", "Ich brauchte", "Ich bräuchte", "Wir brauchen", "Sie brauchen"), arguments("Er sagte: „Ich brauchen mich nicht schuldig fühlen.“", 1, "Ich brauche", "Ich brauchte", "Ich bräuchte", "Wir brauchen", "Sie brauchen"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testWrongVerbSubject_105_120")
    public void testWrongVerbSubject_105_120(String param1, String param2, String param3, String param4, String param5) throws IOException {
        assertBad(param1, param2, param3, param4, param5);
    }

    static public Stream<Arguments> Provider_testWrongVerbSubject_105_120() {
        return Stream.of(arguments("Ich leben.", "Ich lebe", "Ich lebte", "Wir leben", "Sie leben"), arguments("Wir lebst noch.", 2, "Wir leben", "Wir lebten", "Du lebst"));
    }
}
