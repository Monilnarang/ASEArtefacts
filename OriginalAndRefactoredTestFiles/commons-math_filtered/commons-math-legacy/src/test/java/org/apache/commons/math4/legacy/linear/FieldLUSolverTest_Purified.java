package org.apache.commons.math4.legacy.linear;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.core.dfp.Dfp;
import org.junit.Assert;
import org.junit.Test;

public class FieldLUSolverTest_Purified {

    private int[][] testData = { { 1, 2, 3 }, { 2, 5, 3 }, { 1, 0, 8 } };

    private int[][] luData = { { 2, 3, 3 }, { 0, 5, 7 }, { 6, 9, 8 } };

    private int[][] singular = { { 2, 3 }, { 2, 3 } };

    private int[][] bigSingular = { { 1, 2, 3, 4 }, { 2, 5, 3, 4 }, { 7, 3, 256, 1930 }, { 3, 7, 6, 8 } };

    public static FieldMatrix<Dfp> createDfpMatrix(final int[][] data) {
        final int numRows = data.length;
        final int numCols = data[0].length;
        final Array2DRowFieldMatrix<Dfp> m;
        m = new Array2DRowFieldMatrix<>(Dfp25.getField(), numRows, numCols);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                m.setEntry(i, j, Dfp25.of(data[i][j], 1));
            }
        }
        return m;
    }

    private double getDeterminant(final FieldMatrix<Dfp> m) {
        return new FieldLUDecomposition<>(m).getDeterminant().toDouble();
    }

    @Test
    public void testDeterminant_1() {
        Assert.assertEquals(-1, getDeterminant(createDfpMatrix(testData)), 1E-15);
    }

    @Test
    public void testDeterminant_2() {
        Assert.assertEquals(-10, getDeterminant(createDfpMatrix(luData)), 1E-14);
    }

    @Test
    public void testDeterminant_3() {
        Assert.assertEquals(0, getDeterminant(createDfpMatrix(singular)), 1E-15);
    }

    @Test
    public void testDeterminant_4() {
        Assert.assertEquals(0, getDeterminant(createDfpMatrix(bigSingular)), 1E-15);
    }
}
