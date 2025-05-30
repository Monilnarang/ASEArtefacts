package org.apache.commons.math4.legacy.analysis.differentiation;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.math4.legacy.field.ExtendedFieldElementAbstractTest;
import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NumberIsTooLargeException;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.numbers.core.ArithmeticUtils;
import org.apache.commons.numbers.combinatorics.Factorial;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.numbers.core.Precision;
import org.junit.Assert;
import org.junit.Test;

public class DerivativeStructureTest_Purified extends ExtendedFieldElementAbstractTest<DerivativeStructure> {

    @Override
    protected DerivativeStructure build(final double x) {
        return new DerivativeStructure(2, 1, 0, x);
    }

    private void checkF0F1(DerivativeStructure ds, double value, double... derivatives) {
        Assert.assertEquals(derivatives.length, ds.getFreeParameters());
        Assert.assertEquals(value, ds.getValue(), 1.0e-15);
        Assert.assertEquals(value, ds.getPartialDerivative(new int[ds.getFreeParameters()]), 1.0e-15);
        for (int i = 0; i < derivatives.length; ++i) {
            int[] orders = new int[derivatives.length];
            orders[i] = 1;
            Assert.assertEquals(derivatives[i], ds.getPartialDerivative(orders), 1.0e-15);
        }
    }

    private void checkEquals(DerivativeStructure ds1, DerivativeStructure ds2, double epsilon) {
        Assert.assertEquals(ds1.getFreeParameters(), ds2.getFreeParameters());
        Assert.assertEquals(ds1.getOrder(), ds2.getOrder());
        int[] derivatives = new int[ds1.getFreeParameters()];
        int sum = 0;
        while (true) {
            if (sum <= ds1.getOrder()) {
                Assert.assertEquals(ds1.getPartialDerivative(derivatives), ds2.getPartialDerivative(derivatives), epsilon);
            }
            boolean increment = true;
            sum = 0;
            for (int i = derivatives.length - 1; i >= 0; --i) {
                if (increment) {
                    if (derivatives[i] == ds1.getOrder()) {
                        derivatives[i] = 0;
                    } else {
                        derivatives[i]++;
                        increment = false;
                    }
                }
                sum += derivatives[i];
            }
            if (increment) {
                return;
            }
        }
    }

    @Test
    public void testHypotSpecial_1() {
        Assert.assertTrue(Double.isNaN(DerivativeStructure.hypot(new DerivativeStructure(2, 5, 0, Double.NaN), new DerivativeStructure(2, 5, 0, +3.0e250)).getValue()));
    }

    @Test
    public void testHypotSpecial_2() {
        Assert.assertTrue(Double.isNaN(DerivativeStructure.hypot(new DerivativeStructure(2, 5, 0, +3.0e250), new DerivativeStructure(2, 5, 0, Double.NaN)).getValue()));
    }

    @Test
    public void testHypotSpecial_3() {
        Assert.assertTrue(Double.isInfinite(DerivativeStructure.hypot(new DerivativeStructure(2, 5, 0, Double.POSITIVE_INFINITY), new DerivativeStructure(2, 5, 0, +3.0e250)).getValue()));
    }

    @Test
    public void testHypotSpecial_4() {
        Assert.assertTrue(Double.isInfinite(DerivativeStructure.hypot(new DerivativeStructure(2, 5, 0, +3.0e250), new DerivativeStructure(2, 5, 0, Double.POSITIVE_INFINITY)).getValue()));
    }

    @Override
    @Test
    public void testAbs_1_testMerged_1() {
        DerivativeStructure minusOne = new DerivativeStructure(1, 1, 0, -1.0);
        Assert.assertEquals(+1.0, minusOne.abs().getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(-1.0, minusOne.abs().getPartialDerivative(1), 1.0e-15);
    }

    @Override
    @Test
    public void testAbs_3_testMerged_2() {
        DerivativeStructure plusOne = new DerivativeStructure(1, 1, 0, +1.0);
        Assert.assertEquals(+1.0, plusOne.abs().getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(+1.0, plusOne.abs().getPartialDerivative(1), 1.0e-15);
    }

    @Override
    @Test
    public void testAbs_5_testMerged_3() {
        DerivativeStructure minusZero = new DerivativeStructure(1, 1, 0, -0.0);
        Assert.assertEquals(+0.0, minusZero.abs().getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(-1.0, minusZero.abs().getPartialDerivative(1), 1.0e-15);
    }

    @Override
    @Test
    public void testAbs_7_testMerged_4() {
        DerivativeStructure plusZero = new DerivativeStructure(1, 1, 0, +0.0);
        Assert.assertEquals(+0.0, plusZero.abs().getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(+1.0, plusZero.abs().getPartialDerivative(1), 1.0e-15);
    }

    @Override
    @Test
    public void testSignum_1_testMerged_1() {
        DerivativeStructure minusOne = new DerivativeStructure(1, 1, 0, -1.0);
        Assert.assertEquals(-1.0, minusOne.signum().getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(0.0, minusOne.signum().getPartialDerivative(1), 1.0e-15);
    }

    @Override
    @Test
    public void testSignum_3_testMerged_2() {
        DerivativeStructure plusOne = new DerivativeStructure(1, 1, 0, +1.0);
        Assert.assertEquals(+1.0, plusOne.signum().getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(0.0, plusOne.signum().getPartialDerivative(1), 1.0e-15);
    }

    @Override
    @Test
    public void testSignum_5_testMerged_3() {
        DerivativeStructure minusZero = new DerivativeStructure(1, 1, 0, -0.0);
        Assert.assertEquals(-0.0, minusZero.signum().getPartialDerivative(0), 1.0e-15);
        Assert.assertTrue(Double.doubleToLongBits(minusZero.signum().getValue()) < 0);
        Assert.assertEquals(0.0, minusZero.signum().getPartialDerivative(1), 1.0e-15);
    }

    @Override
    @Test
    public void testSignum_8_testMerged_4() {
        DerivativeStructure plusZero = new DerivativeStructure(1, 1, 0, +0.0);
        Assert.assertEquals(+0.0, plusZero.signum().getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(0, Double.doubleToLongBits(plusZero.signum().getValue()));
        Assert.assertEquals(0.0, plusZero.signum().getPartialDerivative(1), 1.0e-15);
    }

    @Test
    public void testCopySign_1_testMerged_1() {
        DerivativeStructure minusOne = new DerivativeStructure(1, 1, 0, -1.0);
        Assert.assertEquals(+1.0, minusOne.copySign(+1.0).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(-1.0, minusOne.copySign(+1.0).getPartialDerivative(1), 1.0e-15);
        Assert.assertEquals(-1.0, minusOne.copySign(-1.0).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(+1.0, minusOne.copySign(-1.0).getPartialDerivative(1), 1.0e-15);
        Assert.assertEquals(+1.0, minusOne.copySign(+0.0).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(-1.0, minusOne.copySign(+0.0).getPartialDerivative(1), 1.0e-15);
        Assert.assertEquals(-1.0, minusOne.copySign(-0.0).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(+1.0, minusOne.copySign(-0.0).getPartialDerivative(1), 1.0e-15);
        Assert.assertEquals(+1.0, minusOne.copySign(Double.NaN).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(-1.0, minusOne.copySign(Double.NaN).getPartialDerivative(1), 1.0e-15);
    }

    @Test
    public void testCopySign_11_testMerged_2() {
        DerivativeStructure plusOne = new DerivativeStructure(1, 1, 0, +1.0);
        Assert.assertEquals(+1.0, plusOne.copySign(+1.0).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(+1.0, plusOne.copySign(+1.0).getPartialDerivative(1), 1.0e-15);
        Assert.assertEquals(-1.0, plusOne.copySign(-1.0).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(-1.0, plusOne.copySign(-1.0).getPartialDerivative(1), 1.0e-15);
        Assert.assertEquals(+1.0, plusOne.copySign(+0.0).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(+1.0, plusOne.copySign(+0.0).getPartialDerivative(1), 1.0e-15);
        Assert.assertEquals(-1.0, plusOne.copySign(-0.0).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(-1.0, plusOne.copySign(-0.0).getPartialDerivative(1), 1.0e-15);
        Assert.assertEquals(+1.0, plusOne.copySign(Double.NaN).getPartialDerivative(0), 1.0e-15);
        Assert.assertEquals(+1.0, plusOne.copySign(Double.NaN).getPartialDerivative(1), 1.0e-15);
    }
}
