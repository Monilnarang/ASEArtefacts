package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class YIntervalSeriesTest_Purified implements SeriesChangeListener {

    SeriesChangeEvent lastEvent;

    @Override
    public void seriesChanged(SeriesChangeEvent event) {
        this.lastEvent = event;
    }

    private static final double EPSILON = 0.0000000001;

    @Test
    public void testClear_1() {
        assertNull(this.lastEvent);
    }

    @Test
    public void testClear_2_testMerged_2() {
        YIntervalSeries<String> s1 = new YIntervalSeries<>("S1");
        s1.addChangeListener(this);
        s1.clear();
        assertTrue(s1.isEmpty());
        s1.add(1.0, 2.0, 3.0, 4.0);
        assertFalse(s1.isEmpty());
    }

    @Test
    public void testClear_4() {
        assertNotNull(this.lastEvent);
    }
}
