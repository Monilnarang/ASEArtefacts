package org.languagetool.rules.fr;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.French;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.spelling.SpellingCheckRule;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MorfologikFrenchSpellerRuleTest_Parameterized {

    private static final JLanguageTool lt = new JLanguageTool(French.getInstance());

    private final SpellingCheckRule rule;

    public MorfologikFrenchSpellerRuleTest() throws IOException {
        rule = French.getInstance().getDefaultSpellingRule();
    }

    private List<String> getTopSuggestions(RuleMatch match, int maxSuggestions) {
        return match.getSuggestedReplacements().subList(0, Math.min(maxSuggestions, match.getSuggestedReplacements().size()));
    }

    private void assertMatches(String input, int expectedMatches) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        assertEquals(expectedMatches, matches.length);
    }

    private void assertNoMatches(String input) throws IOException {
        assertMatches(input, 0);
    }

    private void assertIterateOverSuggestions(List<String> returnedSuggestions, String[] expectedSuggestions) {
        for (String expectedSuggestion : expectedSuggestions) {
            assert returnedSuggestions.contains(expectedSuggestion) : "Expected suggestions to contain '" + expectedSuggestion + "' but got " + returnedSuggestions;
        }
    }

    private void assertSuggestionsContain(String input, String... expectedSuggestions) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        List<String> suggestions = getTopSuggestions(matches[0], 5);
        assertIterateOverSuggestions(suggestions, expectedSuggestions);
    }

    private void assertSingleMatchWithSuggestions(String input, String... expectedSuggestions) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        assertEquals(1, matches.length);
        List<String> suggestions = getTopSuggestions(matches[0], 5);
        assertIterateOverSuggestions(suggestions, expectedSuggestions);
    }

    public void assertSingleMatchZeroSuggestions(String input) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        assertEquals(1, matches.length);
        List<String> suggestions = matches[0].getSuggestedReplacements();
        assertEquals(0, suggestions.size());
    }

    private void assertExactSuggestion(String input, String... expected) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        int i = 0;
        List<String> suggestions = getTopSuggestions(matches[0], 5);
        for (String s : expected) {
            assertEquals(s, suggestions.get(i));
            i++;
        }
    }

    @Test
    public void testMorfologikSpeller_2() throws IOException {
        assertSuggestionsContain("decodés", "décodés", "décodes", "de codés");
    }

    @Test
    public void testWordSplitting_5() throws Exception {
        assertExactSuggestion("Situé àseulement 9 km", "seulement", "à seulement");
    }

    @Test
    public void testVerbsWithPronouns_1() throws Exception {
        assertSingleMatchWithSuggestions("ecoute-moi", "Écoute", "Écouté", "Coûte");
    }

    @ParameterizedTest
    @MethodSource("Provider_testMorfologikSpeller_1to3_11")
    public void testMorfologikSpeller_1to3_11(String param1, String param2, String param3) throws IOException {
        assertSuggestionsContain(param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testMorfologikSpeller_1to3_11() {
        return Stream.of(arguments("darriver", "d'arriver", "arriver"), arguments("camara", "caméra", "camard"), arguments("saperçoit", "sa perçoit", "s'aperçoit"), arguments("saperçu", "sa perçu", "aperçu"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testApostrophes_1_1_1_1_1_1_1to2_2_2_2_2_2to3_3_3_3_3_3_3to4_4_4_4_4to5_5_5_5to6_6to7_7to23")
    public void testApostrophes_1_1_1_1_1_1_1to2_2_2_2_2_2to3_3_3_3_3_3_3to4_4_4_4_4to5_5_5_5to6_6to7_7to23(String param1) throws Exception {
        assertNoMatches(param1);
    }

    static public Stream<Arguments> Provider_testApostrophes_1_1_1_1_1_1_1to2_2_2_2_2_2to3_3_3_3_3_3_3to4_4_4_4_4to5_5_5_5to6_6to7_7to23() {
        return Stream.of(arguments("d'1"), arguments("l'email"), arguments("Aujourd'hui et jusqu'à demain."), arguments("Aujourd’hui et jusqu’à demain."), arguments("L'Allemagne et l'Italie."), arguments("de Harvard ou d'Harvard"), arguments("L'Haÿ-les-Roses"), arguments("Il arrive après-demain."), arguments("Un test simple."), arguments("Le cœur, la sœur."), arguments("Ç'avait"), arguments("LanguageTool"), arguments("Écoute-moi."), arguments("35%"), arguments("20$"), arguments("4x4"), arguments("300 000 yen"), arguments("20°C"), arguments("même s'il coûte 10.000 yens"), arguments("J'ai 38,9 de fièvre."), arguments("Thunderbird 2.0.0.14"), arguments("Va-t’en !"), arguments("-Je ne suis pas venu par manque de temps."), arguments("12hr-14hr"), arguments("Dominique Strauss-Kahn"), arguments("L'ONU"), arguments("d'1"), arguments("L'email"), arguments("Et d'Harvard"), arguments("déconfinement"), arguments("Déconfinement"), arguments("Le Déconfinement"), arguments("Cesse de t'autoflageller."), arguments("L'iPhone"), arguments("Une #sprache @mentioned mywebsite.org ereredd.7z, domaine .com, NH₄OH"), arguments("vox populi"), arguments("statu quo"), arguments("Bugs Bunny"), arguments("pH"), arguments("McDonald's"), arguments("McDonald’s"), arguments("McDonald"), arguments("⏰heures"), arguments("►heures"), arguments("◦heures"), arguments("MERCEDES-BENZ"), arguments("Walt Disney Animation Studios"), arguments("MÉTÉO-FRANCE"), arguments("CLERMONT-FERRAND"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testApostrophes_2_5to6_6to9")
    public void testApostrophes_2_5to6_6to9(String param1, int param2) throws Exception {
        assertMatches(param1, param2);
    }

    static public Stream<Arguments> Provider_testApostrophes_2_5to6_6to9() {
        return Stream.of(arguments("L’allemagne et l’italie.", 2), arguments("L'Haÿ les Roses", 1), arguments("Un test simpple.", 1), arguments("thisisanerror", 1), arguments("thisIsAnError", 1), arguments("Thisisanerror", 1), arguments("ThisIsAnError", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMixedCase_2_2to3_3to4_10to11_15_17to18")
    public void testMixedCase_2_2to3_3to4_10to11_15_17to18(String param1, String param2, String param3) throws Exception {
        assertSingleMatchWithSuggestions(param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testMixedCase_2_2to3_3to4_10to11_15_17to18() {
        return Stream.of(arguments("Mcdonald", "McDonald", "Macdonald"), arguments("ecrit-il", "Écrit", "Décrit"), arguments("Etais-tu", "Étais", "Étés"), arguments("etais-tu", "Étais", "Étés"), arguments("mappelle", "m'appelle", "mappe-le"), arguments("mapelle", "ma pelle", "m'appelle"), arguments("depeche-toi", "Dépêche", "Dépêché"), arguments("sattendre", "s'attendre", "attendre"), arguments("preferes-tu", "Préférés", "Préfères"), arguments("ladolescence", "l'adolescence", "adolescence"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMixedCase_1to3_3to4_4to6_10to12_14")
    public void testMixedCase_1to3_3to4_4to6_10to12_14(String param1, String param2) throws Exception {
        assertExactSuggestion(param1, param2);
    }

    static public Stream<Arguments> Provider_testMixedCase_1to3_3to4_4to6_10to12_14() {
        return Stream.of(arguments("Wordpress", "WordPress"), arguments("wordpress", "WordPress"), arguments("Playstation", "PlayStation"), arguments("Décu", "Déçu"), arguments("etant", "étant"), arguments("Cliqez", "Cliquez"), arguments("cliqez", "cliquez"), arguments("problemes", "problèmes"), arguments("la sante", "santé"), arguments("deja", "déjà"), arguments("Parcontre", "Par contre"), arguments("parcontre", "par contre"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIncorrectWords_7_10")
    public void testIncorrectWords_7_10(String param1, String param2, String param3, String param4, String param5) throws Exception {
        assertSuggestionsContain(param1, param2, param3, param4, param5);
    }

    static public Stream<Arguments> Provider_testIncorrectWords_7_10() {
        return Stream.of(arguments("damazon", "d'Amazon", "Amazon", "d'Amazone", "Damazan"), arguments("offe", "effet", "offre", "coffre", "bouffe"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIncorrectWords_8_13")
    public void testIncorrectWords_8_13(String param1, String param2) throws Exception {
        assertSuggestionsContain(param1, param2);
    }

    static public Stream<Arguments> Provider_testIncorrectWords_8_13() {
        return Stream.of(arguments("coulurs", "couleurs"), arguments("La journé", "journée"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIncorrectWords_1_1_1_1to2_2_2to3_3to4_4to5_5to6_6to9_9_12to14_16_19")
    public void testIncorrectWords_1_1_1_1to2_2_2to3_3to4_4to5_5to6_6to9_9_12to14_16_19(String param1, String param2) throws Exception {
        assertSingleMatchWithSuggestions(param1, param2);
    }

    static public Stream<Arguments> Provider_testIncorrectWords_1_1_1_1to2_2_2to3_3to4_4to5_5to6_6to9_9_12to14_16_19() {
        return Stream.of(arguments("Den", "De"), arguments("BretagneItinéraire", "Bretagne Itinéraire"), arguments("BruxellesCapitale", "Bruxelles Capitale"), arguments("etiez-vous", "Étiez"), arguments("étaistu", "étais-tu"), arguments("etaistu", "étais-tu"), arguments("voulezvous", "voulez-vous"), arguments("ecoutemoi", "écoute-moi"), arguments("allonsy", "allons-y"), arguments("buvezen", "buvez-en"), arguments("avaisje", "avais-je"), arguments("depechetoi", "dépêche-toi"), arguments("àllonsy", "allons-y"), arguments("Lhomme", "L'homme"), arguments("dhommes", "d'hommes"), arguments("qu’il sagissait", "il s'agissait"), arguments("dIsraël", "d'Israël"), arguments("dOrient", "d'Orient"), arguments("123heures", "123 heures"), arguments("⏰heuras", "heures"), arguments("©heures", "© heures"), arguments("windows1", "Windows"), arguments("windows95", "Windows 95"), arguments("à1930", "à 1930"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIncorrectWords_1_12")
    public void testIncorrectWords_1_12(String param1, String param2, String param3, String param4, String param5, String param6) throws Exception {
        assertSuggestionsContain(param1, param2, param3, param4, param5, param6);
    }

    static public Stream<Arguments> Provider_testIncorrectWords_1_12() {
        return Stream.of(arguments("boton", "bâton", "béton", "Boston", "coton", "bouton"), arguments("language", "l'engage", "l'aiguage", "l'engagé", "langage", "langages"));
    }
}
