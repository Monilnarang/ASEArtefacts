package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TableXYDatasetTest_Purified {

    private XYSeries<String> createSeriesA() {
        XYSeries<String> s = new XYSeries<>("A", true, false);
        s.add(1.0, 1.1);
        s.add(2.0, null);
        return s;
    }

    private XYSeries<String> createSeriesB() {
        XYSeries<String> s = new XYSeries<>("B", true, false);
        s.add(1.0, null);
        s.add(2.0, 2.2);
        return s;
    }

    private XYSeries<String> createSeries1() {
        XYSeries<String> series1 = new XYSeries<>("Series 1", true, false);
        series1.add(1.0, 1.0);
        series1.add(2.0, 1.0);
        series1.add(4.0, 1.0);
        series1.add(5.0, 1.0);
        return series1;
    }

    private XYSeries<String> createSeries2() {
        XYSeries<String> series2 = new XYSeries<>("Series 2", true, false);
        series2.add(2.0, 2.0);
        series2.add(3.0, 2.0);
        series2.add(4.0, 2.0);
        series2.add(5.0, 2.0);
        series2.add(6.0, 2.0);
        return series2;
    }

    @Test
    public void testAutoPrune_1_testMerged_1() {
        DefaultTableXYDataset<String> dataset = new DefaultTableXYDataset<>(true);
        dataset.addSeries(createSeriesA());
        assertEquals(2, dataset.getItemCount());
        dataset.addSeries(createSeriesB());
        dataset.removeSeries(1);
        assertEquals(1, dataset.getItemCount());
    }

    @Test
    public void testAutoPrune_4_testMerged_2() {
        DefaultTableXYDataset<String> dataset2 = new DefaultTableXYDataset<>(true);
        dataset2.addSeries(createSeriesA());
        assertEquals(2, dataset2.getItemCount());
        dataset2.addSeries(createSeriesB());
        dataset2.removeSeries(1);
        assertEquals(1, dataset2.getItemCount());
    }
}
