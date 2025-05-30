package org.apache.hadoop.hdfs.server.federation.metrics;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster;
import org.apache.hadoop.hdfs.server.federation.MockResolver;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.router.Router;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import static org.apache.hadoop.hdfs.server.federation.metrics.FederationRPCPerformanceMonitor.CONCURRENT;
import static org.apache.hadoop.hdfs.server.federation.metrics.NameserviceRPCMetrics.NAMESERVICE_RPC_METRICS_PREFIX;
import static org.apache.hadoop.test.MetricsAsserts.assertCounter;
import static org.apache.hadoop.test.MetricsAsserts.getLongCounter;
import static org.apache.hadoop.test.MetricsAsserts.getMetrics;

public class TestNameserviceRPCMetrics_Purified {

    private static final Configuration CONF = new HdfsConfiguration();

    static {
        CONF.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, 100);
        CONF.setInt(DFSConfigKeys.DFS_BYTES_PER_CHECKSUM_KEY, 1);
        CONF.setLong(DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY, 1L);
        CONF.setInt(DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_INTERVAL_SECONDS_KEY, 1);
    }

    private static final int NUM_SUBCLUSTERS = 2;

    private static final int NUM_DNS = 3;

    private static MiniRouterDFSCluster cluster;

    private MiniRouterDFSCluster.RouterContext routerContext;

    private Router router;

    private FileSystem routerFS;

    private FileSystem nnFS;

    @BeforeClass
    public static void globalSetUp() throws Exception {
        cluster = new MiniRouterDFSCluster(false, NUM_SUBCLUSTERS);
        cluster.setNumDatanodesPerNameservice(NUM_DNS);
        cluster.startCluster();
        Configuration routerConf = new RouterConfigBuilder().metrics().rpc().quota().build();
        cluster.addRouterOverrides(routerConf);
        cluster.startRouters();
        cluster.registerNamenodes();
        cluster.waitNamenodeRegistration();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        cluster.shutdown();
    }

    @Test
    public void testProxyOp_1() throws IOException {
        assertCounter("ProxyOp", 1L, getMetrics(NAMESERVICE_RPC_METRICS_PREFIX + "ns0"));
    }

    @Test
    public void testProxyOp_2() throws IOException {
        assertCounter("ProxyOp", 0L, getMetrics(NAMESERVICE_RPC_METRICS_PREFIX + "ns1"));
    }

    @Test
    public void testProxyOp_3() throws IOException {
        assertCounter("ProxyOp", 1L, getMetrics(NAMESERVICE_RPC_METRICS_PREFIX + "ns0"));
    }

    @Test
    public void testProxyOp_4() throws IOException {
        assertCounter("ProxyOp", 1L, getMetrics(NAMESERVICE_RPC_METRICS_PREFIX + "ns1"));
    }
}
