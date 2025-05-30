package org.apache.flink.runtime.operators.util;

import org.apache.flink.core.memory.MemorySegment;
import org.apache.flink.core.memory.MemorySegmentFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BloomFilterTest_Parameterized {

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
    void testBloomFilterNumHashFunctions_1() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(-1, -1)).isOne();
    }

    @Test
    void testBloomFilterNumHashFunctions_5() {
        assertThat(BloomFilter.optimalNumOfHashFunctions(10, 100)).isEqualTo(7);
    }

    @ParameterizedTest
    @MethodSource("Provider_testBloomNumBits_1to4")
    void testBloomNumBits_1to4(int param1, int param2) {
        assertThat(BloomFilter.optimalNumOfBits(param1, param2)).isZero();
    }

    static public Stream<Arguments> Provider_testBloomNumBits_1to4() {
        return Stream.of(arguments(0, 0), arguments(0, 0), arguments(0, 1), arguments(1, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testBloomNumBits_1to5_5to6_6to12")
    void testBloomNumBits_1to5_5to6_6to12(int param1, int param2, double param3) {
        assertThat(BloomFilter.optimalNumOfBits(param2, param3)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testBloomNumBits_1to5_5to6_6to12() {
        return Stream.of(arguments(7, 1, 0.03), arguments(72, 10, 0.03), arguments(729, 100, 0.03), arguments(7298, 1000, 0.03), arguments(72984, 10000, 0.03), arguments(729844, 100000, 0.03), arguments(7298440, 1000000, 0.03), arguments(6235224, 1000000, 0.05), arguments(7298440, 1000000, 0.03), arguments(6235224, 1000000, 0.05), arguments(4792529, 1000000, 0.1), arguments(3349834, 1000000, 0.2), arguments(2505911, 1000000, 0.3), arguments(1907139, 1000000, 0.4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testBloomFilterNumHashFunctions_2to4_6to10")
    void testBloomFilterNumHashFunctions_2to4_6to10(int param1, int param2) {
        assertThat(BloomFilter.optimalNumOfHashFunctions(param1, param2)).isOne();
    }

    static public Stream<Arguments> Provider_testBloomFilterNumHashFunctions_2to4_6to10() {
        return Stream.of(arguments(0, 0), arguments(10, 0), arguments(10, 10), arguments(100, 100), arguments(1000, 100), arguments(10000, 100), arguments(100000, 100), arguments(1000000, 100));
    }

    @ParameterizedTest
    @MethodSource("Provider_testBloomFilterFalsePositiveProbability_7to12")
    void testBloomFilterFalsePositiveProbability_7to12(double param1, double param2, int param3, int param4) {
        assertThat(Math.abs(BloomFilter.estimateFalsePositiveProbability(param3, param4) - param2) < param1).isTrue();
    }

    static public Stream<Arguments> Provider_testBloomFilterFalsePositiveProbability_7to12() {
        return Stream.of(arguments(0.01, 0.03, 1000000, 7298440), arguments(0.01, 0.05, 1000000, 6235224), arguments(0.01, 0.1, 1000000, 4792529), arguments(0.01, 0.2, 1000000, 3349834), arguments(0.01, 0.3, 1000000, 2505911), arguments(0.01, 0.4, 1000000, 1907139));
    }
}
