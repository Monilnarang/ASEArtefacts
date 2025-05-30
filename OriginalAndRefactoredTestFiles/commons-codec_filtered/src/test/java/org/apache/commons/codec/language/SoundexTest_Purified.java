package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_Purified extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    public void testDifference_1() throws EncoderException {
        assertEquals(0, getStringEncoder().difference(null, null));
    }

    @Test
    public void testDifference_2() throws EncoderException {
        assertEquals(0, getStringEncoder().difference("", ""));
    }

    @Test
    public void testDifference_3() throws EncoderException {
        assertEquals(0, getStringEncoder().difference(" ", " "));
    }

    @Test
    public void testDifference_4() throws EncoderException {
        assertEquals(4, getStringEncoder().difference("Smith", "Smythe"));
    }

    @Test
    public void testDifference_5() throws EncoderException {
        assertEquals(2, getStringEncoder().difference("Ann", "Andrew"));
    }

    @Test
    public void testDifference_6() throws EncoderException {
        assertEquals(1, getStringEncoder().difference("Margaret", "Andrew"));
    }

    @Test
    public void testDifference_7() throws EncoderException {
        assertEquals(0, getStringEncoder().difference("Janet", "Margaret"));
    }

    @Test
    public void testDifference_8() throws EncoderException {
        assertEquals(4, getStringEncoder().difference("Green", "Greene"));
    }

    @Test
    public void testDifference_9() throws EncoderException {
        assertEquals(0, getStringEncoder().difference("Blotchet-Halls", "Greene"));
    }

    @Test
    public void testDifference_10() throws EncoderException {
        assertEquals(4, getStringEncoder().difference("Smith", "Smythe"));
    }

    @Test
    public void testDifference_11() throws EncoderException {
        assertEquals(4, getStringEncoder().difference("Smithers", "Smythers"));
    }

    @Test
    public void testDifference_12() throws EncoderException {
        assertEquals(2, getStringEncoder().difference("Anothers", "Brothers"));
    }

    @Test
    public void testEncodeBasic_1() {
        assertEquals("T235", getStringEncoder().encode("testing"));
    }

    @Test
    public void testEncodeBasic_2() {
        assertEquals("T000", getStringEncoder().encode("The"));
    }

    @Test
    public void testEncodeBasic_3() {
        assertEquals("Q200", getStringEncoder().encode("quick"));
    }

    @Test
    public void testEncodeBasic_4() {
        assertEquals("B650", getStringEncoder().encode("brown"));
    }

    @Test
    public void testEncodeBasic_5() {
        assertEquals("F200", getStringEncoder().encode("fox"));
    }

    @Test
    public void testEncodeBasic_6() {
        assertEquals("J513", getStringEncoder().encode("jumped"));
    }

    @Test
    public void testEncodeBasic_7() {
        assertEquals("O160", getStringEncoder().encode("over"));
    }

    @Test
    public void testEncodeBasic_8() {
        assertEquals("T000", getStringEncoder().encode("the"));
    }

    @Test
    public void testEncodeBasic_9() {
        assertEquals("L200", getStringEncoder().encode("lazy"));
    }

    @Test
    public void testEncodeBasic_10() {
        assertEquals("D200", getStringEncoder().encode("dogs"));
    }

    @Test
    public void testEncodeBatch2_1() {
        assertEquals("A462", getStringEncoder().encode("Allricht"));
    }

    @Test
    public void testEncodeBatch2_2() {
        assertEquals("E166", getStringEncoder().encode("Eberhard"));
    }

    @Test
    public void testEncodeBatch2_3() {
        assertEquals("E521", getStringEncoder().encode("Engebrethson"));
    }

    @Test
    public void testEncodeBatch2_4() {
        assertEquals("H512", getStringEncoder().encode("Heimbach"));
    }

    @Test
    public void testEncodeBatch2_5() {
        assertEquals("H524", getStringEncoder().encode("Hanselmann"));
    }

    @Test
    public void testEncodeBatch2_6() {
        assertEquals("H431", getStringEncoder().encode("Hildebrand"));
    }

    @Test
    public void testEncodeBatch2_7() {
        assertEquals("K152", getStringEncoder().encode("Kavanagh"));
    }

    @Test
    public void testEncodeBatch2_8() {
        assertEquals("L530", getStringEncoder().encode("Lind"));
    }

    @Test
    public void testEncodeBatch2_9() {
        assertEquals("L222", getStringEncoder().encode("Lukaschowsky"));
    }

    @Test
    public void testEncodeBatch2_10() {
        assertEquals("M235", getStringEncoder().encode("McDonnell"));
    }

    @Test
    public void testEncodeBatch2_11() {
        assertEquals("M200", getStringEncoder().encode("McGee"));
    }

    @Test
    public void testEncodeBatch2_12() {
        assertEquals("O155", getStringEncoder().encode("Opnian"));
    }

    @Test
    public void testEncodeBatch2_13() {
        assertEquals("O155", getStringEncoder().encode("Oppenheimer"));
    }

    @Test
    public void testEncodeBatch2_14() {
        assertEquals("R355", getStringEncoder().encode("Riedemanas"));
    }

    @Test
    public void testEncodeBatch2_15() {
        assertEquals("Z300", getStringEncoder().encode("Zita"));
    }

    @Test
    public void testEncodeBatch2_16() {
        assertEquals("Z325", getStringEncoder().encode("Zitzmeinn"));
    }

    @Test
    public void testEncodeBatch3_1() {
        assertEquals("W252", getStringEncoder().encode("Washington"));
    }

    @Test
    public void testEncodeBatch3_2() {
        assertEquals("L000", getStringEncoder().encode("Lee"));
    }

    @Test
    public void testEncodeBatch3_3() {
        assertEquals("G362", getStringEncoder().encode("Gutierrez"));
    }

    @Test
    public void testEncodeBatch3_4() {
        assertEquals("P236", getStringEncoder().encode("Pfister"));
    }

    @Test
    public void testEncodeBatch3_5() {
        assertEquals("J250", getStringEncoder().encode("Jackson"));
    }

    @Test
    public void testEncodeBatch3_6() {
        assertEquals("T522", getStringEncoder().encode("Tymczak"));
    }

    @Test
    public void testEncodeBatch3_7() {
        assertEquals("V532", getStringEncoder().encode("VanDeusen"));
    }

    @Test
    public void testEncodeBatch4_1() {
        assertEquals("H452", getStringEncoder().encode("HOLMES"));
    }

    @Test
    public void testEncodeBatch4_2() {
        assertEquals("A355", getStringEncoder().encode("ADOMOMI"));
    }

    @Test
    public void testEncodeBatch4_3() {
        assertEquals("V536", getStringEncoder().encode("VONDERLEHR"));
    }

    @Test
    public void testEncodeBatch4_4() {
        assertEquals("B400", getStringEncoder().encode("BALL"));
    }

    @Test
    public void testEncodeBatch4_5() {
        assertEquals("S000", getStringEncoder().encode("SHAW"));
    }

    @Test
    public void testEncodeBatch4_6() {
        assertEquals("J250", getStringEncoder().encode("JACKSON"));
    }

    @Test
    public void testEncodeBatch4_7() {
        assertEquals("S545", getStringEncoder().encode("SCANLON"));
    }

    @Test
    public void testEncodeBatch4_8() {
        assertEquals("S532", getStringEncoder().encode("SAINTJOHN"));
    }

    @Test
    public void testHWRuleEx1_1() {
        assertEquals("A261", getStringEncoder().encode("Ashcraft"));
    }

    @Test
    public void testHWRuleEx1_2() {
        assertEquals("A261", getStringEncoder().encode("Ashcroft"));
    }

    @Test
    public void testHWRuleEx1_3() {
        assertEquals("Y330", getStringEncoder().encode("yehudit"));
    }

    @Test
    public void testHWRuleEx1_4() {
        assertEquals("Y330", getStringEncoder().encode("yhwdyt"));
    }

    @Test
    public void testHWRuleEx2_1() {
        assertEquals("B312", getStringEncoder().encode("BOOTHDAVIS"));
    }

    @Test
    public void testHWRuleEx2_2() {
        assertEquals("B312", getStringEncoder().encode("BOOTH-DAVIS"));
    }

    @Test
    public void testMsSqlServer1_1() {
        assertEquals("S530", getStringEncoder().encode("Smith"));
    }

    @Test
    public void testMsSqlServer1_2() {
        assertEquals("S530", getStringEncoder().encode("Smythe"));
    }

    @Test
    public void testMsSqlServer3_1() {
        assertEquals("A500", getStringEncoder().encode("Ann"));
    }

    @Test
    public void testMsSqlServer3_2() {
        assertEquals("A536", getStringEncoder().encode("Andrew"));
    }

    @Test
    public void testMsSqlServer3_3() {
        assertEquals("J530", getStringEncoder().encode("Janet"));
    }

    @Test
    public void testMsSqlServer3_4() {
        assertEquals("M626", getStringEncoder().encode("Margaret"));
    }

    @Test
    public void testMsSqlServer3_5() {
        assertEquals("S315", getStringEncoder().encode("Steven"));
    }

    @Test
    public void testMsSqlServer3_6() {
        assertEquals("M240", getStringEncoder().encode("Michael"));
    }

    @Test
    public void testMsSqlServer3_7() {
        assertEquals("R163", getStringEncoder().encode("Robert"));
    }

    @Test
    public void testMsSqlServer3_8() {
        assertEquals("L600", getStringEncoder().encode("Laura"));
    }

    @Test
    public void testMsSqlServer3_9() {
        assertEquals("A500", getStringEncoder().encode("Anne"));
    }

    @Test
    public void testSoundexUtilsNullBehaviour_1() {
        assertNull(SoundexUtils.clean(null));
    }

    @Test
    public void testSoundexUtilsNullBehaviour_2() {
        assertEquals("", SoundexUtils.clean(""));
    }

    @Test
    public void testSoundexUtilsNullBehaviour_3() {
        assertEquals(0, SoundexUtils.differenceEncoded(null, ""));
    }

    @Test
    public void testSoundexUtilsNullBehaviour_4() {
        assertEquals(0, SoundexUtils.differenceEncoded("", null));
    }

    @Test
    public void testWikipediaAmericanSoundex_1() {
        assertEquals("R163", getStringEncoder().encode("Robert"));
    }

    @Test
    public void testWikipediaAmericanSoundex_2() {
        assertEquals("R163", getStringEncoder().encode("Rupert"));
    }

    @Test
    public void testWikipediaAmericanSoundex_3() {
        assertEquals("A261", getStringEncoder().encode("Ashcraft"));
    }

    @Test
    public void testWikipediaAmericanSoundex_4() {
        assertEquals("A261", getStringEncoder().encode("Ashcroft"));
    }

    @Test
    public void testWikipediaAmericanSoundex_5() {
        assertEquals("T522", getStringEncoder().encode("Tymczak"));
    }

    @Test
    public void testWikipediaAmericanSoundex_6() {
        assertEquals("P236", getStringEncoder().encode("Pfister"));
    }
}
