package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class DiffBuilderTest_Purified extends AbstractLangTest {

    private static class NestedTypeTestClass implements Diffable<NestedTypeTestClass> {

        private final ToStringStyle style = SHORT_STYLE;

        private boolean booleanField = true;

        @Override
        public DiffResult<NestedTypeTestClass> diff(final NestedTypeTestClass obj) {
            return new DiffBuilder<>(this, obj, style).append("boolean", booleanField, obj.booleanField).build();
        }

        @Override
        public boolean equals(final Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj, false);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }
    }

    private static final class TypeTestClass implements Diffable<TypeTestClass> {

        private ToStringStyle style = SHORT_STYLE;

        private boolean booleanField = true;

        private boolean[] booleanArrayField = { true };

        private byte byteField = (byte) 0xFF;

        private byte[] byteArrayField = { (byte) 0xFF };

        private char charField = 'a';

        private char[] charArrayField = { 'a' };

        private double doubleField = 1.0;

        private double[] doubleArrayField = { 1.0 };

        private float floatField = 1.0f;

        private float[] floatArrayField = { 1.0f };

        private int intField = 1;

        private int[] intArrayField = { 1 };

        private long longField = 1L;

        private long[] longArrayField = { 1L };

        private short shortField = 1;

        private short[] shortArrayField = { 1 };

        private Object objectField;

        private Object[] objectArrayField = { null };

        private final NestedTypeTestClass nestedDiffableField = new NestedTypeTestClass();

        @Override
        public DiffResult<TypeTestClass> diff(final TypeTestClass obj) {
            return new DiffBuilder<>(this, obj, style).append("boolean", booleanField, obj.booleanField).append("booleanArray", booleanArrayField, obj.booleanArrayField).append("byte", byteField, obj.byteField).append("byteArray", byteArrayField, obj.byteArrayField).append("char", charField, obj.charField).append("charArray", charArrayField, obj.charArrayField).append("double", doubleField, obj.doubleField).append("doubleArray", doubleArrayField, obj.doubleArrayField).append("float", floatField, obj.floatField).append("floatArray", floatArrayField, obj.floatArrayField).append("int", intField, obj.intField).append("intArray", intArrayField, obj.intArrayField).append("long", longField, obj.longField).append("longArray", longArrayField, obj.longArrayField).append("short", shortField, obj.shortField).append("shortArray", shortArrayField, obj.shortArrayField).append("objectField", objectField, obj.objectField).append("objectArrayField", objectArrayField, obj.objectArrayField).append("nestedDiffableField", nestedDiffableField.diff(obj.nestedDiffableField)).build();
        }

        @Override
        public boolean equals(final Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj, false);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }
    }

    private static final ToStringStyle SHORT_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;

    @Test
    public void testTriviallyEqualTestDisabled_1() {
        final DiffBuilder<Integer> explicitTestAndNotEqual1 = new DiffBuilder<>(1, 2, null, false);
        explicitTestAndNotEqual1.append("letter", "X", "Y");
        assertEquals(1, explicitTestAndNotEqual1.build().getNumberOfDiffs());
    }

    @Test
    public void testTriviallyEqualTestDisabled_2() {
        final DiffBuilder<Integer> explicitTestAndNotEqual2 = new DiffBuilder<>(1, 1, null, false);
        explicitTestAndNotEqual2.append("letter", "X", "Y");
        assertEquals(1, explicitTestAndNotEqual2.build().getNumberOfDiffs());
    }

    @Test
    public void testTriviallyEqualTestEnabled_1() {
        final DiffBuilder<Integer> implicitTestAndEqual = new DiffBuilder<>(1, 1, null);
        implicitTestAndEqual.append("letter", "X", "Y");
        assertEquals(0, implicitTestAndEqual.build().getNumberOfDiffs());
    }

    @Test
    public void testTriviallyEqualTestEnabled_2() {
        final DiffBuilder<Integer> implicitTestAndNotEqual = new DiffBuilder<>(1, 2, null);
        implicitTestAndNotEqual.append("letter", "X", "Y");
        assertEquals(1, implicitTestAndNotEqual.build().getNumberOfDiffs());
    }

    @Test
    public void testTriviallyEqualTestEnabled_3() {
        final DiffBuilder<Integer> explicitTestAndEqual = new DiffBuilder<>(1, 1, null, true);
        explicitTestAndEqual.append("letter", "X", "Y");
        assertEquals(0, explicitTestAndEqual.build().getNumberOfDiffs());
    }
}
