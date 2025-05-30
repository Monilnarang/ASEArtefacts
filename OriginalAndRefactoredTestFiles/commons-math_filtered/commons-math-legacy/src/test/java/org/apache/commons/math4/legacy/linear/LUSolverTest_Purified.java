package org.apache.commons.math4.legacy.linear;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Test;
import org.junit.Assert;

public class LUSolverTest_Purified {

    private double[][] testData = { { 1.0, 2.0, 3.0 }, { 2.0, 5.0, 3.0 }, { 1.0, 0.0, 8.0 } };

    private double[][] luData = { { 2.0, 3.0, 3.0 }, { 0.0, 5.0, 7.0 }, { 6.0, 9.0, 8.0 } };

    private double[][] singular = { { 2.0, 3.0 }, { 2.0, 3.0 } };

    private double[][] bigSingular = { { 1.0, 2.0, 3.0, 4.0 }, { 2.0, 5.0, 3.0, 4.0 }, { 7.0, 3.0, 256.0, 1930.0 }, { 3.0, 7.0, 6.0, 8.0 } };

    private double getDeterminant(RealMatrix m) {
        return new LUDecomposition(m).getDeterminant();
    }

    @Test
    public void testDeterminant_1() {
        Assert.assertEquals(-1, getDeterminant(MatrixUtils.createRealMatrix(testData)), 1.0e-15);
    }

    @Test
    public void testDeterminant_2() {
        Assert.assertEquals(-10, getDeterminant(MatrixUtils.createRealMatrix(luData)), 1.0e-14);
    }

    @Test
    public void testDeterminant_3() {
        Assert.assertEquals(0, getDeterminant(MatrixUtils.createRealMatrix(singular)), 1.0e-17);
    }

    @Test
    public void testDeterminant_4() {
        Assert.assertEquals(0, getDeterminant(MatrixUtils.createRealMatrix(bigSingular)), 1.0e-10);
    }
}
