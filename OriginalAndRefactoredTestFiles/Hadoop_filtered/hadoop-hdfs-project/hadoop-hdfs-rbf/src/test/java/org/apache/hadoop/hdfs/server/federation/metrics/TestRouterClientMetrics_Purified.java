package org.apache.hadoop.hdfs.server.federation.metrics;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster;
import org.apache.hadoop.hdfs.server.federation.MockResolver;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.router.Router;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import static org.apache.hadoop.test.MetricsAsserts.assertCounter;
import static org.apache.hadoop.test.MetricsAsserts.getMetrics;

public class TestRouterClientMetrics_Purified {

    private static final Configuration CONF = new HdfsConfiguration();

    private static final String ROUTER_METRICS = "RouterClientActivity";

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
    public void testGetListing_1() throws IOException {
        assertCounter("GetListingOps", 2L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testGetListing_2() throws IOException {
        assertCounter("ConcurrentGetListingOps", 1L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testSetQuota_1() throws Exception {
        assertCounter("SetQuotaOps", 2L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testSetQuota_2() throws Exception {
        assertCounter("ConcurrentSetQuotaOps", 1L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testGetQuota_1() throws Exception {
        assertCounter("GetQuotaUsageOps", 2L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testGetQuota_2() throws Exception {
        assertCounter("ConcurrentGetQuotaUsageOps", 1L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testRenewLease_1() throws Exception {
        assertCounter("RenewLeaseOps", 2L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testRenewLease_2() throws Exception {
        assertCounter("ConcurrentRenewLeaseOps", 1L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testGetDatanodeReport_1() throws Exception {
        assertCounter("GetDatanodeReportOps", 2L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testGetDatanodeReport_2() throws Exception {
        assertCounter("ConcurrentGetDatanodeReportOps", 1L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testGetSlowDatanodeReport_1() throws Exception {
        assertCounter("GetSlowDatanodeReportOps", 2L, getMetrics(ROUTER_METRICS));
    }

    @Test
    public void testGetSlowDatanodeReport_2() throws Exception {
        assertCounter("ConcurrentGetSlowDatanodeReportOps", 1L, getMetrics(ROUTER_METRICS));
    }
}
