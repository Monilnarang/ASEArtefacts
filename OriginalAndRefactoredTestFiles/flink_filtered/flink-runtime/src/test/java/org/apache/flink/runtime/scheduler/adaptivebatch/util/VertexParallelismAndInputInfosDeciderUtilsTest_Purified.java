package org.apache.flink.runtime.scheduler.adaptivebatch.util;

import org.apache.flink.runtime.executiongraph.IndexRange;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.apache.flink.runtime.scheduler.adaptivebatch.util.VertexParallelismAndInputInfosDeciderUtils.cartesianProduct;
import static org.apache.flink.runtime.scheduler.adaptivebatch.util.VertexParallelismAndInputInfosDeciderUtils.computeSkewThreshold;
import static org.apache.flink.runtime.scheduler.adaptivebatch.util.VertexParallelismAndInputInfosDeciderUtils.computeTargetSize;
import static org.apache.flink.runtime.scheduler.adaptivebatch.util.VertexParallelismAndInputInfosDeciderUtils.median;
import static org.apache.flink.runtime.scheduler.adaptivebatch.util.VertexParallelismAndInputInfosDeciderUtils.tryComputeSubpartitionSliceRange;
import static org.assertj.core.api.Assertions.assertThat;

class VertexParallelismAndInputInfosDeciderUtilsTest_Purified {

    List<SubpartitionSlice> createSubpartitionSlices(int numSlices, long[] dataBytesPerSlice) {
        List<SubpartitionSlice> subpartitionSlices = new ArrayList<>();
        for (int i = 0; i < numSlices; ++i) {
            subpartitionSlices.add(SubpartitionSlice.createSubpartitionSlice(new IndexRange(0, 0), new IndexRange(i, i), dataBytesPerSlice[i]));
        }
        return subpartitionSlices;
    }

    @Test
    void computeSkewThresholdTest_1() {
        long mediaSize1 = 100;
        double skewedFactor1 = 1.5;
        long defaultSkewedThreshold1 = 50;
        long result1 = computeSkewThreshold(mediaSize1, skewedFactor1, defaultSkewedThreshold1);
        assertThat(result1).isEqualTo(150L);
    }

    @Test
    void computeSkewThresholdTest_2() {
        long mediaSize2 = 40;
        double skewedFactor2 = 1.0;
        long defaultSkewedThreshold2 = 50;
        long result2 = computeSkewThreshold(mediaSize2, skewedFactor2, defaultSkewedThreshold2);
        assertThat(result2).isEqualTo(50L);
    }
}
