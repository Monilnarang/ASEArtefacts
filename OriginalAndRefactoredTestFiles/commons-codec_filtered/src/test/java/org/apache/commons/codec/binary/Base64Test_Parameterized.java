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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Base64Test_Parameterized {

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
    public void testKnownDecodings_1() {
        assertEquals(FOX_TEXT, new String(Base64.decodeBase64("VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wZWQgb3ZlciB0aGUgbGF6eSBkb2dzLg==".getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_1() {
        assertEquals("VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wZWQgb3ZlciB0aGUgbGF6eSBkb2dzLg==", new String(Base64.encodeBase64(FOX_TEXT.getBytes(CHARSET_UTF8))));
    }

    @Test
    public void testKnownEncodings_2() {
        assertEquals("YmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJs\r\nYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFo\r\nIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBibGFoIGJsYWggYmxhaCBi\r\nbGFoIGJsYWg=\r\n", new String(Base64.encodeBase64Chunked("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah".getBytes(CHARSET_UTF8))));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDecodePadMarkerIndex3_1to2_2_2_2to6")
    public void testDecodePadMarkerIndex3_1to2_2_2_2to6(String param1, String param2) {
        assertEquals(param1, new String(Base64.decodeBase64(param2.getBytes(CHARSET_UTF8))));
    }

    static public Stream<Arguments> Provider_testDecodePadMarkerIndex3_1to2_2_2_2to6() {
        return Stream.of(arguments("AA", "QUE="), arguments("AAA", "QUFB"), arguments("", "===="), arguments("", "====\n"), arguments("It was the best of times, it was the worst of times.", "SXQgd2FzIHRoZSBiZXN0IG9mIHRpbWVzLCBpdCB3YXMgdGhlIHdvcnN0IG9mIHRpbWVzLg=="), arguments("http://jakarta.apache.org/commmons", "aHR0cDovL2pha2FydGEuYXBhY2hlLm9yZy9jb21tbW9ucw=="), arguments("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz", "QWFCYkNjRGRFZUZmR2dIaElpSmpLa0xsTW1Obk9vUHBRcVJyU3NUdFV1VnZXd1h4WXlaeg=="), arguments("{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }", "eyAwLCAxLCAyLCAzLCA0LCA1LCA2LCA3LCA4LCA5IH0="), arguments("xyzzy!", "eHl6enkh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDecodePadOnly_1_1_3_3to4_4to5_5to6_6")
    public void testDecodePadOnly_1_1_3_3to4_4to5_5to6_6(int param1, String param2) {
        assertEquals(param1, Base64.decodeBase64(param2.getBytes(CHARSET_UTF8)).length);
    }

    static public Stream<Arguments> Provider_testDecodePadOnly_1_1_3_3to4_4to5_5to6_6() {
        return Stream.of(arguments(0, "===="), arguments(0, "==="), arguments(0, "=="), arguments(0, "="), arguments(0, ""), arguments(0, "====\n"), arguments(0, "===\n"), arguments(0, "==\n"), arguments(0, "=\n"), arguments(0, "\n"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testKnownEncodings_3to7")
    public void testKnownEncodings_3to7(String param1, String param2) {
        assertEquals(param1, new String(Base64.encodeBase64(param2.getBytes(CHARSET_UTF8))));
    }

    static public Stream<Arguments> Provider_testKnownEncodings_3to7() {
        return Stream.of(arguments("SXQgd2FzIHRoZSBiZXN0IG9mIHRpbWVzLCBpdCB3YXMgdGhlIHdvcnN0IG9mIHRpbWVzLg==", "It was the best of times, it was the worst of times."), arguments("aHR0cDovL2pha2FydGEuYXBhY2hlLm9yZy9jb21tbW9ucw==", "http://jakarta.apache.org/commmons"), arguments("QWFCYkNjRGRFZUZmR2dIaElpSmpLa0xsTW1Obk9vUHBRcVJyU3NUdFV1VnZXd1h4WXlaeg==", "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz"), arguments("eyAwLCAxLCAyLCAzLCA0LCA1LCA2LCA3LCA4LCA5IH0=", "{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }"), arguments("eHl6enkh", "xyzzy!"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRfc4648Section10Decode_1to7")
    public void testRfc4648Section10Decode_1to7(String param1, String param2) {
        assertEquals(param1, StringUtils.newStringUsAscii(Base64.decodeBase64(param2)));
    }

    static public Stream<Arguments> Provider_testRfc4648Section10Decode_1to7() {
        return Stream.of(arguments("", ""), arguments("f", "Zg=="), arguments("fo", "Zm8="), arguments("foo", "Zm9v"), arguments("foob", "Zm9vYg=="), arguments("fooba", "Zm9vYmE="), arguments("foobar", "Zm9vYmFy"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRfc4648Section10Encode_1to7")
    public void testRfc4648Section10Encode_1to7(String param1, String param2) {
        assertEquals(param1, Base64.encodeBase64String(StringUtils.getBytesUtf8(param2)));
    }

    static public Stream<Arguments> Provider_testRfc4648Section10Encode_1to7() {
        return Stream.of(arguments("", ""), arguments("Zg==", "f"), arguments("Zm8=", "fo"), arguments("Zm9v", "foo"), arguments("Zm9vYg==", "foob"), arguments("Zm9vYmE=", "fooba"), arguments("Zm9vYmFy", "foobar"));
    }
}
