package org.apache.commons.io.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IOSpliteratorTest_Purified {

    private IOSpliterator<Path> spliterator;

    @BeforeEach
    public void beforeEach() {
        spliterator = IOSpliterator.adapt(newPathList().spliterator());
    }

    private List<Path> newPathList() {
        return Arrays.asList(TestConstants.ABS_PATH_A, TestConstants.ABS_PATH_B);
    }

    @Test
    public void testAsSpliterator_1() {
        assertEquals(2, spliterator.estimateSize());
    }

    @Test
    public void testAsSpliterator_2() {
        assertEquals(2, spliterator.asSpliterator().estimateSize());
    }

    @Test
    public void testCharacteristics_1() {
        assertEquals(spliterator.unwrap().characteristics(), spliterator.characteristics());
    }

    @Test
    public void testCharacteristics_2() {
        assertEquals(spliterator.unwrap().characteristics(), spliterator.asSpliterator().characteristics());
    }

    @Test
    public void testEstimateSize_1() {
        assertEquals(2, spliterator.estimateSize());
    }

    @Test
    public void testEstimateSize_2() {
        assertEquals(spliterator.unwrap().estimateSize(), spliterator.estimateSize());
    }

    @Test
    public void testEstimateSize_3() {
        assertEquals(spliterator.unwrap().estimateSize(), spliterator.asSpliterator().estimateSize());
    }

    @Test
    public void testGetExactSizeIfKnown_1() {
        assertEquals(2, spliterator.getExactSizeIfKnown());
    }

    @Test
    public void testGetExactSizeIfKnown_2() {
        assertEquals(spliterator.unwrap().getExactSizeIfKnown(), spliterator.getExactSizeIfKnown());
    }

    @Test
    public void testGetExactSizeIfKnown_3() {
        assertEquals(spliterator.unwrap().getExactSizeIfKnown(), spliterator.asSpliterator().getExactSizeIfKnown());
    }

    @Test
    public void testHasCharacteristics_1() {
        assertTrue(spliterator.hasCharacteristics(spliterator.characteristics()));
    }

    @Test
    public void testHasCharacteristics_2() {
        assertEquals(spliterator.unwrap().hasCharacteristics(spliterator.unwrap().characteristics()), spliterator.hasCharacteristics(spliterator.characteristics()));
    }

    @Test
    public void testHasCharacteristics_3() {
        assertEquals(spliterator.unwrap().hasCharacteristics(spliterator.unwrap().characteristics()), spliterator.asSpliterator().hasCharacteristics(spliterator.asSpliterator().characteristics()));
    }
}
