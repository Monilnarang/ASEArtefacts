package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SoundexTest_Parameterized extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    public void testDifference_1() throws EncoderException {
        assertEquals(0, getStringEncoder().difference(null, null));
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

    @ParameterizedTest
    @MethodSource("Provider_testDifference_2to12")
    public void testDifference_2to12(int param1, String param2, String param3) throws EncoderException {
        assertEquals(param1, getStringEncoder().difference(param2, param3));
    }

    static public Stream<Arguments> Provider_testDifference_2to12() {
        return Stream.of(arguments(0, "", ""), arguments(0, " ", " "), arguments(4, "Smith", "Smythe"), arguments(2, "Ann", "Andrew"), arguments(1, "Margaret", "Andrew"), arguments(0, "Janet", "Margaret"), arguments(4, "Green", "Greene"), arguments(0, "Blotchet-Halls", "Greene"), arguments(4, "Smith", "Smythe"), arguments(4, "Smithers", "Smythers"), arguments(2, "Anothers", "Brothers"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEncodeBasic_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3to4_4_4_4_4_4_4to5_5_5_5_5_5to6_6_6_6_6_6to7_7_7_7_7to8_8_8_8to9_9_9to10_10to16")
    public void testEncodeBasic_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3to4_4_4_4_4_4_4to5_5_5_5_5_5to6_6_6_6_6_6to7_7_7_7_7to8_8_8_8to9_9_9to10_10to16(String param1, String param2) {
        assertEquals(param1, getStringEncoder().encode(param2));
    }

    static public Stream<Arguments> Provider_testEncodeBasic_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3to4_4_4_4_4_4_4to5_5_5_5_5_5to6_6_6_6_6_6to7_7_7_7_7to8_8_8_8to9_9_9to10_10to16() {
        return Stream.of(arguments("T235", "testing"), arguments("T000", "The"), arguments("Q200", "quick"), arguments("B650", "brown"), arguments("F200", "fox"), arguments("J513", "jumped"), arguments("O160", "over"), arguments("T000", "the"), arguments("L200", "lazy"), arguments("D200", "dogs"), arguments("A462", "Allricht"), arguments("E166", "Eberhard"), arguments("E521", "Engebrethson"), arguments("H512", "Heimbach"), arguments("H524", "Hanselmann"), arguments("H431", "Hildebrand"), arguments("K152", "Kavanagh"), arguments("L530", "Lind"), arguments("L222", "Lukaschowsky"), arguments("M235", "McDonnell"), arguments("M200", "McGee"), arguments("O155", "Opnian"), arguments("O155", "Oppenheimer"), arguments("R355", "Riedemanas"), arguments("Z300", "Zita"), arguments("Z325", "Zitzmeinn"), arguments("W252", "Washington"), arguments("L000", "Lee"), arguments("G362", "Gutierrez"), arguments("P236", "Pfister"), arguments("J250", "Jackson"), arguments("T522", "Tymczak"), arguments("V532", "VanDeusen"), arguments("H452", "HOLMES"), arguments("A355", "ADOMOMI"), arguments("V536", "VONDERLEHR"), arguments("B400", "BALL"), arguments("S000", "SHAW"), arguments("J250", "JACKSON"), arguments("S545", "SCANLON"), arguments("S532", "SAINTJOHN"), arguments("A261", "Ashcraft"), arguments("A261", "Ashcroft"), arguments("Y330", "yehudit"), arguments("Y330", "yhwdyt"), arguments("B312", "BOOTHDAVIS"), arguments("B312", "BOOTH-DAVIS"), arguments("S530", "Smith"), arguments("S530", "Smythe"), arguments("A500", "Ann"), arguments("A536", "Andrew"), arguments("J530", "Janet"), arguments("M626", "Margaret"), arguments("S315", "Steven"), arguments("M240", "Michael"), arguments("R163", "Robert"), arguments("L600", "Laura"), arguments("A500", "Anne"), arguments("R163", "Robert"), arguments("R163", "Rupert"), arguments("A261", "Ashcraft"), arguments("A261", "Ashcroft"), arguments("T522", "Tymczak"), arguments("P236", "Pfister"));
    }
}
