package org.apache.commons.codec.digest;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Random;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.RandomAccessFileMode;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DigestUtilsTest_Purified {

    private static final String EMPTY_STRING = "";

    private final byte[] testData = new byte[DigestUtils.BUFFER_SIZE * DigestUtils.BUFFER_SIZE];

    private Path testFile;

    private Path testRandomAccessFile;

    private RandomAccessFile testRandomAccessFileWrapper;

    private void assumeJava8() {
        assumeTrue(SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_8));
    }

    private void assumeJava9() {
        assumeTrue(SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_9));
    }

    byte[] getTestData() {
        return testData;
    }

    Path getTestPath() {
        return testFile;
    }

    RandomAccessFile getTestRandomAccessFile() {
        return testRandomAccessFileWrapper;
    }

    @BeforeEach
    public void setUp() throws Exception {
        new Random().nextBytes(testData);
        testFile = Files.createTempFile(DigestUtilsTest.class.getName(), ".dat");
        try (OutputStream fos = Files.newOutputStream(testFile)) {
            fos.write(testData);
        }
        testRandomAccessFile = Files.createTempFile(DigestUtilsTest.class.getName(), ".dat");
        try (OutputStream fos = Files.newOutputStream(testRandomAccessFile)) {
            fos.write(testData);
        }
        testRandomAccessFileWrapper = RandomAccessFileMode.READ_WRITE.create(testRandomAccessFile);
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (testRandomAccessFileWrapper != null) {
            testRandomAccessFileWrapper.close();
        }
        Files.deleteIfExists(testFile);
        Files.deleteIfExists(testRandomAccessFile);
    }

    @Test
    public void testIsAvailable_1() {
        assertTrue(DigestUtils.isAvailable(MessageDigestAlgorithms.MD5));
    }

    @Test
    public void testIsAvailable_2() {
        assertFalse(DigestUtils.isAvailable("FOO"));
    }

    @Test
    public void testIsAvailable_3() {
        assertFalse(DigestUtils.isAvailable(null));
    }

    @Test
    public void testMd2Hex_1() throws IOException {
        assertEquals("8350e5a3e24c153df2275c9f80692773", DigestUtils.md2Hex(EMPTY_STRING));
    }

    @Test
    public void testMd2Hex_2() throws IOException {
        assertEquals("32ec01ec4a6dac72c0ab96fb34c0b5d1", DigestUtils.md2Hex("a"));
    }

    @Test
    public void testMd2Hex_3() throws IOException {
        assertEquals("da853b0d3f88d99b30283a69e6ded6bb", DigestUtils.md2Hex("abc"));
    }

    @Test
    public void testMd2Hex_4() throws IOException {
        assertEquals("ab4f496bfb2a530b219ff33031fe06b0", DigestUtils.md2Hex("message digest"));
    }

    @Test
    public void testMd2Hex_5() throws IOException {
        assertEquals("4e8ddff3650292ab5a4108c3aa47940b", DigestUtils.md2Hex("abcdefghijklmnopqrstuvwxyz"));
    }

    @Test
    public void testMd2Hex_6() throws IOException {
        assertEquals("da33def2a42df13975352846c30338cd", DigestUtils.md2Hex("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789"));
    }

    @Test
    public void testMd2Hex_7() throws IOException {
        assertEquals("d5976f79d83d3a0dc9806c3c66f3efd8", DigestUtils.md2Hex("1234567890123456789012345678901234567890" + "1234567890123456789012345678901234567890"));
    }

    @Test
    public void testMd2Hex_8() throws IOException {
        assertEquals(DigestUtils.md2Hex(testData), DigestUtils.md2Hex(new ByteArrayInputStream(testData)));
    }

    @Test
    public void testMd5Hex_1() throws IOException {
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", DigestUtils.md5Hex(EMPTY_STRING));
    }

    @Test
    public void testMd5Hex_2() throws IOException {
        assertEquals("0cc175b9c0f1b6a831c399e269772661", DigestUtils.md5Hex("a"));
    }

    @Test
    public void testMd5Hex_3() throws IOException {
        assertEquals("900150983cd24fb0d6963f7d28e17f72", DigestUtils.md5Hex("abc"));
    }

    @Test
    public void testMd5Hex_4() throws IOException {
        assertEquals("f96b697d7cb7938d525a2f31aaf161d0", DigestUtils.md5Hex("message digest"));
    }

    @Test
    public void testMd5Hex_5() throws IOException {
        assertEquals("c3fcd3d76192e4007dfb496cca67e13b", DigestUtils.md5Hex("abcdefghijklmnopqrstuvwxyz"));
    }

    @Test
    public void testMd5Hex_6() throws IOException {
        assertEquals("d174ab98d277d9f5a5611c2c9f419d9f", DigestUtils.md5Hex("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789"));
    }

    @Test
    public void testMd5Hex_7() throws IOException {
        assertEquals("57edf4a22be3c955ac49da2e2107b67a", DigestUtils.md5Hex("1234567890123456789012345678901234567890" + "1234567890123456789012345678901234567890"));
    }

    @Test
    public void testMd5Hex_8() throws IOException {
        assertEquals(DigestUtils.md5Hex(testData), DigestUtils.md5Hex(new ByteArrayInputStream(testData)));
    }

    @Test
    public void testSha1Hex_1() throws IOException {
        assertEquals("a9993e364706816aba3e25717850c26c9cd0d89d", DigestUtils.sha1Hex("abc"));
    }

    @Test
    public void testSha1Hex_2() throws IOException {
        assertEquals("a9993e364706816aba3e25717850c26c9cd0d89d", DigestUtils.sha1Hex(getBytesUtf8("abc")));
    }

    @Test
    public void testSha1Hex_3() throws IOException {
        assertEquals("84983e441c3bd26ebaae4aa1f95129e5e54670f1", DigestUtils.sha1Hex("abcdbcdecdefdefgefghfghighij" + "hijkijkljklmklmnlmnomnopnopq"));
    }

    @Test
    public void testSha1Hex_4() throws IOException {
        assertEquals(DigestUtils.sha1Hex(testData), DigestUtils.sha1Hex(new ByteArrayInputStream(testData)));
    }

    @Test
    public void testSha224_StringAsHex_1() {
        assertEquals("d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f", new DigestUtils(MessageDigestAlgorithms.SHA_224).digestAsHex(EMPTY_STRING));
    }

    @Test
    public void testSha224_StringAsHex_2() {
        assertEquals("730e109bd7a8a32b1cb9d9a09aa2325d2430587ddbc0c38bad911525", new DigestUtils(MessageDigestAlgorithms.SHA_224).digestAsHex("The quick brown fox jumps over the lazy dog"));
    }

    @Test
    public void testSha256_1() throws IOException {
        assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", DigestUtils.sha256Hex("abc"));
    }

    @Test
    public void testSha256_2() throws IOException {
        assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", DigestUtils.sha256Hex(getBytesUtf8("abc")));
    }

    @Test
    public void testSha256_3() throws IOException {
        assertEquals("248d6a61d20638b8e5c026930c3e6039a33ce45964ff2167f6ecedd419db06c1", DigestUtils.sha256Hex("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq"));
    }

    @Test
    public void testSha256_4() throws IOException {
        assertEquals(DigestUtils.sha256Hex(testData), DigestUtils.sha256Hex(new ByteArrayInputStream(testData)));
    }

    @Test
    public void testSha384_1() throws IOException {
        assertEquals("cb00753f45a35e8bb5a03d699ac65007272c32ab0eded1631a8b605a43ff5bed" + "8086072ba1e7cc2358baeca134c825a7", DigestUtils.sha384Hex("abc"));
    }

    @Test
    public void testSha384_2() throws IOException {
        assertEquals("cb00753f45a35e8bb5a03d699ac65007272c32ab0eded1631a8b605a43ff5bed" + "8086072ba1e7cc2358baeca134c825a7", DigestUtils.sha384Hex(getBytesUtf8("abc")));
    }

    @Test
    public void testSha384_3() throws IOException {
        assertEquals("09330c33f71147e83d192fc782cd1b4753111b173b3b05d22fa08086e3b0f712" + "fcc7c71a557e2db966c3e9fa91746039", DigestUtils.sha384Hex("abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmn" + "hijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu"));
    }

    @Test
    public void testSha384_4() throws IOException {
        assertEquals(DigestUtils.sha384Hex(testData), DigestUtils.sha384Hex(new ByteArrayInputStream(testData)));
    }

    @Test
    public void testSha512_1() {
        assertEquals("ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a" + "2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f", DigestUtils.sha512Hex("abc"));
    }

    @Test
    public void testSha512_2() {
        assertEquals("ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a" + "2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f", DigestUtils.sha512Hex(getBytesUtf8("abc")));
    }

    @Test
    public void testSha512_3() {
        assertEquals("8e959b75dae313da8cf4f72814fc143f8f7779c6eb9f7fa17299aeadb6889018" + "501d289e4900f7e4331b99dec4b5433ac7d329eeb6dd26545e96e55b874be909", DigestUtils.sha512Hex("abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmn" + "hijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testShaHex_1() throws IOException {
        assertEquals("a9993e364706816aba3e25717850c26c9cd0d89d", DigestUtils.shaHex("abc"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testShaHex_2() throws IOException {
        assertEquals("a9993e364706816aba3e25717850c26c9cd0d89d", DigestUtils.shaHex(getBytesUtf8("abc")));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testShaHex_3() throws IOException {
        assertEquals("84983e441c3bd26ebaae4aa1f95129e5e54670f1", DigestUtils.shaHex("abcdbcdecdefdefgefghfghighij" + "hijkijkljklmklmnlmnomnopnopq"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testShaHex_4() throws IOException {
        assertEquals(DigestUtils.shaHex(testData), DigestUtils.shaHex(new ByteArrayInputStream(testData)));
    }
}
