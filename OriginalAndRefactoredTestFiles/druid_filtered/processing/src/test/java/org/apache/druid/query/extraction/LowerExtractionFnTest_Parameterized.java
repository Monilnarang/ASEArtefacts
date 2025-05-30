package org.apache.druid.query.extraction;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LowerExtractionFnTest_Parameterized {

    ExtractionFn extractionFn = new LowerExtractionFn(null);

    @Test
    public void testApply_3() {
        Assert.assertNull(extractionFn.apply(null));
    }

    @Test
    public void testApply_4() {
        Assert.assertNull(extractionFn.apply((Object) null));
    }

    @Test
    public void testGetCacheKey_1() {
        Assert.assertArrayEquals(extractionFn.getCacheKey(), extractionFn.getCacheKey());
    }

    @Test
    public void testGetCacheKey_2() {
        Assert.assertFalse(Arrays.equals(extractionFn.getCacheKey(), new UpperExtractionFn(null).getCacheKey()));
    }

    @ParameterizedTest
    @MethodSource("Provider_testApply_1to2_5")
    public void testApply_1to2_5(String param1, String param2) {
        Assert.assertEquals(param1, extractionFn.apply(param2));
    }

    static public Stream<Arguments> Provider_testApply_1to2_5() {
        return Stream.of(arguments("lower 1 string", "lOwER 1 String"), arguments("", ""), arguments(1, 1));
    }
}
