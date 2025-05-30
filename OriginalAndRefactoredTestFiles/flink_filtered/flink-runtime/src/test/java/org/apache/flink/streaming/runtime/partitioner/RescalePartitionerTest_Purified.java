package org.apache.flink.streaming.runtime.partitioner;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.JobException;
import org.apache.flink.runtime.executiongraph.ExecutionGraph;
import org.apache.flink.runtime.executiongraph.ExecutionJobVertex;
import org.apache.flink.runtime.executiongraph.ExecutionVertex;
import org.apache.flink.runtime.executiongraph.IntermediateResultPartition;
import org.apache.flink.runtime.executiongraph.InternalExecutionGraphAccessor;
import org.apache.flink.runtime.executiongraph.TestingDefaultExecutionGraphBuilder;
import org.apache.flink.runtime.jobgraph.IntermediateResultPartitionID;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.JobVertex;
import org.apache.flink.runtime.metrics.groups.UnregisteredMetricGroups;
import org.apache.flink.runtime.scheduler.SchedulerBase;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.legacy.ParallelSourceFunction;
import org.apache.flink.testutils.TestingUtils;
import org.apache.flink.testutils.executor.TestExecutorExtension;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SuppressWarnings("serial")
class RescalePartitionerTest_Purified extends StreamPartitionerTest {

    @RegisterExtension
    private static final TestExecutorExtension<ScheduledExecutorService> EXECUTOR_RESOURCE = TestingUtils.defaultExecutorExtension();

    @Override
    StreamPartitioner<Tuple> createPartitioner() {
        StreamPartitioner<Tuple> partitioner = new RescalePartitioner<>();
        assertThat(partitioner.isBroadcast()).isFalse();
        return partitioner;
    }

    @Test
    void testSelectChannelsInterval_1() {
        assertSelectedChannel(0);
    }

    @Test
    void testSelectChannelsInterval_2() {
        assertSelectedChannel(1);
    }

    @Test
    void testSelectChannelsInterval_3() {
        assertSelectedChannel(2);
    }

    @Test
    void testSelectChannelsInterval_4() {
        assertSelectedChannel(0);
    }
}
