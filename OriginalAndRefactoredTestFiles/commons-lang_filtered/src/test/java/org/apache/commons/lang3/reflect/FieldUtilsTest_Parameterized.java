package org.apache.commons.lang3.reflect;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.ArraySorter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.compare.ObjectToStringComparator;
import org.apache.commons.lang3.reflect.testbed.Ambig;
import org.apache.commons.lang3.reflect.testbed.Annotated;
import org.apache.commons.lang3.reflect.testbed.AnotherParent;
import org.apache.commons.lang3.reflect.testbed.Foo;
import org.apache.commons.lang3.reflect.testbed.PrivatelyShadowedChild;
import org.apache.commons.lang3.reflect.testbed.PublicChild;
import org.apache.commons.lang3.reflect.testbed.PubliclyShadowedChild;
import org.apache.commons.lang3.reflect.testbed.StaticContainer;
import org.apache.commons.lang3.reflect.testbed.StaticContainerChild;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FieldUtilsTest_Parameterized extends AbstractLangTest {

    private static final String JACOCO_DATA_FIELD_NAME = "$jacocoData";

    static final Integer I0 = Integer.valueOf(0);

    static final Integer I1 = Integer.valueOf(1);

    static final Double D0 = Double.valueOf(0.0);

    static final Double D1 = Double.valueOf(1.0);

    @Annotated
    private PublicChild publicChild;

    private PubliclyShadowedChild publiclyShadowedChild;

    @Annotated
    private PrivatelyShadowedChild privatelyShadowedChild;

    private final Class<? super PublicChild> parentClass = PublicChild.class.getSuperclass();

    private void callRemoveFinalModifierCheckForException(final Field field, final Boolean forceAccess) {
        try {
            FieldUtils.removeFinalModifier(field, forceAccess);
        } catch (final UnsupportedOperationException exception) {
            if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_12)) {
                assertInstanceOf(NoSuchFieldException.class, exception.getCause());
            } else {
                fail("No exception should be thrown for java prior to 12.0");
            }
        }
    }

    @BeforeEach
    public void setUp() {
        StaticContainer.reset();
        publicChild = new PublicChild();
        publiclyShadowedChild = new PubliclyShadowedChild();
        privatelyShadowedChild = new PrivatelyShadowedChild();
    }

    private Field[] sort(final Field[] fields) {
        return ArraySorter.sort(fields, ObjectToStringComparator.INSTANCE);
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new FieldUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = FieldUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(FieldUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(FieldUtils.class.getModifiers()));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDeclaredField_1to6_11to15")
    public void testGetDeclaredField_1to6_11to15(String param1) {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, param1));
    }

    static public Stream<Arguments> Provider_testGetDeclaredField_1to6_11to15() {
        return Stream.of(arguments("VALUE"), arguments("s"), arguments("b"), arguments("i"), arguments("d"), arguments("VALUE"), arguments("VALUE"), arguments("s"), arguments("b"), arguments("i"), arguments("d"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDeclaredField_7to10")
    public void testGetDeclaredField_7to10(String param1) {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getDeclaredField(PubliclyShadowedChild.class, param1).getDeclaringClass());
    }

    static public Stream<Arguments> Provider_testGetDeclaredField_7to10() {
        return Stream.of(arguments("s"), arguments("b"), arguments("i"), arguments("d"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDeclaredFieldForceAccess_1_7to10_12to15")
    public void testGetDeclaredFieldForceAccess_1_7to10_12to15(String param1) {
        assertEquals(PublicChild.class, FieldUtils.getDeclaredField(PublicChild.class, param1, true).getDeclaringClass());
    }

    static public Stream<Arguments> Provider_testGetDeclaredFieldForceAccess_1_7to10_12to15() {
        return Stream.of(arguments("VALUE"), arguments("s"), arguments("b"), arguments("i"), arguments("d"), arguments("s"), arguments("b"), arguments("i"), arguments("d"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDeclaredFieldForceAccess_2to6_11")
    public void testGetDeclaredFieldForceAccess_2to6_11(String param1) {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, param1, true));
    }

    static public Stream<Arguments> Provider_testGetDeclaredFieldForceAccess_2to6_11() {
        return Stream.of(arguments("s"), arguments("b"), arguments("i"), arguments("d"), arguments("VALUE"), arguments("VALUE"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetField_1_6to11")
    public void testGetField_1_6to11(String param1) {
        assertEquals(Foo.class, FieldUtils.getField(PublicChild.class, param1).getDeclaringClass());
    }

    static public Stream<Arguments> Provider_testGetField_1_6to11() {
        return Stream.of(arguments("VALUE"), arguments("VALUE"), arguments("s"), arguments("b"), arguments("i"), arguments("d"), arguments("VALUE"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetField_2_12")
    public void testGetField_2_12(String param1) {
        assertEquals(parentClass, FieldUtils.getField(PublicChild.class, param1).getDeclaringClass());
    }

    static public Stream<Arguments> Provider_testGetField_2_12() {
        return Stream.of(arguments("s"), arguments("s"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetField_3to5_13to15")
    public void testGetField_3to5_13to15(String param1) {
        assertNull(FieldUtils.getField(PublicChild.class, param1));
    }

    static public Stream<Arguments> Provider_testGetField_3to5_13to15() {
        return Stream.of(arguments("b"), arguments("i"), arguments("d"), arguments("b"), arguments("i"), arguments("d"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFieldForceAccess_1_6to15")
    public void testGetFieldForceAccess_1_6to15(String param1) {
        assertEquals(PublicChild.class, FieldUtils.getField(PublicChild.class, param1, true).getDeclaringClass());
    }

    static public Stream<Arguments> Provider_testGetFieldForceAccess_1_6to15() {
        return Stream.of(arguments("VALUE"), arguments("VALUE"), arguments("s"), arguments("b"), arguments("i"), arguments("d"), arguments("VALUE"), arguments("s"), arguments("b"), arguments("i"), arguments("d"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFieldForceAccess_2to5")
    public void testGetFieldForceAccess_2to5(String param1) {
        assertEquals(parentClass, FieldUtils.getField(PublicChild.class, param1, true).getDeclaringClass());
    }

    static public Stream<Arguments> Provider_testGetFieldForceAccess_2to5() {
        return Stream.of(arguments("s"), arguments("b"), arguments("i"), arguments("d"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testReadStaticFieldForceAccess_1to2")
    public void testReadStaticFieldForceAccess_1to2(String param1) throws Exception {
        assertEquals(Foo.VALUE, FieldUtils.readStaticField(FieldUtils.getField(Foo.class, param1)));
    }

    static public Stream<Arguments> Provider_testReadStaticFieldForceAccess_1to2() {
        return Stream.of(arguments("VALUE"), arguments("VALUE"));
    }
}
