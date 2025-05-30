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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MorfologikPortugueseSpellerRuleTest_Parameterized {

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
    public void testPortugueseAsymmetricalDialectDifferences_1() throws Exception {
        assertSingleExactError("facto", ltBR, ruleBR, "fato", "Possível erro de ortografia: esta é a grafia utilizada no português europeu.", "MORFOLOGIK_RULE_PT_BR_DIALECT");
    }

    @ParameterizedTest
    @MethodSource("Provider_testPortugueseSpellerSanity_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3_3_3_3_3_3_3_3to4_4_4_4_4_4_4_4_4_4_4to5_5_5_5_5_5_5_5to6_6_6_6_6_6to7_7_7_7to8_8_8_8")
    public void testPortugueseSpellerSanity_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3_3_3_3_3_3_3_3to4_4_4_4_4_4_4_4_4_4_4to5_5_5_5_5_5_5_5to6_6_6_6_6_6to7_7_7_7to8_8_8_8(String param1) throws Exception {
        assertNoErrors(param1, ltBR, ruleBR);
    }

    static public Stream<Arguments> Provider_testPortugueseSpellerSanity_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3_3_3_3_3_3_3_3to4_4_4_4_4_4_4_4_4_4_4to5_5_5_5_5_5_5_5to6_6_6_6_6_6to7_7_7_7to8_8_8_8() {
        return Stream.of(arguments("oogaboogatestword"), arguments("oogaboogatestwordBR"), arguments("soto-pôr"), arguments("soto-trepar"), arguments("franguito"), arguments("irmãozinho"), arguments("retratozinho"), arguments("notebookzinho"), arguments("finaizitos"), arguments("cafezito"), arguments("chorõezitos"), arguments("assadito"), arguments("enciclopedicamente"), arguments("nefastamente"), arguments("funereamente"), arguments("physalis"), arguments("R$45,00"), arguments("R$ 3"), arguments("US$1.000,00"), arguments("€99,99"), arguments("6£"), arguments("30 R$"), arguments("US$"), arguments("US$ 58,0 bilhões"), arguments("Nº666"), arguments("N°42189"), arguments("Nº 420"), arguments("N.º69"), arguments("N.º 80085"), arguments("6º"), arguments("100°"), arguments("21ª"), arguments("1,0°"), arguments("2°c"), arguments("3°C"), arguments("4,0ºc"), arguments("5.0ºc"), arguments("6,0ºRø"), arguments("7,5ºN"), arguments("−8,0°"), arguments("10X"), arguments("5x"), arguments("⌈Herói⌋"), arguments("″Santo Antônio do Manga″"), arguments("ABC2000"), arguments("AI5"), arguments("IP65"), arguments("HR2048"), arguments("J&F"), arguments("A&E"), arguments("professor(es)"), arguments("profissional(is)"), arguments("180g"), arguments("16.2kW"), arguments("6x6"), arguments("100x100mm"), arguments("5,5x6.7km"), arguments("5×10×50cm"), arguments("1d20"), arguments("3d6"), arguments("20d10"), arguments("31h40min"), arguments("1h20min3s"), arguments("13:30h"), arguments("hahahahaha"), arguments("heheh"), arguments("huehuehuehue"), arguments("Kkkkkkk"), arguments("XVIII"), arguments("xviii"), arguments("ξ"), arguments("Ω"), arguments("ignorewordoogaboogatest"), arguments("semi-consciente"), arguments("semi-acústicas"), arguments("semi-frio"), arguments("sub-taça"), arguments("sub-pratos"), arguments("ítalo-congolês"), arguments("Belgo-Luxemburguesa"), arguments("franco-prussiana"), arguments("Franco-prussiana"), arguments("Franco-Prussiana"), arguments("húngaro-romeno"), arguments("§1º"), arguments("§ 1º"), arguments("§1º-A"), arguments("§ 1º-A"), arguments("Mewtwo, Pikachu, Rapidash e dois Growlithes"), arguments("bebezice"), arguments("Solucz"), arguments("microagulhamento"), arguments("curricularização"), arguments("email"), arguments("pô"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPortugueseSpellerSanity_1to2_2to5")
    public void testPortugueseSpellerSanity_1to2_2to5(String param1) throws Exception {
        assertNoErrors(param1, ltPT, rulePT);
    }

    static public Stream<Arguments> Provider_testPortugueseSpellerSanity_1to2_2to5() {
        return Stream.of(arguments("oogaboogatestword"), arguments("oogaboogatestwordPT"), arguments("oogaboogatestwordPT90"), arguments("fato"), arguments("jackpot"), arguments("diferençazinha"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPortugueseSpellerAcceptsVerbsWithProductivePrefixes_1to3_3to4")
    public void testPortugueseSpellerAcceptsVerbsWithProductivePrefixes_1to3_3to4(String param1, String param2) throws Exception {
        assertSingleError(param1, ltBR, ruleBR, param2);
    }

    static public Stream<Arguments> Provider_testPortugueseSpellerAcceptsVerbsWithProductivePrefixes_1to3_3to4() {
        return Stream.of(arguments("reune", "reúne"), arguments("Amamo-te", "Amamos"), arguments("Amá-te", "Amar"), arguments("Fazê o quê?", "Fazer"), arguments("Vamo embora", "Vamos"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPortugueseSymmetricalDialectDifferences_1to13")
    public void testPortugueseSymmetricalDialectDifferences_1to13(String param1, String param2) throws Exception {
        assertTwoWayDialectError(param1, param2);
    }

    static public Stream<Arguments> Provider_testPortugueseSymmetricalDialectDifferences_1to13() {
        return Stream.of(arguments("anônimo", "anónimo"), arguments("caratês", "caratés"), arguments("detectariam", "detetariam"), arguments("tênis", "ténis"), arguments("ônus", "ónus"), arguments("pênis", "pénis"), arguments("detecção", "deteção"), arguments("dezesseis", "dezasseis"), arguments("bidê", "bidé"), arguments("detectar", "detetar"), arguments("napoleônia", "napoleónia"), arguments("hiperêmese", "hiperémese"), arguments("bebê", "bebé"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPortugueseSpellingAgreementVariation_1to5")
    public void testPortugueseSpellingAgreementVariation_1to5(String param1, String param2) throws Exception {
        assertTwoWayOrthographicAgreementError(param1, param2);
    }

    static public Stream<Arguments> Provider_testPortugueseSpellingAgreementVariation_1to5() {
        return Stream.of(arguments("detetar", "detectar"), arguments("abjeção", "abjecção"), arguments("direção", "direcção"), arguments("diretamente", "directamente"), arguments("afetada", "afectada"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPortugueseSpellingDoesNotSuggestOffensiveWords_1to2_2to6")
    public void testPortugueseSpellingDoesNotSuggestOffensiveWords_1to2_2to6(String param1, String param2) throws Exception {
        assertSingleErrorWithNegativeSuggestion(param1, ltBR, ruleBR, param2);
    }

    static public Stream<Arguments> Provider_testPortugueseSpellingDoesNotSuggestOffensiveWords_1to2_2to6() {
        return Stream.of(arguments("pwta", "puta"), arguments("bâbaca", "babaca"), arguments("rexardado", "retardado"), arguments("cagguei", "caguei"), arguments("bucetas", "bocetas"), arguments("mongolóide", "mongoloide"), arguments("ignorewordoogaboogatext", "ignorewordoogaboogatest"));
    }
}
