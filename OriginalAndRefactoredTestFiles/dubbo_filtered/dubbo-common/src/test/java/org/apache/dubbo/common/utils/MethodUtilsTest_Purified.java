package org.apache.dubbo.common.utils;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.utils.MethodUtils.excludedDeclaredClass;
import static org.apache.dubbo.common.utils.MethodUtils.findMethod;
import static org.apache.dubbo.common.utils.MethodUtils.findNearestOverriddenMethod;
import static org.apache.dubbo.common.utils.MethodUtils.findOverriddenMethod;
import static org.apache.dubbo.common.utils.MethodUtils.getAllDeclaredMethods;
import static org.apache.dubbo.common.utils.MethodUtils.getAllMethods;
import static org.apache.dubbo.common.utils.MethodUtils.getDeclaredMethods;
import static org.apache.dubbo.common.utils.MethodUtils.getMethods;
import static org.apache.dubbo.common.utils.MethodUtils.invokeMethod;
import static org.apache.dubbo.common.utils.MethodUtils.overrides;

class MethodUtilsTest_Purified {

    public class MethodFieldTestClazz {

        public String is() {
            return "";
        }

        public String get() {
            return "";
        }

        public String getObject() {
            return "";
        }

        public String getFieldName1() {
            return "";
        }

        public String setFieldName2() {
            return "";
        }

        public String isFieldName3() {
            return "";
        }
    }

    public class MethodTestClazz {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public MethodTestClazz get() {
            return this;
        }

        @Deprecated
        public Boolean deprecatedMethod() {
            return true;
        }
    }

    public class MethodOverrideClazz extends MethodTestClazz {

        @Override
        public MethodTestClazz get() {
            return this;
        }
    }

    @Test
    void testIsDeprecated_1() throws Exception {
        Assertions.assertTrue(MethodUtils.isDeprecated(MethodTestClazz.class.getMethod("deprecatedMethod")));
    }

    @Test
    void testIsDeprecated_2() throws Exception {
        Assertions.assertFalse(MethodUtils.isDeprecated(MethodTestClazz.class.getMethod("getValue")));
    }

    @Test
    void testGetMethods_1() throws NoSuchMethodException {
        Assertions.assertTrue(getDeclaredMethods(MethodTestClazz.class, excludedDeclaredClass(String.class)).size() > 0);
    }

    @Test
    void testGetMethods_2() throws NoSuchMethodException {
        Assertions.assertTrue(getMethods(MethodTestClazz.class).size() > 0);
    }

    @Test
    void testGetMethods_3() throws NoSuchMethodException {
        Assertions.assertTrue(getAllDeclaredMethods(MethodTestClazz.class).size() > 0);
    }

    @Test
    void testGetMethods_4() throws NoSuchMethodException {
        Assertions.assertTrue(getAllMethods(MethodTestClazz.class).size() > 0);
    }

    @Test
    void testGetMethods_5() throws NoSuchMethodException {
        Assertions.assertNotNull(findMethod(MethodTestClazz.class, "getValue"));
    }

    @Test
    void testGetMethods_6() throws NoSuchMethodException {
        MethodTestClazz methodTestClazz = new MethodTestClazz();
        invokeMethod(methodTestClazz, "setValue", "Test");
        Assertions.assertEquals(methodTestClazz.getValue(), "Test");
    }

    @Test
    void testGetMethods_7() throws NoSuchMethodException {
        Assertions.assertTrue(overrides(MethodOverrideClazz.class.getMethod("get"), MethodTestClazz.class.getMethod("get")));
    }

    @Test
    void testGetMethods_8() throws NoSuchMethodException {
        Assertions.assertEquals(findNearestOverriddenMethod(MethodOverrideClazz.class.getMethod("get")), MethodTestClazz.class.getMethod("get"));
    }

    @Test
    void testGetMethods_9() throws NoSuchMethodException {
        Assertions.assertEquals(findOverriddenMethod(MethodOverrideClazz.class.getMethod("get"), MethodOverrideClazz.class), MethodTestClazz.class.getMethod("get"));
    }

    @Test
    void testExtractFieldName_1() throws Exception {
        Method m1 = MethodFieldTestClazz.class.getMethod("is");
        Assertions.assertEquals("", MethodUtils.extractFieldName(m1));
    }

    @Test
    void testExtractFieldName_2() throws Exception {
        Method m2 = MethodFieldTestClazz.class.getMethod("get");
        Assertions.assertEquals("", MethodUtils.extractFieldName(m2));
    }

    @Test
    void testExtractFieldName_3() throws Exception {
        Method m3 = MethodFieldTestClazz.class.getMethod("getClass");
        Assertions.assertEquals("", MethodUtils.extractFieldName(m3));
    }

    @Test
    void testExtractFieldName_4() throws Exception {
        Method m4 = MethodFieldTestClazz.class.getMethod("getObject");
        Assertions.assertEquals("", MethodUtils.extractFieldName(m4));
    }

    @Test
    void testExtractFieldName_5() throws Exception {
        Method m5 = MethodFieldTestClazz.class.getMethod("getFieldName1");
        Assertions.assertEquals("fieldName1", MethodUtils.extractFieldName(m5));
    }

    @Test
    void testExtractFieldName_6() throws Exception {
        Method m6 = MethodFieldTestClazz.class.getMethod("setFieldName2");
        Assertions.assertEquals("fieldName2", MethodUtils.extractFieldName(m6));
    }

    @Test
    void testExtractFieldName_7() throws Exception {
        Method m7 = MethodFieldTestClazz.class.getMethod("isFieldName3");
        Assertions.assertEquals("fieldName3", MethodUtils.extractFieldName(m7));
    }
}
