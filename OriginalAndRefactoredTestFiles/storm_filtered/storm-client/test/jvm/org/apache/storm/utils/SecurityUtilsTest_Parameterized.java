package org.apache.storm.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SecurityUtilsTest_Parameterized {

    @Test
    public void testInferKeyStoreTypeFromPath_EmptyOrNullPath_2() {
        Assertions.assertNull(SecurityUtils.inferKeyStoreTypeFromPath(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInferKeyStoreTypeFromPath_Pkcs12Extension_1_1to2_2to3_3to5")
    public void testInferKeyStoreTypeFromPath_Pkcs12Extension_1_1to2_2to3_3to5(String param1, String param2) {
        Assertions.assertEquals(param1, SecurityUtils.inferKeyStoreTypeFromPath(param2));
    }

    static public Stream<Arguments> Provider_testInferKeyStoreTypeFromPath_Pkcs12Extension_1_1to2_2to3_3to5() {
        return Stream.of(arguments("PKCS12", "keystore.p12"), arguments("PKCS12", "mykeys.P12"), arguments("PKCS12", "path/to/keystore.pkcs12"), arguments("PKCS12", "mykeys.pKCS12"), arguments("PKCS12", "another/path/to/keystore.pfx"), arguments("JKS", "keystore.jks"), arguments("JKS", "mykeys.JKS"), arguments("JKS", "path/to/keystore.jKs"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInferKeyStoreTypeFromPath_UnsupportedExtension_1_1to4")
    public void testInferKeyStoreTypeFromPath_UnsupportedExtension_1_1to4(String param1) {
        Assertions.assertNull(SecurityUtils.inferKeyStoreTypeFromPath(param1));
    }

    static public Stream<Arguments> Provider_testInferKeyStoreTypeFromPath_UnsupportedExtension_1_1to4() {
        return Stream.of(arguments("keystore.pem"), arguments("certificate.crt"), arguments("path/to/keystore.txt"), arguments("another/path/to/keystore.pem"), arguments(""));
    }
}
