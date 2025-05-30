package org.apache.hadoop.hdfs.server.namenode.ha;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_STATE_CONTEXT_ENABLED_KEY;
import static org.apache.hadoop.test.MetricsAsserts.getLongCounter;
import static org.apache.hadoop.test.MetricsAsserts.getMetrics;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.FileContext;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.ha.HAServiceProtocol.HAServiceState;
import org.apache.hadoop.ha.HAServiceStatus;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.client.HdfsClientConfigKeys;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.hdfs.qjournal.MiniQJMHACluster;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.RpcScheduler;
import org.apache.hadoop.ipc.Schedulable;
import org.apache.hadoop.ipc.StandbyException;
import org.apache.hadoop.metrics2.MetricsRecordBuilder;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Time;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestConsistentReadsObserver_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestConsistentReadsObserver.class.getName());

    private static Configuration conf;

    private static MiniQJMHACluster qjmhaCluster;

    private static MiniDFSCluster dfsCluster;

    private DistributedFileSystem dfs;

    private final Path testPath = new Path("/TestConsistentReadsObserver");

    @BeforeClass
    public static void startUpCluster() throws Exception {
        conf = new Configuration();
        conf.setBoolean(DFS_NAMENODE_STATE_CONTEXT_ENABLED_KEY, true);
        qjmhaCluster = HATestUtil.setUpObserverCluster(conf, 1, 0, false);
        dfsCluster = qjmhaCluster.getDfsCluster();
    }

    @Before
    public void setUp() throws Exception {
        dfs = setObserverRead(true);
    }

    @After
    public void cleanUp() throws IOException {
        dfs.delete(testPath, true);
    }

    @AfterClass
    public static void shutDownCluster() throws IOException {
        if (qjmhaCluster != null) {
            qjmhaCluster.shutdown();
        }
    }

    private void assertSentTo(int nnIdx) throws IOException {
        assertTrue("Request was not sent to the expected namenode " + nnIdx, HATestUtil.isSentToAnyOfNameNodes(dfs, dfsCluster, nnIdx));
    }

    private DistributedFileSystem setObserverRead(boolean flag) throws Exception {
        return HATestUtil.configureObserverReadFs(dfsCluster, conf, ObserverReadProxyProvider.class, flag);
    }

    public static class TestRpcScheduler implements RpcScheduler {

        private int allowed = 10;

        public TestRpcScheduler() {
        }

        @Override
        public int getPriorityLevel(Schedulable obj) {
            return 0;
        }

        @Override
        public boolean shouldBackOff(Schedulable obj) {
            return --allowed < 0;
        }

        @Override
        public void stop() {
        }
    }

    @Test
    public void testRequeueCall_1_testMerged_1() throws Exception {
        final int observerIdx = 2;
        NameNode nn = dfsCluster.getNameNode(observerIdx);
        int port = nn.getNameNodeAddress().getPort();
        Configuration originalConf = dfsCluster.getConfiguration(observerIdx);
        Configuration configuration = new Configuration(originalConf);
        String prefix = CommonConfigurationKeys.IPC_NAMESPACE + "." + port + ".";
        configuration.set(prefix + CommonConfigurationKeys.IPC_SCHEDULER_IMPL_KEY, TestRpcScheduler.class.getName());
        configuration.setBoolean(prefix + CommonConfigurationKeys.IPC_BACKOFF_ENABLE, true);
        NameNodeAdapter.getRpcServer(nn).refreshCallQueue(configuration);
        assertThat(NameNodeAdapter.getRpcServer(nn).getTotalRequests()).isGreaterThan(0);
        assertThat(NameNodeAdapter.getRpcServer(nn).getTotalRequests()).isGreaterThan(1);
    }

    @Test
    public void testRequeueCall_2() throws Exception {
        assertSentTo(0);
    }

    @Test
    public void testRequeueCall_3() throws Exception {
        assertSentTo(0);
    }
}
