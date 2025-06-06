package org.apache.commons.net.ftp;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class FTPSClientGettersTest_Purified {

    @Test
    public void testGetters_1_testMerged_1() {
        final FTPSClient testClient = new FTPSClient("SSL", true);
        assertTrue(testClient.isImplicit());
        assertEquals("SSL", testClient.getProtocol());
    }

    @Test
    public void testGetters_3_testMerged_2() {
        final FTPSClient testClient2 = new FTPSClient("TLS", false);
        assertFalse(testClient2.isImplicit());
        assertEquals("TLS", testClient2.getProtocol());
        final String[] protocols = { "123", "456" };
        testClient2.setEnabledProtocols(protocols);
        assertArrayEquals(protocols, testClient2.getProtocols());
        testClient2.setNeedClientAuth(true);
        assertTrue(testClient2.isNeedClientAuth());
        testClient2.setNeedClientAuth(false);
        assertFalse(testClient2.isNeedClientAuth());
        testClient2.setWantClientAuth(true);
        assertTrue(testClient2.isWantClientAuth());
        testClient2.setWantClientAuth(false);
        assertFalse(testClient2.isWantClientAuth());
        final String[] suites = { "abc", "def" };
        testClient2.setEnabledCipherSuites(suites);
        assertArrayEquals(suites, testClient2.getSuites());
        testClient2.setAuthValue("qwerty");
        assertEquals("qwerty", testClient2.getAuthValue());
        testClient2.setUseClientMode(true);
        assertTrue(testClient2.isClientMode());
        testClient2.setUseClientMode(false);
        assertFalse(testClient2.isClientMode());
        testClient2.setEnabledSessionCreation(true);
        assertTrue(testClient2.isCreation());
        testClient2.setEnabledSessionCreation(false);
        assertFalse(testClient2.isCreation());
    }
}
