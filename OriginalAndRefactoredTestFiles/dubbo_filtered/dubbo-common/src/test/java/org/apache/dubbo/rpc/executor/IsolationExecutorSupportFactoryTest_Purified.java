package org.apache.dubbo.rpc.executor;

import org.apache.dubbo.common.URL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IsolationExecutorSupportFactoryTest_Purified {

    @Test
    void test_1() {
        Assertions.assertInstanceOf(DefaultExecutorSupport.class, IsolationExecutorSupportFactory.getIsolationExecutorSupport(URL.valueOf("dubbo://")));
    }

    @Test
    void test_2() {
        Assertions.assertInstanceOf(DefaultExecutorSupport.class, IsolationExecutorSupportFactory.getIsolationExecutorSupport(URL.valueOf("empty://")));
    }

    @Test
    void test_3() {
        Assertions.assertInstanceOf(DefaultExecutorSupport.class, IsolationExecutorSupportFactory.getIsolationExecutorSupport(URL.valueOf("exchange://")));
    }

    @Test
    void test_4() {
        Assertions.assertInstanceOf(Mock1ExecutorSupport.class, IsolationExecutorSupportFactory.getIsolationExecutorSupport(URL.valueOf("mock1://")));
    }

    @Test
    void test_5() {
        Assertions.assertInstanceOf(Mock2ExecutorSupport.class, IsolationExecutorSupportFactory.getIsolationExecutorSupport(URL.valueOf("mock2://")));
    }

    @Test
    void test_6() {
        Assertions.assertInstanceOf(DefaultExecutorSupport.class, IsolationExecutorSupportFactory.getIsolationExecutorSupport(URL.valueOf("mock3://")));
    }
}
