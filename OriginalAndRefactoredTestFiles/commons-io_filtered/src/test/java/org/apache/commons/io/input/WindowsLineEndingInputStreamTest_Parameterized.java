package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WindowsLineEndingInputStreamTest_Parameterized {

    private String roundtripReadByte(final String msg) throws IOException {
        return roundtripReadByte(msg, true);
    }

    private String roundtripReadByte(final String msg, final boolean ensure) throws IOException {
        try (WindowsLineEndingInputStream lf = new WindowsLineEndingInputStream(CharSequenceInputStream.builder().setCharSequence(msg).setCharset(StandardCharsets.UTF_8).get(), ensure)) {
            final byte[] buf = new byte[100];
            int i = 0;
            while (i < buf.length) {
                final int read = lf.read();
                if (read < 0) {
                    break;
                }
                buf[i++] = (byte) read;
            }
            return new String(buf, 0, i, StandardCharsets.UTF_8);
        }
    }

    private String roundtripReadByteArray(final String msg) throws IOException {
        return roundtripReadByteArray(msg, true);
    }

    private String roundtripReadByteArray(final String msg, final boolean ensure) throws IOException {
        try (WindowsLineEndingInputStream lf = new WindowsLineEndingInputStream(CharSequenceInputStream.builder().setCharSequence(msg).setCharset(StandardCharsets.UTF_8).get(), ensure)) {
            final byte[] buf = new byte[100];
            final int read = lf.read(buf);
            return new String(buf, 0, read, StandardCharsets.UTF_8);
        }
    }

    private String roundtripReadByteArrayIndex(final String msg) throws IOException {
        return roundtripReadByteArrayIndex(msg, true);
    }

    private String roundtripReadByteArrayIndex(final String msg, final boolean ensure) throws IOException {
        try (WindowsLineEndingInputStream lf = new WindowsLineEndingInputStream(CharSequenceInputStream.builder().setCharSequence(msg).setCharset(StandardCharsets.UTF_8).get(), ensure)) {
            final byte[] buf = new byte[100];
            final int read = lf.read(buf, 0, 100);
            return new String(buf, 0, read, StandardCharsets.UTF_8);
        }
    }

    @Test
    public void testRetainLineFeed_ByteArrayIndex_2() throws Exception {
        assertEquals("a", roundtripReadByteArrayIndex("a", false));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRetainLineFeed_Byte_1to2")
    public void testRetainLineFeed_Byte_1to2(String param1, String param2) throws Exception {
        assertEquals(param1, roundtripReadByte(param2, false));
    }

    static public Stream<Arguments> Provider_testRetainLineFeed_Byte_1to2() {
        return Stream.of(arguments("a\r\n\r\n", "a\r\n\r\n"), arguments("a", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRetainLineFeed_ByteArray_1_1to2")
    public void testRetainLineFeed_ByteArray_1_1to2(String param1, String param2) throws Exception {
        assertEquals(param1, roundtripReadByteArray(param2, false));
    }

    static public Stream<Arguments> Provider_testRetainLineFeed_ByteArray_1_1to2() {
        return Stream.of(arguments("a\r\n\r\n", "a\r\n\r\n"), arguments("a", "a"), arguments("a\r\n\r\n", "a\r\n\r\n"));
    }
}
