package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.MathIllegalStateException;
import org.apache.commons.math4.legacy.exception.NoDataException;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.apache.commons.math4.legacy.core.dfp.Dfp;

public final class FieldMatrixImplTest_Purified {

    protected Dfp[][] id = { { Dfp25.of(1), Dfp25.of(0), Dfp25.of(0) }, { Dfp25.of(0), Dfp25.of(1), Dfp25.of(0) }, { Dfp25.of(0), Dfp25.of(0), Dfp25.of(1) } };

    protected Dfp[][] testData = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3) }, { Dfp25.of(2), Dfp25.of(5), Dfp25.of(3) }, { Dfp25.of(1), Dfp25.of(0), Dfp25.of(8) } };

    protected Dfp[][] testDataLU = { { Dfp25.of(2), Dfp25.of(5), Dfp25.of(3) }, { Dfp25.of(1, 2), Dfp25.of(-5, 2), Dfp25.of(13, 2) }, { Dfp25.of(1, 2), Dfp25.of(1, 5), Dfp25.of(1, 5) } };

    protected Dfp[][] testDataPlus2 = { { Dfp25.of(3), Dfp25.of(4), Dfp25.of(5) }, { Dfp25.of(4), Dfp25.of(7), Dfp25.of(5) }, { Dfp25.of(3), Dfp25.of(2), Dfp25.of(10) } };

    protected Dfp[][] testDataMinus = { { Dfp25.of(-1), Dfp25.of(-2), Dfp25.of(-3) }, { Dfp25.of(-2), Dfp25.of(-5), Dfp25.of(-3) }, { Dfp25.of(-1), Dfp25.of(0), Dfp25.of(-8) } };

    protected Dfp[] testDataRow1 = { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3) };

    protected Dfp[] testDataCol3 = { Dfp25.of(3), Dfp25.of(3), Dfp25.of(8) };

    protected Dfp[][] testDataInv = { { Dfp25.of(-40), Dfp25.of(16), Dfp25.of(9) }, { Dfp25.of(13), Dfp25.of(-5), Dfp25.of(-3) }, { Dfp25.of(5), Dfp25.of(-2), Dfp25.of(-1) } };

    protected Dfp[] preMultTest = { Dfp25.of(8), Dfp25.of(12), Dfp25.of(33) };

    protected Dfp[][] testData2 = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3) }, { Dfp25.of(2), Dfp25.of(5), Dfp25.of(3) } };

    protected Dfp[][] testData2T = { { Dfp25.of(1), Dfp25.of(2) }, { Dfp25.of(2), Dfp25.of(5) }, { Dfp25.of(3), Dfp25.of(3) } };

    protected Dfp[][] testDataPlusInv = { { Dfp25.of(-39), Dfp25.of(18), Dfp25.of(12) }, { Dfp25.of(15), Dfp25.of(0), Dfp25.of(0) }, { Dfp25.of(6), Dfp25.of(-2), Dfp25.of(7) } };

    protected Dfp[][] luData = { { Dfp25.of(2), Dfp25.of(3), Dfp25.of(3) }, { Dfp25.of(0), Dfp25.of(5), Dfp25.of(7) }, { Dfp25.of(6), Dfp25.of(9), Dfp25.of(8) } };

    protected Dfp[][] luDataLUDecomposition = { { Dfp25.of(6), Dfp25.of(9), Dfp25.of(8) }, { Dfp25.of(0), Dfp25.of(5), Dfp25.of(7) }, { Dfp25.of(1, 3), Dfp25.of(0), Dfp25.of(1, 3) } };

    protected Dfp[][] singular = { { Dfp25.of(2), Dfp25.of(3) }, { Dfp25.of(2), Dfp25.of(3) } };

    protected Dfp[][] bigSingular = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(2), Dfp25.of(5), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(7), Dfp25.of(3), Dfp25.of(256), Dfp25.of(1930) }, { Dfp25.of(3), Dfp25.of(7), Dfp25.of(6), Dfp25.of(8) } };

    protected Dfp[][] detData = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3) }, { Dfp25.of(4), Dfp25.of(5), Dfp25.of(6) }, { Dfp25.of(7), Dfp25.of(8), Dfp25.of(10) } };

    protected Dfp[][] detData2 = { { Dfp25.of(1), Dfp25.of(3) }, { Dfp25.of(2), Dfp25.of(4) } };

    protected Dfp[] testVector = { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3) };

    protected Dfp[] testVector2 = { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) };

    protected Dfp[][] subTestData = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(3, 2), Dfp25.of(5, 2), Dfp25.of(7, 2), Dfp25.of(9, 2) }, { Dfp25.of(2), Dfp25.of(4), Dfp25.of(6), Dfp25.of(8) }, { Dfp25.of(4), Dfp25.of(5), Dfp25.of(6), Dfp25.of(7) } };

    protected Dfp[][] subRows02Cols13 = { { Dfp25.of(2), Dfp25.of(4) }, { Dfp25.of(4), Dfp25.of(8) } };

    protected Dfp[][] subRows03Cols12 = { { Dfp25.of(2), Dfp25.of(3) }, { Dfp25.of(5), Dfp25.of(6) } };

    protected Dfp[][] subRows03Cols123 = { { Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(5), Dfp25.of(6), Dfp25.of(7) } };

    protected Dfp[][] subRows20Cols123 = { { Dfp25.of(4), Dfp25.of(6), Dfp25.of(8) }, { Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) } };

    protected Dfp[][] subRows31Cols31 = { { Dfp25.of(7), Dfp25.of(5) }, { Dfp25.of(9, 2), Dfp25.of(5, 2) } };

    protected Dfp[][] subRows01Cols23 = { { Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(7, 2), Dfp25.of(9, 2) } };

    protected Dfp[][] subRows23Cols00 = { { Dfp25.of(2) }, { Dfp25.of(4) } };

    protected Dfp[][] subRows00Cols33 = { { Dfp25.of(4) } };

    protected Dfp[][] subRow0 = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) } };

    protected Dfp[][] subRow3 = { { Dfp25.of(4), Dfp25.of(5), Dfp25.of(6), Dfp25.of(7) } };

    protected Dfp[][] subColumn1 = { { Dfp25.of(2) }, { Dfp25.of(5, 2) }, { Dfp25.of(4) }, { Dfp25.of(5) } };

    protected Dfp[][] subColumn3 = { { Dfp25.of(4) }, { Dfp25.of(9, 2) }, { Dfp25.of(8) }, { Dfp25.of(7) } };

    protected double entryTolerance = 10E-16;

    protected double normTolerance = 10E-14;

    private final Dfp[][] d3 = new Dfp[][] { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(5), Dfp25.of(6), Dfp25.of(7), Dfp25.of(8) } };

    private final Dfp[][] d4 = new Dfp[][] { { Dfp25.of(1) }, { Dfp25.of(2) }, { Dfp25.of(3) }, { Dfp25.of(4) } };

    private final Dfp[][] d5 = new Dfp[][] { { Dfp25.of(30) }, { Dfp25.of(70) } };

    private void checkGetSubMatrix(FieldMatrix<Dfp> m, Dfp[][] reference, int startRow, int endRow, int startColumn, int endColumn) {
        try {
            FieldMatrix<Dfp> sub = m.getSubMatrix(startRow, endRow, startColumn, endColumn);
            if (reference != null) {
                Assert.assertEquals(new Array2DRowFieldMatrix<>(reference), sub);
            } else {
                Assert.fail("Expecting OutOfRangeException or NotStrictlyPositiveException" + " or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NotStrictlyPositiveException e) {
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

    private void checkGetSubMatrix(FieldMatrix<Dfp> m, Dfp[][] reference, int[] selectedRows, int[] selectedColumns) {
        try {
            FieldMatrix<Dfp> sub = m.getSubMatrix(selectedRows, selectedColumns);
            if (reference != null) {
                Assert.assertEquals(new Array2DRowFieldMatrix<>(reference), sub);
            } else {
                Assert.fail("Expecting OutOfRangeException or NotStrictlyPositiveException" + " or NumberIsTooSmallException or NoDataException");
            }
        } catch (OutOfRangeException e) {
            if (reference != null) {
                throw e;
            }
        } catch (NotStrictlyPositiveException e) {
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

    private void checkCopy(FieldMatrix<Dfp> m, Dfp[][] reference, int startRow, int endRow, int startColumn, int endColumn) {
        try {
            Dfp[][] sub = (reference == null) ? new Dfp[1][1] : new Dfp[reference.length][reference[0].length];
            m.copySubMatrix(startRow, endRow, startColumn, endColumn, sub);
            if (reference != null) {
                Assert.assertEquals(new Array2DRowFieldMatrix<>(reference), new Array2DRowFieldMatrix<>(sub));
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

    private void checkCopy(FieldMatrix<Dfp> m, Dfp[][] reference, int[] selectedRows, int[] selectedColumns) {
        try {
            Dfp[][] sub = (reference == null) ? new Dfp[1][1] : new Dfp[reference.length][reference[0].length];
            m.copySubMatrix(selectedRows, selectedColumns, sub);
            if (reference != null) {
                Assert.assertEquals(new Array2DRowFieldMatrix<>(reference), new Array2DRowFieldMatrix<>(sub));
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

    private FieldVector<Dfp> columnToVector(Dfp[][] column) {
        Dfp[] data = new Dfp[column.length];
        for (int i = 0; i < data.length; ++i) {
            data[i] = column[i][0];
        }
        return new ArrayFieldVector<>(data, false);
    }

    private Dfp[] columnToArray(Dfp[][] column) {
        Dfp[] data = new Dfp[column.length];
        for (int i = 0; i < data.length; ++i) {
            data[i] = column[i][0];
        }
        return data;
    }

    private void checkArrays(Dfp[] expected, Dfp[] actual) {
        Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; ++i) {
            Assert.assertEquals(expected[i], actual[i]);
        }
    }

    private static final class SetVisitor extends DefaultFieldMatrixChangingVisitor<Dfp> {

        SetVisitor() {
            super(Dfp25.ZERO);
        }

        @Override
        public Dfp visit(int i, int j, Dfp value) {
            return Dfp25.of(i * 1024 + j, 1024);
        }
    }

    private static final class GetVisitor extends DefaultFieldMatrixPreservingVisitor<Dfp> {

        private int count;

        GetVisitor() {
            super(Dfp25.ZERO);
            count = 0;
        }

        @Override
        public void visit(int i, int j, Dfp value) {
            ++count;
            Assert.assertEquals(Dfp25.of(i * 1024 + j, 1024), value);
        }

        public int getCount() {
            return count;
        }
    }

    protected void splitLU(FieldMatrix<Dfp> lu, Dfp[][] lowerData, Dfp[][] upperData) {
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
                    upperData[i][j] = Dfp25.ZERO;
                } else if (i == j) {
                    lowerData[i][j] = Dfp25.ONE;
                    upperData[i][j] = lu.getEntry(i, j);
                } else {
                    lowerData[i][j] = Dfp25.ZERO;
                    upperData[i][j] = lu.getEntry(i, j);
                }
            }
        }
    }

    protected FieldMatrix<Dfp> permuteRows(FieldMatrix<Dfp> matrix, int[] permutation) {
        if (!matrix.isSquare()) {
            throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
        }
        if (matrix.getRowDimension() != permutation.length) {
            throw new DimensionMismatchException(matrix.getRowDimension(), permutation.length);
        }
        int n = matrix.getRowDimension();
        int m = matrix.getColumnDimension();
        Dfp[][] out = new Dfp[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                out[i][j] = matrix.getEntry(permutation[i], j);
            }
        }
        return new Array2DRowFieldMatrix<>(out);
    }

    @Test
    public void testDimensions_1_testMerged_1() {
        Array2DRowFieldMatrix<Dfp> m = new Array2DRowFieldMatrix<>(testData);
        Assert.assertEquals("testData row dimension", 3, m.getRowDimension());
        Assert.assertEquals("testData column dimension", 3, m.getColumnDimension());
        Assert.assertTrue("testData is square", m.isSquare());
    }

    @Test
    public void testDimensions_4_testMerged_2() {
        Array2DRowFieldMatrix<Dfp> m2 = new Array2DRowFieldMatrix<>(testData2);
        Assert.assertEquals("testData2 row dimension", m2.getRowDimension(), 2);
        Assert.assertEquals("testData2 column dimension", m2.getColumnDimension(), 3);
        Assert.assertFalse("testData2 is not square", m2.isSquare());
    }

    @Test
    public void testCopyFunctions_1() {
        Array2DRowFieldMatrix<Dfp> m1 = new Array2DRowFieldMatrix<>(testData);
        Array2DRowFieldMatrix<Dfp> m2 = new Array2DRowFieldMatrix<>(m1.getData());
        Assert.assertEquals(m2, m1);
    }

    @Test
    public void testCopyFunctions_2() {
        Array2DRowFieldMatrix<Dfp> m3 = new Array2DRowFieldMatrix<>(testData);
        Array2DRowFieldMatrix<Dfp> m4 = new Array2DRowFieldMatrix<>(m3.getData(), false);
        Assert.assertEquals(m4, m3);
    }
}
