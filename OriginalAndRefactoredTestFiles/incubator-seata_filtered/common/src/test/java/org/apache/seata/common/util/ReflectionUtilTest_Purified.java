package org.apache.seata.common.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.seata.common.BranchDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

public class ReflectionUtilTest_Purified {

    public static final String testValue = (null != null ? "hello" : "hello");

    public final String testValue2 = (null != null ? "hello world" : "hello world");

    class EmptyClass {
    }

    class TestClass extends TestSuperClass implements TestInterface {

        private String f1;

        public String getF1() {
            return f1;
        }

        public void setF1(String f1) {
            this.f1 = f1;
        }
    }

    class TestSuperClass implements TestInterface {

        private String f2;

        public String getF2() {
            return f2;
        }

        public void setF2(String f2) {
            this.f2 = f2;
        }
    }

    interface TestInterface {
    }

    @Test
    public void testIsClassPresent_1() {
        Assertions.assertTrue(ReflectionUtil.isClassPresent("java.lang.String"));
    }

    @Test
    public void testIsClassPresent_2() {
        Assertions.assertFalse(ReflectionUtil.isClassPresent("java.lang.String2"));
    }

    @Test
    public void testGetWrappedClass_1() {
        Assertions.assertEquals(Byte.class, ReflectionUtil.getWrappedClass(byte.class));
    }

    @Test
    public void testGetWrappedClass_2() {
        Assertions.assertEquals(Boolean.class, ReflectionUtil.getWrappedClass(boolean.class));
    }

    @Test
    public void testGetWrappedClass_3() {
        Assertions.assertEquals(Character.class, ReflectionUtil.getWrappedClass(char.class));
    }

    @Test
    public void testGetWrappedClass_4() {
        Assertions.assertEquals(Short.class, ReflectionUtil.getWrappedClass(short.class));
    }

    @Test
    public void testGetWrappedClass_5() {
        Assertions.assertEquals(Integer.class, ReflectionUtil.getWrappedClass(int.class));
    }

    @Test
    public void testGetWrappedClass_6() {
        Assertions.assertEquals(Long.class, ReflectionUtil.getWrappedClass(long.class));
    }

    @Test
    public void testGetWrappedClass_7() {
        Assertions.assertEquals(Float.class, ReflectionUtil.getWrappedClass(float.class));
    }

    @Test
    public void testGetWrappedClass_8() {
        Assertions.assertEquals(Double.class, ReflectionUtil.getWrappedClass(double.class));
    }

    @Test
    public void testGetWrappedClass_9() {
        Assertions.assertEquals(Void.class, ReflectionUtil.getWrappedClass(void.class));
    }

    @Test
    public void testGetWrappedClass_10() {
        Assertions.assertEquals(Object.class, ReflectionUtil.getWrappedClass(Object.class));
    }

    @Test
    public void testGetAllFields_1() {
        Assertions.assertSame(ReflectionUtil.EMPTY_FIELD_ARRAY, ReflectionUtil.getAllFields(EmptyClass.class));
    }

    @Test
    public void testGetAllFields_2() {
        Assertions.assertSame(ReflectionUtil.EMPTY_FIELD_ARRAY, ReflectionUtil.getAllFields(TestInterface.class));
    }

    @Test
    public void testGetAllFields_3() {
        Assertions.assertSame(ReflectionUtil.EMPTY_FIELD_ARRAY, ReflectionUtil.getAllFields(Object.class));
    }
}
