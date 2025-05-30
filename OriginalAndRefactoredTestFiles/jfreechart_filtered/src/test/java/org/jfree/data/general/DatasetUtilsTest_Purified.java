package org.jfree.data.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerXYDataset;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.statistics.MultiValueCategoryDataset;
import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.IntervalXYZDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DatasetUtilsTest_Purified {

    private static final double EPSILON = 0.0000000001;

    private CategoryDataset<String, String> createCategoryDataset1() {
        DefaultCategoryDataset<String, String> result = new DefaultCategoryDataset<>();
        result.addValue(1.0, "R0", "C0");
        result.addValue(1.0, "R1", "C0");
        result.addValue(1.0, "R2", "C0");
        result.addValue(4.0, "R0", "C1");
        result.addValue(5.0, "R1", "C1");
        result.addValue(6.0, "R2", "C1");
        return result;
    }

    private CategoryDataset<String, String> createCategoryDataset2() {
        DefaultCategoryDataset<String, String> result = new DefaultCategoryDataset<>();
        result.addValue(1.0, "R0", "C0");
        result.addValue(-2.0, "R1", "C0");
        result.addValue(2.0, "R0", "C1");
        result.addValue(-1.0, "R1", "C1");
        return result;
    }

    private XYDataset<String> createXYDataset1() {
        XYSeries<String> series1 = new XYSeries<>("S1");
        series1.add(1.0, 100.0);
        series1.add(2.0, 101.0);
        series1.add(3.0, 102.0);
        XYSeries<String> series2 = new XYSeries<>("S2");
        series2.add(1.0, 103.0);
        series2.add(2.0, null);
        series2.add(3.0, 105.0);
        XYSeriesCollection<String> result = new XYSeriesCollection<>();
        result.addSeries(series1);
        result.addSeries(series2);
        result.setIntervalWidth(0.0);
        return result;
    }

    private TableXYDataset<String> createTableXYDataset1() {
        DefaultTableXYDataset<String> dataset = new DefaultTableXYDataset<>();
        XYSeries<String> s1 = new XYSeries<>("Series 1", true, false);
        s1.add(1.0, 1.0);
        s1.add(2.0, 2.0);
        dataset.addSeries(s1);
        XYSeries<String> s2 = new XYSeries<>("Series 2", true, false);
        s2.add(1.0, -2.0);
        s2.add(2.0, -1.0);
        dataset.addSeries(s2);
        return dataset;
    }

    @Test
    public void testJava_1() {
        assertTrue(Double.isNaN(Math.min(1.0, Double.NaN)));
    }

    @Test
    public void testJava_2() {
        assertTrue(Double.isNaN(Math.max(1.0, Double.NaN)));
    }
}
