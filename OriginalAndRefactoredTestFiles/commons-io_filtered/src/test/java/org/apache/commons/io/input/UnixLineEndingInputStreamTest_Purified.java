package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class UnixLineEndingInputStreamTest_Purified {

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

    @Test
    public void testRetainLineFeed_1() throws Exception {
        assertEquals("a\n\n", roundtrip("a\r\n\r\n", false, 0));
    }

    @Test
    public void testRetainLineFeed_2() throws Exception {
        assertEquals("a", roundtrip("a", false, 0));
    }
}
