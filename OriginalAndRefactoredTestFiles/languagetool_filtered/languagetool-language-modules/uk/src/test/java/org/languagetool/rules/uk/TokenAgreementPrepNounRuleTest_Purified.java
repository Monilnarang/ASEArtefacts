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

public class TokenAgreementPrepNounRuleTest_Purified extends AbstractRuleTest {

    @Before
    public void setUp() throws IOException {
        rule = new TokenAgreementPrepNounRule(TestTools.getMessages("uk"), lt.getLanguage());
    }

    @Test
    public void testZandZnaAsRare_1() throws IOException {
        assertEmptyMatch("кілометрів зо три");
    }

    @Test
    public void testZandZnaAsRare_2() throws IOException {
        assertEmptyMatch("години з 30");
    }

    @Test
    public void testZandZnaAsRare_3() throws IOException {
        assertEmptyMatch("людей з десяток");
    }

    @Test
    public void testZandZnaAsRare_4() throws IOException {
        assertEmptyMatch("насіння зі жменьку");
    }

    @Test
    public void testZandZnaAsRare_5() throws IOException {
        assertEmptyMatch("розміром з долоню");
    }

    @Test
    public void testZandZnaAsRare_6() throws IOException {
        assertEmptyMatch("шматок з кулак завбільшки");
    }

    @Test
    public void testZandZnaAsRare_7() throws IOException {
        assertEmptyMatch("винайняло з тисячу модераторів");
    }

    @Test
    public void testZandZnaAsRare_8() throws IOException {
        assertEmptyMatch("причеплено ще з сотню дерев'яних крамниць");
    }

    @Test
    public void testZandZnaAsRare_9() throws IOException {
        assertEmptyMatch("з 9-12-поверховий будинок");
    }

    @Test
    public void testZandZnaAsRare_10() throws IOException {
        assertEmptyMatch("виниклу з нізвідки тему");
    }

    @Test
    public void testZandZnaAsRare_11() throws IOException {
        assertEmptyMatch("з Ван Гогом");
    }

    @Test
    public void testZandZnaAsRare_12() throws IOException {
        assertHasError("зі землю");
    }

    @Test
    public void testZandZnaAsRare_13() throws IOException {
        assertHasError("туди ж з своє шкапою");
    }

    @Test
    public void testZandZnaAsRare_14() throws IOException {
        assertHasError("вискочив із барлоги");
    }

    @Test
    public void testZandZnaAsRare_15() throws IOException {
        assertHasError("від співпраці з компанію", "компанією", "компанії");
    }

    @Test
    public void testZandZnaAsRare_16() throws IOException {
        assertEmptyMatch("заввишки з її невеликий зріст");
    }

    @Test
    public void testZandZnaAsRare_17() throws IOException {
        assertEmptyMatch("із добру годину");
    }

    @Test
    public void testRuleFlexibleOrder_1() throws IOException {
        assertHasError("по бодай маленьким справам");
    }

    @Test
    public void testRuleFlexibleOrder_2() throws IOException {
        assertHasError("по смішно маленьким справам");
    }

    @Test
    public void testRuleFlexibleOrder_3() throws IOException {
        assertEmptyMatch("за цілком собі реалістичною соціальною");
    }

    @Test
    public void testRuleFlexibleOrder_4() throws IOException {
        assertEmptyMatch("спиралося на місячної давнини рішення");
    }

    @Test
    public void testRuleFlexibleOrder_5() throws IOException {
        assertEmptyMatch("На середньої довжини шубу");
    }

    @Test
    public void testRuleFlexibleOrder_6() throws IOException {
        assertEmptyMatch("При різного роду процесах");
    }

    @Test
    public void testRuleFlexibleOrder_7() throws IOException {
        assertHasError("завдяки його прийомі");
    }

    @Test
    public void testRuleFlexibleOrder_8() throws IOException {
        assertEmptyMatch("по лише їм цікавих місцях");
    }

    @Test
    public void testRuleFlexibleOrder_9() throws IOException {
        assertHasError("по лише їм цікавим місцям");
    }

    @Test
    public void testRuleFlexibleOrder_10() throws IOException {
        assertEmptyMatch("від дуже близьких людей");
    }

    @Test
    public void testRuleFlexibleOrder_11() throws IOException {
        assertEmptyMatch("завдяки саме цим сімом голосам");
    }

    @Test
    public void testRuleFlexibleOrder_12() throws IOException {
        assertEmptyMatch("на мохом стеленому дні");
    }

    @Test
    public void testRuleFlexibleOrder_13() throws IOException {
        assertEmptyMatch("який до речі вони присягалися");
    }

    @Test
    public void testRuleFlexibleOrder_14() throws IOException {
        assertEmptyMatch("на нічого не вартий папірець");
    }

    @Test
    public void testRuleFlexibleOrder_15() throws IOException {
        assertHasError("призвів до значною мірою демократичному середньому класу");
    }

    @Test
    public void testRuleFlexibleOrder_16() throws IOException {
        assertHasError("це нас для дуже велика сума");
    }

    @Test
    public void testRuleFlexibleOrder_17() throws IOException {
        assertEmptyMatch("для якої з мов воно первинне");
    }

    @Test
    public void testRuleFlexibleOrder_18() throws IOException {
        assertEmptyMatch("об'єктивності заради слід зазначити");
    }

    @Test
    public void testRuleFlexibleOrder_19() throws IOException {
        assertHasError("у дуже обмеженим рамках");
    }

    @Test
    public void testRuleFlexibleOrder_20() throws IOException {
        assertEmptyMatch("чи не проти я тієї церковної стройки");
    }

    @Test
    public void testRuleFlexibleOrder_21() throws IOException {
        assertEmptyMatch("З точністю до навпаки ви все це побачите");
    }

    @Test
    public void testRuleFlexibleOrder_22() throws IOException {
        assertEmptyMatch("Усупереч не те що лихим");
    }

    @Test
    public void testRuleFlexibleOrder_23() throws IOException {
        assertEmptyMatch("весь світ замість спершу самому засвоїти");
    }

    @Test
    public void testRuleFlexibleOrder_24() throws IOException {
        assertEmptyMatch("Йдеться про вже всім добре відому");
    }

    @Test
    public void testRuleFlexibleOrder_25() throws IOException {
        assertHasError("кинулися до мені перші з них");
    }

    @Test
    public void testRuleFlexibleOrder_26() throws IOException {
        assertHasError("Замість лимону можна брати");
    }

    @Test
    public void testRuleFlexibleOrder_27() throws IOException {
        assertEmptyMatch("до дев’яносто дев’ятого");
    }

    @Test
    public void testRuleFlexibleOrder_28() throws IOException {
        assertEmptyMatch("стосовно одне одного");
    }

    @Test
    public void testRuleFlexibleOrder_29() throws IOException {
        assertEmptyMatch("ніч із тридцять першого серпня");
    }

    @Test
    public void testSpecialChars_1() throws IOException {
        assertEmptyMatch("до їм поді\u00ADбних");
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

    @Test
    public void testIsCapitalized_1() {
        assertFalse(LemmaHelper.isCapitalized("боснія"));
    }

    @Test
    public void testIsCapitalized_2() {
        assertTrue(LemmaHelper.isCapitalized("Боснія"));
    }

    @Test
    public void testIsCapitalized_3() {
        assertTrue(LemmaHelper.isCapitalized("Боснія-Герцеговина"));
    }

    @Test
    public void testIsCapitalized_4() {
        assertFalse(LemmaHelper.isCapitalized("По-перше"));
    }

    @Test
    public void testIsCapitalized_5() {
        assertFalse(LemmaHelper.isCapitalized("ПаП"));
    }

    @Test
    public void testIsCapitalized_6() {
        assertTrue(LemmaHelper.isCapitalized("П'ятниця"));
    }

    @Test
    public void testIsCapitalized_7() {
        assertFalse(LemmaHelper.isCapitalized("П'ЯТНИЦЯ"));
    }

    @Test
    public void testIsCapitalized_8() {
        assertTrue(LemmaHelper.isCapitalized("EuroGas"));
    }

    @Test
    public void testIsCapitalized_9() {
        assertTrue(LemmaHelper.isCapitalized("Рясна-2"));
    }

    @Test
    public void testIsCapitalized_10() {
        assertFalse(LemmaHelper.isCapitalized("ДБЗПТЛ"));
    }
}
