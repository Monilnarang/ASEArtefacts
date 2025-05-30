package org.jfree.chart.plot;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombinedDomainCategoryPlotTest_Purified implements ChartChangeListener {

    private final List<ChartChangeEvent> events = new ArrayList<>();

    @Override
    public void chartChanged(ChartChangeEvent event) {
        this.events.add(event);
    }

    public CategoryDataset<String, String> createDataset1() {
        DefaultCategoryDataset<String, String> result = new DefaultCategoryDataset<>();
        String series1 = "First";
        String series2 = "Second";
        String type1 = "Type 1";
        String type2 = "Type 2";
        String type3 = "Type 3";
        String type4 = "Type 4";
        String type5 = "Type 5";
        String type6 = "Type 6";
        String type7 = "Type 7";
        String type8 = "Type 8";
        result.addValue(1.0, series1, type1);
        result.addValue(4.0, series1, type2);
        result.addValue(3.0, series1, type3);
        result.addValue(5.0, series1, type4);
        result.addValue(5.0, series1, type5);
        result.addValue(7.0, series1, type6);
        result.addValue(7.0, series1, type7);
        result.addValue(8.0, series1, type8);
        result.addValue(5.0, series2, type1);
        result.addValue(7.0, series2, type2);
        result.addValue(6.0, series2, type3);
        result.addValue(8.0, series2, type4);
        result.addValue(4.0, series2, type5);
        result.addValue(4.0, series2, type6);
        result.addValue(2.0, series2, type7);
        result.addValue(1.0, series2, type8);
        return result;
    }

    public CategoryDataset<String, String> createDataset2() {
        DefaultCategoryDataset<String, String> result = new DefaultCategoryDataset<>();
        String series1 = "Third";
        String series2 = "Fourth";
        String type1 = "Type 1";
        String type2 = "Type 2";
        String type3 = "Type 3";
        String type4 = "Type 4";
        String type5 = "Type 5";
        String type6 = "Type 6";
        String type7 = "Type 7";
        String type8 = "Type 8";
        result.addValue(11.0, series1, type1);
        result.addValue(14.0, series1, type2);
        result.addValue(13.0, series1, type3);
        result.addValue(15.0, series1, type4);
        result.addValue(15.0, series1, type5);
        result.addValue(17.0, series1, type6);
        result.addValue(17.0, series1, type7);
        result.addValue(18.0, series1, type8);
        result.addValue(15.0, series2, type1);
        result.addValue(17.0, series2, type2);
        result.addValue(16.0, series2, type3);
        result.addValue(18.0, series2, type4);
        result.addValue(14.0, series2, type5);
        result.addValue(14.0, series2, type6);
        result.addValue(12.0, series2, type7);
        result.addValue(11.0, series2, type8);
        return result;
    }

    private CombinedDomainCategoryPlot createPlot() {
        CategoryDataset<String, String> dataset1 = createDataset1();
        NumberAxis rangeAxis1 = new NumberAxis("Value");
        rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
        renderer1.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());
        CategoryPlot<String, String> subplot1 = new CategoryPlot<>(dataset1, null, rangeAxis1, renderer1);
        subplot1.setDomainGridlinesVisible(true);
        CategoryDataset<String, String> dataset2 = createDataset2();
        NumberAxis rangeAxis2 = new NumberAxis("Value");
        rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer2 = new BarRenderer();
        renderer2.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());
        CategoryPlot<String, String> subplot2 = new CategoryPlot<>(dataset2, null, rangeAxis2, renderer2);
        subplot2.setDomainGridlinesVisible(true);
        CategoryAxis domainAxis = new CategoryAxis("Category");
        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
        plot.add(subplot1, 2);
        plot.add(subplot2, 1);
        return plot;
    }

    @Test
    public void testNotification_1() {
        assertEquals(1, this.events.size());
    }

    @Test
    public void testNotification_2() {
        assertTrue(this.events.isEmpty());
    }
}
