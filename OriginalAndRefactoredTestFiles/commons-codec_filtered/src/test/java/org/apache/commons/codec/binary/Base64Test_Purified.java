package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class Base64Test_Purified {

    private static final String FOX_BASE64 = "VGhlIH@$#$@%F1aWN@#@#@@rIGJyb3duIGZve\n\r\t%#%#%#%CBqd##$#$W1wZWQgb3ZlciB0aGUgbGF6eSBkb2dzLg==";

    private static final String FOX_TEXT = "The quick brown fox jumped over the lazy dogs.";

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    static final String[] BASE64_IMPOSSIBLE_CASES = { "ZE==", "ZmC=", "Zm9vYE==", "Zm9vYmC=", "AB" };

    private static final byte[] STANDARD_ENCODE_TABLE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

    private static void assertBase64DecodingOfTrailingBits(final int nbits) {
        final Base64 codec = new Base64(0, null, false, CodecPolicy.STRICT);
        assertTrue(codec.isStrictDecoding());
        assertEquals(CodecPolicy.STRICT, codec.getCodecPolicy());
        final Base64 defaultCodec = new Base64();
        assertFalse(defaultCodec.isStrictDecoding());
        assertEquals(CodecPolicy.LENIENT, defaultCodec.getCodecPolicy());
        final int length = nbits / 6;
        final byte[] encoded = new byte[4];
        Arrays.fill(encoded, 0, length, STANDARD_ENCODE_TABLE[0]);
        Arrays.fill(encoded, length, encoded.length, (byte) '=');
        final int discard = nbits % 8;
        final int emptyBitsMask = (1 << discard) - 1;
        final boolean invalid = length == 1;
        final int last = length - 1;
        for (int i = 0; i < 64; i++) {
            encoded[last] = STANDARD_ENCODE_TABLE[i];
            if (invalid || (i & emptyBitsMask) != 0) {
                assertThrows(IllegalArgumentException.class, () -> codec.decode(encoded), "Final base-64 digit should not be allowed");
                final byte[] decoded = defaultCodec.decode(encoded);
                assertFalse(Arrays.equals(encoded, defaultCodec.encode(decoded)));
            } else {
                final byte[] decoded = codec.decode(encoded);
                final int bitsEncoded = i >> discard;
                assertEquals(bitsEncoded, decoded[decoded.length - 1], "Invalid decoding of last character");
                assertArrayEquals(encoded, codec.encode(decoded));
            }
        }
    }

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
    public void testBuilderCodecPolicy_1() {
        assertEquals(CodecPolicy.LENIENT, Base64.builder().get().getCodecPolicy());
    }

    @Test
    public void testBuilderCodecPolicy_2() {
        assertEquals(CodecPolicy.LENIENT, Base64.builder().setDecodingPolicy(CodecPolicy.LENIENT).get().getCodecPolicy());
    }

    @Test
    public void testBuilderCodecPolicy_3() {
        assertEquals(CodecPolicy.STRICT, Base64.builder().setDecodingPolicy(CodecPolicy.STRICT).get().getCodecPolicy());
    }

    @Test
    public void testBuilderCodecPolicy_4() {
        assertEquals(CodecPolicy.LENIENT, Base64.builder().setDecodingPolicy(CodecPolicy.STRICT).setDecodingPolicy(null).get().getCodecPolicy());
    }

    @Test
    public void testBuilderCodecPolicy_5() {
        assertEquals(CodecPolicy.LENIENT, Base64.builder().setDecodingPolicy(null).get().getCodecPolicy());
    }

    @Test
    public void testBuilderUrlSafe_1() {
        assertFalse(Base64.builder().get().isUrlSafe());
    }

    @Test
    public void testBuilderUrlSafe_2() {
        assertFalse(Base64.builder().setUrlSafe(false).get().isUrlSafe());
    }

    @Test
    public void testBuilderUrlSafe_3() {
        assertFalse(Base64.builder().setUrlSafe(true).setUrlSafe(false).get().isUrlSafe());
    }

    @Test
    public void testBuilderUrlSafe_4() {
        assertTrue(Base64.builder().setUrlSafe(false).setUrlSafe(true).get().isUrlSafe());
    }

    @Test
    public void testDecodePadMarkerIndex3_1() {
        assertEquals("AA", new String(Base64.decodeBase64("QUE=".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testDecodePadMarkerIndex3_2() {
        assertEquals("AAA", new String(Base64.decodeBase64("QUFB".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testDecodePadOnly_1() {
        assertEquals(0, Base64.decodeBase64("====".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testDecodePadOnly_2() {
        assertEquals("", new String(Base64.decodeBase64("====".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testDecodePadOnly_3() {
        assertEquals(0, Base64.decodeBase64("===".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testDecodePadOnly_4() {
        assertEquals(0, Base64.decodeBase64("==".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testDecodePadOnly_5() {
        assertEquals(0, Base64.decodeBase64("=".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testDecodePadOnly_6() {
        assertEquals(0, Base64.decodeBase64("".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testDecodePadOnlyChunked_1() {
        assertEquals(0, Base64.decodeBase64("====\n".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testDecodePadOnlyChunked_2() {
        assertEquals("", new String(Base64.decodeBase64("====\n".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testDecodePadOnlyChunked_3() {
        assertEquals(0, Base64.decodeBase64("===\n".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testDecodePadOnlyChunked_4() {
        assertEquals(0, Base64.decodeBase64("==\n".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testDecodePadOnlyChunked_5() {
        assertEquals(0, Base64.decodeBase64("=\n".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testDecodePadOnlyChunked_6() {
        assertEquals(0, Base64.decodeBase64("\n".getBytes(CHARSET_UTF8)).length);
    }

    @Test
    public void testKnownDecodings_1() {
        assertEquals(FOX_TEXT, new String(Base64.decodeBase64("VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wZWQgb3ZlciB0aGUgbGF6eSBkb2dzLg==".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_2() {
        assertEquals("It was the best of times, it was the worst of times.", new String(Base64.decodeBase64("SXQgd2FzIHRoZSBiZXN0IG9mIHRpbWVzLCBpdCB3YXMgdGhlIHdvcnN0IG9mIHRpbWVzLg==".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_3() {
        assertEquals("http://jakarta.apache.org/commmons", new String(Base64.decodeBase64("aHR0cDovL2pha2FydGEuYXBhY2hlLm9yZy9jb21tbW9ucw==".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_4() {
        assertEquals("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz", new String(Base64.decodeBase64("QWFCYkNjRGRFZUZmR2dIaElpSmpLa0xsTW1Obk9vUHBRcVJyU3NUdFV1VnZXd1h4WXlaeg==".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_5() {
        assertEquals("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }", new String(Base64.decodeBase64("eyAwLCAxLCAyLCAzLCA0LCA1LCA2LCA3LCA4LCA5IH0=".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownDecodings_6() {
        assertEquals("xyzzy!", new String(Base64.decodeBase64("eHl6enkh".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_1() {
        assertEquals("VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wZWQgb3ZlciB0aGUgbGF6eSBkb2dzLg==", new String(Base64.encodeBase64(FOX_TEXT.getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_2() {
        assertEquals("YmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJs\r\nYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFo\r\nIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBi\r\nbGFoIGJsYWg=\r\n", new String(Base64.encodeBase64Chunked("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_3() {
        assertEquals("SXQgd2FzIHRoZSBiZXN0IG9mIHRpbWVzLCBpdCB3YXMgdGhlIHdvcnN0IG9mIHRpbWVzLg==", new String(Base64.encodeBase64("It was the best of times, it was the worst of times.".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_4() {
        assertEquals("aHR0cDovL2pha2FydGEuYXBhY2hlLm9yZy9jb21tbW9ucw==", new String(Base64.encodeBase64("http://jakarta.apache.org/commmons".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_5() {
        assertEquals("QWFCYkNjRGRFZUZmR2dIaElpSmpLa0xsTW1Obk9vUHBRcVJyU3NUdFV1VnZXd1h4WXlaeg==", new String(Base64.encodeBase64("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_6() {
        assertEquals("eyAwLCAxLCAyLCAzLCA0LCA1LCA2LCA3LCA4LCA5IH0=", new String(Base64.encodeBase64("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_7() {
        assertEquals("eHl6enkh", new String(Base64.encodeBase64("xyzzy!".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testRfc4648Section10Decode_1() {
        assertEquals("", StringUtils.newStringUsAscii(Base64.decodeBase64("")));
    }

    @Test
    public void testRfc4648Section10Decode_2() {
        assertEquals("f", StringUtils.newStringUsAscii(Base64.decodeBase64("Zg==")));
    }

    @Test
    public void testRfc4648Section10Decode_3() {
        assertEquals("fo", StringUtils.newStringUsAscii(Base64.decodeBase64("Zm8=")));
    }

    @Test
    public void testRfc4648Section10Decode_4() {
        assertEquals("foo", StringUtils.newStringUsAscii(Base64.decodeBase64("Zm9v")));
    }

    @Test
    public void testRfc4648Section10Decode_5() {
        assertEquals("foob", StringUtils.newStringUsAscii(Base64.decodeBase64("Zm9vYg==")));
    }

    @Test
    public void testRfc4648Section10Decode_6() {
        assertEquals("fooba", StringUtils.newStringUsAscii(Base64.decodeBase64("Zm9vYmE=")));
    }

    @Test
    public void testRfc4648Section10Decode_7() {
        assertEquals("foobar", StringUtils.newStringUsAscii(Base64.decodeBase64("Zm9vYmFy")));
    }

    @Test
    public void testRfc4648Section10Encode_1() {
        assertEquals("", Base64.encodeBase64String(StringUtils.getBytesUtf8("")));
    }

    @Test
    public void testRfc4648Section10Encode_2() {
        assertEquals("Zg==", Base64.encodeBase64String(StringUtils.getBytesUtf8("f")));
    }

    @Test
    public void testRfc4648Section10Encode_3() {
        assertEquals("Zm8=", Base64.encodeBase64String(StringUtils.getBytesUtf8("fo")));
    }

    @Test
    public void testRfc4648Section10Encode_4() {
        assertEquals("Zm9v", Base64.encodeBase64String(StringUtils.getBytesUtf8("foo")));
    }

    @Test
    public void testRfc4648Section10Encode_5() {
        assertEquals("Zm9vYg==", Base64.encodeBase64String(StringUtils.getBytesUtf8("foob")));
    }

    @Test
    public void testRfc4648Section10Encode_6() {
        assertEquals("Zm9vYmE=", Base64.encodeBase64String(StringUtils.getBytesUtf8("fooba")));
    }

    @Test
    public void testRfc4648Section10Encode_7() {
        assertEquals("Zm9vYmFy", Base64.encodeBase64String(StringUtils.getBytesUtf8("foobar")));
    }
}
