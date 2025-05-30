package org.apache.flink.runtime.executiongraph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.apache.flink.runtime.executiongraph.VertexInputInfoComputationUtils.computeVertexInputInfoForAllToAll;
import static org.apache.flink.runtime.executiongraph.VertexInputInfoComputationUtils.computeVertexInputInfoForPointwise;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VertexInputInfoComputationUtilsTest_Purified {

    private static IndexRange computeConsumedSubpartitionRange(int consumerIndex, int numConsumers, int numSubpartitions) {
        return computeConsumedSubpartitionRange(consumerIndex, numConsumers, numSubpartitions, true, false, false);
    }

    private static IndexRange computeConsumedSubpartitionRange(int consumerIndex, int numConsumers, int numSubpartitions, boolean isDynamicGraph, boolean isBroadcast, boolean broadcast) {
        return VertexInputInfoComputationUtils.computeConsumedSubpartitionRange(consumerIndex, numConsumers, () -> numSubpartitions, isDynamicGraph, isBroadcast, broadcast);
    }

    @Test
    void testComputeConsumedSubpartitionRange3to2_1() {
        final IndexRange range1 = computeConsumedSubpartitionRange(0, 2, 3);
        assertThat(range1).isEqualTo(new IndexRange(0, 0));
    }

    @Test
    void testComputeConsumedSubpartitionRange3to2_2() {
        final IndexRange range2 = computeConsumedSubpartitionRange(1, 2, 3);
        assertThat(range2).isEqualTo(new IndexRange(1, 2));
    }

    @Test
    void testComputeConsumedSubpartitionRange6to4_1() {
        final IndexRange range1 = computeConsumedSubpartitionRange(0, 4, 6);
        assertThat(range1).isEqualTo(new IndexRange(0, 0));
    }

    @Test
    void testComputeConsumedSubpartitionRange6to4_2() {
        final IndexRange range2 = computeConsumedSubpartitionRange(1, 4, 6);
        assertThat(range2).isEqualTo(new IndexRange(1, 2));
    }

    @Test
    void testComputeConsumedSubpartitionRange6to4_3() {
        final IndexRange range3 = computeConsumedSubpartitionRange(2, 4, 6);
        assertThat(range3).isEqualTo(new IndexRange(3, 3));
    }

    @Test
    void testComputeConsumedSubpartitionRange6to4_4() {
        final IndexRange range4 = computeConsumedSubpartitionRange(3, 4, 6);
        assertThat(range4).isEqualTo(new IndexRange(4, 5));
    }

    @Test
    void testComputeConsumedSubpartitionRangeForNonDynamicGraph_1() {
        final IndexRange range1 = computeConsumedSubpartitionRange(0, 3, -1, false, false, false);
        assertThat(range1).isEqualTo(new IndexRange(0, 0));
    }

    @Test
    void testComputeConsumedSubpartitionRangeForNonDynamicGraph_2() {
        final IndexRange range2 = computeConsumedSubpartitionRange(1, 3, -1, false, false, false);
        assertThat(range2).isEqualTo(new IndexRange(1, 1));
    }

    @Test
    void testComputeConsumedSubpartitionRangeForNonDynamicGraph_3() {
        final IndexRange range3 = computeConsumedSubpartitionRange(2, 3, -1, false, false, false);
        assertThat(range3).isEqualTo(new IndexRange(2, 2));
    }
}
