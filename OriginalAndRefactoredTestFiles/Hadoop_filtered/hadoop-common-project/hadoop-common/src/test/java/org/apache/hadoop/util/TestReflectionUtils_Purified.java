package org.apache.hadoop.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.*;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.GenericTestUtils.LogCapturer;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestReflectionUtils_Purified {

    private static Class[] toConstruct = { String.class, TestReflectionUtils.class, HashMap.class };

    private Throwable failure = null;

    @Before
    public void setUp() {
        ReflectionUtils.clearCache();
    }

    @SuppressWarnings("unchecked")
    private void doTestCache() {
        for (int i = 0; i < toConstruct.length; i++) {
            Class cl = toConstruct[i];
            Object x = ReflectionUtils.newInstance(cl, null);
            Object y = ReflectionUtils.newInstance(cl, null);
            assertEquals(cl, x.getClass());
            assertEquals(cl, y.getClass());
        }
    }

    private int cacheSize() throws Exception {
        return ReflectionUtils.getCacheSize();
    }

    private class Parent {

        private int parentField;

        @SuppressWarnings("unused")
        public int getParentField() {
            return parentField;
        }
    }

    private static class LoadedInChild {
    }

    public static class NoDefaultCtor {

        public NoDefaultCtor(int x) {
        }
    }

    @Test
    public void testCache_1() throws Exception {
        assertEquals(0, cacheSize());
    }

    @Test
    public void testCache_2() throws Exception {
        assertEquals(toConstruct.length, cacheSize());
    }

    @Test
    public void testCache_3() throws Exception {
        assertEquals(0, cacheSize());
    }
}
