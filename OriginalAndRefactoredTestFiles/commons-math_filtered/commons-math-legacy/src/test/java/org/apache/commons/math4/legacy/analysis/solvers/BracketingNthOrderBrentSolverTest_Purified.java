package org.apache.commons.math4.legacy.analysis.solvers;

import org.apache.commons.math4.legacy.analysis.QuinticFunction;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.apache.commons.math4.legacy.exception.TooManyEvaluationsException;
import org.junit.Assert;
import org.junit.Test;

public final class BracketingNthOrderBrentSolverTest_Purified extends BaseSecantSolverAbstractTest {

    @Override
    protected UnivariateSolver getSolver() {
        return new BracketingNthOrderBrentSolver();
    }

    @Override
    protected int[] getQuinticEvalCounts() {
        return new int[] { 1, 3, 8, 1, 9, 4, 8, 1, 12, 1, 16 };
    }

    private void compare(TestFunction f) {
        compare(f, f.getRoot(), f.getMin(), f.getMax());
    }

    private void compare(final UnivariateDifferentiableFunction f, double root, double min, double max) {
        NewtonRaphsonSolver newton = new NewtonRaphsonSolver(1.0e-12);
        BracketingNthOrderBrentSolver bracketing = new BracketingNthOrderBrentSolver(1.0e-12, 1.0e-12, 1.0e-18, 5);
        double resultN;
        try {
            resultN = newton.solve(100, f, min, max);
        } catch (TooManyEvaluationsException tmee) {
            resultN = Double.NaN;
        }
        double resultB;
        try {
            resultB = bracketing.solve(100, f, min, max);
        } catch (TooManyEvaluationsException tmee) {
            resultB = Double.NaN;
        }
        Assert.assertEquals(root, resultN, newton.getAbsoluteAccuracy());
        Assert.assertEquals(root, resultB, bracketing.getAbsoluteAccuracy());
        final int weightedBracketingEvaluations = bracketing.getEvaluations();
        final int weightedNewtonEvaluations = 2 * newton.getEvaluations();
        Assert.assertTrue(weightedBracketingEvaluations < weightedNewtonEvaluations);
    }

    private abstract static class TestFunction implements UnivariateDifferentiableFunction {

        private final double root;

        private final double min;

        private final double max;

        protected TestFunction(final double root, final double min, final double max) {
            this.root = root;
            this.min = min;
            this.max = max;
        }

        public double getRoot() {
            return root;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }

        @Override
        public double value(final double x) {
            return value(new DerivativeStructure(0, 0, x)).getValue();
        }

        @Override
        public abstract DerivativeStructure value(DerivativeStructure t);
    }

    @Test
    public void testConstructorsOK_1() {
        Assert.assertEquals(2, new BracketingNthOrderBrentSolver(1.0e-10, 2).getMaximalOrder());
    }

    @Test
    public void testConstructorsOK_2() {
        Assert.assertEquals(2, new BracketingNthOrderBrentSolver(1.0e-10, 1.0e-10, 2).getMaximalOrder());
    }

    @Test
    public void testConstructorsOK_3() {
        Assert.assertEquals(2, new BracketingNthOrderBrentSolver(1.0e-10, 1.0e-10, 1.0e-10, 2).getMaximalOrder());
    }
}
