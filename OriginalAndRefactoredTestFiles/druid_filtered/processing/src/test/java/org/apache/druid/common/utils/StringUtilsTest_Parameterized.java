package org.apache.druid.common.utils;

import org.apache.druid.java.util.common.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import java.nio.ByteBuffer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsTest_Parameterized {

    public static final String[] TEST_STRINGS = new String[] { "peach", "péché", "pêche", "sin", "", "☃", "C", "c", "Ç", "ç", "G", "g", "Ğ", "ğ", "I", "ı", "İ", "i", "O", "o", "Ö", "ö", "S", "s", "Ş", "ş", "U", "u", "Ü", "ü", "ä", "\uD841\uDF0E", "\uD841\uDF31", "\uD844\uDC5C", "\uD84F\uDCB7", "\uD860\uDEE2", "\uD867\uDD98", "\u006E\u0303", "\u006E", "\uFB00", "\u0066\u0066", "Å", "\u00C5", "\u212B" };

    @Test
    public void testToUtf8ByteBuffer_1() {
        Assert.assertNull(StringUtils.toUtf8ByteBuffer(null));
    }

    @Test
    public void testToUtf8ByteBuffer_2() {
        Assert.assertEquals(ByteBuffer.allocate(0), StringUtils.toUtf8ByteBuffer(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToUtf8ByteBuffer_3to4")
    public void testToUtf8ByteBuffer_3to4(String param1, String param2) {
        Assert.assertEquals(ByteBuffer.wrap(StringUtils.toUtf8(param2)), StringUtils.toUtf8ByteBuffer(param1));
    }

    static public Stream<Arguments> Provider_testToUtf8ByteBuffer_3to4() {
        return Stream.of(arguments("foo", "foo"), arguments("🙂", "🙂"));
    }
}
