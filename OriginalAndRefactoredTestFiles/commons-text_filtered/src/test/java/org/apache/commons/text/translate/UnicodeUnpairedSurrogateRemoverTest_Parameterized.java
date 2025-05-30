package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.CharArrayWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UnicodeUnpairedSurrogateRemoverTest_Parameterized {

    final UnicodeUnpairedSurrogateRemover subject = new UnicodeUnpairedSurrogateRemover();

    final CharArrayWriter writer = new CharArrayWriter();

    @ParameterizedTest
    @MethodSource("Provider_testInvalidCharacters_1to2")
    public void testInvalidCharacters_1to2(String param1) throws IOException {
        assertTrue(subject.translate(param1, writer));
    }

    static public Stream<Arguments> Provider_testInvalidCharacters_1to2() {
        return Stream.of(arguments("0xd800"), arguments("0xdfff"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvalidCharacters_3_3")
    public void testInvalidCharacters_3_3(int param1) throws IOException {
        assertEquals(param1, writer.size());
    }

    static public Stream<Arguments> Provider_testInvalidCharacters_3_3() {
        return Stream.of(arguments(0), arguments(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidCharacters_1to2")
    public void testValidCharacters_1to2(String param1) throws IOException {
        assertFalse(subject.translate(param1, writer));
    }

    static public Stream<Arguments> Provider_testValidCharacters_1to2() {
        return Stream.of(arguments("0xd7ff"), arguments("0xe000"));
    }
}
