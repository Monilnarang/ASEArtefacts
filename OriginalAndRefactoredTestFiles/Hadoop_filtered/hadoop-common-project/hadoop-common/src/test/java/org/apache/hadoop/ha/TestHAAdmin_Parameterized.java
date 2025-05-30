package org.apache.hadoop.ha;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ha.HAServiceProtocol.HAServiceState;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.thirdparty.com.google.common.base.Charsets;
import org.apache.hadoop.thirdparty.com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestHAAdmin_Parameterized {

    private static final Logger LOG = LoggerFactory.getLogger(TestHAAdmin.class);

    private HAAdmin tool;

    private ByteArrayOutputStream errOutBytes = new ByteArrayOutputStream();

    private ByteArrayOutputStream outBytes = new ByteArrayOutputStream();

    private String errOutput;

    private String output;

    @Before
    public void setup() throws IOException {
        tool = new HAAdmin() {

            @Override
            protected HAServiceTarget resolveTarget(String target) {
                return new DummyHAService(HAServiceState.STANDBY, new InetSocketAddress("dummy", 12345));
            }
        };
        tool.setConf(new Configuration());
        tool.errOut = new PrintStream(errOutBytes);
        tool.out = new PrintStream(outBytes);
    }

    private void assertOutputContains(String string) {
        if (!errOutput.contains(string) && !output.contains(string)) {
            fail("Expected output to contain '" + string + "' but err_output was:\n" + errOutput + "\n and output was: \n" + output);
        }
    }

    private Object runTool(String... args) throws Exception {
        errOutBytes.reset();
        outBytes.reset();
        LOG.info("Running: HAAdmin " + Joiner.on(" ").join(args));
        int ret = tool.run(args);
        errOutput = new String(errOutBytes.toByteArray(), Charsets.UTF_8);
        output = new String(outBytes.toByteArray(), Charsets.UTF_8);
        LOG.info("Err_output:\n" + errOutput + "\nOutput:\n" + output);
        return ret;
    }

    @Test
    public void testAdminUsage_1() throws Exception {
        assertEquals(-1, runTool());
    }

    @Test
    public void testAdminUsage_10() throws Exception {
        assertEquals(-1, runTool("-transitionToActive", "x", "y"));
    }

    @Test
    public void testHelp_1() throws Exception {
        assertEquals(0, runTool("-help"));
    }

    @Test
    public void testHelp_2() throws Exception {
        assertEquals(0, runTool("-help", "transitionToActive"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAdminUsage_2to3_3_5_7_9_11")
    public void testAdminUsage_2to3_3_5_7_9_11(String param1) throws Exception {
        assertOutputContains(param1);
    }

    static public Stream<Arguments> Provider_testAdminUsage_2to3_3_5_7_9_11() {
        return Stream.of(arguments("Usage:"), arguments("-transitionToActive"), arguments("Bad command 'badCommand'"), arguments("badCommand: Unknown"), arguments("transitionToActive: incorrect number of arguments"), arguments("transitionToActive: incorrect number of arguments"), arguments("Transitions the service into Active"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAdminUsage_4_6_8")
    public void testAdminUsage_4_6_8(int param1, String param2) throws Exception {
        assertEquals(-param1, runTool(param2));
    }

    static public Stream<Arguments> Provider_testAdminUsage_4_6_8() {
        return Stream.of(arguments(1, "badCommand"), arguments(1, "-badCommand"), arguments(1, "-transitionToActive"));
    }
}
