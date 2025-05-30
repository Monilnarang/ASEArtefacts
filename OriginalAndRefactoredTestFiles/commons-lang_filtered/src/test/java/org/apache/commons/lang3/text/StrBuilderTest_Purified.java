package org.apache.commons.lang3.text;

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
import java.nio.CharBuffer;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrBuilderTest_Purified extends AbstractLangTest {

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

    static final StrMatcher A_NUMBER_MATCHER = new StrMatcher() {

        @Override
        public int isMatch(final char[] buffer, int pos, final int bufferStart, final int bufferEnd) {
            if (buffer[pos] == 'A') {
                pos++;
                if (pos < bufferEnd && buffer[pos] >= '0' && buffer[pos] <= '9') {
                    return 2;
                }
            }
            return 0;
        }
    };

    @Test
    public void testConstructors_1_testMerged_1() {
        final StrBuilder sb0 = new StrBuilder();
        assertEquals(32, sb0.capacity());
        assertEquals(0, sb0.length());
        assertEquals(0, sb0.size());
    }

    @Test
    public void testConstructors_19_testMerged_2() {
        final StrBuilder sb6 = new StrBuilder("");
        assertEquals(32, sb6.capacity());
        assertEquals(0, sb6.length());
        assertEquals(0, sb6.size());
    }

    @Test
    public void testConstructors_4_testMerged_3() {
        final StrBuilder sb1 = new StrBuilder(32);
        assertEquals(32, sb1.capacity());
        assertEquals(0, sb1.length());
        assertEquals(0, sb1.size());
    }

    @Test
    public void testConstructors_22_testMerged_4() {
        final StrBuilder sb7 = new StrBuilder("foo");
        assertEquals(35, sb7.capacity());
        assertEquals(3, sb7.length());
        assertEquals(3, sb7.size());
    }

    @Test
    public void testConstructors_7_testMerged_5() {
        final StrBuilder sb2 = new StrBuilder(0);
        assertEquals(32, sb2.capacity());
        assertEquals(0, sb2.length());
        assertEquals(0, sb2.size());
    }

    @Test
    public void testConstructors_10_testMerged_6() {
        final StrBuilder sb3 = new StrBuilder(-1);
        assertEquals(32, sb3.capacity());
        assertEquals(0, sb3.length());
        assertEquals(0, sb3.size());
    }

    @Test
    public void testConstructors_13_testMerged_7() {
        final StrBuilder sb4 = new StrBuilder(1);
        assertEquals(1, sb4.capacity());
        assertEquals(0, sb4.length());
        assertEquals(0, sb4.size());
    }

    @Test
    public void testConstructors_16_testMerged_8() {
        final StrBuilder sb5 = new StrBuilder(null);
        assertEquals(32, sb5.capacity());
        assertEquals(0, sb5.length());
        assertEquals(0, sb5.size());
    }
}
