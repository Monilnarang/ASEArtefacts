package org.apache.dubbo.metadata.annotation.processing.util;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.annotation.processing.model.ArrayTypeModel;
import org.apache.dubbo.metadata.annotation.processing.model.Color;
import org.apache.dubbo.metadata.annotation.processing.model.Model;
import org.apache.dubbo.metadata.annotation.processing.model.PrimitiveTypeModel;
import org.apache.dubbo.metadata.tools.DefaultTestService;
import org.apache.dubbo.metadata.tools.GenericTestService;
import org.apache.dubbo.metadata.tools.TestServiceImpl;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.File;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.findField;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.getDeclaredFields;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.getAllInterfaces;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.getAllSuperTypes;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.getHierarchicalTypes;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.getInterfaces;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.getResource;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.getResourceName;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.getSuperType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isAnnotationType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isArrayType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isClassType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isDeclaredType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isEnumType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isInterfaceType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isPrimitiveType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isSameType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isSimpleType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.isTypeElement;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.listDeclaredTypes;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.listTypeElements;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.ofDeclaredType;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.ofDeclaredTypes;
import static org.apache.dubbo.metadata.annotation.processing.util.TypeUtils.ofTypeElement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TypeUtilsTest_Purified extends AbstractAnnotationProcessingTest {

    private TypeElement testType;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
        classesToBeCompiled.add(ArrayTypeModel.class);
        classesToBeCompiled.add(Color.class);
    }

    @Override
    protected void beforeEach() {
        testType = getType(TestServiceImpl.class);
    }

    @Test
    void testIsSimpleType_1() {
        assertTrue(isSimpleType(getType(Void.class)));
    }

    @Test
    void testIsSimpleType_2() {
        assertTrue(isSimpleType(getType(Boolean.class)));
    }

    @Test
    void testIsSimpleType_3() {
        assertTrue(isSimpleType(getType(Character.class)));
    }

    @Test
    void testIsSimpleType_4() {
        assertTrue(isSimpleType(getType(Byte.class)));
    }

    @Test
    void testIsSimpleType_5() {
        assertTrue(isSimpleType(getType(Short.class)));
    }

    @Test
    void testIsSimpleType_6() {
        assertTrue(isSimpleType(getType(Integer.class)));
    }

    @Test
    void testIsSimpleType_7() {
        assertTrue(isSimpleType(getType(Long.class)));
    }

    @Test
    void testIsSimpleType_8() {
        assertTrue(isSimpleType(getType(Float.class)));
    }

    @Test
    void testIsSimpleType_9() {
        assertTrue(isSimpleType(getType(Double.class)));
    }

    @Test
    void testIsSimpleType_10() {
        assertTrue(isSimpleType(getType(String.class)));
    }

    @Test
    void testIsSimpleType_11() {
        assertTrue(isSimpleType(getType(BigDecimal.class)));
    }

    @Test
    void testIsSimpleType_12() {
        assertTrue(isSimpleType(getType(BigInteger.class)));
    }

    @Test
    void testIsSimpleType_13() {
        assertTrue(isSimpleType(getType(Date.class)));
    }

    @Test
    void testIsSimpleType_14() {
        assertTrue(isSimpleType(getType(Object.class)));
    }

    @Test
    void testIsSimpleType_15() {
        assertFalse(isSimpleType(getType(getClass())));
    }

    @Test
    void testIsSimpleType_16() {
        assertFalse(isSimpleType((TypeElement) null));
    }

    @Test
    void testIsSimpleType_17() {
        assertFalse(isSimpleType((TypeMirror) null));
    }

    @Test
    void testIsSameType_1() {
        assertTrue(isSameType(getType(Void.class).asType(), "java.lang.Void"));
    }

    @Test
    void testIsSameType_2() {
        assertFalse(isSameType(getType(String.class).asType(), "java.lang.Void"));
    }

    @Test
    void testIsSameType_3() {
        assertFalse(isSameType(getType(Void.class).asType(), (Type) null));
    }

    @Test
    void testIsSameType_4() {
        assertFalse(isSameType(null, (Type) null));
    }

    @Test
    void testIsSameType_5() {
        assertFalse(isSameType(getType(Void.class).asType(), (String) null));
    }

    @Test
    void testIsSameType_6() {
        assertFalse(isSameType(null, (String) null));
    }

    @Test
    void testIsArrayType_1_testMerged_1() {
        TypeElement type = getType(ArrayTypeModel.class);
        assertTrue(isArrayType(findField(type.asType(), "integers").asType()));
        assertTrue(isArrayType(findField(type.asType(), "strings").asType()));
        assertTrue(isArrayType(findField(type.asType(), "primitiveTypeModels").asType()));
        assertTrue(isArrayType(findField(type.asType(), "models").asType()));
        assertTrue(isArrayType(findField(type.asType(), "colors").asType()));
    }

    @Test
    void testIsArrayType_6() {
        assertFalse(isArrayType((Element) null));
    }

    @Test
    void testIsArrayType_7() {
        assertFalse(isArrayType((TypeMirror) null));
    }

    @Test
    void testIsEnumType_1_testMerged_1() {
        TypeElement type = getType(Color.class);
        assertTrue(isEnumType(type.asType()));
        type = getType(ArrayTypeModel.class);
        assertFalse(isEnumType(type.asType()));
    }

    @Test
    void testIsEnumType_3() {
        assertFalse(isEnumType((Element) null));
    }

    @Test
    void testIsEnumType_4() {
        assertFalse(isEnumType((TypeMirror) null));
    }

    @Test
    void testIsClassType_1_testMerged_1() {
        TypeElement type = getType(ArrayTypeModel.class);
        assertTrue(isClassType(type.asType()));
    }

    @Test
    void testIsClassType_3() {
        assertFalse(isClassType((Element) null));
    }

    @Test
    void testIsClassType_4() {
        assertFalse(isClassType((TypeMirror) null));
    }

    @Test
    void testIsInterfaceType_1_testMerged_1() {
        TypeElement type = getType(CharSequence.class);
        assertTrue(isInterfaceType(type));
        assertTrue(isInterfaceType(type.asType()));
        type = getType(Model.class);
        assertFalse(isInterfaceType(type));
        assertFalse(isInterfaceType(type.asType()));
    }

    @Test
    void testIsInterfaceType_5() {
        assertFalse(isInterfaceType((Element) null));
    }

    @Test
    void testIsInterfaceType_6() {
        assertFalse(isInterfaceType((TypeMirror) null));
    }

    @Test
    void testIsAnnotationType_1_testMerged_1() {
        TypeElement type = getType(Override.class);
        assertTrue(isAnnotationType(type));
        assertTrue(isAnnotationType(type.asType()));
        type = getType(Model.class);
        assertFalse(isAnnotationType(type));
        assertFalse(isAnnotationType(type.asType()));
    }

    @Test
    void testIsAnnotationType_5() {
        assertFalse(isAnnotationType((Element) null));
    }

    @Test
    void testIsAnnotationType_6() {
        assertFalse(isAnnotationType((TypeMirror) null));
    }

    @Test
    void testGetSuperType_1_testMerged_1() {
        TypeElement gtsTypeElement = getSuperType(testType);
        assertEquals(gtsTypeElement, getType(GenericTestService.class));
        TypeElement dtsTypeElement = getSuperType(gtsTypeElement);
        assertEquals(dtsTypeElement, getType(DefaultTestService.class));
        TypeMirror gtsType = getSuperType(testType.asType());
        assertEquals(gtsType, getType(GenericTestService.class).asType());
        TypeMirror dtsType = getSuperType(gtsType);
        assertEquals(dtsType, getType(DefaultTestService.class).asType());
    }

    @Test
    void testGetSuperType_5() {
        assertNull(getSuperType((TypeElement) null));
    }

    @Test
    void testGetSuperType_6() {
        assertNull(getSuperType((TypeMirror) null));
    }

    @Test
    void testIsDeclaredType_1() {
        assertTrue(isDeclaredType(testType));
    }

    @Test
    void testIsDeclaredType_2() {
        assertTrue(isDeclaredType(testType.asType()));
    }

    @Test
    void testIsDeclaredType_3() {
        assertFalse(isDeclaredType((Element) null));
    }

    @Test
    void testIsDeclaredType_4() {
        assertFalse(isDeclaredType((TypeMirror) null));
    }

    @Test
    void testIsDeclaredType_5() {
        assertFalse(isDeclaredType(types.getNullType()));
    }

    @Test
    void testIsDeclaredType_6() {
        assertFalse(isDeclaredType(types.getPrimitiveType(TypeKind.BYTE)));
    }

    @Test
    void testIsDeclaredType_7() {
        assertFalse(isDeclaredType(types.getArrayType(types.getPrimitiveType(TypeKind.BYTE))));
    }

    @Test
    void testOfDeclaredType_1() {
        assertEquals(testType.asType(), ofDeclaredType(testType));
    }

    @Test
    void testOfDeclaredType_2() {
        assertEquals(testType.asType(), ofDeclaredType(testType.asType()));
    }

    @Test
    void testOfDeclaredType_3() {
        assertEquals(ofDeclaredType(testType), ofDeclaredType(testType.asType()));
    }

    @Test
    void testOfDeclaredType_4() {
        assertNull(ofDeclaredType((Element) null));
    }

    @Test
    void testOfDeclaredType_5() {
        assertNull(ofDeclaredType((TypeMirror) null));
    }

    @Test
    void testIsTypeElement_1() {
        assertTrue(isTypeElement(testType));
    }

    @Test
    void testIsTypeElement_2() {
        assertTrue(isTypeElement(testType.asType()));
    }

    @Test
    void testIsTypeElement_3() {
        assertFalse(isTypeElement((Element) null));
    }

    @Test
    void testIsTypeElement_4() {
        assertFalse(isTypeElement((TypeMirror) null));
    }

    @Test
    void testOfTypeElement_1() {
        assertEquals(testType, ofTypeElement(testType));
    }

    @Test
    void testOfTypeElement_2() {
        assertEquals(testType, ofTypeElement(testType.asType()));
    }

    @Test
    void testOfTypeElement_3() {
        assertNull(ofTypeElement((Element) null));
    }

    @Test
    void testOfTypeElement_4() {
        assertNull(ofTypeElement((TypeMirror) null));
    }

    @Test
    void testGetResourceName_1() {
        assertEquals("java/lang/String.class", getResourceName("java.lang.String"));
    }

    @Test
    void testGetResourceName_2() {
        assertNull(getResourceName(null));
    }
}
