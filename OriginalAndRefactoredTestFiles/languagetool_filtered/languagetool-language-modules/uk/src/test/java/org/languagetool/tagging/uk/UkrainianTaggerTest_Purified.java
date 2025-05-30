package org.languagetool.tagging.uk;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.TestTools;
import org.languagetool.language.Ukrainian;
import org.languagetool.tokenizers.uk.UkrainianWordTokenizer;

public class UkrainianTaggerTest_Purified {

    private UkrainianTagger tagger;

    private UkrainianWordTokenizer tokenizer;

    @Before
    public void setUp() {
        tagger = new UkrainianTagger();
        tokenizer = new UkrainianWordTokenizer();
    }

    private void assertNotTagged(String word) throws IOException {
        TestTools.myAssert(word, word + "/[null]null", tokenizer, tagger);
    }

    private void assertTagged(String word, String tagged) {
        try {
            TestTools.myAssert(word, tagged, tokenizer, tagger);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNumberTagging_1() throws IOException {
        assertNotTagged("5610100000:01:041:0487");
    }

    @Test
    public void testNumberTagging_2() throws IOException {
        assertNotTagged("H1");
    }

    @Test
    public void testDynamicTaggingParts_1() throws IOException {
        assertNotTagged("м-б");
    }

    @Test
    public void testDynamicTaggingParts_2() throws IOException {
        assertNotTagged("хто-то");
    }

    @Test
    public void testDynamicTaggingParts_3() throws IOException {
        assertNotTagged("що-то");
    }

    @Test
    public void testDynamicTaggingParts_4() throws IOException {
        assertNotTagged("как-то");
    }

    @Test
    public void testDynamicTaggingParts_5() throws IOException {
        assertNotTagged("кто-то");
    }

    @Test
    public void testDynamicTaggingParts_6() throws IOException {
        assertNotTagged("до-пари");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_1() throws IOException {
        assertNotTagged("Малишко-це");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_2() throws IOException {
        assertNotTagged("відносини-коли");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_3() throws IOException {
        assertNotTagged("лісо-та");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_4() throws IOException {
        assertNotTagged("Ліс-наш");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_5() throws IOException {
        assertNotTagged("Нью-Париж");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_6() throws IOException {
        assertNotTagged("кохання-найщиріше");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_7() throws IOException {
        assertNotTagged("Донець-кий");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_8() throws IOException {
        assertNotTagged("мас-штаби");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_9() throws IOException {
        assertNotTagged("підо-пічні");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_10() throws IOException {
        assertNotTagged("рибо-полювання");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_11() throws IOException {
        assertNotTagged("вовіки-вічні");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_12() throws IOException {
        assertNotTagged("Гірник-спорт");
    }

    @Test
    public void testDynamicTaggingFullTagMatch_13() throws IOException {
        assertNotTagged("Квітку-Основ'яненко");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_1() throws IOException {
        assertTagged("авіа-переліт", "авіа-переліт/[авіа-переліт]noun:inanim:m:v_naz:bad|авіа-переліт/[авіа-переліт]noun:inanim:m:v_zna:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_2() throws IOException {
        assertTagged("авто-салон", "авто-салон/[авто-салон]noun:inanim:m:v_naz:bad|авто-салон/[авто-салон]noun:inanim:m:v_zna:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_3() throws IOException {
        assertTagged("квазі-держави", "квазі-держави/[квазі-держава]noun:inanim:f:v_rod:bad|квазі-держави/[квазі-держава]noun:inanim:p:v_kly:bad|квазі-держави/[квазі-держава]noun:inanim:p:v_naz:bad|квазі-держави/[квазі-держава]noun:inanim:p:v_zna:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_4() throws IOException {
        assertTagged("мульти-візу", "мульти-візу/[мульти-віза]noun:inanim:f:v_zna:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_5() throws IOException {
        assertTagged("контр-міри", "контр-міри/[контр-міра]noun:inanim:f:v_rod:bad|контр-міри/[контр-міра]noun:inanim:p:v_kly:bad|контр-міри/[контр-міра]noun:inanim:p:v_naz:bad|контр-міри/[контр-міра]noun:inanim:p:v_zna:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_6() throws IOException {
        assertTagged("кіно-критика", "кіно-критика/[кіно-критик]noun:anim:m:v_rod:bad|кіно-критика/[кіно-критик]noun:anim:m:v_zna:bad|кіно-критика/[кіно-критика]noun:inanim:f:v_naz:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_7() throws IOException {
        assertTagged("древньо-римський", "древньо-римський/[древньо-римський]adj:m:v_kly:bad|древньо-римський/[древньо-римський]adj:m:v_naz:bad|древньо-римський/[древньо-римський]adj:m:v_zna:rinanim:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_8() throws IOException {
        assertTagged("давньо-римський", "давньо-римський/[давньо-римський]adj:m:v_kly:bad|давньо-римський/[давньо-римський]adj:m:v_naz:bad|давньо-римський/[давньо-римський]adj:m:v_zna:rinanim:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_9() throws IOException {
        assertTagged("максимально-можливу", "максимально-можливу/[максимально-можливий]adj:f:v_zna:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_10() throws IOException {
        assertTagged("загально-правовий", "загально-правовий/[загально-правовий]adj:m:v_kly:bad|загально-правовий/[загально-правовий]adj:m:v_naz:bad|загально-правовий/[загально-правовий]adj:m:v_zna:rinanim:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_11() throws IOException {
        assertTagged("відео-навчання", "відео-навчання/[відео-навчання]noun:inanim:n:v_kly:bad|відео-навчання/[відео-навчання]noun:inanim:n:v_naz:bad|відео-навчання/[відео-навчання]noun:inanim:n:v_rod:bad|відео-навчання/[відео-навчання]noun:inanim:n:v_zna:bad|відео-навчання/[відео-навчання]noun:inanim:p:v_kly:bad|відео-навчання/[відео-навчання]noun:inanim:p:v_naz:bad|відео-навчання/[відео-навчання]noun:inanim:p:v_zna:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_12() throws IOException {
        assertTagged("В2В-рішень", "В2В-рішень/[В2В-рішення]noun:inanim:p:v_rod:bad");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_13() throws IOException {
        assertTagged("кіно-Європа", "кіно-Європа/[кіно-Європа]noun:inanim:f:v_naz:prop:geo");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_14() throws IOException {
        assertNotTagged("теле-та");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_15() throws IOException {
        assertNotTagged("квазі-я");
    }

    @Test
    public void testDynamicTaggingInvalidLeft_16() throws IOException {
        assertNotTagged("макро-і");
    }

    @Test
    public void testDynamicTaggingNoDash_1() throws IOException {
        assertNotTagged("авіамені");
    }

    @Test
    public void testDynamicTaggingNoDash_2() throws IOException {
        assertNotTagged("антиЄвропа");
    }

    @Test
    public void testDynamicTaggingNoDash_3() throws IOException {
        assertNotTagged("напіврозслабеному");
    }

    @Test
    public void testDynamicTaggingNoDash_4() throws IOException {
        assertNotTagged("напіви");
    }

    @Test
    public void testDynamicTaggingOWithAdj_1() throws IOException {
        assertNotTagged("паталого-анатомічний");
    }

    @Test
    public void testDynamicTaggingOWithAdj_2() throws IOException {
        assertNotTagged("патолога-анатомічний");
    }

    @Test
    public void testDynamicTaggingOWithAdj_3() throws IOException {
        assertNotTagged("патолого-гмкнх");
    }

    @Test
    public void testDynamicTaggingOWithAdj_4() throws IOException {
        assertNotTagged("патолого-голова");
    }

    @Test
    public void testDynamicTaggingOWithAdj_5() throws IOException {
        assertNotTagged("бірмюково-блакитний");
    }

    @Test
    public void testDynamicTaggingOWithAdj_6() throws IOException {
        assertNotTagged("во-політичний");
    }

    @Test
    public void testDynamicTaggingOWithAdj_7() throws IOException {
        assertNotTagged("о-політичний");
    }

    @Test
    public void testDynamicTaggingOWithAdj_8() throws IOException {
        assertNotTagged("рово-часового");
    }

    @Test
    public void testTaggingMultidash_1() throws IOException {
        TestTools.myAssert("україно-румуно-болгарський", "україно-румуно-болгарський/[україно-румуно-болгарський]adj:m:v_kly|україно-румуно-болгарський/[україно-румуно-болгарський]adj:m:v_naz|україно-румуно-болгарський/[україно-румуно-болгарський]adj:m:v_zna:rinanim", tokenizer, tagger);
        TestTools.myAssert("синьо-біло-чорному", "синьо-біло-чорному/[синьо-біло-чорний]adj:m:v_dav|синьо-біло-чорному/[синьо-біло-чорний]adj:m:v_mis|синьо-біло-чорному/[синьо-біло-чорний]adj:n:v_dav|синьо-біло-чорному/[синьо-біло-чорний]adj:n:v_mis", tokenizer, tagger);
        TestTools.myAssert("синьо-біло-жовтий", "синьо-біло-жовтий/[синьо-біло-жовтий]adj:m:v_kly|синьо-біло-жовтий/[синьо-біло-жовтий]adj:m:v_naz|синьо-біло-жовтий/[синьо-біло-жовтий]adj:m:v_zna:rinanim", tokenizer, tagger);
        TestTools.myAssert("українсько-англійсько-французьким", "українсько-англійсько-французьким/[українсько-англійсько-французький]adj:m:v_oru|українсько-англійсько-французьким/[українсько-англійсько-французький]adj:n:v_oru|українсько-англійсько-французьким/[українсько-англійсько-французький]adj:p:v_dav", tokenizer, tagger);
        TestTools.myAssert("седативного-снодійного-антигістамінного", "седативного-снодійного-антигістамінного/[снодійний-антигістамінний]adj:m:v_rod|седативного-снодійного-антигістамінного/[снодійний-антигістамінний]adj:m:v_zna:ranim|седативного-снодійного-антигістамінного/[снодійний-антигістамінний]adj:n:v_rod", tokenizer, tagger);
        assertEquals("[–---–[–---–/null*]]", tagger.tag(tokenizer.tokenize("–---–")).toString());
    }

    @Test
    public void testTaggingMultidash_2() throws IOException {
        assertNotTagged("військо-во-політичний");
    }

    @Test
    public void testHypenStretch_1() throws IOException {
        assertNotTagged("ііі");
    }

    @Test
    public void testHypenStretch_2() throws IOException {
        assertNotTagged("відео-конференц-зв'язком");
    }

    @Test
    public void testHypenStretch_3() throws IOException {
        assertNotTagged("Шаабан");
    }

    @Test
    public void testDynamicTaggingSkip_1() throws IOException {
        assertNotTagged("спа-салоне");
    }

    @Test
    public void testDynamicTaggingSkip_2() throws IOException {
        assertNotTagged("піт-стопою");
    }

    @Test
    public void testDynamicTaggingSkip_3() throws IOException {
        assertNotTagged("па-холка");
    }

    @Test
    public void testAltSpelling_1() throws IOException {
        assertNotTagged("АвСтріях");
    }

    @Test
    public void testAltSpelling_2() throws IOException {
        assertNotTagged("аВс");
    }

    @Test
    public void testAltSpelling_3() throws IOException {
        assertNotTagged("вДягнУтися");
    }

    @Test
    public void testAltSpelling_4() throws IOException {
        assertNotTagged("австріях");
    }

    @Test
    public void testAltSpelling_5() throws IOException {
        assertNotTagged("польская");
    }
}
