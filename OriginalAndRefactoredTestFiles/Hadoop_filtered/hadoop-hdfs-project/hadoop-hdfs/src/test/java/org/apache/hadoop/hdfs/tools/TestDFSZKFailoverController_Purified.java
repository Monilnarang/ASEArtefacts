package org.apache.hadoop.hdfs.tools;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_SERVICE_RPC_BIND_HOST_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.ha.ClientBaseWithFixes;
import org.apache.hadoop.ha.HAServiceProtocol.HAServiceState;
import org.apache.hadoop.ha.HAServiceProtocol.RequestSource;
import org.apache.hadoop.ha.HAServiceProtocol.StateChangeRequestInfo;
import org.apache.hadoop.ha.HealthMonitor;
import org.apache.hadoop.ha.TestNodeFencer.AlwaysSucceedFencer;
import org.apache.hadoop.ha.ZKFCTestUtil;
import org.apache.hadoop.ha.ZKFailoverController;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.server.namenode.EditLogFileOutputStream;
import org.apache.hadoop.hdfs.server.namenode.MockNameNodeResourceChecker;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.namenode.ha.HATestUtil;
import org.apache.hadoop.hdfs.server.protocol.NamenodeProtocols;
import org.apache.hadoop.net.ServerSocketUtil;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.alias.CredentialProviderFactory;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.LambdaTestUtils;
import org.apache.hadoop.test.MultithreadedTestUtil.TestContext;
import org.apache.hadoop.test.MultithreadedTestUtil.TestingThread;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.function.Supplier;

public class TestDFSZKFailoverController_Purified extends ClientBaseWithFixes {

    private static final String LOCALHOST_SERVER_ADDRESS = "127.0.0.1";

    private static final String WILDCARD_ADDRESS = "0.0.0.0";

    private Configuration conf;

    private MiniDFSCluster cluster;

    private TestContext ctx;

    private ZKFCThread thr1, thr2;

    private FileSystem fs;

    static {
        EditLogFileOutputStream.setShouldSkipFsyncForTesting(true);
    }

    @Before
    public void setup() throws Exception {
        conf = new Configuration();
        conf.set(ZKFailoverController.ZK_QUORUM_KEY + ".ns1", hostPort);
        conf.set(DFSConfigKeys.DFS_HA_FENCE_METHODS_KEY, AlwaysSucceedFencer.class.getName());
        conf.setBoolean(DFSConfigKeys.DFS_HA_AUTO_FAILOVER_ENABLED_KEY, true);
        conf.setInt(CommonConfigurationKeysPublic.IPC_CLIENT_CONNECTION_MAXIDLETIME_KEY, 0);
        conf.setInt(DFSConfigKeys.DFS_HA_ZKFC_PORT_KEY + ".ns1.nn1", ServerSocketUtil.getPort(10023, 100));
        conf.setInt(DFSConfigKeys.DFS_HA_ZKFC_PORT_KEY + ".ns1.nn2", ServerSocketUtil.getPort(10024, 100));
    }

    private void startCluster() throws Exception {
        MiniDFSNNTopology topology = new MiniDFSNNTopology().addNameservice(new MiniDFSNNTopology.NSConf("ns1").addNN(new MiniDFSNNTopology.NNConf("nn1").setIpcPort(ServerSocketUtil.getPort(10021, 100))).addNN(new MiniDFSNNTopology.NNConf("nn2").setIpcPort(ServerSocketUtil.getPort(10022, 100))));
        cluster = new MiniDFSCluster.Builder(conf).nnTopology(topology).numDataNodes(0).build();
        cluster.waitActive();
        ctx = new TestContext();
        ctx.addThread(thr1 = new ZKFCThread(ctx, 0));
        assertEquals(0, thr1.zkfc.run(new String[] { "-formatZK" }));
        thr1.start();
        waitForHAState(0, HAServiceState.ACTIVE);
        ctx.addThread(thr2 = new ZKFCThread(ctx, 1));
        thr2.start();
        ZKFCTestUtil.waitForHealthState(thr1.zkfc, HealthMonitor.State.SERVICE_HEALTHY, ctx);
        ZKFCTestUtil.waitForHealthState(thr2.zkfc, HealthMonitor.State.SERVICE_HEALTHY, ctx);
        fs = HATestUtil.configureFailoverFs(cluster, conf);
    }

    @After
    public void shutdown() throws Exception {
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
        if (thr1 != null) {
            thr1.interrupt();
            thr1 = null;
        }
        if (thr2 != null) {
            thr2.interrupt();
            thr2 = null;
        }
        if (ctx != null) {
            ctx.stop();
            ctx = null;
        }
    }

    private void waitForHAState(int nnidx, final HAServiceState state) throws TimeoutException, InterruptedException {
        final NameNode nn = cluster.getNameNode(nnidx);
        GenericTestUtils.waitFor(new Supplier<Boolean>() {

            @Override
            public Boolean get() {
                try {
                    return nn.getRpcServer().getServiceStatus().getState() == state;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }, 50, 15000);
    }

    private void waitForZKFCState(DFSZKFailoverController zkfc, final HAServiceState state) throws TimeoutException, InterruptedException {
        GenericTestUtils.waitFor(() -> zkfc.getServiceState() == state, 50, 15000);
    }

    private class ZKFCThread extends TestingThread {

        private final DFSZKFailoverController zkfc;

        public ZKFCThread(TestContext ctx, int idx) {
            super(ctx);
            this.zkfc = DFSZKFailoverController.create(cluster.getConfiguration(idx));
        }

        @Override
        public void doWork() throws Exception {
            try {
                assertEquals(0, zkfc.run(new String[0]));
            } catch (InterruptedException ie) {
            }
        }
    }

    @Test(timeout = 60000)
    public void testFailoverAndBackOnNNShutdown_1_testMerged_1() throws Exception {
        Path p1 = new Path("/dir1");
        Path p2 = new Path("/dir2");
        fs.mkdirs(p1);
        assertTrue(fs.exists(p1));
        fs.mkdirs(p2);
        assertTrue(fs.exists(p2));
    }

    @Test(timeout = 60000)
    public void testFailoverAndBackOnNNShutdown_2() throws Exception {
        assertEquals(AlwaysSucceedFencer.getLastFencedService().getAddress(), thr1.zkfc.getLocalTarget().getAddress());
    }

    @Test(timeout = 60000)
    public void testFailoverAndBackOnNNShutdown_7() throws Exception {
        assertEquals(AlwaysSucceedFencer.getLastFencedService().getAddress(), thr2.zkfc.getLocalTarget().getAddress());
    }
}
