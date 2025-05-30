package org.jfree.chart.axis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.TestUtils;
import org.jfree.chart.api.RectangleEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogarithmicAxisTest_Purified {

    static class MyLogarithmicAxis extends LogarithmicAxis {

        public MyLogarithmicAxis(String label) {
            super(label);
        }

        @Override
        protected double switchedLog10(double val) {
            return super.switchedLog10(val);
        }
    }

    public static double EPSILON = 0.000001;

    MyLogarithmicAxis axis = null;

    @BeforeEach
    public void setUp() throws Exception {
        this.axis = new MyLogarithmicAxis("Value (log)");
        this.axis.setAllowNegativesFlag(false);
        this.axis.setLog10TickLabelsFlag(false);
        this.axis.setLowerMargin(0.0);
        this.axis.setUpperMargin(0.0);
        this.axis.setLowerBound(0.2);
        this.axis.setUpperBound(100.0);
    }

    private void checkLogPowRoundTrip(double value) {
        assertEquals(value, this.axis.adjustedLog10(this.axis.adjustedPow10(value)), EPSILON, "log(pow(x)) = x");
        assertEquals(value, this.axis.adjustedPow10(this.axis.adjustedLog10(value)), EPSILON, "pow(log(x)) = x");
    }

    private void checkSwitchedLogPowRoundTrip(double value) {
        assertEquals(value, this.axis.switchedLog10(this.axis.switchedPow10(value)), EPSILON, "log(pow(x)) = x");
        assertEquals(value, this.axis.switchedPow10(this.axis.switchedLog10(value)), EPSILON, "pow(log(x)) = x");
    }

    private void checkPointsToJava2D(RectangleEdge edge, Rectangle2D plotArea) {
        assertEquals(plotArea.getX(), this.axis.valueToJava2D(this.axis.getLowerBound(), plotArea, edge), EPSILON, "Left most point on the axis should be beginning of range.");
        assertEquals(plotArea.getX() + plotArea.getWidth(), this.axis.valueToJava2D(this.axis.getUpperBound(), plotArea, edge), EPSILON, "Right most point on the axis should be end of range.");
        assertEquals(plotArea.getX() + (plotArea.getWidth() / 2), this.axis.valueToJava2D(Math.sqrt(this.axis.getLowerBound() * this.axis.getUpperBound()), plotArea, edge), EPSILON, "Center point on the axis should geometric mean of the bounds.");
    }

    private void checkPointsToValue(RectangleEdge edge, Rectangle2D plotArea) {
        assertEquals(this.axis.getUpperBound(), this.axis.java2DToValue(plotArea.getX() + plotArea.getWidth(), plotArea, edge), EPSILON, "Right most point on the axis should be end of range.");
        assertEquals(this.axis.getLowerBound(), this.axis.java2DToValue(plotArea.getX(), plotArea, edge), EPSILON, "Left most point on the axis should be beginning of range.");
        assertEquals(Math.sqrt(this.axis.getUpperBound() * this.axis.getLowerBound()), this.axis.java2DToValue(plotArea.getX() + (plotArea.getWidth() / 2), plotArea, edge), EPSILON, "Center point on the axis should geometric mean of the bounds.");
    }

    @Test
    public void testSwitchedLog10_1() {
        assertFalse(this.axis.getAllowNegativesFlag(), "Axis should not allow negative values");
    }

    @Test
    public void testSwitchedLog10_2() {
        assertEquals(Math.log(0.5) / LogarithmicAxis.LOG10_VALUE, this.axis.switchedLog10(0.5), EPSILON);
    }
}
