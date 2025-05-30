package org.apache.dubbo.common.utils;

import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.utils.MemberUtils.isPrivate;
import static org.apache.dubbo.common.utils.MemberUtils.isPublic;
import static org.apache.dubbo.common.utils.MemberUtils.isStatic;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberUtilsTest_Purified {

    public void noStatic() {
    }

    public static void staticMethod() {
    }

    private void privateMethod() {
    }

    public void publicMethod() {
    }

    @Test
    void test_1() throws NoSuchMethodException {
        assertFalse(isStatic(getClass().getMethod("noStatic")));
    }

    @Test
    void test_2() throws NoSuchMethodException {
        assertTrue(isStatic(getClass().getMethod("staticMethod")));
    }

    @Test
    void test_3() throws NoSuchMethodException {
        assertTrue(isPrivate(getClass().getDeclaredMethod("privateMethod")));
    }

    @Test
    void test_4() throws NoSuchMethodException {
        assertTrue(isPublic(getClass().getMethod("publicMethod")));
    }
}
