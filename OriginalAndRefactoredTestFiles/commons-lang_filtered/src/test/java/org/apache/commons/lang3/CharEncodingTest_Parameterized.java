package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Deprecated
public class CharEncodingTest_Parameterized extends AbstractLangTest {

    private void assertSupportedEncoding(final String name) {
        assertTrue(CharEncoding.isSupported(name), "Encoding should be supported: " + name);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_1() {
        assertSupportedEncoding(CharEncoding.ISO_8859_1);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_2() {
        assertSupportedEncoding(CharEncoding.US_ASCII);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_3() {
        assertSupportedEncoding(CharEncoding.UTF_16);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_4() {
        assertSupportedEncoding(CharEncoding.UTF_16BE);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_5() {
        assertSupportedEncoding(CharEncoding.UTF_16LE);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_6() {
        assertSupportedEncoding(CharEncoding.UTF_8);
    }

    @Test
    public void testNotSupported_1() {
        assertFalse(CharEncoding.isSupported(null));
    }

    @Test
    public void testStandardCharsetsEquality_1() {
        assertEquals(StandardCharsets.ISO_8859_1.name(), CharEncoding.ISO_8859_1);
    }

    @Test
    public void testStandardCharsetsEquality_2() {
        assertEquals(StandardCharsets.US_ASCII.name(), CharEncoding.US_ASCII);
    }

    @Test
    public void testStandardCharsetsEquality_3() {
        assertEquals(StandardCharsets.UTF_8.name(), CharEncoding.UTF_8);
    }

    @Test
    public void testStandardCharsetsEquality_4() {
        assertEquals(StandardCharsets.UTF_16.name(), CharEncoding.UTF_16);
    }

    @Test
    public void testStandardCharsetsEquality_5() {
        assertEquals(StandardCharsets.UTF_16BE.name(), CharEncoding.UTF_16BE);
    }

    @Test
    public void testStandardCharsetsEquality_6() {
        assertEquals(StandardCharsets.UTF_16LE.name(), CharEncoding.UTF_16LE);
    }

    @ParameterizedTest
    @MethodSource("Provider_testNotSupported_2to6")
    public void testNotSupported_2to6(String param1) {
        assertFalse(CharEncoding.isSupported(param1));
    }

    static public Stream<Arguments> Provider_testNotSupported_2to6() {
        return Stream.of(arguments(""), arguments(" "), arguments("\t\r\n"), arguments("DOESNOTEXIST"), arguments("this is not a valid encoding name"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSupported_1to3")
    public void testSupported_1to3(String param1) {
        assertTrue(CharEncoding.isSupported(param1));
    }

    static public Stream<Arguments> Provider_testSupported_1to3() {
        return Stream.of(arguments("UTF8"), arguments("UTF-8"), arguments("ASCII"));
    }
}
