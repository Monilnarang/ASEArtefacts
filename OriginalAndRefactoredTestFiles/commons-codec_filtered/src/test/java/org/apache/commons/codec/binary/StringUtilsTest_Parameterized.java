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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsTest_Parameterized {

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
    public void testEqualsCS2_1() {
        assertTrue(StringUtils.equals("abc", new StringBuilder("abc")));
    }

    @Test
    public void testEqualsCS2_3() {
        assertFalse(StringUtils.equals("abcd", new StringBuilder("abc")));
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

    @ParameterizedTest
    @MethodSource("Provider_testEqualsCS1_4to6")
    public void testEqualsCS1_4to6(String param1, String param2) {
        assertFalse(StringUtils.equals(new StringBuilder(param1), new StringBuilder(param2)));
    }

    static public Stream<Arguments> Provider_testEqualsCS1_4to6() {
        return Stream.of(arguments("abc", "abcd"), arguments("abcd", "abc"), arguments("abc", "ABC"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEqualsCS2_2_4")
    public void testEqualsCS2_2_4(String param1, String param2) {
        assertFalse(StringUtils.equals(new StringBuilder(param2), param1));
    }

    static public Stream<Arguments> Provider_testEqualsCS2_2_4() {
        return Stream.of(arguments("abcd", "abc"), arguments("ABC", "abc"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEqualsString_5to7")
    public void testEqualsString_5to7(String param1, String param2) {
        assertFalse(StringUtils.equals(param1, param2));
    }

    static public Stream<Arguments> Provider_testEqualsString_5to7() {
        return Stream.of(arguments("abc", "abcd"), arguments("abcd", "abc"), arguments("abc", "ABC"));
    }
}
