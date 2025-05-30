package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.tagging.ar.ArabicTagManager;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArabicTagManagerTest_Parameterized {

    private ArabicTagManager tagManager;

    @Before
    public void setUp() {
        tagManager = new ArabicTagManager();
    }

    @Test
    public void testTagger_5() {
        assertEquals(tagManager.setPronoun("NJ-;M1I-;---", "H"), "NJ-;M1I-;--H");
    }

    @Test
    public void testTagger_8() {
        assertEquals(tagManager.getConjunctionPrefix("V-1;M1I----;W--"), "و");
    }

    @ParameterizedTest
    @MethodSource("Provider_testTagger_1to2")
    public void testTagger_1to2(String param1, String param2, String param3) {
        assertEquals(tagManager.setJar(param2, param3), param1);
    }

    static public Stream<Arguments> Provider_testTagger_1to2() {
        return Stream.of(arguments("NJ-;M1I-;-K-", "NJ-;M1I-;---", "K"), arguments("NJ-;M1I-;---", "NJ-;M1I-;---", "-"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTagger_3to4")
    public void testTagger_3to4(String param1, String param2, String param3) {
        assertEquals(tagManager.setDefinite(param2, param3), param1);
    }

    static public Stream<Arguments> Provider_testTagger_3to4() {
        return Stream.of(arguments("NJ-;M1I-;--L", "NJ-;M1I-;---", "L"), arguments("NJ-;M1I-;--H", "NJ-;M1I-;--H", "L"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTagger_6to7")
    public void testTagger_6to7(String param1, String param2, String param3) {
        assertEquals(tagManager.setConjunction(param2, param3), param1);
    }

    static public Stream<Arguments> Provider_testTagger_6to7() {
        return Stream.of(arguments("NJ-;M1I-;W--", "NJ-;M1I-;---", "W"), arguments("V-1;M1I----;W--", "V-1;M1I----;---", "W"));
    }
}
