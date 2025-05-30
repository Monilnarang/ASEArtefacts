package org.apache.commons.math4.legacy.field.linalg;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.numbers.fraction.Fraction;
import org.apache.commons.numbers.field.FractionField;
import org.apache.commons.math4.legacy.linear.SingularMatrixException;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;

public class FieldLUDecompositionTest_Purified {

    private final int[][] testData = { { 1, 2, 3 }, { 2, 5, 3 }, { 1, 0, 8 } };

    private final int[][] testDataMinus = { { -1, -2, -3 }, { -2, -5, -3 }, { -1, 0, -8 } };

    private final int[][] luData = { { 2, 3, 3 }, { 2, 3, 7 }, { 6, 6, 8 } };

    private final int[][] luData2 = { { 2, 3, 3 }, { 0, 5, 7 }, { 6, 9, 8 } };

    private int[][] singular = { { 2, 3 }, { 2, 3 } };

    private final int[][] bigSingular = { { 1, 2, 3, 4 }, { 2, 5, 3, 4 }, { 7, 3, 256, 1930 }, { 3, 7, 6, 8 } };

    private static double determinant(int[][] data) {
        return FieldLUDecomposition.of(create(data)).getDeterminant().doubleValue();
    }

    private static FieldDenseMatrix<Fraction> create(final int[][] data) {
        final int numRows = data.length;
        final int numCols = data[0].length;
        final FieldDenseMatrix<Fraction> m = FieldDenseMatrix.create(FractionField.get(), numRows, numCols);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                m.set(i, j, Fraction.of(data[i][j], 1));
            }
        }
        return m;
    }

    @Test
    public void testDeterminant_1() {
        Assert.assertEquals(-1, determinant(testData), 1e-15);
    }

    @Test
    public void testDeterminant_2() {
        Assert.assertEquals(24, determinant(luData), 1e-14);
    }

    @Test
    public void testDeterminant_3() {
        Assert.assertEquals(-10, determinant(luData2), 1e-14);
    }

    @Test
    public void testDeterminant_4() {
        Assert.assertEquals(0, determinant(singular), 1e-15);
    }

    @Test
    public void testDeterminant_5() {
        Assert.assertEquals(0, determinant(bigSingular), 1e-15);
    }
}
