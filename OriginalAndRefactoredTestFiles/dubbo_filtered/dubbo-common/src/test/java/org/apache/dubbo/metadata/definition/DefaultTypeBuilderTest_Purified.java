package org.apache.dubbo.metadata.definition;

import org.apache.dubbo.metadata.definition.builder.DefaultTypeBuilder;
import org.apache.dubbo.rpc.model.FrameworkModel;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultTypeBuilderTest_Purified {

    @Test
    void testInnerClass_1() {
        Assertions.assertEquals(String.class.getName(), DefaultTypeBuilder.build(String.class, new HashMap<>()).getType());
    }

    @Test
    void testInnerClass_2() {
        DefaultTypeBuilderTest innerObject = new DefaultTypeBuilderTest() {
        };
        Assertions.assertEquals(DefaultTypeBuilderTest.class.getName() + "$1", DefaultTypeBuilder.build(innerObject.getClass(), new HashMap<>()).getType());
    }
}
