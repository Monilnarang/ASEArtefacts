package org.languagetool.rules.nl;

import org.junit.Test;
import java.util.Arrays;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ToolsTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testBasicConcatenation_1to7_10_12to15")
    public void testBasicConcatenation_1to7_10_12to15(String param1, String param2, String param3) {
        assertThat(Tools.glueParts(Arrays.asList(param2, param3)), is(param1));
    }

    static public Stream<Arguments> Provider_testBasicConcatenation_1to7_10_12to15() {
        return Stream.of(arguments("huisdeur", "huis", "deur"), arguments("tv-programma", "tv", "programma"), arguments("auto2-deurs", "auto2", "deurs"), arguments("zee-eend", "zee", "eend"), arguments("mms-eend", "mms", "eend"), arguments("EersteKlasservice", "EersteKlas", "service"), arguments("3Dprinter", 3D, "printer"), arguments("auto-pilot", "auto-", "pilot"), arguments("xyZ-xyz", "xyZ", "xyz"), arguments("xyZ-Xyz", "xyZ", "Xyz"), arguments("xyz-Xyz", "xyz", "Xyz"), arguments("xxx-z-yyy", "xxx-z", "yyy"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testBasicConcatenation_8to9_11")
    public void testBasicConcatenation_8to9_11(String param1, String param2, String param3, String param4) {
        assertThat(Tools.glueParts(Arrays.asList(param2, param3, param4)), is(param1));
    }

    static public Stream<Arguments> Provider_testBasicConcatenation_8to9_11() {
        return Stream.of(arguments("grootmoederhuis", "groot", "moeder", "huis"), arguments("sport-tv-uitzending", "sport", "tv", "uitzending"), arguments("foto-5dcamera", "foto", 5d, "camera"));
    }
}
