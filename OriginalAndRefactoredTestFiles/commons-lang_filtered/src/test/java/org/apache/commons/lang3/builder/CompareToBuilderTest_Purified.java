package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigInteger;
import java.util.Objects;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class CompareToBuilderTest_Purified extends AbstractLangTest {

    static class TestObject implements Comparable<TestObject> {

        private int a;

        TestObject(final int a) {
            this.a = a;
        }

        @Override
        public int compareTo(final TestObject rhs) {
            return Integer.compare(a, rhs.a);
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
            return Objects.hash(getA(), b);
        }
    }

    static class TestTransientSubObject extends TestObject {

        @SuppressWarnings("unused")
        private final transient int t;

        TestTransientSubObject(final int a, final int t) {
            super(a);
            this.t = t;
        }
    }

    private void assertReflectionCompareContract(final Object x, final Object y, final Object z, final boolean testTransients, final String[] excludeFields) {
        assertEquals(reflectionCompareSignum(x, y, testTransients, excludeFields), -reflectionCompareSignum(y, x, testTransients, excludeFields));
        if (CompareToBuilder.reflectionCompare(x, y, testTransients, null, excludeFields) > 0 && CompareToBuilder.reflectionCompare(y, z, testTransients, null, excludeFields) > 0) {
            assertTrue(CompareToBuilder.reflectionCompare(x, z, testTransients, null, excludeFields) > 0);
        }
        if (CompareToBuilder.reflectionCompare(x, y, testTransients, null, excludeFields) == 0) {
            assertEquals(reflectionCompareSignum(x, z, testTransients, excludeFields), -reflectionCompareSignum(y, z, testTransients, excludeFields));
        }
        assertTrue(CompareToBuilder.reflectionCompare(x, y, testTransients) == 0 == EqualsBuilder.reflectionEquals(x, y, testTransients));
    }

    private void assertXYZCompareOrder(final Object x, final Object y, final Object z, final boolean testTransients, final String[] excludeFields) {
        assertEquals(0, CompareToBuilder.reflectionCompare(x, x, testTransients, null, excludeFields));
        assertEquals(0, CompareToBuilder.reflectionCompare(y, y, testTransients, null, excludeFields));
        assertEquals(0, CompareToBuilder.reflectionCompare(z, z, testTransients, null, excludeFields));
        assertTrue(0 > CompareToBuilder.reflectionCompare(x, y, testTransients, null, excludeFields));
        assertTrue(0 > CompareToBuilder.reflectionCompare(x, z, testTransients, null, excludeFields));
        assertTrue(0 > CompareToBuilder.reflectionCompare(y, z, testTransients, null, excludeFields));
        assertTrue(0 < CompareToBuilder.reflectionCompare(y, x, testTransients, null, excludeFields));
        assertTrue(0 < CompareToBuilder.reflectionCompare(z, x, testTransients, null, excludeFields));
        assertTrue(0 < CompareToBuilder.reflectionCompare(z, y, testTransients, null, excludeFields));
    }

    private int reflectionCompareSignum(final Object lhs, final Object rhs, final boolean testTransients, final String[] excludeFields) {
        return BigInteger.valueOf(CompareToBuilder.reflectionCompare(lhs, rhs, testTransients)).signum();
    }

    @Test
    public void testDouble_1_testMerged_1() {
        final double o1 = 1;
        final double o2 = 2;
        assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison());
        assertTrue(new CompareToBuilder().append(o1, o2).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(o2, o1).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(o1, Double.MAX_VALUE).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(Double.MAX_VALUE, o1).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(o1, Double.MIN_VALUE).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(Double.MIN_VALUE, o1).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(o1, Double.NaN).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(Double.NaN, o1).toComparison() > 0);
    }

    @Test
    public void testDouble_8() {
        assertEquals(0, new CompareToBuilder().append(Double.NaN, Double.NaN).toComparison());
    }

    @Test
    public void testDouble_9() {
        assertTrue(new CompareToBuilder().append(Double.NaN, Double.MAX_VALUE).toComparison() > 0);
    }

    @Test
    public void testDouble_10() {
        assertTrue(new CompareToBuilder().append(Double.POSITIVE_INFINITY, Double.MAX_VALUE).toComparison() > 0);
    }

    @Test
    public void testDouble_11() {
        assertTrue(new CompareToBuilder().append(Double.NEGATIVE_INFINITY, Double.MIN_VALUE).toComparison() < 0);
    }

    @Test
    public void testDouble_14() {
        assertTrue(new CompareToBuilder().append(-0.0, 0.0).toComparison() < 0);
    }

    @Test
    public void testDouble_15() {
        assertTrue(new CompareToBuilder().append(0.0, -0.0).toComparison() > 0);
    }

    @Test
    public void testFloat_1_testMerged_1() {
        final float o1 = 1;
        final float o2 = 2;
        assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison());
        assertTrue(new CompareToBuilder().append(o1, o2).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(o2, o1).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(o1, Float.MAX_VALUE).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(Float.MAX_VALUE, o1).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(o1, Float.MIN_VALUE).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(Float.MIN_VALUE, o1).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(o1, Float.NaN).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(Float.NaN, o1).toComparison() > 0);
    }

    @Test
    public void testFloat_8() {
        assertEquals(0, new CompareToBuilder().append(Float.NaN, Float.NaN).toComparison());
    }

    @Test
    public void testFloat_9() {
        assertTrue(new CompareToBuilder().append(Float.NaN, Float.MAX_VALUE).toComparison() > 0);
    }

    @Test
    public void testFloat_10() {
        assertTrue(new CompareToBuilder().append(Float.POSITIVE_INFINITY, Float.MAX_VALUE).toComparison() > 0);
    }

    @Test
    public void testFloat_11() {
        assertTrue(new CompareToBuilder().append(Float.NEGATIVE_INFINITY, Float.MIN_VALUE).toComparison() < 0);
    }

    @Test
    public void testFloat_14() {
        assertTrue(new CompareToBuilder().append(-0.0, 0.0).toComparison() < 0);
    }

    @Test
    public void testFloat_15() {
        assertTrue(new CompareToBuilder().append(0.0, -0.0).toComparison() > 0);
    }

    @Test
    public void testObject_1_testMerged_1() {
        final TestObject o1 = new TestObject(4);
        final TestObject o2 = new TestObject(4);
        assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison());
        assertEquals(0, new CompareToBuilder().append(o1, o2).toComparison());
        o2.setA(5);
        assertTrue(new CompareToBuilder().append(o1, o2).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(o2, o1).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(o1, null).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(null, o1).toComparison() < 0);
    }

    @Test
    public void testObject_6() {
        assertEquals(0, new CompareToBuilder().append((Object) null, null).toComparison());
    }

    @Test
    public void testObjectBuild_1_testMerged_1() {
        final TestObject o1 = new TestObject(4);
        final TestObject o2 = new TestObject(4);
        assertEquals(Integer.valueOf(0), new CompareToBuilder().append(o1, o1).build());
        assertEquals(Integer.valueOf(0), new CompareToBuilder().append(o1, o2).build());
        o2.setA(5);
        assertTrue(new CompareToBuilder().append(o1, o2).build().intValue() < 0);
        assertTrue(new CompareToBuilder().append(o2, o1).build().intValue() > 0);
        assertTrue(new CompareToBuilder().append(o1, null).build().intValue() > 0);
        assertTrue(new CompareToBuilder().append(null, o1).build().intValue() < 0);
    }

    @Test
    public void testObjectBuild_6() {
        assertEquals(Integer.valueOf(0), new CompareToBuilder().append((Object) null, null).build());
    }

    @Test
    public void testObjectComparator_1_testMerged_1() {
        final String o1 = "Fred";
        String o2 = "Fred";
        assertEquals(0, new CompareToBuilder().append(o1, o1, String.CASE_INSENSITIVE_ORDER).toComparison());
        assertEquals(0, new CompareToBuilder().append(o1, o2, String.CASE_INSENSITIVE_ORDER).toComparison());
        o2 = "FRED";
        assertEquals(0, new CompareToBuilder().append(o2, o1, String.CASE_INSENSITIVE_ORDER).toComparison());
        o2 = "FREDA";
        assertTrue(new CompareToBuilder().append(o1, o2, String.CASE_INSENSITIVE_ORDER).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(o2, o1, String.CASE_INSENSITIVE_ORDER).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(o1, null, String.CASE_INSENSITIVE_ORDER).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(null, o1, String.CASE_INSENSITIVE_ORDER).toComparison() < 0);
    }

    @Test
    public void testObjectComparator_8() {
        assertEquals(0, new CompareToBuilder().append(null, null, String.CASE_INSENSITIVE_ORDER).toComparison());
    }

    @Test
    public void testObjectComparatorNull_1_testMerged_1() {
        final String o1 = "Fred";
        String o2 = "Fred";
        assertEquals(0, new CompareToBuilder().append(o1, o1, null).toComparison());
        assertEquals(0, new CompareToBuilder().append(o1, o2, null).toComparison());
        o2 = "Zebra";
        assertTrue(new CompareToBuilder().append(o1, o2, null).toComparison() < 0);
        assertTrue(new CompareToBuilder().append(o2, o1, null).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(o1, null, null).toComparison() > 0);
        assertTrue(new CompareToBuilder().append(null, o1, null).toComparison() < 0);
    }

    @Test
    public void testObjectComparatorNull_6() {
        assertEquals(0, new CompareToBuilder().append(null, null, null).toComparison());
    }
}
