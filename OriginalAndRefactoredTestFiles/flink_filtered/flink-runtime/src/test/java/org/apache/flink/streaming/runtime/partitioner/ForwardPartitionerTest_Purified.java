package org.apache.flink.streaming.runtime.partitioner;

import org.apache.flink.api.java.tuple.Tuple;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ForwardPartitionerTest_Purified extends StreamPartitionerTest {

    @Override
    StreamPartitioner<Tuple> createPartitioner() {
        StreamPartitioner<Tuple> partitioner = new ForwardPartitioner<>();
        assertThat(partitioner.isBroadcast()).isFalse();
        return partitioner;
    }

    @Test
    void testSelectChannelsInterval_1() {
        assertSelectedChannelWithSetup(0, 1);
    }

    @Test
    void testSelectChannelsInterval_2() {
        assertSelectedChannelWithSetup(0, 2);
    }

    @Test
    void testSelectChannelsInterval_3() {
        assertSelectedChannelWithSetup(0, 1024);
    }
}
