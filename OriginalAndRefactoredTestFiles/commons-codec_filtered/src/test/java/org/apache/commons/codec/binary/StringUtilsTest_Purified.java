package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_Purified {

    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    private static final byte[] BYTES_FIXTURE_16BE = { 0, 'a', 0, 'b', 0, 'c' };

    private static final byte[] BYTES_FIXTURE_16LE = { 'a', 0, 'b', 0, 'c', 0 };

    private static final String STRING_FIXTURE = "ABC";

    @Test
    public void testByteBufferUtf8_1() {
        assertNull(StringUtils.getByteBufferUtf8(null), "Should be null safe");
    }

    @Test
    public void testByteBufferUtf8_2() {
        final String text = "asdhjfhsadiogasdjhagsdygfjasfgsdaksjdhfk";
        final ByteBuffer bb = StringUtils.getByteBufferUtf8(text);
        assertArrayEquals(text.getBytes(StandardCharsets.UTF_8), bb.array());
    }

    @Test
    public void testEqualsCS1_1() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), null));
    }

    @Test
    public void testEqualsCS1_2() {
        assertFalse(StringUtils.equals(null, new StringBuilder("abc")));
    }

    @Test
    public void testEqualsCS1_3() {
        assertTrue(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abc")));
    }

    @Test
    public void testEqualsCS1_4() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abcd")));
    }

    @Test
    public void testEqualsCS1_5() {
        assertFalse(StringUtils.equals(new StringBuilder("abcd"), new StringBuilder("abc")));
    }

    @Test
    public void testEqualsCS1_6() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("ABC")));
    }

    @Test
    public void testEqualsCS2_1() {
        assertTrue(StringUtils.equals("abc", new StringBuilder("abc")));
    }

    @Test
    public void testEqualsCS2_2() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "abcd"));
    }

    @Test
    public void testEqualsCS2_3() {
        assertFalse(StringUtils.equals("abcd", new StringBuilder("abc")));
    }

    @Test
    public void testEqualsCS2_4() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "ABC"));
    }

    @Test
    public void testEqualsString_1() {
        assertTrue(StringUtils.equals(null, null));
    }

    @Test
    public void testEqualsString_2() {
        assertFalse(StringUtils.equals("abc", null));
    }

    @Test
    public void testEqualsString_3() {
        assertFalse(StringUtils.equals(null, "abc"));
    }

    @Test
    public void testEqualsString_4() {
        assertTrue(StringUtils.equals("abc", "abc"));
    }

    @Test
    public void testEqualsString_5() {
        assertFalse(StringUtils.equals("abc", "abcd"));
    }

    @Test
    public void testEqualsString_6() {
        assertFalse(StringUtils.equals("abcd", "abc"));
    }

    @Test
    public void testEqualsString_7() {
        assertFalse(StringUtils.equals("abc", "ABC"));
    }

    @Test
    public void testNewStringNullInput_CODEC229_1() {
        assertNull(StringUtils.newStringUtf8(null));
    }

    @Test
    public void testNewStringNullInput_CODEC229_2() {
        assertNull(StringUtils.newStringIso8859_1(null));
    }

    @Test
    public void testNewStringNullInput_CODEC229_3() {
        assertNull(StringUtils.newStringUsAscii(null));
    }

    @Test
    public void testNewStringNullInput_CODEC229_4() {
        assertNull(StringUtils.newStringUtf16(null));
    }

    @Test
    public void testNewStringNullInput_CODEC229_5() {
        assertNull(StringUtils.newStringUtf16Be(null));
    }

    @Test
    public void testNewStringNullInput_CODEC229_6() {
        assertNull(StringUtils.newStringUtf16Le(null));
    }
}
