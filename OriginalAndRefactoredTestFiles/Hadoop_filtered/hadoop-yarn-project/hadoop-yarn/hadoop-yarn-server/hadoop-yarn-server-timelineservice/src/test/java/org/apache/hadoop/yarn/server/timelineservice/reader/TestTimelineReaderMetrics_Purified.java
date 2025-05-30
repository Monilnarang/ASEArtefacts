package org.apache.hadoop.yarn.server.timelineservice.reader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.hadoop.yarn.server.timelineservice.metrics.TimelineReaderMetrics;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTimelineReaderMetrics_Purified {

    private TimelineReaderMetrics metrics;

    @BeforeEach
    public void setup() {
        metrics = TimelineReaderMetrics.getInstance();
    }

    @AfterEach
    public void tearDown() {
        TimelineReaderMetrics.destroy();
    }

    @Test
    void testTimelineReaderMetrics_1() {
        assertNotNull(metrics);
    }

    @Test
    void testTimelineReaderMetrics_2() {
        assertEquals(10, metrics.getGetEntitiesSuccessLatency().getInterval());
    }

    @Test
    void testTimelineReaderMetrics_3() {
        assertEquals(10, metrics.getGetEntitiesFailureLatency().getInterval());
    }

    @Test
    void testTimelineReaderMetrics_4() {
        assertEquals(10, metrics.getGetEntityTypesSuccessLatency().getInterval());
    }

    @Test
    void testTimelineReaderMetrics_5() {
        assertEquals(10, metrics.getGetEntityTypesFailureLatency().getInterval());
    }
}
