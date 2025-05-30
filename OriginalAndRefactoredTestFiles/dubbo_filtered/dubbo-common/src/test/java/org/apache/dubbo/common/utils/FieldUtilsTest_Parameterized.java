package org.apache.dubbo.common.utils;

import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.utils.FieldUtils.findField;
import static org.apache.dubbo.common.utils.FieldUtils.getDeclaredField;
import static org.apache.dubbo.common.utils.FieldUtils.getFieldValue;
import static org.apache.dubbo.common.utils.FieldUtils.setFieldValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class FieldUtilsTest_Parameterized {

    @Test
    void testFindField_2() {
        assertEquals("a", findField(new A(), "a").getName());
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDeclaredField_1to3")
    void testGetDeclaredField_1to3(String param1, String param2) {
        assertEquals(param1, getDeclaredField(A.class, param2).getName());
    }

    static public Stream<Arguments> Provider_testGetDeclaredField_1to3() {
        return Stream.of(arguments("a", "a"), arguments("b", "b"), arguments("c", "c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDeclaredField_4to5")
    void testGetDeclaredField_4to5(String param1) {
        assertNull(getDeclaredField(B.class, param1));
    }

    static public Stream<Arguments> Provider_testGetDeclaredField_4to5() {
        return Stream.of(arguments("a"), arguments("a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFindField_1_3to7")
    void testFindField_1_3to7(String param1, String param2) {
        assertEquals(param1, findField(A.class, param2).getName());
    }

    static public Stream<Arguments> Provider_testFindField_1_3to7() {
        return Stream.of(arguments("a", "a"), arguments("a", "a"), arguments("b", "b"), arguments("a", "a"), arguments("b", "b"), arguments("c", "c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFieldValue_1to6")
    void testGetFieldValue_1to6(String param1, String param2) {
        assertEquals(param1, getFieldValue(new A(), param2));
    }

    static public Stream<Arguments> Provider_testGetFieldValue_1to6() {
        return Stream.of(arguments("a", "a"), arguments("a", "a"), arguments("b", "b"), arguments("a", "a"), arguments("b", "b"), arguments("c", "c"));
    }
}
