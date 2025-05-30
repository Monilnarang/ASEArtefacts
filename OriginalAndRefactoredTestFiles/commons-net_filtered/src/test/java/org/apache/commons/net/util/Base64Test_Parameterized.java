package org.apache.commons.net.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import org.apache.commons.lang3.ArrayFill;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings({ "deprecation" })
public class Base64Test_Parameterized {

    private static String toString(final byte[] encodedData) {
        return encodedData != null ? new String(encodedData, StandardCharsets.UTF_8) : null;
    }

    private void checkDecoders(final String expected, final byte[] actual) {
        final byte[] decoded = Base64.decodeBase64(actual);
        assertEquals(expected, toString(decoded));
        assertEquals(expected, toString(actual != null ? getJreDecoder().decode(actual) : null));
        assertEquals(expected, toString(new Base64().decode(actual)));
    }

    private void checkDecoders(final String expected, final String actual) {
        final byte[] decoded = Base64.decodeBase64(actual);
        assertEquals(expected, new String(decoded));
        assertEquals(expected, toString(decoded));
        assertEquals(expected, toString(actual != null ? getJreDecoder().decode(actual) : null));
        assertEquals(expected, toString(new Base64().decode(actual)));
    }

    private Decoder getJreDecoder() {
        return java.util.Base64.getDecoder();
    }

    private Encoder getJreEncoder() {
        return java.util.Base64.getEncoder();
    }

    private Encoder getJreMimeEncoder() {
        return java.util.Base64.getMimeEncoder();
    }

    private Encoder getJreMimeEncoder(final int lineLength, final byte[] lineSeparator) {
        return java.util.Base64.getMimeEncoder(lineLength, lineSeparator);
    }

    private Encoder getJreUrlEncoder() {
        return java.util.Base64.getUrlEncoder();
    }

    @Test
    public void testIsBase64_3() {
        assertFalse(Base64.isBase64((byte) ' '));
    }

    @Test
    public void testIsBase64_4() {
        assertFalse(Base64.isBase64((byte) -1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsBase64_1to2")
    public void testIsBase64_1to2(String param1) {
        assertTrue(Base64.isBase64((byte) param1));
    }

    static public Stream<Arguments> Provider_testIsBase64_1to2() {
        return Stream.of(arguments("="), arguments("b"));
    }
}
