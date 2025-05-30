package org.apache.druid.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.java.util.common.ISE;
import org.apache.druid.java.util.common.UOE;
import org.apache.druid.segment.TestHelper;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.CancellationException;

public class QueryInterruptedExceptionTest_Purified {

    private static final ObjectMapper MAPPER = TestHelper.makeJsonMapper();

    private static QueryInterruptedException roundTrip(final QueryInterruptedException e) {
        try {
            return MAPPER.readValue(MAPPER.writeValueAsBytes(e), QueryInterruptedException.class);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    @Test
    public void testErrorCode_1() {
        Assert.assertEquals("Query cancelled", new QueryInterruptedException(new QueryInterruptedException(new CancellationException())).getErrorCode());
    }

    @Test
    public void testErrorCode_2() {
        Assert.assertEquals("Query cancelled", new QueryInterruptedException(new CancellationException()).getErrorCode());
    }

    @Test
    public void testErrorCode_3() {
        Assert.assertEquals("Query interrupted", new QueryInterruptedException(new InterruptedException()).getErrorCode());
    }

    @Test
    public void testErrorCode_4() {
        Assert.assertEquals("Unsupported operation", new QueryInterruptedException(new UOE("Unsupported")).getErrorCode());
    }

    @Test
    public void testErrorCode_5() {
        Assert.assertEquals("Unknown exception", new QueryInterruptedException(null).getErrorCode());
    }

    @Test
    public void testErrorCode_6() {
        Assert.assertEquals("Unknown exception", new QueryInterruptedException(new ISE("Something bad!")).getErrorCode());
    }

    @Test
    public void testErrorCode_7() {
        Assert.assertEquals("Unknown exception", new QueryInterruptedException(new QueryInterruptedException(new ISE("Something bad!"))).getErrorCode());
    }

    @Test
    public void testErrorMessage_1() {
        Assert.assertEquals(null, new QueryInterruptedException(new QueryInterruptedException(new CancellationException())).getMessage());
    }

    @Test
    public void testErrorMessage_2() {
        Assert.assertEquals(null, new QueryInterruptedException(new CancellationException()).getMessage());
    }

    @Test
    public void testErrorMessage_3() {
        Assert.assertEquals(null, new QueryInterruptedException(new InterruptedException()).getMessage());
    }

    @Test
    public void testErrorMessage_4() {
        Assert.assertEquals(null, new QueryInterruptedException(null).getMessage());
    }

    @Test
    public void testErrorMessage_5() {
        Assert.assertEquals("Something bad!", new QueryInterruptedException(new ISE("Something bad!")).getMessage());
    }

    @Test
    public void testErrorMessage_6() {
        Assert.assertEquals("Something bad!", new QueryInterruptedException(new QueryInterruptedException(new ISE("Something bad!"))).getMessage());
    }

    @Test
    public void testErrorClass_1() {
        Assert.assertEquals("java.util.concurrent.CancellationException", new QueryInterruptedException(new QueryInterruptedException(new CancellationException())).getErrorClass());
    }

    @Test
    public void testErrorClass_2() {
        Assert.assertEquals("java.util.concurrent.CancellationException", new QueryInterruptedException(new CancellationException()).getErrorClass());
    }

    @Test
    public void testErrorClass_3() {
        Assert.assertEquals("java.lang.InterruptedException", new QueryInterruptedException(new InterruptedException()).getErrorClass());
    }

    @Test
    public void testErrorClass_4() {
        Assert.assertEquals(null, new QueryInterruptedException(null).getErrorClass());
    }

    @Test
    public void testErrorClass_5() {
        Assert.assertEquals("org.apache.druid.java.util.common.ISE", new QueryInterruptedException(new ISE("Something bad!")).getErrorClass());
    }

    @Test
    public void testErrorClass_6() {
        Assert.assertEquals("org.apache.druid.java.util.common.ISE", new QueryInterruptedException(new QueryInterruptedException(new ISE("Something bad!"))).getErrorClass());
    }

    @Test
    public void testSerde_1() {
        Assert.assertEquals("Query cancelled", roundTrip(new QueryInterruptedException(new QueryInterruptedException(new CancellationException()))).getErrorCode());
    }

    @Test
    public void testSerde_2() {
        Assert.assertEquals("java.util.concurrent.CancellationException", roundTrip(new QueryInterruptedException(new QueryInterruptedException(new CancellationException()))).getErrorClass());
    }

    @Test
    public void testSerde_3() {
        Assert.assertEquals(null, roundTrip(new QueryInterruptedException(new QueryInterruptedException(new CancellationException()))).getMessage());
    }

    @Test
    public void testSerde_4() {
        Assert.assertEquals("java.util.concurrent.CancellationException", roundTrip(new QueryInterruptedException(new CancellationException())).getErrorClass());
    }

    @Test
    public void testSerde_5() {
        Assert.assertEquals("java.lang.InterruptedException", roundTrip(new QueryInterruptedException(new InterruptedException())).getErrorClass());
    }

    @Test
    public void testSerde_6() {
        Assert.assertEquals(null, roundTrip(new QueryInterruptedException(null)).getErrorClass());
    }

    @Test
    public void testSerde_7() {
        Assert.assertEquals("org.apache.druid.java.util.common.ISE", roundTrip(new QueryInterruptedException(new ISE("Something bad!"))).getErrorClass());
    }

    @Test
    public void testSerde_8() {
        Assert.assertEquals("org.apache.druid.java.util.common.ISE", roundTrip(new QueryInterruptedException(new QueryInterruptedException(new ISE("Something bad!")))).getErrorClass());
    }

    @Test
    public void testSerde_9() {
        Assert.assertEquals("Something bad!", roundTrip(new QueryInterruptedException(new ISE("Something bad!"))).getMessage());
    }

    @Test
    public void testSerde_10() {
        Assert.assertEquals("Something bad!", roundTrip(new QueryInterruptedException(new QueryInterruptedException(new ISE("Something bad!")))).getMessage());
    }

    @Test
    public void testSerde_11() {
        Assert.assertEquals("Unknown exception", roundTrip(new QueryInterruptedException(new ISE("Something bad!"))).getErrorCode());
    }

    @Test
    public void testSerde_12() {
        Assert.assertEquals("Unknown exception", roundTrip(new QueryInterruptedException(new QueryInterruptedException(new ISE("Something bad!")))).getErrorCode());
    }
}
