package org.apache.hadoop.hdfs.server.federation.router;

import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.HADOOP_CALLER_CONTEXT_MAX_SIZE_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_AUDIT_LOG_WITH_REMOTE_PORT_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_CONSIDERLOAD_KEY;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.addDirectory;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.countContents;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.createFile;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.deleteFile;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.getFileStatus;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.verifyFileExists;
import static org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.TEST_STRING;
import static org.apache.hadoop.ipc.CallerContext.PROXY_USER_PORT;
import static org.apache.hadoop.test.GenericTestUtils.assertExceptionContains;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.crypto.CryptoProtocolVersion;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.CreateFlag;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileContext;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsServerDefaults;
import org.apache.hadoop.fs.Options;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.SafeModeAction;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSCluster.DataNodeProperties;
import org.apache.hadoop.hdfs.NameNodeProxies;
import org.apache.hadoop.hdfs.client.HdfsDataOutputStream;
import org.apache.hadoop.hdfs.protocol.AddErasureCodingPolicyResponse;
import org.apache.hadoop.hdfs.protocol.BlockStoragePolicy;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveInfo;
import org.apache.hadoop.hdfs.protocol.CachePoolEntry;
import org.apache.hadoop.hdfs.protocol.CachePoolInfo;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.DirectoryListing;
import org.apache.hadoop.hdfs.protocol.ECBlockGroupStats;
import org.apache.hadoop.hdfs.protocol.ErasureCodingPolicy;
import org.apache.hadoop.hdfs.protocol.ErasureCodingPolicyInfo;
import org.apache.hadoop.hdfs.protocol.ErasureCodingPolicyState;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.protocol.LocatedBlocks;
import org.apache.hadoop.hdfs.protocol.ReplicatedBlockStats;
import org.apache.hadoop.hdfs.protocol.SnapshotDiffReport;
import org.apache.hadoop.hdfs.protocol.SnapshotDiffReportListing;
import org.apache.hadoop.hdfs.protocol.SnapshotException;
import org.apache.hadoop.hdfs.protocol.SnapshottableDirectoryStatus;
import org.apache.hadoop.hdfs.protocol.SnapshotStatus;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.DatanodeReportType;
import org.apache.hadoop.hdfs.security.token.block.ExportedBlockKeys;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManager;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManagerTestUtil;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.NamenodeContext;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.RouterContext;
import org.apache.hadoop.hdfs.server.federation.MockResolver;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.metrics.FederationRPCMetrics;
import org.apache.hadoop.hdfs.server.federation.metrics.NamenodeBeanMetrics;
import org.apache.hadoop.hdfs.server.federation.metrics.RBFMetrics;
import org.apache.hadoop.hdfs.server.federation.resolver.FileSubclusterResolver;
import org.apache.hadoop.hdfs.server.namenode.FSDirectory;
import org.apache.hadoop.hdfs.server.namenode.FSNamesystem;
import org.apache.hadoop.hdfs.server.namenode.INodeDirectory;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.hdfs.server.namenode.snapshot.SnapshotTestHelper;
import org.apache.hadoop.hdfs.server.protocol.BlocksWithLocations;
import org.apache.hadoop.hdfs.server.protocol.BlocksWithLocations.BlockWithLocations;
import org.apache.hadoop.hdfs.server.protocol.DatanodeStorageReport;
import org.apache.hadoop.hdfs.server.protocol.NamenodeProtocol;
import org.apache.hadoop.hdfs.server.protocol.NamespaceInfo;
import org.apache.hadoop.io.EnumSetWritable;
import org.apache.hadoop.io.erasurecode.ECSchema;
import org.apache.hadoop.io.erasurecode.ErasureCodeConstants;
import org.apache.hadoop.ipc.CallerContext;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.service.Service.STATE;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.LambdaTestUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Supplier;
import org.apache.hadoop.thirdparty.com.google.common.collect.Maps;

public class TestRouterRpc_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestRouterRpc.class);

    private static final int NUM_SUBCLUSTERS = 2;

    private static final int NUM_DNS = 6;

    private static final Comparator<ErasureCodingPolicyInfo> EC_POLICY_CMP = new Comparator<ErasureCodingPolicyInfo>() {

        public int compare(ErasureCodingPolicyInfo ec0, ErasureCodingPolicyInfo ec1) {
            String name0 = ec0.getPolicy().getName();
            String name1 = ec1.getPolicy().getName();
            return name0.compareTo(name1);
        }
    };

    private static MiniRouterDFSCluster cluster;

    private RouterContext router;

    private String ns;

    private NamenodeContext namenode;

    private ClientProtocol routerProtocol;

    private ClientProtocol nnProtocol;

    private NamenodeProtocol routerNamenodeProtocol;

    private NamenodeProtocol nnNamenodeProtocol;

    private NamenodeProtocol nnNamenodeProtocol1;

    private FileSystem routerFS;

    private FileSystem nnFS;

    private String routerFile;

    private String nnFile;

    @BeforeClass
    public static void globalSetUp() throws Exception {
        Configuration namenodeConf = new Configuration();
        namenodeConf.setBoolean(DFSConfigKeys.HADOOP_CALLER_CONTEXT_ENABLED_KEY, true);
        namenodeConf.set(HADOOP_CALLER_CONTEXT_MAX_SIZE_KEY, "256");
        namenodeConf.setBoolean(DFS_NAMENODE_REDUNDANCY_CONSIDERLOAD_KEY, false);
        namenodeConf.setBoolean(DFS_NAMENODE_AUDIT_LOG_WITH_REMOTE_PORT_KEY, true);
        cluster = new MiniRouterDFSCluster(false, NUM_SUBCLUSTERS);
        cluster.setNumDatanodesPerNameservice(NUM_DNS);
        cluster.addNamenodeOverrides(namenodeConf);
        cluster.setIndependentDNs();
        Configuration conf = new Configuration();
        conf.set("hadoop.proxyuser.testRealUser.groups", "*");
        conf.set("hadoop.proxyuser.testRealUser.hosts", "*");
        String loginUser = UserGroupInformation.getLoginUser().getUserName();
        conf.set(String.format("hadoop.proxyuser.%s.groups", loginUser), "*");
        conf.set(String.format("hadoop.proxyuser.%s.hosts", loginUser), "*");
        conf.set(DFSConfigKeys.DFS_NAMENODE_IP_PROXY_USERS, "placeholder");
        conf.setInt(DFSConfigKeys.DFS_LIST_LIMIT, 5);
        cluster.addNamenodeOverrides(conf);
        cluster.startCluster();
        Configuration routerConf = new RouterConfigBuilder().metrics().rpc().build();
        routerConf.setTimeDuration(RBFConfigKeys.DN_REPORT_CACHE_EXPIRE, 1, TimeUnit.SECONDS);
        cluster.addRouterOverrides(routerConf);
        cluster.startRouters();
        cluster.registerNamenodes();
        cluster.waitNamenodeRegistration();
        cluster.getCluster().getNamesystem(0).getBlockManager().getDatanodeManager().setHeartbeatInterval(1);
        cluster.getCluster().getNamesystem(1).getBlockManager().getDatanodeManager().setHeartbeatInterval(1);
        cluster.getCluster().getNamesystem(0).getBlockManager().getDatanodeManager().setHeartbeatExpireInterval(3000);
        cluster.getCluster().getNamesystem(1).getBlockManager().getDatanodeManager().setHeartbeatExpireInterval(3000);
    }

    @AfterClass
    public static void tearDown() {
        cluster.shutdown();
    }

    protected MiniRouterDFSCluster getCluster() {
        return TestRouterRpc.cluster;
    }

    protected RouterContext getRouterContext() {
        return this.router;
    }

    protected void setRouter(RouterContext r) throws IOException, URISyntaxException {
        this.router = r;
        this.routerProtocol = r.getClient().getNamenode();
        this.routerFS = r.getFileSystem();
        this.routerNamenodeProtocol = NameNodeProxies.createProxy(router.getConf(), router.getFileSystem().getUri(), NamenodeProtocol.class).getProxy();
    }

    protected FileSystem getRouterFileSystem() {
        return this.routerFS;
    }

    protected FileSystem getNamenodeFileSystem() {
        return this.nnFS;
    }

    protected ClientProtocol getRouterProtocol() {
        return this.routerProtocol;
    }

    protected ClientProtocol getNamenodeProtocol() {
        return this.nnProtocol;
    }

    protected NamenodeContext getNamenode() {
        return this.namenode;
    }

    protected void setNamenodeFile(String filename) {
        this.nnFile = filename;
    }

    protected String getNamenodeFile() {
        return this.nnFile;
    }

    protected void setRouterFile(String filename) {
        this.routerFile = filename;
    }

    protected String getRouterFile() {
        return this.routerFile;
    }

    protected void setNamenode(NamenodeContext nn) throws IOException, URISyntaxException {
        this.namenode = nn;
        this.nnProtocol = nn.getClient().getNamenode();
        this.nnFS = nn.getFileSystem();
        String ns0 = cluster.getNameservices().get(0);
        NamenodeContext nn0 = cluster.getNamenode(ns0, null);
        this.nnNamenodeProtocol = NameNodeProxies.createProxy(nn0.getConf(), nn0.getFileSystem().getUri(), NamenodeProtocol.class).getProxy();
        String ns1 = cluster.getNameservices().get(1);
        NamenodeContext nn1 = cluster.getNamenode(ns1, null);
        this.nnNamenodeProtocol1 = NameNodeProxies.createProxy(nn1.getConf(), nn1.getFileSystem().getUri(), NamenodeProtocol.class).getProxy();
    }

    protected String getNs() {
        return this.ns;
    }

    protected void setNs(String nameservice) {
        this.ns = nameservice;
    }

    protected static void compareResponses(ClientProtocol protocol1, ClientProtocol protocol2, Method m, Object[] paramList) {
        Object return1 = null;
        Exception exception1 = null;
        try {
            return1 = m.invoke(protocol1, paramList);
        } catch (Exception ex) {
            exception1 = ex;
        }
        Object return2 = null;
        Exception exception2 = null;
        try {
            return2 = m.invoke(protocol2, paramList);
        } catch (Exception ex) {
            exception2 = ex;
        }
        assertEquals(return1, return2);
        if (exception1 == null && exception2 == null) {
            return;
        }
        assertEquals(exception1.getCause().getClass(), exception2.getCause().getClass());
    }

    private long[] getAggregateStats() throws Exception {
        long[] individualData = new long[10];
        for (String nameservice : cluster.getNameservices()) {
            NamenodeContext n = cluster.getNamenode(nameservice, null);
            DFSClient client = n.getClient();
            ClientProtocol clientProtocol = client.getNamenode();
            long[] data = clientProtocol.getStats();
            for (int i = 0; i < data.length; i++) {
                individualData[i] += data[i];
            }
        }
        return individualData;
    }

    private void compareVersion(NamespaceInfo rVersion, NamespaceInfo nnVersion) {
        assertEquals(nnVersion.getBlockPoolID(), rVersion.getBlockPoolID());
        assertEquals(nnVersion.getNamespaceID(), rVersion.getNamespaceID());
        assertEquals(nnVersion.getClusterID(), rVersion.getClusterID());
        assertEquals(nnVersion.getLayoutVersion(), rVersion.getLayoutVersion());
        assertEquals(nnVersion.getCTime(), rVersion.getCTime());
    }

    private void compareBlockKeys(ExportedBlockKeys rKeys, ExportedBlockKeys nnKeys) {
        assertEquals(nnKeys.getCurrentKey(), rKeys.getCurrentKey());
        assertEquals(nnKeys.getKeyUpdateInterval(), rKeys.getKeyUpdateInterval());
        assertEquals(nnKeys.getTokenLifetime(), rKeys.getTokenLifetime());
    }

    private ECBlockGroupStats getNamenodeECBlockGroupStats() throws Exception {
        List<ECBlockGroupStats> nnStats = new ArrayList<>();
        for (NamenodeContext nnContext : cluster.getNamenodes()) {
            ClientProtocol cp = nnContext.getClient().getNamenode();
            nnStats.add(cp.getECBlockGroupStats());
        }
        return ECBlockGroupStats.merge(nnStats);
    }

    private ErasureCodingPolicyInfo[] checkErasureCodingPolicies() throws IOException {
        ErasureCodingPolicyInfo[] policiesRouter = routerProtocol.getErasureCodingPolicies();
        assertNotNull(policiesRouter);
        ErasureCodingPolicyInfo[] policiesNamenode = nnProtocol.getErasureCodingPolicies();
        Arrays.sort(policiesRouter, EC_POLICY_CMP);
        Arrays.sort(policiesNamenode, EC_POLICY_CMP);
        assertArrayEquals(policiesRouter, policiesNamenode);
        return policiesRouter;
    }

    private DFSClient getFileDFSClient(final String path) {
        for (String nsId : cluster.getNameservices()) {
            LOG.info("Checking {} for {}", nsId, path);
            NamenodeContext nn = cluster.getNamenode(nsId, null);
            try {
                DFSClient nnClientProtocol = nn.getClient();
                if (nnClientProtocol.getFileInfo(path) != null) {
                    return nnClientProtocol;
                }
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Test
    public void testRBFMetricsMethodsRelayOnStateStore_1() {
        assertNull(router.getRouter().getStateStore());
    }

    @Test
    public void testRBFMetricsMethodsRelayOnStateStore_2_testMerged_2() {
        RBFMetrics metrics = router.getRouter().getMetrics();
        assertEquals("{}", metrics.getNamenodes());
        assertEquals("[]", metrics.getMountTable());
        assertEquals("{}", metrics.getRouters());
        assertEquals(0, metrics.getNumNamenodes());
        assertEquals(0, metrics.getNumExpiredNamenodes());
        assertEquals("[]", metrics.getClusterId());
        assertEquals("[]", metrics.getBlockPoolId());
        assertEquals("{}", metrics.getNameservices());
        assertEquals(0, metrics.getNumLiveNodes());
    }

    @Test
    public void testMkdirsWithCallerContext_1() throws IOException {
        assertNull(CallerContext.getCurrent());
    }

    @Test
    public void testMkdirsWithCallerContext_2_testMerged_2() throws IOException {
        GenericTestUtils.LogCapturer auditlog = GenericTestUtils.LogCapturer.captureLogs(FSNamesystem.AUDIT_LOG);
        final String logOutput = auditlog.getOutput();
        assertTrue(logOutput.contains("callerContext=clientIp:"));
        assertTrue(logOutput.contains(",clientContext"));
        assertTrue(logOutput.contains(",clientId"));
        assertTrue(logOutput.contains(",clientCallId"));
    }

    @Test
    public void testMkdirsWithCallerContext_6() throws IOException {
        String dirPath = "/test_dir_with_callercontext";
        routerProtocol.mkdirs(dirPath, permission, false);
        assertTrue(verifyFileExists(routerFS, dirPath));
    }

    @Test
    public void testAddClientIpPortToCallerContext_1_testMerged_1() throws IOException {
        GenericTestUtils.LogCapturer auditLog = GenericTestUtils.LogCapturer.captureLogs(FSNamesystem.AUDIT_LOG);
        assertTrue(auditLog.getOutput().contains("clientIp:"));
        assertTrue(auditLog.getOutput().contains("clientPort:"));
        auditLog.clearOutput();
        assertFalse(auditLog.getOutput().contains("clientIp:1.1.1.1"));
        assertFalse(auditLog.getOutput().contains("clientPort:1234"));
    }

    @Test
    public void testAddClientIpPortToCallerContext_3() throws IOException {
        String dirPath = "/test";
        routerProtocol.mkdirs(dirPath, new FsPermission("755"), false);
        assertTrue(verifyFileExists(routerFS, dirPath));
    }

    @Test
    public void testAddClientIdAndCallIdToCallerContext_1_testMerged_1() throws IOException {
        GenericTestUtils.LogCapturer auditLog = GenericTestUtils.LogCapturer.captureLogs(FSNamesystem.AUDIT_LOG);
        assertTrue(auditLog.getOutput().contains("clientId:"));
        assertTrue(auditLog.getOutput().contains("clientCallId:"));
        auditLog.clearOutput();
        assertFalse(auditLog.getOutput().contains("clientId:mockClientId"));
        assertFalse(auditLog.getOutput().contains("clientCallId:4321"));
    }

    @Test
    public void testAddClientIdAndCallIdToCallerContext_3() throws IOException {
        String dirPath = "/test";
        routerProtocol.mkdirs(dirPath, new FsPermission("755"), false);
        assertTrue(verifyFileExists(routerFS, dirPath));
    }
}
