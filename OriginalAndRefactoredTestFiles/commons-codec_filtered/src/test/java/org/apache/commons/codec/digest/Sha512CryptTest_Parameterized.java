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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Sha512CryptTest_Parameterized {

    @Test
    public void testSha512CryptExplicitCall_1() {
        assertTrue(Sha2Crypt.sha512Crypt("secret".getBytes()).matches("^\\$6\\$[a-zA-Z0-9./]{0,16}\\$.{1,}$"));
    }

    @Test
    public void testSha512CryptExplicitCall_2() {
        assertTrue(Sha2Crypt.sha512Crypt("secret".getBytes(), null).matches("^\\$6\\$[a-zA-Z0-9./]{0,16}\\$.{1,}$"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSha2CryptRounds_1to3")
    public void testSha2CryptRounds_1to3(String param1, String param2, String param3) {
        assertEquals(param1, Sha2Crypt.sha256Crypt(param3.getBytes(StandardCharsets.UTF_8), param2));
    }

    static public Stream<Arguments> Provider_testSha2CryptRounds_1to3() {
        return Stream.of(arguments("$5$rounds=1000$abcd$b8MCU4GEeZIekOy5ahQ8EWfT330hvYGVeDYkBxXBva.", "$5$rounds=50$abcd$", "secret"), arguments("$5$rounds=1001$abcd$SQsJZs7KXKdd2DtklI3TY3tkD7UYA99RD0FBLm4Sk48", "$5$rounds=1001$abcd$", "secret"), arguments("$5$rounds=9999$abcd$Rh/8ngVh9oyuS6lL3.fsq.9xbvXJsfyKWxSjO2mPIa7", "$5$rounds=9999$abcd", "secret"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSha512CryptStrings_1to6")
    public void testSha512CryptStrings_1to6(String param1, String param2, String param3) {
        assertEquals(param1, Crypt.crypt(param2, param3));
    }

    static public Stream<Arguments> Provider_testSha512CryptStrings_1to6() {
        return Stream.of(arguments("$6$foo$Nywkte7LPWjaJhWjNeGJN.dFdY3pN1wYlGifyRLYOVlGS9EMSiZaDDe/BGSOYQ327q9.32I4UqQ5odsqvsBLX/", "", "$6$foo"), arguments("$6$45678$f2en/Y053Knir/wu/T8DQKSbiUGcPcbXKsmyVlP820dIpXoY0KlqgUqRVFfavdRXwDMUZYsxPOymA4zgX0qE5.", "secret", "$6$45678"), arguments("$6$45678$f2en/Y053Knir/wu/T8DQKSbiUGcPcbXKsmyVlP820dIpXoY0KlqgUqRVFfavdRXwDMUZYsxPOymA4zgX0qE5.", "secret", "$6$45678$012"), arguments("$6$45678$f2en/Y053Knir/wu/T8DQKSbiUGcPcbXKsmyVlP820dIpXoY0KlqgUqRVFfavdRXwDMUZYsxPOymA4zgX0qE5.", "secret", "$6$45678$012$456"), arguments("$6$1234567890123456$d2HCAnimIF5VMqUnwaZ/4JhNDJ.ttsjm0nbbmc9eE7xUYiw79GMvXUc5ZqG5BlqkXSbASZxrvR0QefAgdLbeH.", "secret", "$6$1234567890123456"), arguments("$6$1234567890123456$d2HCAnimIF5VMqUnwaZ/4JhNDJ.ttsjm0nbbmc9eE7xUYiw79GMvXUc5ZqG5BlqkXSbASZxrvR0QefAgdLbeH.", "secret", "$6$1234567890123456789"));
    }
}
