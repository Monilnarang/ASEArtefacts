package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HexTest_Purified {

    private static final String BAD_ENCODING_NAME = "UNKNOWN";

    private static final boolean LOG = false;

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    private boolean charsetSanityCheck(final String name) {
        final String source = "the quick brown dog jumped over the lazy fox";
        try {
            final byte[] bytes = source.getBytes(name);
            final String str = new String(bytes, name);
            final boolean equals = source.equals(str);
            if (!equals) {
                log("FAILED charsetSanityCheck=Interesting Java charset oddity: Roundtrip failed for " + name);
            }
            return equals;
        } catch (final UnsupportedEncodingException | UnsupportedOperationException e) {
            if (LOG) {
                log("FAILED charsetSanityCheck=" + name + ", e=" + e);
                log(e);
            }
            return false;
        }
    }

    private void checkDecodeHexByteBufferOddCharacters(final ByteBuffer data) {
        assertThrows(DecoderException.class, () -> new Hex().decode(data));
    }

    private void checkDecodeHexCharArrayOddCharacters(final char[] data) {
        assertThrows(DecoderException.class, () -> Hex.decodeHex(data));
    }

    private void checkDecodeHexCharArrayOddCharacters(final String data) {
        assertThrows(DecoderException.class, () -> Hex.decodeHex(data));
    }

    private ByteBuffer getByteBufferUtf8(final String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer bb = allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        return bb;
    }

    private void log(final String s) {
        if (LOG) {
            System.out.println(s);
            System.out.flush();
        }
    }

    private void log(final Throwable t) {
        if (LOG) {
            t.printStackTrace(System.out);
            System.out.flush();
        }
    }

    @Test
    public void testEncodeHexByteArrayEmpty_1() {
        assertArrayEquals(new char[0], Hex.encodeHex(new byte[0]));
    }

    @Test
    public void testEncodeHexByteArrayEmpty_2() {
        assertArrayEquals(new byte[0], new Hex().encode(new byte[0]));
    }

    @Test
    public void testEncodeHexByteBufferEmpty_1() {
        assertArrayEquals(new char[0], Hex.encodeHex(allocate(0)));
    }

    @Test
    public void testEncodeHexByteBufferEmpty_2() {
        assertArrayEquals(new byte[0], new Hex().encode(allocate(0)));
    }
}
