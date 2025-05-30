package org.apache.commons.pool3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import org.apache.commons.pool3.impl.DefaultPooledObject;
import org.apache.commons.pool3.impl.GenericKeyedObjectPool;
import org.apache.commons.pool3.impl.GenericObjectPool;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class TestPoolUtils_Purified {

    private static class MethodCallLogger implements InvocationHandler {

        private final List<String> calledMethods;

        MethodCallLogger(final List<String> calledMethods) {
            this.calledMethods = calledMethods;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            if (calledMethods == null) {
                return null;
            }
            calledMethods.add(method.getName());
            if (boolean.class.equals(method.getReturnType())) {
                return Boolean.FALSE;
            }
            if (int.class.equals(method.getReturnType())) {
                return Integer.valueOf(0);
            }
            if (long.class.equals(method.getReturnType())) {
                return Long.valueOf(0);
            }
            if (Object.class.equals(method.getReturnType())) {
                return new Object();
            }
            if (PooledObject.class.equals(method.getReturnType())) {
                return new DefaultPooledObject<>(new Object());
            }
            return null;
        }
    }

    private static final int CHECK_PERIOD = 300;

    private static final int CHECK_COUNT = 4;

    private static final int CHECK_SLEEP_PERIOD = CHECK_PERIOD * (CHECK_COUNT - 1) + CHECK_PERIOD / 2;

    @SuppressWarnings("unchecked")
    private static <T> T createProxy(final Class<T> clazz, final InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, handler);
    }

    private static <T> T createProxy(final Class<T> clazz, final List<String> logger) {
        return createProxy(clazz, new MethodCallLogger(logger));
    }

    private static List<String> invokeEveryMethod(final KeyedObjectPool<Object, Object, RuntimeException> kop) {
        kop.addObject(null);
        kop.borrowObject(null);
        kop.clear();
        kop.clear(null);
        kop.close();
        kop.getKeys();
        kop.getNumActive();
        kop.getNumActive(null);
        kop.getNumIdle();
        kop.getNumIdle(null);
        kop.invalidateObject(null, new Object());
        kop.returnObject(null, new Object());
        kop.toString();
        return Arrays.asList("addObject", "borrowObject", "clear", "clear", "close", "getKeys", "getNumActive", "getNumActive", "getNumIdle", "getNumIdle", "invalidateObject", "returnObject", "toString");
    }

    private static <K, V> List<String> invokeEveryMethod(final KeyedPooledObjectFactory<K, V, RuntimeException> kpof) {
        kpof.activateObject(null, null);
        kpof.destroyObject(null, null);
        kpof.makeObject(null);
        kpof.passivateObject(null, null);
        kpof.validateObject(null, null);
        kpof.toString();
        return Arrays.asList("activateObject", "destroyObject", "makeObject", "passivateObject", "validateObject", "toString");
    }

    private static List<String> invokeEveryMethod(final ObjectPool<Object, RuntimeException> op) {
        op.addObject();
        op.borrowObject();
        op.clear();
        op.close();
        op.getNumActive();
        op.getNumIdle();
        op.invalidateObject(new Object());
        op.returnObject(new Object());
        op.toString();
        return Arrays.asList("addObject", "borrowObject", "clear", "close", "getNumActive", "getNumIdle", "invalidateObject", "returnObject", "toString");
    }

    private static <T, E extends Exception> List<String> invokeEveryMethod(final PooledObjectFactory<T, E> pof) throws E {
        pof.activateObject(null);
        pof.destroyObject(null);
        pof.makeObject();
        pof.passivateObject(null);
        pof.validateObject(null);
        pof.toString();
        return Arrays.asList("activateObject", "destroyObject", "makeObject", "passivateObject", "validateObject", "toString");
    }

    @Test
    public void testTimerHolder_1() {
        final PoolUtils.TimerHolder h = new PoolUtils.TimerHolder();
        assertNotNull(h);
    }

    @Test
    public void testTimerHolder_2() {
        assertNotNull(PoolUtils.TimerHolder.MIN_IDLE_TIMER);
    }
}
