package org.jfree.data.time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.jfree.chart.TestUtils;
import org.jfree.chart.date.MonthConstants;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.Range;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.general.SeriesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimeSeriesTest_Purified implements SeriesChangeListener {

    private TimeSeries<String> seriesA;

    private boolean gotSeriesChangeEvent = false;

    @BeforeEach
    public void setUp() {
        this.seriesA = new TimeSeries<>("Series A");
        this.seriesA.add(new Year(2000), 102000);
        this.seriesA.add(new Year(2001), 102001);
        this.seriesA.add(new Year(2002), 102002);
        this.seriesA.add(new Year(2003), 102003);
        this.seriesA.add(new Year(2004), 102004);
        this.seriesA.add(new Year(2005), 102005);
    }

    @Override
    public void seriesChanged(SeriesChangeEvent event) {
        this.gotSeriesChangeEvent = true;
    }

    private static final double EPSILON = 0.0000000001;

    @Test
    public void testGetValue_1() {
        assertNull(seriesA.getValue(new Year(1999)));
    }

    @Test
    public void testGetValue_2() {
        assertEquals(102000.0, seriesA.getValue(new Year(2000)));
    }

    @Test
    public void testRemoveAgedItems_1_testMerged_1() {
        TimeSeries<String> series = new TimeSeries<>("Test Series");
        series.addChangeListener(this);
        assertEquals(Long.MAX_VALUE, series.getMaximumItemAge());
        assertEquals(Integer.MAX_VALUE, series.getMaximumItemCount());
        series.removeAgedItems(true);
        assertEquals(0, series.getItemCount());
        series.add(new Year(1999), 1.0);
        series.setMaximumItemAge(0);
        assertEquals(1, series.getItemCount());
        series.setMaximumItemAge(10);
        series.add(new Year(2001), 2.0);
        series.setMaximumItemAge(2);
        assertEquals(2, series.getItemCount());
        assertEquals(0, series.getIndex(new Year(1999)));
        series.setMaximumItemAge(1);
        assertEquals(0, series.getIndex(new Year(2001)));
    }

    @Test
    public void testRemoveAgedItems_4() {
        assertFalse(this.gotSeriesChangeEvent);
    }

    @Test
    public void testRemoveAgedItems_6() {
        assertFalse(this.gotSeriesChangeEvent);
    }

    @Test
    public void testRemoveAgedItems_9() {
        assertFalse(this.gotSeriesChangeEvent);
    }

    @Test
    public void testRemoveAgedItems_12() {
        assertTrue(this.gotSeriesChangeEvent);
    }

    @Test
    public void testRemoveAgedItems2_1_testMerged_1() {
        long y2006 = 1157087372534L;
        TimeSeries<String> series = new TimeSeries<>("Test Series");
        series.addChangeListener(this);
        assertEquals(Long.MAX_VALUE, series.getMaximumItemAge());
        assertEquals(Integer.MAX_VALUE, series.getMaximumItemCount());
        series.removeAgedItems(y2006, true);
        assertEquals(0, series.getItemCount());
        series.add(new Year(2004), 1.0);
        series.setMaximumItemAge(1);
        series.removeAgedItems(new Year(2005).getMiddleMillisecond(), true);
        assertEquals(1, series.getItemCount());
        series.setMaximumItemAge(2);
        series.add(new Year(2003), 1.0);
        series.add(new Year(2005), 2.0);
        assertEquals(2, series.getItemCount());
    }

    @Test
    public void testRemoveAgedItems2_4() {
        assertFalse(this.gotSeriesChangeEvent);
    }

    @Test
    public void testRemoveAgedItems2_6() {
        assertFalse(this.gotSeriesChangeEvent);
    }

    @Test
    public void testRemoveAgedItems2_8() {
        assertTrue(this.gotSeriesChangeEvent);
    }

    @Test
    public void testRemoveAgedItems2_12() {
        assertFalse(this.gotSeriesChangeEvent);
    }

    @Test
    public void testRemoveAgedItems2_14() {
        assertTrue(this.gotSeriesChangeEvent);
    }
}
