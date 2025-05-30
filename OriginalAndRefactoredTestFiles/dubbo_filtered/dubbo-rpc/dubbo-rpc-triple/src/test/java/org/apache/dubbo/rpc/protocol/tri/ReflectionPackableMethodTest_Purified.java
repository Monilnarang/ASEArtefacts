package org.apache.dubbo.rpc.protocol.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ReflectionMethodDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.CompletableFuture;
import io.reactivex.Single;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReflectionPackableMethodTest_Purified {

    public boolean needWrap(MethodDescriptor method) {
        Class<?>[] actualRequestTypes;
        Class<?> actualResponseType;
        switch(method.getRpcType()) {
            case CLIENT_STREAM:
            case BI_STREAM:
                actualRequestTypes = new Class<?>[] { (Class<?>) ((ParameterizedType) method.getMethod().getGenericReturnType()).getActualTypeArguments()[0] };
                actualResponseType = (Class<?>) ((ParameterizedType) method.getMethod().getGenericParameterTypes()[0]).getActualTypeArguments()[0];
                break;
            case SERVER_STREAM:
                actualRequestTypes = method.getMethod().getParameterTypes();
                actualResponseType = (Class<?>) ((ParameterizedType) method.getMethod().getGenericParameterTypes()[1]).getActualTypeArguments()[0];
                break;
            case UNARY:
                actualRequestTypes = method.getParameterClasses();
                actualResponseType = method.getReturnClass();
                break;
            default:
                throw new IllegalStateException("Can not reach here");
        }
        return ReflectionPackableMethod.needWrap(method, actualRequestTypes, actualResponseType);
    }

    @Test
    void testIsServerStream_1() throws NoSuchMethodException {
        Method method = DescriptorService.class.getMethod("sayHelloServerStream", HelloReply.class, StreamObserver.class);
        ReflectionMethodDescriptor descriptor = new ReflectionMethodDescriptor(method);
        Assertions.assertFalse(needWrap(descriptor));
    }

    @Test
    void testIsServerStream_2() throws NoSuchMethodException {
        Method method2 = DescriptorService.class.getMethod("sayHelloServerStream2", Object.class, StreamObserver.class);
        ReflectionMethodDescriptor descriptor2 = new ReflectionMethodDescriptor(method2);
        Assertions.assertTrue(needWrap(descriptor2));
    }

    @Test
    void testIgnoreMethod_1() throws NoSuchMethodException {
        Method method = DescriptorService.class.getMethod("iteratorServerStream", HelloReply.class);
        MethodDescriptor descriptor = new ReflectionMethodDescriptor(method);
        Assertions.assertFalse(needWrap(descriptor));
    }

    @Test
    void testIgnoreMethod_2() throws NoSuchMethodException {
        Method method2 = DescriptorService.class.getMethod("reactorMethod", HelloReply.class);
        MethodDescriptor descriptor2 = new ReflectionMethodDescriptor(method2);
        Assertions.assertFalse(needWrap(descriptor2));
    }

    @Test
    void testIgnoreMethod_3() throws NoSuchMethodException {
        Method method3 = DescriptorService.class.getMethod("reactorMethod2", Mono.class);
        MethodDescriptor descriptor3 = new ReflectionMethodDescriptor(method3);
        Assertions.assertFalse(needWrap(descriptor3));
    }

    @Test
    void testIgnoreMethod_4() throws NoSuchMethodException {
        Method method4 = DescriptorService.class.getMethod("rxJavaMethod", Single.class);
        MethodDescriptor descriptor4 = new ReflectionMethodDescriptor(method4);
        Assertions.assertFalse(needWrap(descriptor4));
    }
}
