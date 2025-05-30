package org.apache.dubbo.common.utils;

import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.utils.FieldUtils.findField;
import static org.apache.dubbo.common.utils.FieldUtils.getDeclaredField;
import static org.apache.dubbo.common.utils.FieldUtils.getFieldValue;
import static org.apache.dubbo.common.utils.FieldUtils.setFieldValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FieldUtilsTest_Purified {

    @Test
    void testGetDeclaredField_1() {
        assertEquals("a", getDeclaredField(A.class, "a").getName());
    }

    @Test
    void testGetDeclaredField_2() {
        assertEquals("b", getDeclaredField(B.class, "b").getName());
    }

    @Test
    void testGetDeclaredField_3() {
        assertEquals("c", getDeclaredField(C.class, "c").getName());
    }

    @Test
    void testGetDeclaredField_4() {
        assertNull(getDeclaredField(B.class, "a"));
    }

    @Test
    void testGetDeclaredField_5() {
        assertNull(getDeclaredField(C.class, "a"));
    }

    @Test
    void testFindField_1() {
        assertEquals("a", findField(A.class, "a").getName());
    }

    @Test
    void testFindField_2() {
        assertEquals("a", findField(new A(), "a").getName());
    }

    @Test
    void testFindField_3() {
        assertEquals("a", findField(B.class, "a").getName());
    }

    @Test
    void testFindField_4() {
        assertEquals("b", findField(B.class, "b").getName());
    }

    @Test
    void testFindField_5() {
        assertEquals("a", findField(C.class, "a").getName());
    }

    @Test
    void testFindField_6() {
        assertEquals("b", findField(C.class, "b").getName());
    }

    @Test
    void testFindField_7() {
        assertEquals("c", findField(C.class, "c").getName());
    }

    @Test
    void testGetFieldValue_1() {
        assertEquals("a", getFieldValue(new A(), "a"));
    }

    @Test
    void testGetFieldValue_2() {
        assertEquals("a", getFieldValue(new B(), "a"));
    }

    @Test
    void testGetFieldValue_3() {
        assertEquals("b", getFieldValue(new B(), "b"));
    }

    @Test
    void testGetFieldValue_4() {
        assertEquals("a", getFieldValue(new C(), "a"));
    }

    @Test
    void testGetFieldValue_5() {
        assertEquals("b", getFieldValue(new C(), "b"));
    }

    @Test
    void testGetFieldValue_6() {
        assertEquals("c", getFieldValue(new C(), "c"));
    }
}
