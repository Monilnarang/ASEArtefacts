package org.apache.dubbo.common.compiler.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassUtilsTest_Purified {

    private interface GenericInterface<T> {
    }

    private class GenericClass<T> implements GenericInterface<T> {
    }

    private class GenericClass0 implements GenericInterface<String> {
    }

    private class GenericClass1 implements GenericInterface<Collection<String>> {
    }

    private class GenericClass2<T> implements GenericInterface<T[]> {
    }

    private class GenericClass3<T> implements GenericInterface<T[][]> {

        public int getLength() {
            return -1;
        }
    }

    private class PrivateHelloServiceImpl implements HelloService {

        private PrivateHelloServiceImpl() {
        }

        @Override
        public String sayHello() {
            return "Hello world!";
        }
    }

    @Test
    void testForName2_1() {
        Assertions.assertEquals(boolean.class, ClassUtils.forName("boolean"));
    }

    @Test
    void testForName2_2() {
        Assertions.assertEquals(byte.class, ClassUtils.forName("byte"));
    }

    @Test
    void testForName2_3() {
        Assertions.assertEquals(char.class, ClassUtils.forName("char"));
    }

    @Test
    void testForName2_4() {
        Assertions.assertEquals(short.class, ClassUtils.forName("short"));
    }

    @Test
    void testForName2_5() {
        Assertions.assertEquals(int.class, ClassUtils.forName("int"));
    }

    @Test
    void testForName2_6() {
        Assertions.assertEquals(long.class, ClassUtils.forName("long"));
    }

    @Test
    void testForName2_7() {
        Assertions.assertEquals(float.class, ClassUtils.forName("float"));
    }

    @Test
    void testForName2_8() {
        Assertions.assertEquals(double.class, ClassUtils.forName("double"));
    }

    @Test
    void testForName2_9() {
        Assertions.assertEquals(boolean[].class, ClassUtils.forName("boolean[]"));
    }

    @Test
    void testForName2_10() {
        Assertions.assertEquals(byte[].class, ClassUtils.forName("byte[]"));
    }

    @Test
    void testForName2_11() {
        Assertions.assertEquals(char[].class, ClassUtils.forName("char[]"));
    }

    @Test
    void testForName2_12() {
        Assertions.assertEquals(short[].class, ClassUtils.forName("short[]"));
    }

    @Test
    void testForName2_13() {
        Assertions.assertEquals(int[].class, ClassUtils.forName("int[]"));
    }

    @Test
    void testForName2_14() {
        Assertions.assertEquals(long[].class, ClassUtils.forName("long[]"));
    }

    @Test
    void testForName2_15() {
        Assertions.assertEquals(float[].class, ClassUtils.forName("float[]"));
    }

    @Test
    void testForName2_16() {
        Assertions.assertEquals(double[].class, ClassUtils.forName("double[]"));
    }

    @Test
    void testGetBoxedClass_1() {
        Assertions.assertEquals(Boolean.class, ClassUtils.getBoxedClass(boolean.class));
    }

    @Test
    void testGetBoxedClass_2() {
        Assertions.assertEquals(Character.class, ClassUtils.getBoxedClass(char.class));
    }

    @Test
    void testGetBoxedClass_3() {
        Assertions.assertEquals(Byte.class, ClassUtils.getBoxedClass(byte.class));
    }

    @Test
    void testGetBoxedClass_4() {
        Assertions.assertEquals(Short.class, ClassUtils.getBoxedClass(short.class));
    }

    @Test
    void testGetBoxedClass_5() {
        Assertions.assertEquals(Integer.class, ClassUtils.getBoxedClass(int.class));
    }

    @Test
    void testGetBoxedClass_6() {
        Assertions.assertEquals(Long.class, ClassUtils.getBoxedClass(long.class));
    }

    @Test
    void testGetBoxedClass_7() {
        Assertions.assertEquals(Float.class, ClassUtils.getBoxedClass(float.class));
    }

    @Test
    void testGetBoxedClass_8() {
        Assertions.assertEquals(Double.class, ClassUtils.getBoxedClass(double.class));
    }

    @Test
    void testGetBoxedClass_9() {
        Assertions.assertEquals(ClassUtilsTest.class, ClassUtils.getBoxedClass(ClassUtilsTest.class));
    }

    @Test
    void testBoxedAndUnboxed_1() {
        Assertions.assertEquals(Boolean.valueOf(true), ClassUtils.boxed(true));
    }

    @Test
    void testBoxedAndUnboxed_2() {
        Assertions.assertEquals(Character.valueOf('0'), ClassUtils.boxed('0'));
    }

    @Test
    void testBoxedAndUnboxed_3() {
        Assertions.assertEquals(Byte.valueOf((byte) 0), ClassUtils.boxed((byte) 0));
    }

    @Test
    void testBoxedAndUnboxed_4() {
        Assertions.assertEquals(Short.valueOf((short) 0), ClassUtils.boxed((short) 0));
    }

    @Test
    void testBoxedAndUnboxed_5() {
        Assertions.assertEquals(Integer.valueOf((int) 0), ClassUtils.boxed((int) 0));
    }

    @Test
    void testBoxedAndUnboxed_6() {
        Assertions.assertEquals(Long.valueOf((long) 0), ClassUtils.boxed((long) 0));
    }

    @Test
    void testBoxedAndUnboxed_7() {
        Assertions.assertEquals(Float.valueOf((float) 0), ClassUtils.boxed((float) 0));
    }

    @Test
    void testBoxedAndUnboxed_8() {
        Assertions.assertEquals(Double.valueOf((double) 0), ClassUtils.boxed((double) 0));
    }

    @Test
    void testBoxedAndUnboxed_9() {
        Assertions.assertTrue(ClassUtils.unboxed(Boolean.valueOf(true)));
    }

    @Test
    void testBoxedAndUnboxed_10() {
        Assertions.assertEquals('0', ClassUtils.unboxed(Character.valueOf('0')));
    }

    @Test
    void testBoxedAndUnboxed_11() {
        Assertions.assertEquals((byte) 0, ClassUtils.unboxed(Byte.valueOf((byte) 0)));
    }

    @Test
    void testBoxedAndUnboxed_12() {
        Assertions.assertEquals((short) 0, ClassUtils.unboxed(Short.valueOf((short) 0)));
    }

    @Test
    void testBoxedAndUnboxed_13() {
        Assertions.assertEquals(0, ClassUtils.unboxed(Integer.valueOf((int) 0)));
    }

    @Test
    void testBoxedAndUnboxed_14() {
        Assertions.assertEquals((long) 0, ClassUtils.unboxed(Long.valueOf((long) 0)));
    }

    @Test
    void testGetSimpleClassName_1() {
        Assertions.assertNull(ClassUtils.getSimpleClassName(null));
    }

    @Test
    void testGetSimpleClassName_2() {
        Assertions.assertEquals("Map", ClassUtils.getSimpleClassName(Map.class.getName()));
    }

    @Test
    void testGetSimpleClassName_3() {
        Assertions.assertEquals("Map", ClassUtils.getSimpleClassName(Map.class.getSimpleName()));
    }
}
