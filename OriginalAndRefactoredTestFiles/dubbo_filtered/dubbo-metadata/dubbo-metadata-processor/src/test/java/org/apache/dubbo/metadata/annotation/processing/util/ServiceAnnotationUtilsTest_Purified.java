package org.apache.dubbo.metadata.annotation.processing.util;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.tools.DefaultTestService;
import org.apache.dubbo.metadata.tools.GenericTestService;
import org.apache.dubbo.metadata.tools.TestService;
import org.apache.dubbo.metadata.tools.TestServiceImpl;
import javax.lang.model.element.TypeElement;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.DUBBO_SERVICE_ANNOTATION_TYPE;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.GROUP_ATTRIBUTE_NAME;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.INTERFACE_CLASS_ATTRIBUTE_NAME;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.INTERFACE_NAME_ATTRIBUTE_NAME;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.LEGACY_SERVICE_ANNOTATION_TYPE;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.SERVICE_ANNOTATION_TYPE;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.SUPPORTED_ANNOTATION_TYPES;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.VERSION_ATTRIBUTE_NAME;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.getAnnotation;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.getGroup;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.getVersion;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.isServiceAnnotationPresent;
import static org.apache.dubbo.metadata.annotation.processing.util.ServiceAnnotationUtils.resolveServiceInterfaceName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceAnnotationUtilsTest_Purified extends AbstractAnnotationProcessingTest {

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
    }

    @Override
    protected void beforeEach() {
    }

    @Test
    void testConstants_1() {
        assertEquals("org.apache.dubbo.config.annotation.DubboService", DUBBO_SERVICE_ANNOTATION_TYPE);
    }

    @Test
    void testConstants_2() {
        assertEquals("org.apache.dubbo.config.annotation.Service", SERVICE_ANNOTATION_TYPE);
    }

    @Test
    void testConstants_3() {
        assertEquals("com.alibaba.dubbo.config.annotation.Service", LEGACY_SERVICE_ANNOTATION_TYPE);
    }

    @Test
    void testConstants_4() {
        assertEquals("interfaceClass", INTERFACE_CLASS_ATTRIBUTE_NAME);
    }

    @Test
    void testConstants_5() {
        assertEquals("interfaceName", INTERFACE_NAME_ATTRIBUTE_NAME);
    }

    @Test
    void testConstants_6() {
        assertEquals("group", GROUP_ATTRIBUTE_NAME);
    }

    @Test
    void testConstants_7() {
        assertEquals("version", VERSION_ATTRIBUTE_NAME);
    }

    @Test
    void testConstants_8() {
        assertEquals(new LinkedHashSet<>(asList("org.apache.dubbo.config.annotation.DubboService", "org.apache.dubbo.config.annotation.Service", "com.alibaba.dubbo.config.annotation.Service")), SUPPORTED_ANNOTATION_TYPES);
    }

    @Test
    void testIsServiceAnnotationPresent_1() {
        assertTrue(isServiceAnnotationPresent(getType(TestServiceImpl.class)));
    }

    @Test
    void testIsServiceAnnotationPresent_2() {
        assertTrue(isServiceAnnotationPresent(getType(GenericTestService.class)));
    }

    @Test
    void testIsServiceAnnotationPresent_3() {
        assertTrue(isServiceAnnotationPresent(getType(DefaultTestService.class)));
    }

    @Test
    void testIsServiceAnnotationPresent_4() {
        assertFalse(isServiceAnnotationPresent(getType(TestService.class)));
    }
}
