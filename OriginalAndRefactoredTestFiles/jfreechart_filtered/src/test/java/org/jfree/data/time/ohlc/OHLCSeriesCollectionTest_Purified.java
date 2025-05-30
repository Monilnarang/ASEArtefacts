package org.jfree.data.time.ohlc;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.Year;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OHLCSeriesCollectionTest_Purified implements DatasetChangeListener {

    private DatasetChangeEvent lastEvent;

    @Override
    public void datasetChanged(DatasetChangeEvent event) {
        this.lastEvent = event;
    }

    @Test
    public void testRemoveAllSeries_1() {
        assertNull(this.lastEvent);
    }

    @Test
    public void testRemoveAllSeries_2() {
        OHLCSeriesCollection c1 = new OHLCSeriesCollection();
        c1.addChangeListener(this);
        c1.removeAllSeries();
        OHLCSeries<String> s1 = new OHLCSeries<>("Series 1");
        OHLCSeries<String> s2 = new OHLCSeries<>("Series 2");
        c1.addSeries(s1);
        c1.addSeries(s2);
        c1.removeAllSeries();
        assertEquals(0, c1.getSeriesCount());
    }

    @Test
    public void testRemoveAllSeries_3() {
        assertNotNull(this.lastEvent);
    }
}
