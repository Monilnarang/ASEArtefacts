package org.apache.dubbo.config.spring.reference;

import org.apache.dubbo.config.annotation.Argument;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.api.DemoService;
import org.apache.dubbo.config.spring.api.HelloService;
import org.apache.dubbo.config.spring.api.ProvidedByDemoService1;
import org.apache.dubbo.config.spring.api.ProvidedByDemoService2;
import org.apache.dubbo.config.spring.api.ProvidedByDemoService3;
import org.apache.dubbo.config.spring.impl.DemoServiceImpl;
import org.apache.dubbo.config.spring.impl.HelloServiceImpl;
import org.apache.dubbo.config.spring.util.AnnotationUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.AnnotationAttributes;

class ReferenceKeyTest_Purified {

    @BeforeEach
    protected void setUp() {
        DubboBootstrap.reset();
    }

    private String getStackTrace(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private String getReferenceKey(String fieldName) throws NoSuchFieldException {
        Field field = ReferenceConfiguration.class.getDeclaredField(fieldName);
        AnnotationAttributes attributes = AnnotationUtils.getAnnotationAttributes(field, DubboReference.class, null, true);
        ReferenceBeanSupport.convertReferenceProps(attributes, field.getType());
        return ReferenceBeanSupport.generateReferenceKey(attributes, null);
    }

    private Map<String, Object> getReferenceAttributes(String fieldName) throws NoSuchFieldException {
        Field field = ConsumerConfiguration7.class.getDeclaredField(fieldName);
        AnnotationAttributes attributes = AnnotationUtils.getAnnotationAttributes(field, DubboReference.class, null, true);
        ReferenceBeanSupport.convertReferenceProps(attributes, field.getType());
        return attributes;
    }

    static class ReferenceConfiguration {

        @DubboReference(methods = @Method(name = "sayHello", timeout = 100, retries = 0))
        private HelloService helloService;

        @DubboReference(methods = @Method(timeout = 100, name = "sayHello", retries = 0))
        private HelloService helloService2;

        @DubboReference(methods = @Method(name = "sayHello", timeout = 100, arguments = @Argument(index = 0, callback = true)))
        private HelloService helloService3;

        @DubboReference(methods = @Method(arguments = @Argument(callback = true, index = 0), name = "sayHello", timeout = 100))
        private HelloService helloService4;

        @DubboReference(check = false, parameters = { "a", "2", "b", "1" }, filter = { "echo" })
        private HelloService helloServiceWithArray0;

        @DubboReference(check = false, parameters = { "a=1", "b", "2" }, filter = { "echo" })
        private HelloService helloServiceWithArray1;

        @DubboReference(parameters = { "b", "2", "a", "1" }, filter = { "echo" }, check = false)
        private HelloService helloServiceWithArray2;

        @DubboReference(check = false, parameters = { "a", "1", "b", "2" }, filter = { "echo" }, methods = { @Method(parameters = { "d", "2", "c", "1" }, name = "sayHello", timeout = 100) })
        private HelloService helloServiceWithMethod1;

        @DubboReference(parameters = { "b=2", "a=1" }, filter = { "echo" }, check = false, methods = { @Method(name = "sayHello", timeout = 100, parameters = { "c", "1", "d", "2" }) })
        private HelloService helloServiceWithMethod2;

        @DubboReference(parameters = { "a", "1", "b", "2" }, filter = { "echo" }, methods = { @Method(name = "sayHello", arguments = { @Argument(callback = true, type = "String"), @Argument(callback = false, type = "int") }, timeout = 100) }, check = false)
        private HelloService helloServiceWithArgument1;

        @DubboReference(check = false, filter = { "echo" }, parameters = { "b", "2", "a", "1" }, methods = { @Method(name = "sayHello", timeout = 100, arguments = { @Argument(callback = false, type = "int"), @Argument(callback = true, type = "String") }) })
        private HelloService helloServiceWithArgument2;
    }

    @Configuration
    @ImportResource({ "classpath:/org/apache/dubbo/config/spring/init-reference-keys.xml", "classpath:/org/apache/dubbo/config/spring/init-reference-properties.xml" })
    static class ConsumerConfiguration {

        @DubboReference(group = "demo", version = "1.2.3", consumer = "my-consumer", init = false, methods = { @Method(arguments = { @Argument(callback = true, index = 0) }, name = "sayName", parameters = { "access-token", "my-token", "b", "2" }, retries = 0) }, parameters = { "connec.timeout", "1000" }, protocol = "dubbo", registry = "my-registry", scope = "remote", timeout = 1000, url = "dubbo://127.0.0.1:20813")
        private DemoService demoService;
    }

    @Configuration
    @ImportResource({ "classpath:/org/apache/dubbo/config/spring/init-reference-keys.xml", "classpath:/org/apache/dubbo/config/spring/init-reference-properties.xml" })
    static class ConsumerConfiguration2 {

        @DubboReference(group = "demo", version = "1.2.3", consumer = "my-consumer", init = false, scope = "local", timeout = 100)
        private DemoService demoService;
    }

    @Configuration
    @ImportResource({ "classpath:/org/apache/dubbo/config/spring/init-reference-keys.xml", "classpath:/org/apache/dubbo/config/spring/init-reference-properties.xml" })
    static class ConsumerConfiguration3 {

        @DubboReference(group = "demo", version = "1.2.4", consumer = "my-consumer", init = false, url = "dubbo://127.0.0.1:20813")
        private HelloService demoService;

        @Autowired
        private HelloService helloService;
    }

    @Configuration
    @ImportResource({ "classpath:/org/apache/dubbo/config/spring/init-reference-keys.xml", "classpath:/org/apache/dubbo/config/spring/init-reference-properties.xml" })
    static class ConsumerConfiguration4 {

        @Bean
        public DemoService demoService() {
            return new DemoServiceImpl();
        }
    }

    @Configuration
    @ImportResource({ "classpath:/org/apache/dubbo/config/spring/init-reference-keys.xml", "classpath:/org/apache/dubbo/config/spring/init-reference-properties.xml" })
    static class ConsumerConfiguration5 {

        @Bean
        public HelloService demoService() {
            return new HelloServiceImpl();
        }
    }

    @Configuration
    @ImportResource({ "classpath:/org/apache/dubbo/config/spring/init-reference-keys.xml", "classpath:/org/apache/dubbo/config/spring/init-reference-properties.xml" })
    static class ConsumerConfiguration6 {

        @DubboReference(id = "demoService", group = "demo", version = "1.2.3", consumer = "my-consumer", init = false, url = "dubbo://127.0.0.1:20813")
        private HelloService demoService;
    }

    @Configuration
    static class ConsumerConfiguration7 {

        @DubboReference(providedBy = "provided-demo-service1")
        private ProvidedByDemoService1 providedByDemoService1;

        @DubboReference(providedBy = "provided-demo-service2")
        private ProvidedByDemoService2 providedByDemoService2;

        @DubboReference(providedBy = { "provided-demo-service3", "provided-demo-service4" })
        private ProvidedByDemoService2 multiProvidedByDemoService;

        @DubboReference
        private ProvidedByDemoService1 providedByDemoServiceInterface;

        @DubboReference
        private ProvidedByDemoService3 multiProvidedByDemoServiceInterface;
    }

    @Test
    void testReferenceKey_1_testMerged_1() throws Exception {
        String helloService1 = getReferenceKey("helloService");
        String helloService2 = getReferenceKey("helloService2");
        Assertions.assertEquals("ReferenceBean:org.apache.dubbo.config.spring.api.HelloService(methods=[{name=sayHello, retries=0, timeout=100}])", helloService1);
        Assertions.assertEquals(helloService1, helloService2);
    }

    @Test
    void testReferenceKey_3_testMerged_2() throws Exception {
        String helloService3 = getReferenceKey("helloService3");
        String helloService4 = getReferenceKey("helloService4");
        Assertions.assertEquals("ReferenceBean:org.apache.dubbo.config.spring.api.HelloService(methods=[{arguments=[{callback=true, index=0}], name=sayHello, timeout=100}])", helloService3);
        Assertions.assertEquals(helloService3, helloService4);
    }

    @Test
    void testReferenceKey_5_testMerged_3() throws Exception {
        String helloServiceWithArray0 = getReferenceKey("helloServiceWithArray0");
        String helloServiceWithArray1 = getReferenceKey("helloServiceWithArray1");
        String helloServiceWithArray2 = getReferenceKey("helloServiceWithArray2");
        Assertions.assertEquals("ReferenceBean:org.apache.dubbo.config.spring.api.HelloService(check=false,filter=[echo],parameters={a=2, b=1})", helloServiceWithArray0);
        Assertions.assertNotEquals(helloServiceWithArray0, helloServiceWithArray1);
        Assertions.assertEquals("ReferenceBean:org.apache.dubbo.config.spring.api.HelloService(check=false,filter=[echo],parameters={a=1, b=2})", helloServiceWithArray1);
        Assertions.assertEquals(helloServiceWithArray1, helloServiceWithArray2);
    }

    @Test
    void testReferenceKey_9_testMerged_4() throws Exception {
        String helloServiceWithMethod1 = getReferenceKey("helloServiceWithMethod1");
        String helloServiceWithMethod2 = getReferenceKey("helloServiceWithMethod2");
        Assertions.assertEquals("ReferenceBean:org.apache.dubbo.config.spring.api.HelloService(check=false,filter=[echo],methods=[{name=sayHello, parameters={c=1, d=2}, timeout=100}],parameters={a=1, b=2})", helloServiceWithMethod1);
        Assertions.assertEquals(helloServiceWithMethod1, helloServiceWithMethod2);
    }

    @Test
    void testReferenceKey_11_testMerged_5() throws Exception {
        String helloServiceWithArgument1 = getReferenceKey("helloServiceWithArgument1");
        String helloServiceWithArgument2 = getReferenceKey("helloServiceWithArgument2");
        Assertions.assertEquals("ReferenceBean:org.apache.dubbo.config.spring.api.HelloService(check=false,filter=[echo],methods=[{arguments=[{callback=true, type=String}, {type=int}], name=sayHello, timeout=100}],parameters={a=1, b=2})", helloServiceWithArgument1);
        Assertions.assertEquals(helloServiceWithArgument1, helloServiceWithArgument2);
    }
}
