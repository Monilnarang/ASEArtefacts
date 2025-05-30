package org.apache.dubbo.rpc.protocol.tri;

import org.apache.dubbo.triple.TripleWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.google.protobuf.BoolValue;
import com.google.protobuf.BytesValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.Empty;
import com.google.protobuf.EnumValue;
import com.google.protobuf.FloatValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.ListValue;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.StringValue;
import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SingleProtobufUtilsTest_Purified {

    @Test
    void test_1() throws IOException {
        Assertions.assertFalse(SingleProtobufUtils.isSupported(SingleProtobufUtilsTest.class));
    }

    @Test
    void test_2() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(Empty.class));
    }

    @Test
    void test_3() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(BoolValue.class));
    }

    @Test
    void test_4() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(Int32Value.class));
    }

    @Test
    void test_5() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(Int64Value.class));
    }

    @Test
    void test_6() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(FloatValue.class));
    }

    @Test
    void test_7() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(DoubleValue.class));
    }

    @Test
    void test_8() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(BytesValue.class));
    }

    @Test
    void test_9() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(StringValue.class));
    }

    @Test
    void test_10() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(EnumValue.class));
    }

    @Test
    void test_11() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(ListValue.class));
    }

    @Test
    void test_12() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(HealthCheckResponse.class));
    }

    @Test
    void test_13() throws IOException {
        Assertions.assertTrue(SingleProtobufUtils.isSupported(HealthCheckRequest.class));
    }

    @Test
    void test_14_testMerged_14() throws IOException {
        Message message = SingleProtobufUtils.defaultInst(HealthCheckRequest.class);
        Assertions.assertNotNull(message);
        Parser<HealthCheckRequest> parser = SingleProtobufUtils.getParser(HealthCheckRequest.class);
        Assertions.assertNotNull(parser);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        SingleProtobufUtils.serialize(requestWrapper, bos);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        TripleWrapper.TripleRequestWrapper tripleRequestWrapper = SingleProtobufUtils.deserialize(bis, TripleWrapper.TripleRequestWrapper.class);
        Assertions.assertEquals(tripleRequestWrapper.getSerializeType(), "hessian4");
    }
}
