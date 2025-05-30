package org.apache.druid.common.utils;

import org.apache.druid.java.util.common.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import java.nio.ByteBuffer;

public class StringUtilsTest_Purified {

    public static final String[] TEST_STRINGS = new String[] { "peach", "péché", "pêche", "sin", "", "☃", "C", "c", "Ç", "ç", "G", "g", "Ğ", "ğ", "I", "ı", "İ", "i", "O", "o", "Ö", "ö", "S", "s", "Ş", "ş", "U", "u", "Ü", "ü", "ä", "\uD841\uDF0E", "\uD841\uDF31", "\uD844\uDC5C", "\uD84F\uDCB7", "\uD860\uDEE2", "\uD867\uDD98", "\u006E\u0303", "\u006E", "\uFB00", "\u0066\u0066", "Å", "\u00C5", "\u212B" };

    @Test
    public void testToUtf8ByteBuffer_1() {
        Assert.assertNull(StringUtils.toUtf8ByteBuffer(null));
    }

    @Test
    public void testToUtf8ByteBuffer_2() {
        Assert.assertEquals(ByteBuffer.allocate(0), StringUtils.toUtf8ByteBuffer(""));
    }

    @Test
    public void testToUtf8ByteBuffer_3() {
        Assert.assertEquals(ByteBuffer.wrap(StringUtils.toUtf8("foo")), StringUtils.toUtf8ByteBuffer("foo"));
    }

    @Test
    public void testToUtf8ByteBuffer_4() {
        Assert.assertEquals(ByteBuffer.wrap(StringUtils.toUtf8("🙂")), StringUtils.toUtf8ByteBuffer("🙂"));
    }
}
