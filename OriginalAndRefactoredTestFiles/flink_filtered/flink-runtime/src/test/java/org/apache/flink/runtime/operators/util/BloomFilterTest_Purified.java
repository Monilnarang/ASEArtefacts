package org.apache.flink.runtime.operators.util;

import org.apache.flink.core.memory.MemorySegment;
import org.apache.flink.core.memory.MemorySegmentFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BloomFilterTest_Purified {

    private static BloomFilter bloomFilter;

    private static BloomFilter bloomFilter2;

    private static final int INPUT_SIZE = 1024;

    private static final double FALSE_POSITIVE_PROBABILITY = 0.05;

    @BeforeAll
    static void init() {
        int bitsSize = BloomFilter.optimalNumOfBits(INPUT_SIZE, FALSE_POSITIVE_PROBABILITY);
        bitsSize = bitsSize + (Long.SIZE - (bitsSize % Long.SIZE));
        int byteSize = bitsSize >>> 3;
        MemorySegment memorySegment = MemorySegmentFactory.allocateUnpooledSegment(byteSize);
        bloomFilter = new BloomFilter(INPUT_SIZE, byteSize);
        bloomFilter.setBitsLocation(memorySegment, 0);
        MemorySegment memorySegment2 = MemorySegmentFactory.allocateUnpooledSegment(byteSize);
        bloomFilter2 = new BloomFilter(INPUT_SIZE, byteSize);
        bloomFilter2.setBitsLocation(memorySegment2, 0);
    }

    @Test
    void testBloomNumBits_1() {
        assertThat(BloomFilter.optimalNumOfBits(0, 0)).isZero();
    }

    @Test
    void testBloomNumBits_2() {
        assertThat(BloomFilter.optimalNumOfBits(0, 0)).isZero();
    }

    @Test
    void testBloomNumBits_3() {
        assertThat(BloomFilter.optimalNumOfBits(0, 1)).isZero();
    }

    @Test
    void testBloomNumBits_4() {
        assertThat(BloomFilter.optimalNumOfBits(1, 1)).isZero();
    }

    @Test
    void testBloomNumBits_5() {
        assertThat(BloomFilter.optimalNumOfBits(1, 0.03)).isEqualTo(7);
    }

    @Test
    void testBloomNumBits_6() {
        assertThat(BloomFilter.optimalNumOfBits(10, 0.03)).isEqualTo(72);
    }

    @Test
    void testBloomNumBits_7() {
        assertThat(BloomFilter.optimalNumOfBits(100, 0.03)).isEqualTo(729);
    }

    @Test
    void testBloomNumBits_8() {
        assertThat(BloomFilter.optimalNumOfBits(1000, 0.03)).isEqualTo(7298);
    }

    @Test
    void testBloomNumBits_9() {
        assertThat(BloomFilter.optimalNumOfBits(10000, 0.03)).isEqualTo(72984);
    }

    @Test
    void testBloomNumBits_10() {
        assertThat(BloomFilter.optimalNumOfBits(100000, 0.03)).isEqualTo(729844);
    }

    @Test
    void testBloomNumBits_11() {
        assertThat(BloomFilter.optimalNumOfBits(1000000, 0.03)).isEqualTo(7298440);
    }

    @Test
    void testBloomNumBits_12() {
        assertThat(BloomFilter.optimalNumOfBits(1000000, 0.05)).isEqualTo(6235224);
    }

    @Test
    void testBloomFilterNumHashFunctions_1() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(-1, -1)).isOne();
    }

    @Test
    void testBloomFilterNumHashFunctions_2() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(0, 0)).isOne();
    }

    @Test
    void testBloomFilterNumHashFunctions_3() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(10, 0)).isOne();
    }

    @Test
    void testBloomFilterNumHashFunctions_4() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(10, 10)).isOne();
    }

    @Test
    void testBloomFilterNumHashFunctions_5() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(10, 100)).isEqualTo(7);
    }

    @Test
    void testBloomFilterNumHashFunctions_6() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(100, 100)).isOne();
    }

    @Test
    void testBloomFilterNumHashFunctions_7() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(1000, 100)).isOne();
    }

    @Test
    void testBloomFilterNumHashFunctions_8() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(10000, 100)).isOne();
    }

    @Test
    void testBloomFilterNumHashFunctions_9() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(100000, 100)).isOne();
    }

    @Test
    void testBloomFilterNumHashFunctions_10() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(1000000, 100)).isOne();
    }

    @Test
    void testBloomFilterFalsePositiveProbability_1() {
        assertThat(BloomFilter.optimalNumOfBits(1000000, 0.03)).isEqualTo(7298440);
    }

    @Test
    void testBloomFilterFalsePositiveProbability_2() {
        assertThat(BloomFilter.optimalNumOfBits(1000000, 0.05)).isEqualTo(6235224);
    }

    @Test
    void testBloomFilterFalsePositiveProbability_3() {
        assertThat(BloomFilter.optimalNumOfBits(1000000, 0.1)).isEqualTo(4792529);
    }

    @Test
    void testBloomFilterFalsePositiveProbability_4() {
        assertThat(BloomFilter.optimalNumOfBits(1000000, 0.2)).isEqualTo(3349834);
    }

    @Test
    void testBloomFilterFalsePositiveProbability_5() {
        assertThat(BloomFilter.optimalNumOfBits(1000000, 0.3)).isEqualTo(2505911);
    }

    @Test
    void testBloomFilterFalsePositiveProbability_6() {
        assertThat(BloomFilter.optimalNumOfBits(1000000, 0.4)).isEqualTo(1907139);
    }

    @Test
    void testBloomFilterFalsePositiveProbability_7() {
        assertThat(Math.abs(BloomFilter.estimateFalsePositiveProbability(1000000, 7298440) - 0.03) < 0.01).isTrue();
    }

    @Test
    void testBloomFilterFalsePositiveProbability_8() {
        assertThat(Math.abs(BloomFilter.estimateFalsePositiveProbability(1000000, 6235224) - 0.05) < 0.01).isTrue();
    }

    @Test
    void testBloomFilterFalsePositiveProbability_9() {
        assertThat(Math.abs(BloomFilter.estimateFalsePositiveProbability(1000000, 4792529) - 0.1) < 0.01).isTrue();
    }

    @Test
    void testBloomFilterFalsePositiveProbability_10() {
        assertThat(Math.abs(BloomFilter.estimateFalsePositiveProbability(1000000, 3349834) - 0.2) < 0.01).isTrue();
    }

    @Test
    void testBloomFilterFalsePositiveProbability_11() {
        assertThat(Math.abs(BloomFilter.estimateFalsePositiveProbability(1000000, 2505911) - 0.3) < 0.01).isTrue();
    }

    @Test
    void testBloomFilterFalsePositiveProbability_12() {
        assertThat(Math.abs(BloomFilter.estimateFalsePositiveProbability(1000000, 1907139) - 0.4) < 0.01).isTrue();
    }
}
