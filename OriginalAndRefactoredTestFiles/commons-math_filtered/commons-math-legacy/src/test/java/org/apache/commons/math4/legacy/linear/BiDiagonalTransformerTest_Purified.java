package org.apache.commons.math4.legacy.linear;

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

public class BiDiagonalTransformerTest_Purified {

    private double[][] testSquare = { { 24.0 / 25.0, 43.0 / 25.0 }, { 57.0 / 25.0, 24.0 / 25.0 } };

    private double[][] testNonSquare = { { -540.0 / 625.0, 963.0 / 625.0, -216.0 / 625.0 }, { -1730.0 / 625.0, -744.0 / 625.0, 1008.0 / 625.0 }, { -720.0 / 625.0, 1284.0 / 625.0, -288.0 / 625.0 }, { -360.0 / 625.0, 192.0 / 625.0, 1756.0 / 625.0 } };

    private void checkDimensions(RealMatrix matrix) {
        final int m = matrix.getRowDimension();
        final int n = matrix.getColumnDimension();
        BiDiagonalTransformer transformer = new BiDiagonalTransformer(matrix);
        Assert.assertEquals(m, transformer.getU().getRowDimension());
        Assert.assertEquals(m, transformer.getU().getColumnDimension());
        Assert.assertEquals(m, transformer.getB().getRowDimension());
        Assert.assertEquals(n, transformer.getB().getColumnDimension());
        Assert.assertEquals(n, transformer.getV().getRowDimension());
        Assert.assertEquals(n, transformer.getV().getColumnDimension());
    }

    private void checkAEqualUSVt(RealMatrix matrix) {
        BiDiagonalTransformer transformer = new BiDiagonalTransformer(matrix);
        RealMatrix u = transformer.getU();
        RealMatrix b = transformer.getB();
        RealMatrix v = transformer.getV();
        double norm = u.multiply(b).multiply(v.transpose()).subtract(matrix).getNorm();
        Assert.assertEquals(0, norm, 1.0e-14);
    }

    private void checkOrthogonal(RealMatrix m) {
        RealMatrix mTm = m.transpose().multiply(m);
        RealMatrix id = MatrixUtils.createRealIdentityMatrix(mTm.getRowDimension());
        Assert.assertEquals(0, mTm.subtract(id).getNorm(), 1.0e-14);
    }

    private void checkBiDiagonal(RealMatrix m) {
        final int rows = m.getRowDimension();
        final int cols = m.getColumnDimension();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (rows < cols) {
                    if (i < j || i > j + 1) {
                        Assert.assertEquals(0, m.getEntry(i, j), 1.0e-16);
                    }
                } else {
                    if (i < j - 1 || i > j) {
                        Assert.assertEquals(0, m.getEntry(i, j), 1.0e-16);
                    }
                }
            }
        }
    }

    @Test
    public void testUpperOrLower_1() {
        Assert.assertTrue(new BiDiagonalTransformer(MatrixUtils.createRealMatrix(testSquare)).isUpperBiDiagonal());
    }

    @Test
    public void testUpperOrLower_2() {
        Assert.assertTrue(new BiDiagonalTransformer(MatrixUtils.createRealMatrix(testNonSquare)).isUpperBiDiagonal());
    }

    @Test
    public void testUpperOrLower_3() {
        Assert.assertFalse(new BiDiagonalTransformer(MatrixUtils.createRealMatrix(testNonSquare).transpose()).isUpperBiDiagonal());
    }
}
