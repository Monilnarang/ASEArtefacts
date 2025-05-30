package org.languagetool.tagging.ro;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class RomanianTaggerDiacriticsTest_Parameterized extends AbstractRomanianTaggerTest {

    @Override
    protected RomanianTagger createTagger() {
        return new RomanianTagger("/ro/test_diacritics.dict");
    }

    @ParameterizedTest
    @MethodSource("Provider_testTaggerMerseseram_1_1to2_2to3_3")
    public void testTaggerMerseseram_1_1to2_2to3_3(String param1, String param2, int param3) throws Exception {
        assertHasLemmaAndPos(param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testTaggerMerseseram_1_1to2_2to3_3() {
        return Stream.of(arguments("făcusem", "face", 004), arguments("cuțitul", "cuțit", 002), arguments("merseserăm", "merge", 002), arguments("cușcă", "cușcă", 001), arguments("cuțit", "cuțit", 001), arguments("cuțitul", "cuțit", 002));
    }
}
