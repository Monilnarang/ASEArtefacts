package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class Base32Test_Purified {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static final String[][] BASE32_TEST_CASES = { { "", "" }, { "f", "MY======" }, { "fo", "MZXQ====" }, { "foo", "MZXW6===" }, { "foob", "MZXW6YQ=" }, { "fooba", "MZXW6YTB" }, { "foobar", "MZXW6YTBOI======" } };

    static final String[] BASE32_IMPOSSIBLE_CASES = { "MC======", "MZXE====", "MZXWB===", "MZXW6YB=", "MZXW6YTBOC======", "AB======" };

    private static final String[] BASE32_IMPOSSIBLE_CASES_CHUNKED = { "M2======\r\n", "MZX0====\r\n", "MZXW0===\r\n", "MZXW6Y2=\r\n", "MZXW6YTBO2======\r\n" };

    private static final String[] BASE32HEX_IMPOSSIBLE_CASES = { "C2======", "CPN4====", "CPNM1===", "CPNMUO1=", "CPNMUOJ1E2======" };

    private static final byte[] ENCODE_TABLE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7' };

    private static final Object[][] BASE32_BINARY_TEST_CASES;

    static {
        final Hex hex = new Hex();
        try {
            BASE32_BINARY_TEST_CASES = new Object[][] { new Object[] { hex.decode("623a01735836e9a126e12fbf95e013ee6892997c"), "MI5AC42YG3U2CJXBF67ZLYAT5ZUJFGL4" }, new Object[] { hex.decode("623a01735836e9a126e12fbf95e013ee6892997c"), "mi5ac42yg3u2cjxbf67zlyat5zujfgl4" }, new Object[] { hex.decode("739ce42108"), "OOOOIIII" } };
        } catch (final DecoderException de) {
            throw new AssertionError(":(", de);
        }
    }

    private static final String[][] BASE32HEX_TEST_CASES = { { "", "" }, { "f", "CO======" }, { "fo", "CPNG====" }, { "foo", "CPNMU===" }, { "foob", "CPNMUOG=" }, { "fooba", "CPNMUOJ1" }, { "foobar", "CPNMUOJ1E8======" } };

    private static final String[][] BASE32_TEST_CASES_CHUNKED = { { "", "" }, { "f", "MY======\r\n" }, { "fo", "MZXQ====\r\n" }, { "foo", "MZXW6===\r\n" }, { "foob", "MZXW6YQ=\r\n" }, { "fooba", "MZXW6YTB\r\n" }, { "foobar", "MZXW6YTBOI======\r\n" } };

    private static final String[][] BASE32_PAD_TEST_CASES = { { "", "" }, { "f", "MY%%%%%%" }, { "fo", "MZXQ%%%%" }, { "foo", "MZXW6%%%" }, { "foob", "MZXW6YQ%" }, { "fooba", "MZXW6YTB" }, { "foobar", "MZXW6YTBOI%%%%%%" } };

    private static void assertBase32DecodingOfTrailingBits(final int nbits) {
        final Base32 codec = new Base32(0, null, false, BaseNCodec.PAD_DEFAULT, CodecPolicy.STRICT);
        assertTrue(codec.isStrictDecoding());
        assertEquals(CodecPolicy.STRICT, codec.getCodecPolicy());
        final Base32 defaultCodec = new Base32();
        assertFalse(defaultCodec.isStrictDecoding());
        assertEquals(CodecPolicy.LENIENT, defaultCodec.getCodecPolicy());
        final int length = nbits / 5;
        final byte[] encoded = new byte[8];
        Arrays.fill(encoded, 0, length, ENCODE_TABLE[0]);
        Arrays.fill(encoded, length, encoded.length, (byte) '=');
        final int discard = nbits % 8;
        final int emptyBitsMask = (1 << discard) - 1;
        final boolean invalid = length == 1 || length == 3 || length == 6;
        final int last = length - 1;
        for (int i = 0; i < 32; i++) {
            encoded[last] = ENCODE_TABLE[i];
            if (invalid || (i & emptyBitsMask) != 0) {
                assertThrows(IllegalArgumentException.class, () -> codec.decode(encoded), "Final base-32 digit should not be allowed");
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

    @Test
    public void testBuilderCodecPolicy_1() {
        assertEquals(CodecPolicy.LENIENT, Base32.builder().get().getCodecPolicy());
    }

    @Test
    public void testBuilderCodecPolicy_2() {
        assertEquals(CodecPolicy.LENIENT, Base32.builder().setDecodingPolicy(CodecPolicy.LENIENT).get().getCodecPolicy());
    }

    @Test
    public void testBuilderCodecPolicy_3() {
        assertEquals(CodecPolicy.STRICT, Base32.builder().setDecodingPolicy(CodecPolicy.STRICT).get().getCodecPolicy());
    }

    @Test
    public void testBuilderCodecPolicy_4() {
        assertEquals(CodecPolicy.LENIENT, Base32.builder().setDecodingPolicy(CodecPolicy.STRICT).setDecodingPolicy(null).get().getCodecPolicy());
    }

    @Test
    public void testBuilderCodecPolicy_5() {
        assertEquals(CodecPolicy.LENIENT, Base32.builder().setDecodingPolicy(null).get().getCodecPolicy());
    }
}
