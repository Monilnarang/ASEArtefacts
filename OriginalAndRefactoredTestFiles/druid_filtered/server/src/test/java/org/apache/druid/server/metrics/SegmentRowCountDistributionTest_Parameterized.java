package org.apache.druid.server.metrics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.function.ObjIntConsumer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SegmentRowCountDistributionTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_test_bucketDimensionFromIndex_1to10")
    public void test_bucketDimensionFromIndex_1to10(String param1, int param2) {
        Assert.assertEquals(param1, getBucketDimensionFromIndex(param2));
    }

    static public Stream<Arguments> Provider_test_bucketDimensionFromIndex_1to10() {
        return Stream.of(arguments("Tombstone", 0), arguments(0, 1), arguments("1-10k", 2), arguments("10k-2M", 3), arguments("2M-4M", 4), arguments("4M-6M", 5), arguments("6M-8M", 6), arguments("8M-10M", 7), arguments("10M+", 8), arguments("NA", 9));
    }
}
