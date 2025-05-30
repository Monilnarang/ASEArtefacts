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

public class TokenAgreementVerbNounRuleTest_Purified extends AbstractRuleTest {

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

    @Test
    public void testRuleTPSuggestions_4() throws IOException {
        assertHasError("зазнавати глибоке", "зазнавати глибокого");
    }

    @Test
    public void testRuleWithPart_1() throws IOException {
        assertHasError("сягнув би піку", "сягнув би піка");
    }

    @Test
    public void testRuleWithPart_2() throws IOException {
        assertHasError("це не відбувається не державному рівні");
    }

    @Test
    public void testRuleWithPart_3() throws IOException {
        assertEmptyMatch("Вінні прощалася й охоронці виводили їх");
    }

    @Test
    public void testRuleWithPart_4() throws IOException {
        assertEmptyMatch("Але працювати не те щоб складно");
    }

    @Test
    public void testRuleWithPart_5() throws IOException {
        assertEmptyMatch("не піднявся не те що Київ");
    }

    @Test
    public void testRuleWithPart_6() throws IOException {
        assertEmptyMatch("не піднявся не те, що Київ");
    }

    @Test
    public void testRuleTN_1() throws IOException {
        assertEmptyMatch("вкрадено державою");
    }

    @Test
    public void testRuleTN_2() throws IOException {
        assertEmptyMatch("я буду каву");
    }

    @Test
    public void testRuleTN_3() throws IOException {
        assertEmptyMatch("спроєктувати проект");
    }

    @Test
    public void testRuleTN_4() throws IOException {
        assertEmptyMatch("купив книгу");
    }

    @Test
    public void testRuleTN_5() throws IOException {
        assertEmptyMatch("позбавляло людину");
    }

    @Test
    public void testRuleTN_6() throws IOException {
        assertEmptyMatch("залишати межі");
    }

    @Test
    public void testRuleTN_7() throws IOException {
        assertEmptyMatch("залишаються джерелом");
    }

    @Test
    public void testRuleTN_8() throws IOException {
        assertEmptyMatch("відкидати атрибути");
    }

    @Test
    public void testRuleTN_9() throws IOException {
        assertEmptyMatch("належати людині");
    }

    @Test
    public void testRuleTN_10() throws IOException {
        assertEmptyMatch("належати руку");
    }

    @Test
    public void testRuleTN_11() throws IOException {
        assertEmptyMatch("забракло вмінь");
    }

    @Test
    public void testRuleTN_12() throws IOException {
        assertEmptyMatch("Може поменшати ентузіазму");
    }

    @Test
    public void testRuleTN_13() throws IOException {
        assertEmptyMatch("Може видатися парадоксальним твердження");
    }

    @Test
    public void testRuleTN_14() throws IOException {
        assertEmptyMatch("розподілятиметься пропорційно вкладеній праці");
    }

    @Test
    public void testRuleTN_15() throws IOException {
        assertEmptyMatch("зростатимуть прямо пропорційно україноненависництву");
    }

    @Test
    public void testRuleTN_16() throws IOException {
        assertEmptyMatch("рухатися далі власної історії");
    }

    @Test
    public void testRuleTN_17() throws IOException {
        assertEmptyMatch("запропоновано урядом");
    }

    @Test
    public void testRuleTN_18() throws IOException {
        assertEmptyMatch("мало часу");
    }

    @Test
    public void testRuleTN_19() throws IOException {
        assertEmptyMatch("з’явитися перед ним");
    }

    @Test
    public void testRuleTN_20() throws IOException {
        assertEmptyMatch("не було меблів");
    }

    @Test
    public void testRuleTN_21() throws IOException {
        assertEmptyMatch("оплачуватися повинна відповідно");
    }

    @Test
    public void testRuleTN_22() throws IOException {
        assertEmptyMatch("відрізнятись один від одного");
    }

    @Test
    public void testRuleTN_23() throws IOException {
        assertEmptyMatch("співпрацювати один із одним");
    }

    @Test
    public void testRuleTN_24() throws IOException {
        assertEmptyMatch("допомагати одне одному");
    }

    @Test
    public void testRuleTN_25() throws IOException {
        assertEmptyMatch("залучити інвестицій на 20—30 мільйонів");
    }

    @Test
    public void testRuleTN_26() throws IOException {
        assertEmptyMatch("збереться людей зо 200");
    }

    @Test
    public void testRuleTN_27() throws IOException {
        assertEmptyMatch("як боротися підприємцям");
    }

    @Test
    public void testRuleTN_28() throws IOException {
        assertEmptyMatch("захиститися неспроможні");
    }

    @Test
    public void testRuleTN_29() throws IOException {
        assertEmptyMatch("висміювати такого роду забобони");
    }

    @Test
    public void testRuleTN_30() throws IOException {
        assertEmptyMatch("вважається свого роду психологічним");
    }

    @Test
    public void testRuleTN_31() throws IOException {
        assertEmptyMatch("хто мітингуватиме таким чином");
    }

    @Test
    public void testRuleTN_32() throws IOException {
        assertEmptyMatch("уникнувши тим самим");
    }

    @Test
    public void testRuleTN_33() throws IOException {
        assertEmptyMatch("міг хитрістю змусити");
    }

    @Test
    public void testRuleTN_34() throws IOException {
        assertEmptyMatch("могли займатися структури");
    }

    @Test
    public void testRuleTN_35() throws IOException {
        assertEmptyMatch("могли б займатися структури");
    }

    @Test
    public void testRuleTN_36() throws IOException {
        assertEmptyMatch("мусить чимось перекрити");
    }

    @Test
    public void testRuleTN_37() throws IOException {
        assertEmptyMatch("довелося її розбирати");
    }

    @Test
    public void testRuleTN_38() throws IOException {
        assertEmptyMatch("повинні існувати такі");
    }

    @Test
    public void testRuleTN_39() throws IOException {
        assertEmptyMatch("робити здатна");
    }

    @Test
    public void testRuleTN_40() throws IOException {
        assertEmptyMatch("боротиметься кілька однопартійців");
    }

    @Test
    public void testRuleTN_41() throws IOException {
        assertEmptyMatch("вийшла 1987-го");
    }

    @Test
    public void testRuleTN_42() throws IOException {
        assertEmptyMatch("зігріває його серце");
    }

    @Test
    public void testRuleTN_43() throws IOException {
        assertEmptyMatch("дай Боже");
    }

    @Test
    public void testRuleTN_44() throws IOException {
        assertEmptyMatch("спробуймо йому відповісти");
    }

    @Test
    public void testRuleTN_45() throws IOException {
        assertEmptyMatch("прокинься Тарасе");
    }

    @Test
    public void testRuleTN_46() throws IOException {
        assertEmptyMatch("див. новинні");
    }

    @Test
    public void testRuleTN_47() throws IOException {
        assertEmptyMatch("повинен відбудватися процес");
    }

    @Test
    public void testRuleTN_48() throws IOException {
        assertEmptyMatch("виявилося нікому не потрібним");
    }

    @Test
    public void testRuleTN_49() throws IOException {
        assertEmptyMatch("покататися самому");
    }

    @Test
    public void testRuleTN_50() throws IOException {
        assertEmptyMatch("віддати-відрізати Донбас");
    }

    @Test
    public void testRuleTN_51() throws IOException {
        assertEmptyMatch("вона називалася Оперативний злам");
    }

    @Test
    public void testRuleTN_52() throws IOException {
        assertEmptyMatch("мене звати Ігор");
    }

    @Test
    public void testRuleTN_53() throws IOException {
        assertEmptyMatch("підклали дров");
    }

    @Test
    public void testRuleTN_54() throws IOException {
        assertEmptyMatch("наголосила політик");
    }

    @Test
    public void testRuleTN_55() throws IOException {
        assertEmptyMatch("тривав довгих десять раундів");
    }

    @Test
    public void testRuleTN_56() throws IOException {
        assertEmptyMatch("лежали всю дорогу");
    }

    @Test
    public void testRuleTN_57() throws IOException {
        assertEmptyMatch("мріяли все життя");
    }

    @Test
    public void testRuleTN_58() throws IOException {
        assertEmptyMatch("сподобалося гуляти");
    }

    @Test
    public void testRuleTN_59() throws IOException {
        assertEmptyMatch("належить пройтися");
    }

    @Test
    public void testRuleTN_60() throws IOException {
        assertEmptyMatch("стали каламутного кольору");
    }

    @Test
    public void testRuleTN_61() throws IOException {
        assertEmptyMatch("ні сіло ні впало Комітет держбезпеки");
    }

    @Test
    public void testRuleTN_62() throws IOException {
        assertEmptyMatch("звичайна, якщо не сказати слабка, людина");
    }

    @Test
    public void testRuleTN_63() throws IOException {
        assertEmptyMatch("займаючись кожен своїми справами");
    }

    @Test
    public void testRuleTN_64() throws IOException {
        assertEmptyMatch("підстрахуватися не зайве");
    }

    @Test
    public void testRuleTN_65() throws IOException {
        assertEmptyMatch("поназбиравши купу обіцянок");
    }

    @Test
    public void testRuleTN_66() throws IOException {
        assertEmptyMatch("пішов світ за очі");
    }

    @Test
    public void testRuleTN_67() throws IOException {
        assertEmptyMatch("тікати куди очі бачать");
    }

    @Test
    public void testRuleTN_68() throws IOException {
        assertEmptyMatch("Видно було також великий");
    }

    @Test
    public void testRuleTN_69() throws IOException {
        assertEmptyMatch("буде видно тільки супутники");
    }

    @Test
    public void testRuleTN_70() throws IOException {
        assertEmptyMatch("чути було лише стукіт");
    }

    @Test
    public void testRuleTN_71() throws IOException {
        assertEmptyMatch("було дуже чутно стукіт");
    }

    @Test
    public void testRuleTN_72() throws IOException {
        assertEmptyMatch("зеленій частині");
    }

    @Test
    public void testRuleTN_73() throws IOException {
        assertEmptyMatch("будь то буряки чи малина");
    }

    @Test
    public void testRuleTN_74() throws IOException {
        assertEmptyMatch("поліклініці не було де амбулаторні");
    }

    @Test
    public void testRuleTN_75() throws IOException {
        assertEmptyMatch("чесніше було б державний фонд");
    }

    @Test
    public void testRuleTN_76() throws IOException {
        assertEmptyMatch("Процвітати буде лише бізнес");
    }

    @Test
    public void testRuleTN_77() throws IOException {
        assertEmptyMatch("були б іншої думки");
    }

    @Test
    public void testRuleTN_78() throws IOException {
        assertEmptyMatch("має своїм неодмінним наслідком");
    }

    @Test
    public void testRuleTN_79() throws IOException {
        assertEmptyMatch("що є сил");
    }

    @Test
    public void testRuleTN_80() throws IOException {
        assertEmptyMatch("Конкурс був десь шість");
    }

    @Test
    public void testRuleTN_81() throws IOException {
        assertEmptyMatch("повна була образів");
    }

    @Test
    public void testRuleTN_82() throws IOException {
        assertEmptyMatch("відкрито було журнал");
    }

    @Test
    public void testRuleTN_83() throws IOException {
        assertEmptyMatch("треба буде ще склянку");
    }

    @Test
    public void testRuleTnVdav_1() throws IOException {
        assertEmptyMatch("Не бачити вам цирку");
    }

    @Test
    public void testRuleTnVdav_2() throws IOException {
        assertEmptyMatch("розсміявся йому в обличчя");
    }

    @Test
    public void testRuleTnVdav_3() throws IOException {
        assertEmptyMatch("закружляли мені десь у тьмі");
    }

    @Test
    public void testRuleTnVdav_4() throws IOException {
        assertEmptyMatch("ірже вам у вічі");
    }

    @Test
    public void testRuleTnVdav_5() throws IOException {
        assertEmptyMatch("умоститися господареві на рамена");
    }

    @Test
    public void testRuleTnVdav_6() throws IOException {
        assertEmptyMatch("прилетів йому від посла");
    }

    @Test
    public void testRuleTnVdav_7() throws IOException {
        assertEmptyMatch("пробирається людині під шкіру");
    }

    @Test
    public void testRuleTnVdav_8() throws IOException {
        assertEmptyMatch("їхав їй назустріч");
    }

    @Test
    public void testRuleTnVdav_9() throws IOException {
        assertEmptyMatch("біжать йому навперейми");
    }

    @Test
    public void testRuleTnVdav_10() throws IOException {
        assertHasError("Фабрика Миколая вперше запрацювала Львові у 2001 році");
    }

    @Test
    public void testRuleTnVdav_11() throws IOException {
        assertEmptyMatch("Квапитися їй нікуди");
    }

    @Test
    public void testRuleTnVdav_12() throws IOException {
        assertEmptyMatch("хворіти їй ніколи");
    }

    @Test
    public void testRuleTnVdav_13() throws IOException {
        assertEmptyMatch("Жити родині нема де.");
    }

    @Test
    public void testRuleTnVdav_14() throws IOException {
        assertEmptyMatch("Евакуюватися нам не було куди");
    }

    @Test
    public void testRuleTnVdav_15() throws IOException {
        assertEmptyMatch("нічим пишатися жителям");
    }

    @Test
    public void testRuleTnVdav_16() throws IOException {
        assertEmptyMatch("куди подітися селянам");
    }

    @Test
    public void testRuleTnVdav_17() throws IOException {
        assertEmptyMatch("у таких будиночках жити мешканцям");
    }

    @Test
    public void testRuleTnVdav_18() throws IOException {
        assertEmptyMatch("як тепер жити нам");
    }

    @Test
    public void testRuleTnVdav_19() throws IOException {
        assertEmptyMatch("сидіти б панові");
    }

    @Test
    public void testRuleTnVdav_20() throws IOException {
        assertEmptyMatch("не було де амбулаторні карточки ставити");
    }

    @Test
    public void testRuleTn_V_N_Vinf_1() throws IOException {
        assertEmptyMatch("маю тобі щось підказати");
    }

    @Test
    public void testRuleTn_V_N_Vinf_2() throws IOException {
        assertEmptyMatch("вміємо цим зазвичай користуватися");
    }

    @Test
    public void testRuleTn_V_N_Vinf_3() throws IOException {
        assertHasError("вміємо цьому зазвичай користуватися");
    }

    @Test
    public void testRuleTn_V_N_Vinf_4() throws IOException {
        assertEmptyMatch("вони воліли мені якнайбільш ефективно допомогти");
    }

    @Test
    public void testRuleTn_V_N_Vinf_5() throws IOException {
        assertEmptyMatch("воліли заворушень не допускати");
    }

    @Test
    public void testRuleTn_V_N_Vinf_6() throws IOException {
        assertEmptyMatch("не втомлюються десятиріччями боротися Берлін і Венеція");
    }

    @Test
    public void testRuleTn_V_N_Vinf_7() throws IOException {
        assertEmptyMatch("постарається таку двозначність усунути");
    }

    @Test
    public void testRuleTn_V_N_Vinf_8() throws IOException {
        assertEmptyMatch("розпорядився частину зарплати примусово видавати");
    }

    @Test
    public void testRuleTn_V_N_Vinf_9() throws IOException {
        assertEmptyMatch("не схотіли нам про це казати");
    }

    @Test
    public void testRuleTn_V_N_Vinf_10() throws IOException {
        assertEmptyMatch("Самі респонденти пояснити причин не можуть");
    }

    @Test
    public void testRuleTn_V_N_Vinf_11() throws IOException {
        assertEmptyMatch("довелося план «Б» застосовувати");
    }

    @Test
    public void testRuleTn_V_N_Vinf_12() throws IOException {
        assertEmptyMatch("сподіваючися щось розтлумачити");
    }

    @Test
    public void testRuleTn_V_N_Vinf_13() throws IOException {
        assertEmptyMatch("Резюмуючи політик наголосив");
    }

    @Test
    public void testRuleTn_V_N_Vinf_14() throws IOException {
        assertEmptyMatch("пригадує посміхаючись Аскольд");
    }

    @Test
    public void testRuleTn_V_N_Vinf_15() throws IOException {
        assertHasError("неопізнаний літаючи об’єкт");
    }

    @Test
    public void testRuleTn_V_N_Vinf_16() throws IOException {
        assertHasError("знищила існуючи бази даних");
    }

    @Test
    public void testRuleTn_V_N_Vinf_17() throws IOException {
        assertEmptyMatch("сидячи ціле життя");
    }

    @Test
    public void testRuleTn_V_N_Vinf_18() throws IOException {
        assertEmptyMatch("Не претендуючи жодною мірою");
    }

    @Test
    public void testRuleTn_V_N_Vinf_19() throws IOException {
        assertEmptyMatch("є кого згадувати");
    }

    @Test
    public void testRuleTn_V_Vinf_N_1() throws IOException {
        assertEmptyMatch("має відбуватися ротація");
    }

    @Test
    public void testRuleTn_V_Vinf_N_2() throws IOException {
        assertEmptyMatch("має також народитися власна ідея");
    }

    @Test
    public void testRuleTn_V_Vinf_N_3() throws IOException {
        assertEmptyMatch("мали змогу оцінити відвідувачі");
    }

    @Test
    public void testRuleTn_V_Vinf_N_4() throws IOException {
        assertEmptyMatch("має ж десь поміститися двигун");
    }

    @Test
    public void testRuleTn_V_Vinf_N_5() throws IOException {
        assertEmptyMatch("дав трохи передихнути бізнесу");
    }

    @Test
    public void testRuleTn_V_Vinf_N_6() throws IOException {
        assertEmptyMatch("дають змогу з комфортом мандрувати чотирьом пасажирам");
    }

    @Test
    public void testRuleTn_V_Vinf_N_7() throws IOException {
        assertEmptyMatch("дали б змогу розвиватися національному");
    }

    @Test
    public void testRuleTn_V_Vinf_N_8() throws IOException {
        assertEmptyMatch("дозволила на початку 1990-х узагалі виникнути такому інституту");
    }

    @Test
    public void testRuleTn_V_Vinf_N_9() throws IOException {
        assertEmptyMatch("заважає і далі нестримно поширюватися багатьом міфам");
    }

    @Test
    public void testRuleTn_V_Vinf_N_10() throws IOException {
        assertEmptyMatch("люблять у нас кричати панікери");
    }

    @Test
    public void testRuleTn_V_Vinf_N_11() throws IOException {
        assertEmptyMatch("Почав різко зростати курс долара");
    }

    @Test
    public void testRuleTn_V_Vinf_N_12() throws IOException {
        assertEmptyMatch("пропонує «об’єднатися патріотам»");
    }

    @Test
    public void testRuleTn_V_Vinf_N_13() throws IOException {
        assertEmptyMatch("став формуватися прошарок");
    }

    @Test
    public void testRuleTn_V_Vinf_N_14() throws IOException {
        assertEmptyMatch("ставимо розварюватися легко відтиснену");
    }

    @Test
    public void testRuleTn_V_Vinf_N_15() throws IOException {
        assertEmptyMatch("не даючи виїхати ванатжівці");
    }

    @Test
    public void testRuleTn_V_Vinf_N_16() throws IOException {
        assertEmptyMatch("даючи можливість висловлюватися радикалам");
    }

    @Test
    public void testRuleTn_V_Vinf_N_17() throws IOException {
        assertEmptyMatch("дозволяючи рухатися російському");
    }

    @Test
    public void testRuleTn_V_Vinf_N_18() throws IOException {
        assertEmptyMatch("готувала їсти хлопцям");
    }

    @Test
    public void testRuleTn_V_Vinf_N_19() throws IOException {
        assertEmptyMatch("вкласти спати Маринку");
    }

    @Test
    public void testRuleTn_V_Vinf_N_20() throws IOException {
        assertEmptyMatch("поклавши спати старого Якима");
    }

    @Test
    public void testRuleTn_V_Vinf_N_21() throws IOException {
        assertEmptyMatch("заважають розвиватися погане управління, війна");
    }

    @Test
    public void testRuleTn_V_Vinf_N_22() throws IOException {
        assertEmptyMatch("став все частіше згадуватися незвичайний наслідок");
    }

    @Test
    public void testRuleTn_V_Vinf_N_23() throws IOException {
        assertEmptyMatch(" починає швидко жовтіти й опадати листя");
    }

    @Test
    public void testRuleTn_V_Vinf_N_24() throws IOException {
        assertEmptyMatch("перестають діяти й розвиватися демократичні");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_1() throws IOException {
        assertEmptyMatch("важко розібратися багатьом людям.");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_2() throws IOException {
        assertEmptyMatch("незручно займатися президентові");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_3() throws IOException {
        assertEmptyMatch("пізно готуватися нам");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_4() throws IOException {
        assertEmptyMatch("найлегше ігнорувати людям із категорій");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_5() throws IOException {
        assertEmptyMatch("треба всіма силами берегти кожному");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_6() throws IOException {
        assertEmptyMatch("треба дуже уважно прислухатися владі");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_7() throws IOException {
        assertEmptyMatch("слід реагувати Америці");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_8() throws IOException {
        assertEmptyMatch("слід з обережністю їсти людям");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_9() throws IOException {
        assertEmptyMatch("варто сюди прилетіти людині");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_10() throws IOException {
        assertEmptyMatch("неможливо засвоїти одній людині");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_11() throws IOException {
        assertEmptyMatch("тяжче стало жити селянам");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_12() throws IOException {
        assertEmptyMatch("приємно слухати вчителям");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_13() throws IOException {
        assertEmptyMatch("повинно боротися суспільство");
    }

    @Test
    public void testRuleTn_ADV_Vinf_N_14() throws IOException {
        assertEmptyMatch("слід реально готуватися суспільству");
    }

    @Test
    public void testRuleTn_ADJ_Vinf_N_1() throws IOException {
        assertEmptyMatch("змушені ночувати пасажири");
    }

    @Test
    public void testRuleTn_ADJ_Vinf_N_2() throws IOException {
        assertEmptyMatch("повинен усього добитися сам");
    }

    @Test
    public void testRuleTn_ADJ_Vinf_N_3() throws IOException {
        assertEmptyMatch("схильна лякати така пропаганда");
    }

    @Test
    public void testRuleTn_ADJ_Vinf_N_4() throws IOException {
        assertEmptyMatch("зацікавлена перейняти угорська сторона");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_1() throws IOException {
        assertEmptyMatch("Пора дорослішати всім");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_2() throws IOException {
        assertEmptyMatch("можливість висловитися серійному вбивці?");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_3() throws IOException {
        assertEmptyMatch("рішення про можливість балотуватися Кучмі");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_4() throws IOException {
        assertEmptyMatch("гріх уже зараз зайнятися Генеральній прокуратурі...");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_5() throws IOException {
        assertEmptyMatch("готовність спілкуватися людини");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_6() throws IOException {
        assertEmptyMatch("небажання вибачатися пов’язане з національною гордістю");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_7() throws IOException {
        assertHasError("бажання постійно вчитися новому");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_8() throws IOException {
        assertHasError("Кіно вчить вмінню простими словами");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_9() throws IOException {
        assertHasError("Черга вчитися мистецтву мовчання");
    }

    @Test
    public void testRuleTn_NOUN_Vinf_N_10() throws IOException {
        assertHasError("у своєму виступі на конференції порадив української владі звернуть увагу");
    }

    @Test
    public void testRuleTn_Vinf_N_V_1() throws IOException {
        assertEmptyMatch("дозволивши розвалитись імперії");
    }

    @Test
    public void testRuleTn_Vinf_N_V_2() throws IOException {
        assertEmptyMatch("зніматися йому доводилося рідко");
    }

    @Test
    public void testRuleTn_Vinf_N_V_3() throws IOException {
        assertEmptyMatch("Гуляти мешканки гуртожитку тепер можуть");
    }

    @Test
    public void testRuleTn_Vinf_N_V_4() throws IOException {
        assertEmptyMatch("працювати ці люди не вміють");
    }

    @Test
    public void testRuleTn_Vinf_N_V_5() throws IOException {
        assertEmptyMatch("реагувати Майдан має");
    }

    @Test
    public void testRuleTn_Vinf_N_V_6() throws IOException {
        assertEmptyMatch("працювати українці будуть");
    }

    @Test
    public void testRuleTn_Vinf_N_V_7() throws IOException {
        assertEmptyMatch("влаштуватися їй не вдається");
    }

    @Test
    public void testRuleTn_Vinf_N_V_8() throws IOException {
        assertEmptyMatch("працювати цьому політикові доводиться");
    }

    @Test
    public void testRuleTn_Vinf_N_V_9() throws IOException {
        assertEmptyMatch("Робити прогнозів не буду");
    }

    @Test
    public void testRuleTn_Vinf_N_V_10() throws IOException {
        assertEmptyMatch("панькатися наміру не має");
    }

    @Test
    public void testRuleTn_Vinf_N_V_11() throws IOException {
        assertEmptyMatch("було ввезено тракторів");
    }

    @Test
    public void testRuleTn_Vinf_N_ADV_1() throws IOException {
        assertEmptyMatch("літати тобі не можна");
    }

    @Test
    public void testRuleTn_Vinf_N_ADV_2() throws IOException {
        assertEmptyMatch("Порозумітися обом сторонам було важко");
    }

    @Test
    public void testRuleTn_Vinf_N_ADV_3() throws IOException {
        assertEmptyMatch("втихомирюватися нам зарано");
    }

    @Test
    public void testRuleTn_Vinf_N_ADV_4() throws IOException {
        assertEmptyMatch("Зупинятися мені вже не можна");
    }

    @Test
    public void testRuleTn_Vinf_N_ADV_5() throws IOException {
        assertEmptyMatch("працювати правоохоронцям складно");
    }

    @Test
    public void testRuleTn_Vinf_N_ADV_6() throws IOException {
        assertEmptyMatch("працювати правоохоронцям досить складно");
    }

    @Test
    public void testRuleTn_Vinf_N_ADV_7() throws IOException {
        assertEmptyMatch("звертатися пенсіонерам не потрібно");
    }

    @Test
    public void testRuleTn_Vinf_N_ADV_8() throws IOException {
        assertEmptyMatch("Їхати мені було не страшно");
    }

    @Test
    public void testRuleTn_Vinf_N_ADV_9() throws IOException {
        assertHasError("навчитися цьому досить легко");
    }

    @Test
    public void testRuleTn_Vinf_N_ADJ_1() throws IOException {
        assertEmptyMatch("замислитися нащадки повинні");
    }

    @Test
    public void testRuleTn_Vinf_N_ADJ_2() throws IOException {
        assertEmptyMatch("працювати студенти готові");
    }

    @Test
    public void testRuleTn_Vinf_N_ADJ_3() throws IOException {
        assertEmptyMatch("платити Рига згодна");
    }

    @Test
    public void testRuleTn_Vinf_N_ADJ_4() throws IOException {
        assertEmptyMatch("Владі не потрібен мир, хоча і війну вести вона не здатна.");
    }

    @Test
    public void testRuleTn_Vinf_V_N_1() throws IOException {
        assertEmptyMatch("Заспокоювати стали найагресивнішого");
    }

    @Test
    public void testRuleTn_Vinf_V_N_2() throws IOException {
        assertEmptyMatch("платити доведеться повну вартість");
    }

    @Test
    public void testRuleTn_Vinf_V_N_3() throws IOException {
        assertEmptyMatch("закінчити цей огляд хочеться словами");
    }

    @Test
    public void testRuleTn_Vinf_V_N_4() throws IOException {
        assertHasError("все досліджуйте і постійно навчайтеся новому.");
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

    @Test
    public void testRuleTNvNaz_1() throws IOException {
        assertEmptyMatch("прийшов Тарас");
    }

    @Test
    public void testRuleTNvNaz_2() throws IOException {
        assertEmptyMatch("було пасмо");
    }

    @Test
    public void testRuleTNvNaz_3() throws IOException {
        assertEmptyMatch("сміялися смішні гієни");
    }

    @Test
    public void testRuleTNvNaz_4() throws IOException {
        assertEmptyMatch("в мені наростали впевненість і сила");
    }

    @Test
    public void testRuleTNvNaz_5() throws IOException {
        assertEmptyMatch("де є якість");
    }

    @Test
    public void testRuleTNvNaz_6() throws IOException {
        assertEmptyMatch("в усіх країнах є міфи");
    }

    @Test
    public void testRuleTNvNaz_7() throws IOException {
        assertEmptyMatch("звалося Подєбради");
    }

    @Test
    public void testRuleTNTime_1() throws IOException {
        assertEmptyMatch("тренувалися годину");
    }

    @Test
    public void testRuleTNTime_2() throws IOException {
        assertEmptyMatch("відбудуться наступного дня");
    }

    @Test
    public void testRuleTNTime_3() throws IOException {
        assertEmptyMatch("їхав цілу ніч");
    }

    @Test
    public void testRuleTNTime_4() throws IOException {
        assertEmptyMatch("сколихнула минулого року");
    }

    @Test
    public void testRuleTNTime_5() throws IOException {
        assertEmptyMatch("збираються цього вечора");
    }

    @Test
    public void testRuleTNTime_6() throws IOException {
        assertEmptyMatch("чекати годинами");
    }

    @Test
    public void testRuleTNTime_7() throws IOException {
        assertEmptyMatch("спостерігається останнім часом");
    }

    @Test
    public void testRuleTNTime_8() throws IOException {
        assertEmptyMatch("відійшли метрів п'ять");
    }

    @Test
    public void testRuleTNTime_9() throws IOException {
        assertEmptyMatch("публікується кожні два роки");
    }

    @Test
    public void testRuleTNTime_10() throws IOException {
        assertEmptyMatch("зірватися всі ці 27 років");
    }

    @Test
    public void testRuleTNTime_11() throws IOException {
        assertEmptyMatch("відбувся минулої неділі");
    }

    @Test
    public void testRuleTNTime_12() throws IOException {
        assertEmptyMatch("закінчилося 18-го ввечері");
    }

    @Test
    public void testRuleTNTime_13() throws IOException {
        assertEmptyMatch("не знімався останні 10 років");
    }

    @Test
    public void testRuleTNTime_14() throws IOException {
        assertEmptyMatch("розпочнеться того ж дня");
    }

    @Test
    public void testRuleTNTime_15() throws IOException {
        assertEmptyMatch("помер цього вересня");
    }

    @Test
    public void testRuleTNTime_16() throws IOException {
        assertEmptyMatch("з’являться наступного ранку на суд");
    }

    @Test
    public void testRuleTNTime_17() throws IOException {
        assertEmptyMatch("мучитися три з половиною роки");
    }

    @Test
    public void testRuleTNTime_18() throws IOException {
        assertEmptyMatch("буде стояти двадцять і три роки");
    }

    @Test
    public void testRuleTNTime_19() throws IOException {
        assertEmptyMatch("попрацювала місяць-півтора");
    }

    @Test
    public void testRuleTNTime_20() throws IOException {
        assertEmptyMatch("сніг не тане всю зиму");
    }

    @Test
    public void testRuleTNTime_21() throws IOException {
        assertEmptyMatch("вщухнуть протягом кількох днів");
    }

    @Test
    public void testRuleTNTime_22() throws IOException {
        assertEmptyMatch("побутувала одночасно двома чи трьома мовами");
    }

    @Test
    public void testRuleTNTime_23() throws IOException {
        assertEmptyMatch("працює більшу частину часу");
    }

    @Test
    public void testRuleTNTime_24() throws IOException {
        assertEmptyMatch("які стартували цього та минулого року");
    }

    @Test
    public void testRuleTnVrod_1() throws IOException {
        assertEmptyMatch("не мав меблів");
    }

    @Test
    public void testRuleTnVrod_2() throws IOException {
        assertEmptyMatch("не повинен пропускати жодного звуку");
    }

    @Test
    public void testRuleTnVrod_3() throws IOException {
        assertEmptyMatch("не став витрачати грошей");
    }

    @Test
    public void testRuleTnVrod_4() throws IOException {
        assertEmptyMatch("казала цього не робити");
    }

    @Test
    public void testRuleTnVrod_5() throws IOException {
        assertEmptyMatch("не повинно перевищувати граничної величини");
    }

    @Test
    public void testRuleTnVrod_6() throws IOException {
        assertEmptyMatch("здаватися коаліціанти не збираються");
    }

    @Test
    public void testRuleTnVrod_7() throws IOException {
        assertEmptyMatch("не існувало конкуренції");
    }

    @Test
    public void testRuleTnVrod_8() throws IOException {
        assertEmptyMatch("не можна зберігати ілюзій");
    }

    @Test
    public void testRuleTnVrod_9() throws IOException {
        assertEmptyMatch("скільки отримує грошей");
    }

    @Test
    public void testRuleTnVrod_10() throws IOException {
        assertEmptyMatch("скільки буде людей");
    }

    @Test
    public void testRuleTnVrod_11() throws IOException {
        assertEmptyMatch("трохи маю контактів");
    }

    @Test
    public void testRuleTnVrod_12() throws IOException {
        assertEmptyMatch("скільки загалом здійснили постановок");
    }

    @Test
    public void testRuleTnVrod_13() throws IOException {
        assertEmptyMatch("стільки заплановано постановок");
    }

    @Test
    public void testRuleTnVrod_14() throws IOException {
        assertEmptyMatch("Багато в пресі з’явилося публікацій");
    }

    @Test
    public void testRuleTnVrod_15() throws IOException {
        assertEmptyMatch("небагато надходить книжок");
    }

    @Test
    public void testRuleTnVrod_16() throws IOException {
        assertEmptyMatch("ніж поставили стільців");
    }

    @Test
    public void testRuleTnVrod_17() throws IOException {
        assertEmptyMatch("Росія будує трубопроводів більше");
    }

    @Test
    public void testRuleTnVrod_18() throws IOException {
        assertEmptyMatch("не стане сили");
    }

    @Test
    public void testRuleTnVrod_19() throws IOException {
        assertEmptyMatch("нарубав лісу");
    }

    @Test
    public void testRuleTnInsertPhrase_1() throws IOException {
        assertEmptyMatch("змалював дивовижної краси церкву");
    }

    @Test
    public void testRuleTnInsertPhrase_2() throws IOException {
        assertEmptyMatch("Піднявся страшенної сили крижаний шторм");
    }

    @Test
    public void testRuleTnInsertPhrase_3() throws IOException {
        assertEmptyMatch("поводиться дивним чином");
    }

    @Test
    public void testRuleTnInsertPhrase_4() throws IOException {
        assertEmptyMatch("триває повним ходом.");
    }
}
