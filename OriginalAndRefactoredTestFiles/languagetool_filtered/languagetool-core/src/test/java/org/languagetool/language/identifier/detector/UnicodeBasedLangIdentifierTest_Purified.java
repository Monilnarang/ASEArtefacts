package org.languagetool.language.identifier.detector;

import org.junit.Test;
import org.languagetool.language.identifier.detector.UnicodeBasedDetector;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class UnicodeBasedLangIdentifierTest_Purified {

    private final UnicodeBasedDetector ident = new UnicodeBasedDetector(100);

    private String codes(String s) {
        return ident.getDominantLangCodes(s).toString();
    }

    @Test
    public void testGetDominantLangCodes_1() {
        assertThat(codes(""), is("[]"));
    }

    @Test
    public void testGetDominantLangCodes_34_testMerged_2() {
        String thai = "[th]";
        assertThat(codes("ลินุกซ์ (อังกฤษ: Linux) และรู้จักในชื่อ กะนู/ลินุกซ์"), is(thai));
        assertThat(codes("ลินุกซ์มีสัญญาอนุญาตแบบ GPL ซึ่งเป็นสัญญาอนุญาตที่กำหนด"), is(thai));
    }

    @Test
    public void testGetDominantLangCodes_2() {
        assertThat(codes(" "), is("[]"));
    }

    @Test
    public void testGetDominantLangCodes_3() {
        assertThat(codes("hallo"), is("[]"));
    }

    @Test
    public void testGetDominantLangCodes_36_testMerged_5() {
        String hebrew = "[he]";
        assertThat(codes("לינוקס (באנגלית: Linux) היא משפחה של מערכות הפעלה המבוססות"), is(hebrew));
        assertThat(codes("לינוקס היא דוגמה"), is(hebrew));
    }

    @Test
    public void testGetDominantLangCodes_4() {
        assertThat(codes("hallo this is a text"), is("[]"));
    }

    @Test
    public void testGetDominantLangCodes_5() {
        assertThat(codes("hallo this is a text стиль"), is("[]"));
    }

    @Test
    public void testGetDominantLangCodes_38_testMerged_8() {
        String korean = "[ko]";
        assertThat(codes("리눅스(Linux)[4]는 1991년 9월 17일 리누스 토르발스가 처음 출시한"), is(korean));
        assertThat(codes("리눅스(Linux)[4]는 1991년 9월 17일 리누"), is(korean));
        assertThat(codes("배포판에는 리눅스 커널과 지원"), is(korean));
        assertThat(codes("저명한 리눅스 배포판"), is(korean));
        assertThat(codes("리눅스는 또한 일반적으로"), is(korean));
        assertThat(codes("리눅스는 자유-"), is(korean));
    }

    @Test
    public void testGetDominantLangCodes_6_testMerged_9() {
        String cyrillic = "[ru, uk, be]";
        assertThat(codes("Грамматика, стиль и орфография LanguageTool проверяет ваше правописание на более чем 20 языках"), is(cyrillic));
        assertThat(codes("проверяет ваше правописание на более чем 20 языках"), is(cyrillic));
        assertThat(codes("Програма перевірки граматики, стилю та орфографії. LanguageTool перевіряє ваші тексти більш ніж 20-ма мовами"), is(cyrillic));
        assertThat(codes("Сучасная беларуская мова існуе ў літаратурнай і дыялектнай формах."), is(cyrillic));
        assertThat(codes("Програма перевірки граматики, стилю та орфографії."), is(cyrillic));
        assertThat(codes("проверяет ваше правописание на более чем 20 языках - Програма перевірки граматики, стилю та орфографії."), is(cyrillic));
        assertThat(codes("Сучасная беларуская мова існуе ў літаратурнай і дыялектнай формах. - Програма перевірки граматики, стилю та орфографії."), is(cyrillic));
        assertThat(codes("проверяет ваше правописание на более чем 20 языках - Сучасная беларуская мова існуе ў літаратурнай і дыялектнай формах."), is(cyrillic));
        assertThat(codes("проверяет ваше правописание на более чем 20 языках" + "Here's some short English text, but it's short"), is(cyrillic));
        assertThat(codes("Програма перевірки граматики, стилю та орфографії." + "Here's some short English text, but it's short"), is(cyrillic));
        assertThat(codes("проверяет ваше правописание на более чем 20 языках" + "Here's some English text"), is(cyrillic));
    }

    @Test
    public void testGetDominantLangCodes_17_testMerged_10() {
        String arabic = "[ar, fa]";
        assertThat(codes("لِينُكس (بالإنجليزية: Linux)\u200F (عن هذا الملف استمع (؟·معلومات)) ويسمى أيضا"), is(arabic));
        assertThat(codes("طور لينكس في الأصل لكي يعمل على"), is(arabic));
        assertThat(codes("يعمل لينكس أيضا على"), is(arabic));
        assertThat(codes("في بادئ الأمر أراد"), is(arabic));
    }

    @Test
    public void testGetDominantLangCodes_21_testMerged_11() {
        String cjk = "[zh, ja]";
        assertThat(codes("您的意思是"), is(cjk));
        assertThat(codes("Linux嚴格來說是單指作業系統的内核"), is(cjk));
        assertThat(codes("Linux（リナックス、他の読みは後述）とは、Unix系オペレーティングシステムカーネル"), is(cjk));
        assertThat(codes("1990年代はFreeBSDと比較して安定性に劣ると言われてきたが"), is(cjk));
        assertThat(codes("リーナス・トーバルズはカーネル開"), is(cjk));
    }

    @Test
    public void testGetDominantLangCodes_23() {
        assertThat(codes("通常情况下 but here's more text with Latin characters"), is("[]"));
    }

    @Test
    public void testGetDominantLangCodes_27() {
        assertThat(codes("ហើយដោយ​ព្រោះ​"), is("[km]"));
    }

    @Test
    public void testGetDominantLangCodes_28() {
        assertThat(codes("லேங்குவேஜ்"), is("[ta]"));
    }

    @Test
    public void testGetDominantLangCodes_29() {
        assertThat(codes("Το Linux μπορεί να εγκατασταθεί και"), is("[el]"));
    }

    @Test
    public void testGetDominantLangCodes_30() {
        assertThat(codes("Δημιουργός του πυρήνα Linux είναι ο"), is("[el]"));
    }

    @Test
    public void testGetDominantLangCodes_31() {
        assertThat(codes("Ο Τόρβαλντς ξεκίνησε"), is("[el]"));
    }

    @Test
    public void testGetDominantLangCodes_32_testMerged_18() {
        String devanagari = "[hi, mr]";
        assertThat(codes("दरलैंड में कोरोनोवायरस के दर्ज मामलों की संख्या 38 से बढ़कर 82 हो गई है।\n" + " मामलों में वृद्धि तब होती है जब देश उत्तरी इटली में स्कीइंग की छुट्टियों"), is(devanagari));
        assertThat(codes("आम्हाला उशीर होणार नाही"), is(devanagari));
    }
}
