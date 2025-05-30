package org.apache.druid.msq.util;

import org.apache.druid.msq.indexing.error.MSQFaultUtils;
import org.apache.druid.msq.indexing.error.UnknownFault;
import org.apache.druid.msq.indexing.error.WorkerFailedFault;
import org.junit.Assert;
import org.junit.Test;

public class MSQFaultUtilsTest_Purified {

    @Test
    public void testGetErrorCodeFromMessage_1() {
        Assert.assertEquals(UnknownFault.CODE, MSQFaultUtils.getErrorCodeFromMessage("Task execution process exited unsuccessfully with code[137]. See middleManager logs for more details..."));
    }

    @Test
    public void testGetErrorCodeFromMessage_2() {
        Assert.assertEquals(UnknownFault.CODE, MSQFaultUtils.getErrorCodeFromMessage(""));
    }

    @Test
    public void testGetErrorCodeFromMessage_3() {
        Assert.assertEquals(UnknownFault.CODE, MSQFaultUtils.getErrorCodeFromMessage(null));
    }

    @Test
    public void testGetErrorCodeFromMessage_4() {
        Assert.assertEquals("ABC", MSQFaultUtils.getErrorCodeFromMessage("ABC: xyz xyz : xyz"));
    }

    @Test
    public void testGetErrorCodeFromMessage_5() {
        Assert.assertEquals(WorkerFailedFault.CODE, MSQFaultUtils.getErrorCodeFromMessage(MSQFaultUtils.generateMessageWithErrorCode(new WorkerFailedFault("123", "error"))));
    }
}
