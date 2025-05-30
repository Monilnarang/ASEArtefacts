package org.apache.commons.math4.legacy.distribution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.statistics.distribution.ContinuousDistribution;
import org.apache.commons.statistics.distribution.UniformContinuousDistribution;
import org.apache.commons.statistics.distribution.NormalDistribution;
import org.apache.commons.statistics.distribution.ExponentialDistribution;
import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.legacy.analysis.integration.IterativeLegendreGaussIntegrator;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.legacy.stat.descriptive.SummaryStatistics;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class EmpiricalDistributionTest_Purified extends RealDistributionAbstractTest {

    private EmpiricalDistribution empiricalDistribution = null;

    private double[] dataArray = null;

    private final int n = 10000;

    private final double binMass = 10d / (n + 1);

    private final double firstBinMass = 11d / (n + 1);

    @Override
    @Before
    public void setUp() {
        super.setUp();
        final URL url = getClass().getResource("testData.txt");
        final ArrayList<Double> list = new ArrayList<>();
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = null;
            while ((str = in.readLine()) != null) {
                list.add(Double.valueOf(str));
            }
            in.close();
        } catch (IOException ex) {
            Assert.fail("IOException " + ex);
        }
        dataArray = new double[list.size()];
        int i = 0;
        for (Double data : list) {
            dataArray[i] = data.doubleValue();
            i++;
        }
        empiricalDistribution = EmpiricalDistribution.from(100, dataArray);
    }

    private void verifySame(EmpiricalDistribution d1, EmpiricalDistribution d2) {
        Assert.assertEquals(d1.getBinCount(), d2.getBinCount());
        Assert.assertEquals(d1.getSampleStats(), d2.getSampleStats());
        for (int i = 0; i < d1.getUpperBounds().length; i++) {
            Assert.assertEquals(d1.getUpperBounds()[i], d2.getUpperBounds()[i], 0);
        }
        Assert.assertEquals(d1.getBinStats(), d2.getBinStats());
    }

    private void tstGen(EmpiricalDistribution dist, double tolerance) {
        final ContinuousDistribution.Sampler sampler = dist.createSampler(RandomSource.WELL_19937_C.create(1000));
        final SummaryStatistics stats = new SummaryStatistics();
        for (int i = 1; i < 1000; i++) {
            stats.addValue(sampler.sample());
        }
        Assert.assertEquals("mean", 5.069831575018909, stats.getMean(), tolerance);
        Assert.assertEquals("std dev", 1.0173699343977738, stats.getStandardDeviation(), tolerance);
    }

    @Override
    public ContinuousDistribution makeDistribution() {
        final double[] sourceData = new double[n + 1];
        for (int i = 0; i < n + 1; i++) {
            sourceData[i] = i;
        }
        EmpiricalDistribution dist = EmpiricalDistribution.from(1000, sourceData);
        return dist;
    }

    @Override
    public double[] makeCumulativeTestPoints() {
        final double[] testPoints = new double[] { 9, 10, 15, 1000, 5004, 9999 };
        return testPoints;
    }

    @Override
    public double[] makeCumulativeTestValues() {
        final double[] testPoints = getCumulativeTestPoints();
        final double[] cumValues = new double[testPoints.length];
        final EmpiricalDistribution empiricalDistribution = (EmpiricalDistribution) makeDistribution();
        final double[] binBounds = empiricalDistribution.getUpperBounds();
        for (int i = 0; i < testPoints.length; i++) {
            final int bin = findBin(testPoints[i]);
            final double lower = bin == 0 ? empiricalDistribution.getSupportLowerBound() : binBounds[bin - 1];
            final double upper = binBounds[bin];
            final double bMinus = bin == 0 ? 0 : (bin - 1) * binMass + firstBinMass;
            final ContinuousDistribution kernel = findKernel(lower, upper);
            final double withinBinKernelMass = kernel.probability(lower, upper);
            final double kernelCum = kernel.probability(lower, testPoints[i]);
            cumValues[i] = bMinus + (bin == 0 ? firstBinMass : binMass) * kernelCum / withinBinKernelMass;
        }
        return cumValues;
    }

    @Override
    public double[] makeDensityTestValues() {
        final double[] testPoints = getCumulativeTestPoints();
        final double[] densityValues = new double[testPoints.length];
        final EmpiricalDistribution empiricalDistribution = (EmpiricalDistribution) makeDistribution();
        final double[] binBounds = empiricalDistribution.getUpperBounds();
        for (int i = 0; i < testPoints.length; i++) {
            final int bin = findBin(testPoints[i]);
            final double lower = bin == 0 ? empiricalDistribution.getSupportLowerBound() : binBounds[bin - 1];
            final double upper = binBounds[bin];
            final ContinuousDistribution kernel = findKernel(lower, upper);
            final double withinBinKernelMass = kernel.probability(lower, upper);
            final double density = kernel.density(testPoints[i]);
            densityValues[i] = density * (bin == 0 ? firstBinMass : binMass) / withinBinKernelMass;
        }
        return densityValues;
    }

    private int findBin(double x) {
        final double nMinus = JdkMath.floor(x / 10);
        final int bin = (int) JdkMath.round(nMinus);
        return JdkMath.floor(x / 10) == x / 10 ? bin - 1 : bin;
    }

    private ContinuousDistribution findKernel(double lower, double upper) {
        if (lower < 1) {
            return NormalDistribution.of(5d, 3.3166247903554);
        } else {
            return NormalDistribution.of((upper + lower + 1) / 2d, 3.0276503540974917);
        }
    }

    @Test
    public void testDoubleLoad_1() {
        Assert.assertEquals(empiricalDistribution.getSampleStats().getN(), 1000, 1e-7);
    }

    @Test
    public void testDoubleLoad_2() {
        Assert.assertEquals(empiricalDistribution.getSampleStats().getMean(), 5.069831575018909, 1e-7);
    }

    @Test
    public void testDoubleLoad_3() {
        Assert.assertEquals(empiricalDistribution.getSampleStats().getStandardDeviation(), 1.0173699343977738, 1e-7);
    }

    @Test
    public void testDoubleLoad_4_testMerged_4() {
        double[] bounds = empiricalDistribution.getGeneratorUpperBounds();
        Assert.assertEquals(bounds.length, 100);
        Assert.assertEquals(bounds[99], 1.0, 10e-12);
    }
}
