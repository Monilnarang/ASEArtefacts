package org.apache.flink.connector.base.sink.writer;

import org.apache.flink.api.common.operators.MailboxExecutor;
import org.apache.flink.api.connector.sink2.WriterInitContext;
import org.apache.flink.connector.base.sink.writer.config.AsyncSinkWriterConfiguration;
import org.apache.flink.connector.base.sink.writer.strategy.AIMDScalingStrategy;
import org.apache.flink.connector.base.sink.writer.strategy.CongestionControlRateLimitingStrategy;
import org.apache.flink.streaming.runtime.tasks.TestProcessingTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import static org.apache.flink.connector.base.sink.writer.AsyncSinkWriterTestUtils.assertThatBufferStatesAreEqual;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class AsyncSinkWriterTest_Purified {

    private final List<Integer> res = new ArrayList<>();

    private TestSinkInitContext sinkInitContext;

    private TestSinkInitContextAnyThreadMailbox sinkInitContextAnyThreadMailbox;

    @BeforeEach
    void before() {
        res.clear();
        sinkInitContext = new TestSinkInitContext();
        sinkInitContextAnyThreadMailbox = new TestSinkInitContextAnyThreadMailbox();
    }

    private void performNormalWriteOfEightyRecordsToMock() throws IOException, InterruptedException {
        AsyncSinkWriterImpl sink = new AsyncSinkWriterImplBuilder().context(sinkInitContext).build();
        for (int i = 0; i < 80; i++) {
            sink.write(String.valueOf(i));
        }
    }

    public void writeFiveRecordsWithOneFailingThenCallPrepareCommitWithFlushing() throws IOException, InterruptedException {
        AsyncSinkWriterImpl sink = new AsyncSinkWriterImplBuilder().context(sinkInitContext).maxBatchSize(3).build();
        sink.write("25");
        sink.write("55");
        sink.write("75");
        sink.write("95");
        sink.write("955");
        assertThatBufferStatesAreEqual(sink.wrapRequests(95, 955), getWriterState(sink));
        sink.flush(true);
        assertThatBufferStatesAreEqual(BufferedRequestState.emptyState(), getWriterState(sink));
    }

    private void writeXToSinkAssertDestinationIsInStateYAndBufferHasZ(AsyncSinkWriterImpl sink, String x, List<Integer> y, List<Integer> z) throws IOException, InterruptedException {
        sink.write(x);
        assertThat(res).isEqualTo(y);
        assertThatBufferStatesAreEqual(sink.wrapRequests(z), getWriterState(sink));
    }

    private void writeTwoElementsAndInterleaveTheNextTwoElements(AsyncSinkWriterImpl sink, CountDownLatch blockedWriteLatch, CountDownLatch delayedStartLatch) throws Exception {
        TestProcessingTimeService tpts = sinkInitContext.getTestProcessingTimeService();
        ExecutorService es = Executors.newFixedThreadPool(4);
        try {
            tpts.setCurrentTime(0L);
            sink.write("1");
            sink.write("2");
            es.submit(() -> {
                try {
                    sink.writeAsNonMailboxThread("3");
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            delayedStartLatch.await();
            sink.write("4");
            tpts.setCurrentTime(100L);
            blockedWriteLatch.countDown();
            es.shutdown();
            assertThat(es.awaitTermination(500, TimeUnit.MILLISECONDS)).as("Executor Service stuck at termination, not terminated after 500ms!").isTrue();
        } finally {
            es.shutdown();
        }
    }

    private BufferedRequestState<Integer> getWriterState(AsyncSinkWriter<String, Integer> sinkWriter) {
        List<BufferedRequestState<Integer>> states = sinkWriter.snapshotState(1);
        assertThat(states.size()).isEqualTo(1);
        return states.get(0);
    }

    private class AsyncSinkWriterImpl extends AsyncSinkWriter<String, Integer> {

        private final Set<Integer> failedFirstAttempts = new HashSet<>();

        private final boolean simulateFailures;

        private final int delay;

        private AsyncSinkWriterImpl(ElementConverter<String, Integer> elementConverter, WriterInitContext context, int maxBatchSize, int maxInFlightRequests, int maxBufferedRequests, long maxBatchSizeInBytes, long maxTimeInBufferMS, long maxRecordSizeInBytes, boolean simulateFailures, int delay, List<BufferedRequestState<Integer>> bufferedState) {
            super(elementConverter, context, AsyncSinkWriterConfiguration.builder().setMaxBatchSize(maxBatchSize).setMaxBatchSizeInBytes(maxBatchSizeInBytes).setMaxInFlightRequests(maxInFlightRequests).setMaxBufferedRequests(maxBufferedRequests).setMaxTimeInBufferMS(maxTimeInBufferMS).setMaxRecordSizeInBytes(maxRecordSizeInBytes).setRateLimitingStrategy(CongestionControlRateLimitingStrategy.builder().setInitialMaxInFlightMessages(maxBatchSize * maxInFlightRequests).setMaxInFlightRequests(maxInFlightRequests).setScalingStrategy(AIMDScalingStrategy.builder(maxBatchSize * maxInFlightRequests).build()).build()).build(), bufferedState);
            this.simulateFailures = simulateFailures;
            this.delay = delay;
        }

        private AsyncSinkWriterImpl(ElementConverter<String, Integer> elementConverter, WriterInitContext context, int maxBatchSize, int maxInFlightRequests, int maxBufferedRequests, long maxBatchSizeInBytes, long maxTimeInBufferMS, long maxRecordSizeInBytes, boolean simulateFailures, int delay, List<BufferedRequestState<Integer>> bufferedState, BatchCreator<Integer> batchCreator, RequestBuffer<Integer> requestBuffer) {
            super(elementConverter, context, AsyncSinkWriterConfiguration.builder().setMaxBatchSize(maxBatchSize).setMaxBatchSizeInBytes(maxBatchSizeInBytes).setMaxInFlightRequests(maxInFlightRequests).setMaxBufferedRequests(maxBufferedRequests).setMaxTimeInBufferMS(maxTimeInBufferMS).setMaxRecordSizeInBytes(maxRecordSizeInBytes).setRateLimitingStrategy(CongestionControlRateLimitingStrategy.builder().setInitialMaxInFlightMessages(maxBatchSize * maxInFlightRequests).setMaxInFlightRequests(maxInFlightRequests).setScalingStrategy(AIMDScalingStrategy.builder(maxBatchSize * maxInFlightRequests).build()).build()).build(), bufferedState, batchCreator, requestBuffer);
            this.simulateFailures = simulateFailures;
            this.delay = delay;
        }

        public void write(String val) throws IOException, InterruptedException {
            yieldMailbox(sinkInitContext.getMailboxExecutor());
            yieldMailbox(sinkInitContextAnyThreadMailbox.getMailboxExecutor());
            write(val, null);
        }

        public void yieldMailbox(MailboxExecutor mailbox) {
            boolean canYield = true;
            while (canYield) {
                canYield = mailbox.tryYield();
            }
        }

        public void writeAsNonMailboxThread(String val) throws IOException, InterruptedException {
            write(val, null);
        }

        @Override
        protected void submitRequestEntries(List<Integer> requestEntries, ResultHandler<Integer> resultHandler) {
            maybeDelay();
            if (requestEntries.stream().anyMatch(val -> val > 100 && val <= 200)) {
                throw new RuntimeException("Deliberate runtime exception occurred in SinkWriterImplementation.");
            }
            if (simulateFailures) {
                List<Integer> successfulRetries = failedFirstAttempts.stream().filter(requestEntries::contains).collect(Collectors.toList());
                failedFirstAttempts.removeIf(successfulRetries::contains);
                List<Integer> firstTimeFailed = requestEntries.stream().filter(x -> !successfulRetries.contains(x)).filter(val -> val > 200).collect(Collectors.toList());
                failedFirstAttempts.addAll(firstTimeFailed);
                requestEntries.removeAll(firstTimeFailed);
                res.addAll(requestEntries);
                resultHandler.retryForEntries(firstTimeFailed);
            } else {
                res.addAll(requestEntries);
                resultHandler.complete();
            }
        }

        private void maybeDelay() {
            if (delay <= 0) {
                return;
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                fail("Thread sleeping for delay in submitRequestEntries was interrupted.");
            }
        }

        @Override
        protected long getSizeInBytes(Integer requestEntry) {
            return requestEntry > 200 && simulateFailures ? 100 : 4;
        }

        public BufferedRequestState<Integer> wrapRequests(Integer... requests) {
            return wrapRequests(Arrays.asList(requests));
        }

        public BufferedRequestState<Integer> wrapRequests(List<Integer> requests) {
            List<RequestEntryWrapper<Integer>> wrapperList = new ArrayList<>();
            for (Integer request : requests) {
                wrapperList.add(new RequestEntryWrapper<>(request, getSizeInBytes(request)));
            }
            return new BufferedRequestState<>(wrapperList);
        }
    }

    private class AsyncSinkWriterImplBuilder {

        private ElementConverter<String, Integer> elementConverter = (elem, ctx) -> Integer.parseInt(elem);

        private boolean simulateFailures = false;

        private int delay = 0;

        private WriterInitContext context;

        private int maxBatchSize = 10;

        private int maxInFlightRequests = 1;

        private int maxBufferedRequests = 100;

        private long maxBatchSizeInBytes = 110;

        private long maxTimeInBufferMS = 1_000;

        private long maxRecordSizeInBytes = maxBatchSizeInBytes;

        private AsyncSinkWriterImplBuilder elementConverter(ElementConverter<String, Integer> elementConverter) {
            this.elementConverter = elementConverter;
            return this;
        }

        private AsyncSinkWriterImplBuilder context(WriterInitContext context) {
            this.context = context;
            return this;
        }

        private AsyncSinkWriterImplBuilder maxBatchSize(int maxBatchSize) {
            this.maxBatchSize = maxBatchSize;
            return this;
        }

        private AsyncSinkWriterImplBuilder maxInFlightRequests(int maxInFlightRequests) {
            this.maxInFlightRequests = maxInFlightRequests;
            return this;
        }

        private AsyncSinkWriterImplBuilder maxBufferedRequests(int maxBufferedRequests) {
            this.maxBufferedRequests = maxBufferedRequests;
            return this;
        }

        private AsyncSinkWriterImplBuilder maxBatchSizeInBytes(long maxBatchSizeInBytes) {
            this.maxBatchSizeInBytes = maxBatchSizeInBytes;
            return this;
        }

        private AsyncSinkWriterImplBuilder maxTimeInBufferMS(long maxTimeInBufferMS) {
            this.maxTimeInBufferMS = maxTimeInBufferMS;
            return this;
        }

        private AsyncSinkWriterImplBuilder maxRecordSizeInBytes(long maxRecordSizeInBytes) {
            this.maxRecordSizeInBytes = maxRecordSizeInBytes;
            return this;
        }

        private AsyncSinkWriterImplBuilder delay(int delay) {
            this.delay = delay;
            return this;
        }

        private AsyncSinkWriterImplBuilder simulateFailures(boolean simulateFailures) {
            this.simulateFailures = simulateFailures;
            return this;
        }

        private AsyncSinkWriterImpl build() {
            return new AsyncSinkWriterImpl(elementConverter, context, maxBatchSize, maxInFlightRequests, maxBufferedRequests, maxBatchSizeInBytes, maxTimeInBufferMS, maxRecordSizeInBytes, simulateFailures, delay, Collections.emptyList());
        }

        private AsyncSinkWriterImpl buildWithState(List<BufferedRequestState<Integer>> bufferedState) {
            return new AsyncSinkWriterImpl(elementConverter, context, maxBatchSize, maxInFlightRequests, maxBufferedRequests, maxBatchSizeInBytes, maxTimeInBufferMS, maxRecordSizeInBytes, simulateFailures, delay, bufferedState);
        }

        private AsyncSinkWriterImpl buildWithCustomPluggableComponents(BatchCreator<Integer> batchCreator, RequestBuffer<Integer> requestBuffer) {
            return new AsyncSinkWriterImpl(elementConverter, context, maxBatchSize, maxInFlightRequests, maxBufferedRequests, maxBatchSizeInBytes, maxTimeInBufferMS, maxRecordSizeInBytes, simulateFailures, delay, Collections.emptyList(), batchCreator, requestBuffer);
        }
    }

    private class AsyncSinkReleaseAndBlockWriterImpl extends AsyncSinkWriterImpl {

        private final CountDownLatch blockedThreadLatch;

        private final CountDownLatch delayedStartLatch;

        private final boolean blockForLimitedTime;

        public AsyncSinkReleaseAndBlockWriterImpl(WriterInitContext context, int maxInFlightRequests, CountDownLatch blockedThreadLatch, CountDownLatch delayedStartLatch, boolean blockForLimitedTime) {
            super((elem, ctx) -> Integer.parseInt(elem), context, 3, maxInFlightRequests, 20, 100, 100, 100, false, 0, Collections.emptyList());
            this.blockedThreadLatch = blockedThreadLatch;
            this.delayedStartLatch = delayedStartLatch;
            this.blockForLimitedTime = blockForLimitedTime;
        }

        @Override
        protected void submitRequestEntries(List<Integer> requestEntries, ResultHandler<Integer> resultHandler) {
            if (requestEntries.size() == 3) {
                try {
                    delayedStartLatch.countDown();
                    if (blockForLimitedTime) {
                        assertThat(blockedThreadLatch.await(500, TimeUnit.MILLISECONDS)).as("The countdown latch was released before the full amount" + "of time was reached.").isFalse();
                    } else {
                        blockedThreadLatch.await();
                    }
                } catch (InterruptedException e) {
                    fail("The unit test latch must not have been interrupted by another thread.");
                }
            }
            res.addAll(requestEntries);
            resultHandler.complete();
        }
    }

    @Test
    void testMetricsGroupHasLoggedNumberOfRecordsAndNumberOfBytesCorrectly_1() throws IOException, InterruptedException {
        assertThat(sinkInitContext.getNumRecordsOutCounter().getCount()).isEqualTo(80);
    }

    @Test
    void testMetricsGroupHasLoggedNumberOfRecordsAndNumberOfBytesCorrectly_2() throws IOException, InterruptedException {
        assertThat(sinkInitContext.getNumBytesOutCounter().getCount()).isEqualTo(320);
    }

    @Test
    void testMetricsGroupHasLoggedNumberOfRecordsAndNumberOfBytesCorrectly_3() throws IOException, InterruptedException {
        assertThat(sinkInitContext.getCurrentSendTimeGauge().get().getValue()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void testMetricsGroupHasLoggedNumberOfRecordsAndNumberOfBytesCorrectly_4() throws IOException, InterruptedException {
        assertThat(sinkInitContext.getCurrentSendTimeGauge().get().getValue()).isLessThan(1000);
    }

    @Test
    void testThatSnapshotsAreTakenOfBufferCorrectlyBeforeAndAfterAutomaticFlush_1_testMerged_1() throws IOException, InterruptedException {
        AsyncSinkWriterImpl sink = new AsyncSinkWriterImplBuilder().context(sinkInitContext).maxBatchSize(3).build();
        sink.write("25");
        sink.write("55");
        assertThatBufferStatesAreEqual(sink.wrapRequests(25, 55), getWriterState(sink));
        sink.write("75");
        assertThatBufferStatesAreEqual(BufferedRequestState.emptyState(), getWriterState(sink));
    }

    @Test
    void testThatSnapshotsAreTakenOfBufferCorrectlyBeforeAndAfterAutomaticFlush_2() throws IOException, InterruptedException {
        assertThat(res.size()).isEqualTo(0);
    }

    @Test
    void testThatSnapshotsAreTakenOfBufferCorrectlyBeforeAndAfterAutomaticFlush_4() throws IOException, InterruptedException {
        assertThat(res.size()).isEqualTo(3);
    }

    @Test
    void metricsAreLoggedEachTimeSubmitRequestEntriesIsCalled_1() throws IOException, InterruptedException {
        assertThat(sinkInitContext.getNumRecordsOutCounter().getCount()).isEqualTo(5);
    }

    @Test
    void metricsAreLoggedEachTimeSubmitRequestEntriesIsCalled_2() throws IOException, InterruptedException {
        assertThat(sinkInitContext.getNumBytesOutCounter().getCount()).isEqualTo(20);
    }

    @Test
    void testThatIntermittentlyFailingEntriesAreEnqueuedOnToTheBufferWithCorrectSize_1() throws IOException, InterruptedException {
        assertThat(res.size()).isEqualTo(2);
    }

    @Test
    void testThatIntermittentlyFailingEntriesAreEnqueuedOnToTheBufferWithCorrectSize_2() throws IOException, InterruptedException {
        assertThat(res).isEqualTo(Arrays.asList(1, 2, 225, 3, 4));
    }

    @Test
    void testThatIntermittentlyFailingEntriesAreEnqueuedOnToTheBufferWithCorrectOrder_1() throws IOException, InterruptedException {
        assertThat(res.size()).isEqualTo(2);
    }

    @Test
    void testThatIntermittentlyFailingEntriesAreEnqueuedOnToTheBufferWithCorrectOrder_2() throws IOException, InterruptedException {
        assertThat(res).isEqualTo(Arrays.asList(1, 2, 228, 225, 3, 4));
    }

    @Test
    void prepareCommitFlushesInflightElementsAndDoesNotFlushIfFlushIsSetToFalse_1() throws Exception {
        assertThat(res).isEqualTo(Arrays.asList(0, 1));
    }

    @Test
    void prepareCommitFlushesInflightElementsAndDoesNotFlushIfFlushIsSetToFalse_2_testMerged_2() throws Exception {
        AsyncSinkWriterImpl sink = new AsyncSinkWriterImplBuilder().context(sinkInitContext).maxBatchSize(8).maxBufferedRequests(10).simulateFailures(true).build();
        sink.write(String.valueOf(225));
        sink.write(String.valueOf(0));
        sink.write(String.valueOf(1));
        sink.write(String.valueOf(2));
        assertThatBufferStatesAreEqual(sink.wrapRequests(2), getWriterState(sink));
        sink.flush(false);
        assertThatBufferStatesAreEqual(sink.wrapRequests(225, 2), getWriterState(sink));
    }

    @Test
    void prepareCommitFlushesInflightElementsAndDoesNotFlushIfFlushIsSetToFalse_3() throws Exception {
        assertThat(res).isEqualTo(Arrays.asList(0, 1));
    }

    @Test
    void prepareCommitFlushesInflightElementsAndDoesNotFlushIfFlushIsSetToFalse_5() throws Exception {
        assertThat(res).isEqualTo(Arrays.asList(0, 1, 225, 2));
    }

    @Test
    void testWriterInitializedWithStateHasCallbackRegistered_1() throws Exception {
        assertThat(res.size()).isEqualTo(0);
    }

    @Test
    void testWriterInitializedWithStateHasCallbackRegistered_2() throws Exception {
        assertThat(res.size()).isEqualTo(4);
    }

    @Test
    void testThatOneAndOnlyOneCallbackIsEverRegistered_1() throws Exception {
        assertThat(res.size()).isEqualTo(0);
    }

    @Test
    void testThatOneAndOnlyOneCallbackIsEverRegistered_2() throws Exception {
        assertThat(res.size()).isEqualTo(1);
    }

    @Test
    void testThatOneAndOnlyOneCallbackIsEverRegistered_3() throws Exception {
        assertThat(res.size()).isEqualTo(1);
    }

    @Test
    void testThatOneAndOnlyOneCallbackIsEverRegistered_4() throws Exception {
        assertThat(res.size()).isEqualTo(2);
    }

    @Test
    void testThatOneAndOnlyOneCallbackIsEverRegistered_5() throws Exception {
        assertThat(res.size()).isEqualTo(2);
    }

    @Test
    void testThatOneAndOnlyOneCallbackIsEverRegistered_6() throws Exception {
        assertThat(res.size()).isEqualTo(3);
    }

    @Test
    void testThatIntermittentlyFailingEntriesShouldBeFlushedWithMainBatchInTimeBasedFlush_1() throws Exception {
        assertThat(res.size()).isEqualTo(2);
    }

    @Test
    void testThatIntermittentlyFailingEntriesShouldBeFlushedWithMainBatchInTimeBasedFlush_2() throws Exception {
        assertThat(res.size()).isEqualTo(2);
    }

    @Test
    void testThatIntermittentlyFailingEntriesShouldBeFlushedWithMainBatchInTimeBasedFlush_3() throws Exception {
        assertThat(res.size()).isEqualTo(5);
    }

    @Test
    void testThatOnExpiryOfAnOldTimeoutANewOneMayBeRegisteredImmediately_1() throws Exception {
        assertThat(res.size()).isEqualTo(1);
    }

    @Test
    void testThatOnExpiryOfAnOldTimeoutANewOneMayBeRegisteredImmediately_2() throws Exception {
        assertThat(res.size()).isEqualTo(2);
    }
}
