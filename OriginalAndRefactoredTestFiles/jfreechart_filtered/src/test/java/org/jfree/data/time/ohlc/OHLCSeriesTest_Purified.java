package org.jfree.data.time.ohlc;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Year;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OHLCSeriesTest_Purified implements SeriesChangeListener {

    SeriesChangeEvent lastEvent;

    @Override
    public void seriesChanged(SeriesChangeEvent event) {
        this.lastEvent = event;
    }

    @Test
    public void testClear_1() {
        assertNull(this.lastEvent);
    }

    @Test
    public void testClear_2_testMerged_2() {
        OHLCSeries<String> s1 = new OHLCSeries<>("S1");
        s1.addChangeListener(this);
        s1.clear();
        assertTrue(s1.isEmpty());
        s1.add(new Year(2006), 1.0, 1.1, 1.1, 1.1);
        assertFalse(s1.isEmpty());
    }

    @Test
    public void testClear_4() {
        assertNotNull(this.lastEvent);
    }
}
