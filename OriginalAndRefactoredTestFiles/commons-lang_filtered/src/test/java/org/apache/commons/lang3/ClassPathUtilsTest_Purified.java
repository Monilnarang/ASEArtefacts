package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

public class ClassPathUtilsTest_Purified extends AbstractLangTest {

    @Test
    public void testConstructor_1() {
        assertNotNull(new ClassPathUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = ClassPathUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(ClassPathUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(ClassPathUtils.class.getModifiers()));
    }

    @Test
    public void testPackageToPath_1() {
        assertEquals("a", ClassPathUtils.packageToPath("a"));
    }

    @Test
    public void testPackageToPath_2() {
        assertEquals("a/b", ClassPathUtils.packageToPath("a.b"));
    }

    @Test
    public void testPackageToPath_3() {
        assertEquals("a/b/c", ClassPathUtils.packageToPath("a.b.c"));
    }

    @Test
    public void testPathToPackage_1() {
        assertEquals("a", ClassPathUtils.pathToPackage("a"));
    }

    @Test
    public void testPathToPackage_2() {
        assertEquals("a.b", ClassPathUtils.pathToPackage("a/b"));
    }

    @Test
    public void testPathToPackage_3() {
        assertEquals("a.b.c", ClassPathUtils.pathToPackage("a/b/c"));
    }
}
