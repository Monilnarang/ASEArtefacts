package org.apache.dubbo.common.utils;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;
import static org.apache.dubbo.common.utils.AnnotationUtils.excludedType;
import static org.apache.dubbo.common.utils.AnnotationUtils.filterDefaultValues;
import static org.apache.dubbo.common.utils.AnnotationUtils.findAnnotation;
import static org.apache.dubbo.common.utils.AnnotationUtils.findMetaAnnotation;
import static org.apache.dubbo.common.utils.AnnotationUtils.findMetaAnnotations;
import static org.apache.dubbo.common.utils.AnnotationUtils.getAllDeclaredAnnotations;
import static org.apache.dubbo.common.utils.AnnotationUtils.getAllMetaAnnotations;
import static org.apache.dubbo.common.utils.AnnotationUtils.getAnnotation;
import static org.apache.dubbo.common.utils.AnnotationUtils.getAttribute;
import static org.apache.dubbo.common.utils.AnnotationUtils.getAttributes;
import static org.apache.dubbo.common.utils.AnnotationUtils.getDeclaredAnnotations;
import static org.apache.dubbo.common.utils.AnnotationUtils.getDefaultValue;
import static org.apache.dubbo.common.utils.AnnotationUtils.getMetaAnnotations;
import static org.apache.dubbo.common.utils.AnnotationUtils.getValue;
import static org.apache.dubbo.common.utils.AnnotationUtils.isAnnotationPresent;
import static org.apache.dubbo.common.utils.AnnotationUtils.isAnyAnnotationPresent;
import static org.apache.dubbo.common.utils.AnnotationUtils.isSameType;
import static org.apache.dubbo.common.utils.AnnotationUtils.isType;
import static org.apache.dubbo.common.utils.MethodUtils.findMethod;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AnnotationUtilsTest_Parameterized {

    @Service(interfaceName = "java.lang.CharSequence", interfaceClass = CharSequence.class)
    @com.alibaba.dubbo.config.annotation.Service(interfaceName = "java.lang.CharSequence", interfaceClass = CharSequence.class)
    @Adaptive(value = { "a", "b", "c" })
    static class A {

        @MyAdaptive("e")
        public void execute() {
        }
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    @Inherited
    @DubboService(interfaceClass = Cloneable.class)
    @interface Service2 {
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    @Inherited
    @Service2
    @interface Service3 {
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    @Inherited
    @Service3
    @interface Service4 {
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    @Inherited
    @Service4
    @interface Service5 {
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE, ElementType.METHOD })
    @Inherited
    @Adaptive
    @interface MyAdaptive {

        String[] value() default {};
    }

    @Service5
    static class B extends A {

        @Adaptive("f")
        @Override
        public void execute() {
        }
    }

    @MyAdaptive
    static class C extends B {
    }

    private void assertADeclaredAnnotations(List<Annotation> annotations, int offset) {
        int size = 3 + offset;
        assertEquals(size, annotations.size());
        boolean apacheServiceFound = false;
        boolean alibabaServiceFound = false;
        boolean adaptiveFound = false;
        for (Annotation annotation : annotations) {
            if (!apacheServiceFound && (annotation instanceof Service)) {
                assertEquals("java.lang.CharSequence", ((Service) annotation).interfaceName());
                assertEquals(CharSequence.class, ((Service) annotation).interfaceClass());
                apacheServiceFound = true;
                continue;
            }
            if (!alibabaServiceFound && (annotation instanceof com.alibaba.dubbo.config.annotation.Service)) {
                assertEquals("java.lang.CharSequence", ((com.alibaba.dubbo.config.annotation.Service) annotation).interfaceName());
                assertEquals(CharSequence.class, ((com.alibaba.dubbo.config.annotation.Service) annotation).interfaceClass());
                alibabaServiceFound = true;
                continue;
            }
            if (!adaptiveFound && (annotation instanceof Adaptive)) {
                assertArrayEquals(new String[] { "a", "b", "c" }, ((Adaptive) annotation).value());
                adaptiveFound = true;
                continue;
            }
        }
        assertTrue(apacheServiceFound && alibabaServiceFound && adaptiveFound);
    }

    @Test
    void testIsType_1() {
        assertFalse(isType(null));
    }

    @Test
    void testIsType_2() {
        assertFalse(isType(findMethod(A.class, "execute")));
    }

    @Test
    void testIsType_3() {
        assertTrue(isType(A.class));
    }

    @Test
    void testIsSameType_1() {
        assertTrue(isSameType(A.class.getAnnotation(Service.class), Service.class));
    }

    @Test
    void testIsSameType_2() {
        assertFalse(isSameType(A.class.getAnnotation(Service.class), Deprecated.class));
    }

    @Test
    void testIsSameType_3() {
        assertFalse(isSameType(A.class.getAnnotation(Service.class), null));
    }

    @Test
    void testIsSameType_4() {
        assertFalse(isSameType(null, Deprecated.class));
    }

    @Test
    void testIsSameType_5() {
        assertFalse(isSameType(null, null));
    }

    @Test
    void testExcludedType_1() {
        assertFalse(excludedType(Service.class).test(A.class.getAnnotation(Service.class)));
    }

    @Test
    void testExcludedType_2() {
        assertTrue(excludedType(Service.class).test(A.class.getAnnotation(Deprecated.class)));
    }

    @Test
    void testIsAnnotationPresent_1() {
        assertTrue(isAnnotationPresent(A.class, true, Service.class));
    }

    @Test
    void testIsAnnotationPresent_2() {
        assertTrue(isAnnotationPresent(A.class, true, Service.class, com.alibaba.dubbo.config.annotation.Service.class));
    }

    @Test
    void testIsAnnotationPresent_3() {
        assertTrue(isAnnotationPresent(A.class, Service.class));
    }

    @Test
    void testIsAnnotationPresent_4() {
        assertTrue(isAnnotationPresent(A.class, "org.apache.dubbo.config.annotation.Service"));
    }

    @Test
    void testIsAnnotationPresent_5() {
        assertTrue(AnnotationUtils.isAllAnnotationPresent(A.class, Service.class, Service.class, com.alibaba.dubbo.config.annotation.Service.class));
    }

    @Test
    void testIsAnnotationPresent_6() {
        assertTrue(isAnnotationPresent(A.class, Deprecated.class));
    }

    @Test
    void testIsAnyAnnotationPresent_1() {
        assertTrue(isAnyAnnotationPresent(A.class, Service.class, com.alibaba.dubbo.config.annotation.Service.class, Deprecated.class));
    }

    @Test
    void testIsAnyAnnotationPresent_2() {
        assertTrue(isAnyAnnotationPresent(A.class, Service.class, com.alibaba.dubbo.config.annotation.Service.class));
    }

    @Test
    void testIsAnyAnnotationPresent_3() {
        assertTrue(isAnyAnnotationPresent(A.class, Service.class, Deprecated.class));
    }

    @Test
    void testIsAnyAnnotationPresent_4() {
        assertTrue(isAnyAnnotationPresent(A.class, com.alibaba.dubbo.config.annotation.Service.class, Deprecated.class));
    }

    @Test
    void testIsAnyAnnotationPresent_5() {
        assertTrue(isAnyAnnotationPresent(A.class, Service.class));
    }

    @Test
    void testIsAnyAnnotationPresent_6() {
        assertTrue(isAnyAnnotationPresent(A.class, com.alibaba.dubbo.config.annotation.Service.class));
    }

    @Test
    void testIsAnyAnnotationPresent_7() {
        assertTrue(isAnyAnnotationPresent(A.class, Deprecated.class));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAnnotation_1to3")
    void testGetAnnotation_1to3(String param1) {
        assertNotNull(getAnnotation(A.class, param1));
    }

    static public Stream<Arguments> Provider_testGetAnnotation_1to3() {
        return Stream.of(arguments("org.apache.dubbo.config.annotation.Service"), arguments("com.alibaba.dubbo.config.annotation.Service"), arguments("org.apache.dubbo.common.extension.Adaptive"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetAnnotation_4to6")
    void testGetAnnotation_4to6(String param1) {
        assertNull(getAnnotation(A.class, param1));
    }

    static public Stream<Arguments> Provider_testGetAnnotation_4to6() {
        return Stream.of(arguments("java.lang.Deprecated"), arguments("java.lang.String"), arguments("NotExistedClass"));
    }
}
