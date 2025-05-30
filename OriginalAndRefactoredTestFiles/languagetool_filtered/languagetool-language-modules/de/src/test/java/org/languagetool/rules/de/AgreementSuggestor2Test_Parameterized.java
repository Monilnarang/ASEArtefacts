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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AgreementSuggestor2Test_Parameterized {

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
    public void testSuggestions_10() throws IOException {
        assertSuggestion1("die Kühlschranktest", "[die Kühlschrankteste, die Kühlschranktests, der Kühlschranktest, den Kühlschranktest, " + "dem Kühlschranktest, des Kühlschranktests, den Kühlschranktests, der Kühlschrankteste, der Kühlschranktests, " + "des Kühlschranktestes, den Kühlschranktesten]");
    }

    @Test
    public void testSuggestions_11() throws IOException {
        assertSuggestion1("die Kühlschrankverarbeitungstest", "[die Kühlschrankverarbeitungsteste, die Kühlschrankverarbeitungstests, " + "der Kühlschrankverarbeitungstest, den Kühlschrankverarbeitungstest, dem Kühlschrankverarbeitungstest, " + "des Kühlschrankverarbeitungstests, den Kühlschrankverarbeitungstests, der Kühlschrankverarbeitungsteste, " + "der Kühlschrankverarbeitungstests, des Kühlschrankverarbeitungstestes, den Kühlschrankverarbeitungstesten]");
    }

    @Test
    public void testSuggestions_13() throws IOException {
        assertSuggestion2("den vorangegangen Versuchen", "[den vorangegangenen Versuchen, das vorangegangene Versuchen, " + "dem vorangegangenen Versuchen, den vorangegangenen Versuch, der vorangegangene Versuch, des vorangegangenen Versuches, " + "das vorangegangene versuchen, dem vorangegangenen versuchen, des vorangegangenen Versuchens, der vorangegangenen Versuche, " + "dem vorangegangenen Versuch, des vorangegangenen Versuchs, des vorangegangenen versuchens, die vorangegangenen Versuche]");
    }

    @Test
    public void testDetAdjNounSuggestions_25() throws IOException {
        assertSuggestion2("der ikonischsten Gebäuden", "[den ikonischsten Gebäuden, der ikonischsten Gebäude, dem ikonischsten Gebäude, " + "des ikonischsten Gebäudes, die ikonischsten Gebäude, den ikonischen Gebäuden, der ikonischen Gebäude, das ikonischste Gebäude, " + "dem ikonischen Gebäude, des ikonischen Gebäudes, die ikonischen Gebäude, das ikonische Gebäude]");
    }

    @ParameterizedTest
    @MethodSource("Provider_testAdverbSuggestions_1_1to2_2to3_3to12")
    public void testAdverbSuggestions_1_1to2_2to3_3to12(String param1, String param2) throws IOException {
        assertSuggestion3(param1, param2, true);
    }

    static public Stream<Arguments> Provider_testAdverbSuggestions_1_1to2_2to3_3to12() {
        return Stream.of(arguments("ein sehr schönes Tisch", "[ein sehr schöner Tisch]"), arguments("eines sehr schönen Tisch", "[einen sehr schönen Tisch, einem sehr schönen Tisch, eines sehr schönen Tischs, eines sehr schönen Tisches]"), arguments("der sehr schönen Tischen", "[dem sehr schönen Tischen, den sehr schönen Tischen, der sehr schönen Tische]"), arguments("eine solides strategisches Fundament", "[ein solides strategisches Fundament]"), arguments("ein solide strategisches Fundament", "[ein solides strategisches Fundament]"), arguments("ein solides strategische Fundament", "[ein solides strategisches Fundament]"), arguments("ein solides strategisches Fundamente", "[ein solides strategisches Fundament]"), arguments("ein solides strategisches Fundamenten", "[ein solides strategisches Fundament]"), arguments("die meisten kommerziellen System", "[die meisten kommerziellen Systeme]"), arguments("die meisten kommerzielle Systeme", "[die meisten kommerziellen Systeme]"), arguments("die meisten kommerziell Systeme", "[die meisten kommerziellen Systeme]"), arguments("die meisten kommerziell System", "[die meisten kommerziellen Systeme]"), arguments("Die meisten kommerziellen System", "[Die meisten kommerziellen Systeme]"), arguments("der meisten kommerziellen System", "[der meisten kommerziellen Systeme]"), arguments("der meisten kommerziellen Systems", "[der meisten kommerziellen Systeme]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSuggestions_1_1to2_2to3_3to4_4to5_5to6_6to7_7to9_14_16to31")
    public void testSuggestions_1_1to2_2to3_3to4_4to5_5to6_6to7_7to9_14_16to31(String param1, String param2) throws IOException {
        assertSuggestion1(param1, param2);
    }

    static public Stream<Arguments> Provider_testSuggestions_1_1to2_2to3_3to4_4to5_5to6_6to7_7to9_14_16to31() {
        return Stream.of(arguments("deine Buch", "[dein Buch, deinem Buch, deine Bücher, deinem Buche, deines Buchs, deines Buches, deiner Bücher, deinen Büchern]"), arguments("dieser Buch", "[dieses Buch, diesem Buch, dies Buch, dieser Bücher, diesem Buche, dieses Buchs, dieses Buches, diese Bücher, diesen Büchern]"), arguments("die Kabels", "[die Kabel, des Kabels, der Kabel, den Kabel, dem Kabel, das Kabel, den Kabeln]"), arguments("die LAN-Kabels", "[die LAN-Kabel, des LAN-Kabels, der LAN-Kabel, den LAN-Kabel, dem LAN-Kabel, das LAN-Kabel, den LAN-Kabeln]"), arguments("mehrere Kabels", "[mehrere Kabel, mehreren Kabeln, mehrerer Kabel]"), arguments("mehrere LAN-Kabels", "[mehrere LAN-Kabel, mehreren LAN-Kabeln, mehrerer LAN-Kabel]"), arguments("mehrere WLAN-LAN-Kabels", "[mehrere WLAN-LAN-Kabel, mehreren WLAN-LAN-Kabeln, mehrerer WLAN-LAN-Kabel]"), arguments("Ihren Verständnis", "[Ihrem Verständnis, Ihr Verständnis, Ihres Verständnisses]"), arguments("des Züchten", "[das Züchten, dem Züchten, des Züchtens]"), arguments("der Blutflusses", "[des Blutflusses, der Blutfluss, der Blutflüsse, den Blutfluss, dem Blutfluss, den Blutflüssen, die Blutflüsse]"), arguments("dasselbe Erinnerung", "[dieselbe Erinnerung, derselben Erinnerung]"), arguments("derselbe Erinnerung", "[derselben Erinnerung, dieselbe Erinnerung]"), arguments("derselbe Frau", "[derselben Frau, dieselbe Frau]"), arguments("dieselben Erinnerung", "[dieselbe Erinnerung, derselben Erinnerung]"), arguments("derselben Mann", "[derselbe Mann, denselben Mann, demselben Mann, desselben Manns, desselben Mannes]"), arguments("demselben Frau", "[derselben Frau, dieselbe Frau]"), arguments("desselben Mann", "[denselben Mann, demselben Mann, desselben Manns, derselbe Mann, desselben Mannes]"), arguments("Desselben Mann", "[Denselben Mann, Demselben Mann, Desselben Manns, Derselbe Mann, Desselben Mannes]"), arguments("meinem Eltern", "[meine Eltern, meinen Eltern, meiner Eltern]"), arguments("eure Auge", "[eurem Auge, eure Augen, euer Auge, euerem Auge, euerm Auge, eures Auges, euren Augen, eueres Auges, euerer Augen]"), arguments("welche Internetvideo", "[welches Internetvideo, welchem Internetvideo, welche Internetvideos, welches Internetvideos, welchen Internetvideos, welcher Internetvideos]"), arguments("welchen Internetvideo", "[welches Internetvideo, welchem Internetvideo, welchen Internetvideos, welches Internetvideos, welche Internetvideos, welcher Internetvideos]"), arguments("welches Mann", "[welcher Mann, welchen Mann, welchem Mann, welches Manns, welches Mannes, welche Männer, welcher Männer, welchen Männern]"), arguments("welchem Frau", "[welche Frau, welcher Frau, welche Frauen, welchen Frauen, welcher Frauen]"), arguments("welcher Kind", "[welches Kind, welchem Kind, welcher Kinder, welchem Kinde, welches Kinds, welches Kindes, welche Kinder, welchen Kindern]"), arguments("Welcher Kind", "[Welches Kind, Welchem Kind, Welcher Kinder, Welchem Kinde, Welches Kinds, Welches Kindes, Welche Kinder, Welchen Kindern]"), arguments("Der Haus", "[Dem Haus, Das Haus, Der Häuser, Dem Hause, Des Hauses, Die Häuser, Den Häusern]"), arguments("der Haus", "[dem Haus, das Haus, der Häuser, dem Hause, des Hauses, die Häuser, den Häusern]"), arguments("das Haus", "[dem Haus, dem Hause, des Hauses, die Häuser, der Häuser, den Häusern]"), arguments("der Haus", "[dem Haus, das Haus, der Häuser, dem Hause, des Hauses, die Häuser, den Häusern]"), arguments("die Haus", "[das Haus, dem Haus, die Häuser, dem Hause, des Hauses, der Häuser, den Häusern]"), arguments("die Hauses", "[des Hauses, die Häuser, dem Hause, das Haus, dem Haus, der Häuser, den Häusern]"), arguments("die Häusern", "[die Häuser, den Häusern, der Häuser, dem Hause, des Hauses, das Haus, dem Haus]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSuggestions_12_22")
    public void testSuggestions_12_22(String param1, String param2, String param3) throws IOException {
        assertSuggestion2(param1, param2 + param3);
    }

    static public Stream<Arguments> Provider_testSuggestions_12_22() {
        return Stream.of(arguments("den gleiche Gebiete", "[dem gleichen Gebiete, den gleichen Gebieten, der gleichen Gebiete, ", "das gleiche Gebiet, die gleichen Gebiete, dem gleichen Gebiet, des gleichen Gebietes, des gleichen Gebiets]"), arguments("das schönsten Auto", "[das schönste Auto, dem schönsten Auto, das schöne Auto, des schönsten Autos, die schönsten Autos, ", "den schönsten Autos, der schönsten Autos, dem schönen Auto, des schönen Autos, die schönen Autos, den schönen Autos, der schönen Autos]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSuggestions_1to15_15to21_23to24_26")
    public void testSuggestions_1to15_15to21_23to24_26(String param1, String param2) throws IOException {
        assertSuggestion2(param1, param2);
    }

    static public Stream<Arguments> Provider_testSuggestions_1to15_15to21_23to24_26() {
        return Stream.of(arguments("ein anstrengenden Tag", "[ein anstrengender Tag, ein anstrengendes Tag, einen anstrengenden Tag, einem anstrengenden Tag, eines anstrengenden Tags, eines anstrengenden Tages]"), arguments("die neuen Unterlage", "[die neue Unterlage, die neuen Unterlagen, der neuen Unterlage, den neuen Unterlagen, der neuen Unterlagen]"), arguments("der neue Unterlagen", "[der neuen Unterlagen, der neuen Unterlage, den neuen Unterlagen, die neue Unterlage, die neuen Unterlagen]"), arguments("eine schönes Auto", "[ein schönes Auto, einem schönen Auto, eines schönen Autos]"), arguments("eine schöne Auto", "[ein schönes Auto, einem schönen Auto, eines schönen Autos]"), arguments("ein schöne Auto", "[ein schönes Auto, einem schönen Auto, eines schönen Autos]"), arguments("einen großen Auto", "[einem großen Auto, eines großen Autos, ein großes Auto]"), arguments("der schöne Auto", "[das schöne Auto, dem schönen Auto, der schönen Autos, des schönen Autos, den schönen Autos, die schönen Autos]"), arguments("der kleine Auto", "[das kleine Auto, dem kleinen Auto, der kleinen Autos, des kleinen Autos, den kleinen Autos, die kleinen Autos]"), arguments("der kleiner Auto", "[dem kleinen Auto, der kleinen Autos, das kleine Auto, des kleinen Autos, den kleinen Autos, die kleinen Autos]"), arguments("das stärkste Körperteil", "[der stärkste Körperteil, den stärksten Körperteil, dem stärksten Körperteil, des stärksten Körperteils, dem stärksten Körperteile, des stärksten Körperteiles, die stärksten Körperteile, der stärksten Körperteile, den stärksten Körperteilen]"), arguments("die benötigten Unterlage", "[die benötigte Unterlage, die benötigten Unterlagen, der benötigten Unterlage, den benötigten Unterlagen, der benötigten Unterlagen]"), arguments("eine benötigten Unterlage", "[eine benötigte Unterlage, einer benötigten Unterlage]"), arguments("die voller Verzierungen", "[die vollen Verzierungen, die volle Verzierung, den vollen Verzierungen, der vollen Verzierungen, der vollen Verzierung]"), arguments("zu zukünftigen Vorstands", "[]"), arguments("des südlichen Kontinent", "[den südlichen Kontinent, dem südlichen Kontinent, des südlichen Kontinents, des südlichen Kontinentes, der südliche Kontinent, der südlichen Kontinente, die südlichen Kontinente, den südlichen Kontinenten]"), arguments("die erwartet Entwicklung", "[die erwartete Entwicklung, der erwarteten Entwicklung, die erwarteten Entwicklungen, den erwarteten Entwicklungen, der erwarteten Entwicklungen]"), arguments("die verschieden Ämter", "[die verschiedenen Ämter, der verschiedenen Ämter, den verschiedenen Ämtern, das verschiedene Amt, dem verschiedenen Amte, des verschiedenen Amtes, dem verschiedenen Amt, des verschiedenen Amts]"), arguments("keine richtiger Fahrerin", "[keine richtige Fahrerin, keiner richtigen Fahrerin, keine richtigen Fahrerinnen, keinen richtigen Fahrerinnen, keiner richtigen Fahrerinnen]"), arguments("das schönes Auto", "[das schöne Auto, dem schönen Auto, des schönen Autos, die schönen Autos, den schönen Autos, der schönen Autos]"), arguments("das schöneren Auto", "[das schönere Auto, dem schöneren Auto, des schöneren Autos, die schöneren Autos, den schöneren Autos, der schöneren Autos]"), arguments("das schönstem Auto", "[das schönste Auto, dem schönsten Auto, des schönsten Autos, die schönsten Autos, den schönsten Autos, der schönsten Autos]"), arguments("der ikonischen Gebäuden", "[den ikonischen Gebäuden, der ikonischen Gebäude, dem ikonischen Gebäude, des ikonischen Gebäudes, die ikonischen Gebäude, das ikonische Gebäude]"), arguments("der ikonischeren Gebäuden", "[den ikonischeren Gebäuden, der ikonischeren Gebäude, dem ikonischeren Gebäude, des ikonischeren Gebäudes, die ikonischeren Gebäude, das ikonischere Gebäude]"), arguments("den meisten Fälle", "[den meisten Fällen, der meisten Fälle, die meisten Fälle]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSuggestionsHaus_8to15")
    public void testSuggestionsHaus_8to15(String param1, String param2) throws IOException {
        assertSuggestion1(param1, param2, true);
    }

    static public Stream<Arguments> Provider_testSuggestionsHaus_8to15() {
        return Stream.of(arguments("Der Haus", "[Dem Haus, Das Haus, Der Häuser]"), arguments("der Haus", "[dem Haus, das Haus, der Häuser]"), arguments("das Haus", "[dem Haus]"), arguments("der Haus", "[dem Haus, das Haus, der Häuser]"), arguments("die Haus", "[das Haus, dem Haus, die Häuser]"), arguments("die Hauses", "[des Hauses, die Häuser]"), arguments("die Häusern", "[die Häuser, den Häusern]"), arguments("unsere Buch", "[unser Buch, unserem Buch, unsere Bücher]"));
    }
}
