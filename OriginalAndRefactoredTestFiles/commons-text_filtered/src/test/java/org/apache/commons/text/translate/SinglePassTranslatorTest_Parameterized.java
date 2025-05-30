package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SinglePassTranslatorTest_Parameterized {

    private final SinglePassTranslator dummyTranslator = new SinglePassTranslator() {

        @Override
        void translateWhole(final CharSequence input, final Writer writer) throws IOException {
        }
    };

    private StringWriter out;

    @BeforeEach
    public void before() {
        out = new StringWriter();
    }

    @ParameterizedTest
    @MethodSource("Provider_testCodePointsAreReturned_1to3")
    public void testCodePointsAreReturned_1to3(int param1, String param2, int param3) throws Exception {
        assertEquals(param1, dummyTranslator.translate(param2, param3, out));
    }

    static public Stream<Arguments> Provider_testCodePointsAreReturned_1to3() {
        return Stream.of(arguments(0, "", 0), arguments(3, "abc", 0), arguments(7, "abcdefg", 0));
    }
}
