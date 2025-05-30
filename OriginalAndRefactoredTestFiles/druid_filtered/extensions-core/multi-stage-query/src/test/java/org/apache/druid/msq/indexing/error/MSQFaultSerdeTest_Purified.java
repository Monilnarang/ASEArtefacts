package org.apache.druid.msq.indexing.error;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.msq.guice.MSQIndexingModule;
import org.apache.druid.query.JoinAlgorithm;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.column.ColumnType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class MSQFaultSerdeTest_Purified {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = TestHelper.makeJsonMapper();
        objectMapper.registerModules(new MSQIndexingModule().getJacksonModules());
        objectMapper.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
    }

    private void assertFaultSerde(final MSQFault fault) throws IOException {
        final String json = objectMapper.writeValueAsString(fault);
        final MSQFault fault2 = objectMapper.readValue(json, MSQFault.class);
        Assert.assertEquals(json, fault, fault2);
    }

    @Test
    public void testFaultSerde_1() throws IOException {
        assertFaultSerde(new BroadcastTablesTooLargeFault(10, null));
    }

    @Test
    public void testFaultSerde_2() throws IOException {
        assertFaultSerde(new BroadcastTablesTooLargeFault(10, JoinAlgorithm.SORT_MERGE));
    }

    @Test
    public void testFaultSerde_3() throws IOException {
        assertFaultSerde(CanceledFault.shutdown());
    }

    @Test
    public void testFaultSerde_4() throws IOException {
        assertFaultSerde(CanceledFault.timeout());
    }

    @Test
    public void testFaultSerde_5() throws IOException {
        assertFaultSerde(new CannotParseExternalDataFault("the message"));
    }

    @Test
    public void testFaultSerde_6() throws IOException {
        assertFaultSerde(new ColumnTypeNotSupportedFault("the column", null));
    }

    @Test
    public void testFaultSerde_7() throws IOException {
        assertFaultSerde(new ColumnTypeNotSupportedFault("the column", ColumnType.STRING_ARRAY));
    }

    @Test
    public void testFaultSerde_8() throws IOException {
        assertFaultSerde(new ColumnNameRestrictedFault("the column"));
    }

    @Test
    public void testFaultSerde_9() throws IOException {
        assertFaultSerde(new InsertCannotAllocateSegmentFault("the datasource", Intervals.ETERNITY, null));
    }

    @Test
    public void testFaultSerde_10() throws IOException {
        assertFaultSerde(new InsertCannotAllocateSegmentFault("the datasource", Intervals.of("2000-01-01/2002-01-01"), Intervals.ETERNITY));
    }

    @Test
    public void testFaultSerde_11() throws IOException {
        assertFaultSerde(new InsertCannotBeEmptyFault("the datasource"));
    }

    @Test
    public void testFaultSerde_12() throws IOException {
        assertFaultSerde(InsertLockPreemptedFault.INSTANCE);
    }

    @Test
    public void testFaultSerde_13() throws IOException {
        assertFaultSerde(InsertTimeNullFault.INSTANCE);
    }

    @Test
    public void testFaultSerde_14() throws IOException {
        assertFaultSerde(new InsertTimeOutOfBoundsFault(Intervals.of("2001/2002"), Collections.singletonList(Intervals.of("2000/2001"))));
    }

    @Test
    public void testFaultSerde_15() throws IOException {
        assertFaultSerde(new InvalidNullByteFault("the source", 1, "the column", "the value", 2));
    }

    @Test
    public void testFaultSerde_16() throws IOException {
        assertFaultSerde(new InvalidFieldFault("the source", "the column", 1, "the error", "the log msg"));
    }

    @Test
    public void testFaultSerde_17() throws IOException {
        assertFaultSerde(new NotEnoughMemoryFault(1234, 1000, 1000, 900, 1, 2, 2));
    }

    @Test
    public void testFaultSerde_18() throws IOException {
        assertFaultSerde(QueryNotSupportedFault.INSTANCE);
    }

    @Test
    public void testFaultSerde_19() throws IOException {
        assertFaultSerde(new QueryRuntimeFault("new error", "base error"));
    }

    @Test
    public void testFaultSerde_20() throws IOException {
        assertFaultSerde(new QueryRuntimeFault("new error", null));
    }

    @Test
    public void testFaultSerde_21() throws IOException {
        assertFaultSerde(new RowTooLargeFault(1000));
    }

    @Test
    public void testFaultSerde_22() throws IOException {
        assertFaultSerde(new TaskStartTimeoutFault(1, 10, 11));
    }

    @Test
    public void testFaultSerde_23() throws IOException {
        assertFaultSerde(new TooManyBucketsFault(10));
    }

    @Test
    public void testFaultSerde_24() throws IOException {
        assertFaultSerde(new TooManyColumnsFault(10, 8));
    }

    @Test
    public void testFaultSerde_25() throws IOException {
        assertFaultSerde(new TooManyClusteredByColumnsFault(10, 8, 1));
    }

    @Test
    public void testFaultSerde_26() throws IOException {
        assertFaultSerde(new TooManyInputFilesFault(15, 10, 5));
    }

    @Test
    public void testFaultSerde_27() throws IOException {
        assertFaultSerde(new TooManyPartitionsFault(10));
    }

    @Test
    public void testFaultSerde_28() throws IOException {
        assertFaultSerde(new TooManyRowsInAWindowFault(10, 20));
    }

    @Test
    public void testFaultSerde_29() throws IOException {
        assertFaultSerde(new TooManyRowsWithSameKeyFault(Arrays.asList("foo", 123), 1, 2));
    }

    @Test
    public void testFaultSerde_30() throws IOException {
        assertFaultSerde(new TooManySegmentsInTimeChunkFault(DateTimes.nowUtc(), 10, 1, Granularities.ALL));
    }

    @Test
    public void testFaultSerde_31() throws IOException {
        assertFaultSerde(new TooManyWarningsFault(10, "the error"));
    }

    @Test
    public void testFaultSerde_32() throws IOException {
        assertFaultSerde(new TooManyWorkersFault(10, 5));
    }

    @Test
    public void testFaultSerde_33() throws IOException {
        assertFaultSerde(new TooManyAttemptsForWorker(2, "taskId", 1, "rootError"));
    }

    @Test
    public void testFaultSerde_34() throws IOException {
        assertFaultSerde(new TooManyAttemptsForJob(2, 2, "taskId", "rootError"));
    }

    @Test
    public void testFaultSerde_35() throws IOException {
        assertFaultSerde(UnknownFault.forMessage(null));
    }

    @Test
    public void testFaultSerde_36() throws IOException {
        assertFaultSerde(UnknownFault.forMessage("the message"));
    }

    @Test
    public void testFaultSerde_37() throws IOException {
        assertFaultSerde(new WorkerFailedFault("the worker task", null));
    }

    @Test
    public void testFaultSerde_38() throws IOException {
        assertFaultSerde(new WorkerFailedFault("the worker task", "the error msg"));
    }

    @Test
    public void testFaultSerde_39() throws IOException {
        assertFaultSerde(new WorkerRpcFailedFault("the worker task", null));
    }

    @Test
    public void testFaultSerde_40() throws IOException {
        assertFaultSerde(new WorkerRpcFailedFault("the worker task", "the error msg"));
    }

    @Test
    public void testFaultSerde_41() throws IOException {
        assertFaultSerde(new NotEnoughTemporaryStorageFault(250, 2));
    }
}
