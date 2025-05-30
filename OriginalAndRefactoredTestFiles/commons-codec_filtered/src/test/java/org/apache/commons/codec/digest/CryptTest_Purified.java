package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CryptTest_Purified {

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

    @Test
    public void testSamples_1() {
        assertEquals("$1$xxxx$aMkevjfEIpa35Bh3G4bAc.", Crypt.crypt("secret", "$1$xxxx"));
    }

    @Test
    public void testSamples_2() {
        assertEquals("xxWAum7tHdIUw", Crypt.crypt("secret", "xx"));
    }

    @Test
    public void testStored_1() {
        assertEquals("$1$xxxx$aMkevjfEIpa35Bh3G4bAc.", Crypt.crypt("secret", "$1$xxxx$aMkevjfEIpa35Bh3G4bAc."));
    }

    @Test
    public void testStored_2() {
        assertEquals("xxWAum7tHdIUw", Crypt.crypt("secret", "xxWAum7tHdIUw"));
    }
}
