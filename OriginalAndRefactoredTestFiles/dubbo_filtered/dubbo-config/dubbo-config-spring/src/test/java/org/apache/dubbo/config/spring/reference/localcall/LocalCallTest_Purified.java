package org.apache.dubbo.config.spring.reference.localcall;

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.spring.api.HelloService;
import java.net.InetSocketAddress;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:/org/apache/dubbo/config/spring/reference/localcall/local-call-provider.xml", "classpath:/org/apache/dubbo/config/spring/reference/localcall/local-call-consumer.xml" })
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class LocalCallTest_Purified {

    @BeforeAll
    public static void beforeAll() {
        DubboBootstrap.reset();
    }

    @AfterAll
    public static void afterAll() {
        DubboBootstrap.reset();
    }

    @Autowired
    private HelloService helloService;

    @Autowired
    private HelloService localHelloService;

    @Test
    void testLocalCall_1() {
        String result = helloService.sayHello("world");
        Assertions.assertEquals("Hello world, response from provider: " + InetSocketAddress.createUnresolved("127.0.0.1", 0), result);
    }

    @Test
    void testLocalCall_2() {
        String originResult = localHelloService.sayHello("world");
        Assertions.assertEquals("Hello world, response from provider: null", originResult);
    }
}
