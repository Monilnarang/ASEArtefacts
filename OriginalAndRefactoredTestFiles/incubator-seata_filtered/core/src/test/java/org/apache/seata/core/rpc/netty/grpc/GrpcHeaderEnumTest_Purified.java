package org.apache.seata.core.rpc.netty.grpc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrpcHeaderEnumTest_Purified {

    @Test
    public void testHeaderValues_1() {
        assertEquals("grpc-status", GrpcHeaderEnum.GRPC_STATUS.header);
    }

    @Test
    public void testHeaderValues_2() {
        assertEquals(":status", GrpcHeaderEnum.HTTP2_STATUS.header);
    }

    @Test
    public void testHeaderValues_3() {
        assertEquals("content-type", GrpcHeaderEnum.GRPC_CONTENT_TYPE.header);
    }

    @Test
    public void testHeaderValues_4() {
        assertEquals("codec-type", GrpcHeaderEnum.CODEC_TYPE.header);
    }

    @Test
    public void testHeaderValues_5() {
        assertEquals("compress-type", GrpcHeaderEnum.COMPRESS_TYPE.header);
    }
}
