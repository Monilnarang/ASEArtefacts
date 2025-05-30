package org.apache.druid.query.extraction;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public class LowerExtractionFnTest_Purified {

    ExtractionFn extractionFn = new LowerExtractionFn(null);

    @Test
    public void testApply_1() {
        Assert.assertEquals("lower 1 string", extractionFn.apply("lOwER 1 String"));
    }

    @Test
    public void testApply_2() {
        Assert.assertEquals("", extractionFn.apply(""));
    }

    @Test
    public void testApply_3() {
        Assert.assertNull(extractionFn.apply(null));
    }

    @Test
    public void testApply_4() {
        Assert.assertNull(extractionFn.apply((Object) null));
    }

    @Test
    public void testApply_5() {
        Assert.assertEquals("1", extractionFn.apply(1));
    }

    @Test
    public void testGetCacheKey_1() {
        Assert.assertArrayEquals(extractionFn.getCacheKey(), extractionFn.getCacheKey());
    }

    @Test
    public void testGetCacheKey_2() {
        Assert.assertFalse(Arrays.equals(extractionFn.getCacheKey(), new UpperExtractionFn(null).getCacheKey()));
    }
}
