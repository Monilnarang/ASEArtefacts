package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class JavaVersionTest_Purified extends AbstractLangTest {

    @Test
    public void testAtLeast_1() {
        assertFalse(JavaVersion.JAVA_1_2.atLeast(JavaVersion.JAVA_1_5), "1.2 at least 1.5 passed");
    }

    @Test
    public void testAtLeast_2() {
        assertTrue(JavaVersion.JAVA_1_5.atLeast(JavaVersion.JAVA_1_2), "1.5 at least 1.2 failed");
    }

    @Test
    public void testAtLeast_3() {
        assertFalse(JavaVersion.JAVA_1_6.atLeast(JavaVersion.JAVA_1_7), "1.6 at least 1.7 passed");
    }

    @Test
    public void testAtLeast_4() {
        assertTrue(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_5), "0.9 at least 1.5 failed");
    }

    @Test
    public void testAtLeast_5() {
        assertFalse(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_6), "0.9 at least 1.6 passed");
    }

    @Test
    public void testGetJavaVersion_1() throws Exception {
        assertEquals(JavaVersion.JAVA_0_9, JavaVersion.get("0.9"), "0.9 failed");
    }

    @Test
    public void testGetJavaVersion_2() throws Exception {
        assertEquals(JavaVersion.JAVA_1_1, JavaVersion.get("1.1"), "1.1 failed");
    }

    @Test
    public void testGetJavaVersion_3() throws Exception {
        assertEquals(JavaVersion.JAVA_1_2, JavaVersion.get("1.2"), "1.2 failed");
    }

    @Test
    public void testGetJavaVersion_4() throws Exception {
        assertEquals(JavaVersion.JAVA_1_3, JavaVersion.get("1.3"), "1.3 failed");
    }

    @Test
    public void testGetJavaVersion_5() throws Exception {
        assertEquals(JavaVersion.JAVA_1_4, JavaVersion.get("1.4"), "1.4 failed");
    }

    @Test
    public void testGetJavaVersion_6() throws Exception {
        assertEquals(JavaVersion.JAVA_1_5, JavaVersion.get("1.5"), "1.5 failed");
    }

    @Test
    public void testGetJavaVersion_7() throws Exception {
        assertEquals(JavaVersion.JAVA_1_6, JavaVersion.get("1.6"), "1.6 failed");
    }

    @Test
    public void testGetJavaVersion_8() throws Exception {
        assertEquals(JavaVersion.JAVA_1_7, JavaVersion.get("1.7"), "1.7 failed");
    }

    @Test
    public void testGetJavaVersion_9() throws Exception {
        assertEquals(JavaVersion.JAVA_1_8, JavaVersion.get("1.8"), "1.8 failed");
    }

    @Test
    public void testGetJavaVersion_10() throws Exception {
        assertEquals(JavaVersion.JAVA_9, JavaVersion.get("9"));
    }

    @Test
    public void testGetJavaVersion_11() throws Exception {
        assertEquals(JavaVersion.JAVA_10, JavaVersion.get("10"));
    }

    @Test
    public void testGetJavaVersion_12() throws Exception {
        assertEquals(JavaVersion.JAVA_11, JavaVersion.get("11"));
    }

    @Test
    public void testGetJavaVersion_13() throws Exception {
        assertEquals(JavaVersion.JAVA_12, JavaVersion.get("12"));
    }

    @Test
    public void testGetJavaVersion_14() throws Exception {
        assertEquals(JavaVersion.JAVA_13, JavaVersion.get("13"));
    }

    @Test
    public void testGetJavaVersion_15() throws Exception {
        assertEquals(JavaVersion.JAVA_14, JavaVersion.get("14"));
    }

    @Test
    public void testGetJavaVersion_16() throws Exception {
        assertEquals(JavaVersion.JAVA_15, JavaVersion.get("15"));
    }

    @Test
    public void testGetJavaVersion_17() throws Exception {
        assertEquals(JavaVersion.JAVA_16, JavaVersion.get("16"));
    }

    @Test
    public void testGetJavaVersion_18() throws Exception {
        assertEquals(JavaVersion.JAVA_17, JavaVersion.get("17"));
    }

    @Test
    public void testGetJavaVersion_19() throws Exception {
        assertEquals(JavaVersion.JAVA_18, JavaVersion.get("18"));
    }

    @Test
    public void testGetJavaVersion_20() throws Exception {
        assertEquals(JavaVersion.JAVA_19, JavaVersion.get("19"));
    }

    @Test
    public void testGetJavaVersion_21() throws Exception {
        assertEquals(JavaVersion.JAVA_20, JavaVersion.get("20"));
    }

    @Test
    public void testGetJavaVersion_22() throws Exception {
        assertEquals(JavaVersion.JAVA_21, JavaVersion.get("21"));
    }

    @Test
    public void testGetJavaVersion_23() throws Exception {
        assertEquals(JavaVersion.JAVA_22, JavaVersion.get("22"));
    }

    @Test
    public void testGetJavaVersion_24() throws Exception {
        assertEquals(JavaVersion.JAVA_23, JavaVersion.get("23"));
    }

    @Test
    public void testGetJavaVersion_25() throws Exception {
        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("1.10"), "1.10 failed");
    }

    @Test
    public void testGetJavaVersion_26() throws Exception {
        assertEquals(JavaVersion.get("1.5"), JavaVersion.getJavaVersion("1.5"), "Wrapper method failed");
    }

    @Test
    public void testGetJavaVersion_27() throws Exception {
        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("24"), "Unhandled");
    }
}
