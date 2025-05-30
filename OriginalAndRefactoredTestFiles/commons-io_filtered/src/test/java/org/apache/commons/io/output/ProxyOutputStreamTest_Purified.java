package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProxyOutputStreamTest_Purified {

    private ByteArrayOutputStream original;

    private ProxyOutputStream proxied;

    private final AtomicBoolean hit = new AtomicBoolean();

    @BeforeEach
    public void setUp() {
        original = new ByteArrayOutputStream() {

            @Override
            public void write(final byte[] ba) {
                hit.set(true);
                super.write(ba);
            }

            @Override
            public synchronized void write(final int ba) {
                hit.set(true);
                super.write(ba);
            }
        };
        proxied = new ProxyOutputStream(original);
    }

    @SuppressWarnings("resource")
    @Test
    public void testSetReference_1() throws Exception {
        assertFalse(hit.get());
    }

    @SuppressWarnings("resource")
    @Test
    public void testSetReference_2() throws Exception {
        assertFalse(hit.get());
    }

    @SuppressWarnings("resource")
    @Test
    public void testSetReference_3_testMerged_3() throws Exception {
        proxied.setReference(new ByteArrayOutputStream());
        proxied.write('y');
        assertEquals(0, original.size());
        assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, original.toByteArray());
    }

    @Test
    public void testWrite_1() throws Exception {
        assertFalse(hit.get());
    }

    @Test
    public void testWrite_2() throws Exception {
        assertTrue(hit.get());
    }

    @Test
    public void testWrite_3_testMerged_3() throws Exception {
        proxied.write('y');
        assertEquals(1, original.size());
        assertEquals('y', original.toByteArray()[0]);
    }
}
