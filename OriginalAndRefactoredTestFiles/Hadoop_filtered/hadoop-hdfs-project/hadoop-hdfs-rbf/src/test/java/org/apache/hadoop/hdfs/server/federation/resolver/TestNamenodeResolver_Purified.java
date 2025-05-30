package org.apache.hadoop.hdfs.server.federation.resolver;

import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.NAMENODES;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.NAMESERVICES;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.ROUTERS;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.createNamenodeReport;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.verifyException;
import static org.apache.hadoop.hdfs.server.federation.store.FederationStateStoreTestUtils.clearRecords;
import static org.apache.hadoop.hdfs.server.federation.store.FederationStateStoreTestUtils.getStateStoreConfiguration;
import static org.apache.hadoop.hdfs.server.federation.store.FederationStateStoreTestUtils.newStateStore;
import static org.apache.hadoop.hdfs.server.federation.store.FederationStateStoreTestUtils.waitStateStore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ha.HAServiceProtocol.HAServiceState;
import org.apache.hadoop.hdfs.server.federation.router.RBFConfigKeys;
import org.apache.hadoop.hdfs.server.federation.store.StateStoreService;
import org.apache.hadoop.hdfs.server.federation.store.StateStoreUnavailableException;
import org.apache.hadoop.hdfs.server.federation.store.records.MembershipState;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestNamenodeResolver_Purified {

    private static StateStoreService stateStore;

    private static ActiveNamenodeResolver namenodeResolver;

    @BeforeClass
    public static void create() throws Exception {
        Configuration conf = getStateStoreConfiguration();
        conf.setLong(RBFConfigKeys.FEDERATION_STORE_MEMBERSHIP_EXPIRATION_MS, TimeUnit.SECONDS.toMillis(5));
        stateStore = newStateStore(conf);
        assertNotNull(stateStore);
        namenodeResolver = new MembershipNamenodeResolver(conf, stateStore);
        namenodeResolver.setRouterId(ROUTERS[0]);
    }

    @AfterClass
    public static void destroy() throws Exception {
        stateStore.stop();
        stateStore.close();
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        stateStore.loadDriver();
        waitStateStore(stateStore, 10000);
        boolean cleared = clearRecords(stateStore, MembershipState.class);
        assertTrue(cleared);
    }

    private void verifyFirstRegistration(String nsId, String nnId, int resultsCount, FederationNamenodeServiceState state) throws IOException {
        List<? extends FederationNamenodeContext> namenodes = namenodeResolver.getNamenodesForNameserviceId(nsId, false);
        if (resultsCount == 0) {
            assertNull(namenodes);
        } else {
            assertEquals(resultsCount, namenodes.size());
            if (namenodes.size() > 0) {
                FederationNamenodeContext namenode = namenodes.get(0);
                assertEquals(state, namenode.getState());
                assertEquals(nnId, namenode.getNamenodeId());
            }
        }
    }

    private static InetSocketAddress getInetSocketAddress(String rpcAddr) {
        String[] rpcAddrArr = rpcAddr.split(":");
        int port = Integer.parseInt(rpcAddrArr[1]);
        String hostname = rpcAddrArr[0];
        return new InetSocketAddress(hostname, port);
    }

    @Test
    public void testCacheUpdateOnNamenodeStateUpdate_1() throws IOException {
        assertTrue(namenodeResolver.registerNamenode(createNamenodeReport(NAMESERVICES[0], NAMENODES[0], HAServiceState.STANDBY)));
    }

    @Test
    public void testCacheUpdateOnNamenodeStateUpdate_2_testMerged_2() throws IOException {
        FederationNamenodeContext namenode = namenodeResolver.getNamenodesForNameserviceId(NAMESERVICES[0], false).get(0);
        assertEquals(FederationNamenodeServiceState.STANDBY, namenode.getState());
        namenodeResolver.updateActiveNamenode(NAMESERVICES[0], inetAddr);
        FederationNamenodeContext namenode1 = namenodeResolver.getNamenodesForNameserviceId(NAMESERVICES[0], false).get(0);
        assertEquals("The namenode state should be ACTIVE post update.", FederationNamenodeServiceState.ACTIVE, namenode1.getState());
    }

    @Test
    public void testCacheUpdateOnNamenodeStateUpdateWithIp_1() throws IOException {
        final String rpcAddress = "127.0.0.1:10000";
        assertTrue(namenodeResolver.registerNamenode(createNamenodeReport(NAMESERVICES[0], NAMENODES[0], rpcAddress, HAServiceState.STANDBY)));
    }

    @Test
    public void testCacheUpdateOnNamenodeStateUpdateWithIp_2() throws IOException {
        namenodeResolver.updateActiveNamenode(NAMESERVICES[0], inetAddr);
        FederationNamenodeContext namenode = namenodeResolver.getNamenodesForNameserviceId(NAMESERVICES[0], false).get(0);
        assertEquals("The namenode state should be ACTIVE post update.", FederationNamenodeServiceState.ACTIVE, namenode.getState());
    }
}
