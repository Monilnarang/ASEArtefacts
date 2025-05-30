package org.apache.hadoop.yarn.server.timelineservice.collector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.hadoop.yarn.server.timelineservice.metrics.PerNodeAggTimelineCollectorMetrics;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestPerNodeAggTimelineCollectorMetrics_Purified {

    private PerNodeAggTimelineCollectorMetrics metrics;

    @BeforeEach
    public void setup() {
        metrics = PerNodeAggTimelineCollectorMetrics.getInstance();
    }

    @AfterEach
    public void tearDown() {
        PerNodeAggTimelineCollectorMetrics.destroy();
    }

    @Test
    void testTimelineCollectorMetrics_1() {
        assertNotNull(metrics);
    }

    @Test
    void testTimelineCollectorMetrics_2() {
        assertEquals(10, metrics.getPutEntitiesSuccessLatency().getInterval());
    }

    @Test
    void testTimelineCollectorMetrics_3() {
        assertEquals(10, metrics.getPutEntitiesFailureLatency().getInterval());
    }

    @Test
    void testTimelineCollectorMetrics_4() {
        assertEquals(10, metrics.getAsyncPutEntitiesSuccessLatency().getInterval());
    }

    @Test
    void testTimelineCollectorMetrics_5() {
        assertEquals(10, metrics.getAsyncPutEntitiesFailureLatency().getInterval());
    }
}
