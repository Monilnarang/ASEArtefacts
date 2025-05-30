package org.apache.dubbo.metadata.annotation.processing.builder;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.annotation.processing.model.PrimitiveTypeModel;
import org.apache.dubbo.metadata.definition.model.TypeDefinition;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.findField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimitiveTypeDefinitionBuilderTest_Purified extends AbstractAnnotationProcessingTest {

    private PrimitiveTypeDefinitionBuilder builder;

    private VariableElement zField;

    private VariableElement bField;

    private VariableElement cField;

    private VariableElement sField;

    private VariableElement iField;

    private VariableElement lField;

    private VariableElement fField;

    private VariableElement dField;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
        classesToBeCompiled.add(PrimitiveTypeModel.class);
    }

    @Override
    protected void beforeEach() {
        builder = new PrimitiveTypeDefinitionBuilder();
        TypeElement testType = getType(PrimitiveTypeModel.class);
        zField = findField(testType, "z");
        bField = findField(testType, "b");
        cField = findField(testType, "c");
        sField = findField(testType, "s");
        iField = findField(testType, "i");
        lField = findField(testType, "l");
        fField = findField(testType, "f");
        dField = findField(testType, "d");
        assertEquals("boolean", zField.asType().toString());
        assertEquals("byte", bField.asType().toString());
        assertEquals("char", cField.asType().toString());
        assertEquals("short", sField.asType().toString());
        assertEquals("int", iField.asType().toString());
        assertEquals("long", lField.asType().toString());
        assertEquals("float", fField.asType().toString());
        assertEquals("double", dField.asType().toString());
    }

    static void buildAndAssertTypeDefinition(ProcessingEnvironment processingEnv, VariableElement field, TypeBuilder builder) {
        Map<String, TypeDefinition> typeCache = new HashMap<>();
        TypeDefinition typeDefinition = TypeDefinitionBuilder.build(processingEnv, field, typeCache);
        assertBasicTypeDefinition(typeDefinition, field.asType().toString(), builder);
    }

    static void assertBasicTypeDefinition(TypeDefinition typeDefinition, String type, TypeBuilder builder) {
        assertEquals(type, typeDefinition.getType());
        assertTrue(typeDefinition.getProperties().isEmpty());
        assertTrue(typeDefinition.getItems().isEmpty());
        assertTrue(typeDefinition.getEnums().isEmpty());
    }

    @Test
    void testAccept_1() {
        assertTrue(builder.accept(processingEnv, zField.asType()));
    }

    @Test
    void testAccept_2() {
        assertTrue(builder.accept(processingEnv, bField.asType()));
    }

    @Test
    void testAccept_3() {
        assertTrue(builder.accept(processingEnv, cField.asType()));
    }

    @Test
    void testAccept_4() {
        assertTrue(builder.accept(processingEnv, sField.asType()));
    }

    @Test
    void testAccept_5() {
        assertTrue(builder.accept(processingEnv, iField.asType()));
    }

    @Test
    void testAccept_6() {
        assertTrue(builder.accept(processingEnv, lField.asType()));
    }

    @Test
    void testAccept_7() {
        assertTrue(builder.accept(processingEnv, fField.asType()));
    }

    @Test
    void testAccept_8() {
        assertTrue(builder.accept(processingEnv, dField.asType()));
    }
}
