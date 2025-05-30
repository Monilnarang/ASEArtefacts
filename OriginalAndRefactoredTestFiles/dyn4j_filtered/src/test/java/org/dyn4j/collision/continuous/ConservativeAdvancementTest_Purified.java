package org.dyn4j.collision.continuous;

import org.dyn4j.collision.narrowphase.DistanceDetector;
import org.dyn4j.collision.narrowphase.Gjk;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class ConservativeAdvancementTest_Purified {

    private static final Transform IDENTITY = new Transform();

    private static final double TIME_STEP = 1.0 / 60.0;

    private ConservativeAdvancement detector;

    private Convex c1;

    private Convex c2;

    @Before
    public void setup() {
        this.detector = new ConservativeAdvancement();
        this.c1 = Geometry.createUnitCirclePolygon(5, 0.1);
        this.c2 = Geometry.createRectangle(20.0, 0.5);
    }

    @Test
    public void setTolerance_1() {
        TestCase.assertEquals(0.3, this.detector.getDistanceEpsilon());
    }

    @Test
    public void setTolerance_2() {
        TestCase.assertEquals(0.000002, this.detector.getDistanceEpsilon());
    }

    @Test
    public void setMaxIterations_1() {
        TestCase.assertEquals(23, this.detector.getMaxIterations());
    }

    @Test
    public void setMaxIterations_2() {
        TestCase.assertEquals(10, this.detector.getMaxIterations());
    }
}
