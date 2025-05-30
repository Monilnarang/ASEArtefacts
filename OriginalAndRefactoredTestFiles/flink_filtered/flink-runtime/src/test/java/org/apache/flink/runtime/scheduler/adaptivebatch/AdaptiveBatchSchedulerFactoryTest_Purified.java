package org.apache.flink.runtime.scheduler.adaptivebatch;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.JobManagerOptions;
import org.apache.flink.configuration.JobManagerOptions.HybridPartitionDataConsumeConstraint;
import org.apache.flink.runtime.scheduler.strategy.AllFinishedInputConsumableDecider;
import org.apache.flink.runtime.scheduler.strategy.DefaultInputConsumableDecider;
import org.apache.flink.runtime.scheduler.strategy.InputConsumableDecider;
import org.apache.flink.runtime.scheduler.strategy.PartialFinishedInputConsumableDecider;
import org.junit.jupiter.api.Test;
import static org.apache.flink.configuration.JobManagerOptions.HybridPartitionDataConsumeConstraint.ALL_PRODUCERS_FINISHED;
import static org.apache.flink.configuration.JobManagerOptions.HybridPartitionDataConsumeConstraint.ONLY_FINISHED_PRODUCERS;
import static org.apache.flink.configuration.JobManagerOptions.HybridPartitionDataConsumeConstraint.UNFINISHED_PRODUCERS;
import static org.apache.flink.runtime.scheduler.adaptivebatch.AdaptiveBatchSchedulerFactory.getOrDecideHybridPartitionDataConsumeConstraint;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AdaptiveBatchSchedulerFactoryTest_Purified {

    private void assertAndLoadInputConsumableDecider(HybridPartitionDataConsumeConstraint hybridPartitionDataConsumeConstraint, InputConsumableDecider.Factory expectedFactory) {
        InputConsumableDecider.Factory factory = AdaptiveBatchSchedulerFactory.loadInputConsumableDeciderFactory(hybridPartitionDataConsumeConstraint);
        assertThat(factory).isEqualTo(expectedFactory);
    }

    @Test
    void testLoadInputConsumableDeciderFactory_1() {
        assertAndLoadInputConsumableDecider(UNFINISHED_PRODUCERS, DefaultInputConsumableDecider.Factory.INSTANCE);
    }

    @Test
    void testLoadInputConsumableDeciderFactory_2() {
        assertAndLoadInputConsumableDecider(ONLY_FINISHED_PRODUCERS, PartialFinishedInputConsumableDecider.Factory.INSTANCE);
    }

    @Test
    void testLoadInputConsumableDeciderFactory_3() {
        assertAndLoadInputConsumableDecider(ALL_PRODUCERS_FINISHED, AllFinishedInputConsumableDecider.Factory.INSTANCE);
    }
}
