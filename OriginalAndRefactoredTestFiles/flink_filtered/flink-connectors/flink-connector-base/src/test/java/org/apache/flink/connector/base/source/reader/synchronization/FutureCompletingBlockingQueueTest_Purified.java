package org.apache.flink.connector.base.source.reader.synchronization;

import org.apache.flink.connector.base.source.reader.SourceReaderOptions;
import org.apache.flink.runtime.io.AvailabilityProvider;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class FutureCompletingBlockingQueueTest_Purified {

    private static final int DEFAULT_CAPACITY = 2;

    @Test
    void testQueueDefaultCapacity_1() {
        final FutureCompletingBlockingQueue<Object> queue = new FutureCompletingBlockingQueue<>();
        assertThat(queue.remainingCapacity()).isEqualTo(DEFAULT_CAPACITY);
    }

    @Test
    void testQueueDefaultCapacity_2() {
        assertThat(SourceReaderOptions.ELEMENT_QUEUE_CAPACITY.defaultValue().intValue()).isEqualTo(DEFAULT_CAPACITY);
    }
}
