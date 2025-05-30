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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Timeout(3)
public class Md5CryptTest_Parameterized {

    @Test
    public void testMd5CryptExplicitCall_1() {
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes()).matches("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @Test
    public void testMd5CryptExplicitCallWithThreadLocalRandom_1() {
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), threadLocalRandom).matches("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMd5CryptExplicitCall_2_2")
    public void testMd5CryptExplicitCall_2_2(String param1, String param2) {
        assertTrue(Md5Crypt.md5Crypt(param2.getBytes(), (String) null).matches(param1));
    }

    static public Stream<Arguments> Provider_testMd5CryptExplicitCall_2_2() {
        return Stream.of(arguments("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$", "secret"), arguments("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$", "secret"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMd5CryptStrings_1to6")
    public void testMd5CryptStrings_1to6(String param1, String param2, String param3) {
        assertEquals(param1, Crypt.crypt(param2, param3));
    }

    static public Stream<Arguments> Provider_testMd5CryptStrings_1to6() {
        return Stream.of(arguments("$1$foo$9mS5ExwgIECGE5YKlD5o91", "", "$1$foo"), arguments("$1$1234$ImZYBLmYC.rbBKg9ERxX70", "secret", "$1$1234"), arguments("$1$1234$ImZYBLmYC.rbBKg9ERxX70", "secret", "$1$1234$567"), arguments("$1$1234$ImZYBLmYC.rbBKg9ERxX70", "secret", "$1$1234$567$890"), arguments("$1$12345678$hj0uLpdidjPhbMMZeno8X/", "secret", "$1$1234567890123456"), arguments("$1$12345678$hj0uLpdidjPhbMMZeno8X/", "secret", "$1$123456789012345678"));
    }
}
