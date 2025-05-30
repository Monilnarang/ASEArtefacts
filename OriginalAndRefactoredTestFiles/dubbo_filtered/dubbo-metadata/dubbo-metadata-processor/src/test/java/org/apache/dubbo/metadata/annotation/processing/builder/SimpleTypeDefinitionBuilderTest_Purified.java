package org.apache.dubbo.metadata.annotation.processing.builder;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.annotation.processing.model.SimpleTypeModel;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.metadata.annotation.processing.builder.PrimitiveTypeDefinitionBuilderTest.buildAndAssertTypeDefinition;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.findField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleTypeDefinitionBuilderTest_Purified extends AbstractAnnotationProcessingTest {

    private SimpleTypeDefinitionBuilder builder;

    private VariableElement vField;

    private VariableElement zField;

    private VariableElement cField;

    private VariableElement bField;

    private VariableElement sField;

    private VariableElement iField;

    private VariableElement lField;

    private VariableElement fField;

    private VariableElement dField;

    private VariableElement strField;

    private VariableElement bdField;

    private VariableElement biField;

    private VariableElement dtField;

    private VariableElement invalidField;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
        classesToBeCompiled.add(SimpleTypeModel.class);
    }

    @Override
    protected void beforeEach() {
        builder = new SimpleTypeDefinitionBuilder();
        TypeElement testType = getType(SimpleTypeModel.class);
        vField = findField(testType, "v");
        zField = findField(testType, "z");
        cField = findField(testType, "c");
        bField = findField(testType, "b");
        sField = findField(testType, "s");
        iField = findField(testType, "i");
        lField = findField(testType, "l");
        fField = findField(testType, "f");
        dField = findField(testType, "d");
        strField = findField(testType, "str");
        bdField = findField(testType, "bd");
        biField = findField(testType, "bi");
        dtField = findField(testType, "dt");
        invalidField = findField(testType, "invalid");
        assertEquals("java.lang.Void", vField.asType().toString());
        assertEquals("java.lang.Boolean", zField.asType().toString());
        assertEquals("java.lang.Character", cField.asType().toString());
        assertEquals("java.lang.Byte", bField.asType().toString());
        assertEquals("java.lang.Short", sField.asType().toString());
        assertEquals("java.lang.Integer", iField.asType().toString());
        assertEquals("java.lang.Long", lField.asType().toString());
        assertEquals("java.lang.Float", fField.asType().toString());
        assertEquals("java.lang.Double", dField.asType().toString());
        assertEquals("java.lang.String", strField.asType().toString());
        assertEquals("java.math.BigDecimal", bdField.asType().toString());
        assertEquals("java.math.BigInteger", biField.asType().toString());
        assertEquals("java.util.Date", dtField.asType().toString());
        assertEquals("int", invalidField.asType().toString());
    }

    @Test
    void testAccept_1() {
        assertTrue(builder.accept(processingEnv, vField.asType()));
    }

    @Test
    void testAccept_2() {
        assertTrue(builder.accept(processingEnv, zField.asType()));
    }

    @Test
    void testAccept_3() {
        assertTrue(builder.accept(processingEnv, cField.asType()));
    }

    @Test
    void testAccept_4() {
        assertTrue(builder.accept(processingEnv, bField.asType()));
    }

    @Test
    void testAccept_5() {
        assertTrue(builder.accept(processingEnv, sField.asType()));
    }

    @Test
    void testAccept_6() {
        assertTrue(builder.accept(processingEnv, iField.asType()));
    }

    @Test
    void testAccept_7() {
        assertTrue(builder.accept(processingEnv, lField.asType()));
    }

    @Test
    void testAccept_8() {
        assertTrue(builder.accept(processingEnv, fField.asType()));
    }

    @Test
    void testAccept_9() {
        assertTrue(builder.accept(processingEnv, dField.asType()));
    }

    @Test
    void testAccept_10() {
        assertTrue(builder.accept(processingEnv, strField.asType()));
    }

    @Test
    void testAccept_11() {
        assertTrue(builder.accept(processingEnv, bdField.asType()));
    }

    @Test
    void testAccept_12() {
        assertTrue(builder.accept(processingEnv, biField.asType()));
    }

    @Test
    void testAccept_13() {
        assertTrue(builder.accept(processingEnv, dtField.asType()));
    }

    @Test
    void testAccept_14() {
        assertFalse(builder.accept(processingEnv, invalidField.asType()));
    }
}
