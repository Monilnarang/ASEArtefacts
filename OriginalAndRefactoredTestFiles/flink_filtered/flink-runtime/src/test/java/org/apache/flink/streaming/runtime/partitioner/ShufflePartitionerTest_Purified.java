package org.apache.flink.streaming.runtime.partitioner;

import org.apache.flink.api.java.tuple.Tuple;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ShufflePartitionerTest_Purified extends StreamPartitionerTest {

    @Override
    StreamPartitioner<Tuple> createPartitioner() {
        StreamPartitioner<Tuple> partitioner = new ShufflePartitioner<>();
        assertThat(partitioner.isBroadcast()).isFalse();
        return partitioner;
    }

    @Test
    void testSelectChannelsInterval_1() {
        assertSelectedChannelWithSetup(0, 1);
    }

    @Test
    void testSelectChannelsInterval_2_testMerged_2() {
        streamPartitioner.setup(2);
        streamPartitioner.setup(1024);
    }
}
