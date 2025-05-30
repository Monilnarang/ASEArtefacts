package org.apache.dubbo.rpc.cluster.support;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.support.MockInvoker;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.rpc.Constants.MOCK_KEY;

class MockInvokerTest_Purified {

    @Test
    void testNormalizeMock_1() {
        Assertions.assertNull(MockInvoker.normalizeMock(null));
    }

    @Test
    void testNormalizeMock_2() {
        Assertions.assertEquals("", MockInvoker.normalizeMock(""));
    }

    @Test
    void testNormalizeMock_3() {
        Assertions.assertEquals("", MockInvoker.normalizeMock("fail:"));
    }

    @Test
    void testNormalizeMock_4() {
        Assertions.assertEquals("", MockInvoker.normalizeMock("force:"));
    }

    @Test
    void testNormalizeMock_5() {
        Assertions.assertEquals("throw", MockInvoker.normalizeMock("throw"));
    }

    @Test
    void testNormalizeMock_6() {
        Assertions.assertEquals("default", MockInvoker.normalizeMock("fail"));
    }

    @Test
    void testNormalizeMock_7() {
        Assertions.assertEquals("default", MockInvoker.normalizeMock("force"));
    }

    @Test
    void testNormalizeMock_8() {
        Assertions.assertEquals("default", MockInvoker.normalizeMock("true"));
    }

    @Test
    void testNormalizeMock_9() {
        Assertions.assertEquals("default", MockInvoker.normalizeMock("default"));
    }

    @Test
    void testNormalizeMock_10() {
        Assertions.assertEquals("return null", MockInvoker.normalizeMock("return"));
    }

    @Test
    void testNormalizeMock_11() {
        Assertions.assertEquals("return null", MockInvoker.normalizeMock("return null"));
    }
}
