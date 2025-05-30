package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;

public class Sha256CryptTest_Purified {

    @Test
    public void testSha256CryptExplicitCall_1() {
        assertTrue(Sha2Crypt.sha256Crypt("secret".getBytes()).matches("^\\$5\\$[a-zA-Z0-9./]{0,16}\\$.{1,}$"));
    }

    @Test
    public void testSha256CryptExplicitCall_2() {
        assertTrue(Sha2Crypt.sha256Crypt("secret".getBytes(), null).matches("^\\$5\\$[a-zA-Z0-9./]{0,16}\\$.{1,}$"));
    }

    @Test
    public void testSha256CryptStrings_1() {
        assertEquals("$5$foo$Fq9CX624QIfnCAmlGiPKLlAasdacKCRxZztPoeo7o0B", Crypt.crypt("", "$5$foo"));
    }

    @Test
    public void testSha256CryptStrings_2() {
        assertEquals("$5$45678$LulJuUIJIn.1uU.KPV9x92umMYFopzVDD.o2ZqA1i2/", Crypt.crypt("secret", "$5$45678"));
    }

    @Test
    public void testSha256CryptStrings_3() {
        assertEquals("$5$45678$LulJuUIJIn.1uU.KPV9x92umMYFopzVDD.o2ZqA1i2/", Crypt.crypt("secret", "$5$45678$012"));
    }

    @Test
    public void testSha256CryptStrings_4() {
        assertEquals("$5$45678$LulJuUIJIn.1uU.KPV9x92umMYFopzVDD.o2ZqA1i2/", Crypt.crypt("secret", "$5$45678$012$456"));
    }

    @Test
    public void testSha256CryptStrings_5() {
        assertEquals("$5$1234567890123456$GUiFKBSTUAGvcK772ulTDPltkTOLtFvPOmp9o.9FNPB", Crypt.crypt("secret", "$5$1234567890123456"));
    }

    @Test
    public void testSha256CryptStrings_6() {
        assertEquals("$5$1234567890123456$GUiFKBSTUAGvcK772ulTDPltkTOLtFvPOmp9o.9FNPB", Crypt.crypt("secret", "$5$1234567890123456789"));
    }

    @Test
    public void testSha2CryptRounds_1() {
        assertEquals("$5$rounds=1000$abcd$b8MCU4GEeZIekOy5ahQ8EWfT330hvYGVeDYkBxXBva.", Sha2Crypt.sha256Crypt("secret".getBytes(StandardCharsets.UTF_8), "$5$rounds=50$abcd$"));
    }

    @Test
    public void testSha2CryptRounds_2() {
        assertEquals("$5$rounds=1001$abcd$SQsJZs7KXKdd2DtklI3TY3tkD7UYA99RD0FBLm4Sk48", Sha2Crypt.sha256Crypt("secret".getBytes(StandardCharsets.UTF_8), "$5$rounds=1001$abcd$"));
    }

    @Test
    public void testSha2CryptRounds_3() {
        assertEquals("$5$rounds=9999$abcd$Rh/8ngVh9oyuS6lL3.fsq.9xbvXJsfyKWxSjO2mPIa7", Sha2Crypt.sha256Crypt("secret".getBytes(StandardCharsets.UTF_8), "$5$rounds=9999$abcd"));
    }
}
