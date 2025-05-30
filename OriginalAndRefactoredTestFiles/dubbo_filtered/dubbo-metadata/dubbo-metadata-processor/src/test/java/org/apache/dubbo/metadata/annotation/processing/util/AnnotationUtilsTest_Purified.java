package org.apache.dubbo.metadata.annotation.processing.util;

import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.tools.TestService;
import org.apache.dubbo.metadata.tools.TestServiceImpl;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.ws.rs.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.metadata.annotation.processing.util.AnnotationUtils.findAnnotation;
import static org.apache.dubbo.metadata.annotation.processing.util.AnnotationUtils.findMetaAnnotation;
import static org.apache.dubbo.metadata.annotation.processing.util.AnnotationUtils.getAllAnnotations;
import static org.apache.dubbo.metadata.annotation.processing.util.AnnotationUtils.getAnnotation;
import static org.apache.dubbo.metadata.annotation.processing.util.AnnotationUtils.getAnnotations;
import static org.apache.dubbo.metadata.annotation.processing.util.AnnotationUtils.getAttribute;
import static org.apache.dubbo.metadata.annotation.processing.util.AnnotationUtils.getValue;
import static org.apache.dubbo.metadata.annotation.processing.util.AnnotationUtils.isAnnotationPresent;
import static org.apache.dubbo.metadata.annotation.processing.util.MethodUtils.getAllDeclaredMethods;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnnotationUtilsTest_Purified extends AbstractAnnotationProcessingTest {

    private TypeElement testType;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
    }

    @Override
    protected void beforeEach() {
        testType = getType(TestServiceImpl.class);
    }

    @Test
    void testGetAnnotation_1_testMerged_1() {
        AnnotationMirror serviceAnnotation = getAnnotation(testType, Service.class);
        assertEquals("3.0.0", getAttribute(serviceAnnotation, "version"));
        assertEquals("test", getAttribute(serviceAnnotation, "group"));
        assertEquals("org.apache.dubbo.metadata.tools.TestService", getAttribute(serviceAnnotation, "interfaceName"));
        assertNull(getAnnotation(testType, (Class) null));
        assertNull(getAnnotation(testType, (String) null));
        assertNull(getAnnotation(testType.asType(), (Class) null));
        assertNull(getAnnotation(testType.asType(), (String) null));
    }

    @Test
    void testGetAnnotation_8() {
        assertNull(getAnnotation((Element) null, (Class) null));
    }

    @Test
    void testGetAnnotation_9() {
        assertNull(getAnnotation((Element) null, (String) null));
    }

    @Test
    void testGetAnnotation_10() {
        assertNull(getAnnotation((TypeElement) null, (Class) null));
    }

    @Test
    void testGetAnnotation_11() {
        assertNull(getAnnotation((TypeElement) null, (String) null));
    }

    @Test
    void testFindAnnotation_1() {
        assertEquals("org.apache.dubbo.config.annotation.Service", findAnnotation(testType, Service.class).getAnnotationType().toString());
    }

    @Test
    void testFindAnnotation_2() {
        assertEquals("javax.ws.rs.Path", findAnnotation(testType, Path.class).getAnnotationType().toString());
    }

    @Test
    void testFindAnnotation_3() {
        assertEquals("javax.ws.rs.Path", findAnnotation(testType.asType(), Path.class).getAnnotationType().toString());
    }

    @Test
    void testFindAnnotation_4() {
        assertEquals("javax.ws.rs.Path", findAnnotation(testType.asType(), Path.class.getTypeName()).getAnnotationType().toString());
    }

    @Test
    void testFindAnnotation_5() {
        assertNull(findAnnotation(testType, Override.class));
    }

    @Test
    void testFindAnnotation_6() {
        assertNull(findAnnotation((Element) null, (Class) null));
    }

    @Test
    void testFindAnnotation_7() {
        assertNull(findAnnotation((Element) null, (String) null));
    }

    @Test
    void testFindAnnotation_8() {
        assertNull(findAnnotation((TypeMirror) null, (Class) null));
    }

    @Test
    void testFindAnnotation_9() {
        assertNull(findAnnotation((TypeMirror) null, (String) null));
    }

    @Test
    void testFindAnnotation_10() {
        assertNull(findAnnotation(testType, (Class) null));
    }

    @Test
    void testFindAnnotation_11() {
        assertNull(findAnnotation(testType, (String) null));
    }

    @Test
    void testFindAnnotation_12() {
        assertNull(findAnnotation(testType.asType(), (Class) null));
    }

    @Test
    void testFindAnnotation_13() {
        assertNull(findAnnotation(testType.asType(), (String) null));
    }

    @Test
    void testGetAttribute_1() {
        assertEquals("org.apache.dubbo.metadata.tools.TestService", getAttribute(findAnnotation(testType, Service.class), "interfaceName"));
    }

    @Test
    void testGetAttribute_2() {
        assertEquals("org.apache.dubbo.metadata.tools.TestService", getAttribute(findAnnotation(testType, Service.class).getElementValues(), "interfaceName"));
    }

    @Test
    void testGetAttribute_3() {
        assertEquals("/echo", getAttribute(findAnnotation(testType, Path.class), "value"));
    }

    @Test
    void testGetAttribute_4() {
        assertNull(getAttribute(findAnnotation(testType, Path.class), null));
    }

    @Test
    void testGetAttribute_5() {
        assertNull(getAttribute(findAnnotation(testType, (Class) null), null));
    }

    @Test
    void testIsAnnotationPresent_1() {
        assertTrue(isAnnotationPresent(testType, "org.apache.dubbo.config.annotation.Service"));
    }

    @Test
    void testIsAnnotationPresent_2() {
        assertTrue(isAnnotationPresent(testType, "javax.ws.rs.Path"));
    }
}
