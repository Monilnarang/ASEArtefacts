package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DaitchMokotoffSoundexTest_Parameterized extends AbstractStringEncoderTest<DaitchMokotoffSoundex> {

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

    @ParameterizedTest
    @MethodSource("Provider_testAccentedCharacterFolding_1_1_1_1_1_1to2_2_2_2_2_2to3_3_3_3to4_4_4_4to5_5_5to6_6_6to7_7to8_8to17")
    public void testAccentedCharacterFolding_1_1_1_1_1_1to2_2_2_2_2_2to3_3_3_3to4_4_4_4to5_5_5to6_6_6to7_7to8_8to17(int param1, String param2) {
        assertEquals(param1, soundex(param2));
    }

    static public Stream<Arguments> Provider_testAccentedCharacterFolding_1_1_1_1_1_1to2_2_2_2_2_2to3_3_3_3to4_4_4_4to5_5_5to6_6_6to7_7to8_8to17() {
        return Stream.of(arguments(294795, "Straßburg"), arguments(294795, "Strasburg"), arguments(095600, "Éregon"), arguments(095600, "Eregon"), arguments(054800, "AKSSOL"), arguments("547830|545783|594783|594578", "GERSCHFELD"), arguments(583600, "GOLDEN"), arguments(087930, "Alpert"), arguments(791900, "Breuer"), arguments(579000, "Haber"), arguments(665600, "Mannheim"), arguments(664000, "Mintz"), arguments(370000, "Topf"), arguments(586660, "Kleinmann"), arguments(769600, "Ben Aron"), arguments("097400|097500", "AUERBACH"), arguments("097400|097500", "OHRBACH"), arguments(874400, "LIPSHITZ"), arguments("874400|874500", "LIPPSZYC"), arguments(876450, "LEWINSKY"), arguments(876450, "LEVINSKI"), arguments(486740, "SZLAMAWICZ"), arguments(486740, "SHLAMOVITZ"), arguments("467000|567000", "Ceniow"), arguments(467000, "Tsenyuv"), arguments("587400|587500", "Holubica"), arguments(587400, "Golubitsa"), arguments("746480|794648", "Przemysl"), arguments(746480, "Pshemeshil"), arguments("944744|944745|944754|944755|945744|945745|945754|945755", "Rosochowaciec"), arguments(945744, "Rosokhovatsets"), arguments("734000|739400", "Peters"), arguments("734600|739460", "Peterson"), arguments(645740, "Moskowitz"), arguments(645740, "Moskovitz"), arguments("154600|145460|454600|445460", "Jackson"), arguments("154654|154645|154644|145465|145464|454654|454645|454644|445465|445464", "Jackson-Jackson"), arguments("364000|464000", "ţamas"), arguments("364000|464000", "țamas"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEncodeIgnoreTrimmable_1to2")
    public void testEncodeIgnoreTrimmable_1to2(int param1, String param2) {
        assertEquals(param1, encode(param2));
    }

    static public Stream<Arguments> Provider_testEncodeIgnoreTrimmable_1to2() {
        return Stream.of(arguments(746536, " \t\n\r Washington \t\n\r "), arguments(746536, "Washington"));
    }
}
