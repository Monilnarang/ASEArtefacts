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

public class MorfologikFrenchSpellerRuleTest_Purified {

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
    public void testMorfologikSpeller_1() throws IOException {
        assertSuggestionsContain("darriver", "d'arriver", "arriver");
    }

    @Test
    public void testMorfologikSpeller_2() throws IOException {
        assertSuggestionsContain("decodés", "décodés", "décodes", "de codés");
    }

    @Test
    public void testApostrophes_1() throws Exception {
        assertNoMatches("d'1");
    }

    @Test
    public void testApostrophes_2() throws Exception {
        assertNoMatches("l'email");
    }

    @Test
    public void testApostrophes_3() throws Exception {
        assertNoMatches("Aujourd'hui et jusqu'à demain.");
    }

    @Test
    public void testApostrophes_4() throws Exception {
        assertNoMatches("Aujourd’hui et jusqu’à demain.");
    }

    @Test
    public void testApostrophes_5() throws Exception {
        assertNoMatches("L'Allemagne et l'Italie.");
    }

    @Test
    public void testApostrophes_6() throws Exception {
        assertMatches("L’allemagne et l’italie.", 2);
    }

    @Test
    public void testApostrophes_7() throws Exception {
        assertNoMatches("de Harvard ou d'Harvard");
    }

    @Test
    public void testHyphenated_1() throws Exception {
        assertNoMatches("L'Haÿ-les-Roses");
    }

    @Test
    public void testHyphenated_2() throws Exception {
        assertMatches("L'Haÿ les Roses", 1);
    }

    @Test
    public void testHyphenated_3() throws Exception {
        assertNoMatches("Il arrive après-demain.");
    }

    @Test
    public void testSanity_1() throws Exception {
        assertNoMatches("Un test simple.");
    }

    @Test
    public void testSanity_2() throws Exception {
        assertNoMatches("Le cœur, la sœur.");
    }

    @Test
    public void testSanity_3() throws Exception {
        assertNoMatches("Ç'avait");
    }

    @Test
    public void testSanity_4() throws Exception {
        assertNoMatches("LanguageTool");
    }

    @Test
    public void testSanity_5() throws Exception {
        assertMatches("Un test simpple.", 1);
    }

    @Test
    public void testCorrectWords_1() throws Exception {
        assertNoMatches("Écoute-moi.");
    }

    @Test
    public void testCorrectWords_2() throws Exception {
        assertNoMatches("35%");
    }

    @Test
    public void testCorrectWords_3() throws Exception {
        assertNoMatches("20$");
    }

    @Test
    public void testCorrectWords_4() throws Exception {
        assertNoMatches("4x4");
    }

    @Test
    public void testCorrectWords_5() throws Exception {
        assertNoMatches("300 000 yen");
    }

    @Test
    public void testCorrectWords_6() throws Exception {
        assertNoMatches("20°C");
    }

    @Test
    public void testCorrectWords_7() throws Exception {
        assertNoMatches("même s'il coûte 10.000 yens");
    }

    @Test
    public void testCorrectWords_8() throws Exception {
        assertNoMatches("J'ai 38,9 de fièvre.");
    }

    @Test
    public void testCorrectWords_9() throws Exception {
        assertNoMatches("Thunderbird 2.0.0.14");
    }

    @Test
    public void testCorrectWords_10() throws Exception {
        assertNoMatches("Va-t’en !");
    }

    @Test
    public void testCorrectWords_11() throws Exception {
        assertNoMatches("-Je ne suis pas venu par manque de temps.");
    }

    @Test
    public void testCorrectWords_12() throws Exception {
        assertNoMatches("12hr-14hr");
    }

    @Test
    public void testCorrectWords_13() throws Exception {
        assertNoMatches("Dominique Strauss-Kahn");
    }

    @Test
    public void testCorrectWords_14() throws Exception {
        assertNoMatches("L'ONU");
    }

    @Test
    public void testCorrectWords_15() throws Exception {
        assertNoMatches("d'1");
    }

    @Test
    public void testCorrectWords_16() throws Exception {
        assertNoMatches("L'email");
    }

    @Test
    public void testCorrectWords_17() throws Exception {
        assertNoMatches("Et d'Harvard");
    }

    @Test
    public void testCorrectWords_18() throws Exception {
        assertNoMatches("déconfinement");
    }

    @Test
    public void testCorrectWords_19() throws Exception {
        assertNoMatches("Déconfinement");
    }

    @Test
    public void testCorrectWords_20() throws Exception {
        assertNoMatches("Le Déconfinement");
    }

    @Test
    public void testCorrectWords_21() throws Exception {
        assertNoMatches("Cesse de t'autoflageller.");
    }

    @Test
    public void testCorrectWords_22() throws Exception {
        assertNoMatches("L'iPhone");
    }

    @Test
    public void testCorrectWords_23() throws Exception {
        assertNoMatches("Une #sprache @mentioned mywebsite.org ereredd.7z, domaine .com, NH₄OH");
    }

    @Test
    public void testMultiwords_1() throws Exception {
        assertNoMatches("vox populi");
    }

    @Test
    public void testMultiwords_2() throws Exception {
        assertNoMatches("statu quo");
    }

    @Test
    public void testMultiwords_3() throws Exception {
        assertNoMatches("Bugs Bunny");
    }

    @Test
    public void testMixedCase_1() throws Exception {
        assertNoMatches("pH");
    }

    @Test
    public void testMixedCase_2() throws Exception {
        assertSingleMatchWithSuggestions("Mcdonald", "McDonald", "Macdonald");
    }

    @Test
    public void testMixedCase_3() throws Exception {
        assertNoMatches("McDonald's");
    }

    @Test
    public void testMixedCase_4() throws Exception {
        assertNoMatches("McDonald’s");
    }

    @Test
    public void testMixedCase_5() throws Exception {
        assertNoMatches("McDonald");
    }

    @Test
    public void testMixedCase_6() throws Exception {
        assertMatches("thisisanerror", 1);
    }

    @Test
    public void testMixedCase_7() throws Exception {
        assertMatches("thisIsAnError", 1);
    }

    @Test
    public void testMixedCase_8() throws Exception {
        assertMatches("Thisisanerror", 1);
    }

    @Test
    public void testMixedCase_9() throws Exception {
        assertMatches("ThisIsAnError", 1);
    }

    @Test
    public void testMixedCase_10() throws Exception {
        assertExactSuggestion("Wordpress", "WordPress");
    }

    @Test
    public void testMixedCase_11() throws Exception {
        assertExactSuggestion("wordpress", "WordPress");
    }

    @Test
    public void testMixedCase_12() throws Exception {
        assertExactSuggestion("Playstation", "PlayStation");
    }

    @Test
    public void testIncorrectWords_1() throws Exception {
        assertExactSuggestion("Décu", "Déçu");
    }

    @Test
    public void testIncorrectWords_2() throws Exception {
        assertExactSuggestion("etant", "étant");
    }

    @Test
    public void testIncorrectWords_3() throws Exception {
        assertExactSuggestion("Cliqez", "Cliquez");
    }

    @Test
    public void testIncorrectWords_4() throws Exception {
        assertExactSuggestion("cliqez", "cliquez");
    }

    @Test
    public void testIncorrectWords_5() throws Exception {
        assertExactSuggestion("problemes", "problèmes");
    }

    @Test
    public void testIncorrectWords_6() throws Exception {
        assertExactSuggestion("la sante", "santé");
    }

    @Test
    public void testIncorrectWords_7() throws Exception {
        assertSuggestionsContain("damazon", "d'Amazon", "Amazon", "d'Amazone", "Damazan");
    }

    @Test
    public void testIncorrectWords_8() throws Exception {
        assertSuggestionsContain("coulurs", "couleurs");
    }

    @Test
    public void testIncorrectWords_9() throws Exception {
        assertSingleMatchWithSuggestions("Den", "De");
    }

    @Test
    public void testIncorrectWords_10() throws Exception {
        assertSuggestionsContain("offe", "effet", "offre", "coffre", "bouffe");
    }

    @Test
    public void testIncorrectWords_11() throws Exception {
        assertSuggestionsContain("camara", "caméra", "camard");
    }

    @Test
    public void testIncorrectWords_12() throws Exception {
        assertSuggestionsContain("boton", "bâton", "béton", "Boston", "coton", "bouton");
    }

    @Test
    public void testIncorrectWords_13() throws Exception {
        assertSuggestionsContain("La journé", "journée");
    }

    @Test
    public void testIncorrectWords_14() throws Exception {
        assertExactSuggestion("deja", "déjà");
    }

    @Test
    public void testWordSplitting_1() throws Exception {
        assertSingleMatchWithSuggestions("BretagneItinéraire", "Bretagne Itinéraire");
    }

    @Test
    public void testWordSplitting_2() throws Exception {
        assertSingleMatchWithSuggestions("BruxellesCapitale", "Bruxelles Capitale");
    }

    @Test
    public void testWordSplitting_3() throws Exception {
        assertExactSuggestion("Parcontre", "Par contre");
    }

    @Test
    public void testWordSplitting_4() throws Exception {
        assertExactSuggestion("parcontre", "par contre");
    }

    @Test
    public void testWordSplitting_5() throws Exception {
        assertExactSuggestion("Situé àseulement 9 km", "seulement", "à seulement");
    }

    @Test
    public void testVerbsWithPronouns_1() throws Exception {
        assertSingleMatchWithSuggestions("ecoute-moi", "Écoute", "Écouté", "Coûte");
    }

    @Test
    public void testVerbsWithPronouns_2() throws Exception {
        assertSingleMatchWithSuggestions("ecrit-il", "Écrit", "Décrit");
    }

    @Test
    public void testVerbsWithPronouns_3() throws Exception {
        assertSingleMatchWithSuggestions("Etais-tu", "Étais", "Étés");
    }

    @Test
    public void testVerbsWithPronouns_4() throws Exception {
        assertSingleMatchWithSuggestions("etais-tu", "Étais", "Étés");
    }

    @Test
    public void testVerbsWithPronouns_5() throws Exception {
        assertSingleMatchWithSuggestions("etiez-vous", "Étiez");
    }

    @Test
    public void testVerbsWithPronouns_6() throws Exception {
        assertSingleMatchWithSuggestions("étaistu", "étais-tu");
    }

    @Test
    public void testVerbsWithPronouns_7() throws Exception {
        assertSingleMatchWithSuggestions("etaistu", "étais-tu");
    }

    @Test
    public void testVerbsWithPronouns_8() throws Exception {
        assertSingleMatchWithSuggestions("voulezvous", "voulez-vous");
    }

    @Test
    public void testVerbsWithPronouns_9() throws Exception {
        assertSingleMatchWithSuggestions("ecoutemoi", "écoute-moi");
    }

    @Test
    public void testVerbsWithPronouns_10() throws Exception {
        assertSingleMatchWithSuggestions("mappelle", "m'appelle", "mappe-le");
    }

    @Test
    public void testVerbsWithPronouns_11() throws Exception {
        assertSingleMatchWithSuggestions("mapelle", "ma pelle", "m'appelle");
    }

    @Test
    public void testVerbsWithPronouns_12() throws Exception {
        assertSingleMatchWithSuggestions("allonsy", "allons-y");
    }

    @Test
    public void testVerbsWithPronouns_13() throws Exception {
        assertSingleMatchWithSuggestions("buvezen", "buvez-en");
    }

    @Test
    public void testVerbsWithPronouns_14() throws Exception {
        assertSingleMatchWithSuggestions("avaisje", "avais-je");
    }

    @Test
    public void testVerbsWithPronouns_15() throws Exception {
        assertSingleMatchWithSuggestions("depeche-toi", "Dépêche", "Dépêché");
    }

    @Test
    public void testVerbsWithPronouns_16() throws Exception {
        assertSingleMatchWithSuggestions("depechetoi", "dépêche-toi");
    }

    @Test
    public void testVerbsWithPronouns_17() throws Exception {
        assertSingleMatchWithSuggestions("sattendre", "s'attendre", "attendre");
    }

    @Test
    public void testVerbsWithPronouns_18() throws Exception {
        assertSingleMatchWithSuggestions("preferes-tu", "Préférés", "Préfères");
    }

    @Test
    public void testVerbsWithPronouns_19() throws Exception {
        assertSingleMatchWithSuggestions("àllonsy", "allons-y");
    }

    @Test
    public void testWordEdgeElision_1() throws Exception {
        assertSingleMatchWithSuggestions("Lhomme", "L'homme");
    }

    @Test
    public void testWordEdgeElision_2() throws Exception {
        assertSingleMatchWithSuggestions("dhommes", "d'hommes");
    }

    @Test
    public void testWordEdgeElision_3() throws Exception {
        assertSingleMatchWithSuggestions("ladolescence", "l'adolescence", "adolescence");
    }

    @Test
    public void testWordEdgeElision_4() throws Exception {
        assertSingleMatchWithSuggestions("qu’il sagissait", "il s'agissait");
    }

    @Test
    public void testWordEdgeElision_5() throws Exception {
        assertSingleMatchWithSuggestions("dIsraël", "d'Israël");
    }

    @Test
    public void testWordEdgeElision_6() throws Exception {
        assertSingleMatchWithSuggestions("dOrient", "d'Orient");
    }

    @Test
    public void testTokenisation_1() throws Exception {
        assertSingleMatchWithSuggestions("123heures", "123 heures");
    }

    @Test
    public void testTokenisation_2() throws Exception {
        assertNoMatches("⏰heures");
    }

    @Test
    public void testTokenisation_3() throws Exception {
        assertSingleMatchWithSuggestions("⏰heuras", "heures");
    }

    @Test
    public void testTokenisation_4() throws Exception {
        assertSingleMatchWithSuggestions("©heures", "© heures");
    }

    @Test
    public void testTokenisation_5() throws Exception {
        assertNoMatches("►heures");
    }

    @Test
    public void testTokenisation_6() throws Exception {
        assertNoMatches("◦heures");
    }

    @Test
    public void testDigits_1() throws Exception {
        assertSingleMatchWithSuggestions("windows1", "Windows");
    }

    @Test
    public void testDigits_2() throws Exception {
        assertSingleMatchWithSuggestions("windows95", "Windows 95");
    }

    @Test
    public void testDigits_3() throws Exception {
        assertSingleMatchWithSuggestions("à1930", "à 1930");
    }

    @Test
    public void testToImprove_1() throws Exception {
        assertSuggestionsContain("language", "l'engage", "l'aiguage", "l'engagé", "langage", "langages");
    }

    @Test
    public void testToImprove_2() throws Exception {
        assertSuggestionsContain("saperçoit", "sa perçoit", "s'aperçoit");
    }

    @Test
    public void testToImprove_3() throws Exception {
        assertSuggestionsContain("saperçu", "sa perçu", "aperçu");
    }

    @Test
    public void testMultitokens_1() throws IOException {
        assertNoMatches("MERCEDES-BENZ");
    }

    @Test
    public void testMultitokens_2() throws IOException {
        assertNoMatches("Walt Disney Animation Studios");
    }

    @Test
    public void testMultitokens_3() throws IOException {
        assertNoMatches("MÉTÉO-FRANCE");
    }

    @Test
    public void testMultitokens_4() throws IOException {
        assertNoMatches("CLERMONT-FERRAND");
    }
}
