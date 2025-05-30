package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(3)
public class Md5CryptTest_Purified {

    @Test
    public void testMd5CryptExplicitCall_1() {
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes()).matches("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @Test
    public void testMd5CryptExplicitCall_2() {
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), (String) null).matches("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @Test
    public void testMd5CryptExplicitCallWithThreadLocalRandom_1() {
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), threadLocalRandom).matches("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @Test
    public void testMd5CryptExplicitCallWithThreadLocalRandom_2() {
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), (String) null).matches("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @Test
    public void testMd5CryptStrings_1() {
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt("", "$1$foo"));
    }

    @Test
    public void testMd5CryptStrings_2() {
        assertEquals("$1$1234$ImZYBLmYC.rbBKg9ERxX70", Crypt.crypt("secret", "$1$1234"));
    }

    @Test
    public void testMd5CryptStrings_3() {
        assertEquals("$1$1234$ImZYBLmYC.rbBKg9ERxX70", Crypt.crypt("secret", "$1$1234$567"));
    }

    @Test
    public void testMd5CryptStrings_4() {
        assertEquals("$1$1234$ImZYBLmYC.rbBKg9ERxX70", Crypt.crypt("secret", "$1$1234$567$890"));
    }

    @Test
    public void testMd5CryptStrings_5() {
        assertEquals("$1$12345678$hj0uLpdidjPhbMMZeno8X/", Crypt.crypt("secret", "$1$1234567890123456"));
    }

    @Test
    public void testMd5CryptStrings_6() {
        assertEquals("$1$12345678$hj0uLpdidjPhbMMZeno8X/", Crypt.crypt("secret", "$1$123456789012345678"));
    }
}
