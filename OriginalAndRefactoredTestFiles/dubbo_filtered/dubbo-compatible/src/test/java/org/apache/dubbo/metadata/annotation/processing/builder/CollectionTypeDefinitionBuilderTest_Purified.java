package org.apache.dubbo.metadata.annotation.processing.builder;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.annotation.processing.model.CollectionTypeModel;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.metadata.annotation.processing.builder.ArrayTypeDefinitionBuilderTest.buildAndAssertTypeDefinition;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.findField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectionTypeDefinitionBuilderTest_Purified extends AbstractAnnotationProcessingTest {

    private CollectionTypeDefinitionBuilder builder;

    private VariableElement stringsField;

    private VariableElement colorsField;

    private VariableElement primitiveTypeModelsField;

    private VariableElement modelsField;

    private VariableElement modelArraysField;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
        classesToBeCompiled.add(CollectionTypeModel.class);
    }

    @Override
    protected void beforeEach() {
        builder = new CollectionTypeDefinitionBuilder();
        TypeElement testType = getType(CollectionTypeModel.class);
        stringsField = findField(testType, "strings");
        colorsField = findField(testType, "colors");
        primitiveTypeModelsField = findField(testType, "primitiveTypeModels");
        modelsField = findField(testType, "models");
        modelArraysField = findField(testType, "modelArrays");
        assertEquals("strings", stringsField.getSimpleName().toString());
        assertEquals("colors", colorsField.getSimpleName().toString());
        assertEquals("primitiveTypeModels", primitiveTypeModelsField.getSimpleName().toString());
        assertEquals("models", modelsField.getSimpleName().toString());
        assertEquals("modelArrays", modelArraysField.getSimpleName().toString());
    }

    @Test
    void testAccept_1() {
        assertTrue(builder.accept(processingEnv, stringsField.asType()));
    }

    @Test
    void testAccept_2() {
        assertTrue(builder.accept(processingEnv, colorsField.asType()));
    }

    @Test
    void testAccept_3() {
        assertTrue(builder.accept(processingEnv, primitiveTypeModelsField.asType()));
    }

    @Test
    void testAccept_4() {
        assertTrue(builder.accept(processingEnv, modelsField.asType()));
    }

    @Test
    void testAccept_5() {
        assertTrue(builder.accept(processingEnv, modelArraysField.asType()));
    }
}
