package org.apache.dubbo.rpc.executor;

import org.apache.dubbo.common.URL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class IsolationExecutorSupportFactoryTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_test_1to6")
    void test_1to6(String param1) {
        Assertions.assertInstanceOf(DefaultExecutorSupport.class, IsolationExecutorSupportFactory.getIsolationExecutorSupport(URL.valueOf(param1)));
    }

    static public Stream<Arguments> Provider_test_1to6() {
        return Stream.of(arguments("dubbo://"), arguments("empty://"), arguments("exchange://"), arguments("mock1://"), arguments("mock2://"), arguments("mock3://"));
    }
}
