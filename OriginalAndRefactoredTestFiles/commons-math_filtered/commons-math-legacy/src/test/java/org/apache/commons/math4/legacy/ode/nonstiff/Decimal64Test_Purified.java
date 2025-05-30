package org.apache.commons.math4.legacy.ode.nonstiff;

import org.apache.commons.math4.legacy.field.ExtendedFieldElementAbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class Decimal64Test_Purified extends ExtendedFieldElementAbstractTest<Decimal64> {

    public static final double X = 1.2345;

    public static final Decimal64 PLUS_X = new Decimal64(X);

    public static final Decimal64 MINUS_X = new Decimal64(-X);

    public static final double Y = 6.789;

    public static final Decimal64 PLUS_Y = new Decimal64(Y);

    public static final Decimal64 MINUS_Y = new Decimal64(-Y);

    public static final Decimal64 PLUS_ZERO = new Decimal64(0.0);

    public static final Decimal64 MINUS_ZERO = new Decimal64(-0.0);

    @Override
    protected Decimal64 build(final double x) {
        return new Decimal64(x);
    }

    @Test
    public void testIsInfinite_1() {
        Assert.assertFalse(MINUS_X.isInfinite());
    }

    @Test
    public void testIsInfinite_2() {
        Assert.assertFalse(PLUS_X.isInfinite());
    }

    @Test
    public void testIsInfinite_3() {
        Assert.assertFalse(MINUS_Y.isInfinite());
    }

    @Test
    public void testIsInfinite_4() {
        Assert.assertFalse(PLUS_Y.isInfinite());
    }

    @Test
    public void testIsInfinite_5() {
        Assert.assertFalse(Decimal64.NAN.isInfinite());
    }

    @Test
    public void testIsInfinite_6() {
        Assert.assertTrue(Decimal64.NEGATIVE_INFINITY.isInfinite());
    }

    @Test
    public void testIsInfinite_7() {
        Assert.assertTrue(Decimal64.POSITIVE_INFINITY.isInfinite());
    }

    @Test
    public void testIsNaN_1() {
        Assert.assertFalse(MINUS_X.isNaN());
    }

    @Test
    public void testIsNaN_2() {
        Assert.assertFalse(PLUS_X.isNaN());
    }

    @Test
    public void testIsNaN_3() {
        Assert.assertFalse(MINUS_Y.isNaN());
    }

    @Test
    public void testIsNaN_4() {
        Assert.assertFalse(PLUS_Y.isNaN());
    }

    @Test
    public void testIsNaN_5() {
        Assert.assertFalse(Decimal64.NEGATIVE_INFINITY.isNaN());
    }

    @Test
    public void testIsNaN_6() {
        Assert.assertFalse(Decimal64.POSITIVE_INFINITY.isNaN());
    }

    @Test
    public void testIsNaN_7() {
        Assert.assertTrue(Decimal64.NAN.isNaN());
    }
}
