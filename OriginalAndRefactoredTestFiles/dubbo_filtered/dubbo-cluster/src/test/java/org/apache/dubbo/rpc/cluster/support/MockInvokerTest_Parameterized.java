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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MockInvokerTest_Parameterized {

    @Test
    void testNormalizeMock_1() {
        Assertions.assertNull(MockInvoker.normalizeMock(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNormalizeMock_2to11")
    void testNormalizeMock_2to11(String param1, String param2) {
        Assertions.assertEquals(param1, MockInvoker.normalizeMock(param2));
    }

    static public Stream<Arguments> Provider_testNormalizeMock_2to11() {
        return Stream.of(arguments("", ""), arguments("", "fail:"), arguments("", "force:"), arguments("throw", "throw"), arguments("default", "fail"), arguments("default", "force"), arguments("default", true), arguments("default", "default"), arguments("return null", "return"), arguments("return null", "return null"));
    }
}
