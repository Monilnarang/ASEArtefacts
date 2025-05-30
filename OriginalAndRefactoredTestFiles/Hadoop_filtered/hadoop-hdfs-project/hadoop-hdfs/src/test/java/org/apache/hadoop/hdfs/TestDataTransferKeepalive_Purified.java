package org.apache.hadoop.hdfs;

import static org.apache.hadoop.hdfs.client.HdfsClientConfigKeys.DFS_CLIENT_CONTEXT;
import static org.apache.hadoop.hdfs.client.HdfsClientConfigKeys.DFS_CLIENT_MAX_BLOCK_ACQUIRE_FAILURES_KEY;
import static org.apache.hadoop.hdfs.client.HdfsClientConfigKeys.DFS_CLIENT_SOCKET_CACHE_EXPIRY_MSEC_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_SOCKET_REUSE_KEEPALIVE_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_SOCKET_REUSE_KEEPALIVE_KEY;
import static org.apache.hadoop.hdfs.client.HdfsClientConfigKeys.DFS_DATANODE_SOCKET_WRITE_TIMEOUT_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.InputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster.DataNodeProperties;
import org.apache.hadoop.hdfs.net.Peer;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.function.Supplier;

public class TestDataTransferKeepalive_Purified {

    final Configuration conf = new HdfsConfiguration();

    private MiniDFSCluster cluster;

    private DataNode dn;

    private static final Path TEST_FILE = new Path("/test");

    private static final int KEEPALIVE_TIMEOUT = 1000;

    private static final int WRITE_TIMEOUT = 3000;

    @Before
    public void setup() throws Exception {
        conf.setInt(DFS_DATANODE_SOCKET_REUSE_KEEPALIVE_KEY, KEEPALIVE_TIMEOUT);
        conf.setInt(DFS_CLIENT_MAX_BLOCK_ACQUIRE_FAILURES_KEY, 0);
        cluster = new MiniDFSCluster.Builder(conf).numDataNodes(1).build();
        dn = cluster.getDataNodes().get(0);
    }

    @After
    public void teardown() {
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
    }

    private void assertXceiverCount(int expected) {
        int count = getXceiverCountWithoutServer();
        if (count != expected) {
            ReflectionUtils.printThreadInfo(System.err, "Thread dumps");
            fail("Expected " + expected + " xceivers, found " + count);
        }
    }

    private int getXceiverCountWithoutServer() {
        return dn.getXceiverCount();
    }

    @Test(timeout = 300000)
    public void testSlowReader_1() throws Exception {
        final long CLIENT_EXPIRY_MS = 600000L;
        Configuration clientConf = new Configuration(conf);
        clientConf.setLong(DFS_CLIENT_SOCKET_CACHE_EXPIRY_MSEC_KEY, CLIENT_EXPIRY_MS);
        clientConf.set(DFS_CLIENT_CONTEXT, "testSlowReader");
        DistributedFileSystem fs = (DistributedFileSystem) FileSystem.get(cluster.getURI(), clientConf);
        DataNodeProperties props = cluster.stopDataNode(0);
        props.conf.setInt(DFS_DATANODE_SOCKET_WRITE_TIMEOUT_KEY, WRITE_TIMEOUT);
        props.conf.setInt(DFS_DATANODE_SOCKET_REUSE_KEEPALIVE_KEY, 120000);
        assertTrue(cluster.restartDataNode(props, true));
    }

    @Test(timeout = 300000)
    public void testSlowReader_2() throws Exception {
        assertXceiverCount(1);
    }
}
