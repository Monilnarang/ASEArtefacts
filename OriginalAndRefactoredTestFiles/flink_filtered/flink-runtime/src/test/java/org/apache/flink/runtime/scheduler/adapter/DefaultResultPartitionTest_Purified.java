package org.apache.flink.runtime.scheduler.adapter;

import org.apache.flink.runtime.jobgraph.IntermediateDataSetID;
import org.apache.flink.runtime.jobgraph.IntermediateResultPartitionID;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.runtime.scheduler.strategy.ConsumerVertexGroup;
import org.apache.flink.runtime.scheduler.strategy.ExecutionVertexID;
import org.apache.flink.runtime.scheduler.strategy.ResultPartitionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import static org.apache.flink.runtime.io.network.partition.ResultPartitionType.BLOCKING;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultResultPartitionTest_Purified {

    private static final TestResultPartitionStateSupplier resultPartitionState = new TestResultPartitionStateSupplier();

    private final IntermediateResultPartitionID resultPartitionId = new IntermediateResultPartitionID();

    private final IntermediateDataSetID intermediateResultId = new IntermediateDataSetID();

    private DefaultResultPartition resultPartition;

    private final Map<IntermediateResultPartitionID, List<ConsumerVertexGroup>> consumerVertexGroups = new HashMap<>();

    @BeforeEach
    void setUp() {
        resultPartition = new DefaultResultPartition(resultPartitionId, intermediateResultId, BLOCKING, resultPartitionState, () -> consumerVertexGroups.computeIfAbsent(resultPartitionId, ignored -> new ArrayList<>()), () -> {
            throw new UnsupportedOperationException();
        });
    }

    private static class TestResultPartitionStateSupplier implements Supplier<ResultPartitionState> {

        private ResultPartitionState resultPartitionState;

        void setResultPartitionState(ResultPartitionState state) {
            resultPartitionState = state;
        }

        @Override
        public ResultPartitionState get() {
            return resultPartitionState;
        }
    }

    @Test
    void testGetConsumerVertexGroup_1() {
        assertThat(resultPartition.getConsumerVertexGroups()).isEmpty();
    }

    @Test
    void testGetConsumerVertexGroup_2_testMerged_2() {
        ExecutionVertexID executionVertexId = new ExecutionVertexID(new JobVertexID(), 0);
        consumerVertexGroups.put(resultPartition.getId(), Collections.singletonList(ConsumerVertexGroup.fromSingleVertex(executionVertexId, resultPartition.getResultType())));
        assertThat(resultPartition.getConsumerVertexGroups()).isNotEmpty();
        assertThat(resultPartition.getConsumerVertexGroups().get(0)).contains(executionVertexId);
    }
}
