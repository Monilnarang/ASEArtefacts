package org.languagetool.rules.uk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;

public class TokenAgreementAdjNounRuleTest_Purified extends AbstractRuleTest {

    @Before
    public void setUp() throws IOException {
        rule = new TokenAgreementAdjNounRule(TestTools.getMessages("uk"), lt.getLanguage());
    }

    @Test
    public void testRuleTP_1() throws IOException {
        assertHasError("скрутна справі");
    }

    @Test
    public void testRuleTP_2() throws IOException {
        assertHasError("район імпозантних віл");
    }

    @Test
    public void testRuleTP_3() throws IOException {
        assertHasError("офіційний статистика");
    }

    @Test
    public void testRuleTP_4() throws IOException {
        assertHasError("зелена яблуко.");
    }

    @Test
    public void testRuleTP_5() throws IOException {
        assertHasError("сп’янілі свободою");
    }

    @Test
    public void testRuleTP_6() throws IOException {
        assertHasError("кволий депутата");
    }

    @Test
    public void testRuleTP_7() throws IOException {
        assertHasError("кволого тюльпан");
    }

    @Test
    public void testRuleTP_8() throws IOException {
        assertHasError("цинічна винахідливості");
    }

    @Test
    public void testRuleTP_9() throws IOException {
        assertHasError("наступній рік свого життя");
    }

    @Test
    public void testRuleTP_10() throws IOException {
        assertHasError("жодного кубічного метру в Україні не буде");
    }

    @Test
    public void testRuleTP_11() throws IOException {
        assertHasError("складний рік на фондовим ринку");
    }

    @Test
    public void testRuleTP_12() throws IOException {
        assertHasError("є найкращий засобом для очистки");
    }

    @Test
    public void testRuleTP_13() throws IOException {
        assertHasError("має вчену ступінь з хімії");
    }

    @Test
    public void testRuleTP_14() throws IOException {
        assertHasError("розкішні чорні коси й, засукавши широкі рукава своєї сорочки,");
    }

    @Test
    public void testRuleTP_15() throws IOException {
        assertHasError("то коло стола її вишивані рукава мають, то коло печі");
    }

    @Test
    public void testRuleTP_16() throws IOException {
        assertHasError("валялись одірвані рукава кунтушів та поли жупанів");
    }

    @Test
    public void testRuleTP_17() throws IOException {
        assertHasError("вчинили страшенний диплома тичний галас");
    }

    @Test
    public void testRuleTP_18() throws IOException {
        assertHasError("Раймон Бенжамен і керівник європейського філії");
    }

    @Test
    public void testRuleTP_19() throws IOException {
        assertHasError("обвинувачення у вчинені злочину, передбаченого");
    }

    @Test
    public void testRuleTP_20() throws IOException {
        assertHasError("перша ступінь");
    }

    @Test
    public void testRuleTP_21() throws IOException {
        assertHasError("друга ступінь");
    }

    @Test
    public void testRuleTP_22() throws IOException {
        assertHasError("встановлена Верховної Радою 17 січня 2017 року");
    }

    @Test
    public void testRuleTP_23() throws IOException {
        assertHasError("фракцію у Верховні Раді й мати");
    }

    @Test
    public void testRuleTP_24_testMerged_24() throws IOException {
        RuleMatch[] matches0 = rule.match(lt.getAnalyzedSentence("4 російських винищувача"));
        assertEquals(1, matches0.length);
        assertTrue("Message is wrong: " + matches0[0].getMessage(), matches0[0].getMessage().contains("[ч.р.: родовий, знахідний]"));
        assertEquals(Arrays.asList("російських винищувачів", "російського винищувача"), matches0[0].getSuggestedReplacements());
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("кримського безсмертнику"));
        assertEquals(1, matches.length);
        assertTrue("Missing message for v_rod/v_dav -у/ю", matches[0].getMessage().contains("Можливо"));
        matches = rule.match(lt.getAnalyzedSentence("до 7-ми відсотків ВВП"));
        assertTrue("Missing message for кількисний числівник", matches[0].getMessage().contains("Можливо"));
        assertTrue(matches[0].getSuggestedReplacements().contains("7 відсотків"));
        matches = rule.match(lt.getAnalyzedSentence("Та пахових ділянках"));
        assertTrue("Missing message for v_mis", matches[0].getMessage().contains("Можливо"));
    }

    @Test
    public void testRuleTP_27() throws IOException {
        assertHasError("під зеківській нуль");
    }

    @Test
    public void testRuleTP_28() throws IOException {
        assertHasError("у повітряній простір");
    }

    @Test
    public void testRuleTP_29() throws IOException {
        assertHasError("в дитячий лікарні");
    }

    @Test
    public void testRuleTP_30() throws IOException {
        assertHasError("у Київський філармонії");
    }

    @Test
    public void testRuleTP_31() throws IOException {
        assertHasError("на керівні посаді");
    }

    @Test
    public void testRuleTP_32() throws IOException {
        assertHasError("незворотній процес");
    }

    @Test
    public void testRuleTP_33() throws IOException {
        assertHasError("нинішній російські владі");
    }

    @Test
    public void testRuleTP_34() throws IOException {
        assertHasError("заробітної платі");
    }

    @Test
    public void testRuleTP_35() throws IOException {
        assertHasError("сталеві панцирі");
    }

    @Test
    public void testRuleTP_36() throws IOException {
        assertHasError("У львівській та київський Книгарнях");
    }

    @Test
    public void testRuleTP_37() throws IOException {
        assertHasError("— робочій день.");
    }

    @Test
    public void testRuleTP_38() throws IOException {
        assertEmptyMatch("президентів Леонідів Кравчука та Кучму");
    }

    @Test
    public void testRuleTP_39() throws IOException {
        assertHasError("угода на 30 років зі щорічного поставкою росіянами ...");
    }

    @Test
    public void testRuleTP_40() throws IOException {
        assertHasError("сприймали так власні громадян");
    }

    @Test
    public void testRuleTP_41() throws IOException {
        assertHasError("прокатні транспорті засоби");
    }

    @Test
    public void testRuleTP_42() throws IOException {
        assertHasError("Ви не притягнене капіталу");
    }

    @Test
    public void testRuleTP_43() throws IOException {
        assertHasError("потрібна змін поколінь");
    }

    @Test
    public void testRuleTP_44() throws IOException {
        assertHasError("Незадовільне забезпеченням паливом");
    }

    @Test
    public void testRuleTP_45() throws IOException {
        assertHasError("сочиться коштовний камінням");
    }

    @Test
    public void testRuleTP_46() throws IOException {
        assertHasError("будь-якої демократичної крани");
    }

    @Test
    public void testRuleTP_47() throws IOException {
        assertHasError("Генеральній прокураторі");
    }

    @Test
    public void testRuleTP_48() throws IOException {
        assertHasError("була низька передумов");
    }

    @Test
    public void testRuleTP_49() throws IOException {
        assertHasError("після смерті легендарного Фреді Меркюрі");
    }

    @Test
    public void testRuleTP_50() throws IOException {
        assertHasError("юна викрадача й не здогадувалася");
    }

    @Test
    public void testRuleTP_51() throws IOException {
        assertHasError("По повернені кореспондентів");
    }

    @Test
    public void testRuleTP_52() throws IOException {
        assertHasError("в очікувані експериментатора");
    }

    @Test
    public void testRuleTP_53() throws IOException {
        assertHasError("певної мірою");
    }

    @Test
    public void testRuleTP_54() throws IOException {
        assertHasError("люмпенізується дедалі більша частини");
    }

    @Test
    public void testRuleTP_55() throws IOException {
        assertHasError("Державна фіскальну служба");
    }

    @Test
    public void testRuleTP_56() throws IOException {
        assertHasError("Як правило, це чоловіки, годувальними сімей");
    }

    @Test
    public void testRuleTP_57() throws IOException {
        assertHasError("з московською боку");
    }

    @Test
    public void testRuleTP_58() throws IOException {
        assertHasError("з насиджених барліг");
    }

    @Test
    public void testRuleTP_59() throws IOException {
        assertHasError("У польському Лодзі");
    }

    @Test
    public void testRuleTP_60() throws IOException {
        assertHasError("панування зимових сутінок");
    }

    @Test
    public void testRuleTP_61() throws IOException {
        assertHasError("за наявною інформацію");
    }

    @Test
    public void testRuleTP_62() throws IOException {
        assertHasError("асоціюється в нас із сучасною цивілізацію");
    }

    @Test
    public void testRuleTP_63() throws IOException {
        assertHasError("зловживання монопольних становищем");
    }

    @Test
    public void testRuleTP_64() throws IOException {
        assertHasError("проживання та дворазове харчуванням");
    }

    @Test
    public void testRuleTP_72() throws IOException {
        assertHasError("федерального округа");
    }

    @Test
    public void testRuleTP_73() throws IOException {
        assertHasError("затверджений народним віче");
    }

    @Test
    public void testRuleTP_74() throws IOException {
        assertHasError("На великому родинному віче");
    }

    @Test
    public void testRuleTP_75() throws IOException {
        assertHasError("в листопаді 2015 року на народні віче до Кривого Рогу");
    }

    @Test
    public void testRuleTP_76() throws IOException {
        assertHasError("як японські ніндзя");
    }

    @Test
    public void testRuleTP_77() throws IOException {
        assertHasError("приталені пальто");
    }

    @Test
    public void testRuleTP_78() throws IOException {
        assertHasError("соціальними мережа ми");
    }

    @Test
    public void testRuleTP_79() throws IOException {
        assertHasError("принциповими країна ми");
    }

    @Test
    public void testRuleTP_80() throws IOException {
        assertHasError("отримала мандатна ведення");
    }

    @Test
    public void testRuleTP_81() throws IOException {
        assertHasError("по вуличному Копійчина");
    }

    @Test
    public void testRuleTP_82() throws IOException {
        assertHasError("У мінську влада");
    }

    @Test
    public void testRuleTP_83() throws IOException {
        assertEmptyMatch("на пострадянський манер");
    }

    @Test
    public void testRuleTP_84() throws IOException {
        assertHasError("вздовж дніпровської вісі");
    }

    @Test
    public void testRuleTP_85() throws IOException {
        assertHasError("ніщо так не зближає приморських партизан");
    }

    @Test
    public void testRuleTP_86() throws IOException {
        assertHasError("як боротьбу сунітської більшість");
    }

    @Test
    public void testRuleTP_87() throws IOException {
        assertHasError("найцікавішій час");
    }

    @Test
    public void testRuleTP_88() throws IOException {
        assertHasError("відкинутий набагато років назад");
    }

    @Test
    public void testRuleTP_89() throws IOException {
        assertHasError("символічною акцію стало складання");
    }

    @Test
    public void testRuleTP_90() throws IOException {
        assertHasError("в будь–яким момент");
    }

    @Test
    public void testRuleTP_91() throws IOException {
        assertHasError("хронічними порушення торговельних угод");
    }

    @Test
    public void testRuleTP_92() throws IOException {
        assertHasError("вбивство 148-ми селян");
    }

    @Test
    public void testRule_1() throws IOException {
        assertEmptyMatch("холодний яр");
    }

    @Test
    public void testRule_2() throws IOException {
        assertEmptyMatch("страшне плацебо");
    }

    @Test
    public void testRule_3() throws IOException {
        assertEmptyMatch("військової продукції");
    }

    @Test
    public void testRule_4() throws IOException {
        assertEmptyMatch("Ім'я Мандели, дане йому при народженні");
    }

    @Test
    public void testRule_5() throws IOException {
        assertEmptyMatch("Я не бачив сенсу в тому, щоб виклика́ти свідків і захищатися.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertEmptyMatch("погоджувальної комісії Інституту");
    }

    @Test
    public void testRule_7() throws IOException {
        assertEmptyMatch("відштовхнути нового колегу.");
    }

    @Test
    public void testRule_8() throws IOException {
        assertEmptyMatch("державну зраду.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertEmptyMatch("(Пізніше Вальтер став першим");
    }

    @Test
    public void testRule_10() throws IOException {
        assertEmptyMatch("складовою успіху");
    }

    @Test
    public void testRule_11() throws IOException {
        assertEmptyMatch("про екс-першого віце-спікера.");
    }

    @Test
    public void testRule_12() throws IOException {
        assertEmptyMatch("Маю лишній квиток і подумав за свого найкращого друга");
    }

    @Test
    public void testRule_13() throws IOException {
        assertEmptyMatch("Засвідчувана досить часто наукою «гнучкість» — один із коренів\n" + "паранаукових явищ на кшталт «нової хронології» Фоменка.");
    }

    @Test
    public void testRule_14() throws IOException {
        assertEmptyMatch("прикрита швидко рука");
    }

    @Test
    public void testRule_15() throws IOException {
        assertHasError("прикрита швидко руку");
    }

    @Test
    public void testRule_16() throws IOException {
        assertEmptyMatch("прикрита отруйливо гарячим");
    }

    @Test
    public void testRule_17() throws IOException {
        assertEmptyMatch("після короткого резюме справи");
    }

    @Test
    public void testRule_18() throws IOException {
        assertEmptyMatch("білий як полотно");
    }

    @Test
    public void testRule_19() throws IOException {
        assertEmptyMatch("розділеного вже чверть століття");
    }

    @Test
    public void testRule_20() throws IOException {
        assertEmptyMatch("розділеного третину століття");
    }

    @Test
    public void testRule_21() throws IOException {
        assertEmptyMatch("заклопотані чимало людей");
    }

    @Test
    public void testRule_22() throws IOException {
        assertEmptyMatch("заклопотані дуже обмаль людей");
    }

    @Test
    public void testRule_23() throws IOException {
        assertEmptyMatch("заданою відносно спостерігача");
    }

    @Test
    public void testRule_24() throws IOException {
        assertEmptyMatch("опублікований увечері понеділка");
    }

    @Test
    public void testRule_25() throws IOException {
        assertEmptyMatch("зареєстровані зокрема БЮТівець Микола Булатецький та самовисуванець");
    }

    @Test
    public void testRule_26() throws IOException {
        assertEmptyMatch("схований всередині номера");
    }

    @Test
    public void testRule_27() throws IOException {
        assertEmptyMatch("надана як раз");
    }

    @Test
    public void testRule_28() throws IOException {
        assertEmptyMatch("підірваною ще раз");
    }

    @Test
    public void testRule_29() throws IOException {
        assertEmptyMatch("спортивні ЦРУ");
    }

    @Test
    public void testRule_30() throws IOException {
        assertEmptyMatch("Сумське НПО");
    }

    @Test
    public void testExceptions_1() throws IOException {
        assertEmptyMatch("у могутні Максимові обійми");
    }

    @Test
    public void testExceptions_2() throws IOException {
        assertEmptyMatch("надання болгарській статусу");
    }

    @Test
    public void testExceptions_3() throws IOException {
        assertEmptyMatch("старший зміни");
    }

    @Test
    public void testExceptions_4() throws IOException {
        assertEmptyMatch("Наступні півроку.");
    }

    @Test
    public void testExceptions_5() throws IOException {
        assertEmptyMatch("одної шостої світу");
    }

    @Test
    public void testExceptions_6() throws IOException {
        assertEmptyMatch("Кожному наглядач кивав");
    }

    @Test
    public void testExceptions_7() throws IOException {
        assertEmptyMatch("чогось схожого Європа");
    }

    @Test
    public void testExceptions_8() throws IOException {
        assertEmptyMatch("писав про щось подібне Юрій");
    }

    @Test
    public void testExceptions_9() throws IOException {
        assertEmptyMatch("з 11-ма годинами");
    }

    @Test
    public void testExceptions_10() throws IOException {
        assertEmptyMatch("ще чого доброго ціна впаде");
    }

    @Test
    public void testExceptions_11() throws IOException {
        assertEmptyMatch("чотирициліндровий об’ємом 1000 куб. см.");
    }

    @Test
    public void testExceptions_12() throws IOException {
        assertEmptyMatch("10 жовтих площею 1,5 ");
    }

    @Test
    public void testExceptions_13() throws IOException {
        assertEmptyMatch("безплатні довжиною від 100 до 1000 метрів");
    }

    @Test
    public void testExceptions_14() throws IOException {
        assertEmptyMatch("за метр кубічний води");
    }

    @Test
    public void testExceptions_15() throws IOException {
        assertEmptyMatch("200% річних прибутку");
    }

    @Test
    public void testExceptions_16() throws IOException {
        assertEmptyMatch("муніципальна плюс виробнича");
    }

    @Test
    public void testExceptions_17() throws IOException {
        assertEmptyMatch("Головне центр правильно вибити");
    }

    @Test
    public void testExceptions_18() throws IOException {
        assertEmptyMatch("вибране сучукрліту");
    }

    @Test
    public void testExceptions_19() throws IOException {
        assertEmptyMatch("Менше народу – більше кисню");
    }

    @Test
    public void testExceptions_20() throws IOException {
        assertEmptyMatch("Найчастіше випадки прямого підкупу");
    }

    @Test
    public void testExceptions_21() throws IOException {
        assertEmptyMatch("– щонайперше олівець, простий, твердий");
    }

    @Test
    public void testExceptions_22() throws IOException {
        assertEmptyMatch("Найбільше звинувачень у відьомстві");
    }

    @Test
    public void testExceptions_23() throws IOException {
        assertEmptyMatch("— Раніше Україна неодноразово заявляла");
    }

    @Test
    public void testExceptions_24() throws IOException {
        assertEmptyMatch("Від наступних пари десятків");
    }

    @Test
    public void testExceptions_25() throws IOException {
        assertEmptyMatch("Суд визнав неконституційними низку положень");
    }

    @Test
    public void testExceptions_26() throws IOException {
        assertHasError("від наступних пари");
    }

    @Test
    public void testExceptions_27() throws IOException {
        assertEmptyMatch("Північний Рейн-Вестфалія");
    }

    @Test
    public void testExceptionsNumbers_1() throws IOException {
        assertEmptyMatch("33 народних обранці");
    }

    @Test
    public void testExceptionsNumbers_2() throws IOException {
        assertEmptyMatch("ще троє автокефальних єпископи");
    }

    @Test
    public void testExceptionsNumbers_3() throws IOException {
        assertEmptyMatch("два-три реальних кандидати");
    }

    @Test
    public void testExceptionsNumbers_4() throws IOException {
        assertEmptyMatch("три жовтих обірваних чоловіки");
    }

    @Test
    public void testExceptionsNumbers_5() throws IOException {
        assertEmptyMatch("обидва вітчизняних наукових ступені");
    }

    @Test
    public void testExceptionsNumbers_6() throws IOException {
        assertEmptyMatch("3-4 реально хворих депутати");
    }

    @Test
    public void testExceptionsNumbers_7() throws IOException {
        assertEmptyMatch("два–три колишніх кандидати");
    }

    @Test
    public void testExceptionsNumbers_8() throws IOException {
        assertEmptyMatch("два (чи навіть три) різних завершення роману");
    }

    @Test
    public void testExceptionsNumbers_9() throws IOException {
        assertEmptyMatch("два нових горнятка");
    }

    @Test
    public void testExceptionsNumbers_10() throws IOException {
        assertEmptyMatch("два жіночих імені");
    }

    @Test
    public void testExceptionsNumbers_11() throws IOException {
        assertEmptyMatch("два різних міста");
    }

    @Test
    public void testExceptionsNumbers_12() throws IOException {
        assertEmptyMatch("два абсолютно різних міста");
    }

    @Test
    public void testExceptionsNumbers_13() throws IOException {
        assertEmptyMatch("три предкові слов’янські племені");
    }

    @Test
    public void testExceptionsNumbers_14() throws IOException {
        assertEmptyMatch("два «круглих столи»");
    }

    @Test
    public void testExceptionsNumbers_15() throws IOException {
        assertHasError("два високих депутат");
    }

    @Test
    public void testExceptionsNumbers_16() throws IOException {
        assertHasError("дві високих дівчині");
    }

    @Test
    public void testExceptionsNumbers_17() throws IOException {
        assertHasError("83,7 квадратних кілометра");
    }

    @Test
    public void testExceptionsNumbers_18() throws IOException {
        assertEmptyMatch("дві мільярдних метра");
    }

    @Test
    public void testExceptionsNumbers_19() throws IOException {
        assertEmptyMatch("п’ять шостих населення");
    }

    @Test
    public void testExceptionsNumbers_20() throws IOException {
        assertEmptyMatch("чотирьох п’ятих прибутку");
    }

    @Test
    public void testExceptionsNumbers_21() throws IOException {
        assertHasError("п'ять шості світу");
    }

    @Test
    public void testExceptionsNumbers_22() throws IOException {
        assertEmptyMatch("1/8-ї фіналу");
    }

    @Test
    public void testExceptionsNumbers_23() throws IOException {
        assertHasError("1/8-ї фіналом");
    }

    @Test
    public void testExceptionsNumbers_24() throws IOException {
        assertEmptyMatch("В одній другій українка здолала");
    }

    @Test
    public void testExceptionsNumbers_25() throws IOException {
        assertEmptyMatch("поступився в одній восьмій французу");
    }

    @Test
    public void testExceptionsNumbers_26() throws IOException {
        assertEmptyMatch("дві других дівчини");
    }

    @Test
    public void testExceptionsNumbers_27() throws IOException {
        assertHasError("дві других дівчині");
    }

    @Test
    public void testExceptionsNumbers_28() throws IOException {
        assertEmptyMatch("1–3-й класи поснідали й побігли");
    }

    @Test
    public void testExceptionsNumbers_29() throws IOException {
        assertEmptyMatch("у 5–8-му класах");
    }

    @Test
    public void testExceptionsNumbers_30() throws IOException {
        assertEmptyMatch("на сьомому–восьмому поверхах");
    }

    @Test
    public void testExceptionsNumbers_31() throws IOException {
        assertEmptyMatch("на 14—16-те місця");
    }

    @Test
    public void testExceptionsNumbers_32() throws IOException {
        assertHasError("3-й класи поснідали");
    }

    @Test
    public void testExceptionsNumbers_33() throws IOException {
        assertHasError("одному-два непоганих шанси");
    }

    @Test
    public void testExceptionsNumbers_34_testMerged_34() throws IOException {
        RuleMatch[] match = rule.match(lt.getAnalyzedSentence("залишилося сиротами 22-є дітей"));
        assertEquals(1, match.length);
        assertTrue(match[0].getMessage().contains("літерне нарощення після кількісного числівника"));
    }

    @Test
    public void testExceptionsNumbers_36() throws IOException {
        assertEmptyMatch("восьмого – дев’ятого класів");
    }

    @Test
    public void testExceptionsNumbers_37() throws IOException {
        assertEmptyMatch("перший — дев’ятий класи");
    }

    @Test
    public void testExceptionsNumbers_38() throws IOException {
        assertEmptyMatch("і о 7-й ранку нас зустрічає");
    }

    @Test
    public void testExceptionsNumbers_39() throws IOException {
        assertEmptyMatch("Призначений на 11-ту похід");
    }

    @Test
    public void testExceptionsNumbers_40() throws IOException {
        assertEmptyMatch("о шостій ранку");
    }

    @Test
    public void testExceptionsNumbers_41() throws IOException {
        assertEmptyMatch("дванадцята дня");
    }

    @Test
    public void testExceptionsNumbers_42() throws IOException {
        assertEmptyMatch("Ставши 2003-го прем’єром");
    }

    @Test
    public void testExceptionsNumbers_43() throws IOException {
        assertEmptyMatch("У 1990-х скрута змусила");
    }

    @Test
    public void testExceptionsNumbers_44() throws IOException {
        assertEmptyMatch("за 2009-й відомство зобов’язало");
    }

    @Test
    public void testExceptionsNumbers_45() throws IOException {
        assertEmptyMatch("підвів риску під 2011-м програмою «ТОП-100»");
    }

    @Test
    public void testExceptionsNumbers_46() throws IOException {
        assertEmptyMatch("Лише в 1990-ті частину саду вдруге зробили доступною");
    }

    @Test
    public void testExceptionsNumbers_47() throws IOException {
        assertEmptyMatch("У 2009–2010-му дефіцит бюджету сягав близько 1/3 видатків");
    }

    @Test
    public void testExceptionsNumbers_48() throws IOException {
        assertEmptyMatch("в 1920–1930-х батько митця показав себе як український патріот");
    }

    @Test
    public void testExceptionsNumbers_49() throws IOException {
        assertEmptyMatch("за часів «конфронтації» 2008–2009-го квота на них зросла");
    }

    @Test
    public void testExceptionsNumbers_50() throws IOException {
        assertEmptyMatch("тільки за 1986–1988-й країна втратила близько 40 млрд крб");
    }

    @Test
    public void testExceptionsNumbers_51() throws IOException {
        assertEmptyMatch("На початку двотисячних режисер зустрів двох людей");
    }

    @Test
    public void testExceptionsNumbers_52() throws IOException {
        assertEmptyMatch("корифеї американської поезії 1950-60-х Лоренс Ферлінгетті");
    }

    @Test
    public void testExceptionsNumbers_53() throws IOException {
        assertEmptyMatch("На зламі 80-90-их функціонери ...");
    }

    @Test
    public void testExceptionsNumbers_54() throws IOException {
        assertEmptyMatch("щороку під Дев’яте травня");
    }

    @Test
    public void testExceptionsNumbers_55() throws IOException {
        assertEmptyMatch("з четвертого по одинадцяте липня");
    }

    @Test
    public void testExceptionsNumbers_56() throws IOException {
        assertEmptyMatch("замість звичного десятиліттями «Українського»");
    }

    @Test
    public void testExceptionsNumbers_57() throws IOException {
        assertEmptyMatch("природний тисячею років підтверджений");
    }

    @Test
    public void testExceptionsNumbers_58() throws IOException {
        assertHasError("на 131-му хвилині");
    }

    @Test
    public void testExceptionsNumbers_59() throws IOException {
        assertHasError("О 12–ї годині");
    }

    @Test
    public void testExceptionsNumbers_60() throws IOException {
        assertEmptyMatch("Анонсована тиждень тому домовленість");
    }

    @Test
    public void testExceptionsNumbers_61() throws IOException {
        assertEmptyMatch("забули про популярні пару років тому");
    }

    @Test
    public void testExceptionsNumbers_62() throws IOException {
        assertEmptyMatch("завищена разів у десять");
    }

    @Test
    public void testExceptionsNumbers_63() throws IOException {
        assertEmptyMatch("інвестиція на найближчі років п’ять");
    }

    @Test
    public void testExceptionsNumbers_64() throws IOException {
        assertEmptyMatch("до розташованого кілометрів за шість");
    }

    @Test
    public void testExceptionsNumbers_65() throws IOException {
        assertEmptyMatch("заповнені відсотків на 80");
    }

    @Test
    public void testExceptionsNumbers_66() throws IOException {
        assertEmptyMatch("лячно було перші хвилин 40");
    }

    @Test
    public void testExceptionsNumbers_67() throws IOException {
        assertEmptyMatch("і посаджений років на 10–15");
    }

    @Test
    public void testExceptionsNumbers_68() throws IOException {
        assertEmptyMatch("і піднятий відсотки на 3");
    }

    @Test
    public void testExceptionsNumbers_69() throws IOException {
        assertEmptyMatch("Поховавши році в п'ятдесятому жінку");
    }

    @Test
    public void testExceptionsNumbers_70() throws IOException {
        assertEmptyMatch("хвилини з 55-ї вірмени почали застосовувати пресинг");
    }

    @Test
    public void testExceptionsNumbers_71() throws IOException {
        assertEmptyMatch("один 5-а клас");
    }

    @Test
    public void testExceptionsNumbers_72() throws IOException {
        assertEmptyMatch("маршрутка номер 29-а фірми “Фіакр”");
    }

    @Test
    public void testExceptionsNumbers_73() throws IOException {
        assertEmptyMatch("на вул. Рубчака, 17-а Тарас Стецьків");
    }

    @Test
    public void testExceptionsNumbers_74() throws IOException {
        assertEmptyMatch("вулиці Володимира Великого, 35-а Юрій Борсук");
    }

    @Test
    public void testExceptionsOther_1() throws IOException {
        assertEmptyMatch("Завдяки останнім бізнес");
    }

    @Test
    public void testExceptionsOther_2() throws IOException {
        assertEmptyMatch("порядок денний парламенту");
    }

    @Test
    public void testExceptionsOther_3() throws IOException {
        assertEmptyMatch("зокрема статтю 6-ту закону");
    }

    @Test
    public void testExceptionsOther_4() throws IOException {
        assertEmptyMatch("князівством Литовським подоляни");
    }

    @Test
    public void testExceptionsOther_5() throws IOException {
        assertEmptyMatch("абзац перший частини другої");
    }

    @Test
    public void testExceptionsOther_6() throws IOException {
        assertEmptyMatch("абзац другий частини першої");
    }

    @Test
    public void testExceptionsOther_7() throws IOException {
        assertEmptyMatch("частина четверта статті 53");
    }

    @Test
    public void testExceptionsOther_8() throws IOException {
        assertEmptyMatch("яких не мала рівних українка");
    }

    @Test
    public void testExceptionsOther_9() throws IOException {
        assertEmptyMatch("Київський імені Шевченка");
    }

    @Test
    public void testExceptionsOther_10() throws IOException {
        assertEmptyMatch("і колишня Маяковського");
    }

    @Test
    public void testExceptionsOther_11() throws IOException {
        assertEmptyMatch("Львівської ім. С. Крушельницької");
    }

    @Test
    public void testExceptionsOther_12() throws IOException {
        assertEmptyMatch("4-й Запорізький ім. гетьмана Б. Хмельницького");
    }

    @Test
    public void testExceptionsOther_13() throws IOException {
        assertHasError("у Великій Вітчизняній Війн");
    }

    @Test
    public void testExceptionsOther_14() throws IOException {
        assertHasError("у Великій Вітчизняній війна");
    }

    @Test
    public void testExceptionsOther_15() throws IOException {
        assertEmptyMatch("Після Великої Вітчизняної будівництво істотно розширилося");
    }

    @Test
    public void testExceptionsOther_16() throws IOException {
        assertEmptyMatch("польські зразка 1620—1650 років");
    }

    @Test
    public void testExceptionsOther_17() throws IOException {
        assertEmptyMatch("чинних станом на 4 червня");
    }

    @Test
    public void testExceptionsOther_18() throws IOException {
        assertEmptyMatch("а старший групи");
    }

    @Test
    public void testExceptionsOther_19() throws IOException {
        assertEmptyMatch("Не пасли задніх міліціонери");
    }

    @Test
    public void testExceptionsOther_20() throws IOException {
        assertEmptyMatch("сильних світу цього");
    }

    @Test
    public void testExceptionsOther_21() throws IOException {
        assertEmptyMatch("найвпливовіших світу сього");
    }

    @Test
    public void testExceptionsOther_22() throws IOException {
        assertEmptyMatch("усіх до єдиного");
    }

    @Test
    public void testExceptionsOther_23() throws IOException {
        assertEmptyMatch("усі до єдиного депутати");
    }

    @Test
    public void testExceptionsOther_24() throws IOException {
        assertEmptyMatch("Вольному воля");
    }

    @Test
    public void testExceptionsOther_25() throws IOException {
        assertEmptyMatch("порядку денного засідань");
    }

    @Test
    public void testExceptionsOther_26() throws IOException {
        assertEmptyMatch("лаву запасних партії");
    }

    @Test
    public void testExceptionsOther_27() throws IOException {
        assertEmptyMatch("викладатися на повну артисти");
    }

    @Test
    public void testExceptionsOther_28() throws IOException {
        assertEmptyMatch("молодшого гвардії сержанта");
    }

    @Test
    public void testExceptionsOther_29() throws IOException {
        assertEmptyMatch("постійно на рівних міністри, президенти");
    }

    @Test
    public void testExceptionsOther_30() throws IOException {
        assertEmptyMatch("під час Другої світової командири");
    }

    @Test
    public void testExceptionsOther_31() throws IOException {
        assertEmptyMatch("до слова Божого людей");
    }

    @Test
    public void testExceptionsOther_32() throws IOException {
        assertEmptyMatch("Різдва Христова вигнанець");
    }

    @Test
    public void testExceptionsOther_33() throws IOException {
        assertEmptyMatch("ведуча Першого Національного Марія Орлова");
    }

    @Test
    public void testExceptionsOther_34() throws IOException {
        assertEmptyMatch("дівоче Анна");
    }

    @Test
    public void testExceptionsOther_35() throws IOException {
        assertEmptyMatch("В середньому тривалість курсів для отримання");
    }

    @Test
    public void testExceptionsOther_36() throws IOException {
        assertEmptyMatch("в цілому результатом задоволені");
    }

    @Test
    public void testExceptionsOther_37() throws IOException {
        assertEmptyMatch("на червень поточного року $29,3 млрд");
    }

    @Test
    public void testExceptionsOther_38() throws IOException {
        assertEmptyMatch("Одним із перших бажання придбати");
    }

    @Test
    public void testExceptionsOther_39() throws IOException {
        assertEmptyMatch("Першими голодування оголосили депутати");
    }

    @Test
    public void testExceptionsOther_40() throws IOException {
        assertEmptyMatch("Перший людина проходить");
    }

    @Test
    public void testExceptionsOther_41() throws IOException {
        assertEmptyMatch("Перший митців принуджував");
    }

    @Test
    public void testExceptionsOther_42() throws IOException {
        assertEmptyMatch("вважаючи перших джерелом");
    }

    @Test
    public void testExceptionsOther_43() throws IOException {
        assertEmptyMatch("нічого протизаконного жінка не зробила");
    }

    @Test
    public void testExceptionsOther_44() throws IOException {
        assertEmptyMatch("Нічого подібного Сергій не казав");
    }

    @Test
    public void testExceptionsOther_45() throws IOException {
        assertEmptyMatch("Нічого поганого людям");
    }

    @Test
    public void testExceptionsOther_46() throws IOException {
        assertEmptyMatch("що нічим дієвим ініціативи не завершаться");
    }

    @Test
    public void testExceptionsOther_47() throws IOException {
        assertHasError("на Західній України");
    }

    @Test
    public void testExceptionsOther_48() throws IOException {
        assertEmptyMatch("переконана психолог");
    }

    @Test
    public void testExceptionsOther_49() throws IOException {
        assertEmptyMatch("Серед присутніх Микола");
    }

    @Test
    public void testExceptionsOther_50() throws IOException {
        assertEmptyMatch("контраргументами рідних засновника структури");
    }

    @Test
    public void testExceptionsOther_51() throws IOException {
        assertEmptyMatch("спіймали на гарячому хабарників");
    }

    @Test
    public void testExceptionsOther_52() throws IOException {
        assertEmptyMatch("була б зовсім іншою динаміка");
    }

    @Test
    public void testExceptionsOther_53() throws IOException {
        assertEmptyMatch("була такою жорстокою політика");
    }

    @Test
    public void testExceptionsOther_54() throws IOException {
        assertEmptyMatch("стали архаїчними структури");
    }

    @Test
    public void testExceptionsOther_55() throws IOException {
        assertEmptyMatch("назвав винним Юрія");
    }

    @Test
    public void testExceptionsOther_56() throws IOException {
        assertEmptyMatch("відмінних від російської моделей");
    }

    @Test
    public void testExceptionsOther_57() throws IOException {
        assertEmptyMatch("не перевищував кількох десятих відсотка");
    }

    @Test
    public void testExceptionsOther_58() throws IOException {
        assertEmptyMatch("Береженого Бог береже");
    }

    @Test
    public void testExceptionsPredic_1() throws IOException {
        assertEmptyMatch("всі сумнівні слід викинути");
    }

    @Test
    public void testExceptionsPredic_2() throws IOException {
        assertEmptyMatch("все зроблене слід обов'язково перетворити");
    }

    @Test
    public void testExceptionsAdjp_1() throws IOException {
        assertEmptyMatch("обмежуючий власність");
    }

    @Test
    public void testExceptionsAdjp_2() throws IOException {
        assertEmptyMatch("створивший історію");
    }

    @Test
    public void testExceptionsAdjp_3() throws IOException {
        assertEmptyMatch("Помальована в біле кімната");
    }

    @Test
    public void testExceptionsAdjp_4() throws IOException {
        assertEmptyMatch("Помальована в усе біле кімната");
    }

    @Test
    public void testExceptionsAdjp_5() throws IOException {
        assertHasError("помальований в біле кімната");
    }

    @Test
    public void testExceptionsAdjp_6() throws IOException {
        assertEmptyMatch("вкриті плющем будинки");
    }

    @Test
    public void testExceptionsAdjp_7() throws IOException {
        assertEmptyMatch("всі вкриті плющем");
    }

    @Test
    public void testExceptionsAdjp_8() throws IOException {
        assertEmptyMatch("оприлюднений депутатом Луценком");
    }

    @Test
    public void testExceptionsAdjp_9() throws IOException {
        assertEmptyMatch("щойно оголошених спікером як відсутніх");
    }

    @Test
    public void testExceptionsAdjp_10() throws IOException {
        assertEmptyMatch("групи захищені законом від образ");
    }

    @Test
    public void testExceptionsAdjp_11() throws IOException {
        assertEmptyMatch("змучений тягарем життя");
    }

    @Test
    public void testExceptionsAdjp_12() throws IOException {
        assertEmptyMatch("відправлені глядачами протягом 20 хвилин");
    }

    @Test
    public void testExceptionsAdjp_13() throws IOException {
        assertEmptyMatch("здивований запалом, який проступав");
    }

    @Test
    public void testExceptionsAdjp_14() throws IOException {
        assertEmptyMatch("охопленому насильством ваальському трикутнику");
    }

    @Test
    public void testExceptionsAdjp_15() throws IOException {
        assertEmptyMatch("переданих заповідником церкві");
    }

    @Test
    public void testExceptionsAdjp_16() throws IOException {
        assertEmptyMatch("більше занепокоєних захистом власних прав");
    }

    @Test
    public void testExceptionsAdjp_17() throws IOException {
        assertEmptyMatch("підсвічений синім діамант");
    }

    @Test
    public void testExceptionsAdjp_18() throws IOException {
        assertEmptyMatch("повторена тисячу разів");
    }

    @Test
    public void testExceptionsAdjp_19() throws IOException {
        assertEmptyMatch("підсвічений синім діамант");
    }

    @Test
    public void testExceptionsAdjp_20() throws IOException {
        assertHasError("підсвічений синім діамантів");
    }

    @Test
    public void testExceptionsAdjp_21() throws IOException {
        assertEmptyMatch("Нав’язаний Австрії нейтралітет");
    }

    @Test
    public void testExceptionsAdjp_22() throws IOException {
        assertEmptyMatch("Нав’язаний Австрії коаліцією");
    }

    @Test
    public void testExceptionsAdjp_23() throws IOException {
        assertEmptyMatch("Наймилішою українцеві залишається бронза");
    }

    @Test
    public void testExceptionsAdjp_24() throws IOException {
        assertEmptyMatch("на цих загальновідомих американцям зразках");
    }

    @Test
    public void testExceptionsAdjp_25() throws IOException {
        assertEmptyMatch("слід бути свідомими необхідності");
    }

    @Test
    public void testExceptionsAdjp_26() throws IOException {
        assertEmptyMatch("влаштованою Мазепі Петром");
    }

    @Test
    public void testExceptionsAdjp_27() throws IOException {
        assertEmptyMatch("будуть вдячні державі Україна");
    }

    @Test
    public void testExceptionsAdjp_28() throws IOException {
        assertEmptyMatch("мають бути підпорядковані служінню чоловікові");
    }

    @Test
    public void testExceptionsAdjp_29() throws IOException {
        assertEmptyMatch("більше відомої загалу як");
    }

    @Test
    public void testExceptionsAdjp_30() throws IOException {
        assertEmptyMatch("одержимі суверенітетом");
    }

    @Test
    public void testExceptionsAdjp_31() throws IOException {
        assertHasError("Нав’язаний Австрії нейтралітеті");
    }

    @Test
    public void testExceptionsVerb_1() throws IOException {
        assertEmptyMatch("слід бути обережними туристам у горах");
    }

    @Test
    public void testExceptionsVerb_2() throws IOException {
        assertHasError("слід бути обережною туристам у горах");
    }

    @Test
    public void testExceptionsVerb_3() throws IOException {
        assertEmptyMatch("зараз повинне ділом довести");
    }

    @Test
    public void testExceptionsVerb_4() throws IOException {
        assertEmptyMatch("Вони здатні екскаватором переорювати");
    }

    @Test
    public void testExceptionsVerb_5() throws IOException {
        assertEmptyMatch("яке готове матір рідну продати");
    }

    @Test
    public void testExceptionsVerb_6() throws IOException {
        assertEmptyMatch("Через якийсь час був змушений академію покинути");
    }

    @Test
    public void testExceptionsVerb_7() throws IOException {
        assertEmptyMatch("Досі була чинною заборона");
    }

    @Test
    public void testExceptionsVerb_8() throws IOException {
        assertEmptyMatch("Досі була б чинною заборона");
    }

    @Test
    public void testExceptionsVerb_9() throws IOException {
        assertEmptyMatch("Стає очевидною наявність");
    }

    @Test
    public void testExceptionsVerb_10() throws IOException {
        assertEmptyMatch("було куди зрозумілішим гасло самостійності");
    }

    @Test
    public void testExceptionsVerb_11() throws IOException {
        assertEmptyMatch("є очевидною війна");
    }

    @Test
    public void testExceptionsVerb_12() throws IOException {
        assertEmptyMatch("була б такою ж суттєвою явка");
    }

    @Test
    public void testExceptionsVerb_13() throws IOException {
        assertEmptyMatch("і була б дещо абсурдною ситуація.");
    }

    @Test
    public void testExceptionsVerb_14() throws IOException {
        assertEmptyMatch("Стали дорожчими хліб чи бензин");
    }

    @Test
    public void testExceptionsVerb_15() throws IOException {
        assertEmptyMatch("дівчат не залишила байдужими інформація");
    }

    @Test
    public void testExceptionsVerb_16() throws IOException {
        assertEmptyMatch("визнали справедливою наставники обох команд");
    }

    @Test
    public void testExceptionsVerb_17() throws IOException {
        assertEmptyMatch("визнало незаконною Міністерство юстиції");
    }

    @Test
    public void testExceptionsVerb_18() throws IOException {
        assertEmptyMatch("яку роблять знаковою плями на мундирі");
    }

    @Test
    public void testExceptionsVerb_19() throws IOException {
        assertEmptyMatch("видається цілком стабільною демократія");
    }

    @Test
    public void testExceptionsVerb_20() throws IOException {
        assertEmptyMatch("може бути не ідеальною форма тістечок");
    }

    @Test
    public void testExceptionsVerb_21() throws IOException {
        assertEmptyMatch("не можуть бути толерантними ізраїльтяни");
    }

    @Test
    public void testExceptionsVerb_22() throws IOException {
        assertEmptyMatch("які зроблять неможливою ротацію влади");
    }

    @Test
    public void testExceptionsVerb_23() throws IOException {
        assertEmptyMatch("зробити відкритим доступ");
    }

    @Test
    public void testExceptionsVerb_24() throws IOException {
        assertEmptyMatch("визнають регіональними облради");
    }

    @Test
    public void testExceptionsVerb_25() throws IOException {
        assertEmptyMatch("залишивши незруйнованим Карфаген");
    }

    @Test
    public void testExceptionsVerb_26() throws IOException {
        assertEmptyMatch("зробить обтяжливим використання");
    }

    @Test
    public void testExceptionsVerb_27() throws IOException {
        assertEmptyMatch("На сьогодні залишається невідомою доля близько 200 людей");
    }

    @Test
    public void testExceptionsVerb_28() throws IOException {
        assertEmptyMatch("виявлено побитою Катю");
    }

    @Test
    public void testExceptionsVerb_29() throws IOException {
        assertEmptyMatch("лишаючи порожньою клітку");
    }

    @Test
    public void testExceptionsVerb_30() throws IOException {
        assertEmptyMatch("роблячи жорсткішими правила");
    }

    @Test
    public void testExceptionsVerb_31() throws IOException {
        assertEmptyMatch("вважає повністю вірною постанову");
    }

    @Test
    public void testExceptionsVerb_32() throws IOException {
        assertEmptyMatch("визнав протиправним і недійсним внесення");
    }

    @Test
    public void testExceptionsVerb_33() throws IOException {
        assertEmptyMatch("зробило можливою і необхідною появу нового гравця");
    }

    @Test
    public void testExceptionsVerb_34() throws IOException {
        assertHasError("саме з такою тенденцію стикнулися");
    }

    @Test
    public void testExceptionsVerb_35() throws IOException {
        assertHasError("був продиктований глибоким розуміння");
    }

    @Test
    public void testExceptionsVerb_36() throws IOException {
        assertHasError("вважають нелегітимними анексію");
    }

    @Test
    public void testExceptionsVerb_37() throws IOException {
        assertHasError("не залишили байдужими адміністрацію");
    }

    @Test
    public void testExceptionsVerb_38() throws IOException {
        assertEmptyMatch("визнання неконституційним закону");
    }

    @Test
    public void testExceptionsVerb_39() throws IOException {
        assertEmptyMatch("визнання недійсним рішення");
    }

    @Test
    public void testExceptionsVerb_40() throws IOException {
        assertHasError("через визнання тут шкідливою орієнтацію на народну мову");
    }

    @Test
    public void testExceptionsVerb_41() throws IOException {
        assertHasError("визнання неконституційними закону");
    }

    @Test
    public void testExceptionsVerb_42() throws IOException {
        assertHasError("визнання недійсним рішенню");
    }

    @Test
    public void testExceptionsAdj_1() throws IOException {
        assertEmptyMatch("жадібна землі");
    }

    @Test
    public void testExceptionsAdj_2() throws IOException {
        assertEmptyMatch("вдячного батьку");
    }

    @Test
    public void testExceptionsAdj_3() throws IOException {
        assertEmptyMatch("Я вдячний редакторові Вільяму Філліпсу");
    }

    @Test
    public void testExceptionsAdj_4() throws IOException {
        assertEmptyMatch("радий присутності генерала");
    }

    @Test
    public void testExceptionsAdj_5() throws IOException {
        assertEmptyMatch("відомий мешканцям");
    }

    @Test
    public void testExceptionsAdj_6() throws IOException {
        assertEmptyMatch("менш незрозумілу киянам");
    }

    @Test
    public void testExceptionsAdj_7() throws IOException {
        assertEmptyMatch("найстарший віком із нас");
    }

    @Test
    public void testExceptionsAdj_8() throws IOException {
        assertEmptyMatch("таких немилих серцю Булгакова");
    }

    @Test
    public void testExceptionsAdj_9() throws IOException {
        assertEmptyMatch("експозиція, присвячена Леоніду Іллічу");
    }

    @Test
    public void testExceptionsAdj_10() throws IOException {
        assertEmptyMatch("печаткою та вручене платнику");
    }

    @Test
    public void testExceptionsAdj_11() throws IOException {
        assertEmptyMatch("і кожна масою 10 кг");
    }

    @Test
    public void testExceptionsAdj_12() throws IOException {
        assertHasError("жадібна землею");
    }

    @Test
    public void testExceptionsAdj_13() throws IOException {
        assertEmptyMatch("протилежний очікуваному результат");
    }

    @Test
    public void testExceptionsAdj_14() throws IOException {
        assertEmptyMatch("альтернативну олігархічній модель");
    }

    @Test
    public void testExceptionsAdj_15() throws IOException {
        assertEmptyMatch("альтернативні газовому варіанти");
    }

    @Test
    public void testExceptionsAdj_16() throws IOException {
        assertHasError("альтернативну олігархічній порядку");
    }

    @Test
    public void testExceptionsAdj_17() throws IOException {
        assertEmptyMatch("найчисленнішими цеховики були саме в Грузії");
    }

    @Test
    public void testExceptionsAdj_18() throws IOException {
        assertEmptyMatch("Дефіцитною торгівля США є");
    }

    @Test
    public void testExceptionsAdj_19() throws IOException {
        assertEmptyMatch("Не менш виснажливою війна є і для ворога");
    }

    @Test
    public void testExceptionsAdj_20() throws IOException {
        assertEmptyMatch("Найнижчою частка таких є на Півдні");
    }

    @Test
    public void testExceptionsAdj_21() throws IOException {
        assertEmptyMatch("розвинутою Україну назвати важко");
    }

    @Test
    public void testExceptionsAdj_22() throws IOException {
        assertEmptyMatch("кількість визнаних недійсними бюлетенів");
    }

    @Test
    public void testExceptionsAdj_23() throws IOException {
        assertEmptyMatch("Слабшою критики вважають");
    }

    @Test
    public void testExceptionsAdj_24() throws IOException {
        assertEmptyMatch("найбільш райдужною перспектива членства в ЄС залишається");
    }

    @Test
    public void testExceptionsAdj_25() throws IOException {
        assertEmptyMatch("Однак безлюдним місто також не назвеш");
    }

    @Test
    public void testExceptionsAdj_26() throws IOException {
        assertEmptyMatch("Вагомим експерти називають той факт");
    }

    @Test
    public void testExceptionsAdj_27() throws IOException {
        assertEmptyMatch("такою ситуацію бачить сам");
    }

    @Test
    public void testExceptionsAdj_28() throws IOException {
        assertEmptyMatch("таким піднесеним президента не бачили давно");
    }

    @Test
    public void testExceptionsAdj_29() throws IOException {
        assertEmptyMatch("Найближчі півроку-рік");
    }

    @Test
    public void testExceptionsAdj_30() throws IOException {
        assertEmptyMatch("найближчих тиждень-два");
    }

    @Test
    public void testExceptionsAdj_31() throws IOException {
        assertEmptyMatch("протягом минулих травня-липня");
    }

    @Test
    public void testExceptionsAdj_32() throws IOException {
        assertEmptyMatch("Перші рік-два влада відбивалася");
    }

    @Test
    public void testExceptionsAdj_33() throws IOException {
        assertEmptyMatch("суперкризовими січнем–лютим");
    }

    @Test
    public void testExceptionsAdj_34() throws IOException {
        assertHasError("найближчі тиждень");
    }

    @Test
    public void testExceptionsPrepAdj_1() throws IOException {
        assertEmptyMatch("діє подібний до попереднього закон");
    }

    @Test
    public void testExceptionsPrepAdj_2() throws IOException {
        assertEmptyMatch("з відмінним від їхнього набором цінностей");
    }

    @Test
    public void testExceptionsPrepAdj_3() throws IOException {
        assertEmptyMatch("Про далеку від взірцевої поведінку");
    }

    @Test
    public void testExceptionsPrepAdj_4() throws IOException {
        assertEmptyMatch("нижчими від ринкових цінами");
    }

    @Test
    public void testExceptionsPrepAdj_5() throws IOException {
        assertEmptyMatch("протилежний до загальнодержавного процес");
    }

    @Test
    public void testExceptionsPrepAdj_6() throws IOException {
        assertEmptyMatch("Схожої з тамтешньою концепції");
    }

    @Test
    public void testExceptionsPrepAdj_7() throws IOException {
        assertEmptyMatch("відрізнялася від нинішньої ситуація");
    }

    @Test
    public void testExceptionsPrepAdj_8() throws IOException {
        assertEmptyMatch("відрізнялася б від нинішньої ситуація");
    }

    @Test
    public void testExceptionsPrepAdj_9() throws IOException {
        assertEmptyMatch("відрізнялося від російського способом");
    }

    @Test
    public void testExceptionsPrepAdj_10() throws IOException {
        assertHasError("асоціюється в нас із сучасною цивілізацію");
    }

    @Test
    public void testExceptionsPrepAdj_11() throws IOException {
        assertEmptyMatch("На відміну від європейських санкції США");
    }

    @Test
    public void testExceptionsPrepAdj_12() throws IOException {
        assertEmptyMatch("поряд з енергетичними Москва висувала");
    }

    @Test
    public void testExceptionsPrepAdj_13() throws IOException {
        assertEmptyMatch("тотожні із загальносоюзними герб і прапор");
    }

    @Test
    public void testExceptionsPrepAdj_14() throws IOException {
        assertEmptyMatch("чотири подібних до естонських звіти.");
    }

    @Test
    public void testExceptionsPrepAdj_15() throws IOException {
        assertEmptyMatch("порівняно з попереднім результат");
    }

    @Test
    public void testExceptionsPrepAdj_16() throws IOException {
        assertEmptyMatch("порівняно з російським рівень");
    }

    @Test
    public void testExceptionsPrepAdj_17() throws IOException {
        assertHasError("він є одним із найстаріший амфітеатрів");
    }

    @Test
    public void testExceptionsPrepAdj_18() throws IOException {
        assertHasError("подібний до попереднього закони");
    }

    @Test
    public void testExceptionsPrepAdj_19() throws IOException {
        assertHasError("порівняний з попереднім результатів");
    }

    @Test
    public void testExceptionsPrepAdj_20() throws IOException {
        assertHasError("Схожої з тамтешньою концепція");
    }

    @Test
    public void testExceptionsPrepAdj_21() throws IOException {
        assertHasError("протилежний до загальнодержавному процес");
    }

    @Test
    public void testExceptionsPrepAdj_22() throws IOException {
        assertHasError("вдалися до збройної боротьбі");
    }

    @Test
    public void testExceptionsPlural_1() throws IOException {
        assertEmptyMatch("на довгих півстоліття");
    }

    @Test
    public void testExceptionsPlural_2() throws IOException {
        assertEmptyMatch("цілих півмісяця");
    }

    @Test
    public void testExceptionsPlural_3() throws IOException {
        assertEmptyMatch("на довгих чверть століття");
    }

    @Test
    public void testExceptionsPlural_4() throws IOException {
        assertHasError("на довгих місяця");
    }

    @Test
    public void testExceptionsPlural_5() throws IOException {
        assertHasError("продукту, а просунути український фільми");
    }

    @Test
    public void testExceptionsPlural_6() throws IOException {
        assertEmptyMatch("щоб моїх маму й сестер");
    }

    @Test
    public void testExceptionsPlural_7() throws IOException {
        assertEmptyMatch("власними потом i кров’ю");
    }

    @Test
    public void testExceptionsPlural_8() throws IOException {
        assertEmptyMatch("Перші тиждень чи два");
    }

    @Test
    public void testExceptionsPlural_9() throws IOException {
        assertEmptyMatch("зазначені ім'я, прізвище та місто");
    }

    @Test
    public void testExceptionsPlural_10() throws IOException {
        assertEmptyMatch("Житомирська, Кіровоградська області");
    }

    @Test
    public void testExceptionsPlural_11() throws IOException {
        assertEmptyMatch("ані судова, ані правоохоронна системи");
    }

    @Test
    public void testExceptionsPlural_12() throws IOException {
        assertEmptyMatch("шиїтську та сунітську, а також курдську частини");
    }

    @Test
    public void testExceptionsPlural_13() throws IOException {
        assertEmptyMatch("Чорного і Азовського морів");
    }

    @Test
    public void testExceptionsPlural_14() throws IOException {
        assertEmptyMatch("називає й традиційні корупцію, «відкати», хабарі");
    }

    @Test
    public void testExceptionsPlural_15() throws IOException {
        assertEmptyMatch("державні Ощадбанк, «Укргазбанк»");
    }

    @Test
    public void testExceptionsPlural_16() throws IOException {
        assertEmptyMatch("коринфський з іонійським ордери");
    }

    @Test
    public void testExceptionsPlural_17() throws IOException {
        assertEmptyMatch("від однієї й другої сторін");
    }

    @Test
    public void testExceptionsPlural_18() throws IOException {
        assertEmptyMatch("під’їздить один, другий автобуси");
    }

    @Test
    public void testExceptionsPlural_19() throws IOException {
        assertEmptyMatch("зв'язаних ченця з черницею");
    }

    @Test
    public void testExceptionsPlural_20() throws IOException {
        assertEmptyMatch("на зарубаних матір з двома синами");
    }

    @Test
    public void testExceptionsPlural_21() throws IOException {
        assertEmptyMatch("повоєнні Австрія з Фінляндією");
    }

    @Test
    public void testExceptionsPlural_22() throws IOException {
        assertEmptyMatch("Опозиційні Андрієвський і Черников");
    }

    @Test
    public void testExceptionsPlural_23() throws IOException {
        assertEmptyMatch("директори навчальної та середньої шкіл");
    }

    @Test
    public void testExceptionsPlural_24() throws IOException {
        assertEmptyMatch("протягом минулих травня – липня");
    }

    @Test
    public void testExceptionsPlural_25() throws IOException {
        assertEmptyMatch("практично відсутні транспорт, гомінкі базари");
    }

    @Test
    public void testExceptionsPlural_26() throws IOException {
        assertEmptyMatch("ВАЖЛИВІ МОТОРИКА І ВІДЧУТТЯ РІВНОВАГИ");
    }

    @Test
    public void testExceptionsPlural_27() throws IOException {
        assertEmptyMatch("канонізовані Іоанн XXIII і Іван Павло II");
    }

    @Test
    public void testExceptionsPlural_28() throws IOException {
        assertEmptyMatch("у дво- й тривимірному форматах");
    }

    @Test
    public void testExceptionsPlural_29() throws IOException {
        assertEmptyMatch("Однак ні паровий, ні електричний двигуни не могли");
    }

    @Test
    public void testExceptionsPlural_30() throws IOException {
        assertEmptyMatch("сміттєпереробного і/або сміттєспалювального заводів");
    }

    @Test
    public void testExceptionsPlural_31() throws IOException {
        assertEmptyMatch("130-те (мінус вісім позицій порівняно з 2009-м) та 145-те місця");
    }

    @Test
    public void testExceptionsPlural_32() throws IOException {
        assertEmptyMatch("ні у методологічному, ні у практичному аспектах.");
    }

    @Test
    public void testExceptionsPlural_33() throws IOException {
        assertEmptyMatch("Хоч в англомовній, хоч в україномовній версіях");
    }

    @Test
    public void testExceptionsPlural_34() throws IOException {
        assertEmptyMatch("Большого та Маріїнського театрів");
    }

    @Test
    public void testExceptionsPlural_35() throws IOException {
        assertEmptyMatch("Пляжі 3, 4 і 5-ї категорій.");
    }

    @Test
    public void testExceptionsPlural_36() throws IOException {
        assertHasError("У львівській та київський Книгарнях");
    }

    @Test
    public void testExceptionsPlural_37() throws IOException {
        assertHasError("налякані Австрія з Фінляндію");
    }

    @Test
    public void testExceptionsPlural_38() throws IOException {
        assertHasError("Судячи з січневих продаж, 2009-й може стати");
    }

    @Test
    public void testExceptionsPlural_39() throws IOException {
        assertHasError("які наполягали на введені санкцій, будуть продовжуватися.");
    }

    @Test
    public void testExceptionsPlural_40() throws IOException {
        assertEmptyMatch("символізують творчий, оберігальний та руйнівний аспекти Вищої Сили");
    }

    @Test
    public void testExceptionsPlural_41() throws IOException {
        assertEmptyMatch("на місцевому, так і на центральному рівнях");
    }

    @Test
    public void testExceptionsPlural_42() throws IOException {
        assertEmptyMatch("передався повоєнним Відню та Парижу");
    }

    @Test
    public void testExceptionsPlural_43() throws IOException {
        assertEmptyMatch("найхарактерніші лояльність до влади й відданість місцевим лідерам.");
    }

    @Test
    public void testExceptionsPlural_44() throws IOException {
        assertEmptyMatch("і з першим, і з другим чоловіками");
    }

    @Test
    public void testExceptionsPlural_45() throws IOException {
        assertEmptyMatch("молодші Олександр Ірванець, Оксана Луцишина, Євгенія Кононенко");
    }

    @Test
    public void testExceptionsPlural_46() throws IOException {
        assertEmptyMatch("230 вчилися за старшинською і 120 за підстаршинською програмами");
    }

    @Test
    public void testExceptionsPluralConjAdv_1() throws IOException {
        assertEmptyMatch("уражені штаб ІДІЛ, а також збройний завод.");
    }

    @Test
    public void testExceptionsPluralConjAdv_2() throws IOException {
        assertEmptyMatch("в соціальному, а згодом і в економічному аспектах");
    }

    @Test
    public void testExceptionsPluralConjAdv_3() throws IOException {
        assertEmptyMatch("до апеляційного, а відтак і до конституційного судів");
    }

    @Test
    public void testExceptionsPluralConjAdv_4() throws IOException {
        assertEmptyMatch("У переносному та навіть у прямому сенсах слова");
    }

    @Test
    public void testExceptionsPluralConjAdv_5() throws IOException {
        assertEmptyMatch("в Чернівецькій і частково у Закарпатській областях");
    }

    @Test
    public void testExceptionsPluralConjAdv_6() throws IOException {
        assertEmptyMatch("парламентської, а згодом і президентської кампаній");
    }

    @Test
    public void testExceptionsPluralConjAdv_7() throws IOException {
        assertEmptyMatch("на західноєвропейському, а потім і на американському ринках");
    }

    @Test
    public void testExceptionsPluralConjAdv_8() throws IOException {
        assertEmptyMatch("навчався в реальному, потім у землемірному училищах");
    }

    @Test
    public void testExceptionsPluralConjAdv_9() throws IOException {
        assertHasError("для того, щоб пожвавити культурне середовища села");
    }

    @Test
    public void testExceptionsInsertPhrase_1() throws IOException {
        assertEmptyMatch("трагедію російського й, особливо, українського народів");
    }

    @Test
    public void testExceptionsInsertPhrase_2() throws IOException {
        assertEmptyMatch("Китай і, певною мірою, Росія зуміли поставити");
    }

    @Test
    public void testExceptionsInsertPhrase_3() throws IOException {
        assertHasError("що, однак, не змінюють загальної картин");
    }

    @Test
    public void testExceptionsInsertPhrase_4() throws IOException {
        assertHasError("про те, що в різних міста");
    }

    @Test
    public void testExceptionsInsertPhrase_5() throws IOException {
        assertHasError("Він додав, що у комунальних підприємства");
    }

    @Test
    public void testExceptionsInsertPhrase_6() throws IOException {
        assertHasError("наставник сказав, що на світовий першості");
    }

    @Test
    public void testExceptionsInsertPhrase_7() throws IOException {
        assertHasError("Аль-Каїда, і чи не найстрашніше терористичний об’єднання");
    }

    @Test
    public void testExceptionsInsertPhrase_8() throws IOException {
        assertHasError("буде очікувати, коли нова редакції");
    }

    @Test
    public void testExceptionsInsertPhrase_9() throws IOException {
        assertHasError("вважає Порошенко, одночасно закликаючи європейську коаліції");
    }

    @Test
    public void testExceptionsInsertPhrase_10() throws IOException {
        assertHasError("пішли вперед, хай і міліметровим кроками");
    }

    @Test
    public void testExceptionsInsertPhrase_11() throws IOException {
        assertHasError("Словом, у проблематиці подвійного громадянств");
    }

    @Test
    public void testExceptionsInsertPhrase_12() throws IOException {
        assertHasError("Правоохоронцями, зокрема, проведена масштабна операції");
    }

    @Test
    public void testExceptionsInsertPhrase_13() throws IOException {
        assertHasError("Він так і не прийняв односторонню капітуляції");
    }

    @Test
    public void testExceptionsInsertPhrase_14() throws IOException {
        assertHasError("Так, наприклад, косівським аматорами");
    }

    @Test
    public void testExceptionsInsertPhrase_15() throws IOException {
        assertHasError("Якщо третина чи навіть половинна населення");
    }

    @Test
    public void testExceptionsInsertPhrase_16() throws IOException {
        assertHasError("Думаю, це фейковий вкидання");
    }

    @Test
    public void testExceptionsInsertPhrase_17() throws IOException {
        assertHasError("Сьогодні, наприклад, часта машинобудування");
    }

    @Test
    public void testExceptionsInsertPhrase_18() throws IOException {
        assertEmptyMatch("латиську і, здається, молдавську поезії");
    }

    @Test
    public void testExceptionsInsertPhrase_19() throws IOException {
        assertEmptyMatch("в Житомирській чи, скажімо, Миколаївській областях");
    }

    @Test
    public void testPronouns_1() throws IOException {
        assertEmptyMatch("усі решта");
    }

    @Test
    public void testPronouns_2() throws IOException {
        assertEmptyMatch("єдину для всіх схему");
    }

    @Test
    public void testPronouns_3() throws IOException {
        assertEmptyMatch("без таких документів");
    }

    @Test
    public void testPronouns_4() throws IOException {
        assertEmptyMatch("згідно з якими африканцям");
    }

    @Test
    public void testPronouns_5() throws IOException {
        assertEmptyMatch("чиновників, яким доступ");
    }

    @Test
    public void testPronouns_6() throws IOException {
        assertEmptyMatch("так само");
    }

    @Test
    public void testPronouns_7() throws IOException {
        assertEmptyMatch("перед тим гарант");
    }

    @Test
    public void testPronouns_8() throws IOException {
        assertEmptyMatch("усього місяць тому");
    }

    @Test
    public void testPronouns_9() throws IOException {
        assertEmptyMatch("це мова сото");
    }

    @Test
    public void testPronouns_10() throws IOException {
        assertEmptyMatch("без якої сім’я не проживе");
    }

    @Test
    public void testPronouns_11() throws IOException {
        assertEmptyMatch("ВО «Свобода», лідер котрої Олег Тягрибок");
    }

    @Test
    public void testPronouns_12() throws IOException {
        assertEmptyMatch("стільки само свідків");
    }

    @Test
    public void testPronouns_13() throws IOException {
        assertEmptyMatch("що таке звук?");
    }

    @Test
    public void testPronouns_14() throws IOException {
        assertEmptyMatch("обстрілює один за одним охоронців.");
    }

    @Test
    public void testPronouns_15() throws IOException {
        assertEmptyMatch("повів сам військо");
    }

    @Test
    public void testPronouns_16() throws IOException {
        assertEmptyMatch("що ж таке геноцид");
    }

    @Test
    public void testPronouns_17() throws IOException {
        assertEmptyMatch("що воно таке еліта");
    }

    @Test
    public void testPronouns_18() throws IOException {
        assertEmptyMatch("Таких меншість.");
    }

    @Test
    public void testPronouns_19() throws IOException {
        assertEmptyMatch("той родом з Білорусі");
    }

    @Test
    public void testPronouns_20() throws IOException {
        assertEmptyMatch("в нашу Богом забуту Данину");
    }

    @Test
    public void testPronouns_21() throws IOException {
        assertEmptyMatch("той кібернетикою займається");
    }

    @Test
    public void testPronouns_22() throws IOException {
        assertEmptyMatch("такого світ ще не бачив");
    }

    @Test
    public void testPronouns_23() throws IOException {
        assertHasError("позицію у такій спосіб, — ділиться думками");
    }

    @Test
    public void testSpecialChars_1() throws IOException {
        assertEmptyMatch("зелений поді\u00ADум");
    }

    @Test
    public void testSpecialChars_2() throws IOException {
        assertHasError("зелений по\u00ADділка.");
    }

    @Test
    public void testSpecialChars_3() throws IOException {
        assertHasError("зе\u00ADлений поділка.");
    }
}
