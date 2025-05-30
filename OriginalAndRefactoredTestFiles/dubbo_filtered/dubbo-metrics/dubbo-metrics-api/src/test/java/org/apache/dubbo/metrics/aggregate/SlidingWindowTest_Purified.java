package org.apache.dubbo.metrics.aggregate;

import java.util.concurrent.atomic.LongAdder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SlidingWindowTest_Purified {

    static final int paneCount = 10;

    static final long intervalInMs = 2000;

    TestSlidingWindow window;

    @BeforeEach
    void setup() {
        window = new TestSlidingWindow(paneCount, intervalInMs);
    }

    private static class TestSlidingWindow extends SlidingWindow<LongAdder> {

        public TestSlidingWindow(int paneCount, long intervalInMs) {
            super(paneCount, intervalInMs);
        }

        @Override
        public LongAdder newEmptyValue(long timeMillis) {
            return new LongAdder();
        }

        @Override
        protected Pane<LongAdder> resetPaneTo(Pane<LongAdder> pane, long startInMs) {
            pane.setStartInMs(startInMs);
            pane.getValue().reset();
            return pane;
        }
    }

    @Test
    void testGetPaneData_1() {
        assertNull(window.getPaneValue(-1L));
    }

    @Test
    void testGetPaneData_2_testMerged_2() {
        window.currentPane();
        assertNotNull(window.getPaneValue(System.currentTimeMillis()));
        assertNull(window.getPaneValue(System.currentTimeMillis() + window.getPaneIntervalInMs()));
    }
}
