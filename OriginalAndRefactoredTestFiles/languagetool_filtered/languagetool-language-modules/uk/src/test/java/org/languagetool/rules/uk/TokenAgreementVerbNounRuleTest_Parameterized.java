package org.languagetool.rules.uk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Ignore;
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

public class TokenAgreementVerbNounRuleTest_Parameterized extends AbstractRuleTest {

    @Before
    public void setUp() throws IOException {
        rule = new TokenAgreementVerbNounRule(TestTools.getMessages("uk"), lt.getLanguage());
    }

    @Test
    public void testRuleTPSuggestions_1_testMerged_1() throws IOException {
        String text = "перераховувати причин";
        AnalyzedSentence sent = lt.getAnalyzedSentence(text);
        RuleMatch[] match = rule.match(sent);
        assertEquals(1, match.length);
        assertEquals("перераховувати причин", text.substring(match[0].getFromPos(), match[0].getToPos()));
        assertEquals(Arrays.asList("перераховувати причинам", "перераховувати причинами", "перераховувати причини"), match[0].getSuggestedReplacements());
    }

    @Ignore
    @Test
    public void testRuleTn_N_Vinf_ADJ_1() throws IOException {
        assertEmptyMatch("Здатність ненавидіти прошита");
    }

    @Ignore
    @Test
    public void testRuleTn_N_Vinf_ADJ_2() throws IOException {
        assertEmptyMatch("рішення балотуватися продиктоване");
    }

    @Ignore
    @Test
    public void testRuleTn_N_Vinf_ADJ_3() throws IOException {
        assertEmptyMatch("Вони обманюватись раді");
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleTPSuggestions_1_4")
    public void testRuleTPSuggestions_1_4(String param1, String param2) throws IOException {
        assertHasError(param1, param2);
    }

    static public Stream<Arguments> Provider_testRuleTPSuggestions_1_4() {
        return Stream.of(arguments("зазнавати глибоке", "зазнавати глибокого"), arguments("сягнув би піку", "сягнув би піка"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleWithPart_2to4_7to9_9to10_10_15to16")
    public void testRuleWithPart_2to4_7to9_9to10_10_15to16(String param1) throws IOException {
        assertHasError(param1);
    }

    static public Stream<Arguments> Provider_testRuleWithPart_2to4_7to9_9to10_10_15to16() {
        return Stream.of(arguments("це не відбувається не державному рівні"), arguments("Фабрика Миколая вперше запрацювала Львові у 2001 році"), arguments("вміємо цьому зазвичай користуватися"), arguments("неопізнаний літаючи об’єкт"), arguments("знищила існуючи бази даних"), arguments("бажання постійно вчитися новому"), arguments("Кіно вчить вмінню простими словами"), arguments("Черга вчитися мистецтву мовчання"), arguments("у своєму виступі на конференції порадив української владі звернуть увагу"), arguments("навчитися цьому досить легко"), arguments("все досліджуйте і постійно навчайтеся новому."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleWithPart_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3_3_3_3_3_3_3_3to4_4_4_4_4_4_4_4_4_4_4_4_4_4_4to5_5_5_5_5_5_5_5_5_5_5_5to6_6_6_6_6_6_6_6_6_6_6_6to7_7_7_7_7_7_7_7_7_7to8_8_8_8_8_8_8_8_8to9_9_9_9_9_9_9_9to10_10_10_10_10_10_10to11_11_11_11_11_11_11_11to12_12_12_12_12_12_12to13_13_13_13_13_13_13to14_14_14_14_14_14_14to15_15_15_15_15to16_16_16_16_16to17_17_17_17_17_17to18_18_18_18_18_18to19_19_19_19_19_19to20_20_20_20to21_21_21to22_22_22to23_23_23to24_24_24to83")
    public void testRuleWithPart_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3_3_3_3_3_3_3_3to4_4_4_4_4_4_4_4_4_4_4_4_4_4_4to5_5_5_5_5_5_5_5_5_5_5_5to6_6_6_6_6_6_6_6_6_6_6_6to7_7_7_7_7_7_7_7_7_7to8_8_8_8_8_8_8_8_8to9_9_9_9_9_9_9_9to10_10_10_10_10_10_10to11_11_11_11_11_11_11_11to12_12_12_12_12_12_12to13_13_13_13_13_13_13to14_14_14_14_14_14_14to15_15_15_15_15to16_16_16_16_16to17_17_17_17_17_17to18_18_18_18_18_18to19_19_19_19_19_19to20_20_20_20to21_21_21to22_22_22to23_23_23to24_24_24to83(String param1) throws IOException {
        assertEmptyMatch(param1);
    }

    static public Stream<Arguments> Provider_testRuleWithPart_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3_3_3_3_3_3_3_3to4_4_4_4_4_4_4_4_4_4_4_4_4_4_4to5_5_5_5_5_5_5_5_5_5_5_5to6_6_6_6_6_6_6_6_6_6_6_6to7_7_7_7_7_7_7_7_7_7to8_8_8_8_8_8_8_8_8to9_9_9_9_9_9_9_9to10_10_10_10_10_10_10to11_11_11_11_11_11_11_11to12_12_12_12_12_12_12to13_13_13_13_13_13_13to14_14_14_14_14_14_14to15_15_15_15_15to16_16_16_16_16to17_17_17_17_17_17to18_18_18_18_18_18to19_19_19_19_19_19to20_20_20_20to21_21_21to22_22_22to23_23_23to24_24_24to83() {
        return Stream.of(arguments("Вінні прощалася й охоронці виводили їх"), arguments("Але працювати не те щоб складно"), arguments("не піднявся не те що Київ"), arguments("не піднявся не те, що Київ"), arguments("вкрадено державою"), arguments("я буду каву"), arguments("спроєктувати проект"), arguments("купив книгу"), arguments("позбавляло людину"), arguments("залишати межі"), arguments("залишаються джерелом"), arguments("відкидати атрибути"), arguments("належати людині"), arguments("належати руку"), arguments("забракло вмінь"), arguments("Може поменшати ентузіазму"), arguments("Може видатися парадоксальним твердження"), arguments("розподілятиметься пропорційно вкладеній праці"), arguments("зростатимуть прямо пропорційно україноненависництву"), arguments("рухатися далі власної історії"), arguments("запропоновано урядом"), arguments("мало часу"), arguments("з’явитися перед ним"), arguments("не було меблів"), arguments("оплачуватися повинна відповідно"), arguments("відрізнятись один від одного"), arguments("співпрацювати один із одним"), arguments("допомагати одне одному"), arguments("залучити інвестицій на 20—30 мільйонів"), arguments("збереться людей зо 200"), arguments("як боротися підприємцям"), arguments("захиститися неспроможні"), arguments("висміювати такого роду забобони"), arguments("вважається свого роду психологічним"), arguments("хто мітингуватиме таким чином"), arguments("уникнувши тим самим"), arguments("міг хитрістю змусити"), arguments("могли займатися структури"), arguments("могли б займатися структури"), arguments("мусить чимось перекрити"), arguments("довелося її розбирати"), arguments("повинні існувати такі"), arguments("робити здатна"), arguments("боротиметься кілька однопартійців"), arguments("вийшла 1987-го"), arguments("зігріває його серце"), arguments("дай Боже"), arguments("спробуймо йому відповісти"), arguments("прокинься Тарасе"), arguments("див. новинні"), arguments("повинен відбудватися процес"), arguments("виявилося нікому не потрібним"), arguments("покататися самому"), arguments("віддати-відрізати Донбас"), arguments("вона називалася Оперативний злам"), arguments("мене звати Ігор"), arguments("підклали дров"), arguments("наголосила політик"), arguments("тривав довгих десять раундів"), arguments("лежали всю дорогу"), arguments("мріяли все життя"), arguments("сподобалося гуляти"), arguments("належить пройтися"), arguments("стали каламутного кольору"), arguments("ні сіло ні впало Комітет держбезпеки"), arguments("звичайна, якщо не сказати слабка, людина"), arguments("займаючись кожен своїми справами"), arguments("підстрахуватися не зайве"), arguments("поназбиравши купу обіцянок"), arguments("пішов світ за очі"), arguments("тікати куди очі бачать"), arguments("Видно було також великий"), arguments("буде видно тільки супутники"), arguments("чути було лише стукіт"), arguments("було дуже чутно стукіт"), arguments("зеленій частині"), arguments("будь то буряки чи малина"), arguments("поліклініці не було де амбулаторні"), arguments("чесніше було б державний фонд"), arguments("Процвітати буде лише бізнес"), arguments("були б іншої думки"), arguments("має своїм неодмінним наслідком"), arguments("що є сил"), arguments("Конкурс був десь шість"), arguments("повна була образів"), arguments("відкрито було журнал"), arguments("треба буде ще склянку"), arguments("Не бачити вам цирку"), arguments("розсміявся йому в обличчя"), arguments("закружляли мені десь у тьмі"), arguments("ірже вам у вічі"), arguments("умоститися господареві на рамена"), arguments("прилетів йому від посла"), arguments("пробирається людині під шкіру"), arguments("їхав їй назустріч"), arguments("біжать йому навперейми"), arguments("Квапитися їй нікуди"), arguments("хворіти їй ніколи"), arguments("Жити родині нема де."), arguments("Евакуюватися нам не було куди"), arguments("нічим пишатися жителям"), arguments("куди подітися селянам"), arguments("у таких будиночках жити мешканцям"), arguments("як тепер жити нам"), arguments("сидіти б панові"), arguments("не було де амбулаторні карточки ставити"), arguments("маю тобі щось підказати"), arguments("вміємо цим зазвичай користуватися"), arguments("вони воліли мені якнайбільш ефективно допомогти"), arguments("воліли заворушень не допускати"), arguments("не втомлюються десятиріччями боротися Берлін і Венеція"), arguments("постарається таку двозначність усунути"), arguments("розпорядився частину зарплати примусово видавати"), arguments("не схотіли нам про це казати"), arguments("Самі респонденти пояснити причин не можуть"), arguments("довелося план «Б» застосовувати"), arguments("сподіваючися щось розтлумачити"), arguments("Резюмуючи політик наголосив"), arguments("пригадує посміхаючись Аскольд"), arguments("сидячи ціле життя"), arguments("Не претендуючи жодною мірою"), arguments("є кого згадувати"), arguments("має відбуватися ротація"), arguments("має також народитися власна ідея"), arguments("мали змогу оцінити відвідувачі"), arguments("має ж десь поміститися двигун"), arguments("дав трохи передихнути бізнесу"), arguments("дають змогу з комфортом мандрувати чотирьом пасажирам"), arguments("дали б змогу розвиватися національному"), arguments("дозволила на початку 1990-х узагалі виникнути такому інституту"), arguments("заважає і далі нестримно поширюватися багатьом міфам"), arguments("люблять у нас кричати панікери"), arguments("Почав різко зростати курс долара"), arguments("пропонує «об’єднатися патріотам»"), arguments("став формуватися прошарок"), arguments("ставимо розварюватися легко відтиснену"), arguments("не даючи виїхати ванатжівці"), arguments("даючи можливість висловлюватися радикалам"), arguments("дозволяючи рухатися російському"), arguments("готувала їсти хлопцям"), arguments("вкласти спати Маринку"), arguments("поклавши спати старого Якима"), arguments("заважають розвиватися погане управління, війна"), arguments("став все частіше згадуватися незвичайний наслідок"), arguments(" починає швидко жовтіти й опадати листя"), arguments("перестають діяти й розвиватися демократичні"), arguments("важко розібратися багатьом людям."), arguments("незручно займатися президентові"), arguments("пізно готуватися нам"), arguments("найлегше ігнорувати людям із категорій"), arguments("треба всіма силами берегти кожному"), arguments("треба дуже уважно прислухатися владі"), arguments("слід реагувати Америці"), arguments("слід з обережністю їсти людям"), arguments("варто сюди прилетіти людині"), arguments("неможливо засвоїти одній людині"), arguments("тяжче стало жити селянам"), arguments("приємно слухати вчителям"), arguments("повинно боротися суспільство"), arguments("слід реально готуватися суспільству"), arguments("змушені ночувати пасажири"), arguments("повинен усього добитися сам"), arguments("схильна лякати така пропаганда"), arguments("зацікавлена перейняти угорська сторона"), arguments("Пора дорослішати всім"), arguments("можливість висловитися серійному вбивці?"), arguments("рішення про можливість балотуватися Кучмі"), arguments("гріх уже зараз зайнятися Генеральній прокуратурі..."), arguments("готовність спілкуватися людини"), arguments("небажання вибачатися пов’язане з національною гордістю"), arguments("дозволивши розвалитись імперії"), arguments("зніматися йому доводилося рідко"), arguments("Гуляти мешканки гуртожитку тепер можуть"), arguments("працювати ці люди не вміють"), arguments("реагувати Майдан має"), arguments("працювати українці будуть"), arguments("влаштуватися їй не вдається"), arguments("працювати цьому політикові доводиться"), arguments("Робити прогнозів не буду"), arguments("панькатися наміру не має"), arguments("було ввезено тракторів"), arguments("літати тобі не можна"), arguments("Порозумітися обом сторонам було важко"), arguments("втихомирюватися нам зарано"), arguments("Зупинятися мені вже не можна"), arguments("працювати правоохоронцям складно"), arguments("працювати правоохоронцям досить складно"), arguments("звертатися пенсіонерам не потрібно"), arguments("Їхати мені було не страшно"), arguments("замислитися нащадки повинні"), arguments("працювати студенти готові"), arguments("платити Рига згодна"), arguments("Владі не потрібен мир, хоча і війну вести вона не здатна."), arguments("Заспокоювати стали найагресивнішого"), arguments("платити доведеться повну вартість"), arguments("закінчити цей огляд хочеться словами"), arguments("прийшов Тарас"), arguments("було пасмо"), arguments("сміялися смішні гієни"), arguments("в мені наростали впевненість і сила"), arguments("де є якість"), arguments("в усіх країнах є міфи"), arguments("звалося Подєбради"), arguments("тренувалися годину"), arguments("відбудуться наступного дня"), arguments("їхав цілу ніч"), arguments("сколихнула минулого року"), arguments("збираються цього вечора"), arguments("чекати годинами"), arguments("спостерігається останнім часом"), arguments("відійшли метрів п'ять"), arguments("публікується кожні два роки"), arguments("зірватися всі ці 27 років"), arguments("відбувся минулої неділі"), arguments("закінчилося 18-го ввечері"), arguments("не знімався останні 10 років"), arguments("розпочнеться того ж дня"), arguments("помер цього вересня"), arguments("з’являться наступного ранку на суд"), arguments("мучитися три з половиною роки"), arguments("буде стояти двадцять і три роки"), arguments("попрацювала місяць-півтора"), arguments("сніг не тане всю зиму"), arguments("вщухнуть протягом кількох днів"), arguments("побутувала одночасно двома чи трьома мовами"), arguments("працює більшу частину часу"), arguments("які стартували цього та минулого року"), arguments("не мав меблів"), arguments("не повинен пропускати жодного звуку"), arguments("не став витрачати грошей"), arguments("казала цього не робити"), arguments("не повинно перевищувати граничної величини"), arguments("здаватися коаліціанти не збираються"), arguments("не існувало конкуренції"), arguments("не можна зберігати ілюзій"), arguments("скільки отримує грошей"), arguments("скільки буде людей"), arguments("трохи маю контактів"), arguments("скільки загалом здійснили постановок"), arguments("стільки заплановано постановок"), arguments("Багато в пресі з’явилося публікацій"), arguments("небагато надходить книжок"), arguments("ніж поставили стільців"), arguments("Росія будує трубопроводів більше"), arguments("не стане сили"), arguments("нарубав лісу"), arguments("змалював дивовижної краси церкву"), arguments("Піднявся страшенної сили крижаний шторм"), arguments("поводиться дивним чином"), arguments("триває повним ходом."));
    }
}
