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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MSQFaultSerdeTest_Parameterized {

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
    public void testFaultSerde_7() throws IOException {
        assertFaultSerde(new ColumnTypeNotSupportedFault("the column", ColumnType.STRING_ARRAY));
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
    public void testFaultSerde_17() throws IOException {
        assertFaultSerde(new NotEnoughMemoryFault(1234, 1000, 1000, 900, 1, 2, 2));
    }

    @Test
    public void testFaultSerde_18() throws IOException {
        assertFaultSerde(QueryNotSupportedFault.INSTANCE);
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
    public void testFaultSerde_35() throws IOException {
        assertFaultSerde(UnknownFault.forMessage(null));
    }

    @Test
    public void testFaultSerde_36() throws IOException {
        assertFaultSerde(UnknownFault.forMessage("the message"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFaultSerde_1_6_20_37_39")
    public void testFaultSerde_1_6_20_37_39(int param1) throws IOException {
        assertFaultSerde(new BroadcastTablesTooLargeFault(param1, null));
    }

    static public Stream<Arguments> Provider_testFaultSerde_1_6_20_37_39() {
        return Stream.of(arguments(10), arguments("the column"), arguments("new error"), arguments("the worker task"), arguments("the worker task"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFaultSerde_5_8_11_21_23_27")
    public void testFaultSerde_5_8_11_21_23_27(String param1) throws IOException {
        assertFaultSerde(new CannotParseExternalDataFault(param1));
    }

    static public Stream<Arguments> Provider_testFaultSerde_5_8_11_21_23_27() {
        return Stream.of(arguments("the message"), arguments("the column"), arguments("the datasource"), arguments(1000), arguments(10), arguments(10));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFaultSerde_15to16")
    public void testFaultSerde_15to16(String param1, int param2, String param3, String param4, int param5) throws IOException {
        assertFaultSerde(new InvalidNullByteFault(param1, param2, param3, param4, param5));
    }

    static public Stream<Arguments> Provider_testFaultSerde_15to16() {
        return Stream.of(arguments("the source", 1, "the column", "the value", 2), arguments("the source", "the column", 1, "the error", "the log msg"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFaultSerde_19_24_28_31to32_38_40to41")
    public void testFaultSerde_19_24_28_31to32_38_40to41(String param1, String param2) throws IOException {
        assertFaultSerde(new QueryRuntimeFault(param1, param2));
    }

    static public Stream<Arguments> Provider_testFaultSerde_19_24_28_31to32_38_40to41() {
        return Stream.of(arguments("new error", "base error"), arguments(10, 8), arguments(10, 20), arguments(10, "the error"), arguments(10, 5), arguments("the worker task", "the error msg"), arguments("the worker task", "the error msg"), arguments(250, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFaultSerde_22_25to26")
    public void testFaultSerde_22_25to26(int param1, int param2, int param3) throws IOException {
        assertFaultSerde(new TaskStartTimeoutFault(param1, param2, param3));
    }

    static public Stream<Arguments> Provider_testFaultSerde_22_25to26() {
        return Stream.of(arguments(1, 10, 11), arguments(10, 8, 1), arguments(15, 10, 5));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFaultSerde_33to34")
    public void testFaultSerde_33to34(int param1, String param2, int param3, String param4) throws IOException {
        assertFaultSerde(new TooManyAttemptsForWorker(param1, param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testFaultSerde_33to34() {
        return Stream.of(arguments(2, "taskId", 1, "rootError"), arguments(2, 2, "taskId", "rootError"));
    }
}
