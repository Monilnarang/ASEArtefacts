package org.apache.hadoop.hdfs.server.federation.store;

import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.NAMENODES;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.NAMESERVICES;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.ROUTERS;
import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.verifyException;
import static org.apache.hadoop.hdfs.server.federation.store.FederationStateStoreTestUtils.clearRecords;
import static org.apache.hadoop.hdfs.server.federation.store.FederationStateStoreTestUtils.createMockRegistrationForNamenode;
import static org.apache.hadoop.hdfs.server.federation.store.FederationStateStoreTestUtils.synchronizeRecords;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.hadoop.hdfs.server.federation.resolver.FederationNamenodeServiceState;
import org.apache.hadoop.hdfs.server.federation.resolver.FederationNamespaceInfo;
import org.apache.hadoop.hdfs.server.federation.router.RBFConfigKeys;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetNamenodeRegistrationsRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetNamenodeRegistrationsResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetNamespaceInfoRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetNamespaceInfoResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.NamenodeHeartbeatRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.NamenodeHeartbeatResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.UpdateNamenodeRegistrationRequest;
import org.apache.hadoop.hdfs.server.federation.store.records.MembershipState;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Time;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestStateStoreMembershipState_Purified extends TestStateStoreBase {

    private static MembershipStore membershipStore;

    @BeforeClass
    public static void create() {
        getConf().setLong(RBFConfigKeys.FEDERATION_STORE_MEMBERSHIP_EXPIRATION_MS, TimeUnit.SECONDS.toMillis(2));
        getConf().setLong(RBFConfigKeys.FEDERATION_STORE_MEMBERSHIP_EXPIRATION_DELETION_MS, TimeUnit.SECONDS.toMillis(2));
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        membershipStore = getStateStore().getRegisteredRecordStore(MembershipStore.class);
        assertTrue(clearRecords(getStateStore(), MembershipState.class));
    }

    private void registerAndLoadRegistrations(List<MembershipState> registrationList) throws IOException {
        assertTrue(synchronizeRecords(getStateStore(), registrationList, MembershipState.class));
        assertTrue(getStateStore().loadCache(MembershipStore.class, true));
    }

    private MembershipState createRegistration(String ns, String nn, String router, FederationNamenodeServiceState state) throws IOException {
        MembershipState record = MembershipState.newInstance(router, ns, nn, "testcluster", "testblock-" + ns, "testrpc-" + ns + nn, "testservice-" + ns + nn, "testlifeline-" + ns + nn, "http", "testweb-" + ns + nn, state, false);
        return record;
    }

    private MembershipState getNamenodeRegistration(final String nsId, final String nnId) throws IOException {
        MembershipState partial = MembershipState.newInstance();
        partial.setNameserviceId(nsId);
        partial.setNamenodeId(nnId);
        GetNamenodeRegistrationsRequest request = GetNamenodeRegistrationsRequest.newInstance(partial);
        GetNamenodeRegistrationsResponse response = membershipStore.getNamenodeRegistrations(request);
        List<MembershipState> results = response.getNamenodeMemberships();
        if (results != null && results.size() == 1) {
            MembershipState record = results.get(0);
            return record;
        }
        return null;
    }

    private MembershipState getExpiredNamenodeRegistration(final String nsId, final String nnId) throws IOException {
        MembershipState partial = MembershipState.newInstance();
        partial.setNameserviceId(nsId);
        partial.setNamenodeId(nnId);
        GetNamenodeRegistrationsRequest request = GetNamenodeRegistrationsRequest.newInstance(partial);
        GetNamenodeRegistrationsResponse response = membershipStore.getExpiredNamenodeRegistrations(request);
        List<MembershipState> results = response.getNamenodeMemberships();
        if (results != null && results.size() == 1) {
            MembershipState record = results.get(0);
            return record;
        }
        return null;
    }

    private boolean namenodeHeartbeat(MembershipState namenode) throws IOException {
        NamenodeHeartbeatRequest request = NamenodeHeartbeatRequest.newInstance(namenode);
        NamenodeHeartbeatResponse response = membershipStore.namenodeHeartbeat(request);
        return response.getResult();
    }

    @Test
    public void testNamenodeStateOverride_1_testMerged_1() throws Exception {
        String ns = "ns0";
        String nn = "nn0";
        MembershipState report = createRegistration(ns, nn, ROUTERS[1], FederationNamenodeServiceState.STANDBY);
        assertTrue(namenodeHeartbeat(report));
        MembershipState existingState = getNamenodeRegistration(ns, nn);
        assertEquals(FederationNamenodeServiceState.STANDBY, existingState.getState());
        UpdateNamenodeRegistrationRequest request = UpdateNamenodeRegistrationRequest.newInstance(ns, nn, FederationNamenodeServiceState.ACTIVE);
        assertTrue(membershipStore.updateNamenodeRegistration(request).getResult());
        MembershipState newState = getNamenodeRegistration(ns, nn);
        assertEquals(FederationNamenodeServiceState.ACTIVE, newState.getState());
        UpdateNamenodeRegistrationRequest request1 = UpdateNamenodeRegistrationRequest.newInstance(ns, nn, FederationNamenodeServiceState.OBSERVER);
        assertTrue(membershipStore.updateNamenodeRegistration(request1).getResult());
        MembershipState newState1 = getNamenodeRegistration(ns, nn);
        assertEquals(FederationNamenodeServiceState.OBSERVER, newState1.getState());
    }

    @Test
    public void testNamenodeStateOverride_2() throws Exception {
        assertTrue(getStateStore().loadCache(MembershipStore.class, true));
    }
}
