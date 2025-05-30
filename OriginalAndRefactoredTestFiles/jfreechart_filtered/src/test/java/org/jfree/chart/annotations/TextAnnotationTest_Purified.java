package org.jfree.chart.annotations;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.event.AnnotationChangeListener;
import org.jfree.chart.text.TextAnchor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TextAnnotationTest_Purified implements AnnotationChangeListener {

    private AnnotationChangeEvent lastEvent;

    @Override
    public void annotationChanged(AnnotationChangeEvent event) {
        this.lastEvent = event;
    }

    @Test
    public void testChangeEvents_1() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testChangeEvents_2() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testChangeEvents_3() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testChangeEvents_4() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testChangeEvents_5() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testChangeEvents_6() {
        assertNotNull(this.lastEvent);
    }

    @Test
    public void testChangeEvents_7() {
        assertNotNull(this.lastEvent);
    }
}
