package org.apache.commons.math4.legacy.analysis.differentiation;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.math4.legacy.field.ExtendedFieldElementAbstractTest;
import org.apache.commons.math4.legacy.analysis.polynomials.PolynomialFunction;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class SparseGradientTest_Purified extends ExtendedFieldElementAbstractTest<SparseGradient> {

    @Override
    protected SparseGradient build(final double x) {
        return SparseGradient.createVariable(0, x);
    }

    private void checkF0F1(SparseGradient sg, double value, double... derivatives) {
        Assert.assertEquals(value, sg.getValue(), 1.0e-13);
        for (int i = 0; i < derivatives.length; ++i) {
            Assert.assertEquals(derivatives[i], sg.getDerivative(i), 1.0e-13);
        }
    }

    @Test
    public void testHypotSpecial_1() {
        Assert.assertTrue(Double.isNaN(SparseGradient.hypot(SparseGradient.createVariable(0, Double.NaN), SparseGradient.createVariable(0, +3.0e250)).getValue()));
    }

    @Test
    public void testHypotSpecial_2() {
        Assert.assertTrue(Double.isNaN(SparseGradient.hypot(SparseGradient.createVariable(0, +3.0e250), SparseGradient.createVariable(0, Double.NaN)).getValue()));
    }

    @Test
    public void testHypotSpecial_3() {
        Assert.assertTrue(Double.isInfinite(SparseGradient.hypot(SparseGradient.createVariable(0, Double.POSITIVE_INFINITY), SparseGradient.createVariable(0, +3.0e250)).getValue()));
    }

    @Test
    public void testHypotSpecial_4() {
        Assert.assertTrue(Double.isInfinite(SparseGradient.hypot(SparseGradient.createVariable(0, +3.0e250), SparseGradient.createVariable(0, Double.POSITIVE_INFINITY)).getValue()));
    }
}
