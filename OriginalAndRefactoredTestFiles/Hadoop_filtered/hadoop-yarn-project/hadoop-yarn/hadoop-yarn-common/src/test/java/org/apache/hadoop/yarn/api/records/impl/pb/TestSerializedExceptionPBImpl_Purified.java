package org.apache.hadoop.yarn.api.records.impl.pb;

import java.nio.channels.ClosedChannelException;
import org.junit.jupiter.api.Test;
import org.apache.hadoop.yarn.exceptions.YarnRuntimeException;
import org.apache.hadoop.yarn.proto.YarnProtos.SerializedExceptionProto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class TestSerializedExceptionPBImpl_Purified {

    @Test
    void testBeforeInit_1() throws Exception {
        SerializedExceptionPBImpl pb1 = new SerializedExceptionPBImpl();
        assertNull(pb1.getCause());
    }

    @Test
    void testBeforeInit_2_testMerged_2() throws Exception {
        SerializedExceptionProto defaultProto = SerializedExceptionProto.newBuilder().build();
        SerializedExceptionPBImpl pb2 = new SerializedExceptionPBImpl();
        assertEquals(defaultProto, pb2.getProto());
        SerializedExceptionPBImpl pb3 = new SerializedExceptionPBImpl();
        assertEquals(defaultProto.getTrace(), pb3.getRemoteTrace());
    }
}
