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

public class FieldUtilsTest_Purified extends AbstractLangTest {

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

    @Test
    public void testGetDeclaredField_1() {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, "VALUE"));
    }

    @Test
    public void testGetDeclaredField_2() {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, "s"));
    }

    @Test
    public void testGetDeclaredField_3() {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, "b"));
    }

    @Test
    public void testGetDeclaredField_4() {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, "i"));
    }

    @Test
    public void testGetDeclaredField_5() {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, "d"));
    }

    @Test
    public void testGetDeclaredField_6() {
        assertNull(FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "VALUE"));
    }

    @Test
    public void testGetDeclaredField_7() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "s").getDeclaringClass());
    }

    @Test
    public void testGetDeclaredField_8() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "b").getDeclaringClass());
    }

    @Test
    public void testGetDeclaredField_9() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "i").getDeclaringClass());
    }

    @Test
    public void testGetDeclaredField_10() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "d").getDeclaringClass());
    }

    @Test
    public void testGetDeclaredField_11() {
        assertNull(FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "VALUE"));
    }

    @Test
    public void testGetDeclaredField_12() {
        assertNull(FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "s"));
    }

    @Test
    public void testGetDeclaredField_13() {
        assertNull(FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "b"));
    }

    @Test
    public void testGetDeclaredField_14() {
        assertNull(FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "i"));
    }

    @Test
    public void testGetDeclaredField_15() {
        assertNull(FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "d"));
    }

    @Test
    public void testGetDeclaredFieldForceAccess_1() {
        assertEquals(PublicChild.class, FieldUtils.getDeclaredField(PublicChild.class, "VALUE", true).getDeclaringClass());
    }

    @Test
    public void testGetDeclaredFieldForceAccess_2() {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, "s", true));
    }

    @Test
    public void testGetDeclaredFieldForceAccess_3() {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, "b", true));
    }

    @Test
    public void testGetDeclaredFieldForceAccess_4() {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, "i", true));
    }

    @Test
    public void testGetDeclaredFieldForceAccess_5() {
        assertNull(FieldUtils.getDeclaredField(PublicChild.class, "d", true));
    }

    @Test
    public void testGetDeclaredFieldForceAccess_6() {
        assertNull(FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "VALUE", true));
    }

    @Test
    public void testGetDeclaredFieldForceAccess_7() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "s", true).getDeclaringClass());
    }

    @Test
    public void testGetDeclaredFieldForceAccess_8() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "b", true).getDeclaringClass());
    }

    @Test
    public void testGetDeclaredFieldForceAccess_9() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "i", true).getDeclaringClass());
    }

    @Test
    public void testGetDeclaredFieldForceAccess_10() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getDeclaredField(PubliclyShadowedChild.class, "d", true).getDeclaringClass());
    }

    @Test
    public void testGetDeclaredFieldForceAccess_11() {
        assertNull(FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "VALUE", true));
    }

    @Test
    public void testGetDeclaredFieldForceAccess_12() {
        assertEquals(PrivatelyShadowedChild.class, FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "s", true).getDeclaringClass());
    }

    @Test
    public void testGetDeclaredFieldForceAccess_13() {
        assertEquals(PrivatelyShadowedChild.class, FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "b", true).getDeclaringClass());
    }

    @Test
    public void testGetDeclaredFieldForceAccess_14() {
        assertEquals(PrivatelyShadowedChild.class, FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "i", true).getDeclaringClass());
    }

    @Test
    public void testGetDeclaredFieldForceAccess_15() {
        assertEquals(PrivatelyShadowedChild.class, FieldUtils.getDeclaredField(PrivatelyShadowedChild.class, "d", true).getDeclaringClass());
    }

    @Test
    public void testGetField_1() {
        assertEquals(Foo.class, FieldUtils.getField(PublicChild.class, "VALUE").getDeclaringClass());
    }

    @Test
    public void testGetField_2() {
        assertEquals(parentClass, FieldUtils.getField(PublicChild.class, "s").getDeclaringClass());
    }

    @Test
    public void testGetField_3() {
        assertNull(FieldUtils.getField(PublicChild.class, "b"));
    }

    @Test
    public void testGetField_4() {
        assertNull(FieldUtils.getField(PublicChild.class, "i"));
    }

    @Test
    public void testGetField_5() {
        assertNull(FieldUtils.getField(PublicChild.class, "d"));
    }

    @Test
    public void testGetField_6() {
        assertEquals(Foo.class, FieldUtils.getField(PubliclyShadowedChild.class, "VALUE").getDeclaringClass());
    }

    @Test
    public void testGetField_7() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getField(PubliclyShadowedChild.class, "s").getDeclaringClass());
    }

    @Test
    public void testGetField_8() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getField(PubliclyShadowedChild.class, "b").getDeclaringClass());
    }

    @Test
    public void testGetField_9() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getField(PubliclyShadowedChild.class, "i").getDeclaringClass());
    }

    @Test
    public void testGetField_10() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getField(PubliclyShadowedChild.class, "d").getDeclaringClass());
    }

    @Test
    public void testGetField_11() {
        assertEquals(Foo.class, FieldUtils.getField(PrivatelyShadowedChild.class, "VALUE").getDeclaringClass());
    }

    @Test
    public void testGetField_12() {
        assertEquals(parentClass, FieldUtils.getField(PrivatelyShadowedChild.class, "s").getDeclaringClass());
    }

    @Test
    public void testGetField_13() {
        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, "b"));
    }

    @Test
    public void testGetField_14() {
        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, "i"));
    }

    @Test
    public void testGetField_15() {
        assertNull(FieldUtils.getField(PrivatelyShadowedChild.class, "d"));
    }

    @Test
    public void testGetFieldForceAccess_1() {
        assertEquals(PublicChild.class, FieldUtils.getField(PublicChild.class, "VALUE", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_2() {
        assertEquals(parentClass, FieldUtils.getField(PublicChild.class, "s", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_3() {
        assertEquals(parentClass, FieldUtils.getField(PublicChild.class, "b", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_4() {
        assertEquals(parentClass, FieldUtils.getField(PublicChild.class, "i", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_5() {
        assertEquals(parentClass, FieldUtils.getField(PublicChild.class, "d", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_6() {
        assertEquals(Foo.class, FieldUtils.getField(PubliclyShadowedChild.class, "VALUE", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_7() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getField(PubliclyShadowedChild.class, "s", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_8() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getField(PubliclyShadowedChild.class, "b", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_9() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getField(PubliclyShadowedChild.class, "i", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_10() {
        assertEquals(PubliclyShadowedChild.class, FieldUtils.getField(PubliclyShadowedChild.class, "d", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_11() {
        assertEquals(Foo.class, FieldUtils.getField(PrivatelyShadowedChild.class, "VALUE", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_12() {
        assertEquals(PrivatelyShadowedChild.class, FieldUtils.getField(PrivatelyShadowedChild.class, "s", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_13() {
        assertEquals(PrivatelyShadowedChild.class, FieldUtils.getField(PrivatelyShadowedChild.class, "b", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_14() {
        assertEquals(PrivatelyShadowedChild.class, FieldUtils.getField(PrivatelyShadowedChild.class, "i", true).getDeclaringClass());
    }

    @Test
    public void testGetFieldForceAccess_15() {
        assertEquals(PrivatelyShadowedChild.class, FieldUtils.getField(PrivatelyShadowedChild.class, "d", true).getDeclaringClass());
    }

    @Test
    public void testReadStaticFieldForceAccess_1() throws Exception {
        assertEquals(Foo.VALUE, FieldUtils.readStaticField(FieldUtils.getField(Foo.class, "VALUE")));
    }

    @Test
    public void testReadStaticFieldForceAccess_2() throws Exception {
        assertEquals(Foo.VALUE, FieldUtils.readStaticField(FieldUtils.getField(PublicChild.class, "VALUE")));
    }
}
