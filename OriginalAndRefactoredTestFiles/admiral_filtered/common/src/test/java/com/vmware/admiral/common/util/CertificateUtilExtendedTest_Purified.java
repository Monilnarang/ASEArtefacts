package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Properties;
import org.junit.BeforeClass;
import org.junit.Test;

public class CertificateUtilExtendedTest_Purified {

    private static final String PROPERTIES_FILE_NAME = "CertificateUtilExtendedTest.properties";

    private static final String SELF_SIGNED_CERT_PROP_NAME = "cert.selfsigned";

    private static final String NOT_SELF_SIGNED_CERT_PROP_NAME = "cert.notselfsigned";

    private static final String CERT_CHAIN_PROP_NAME = "cert.chain";

    private static Properties testProperties;

    @BeforeClass
    public static void loadProperties() {
        testProperties = new Properties();
        try (InputStream in = CertificateUtilExtendedTest.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            if (in == null) {
                fail("Test input properties file missing: " + PROPERTIES_FILE_NAME);
            }
            testProperties.load(in);
        } catch (IOException e) {
            fail("Failed to read properties file with test input: " + PROPERTIES_FILE_NAME + ", " + e.getMessage());
        }
    }

    @Test
    public void testInvalidCertificate_1() {
        assertFalse(CertificateUtilExtended.isSelfSignedCertificate("invalid certificate"));
    }

    @Test
    public void testInvalidCertificate_2() {
        assertFalse(CertificateUtilExtended.isSelfSignedCertificate(null));
    }
}
