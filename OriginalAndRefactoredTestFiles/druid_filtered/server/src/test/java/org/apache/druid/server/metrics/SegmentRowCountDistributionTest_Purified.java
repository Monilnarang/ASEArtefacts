package org.apache.druid.server.metrics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.function.ObjIntConsumer;

public class SegmentRowCountDistributionTest_Purified {

    private SegmentRowCountDistribution rowCountBucket;

    @Before
    public void setUp() {
        rowCountBucket = new SegmentRowCountDistribution();
    }

    private static class AssertBucketHasValue implements ObjIntConsumer<String> {

        private final int expectedBucket;

        private final int expectedValue;

        private AssertBucketHasValue(int expectedBucket, int expectedValue) {
            this.expectedBucket = expectedBucket;
            this.expectedValue = expectedValue;
        }

        static AssertBucketHasValue assertExpected(int expectedValue, int expectedBucket) {
            return new AssertBucketHasValue(expectedBucket, expectedValue);
        }

        @Override
        public void accept(String s, int value) {
            if (s.equals(getBucketDimensionFromIndex(expectedBucket))) {
                Assert.assertEquals(expectedValue, value);
            } else {
                Assert.assertEquals(0, value);
            }
        }
    }

    private static String getBucketDimensionFromIndex(int index) {
        switch(index) {
            case 0:
                return "Tombstone";
            case 1:
                return "0";
            case 2:
                return "1-10k";
            case 3:
                return "10k-2M";
            case 4:
                return "2M-4M";
            case 5:
                return "4M-6M";
            case 6:
                return "6M-8M";
            case 7:
                return "8M-10M";
            case 8:
                return "10M+";
            default:
                return "NA";
        }
    }

    @Test
    public void test_bucketDimensionFromIndex_1() {
        Assert.assertEquals("Tombstone", getBucketDimensionFromIndex(0));
    }

    @Test
    public void test_bucketDimensionFromIndex_2() {
        Assert.assertEquals("0", getBucketDimensionFromIndex(1));
    }

    @Test
    public void test_bucketDimensionFromIndex_3() {
        Assert.assertEquals("1-10k", getBucketDimensionFromIndex(2));
    }

    @Test
    public void test_bucketDimensionFromIndex_4() {
        Assert.assertEquals("10k-2M", getBucketDimensionFromIndex(3));
    }

    @Test
    public void test_bucketDimensionFromIndex_5() {
        Assert.assertEquals("2M-4M", getBucketDimensionFromIndex(4));
    }

    @Test
    public void test_bucketDimensionFromIndex_6() {
        Assert.assertEquals("4M-6M", getBucketDimensionFromIndex(5));
    }

    @Test
    public void test_bucketDimensionFromIndex_7() {
        Assert.assertEquals("6M-8M", getBucketDimensionFromIndex(6));
    }

    @Test
    public void test_bucketDimensionFromIndex_8() {
        Assert.assertEquals("8M-10M", getBucketDimensionFromIndex(7));
    }

    @Test
    public void test_bucketDimensionFromIndex_9() {
        Assert.assertEquals("10M+", getBucketDimensionFromIndex(8));
    }

    @Test
    public void test_bucketDimensionFromIndex_10() {
        Assert.assertEquals("NA", getBucketDimensionFromIndex(9));
    }
}
