package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UnixLineEndingInputStreamTest_Parameterized {

    private String roundtrip(final String msg) throws IOException {
        return roundtrip(msg, true, 0);
    }

    private String roundtrip(final String msg, final boolean ensureLineFeedAtEndOfFile, final int minBufferLen) throws IOException {
        final String string;
        try (ByteArrayInputStream baos = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));
            UnixLineEndingInputStream in = new UnixLineEndingInputStream(baos, ensureLineFeedAtEndOfFile)) {
            final byte[] buf = new byte[minBufferLen + msg.length() * 10];
            string = new String(buf, 0, in.read(buf), StandardCharsets.UTF_8);
        }
        try (ByteArrayInputStream baos = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));
            UnixLineEndingInputStream in = new UnixLineEndingInputStream(baos, ensureLineFeedAtEndOfFile)) {
            final byte[] buf = new byte[minBufferLen + msg.length() * 10];
            assertEquals(string, new String(buf, 0, in.read(buf, 0, buf.length), StandardCharsets.UTF_8));
        }
        try (ByteArrayInputStream baos = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));
            UnixLineEndingInputStream in = new UnixLineEndingInputStream(baos, ensureLineFeedAtEndOfFile)) {
            final int[] buf = new int[minBufferLen + msg.length() * 10];
            if (buf.length > 0) {
                int b;
                int i = 0;
                while ((b = in.read()) != -1) {
                    buf[i++] = b;
                }
                assertEquals(string, new String(buf, 0, i));
            }
        }
        return string;
    }

    @ParameterizedTest
    @MethodSource("Provider_testRetainLineFeed_1to2")
    public void testRetainLineFeed_1to2(String param1, String param2, int param3) throws Exception {
        assertEquals(param1, roundtrip(param2, param3, 0));
    }

    static public Stream<Arguments> Provider_testRetainLineFeed_1to2() {
        return Stream.of(arguments("a\n\n", "a\r\n\r\n", 0), arguments("a", "a", 0));
    }
}
