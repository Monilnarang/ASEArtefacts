package org.apache.commons.math4.legacy.distribution;

import org.junit.Assert;
import org.junit.Test;

public class AbstractIntegerDistributionTest_Purified {

    protected final DiceDistribution diceDistribution = new DiceDistribution();

    protected final double p = diceDistribution.probability(1);

    class DiceDistribution extends AbstractIntegerDistribution {

        public static final long serialVersionUID = 23734213;

        private final double p = 1d / 6d;

        @Override
        public double probability(int x) {
            if (x < 1 || x > 6) {
                return 0;
            } else {
                return p;
            }
        }

        @Override
        public double cumulativeProbability(int x) {
            if (x < 1) {
                return 0;
            } else if (x >= 6) {
                return 1;
            } else {
                return p * x;
            }
        }

        @Override
        public double getMean() {
            return 3.5;
        }

        @Override
        public double getVariance() {
            return 70 / 24;
        }

        @Override
        public int getSupportLowerBound() {
            return 1;
        }

        @Override
        public int getSupportUpperBound() {
            return 6;
        }
    }

    @Test
    public void testInverseCumulativeProbabilityMethod_1() {
        Assert.assertEquals(1, diceDistribution.inverseCumulativeProbability(0));
    }

    @Test
    public void testInverseCumulativeProbabilityMethod_2() {
        Assert.assertEquals(1, diceDistribution.inverseCumulativeProbability((1d - Double.MIN_VALUE) / 6d));
    }

    @Test
    public void testInverseCumulativeProbabilityMethod_3_testMerged_3() {
        double precision = 0.000000000000001;
        Assert.assertEquals(2, diceDistribution.inverseCumulativeProbability((1d + precision) / 6d));
        Assert.assertEquals(3, diceDistribution.inverseCumulativeProbability((2d + precision) / 6d));
        Assert.assertEquals(4, diceDistribution.inverseCumulativeProbability((3d + precision) / 6d));
        Assert.assertEquals(5, diceDistribution.inverseCumulativeProbability((4d + precision) / 6d));
        Assert.assertEquals(5, diceDistribution.inverseCumulativeProbability((5d - precision) / 6d));
        Assert.assertEquals(6, diceDistribution.inverseCumulativeProbability((5d + precision) / 6d));
        Assert.assertEquals(6, diceDistribution.inverseCumulativeProbability((6d - precision) / 6d));
    }

    @Test
    public void testInverseCumulativeProbabilityMethod_4() {
        Assert.assertEquals(2, diceDistribution.inverseCumulativeProbability((2d - Double.MIN_VALUE) / 6d));
    }

    @Test
    public void testInverseCumulativeProbabilityMethod_6() {
        Assert.assertEquals(3, diceDistribution.inverseCumulativeProbability((3d - Double.MIN_VALUE) / 6d));
    }

    @Test
    public void testInverseCumulativeProbabilityMethod_8() {
        Assert.assertEquals(4, diceDistribution.inverseCumulativeProbability((4d - Double.MIN_VALUE) / 6d));
    }

    @Test
    public void testInverseCumulativeProbabilityMethod_13() {
        Assert.assertEquals(6, diceDistribution.inverseCumulativeProbability(6d / 6d));
    }
}
