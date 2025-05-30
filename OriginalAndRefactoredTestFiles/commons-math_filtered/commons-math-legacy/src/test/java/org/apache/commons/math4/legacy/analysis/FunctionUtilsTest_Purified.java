package org.apache.commons.math4.legacy.analysis;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.analysis.differentiation.MultivariateDifferentiableFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.legacy.analysis.function.Add;
import org.apache.commons.math4.legacy.analysis.function.Constant;
import org.apache.commons.math4.legacy.analysis.function.Cos;
import org.apache.commons.math4.legacy.analysis.function.Cosh;
import org.apache.commons.math4.legacy.analysis.function.Divide;
import org.apache.commons.math4.legacy.analysis.function.Identity;
import org.apache.commons.math4.legacy.analysis.function.Inverse;
import org.apache.commons.math4.legacy.analysis.function.Log;
import org.apache.commons.math4.legacy.analysis.function.Max;
import org.apache.commons.math4.legacy.analysis.function.Min;
import org.apache.commons.math4.legacy.analysis.function.Minus;
import org.apache.commons.math4.legacy.analysis.function.Multiply;
import org.apache.commons.math4.legacy.analysis.function.Pow;
import org.apache.commons.math4.legacy.analysis.function.Power;
import org.apache.commons.math4.legacy.analysis.function.Sin;
import org.apache.commons.math4.legacy.analysis.function.Sinc;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NumberIsTooLargeException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class FunctionUtilsTest_Purified {

    private final double EPS = JdkMath.ulp(1d);

    @Test
    public void testCompose_1_testMerged_1() {
        UnivariateFunction id = new Identity();
        Assert.assertEquals(3, FunctionUtils.compose(id, id, id).value(3), EPS);
        UnivariateFunction c = new Constant(4);
        Assert.assertEquals(4, FunctionUtils.compose(id, c).value(3), EPS);
        Assert.assertEquals(4, FunctionUtils.compose(c, id).value(3), EPS);
        UnivariateFunction m = new Minus();
        Assert.assertEquals(-3, FunctionUtils.compose(m).value(3), EPS);
        Assert.assertEquals(3, FunctionUtils.compose(m, m).value(3), EPS);
        UnivariateFunction inv = new Inverse();
        Assert.assertEquals(-0.25, FunctionUtils.compose(inv, m, c, id).value(3), EPS);
    }

    @Test
    public void testCompose_7() {
        UnivariateFunction pow = new Power(2);
        Assert.assertEquals(81, FunctionUtils.compose(pow, pow).value(3), EPS);
    }

    @Test
    public void testComposeDifferentiable_1_testMerged_1() {
        UnivariateDifferentiableFunction id = new Identity();
        Assert.assertEquals(1, FunctionUtils.compose(id, id, id).value(new DerivativeStructure(1, 1, 0, 3)).getPartialDerivative(1), EPS);
        UnivariateDifferentiableFunction c = new Constant(4);
        Assert.assertEquals(0, FunctionUtils.compose(id, c).value(new DerivativeStructure(1, 1, 0, 3)).getPartialDerivative(1), EPS);
        Assert.assertEquals(0, FunctionUtils.compose(c, id).value(new DerivativeStructure(1, 1, 0, 3)).getPartialDerivative(1), EPS);
        UnivariateDifferentiableFunction m = new Minus();
        Assert.assertEquals(-1, FunctionUtils.compose(m).value(new DerivativeStructure(1, 1, 0, 3)).getPartialDerivative(1), EPS);
        Assert.assertEquals(1, FunctionUtils.compose(m, m).value(new DerivativeStructure(1, 1, 0, 3)).getPartialDerivative(1), EPS);
        UnivariateDifferentiableFunction inv = new Inverse();
        Assert.assertEquals(0.25, FunctionUtils.compose(inv, m, id).value(new DerivativeStructure(1, 1, 0, 2)).getPartialDerivative(1), EPS);
    }

    @Test
    public void testComposeDifferentiable_7_testMerged_2() {
        UnivariateDifferentiableFunction pow = new Power(2);
        Assert.assertEquals(108, FunctionUtils.compose(pow, pow).value(new DerivativeStructure(1, 1, 0, 3)).getPartialDerivative(1), EPS);
        UnivariateDifferentiableFunction log = new Log();
        double a = 9876.54321;
        Assert.assertEquals(pow.value(new DerivativeStructure(1, 1, 0, a)).getPartialDerivative(1) / pow.value(a), FunctionUtils.compose(log, pow).value(new DerivativeStructure(1, 1, 0, a)).getPartialDerivative(1), EPS);
    }

    @Test
    public void testMultiply_1() {
        UnivariateFunction c = new Constant(4);
        Assert.assertEquals(16, FunctionUtils.multiply(c, c).value(12345), EPS);
    }

    @Test
    public void testMultiply_2() {
        UnivariateFunction inv = new Inverse();
        UnivariateFunction pow = new Power(2);
        Assert.assertEquals(1, FunctionUtils.multiply(FunctionUtils.compose(inv, pow), pow).value(3.5), EPS);
    }
}
