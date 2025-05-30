package org.jfree.data.time;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.DatasetChangeConfirmation;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtils;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TimeSeriesCollectionTest_Purified {

    private TimeSeries<String> createSeries() {
        RegularTimePeriod t = new Day();
        TimeSeries<String> series = new TimeSeries<>("Test");
        series.add(t, 1.0);
        t = t.next();
        series.add(t, 2.0);
        t = t.next();
        series.add(t, null);
        t = t.next();
        series.add(t, 4.0);
        return series;
    }

    private static final double EPSILON = 0.0000000001;

    @Test
    public void testSerialization_1_testMerged_1() {
        var s1 = createSeries();
        TimeSeriesCollection<String> c1 = new TimeSeriesCollection<>(s1);
        TimeSeriesCollection<String> c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
        assertNotEquals(c1, c2);
    }

    @Test
    public void testSerialization_4() {
        DatasetChangeConfirmation listener = new DatasetChangeConfirmation();
        c2.addChangeListener(listener);
        assertNotNull(listener.event);
    }
}
