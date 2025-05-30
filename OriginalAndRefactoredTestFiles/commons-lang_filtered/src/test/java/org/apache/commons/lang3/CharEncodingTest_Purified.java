package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@Deprecated
public class CharEncodingTest_Purified extends AbstractLangTest {

    private void assertSupportedEncoding(final String name) {
        assertTrue(CharEncoding.isSupported(name), "Encoding should be supported: " + name);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_1() {
        assertSupportedEncoding(CharEncoding.ISO_8859_1);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_2() {
        assertSupportedEncoding(CharEncoding.US_ASCII);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_3() {
        assertSupportedEncoding(CharEncoding.UTF_16);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_4() {
        assertSupportedEncoding(CharEncoding.UTF_16BE);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_5() {
        assertSupportedEncoding(CharEncoding.UTF_16LE);
    }

    @Test
    public void testMustBeSupportedJava1_3_1_and_above_6() {
        assertSupportedEncoding(CharEncoding.UTF_8);
    }

    @Test
    public void testNotSupported_1() {
        assertFalse(CharEncoding.isSupported(null));
    }

    @Test
    public void testNotSupported_2() {
        assertFalse(CharEncoding.isSupported(""));
    }

    @Test
    public void testNotSupported_3() {
        assertFalse(CharEncoding.isSupported(" "));
    }

    @Test
    public void testNotSupported_4() {
        assertFalse(CharEncoding.isSupported("\t\r\n"));
    }

    @Test
    public void testNotSupported_5() {
        assertFalse(CharEncoding.isSupported("DOESNOTEXIST"));
    }

    @Test
    public void testNotSupported_6() {
        assertFalse(CharEncoding.isSupported("this is not a valid encoding name"));
    }

    @Test
    public void testStandardCharsetsEquality_1() {
        assertEquals(StandardCharsets.ISO_8859_1.name(), CharEncoding.ISO_8859_1);
    }

    @Test
    public void testStandardCharsetsEquality_2() {
        assertEquals(StandardCharsets.US_ASCII.name(), CharEncoding.US_ASCII);
    }

    @Test
    public void testStandardCharsetsEquality_3() {
        assertEquals(StandardCharsets.UTF_8.name(), CharEncoding.UTF_8);
    }

    @Test
    public void testStandardCharsetsEquality_4() {
        assertEquals(StandardCharsets.UTF_16.name(), CharEncoding.UTF_16);
    }

    @Test
    public void testStandardCharsetsEquality_5() {
        assertEquals(StandardCharsets.UTF_16BE.name(), CharEncoding.UTF_16BE);
    }

    @Test
    public void testStandardCharsetsEquality_6() {
        assertEquals(StandardCharsets.UTF_16LE.name(), CharEncoding.UTF_16LE);
    }

    @Test
    public void testSupported_1() {
        assertTrue(CharEncoding.isSupported("UTF8"));
    }

    @Test
    public void testSupported_2() {
        assertTrue(CharEncoding.isSupported("UTF-8"));
    }

    @Test
    public void testSupported_3() {
        assertTrue(CharEncoding.isSupported("ASCII"));
    }
}
