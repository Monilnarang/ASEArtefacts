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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TokenAgreementNumrNounRuleTest_Parameterized extends AbstractRuleTest {

    @Before
    public void setUp() throws IOException {
        rule = new TokenAgreementNumrNounRule(TestTools.getMessages("uk"), lt.getLanguage());
    }

    @Test
    public void testRuleTN_34() throws IOException {
        assertHasError("як багато білку", "багато білка", "багато білки", "багато білок", "багато білків");
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleTN_1_1to4_4to5_5to6_6to7_7to8_8_8to9_9_9to10_10to11_11to12_12_12to13_13to16_16to17_17to33_35to87")
    public void testRuleTN_1_1to4_4to5_5to6_6to7_7to8_8_8to9_9_9to10_10to11_11to12_12_12to13_13to16_16to17_17to33_35to87(String param1) throws IOException {
        assertEmptyMatch(param1);
    }

    static public Stream<Arguments> Provider_testRuleTN_1_1to4_4to5_5to6_6to7_7to8_8_8to9_9_9to10_10to11_11to12_12_12to13_13to16_16to17_17to33_35to87() {
        return Stream.of(arguments("два пацани"), arguments("два паркани"), arguments("двох пацанів"), arguments("двох парканів"), arguments("трьома пацанами"), arguments("трьома парканами"), arguments("шість пацанів"), arguments("шість парканів"), arguments("двадцять пацанів"), arguments("восьми пацанів"), arguments("восьми парканів"), arguments("вісьма пацанами"), arguments("вісьма парканами"), arguments("декілька пацанів"), arguments("декілька парканів"), arguments("кілька ложок цукру"), arguments("декільком пацанам"), arguments("декільком парканам"), arguments("вісім-дев'ять місяців"), arguments("протягом шістьох місяців"), arguments("за чотирма категоріями"), arguments("за кількома торгівельними центрами"), arguments("двоє дверей"), arguments("трьох людей"), arguments("22 червня"), arguments("2 лютого їжака забрали"), arguments("Ту-154 президента"), arguments("до 4 секунд"), arguments("2 сонця"), arguments("було дев’ять років"), arguments("багато часу"), arguments("Минуло багато-багато часу"), arguments("багато заліза"), arguments("пів ковбаси"), arguments("Вісімдесят Геннадію Терентійовичу"), arguments("3 подолянина"), arguments("два подолянина"), arguments("три рабини"), arguments("Обидва волиняни"), arguments("Обидва волинянина"), arguments("дві Франції"), arguments("дві СБУ"), arguments("усі три Петра"), arguments("у три погибелі"), arguments("багато хто"), arguments("багато які"), arguments("мало наслідком"), arguments("все одно йому"), arguments("один одному руки"), arguments("за день чи два мене"), arguments("сьома ранку"), arguments("години півтори"), arguments("хвилини за півтори поїзд приїхав"), arguments("1972 року"), arguments("1970-1971 рр."), arguments("ст. 2 пункта 1"), arguments("3 / 5 вугілля світу"), arguments("3 / 4 понеділка"), arguments("1992 рік"), arguments("обоє винні"), arguments("скільки-небудь осяжний"), arguments("обоє режисери"), arguments("сотні дві персон"), arguments("метрів п’ять килиму"), arguments("років з п’ять в'язниці"), arguments("рази може у два більший"), arguments("десятка півтора списочників"), arguments("два провінційного вигляду персонажі"), arguments("У свої вісімдесят пан Василь"), arguments("№ 4 Тижня"), arguments("57-ма вулиця"), arguments("мати не стільки слух"), arguments("$300 тис. кредиту"), arguments("20 мм кредиту"), arguments("три нікому не відомих"), arguments("несподіваного для багатьох висновку"), arguments("чотири десятих відсотка"), arguments("останніх півроку"), arguments("дві англійською"), arguments("за два злотих (15 грн)"), arguments("Дев'яноста річниця"), arguments("сьома вода на киселі"), arguments("років п'ять люди"), arguments("років через десять Литва"), arguments("у 2020 мати надійний фундамент"), arguments("тижнів зо два мати горшком воду носила"), arguments("мільйон байтів"), arguments("тримали п'ять бейсбольних біт"), arguments("10 чоловік."), arguments("дві з половиною кулі"), arguments("33,77 тонни"), arguments("2,19 бала"), arguments("33,77 мм"), arguments("33,77 тис"), arguments("33,5 роки"), arguments("33,5 Терещенко"), arguments("1998,1999 рр."), arguments("із результатом 28,91 вона посіла третє місце"), arguments("Становлять відповідно 0,5 і 0,3 її оцінки."), arguments("це на 0,21 краще за попередній рекорд світу."), arguments("або у 2,2 раза."), arguments("або у 2 рази."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleForceNoun_1_1_1to2_2_2_2to3_3to5_5to6_6to7_10to11_14to15")
    public void testRuleForceNoun_1_1_1to2_2_2_2to3_3to5_5to6_6to7_10to11_14to15(String param1, String param2) throws IOException {
        assertHasError(param1, param2);
    }

    static public Stream<Arguments> Provider_testRuleForceNoun_1_1_1to2_2_2_2to3_3to5_5to6_6to7_10to11_14to15() {
        return Stream.of(arguments("фронтові сто грам", "сто грамів"), arguments("10—12 мегават", "12 мегаватів"), arguments("п'ять байт", "п'ять байтів"), arguments("5 байт", "5 байтів"), arguments("мільйон байт", "мільйон байтів"), arguments("60 мікрорентген", "60 мікрорентгенів"), arguments("10 чоловік і жінок", "10 чоловіків"), arguments("на 200 тон", "тонн"), arguments("2 млн тон", "тонн"), arguments("кілька тон", "тонн"), arguments("вісім тисяч тон", "тонн"), arguments("22 тон", "тонн"), arguments("8,5 тон", "тонн"), arguments("два з половиною автобуса", "два з половиною автобуси"), arguments("2,4 кілограми", "2,4 кілограма"), arguments("2,4 мільйони", "2,4 мільйона"), arguments("2,54 мільйони", "2,54 мільйона"), arguments("або у 2,2 рази.", "2,2 раза"), arguments("або у 2,2 раз", "2,2 раза"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleForceNoun_3to4")
    public void testRuleForceNoun_3to4(String param1) throws IOException {
        assertHasError(param1);
    }

    static public Stream<Arguments> Provider_testRuleForceNoun_3to4() {
        return Stream.of(arguments("12 німецьких солдат"), arguments("десятки тисяч своїх солдат"));
    }
}
