package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.MathIllegalStateException;
import org.apache.commons.math4.legacy.exception.NoDataException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.apache.commons.math4.core.jdkmath.JdkMath;

public final class Array2DRowRealMatrixTest_Purified {

    protected double[][] id = { { 1d, 0d, 0d }, { 0d, 1d, 0d }, { 0d, 0d, 1d } };

    protected double[][] testData = { { 1d, 2d, 3d }, { 2d, 5d, 3d }, { 1d, 0d, 8d } };

    protected double[][] testDataLU = { { 2d, 5d, 3d }, { .5d, -2.5d, 6.5d }, { 0.5d, 0.2d, .2d } };

    protected double[][] testDataPlus2 = { { 3d, 4d, 5d }, { 4d, 7d, 5d }, { 3d, 2d, 10d } };

    protected double[][] testDataMinus = { { -1d, -2d, -3d }, { -2d, -5d, -3d }, { -1d, 0d, -8d } };

    protected double[] testDataRow1 = { 1d, 2d, 3d };

    protected double[] testDataCol3 = { 3d, 3d, 8d };

    protected double[][] testDataInv = { { -40d, 16d, 9d }, { 13d, -5d, -3d }, { 5d, -2d, -1d } };

    protected double[] preMultTest = { 8, 12, 33 };

    protected double[][] testData2 = { { 1d, 2d, 3d }, { 2d, 5d, 3d } };

    protected double[][] testData2T = { { 1d, 2d }, { 2d, 5d }, { 3d, 3d } };

    protected double[][] testDataPlusInv = { { -39d, 18d, 12d }, { 15d, 0d, 0d }, { 6d, -2d, 7d } };

    protected double[][] luData = { { 2d, 3d, 3d }, { 0d, 5d, 7d }, { 6d, 9d, 8d } };

    protected double[][] luDataLUDecomposition = { { 6d, 9d, 8d }, { 0d, 5d, 7d }, { 0.33333333333333, 0d, 0.33333333333333 } };

    protected double[][] singular = { { 2d, 3d }, { 2d, 3d } };

    protected double[][] bigSingular = { { 1d, 2d, 3d, 4d }, { 2d, 5d, 3d, 4d }, { 7d, 3d, 256d, 1930d }, { 3d, 7d, 6d, 8d } };

    protected double[][] detData = { { 1d, 2d, 3d }, { 4d, 5d, 6d }, { 7d, 8d, 10d } };

    protected double[][] detData2 = { { 1d, 3d }, { 2d, 4d } };

    protected double[] testVector = { 1, 2, 3 };

    protected double[] testVector2 = { 1, 2, 3, 4 };

    protected double[][] subTestData = { { 1, 2, 3, 4 }, { 1.5, 2.5, 3.5, 4.5 }, { 2, 4, 6, 8 }, { 4, 5, 6, 7 } };

    protected double[][] subRows02Cols13 = { { 2, 4 }, { 4, 8 } };

    protected double[][] subRows03Cols12 = { { 2, 3 }, { 5, 6 } };

    protected double[][] subRows03Cols123 = { { 2, 3, 4 }, { 5, 6, 7 } };

    protected double[][] subRows20Cols123 = { { 4, 6, 8 }, { 2, 3, 4 } };

    protected double[][] subRows31Cols31 = { { 7, 5 }, { 4.5, 2.5 } };

    protected double[][] subRows01Cols23 = { { 3, 4 }, { 3.5, 4.5 } };

    protected double[][] subRows23Cols00 = { { 2 }, { 4 } };

    protected double[][] subRows00Cols33 = { { 4 } };

    protected double[][] subRow0 = { { 1, 2, 3, 4 } };

    protected double[][] subRow3 = { { 4, 5, 6, 7 } };

    protected double[][] subColumn1 = { { 2 }, { 2.5 }, { 4 }, { 5 } };

    protected double[][] subColumn3 = { { 4 }, { 4.5 }, { 8 }, { 7 } };

    protected double entryTolerance = 10E-16;

    protected double normTolerance = 10E-14;

    protected double powerTolerance = 10E-16;

    private final double[][] d3 = new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } };

    private final double[][] d4 = new double[][] { { 1 }, { 2 }, { 3 }, { 4 } };

    private final double[][] d5 = new double[][] { { 30 }, { 70 } };

    private void checkGetSubMatrix(RealMatrix m, double[][] reference, int startRow, int endRow, int startColumn, int endColumn, boolean mustFail) {
        try {
            RealMatrix sub = m.getSubMatrix(startRow, endRow, startColumn, endColumn);
            Assert.assertEquals(new Array2DRowRealMatrix(reference), sub);
            if (mustFail) {
                Assert.fail("Expecting OutOfRangeException or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (NumberIsTooSmallException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (NoDataException e) {
            if (!mustFail) {
                throw e;
            }
        }
    }

    private void checkGetSubMatrix(RealMatrix m, double[][] reference, int[] selectedRows, int[] selectedColumns, boolean mustFail) {
        try {
            RealMatrix sub = m.getSubMatrix(selectedRows, selectedColumns);
            Assert.assertEquals(new Array2DRowRealMatrix(reference), sub);
            if (mustFail) {
                Assert.fail("Expecting OutOfRangeException or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (NumberIsTooSmallException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (NoDataException e) {
            if (!mustFail) {
                throw e;
            }
        }
    }

    private void checkCopy(RealMatrix m, double[][] reference, int startRow, int endRow, int startColumn, int endColumn, boolean mustFail) {
        try {
            double[][] sub = (reference == null) ? new double[1][1] : createIdenticalCopy(reference);
            m.copySubMatrix(startRow, endRow, startColumn, endColumn, sub);
            Assert.assertEquals(new Array2DRowRealMatrix(reference), new Array2DRowRealMatrix(sub));
            if (mustFail) {
                Assert.fail("Expecting OutOfRangeException or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (NumberIsTooSmallException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (NoDataException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (MatrixDimensionMismatchException e) {
            if (!mustFail) {
                throw e;
            }
        }
    }

    private void checkCopy(RealMatrix m, double[][] reference, int[] selectedRows, int[] selectedColumns, boolean mustFail) {
        try {
            double[][] sub = (reference == null) ? new double[1][1] : createIdenticalCopy(reference);
            m.copySubMatrix(selectedRows, selectedColumns, sub);
            Assert.assertEquals(new Array2DRowRealMatrix(reference), new Array2DRowRealMatrix(sub));
            if (mustFail) {
                Assert.fail("Expecting OutOfRangeException or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (NumberIsTooSmallException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (NoDataException e) {
            if (!mustFail) {
                throw e;
            }
        } catch (MatrixDimensionMismatchException e) {
            if (!mustFail) {
                throw e;
            }
        }
    }

    private double[][] createIdenticalCopy(final double[][] matrix) {
        final double[][] matrixCopy = new double[matrix.length][];
        for (int i = 0; i < matrixCopy.length; i++) {
            matrixCopy[i] = new double[matrix[i].length];
        }
        return matrixCopy;
    }

    private RealVector columnToVector(double[][] column) {
        double[] data = new double[column.length];
        for (int i = 0; i < data.length; ++i) {
            data[i] = column[i][0];
        }
        return new ArrayRealVector(data, false);
    }

    private double[] columnToArray(double[][] column) {
        double[] data = new double[column.length];
        for (int i = 0; i < data.length; ++i) {
            data[i] = column[i][0];
        }
        return data;
    }

    private void checkArrays(double[] expected, double[] actual) {
        Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; ++i) {
            Assert.assertEquals(expected[i], actual[i], 0);
        }
    }

    private static final class SetVisitor extends DefaultRealMatrixChangingVisitor {

        @Override
        public double visit(int i, int j, double value) {
            return i + j / 1024.0;
        }
    }

    private static final class GetVisitor extends DefaultRealMatrixPreservingVisitor {

        private int count = 0;

        @Override
        public void visit(int i, int j, double value) {
            ++count;
            Assert.assertEquals(i + j / 1024.0, value, 0.0);
        }

        public int getCount() {
            return count;
        }
    }

    protected void splitLU(RealMatrix lu, double[][] lowerData, double[][] upperData) {
        if (!lu.isSquare()) {
            throw new NonSquareMatrixException(lu.getRowDimension(), lu.getColumnDimension());
        }
        if (lowerData.length != lowerData[0].length) {
            throw new DimensionMismatchException(lowerData.length, lowerData[0].length);
        }
        if (upperData.length != upperData[0].length) {
            throw new DimensionMismatchException(upperData.length, upperData[0].length);
        }
        if (lowerData.length != upperData.length) {
            throw new DimensionMismatchException(lowerData.length, upperData.length);
        }
        if (lowerData.length != lu.getRowDimension()) {
            throw new DimensionMismatchException(lowerData.length, lu.getRowDimension());
        }
        int n = lu.getRowDimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j < i) {
                    lowerData[i][j] = lu.getEntry(i, j);
                    upperData[i][j] = 0d;
                } else if (i == j) {
                    lowerData[i][j] = 1d;
                    upperData[i][j] = lu.getEntry(i, j);
                } else {
                    lowerData[i][j] = 0d;
                    upperData[i][j] = lu.getEntry(i, j);
                }
            }
        }
    }

    protected RealMatrix permuteRows(RealMatrix matrix, int[] permutation) {
        if (!matrix.isSquare()) {
            throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
        }
        if (matrix.getRowDimension() != permutation.length) {
            throw new DimensionMismatchException(matrix.getRowDimension(), permutation.length);
        }
        int n = matrix.getRowDimension();
        int m = matrix.getColumnDimension();
        double[][] out = new double[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                out[i][j] = matrix.getEntry(permutation[i], j);
            }
        }
        return new Array2DRowRealMatrix(out);
    }

    @Test
    public void testDimensions_1_testMerged_1() {
        Array2DRowRealMatrix m = new Array2DRowRealMatrix(testData);
        Assert.assertEquals("testData row dimension", 3, m.getRowDimension());
        Assert.assertEquals("testData column dimension", 3, m.getColumnDimension());
        Assert.assertTrue("testData is square", m.isSquare());
    }

    @Test
    public void testDimensions_4_testMerged_2() {
        Array2DRowRealMatrix m2 = new Array2DRowRealMatrix(testData2);
        Assert.assertEquals("testData2 row dimension", m2.getRowDimension(), 2);
        Assert.assertEquals("testData2 column dimension", m2.getColumnDimension(), 3);
        Assert.assertFalse("testData2 is not square", m2.isSquare());
    }

    @Test
    public void testCopyFunctions_1() {
        Array2DRowRealMatrix m1 = new Array2DRowRealMatrix(testData);
        Array2DRowRealMatrix m2 = new Array2DRowRealMatrix(m1.getData());
        Assert.assertEquals(m2, m1);
    }

    @Test
    public void testCopyFunctions_2() {
        Array2DRowRealMatrix m3 = new Array2DRowRealMatrix(testData);
        Array2DRowRealMatrix m4 = new Array2DRowRealMatrix(m3.getData(), false);
        Assert.assertEquals(m4, m3);
    }

    @Test
    public void testNorm_1() {
        Array2DRowRealMatrix m = new Array2DRowRealMatrix(testData);
        Assert.assertEquals("testData norm", 14d, m.getNorm(), entryTolerance);
    }

    @Test
    public void testNorm_2() {
        Array2DRowRealMatrix m2 = new Array2DRowRealMatrix(testData2);
        Assert.assertEquals("testData2 norm", 7d, m2.getNorm(), entryTolerance);
    }

    @Test
    public void testFrobeniusNorm_1() {
        Array2DRowRealMatrix m = new Array2DRowRealMatrix(testData);
        Assert.assertEquals("testData Frobenius norm", JdkMath.sqrt(117.0), m.getFrobeniusNorm(), entryTolerance);
    }

    @Test
    public void testFrobeniusNorm_2() {
        Array2DRowRealMatrix m2 = new Array2DRowRealMatrix(testData2);
        Assert.assertEquals("testData2 Frobenius norm", JdkMath.sqrt(52.0), m2.getFrobeniusNorm(), entryTolerance);
    }
}
