package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class DaitchMokotoffSoundexTest_Purified extends AbstractStringEncoderTest<DaitchMokotoffSoundex> {

    @Override
    protected DaitchMokotoffSoundex createStringEncoder() {
        return new DaitchMokotoffSoundex();
    }

    private String encode(final String source) {
        return getStringEncoder().encode(source);
    }

    private String soundex(final String source) {
        return getStringEncoder().soundex(source);
    }

    @Test
    public void testAccentedCharacterFolding_1() {
        assertEquals("294795", soundex("Straßburg"));
    }

    @Test
    public void testAccentedCharacterFolding_2() {
        assertEquals("294795", soundex("Strasburg"));
    }

    @Test
    public void testAccentedCharacterFolding_3() {
        assertEquals("095600", soundex("Éregon"));
    }

    @Test
    public void testAccentedCharacterFolding_4() {
        assertEquals("095600", soundex("Eregon"));
    }

    @Test
    public void testAdjacentCodes_1() {
        assertEquals("054800", soundex("AKSSOL"));
    }

    @Test
    public void testAdjacentCodes_2() {
        assertEquals("547830|545783|594783|594578", soundex("GERSCHFELD"));
    }

    @Test
    public void testEncodeIgnoreTrimmable_1() {
        assertEquals("746536", encode(" \t\n\r Washington \t\n\r "));
    }

    @Test
    public void testEncodeIgnoreTrimmable_2() {
        assertEquals("746536", encode("Washington"));
    }

    @Test
    public void testSoundexBasic_1() {
        assertEquals("583600", soundex("GOLDEN"));
    }

    @Test
    public void testSoundexBasic_2() {
        assertEquals("087930", soundex("Alpert"));
    }

    @Test
    public void testSoundexBasic_3() {
        assertEquals("791900", soundex("Breuer"));
    }

    @Test
    public void testSoundexBasic_4() {
        assertEquals("579000", soundex("Haber"));
    }

    @Test
    public void testSoundexBasic_5() {
        assertEquals("665600", soundex("Mannheim"));
    }

    @Test
    public void testSoundexBasic_6() {
        assertEquals("664000", soundex("Mintz"));
    }

    @Test
    public void testSoundexBasic_7() {
        assertEquals("370000", soundex("Topf"));
    }

    @Test
    public void testSoundexBasic_8() {
        assertEquals("586660", soundex("Kleinmann"));
    }

    @Test
    public void testSoundexBasic_9() {
        assertEquals("769600", soundex("Ben Aron"));
    }

    @Test
    public void testSoundexBasic_10() {
        assertEquals("097400|097500", soundex("AUERBACH"));
    }

    @Test
    public void testSoundexBasic_11() {
        assertEquals("097400|097500", soundex("OHRBACH"));
    }

    @Test
    public void testSoundexBasic_12() {
        assertEquals("874400", soundex("LIPSHITZ"));
    }

    @Test
    public void testSoundexBasic_13() {
        assertEquals("874400|874500", soundex("LIPPSZYC"));
    }

    @Test
    public void testSoundexBasic_14() {
        assertEquals("876450", soundex("LEWINSKY"));
    }

    @Test
    public void testSoundexBasic_15() {
        assertEquals("876450", soundex("LEVINSKI"));
    }

    @Test
    public void testSoundexBasic_16() {
        assertEquals("486740", soundex("SZLAMAWICZ"));
    }

    @Test
    public void testSoundexBasic_17() {
        assertEquals("486740", soundex("SHLAMOVITZ"));
    }

    @Test
    public void testSoundexBasic2_1() {
        assertEquals("467000|567000", soundex("Ceniow"));
    }

    @Test
    public void testSoundexBasic2_2() {
        assertEquals("467000", soundex("Tsenyuv"));
    }

    @Test
    public void testSoundexBasic2_3() {
        assertEquals("587400|587500", soundex("Holubica"));
    }

    @Test
    public void testSoundexBasic2_4() {
        assertEquals("587400", soundex("Golubitsa"));
    }

    @Test
    public void testSoundexBasic2_5() {
        assertEquals("746480|794648", soundex("Przemysl"));
    }

    @Test
    public void testSoundexBasic2_6() {
        assertEquals("746480", soundex("Pshemeshil"));
    }

    @Test
    public void testSoundexBasic2_7() {
        assertEquals("944744|944745|944754|944755|945744|945745|945754|945755", soundex("Rosochowaciec"));
    }

    @Test
    public void testSoundexBasic2_8() {
        assertEquals("945744", soundex("Rosokhovatsets"));
    }

    @Test
    public void testSoundexBasic3_1() {
        assertEquals("734000|739400", soundex("Peters"));
    }

    @Test
    public void testSoundexBasic3_2() {
        assertEquals("734600|739460", soundex("Peterson"));
    }

    @Test
    public void testSoundexBasic3_3() {
        assertEquals("645740", soundex("Moskowitz"));
    }

    @Test
    public void testSoundexBasic3_4() {
        assertEquals("645740", soundex("Moskovitz"));
    }

    @Test
    public void testSoundexBasic3_5() {
        assertEquals("154600|145460|454600|445460", soundex("Jackson"));
    }

    @Test
    public void testSoundexBasic3_6() {
        assertEquals("154654|154645|154644|145465|145464|454654|454645|454644|445465|445464", soundex("Jackson-Jackson"));
    }

    @Test
    public void testSpecialRomanianCharacters_1() {
        assertEquals("364000|464000", soundex("ţamas"));
    }

    @Test
    public void testSpecialRomanianCharacters_2() {
        assertEquals("364000|464000", soundex("țamas"));
    }
}
