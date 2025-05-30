package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CloseShieldInputStreamTest_Purified {

    private byte[] data;

    private InputStream byteArrayInputStream;

    private InputStream shielded;

    private boolean closed;

    @BeforeEach
    public void setUp() {
        data = new byte[] { 'x', 'y', 'z' };
        byteArrayInputStream = new ByteArrayInputStream(data) {

            @Override
            public void close() {
                closed = true;
            }
        };
        closed = false;
    }

    @Test
    public void testClose_1() throws IOException {
        assertFalse(closed, "closed");
    }

    @Test
    public void testClose_2_testMerged_2() throws IOException {
        shielded = CloseShieldInputStream.wrap(byteArrayInputStream);
        shielded.close();
        assertEquals(-1, shielded.read(), "read()");
        assertEquals(data[0], byteArrayInputStream.read(), "read()");
    }

    @Test
    public void testSystemInOnSystemInNo_1() throws IOException {
        assertTrue(closed, "closed");
    }

    @Test
    public void testSystemInOnSystemInNo_2_testMerged_2() throws IOException {
        shielded = CloseShieldInputStream.systemIn(byteArrayInputStream);
        shielded.close();
        assertEquals(data[0], shielded.read(), "read()");
        assertEquals(data[1], byteArrayInputStream.read(), "read()");
    }

    @Test
    public void testSystemInOnSystemInYes_1() throws IOException {
        assertFalse(closed, "closed");
    }

    @Test
    public void testSystemInOnSystemInYes_2() throws IOException {
        shielded = CloseShieldInputStream.systemIn(System.in);
        shielded.close();
        assertEquals(-1, shielded.read(), "read()");
    }

    @Test
    public void testSystemInOnSystemInYes_3() throws IOException {
        assertEquals(data[0], byteArrayInputStream.read(), "read()");
    }
}
