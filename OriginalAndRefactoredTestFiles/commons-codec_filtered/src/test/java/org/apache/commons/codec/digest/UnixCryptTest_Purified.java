package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class UnixCryptTest_Purified {

    @Test
    public void testUnixCryptExplicitCall_1() {
        assertTrue(UnixCrypt.crypt("secret".getBytes()).matches("^[a-zA-Z0-9./]{13}$"));
    }

    @Test
    public void testUnixCryptExplicitCall_2() {
        assertTrue(UnixCrypt.crypt("secret".getBytes(), null).matches("^[a-zA-Z0-9./]{13}$"));
    }

    @Test
    public void testUnixCryptStrings_1() {
        assertEquals("xxWAum7tHdIUw", Crypt.crypt("secret", "xx"));
    }

    @Test
    public void testUnixCryptStrings_2() {
        assertEquals("12UFlHxel6uMM", Crypt.crypt("", "12"));
    }

    @Test
    public void testUnixCryptStrings_3() {
        assertEquals("12FJgqDtVOg7Q", Crypt.crypt("secret", "12"));
    }

    @Test
    public void testUnixCryptStrings_4() {
        assertEquals("12FJgqDtVOg7Q", Crypt.crypt("secret", "12345678"));
    }
}
