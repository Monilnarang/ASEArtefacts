package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_Purified {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static final int BIT_0 = 0x01;

    private static final int BIT_1 = 0x02;

    private static final int BIT_2 = 0x04;

    private static final int BIT_3 = 0x08;

    private static final int BIT_4 = 0x10;

    private static final int BIT_5 = 0x20;

    private static final int BIT_6 = 0x40;

    private static final int BIT_7 = 0x80;

    BinaryCodec instance;

    void assertDecodeObject(final byte[] bits, final String encodeMe) throws DecoderException {
        byte[] decoded;
        decoded = (byte[]) instance.decode(encodeMe);
        assertEquals(new String(bits), new String(decoded));
        if (encodeMe == null) {
            decoded = instance.decode((byte[]) null);
        } else {
            decoded = (byte[]) instance.decode((Object) encodeMe.getBytes(CHARSET_UTF8));
        }
        assertEquals(new String(bits), new String(decoded));
        if (encodeMe == null) {
            decoded = (byte[]) instance.decode((char[]) null);
        } else {
            decoded = (byte[]) instance.decode(encodeMe.toCharArray());
        }
        assertEquals(new String(bits), new String(decoded));
    }

    @BeforeEach
    public void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.instance = null;
    }

    @Test
    public void testDecodeObject_1_testMerged_1() throws Exception {
        byte[] bits;
        bits = new byte[1];
        assertDecodeObject(bits, "00000000");
        assertDecodeObject(bits, "00000001");
        assertDecodeObject(bits, "00000011");
        assertDecodeObject(bits, "00000111");
        assertDecodeObject(bits, "00001111");
        assertDecodeObject(bits, "00011111");
        assertDecodeObject(bits, "00111111");
        assertDecodeObject(bits, "01111111");
        assertDecodeObject(bits, "11111111");
        bits = new byte[2];
        assertDecodeObject(bits, "0000000011111111");
        assertDecodeObject(bits, "0000000111111111");
        assertDecodeObject(bits, "0000001111111111");
        assertDecodeObject(bits, "0000011111111111");
        assertDecodeObject(bits, "0000111111111111");
        assertDecodeObject(bits, "0001111111111111");
        assertDecodeObject(bits, "0011111111111111");
        assertDecodeObject(bits, "0111111111111111");
        assertDecodeObject(bits, "1111111111111111");
    }

    @Test
    public void testDecodeObject_19() throws Exception {
        assertDecodeObject(new byte[0], null);
    }

    @Test
    public void testFromAsciiByteArray_1() {
        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
    }

    @Test
    public void testFromAsciiByteArray_2() {
        assertEquals(0, BinaryCodec.fromAscii(new byte[0]).length);
    }

    @Test
    public void testFromAsciiByteArray_3_testMerged_3() {
        byte[] bits = new byte[1];
        byte[] decoded = BinaryCodec.fromAscii("00000000".getBytes(CHARSET_UTF8));
        assertEquals(new String(bits), new String(decoded));
    }

    @Test
    public void testFromAsciiByteArray_21() {
        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
    }

    @Test
    public void testFromAsciiCharArray_1() {
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
    }

    @Test
    public void testFromAsciiCharArray_2() {
        assertEquals(0, BinaryCodec.fromAscii(new char[0]).length);
    }

    @Test
    public void testFromAsciiCharArray_3_testMerged_3() {
        byte[] bits = new byte[1];
        byte[] decoded = BinaryCodec.fromAscii("00000000".toCharArray());
        assertEquals(new String(bits), new String(decoded));
    }

    @Test
    public void testFromAsciiCharArray_21() {
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
    }
}
