package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class Base16Test_Purified {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private final Random random = new Random();

    public Random getRandom() {
        return this.random;
    }

    private String toString(final byte[] data) {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            buf.append(data[i]);
            if (i != data.length - 1) {
                buf.append(",");
            }
        }
        return buf.toString();
    }

    @Test
    public void testKnownDecodings_1() {
        assertEquals("The quick brown fox jumped over the lazy dogs.", new String(new Base16(true).decode("54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_2() {
        assertEquals("It was the best of times, it was the worst of times.", new String(new Base16(true).decode("497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_3() {
        assertEquals("http://jakarta.apache.org/commmons", new String(new Base16(true).decode("687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_4() {
        assertEquals("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz", new String(new Base16(true).decode("4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_5() {
        assertEquals("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }", new String(new Base16(true).decode("7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_6() {
        assertEquals("xyzzy!", new String(new Base16(true).decode("78797a7a7921".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_1() {
        assertEquals("54686520717569636b2062726f776e20666f78206a756d706564206f76657220746865206c617a7920646f67732e", new String(new Base16(true).encode("The quick brown fox jumped over the lazy dogs.".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_2() {
        assertEquals("497420776173207468652062657374206f662074696d65732c206974207761732074686520776f727374206f662074696d65732e", new String(new Base16(true).encode("It was the best of times, it was the worst of times.".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_3() {
        assertEquals("687474703a2f2f6a616b617274612e6170616368652e6f72672f636f6d6d6d6f6e73", new String(new Base16(true).encode("http://jakarta.apache.org/commmons".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_4() {
        assertEquals("4161426243634464456546664767486849694a6a4b6b4c6c4d6d4e6e4f6f50705171527253735474557556765777587859795a7a", new String(new Base16(true).encode("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_5() {
        assertEquals("7b20302c20312c20322c20332c20342c20352c20362c20372c20382c2039207d", new String(new Base16(true).encode("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_6() {
        assertEquals("78797a7a7921", new String(new Base16(true).encode("xyzzy!".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testStringToByteVariations_1_testMerged_1() throws DecoderException {
        final Base16 base16 = new Base16();
        final String s1 = "48656C6C6F20576F726C64";
        assertEquals("Hello World", StringUtils.newStringUtf8(base16.decode(s1)), "StringToByte Hello World");
        assertEquals("Hello World", StringUtils.newStringUtf8((byte[]) new Base16().decode((Object) s1)), "StringToByte Hello World");
        assertEquals("Hello World", StringUtils.newStringUtf8(new Base16().decode(s1)), "StringToByte static Hello World");
    }

    @Test
    public void testStringToByteVariations_4_testMerged_2() throws DecoderException {
        final String s2 = "";
        assertEquals("", StringUtils.newStringUtf8(new Base16().decode(s2)), "StringToByte \"\"");
        assertEquals("", StringUtils.newStringUtf8(new Base16().decode(s2)), "StringToByte static \"\"");
    }

    @Test
    public void testStringToByteVariations_6_testMerged_3() throws DecoderException {
        final String s3 = null;
        assertNull(StringUtils.newStringUtf8(new Base16().decode(s3)), "StringToByte null");
        assertNull(StringUtils.newStringUtf8(new Base16().decode(s3)), "StringToByte static null");
    }
}
