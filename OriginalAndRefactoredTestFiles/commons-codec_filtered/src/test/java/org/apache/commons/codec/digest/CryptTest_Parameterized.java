package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CryptTest_Parameterized {

    public static void main(final String[] args) {
        final String hash;
        switch(args.length) {
            case 1:
                hash = Crypt.crypt(args[0]);
                System.out.println(hash.length() + ": " + hash);
                break;
            case 2:
                hash = Crypt.crypt(args[0], args[1]);
                System.out.println(hash.length() + "; " + hash);
                break;
            default:
                System.out.println("Enter key [salt (remember to quote this!)]");
                break;
        }
    }

    private void startsWith(final String string, final String prefix) {
        assertTrue(string.startsWith(prefix), string + " should start with " + prefix);
    }

    @Test
    public void testDefaultCryptVariant_1() {
        assertTrue(Crypt.crypt("secret").startsWith("$6$"));
    }

    @Test
    public void testDefaultCryptVariant_2() {
        assertTrue(Crypt.crypt("secret", null).startsWith("$6$"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSamples_1_1to2_2")
    public void testSamples_1_1to2_2(String param1, String param2, String param3) {
        assertEquals(param1, Crypt.crypt(param2, param3));
    }

    static public Stream<Arguments> Provider_testSamples_1_1to2_2() {
        return Stream.of(arguments("$1$xxxx$aMkevjfEIpa35Bh3G4bAc.", "secret", "$1$xxxx"), arguments("xxWAum7tHdIUw", "secret", "xx"), arguments("$1$xxxx$aMkevjfEIpa35Bh3G4bAc.", "secret", "$1$xxxx$aMkevjfEIpa35Bh3G4bAc."), arguments("xxWAum7tHdIUw", "secret", "xxWAum7tHdIUw"));
    }
}
