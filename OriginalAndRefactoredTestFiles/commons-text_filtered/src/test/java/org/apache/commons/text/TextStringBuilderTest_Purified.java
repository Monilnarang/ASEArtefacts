package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import org.apache.commons.io.output.NullAppendable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class TextStringBuilderTest_Purified {

    private static final class MockReadable implements Readable {

        private final CharBuffer src;

        MockReadable(final String src) {
            this.src = CharBuffer.wrap(src);
        }

        @Override
        public int read(final CharBuffer cb) throws IOException {
            return src.read(cb);
        }
    }

    static final StringMatcher A_NUMBER_MATCHER = (buffer, start, bufferStart, bufferEnd) -> {
        if (buffer[start] == 'A') {
            start++;
            if (start < bufferEnd && buffer[start] >= '0' && buffer[start] <= '9') {
                return 2;
            }
        }
        return 0;
    };

    private static void fill(final TextStringBuilder sb, final int length) {
        sb.clear();
        final int limit = Math.min(64, length);
        for (int i = 0; i < limit; i++) {
            sb.append(' ');
        }
        while (sb.length() * 2L <= length) {
            sb.append(sb);
        }
        sb.append(sb, 0, length - sb.length());
        assertEquals(length, sb.length(), "Expected the buffer to be full to the given length");
    }

    @Test
    public void testAppendCharBufferNull_1() throws Exception {
        final TextStringBuilder sb = new TextStringBuilder("1234567890");
        final CharBuffer buffer = null;
        sb.append(buffer);
        assertEquals("1234567890", sb.toString());
    }

    @Test
    public void testAppendCharBufferNull_2() throws Exception {
        final TextStringBuilder sb1 = new TextStringBuilder("1234567890");
        assertEquals("1234567890", sb1.toString());
    }

    @Test
    public void testAppendCharSequence_1_testMerged_1() {
        final CharSequence obj0 = null;
        final TextStringBuilder sb0 = new TextStringBuilder();
        assertEquals("", sb0.append(obj0).toString());
        final TextStringBuilder sb5 = new TextStringBuilder();
        assertEquals("", sb5.append(obj0, 0, 0).toString());
    }

    @Test
    public void testAppendCharSequence_2() {
        final CharSequence obj1 = new TextStringBuilder("test1");
        final TextStringBuilder sb1 = new TextStringBuilder();
        assertEquals("test1", sb1.append(obj1).toString());
    }

    @Test
    public void testAppendCharSequence_3() {
        final CharSequence obj2 = new StringBuilder("test2");
        final TextStringBuilder sb2 = new TextStringBuilder();
        assertEquals("test2", sb2.append(obj2).toString());
    }

    @Test
    public void testAppendCharSequence_4() {
        final CharSequence obj3 = new StringBuffer("test3");
        final TextStringBuilder sb3 = new TextStringBuilder();
        assertEquals("test3", sb3.append(obj3).toString());
    }

    @Test
    public void testAppendCharSequence_5() {
        final CharBuffer obj4 = CharBuffer.wrap("test4".toCharArray());
        final TextStringBuilder sb4 = new TextStringBuilder();
        assertEquals("test4", sb4.append(obj4).toString());
    }

    @Test
    public void testConstructors_1_testMerged_1() {
        final TextStringBuilder sb0 = new TextStringBuilder();
        assertEquals(32, sb0.capacity());
        assertEquals(0, sb0.length());
        assertEquals(0, sb0.size());
    }

    @Test
    public void testConstructors_19_testMerged_2() {
        final TextStringBuilder sb6 = new TextStringBuilder("");
        assertEquals(32, sb6.capacity());
        assertEquals(0, sb6.length());
        assertEquals(0, sb6.size());
    }

    @Test
    public void testConstructors_4_testMerged_3() {
        final TextStringBuilder sb1 = new TextStringBuilder(32);
        assertEquals(32, sb1.capacity());
        assertEquals(0, sb1.length());
        assertEquals(0, sb1.size());
    }

    @Test
    public void testConstructors_22_testMerged_4() {
        final TextStringBuilder sb7 = new TextStringBuilder("foo");
        assertEquals(35, sb7.capacity());
        assertEquals(3, sb7.length());
        assertEquals(3, sb7.size());
    }

    @Test
    public void testConstructors_7_testMerged_5() {
        final TextStringBuilder sb2 = new TextStringBuilder(0);
        assertEquals(32, sb2.capacity());
        assertEquals(0, sb2.length());
        assertEquals(0, sb2.size());
    }

    @Test
    public void testConstructors_10_testMerged_6() {
        final TextStringBuilder sb3 = new TextStringBuilder(-1);
        assertEquals(32, sb3.capacity());
        assertEquals(0, sb3.length());
        assertEquals(0, sb3.size());
    }

    @Test
    public void testConstructors_13_testMerged_7() {
        final TextStringBuilder sb4 = new TextStringBuilder(1);
        assertEquals(1, sb4.capacity());
        assertEquals(0, sb4.length());
        assertEquals(0, sb4.size());
    }

    @Test
    public void testConstructors_16_testMerged_8() {
        final TextStringBuilder sb5 = new TextStringBuilder((String) null);
        assertEquals(32, sb5.capacity());
        assertEquals(0, sb5.length());
        assertEquals(0, sb5.size());
    }

    @Test
    public void testEqualsIgnoreCase_1_testMerged_1() {
        final TextStringBuilder sb1 = new TextStringBuilder();
        final TextStringBuilder sb2 = new TextStringBuilder();
        assertTrue(sb1.equalsIgnoreCase(sb1));
        assertTrue(sb1.equalsIgnoreCase(sb2));
        assertTrue(sb2.equalsIgnoreCase(sb2));
        sb1.append("abc");
        assertFalse(sb1.equalsIgnoreCase(sb2));
    }

    @Test
    public void testEqualsIgnoreCase_10_testMerged_2() {
        final Locale turkish = Locale.forLanguageTag("tr");
        assertTrue(new TextStringBuilder("title").equalsIgnoreCase(new TextStringBuilder("title".toLowerCase(turkish))));
        assertTrue(new TextStringBuilder("title").equalsIgnoreCase(new TextStringBuilder("TITLE".toLowerCase(turkish))));
        assertTrue(new TextStringBuilder("TITLE").equalsIgnoreCase(new TextStringBuilder("TITLE".toLowerCase(turkish))));
        assertTrue(new TextStringBuilder("TITLE").equalsIgnoreCase(new TextStringBuilder("title".toLowerCase(turkish))));
        assertTrue(new TextStringBuilder("TITLE").equalsIgnoreCase(new TextStringBuilder("TITLE".toUpperCase(turkish))));
    }

    @Test
    public void testHashCode_1_testMerged_1() {
        final TextStringBuilder sb = new TextStringBuilder();
        final int hc1a = sb.hashCode();
        final int hc1b = sb.hashCode();
        assertEquals(hc1a, hc1b);
        final int emptyHc = Arrays.hashCode(sb.getBuffer());
        assertNotEquals(emptyHc, hc1a);
        sb.append("abc");
        final int hc2a = sb.hashCode();
        final int hc2b = sb.hashCode();
        assertEquals(hc2a, hc2b);
    }

    @Test
    public void testHashCode_4_testMerged_2() {
        final TextStringBuilder sb2 = new TextStringBuilder(100);
        final TextStringBuilder sb3 = new TextStringBuilder(10);
        final int hc2 = sb2.hashCode();
        final int hc3 = sb3.hashCode();
        assertEquals(hc2, hc3);
        sb2.append("abc");
        sb3.append("abc");
        final int hc2b2 = sb2.hashCode();
        final int hc3b2 = sb3.hashCode();
        assertEquals(hc2b2, hc3b2);
    }
}
