package org.jfree.chart.plot.dial;

import java.awt.Color;
import java.awt.GradientPaint;
import org.jfree.chart.TestUtils;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DialPlotTest_Purified implements PlotChangeListener {

    private PlotChangeEvent lastEvent;

    @Override
    public void plotChanged(PlotChangeEvent event) {
        this.lastEvent = event;
    }

    @Test
    public void testBackgroundListener_1() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testBackgroundListener_2() {
        assertNull(this.lastEvent);
    }

    @Test
    public void testBackgroundListener_3() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testCapListener_1() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testCapListener_2() {
        assertNull(this.lastEvent);
    }

    @Test
    public void testCapListener_3() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testFrameListener_1() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testFrameListener_2() {
        assertNull(this.lastEvent);
    }

    @Test
    public void testFrameListener_3() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testScaleListener_1() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testScaleListener_2() {
        assertNull(this.lastEvent);
    }

    @Test
    public void testScaleListener_3() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testLayerListener_1() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testLayerListener_2() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testLayerListener_3() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testLayerListener_4() {
        assertNull(this.lastEvent);
    }
}
