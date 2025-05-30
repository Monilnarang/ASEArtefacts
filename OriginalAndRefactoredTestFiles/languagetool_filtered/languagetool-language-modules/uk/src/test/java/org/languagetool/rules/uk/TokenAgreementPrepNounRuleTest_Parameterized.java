package org.languagetool.rules.uk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TokenAgreementPrepNounRuleTest_Parameterized extends AbstractRuleTest {

    @Before
    public void setUp() throws IOException {
        rule = new TokenAgreementPrepNounRule(TestTools.getMessages("uk"), lt.getLanguage());
    }

    @Test
    public void testZandZnaAsRare_15() throws IOException {
        assertHasError("від співпраці з компанію", "компанією", "компанії");
    }

    @Test
    public void testSpecialChars_2_testMerged_2() throws IOException {
        RuleMatch[] matches = ruleMatch("о справедли\u00ADвости.");
        assertEquals(1, matches.length);
        matches = ruleMatch("по не́рвам, по мо\u00ADстам, по воротам");
        assertEquals(3, matches.length);
        assertEquals(3, matches[0].getFromPos());
        assertEquals(10, matches[0].getToPos());
        assertEquals(Arrays.asList("нервах", "нерви"), matches[0].getSuggestedReplacements());
        assertEquals(15, matches[1].getFromPos());
        assertEquals(Arrays.asList("мостах", "мости"), matches[1].getSuggestedReplacements());
        assertEquals(27, matches[2].getFromPos());
        assertEquals(Arrays.asList("воротах", "воротях", "ворота"), matches[2].getSuggestedReplacements());
    }

    @ParameterizedTest
    @MethodSource("Provider_testZandZnaAsRare_1_1to3_3to4_4to5_5to6_6to8_8to10_10to11_11to14_16to17_17to18_20to24_27to29")
    public void testZandZnaAsRare_1_1to3_3to4_4to5_5to6_6to8_8to10_10to11_11to14_16to17_17to18_20to24_27to29(String param1) throws IOException {
        assertEmptyMatch(param1);
    }

    static public Stream<Arguments> Provider_testZandZnaAsRare_1_1to3_3to4_4to5_5to6_6to8_8to10_10to11_11to14_16to17_17to18_20to24_27to29() {
        return Stream.of(arguments("кілометрів зо три"), arguments("години з 30"), arguments("людей з десяток"), arguments("насіння зі жменьку"), arguments("розміром з долоню"), arguments("шматок з кулак завбільшки"), arguments("винайняло з тисячу модераторів"), arguments("причеплено ще з сотню дерев'яних крамниць"), arguments("з 9-12-поверховий будинок"), arguments("виниклу з нізвідки тему"), arguments("з Ван Гогом"), arguments("заввишки з її невеликий зріст"), arguments("із добру годину"), arguments("за цілком собі реалістичною соціальною"), arguments("спиралося на місячної давнини рішення"), arguments("На середньої довжини шубу"), arguments("При різного роду процесах"), arguments("по лише їм цікавих місцях"), arguments("від дуже близьких людей"), arguments("завдяки саме цим сімом голосам"), arguments("на мохом стеленому дні"), arguments("який до речі вони присягалися"), arguments("на нічого не вартий папірець"), arguments("для якої з мов воно первинне"), arguments("об'єктивності заради слід зазначити"), arguments("чи не проти я тієї церковної стройки"), arguments("З точністю до навпаки ви все це побачите"), arguments("Усупереч не те що лихим"), arguments("весь світ замість спершу самому засвоїти"), arguments("Йдеться про вже всім добре відому"), arguments("до дев’яносто дев’ятого"), arguments("стосовно одне одного"), arguments("ніч із тридцять першого серпня"), arguments("до їм поді\u00ADбних"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testZandZnaAsRare_1to2_7_9_12to16_19_25to26")
    public void testZandZnaAsRare_1to2_7_9_12to16_19_25to26(String param1) throws IOException {
        assertHasError(param1);
    }

    static public Stream<Arguments> Provider_testZandZnaAsRare_1to2_7_9_12to16_19_25to26() {
        return Stream.of(arguments("зі землю"), arguments("туди ж з своє шкапою"), arguments("вискочив із барлоги"), arguments("по бодай маленьким справам"), arguments("по смішно маленьким справам"), arguments("завдяки його прийомі"), arguments("по лише їм цікавим місцям"), arguments("призвів до значною мірою демократичному середньому класу"), arguments("це нас для дуже велика сума"), arguments("у дуже обмеженим рамках"), arguments("кинулися до мені перші з них"), arguments("Замість лимону можна брати"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsCapitalized_1_4to5_7_10")
    public void testIsCapitalized_1_4to5_7_10(String param1) {
        assertFalse(LemmaHelper.isCapitalized(param1));
    }

    static public Stream<Arguments> Provider_testIsCapitalized_1_4to5_7_10() {
        return Stream.of(arguments("боснія"), arguments("По-перше"), arguments("ПаП"), arguments("П'ЯТНИЦЯ"), arguments("ДБЗПТЛ"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsCapitalized_2to3_6_8to9")
    public void testIsCapitalized_2to3_6_8to9(String param1) {
        assertTrue(LemmaHelper.isCapitalized(param1));
    }

    static public Stream<Arguments> Provider_testIsCapitalized_2to3_6_8to9() {
        return Stream.of(arguments("Боснія"), arguments("Боснія-Герцеговина"), arguments("П'ятниця"), arguments("EuroGas"), arguments("Рясна-2"));
    }
}
