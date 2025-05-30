package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.NoDataException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.apache.commons.math4.core.jdkmath.JdkMath;

public final class BlockRealMatrixTest_Purified {

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

    private double[][] d3 = new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } };

    private double[][] d4 = new double[][] { { 1 }, { 2 }, { 3 }, { 4 } };

    private double[][] d5 = new double[][] { { 30 }, { 70 } };

    private void checkGetSubMatrix(RealMatrix m, double[][] reference, int startRow, int endRow, int startColumn, int endColumn) {
        try {
            RealMatrix sub = m.getSubMatrix(startRow, endRow, startColumn, endColumn);
            if (reference != null) {
                Assert.assertEquals(new BlockRealMatrix(reference), sub);
            } else {
                Assert.fail("Expecting OutOfRangeException or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NumberIsTooSmallException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NoDataException e) {
            if (reference != null) {
                throw e;
            }
        }
    }

    private void checkGetSubMatrix(RealMatrix m, double[][] reference, int[] selectedRows, int[] selectedColumns) {
        try {
            RealMatrix sub = m.getSubMatrix(selectedRows, selectedColumns);
            if (reference != null) {
                Assert.assertEquals(new BlockRealMatrix(reference), sub);
            } else {
                Assert.fail("Expecting OutOfRangeException or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NumberIsTooSmallException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NoDataException e) {
            if (reference != null) {
                throw e;
            }
        }
    }

    private void checkCopy(RealMatrix m, double[][] reference, int startRow, int endRow, int startColumn, int endColumn) {
        try {
            double[][] sub = (reference == null) ? new double[1][1] : new double[reference.length][reference[0].length];
            m.copySubMatrix(startRow, endRow, startColumn, endColumn, sub);
            if (reference != null) {
                Assert.assertEquals(new BlockRealMatrix(reference), new BlockRealMatrix(sub));
            } else {
                Assert.fail("Expecting OutOfRangeException or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NumberIsTooSmallException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NoDataException e) {
            if (reference != null) {
                throw e;
            }
        }
    }

    private void checkCopy(RealMatrix m, double[][] reference, int[] selectedRows, int[] selectedColumns) {
        try {
            double[][] sub = (reference == null) ? new double[1][1] : new double[reference.length][reference[0].length];
            m.copySubMatrix(selectedRows, selectedColumns, sub);
            if (reference != null) {
                Assert.assertEquals(new BlockRealMatrix(reference), new BlockRealMatrix(sub));
            } else {
                Assert.fail("Expecting OutOfRangeException or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NumberIsTooSmallException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NoDataException e) {
            if (reference != null) {
                throw e;
            }
        }
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

    protected void assertClose(RealMatrix m, RealMatrix n, double tolerance) {
        Assert.assertTrue(m.subtract(n).getNorm() < tolerance);
    }

    protected void assertClose(double[] m, double[] n, double tolerance) {
        if (m.length != n.length) {
            Assert.fail("vectors not same length");
        }
        for (int i = 0; i < m.length; i++) {
            Assert.assertEquals(m[i], n[i], tolerance);
        }
    }

    private BlockRealMatrix createRandomMatrix(Random r, int rows, int columns) {
        BlockRealMatrix m = new BlockRealMatrix(rows, columns);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                m.setEntry(i, j, 200 * r.nextDouble() - 100);
            }
        }
        return m;
    }

    @Test
    public void testDimensions_1_testMerged_1() {
        BlockRealMatrix m = new BlockRealMatrix(testData);
        Assert.assertEquals("testData row dimension", 3, m.getRowDimension());
        Assert.assertEquals("testData column dimension", 3, m.getColumnDimension());
        Assert.assertTrue("testData is square", m.isSquare());
    }

    @Test
    public void testDimensions_4_testMerged_2() {
        BlockRealMatrix m2 = new BlockRealMatrix(testData2);
        Assert.assertEquals("testData2 row dimension", m2.getRowDimension(), 2);
        Assert.assertEquals("testData2 column dimension", m2.getColumnDimension(), 3);
        Assert.assertFalse("testData2 is not square", m2.isSquare());
    }

    @Test
    public void testCopyFunctions_1() {
        Random r = new Random(66636328996002L);
        BlockRealMatrix m1 = createRandomMatrix(r, 47, 83);
        BlockRealMatrix m2 = new BlockRealMatrix(m1.getData());
        Assert.assertEquals(m1, m2);
    }

    @Test
    public void testCopyFunctions_2() {
        BlockRealMatrix m3 = new BlockRealMatrix(testData);
        BlockRealMatrix m4 = new BlockRealMatrix(m3.getData());
        Assert.assertEquals(m3, m4);
    }

    @Test
    public void testNorm_1() {
        BlockRealMatrix m = new BlockRealMatrix(testData);
        Assert.assertEquals("testData norm", 14d, m.getNorm(), entryTolerance);
    }

    @Test
    public void testNorm_2() {
        BlockRealMatrix m2 = new BlockRealMatrix(testData2);
        Assert.assertEquals("testData2 norm", 7d, m2.getNorm(), entryTolerance);
    }

    @Test
    public void testFrobeniusNorm_1() {
        BlockRealMatrix m = new BlockRealMatrix(testData);
        Assert.assertEquals("testData Frobenius norm", JdkMath.sqrt(117.0), m.getFrobeniusNorm(), entryTolerance);
    }

    @Test
    public void testFrobeniusNorm_2() {
        BlockRealMatrix m2 = new BlockRealMatrix(testData2);
        Assert.assertEquals("testData2 Frobenius norm", JdkMath.sqrt(52.0), m2.getFrobeniusNorm(), entryTolerance);
    }
}
