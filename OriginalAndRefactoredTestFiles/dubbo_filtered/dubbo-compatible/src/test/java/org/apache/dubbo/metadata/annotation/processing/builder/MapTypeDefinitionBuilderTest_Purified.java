package org.apache.dubbo.metadata.annotation.processing.builder;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.annotation.processing.model.MapTypeModel;
import org.apache.dubbo.metadata.definition.model.TypeDefinition;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.metadata.annotation.processing.util.FieldUtils.findField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapTypeDefinitionBuilderTest_Purified extends AbstractAnnotationProcessingTest {

    private MapTypeDefinitionBuilder builder;

    private VariableElement stringsField;

    private VariableElement colorsField;

    private VariableElement primitiveTypeModelsField;

    private VariableElement modelsField;

    private VariableElement modelArraysField;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
        classesToBeCompiled.add(MapTypeModel.class);
    }

    @Override
    protected void beforeEach() {
        builder = new MapTypeDefinitionBuilder();
        TypeElement testType = getType(MapTypeModel.class);
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

    static void buildAndAssertTypeDefinition(ProcessingEnvironment processingEnv, VariableElement field, String expectedType, String keyType, String valueType, TypeBuilder builder, BiConsumer<TypeDefinition, TypeDefinition>... assertions) {
        Map<String, TypeDefinition> typeCache = new HashMap<>();
        TypeDefinition typeDefinition = TypeDefinitionBuilder.build(processingEnv, field, typeCache);
        String keyTypeName = typeDefinition.getItems().get(0);
        TypeDefinition keyTypeDefinition = typeCache.get(keyTypeName);
        String valueTypeName = typeDefinition.getItems().get(1);
        TypeDefinition valueTypeDefinition = typeCache.get(valueTypeName);
        assertEquals(expectedType, typeDefinition.getType());
        assertEquals(keyType, keyTypeDefinition.getType());
        assertEquals(valueType, valueTypeDefinition.getType());
        Stream.of(assertions).forEach(assertion -> assertion.accept(typeDefinition, keyTypeDefinition));
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
