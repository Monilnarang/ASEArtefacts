package org.jfree.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.util.List;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.pie.PiePlot;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.legend.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.chart.api.RectangleAlignment;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class JFreeChartTest_Purified implements ChartChangeListener {

    private JFreeChart pieChart;

    @BeforeEach
    public void setUp() {
        DefaultPieDataset<String> data = new DefaultPieDataset<>();
        data.setValue("Java", 43.2);
        data.setValue("Visual Basic", 0.0);
        data.setValue("C/C++", 17.5);
        this.pieChart = ChartFactory.createPieChart("Pie Chart", data);
    }

    private ChartChangeEvent lastChartChangeEvent;

    @Override
    public void chartChanged(ChartChangeEvent event) {
        this.lastChartChangeEvent = event;
    }

    @Test
    public void testTitleChangeEvent_1() {
        assertNotNull(this.lastChartChangeEvent);
    }

    @Test
    public void testTitleChangeEvent_2() {
        assertNotNull(this.lastChartChangeEvent);
    }

    @Test
    public void testTitleChangeEvent_3() {
        assertNotNull(this.lastChartChangeEvent);
    }

    @Test
    public void testTitleChangeEvent_4() {
        assertNull(this.lastChartChangeEvent);
    }
}
