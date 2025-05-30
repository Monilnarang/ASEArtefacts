package org.apache.dubbo.common.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ReflectUtilsTest_Parameterized {

    protected void assertSame(Class<?>[] cs1, Class<?>[] cs2) throws Exception {
        assertEquals(cs1.length, cs2.length);
        for (int i = 0; i < cs1.length; i++) assertEquals(cs1[i], cs2[i]);
    }

    public interface TypeClass<T extends String, S> {

        CompletableFuture<String> getFuture();

        String getString();

        T getT();

        S getS();

        CompletableFuture<List<String>> getListFuture();

        CompletableFuture<T> getGenericWithUpperFuture();

        CompletableFuture<S> getGenericFuture();
    }

    public static class EmptyClass {

        private EmptyProperty property;

        public boolean set;

        public static String s;

        private transient int i;

        public EmptyProperty getProperty() {
            return property;
        }

        public EmptyProperty getPropertyIndex(int i) {
            return property;
        }

        public static EmptyProperty getProperties() {
            return null;
        }

        public void isProperty() {
        }

        public boolean isSet() {
            return set;
        }

        public void setProperty(EmptyProperty property) {
            this.property = property;
        }

        public void setSet(boolean set) {
            this.set = set;
        }
    }

    public static class EmptyProperty {
    }

    static class TestedClass {

        public void method1(int x) {
        }

        public void overrideMethod(int x) {
        }

        public void overrideMethod(Integer x) {
        }

        public void overrideMethod(String s) {
        }

        public void overrideMethod(String s1, String s2) {
        }
    }

    interface Foo<A, B> {

        A hello(B b);
    }

    static class Foo1 implements Foo<String, Integer> {

        @Override
        public String hello(Integer integer) {
            return null;
        }
    }

    static class Foo2 implements Foo<List<String>, int[]> {

        public Foo2(List<String> list, int[] ints) {
        }

        @Override
        public List<String> hello(int[] ints) {
            return null;
        }
    }

    static class Foo3 implements Foo<Foo1, Foo2> {

        public Foo3(Foo foo) {
        }

        @Override
        public Foo1 hello(Foo2 foo2) {
            return null;
        }
    }

    @Test
    void testIsPrimitives_1() {
        assertTrue(ReflectUtils.isPrimitives(boolean[].class));
    }

    @Test
    void testIsPrimitives_2() {
        assertTrue(ReflectUtils.isPrimitives(byte.class));
    }

    @Test
    void testIsPrimitives_3() {
        assertFalse(ReflectUtils.isPrimitive(Map[].class));
    }

    @Test
    void testIsPrimitive_1() {
        assertTrue(ReflectUtils.isPrimitive(boolean.class));
    }

    @Test
    void testIsPrimitive_2() {
        assertTrue(ReflectUtils.isPrimitive(String.class));
    }

    @Test
    void testIsPrimitive_3() {
        assertTrue(ReflectUtils.isPrimitive(Boolean.class));
    }

    @Test
    void testIsPrimitive_4() {
        assertTrue(ReflectUtils.isPrimitive(Character.class));
    }

    @Test
    void testIsPrimitive_5() {
        assertTrue(ReflectUtils.isPrimitive(Number.class));
    }

    @Test
    void testIsPrimitive_6() {
        assertTrue(ReflectUtils.isPrimitive(Date.class));
    }

    @Test
    void testIsPrimitive_7() {
        assertFalse(ReflectUtils.isPrimitive(Map.class));
    }

    @Test
    void testGetBoxedClass_1() {
        assertThat(ReflectUtils.getBoxedClass(int.class), sameInstance(Integer.class));
    }

    @Test
    void testGetBoxedClass_2() {
        assertThat(ReflectUtils.getBoxedClass(boolean.class), sameInstance(Boolean.class));
    }

    @Test
    void testGetBoxedClass_3() {
        assertThat(ReflectUtils.getBoxedClass(long.class), sameInstance(Long.class));
    }

    @Test
    void testGetBoxedClass_4() {
        assertThat(ReflectUtils.getBoxedClass(float.class), sameInstance(Float.class));
    }

    @Test
    void testGetBoxedClass_5() {
        assertThat(ReflectUtils.getBoxedClass(double.class), sameInstance(Double.class));
    }

    @Test
    void testGetBoxedClass_6() {
        assertThat(ReflectUtils.getBoxedClass(char.class), sameInstance(Character.class));
    }

    @Test
    void testGetBoxedClass_7() {
        assertThat(ReflectUtils.getBoxedClass(byte.class), sameInstance(Byte.class));
    }

    @Test
    void testGetBoxedClass_8() {
        assertThat(ReflectUtils.getBoxedClass(short.class), sameInstance(Short.class));
    }

    @Test
    void testGetBoxedClass_9() {
        assertThat(ReflectUtils.getBoxedClass(String.class), sameInstance(String.class));
    }

    @Test
    void testIsCompatible_1() {
        assertTrue(ReflectUtils.isCompatible(short.class, (short) 1));
    }

    @Test
    void testIsCompatible_4() {
        assertTrue(ReflectUtils.isCompatible(Object.class, 1.2));
    }

    @Test
    void testIsCompatible_5() {
        assertTrue(ReflectUtils.isCompatible(List.class, new ArrayList<String>()));
    }

    @Test
    void testGetCodeBase_1() {
        assertNull(ReflectUtils.getCodeBase(null));
    }

    @Test
    void testGetCodeBase_2() {
        assertNull(ReflectUtils.getCodeBase(String.class));
    }

    @Test
    void testGetCodeBase_3() {
        assertNotNull(ReflectUtils.getCodeBase(ReflectUtils.class));
    }

    @Test
    void testGetName_1() {
        assertEquals("boolean", ReflectUtils.getName(boolean.class));
    }

    @Test
    void testGetName_2() {
        assertEquals("int[][][]", ReflectUtils.getName(int[][][].class));
    }

    @Test
    void testGetName_3() {
        assertEquals("java.lang.Object[][]", ReflectUtils.getName(Object[][].class));
    }

    @Test
    void testGetDesc_1() {
        assertEquals("Z", ReflectUtils.getDesc(boolean.class));
    }

    @Test
    void testGetDesc_2() {
        assertEquals("[[[I", ReflectUtils.getDesc(int[][][].class));
    }

    @Test
    void testGetDesc_3() {
        assertEquals("[[Ljava/lang/Object;", ReflectUtils.getDesc(Object[][].class));
    }

    @Test
    void testName2desc_1() {
        assertEquals("Z", ReflectUtils.name2desc(ReflectUtils.getName(boolean.class)));
    }

    @Test
    void testName2desc_2() {
        assertEquals("[[[I", ReflectUtils.name2desc(ReflectUtils.getName(int[][][].class)));
    }

    @Test
    void testName2desc_3() {
        assertEquals("[[Ljava/lang/Object;", ReflectUtils.name2desc(ReflectUtils.getName(Object[][].class)));
    }

    @Test
    void testDesc2name_11() {
        assertEquals("java.lang.Object[][]", ReflectUtils.desc2name(ReflectUtils.getDesc(Object[][].class)));
    }

    @Test
    void testGetGenericClassWithIndex_4() {
        assertThat(ReflectUtils.getGenericClass(Foo2.class, 1), sameInstance(int.class));
    }

    @Test
    void testName2Class_1() throws Exception {
        assertEquals(boolean.class, ReflectUtils.name2class("boolean"));
    }

    @Test
    void testName2Class_2() throws Exception {
        assertEquals(boolean[].class, ReflectUtils.name2class("boolean[]"));
    }

    @Test
    void testName2Class_3() throws Exception {
        assertEquals(int[][].class, ReflectUtils.name2class(ReflectUtils.getName(int[][].class)));
    }

    @Test
    void testName2Class_4() throws Exception {
        assertEquals(ReflectUtilsTest[].class, ReflectUtils.name2class(ReflectUtils.getName(ReflectUtilsTest[].class)));
    }

    @Test
    void testGetEmptyObject_1() {
        assertTrue(ReflectUtils.getEmptyObject(Collection.class) instanceof Collection);
    }

    @Test
    void testGetEmptyObject_2() {
        assertTrue(ReflectUtils.getEmptyObject(List.class) instanceof List);
    }

    @Test
    void testGetEmptyObject_3() {
        assertTrue(ReflectUtils.getEmptyObject(Set.class) instanceof Set);
    }

    @Test
    void testGetEmptyObject_4() {
        assertTrue(ReflectUtils.getEmptyObject(Map.class) instanceof Map);
    }

    @Test
    void testGetEmptyObject_5() {
        assertTrue(ReflectUtils.getEmptyObject(Object[].class) instanceof Object[]);
    }

    @Test
    void testGetEmptyObject_6() {
        assertEquals("", ReflectUtils.getEmptyObject(String.class));
    }

    @Test
    void testGetEmptyObject_14() {
        assertEquals(Boolean.FALSE, ReflectUtils.getEmptyObject(boolean.class));
    }

    @Test
    void testGetEmptyObject_15_testMerged_15() {
        EmptyClass object = (EmptyClass) ReflectUtils.getEmptyObject(EmptyClass.class);
        assertNotNull(object);
        assertNotNull(object.getProperty());
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsCompatible_2to3")
    void testIsCompatible_2to3(int param1) {
        assertTrue(ReflectUtils.isCompatible(int.class, param1));
    }

    static public Stream<Arguments> Provider_testIsCompatible_2to3() {
        return Stream.of(arguments(1), arguments(1.2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDesc2name_1to8")
    void testDesc2name_1to8(String param1) {
        assertEquals(param1, ReflectUtils.desc2name(ReflectUtils.getDesc(short[].class)));
    }

    static public Stream<Arguments> Provider_testDesc2name_1to8() {
        return Stream.of(arguments("short[]"), arguments("boolean[]"), arguments("byte[]"), arguments("char[]"), arguments("double[]"), arguments("float[]"), arguments("int[]"), arguments("long[]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDesc2name_9to10")
    void testDesc2name_9to10(String param1) {
        assertEquals(param1, ReflectUtils.desc2name(ReflectUtils.getDesc(int.class)));
    }

    static public Stream<Arguments> Provider_testDesc2name_9to10() {
        return Stream.of(arguments("int"), arguments("void"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetGenericClassWithIndex_1to3_5to6")
    void testGetGenericClassWithIndex_1to3_5to6(int param1) {
        assertThat(ReflectUtils.getGenericClass(Foo1.class, param1), sameInstance(String.class));
    }

    static public Stream<Arguments> Provider_testGetGenericClassWithIndex_1to3_5to6() {
        return Stream.of(arguments(0), arguments(1), arguments(0), arguments(0), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetEmptyObject_7to8_11to12")
    void testGetEmptyObject_7to8_11to12(int param1) {
        assertEquals((short) param1, ReflectUtils.getEmptyObject(short.class));
    }

    static public Stream<Arguments> Provider_testGetEmptyObject_7to8_11to12() {
        return Stream.of(arguments(0), arguments(0), arguments(0), arguments(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetEmptyObject_9to10_13")
    void testGetEmptyObject_9to10_13(int param1) {
        assertEquals(param1, ReflectUtils.getEmptyObject(int.class));
    }

    static public Stream<Arguments> Provider_testGetEmptyObject_9to10_13() {
        return Stream.of(arguments(0), arguments(0L), arguments("\0"));
    }
}
