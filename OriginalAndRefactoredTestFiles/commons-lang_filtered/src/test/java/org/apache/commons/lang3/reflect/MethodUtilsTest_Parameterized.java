package org.apache.commons.lang3.reflect;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ClassUtils.Interfaces;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.reflect.testbed.Annotated;
import org.apache.commons.lang3.reflect.testbed.GenericConsumer;
import org.apache.commons.lang3.reflect.testbed.GenericParent;
import org.apache.commons.lang3.reflect.testbed.PublicChild;
import org.apache.commons.lang3.reflect.testbed.StringParameterizedChild;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MethodUtilsTest_Parameterized extends AbstractLangTest {

    protected abstract static class AbstractGetMatchingMethod implements InterfaceGetMatchingMethod {

        public abstract void testMethod5(Exception exception);
    }

    interface ChildInterface {
    }

    public static class ChildObject extends ParentObject implements ChildInterface {
    }

    private static final class GetMatchingMethodClass {

        public void testMethod() {
        }

        public void testMethod(final long aLong) {
        }

        public void testMethod(final Long aLong) {
        }

        public void testMethod2(final Color aColor) {
        }

        public void testMethod2(final long aLong) {
        }

        public void testMethod2(final Long aLong) {
        }

        public void testMethod3(final long aLong, final Long anotherLong) {
        }

        public void testMethod3(final Long aLong, final long anotherLong) {
        }

        public void testMethod3(final Long aLong, final Long anotherLong) {
        }

        public void testMethod4(final Color aColor1, final Color aColor2) {
        }

        public void testMethod4(final Long aLong, final Long anotherLong) {
        }
    }

    private static final class GetMatchingMethodImpl extends AbstractGetMatchingMethod {

        @Override
        public void testMethod5(final Exception exception) {
        }
    }

    public static class GrandParentObject {
    }

    public static class InheritanceBean {

        public void testOne(final GrandParentObject obj) {
        }

        public void testOne(final Object obj) {
        }

        public void testOne(final ParentObject obj) {
        }

        public void testTwo(final ChildInterface obj) {
        }

        public void testTwo(final GrandParentObject obj) {
        }

        public void testTwo(final Object obj) {
        }
    }

    interface InterfaceGetMatchingMethod {

        default void testMethod6() {
        }
    }

    private static final class MethodDescriptor {

        final Class<?> declaringClass;

        final String name;

        final Type[] parameterTypes;

        MethodDescriptor(final Class<?> declaringClass, final String name, final Type... parameterTypes) {
            this.declaringClass = declaringClass;
            this.name = name;
            this.parameterTypes = parameterTypes;
        }
    }

    public static class ParentObject extends GrandParentObject {
    }

    private interface PrivateInterface {
    }

    public static class TestBean {

        public static String bar() {
            return "bar()";
        }

        public static String bar(final double d) {
            return "bar(double)";
        }

        public static String bar(final int i) {
            return "bar(int)";
        }

        public static String bar(final Integer i) {
            return "bar(Integer)";
        }

        public static String bar(final Integer i, final String... s) {
            return "bar(int, String...)";
        }

        public static String bar(final long... s) {
            return "bar(long...)";
        }

        public static String bar(final Object o) {
            return "bar(Object)";
        }

        public static String bar(final String s) {
            return "bar(String)";
        }

        public static String bar(final String... s) {
            return "bar(String...)";
        }

        public static String numOverload(final Byte... args) {
            return "Byte...";
        }

        public static String numOverload(final Double... args) {
            return "Double...";
        }

        public static String numOverload(final Float... args) {
            return "Float...";
        }

        public static String numOverload(final Integer... args) {
            return "Integer...";
        }

        public static String numOverload(final Long... args) {
            return "Long...";
        }

        public static String numOverload(final Number... args) {
            return "Number...";
        }

        public static String numOverload(final Short... args) {
            return "Short...";
        }

        public static void oneParameterStatic(final String s) {
        }

        public static String varOverload(final Boolean... args) {
            return "Boolean...";
        }

        public static String varOverload(final Byte... args) {
            return "Byte...";
        }

        public static String varOverload(final Character... args) {
            return "Character...";
        }

        public static String varOverload(final Double... args) {
            return "Double...";
        }

        public static String varOverload(final Float... args) {
            return "Float...";
        }

        public static String varOverload(final Integer... args) {
            return "Integer...";
        }

        public static String varOverload(final Long... args) {
            return "Long...";
        }

        public static String varOverload(final Number... args) {
            return "Number...";
        }

        public static String varOverload(final Object... args) {
            return "Object...";
        }

        public static String varOverload(final Short... args) {
            return "Short...";
        }

        public static String varOverload(final String... args) {
            return "String...";
        }

        public static ImmutablePair<String, Object[]> varOverloadEchoStatic(final Number... args) {
            return new ImmutablePair<>("Number...", args);
        }

        public static ImmutablePair<String, Object[]> varOverloadEchoStatic(final String... args) {
            return new ImmutablePair<>("String...", args);
        }

        static void verify(final ImmutablePair<String, Object[]> a, final ImmutablePair<String, Object[]> b) {
            assertEquals(a.getLeft(), b.getLeft());
            assertArrayEquals(a.getRight(), b.getRight());
        }

        static void verify(final ImmutablePair<String, Object[]> a, final Object obj) {
            @SuppressWarnings("unchecked")
            final ImmutablePair<String, Object[]> pair = (ImmutablePair<String, Object[]>) obj;
            verify(a, pair);
        }

        public String foo() {
            return "foo()";
        }

        public String foo(final double d) {
            return "foo(double)";
        }

        public String foo(final int i) {
            return "foo(int)";
        }

        public String foo(final Integer i) {
            return "foo(Integer)";
        }

        public String foo(final Integer i, final String... s) {
            return "foo(int, String...)";
        }

        public String foo(final long l) {
            return "foo(long)";
        }

        public String foo(final long... l) {
            return "foo(long...)";
        }

        public String foo(final Object o) {
            return "foo(Object)";
        }

        public String foo(final Object... s) {
            return "foo(Object...)";
        }

        public String foo(final String s) {
            return "foo(String)";
        }

        public String foo(final String... s) {
            return "foo(String...)";
        }

        public void oneParameter(final String s) {
        }

        @SuppressWarnings("unused")
        private String privateStringStuff() {
            return "privateStringStuff()";
        }

        @SuppressWarnings("unused")
        private String privateStringStuff(final double d) {
            return "privateStringStuff(double)";
        }

        @SuppressWarnings("unused")
        private String privateStringStuff(final int i) {
            return "privateStringStuff(int)";
        }

        @SuppressWarnings("unused")
        private String privateStringStuff(final Integer i) {
            return "privateStringStuff(Integer)";
        }

        @SuppressWarnings("unused")
        private String privateStringStuff(final Object s) {
            return "privateStringStuff(Object)";
        }

        @SuppressWarnings("unused")
        private String privateStringStuff(final String s) {
            return "privateStringStuff(String)";
        }

        @SuppressWarnings("unused")
        private void privateStuff() {
        }

        public int[] unboxing(final int... values) {
            return values;
        }

        public ImmutablePair<String, Object[]> varOverloadEcho(final Number... args) {
            return new ImmutablePair<>("Number...", args);
        }

        public ImmutablePair<String, Object[]> varOverloadEcho(final String... args) {
            return new ImmutablePair<>("String...", args);
        }
    }

    static class TestBeanWithInterfaces implements PrivateInterface {

        public String foo() {
            return "foo()";
        }
    }

    private static final class TestMutable implements Mutable<Object> {

        @Override
        public Object getValue() {
            return null;
        }

        @Override
        public void setValue(final Object value) {
        }
    }

    private TestBean testBean;

    private final Map<Class<?>, Class<?>[]> classCache = new HashMap<>();

    private void expectMatchingAccessibleMethodParameterTypes(final Class<?> cls, final String methodName, final Class<?>[] requestTypes, final Class<?>[] actualTypes) {
        final Method m = MethodUtils.getMatchingAccessibleMethod(cls, methodName, requestTypes);
        assertNotNull(m, "could not find any matches for " + methodName + " (" + (requestTypes == null ? null : toString(requestTypes)) + ")");
        assertArrayEquals(actualTypes, m.getParameterTypes(), toString(m.getParameterTypes()) + " not equals " + toString(actualTypes));
    }

    @BeforeEach
    public void setUp() {
        testBean = new TestBean();
        classCache.clear();
    }

    private Class<?>[] singletonArray(final Class<?> c) {
        Class<?>[] result = classCache.get(c);
        if (result == null) {
            result = new Class[] { c };
            classCache.put(c, result);
        }
        return result;
    }

    private String toString(final Class<?>[] c) {
        return Arrays.asList(c).toString();
    }

    @Test
    public void testGetAccessibleMethodPrivateInterface_1() throws Exception {
        final Method expected = TestBeanWithInterfaces.class.getMethod("foo");
        assertNotNull(expected);
    }

    @Test
    public void testGetAccessibleMethodPrivateInterface_2() throws Exception {
        final Method actual = MethodUtils.getAccessibleMethod(TestBeanWithInterfaces.class, "foo");
        assertNull(actual);
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_8() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getDeclaredMethod("privateAnnotatedMethod", String.class), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_8() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getDeclaredMethod("privateAnnotatedMethod", String.class), Annotated.class, true, false));
    }

    @Test
    public void testGetMethodObject_1() throws Exception {
        assertEquals(MutableObject.class.getMethod("getValue", ArrayUtils.EMPTY_CLASS_ARRAY), MethodUtils.getMethodObject(MutableObject.class, "getValue", ArrayUtils.EMPTY_CLASS_ARRAY));
    }

    @Test
    public void testGetMethodObject_2() throws Exception {
        assertNull(MethodUtils.getMethodObject(MutableObject.class, "does not exist, at all", ArrayUtils.EMPTY_CLASS_ARRAY));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_9() throws Exception {
        assertEquals("Boolean...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", true, false));
    }

    @Test
    public void testInvokeMethod_VarArgsWithNullValues_1() throws Exception {
        assertEquals("String...", MethodUtils.invokeMethod(testBean, "varOverload", "a", null, "c"));
    }

    @Test
    public void testInvokeMethod_VarArgsWithNullValues_2() throws Exception {
        assertEquals("String...", MethodUtils.invokeMethod(testBean, "varOverload", "a", "b", null));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_9() {
        assertEquals("Boolean...", TestBean.varOverload(true, false));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAnnotationNotSearchSupersAndNotIgnoreAccess_1_1_1_1to2_2_2to3_3_3")
    public void testGetAnnotationNotSearchSupersAndNotIgnoreAccess_1_1_1_1to2_2_2to3_3_3(String param1) throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("parentNotAnnotatedMethod"), Annotated.class, param1, false));
    }

    static public Stream<Arguments> Provider_testGetAnnotationNotSearchSupersAndNotIgnoreAccess_1_1_1_1to2_2_2to3_3_3() {
        return Stream.of(arguments("parentNotAnnotatedMethod"), arguments("doIt"), arguments("parentProtectedAnnotatedMethod"), arguments("parentNotAnnotatedMethod"), arguments("doIt"), arguments("parentProtectedAnnotatedMethod"), arguments("parentNotAnnotatedMethod"), arguments("parentNotAnnotatedMethod"), arguments("doIt"), arguments("parentProtectedAnnotatedMethod"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAnnotationNotSearchSupersAndNotIgnoreAccess_4_4")
    public void testGetAnnotationNotSearchSupersAndNotIgnoreAccess_4_4(String param1) throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getDeclaredMethod("privateAnnotatedMethod"), Annotated.class, param1, false));
    }

    static public Stream<Arguments> Provider_testGetAnnotationNotSearchSupersAndNotIgnoreAccess_4_4() {
        return Stream.of(arguments("privateAnnotatedMethod"), arguments("privateAnnotatedMethod"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAnnotationNotSearchSupersAndNotIgnoreAccess_2to3_5_5_5_5")
    public void testGetAnnotationNotSearchSupersAndNotIgnoreAccess_2to3_5_5_5_5(String param1) throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("publicAnnotatedMethod"), Annotated.class, param1, false));
    }

    static public Stream<Arguments> Provider_testGetAnnotationNotSearchSupersAndNotIgnoreAccess_2to3_5_5_5_5() {
        return Stream.of(arguments("publicAnnotatedMethod"), arguments("publicAnnotatedMethod"), arguments("doIt"), arguments("parentProtectedAnnotatedMethod"), arguments("publicAnnotatedMethod"), arguments("publicAnnotatedMethod"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAnnotationNotSearchSupersButIgnoreAccess_4_4")
    public void testGetAnnotationNotSearchSupersButIgnoreAccess_4_4(String param1) throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getDeclaredMethod("privateAnnotatedMethod"), Annotated.class, param1, true));
    }

    static public Stream<Arguments> Provider_testGetAnnotationNotSearchSupersButIgnoreAccess_4_4() {
        return Stream.of(arguments("privateAnnotatedMethod"), arguments("privateAnnotatedMethod"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAnnotationSearchSupersAndIgnoreAccess_6_6to7")
    public void testGetAnnotationSearchSupersAndIgnoreAccess_6_6to7(String param1) throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getMethod("parentNotAnnotatedMethod", String.class), Annotated.class, param1, true));
    }

    static public Stream<Arguments> Provider_testGetAnnotationSearchSupersAndIgnoreAccess_6_6to7() {
        return Stream.of(arguments("parentNotAnnotatedMethod"), arguments("parentNotAnnotatedMethod"), arguments("parentProtectedAnnotatedMethod"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAnnotationSearchSupersAndIgnoreAccess_7_9_9")
    public void testGetAnnotationSearchSupersAndIgnoreAccess_7_9_9(String param1) throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getMethod("parentProtectedAnnotatedMethod", String.class), Annotated.class, param1, true));
    }

    static public Stream<Arguments> Provider_testGetAnnotationSearchSupersAndIgnoreAccess_7_9_9() {
        return Stream.of(arguments("parentProtectedAnnotatedMethod"), arguments("publicAnnotatedMethod"), arguments("publicAnnotatedMethod"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvokeJavaVarargsOverloadingResolution_1to2_17")
    public void testInvokeJavaVarargsOverloadingResolution_1to2_17(String param1, String param2, int param3, int param4) throws Exception {
        assertEquals(param1, MethodUtils.invokeStaticMethod(TestBean.class, param2, (byte) param3, (byte) param4));
    }

    static public Stream<Arguments> Provider_testInvokeJavaVarargsOverloadingResolution_1to2_17() {
        return Stream.of(arguments("Byte...", "varOverload", 1, 2), arguments("Short...", "varOverload", 1, 2), arguments("Number...", "varOverload", 1, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvokeJavaVarargsOverloadingResolution_3to8_10_14to16_18to19")
    public void testInvokeJavaVarargsOverloadingResolution_3to8_10_14to16_18to19(String param1, String param2, int param3, int param4) throws Exception {
        assertEquals(param1, MethodUtils.invokeStaticMethod(TestBean.class, param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testInvokeJavaVarargsOverloadingResolution_3to8_10_14to16_18to19() {
        return Stream.of(arguments("Integer...", "varOverload", 1, 2), arguments("Long...", "varOverload", 1L, 2L), arguments("Float...", "varOverload", 1f, 2f), arguments("Double...", "varOverload", 1d, 2d), arguments("Character...", "varOverload", "a", "b"), arguments("String...", "varOverload", "a", "b"), arguments("Object...", "varOverload", 1, "s"), arguments("Number...", "varOverload", 1, 1.1), arguments("Number...", "varOverload", 1, 1L), arguments("Number...", "varOverload", 1d, 1f), arguments("Object...", "varOverload", 1, "c"), arguments("Object...", "varOverload", "c", "s"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvokeJavaVarargsOverloadingResolution_11to13")
    public void testInvokeJavaVarargsOverloadingResolution_11to13(String param1, String param2, int param3) throws Exception {
        assertEquals(param1, MethodUtils.invokeStaticMethod(TestBean.class, param2, param3, true));
    }

    static public Stream<Arguments> Provider_testInvokeJavaVarargsOverloadingResolution_11to13() {
        return Stream.of(arguments("Object...", "varOverload", 1), arguments("Object...", "varOverload", 1.1), arguments("Object...", "varOverload", "c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvokeJavaVarargsOverloadingResolution_20to21")
    public void testInvokeJavaVarargsOverloadingResolution_20to21(String param1, String param2) throws Exception {
        assertEquals(param1, MethodUtils.invokeStaticMethod(TestBean.class, param2, (Object[]) ArrayUtils.EMPTY_CLASS_ARRAY));
    }

    static public Stream<Arguments> Provider_testInvokeJavaVarargsOverloadingResolution_20to21() {
        return Stream.of(arguments("Object...", "varOverload"), arguments("Number...", "numOverload"));
    }

    @ParameterizedTest
    @MethodSource("Provider_verifyJavaVarargsOverloadingResolution_1to2_17")
    public void verifyJavaVarargsOverloadingResolution_1to2_17(String param1, int param2, int param3) {
        assertEquals(param1, TestBean.varOverload((byte) param2, (byte) param3));
    }

    static public Stream<Arguments> Provider_verifyJavaVarargsOverloadingResolution_1to2_17() {
        return Stream.of(arguments("Byte...", 1, 2), arguments("Short...", 1, 2), arguments("Number...", 1, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_verifyJavaVarargsOverloadingResolution_3to8_10_14to16_18to19")
    public void verifyJavaVarargsOverloadingResolution_3to8_10_14to16_18to19(String param1, int param2, int param3) {
        assertEquals(param1, TestBean.varOverload(param2, param3));
    }

    static public Stream<Arguments> Provider_verifyJavaVarargsOverloadingResolution_3to8_10_14to16_18to19() {
        return Stream.of(arguments("Integer...", 1, 2), arguments("Long...", 1L, 2L), arguments("Float...", 1f, 2f), arguments("Double...", 1d, 2d), arguments("Character...", "a", "b"), arguments("String...", "a", "b"), arguments("Object...", 1, "s"), arguments("Number...", 1, 1.1), arguments("Number...", 1, 1L), arguments("Number...", 1d, 1f), arguments("Object...", 1, "c"), arguments("Object...", "c", "s"));
    }

    @ParameterizedTest
    @MethodSource("Provider_verifyJavaVarargsOverloadingResolution_11to13")
    public void verifyJavaVarargsOverloadingResolution_11to13(String param1, int param2) {
        assertEquals(param1, TestBean.varOverload(param2, true));
    }

    static public Stream<Arguments> Provider_verifyJavaVarargsOverloadingResolution_11to13() {
        return Stream.of(arguments("Object...", 1), arguments("Object...", 1.1), arguments("Object...", "c"));
    }
}
