package org.languagetool.rules.uk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;

public class TokenAgreementNumrNounRuleTest_Purified extends AbstractRuleTest {

    @Before
    public void setUp() throws IOException {
        rule = new TokenAgreementNumrNounRule(TestTools.getMessages("uk"), lt.getLanguage());
    }

    @Test
    public void testRuleTN_1() throws IOException {
        assertEmptyMatch("два пацани");
    }

    @Test
    public void testRuleTN_2() throws IOException {
        assertEmptyMatch("два паркани");
    }

    @Test
    public void testRuleTN_3() throws IOException {
        assertEmptyMatch("двох пацанів");
    }

    @Test
    public void testRuleTN_4() throws IOException {
        assertEmptyMatch("двох парканів");
    }

    @Test
    public void testRuleTN_5() throws IOException {
        assertEmptyMatch("трьома пацанами");
    }

    @Test
    public void testRuleTN_6() throws IOException {
        assertEmptyMatch("трьома парканами");
    }

    @Test
    public void testRuleTN_7() throws IOException {
        assertEmptyMatch("шість пацанів");
    }

    @Test
    public void testRuleTN_8() throws IOException {
        assertEmptyMatch("шість парканів");
    }

    @Test
    public void testRuleTN_9() throws IOException {
        assertEmptyMatch("двадцять пацанів");
    }

    @Test
    public void testRuleTN_10() throws IOException {
        assertEmptyMatch("восьми пацанів");
    }

    @Test
    public void testRuleTN_11() throws IOException {
        assertEmptyMatch("восьми парканів");
    }

    @Test
    public void testRuleTN_12() throws IOException {
        assertEmptyMatch("вісьма пацанами");
    }

    @Test
    public void testRuleTN_13() throws IOException {
        assertEmptyMatch("вісьма парканами");
    }

    @Test
    public void testRuleTN_14() throws IOException {
        assertEmptyMatch("декілька пацанів");
    }

    @Test
    public void testRuleTN_15() throws IOException {
        assertEmptyMatch("декілька парканів");
    }

    @Test
    public void testRuleTN_16() throws IOException {
        assertEmptyMatch("кілька ложок цукру");
    }

    @Test
    public void testRuleTN_17() throws IOException {
        assertEmptyMatch("декільком пацанам");
    }

    @Test
    public void testRuleTN_18() throws IOException {
        assertEmptyMatch("декільком парканам");
    }

    @Test
    public void testRuleTN_19() throws IOException {
        assertEmptyMatch("вісім-дев'ять місяців");
    }

    @Test
    public void testRuleTN_20() throws IOException {
        assertEmptyMatch("протягом шістьох місяців");
    }

    @Test
    public void testRuleTN_21() throws IOException {
        assertEmptyMatch("за чотирма категоріями");
    }

    @Test
    public void testRuleTN_22() throws IOException {
        assertEmptyMatch("за кількома торгівельними центрами");
    }

    @Test
    public void testRuleTN_23() throws IOException {
        assertEmptyMatch("двоє дверей");
    }

    @Test
    public void testRuleTN_24() throws IOException {
        assertEmptyMatch("трьох людей");
    }

    @Test
    public void testRuleTN_25() throws IOException {
        assertEmptyMatch("22 червня");
    }

    @Test
    public void testRuleTN_26() throws IOException {
        assertEmptyMatch("2 лютого їжака забрали");
    }

    @Test
    public void testRuleTN_27() throws IOException {
        assertEmptyMatch("Ту-154 президента");
    }

    @Test
    public void testRuleTN_28() throws IOException {
        assertEmptyMatch("до 4 секунд");
    }

    @Test
    public void testRuleTN_29() throws IOException {
        assertEmptyMatch("2 сонця");
    }

    @Test
    public void testRuleTN_30() throws IOException {
        assertEmptyMatch("було дев’ять років");
    }

    @Test
    public void testRuleTN_31() throws IOException {
        assertEmptyMatch("багато часу");
    }

    @Test
    public void testRuleTN_32() throws IOException {
        assertEmptyMatch("Минуло багато-багато часу");
    }

    @Test
    public void testRuleTN_33() throws IOException {
        assertEmptyMatch("багато заліза");
    }

    @Test
    public void testRuleTN_34() throws IOException {
        assertHasError("як багато білку", "багато білка", "багато білки", "багато білок", "багато білків");
    }

    @Test
    public void testRuleTN_35() throws IOException {
        assertEmptyMatch("пів ковбаси");
    }

    @Test
    public void testRuleTN_36() throws IOException {
        assertEmptyMatch("Вісімдесят Геннадію Терентійовичу");
    }

    @Test
    public void testRuleTN_37() throws IOException {
        assertEmptyMatch("3 подолянина");
    }

    @Test
    public void testRuleTN_38() throws IOException {
        assertEmptyMatch("два подолянина");
    }

    @Test
    public void testRuleTN_39() throws IOException {
        assertEmptyMatch("три рабини");
    }

    @Test
    public void testRuleTN_40() throws IOException {
        assertEmptyMatch("Обидва волиняни");
    }

    @Test
    public void testRuleTN_41() throws IOException {
        assertEmptyMatch("Обидва волинянина");
    }

    @Test
    public void testRuleTN_42() throws IOException {
        assertEmptyMatch("дві Франції");
    }

    @Test
    public void testRuleTN_43() throws IOException {
        assertEmptyMatch("дві СБУ");
    }

    @Test
    public void testRuleTN_44() throws IOException {
        assertEmptyMatch("усі три Петра");
    }

    @Test
    public void testRuleTN_45() throws IOException {
        assertEmptyMatch("у три погибелі");
    }

    @Test
    public void testRuleTN_46() throws IOException {
        assertEmptyMatch("багато хто");
    }

    @Test
    public void testRuleTN_47() throws IOException {
        assertEmptyMatch("багато які");
    }

    @Test
    public void testRuleTN_48() throws IOException {
        assertEmptyMatch("мало наслідком");
    }

    @Test
    public void testRuleTN_49() throws IOException {
        assertEmptyMatch("все одно йому");
    }

    @Test
    public void testRuleTN_50() throws IOException {
        assertEmptyMatch("один одному руки");
    }

    @Test
    public void testRuleTN_51() throws IOException {
        assertEmptyMatch("за день чи два мене");
    }

    @Test
    public void testRuleTN_52() throws IOException {
        assertEmptyMatch("сьома ранку");
    }

    @Test
    public void testRuleTN_53() throws IOException {
        assertEmptyMatch("години півтори");
    }

    @Test
    public void testRuleTN_54() throws IOException {
        assertEmptyMatch("хвилини за півтори поїзд приїхав");
    }

    @Test
    public void testRuleTN_55() throws IOException {
        assertEmptyMatch("1972 року");
    }

    @Test
    public void testRuleTN_56() throws IOException {
        assertEmptyMatch("1970-1971 рр.");
    }

    @Test
    public void testRuleTN_57() throws IOException {
        assertEmptyMatch("ст. 2 пункта 1");
    }

    @Test
    public void testRuleTN_58() throws IOException {
        assertEmptyMatch("3 / 5 вугілля світу");
    }

    @Test
    public void testRuleTN_59() throws IOException {
        assertEmptyMatch("3 / 4 понеділка");
    }

    @Test
    public void testRuleTN_60() throws IOException {
        assertEmptyMatch("1992 рік");
    }

    @Test
    public void testRuleTN_61() throws IOException {
        assertEmptyMatch("обоє винні");
    }

    @Test
    public void testRuleTN_62() throws IOException {
        assertEmptyMatch("скільки-небудь осяжний");
    }

    @Test
    public void testRuleTN_63() throws IOException {
        assertEmptyMatch("обоє режисери");
    }

    @Test
    public void testRuleTN_64() throws IOException {
        assertEmptyMatch("сотні дві персон");
    }

    @Test
    public void testRuleTN_65() throws IOException {
        assertEmptyMatch("метрів п’ять килиму");
    }

    @Test
    public void testRuleTN_66() throws IOException {
        assertEmptyMatch("років з п’ять в'язниці");
    }

    @Test
    public void testRuleTN_67() throws IOException {
        assertEmptyMatch("рази може у два більший");
    }

    @Test
    public void testRuleTN_68() throws IOException {
        assertEmptyMatch("десятка півтора списочників");
    }

    @Test
    public void testRuleTN_69() throws IOException {
        assertEmptyMatch("два провінційного вигляду персонажі");
    }

    @Test
    public void testRuleTN_70() throws IOException {
        assertEmptyMatch("У свої вісімдесят пан Василь");
    }

    @Test
    public void testRuleTN_71() throws IOException {
        assertEmptyMatch("№ 4 Тижня");
    }

    @Test
    public void testRuleTN_72() throws IOException {
        assertEmptyMatch("57-ма вулиця");
    }

    @Test
    public void testRuleTN_73() throws IOException {
        assertEmptyMatch("мати не стільки слух");
    }

    @Test
    public void testRuleTN_74() throws IOException {
        assertEmptyMatch("$300 тис. кредиту");
    }

    @Test
    public void testRuleTN_75() throws IOException {
        assertEmptyMatch("20 мм кредиту");
    }

    @Test
    public void testRuleTN_76() throws IOException {
        assertEmptyMatch("три нікому не відомих");
    }

    @Test
    public void testRuleTN_77() throws IOException {
        assertEmptyMatch("несподіваного для багатьох висновку");
    }

    @Test
    public void testRuleTN_78() throws IOException {
        assertEmptyMatch("чотири десятих відсотка");
    }

    @Test
    public void testRuleTN_79() throws IOException {
        assertEmptyMatch("останніх півроку");
    }

    @Test
    public void testRuleTN_80() throws IOException {
        assertEmptyMatch("дві англійською");
    }

    @Test
    public void testRuleTN_81() throws IOException {
        assertEmptyMatch("за два злотих (15 грн)");
    }

    @Test
    public void testRuleTN_82() throws IOException {
        assertEmptyMatch("Дев'яноста річниця");
    }

    @Test
    public void testRuleTN_83() throws IOException {
        assertEmptyMatch("сьома вода на киселі");
    }

    @Test
    public void testRuleTN_84() throws IOException {
        assertEmptyMatch("років п'ять люди");
    }

    @Test
    public void testRuleTN_85() throws IOException {
        assertEmptyMatch("років через десять Литва");
    }

    @Test
    public void testRuleTN_86() throws IOException {
        assertEmptyMatch("у 2020 мати надійний фундамент");
    }

    @Test
    public void testRuleTN_87() throws IOException {
        assertEmptyMatch("тижнів зо два мати горшком воду носила");
    }

    @Test
    public void testRuleForceNoun_1() throws IOException {
        assertHasError("фронтові сто грам", "сто грамів");
    }

    @Test
    public void testRuleForceNoun_2() throws IOException {
        assertHasError("10—12 мегават", "12 мегаватів");
    }

    @Test
    public void testRuleForceNoun_3() throws IOException {
        assertHasError("12 німецьких солдат");
    }

    @Test
    public void testRuleForceNoun_4() throws IOException {
        assertHasError("десятки тисяч своїх солдат");
    }

    @Test
    public void testRuleForceNoun_5() throws IOException {
        assertHasError("п'ять байт", "п'ять байтів");
    }

    @Test
    public void testRuleForceNoun_6() throws IOException {
        assertHasError("5 байт", "5 байтів");
    }

    @Test
    public void testRuleForceNoun_7() throws IOException {
        assertHasError("мільйон байт", "мільйон байтів");
    }

    @Test
    public void testRuleForceNoun_8() throws IOException {
        assertEmptyMatch("мільйон байтів");
    }

    @Test
    public void testRuleForceNoun_9() throws IOException {
        assertEmptyMatch("тримали п'ять бейсбольних біт");
    }

    @Test
    public void testRuleForceNoun_10() throws IOException {
        assertHasError("60 мікрорентген", "60 мікрорентгенів");
    }

    @Test
    public void testRuleForceNoun_11() throws IOException {
        assertHasError("10 чоловік і жінок", "10 чоловіків");
    }

    @Test
    public void testRuleForceNoun_12() throws IOException {
        assertEmptyMatch("10 чоловік.");
    }

    @Test
    public void testRuleTon_1() throws IOException {
        assertHasError("на 200 тон", "тонн");
    }

    @Test
    public void testRuleTon_2() throws IOException {
        assertHasError("2 млн тон", "тонн");
    }

    @Test
    public void testRuleTon_3() throws IOException {
        assertHasError("кілька тон", "тонн");
    }

    @Test
    public void testRuleTon_4() throws IOException {
        assertHasError("вісім тисяч тон", "тонн");
    }

    @Test
    public void testRuleTon_5() throws IOException {
        assertHasError("22 тон", "тонн");
    }

    @Test
    public void testRuleTon_6() throws IOException {
        assertHasError("8,5 тон", "тонн");
    }

    @Test
    public void testRuleFract_1() throws IOException {
        assertEmptyMatch("дві з половиною кулі");
    }

    @Test
    public void testRuleFract_2() throws IOException {
        assertHasError("два з половиною автобуса", "два з половиною автобуси");
    }

    @Test
    public void testRuleFractionals_1() {
        assertHasError("2,4 кілограми", "2,4 кілограма");
    }

    @Test
    public void testRuleFractionals_2() {
        assertHasError("2,4 мільйони", "2,4 мільйона");
    }

    @Test
    public void testRuleFractionals_3() {
        assertHasError("2,54 мільйони", "2,54 мільйона");
    }

    @Test
    public void testRuleFractionals_4() {
        assertEmptyMatch("33,77 тонни");
    }

    @Test
    public void testRuleFractionals_5() {
        assertEmptyMatch("2,19 бала");
    }

    @Test
    public void testRuleFractionals_6() {
        assertEmptyMatch("33,77 мм");
    }

    @Test
    public void testRuleFractionals_7() {
        assertEmptyMatch("33,77 тис");
    }

    @Test
    public void testRuleFractionals_8() {
        assertEmptyMatch("33,5 роки");
    }

    @Test
    public void testRuleFractionals_9() {
        assertEmptyMatch("33,5 Терещенко");
    }

    @Test
    public void testRuleFractionals_10() {
        assertEmptyMatch("1998,1999 рр.");
    }

    @Test
    public void testRuleFractionals_11() {
        assertEmptyMatch("із результатом 28,91 вона посіла третє місце");
    }

    @Test
    public void testRuleFractionals_12() {
        assertEmptyMatch("Становлять відповідно 0,5 і 0,3 її оцінки.");
    }

    @Test
    public void testRuleFractionals_13() {
        assertEmptyMatch("це на 0,21 краще за попередній рекорд світу.");
    }

    @Test
    public void testRuleFractionals_14() {
        assertHasError("або у 2,2 рази.", "2,2 раза");
    }

    @Test
    public void testRuleFractionals_15() {
        assertHasError("або у 2,2 раз", "2,2 раза");
    }

    @Test
    public void testRuleFractionals_16() {
        assertEmptyMatch("або у 2,2 раза.");
    }

    @Test
    public void testRuleFractionals_17() {
        assertEmptyMatch("або у 2 рази.");
    }
}
