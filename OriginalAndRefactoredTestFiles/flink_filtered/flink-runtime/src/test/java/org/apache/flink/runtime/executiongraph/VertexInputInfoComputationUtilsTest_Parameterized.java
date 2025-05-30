package org.apache.flink.runtime.executiongraph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.apache.flink.runtime.executiongraph.VertexInputInfoComputationUtils.computeVertexInputInfoForAllToAll;
import static org.apache.flink.runtime.executiongraph.VertexInputInfoComputationUtils.computeVertexInputInfoForPointwise;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class VertexInputInfoComputationUtilsTest_Parameterized {

    private static IndexRange computeConsumedSubpartitionRange(int consumerIndex, int numConsumers, int numSubpartitions) {
        return computeConsumedSubpartitionRange(consumerIndex, numConsumers, numSubpartitions, true, false, false);
    }

    private static IndexRange computeConsumedSubpartitionRange(int consumerIndex, int numConsumers, int numSubpartitions, boolean isDynamicGraph, boolean isBroadcast, boolean broadcast) {
        return VertexInputInfoComputationUtils.computeConsumedSubpartitionRange(consumerIndex, numConsumers, () -> numSubpartitions, isDynamicGraph, isBroadcast, broadcast);
    }

    @Test
    void testComputeConsumedSubpartitionRange6to4_4() {
        final IndexRange range4 = computeConsumedSubpartitionRange(3, 4, 6);
        assertThat(range4).isEqualTo(new IndexRange(4, 5));
    }

    @ParameterizedTest
    @MethodSource("Provider_testComputeConsumedSubpartitionRange3to2_1_1_1")
    void testComputeConsumedSubpartitionRange3to2_1_1_1(int param1, int param2, int param3, int param4, int param5) {
        final IndexRange range1 = computeConsumedSubpartitionRange(param3, param4, param5);
        assertThat(range1).isEqualTo(new IndexRange(param1, param2));
    }

    static public Stream<Arguments> Provider_testComputeConsumedSubpartitionRange3to2_1_1_1() {
        return Stream.of(arguments(0, 2, 3, 0, 0), arguments(0, 4, 6, 0, 0), arguments(0, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testComputeConsumedSubpartitionRange3to2_2_2_2")
    void testComputeConsumedSubpartitionRange3to2_2_2_2(int param1, int param2, int param3, int param4, int param5) {
        final IndexRange range2 = computeConsumedSubpartitionRange(param3, param4, param5);
        assertThat(range2).isEqualTo(new IndexRange(param1, param2));
    }

    static public Stream<Arguments> Provider_testComputeConsumedSubpartitionRange3to2_2_2_2() {
        return Stream.of(arguments(1, 2, 3, 1, 2), arguments(1, 4, 6, 1, 2), arguments(1, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testComputeConsumedSubpartitionRange6to4_3_3")
    void testComputeConsumedSubpartitionRange6to4_3_3(int param1, int param2, int param3, int param4, int param5) {
        final IndexRange range3 = computeConsumedSubpartitionRange(param3, param4, param5);
        assertThat(range3).isEqualTo(new IndexRange(param1, param2));
    }

    static public Stream<Arguments> Provider_testComputeConsumedSubpartitionRange6to4_3_3() {
        return Stream.of(arguments(2, 4, 6, 3, 3), arguments(2, 3));
    }
}
