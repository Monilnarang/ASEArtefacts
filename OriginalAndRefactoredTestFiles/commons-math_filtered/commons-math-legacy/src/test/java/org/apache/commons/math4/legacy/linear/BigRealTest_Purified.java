package org.apache.commons.math4.legacy.linear;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.MathArithmeticException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class BigRealTest_Purified {

    @Test
    public void testBigDecimalValue_1() {
        BigDecimal pi = new BigDecimal("3.1415926535897932384626433832795028841971693993751");
        Assert.assertEquals(pi, new BigReal(pi).bigDecimalValue());
    }

    @Test
    public void testBigDecimalValue_2() {
        Assert.assertEquals(new BigDecimal(0.5), new BigReal(1.0 / 2.0).bigDecimalValue());
    }

    @Test
    public void testEqualsAndHashCode_1_testMerged_1() {
        BigReal zero = new BigReal(0.0);
        BigReal nullReal = null;
        Assert.assertEquals(zero, zero);
        Assert.assertNotEquals(zero, nullReal);
        Assert.assertFalse(zero.equals(Double.valueOf(0)));
        BigReal zero2 = new BigReal(0.0);
        Assert.assertEquals(zero, zero2);
        Assert.assertEquals(zero.hashCode(), zero2.hashCode());
        BigReal one = new BigReal(1.0);
        Assert.assertFalse(one.equals(zero) || zero.equals(one));
        Assert.assertEquals(one, BigReal.ONE);
    }

    @Test
    public void testEqualsAndHashCode_8_testMerged_2() {
        BigReal oneWithScaleOne = new BigReal(new BigDecimal("1.0"));
        BigReal oneWithScaleTwo = new BigReal(new BigDecimal("1.00"));
        Assert.assertEquals(oneWithScaleOne, oneWithScaleTwo);
        Assert.assertEquals(oneWithScaleOne.hashCode(), oneWithScaleTwo.hashCode());
    }
}
