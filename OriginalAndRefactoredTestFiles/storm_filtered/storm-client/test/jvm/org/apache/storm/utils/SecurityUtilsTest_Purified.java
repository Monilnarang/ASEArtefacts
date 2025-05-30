package org.apache.storm.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SecurityUtilsTest_Purified {

    @Test
    public void testInferKeyStoreTypeFromPath_Pkcs12Extension_1() {
        Assertions.assertEquals("PKCS12", SecurityUtils.inferKeyStoreTypeFromPath("keystore.p12"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_Pkcs12Extension_2() {
        Assertions.assertEquals("PKCS12", SecurityUtils.inferKeyStoreTypeFromPath("mykeys.P12"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_Pkcs12Extension_3() {
        Assertions.assertEquals("PKCS12", SecurityUtils.inferKeyStoreTypeFromPath("path/to/keystore.pkcs12"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_Pkcs12Extension_4() {
        Assertions.assertEquals("PKCS12", SecurityUtils.inferKeyStoreTypeFromPath("mykeys.pKCS12"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_Pkcs12Extension_5() {
        Assertions.assertEquals("PKCS12", SecurityUtils.inferKeyStoreTypeFromPath("another/path/to/keystore.pfx"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_JksExtension_1() {
        Assertions.assertEquals("JKS", SecurityUtils.inferKeyStoreTypeFromPath("keystore.jks"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_JksExtension_2() {
        Assertions.assertEquals("JKS", SecurityUtils.inferKeyStoreTypeFromPath("mykeys.JKS"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_JksExtension_3() {
        Assertions.assertEquals("JKS", SecurityUtils.inferKeyStoreTypeFromPath("path/to/keystore.jKs"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_UnsupportedExtension_1() {
        Assertions.assertNull(SecurityUtils.inferKeyStoreTypeFromPath("keystore.pem"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_UnsupportedExtension_2() {
        Assertions.assertNull(SecurityUtils.inferKeyStoreTypeFromPath("certificate.crt"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_UnsupportedExtension_3() {
        Assertions.assertNull(SecurityUtils.inferKeyStoreTypeFromPath("path/to/keystore.txt"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_UnsupportedExtension_4() {
        Assertions.assertNull(SecurityUtils.inferKeyStoreTypeFromPath("another/path/to/keystore.pem"));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_EmptyOrNullPath_1() {
        Assertions.assertNull(SecurityUtils.inferKeyStoreTypeFromPath(""));
    }

    @Test
    public void testInferKeyStoreTypeFromPath_EmptyOrNullPath_2() {
        Assertions.assertNull(SecurityUtils.inferKeyStoreTypeFromPath(null));
    }
}
