package org.languagetool.rules.pt;

import org.junit.BeforeClass;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MorfologikPortugueseSpellerRuleTest_Purified {

    private static MorfologikPortugueseSpellerRule ruleBR;

    private static JLanguageTool ltBR;

    private static MorfologikPortugueseSpellerRule rulePT;

    private static JLanguageTool ltPT;

    private static MorfologikPortugueseSpellerRule ruleMZ;

    private static JLanguageTool ltMZ;

    public MorfologikPortugueseSpellerRuleTest() throws IOException {
    }

    private static MorfologikPortugueseSpellerRule getSpellerRule(String countryCode) throws IOException {
        return new MorfologikPortugueseSpellerRule(TestTools.getMessages("pt"), Languages.getLanguageForShortCode("pt-" + countryCode), null, null);
    }

    private static JLanguageTool getLT(String countryCode) {
        return new JLanguageTool(Languages.getLanguageForShortCode("pt-" + countryCode));
    }

    @BeforeClass
    public static void setUp() throws IOException {
        ltBR = getLT("BR");
        ltPT = getLT("PT");
        ltMZ = getLT("MZ");
        ruleBR = getSpellerRule("BR");
        rulePT = getSpellerRule("PT");
        ruleMZ = getSpellerRule("MZ");
    }

    private List<String> getFirstSuggestions(RuleMatch match, int max) {
        return match.getSuggestedReplacements().stream().limit(5).collect(Collectors.toList());
    }

    private void assertErrorLength(String sentence, int length, JLanguageTool lt, MorfologikPortugueseSpellerRule rule, String[] suggestions) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(length, matches.length);
        if (matches.length > 0) {
            List<String> returnedSuggestions = getFirstSuggestions(matches[0], 5);
            assert returnedSuggestions.containsAll(Arrays.asList(suggestions));
        }
    }

    private void assertSingleErrorWithNegativeSuggestion(String sentence, JLanguageTool lt, MorfologikPortugueseSpellerRule rule, String badSuggestion) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
        if (matches.length > 0) {
            List<String> returnedSuggestions = matches[0].getSuggestedReplacements();
            assertFalse(returnedSuggestions.contains(badSuggestion));
        }
    }

    private void assertSingleErrorAndPos(String sentence, JLanguageTool lt, MorfologikPortugueseSpellerRule rule, String[] suggestions, int fromPos, int toPos) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        for (int i = 0; i < suggestions.length; i++) {
            assertEquals(suggestions[i], matches[0].getSuggestedReplacements().get(i));
        }
        assertEquals(1, matches.length);
        assertEquals(fromPos, matches[0].getFromPos());
        assertEquals(toPos, matches[0].getToPos());
    }

    private void assertNoErrors(String sentence, JLanguageTool lt, MorfologikPortugueseSpellerRule rule) throws IOException {
        assertErrorLength(sentence, 0, lt, rule, new String[] {});
    }

    private void assertSingleError(String sentence, JLanguageTool lt, MorfologikPortugueseSpellerRule rule, String... suggestions) throws IOException {
        assertErrorLength(sentence, 1, lt, rule, suggestions);
    }

    private void assertSingleExactError(String sentence, JLanguageTool lt, MorfologikPortugueseSpellerRule rule, String suggestion, String message, String id) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
        if (matches.length > 0) {
            RuleMatch match = matches[0];
            List<String> returnedSuggestions = match.getSuggestedReplacements();
            assert Objects.equals(returnedSuggestions.get(0), suggestion);
            assert Objects.equals(match.getMessage(), message);
            assert Objects.equals(match.getSpecificRuleId(), id);
        }
    }

    private void assertTwoWayDialectError(String sentenceBR, String sentencePT) throws IOException {
        String brMessage = "Possível erro de ortografia: esta é a grafia utilizada no português europeu.";
        String ptMessage = "Possível erro de ortografia: esta é a grafia utilizada no português brasileiro.";
        assertNoErrors(sentenceBR, ltBR, ruleBR);
        assertSingleExactError(sentencePT, ltBR, ruleBR, sentenceBR, brMessage, "MORFOLOGIK_RULE_PT_BR_DIALECT");
        assertNoErrors(sentencePT, ltPT, rulePT);
        assertSingleExactError(sentenceBR, ltPT, rulePT, sentencePT, ptMessage, "MORFOLOGIK_RULE_PT_PT_DIALECT");
    }

    private void assertTwoWayOrthographicAgreementError(String sentence90, String sentence45) throws IOException {
        assertNoErrors(sentence90, ltPT, rulePT);
        assertSingleError(sentence45, ltPT, rulePT, sentence90);
        assertNoErrors(sentence45, ltMZ, ruleMZ);
        assertSingleError(sentence90, ltMZ, ruleMZ, sentence45);
    }

    @Test
    public void testPortugueseSpellerSanity_1() throws Exception {
        assertNoErrors("oogaboogatestword", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerSanity_2() throws Exception {
        assertNoErrors("oogaboogatestwordBR", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerSanity_3() throws Exception {
        assertNoErrors("oogaboogatestword", ltPT, rulePT);
    }

    @Test
    public void testPortugueseSpellerSanity_4() throws Exception {
        assertNoErrors("oogaboogatestwordPT", ltPT, rulePT);
    }

    @Test
    public void testPortugueseSpellerSanity_5() throws Exception {
        assertNoErrors("oogaboogatestwordPT90", ltPT, rulePT);
    }

    @Test
    public void testPortugueseSpellerAcceptsVerbsWithProductivePrefixes_1() throws Exception {
        assertNoErrors("soto-pôr", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsVerbsWithProductivePrefixes_2() throws Exception {
        assertNoErrors("soto-trepar", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsVerbsWithProductivePrefixes_3() throws Exception {
        assertSingleError("reune", ltBR, ruleBR, "reúne");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_1() throws Exception {
        assertTwoWayDialectError("anônimo", "anónimo");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_2() throws Exception {
        assertTwoWayDialectError("caratês", "caratés");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_3() throws Exception {
        assertTwoWayDialectError("detectariam", "detetariam");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_4() throws Exception {
        assertTwoWayDialectError("tênis", "ténis");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_5() throws Exception {
        assertTwoWayDialectError("ônus", "ónus");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_6() throws Exception {
        assertTwoWayDialectError("pênis", "pénis");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_7() throws Exception {
        assertTwoWayDialectError("detecção", "deteção");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_8() throws Exception {
        assertTwoWayDialectError("dezesseis", "dezasseis");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_9() throws Exception {
        assertTwoWayDialectError("bidê", "bidé");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_10() throws Exception {
        assertTwoWayDialectError("detectar", "detetar");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_11() throws Exception {
        assertTwoWayDialectError("napoleônia", "napoleónia");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_12() throws Exception {
        assertTwoWayDialectError("hiperêmese", "hiperémese");
    }

    @Test
    public void testPortugueseSymmetricalDialectDifferences_13() throws Exception {
        assertTwoWayDialectError("bebê", "bebé");
    }

    @Test
    public void testPortugueseAsymmetricalDialectDifferences_1() throws Exception {
        assertSingleExactError("facto", ltBR, ruleBR, "fato", "Possível erro de ortografia: esta é a grafia utilizada no português europeu.", "MORFOLOGIK_RULE_PT_BR_DIALECT");
    }

    @Test
    public void testPortugueseAsymmetricalDialectDifferences_2() throws Exception {
        assertNoErrors("fato", ltPT, rulePT);
    }

    @Test
    public void testPortugueseSpellingAgreementVariation_1() throws Exception {
        assertTwoWayOrthographicAgreementError("detetar", "detectar");
    }

    @Test
    public void testPortugueseSpellingAgreementVariation_2() throws Exception {
        assertTwoWayOrthographicAgreementError("abjeção", "abjecção");
    }

    @Test
    public void testPortugueseSpellingAgreementVariation_3() throws Exception {
        assertTwoWayOrthographicAgreementError("direção", "direcção");
    }

    @Test
    public void testPortugueseSpellingAgreementVariation_4() throws Exception {
        assertTwoWayOrthographicAgreementError("diretamente", "directamente");
    }

    @Test
    public void testPortugueseSpellingAgreementVariation_5() throws Exception {
        assertTwoWayOrthographicAgreementError("afetada", "afectada");
    }

    @Test
    public void testPortugueseSpellingDiminutives_1() throws Exception {
        assertNoErrors("franguito", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingDiminutives_2() throws Exception {
        assertNoErrors("irmãozinho", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingDiminutives_3() throws Exception {
        assertNoErrors("retratozinho", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingDiminutives_4() throws Exception {
        assertNoErrors("notebookzinho", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingDiminutives_5() throws Exception {
        assertNoErrors("finaizitos", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingDiminutives_6() throws Exception {
        assertNoErrors("cafezito", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingDiminutives_7() throws Exception {
        assertNoErrors("chorõezitos", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingDiminutives_8() throws Exception {
        assertNoErrors("assadito", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingProductiveAdverbs_1() throws Exception {
        assertNoErrors("enciclopedicamente", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingProductiveAdverbs_2() throws Exception {
        assertNoErrors("nefastamente", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingProductiveAdverbs_3() throws Exception {
        assertNoErrors("funereamente", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingSpellingTXT_1() throws Exception {
        assertNoErrors("physalis", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellingSpellingTXT_2() throws Exception {
        assertNoErrors("jackpot", ltPT, rulePT);
    }

    @Test
    public void testPortugueseSpellingDoesNotSuggestOffensiveWords_1() throws Exception {
        assertSingleErrorWithNegativeSuggestion("pwta", ltBR, ruleBR, "puta");
    }

    @Test
    public void testPortugueseSpellingDoesNotSuggestOffensiveWords_2() throws Exception {
        assertSingleErrorWithNegativeSuggestion("bâbaca", ltBR, ruleBR, "babaca");
    }

    @Test
    public void testPortugueseSpellingDoesNotSuggestOffensiveWords_3() throws Exception {
        assertSingleErrorWithNegativeSuggestion("rexardado", ltBR, ruleBR, "retardado");
    }

    @Test
    public void testPortugueseSpellingDoesNotSuggestOffensiveWords_4() throws Exception {
        assertSingleErrorWithNegativeSuggestion("cagguei", ltBR, ruleBR, "caguei");
    }

    @Test
    public void testPortugueseSpellingDoesNotSuggestOffensiveWords_5() throws Exception {
        assertSingleErrorWithNegativeSuggestion("bucetas", ltBR, ruleBR, "bocetas");
    }

    @Test
    public void testPortugueseSpellingDoesNotSuggestOffensiveWords_6() throws Exception {
        assertSingleErrorWithNegativeSuggestion("mongolóide", ltBR, ruleBR, "mongoloide");
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckCurrencyValues_1() throws Exception {
        assertNoErrors("R$45,00", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckCurrencyValues_2() throws Exception {
        assertNoErrors("R$ 3", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckCurrencyValues_3() throws Exception {
        assertNoErrors("US$1.000,00", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckCurrencyValues_4() throws Exception {
        assertNoErrors("€99,99", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckCurrencyValues_5() throws Exception {
        assertNoErrors("6£", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckCurrencyValues_6() throws Exception {
        assertNoErrors("30 R$", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckCurrencyValues_7() throws Exception {
        assertNoErrors("US$", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckCurrencyValues_8() throws Exception {
        assertNoErrors("US$ 58,0 bilhões", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckNumberAbbreviations_1() throws Exception {
        assertNoErrors("Nº666", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckNumberAbbreviations_2() throws Exception {
        assertNoErrors("N°42189", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckNumberAbbreviations_3() throws Exception {
        assertNoErrors("Nº 420", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckNumberAbbreviations_4() throws Exception {
        assertNoErrors("N.º69", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckNumberAbbreviations_5() throws Exception {
        assertNoErrors("N.º 80085", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectOrdinalSuperscripts_1() throws Exception {
        assertNoErrors("6º", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectOrdinalSuperscripts_2() throws Exception {
        assertNoErrors("100°", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectOrdinalSuperscripts_3() throws Exception {
        assertNoErrors("21ª", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectDegreeExpressions_1() throws Exception {
        assertNoErrors("1,0°", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectDegreeExpressions_2() throws Exception {
        assertNoErrors("2°c", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectDegreeExpressions_3() throws Exception {
        assertNoErrors("3°C", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectDegreeExpressions_4() throws Exception {
        assertNoErrors("4,0ºc", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectDegreeExpressions_5() throws Exception {
        assertNoErrors("5.0ºc", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectDegreeExpressions_6() throws Exception {
        assertNoErrors("6,0ºRø", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectDegreeExpressions_7() throws Exception {
        assertNoErrors("7,5ºN", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerDoesNotCorrectDegreeExpressions_8() throws Exception {
        assertNoErrors("−8,0°", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckXForVezes_1() throws Exception {
        assertNoErrors("10X", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingDoesNotCheckXForVezes_2() throws Exception {
        assertNoErrors("5x", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingWorksWithRarePunctuation_1() throws Exception {
        assertNoErrors("⌈Herói⌋", ltBR, ruleBR);
    }

    @Test
    public void testBrazilPortugueseSpellingWorksWithRarePunctuation_2() throws Exception {
        assertNoErrors("″Santo Antônio do Manga″", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresUppercaseAndDigitString_1() throws Exception {
        assertNoErrors("ABC2000", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresUppercaseAndDigitString_2() throws Exception {
        assertNoErrors("AI5", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresUppercaseAndDigitString_3() throws Exception {
        assertNoErrors("IP65", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresUppercaseAndDigitString_4() throws Exception {
        assertNoErrors("HR2048", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresAmpersandBetweenTwoCapitals_1() throws Exception {
        assertNoErrors("J&F", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresAmpersandBetweenTwoCapitals_2() throws Exception {
        assertNoErrors("A&E", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresParentheticalInflection_1() throws Exception {
        assertNoErrors("professor(es)", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresParentheticalInflection_2() throws Exception {
        assertNoErrors("profissional(is)", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresProbableUnitsOfMeasurement_1() throws Exception {
        assertNoErrors("180g", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresProbableUnitsOfMeasurement_2() throws Exception {
        assertNoErrors("16.2kW", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresProbableUnitsOfMeasurement_3() throws Exception {
        assertNoErrors("6x6", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresProbableUnitsOfMeasurement_4() throws Exception {
        assertNoErrors("100x100mm", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresProbableUnitsOfMeasurement_5() throws Exception {
        assertNoErrors("5,5x6.7km", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresProbableUnitsOfMeasurement_6() throws Exception {
        assertNoErrors("5×10×50cm", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresDiceRollNotation_1() throws Exception {
        assertNoErrors("1d20", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresDiceRollNotation_2() throws Exception {
        assertNoErrors("3d6", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresDiceRollNotation_3() throws Exception {
        assertNoErrors("20d10", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresNonstandardTimeFormat_1() throws Exception {
        assertNoErrors("31h40min", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresNonstandardTimeFormat_2() throws Exception {
        assertNoErrors("1h20min3s", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresNonstandardTimeFormat_3() throws Exception {
        assertNoErrors("13:30h", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresLaughterOnomatopoeia_1() throws Exception {
        assertNoErrors("hahahahaha", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresLaughterOnomatopoeia_2() throws Exception {
        assertNoErrors("heheh", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresLaughterOnomatopoeia_3() throws Exception {
        assertNoErrors("huehuehuehue", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresLaughterOnomatopoeia_4() throws Exception {
        assertNoErrors("Kkkkkkk", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerRecognisesRomanNumerals_1() throws Exception {
        assertNoErrors("XVIII", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerRecognisesRomanNumerals_2() throws Exception {
        assertNoErrors("xviii", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresIsolatedGreekLetters_1() throws Exception {
        assertNoErrors("ξ", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresIsolatedGreekLetters_2() throws Exception {
        assertNoErrors("Ω", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresWordsFromIgnoreTXT_1() throws Exception {
        assertNoErrors("ignorewordoogaboogatest", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerIgnoresWordsFromIgnoreTXT_2() throws Exception {
        assertSingleErrorWithNegativeSuggestion("ignorewordoogaboogatext", ltBR, ruleBR, "ignorewordoogaboogatest");
    }

    @Test
    public void testPortugueseSpellerAcceptsIllegalPrefixation_1() throws Exception {
        assertNoErrors("semi-consciente", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsIllegalPrefixation_2() throws Exception {
        assertNoErrors("semi-acústicas", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsIllegalPrefixation_3() throws Exception {
        assertNoErrors("semi-frio", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsIllegalPrefixation_4() throws Exception {
        assertNoErrors("sub-taça", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsIllegalPrefixation_5() throws Exception {
        assertNoErrors("sub-pratos", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsNationalPrefixes_1() throws Exception {
        assertNoErrors("ítalo-congolês", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsNationalPrefixes_2() throws Exception {
        assertNoErrors("Belgo-Luxemburguesa", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsNationalPrefixes_3() throws Exception {
        assertNoErrors("franco-prussiana", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsNationalPrefixes_4() throws Exception {
        assertNoErrors("Franco-prussiana", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsNationalPrefixes_5() throws Exception {
        assertNoErrors("Franco-Prussiana", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsNationalPrefixes_6() throws Exception {
        assertNoErrors("húngaro-romeno", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsParagraphAndOrdinal_1() throws Exception {
        assertNoErrors("§1º", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsParagraphAndOrdinal_2() throws Exception {
        assertNoErrors("§ 1º", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsParagraphAndOrdinal_3() throws Exception {
        assertNoErrors("§1º-A", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerAcceptsParagraphAndOrdinal_4() throws Exception {
        assertNoErrors("§ 1º-A", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerReplacesOldGrammarRules_1() throws Exception {
        assertSingleError("Amamo-te", ltBR, ruleBR, "Amamos");
    }

    @Test
    public void testPortugueseSpellerReplacesOldGrammarRules_2() throws Exception {
        assertSingleError("Amá-te", ltBR, ruleBR, "Amar");
    }

    @Test
    public void testPortugueseSpellerReplacesOldGrammarRules_3() throws Exception {
        assertSingleError("Fazê o quê?", ltBR, ruleBR, "Fazer");
    }

    @Test
    public void testPortugueseSpellerReplacesOldGrammarRules_4() throws Exception {
        assertSingleError("Vamo embora", ltBR, ruleBR, "Vamos");
    }

    @Test
    public void testPortugueseSpellerHasNewWords_1() throws Exception {
        assertNoErrors("diferençazinha", ltPT, rulePT);
    }

    @Test
    public void testPortugueseSpellerHasNewWords_2() throws Exception {
        assertNoErrors("Mewtwo, Pikachu, Rapidash e dois Growlithes", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerHasNewWords_3() throws Exception {
        assertNoErrors("bebezice", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerHasNewWords_4() throws Exception {
        assertNoErrors("Solucz", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerHasNewWords_5() throws Exception {
        assertNoErrors("microagulhamento", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerHasNewWords_6() throws Exception {
        assertNoErrors("curricularização", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerHasNewWords_7() throws Exception {
        assertNoErrors("email", ltBR, ruleBR);
    }

    @Test
    public void testPortugueseSpellerHasNewWords_8() throws Exception {
        assertNoErrors("pô", ltBR, ruleBR);
    }
}
