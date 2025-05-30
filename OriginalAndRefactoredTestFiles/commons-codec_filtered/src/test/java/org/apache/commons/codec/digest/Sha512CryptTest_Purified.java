package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Sha512CryptTest_Purified {

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

    @Test
    public void testSha512CryptExplicitCall_1() {
        assertTrue(Sha2Crypt.sha512Crypt("secret".getBytes()).matches("^\\$6\\$[a-zA-Z0-9./]{0,16}\\$.{1,}$"));
    }

    @Test
    public void testSha512CryptExplicitCall_2() {
        assertTrue(Sha2Crypt.sha512Crypt("secret".getBytes(), null).matches("^\\$6\\$[a-zA-Z0-9./]{0,16}\\$.{1,}$"));
    }

    @Test
    public void testSha512CryptStrings_1() {
        assertEquals("$6$foo$Nywkte7LPWjaJhWjNeGJN.dFdY3pN1wYlGifyRLYOVlGS9EMSiZaDDe/BGSOYQ327q9.32I4UqQ5odsqvsBLX/", Crypt.crypt("", "$6$foo"));
    }

    @Test
    public void testSha512CryptStrings_2() {
        assertEquals("$6$45678$f2en/Y053Knir/wu/T8DQKSbiUGcPcbXKsmyVlP820dIpXoY0KlqgUqRVFfavdRXwDMUZYsxPOymA4zgX0qE5.", Crypt.crypt("secret", "$6$45678"));
    }

    @Test
    public void testSha512CryptStrings_3() {
        assertEquals("$6$45678$f2en/Y053Knir/wu/T8DQKSbiUGcPcbXKsmyVlP820dIpXoY0KlqgUqRVFfavdRXwDMUZYsxPOymA4zgX0qE5.", Crypt.crypt("secret", "$6$45678$012"));
    }

    @Test
    public void testSha512CryptStrings_4() {
        assertEquals("$6$45678$f2en/Y053Knir/wu/T8DQKSbiUGcPcbXKsmyVlP820dIpXoY0KlqgUqRVFfavdRXwDMUZYsxPOymA4zgX0qE5.", Crypt.crypt("secret", "$6$45678$012$456"));
    }

    @Test
    public void testSha512CryptStrings_5() {
        assertEquals("$6$1234567890123456$d2HCAnimIF5VMqUnwaZ/4JhNDJ.ttsjm0nbbmc9eE7xUYiw79GMvXUc5ZqG5BlqkXSbASZxrvR0QefAgdLbeH.", Crypt.crypt("secret", "$6$1234567890123456"));
    }

    @Test
    public void testSha512CryptStrings_6() {
        assertEquals("$6$1234567890123456$d2HCAnimIF5VMqUnwaZ/4JhNDJ.ttsjm0nbbmc9eE7xUYiw79GMvXUc5ZqG5BlqkXSbASZxrvR0QefAgdLbeH.", Crypt.crypt("secret", "$6$1234567890123456789"));
    }
}
