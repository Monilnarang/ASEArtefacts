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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestSshFenceByTcpPort_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testBadArgsParsing_1to4")
    public void testBadArgsParsing_1to4(String param1) throws BadFencingConfigurationException {
        assertBadArgs(param1);
    }

    static public Stream<Arguments> Provider_testBadArgsParsing_1to4() {
        return Stream.of(arguments(":"), arguments("bar.com:"), arguments(":xx"), arguments("bar.com:xx"));
    }
}
