package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class HashCodeBuilderTest_Purified extends AbstractLangTest {

    static class ReflectionTestCycleA {

        ReflectionTestCycleB b;

        @Override
        public boolean equals(final Object o) {
            return super.equals(o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }

    static class ReflectionTestCycleB {

        ReflectionTestCycleA a;

        @Override
        public boolean equals(final Object o) {
            return super.equals(o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }

    static class TestObject {

        private int a;

        TestObject(final int a) {
            this.a = a;
        }

        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof TestObject)) {
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

    static class TestObjectHashCodeExclude {

        @HashCodeExclude
        private final int a;

        private final int b;

        TestObjectHashCodeExclude(final int a, final int b) {
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

    static class TestObjectHashCodeExclude2 {

        @HashCodeExclude
        private final int a;

        @HashCodeExclude
        private final int b;

        TestObjectHashCodeExclude2(final int a, final int b) {
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

    static class TestObjectWithMultipleFields {

        @SuppressWarnings("unused")
        private final int one;

        @SuppressWarnings("unused")
        private final int two;

        @SuppressWarnings("unused")
        private final int three;

        TestObjectWithMultipleFields(final int one, final int two, final int three) {
            this.one = one;
            this.two = two;
            this.three = three;
        }
    }

    static class TestSubObject extends TestObject {

        private int b;

        @SuppressWarnings("unused")
        private transient int t;

        TestSubObject() {
            super(0);
        }

        TestSubObject(final int a, final int b, final int t) {
            super(a);
            this.b = b;
            this.t = t;
        }

        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof TestSubObject)) {
                return false;
            }
            final TestSubObject rhs = (TestSubObject) o;
            return super.equals(o) && b == rhs.b;
        }

        @Override
        public int hashCode() {
            return b * 17 + super.hashCode();
        }
    }

    @Test
    public void testBoolean_1() {
        assertEquals(17 * 37 + 0, new HashCodeBuilder(17, 37).append(true).toHashCode());
    }

    @Test
    public void testBoolean_2() {
        assertEquals(17 * 37 + 1, new HashCodeBuilder(17, 37).append(false).toHashCode());
    }

    @Test
    public void testByte_1() {
        assertEquals(17 * 37, new HashCodeBuilder(17, 37).append((byte) 0).toHashCode());
    }

    @Test
    public void testByte_2() {
        assertEquals(17 * 37 + 123, new HashCodeBuilder(17, 37).append((byte) 123).toHashCode());
    }

    @Test
    public void testChar_1() {
        assertEquals(17 * 37, new HashCodeBuilder(17, 37).append((char) 0).toHashCode());
    }

    @Test
    public void testChar_2() {
        assertEquals(17 * 37 + 1234, new HashCodeBuilder(17, 37).append((char) 1234).toHashCode());
    }

    @Test
    public void testDouble_1() {
        assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0d).toHashCode());
    }

    @Test
    public void testDouble_2() {
        final double d = 1234567.89;
        final long l = Double.doubleToLongBits(d);
        assertEquals(17 * 37 + (int) (l ^ l >> 32), new HashCodeBuilder(17, 37).append(d).toHashCode());
    }

    @Test
    public void testFloat_1() {
        assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0f).toHashCode());
    }

    @Test
    public void testFloat_2() {
        final float f = 1234.89f;
        final int i = Float.floatToIntBits(f);
        assertEquals(17 * 37 + i, new HashCodeBuilder(17, 37).append(f).toHashCode());
    }

    @Test
    public void testInt_1() {
        assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0).toHashCode());
    }

    @Test
    public void testInt_2() {
        assertEquals(17 * 37 + 123456, new HashCodeBuilder(17, 37).append(123456).toHashCode());
    }

    @Test
    public void testLong_1() {
        assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0L).toHashCode());
    }

    @Test
    public void testLong_2() {
        assertEquals(17 * 37 + (int) (123456789L ^ 123456789L >> 32), new HashCodeBuilder(17, 37).append(123456789L).toHashCode());
    }

    @Test
    public void testReflectionHashCode_1() {
        assertEquals(17 * 37, HashCodeBuilder.reflectionHashCode(new TestObject(0)));
    }

    @Test
    public void testReflectionHashCode_2() {
        assertEquals(17 * 37 + 123456, HashCodeBuilder.reflectionHashCode(new TestObject(123456)));
    }

    @Test
    public void testReflectionHierarchyHashCode_1() {
        assertEquals(17 * 37 * 37, HashCodeBuilder.reflectionHashCode(new TestSubObject(0, 0, 0)));
    }

    @Test
    public void testReflectionHierarchyHashCode_2() {
        assertEquals(17 * 37 * 37 * 37, HashCodeBuilder.reflectionHashCode(new TestSubObject(0, 0, 0), true));
    }

    @Test
    public void testReflectionHierarchyHashCode_3() {
        assertEquals((17 * 37 + 7890) * 37 + 123456, HashCodeBuilder.reflectionHashCode(new TestSubObject(123456, 7890, 0)));
    }

    @Test
    public void testReflectionHierarchyHashCode_4() {
        assertEquals(((17 * 37 + 7890) * 37 + 0) * 37 + 123456, HashCodeBuilder.reflectionHashCode(new TestSubObject(123456, 7890, 0), true));
    }

    @Test
    public void testReflectionObjectCycle_1() {
        assertTrue(HashCodeBuilder.getRegistry().isEmpty());
    }

    @Test
    public void testReflectionObjectCycle_2() {
        assertTrue(HashCodeBuilder.getRegistry().isEmpty());
    }

    @Test
    public void testShort_1() {
        assertEquals(17 * 37, new HashCodeBuilder(17, 37).append((short) 0).toHashCode());
    }

    @Test
    public void testShort_2() {
        assertEquals(17 * 37 + 12345, new HashCodeBuilder(17, 37).append((short) 12345).toHashCode());
    }

    @Test
    public void testToHashCodeExclude_1() {
        final TestObjectHashCodeExclude one = new TestObjectHashCodeExclude(1, 2);
        assertEquals(17 * 37 + 2, HashCodeBuilder.reflectionHashCode(one));
    }

    @Test
    public void testToHashCodeExclude_2() {
        final TestObjectHashCodeExclude2 two = new TestObjectHashCodeExclude2(1, 2);
        assertEquals(17, HashCodeBuilder.reflectionHashCode(two));
    }
}
