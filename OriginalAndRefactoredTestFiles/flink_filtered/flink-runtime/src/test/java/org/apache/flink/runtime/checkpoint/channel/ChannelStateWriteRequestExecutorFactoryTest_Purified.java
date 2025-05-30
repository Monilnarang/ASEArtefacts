package org.apache.flink.runtime.checkpoint.channel;

import org.apache.flink.api.common.JobID;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.runtime.state.CheckpointStorage;
import org.apache.flink.runtime.state.storage.JobManagerCheckpointStorage;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelStateWriteRequestExecutorFactoryTest_Purified {

    private static final CheckpointStorage CHECKPOINT_STORAGE = new JobManagerCheckpointStorage();

    private void assertReuseExecutor(int maxSubtasksPerChannelStateFile) throws IOException {
        JobID JOB_ID = new JobID();
        Random RANDOM = new Random();
        ChannelStateWriteRequestExecutorFactory executorFactory = new ChannelStateWriteRequestExecutorFactory(JOB_ID);
        int numberOfTasks = 100;
        ChannelStateWriteRequestExecutor currentExecutor = null;
        for (int i = 0; i < numberOfTasks; i++) {
            ChannelStateWriteRequestExecutor newExecutor = executorFactory.getOrCreateExecutor(new JobVertexID(), RANDOM.nextInt(numberOfTasks), () -> CHECKPOINT_STORAGE.createCheckpointStorage(JOB_ID), maxSubtasksPerChannelStateFile);
            if (i % maxSubtasksPerChannelStateFile == 0) {
                assertThat(newExecutor).as("Factory should create the new executor.").isNotSameAs(currentExecutor);
                currentExecutor = newExecutor;
            } else {
                assertThat(newExecutor).as("Factory should reuse the old executor.").isSameAs(currentExecutor);
            }
        }
    }

    @Test
    void testReuseExecutorForSameJobId_1() throws IOException {
        assertReuseExecutor(1);
    }

    @Test
    void testReuseExecutorForSameJobId_2() throws IOException {
        assertReuseExecutor(2);
    }

    @Test
    void testReuseExecutorForSameJobId_3() throws IOException {
        assertReuseExecutor(3);
    }

    @Test
    void testReuseExecutorForSameJobId_4() throws IOException {
        assertReuseExecutor(5);
    }

    @Test
    void testReuseExecutorForSameJobId_5() throws IOException {
        assertReuseExecutor(10);
    }
}
