package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Arrays;
import org.apache.commons.io.TestResources;
import org.junit.jupiter.api.Test;

public class CharSequenceReaderTest_Purified {

    private static final char NONE = (new char[1])[0];

    private void checkArray(final char[] expected, final char[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "Compare[" + i + "]");
        }
    }

    private void checkRead(final Reader reader, final String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            assertEquals(expected.charAt(i), (char) reader.read(), "Read[" + i + "] of '" + expected + "'");
        }
    }

    @Test
    public void testReady_1_testMerged_1() throws IOException {
        final Reader reader = new CharSequenceReader("FooBar");
        assertTrue(reader.ready());
        reader.skip(3);
        checkRead(reader, "Bar");
        assertFalse(reader.ready());
    }

    @Test
    public void testReady_9_testMerged_2() throws IOException {
        final Reader subReader = new CharSequenceReader("xFooBarx", 1, 7);
        assertTrue(subReader.ready());
        subReader.skip(3);
        checkRead(subReader, "Bar");
        assertFalse(subReader.ready());
    }

    @Test
    public void testSkip_1_testMerged_1() throws IOException {
        final Reader reader = new CharSequenceReader("FooBar");
        assertEquals(3, reader.skip(3));
        checkRead(reader, "Bar");
        assertEquals(0, reader.skip(3));
        reader.reset();
        assertEquals(2, reader.skip(2));
        assertEquals(4, reader.skip(10));
        assertEquals(0, reader.skip(1));
        reader.close();
        assertEquals(6, reader.skip(20));
        assertEquals(-1, reader.read());
    }

    @Test
    public void testSkip_8_testMerged_2() throws IOException {
        final Reader subReader = new CharSequenceReader("xFooBarx", 1, 7);
        assertEquals(3, subReader.skip(3));
        checkRead(subReader, "Bar");
        assertEquals(0, subReader.skip(3));
        subReader.reset();
        assertEquals(2, subReader.skip(2));
        assertEquals(4, subReader.skip(10));
        assertEquals(0, subReader.skip(1));
        subReader.close();
        assertEquals(6, subReader.skip(20));
        assertEquals(-1, subReader.read());
    }

    @Test
    @SuppressWarnings("resource")
    public void testToString_1() {
        assertEquals("FooBar", new CharSequenceReader("FooBar").toString());
    }

    @Test
    @SuppressWarnings("resource")
    public void testToString_2() {
        assertEquals("FooBar", new CharSequenceReader("xFooBarx", 1, 7).toString());
    }
}
