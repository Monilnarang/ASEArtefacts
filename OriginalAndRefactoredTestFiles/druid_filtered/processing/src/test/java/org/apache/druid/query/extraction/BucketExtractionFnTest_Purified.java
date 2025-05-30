package org.apache.druid.query.extraction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class BucketExtractionFnTest_Purified {

    private static final double DELTA = 0.0000001;

    @Test
    public void testApply_1_testMerged_1() {
        BucketExtractionFn extractionFn1 = new BucketExtractionFn(100.0, 0.5);
        Assert.assertEquals("1200.5", extractionFn1.apply((Object) "1234.99"));
        Assert.assertEquals("1200.5", extractionFn1.apply("1234.99"));
        Assert.assertEquals("0.5", extractionFn1.apply("1"));
        Assert.assertEquals("0.5", extractionFn1.apply("100"));
        Assert.assertEquals("500.5", extractionFn1.apply(501));
        Assert.assertEquals("-399.5", extractionFn1.apply("-325"));
        Assert.assertEquals("2400.5", extractionFn1.apply("2.42e3"));
        Assert.assertEquals("-99.5", extractionFn1.apply("1.2e-1"));
        Assert.assertEquals(null, extractionFn1.apply("should be null"));
        Assert.assertEquals(null, extractionFn1.apply(""));
    }

    @Test
    public void testApply_11_testMerged_2() {
        BucketExtractionFn extractionFn2 = new BucketExtractionFn(3.0, 2.0);
        Assert.assertEquals("2", extractionFn2.apply("2"));
        Assert.assertEquals("2", extractionFn2.apply("3"));
        Assert.assertEquals("2", extractionFn2.apply("4.22"));
        Assert.assertEquals("-10", extractionFn2.apply("-8"));
        Assert.assertEquals("71", extractionFn2.apply("7.1e1"));
    }
}
