package org.apache.druid.msq.indexing.error;

import org.apache.druid.frame.processor.FrameRowTooLargeException;
import org.apache.druid.frame.write.UnsupportedColumnTypeException;
import org.apache.druid.indexing.common.task.batch.TooManyBucketsException;
import org.apache.druid.java.util.common.parsers.ParseException;
import org.apache.druid.query.QueryTimeoutException;
import org.apache.druid.query.groupby.epinephelinae.UnexpectedMultiValueDimensionException;
import org.junit.Assert;
import org.junit.Test;

public class MSQErrorReportTest_Purified {

    public static final String ERROR_MESSAGE = "test";

    @Test
    public void testErrorReportFault_1() {
        Assert.assertEquals(UnknownFault.forException(null), MSQErrorReport.getFaultFromException(null));
    }

    @Test
    public void testErrorReportFault_2_testMerged_2() {
        MSQException msqException = new MSQException(null, UnknownFault.forMessage(ERROR_MESSAGE));
        Assert.assertEquals(msqException.getFault(), MSQErrorReport.getFaultFromException(msqException));
        ParseException parseException = new ParseException(null, ERROR_MESSAGE);
        Assert.assertEquals(new CannotParseExternalDataFault(ERROR_MESSAGE), MSQErrorReport.getFaultFromException(parseException));
        UnsupportedColumnTypeException columnTypeException = new UnsupportedColumnTypeException(ERROR_MESSAGE, null);
        Assert.assertEquals(new ColumnTypeNotSupportedFault(ERROR_MESSAGE, null), MSQErrorReport.getFaultFromException(columnTypeException));
        UnexpectedMultiValueDimensionException mvException = new UnexpectedMultiValueDimensionException(ERROR_MESSAGE);
        Assert.assertEquals(QueryRuntimeFault.CODE, MSQErrorReport.getFaultFromException(mvException).getErrorCode());
        QueryTimeoutException queryException = new QueryTimeoutException(ERROR_MESSAGE);
        Assert.assertEquals(new QueryRuntimeFault(ERROR_MESSAGE, null), MSQErrorReport.getFaultFromException(queryException));
        RuntimeException runtimeException = new RuntimeException(ERROR_MESSAGE);
        Assert.assertEquals(UnknownFault.forException(runtimeException), MSQErrorReport.getFaultFromException(runtimeException));
    }

    @Test
    public void testErrorReportFault_5() {
        TooManyBucketsException tooManyBucketsException = new TooManyBucketsException(10);
        Assert.assertEquals(new TooManyBucketsFault(10), MSQErrorReport.getFaultFromException(tooManyBucketsException));
    }

    @Test
    public void testErrorReportFault_6() {
        FrameRowTooLargeException tooLargeException = new FrameRowTooLargeException(10);
        Assert.assertEquals(new RowTooLargeFault(10), MSQErrorReport.getFaultFromException(tooLargeException));
    }
}
