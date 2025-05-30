package org.apache.commons.io.input;

import static org.apache.commons.io.IOUtils.EOF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

public class ClosedInputStreamTest_Purified {

    private void assertEof(final ClosedInputStream cis) {
        assertEquals(EOF, cis.read(), "read()");
    }

    @SuppressWarnings("resource")
    @Test
    public void testNonNull_1() throws Exception {
        assertSame(ClosedInputStream.INSTANCE, ClosedInputStream.ifNull(null));
    }

    @SuppressWarnings("resource")
    @Test
    public void testNonNull_2() throws Exception {
        assertSame(ClosedInputStream.INSTANCE, ClosedInputStream.ifNull(ClosedInputStream.INSTANCE));
    }

    @SuppressWarnings("resource")
    @Test
    public void testNonNull_3() throws Exception {
        assertSame(System.in, ClosedInputStream.ifNull(System.in));
    }
}
