package org.apache.hadoop.yarn.server.federation.store.metrics;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFederationStateStoreClientMetrics_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestFederationStateStoreClientMetrics.class);

    private MockBadFederationStateStore badStateStore = new MockBadFederationStateStore();

    private MockGoodFederationStateStore goodStateStore = new MockGoodFederationStateStore();

    private class MockBadFederationStateStore {

        public void registerSubCluster() {
            LOG.info("Mocked: failed registerSubCluster call");
            FederationStateStoreClientMetrics.failedStateStoreCall();
        }
    }

    private class MockGoodFederationStateStore {

        public void registerSubCluster(long duration) {
            LOG.info("Mocked: successful registerSubCluster call with duration {}", duration);
            FederationStateStoreClientMetrics.succeededStateStoreCall(duration);
        }
    }

    @Test
    public void testAggregateMetricInit_1() {
        Assert.assertEquals(0, FederationStateStoreClientMetrics.getNumSucceededCalls());
    }

    @Test
    public void testAggregateMetricInit_2() {
        Assert.assertEquals(0, FederationStateStoreClientMetrics.getNumFailedCalls());
    }
}
