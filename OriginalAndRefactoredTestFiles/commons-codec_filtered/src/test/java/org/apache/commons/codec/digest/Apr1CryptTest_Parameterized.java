package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Apr1CryptTest_Parameterized {

    @Test
    public void testApr1CryptExplicitCall_2() {
        assertTrue(Md5Crypt.apr1Crypt("secret".getBytes()).matches("^\\$apr1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @Test
    public void testApr1CryptExplicitCall_3() {
        assertTrue(Md5Crypt.apr1Crypt("secret".getBytes(), (String) null).matches("^\\$apr1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testApr1CryptExplicitCall_1_1to7")
    public void testApr1CryptExplicitCall_1_1to7(String param1, String param2, int param3) {
        assertEquals(param1, Md5Crypt.apr1Crypt(param2, param3));
    }

    static public Stream<Arguments> Provider_testApr1CryptExplicitCall_1_1to7() {
        return Stream.of(arguments("$apr1$1234$mAlH7FRST6FiRZ.kcYL.j1", "secret", 1234), arguments("$apr1$TqI9WECO$LHZB2DqRlk9nObiB6vJG9.", "secret", "$apr1$TqI9WECO"), arguments("$apr1$foo$P27KyD1htb4EllIPEYhqi0", "", "$apr1$foo"), arguments("$apr1$1234$mAlH7FRST6FiRZ.kcYL.j1", "secret", "$apr1$1234"), arguments("$apr1$1234$mAlH7FRST6FiRZ.kcYL.j1", "secret", "$apr1$1234$567"), arguments("$apr1$1234$mAlH7FRST6FiRZ.kcYL.j1", "secret", "$apr1$1234$567$890"), arguments("$apr1$12345678$0lqb/6VUFP8JY/s/jTrIk0", "secret", "$apr1$1234567890123456"), arguments("$apr1$12345678$0lqb/6VUFP8JY/s/jTrIk0", "secret", "$apr1$123456789012345678"));
    }
}
