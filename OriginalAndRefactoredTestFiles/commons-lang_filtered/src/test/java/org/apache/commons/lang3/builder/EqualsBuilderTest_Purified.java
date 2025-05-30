package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Test;

public class EqualsBuilderTest_Purified extends AbstractLangTest {

    public static class TestACanEqualB {

        private final int a;

        public TestACanEqualB(final int a) {
            this.a = a;
        }

        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof TestACanEqualB) {
                return this.a == ((TestACanEqualB) o).getA();
            }
            if (o instanceof TestBCanEqualA) {
                return this.a == ((TestBCanEqualA) o).getB();
            }
            return false;
        }

        public int getA() {
            return this.a;
        }

        @Override
        public int hashCode() {
            return a;
        }
    }

    public static class TestBCanEqualA {

        private final int b;

        public TestBCanEqualA(final int b) {
            this.b = b;
        }

        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof TestACanEqualB) {
                return this.b == ((TestACanEqualB) o).getA();
            }
            if (o instanceof TestBCanEqualA) {
                return this.b == ((TestBCanEqualA) o).getB();
            }
            return false;
        }

        public int getB() {
            return this.b;
        }

        @Override
        public int hashCode() {
            return b;
        }
    }

    static class TestEmptySubObject extends TestObject {

        TestEmptySubObject(final int a) {
            super(a);
        }
    }

    static class TestObject {

        private int a;

        TestObject() {
        }

        TestObject(final int a) {
            this.a = a;
        }

        @Override
        public boolean equals(final Object o) {
            if (o == null) {
                return false;
            }
            if (o == this) {
                return true;
            }
            if (o.getClass() != getClass()) {
                return false;
            }
            final TestObject rhs = (TestObject) o;
            return a == rhs.a;
        }

        public int getA() {
            return a;
        }

        @Override
        public int hashCode() {
            return a;
        }

        public void setA(final int a) {
            this.a = a;
        }
    }

    static class TestObjectEqualsExclude {

        @EqualsExclude
        private final int a;

        private final int b;

        TestObjectEqualsExclude(final int a, final int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }
    }

    static class TestObjectReference {

        @SuppressWarnings("unused")
        private TestObjectReference reference;

        @SuppressWarnings("unused")
        private final TestObject one;

        TestObjectReference(final int one) {
            this.one = new TestObject(one);
        }

        @Override
        public boolean equals(final Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        @Override
        public int hashCode() {
            return one.hashCode();
        }

        public void setObjectReference(final TestObjectReference reference) {
            this.reference = reference;
        }
    }

    static class TestObjectWithMultipleFields {

        @SuppressWarnings("unused")
        private final TestObject one;

        @SuppressWarnings("unused")
        private final TestObject two;

        @SuppressWarnings("unused")
        private final TestObject three;

        TestObjectWithMultipleFields(final int one, final int two, final int three) {
            this.one = new TestObject(one);
            this.two = new TestObject(two);
            this.three = new TestObject(three);
        }
    }

    static class TestRecursiveCycleObject {

        private TestRecursiveCycleObject cycle;

        private final int n;

        TestRecursiveCycleObject(final int n) {
            this.n = n;
            this.cycle = this;
        }

        TestRecursiveCycleObject(final TestRecursiveCycleObject cycle, final int n) {
            this.n = n;
            this.cycle = cycle;
        }

        public TestRecursiveCycleObject getCycle() {
            return cycle;
        }

        public int getN() {
            return n;
        }

        public void setCycle(final TestRecursiveCycleObject cycle) {
            this.cycle = cycle;
        }
    }

    static class TestRecursiveGenericObject<T> {

        private final T a;

        TestRecursiveGenericObject(final T a) {
            this.a = a;
        }

        public T getA() {
            return a;
        }
    }

    static class TestRecursiveInnerObject {

        private final int n;

        TestRecursiveInnerObject(final int n) {
            this.n = n;
        }

        public int getN() {
            return n;
        }
    }

    static class TestRecursiveObject {

        private final TestRecursiveInnerObject a;

        private final TestRecursiveInnerObject b;

        private int z;

        TestRecursiveObject(final TestRecursiveInnerObject a, final TestRecursiveInnerObject b, final int z) {
            this.a = a;
            this.b = b;
        }

        public TestRecursiveInnerObject getA() {
            return a;
        }

        public TestRecursiveInnerObject getB() {
            return b;
        }

        public int getZ() {
            return z;
        }
    }

    static class TestSubObject extends TestObject {

        private int b;

        TestSubObject() {
            super(0);
        }

        TestSubObject(final int a, final int b) {
            super(a);
            this.b = b;
        }

        @Override
        public boolean equals(final Object o) {
            if (o == null) {
                return false;
            }
            if (o == this) {
                return true;
            }
            if (o.getClass() != getClass()) {
                return false;
            }
            final TestSubObject rhs = (TestSubObject) o;
            return super.equals(o) && b == rhs.b;
        }

        public int getB() {
            return b;
        }

        @Override
        public int hashCode() {
            return b * 17 + super.hashCode();
        }

        public void setB(final int b) {
            this.b = b;
        }
    }

    static class TestTSubObject extends TestObject {

        @SuppressWarnings("unused")
        private final transient int t;

        TestTSubObject(final int a, final int t) {
            super(a);
            this.t = t;
        }
    }

    static class TestTSubObject2 extends TestObject {

        private transient int t;

        TestTSubObject2(final int a, final int t) {
            super(a);
        }

        public int getT() {
            return t;
        }

        public void setT(final int t) {
            this.t = t;
        }
    }

    static class TestTTLeafObject extends TestTTSubObject {

        @SuppressWarnings("unused")
        private final int leafValue;

        TestTTLeafObject(final int a, final int t, final int tt, final int leafValue) {
            super(a, t, tt);
            this.leafValue = leafValue;
        }
    }

    static class TestTTSubObject extends TestTSubObject {

        @SuppressWarnings("unused")
        private final transient int tt;

        TestTTSubObject(final int a, final int t, final int tt) {
            super(a, t);
            this.tt = tt;
        }
    }

    @Test
    public void testBigInteger_1_testMerged_1() {
        final BigInteger o1 = BigInteger.valueOf(1);
        final BigInteger o2 = BigInteger.valueOf(2);
        assertTrue(new EqualsBuilder().append(o1, o1).isEquals());
        assertFalse(new EqualsBuilder().append(o1, o2).isEquals());
        assertFalse(new EqualsBuilder().append(o1, o2).append(o1, o1).isEquals());
        assertFalse(new EqualsBuilder().append(o1, Double.NaN).isEquals());
    }

    @Test
    public void testBigInteger_5() {
        assertTrue(new EqualsBuilder().append(Double.NaN, Double.NaN).isEquals());
    }

    @Test
    public void testBigInteger_6() {
        assertTrue(new EqualsBuilder().append(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY).isEquals());
    }

    @Test
    public void testDouble_1_testMerged_1() {
        final double o1 = 1;
        final double o2 = 2;
        assertTrue(new EqualsBuilder().append(o1, o1).isEquals());
        assertFalse(new EqualsBuilder().append(o1, o2).isEquals());
        assertFalse(new EqualsBuilder().append(o1, o2).append(o1, o1).isEquals());
        assertFalse(new EqualsBuilder().append(o1, Double.NaN).isEquals());
    }

    @Test
    public void testDouble_5() {
        assertTrue(new EqualsBuilder().append(Double.NaN, Double.NaN).isEquals());
    }

    @Test
    public void testDouble_6() {
        assertTrue(new EqualsBuilder().append(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY).isEquals());
    }

    @Test
    public void testFloat_1_testMerged_1() {
        final float o1 = 1;
        final float o2 = 2;
        assertTrue(new EqualsBuilder().append(o1, o1).isEquals());
        assertFalse(new EqualsBuilder().append(o1, o2).isEquals());
        assertFalse(new EqualsBuilder().append(o1, o2).append(o1, o1).isEquals());
        assertFalse(new EqualsBuilder().append(o1, Float.NaN).isEquals());
    }

    @Test
    public void testFloat_5() {
        assertTrue(new EqualsBuilder().append(Float.NaN, Float.NaN).isEquals());
    }

    @Test
    public void testFloat_6() {
        assertTrue(new EqualsBuilder().append(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY).isEquals());
    }

    @Test
    public void testObject_1_testMerged_1() {
        final TestObject o1 = new TestObject(4);
        final TestObject o2 = new TestObject(5);
        assertTrue(new EqualsBuilder().append(o1, o1).isEquals());
        assertFalse(new EqualsBuilder().append(o1, o2).isEquals());
        o2.setA(4);
        assertTrue(new EqualsBuilder().append(o1, o2).isEquals());
        assertFalse(new EqualsBuilder().append(o1, this).isEquals());
        assertFalse(new EqualsBuilder().append(o1, null).isEquals());
        assertFalse(new EqualsBuilder().append(null, o2).isEquals());
    }

    @Test
    public void testObject_7() {
        assertTrue(new EqualsBuilder().append((Object) null, null).isEquals());
    }

    @Test
    public void testObjectBuild_1_testMerged_1() {
        final TestObject o1 = new TestObject(4);
        final TestObject o2 = new TestObject(5);
        assertEquals(Boolean.TRUE, new EqualsBuilder().append(o1, o1).build());
        assertEquals(Boolean.FALSE, new EqualsBuilder().append(o1, o2).build());
        o2.setA(4);
        assertEquals(Boolean.TRUE, new EqualsBuilder().append(o1, o2).build());
        assertEquals(Boolean.FALSE, new EqualsBuilder().append(o1, this).build());
        assertEquals(Boolean.FALSE, new EqualsBuilder().append(o1, null).build());
        assertEquals(Boolean.FALSE, new EqualsBuilder().append(null, o2).build());
    }

    @Test
    public void testObjectBuild_7() {
        assertEquals(Boolean.TRUE, new EqualsBuilder().append((Object) null, null).build());
    }

    @Test
    public void testReflectionAppend_1() {
        assertTrue(EqualsBuilder.reflectionEquals(null, null));
    }

    @Test
    public void testReflectionAppend_2_testMerged_2() {
        final TestObject o1 = new TestObject(4);
        final TestObject o2 = new TestObject(5);
        assertTrue(new EqualsBuilder().reflectionAppend(o1, o1).build());
        assertFalse(new EqualsBuilder().reflectionAppend(o1, o2).build());
        assertFalse(new EqualsBuilder().reflectionAppend(o1, o2).reflectionAppend(o1, o1).build());
        o2.setA(4);
        assertTrue(new EqualsBuilder().reflectionAppend(o1, o2).build());
        assertFalse(new EqualsBuilder().reflectionAppend(o1, this).build());
        assertFalse(new EqualsBuilder().reflectionAppend(o1, null).build());
        assertFalse(new EqualsBuilder().reflectionAppend(null, o2).build());
    }

    @Test
    public void testReflectionEquals_1_testMerged_1() {
        final TestObject o1 = new TestObject(4);
        final TestObject o2 = new TestObject(5);
        assertTrue(EqualsBuilder.reflectionEquals(o1, o1));
        assertFalse(EqualsBuilder.reflectionEquals(o1, o2));
        o2.setA(4);
        assertTrue(EqualsBuilder.reflectionEquals(o1, o2));
        assertFalse(EqualsBuilder.reflectionEquals(o1, this));
        assertFalse(EqualsBuilder.reflectionEquals(o1, null));
        assertFalse(EqualsBuilder.reflectionEquals(null, o2));
    }

    @Test
    public void testReflectionEquals_7() {
        assertTrue(EqualsBuilder.reflectionEquals(null, null));
    }

    @Test
    public void testReflectionHierarchyEquals_1() {
        assertTrue(EqualsBuilder.reflectionEquals(new TestTTLeafObject(1, 2, 3, 4), new TestTTLeafObject(1, 2, 3, 4), true));
    }

    @Test
    public void testReflectionHierarchyEquals_2() {
        assertTrue(EqualsBuilder.reflectionEquals(new TestTTLeafObject(1, 2, 3, 4), new TestTTLeafObject(1, 2, 3, 4), false));
    }

    @Test
    public void testReflectionHierarchyEquals_3() {
        assertFalse(EqualsBuilder.reflectionEquals(new TestTTLeafObject(1, 0, 0, 4), new TestTTLeafObject(1, 2, 3, 4), true));
    }

    @Test
    public void testReflectionHierarchyEquals_4() {
        assertFalse(EqualsBuilder.reflectionEquals(new TestTTLeafObject(1, 2, 3, 4), new TestTTLeafObject(1, 2, 3, 0), true));
    }

    @Test
    public void testReflectionHierarchyEquals_5() {
        assertFalse(EqualsBuilder.reflectionEquals(new TestTTLeafObject(0, 2, 3, 4), new TestTTLeafObject(1, 2, 3, 4), true));
    }
}
