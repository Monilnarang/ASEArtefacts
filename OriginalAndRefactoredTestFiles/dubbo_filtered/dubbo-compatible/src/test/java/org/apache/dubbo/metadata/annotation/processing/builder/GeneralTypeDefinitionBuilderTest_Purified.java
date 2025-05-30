package org.apache.dubbo.metadata.annotation.processing.builder;

import org.apache.dubbo.metadata.annotation.processing.AbstractAnnotationProcessingTest;
import org.apache.dubbo.metadata.annotation.processing.model.ArrayTypeModel;
import org.apache.dubbo.metadata.annotation.processing.model.CollectionTypeModel;
import org.apache.dubbo.metadata.annotation.processing.model.Color;
import org.apache.dubbo.metadata.annotation.processing.model.Model;
import org.apache.dubbo.metadata.annotation.processing.model.PrimitiveTypeModel;
import org.apache.dubbo.metadata.annotation.processing.model.SimpleTypeModel;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneralTypeDefinitionBuilderTest_Purified extends AbstractAnnotationProcessingTest {

    private GeneralTypeDefinitionBuilder builder;

    @Override
    protected void addCompiledClasses(Set<Class<?>> classesToBeCompiled) {
        classesToBeCompiled.add(Model.class);
    }

    @Override
    protected void beforeEach() {
        builder = new GeneralTypeDefinitionBuilder();
    }

    @Test
    void testAccept_1() {
        assertTrue(builder.accept(processingEnv, getType(Model.class).asType()));
    }

    @Test
    void testAccept_2() {
        assertTrue(builder.accept(processingEnv, getType(PrimitiveTypeModel.class).asType()));
    }

    @Test
    void testAccept_3() {
        assertTrue(builder.accept(processingEnv, getType(SimpleTypeModel.class).asType()));
    }

    @Test
    void testAccept_4() {
        assertTrue(builder.accept(processingEnv, getType(ArrayTypeModel.class).asType()));
    }

    @Test
    void testAccept_5() {
        assertTrue(builder.accept(processingEnv, getType(CollectionTypeModel.class).asType()));
    }

    @Test
    void testAccept_6() {
        assertFalse(builder.accept(processingEnv, getType(Color.class).asType()));
    }
}
