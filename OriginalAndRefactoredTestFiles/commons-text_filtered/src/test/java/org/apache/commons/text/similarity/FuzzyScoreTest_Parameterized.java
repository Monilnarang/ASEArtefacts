package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FuzzyScoreTest_Parameterized {

    private static final FuzzyScore ENGLISH_SCORE = new FuzzyScore(Locale.ENGLISH);

    @ParameterizedTest
    @MethodSource("Provider_testGetFuzzyScore_1to7")
    public void testGetFuzzyScore_1to7(int param1, String param2, String param3) {
        assertEquals(param1, ENGLISH_SCORE.fuzzyScore(param2, param3));
    }

    static public Stream<Arguments> Provider_testGetFuzzyScore_1to7() {
        return Stream.of(arguments(0, "", ""), arguments(0, "Workshop", "b"), arguments(1, "Room", "o"), arguments(1, "Workshop", "w"), arguments(2, "Workshop", "ws"), arguments(4, "Workshop", "wo"), arguments(3, "Apache Software Foundation", "asf"));
    }
}
