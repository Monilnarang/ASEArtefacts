package org.jfree.chart.plot;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtils;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombinedRangeXYPlotTest_Purified implements ChartChangeListener {

    private final List<ChartChangeEvent> events = new ArrayList<>();

    @Override
    public void chartChanged(ChartChangeEvent event) {
        this.events.add(event);
    }

    private XYDataset<String> createDataset1() {
        XYSeries<String> series1 = new XYSeries<>("Series 1");
        series1.add(10.0, 12353.3);
        series1.add(20.0, 13734.4);
        series1.add(30.0, 14525.3);
        series1.add(40.0, 13984.3);
        series1.add(50.0, 12999.4);
        series1.add(60.0, 14274.3);
        series1.add(70.0, 15943.5);
        series1.add(80.0, 14845.3);
        series1.add(90.0, 14645.4);
        series1.add(100.0, 16234.6);
        series1.add(110.0, 17232.3);
        series1.add(120.0, 14232.2);
        series1.add(130.0, 13102.2);
        series1.add(140.0, 14230.2);
        series1.add(150.0, 11235.2);
        XYSeries<String> series2 = new XYSeries<>("Series 2");
        series2.add(10.0, 15000.3);
        series2.add(20.0, 11000.4);
        series2.add(30.0, 17000.3);
        series2.add(40.0, 15000.3);
        series2.add(50.0, 14000.4);
        series2.add(60.0, 12000.3);
        series2.add(70.0, 11000.5);
        series2.add(80.0, 12000.3);
        series2.add(90.0, 13000.4);
        series2.add(100.0, 12000.6);
        series2.add(110.0, 13000.3);
        series2.add(120.0, 17000.2);
        series2.add(130.0, 18000.2);
        series2.add(140.0, 16000.2);
        series2.add(150.0, 17000.2);
        XYSeriesCollection<String> collection = new XYSeriesCollection<>();
        collection.addSeries(series1);
        collection.addSeries(series2);
        return collection;
    }

    private XYDataset<String> createDataset2() {
        XYSeries<String> series2 = new XYSeries<>("Series 3");
        series2.add(10.0, 16853.2);
        series2.add(20.0, 19642.3);
        series2.add(30.0, 18253.5);
        series2.add(40.0, 15352.3);
        series2.add(50.0, 13532.0);
        series2.add(100.0, 12635.3);
        series2.add(110.0, 13998.2);
        series2.add(120.0, 11943.2);
        series2.add(130.0, 16943.9);
        series2.add(140.0, 17843.2);
        series2.add(150.0, 16495.3);
        series2.add(160.0, 17943.6);
        series2.add(170.0, 18500.7);
        series2.add(180.0, 19595.9);
        return new XYSeriesCollection<>(series2);
    }

    private CombinedRangeXYPlot<String> createPlot() {
        XYDataset<String> data1 = createDataset1();
        XYItemRenderer renderer1 = new StandardXYItemRenderer();
        NumberAxis xAxis1 = new NumberAxis("X1");
        XYPlot<String> subplot1 = new XYPlot<>(data1, xAxis1, null, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        XYTextAnnotation annotation = new XYTextAnnotation("Hello!", 50.0, 10000.0);
        annotation.setFont(new Font("SansSerif", Font.PLAIN, 9));
        annotation.setRotationAngle(Math.PI / 4.0);
        subplot1.addAnnotation(annotation);
        XYDataset<String> data2 = createDataset2();
        XYItemRenderer renderer2 = new StandardXYItemRenderer();
        NumberAxis xAxis2 = new NumberAxis("X2");
        xAxis2.setAutoRangeIncludesZero(false);
        XYPlot<String> subplot2 = new XYPlot<>(data2, xAxis2, null, renderer2);
        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        CombinedRangeXYPlot<String> plot = new CombinedRangeXYPlot<>(new NumberAxis("Range"));
        plot.setGap(10.0);
        plot.add(subplot1, 1);
        plot.add(subplot2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
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
