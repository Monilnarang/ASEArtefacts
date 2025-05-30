package org.apache.dubbo.metadata.annotation.processing.builder;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.annotation.processing.model.ArrayTypeModel;
import org.apache.dubbo.metadata.definition.model.TypeDefinition;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
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

class ArrayTypeDefinitionBuilderTest_Purified extends AbstractAnnotationProcessingTest {

    private ArrayTypeDefinitionBuilder builder;

    private TypeElement testType;

    private VariableElement integersField;

    private VariableElement stringsField;

    private VariableElement primitiveTypeModelsField;

    private VariableElement modelsField;

    private VariableElement colorsField;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
        classesToBeCompiled.add(ArrayTypeModel.class);
    }

    @Override
    protected void beforeEach() {
        builder = new ArrayTypeDefinitionBuilder();
        testType = getType(ArrayTypeModel.class);
        integersField = findField(testType, "integers");
        stringsField = findField(testType, "strings");
        primitiveTypeModelsField = findField(testType, "primitiveTypeModels");
        modelsField = findField(testType, "models");
        colorsField = findField(testType, "colors");
    }

    static void buildAndAssertTypeDefinition(ProcessingEnvironment processingEnv, VariableElement field, String expectedType, String compositeType, TypeBuilder builder, BiConsumer<TypeDefinition, TypeDefinition>... assertions) {
        Map<String, TypeDefinition> typeCache = new HashMap<>();
        TypeDefinition typeDefinition = TypeDefinitionBuilder.build(processingEnv, field, typeCache);
        String subTypeName = typeDefinition.getItems().get(0);
        TypeDefinition subTypeDefinition = typeCache.get(subTypeName);
        assertEquals(expectedType, typeDefinition.getType());
        assertEquals(compositeType, subTypeDefinition.getType());
        Stream.of(assertions).forEach(assertion -> assertion.accept(typeDefinition, subTypeDefinition));
    }

    @Test
    void testAccept_1() {
        assertTrue(builder.accept(processingEnv, integersField.asType()));
    }

    @Test
    void testAccept_2() {
        assertTrue(builder.accept(processingEnv, stringsField.asType()));
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
        assertTrue(builder.accept(processingEnv, colorsField.asType()));
    }
}
