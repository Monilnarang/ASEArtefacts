package org.apache.dubbo.rpc;

import org.apache.dubbo.remoting.TimeoutException;
import org.apache.dubbo.rpc.TriRpcStatus.Code;
import java.io.Serializable;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.rpc.RpcException.FORBIDDEN_EXCEPTION;
import static org.apache.dubbo.rpc.RpcException.METHOD_NOT_FOUND;
import static org.apache.dubbo.rpc.RpcException.TIMEOUT_EXCEPTION;
import static org.apache.dubbo.rpc.RpcException.UNKNOWN_EXCEPTION;
import static org.junit.jupiter.api.Assertions.fail;

class TriRpcStatusTest_Purified {

    @Test
    void testGetStatus_1() {
        StatusRpcException rpcException = new StatusRpcException(TriRpcStatus.INTERNAL);
        Assertions.assertEquals(TriRpcStatus.INTERNAL.code, TriRpcStatus.getStatus(rpcException, null).code);
    }

    @Test
    void testGetStatus_2() {
        Assertions.assertEquals(TriRpcStatus.DEADLINE_EXCEEDED.code, TriRpcStatus.getStatus(new RpcException(RpcException.TIMEOUT_EXCEPTION), null).code);
    }

    @Test
    void testGetStatus_3() {
        Assertions.assertEquals(TriRpcStatus.DEADLINE_EXCEEDED.code, TriRpcStatus.getStatus(new TimeoutException(true, null, null), null).code);
    }

    @Test
    void decodeMessage_1() {
        String message = "😯";
        Assertions.assertEquals(message, TriRpcStatus.decodeMessage(TriRpcStatus.encodeMessage(message)));
    }

    @Test
    void decodeMessage_2() {
        Assertions.assertTrue(TriRpcStatus.decodeMessage("").isEmpty());
    }

    @Test
    void decodeMessage_3() {
        Assertions.assertTrue(TriRpcStatus.decodeMessage(null).isEmpty());
    }

    @Test
    void httpStatusToGrpcCode_1() {
        Assertions.assertEquals(Code.UNIMPLEMENTED, TriRpcStatus.httpStatusToGrpcCode(404));
    }

    @Test
    void httpStatusToGrpcCode_2() {
        Assertions.assertEquals(Code.UNAVAILABLE, TriRpcStatus.httpStatusToGrpcCode(HttpResponseStatus.BAD_GATEWAY.code()));
    }

    @Test
    void httpStatusToGrpcCode_3() {
        Assertions.assertEquals(Code.UNAVAILABLE, TriRpcStatus.httpStatusToGrpcCode(HttpResponseStatus.TOO_MANY_REQUESTS.code()));
    }

    @Test
    void httpStatusToGrpcCode_4() {
        Assertions.assertEquals(Code.UNAVAILABLE, TriRpcStatus.httpStatusToGrpcCode(HttpResponseStatus.SERVICE_UNAVAILABLE.code()));
    }

    @Test
    void httpStatusToGrpcCode_5() {
        Assertions.assertEquals(Code.UNAVAILABLE, TriRpcStatus.httpStatusToGrpcCode(HttpResponseStatus.GATEWAY_TIMEOUT.code()));
    }

    @Test
    void httpStatusToGrpcCode_6() {
        Assertions.assertEquals(Code.INTERNAL, TriRpcStatus.httpStatusToGrpcCode(HttpResponseStatus.CONTINUE.code()));
    }

    @Test
    void httpStatusToGrpcCode_7() {
        Assertions.assertEquals(Code.INTERNAL, TriRpcStatus.httpStatusToGrpcCode(HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.code()));
    }

    @Test
    void httpStatusToGrpcCode_8() {
        Assertions.assertEquals(Code.UNKNOWN, TriRpcStatus.httpStatusToGrpcCode(HttpResponseStatus.ACCEPTED.code()));
    }

    @Test
    void httpStatusToGrpcCode_9() {
        Assertions.assertEquals(Code.PERMISSION_DENIED, TriRpcStatus.httpStatusToGrpcCode(HttpResponseStatus.FORBIDDEN.code()));
    }

    @Test
    void httpStatusToGrpcCode_10() {
        Assertions.assertEquals(Code.UNIMPLEMENTED, TriRpcStatus.httpStatusToGrpcCode(HttpResponseStatus.NOT_FOUND.code()));
    }

    @Test
    void isOk_1() {
        Assertions.assertTrue(TriRpcStatus.OK.isOk());
    }

    @Test
    void isOk_2() {
        Assertions.assertFalse(TriRpcStatus.NOT_FOUND.isOk());
    }

    @Test
    void encodeMessage_1() {
        Assertions.assertTrue(TriRpcStatus.encodeMessage(null).isEmpty());
    }

    @Test
    void encodeMessage_2() {
        Assertions.assertTrue(TriRpcStatus.encodeMessage("").isEmpty());
    }

    @Test
    void triCodeToDubboCode_1() {
        Assertions.assertEquals(TIMEOUT_EXCEPTION, TriRpcStatus.triCodeToDubboCode(Code.DEADLINE_EXCEEDED));
    }

    @Test
    void triCodeToDubboCode_2() {
        Assertions.assertEquals(FORBIDDEN_EXCEPTION, TriRpcStatus.triCodeToDubboCode(Code.PERMISSION_DENIED));
    }

    @Test
    void triCodeToDubboCode_3() {
        Assertions.assertEquals(METHOD_NOT_FOUND, TriRpcStatus.triCodeToDubboCode(Code.UNIMPLEMENTED));
    }

    @Test
    void triCodeToDubboCode_4() {
        Assertions.assertEquals(UNKNOWN_EXCEPTION, TriRpcStatus.triCodeToDubboCode(Code.UNKNOWN));
    }
}
