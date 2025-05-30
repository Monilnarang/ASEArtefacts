package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.lang3.function.Suppliers;
import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation")
public class ArrayUtilsTest_Purified extends AbstractLangTest {

    private static final class TestClass {
    }

    private static final long SEED = 16111981L;

    @SafeVarargs
    private static <T> T[] toArrayPropagatingType(final T... items) {
        return ArrayUtils.toArray(items);
    }

    private void assertIsEquals(final Object array1, final Object array2, final Object array3) {
        assertTrue(ArrayUtils.isEquals(array1, array1));
        assertTrue(ArrayUtils.isEquals(array2, array2));
        assertTrue(ArrayUtils.isEquals(array3, array3));
        assertFalse(ArrayUtils.isEquals(array1, array2));
        assertFalse(ArrayUtils.isEquals(array2, array1));
        assertFalse(ArrayUtils.isEquals(array1, array3));
        assertFalse(ArrayUtils.isEquals(array3, array1));
        assertFalse(ArrayUtils.isEquals(array1, array2));
        assertFalse(ArrayUtils.isEquals(array2, array1));
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new ArrayUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = ArrayUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(ArrayUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(ArrayUtils.class.getModifiers()));
    }

    @Test
    public void testContainsAnyEnum_1() {
        assertTrue(ArrayUtils.containsAny(ElementType.values(), ElementType.ANNOTATION_TYPE));
    }

    @Test
    public void testContainsAnyEnum_2() {
        assertFalse(ArrayUtils.containsAny(ElementType.values(), (ElementType) null));
    }
}
