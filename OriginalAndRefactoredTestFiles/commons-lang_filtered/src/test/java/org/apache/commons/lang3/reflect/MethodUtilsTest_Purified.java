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

public class MethodUtilsTest_Purified extends AbstractLangTest {

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
    public void testGetAnnotationNotSearchSupersAndNotIgnoreAccess_1() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("parentNotAnnotatedMethod"), Annotated.class, false, false));
    }

    @Test
    public void testGetAnnotationNotSearchSupersAndNotIgnoreAccess_2() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("doIt"), Annotated.class, false, false));
    }

    @Test
    public void testGetAnnotationNotSearchSupersAndNotIgnoreAccess_3() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("parentProtectedAnnotatedMethod"), Annotated.class, false, false));
    }

    @Test
    public void testGetAnnotationNotSearchSupersAndNotIgnoreAccess_4() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getDeclaredMethod("privateAnnotatedMethod"), Annotated.class, false, false));
    }

    @Test
    public void testGetAnnotationNotSearchSupersAndNotIgnoreAccess_5() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("publicAnnotatedMethod"), Annotated.class, false, false));
    }

    @Test
    public void testGetAnnotationNotSearchSupersButIgnoreAccess_1() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("parentNotAnnotatedMethod"), Annotated.class, false, true));
    }

    @Test
    public void testGetAnnotationNotSearchSupersButIgnoreAccess_2() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("doIt"), Annotated.class, false, true));
    }

    @Test
    public void testGetAnnotationNotSearchSupersButIgnoreAccess_3() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("parentProtectedAnnotatedMethod"), Annotated.class, false, true));
    }

    @Test
    public void testGetAnnotationNotSearchSupersButIgnoreAccess_4() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getDeclaredMethod("privateAnnotatedMethod"), Annotated.class, false, true));
    }

    @Test
    public void testGetAnnotationNotSearchSupersButIgnoreAccess_5() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("publicAnnotatedMethod"), Annotated.class, false, true));
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_1() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("parentNotAnnotatedMethod"), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_2() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("doIt"), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_3() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("parentProtectedAnnotatedMethod"), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_4() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getDeclaredMethod("privateAnnotatedMethod"), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_5() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("publicAnnotatedMethod"), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_6() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getMethod("parentNotAnnotatedMethod", String.class), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_7() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getMethod("parentProtectedAnnotatedMethod", String.class), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_8() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getDeclaredMethod("privateAnnotatedMethod", String.class), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersAndIgnoreAccess_9() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getMethod("publicAnnotatedMethod", String.class), Annotated.class, true, true));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_1() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("parentNotAnnotatedMethod"), Annotated.class, true, false));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_2() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("doIt"), Annotated.class, true, false));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_3() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("parentProtectedAnnotatedMethod"), Annotated.class, true, false));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_4() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(PublicChild.class.getDeclaredMethod("privateAnnotatedMethod"), Annotated.class, true, false));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_5() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(PublicChild.class.getMethod("publicAnnotatedMethod"), Annotated.class, true, false));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_6() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getMethod("parentNotAnnotatedMethod", String.class), Annotated.class, true, false));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_7() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getMethod("parentProtectedAnnotatedMethod", String.class), Annotated.class, true, false));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_8() throws NoSuchMethodException {
        assertNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getDeclaredMethod("privateAnnotatedMethod", String.class), Annotated.class, true, false));
    }

    @Test
    public void testGetAnnotationSearchSupersButNotIgnoreAccess_9() throws NoSuchMethodException {
        assertNotNull(MethodUtils.getAnnotation(StringParameterizedChild.class.getMethod("publicAnnotatedMethod", String.class), Annotated.class, true, false));
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
    public void testInvokeJavaVarargsOverloadingResolution_1() throws Exception {
        assertEquals("Byte...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", (byte) 1, (byte) 2));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_2() throws Exception {
        assertEquals("Short...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", (short) 1, (short) 2));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_3() throws Exception {
        assertEquals("Integer...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1, 2));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_4() throws Exception {
        assertEquals("Long...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1L, 2L));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_5() throws Exception {
        assertEquals("Float...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1f, 2f));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_6() throws Exception {
        assertEquals("Double...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1d, 2d));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_7() throws Exception {
        assertEquals("Character...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 'a', 'b'));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_8() throws Exception {
        assertEquals("String...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", "a", "b"));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_9() throws Exception {
        assertEquals("Boolean...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", true, false));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_10() throws Exception {
        assertEquals("Object...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1, "s"));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_11() throws Exception {
        assertEquals("Object...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1, true));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_12() throws Exception {
        assertEquals("Object...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1.1, true));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_13() throws Exception {
        assertEquals("Object...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 'c', true));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_14() throws Exception {
        assertEquals("Number...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1, 1.1));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_15() throws Exception {
        assertEquals("Number...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1, 1L));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_16() throws Exception {
        assertEquals("Number...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1d, 1f));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_17() throws Exception {
        assertEquals("Number...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", (short) 1, (byte) 1));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_18() throws Exception {
        assertEquals("Object...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 1, 'c'));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_19() throws Exception {
        assertEquals("Object...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", 'c', "s"));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_20() throws Exception {
        assertEquals("Object...", MethodUtils.invokeStaticMethod(TestBean.class, "varOverload", (Object[]) ArrayUtils.EMPTY_CLASS_ARRAY));
    }

    @Test
    public void testInvokeJavaVarargsOverloadingResolution_21() throws Exception {
        assertEquals("Number...", MethodUtils.invokeStaticMethod(TestBean.class, "numOverload", (Object[]) ArrayUtils.EMPTY_CLASS_ARRAY));
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
    public void verifyJavaVarargsOverloadingResolution_1() {
        assertEquals("Byte...", TestBean.varOverload((byte) 1, (byte) 2));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_2() {
        assertEquals("Short...", TestBean.varOverload((short) 1, (short) 2));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_3() {
        assertEquals("Integer...", TestBean.varOverload(1, 2));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_4() {
        assertEquals("Long...", TestBean.varOverload(1L, 2L));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_5() {
        assertEquals("Float...", TestBean.varOverload(1f, 2f));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_6() {
        assertEquals("Double...", TestBean.varOverload(1d, 2d));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_7() {
        assertEquals("Character...", TestBean.varOverload('a', 'b'));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_8() {
        assertEquals("String...", TestBean.varOverload("a", "b"));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_9() {
        assertEquals("Boolean...", TestBean.varOverload(true, false));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_10() {
        assertEquals("Object...", TestBean.varOverload(1, "s"));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_11() {
        assertEquals("Object...", TestBean.varOverload(1, true));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_12() {
        assertEquals("Object...", TestBean.varOverload(1.1, true));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_13() {
        assertEquals("Object...", TestBean.varOverload('c', true));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_14() {
        assertEquals("Number...", TestBean.varOverload(1, 1.1));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_15() {
        assertEquals("Number...", TestBean.varOverload(1, 1L));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_16() {
        assertEquals("Number...", TestBean.varOverload(1d, 1f));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_17() {
        assertEquals("Number...", TestBean.varOverload((short) 1, (byte) 1));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_18() {
        assertEquals("Object...", TestBean.varOverload(1, 'c'));
    }

    @Test
    public void verifyJavaVarargsOverloadingResolution_19() {
        assertEquals("Object...", TestBean.varOverload('c', "s"));
    }
}
