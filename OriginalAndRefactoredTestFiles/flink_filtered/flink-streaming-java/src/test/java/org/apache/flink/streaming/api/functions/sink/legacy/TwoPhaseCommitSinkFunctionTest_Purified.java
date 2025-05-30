package org.apache.flink.streaming.api.functions.sink.legacy;

import org.apache.flink.api.common.serialization.SerializerConfigImpl;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.api.common.typeutils.base.VoidSerializer;
import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer;
import org.apache.flink.runtime.checkpoint.OperatorSubtaskState;
import org.apache.flink.streaming.api.operators.StreamSink;
import org.apache.flink.streaming.util.ContentDump;
import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
import org.apache.flink.testutils.logging.LoggerAuditingExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.event.Level;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TwoPhaseCommitSinkFunctionTest_Purified {

    private ContentDumpSinkFunction sinkFunction;

    private OneInputStreamOperatorTestHarness<String, Object> harness;

    private AtomicBoolean throwException = new AtomicBoolean();

    private ContentDump targetDirectory;

    private ContentDump tmpDirectory;

    private SettableClock clock;

    @RegisterExtension
    private LoggerAuditingExtension testLoggerResource = new LoggerAuditingExtension(TwoPhaseCommitSinkFunction.class, Level.WARN);

    @BeforeEach
    void setUp() throws Exception {
        targetDirectory = new ContentDump();
        tmpDirectory = new ContentDump();
        clock = new SettableClock();
        setUpTestHarness();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeTestHarness();
    }

    private void setUpTestHarness() throws Exception {
        sinkFunction = new ContentDumpSinkFunction();
        harness = new OneInputStreamOperatorTestHarness<>(new StreamSink<>(sinkFunction), StringSerializer.INSTANCE);
        harness.setup();
    }

    private void closeTestHarness() throws Exception {
        harness.close();
    }

    private void assertExactlyOnce(List<String> expectedValues) {
        ArrayList<String> actualValues = new ArrayList<>();
        for (String name : targetDirectory.listFiles()) {
            actualValues.addAll(targetDirectory.read(name));
        }
        Collections.sort(actualValues);
        Collections.sort(expectedValues);
        assertThat(actualValues).isEqualTo(expectedValues);
    }

    private class ContentDumpSinkFunction extends TwoPhaseCommitSinkFunction<String, ContentTransaction, Void> {

        final List<ContentTransaction> abortedTransactions = new ArrayList<>();

        public ContentDumpSinkFunction() {
            super(new ContentTransactionSerializer(), VoidSerializer.INSTANCE, clock);
        }

        @Override
        protected void invoke(ContentTransaction transaction, String value, Context context) throws Exception {
            transaction.tmpContentWriter.write(value);
        }

        @Override
        protected ContentTransaction beginTransaction() throws Exception {
            return new ContentTransaction(tmpDirectory.createWriter(UUID.randomUUID().toString()));
        }

        @Override
        protected void preCommit(ContentTransaction transaction) throws Exception {
            transaction.tmpContentWriter.flush();
            transaction.tmpContentWriter.close();
        }

        @Override
        protected void commit(ContentTransaction transaction) {
            if (throwException.get()) {
                throw new RuntimeException("Expected exception");
            }
            ContentDump.move(transaction.tmpContentWriter.getName(), tmpDirectory, targetDirectory);
        }

        @Override
        protected void abort(ContentTransaction transaction) {
            abortedTransactions.add(transaction);
            transaction.tmpContentWriter.close();
            tmpDirectory.delete(transaction.tmpContentWriter.getName());
        }
    }

    private static class ContentTransaction {

        private ContentDump.ContentWriter tmpContentWriter;

        public ContentTransaction(ContentDump.ContentWriter tmpContentWriter) {
            this.tmpContentWriter = tmpContentWriter;
        }

        @Override
        public String toString() {
            return String.format("ContentTransaction[%s]", tmpContentWriter.getName());
        }
    }

    private static class ContentTransactionSerializer extends KryoSerializer<ContentTransaction> {

        public ContentTransactionSerializer() {
            super(ContentTransaction.class, new SerializerConfigImpl());
        }

        @Override
        public KryoSerializer<ContentTransaction> duplicate() {
            return this;
        }

        @Override
        public String toString() {
            return "ContentTransactionSerializer";
        }
    }

    private static class SettableClock extends Clock {

        private final ZoneId zoneId;

        private long epochMilli;

        private SettableClock() {
            this.zoneId = ZoneOffset.UTC;
        }

        public SettableClock(ZoneId zoneId, long epochMilli) {
            this.zoneId = zoneId;
            this.epochMilli = epochMilli;
        }

        public void setEpochMilli(long epochMilli) {
            this.epochMilli = epochMilli;
        }

        @Override
        public ZoneId getZone() {
            return zoneId;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            if (zone.equals(this.zoneId)) {
                return this;
            }
            return new SettableClock(zone, epochMilli);
        }

        @Override
        public Instant instant() {
            return Instant.ofEpochMilli(epochMilli);
        }
    }

    @Test
    void testSubsumedNotificationOfPreviousCheckpoint_1() throws Exception {
        assertExactlyOnce(Arrays.asList("42", "43", "44"));
    }

    @Test
    void testSubsumedNotificationOfPreviousCheckpoint_2() throws Exception {
        assertThat(tmpDirectory.listFiles()).hasSize(1);
    }

    @Test
    void testNotifyOfCompletedCheckpoint_1() throws Exception {
        assertExactlyOnce(Arrays.asList("42", "43"));
    }

    @Test
    void testNotifyOfCompletedCheckpoint_2() throws Exception {
        assertThat(tmpDirectory.listFiles()).hasSize(2);
    }
}
