package org.apache.hadoop.hdfs.server.namenode.ha;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_STATE_CONTEXT_ENABLED_KEY;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.qjournal.MiniQJMHACluster;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMultiObserverNode_Purified {

    private static Configuration conf;

    private static MiniQJMHACluster qjmhaCluster;

    private static MiniDFSCluster dfsCluster;

    private static DistributedFileSystem dfs;

    private final Path testPath = new Path("/TestMultiObserverNode");

    @BeforeClass
    public static void startUpCluster() throws Exception {
        conf = new Configuration();
        conf.setBoolean(DFS_NAMENODE_STATE_CONTEXT_ENABLED_KEY, true);
        qjmhaCluster = HATestUtil.setUpObserverCluster(conf, 2, 0, true);
        dfsCluster = qjmhaCluster.getDfsCluster();
        dfs = HATestUtil.configureObserverReadFs(dfsCluster, conf, ObserverReadProxyProvider.class, true);
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

    private void assertSentTo(int... nnIndices) throws IOException {
        assertTrue("Request was not sent to any of the expected namenodes.", HATestUtil.isSentToAnyOfNameNodes(dfs, dfsCluster, nnIndices));
    }

    @Test
    public void testObserverFailover_1() throws Exception {
        assertSentTo(2, 3);
    }

    @Test
    public void testObserverFailover_2() throws Exception {
        assertSentTo(3);
    }

    @Test
    public void testObserverFailover_3() throws Exception {
        assertSentTo(0);
    }

    @Test
    public void testObserverFailover_4() throws Exception {
        assertSentTo(2);
    }

    @Test
    public void testObserverFailover_5() throws Exception {
        assertSentTo(2, 3);
    }

    @Test
    public void testMultiObserver_1() throws Exception {
        assertSentTo(0);
    }

    @Test
    public void testMultiObserver_2() throws Exception {
        assertSentTo(2, 3);
    }

    @Test
    public void testMultiObserver_3() throws Exception {
        assertSentTo(3);
    }

    @Test
    public void testMultiObserver_4() throws Exception {
        assertSentTo(3);
    }

    @Test
    public void testMultiObserver_5() throws Exception {
        assertSentTo(2, 3);
    }

    @Test
    public void testMultiObserver_6() throws Exception {
        assertSentTo(2);
    }

    @Test
    public void testMultiObserver_7() throws Exception {
        assertSentTo(0);
    }

    @Test
    public void testObserverFallBehind_1() throws Exception {
        assertSentTo(0);
    }

    @Test
    public void testObserverFallBehind_2() throws Exception {
        assertSentTo(0);
    }
}
