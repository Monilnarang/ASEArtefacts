package org.apache.druid.msq.util;

import org.apache.druid.msq.indexing.error.MSQFaultUtils;
import org.apache.druid.msq.indexing.error.UnknownFault;
import org.apache.druid.msq.indexing.error.WorkerFailedFault;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MSQFaultUtilsTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testGetErrorCodeFromMessage_1to2")
    public void testGetErrorCodeFromMessage_1to2(String param1) {
        Assert.assertEquals(UnknownFault.CODE, MSQFaultUtils.getErrorCodeFromMessage(param1));
    }

    static public Stream<Arguments> Provider_testGetErrorCodeFromMessage_1to2() {
        return Stream.of(arguments("Task execution process exited unsuccessfully with code[137]. See middleManager logs for more details..."), arguments(""));
    }
}
