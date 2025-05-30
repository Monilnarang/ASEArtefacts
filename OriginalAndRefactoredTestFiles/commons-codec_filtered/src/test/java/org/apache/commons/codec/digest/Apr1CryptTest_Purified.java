package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;

public class Apr1CryptTest_Purified {

    @Test
    public void testApr1CryptExplicitCall_1() {
        assertEquals("$apr1$1234$mAlH7FRST6FiRZ.kcYL.j1", Md5Crypt.apr1Crypt("secret", "1234"));
    }

    @Test
    public void testApr1CryptExplicitCall_2() {
        assertTrue(Md5Crypt.apr1Crypt("secret".getBytes()).matches("^\\$apr1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @Test
    public void testApr1CryptExplicitCall_3() {
        assertTrue(Md5Crypt.apr1Crypt("secret".getBytes(), (String) null).matches("^\\$apr1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @Test
    public void testApr1CryptStrings_1() {
        assertEquals("$apr1$TqI9WECO$LHZB2DqRlk9nObiB6vJG9.", Md5Crypt.apr1Crypt("secret", "$apr1$TqI9WECO"));
    }

    @Test
    public void testApr1CryptStrings_2() {
        assertEquals("$apr1$foo$P27KyD1htb4EllIPEYhqi0", Md5Crypt.apr1Crypt("", "$apr1$foo"));
    }

    @Test
    public void testApr1CryptStrings_3() {
        assertEquals("$apr1$1234$mAlH7FRST6FiRZ.kcYL.j1", Md5Crypt.apr1Crypt("secret", "$apr1$1234"));
    }

    @Test
    public void testApr1CryptStrings_4() {
        assertEquals("$apr1$1234$mAlH7FRST6FiRZ.kcYL.j1", Md5Crypt.apr1Crypt("secret", "$apr1$1234$567"));
    }

    @Test
    public void testApr1CryptStrings_5() {
        assertEquals("$apr1$1234$mAlH7FRST6FiRZ.kcYL.j1", Md5Crypt.apr1Crypt("secret", "$apr1$1234$567$890"));
    }

    @Test
    public void testApr1CryptStrings_6() {
        assertEquals("$apr1$12345678$0lqb/6VUFP8JY/s/jTrIk0", Md5Crypt.apr1Crypt("secret", "$apr1$1234567890123456"));
    }

    @Test
    public void testApr1CryptStrings_7() {
        assertEquals("$apr1$12345678$0lqb/6VUFP8JY/s/jTrIk0", Md5Crypt.apr1Crypt("secret", "$apr1$123456789012345678"));
    }
}
