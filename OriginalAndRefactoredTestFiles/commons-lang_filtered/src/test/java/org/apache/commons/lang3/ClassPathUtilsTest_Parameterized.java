package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ClassPathUtilsTest_Parameterized extends AbstractLangTest {

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

    @ParameterizedTest
    @MethodSource("Provider_testPackageToPath_1to3")
    public void testPackageToPath_1to3(String param1, String param2) {
        assertEquals(param1, ClassPathUtils.packageToPath(param2));
    }

    static public Stream<Arguments> Provider_testPackageToPath_1to3() {
        return Stream.of(arguments("a", "a"), arguments("a/b", "a.b"), arguments("a/b/c", "a.b.c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPathToPackage_1to3")
    public void testPathToPackage_1to3(String param1, String param2) {
        assertEquals(param1, ClassPathUtils.pathToPackage(param2));
    }

    static public Stream<Arguments> Provider_testPathToPackage_1to3() {
        return Stream.of(arguments("a", "a"), arguments("a.b", "a/b"), arguments("a.b.c", "a/b/c"));
    }
}
