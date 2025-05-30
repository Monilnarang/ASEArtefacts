package org.languagetool.rules.de;

import org.junit.Test;
import org.languagetool.*;
import org.languagetool.synthesis.Synthesizer;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AgreementSuggestor2Test_Purified {

    private final Language german = Languages.getLanguageForShortCode("de-DE");

    private final Synthesizer synthesizer = german.getSynthesizer();

    private final JLanguageTool lt = new JLanguageTool(german);

    private void assertSuggestion1(String input, String expectedSuggestions) throws IOException {
        assertSuggestion1(input, expectedSuggestions, false);
    }

    private void assertSuggestion1(String input, String expectedSuggestions, boolean filter) throws IOException {
        AnalyzedSentence analyzedSentence = lt.getAnalyzedSentence(input);
        List<AnalyzedTokenReadings> tags = Arrays.asList(analyzedSentence.getTokensWithoutWhitespace());
        if (analyzedSentence.getTokensWithoutWhitespace().length != 3) {
            fail("Please use 2 tokens (det, noun) as input: " + input);
        }
        AgreementSuggestor2 suggestor = new AgreementSuggestor2(synthesizer, tags.get(1), tags.get(2), null);
        assertThat(suggestor.getSuggestions(filter).toString(), is(expectedSuggestions));
    }

    private void assertSuggestion2(String input, String expectedSuggestions) throws IOException {
        AnalyzedSentence analyzedSentence = lt.getAnalyzedSentence(input);
        List<AnalyzedTokenReadings> tags = Arrays.asList(analyzedSentence.getTokensWithoutWhitespace());
        if (analyzedSentence.getTokensWithoutWhitespace().length != 4) {
            fail("Please use 3 tokens (det, adj, noun) as input: " + input);
        }
        AgreementSuggestor2 suggestor = new AgreementSuggestor2(synthesizer, tags.get(1), tags.get(2), tags.get(3), null);
        assertThat(suggestor.getSuggestions().toString(), is(expectedSuggestions));
    }

    private void assertSuggestion3(String input, String expectedSuggestions, boolean filter) throws IOException {
        AnalyzedSentence analyzedSentence = lt.getAnalyzedSentence(input);
        List<AnalyzedTokenReadings> tags = Arrays.asList(analyzedSentence.getTokensWithoutWhitespace());
        if (analyzedSentence.getTokensWithoutWhitespace().length != 5) {
            fail("Please use 4 tokens (det, adj, adj, noun) as input: " + input);
        }
        AgreementSuggestor2 suggestor = new AgreementSuggestor2(synthesizer, tags.get(1), tags.get(2), tags.get(3), tags.get(4), null);
        assertThat(suggestor.getSuggestions(filter).toString(), is(expectedSuggestions));
    }

    @Test
    public void testAdverbSuggestions_1() throws IOException {
        assertSuggestion3("ein sehr schönes Tisch", "[ein sehr schöner Tisch]", true);
    }

    @Test
    public void testAdverbSuggestions_2() throws IOException {
        assertSuggestion3("eines sehr schönen Tisch", "[einen sehr schönen Tisch, einem sehr schönen Tisch, eines sehr schönen Tischs, eines sehr schönen Tisches]", true);
    }

    @Test
    public void testAdverbSuggestions_3() throws IOException {
        assertSuggestion3("der sehr schönen Tischen", "[dem sehr schönen Tischen, den sehr schönen Tischen, der sehr schönen Tische]", true);
    }

    @Test
    public void testSuggestions_1() throws IOException {
        assertSuggestion1("deine Buch", "[dein Buch, deinem Buch, deine Bücher, deinem Buche, deines Buchs, deines Buches, deiner Bücher, deinen Büchern]");
    }

    @Test
    public void testSuggestions_2() throws IOException {
        assertSuggestion1("dieser Buch", "[dieses Buch, diesem Buch, dies Buch, dieser Bücher, diesem Buche, dieses Buchs, dieses Buches, diese Bücher, diesen Büchern]");
    }

    @Test
    public void testSuggestions_3() throws IOException {
        assertSuggestion1("die Kabels", "[die Kabel, des Kabels, der Kabel, den Kabel, dem Kabel, das Kabel, den Kabeln]");
    }

    @Test
    public void testSuggestions_4() throws IOException {
        assertSuggestion1("die LAN-Kabels", "[die LAN-Kabel, des LAN-Kabels, der LAN-Kabel, den LAN-Kabel, dem LAN-Kabel, das LAN-Kabel, den LAN-Kabeln]");
    }

    @Test
    public void testSuggestions_5() throws IOException {
        assertSuggestion1("mehrere Kabels", "[mehrere Kabel, mehreren Kabeln, mehrerer Kabel]");
    }

    @Test
    public void testSuggestions_6() throws IOException {
        assertSuggestion1("mehrere LAN-Kabels", "[mehrere LAN-Kabel, mehreren LAN-Kabeln, mehrerer LAN-Kabel]");
    }

    @Test
    public void testSuggestions_7() throws IOException {
        assertSuggestion1("mehrere WLAN-LAN-Kabels", "[mehrere WLAN-LAN-Kabel, mehreren WLAN-LAN-Kabeln, mehrerer WLAN-LAN-Kabel]");
    }

    @Test
    public void testSuggestions_8() throws IOException {
        assertSuggestion1("Ihren Verständnis", "[Ihrem Verständnis, Ihr Verständnis, Ihres Verständnisses]");
    }

    @Test
    public void testSuggestions_9() throws IOException {
        assertSuggestion1("des Züchten", "[das Züchten, dem Züchten, des Züchtens]");
    }

    @Test
    public void testSuggestions_10() throws IOException {
        assertSuggestion1("die Kühlschranktest", "[die Kühlschrankteste, die Kühlschranktests, der Kühlschranktest, den Kühlschranktest, " + "dem Kühlschranktest, des Kühlschranktests, den Kühlschranktests, der Kühlschrankteste, der Kühlschranktests, " + "des Kühlschranktestes, den Kühlschranktesten]");
    }

    @Test
    public void testSuggestions_11() throws IOException {
        assertSuggestion1("die Kühlschrankverarbeitungstest", "[die Kühlschrankverarbeitungsteste, die Kühlschrankverarbeitungstests, " + "der Kühlschrankverarbeitungstest, den Kühlschrankverarbeitungstest, dem Kühlschrankverarbeitungstest, " + "des Kühlschrankverarbeitungstests, den Kühlschrankverarbeitungstests, der Kühlschrankverarbeitungsteste, " + "der Kühlschrankverarbeitungstests, des Kühlschrankverarbeitungstestes, den Kühlschrankverarbeitungstesten]");
    }

    @Test
    public void testSuggestions_12() throws IOException {
        assertSuggestion2("den gleiche Gebiete", "[dem gleichen Gebiete, den gleichen Gebieten, der gleichen Gebiete, " + "das gleiche Gebiet, die gleichen Gebiete, dem gleichen Gebiet, des gleichen Gebietes, des gleichen Gebiets]");
    }

    @Test
    public void testSuggestions_13() throws IOException {
        assertSuggestion2("den vorangegangen Versuchen", "[den vorangegangenen Versuchen, das vorangegangene Versuchen, " + "dem vorangegangenen Versuchen, den vorangegangenen Versuch, der vorangegangene Versuch, des vorangegangenen Versuches, " + "das vorangegangene versuchen, dem vorangegangenen versuchen, des vorangegangenen Versuchens, der vorangegangenen Versuche, " + "dem vorangegangenen Versuch, des vorangegangenen Versuchs, des vorangegangenen versuchens, die vorangegangenen Versuche]");
    }

    @Test
    public void testSuggestions_14() throws IOException {
        assertSuggestion1("der Blutflusses", "[des Blutflusses, der Blutfluss, der Blutflüsse, den Blutfluss, dem Blutfluss, den Blutflüssen, die Blutflüsse]");
    }

    @Test
    public void testSuggestions_15() throws IOException {
        assertSuggestion2("ein anstrengenden Tag", "[ein anstrengender Tag, ein anstrengendes Tag, einen anstrengenden Tag, einem anstrengenden Tag, eines anstrengenden Tags, eines anstrengenden Tages]");
    }

    @Test
    public void testSuggestions_16() throws IOException {
        assertSuggestion1("dasselbe Erinnerung", "[dieselbe Erinnerung, derselben Erinnerung]");
    }

    @Test
    public void testSuggestions_17() throws IOException {
        assertSuggestion1("derselbe Erinnerung", "[derselben Erinnerung, dieselbe Erinnerung]");
    }

    @Test
    public void testSuggestions_18() throws IOException {
        assertSuggestion1("derselbe Frau", "[derselben Frau, dieselbe Frau]");
    }

    @Test
    public void testSuggestions_19() throws IOException {
        assertSuggestion1("dieselben Erinnerung", "[dieselbe Erinnerung, derselben Erinnerung]");
    }

    @Test
    public void testSuggestions_20() throws IOException {
        assertSuggestion1("derselben Mann", "[derselbe Mann, denselben Mann, demselben Mann, desselben Manns, desselben Mannes]");
    }

    @Test
    public void testSuggestions_21() throws IOException {
        assertSuggestion1("demselben Frau", "[derselben Frau, dieselbe Frau]");
    }

    @Test
    public void testSuggestions_22() throws IOException {
        assertSuggestion1("desselben Mann", "[denselben Mann, demselben Mann, desselben Manns, derselbe Mann, desselben Mannes]");
    }

    @Test
    public void testSuggestions_23() throws IOException {
        assertSuggestion1("Desselben Mann", "[Denselben Mann, Demselben Mann, Desselben Manns, Derselbe Mann, Desselben Mannes]");
    }

    @Test
    public void testSuggestions_24() throws IOException {
        assertSuggestion1("meinem Eltern", "[meine Eltern, meinen Eltern, meiner Eltern]");
    }

    @Test
    public void testSuggestions_25() throws IOException {
        assertSuggestion1("eure Auge", "[eurem Auge, eure Augen, euer Auge, euerem Auge, euerm Auge, eures Auges, euren Augen, eueres Auges, euerer Augen]");
    }

    @Test
    public void testSuggestions_26() throws IOException {
        assertSuggestion1("welche Internetvideo", "[welches Internetvideo, welchem Internetvideo, welche Internetvideos, welches Internetvideos, welchen Internetvideos, welcher Internetvideos]");
    }

    @Test
    public void testSuggestions_27() throws IOException {
        assertSuggestion1("welchen Internetvideo", "[welches Internetvideo, welchem Internetvideo, welchen Internetvideos, welches Internetvideos, welche Internetvideos, welcher Internetvideos]");
    }

    @Test
    public void testSuggestions_28() throws IOException {
        assertSuggestion1("welches Mann", "[welcher Mann, welchen Mann, welchem Mann, welches Manns, welches Mannes, welche Männer, welcher Männer, welchen Männern]");
    }

    @Test
    public void testSuggestions_29() throws IOException {
        assertSuggestion1("welchem Frau", "[welche Frau, welcher Frau, welche Frauen, welchen Frauen, welcher Frauen]");
    }

    @Test
    public void testSuggestions_30() throws IOException {
        assertSuggestion1("welcher Kind", "[welches Kind, welchem Kind, welcher Kinder, welchem Kinde, welches Kinds, welches Kindes, welche Kinder, welchen Kindern]");
    }

    @Test
    public void testSuggestions_31() throws IOException {
        assertSuggestion1("Welcher Kind", "[Welches Kind, Welchem Kind, Welcher Kinder, Welchem Kinde, Welches Kinds, Welches Kindes, Welche Kinder, Welchen Kindern]");
    }

    @Test
    public void testSuggestionsHaus_1() throws IOException {
        assertSuggestion1("Der Haus", "[Dem Haus, Das Haus, Der Häuser, Dem Hause, Des Hauses, Die Häuser, Den Häusern]");
    }

    @Test
    public void testSuggestionsHaus_2() throws IOException {
        assertSuggestion1("der Haus", "[dem Haus, das Haus, der Häuser, dem Hause, des Hauses, die Häuser, den Häusern]");
    }

    @Test
    public void testSuggestionsHaus_3() throws IOException {
        assertSuggestion1("das Haus", "[dem Haus, dem Hause, des Hauses, die Häuser, der Häuser, den Häusern]");
    }

    @Test
    public void testSuggestionsHaus_4() throws IOException {
        assertSuggestion1("der Haus", "[dem Haus, das Haus, der Häuser, dem Hause, des Hauses, die Häuser, den Häusern]");
    }

    @Test
    public void testSuggestionsHaus_5() throws IOException {
        assertSuggestion1("die Haus", "[das Haus, dem Haus, die Häuser, dem Hause, des Hauses, der Häuser, den Häusern]");
    }

    @Test
    public void testSuggestionsHaus_6() throws IOException {
        assertSuggestion1("die Hauses", "[des Hauses, die Häuser, dem Hause, das Haus, dem Haus, der Häuser, den Häusern]");
    }

    @Test
    public void testSuggestionsHaus_7() throws IOException {
        assertSuggestion1("die Häusern", "[die Häuser, den Häusern, der Häuser, dem Hause, des Hauses, das Haus, dem Haus]");
    }

    @Test
    public void testSuggestionsHaus_8() throws IOException {
        assertSuggestion1("Der Haus", "[Dem Haus, Das Haus, Der Häuser]", true);
    }

    @Test
    public void testSuggestionsHaus_9() throws IOException {
        assertSuggestion1("der Haus", "[dem Haus, das Haus, der Häuser]", true);
    }

    @Test
    public void testSuggestionsHaus_10() throws IOException {
        assertSuggestion1("das Haus", "[dem Haus]", true);
    }

    @Test
    public void testSuggestionsHaus_11() throws IOException {
        assertSuggestion1("der Haus", "[dem Haus, das Haus, der Häuser]", true);
    }

    @Test
    public void testSuggestionsHaus_12() throws IOException {
        assertSuggestion1("die Haus", "[das Haus, dem Haus, die Häuser]", true);
    }

    @Test
    public void testSuggestionsHaus_13() throws IOException {
        assertSuggestion1("die Hauses", "[des Hauses, die Häuser]", true);
    }

    @Test
    public void testSuggestionsHaus_14() throws IOException {
        assertSuggestion1("die Häusern", "[die Häuser, den Häusern]", true);
    }

    @Test
    public void testSuggestionsHaus_15() throws IOException {
        assertSuggestion1("unsere Buch", "[unser Buch, unserem Buch, unsere Bücher]", true);
    }

    @Test
    public void testDetAdjNounSuggestions_1() throws IOException {
        assertSuggestion2("die neuen Unterlage", "[die neue Unterlage, die neuen Unterlagen, der neuen Unterlage, den neuen Unterlagen, der neuen Unterlagen]");
    }

    @Test
    public void testDetAdjNounSuggestions_2() throws IOException {
        assertSuggestion2("der neue Unterlagen", "[der neuen Unterlagen, der neuen Unterlage, den neuen Unterlagen, die neue Unterlage, die neuen Unterlagen]");
    }

    @Test
    public void testDetAdjNounSuggestions_3() throws IOException {
        assertSuggestion2("eine schönes Auto", "[ein schönes Auto, einem schönen Auto, eines schönen Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_4() throws IOException {
        assertSuggestion2("eine schöne Auto", "[ein schönes Auto, einem schönen Auto, eines schönen Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_5() throws IOException {
        assertSuggestion2("ein schöne Auto", "[ein schönes Auto, einem schönen Auto, eines schönen Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_6() throws IOException {
        assertSuggestion2("einen großen Auto", "[einem großen Auto, eines großen Autos, ein großes Auto]");
    }

    @Test
    public void testDetAdjNounSuggestions_7() throws IOException {
        assertSuggestion2("der schöne Auto", "[das schöne Auto, dem schönen Auto, der schönen Autos, des schönen Autos, den schönen Autos, die schönen Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_8() throws IOException {
        assertSuggestion2("der kleine Auto", "[das kleine Auto, dem kleinen Auto, der kleinen Autos, des kleinen Autos, den kleinen Autos, die kleinen Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_9() throws IOException {
        assertSuggestion2("der kleiner Auto", "[dem kleinen Auto, der kleinen Autos, das kleine Auto, des kleinen Autos, den kleinen Autos, die kleinen Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_10() throws IOException {
        assertSuggestion2("das stärkste Körperteil", "[der stärkste Körperteil, den stärksten Körperteil, dem stärksten Körperteil, des stärksten Körperteils, dem stärksten Körperteile, des stärksten Körperteiles, die stärksten Körperteile, der stärksten Körperteile, den stärksten Körperteilen]");
    }

    @Test
    public void testDetAdjNounSuggestions_11() throws IOException {
        assertSuggestion2("die benötigten Unterlage", "[die benötigte Unterlage, die benötigten Unterlagen, der benötigten Unterlage, den benötigten Unterlagen, der benötigten Unterlagen]");
    }

    @Test
    public void testDetAdjNounSuggestions_12() throws IOException {
        assertSuggestion2("eine benötigten Unterlage", "[eine benötigte Unterlage, einer benötigten Unterlage]");
    }

    @Test
    public void testDetAdjNounSuggestions_13() throws IOException {
        assertSuggestion2("die voller Verzierungen", "[die vollen Verzierungen, die volle Verzierung, den vollen Verzierungen, der vollen Verzierungen, der vollen Verzierung]");
    }

    @Test
    public void testDetAdjNounSuggestions_14() throws IOException {
        assertSuggestion2("zu zukünftigen Vorstands", "[]");
    }

    @Test
    public void testDetAdjNounSuggestions_15() throws IOException {
        assertSuggestion2("des südlichen Kontinent", "[den südlichen Kontinent, dem südlichen Kontinent, des südlichen Kontinents, des südlichen Kontinentes, der südliche Kontinent, der südlichen Kontinente, die südlichen Kontinente, den südlichen Kontinenten]");
    }

    @Test
    public void testDetAdjNounSuggestions_16() throws IOException {
        assertSuggestion2("die erwartet Entwicklung", "[die erwartete Entwicklung, der erwarteten Entwicklung, die erwarteten Entwicklungen, den erwarteten Entwicklungen, der erwarteten Entwicklungen]");
    }

    @Test
    public void testDetAdjNounSuggestions_17() throws IOException {
        assertSuggestion2("die verschieden Ämter", "[die verschiedenen Ämter, der verschiedenen Ämter, den verschiedenen Ämtern, das verschiedene Amt, dem verschiedenen Amte, des verschiedenen Amtes, dem verschiedenen Amt, des verschiedenen Amts]");
    }

    @Test
    public void testDetAdjNounSuggestions_18() throws IOException {
        assertSuggestion2("keine richtiger Fahrerin", "[keine richtige Fahrerin, keiner richtigen Fahrerin, keine richtigen Fahrerinnen, keinen richtigen Fahrerinnen, keiner richtigen Fahrerinnen]");
    }

    @Test
    public void testDetAdjNounSuggestions_19() throws IOException {
        assertSuggestion2("das schönes Auto", "[das schöne Auto, dem schönen Auto, des schönen Autos, die schönen Autos, den schönen Autos, der schönen Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_20() throws IOException {
        assertSuggestion2("das schöneren Auto", "[das schönere Auto, dem schöneren Auto, des schöneren Autos, die schöneren Autos, den schöneren Autos, der schöneren Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_21() throws IOException {
        assertSuggestion2("das schönstem Auto", "[das schönste Auto, dem schönsten Auto, des schönsten Autos, die schönsten Autos, den schönsten Autos, der schönsten Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_22() throws IOException {
        assertSuggestion2("das schönsten Auto", "[das schönste Auto, dem schönsten Auto, das schöne Auto, des schönsten Autos, die schönsten Autos, " + "den schönsten Autos, der schönsten Autos, dem schönen Auto, des schönen Autos, die schönen Autos, den schönen Autos, der schönen Autos]");
    }

    @Test
    public void testDetAdjNounSuggestions_23() throws IOException {
        assertSuggestion2("der ikonischen Gebäuden", "[den ikonischen Gebäuden, der ikonischen Gebäude, dem ikonischen Gebäude, des ikonischen Gebäudes, die ikonischen Gebäude, das ikonische Gebäude]");
    }

    @Test
    public void testDetAdjNounSuggestions_24() throws IOException {
        assertSuggestion2("der ikonischeren Gebäuden", "[den ikonischeren Gebäuden, der ikonischeren Gebäude, dem ikonischeren Gebäude, des ikonischeren Gebäudes, die ikonischeren Gebäude, das ikonischere Gebäude]");
    }

    @Test
    public void testDetAdjNounSuggestions_25() throws IOException {
        assertSuggestion2("der ikonischsten Gebäuden", "[den ikonischsten Gebäuden, der ikonischsten Gebäude, dem ikonischsten Gebäude, " + "des ikonischsten Gebäudes, die ikonischsten Gebäude, den ikonischen Gebäuden, der ikonischen Gebäude, das ikonischste Gebäude, " + "dem ikonischen Gebäude, des ikonischen Gebäudes, die ikonischen Gebäude, das ikonische Gebäude]");
    }

    @Test
    public void testDetAdjNounSuggestions_26() throws IOException {
        assertSuggestion2("den meisten Fälle", "[den meisten Fällen, der meisten Fälle, die meisten Fälle]");
    }

    @Test
    public void testDetAdjAdjNounSuggestions_1() throws IOException {
        assertSuggestion3("eine solides strategisches Fundament", "[ein solides strategisches Fundament]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_2() throws IOException {
        assertSuggestion3("ein solide strategisches Fundament", "[ein solides strategisches Fundament]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_3() throws IOException {
        assertSuggestion3("ein solides strategische Fundament", "[ein solides strategisches Fundament]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_4() throws IOException {
        assertSuggestion3("ein solides strategisches Fundamente", "[ein solides strategisches Fundament]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_5() throws IOException {
        assertSuggestion3("ein solides strategisches Fundamenten", "[ein solides strategisches Fundament]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_6() throws IOException {
        assertSuggestion3("die meisten kommerziellen System", "[die meisten kommerziellen Systeme]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_7() throws IOException {
        assertSuggestion3("die meisten kommerzielle Systeme", "[die meisten kommerziellen Systeme]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_8() throws IOException {
        assertSuggestion3("die meisten kommerziell Systeme", "[die meisten kommerziellen Systeme]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_9() throws IOException {
        assertSuggestion3("die meisten kommerziell System", "[die meisten kommerziellen Systeme]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_10() throws IOException {
        assertSuggestion3("Die meisten kommerziellen System", "[Die meisten kommerziellen Systeme]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_11() throws IOException {
        assertSuggestion3("der meisten kommerziellen System", "[der meisten kommerziellen Systeme]", true);
    }

    @Test
    public void testDetAdjAdjNounSuggestions_12() throws IOException {
        assertSuggestion3("der meisten kommerziellen Systems", "[der meisten kommerziellen Systeme]", true);
    }
}
