package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.CharArrayWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class UnicodeUnpairedSurrogateRemoverTest_Purified {

    final UnicodeUnpairedSurrogateRemover subject = new UnicodeUnpairedSurrogateRemover();

    final CharArrayWriter writer = new CharArrayWriter();

    @Test
    public void testInvalidCharacters_1() throws IOException {
        assertTrue(subject.translate(0xd800, writer));
    }

    @Test
    public void testInvalidCharacters_2() throws IOException {
        assertTrue(subject.translate(0xdfff, writer));
    }

    @Test
    public void testInvalidCharacters_3() throws IOException {
        assertEquals(0, writer.size());
    }

    @Test
    public void testValidCharacters_1() throws IOException {
        assertFalse(subject.translate(0xd7ff, writer));
    }

    @Test
    public void testValidCharacters_2() throws IOException {
        assertFalse(subject.translate(0xe000, writer));
    }

    @Test
    public void testValidCharacters_3() throws IOException {
        assertEquals(0, writer.size());
    }
}
