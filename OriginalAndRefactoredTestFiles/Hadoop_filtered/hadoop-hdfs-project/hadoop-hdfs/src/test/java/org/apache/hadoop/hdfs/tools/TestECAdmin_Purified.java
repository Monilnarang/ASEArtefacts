package org.apache.hadoop.hdfs.tools;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.SystemErasureCodingPolicies;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestECAdmin_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestECAdmin.class);

    private Configuration conf = new Configuration();

    private MiniDFSCluster cluster;

    private ECAdmin admin = new ECAdmin(conf);

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    private static final PrintStream OLD_OUT = System.out;

    private static final PrintStream OLD_ERR = System.err;

    private final static String RS_3_2 = SystemErasureCodingPolicies.getByID(SystemErasureCodingPolicies.RS_3_2_POLICY_ID).getName();

    private final static String RS_6_3 = SystemErasureCodingPolicies.getByID(SystemErasureCodingPolicies.RS_6_3_POLICY_ID).getName();

    private final static String RS_10_4 = SystemErasureCodingPolicies.getByID(SystemErasureCodingPolicies.RS_10_4_POLICY_ID).getName();

    private final static String XOR_2_1 = SystemErasureCodingPolicies.getByID(SystemErasureCodingPolicies.XOR_2_1_POLICY_ID).getName();

    @Rule
    public Timeout globalTimeout = new Timeout(300000, TimeUnit.MILLISECONDS);

    @Before
    public void setup() throws Exception {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @After
    public void tearDown() throws Exception {
        try {
            System.out.flush();
            System.err.flush();
            resetOutputs();
        } finally {
            System.setOut(OLD_OUT);
            System.setErr(OLD_ERR);
        }
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
    }

    private void resetOutputs() {
        out.reset();
        err.reset();
    }

    private void assertNotEnoughDataNodesMessage(String policy, int numDataNodes, int expectedNumDataNodes) {
        assertTrue("Result of cluster topology verify " + "should be logged correctly", out.toString().contains(expectedNumDataNodes + " DataNodes are required " + "for the erasure coding policies: " + policy + ". The number of DataNodes is only " + numDataNodes));
        assertTrue("Error output should be empty", err.toString().isEmpty());
    }

    private void assertNotEnoughRacksMessage(String policy, int numRacks, int expectedNumRacks) {
        assertTrue("Result of cluster topology verify " + "should be logged correctly", out.toString().contains(expectedNumRacks + " racks are required for " + "the erasure coding policies: " + policy + ". The number of racks is only " + numRacks));
        assertTrue("Error output should be empty", err.toString().isEmpty());
    }

    private int runCommandWithParams(String... args) throws Exception {
        final int ret = admin.run(args);
        LOG.info("Command stdout: {}", out.toString());
        LOG.info("Command stderr: {}", err.toString());
        return ret;
    }

    @Test
    public void testRS63MinDN_1() throws Exception {
        int ret = runCommandWithParams("-verifyClusterSetup");
        assertEquals("Return value of the command is not successful", 2, ret);
    }

    @Test
    public void testRS63MinDN_2() throws Exception {
        final int numDataNodes = 6;
        final int numRacks = 3;
        final int expectedNumDataNodes = 9;
        cluster = DFSTestUtil.setupCluster(conf, numDataNodes, numRacks, 0);
        assertNotEnoughDataNodesMessage(RS_6_3, numDataNodes, expectedNumDataNodes);
    }

    @Test
    public void testRS104MinRacks_1() throws Exception {
        int ret = runCommandWithParams("-verifyClusterSetup");
        assertEquals("Return value of the command is not successful", 2, ret);
    }

    @Test
    public void testRS104MinRacks_2() throws Exception {
        final String testPolicy = RS_10_4;
        final int numDataNodes = 15;
        final int numRacks = 3;
        final int expectedNumRacks = 4;
        cluster = DFSTestUtil.setupCluster(conf, numDataNodes, numRacks, 0);
        cluster.getFileSystem().enableErasureCodingPolicy(testPolicy);
        assertNotEnoughRacksMessage(testPolicy, numRacks, expectedNumRacks);
    }

    @Test
    public void testXOR21MinRacks_1() throws Exception {
        int ret = runCommandWithParams("-verifyClusterSetup");
        assertEquals("Return value of the command is not successful", 2, ret);
    }

    @Test
    public void testXOR21MinRacks_2() throws Exception {
        final String testPolicy = XOR_2_1;
        final int numDataNodes = 5;
        final int numRacks = 2;
        final int expectedNumRacks = 3;
        cluster = DFSTestUtil.setupCluster(conf, numDataNodes, numRacks, 0);
        cluster.getFileSystem().enableErasureCodingPolicy(testPolicy);
        assertNotEnoughRacksMessage(testPolicy, numRacks, expectedNumRacks);
    }

    @Test
    public void testRS32MinRacks_1() throws Exception {
        int ret = runCommandWithParams("-verifyClusterSetup");
        assertEquals("Return value of the command is not successful", 2, ret);
    }

    @Test
    public void testRS32MinRacks_2() throws Exception {
        final String testPolicy = RS_3_2;
        final int numDataNodes = 5;
        final int numRacks = 2;
        final int expectedNumRacks = 3;
        cluster = DFSTestUtil.setupCluster(conf, numDataNodes, numRacks, 0);
        cluster.getFileSystem().enableErasureCodingPolicy(testPolicy);
        assertNotEnoughRacksMessage(testPolicy, numRacks, expectedNumRacks);
    }

    @Test
    public void testRS63Good_1() throws Exception {
        int ret = runCommandWithParams("-verifyClusterSetup");
        assertEquals("Return value of the command is successful", 0, ret);
    }

    @Test
    public void testRS63Good_2() throws Exception {
        assertTrue("Result of cluster topology verify " + "should be logged correctly", out.toString().contains("The cluster setup can support EC policies: " + RS_6_3));
    }

    @Test
    public void testRS63Good_3() throws Exception {
        assertTrue("Error output should be empty", err.toString().isEmpty());
    }

    @Test
    public void testNoECEnabled_1() throws Exception {
        int ret = runCommandWithParams("-verifyClusterSetup");
        assertEquals("Return value of the command is successful", 0, ret);
    }

    @Test
    public void testNoECEnabled_2() throws Exception {
        assertTrue("Result of cluster topology verify " + "should be logged correctly", out.toString().contains("No erasure coding policy is given"));
    }

    @Test
    public void testNoECEnabled_3() throws Exception {
        assertTrue("Error output should be empty", err.toString().isEmpty());
    }

    @Test
    public void testSuccessfulEnablePolicyMessage_1_testMerged_1() throws Exception {
        final String testPolicy = RS_3_2;
        final int ret = runCommandWithParams("-enablePolicy", "-policy", testPolicy);
        assertEquals("Return value of the command is successful", 0, ret);
        assertTrue("Enabling policy should be logged", out.toString().contains("Erasure coding policy " + testPolicy + " is enabled"));
    }

    @Test
    public void testSuccessfulEnablePolicyMessage_3() throws Exception {
        assertFalse("Warning about cluster topology should not be printed", out.toString().contains("Warning: The cluster setup does not support"));
    }

    @Test
    public void testSuccessfulEnablePolicyMessage_4() throws Exception {
        assertTrue("Error output should be empty", err.toString().isEmpty());
    }

    @Test
    public void testEnableNonExistentPolicyMessage_1() throws Exception {
        final int ret = runCommandWithParams("-enablePolicy", "-policy", "NonExistentPolicy");
        assertEquals("Return value of the command is unsuccessful", 2, ret);
    }

    @Test
    public void testEnableNonExistentPolicyMessage_2() throws Exception {
        assertFalse("Enabling policy should not be logged when " + "it was unsuccessful", out.toString().contains("is enabled"));
    }

    @Test
    public void testEnableNonExistentPolicyMessage_3() throws Exception {
        assertTrue("Error message should be printed", err.toString().contains("RemoteException: The policy name " + "NonExistentPolicy does not exist"));
    }

    @Test
    public void testVerifyClusterSetupWithGivenPolicies_1_testMerged_1() throws Exception {
        final int numDataNodes = 5;
        final int numRacks = 2;
        cluster = DFSTestUtil.setupCluster(conf, numDataNodes, numRacks, 0);
        int ret = runCommandWithParams("-verifyClusterSetup", "-policy", RS_3_2);
        assertEquals("Return value of the command is not successful", 2, ret);
        assertNotEnoughRacksMessage(RS_3_2, numRacks, 3);
        ret = runCommandWithParams("-verifyClusterSetup", "-policy", RS_10_4, RS_3_2);
        assertNotEnoughDataNodesMessage(RS_10_4 + ", " + RS_3_2, numDataNodes, 14);
        ret = runCommandWithParams("-verifyClusterSetup", "-policy", "invalidPolicy");
        assertEquals("Return value of the command is not successful", -1, ret);
    }

    @Test
    public void testVerifyClusterSetupWithGivenPolicies_6() throws Exception {
        assertTrue("Error message should be logged", err.toString().contains("The given erasure coding policy invalidPolicy " + "does not exist."));
    }

    @Test
    public void testVerifyClusterSetupWithGivenPolicies_8() throws Exception {
        assertTrue("Error message should be logged", err.toString().contains("NotEnoughArgumentsException: Not enough arguments: " + "expected 1 but got 0"));
    }
}
