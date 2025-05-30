package org.apache.hadoop.hdfs.tools;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_HA_NN_NOT_BECOME_ACTIVE_IN_SAFEMODE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ha.HAAdmin;
import org.apache.hadoop.ha.HAServiceProtocol.HAServiceState;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Shell;
import org.slf4j.event.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.thirdparty.com.google.common.base.Charsets;
import org.apache.hadoop.thirdparty.com.google.common.base.Joiner;
import org.apache.hadoop.thirdparty.com.google.common.io.Files;

public class TestDFSHAAdminMiniCluster_Purified {

    static {
        GenericTestUtils.setLogLevel(LoggerFactory.getLogger(HAAdmin.class), Level.TRACE);
    }

    private static final Logger LOG = LoggerFactory.getLogger(TestDFSHAAdminMiniCluster.class);

    private MiniDFSCluster cluster;

    private Configuration conf;

    private DFSHAAdmin tool;

    private final ByteArrayOutputStream errOutBytes = new ByteArrayOutputStream();

    private String errOutput;

    private int nn1Port;

    @Before
    public void setup() throws IOException {
        conf = new Configuration();
        conf.setBoolean(DFS_HA_NN_NOT_BECOME_ACTIVE_IN_SAFEMODE, true);
        cluster = new MiniDFSCluster.Builder(conf).nnTopology(MiniDFSNNTopology.simpleHATopology()).numDataNodes(0).build();
        tool = new DFSHAAdmin();
        tool.setConf(conf);
        tool.setErrOut(new PrintStream(errOutBytes));
        cluster.waitActive();
        nn1Port = cluster.getNameNodePort(0);
    }

    @After
    public void shutdown() throws Exception {
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
    }

    private int runTool(String... args) throws Exception {
        errOutBytes.reset();
        LOG.info("Running: DFSHAAdmin " + Joiner.on(" ").join(args));
        int ret = tool.run(args);
        errOutput = new String(errOutBytes.toByteArray(), Charsets.UTF_8);
        LOG.info("Output:\n" + errOutput);
        return ret;
    }

    @Test
    public void testGetServiceState_1() throws Exception {
        assertEquals(0, runTool("-getServiceState", "nn1"));
    }

    @Test
    public void testGetServiceState_2() throws Exception {
        assertEquals(0, runTool("-getServiceState", "nn2"));
    }

    @Test
    public void testGetServiceState_3() throws Exception {
        assertEquals(0, runTool("-getServiceState", "nn1"));
    }

    @Test
    public void testGetServiceState_4() throws Exception {
        assertEquals(0, runTool("-getServiceState", "nn1"));
    }

    @Test
    public void testStateTransition_1_testMerged_1() throws Exception {
        NameNode nnode1 = cluster.getNameNode(0);
        assertTrue(nnode1.isStandbyState());
        assertFalse(nnode1.isStandbyState());
        NameNode nnode2 = cluster.getNameNode(1);
        assertTrue(nnode2.isStandbyState());
        assertFalse(nnode2.isStandbyState());
        assertTrue(nnode2.isObserverState());
    }

    @Test
    public void testStateTransition_2() throws Exception {
        assertEquals(0, runTool("-transitionToActive", "nn1"));
    }

    @Test
    public void testStateTransition_4() throws Exception {
        assertEquals(0, runTool("-transitionToStandby", "nn1"));
    }

    @Test
    public void testStateTransition_7() throws Exception {
        assertEquals(0, runTool("-transitionToActive", "nn2"));
    }

    @Test
    public void testStateTransition_9() throws Exception {
        assertEquals(0, runTool("-transitionToStandby", "nn2"));
    }

    @Test
    public void testStateTransition_11() throws Exception {
        assertEquals(0, runTool("-transitionToObserver", "nn2"));
    }

    @Test
    public void testObserverTransition_1_testMerged_1() throws Exception {
        NameNode nnode1 = cluster.getNameNode(0);
        assertTrue(nnode1.isStandbyState());
        assertFalse(nnode1.isStandbyState());
        assertTrue(nnode1.isObserverState());
        assertFalse(nnode1.isObserverState());
    }

    @Test
    public void testObserverTransition_2() throws Exception {
        assertEquals(0, runTool("-transitionToObserver", "nn1"));
    }

    @Test
    public void testObserverTransition_5() throws Exception {
        assertEquals(0, runTool("-transitionToObserver", "nn1"));
    }

    @Test
    public void testObserverTransition_7() throws Exception {
        assertEquals(0, runTool("-transitionToStandby", "nn1"));
    }

    @Test
    public void testObserverIllegalTransition_1_testMerged_1() throws Exception {
        NameNode nnode1 = cluster.getNameNode(0);
        assertTrue(nnode1.isStandbyState());
        assertFalse(nnode1.isStandbyState());
        assertTrue(nnode1.isActiveState());
        assertTrue(nnode1.isObserverState());
        assertFalse(nnode1.isActiveState());
    }

    @Test
    public void testObserverIllegalTransition_2() throws Exception {
        assertEquals(0, runTool("-transitionToActive", "nn1"));
    }

    @Test
    public void testObserverIllegalTransition_5() throws Exception {
        assertEquals(-1, runTool("-transitionToObserver", "nn1"));
    }

    @Test
    public void testObserverIllegalTransition_7() throws Exception {
        assertEquals(0, runTool("-transitionToStandby", "nn1"));
    }

    @Test
    public void testObserverIllegalTransition_9() throws Exception {
        assertEquals(0, runTool("-transitionToObserver", "nn1"));
    }

    @Test
    public void testObserverIllegalTransition_11() throws Exception {
        assertEquals(-1, runTool("-transitionToActive", "nn1"));
    }

    @Test
    public void testTryFailoverToSafeMode_1() throws Exception {
        assertEquals(-1, runTool("-failover", "nn2", "nn1"));
    }

    @Test
    public void testTryFailoverToSafeMode_2() throws Exception {
        assertTrue("Bad output: " + errOutput, errOutput.contains("is not ready to become active: " + "The NameNode is in safemode"));
    }

    @Test
    public void testCheckHealth_1() throws Exception {
        assertEquals(0, runTool("-checkHealth", "nn1"));
    }

    @Test
    public void testCheckHealth_2() throws Exception {
        assertEquals(0, runTool("-checkHealth", "nn2"));
    }
}
