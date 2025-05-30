package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UnixCryptTest_Parameterized {

    @Test
    public void testUnixCryptExplicitCall_1() {
        assertTrue(UnixCrypt.crypt("secret".getBytes()).matches("^[a-zA-Z0-9./]{13}$"));
    }

    @Test
    public void testUnixCryptExplicitCall_2() {
        assertTrue(UnixCrypt.crypt("secret".getBytes(), null).matches("^[a-zA-Z0-9./]{13}$"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnixCryptStrings_1to4")
    public void testUnixCryptStrings_1to4(String param1, String param2, String param3) {
        assertEquals(param1, Crypt.crypt(param2, param3));
    }

    static public Stream<Arguments> Provider_testUnixCryptStrings_1to4() {
        return Stream.of(arguments("xxWAum7tHdIUw", "secret", "xx"), arguments("12UFlHxel6uMM", "", 12), arguments("12FJgqDtVOg7Q", "secret", 12), arguments("12FJgqDtVOg7Q", "secret", 12345678));
    }
}
