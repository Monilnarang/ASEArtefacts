package org.apache.dubbo.metadata.annotation.processing.util;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.annotation.processing.model.Model;
import org.apache.dubbo.metadata.tools.TestService;
import org.apache.dubbo.metadata.tools.TestServiceImpl;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.metadata.annotation.processing.util.MethodUtils.findMethod;
import static org.apache.dubbo.metadata.annotation.processing.util.MethodUtils.getAllDeclaredMethods;
import static org.apache.dubbo.metadata.annotation.processing.util.MethodUtils.getDeclaredMethods;
import static org.apache.dubbo.metadata.annotation.processing.util.MethodUtils.getMethodName;
import static org.apache.dubbo.metadata.annotation.processing.util.MethodUtils.getMethodParameterTypes;
import static org.apache.dubbo.metadata.annotation.processing.util.MethodUtils.getOverrideMethod;
import static org.apache.dubbo.metadata.annotation.processing.util.MethodUtils.getPublicNonStaticMethods;
import static org.apache.dubbo.metadata.annotation.processing.util.MethodUtils.getReturnType;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MethodUtilsTest_Purified extends AbstractAnnotationProcessingTest {

    private TypeElement testType;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
    }

    @Override
    protected void beforeEach() {
        testType = getType(TestServiceImpl.class);
    }

    private List<? extends ExecutableElement> doGetAllDeclaredMethods() {
        return getAllDeclaredMethods(testType, Object.class);
    }

    @Test
    void testGetMethodName_1() {
        ExecutableElement method = findMethod(testType, "echo", "java.lang.String");
        assertEquals("echo", getMethodName(method));
    }

    @Test
    void testGetMethodName_2() {
        assertNull(getMethodName(null));
    }

    @Test
    void testReturnType_1() {
        ExecutableElement method = findMethod(testType, "echo", "java.lang.String");
        assertEquals("java.lang.String", getReturnType(method));
    }

    @Test
    void testReturnType_2() {
        assertNull(getReturnType(null));
    }
}
