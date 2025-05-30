package org.apache.hadoop.ha;

import static org.junit.Assert.*;
import java.net.InetSocketAddress;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ha.HAServiceProtocol.HAServiceState;
import org.apache.hadoop.ha.SshFenceByTcpPort.Args;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.Assume;
import org.junit.Test;
import org.slf4j.event.Level;

public class TestSshFenceByTcpPort_Purified {

    static {
        GenericTestUtils.setLogLevel(SshFenceByTcpPort.LOG, Level.TRACE);
    }

    private static String TEST_FENCING_HOST = System.getProperty("test.TestSshFenceByTcpPort.host", "localhost");

    private static final String TEST_FENCING_PORT = System.getProperty("test.TestSshFenceByTcpPort.port", "8020");

    private static final String TEST_KEYFILE = System.getProperty("test.TestSshFenceByTcpPort.key");

    private static final InetSocketAddress TEST_ADDR = new InetSocketAddress(TEST_FENCING_HOST, Integer.parseInt(TEST_FENCING_PORT));

    private static final HAServiceTarget TEST_TARGET = new DummyHAService(HAServiceState.ACTIVE, TEST_ADDR);

    private static final HAServiceTarget UNFENCEABLE_TARGET = new DummyHAService(HAServiceState.ACTIVE, new InetSocketAddress("8.8.8.8", 1234));

    private void assertBadArgs(String argStr) {
        try {
            new Args(argStr);
            fail("Did not fail on bad args: " + argStr);
        } catch (BadFencingConfigurationException e) {
        }
    }

    private boolean isConfigured() {
        return (TEST_FENCING_HOST != null && !TEST_FENCING_HOST.isEmpty()) && (TEST_FENCING_PORT != null && !TEST_FENCING_PORT.isEmpty()) && (TEST_KEYFILE != null && !TEST_KEYFILE.isEmpty());
    }

    @Test
    public void testBadArgsParsing_1() throws BadFencingConfigurationException {
        assertBadArgs(":");
    }

    @Test
    public void testBadArgsParsing_2() throws BadFencingConfigurationException {
        assertBadArgs("bar.com:");
    }

    @Test
    public void testBadArgsParsing_3() throws BadFencingConfigurationException {
        assertBadArgs(":xx");
    }

    @Test
    public void testBadArgsParsing_4() throws BadFencingConfigurationException {
        assertBadArgs("bar.com:xx");
    }
}
