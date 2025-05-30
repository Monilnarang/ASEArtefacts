package org.apache.dubbo.common.beanutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaBeanAccessorTest_Purified {

    @Test
    void testIsAccessByMethod_1() {
        Assertions.assertTrue(JavaBeanAccessor.isAccessByMethod(JavaBeanAccessor.METHOD));
    }

    @Test
    void testIsAccessByMethod_2() {
        Assertions.assertTrue(JavaBeanAccessor.isAccessByMethod(JavaBeanAccessor.ALL));
    }

    @Test
    void testIsAccessByMethod_3() {
        Assertions.assertFalse(JavaBeanAccessor.isAccessByMethod(JavaBeanAccessor.FIELD));
    }

    @Test
    void testIsAccessByField_1() {
        Assertions.assertTrue(JavaBeanAccessor.isAccessByField(JavaBeanAccessor.FIELD));
    }

    @Test
    void testIsAccessByField_2() {
        Assertions.assertTrue(JavaBeanAccessor.isAccessByField(JavaBeanAccessor.ALL));
    }

    @Test
    void testIsAccessByField_3() {
        Assertions.assertFalse(JavaBeanAccessor.isAccessByField(JavaBeanAccessor.METHOD));
    }
}
