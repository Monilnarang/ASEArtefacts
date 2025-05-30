package org.languagetool.rules.uk;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;

public class TokenAgreementNounVerbRuleTest_Purified extends AbstractRuleTest {

    @Before
    public void setUp() throws IOException {
        rule = new TokenAgreementNounVerbRule(TestTools.getMessages("uk"));
    }

    private static final String GOOD_TEXT = "Хоча упродовж десятиліть ширилися численні історії про те, що я був у ряду наступників трону Тембу, щойно наведений простий генеалогічний екскурс викриває міфічність таких тверджень." + " Я був членом королівської родини, проте не належав до небагатьох привілейованих, що їх виховували на правителів." + " Натомість мене як нащадка Лівого дому навчали — так само, як і раніше мого батька — бути радником правителів племені." + " Мій батько був високим темношкірим чоловіком із прямою й величною поставою, яку я, хочеться думати, успадкував." + " У батька було пасмо білого волосся якраз над чолом, і хлопчиком я бувало брав сірий попіл і втирав його у своє волосся, щоб воно було таке саме, як у тата." + " Батько мій мав сувору вдачу й не шкодував різки, виховуючи дітей. Він міг бути дивовижно впертим, і це ще одна риса, яка, на жаль, теж могла перейти від батька до сина." + " Мого батька інколи називали прем’єр-міністром Тембуленду за врядування Далінд’єбо, батька Сабати, який правив на початку 1900-х років, та його сина й наступника Джонгінтаба." + " Насправді ж такого титулу не існувало, але мій батько справді відіграв роль, яка не надто відрізнялася від функції прем’єра." + " Як шанований і високо цінований радник обох королів, він супроводжував їх у подорожах і зазвичай перебував поруч із ними на важливих зустрічах із урядовими чиновниками." + " Він був також визнаним хоронителем історії коса, і частково через це його порадами так дорожили. Моє власне зацікавлення історією прокинулося рано, і батько його підживлював." + " Він не вмів ні читати, ні писати, але мав репутацію чудового оратора, який захоплював слухачів, розважаючи й водночас повчаючи їх.";

    @Test
    public void testRule_1() throws IOException {
        assertHasError("Тарас прибігла");
    }

    @Test
    public void testRule_2() throws IOException {
        assertHasError("вони прибіг");
    }

    @Test
    public void testRule_3() throws IOException {
        assertHasError("я прибіжиш");
    }

    @Test
    public void testRule_4() throws IOException {
        assertHasError("вони швидко прибіг");
    }

    @Test
    public void testRule_5() throws IOException {
        assertHasError("та з інших питань перевірка проведено не повно");
    }

    @Test
    public void testRule_6() throws IOException {
        assertHasError("з часом пара вирішили узаконити");
    }

    @Test
    public void testRule_7() throws IOException {
        assertEmptyMatch("На честь Джудіт Резнік названо кратер");
    }

    @Test
    public void testRule_8() throws IOException {
        assertEmptyMatch("чи зуміє наша держава забезпечити власні потреби");
    }

    @Test
    public void testRule_9() throws IOException {
        assertEmptyMatch("так навчила мене бабуся місити пухке дріжджове тісто");
    }

    @Test
    public void testRule_10() throws IOException {
        assertEmptyMatch("чи можуть російськомовні громадяни вважатися українцями");
    }

    @Test
    public void testRule_11() throws IOException {
        assertEmptyMatch("не шкода віддати життя");
    }

    @Test
    public void testRule_12() throws IOException {
        assertEmptyMatch("не шкода було віддати життя");
    }

    @Test
    public void testRule_13() throws IOException {
        assertEmptyMatch("чоловік прибіг");
    }

    @Test
    public void testRule_14() throws IOException {
        assertEmptyMatch("я прибіг");
    }

    @Test
    public void testRule_15() throws IOException {
        assertEmptyMatch("я прибігла");
    }

    @Test
    public void testRule_16() throws IOException {
        assertEmptyMatch("кандидат в президенти поїхав");
    }

    @Test
    public void testRule_17() throws IOException {
        assertEmptyMatch("кандидат в народні депутати поїхав");
    }

    @Test
    public void testRule_18() throws IOException {
        assertEmptyMatch("40 тисяч чоловік виявили бажання");
    }

    @Test
    public void testRule_19() throws IOException {
        assertEmptyMatch("клан Алькапоне було знищено");
    }

    @Test
    public void testRule_20() throws IOException {
        assertEmptyMatch("він було вмовк");
    }

    @Test
    public void testRule_21() throws IOException {
        assertEmptyMatch("тобто Угорщина було пішла шляхом");
    }

    @Test
    public void testRule_22() throws IOException {
        assertEmptyMatch("він було трохи скис");
    }

    @Test
    public void testRule_23() throws IOException {
        assertEmptyMatch("більше ніж будь-хто маємо повне право");
    }

    @Test
    public void testRule_24() throws IOException {
        assertEmptyMatch("а вона візьми і зроби");
    }

    @Test
    public void testRule_25() throws IOException {
        assertEmptyMatch("Збережені Я позбудуться необхідності");
    }

    @Test
    public void testRule_26() throws IOException {
        assertEmptyMatch("У місті Біла Церква було сформовано");
    }

    @Test
    public void testRule_27() throws IOException {
        assertHasError("із містом юності Ви очікувала");
    }

    @Test
    public void testRule_28() throws IOException {
        assertEmptyMatch("Андрій Качала");
    }

    @Test
    public void testRule_29() throws IOException {
        assertEmptyMatch("Любов Євтушок зауважила");
    }

    @Test
    public void testRule_30() throws IOException {
        assertEmptyMatch("а він давай пити горілку");
    }

    @Test
    public void testRule_31() throws IOException {
        assertEmptyMatch("Тарас ЗАКУСИЛО");
    }

    @Test
    public void testRule_32() throws IOException {
        assertEmptyMatch("не сила була йти далі");
    }

    @Test
    public void testRule_33() throws IOException {
        assertEmptyMatch("Вже давно пора було Мовчану відійти від керма.");
    }

    @Test
    public void testRule_34() throws IOException {
        assertEmptyMatch("тому, що як австрієць маєте");
    }

    @Test
    public void testRule_35() throws IOException {
        assertHasError("не встиг вона отямитися");
    }

    @Test
    public void testRule_36() throws IOException {
        assertEmptyMatch("про припинення їхньої діяльності ми ухвалити, зрозуміло, не могли");
    }

    @Test
    public void testRule_37() throws IOException {
        assertHasError("Ми може спробувати знайти");
    }

    @Test
    public void testRule_38() throws IOException {
        assertEmptyMatch(GOOD_TEXT);
    }

    @Test
    public void testRule_39() throws IOException {
        assertEmptyMatch("— це були невільники");
    }

    @Test
    public void testRule_40() throws IOException {
        assertHasError("щоб конкуренти підішли до виборів");
    }

    @Test
    public void testRuleNe_1() throws IOException {
        assertHasError("Тарас не прибігла");
    }

    @Test
    public void testRuleNe_2() throws IOException {
        assertHasError("Тарас би не прибігла");
    }

    @Test
    public void testRuleNe_3() throws IOException {
        assertHasError("вони не прибіг");
    }

    @Test
    public void testRuleNe_4() throws IOException {
        assertHasError("я не прибіжиш");
    }

    @Test
    public void testRuleNe_5() throws IOException {
        assertEmptyMatch("ні він, ні вона не хотіли");
    }

    @Test
    public void testRuleNe_6() throws IOException {
        assertEmptyMatch("уряд та поліція не контролюють події");
    }

    @Test
    public void testRuleNe_7() throws IOException {
        assertEmptyMatch("— це не була хвороба");
    }

    @Test
    public void testRuleNe_8() throws IOException {
        assertEmptyMatch("— це не передбачено");
    }

    @Test
    public void testRuleNe_9() throws IOException {
        assertEmptyMatch("Решта не мають жодних дозволів");
    }

    @Test
    public void testRuleNe_10() throws IOException {
        assertEmptyMatch("ні лауреат, ні його жінка не розмовляють жодними мовами");
    }

    @Test
    public void testRuleNe_11() throws IOException {
        assertEmptyMatch("Чи ж могла я не повернутися назад?");
    }

    @Test
    public void testRuleNe_12() throws IOException {
        assertEmptyMatch("кефаль, хамса не затримуються");
    }

    @Test
    public void testRuleNe_13() throws IOException {
        assertHasError("інша мова, вона примітивізовано й");
    }

    @Test
    public void testRuleNe_14() throws IOException {
        assertEmptyMatch("душ, одеколони, навіть хлорка не допомогли");
    }

    @Test
    public void testRuleNe_15() throws IOException {
        assertEmptyMatch("і Виговський, ні навіть Мазепа не розглядали");
    }

    @Test
    public void testRuleNe_16() throws IOException {
        assertEmptyMatch("уряд та поліція не контролюють");
    }

    @Test
    public void testRuleNe_17() throws IOException {
        assertEmptyMatch("кадрове забезпечення, матеріальна база не дісталися");
    }

    @Test
    public void testRuleNe_18() throws IOException {
        assertEmptyMatch("Волосожар/Морозов не припинили");
    }

    @Test
    public void testRuleNe_19() throws IOException {
        assertEmptyMatch("ні Європа, ні тим більше Україна не мають");
    }

    @Test
    public void testProperNames_1() throws IOException {
        assertEmptyMatch("Конституційний суд Республіки Молдова визнав румунську державною");
    }

    @Test
    public void testProperNames_2() throws IOException {
        assertEmptyMatch("Мешканці планети Земля споживають щороку");
    }

    @Test
    public void testProperNames_3() throws IOException {
        assertEmptyMatch("жителі селища Новобудова зверталися.");
    }

    @Test
    public void testProperNames_4() throws IOException {
        assertHasError("при своїх дружинах Крішна не роздягалася і одразу...");
    }

    @Test
    public void testProperNames_5() throws IOException {
        assertEmptyMatch("всі українські жінки з ім’ям Марія мають знати");
    }

    @Test
    public void testProperNames_6() throws IOException {
        assertEmptyMatch("а ім’я Франклін згадують не досить часто");
    }

    @Test
    public void testProperNames_7() throws IOException {
        assertEmptyMatch("шимпанзе на прізвисько Чита зіграли 16 «акторів»");
    }

    @Test
    public void testProperNames_8() throws IOException {
        assertEmptyMatch("лижний курорт Криниця розташувався в Бескидах");
    }

    @Test
    public void testProperNames_9() throws IOException {
        assertEmptyMatch("містечко Баришівка потрапило до історії");
    }

    @Test
    public void testProperNames_10() throws IOException {
        assertEmptyMatch("У місті Ліда незабаром мають встановити");
    }

    @Test
    public void testProperNames_11() throws IOException {
        assertEmptyMatch("ми в державі Україна маємо такі підстави");
    }

    @Test
    public void testProperNames_12() throws IOException {
        assertEmptyMatch("У литовський порт Клайпеда прибуло плавуче сховище");
    }

    @Test
    public void testProperNames_13() throws IOException {
        assertEmptyMatch("на австралійський штат Вікторія налетів сильний шторм");
    }

    @Test
    public void testProperNames_14() throws IOException {
        assertEmptyMatch("в селі Червона Слобода було вирішено перейменувати");
    }

    @Test
    public void testProperNames_15() throws IOException {
        assertEmptyMatch("Хоча б межі курорту Східниця визначено?");
    }

    @Test
    public void testProperNames_16() throws IOException {
        assertEmptyMatch("комітет порятунку села Оляниця вирішив");
    }

    @Test
    public void testProperNames_17() throws IOException {
        assertEmptyMatch("колишній кандидат у губернатори штату Аризона їхав до Чернівців");
    }

    @Test
    public void testProperNames_18() throws IOException {
        assertEmptyMatch("У невизнаній республіці Південна Осетія відбулися вибори");
    }

    @Test
    public void testProperNames_19() throws IOException {
        assertEmptyMatch("Рибалки італійського острова Лампедуза заблокували");
    }

    @Test
    public void testProperNames_20() throws IOException {
        assertEmptyMatch("у латвійське курортне містечко Юрмала з’їхався весь бомонд");
    }

    @Test
    public void testProperNames_21() throws IOException {
        assertEmptyMatch("Суд американського штату Каліфорнія присудив акторці");
    }

    @Test
    public void testProperNames_22() throws IOException {
        assertEmptyMatch("У штатах Техас і Луїзіана запроваджено надзвичайний стан");
    }

    @Test
    public void testProperNames_23() throws IOException {
        assertEmptyMatch("на прізвисько Михайло відбулася");
    }

    @Test
    public void testProperNames_24() throws IOException {
        assertHasError("Вистава зроблено чесно, професійно.");
    }

    @Test
    public void testPron_1() throws IOException {
        assertHasError("яка прибіг");
    }

    @Test
    public void testPron_2() throws IOException {
        assertHasError("яка не мають паспортів");
    }

    @Test
    public void testPron_3() throws IOException {
        assertHasError("яка не залежать від волі");
    }

    @Test
    public void testPron_4() throws IOException {
        assertHasError("яка було нещодавно опубліковано");
    }

    @Test
    public void testPron_5() throws IOException {
        assertEmptyMatch("вони як ніхто інший знали");
    }

    @Test
    public void testPron_6() throws IOException {
        assertEmptyMatch("який прибила хвиля");
    }

    @Test
    public void testPron_7() throws IOException {
        assertEmptyMatch("Ті, хто зрозуміли");
    }

    @Test
    public void testPron_8() throws IOException {
        assertEmptyMatch("ті, хто сповідує");
    }

    @Test
    public void testPron_9() throws IOException {
        assertEmptyMatch("ті, хто не сповідують");
    }

    @Test
    public void testPron_10() throws IOException {
        assertEmptyMatch("Ті, хто просто зрозуміли");
    }

    @Test
    public void testPron_11() throws IOException {
        assertEmptyMatch("всі хто зрозуміли");
    }

    @Test
    public void testPron_12() throws IOException {
        assertEmptyMatch("про те, хто була ця клята Пандора");
    }

    @Test
    public void testPron_13() throws IOException {
        assertEmptyMatch("що можна було й інший пошукати");
    }

    @Test
    public void testVerbInf_1() throws IOException {
        assertEmptyMatch("не встиг я отямитися");
    }

    @Test
    public void testVerbInf_2() throws IOException {
        assertEmptyMatch("що я зробити встиг");
    }

    @Test
    public void testVerbInf_3() throws IOException {
        assertEmptyMatch("це я робити швидко вмію");
    }

    @Test
    public void testVerbInf_4() throws IOException {
        assertEmptyMatch("Саудівську Аравію ми проходити зобов’язані");
    }

    @Test
    public void testVerbInf_5() throws IOException {
        assertHasError("я бігати");
    }

    @Test
    public void testVerbInf_6() throws IOException {
        assertEmptyMatch("ми воювати з нашими людьми не збираємося");
    }

    @Test
    public void testVerbInf_7() throws IOException {
        assertEmptyMatch("що ми зробити не зможемо");
    }

    @Test
    public void testVerbInf_8() throws IOException {
        assertEmptyMatch("Я уявити себе не можу без нашої програми.");
    }

    @Test
    public void testVerbInf_9() throws IOException {
        assertEmptyMatch("ми розраховувати не повинні");
    }

    @Test
    public void testVerbInf_10() throws IOException {
        assertEmptyMatch("Хотів би я подивитися");
    }

    @Test
    public void testVerbInf_11() throws IOException {
        assertEmptyMatch("на останніх ми працювати не згідні");
    }

    @Test
    public void testVerbInf_12() throws IOException {
        assertHasError("на останніх ми працювати не питаючи нікого");
    }

    @Test
    public void testVerbInf_13() throws IOException {
        assertEmptyMatch("те, чого я слухати не дуже хочу");
    }

    @Test
    public void testVerbInf_14() throws IOException {
        assertEmptyMatch("чи гідні ми бути незалежними");
    }

    @Test
    public void testVerbInf_15() throws IOException {
        assertEmptyMatch("Чи здатен її автор навести хоча б один факт");
    }

    @Test
    public void testVerbInf_16() throws IOException {
        assertEmptyMatch("чи готові ми сидіти без світла");
    }

    @Test
    public void testVerbInf_17() throws IOException {
        assertEmptyMatch("Чи повинен я просити");
    }

    @Test
    public void testVerbInf_18() throws IOException {
        assertEmptyMatch("ніхто робити цього не буде");
    }

    @Test
    public void testVerbInf_19() throws IOException {
        assertEmptyMatch("ніхто знижувати тарифи на газ, зрозуміло, не збирається");
    }

    @Test
    public void testVerbInf_20() throws IOException {
        assertHasError("Та припинити ти переживати");
    }

    @Test
    public void testVerbInf_21() throws IOException {
        assertEmptyMatch("та я купувати цю куртку не дуже хотіла");
    }

    @Test
    public void testVerbInf_22() throws IOException {
        assertEmptyMatch("Правоохоронці зняти провокаційний стяг не змогли, оскільки");
    }

    @Test
    public void testVerbInf_23() throws IOException {
        assertEmptyMatch("Однак Банкова виконувати приписи Закону \"Про очищення влади\" не поспішає");
    }

    @Test
    public void testVerbInf_24() throws IOException {
        assertEmptyMatch("Хтось намагається її торкнутися, хтось сфотографувати.");
    }

    @Test
    public void testVerbInf_25() throws IOException {
        assertEmptyMatch("Ми не проти сплачувати податки");
    }

    @Test
    public void testPlural_1() throws IOException {
        assertHasError("21 гравець дивилися");
    }

    @Test
    public void testPlural_2() throws IOException {
        assertHasError("один гравець дивилися");
    }

    @Test
    public void testPlural_3() throws IOException {
        assertHasError("Серед вбитих і полонених радянських солдат були чоловіки");
    }

    @Test
    public void testPlural_4() throws IOException {
        assertHasError("Пригадую випадок, коли одна аудіокомпанія звернулися до провідних київських «ефемок»");
    }

    @Test
    public void testPlural_5() throws IOException {
        assertHasError("підписку про невиїзд двох молодиків, яких міліція затримали першими");
    }

    @Test
    public void testPlural_6() throws IOException {
        assertHasError("рук та ніг Параски, темниця розчинилася і дівчина опинилися за стінами фортеці");
    }

    @Test
    public void testPlural_7() throws IOException {
        assertHasError("його арештували і вислали у Воркуту, я залишилися одна з дитиною.");
    }

    @Test
    public void testPlural_8() throws IOException {
        assertHasError("На проспекті Чорновола, ближче до центру, вона зупинилися на перехресті");
    }

    @Test
    public void testPlural_9() throws IOException {
        assertHasError("порадилися і громада запропонували мені зайняти його місце");
    }

    @Test
    public void testPlural_10() throws IOException {
        assertHasError("то й небо вона бачите саме таким");
    }

    @Test
    public void testPlural_11() throws IOException {
        assertHasError("молочного туману, потім вона заснувалися");
    }

    @Test
    public void testPlural_12() throws IOException {
        assertHasError("Наташа смикала за волосся, а Софія намагалися бризнути");
    }

    @Test
    public void testPlural_13() throws IOException {
        assertEmptyMatch("моя мама й сестра мешкали");
    }

    @Test
    public void testPlural_14() throws IOException {
        assertEmptyMatch("чи то Вальтер, чи я вжили фразу");
    }

    @Test
    public void testPlural_15() throws IOException {
        assertEmptyMatch("То вона, то Гриць виринають перед її душею");
    }

    @Test
    public void testPlural_16() throws IOException {
        assertEmptyMatch("Кожен чоловік і кожна жінка мають");
    }

    @Test
    public void testPlural_17() throws IOException {
        assertEmptyMatch("каналізація і навіть охорона пропонувалися");
    }

    @Test
    public void testPlural_18() throws IOException {
        assertEmptyMatch("Кавказ загалом і Чечня зокрема лишаться");
    }

    @Test
    public void testPlural_19() throws IOException {
        assertEmptyMatch("Європейський Союз і моя рідна дочка переживуть це збурення");
    }

    @Test
    public void testPlural_20() throws IOException {
        assertEmptyMatch("Бразилія, Мексика, Індія збувають");
    }

    @Test
    public void testPlural_21() throws IOException {
        assertEmptyMatch("Банкова й особисто президент дістали");
    }

    @Test
    public void testPlural_22() throws IOException {
        assertEmptyMatch("українська мода та взагалі українська культура впливають");
    }

    @Test
    public void testPlural_23() throws IOException {
        assertEmptyMatch("Тато і Юзь Федорків були прикладом");
    }

    @Test
    public void testPlural_24() throws IOException {
        assertEmptyMatch("Клочкова ти Лисогор перемагають на своїх дистанціях");
    }

    @Test
    public void testPlural_25() throws IOException {
        assertEmptyMatch("він особисто й облдержадміністрація винесли");
    }

    @Test
    public void testPlural_26() throws IOException {
        assertEmptyMatch("тисяча й одна ознака вказують");
    }

    @Test
    public void testPlural_27() throws IOException {
        assertEmptyMatch("і “більшовики”, і Президент звинуватили опозицію у зриві");
    }

    @Test
    public void testPlural_28() throws IOException {
        assertEmptyMatch("І “швидка“, і міліція приїхали майже вчасно");
    }

    @Test
    public void testPlural_29() throws IOException {
        assertEmptyMatch("І уряд, і президент позитивно оцінюють");
    }

    @Test
    public void testPlural_30() throws IOException {
        assertEmptyMatch("Андрій Ярмоленко, Євген Коноплянка, Ярослав Ракицький допомогли вітчизняній «молодіжці»");
    }

    @Test
    public void testPlural_31() throws IOException {
        assertEmptyMatch("Мустафа Джемілєв, Рефат Чубаров зможуть");
    }

    @Test
    public void testPlural_32() throws IOException {
        assertEmptyMatch("Єжи Тур, Александр Рибіцький перебороли");
    }

    @Test
    public void testPlural_33() throws IOException {
        assertEmptyMatch("27-річний водій та 54-річна пасажирка були травмовані");
    }

    @Test
    public void testPlural_34() throws IOException {
        assertEmptyMatch("фізична робота та щоденна 30–хвилинна фіззарядка приносять");
    }

    @Test
    public void testPlural_35() throws IOException {
        assertEmptyMatch("Б. Єльцин і Л. Кучма погодилися вважати Азовське море внутрішнім морем");
    }

    @Test
    public void testPlural_36() throws IOException {
        assertEmptyMatch("Ґорбачов і його дружина виглядали");
    }

    @Test
    public void testPlural_37() throws IOException {
        assertEmptyMatch("і Ципкалов, і Кисельов могли одразу");
    }

    @Test
    public void testPlural_38() throws IOException {
        assertEmptyMatch("«Самопоміч» та Радикальна партія дали більшість голосів");
    }

    @Test
    public void testPlural_39() throws IOException {
        assertEmptyMatch("Канада, Австралія та й Західна Європа знають");
    }

    @Test
    public void testPlural_40() throws IOException {
        assertEmptyMatch("процес формування уряду та його відставка залежать");
    }

    @Test
    public void testPlural_41() throws IOException {
        assertEmptyMatch("Усі розписи, а також архітектура відрізняються");
    }

    @Test
    public void testPlural_42() throws IOException {
        assertEmptyMatch("Німеччина (ще демократична) та Росія почали «дружити»");
    }

    @Test
    public void testPlural_43() throws IOException {
        assertEmptyMatch("Як Україна, так і Німеччина відчувають");
    }

    @Test
    public void testPlural_44() throws IOException {
        assertEmptyMatch("обласної ради, а й уся львівська громада виявилися обманутими");
    }

    @Test
    public void testPlural_45() throws IOException {
        assertEmptyMatch("Авіація ж і космонавтика справили на розвиток науки");
    }

    @Test
    public void testPlural_46() throws IOException {
        assertEmptyMatch("І спочатку Білорусь, а тепер і Україна пішли");
    }

    @Test
    public void testPlural_47() throws IOException {
        assertEmptyMatch("Саме тоді Англія, а невдовзі й уся Європа дізналися");
    }

    @Test
    public void testPlural_48() throws IOException {
        assertEmptyMatch("але концепція, а потім і програма мають бути");
    }

    @Test
    public void testPlural_49() throws IOException {
        assertEmptyMatch("Низка трагедій, а потім громадянська війна вигубили чимало люду");
    }

    @Test
    public void testPlural_50() throws IOException {
        assertEmptyMatch("Наша родина, я особисто і наша політична сила займаємося благодійністю");
    }

    @Test
    public void testPlural_51() throws IOException {
        assertEmptyMatch("і держава, і, відповідно, посада перестали існувати");
    }

    @Test
    public void testPlural_52() throws IOException {
        assertEmptyMatch("Швидке заселення земель, вирубування лісів, меліорація призвели");
    }

    @Test
    public void testPlural_53() throws IOException {
        assertEmptyMatch("узагалі, почуття гумору, іронія були притаманні");
    }

    @Test
    public void testPlural_54() throws IOException {
        assertEmptyMatch("почуття гумору, іронія були притаманні");
    }

    @Test
    public void testPlural_55() throws IOException {
        assertEmptyMatch("оскільки назва фільму, прізвище режисера, країна будуть викарбувані на бронзовій плиті");
    }

    @Test
    public void testPlural_56() throws IOException {
        assertEmptyMatch("Дух Києва, його атмосфера складаються");
    }

    @Test
    public void testPlural_57() throws IOException {
        assertEmptyMatch("Верховний суд, Рада суддів, Кваліфікаційна комісія могли б перевірити");
    }

    @Test
    public void testPlural_58() throws IOException {
        assertEmptyMatch("зеленкуваті плями моху, сіро-коричнева бруківка повертають нас на кілька століть назад");
    }

    @Test
    public void testPlural_59() throws IOException {
        assertEmptyMatch("водій “Мазди” та її пасажир загинули");
    }

    @Test
    public void testPlural_60() throws IOException {
        assertEmptyMatch("біологічна і ядерна зброя стають товаром");
    }

    @Test
    public void testPlural_61() throws IOException {
        assertEmptyMatch("І та й інша група вводили в клітини шкіри");
    }

    @Test
    public void testPlural_62() throws IOException {
        assertEmptyMatch("матч Туреччина — Україна зіграють");
    }

    @Test
    public void testPlural_63() throws IOException {
        assertEmptyMatch("у смузі Піски—Авдіївка оперують");
    }

    @Test
    public void testPlural_64() throws IOException {
        assertEmptyMatch("злість плюс іронія можуть вбити");
    }

    @Test
    public void testPlural_65() throws IOException {
        assertEmptyMatch("із яких 50% плюс одна акція знаходяться");
    }

    @Test
    public void testPlural_66() throws IOException {
        assertEmptyMatch("Матеріальна заінтересованість плюс гарна вивіска зіграли злий жарт");
    }

    @Test
    public void testPlural_67() throws IOException {
        assertEmptyMatch("Колесніков/Ахметов посилили");
    }

    @Test
    public void testPlural_68() throws IOException {
        assertEmptyMatch("решта забороняються");
    }

    @Test
    public void testPlural_69() throws IOException {
        assertEmptyMatch("все решта відійшло на другий план");
    }

    @Test
    public void testPlural_70() throws IOException {
        assertEmptyMatch("Більш ніж половина віддали голоси");
    }

    @Test
    public void testPlural_71() throws IOException {
        assertEmptyMatch("Левова їхня частка працюють через російських туроператорів");
    }

    @Test
    public void testPlural_72() throws IOException {
        assertEmptyMatch("дві групи з трьох осіб кожна виконували просте завдання");
    }

    @Test
    public void testPlural_73() throws IOException {
        assertEmptyMatch("Що пачка цигарок, що ковбаса коштують");
    }

    @Test
    public void testPlural_74() throws IOException {
        assertEmptyMatch("не вулична злочинність, не корупція відлякували");
    }

    @Test
    public void testPlural_75() throws IOException {
        assertEmptyMatch("з Василем Кричевським Оскар Германович разом брали участь");
    }

    @Test
    public void testPlural_76() throws IOException {
        assertEmptyMatch("Леонід Новохатько й керівник головного управління з питань гуманітарного розвитку адміністрації\n" + "Президента Юрій Богуцький обговорили");
    }

    @Test
    public void testPlural_77() throws IOException {
        assertEmptyMatch("інший нардеп та лідер «Правого сектору» Дмитро Ярош просили");
    }

    @Test
    public void testPlural_78() throws IOException {
        assertEmptyMatch("пара Катерина Морозова/Дмитро Алексєєв тріумфувала");
    }

    @Test
    public void testNum_1() throws IOException {
        assertEmptyMatch("понад тисяча отримали поранення");
    }

    @Test
    public void testNum_2() throws IOException {
        assertEmptyMatch("Решта 121 депутат висловилися проти");
    }

    @Test
    public void testNum_3() throws IOException {
        assertEmptyMatch("понад сотня отримали поранення");
    }

    @Test
    public void testNum_4() throws IOException {
        assertHasError("22 льотчики удостоєно");
    }

    @Test
    public void testNum_5() throws IOException {
        assertEmptyMatch("два сини народилося там");
    }

    @Test
    public void testMascFem_1() throws IOException {
        assertEmptyMatch("німецький канцлер зателефонувала російському президенту");
    }

    @Test
    public void testMascFem_2() throws IOException {
        assertEmptyMatch("екс-міністр повторила у телезверненні");
    }

    @Test
    public void testMascFem_3() throws IOException {
        assertEmptyMatch("Прем’єр-міністр повторила у телезверненні");
    }

    @Test
    public void testMascFem_4() throws IOException {
        assertEmptyMatch("Прем’єр—міністр повторила у телезверненні");
    }

    @Test
    public void testMascFem_5() throws IOException {
        assertEmptyMatch("єврокомісар зазначила, що");
    }

    @Test
    public void testMascFem_6() throws IOException {
        assertEmptyMatch("кінолог пояснила");
    }

    @Test
    public void testMascFem_7() throws IOException {
        assertEmptyMatch("автор-упорядник назвала збірник");
    }

    @Test
    public void testMascFem_8() throws IOException {
        assertHasError("Прем’єр-міністр повторило у телезверненні");
    }

    @Test
    public void testMascFem_9() throws IOException {
        assertHasError("приятель повторила у телезверненні");
    }

    @Test
    public void testIgnoreByIntent_1() throws IOException {
        assertEmptyMatch("тому що воно привнесено ззовні");
    }

    @Test
    public void testIgnoreByIntent_2() throws IOException {
        assertEmptyMatch("Воно просочено історією");
    }

    @Test
    public void testIgnoreByIntent_3() throws IOException {
        assertEmptyMatch("Все решта зафіксовано");
    }

    @Test
    public void testIgnoreByIntent_4() throws IOException {
        assertEmptyMatch("решта зафіксовано");
    }

    @Test
    public void testIgnoreByIntent_5() throws IOException {
        assertEmptyMatch("З охопленого війною Сектора Газа вивезли «більш як 80 громадян України».");
    }

    @Test
    public void testIgnoreByIntent_6() throws IOException {
        assertEmptyMatch("подружжя Обама запросило 350 гостей");
    }

    @Test
    public void testCaseGovernment_1() throws IOException {
        assertEmptyMatch("коли українцям пора показати");
    }

    @Test
    public void testCaseGovernment_2() throws IOException {
        assertEmptyMatch("або пропозиція збільшити частку");
    }

    @Test
    public void testRuleWithAdjOrKly_1() throws IOException {
        assertHasError("Ви постане перед вибором");
    }

    @Test
    public void testRuleWithAdjOrKly_2() throws IOException {
        assertHasError("військовий прибігла");
    }

    @Test
    public void testRuleWithAdjOrKly_3() throws IOException {
        assertHasError("Фехтувальна збірна було до цього готова");
    }

    @Test
    public void testRuleWithAdjOrKly_4() throws IOException {
        assertHasError("наші дівчата виграти загальний залік");
    }

    @Test
    public void testRuleWithAdjOrKly_5() throws IOException {
        assertHasError("окремі ентузіасти переймалася");
    }

    @Test
    public void testRuleWithAdjOrKly_6() throws IOException {
        assertHasError("угорська влада пообіцяли переглянути");
    }

    @Test
    public void testRuleWithAdjOrKly_7() throws IOException {
        assertHasError("точно також ви буде платити");
    }

    @Test
    public void testRuleWithAdjOrKly_8() throws IOException {
        assertEmptyMatch("багато хто в драматурги прийшов");
    }

    @Test
    public void testRuleWithAdjOrKly_9() throws IOException {
        assertEmptyMatch("з кандидатом у президенти не визначився");
    }

    @Test
    public void testRuleWithAdjOrKly_10() throws IOException {
        assertEmptyMatch("Мої співвітчизники терпіти це далі не повинні");
    }

    @Test
    public void testRuleWithAdjOrKly_11() throws IOException {
        assertEmptyMatch("Ви може образились");
    }

    @Test
    public void testRuleWithAdjOrKly_12() throws IOException {
        assertEmptyMatch("Моя ти зоре в тумані");
    }

    @Test
    public void testRuleWithAdjOrKly_13() throws IOException {
        assertEmptyMatch("На кожен покладіть по кільцю");
    }

    @Test
    public void testRuleWithAdjOrKly_14() throws IOException {
        assertEmptyMatch("Любителі фотографувати їжу");
    }

    @Test
    public void testSpecialChars_1() throws IOException {
        assertEmptyMatch("Тарас при\u00ADбіг.");
    }

    @Test
    public void testSpecialChars_2_testMerged_2() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("Тарас при\u00ADбігла."));
        assertEquals(1, matches.length);
    }
}
