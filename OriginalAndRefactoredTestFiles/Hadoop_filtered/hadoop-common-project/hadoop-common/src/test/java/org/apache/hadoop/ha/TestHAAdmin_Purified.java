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

public class TestHAAdmin_Purified {

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
    public void testAdminUsage_2() throws Exception {
        assertOutputContains("Usage:");
    }

    @Test
    public void testAdminUsage_3() throws Exception {
        assertOutputContains("-transitionToActive");
    }

    @Test
    public void testAdminUsage_4() throws Exception {
        assertEquals(-1, runTool("badCommand"));
    }

    @Test
    public void testAdminUsage_5() throws Exception {
        assertOutputContains("Bad command 'badCommand'");
    }

    @Test
    public void testAdminUsage_6() throws Exception {
        assertEquals(-1, runTool("-badCommand"));
    }

    @Test
    public void testAdminUsage_7() throws Exception {
        assertOutputContains("badCommand: Unknown");
    }

    @Test
    public void testAdminUsage_8() throws Exception {
        assertEquals(-1, runTool("-transitionToActive"));
    }

    @Test
    public void testAdminUsage_9() throws Exception {
        assertOutputContains("transitionToActive: incorrect number of arguments");
    }

    @Test
    public void testAdminUsage_10() throws Exception {
        assertEquals(-1, runTool("-transitionToActive", "x", "y"));
    }

    @Test
    public void testAdminUsage_11() throws Exception {
        assertOutputContains("transitionToActive: incorrect number of arguments");
    }

    @Test
    public void testHelp_1() throws Exception {
        assertEquals(0, runTool("-help"));
    }

    @Test
    public void testHelp_2() throws Exception {
        assertEquals(0, runTool("-help", "transitionToActive"));
    }

    @Test
    public void testHelp_3() throws Exception {
        assertOutputContains("Transitions the service into Active");
    }
}
