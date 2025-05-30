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

public class RefinedSoundexTest_Parameterized extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    @Test
    public void testDifference_1() throws EncoderException {
        assertEquals(0, getStringEncoder().difference(null, null));
    }

    @Test
    public void testEncode_12() {
        assertEquals("D6043", RefinedSoundex.US_ENGLISH.encode("dogs"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDifference_2to12")
    public void testDifference_2to12(int param1, String param2, String param3) throws EncoderException {
        assertEquals(param1, getStringEncoder().difference(param2, param3));
    }

    static public Stream<Arguments> Provider_testDifference_2to12() {
        return Stream.of(arguments(0, "", ""), arguments(0, " ", " "), arguments(6, "Smith", "Smythe"), arguments(3, "Ann", "Andrew"), arguments(1, "Margaret", "Andrew"), arguments(1, "Janet", "Margaret"), arguments(5, "Green", "Greene"), arguments(1, "Blotchet-Halls", "Greene"), arguments(6, "Smith", "Smythe"), arguments(8, "Smithers", "Smythers"), arguments(5, "Anothers", "Brothers"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEncode_1to11")
    public void testEncode_1to11(String param1, String param2) {
        assertEquals(param1, getStringEncoder().encode(param2));
    }

    static public Stream<Arguments> Provider_testEncode_1to11() {
        return Stream.of(arguments("T6036084", "testing"), arguments("T6036084", "TESTING"), arguments("T60", "The"), arguments("Q503", "quick"), arguments("B1908", "brown"), arguments("F205", "fox"), arguments("J408106", "jumped"), arguments("O0209", "over"), arguments("T60", "the"), arguments("L7050", "lazy"), arguments("D6043", "dogs"));
    }
}
