package org.apache.flink.runtime.executiongraph.failover.partitionrelease;

import org.apache.flink.runtime.scheduler.strategy.TestingSchedulingExecutionVertex;
import org.apache.flink.runtime.scheduler.strategy.TestingSchedulingPipelinedRegion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;

class ConsumerRegionGroupExecutionViewMaintainerTest_Purified {

    private TestingSchedulingPipelinedRegion producerRegion;

    private TestingSchedulingPipelinedRegion consumerRegion;

    private ConsumerRegionGroupExecutionView consumerRegionGroupExecutionView;

    private ConsumerRegionGroupExecutionViewMaintainer consumerRegionGroupExecutionViewMaintainer;

    @BeforeEach
    void setup() {
        createProducerAndConsumer();
        createConsumerRegionGroupExecutionViewMaintainer();
    }

    private void createProducerAndConsumer() {
        TestingSchedulingExecutionVertex producer = TestingSchedulingExecutionVertex.newBuilder().build();
        TestingSchedulingExecutionVertex consumer = TestingSchedulingExecutionVertex.newBuilder().build();
        producerRegion = new TestingSchedulingPipelinedRegion(Collections.singleton(producer));
        consumerRegion = new TestingSchedulingPipelinedRegion(Collections.singleton(consumer));
    }

    private void createConsumerRegionGroupExecutionViewMaintainer() {
        consumerRegionGroupExecutionView = new ConsumerRegionGroupExecutionView();
        consumerRegionGroupExecutionView.add(consumerRegion);
        consumerRegionGroupExecutionViewMaintainer = new ConsumerRegionGroupExecutionViewMaintainer();
        consumerRegionGroupExecutionViewMaintainer.notifyNewRegionGroupExecutionViews(Collections.singletonList(consumerRegionGroupExecutionView));
    }

    @Test
    void testRegionUnfinishedMultipleTimes_1() throws Exception {
        assertThat(consumerRegionGroupExecutionView.isFinished()).isFalse();
    }

    @Test
    void testRegionUnfinishedMultipleTimes_2() throws Exception {
        assertThat(consumerRegionGroupExecutionView.isFinished()).isTrue();
    }
}
