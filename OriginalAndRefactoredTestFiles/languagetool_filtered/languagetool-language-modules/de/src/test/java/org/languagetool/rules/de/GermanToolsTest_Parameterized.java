package org.languagetool.rules.de;

import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GermanToolsTest_Parameterized {

    @Test
    public void testIsVowel_5() {
        assertFalse(GermanTools.isVowel('b'));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsVowel_1to4")
    public void testIsVowel_1to4(String param1) {
        assertTrue(GermanTools.isVowel(param1));
    }

    static public Stream<Arguments> Provider_testIsVowel_1to4() {
        return Stream.of(arguments("a"), arguments("Y"), arguments("A"), arguments("ö"));
    }
}
