package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.jupiter.api.Test;

public class HmacUtilsTest_Purified {

    @SuppressWarnings("deprecation")
    @Test
    public void testGetHMac_1() {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_MD5_RESULT_BYTES, HmacUtils.getHmacMd5(HmacAlgorithmsTest.STANDARD_KEY_BYTES).doFinal(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetHMac_2() {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA1_RESULT_BYTES, HmacUtils.getHmacSha1(HmacAlgorithmsTest.STANDARD_KEY_BYTES).doFinal(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetHMac_3() {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA256_RESULT_BYTES, HmacUtils.getHmacSha256(HmacAlgorithmsTest.STANDARD_KEY_BYTES).doFinal(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetHMac_4() {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA384_RESULT_BYTES, HmacUtils.getHmacSha384(HmacAlgorithmsTest.STANDARD_KEY_BYTES).doFinal(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetHMac_5() {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA512_RESULT_BYTES, HmacUtils.getHmacSha512(HmacAlgorithmsTest.STANDARD_KEY_BYTES).doFinal(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testHmacMd5Hex_1() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_MD5_RESULT_STRING, HmacUtils.hmacMd5Hex(HmacAlgorithmsTest.STANDARD_KEY_STRING, "The quick brown fox jumps over the lazy dog"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testHmacMd5Hex_2() throws IOException {
        assertEquals("750c783e6ab0b503eaa86e310a5db738", HmacUtils.hmacMd5Hex("Jefe", "what do ya want for nothing?"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testHmacMd5Hex_3() throws IOException {
        assertEquals("750c783e6ab0b503eaa86e310a5db738", HmacUtils.hmacMd5Hex("Jefe".getBytes(), new ByteArrayInputStream("what do ya want for nothing?".getBytes())));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testHmacSha1Hex_1() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA1_RESULT_STRING, HmacUtils.hmacSha1Hex(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testHmacSha1Hex_2() throws IOException {
        assertEquals("f42bb0eeb018ebbd4597ae7213711ec60760843f", HmacUtils.hmacSha1Hex(HmacAlgorithmsTest.STANDARD_KEY_STRING, ""));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testHmacSha1Hex_3() throws IOException {
        assertEquals("effcdf6ae5eb2fa2d27416d5f184df9c259a7c79", HmacUtils.hmacSha1Hex("Jefe", "what do ya want for nothing?"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testHmacSha1Hex_4() throws IOException {
        assertEquals("effcdf6ae5eb2fa2d27416d5f184df9c259a7c79", HmacUtils.hmacSha1Hex("Jefe".getBytes(), new ByteArrayInputStream("what do ya want for nothing?".getBytes())));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMd5HMac_1() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_MD5_RESULT_BYTES, HmacUtils.hmacMd5(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMd5HMac_2() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_MD5_RESULT_BYTES, HmacUtils.hmacMd5(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMd5HMac_3() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_MD5_RESULT_BYTES, HmacUtils.hmacMd5(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMd5HMac_4() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_MD5_RESULT_STRING, HmacUtils.hmacMd5Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMd5HMac_5() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_MD5_RESULT_STRING, HmacUtils.hmacMd5Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMd5HMac_6() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_MD5_RESULT_STRING, HmacUtils.hmacMd5Hex(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha1HMac_1() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA1_RESULT_BYTES, HmacUtils.hmacSha1(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha1HMac_2() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA1_RESULT_BYTES, HmacUtils.hmacSha1(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha1HMac_3() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA1_RESULT_BYTES, HmacUtils.hmacSha1(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha1HMac_4() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA1_RESULT_STRING, HmacUtils.hmacSha1Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha1HMac_5() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA1_RESULT_STRING, HmacUtils.hmacSha1Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha1HMac_6() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA1_RESULT_STRING, HmacUtils.hmacSha1Hex(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha256HMac_1() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA256_RESULT_BYTES, HmacUtils.hmacSha256(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha256HMac_2() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA256_RESULT_BYTES, HmacUtils.hmacSha256(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha256HMac_3() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA256_RESULT_BYTES, HmacUtils.hmacSha256(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha256HMac_4() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA256_RESULT_STRING, HmacUtils.hmacSha256Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha256HMac_5() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA256_RESULT_STRING, HmacUtils.hmacSha256Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha256HMac_6() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA256_RESULT_STRING, HmacUtils.hmacSha256Hex(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha384HMac_1() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA384_RESULT_BYTES, HmacUtils.hmacSha384(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha384HMac_2() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA384_RESULT_BYTES, HmacUtils.hmacSha384(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha384HMac_3() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA384_RESULT_BYTES, HmacUtils.hmacSha384(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha384HMac_4() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA384_RESULT_STRING, HmacUtils.hmacSha384Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha384HMac_5() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA384_RESULT_STRING, HmacUtils.hmacSha384Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha384HMac_6() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA384_RESULT_STRING, HmacUtils.hmacSha384Hex(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha512HMac_1() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA512_RESULT_BYTES, HmacUtils.hmacSha512(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha512HMac_2() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA512_RESULT_BYTES, HmacUtils.hmacSha512(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha512HMac_3() throws IOException {
        assertArrayEquals(HmacAlgorithmsTest.STANDARD_SHA512_RESULT_BYTES, HmacUtils.hmacSha512(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha512HMac_4() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA512_RESULT_STRING, HmacUtils.hmacSha512Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, HmacAlgorithmsTest.STANDARD_PHRASE_BYTES));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha512HMac_5() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA512_RESULT_STRING, HmacUtils.hmacSha512Hex(HmacAlgorithmsTest.STANDARD_KEY_BYTES, new ByteArrayInputStream(HmacAlgorithmsTest.STANDARD_PHRASE_BYTES)));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSha512HMac_6() throws IOException {
        assertEquals(HmacAlgorithmsTest.STANDARD_SHA512_RESULT_STRING, HmacUtils.hmacSha512Hex(HmacAlgorithmsTest.STANDARD_KEY_STRING, HmacAlgorithmsTest.STANDARD_PHRASE_STRING));
    }
}
