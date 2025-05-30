package org.apache.amoro.server.optimizing;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OptimizingStatusTest_Parameterized {

    @Test
    public void testOptimizingStatusCodeValue_2() {
        assertEquals(OptimizingStatus.FULL_OPTIMIZING, OptimizingStatus.ofCode(100));
    }

    @Test
    public void testOptimizingStatusCodeValue_3() {
        assertEquals(OptimizingStatus.MAJOR_OPTIMIZING, OptimizingStatus.ofCode(200));
    }

    @Test
    public void testOptimizingStatusCodeValue_4() {
        assertEquals(OptimizingStatus.MINOR_OPTIMIZING, OptimizingStatus.ofCode(300));
    }

    @Test
    public void testOptimizingStatusCodeValue_5() {
        assertEquals(OptimizingStatus.COMMITTING, OptimizingStatus.ofCode(400));
    }

    @Test
    public void testOptimizingStatusCodeValue_6() {
        assertEquals(OptimizingStatus.PLANNING, OptimizingStatus.ofCode(500));
    }

    @Test
    public void testOptimizingStatusCodeValue_7() {
        assertEquals(OptimizingStatus.PENDING, OptimizingStatus.ofCode(600));
    }

    @Test
    public void testOptimizingStatusCodeValue_8() {
        assertEquals(OptimizingStatus.IDLE, OptimizingStatus.ofCode(700));
    }

    @Test
    public void testOptimizingStatusDisplayValue_2() {
        assertEquals(OptimizingStatus.FULL_OPTIMIZING, OptimizingStatus.ofDisplayValue("full"));
    }

    @Test
    public void testOptimizingStatusDisplayValue_3() {
        assertEquals(OptimizingStatus.MAJOR_OPTIMIZING, OptimizingStatus.ofDisplayValue("major"));
    }

    @Test
    public void testOptimizingStatusDisplayValue_4() {
        assertEquals(OptimizingStatus.MINOR_OPTIMIZING, OptimizingStatus.ofDisplayValue("minor"));
    }

    @Test
    public void testOptimizingStatusDisplayValue_5() {
        assertEquals(OptimizingStatus.COMMITTING, OptimizingStatus.ofDisplayValue("committing"));
    }

    @Test
    public void testOptimizingStatusDisplayValue_6() {
        assertEquals(OptimizingStatus.PLANNING, OptimizingStatus.ofDisplayValue("planning"));
    }

    @Test
    public void testOptimizingStatusDisplayValue_7() {
        assertEquals(OptimizingStatus.PENDING, OptimizingStatus.ofDisplayValue("pending"));
    }

    @Test
    public void testOptimizingStatusDisplayValue_8() {
        assertEquals(OptimizingStatus.IDLE, OptimizingStatus.ofDisplayValue("idle"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOptimizingStatusCodeValue_1_1")
    public void testOptimizingStatusCodeValue_1_1(int param1) {
        assertEquals(param1, OptimizingStatus.values().length);
    }

    static public Stream<Arguments> Provider_testOptimizingStatusCodeValue_1_1() {
        return Stream.of(arguments(7), arguments(7));
    }
}
