package org.apache.hadoop.yarn.server.resourcemanager.federation;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;

public class TestFederationStateStoreServiceMetrics_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestFederationStateStoreServiceMetrics.class);

    private static FederationStateStoreServiceMetrics metrics = FederationStateStoreServiceMetrics.getMetrics();

    private MockBadFederationStateStoreService badStateStore = new MockBadFederationStateStoreService();

    private MockGoodFederationStateStoreService goodStateStore = new MockGoodFederationStateStoreService();

    private class MockBadFederationStateStoreService {

        public void registerSubCluster() {
            LOG.info("Mocked: failed registerSubCluster call");
            FederationStateStoreServiceMetrics.failedStateStoreServiceCall();
        }
    }

    private class MockGoodFederationStateStoreService {

        public void registerSubCluster(long duration) {
            LOG.info("Mocked: successful registerSubCluster call with duration {}", duration);
            FederationStateStoreServiceMetrics.succeededStateStoreServiceCall(duration);
        }
    }

    @Test
    public void testFederationStateStoreServiceMetricInit_1() {
        assertEquals(0, FederationStateStoreServiceMetrics.getNumSucceededCalls());
    }

    @Test
    public void testFederationStateStoreServiceMetricInit_2() {
        assertEquals(0, FederationStateStoreServiceMetrics.getNumFailedCalls());
    }
}
