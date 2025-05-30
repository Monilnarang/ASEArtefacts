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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ConservativeAdvancementTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_setTolerance_1to2")
    public void setTolerance_1to2(double param1) {
        TestCase.assertEquals(param1, this.detector.getDistanceEpsilon());
    }

    static public Stream<Arguments> Provider_setTolerance_1to2() {
        return Stream.of(arguments(0.3), arguments(0.000002));
    }

    @ParameterizedTest
    @MethodSource("Provider_setMaxIterations_1to2")
    public void setMaxIterations_1to2(int param1) {
        TestCase.assertEquals(param1, this.detector.getMaxIterations());
    }

    static public Stream<Arguments> Provider_setMaxIterations_1to2() {
        return Stream.of(arguments(23), arguments(10));
    }
}
