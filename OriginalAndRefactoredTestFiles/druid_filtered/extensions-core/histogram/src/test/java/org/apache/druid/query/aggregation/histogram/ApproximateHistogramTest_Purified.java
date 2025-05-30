package org.apache.druid.query.aggregation.histogram;

import com.google.common.collect.Iterators;
import org.apache.druid.java.util.common.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ApproximateHistogramTest_Purified {

    static final float[] VALUES = { 23, 19, 10, 16, 36, 2, 9, 32, 30, 45 };

    static final float[] VALUES2 = { 23, 19, 10, 16, 36, 2, 1, 9, 32, 30, 45, 46 };

    static final float[] VALUES3 = { 20, 16, 19, 27, 17, 20, 18, 20, 28, 14, 17, 21, 20, 21, 10, 25, 23, 17, 21, 18, 14, 20, 18, 12, 19, 20, 23, 25, 15, 22, 14, 17, 15, 23, 23, 15, 27, 20, 17, 15 };

    static final float[] VALUES4 = { 27.489f, 3.085f, 3.722f, 66.875f, 30.998f, -8.193f, 5.395f, 5.109f, 10.944f, 54.75f, 14.092f, 15.604f, 52.856f, 66.034f, 22.004f, -14.682f, -50.985f, 2.872f, 61.013f, -21.766f, 19.172f, 62.882f, 33.537f, 21.081f, 67.115f, 44.789f, 64.1f, 20.911f, -6.553f, 2.178f };

    static final float[] VALUES5 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    static final float[] VALUES6 = { 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f, 5.5f, 6f, 6.5f, 7f, 7.5f, 8f, 8.5f, 9f, 9.5f, 10f };

    static final float[] VALUES7 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 12, 12, 15, 20, 25, 25, 25 };

    protected ApproximateHistogram buildHistogram(int size, float[] values) {
        ApproximateHistogram h = new ApproximateHistogram(size);
        for (float v : values) {
            h.offer(v);
        }
        return h;
    }

    protected ApproximateHistogram buildHistogram(int size, float[] values, float lowerLimit, float upperLimit) {
        ApproximateHistogram h = new ApproximateHistogram(size, lowerLimit, upperLimit);
        for (float v : values) {
            h.offer(v);
        }
        return h;
    }

    @Test
    public void testSum_1_testMerged_1() {
        ApproximateHistogram h = buildHistogram(5, VALUES);
        Assert.assertEquals(0.0f, h.sum(0), 0.01);
        Assert.assertEquals(1.0f, h.sum(2), 0.01);
        Assert.assertEquals(1.16f, h.sum(5), 0.01);
        Assert.assertEquals(3.28f, h.sum(15), 0.01);
        Assert.assertEquals(VALUES.length, h.sum(45), 0.01);
        Assert.assertEquals(VALUES.length, h.sum(46), 0.01);
    }

    @Test
    public void testSum_7_testMerged_2() {
        ApproximateHistogram h2 = buildHistogram(5, VALUES2);
        Assert.assertEquals(0.0f, h2.sum(0), 0.01);
        Assert.assertEquals(0.0f, h2.sum(1f), 0.01);
        Assert.assertEquals(1.0f, h2.sum(1.5f), 0.01);
        Assert.assertEquals(1.125f, h2.sum(2f), 0.001);
        Assert.assertEquals(2.0625f, h2.sum(5.75f), 0.001);
        Assert.assertEquals(3.0f, h2.sum(9.5f), 0.01);
        Assert.assertEquals(11.0f, h2.sum(45.5f), 0.01);
        Assert.assertEquals(12.0f, h2.sum(46f), 0.01);
        Assert.assertEquals(12.0f, h2.sum(47f), 0.01);
    }
}
