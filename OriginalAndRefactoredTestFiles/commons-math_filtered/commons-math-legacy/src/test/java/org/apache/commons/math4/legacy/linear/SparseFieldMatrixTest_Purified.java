package org.apache.commons.math4.legacy.linear;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.math4.legacy.core.Field;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.NoDataException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.apache.commons.math4.legacy.core.dfp.Dfp;

public class SparseFieldMatrixTest_Purified {

    protected Dfp[][] id = { { Dfp25.of(1), Dfp25.of(0), Dfp25.of(0) }, { Dfp25.of(0), Dfp25.of(1), Dfp25.of(0) }, { Dfp25.of(0), Dfp25.of(0), Dfp25.of(1) } };

    protected Dfp[][] testData = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3) }, { Dfp25.of(2), Dfp25.of(5), Dfp25.of(3) }, { Dfp25.of(1), Dfp25.of(0), Dfp25.of(8) } };

    protected Dfp[][] testDataLU = null;

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

    protected Dfp[][] luDataLUDecomposition = null;

    protected Dfp[][] singular = { { Dfp25.of(2), Dfp25.of(3) }, { Dfp25.of(2), Dfp25.of(3) } };

    protected Dfp[][] bigSingular = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(2), Dfp25.of(5), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(7), Dfp25.of(3), Dfp25.of(256), Dfp25.of(1930) }, { Dfp25.of(3), Dfp25.of(7), Dfp25.of(6), Dfp25.of(8) } };

    protected Dfp[][] detData = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3) }, { Dfp25.of(4), Dfp25.of(5), Dfp25.of(6) }, { Dfp25.of(7), Dfp25.of(8), Dfp25.of(10) } };

    protected Dfp[][] detData2 = { { Dfp25.of(1), Dfp25.of(3) }, { Dfp25.of(2), Dfp25.of(4) } };

    protected Dfp[] testVector = { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3) };

    protected Dfp[] testVector2 = { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) };

    protected Dfp[][] subTestData = null;

    protected Dfp[][] subRows02Cols13 = { { Dfp25.of(2), Dfp25.of(4) }, { Dfp25.of(4), Dfp25.of(8) } };

    protected Dfp[][] subRows03Cols12 = { { Dfp25.of(2), Dfp25.of(3) }, { Dfp25.of(5), Dfp25.of(6) } };

    protected Dfp[][] subRows03Cols123 = { { Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(5), Dfp25.of(6), Dfp25.of(7) } };

    protected Dfp[][] subRows20Cols123 = { { Dfp25.of(4), Dfp25.of(6), Dfp25.of(8) }, { Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) } };

    protected Dfp[][] subRows31Cols31 = null;

    protected Dfp[][] subRows01Cols23 = null;

    protected Dfp[][] subRows23Cols00 = { { Dfp25.of(2) }, { Dfp25.of(4) } };

    protected Dfp[][] subRows00Cols33 = { { Dfp25.of(4) } };

    protected Dfp[][] subRow0 = { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) } };

    protected Dfp[][] subRow3 = { { Dfp25.of(4), Dfp25.of(5), Dfp25.of(6), Dfp25.of(7) } };

    protected Dfp[][] subColumn1 = null;

    protected Dfp[][] subColumn3 = null;

    protected double entryTolerance = 10E-16;

    protected double normTolerance = 10E-14;

    protected Field<Dfp> field = Dfp25.getField();

    public SparseFieldMatrixTest() {
        testDataLU = new Dfp[][] { { Dfp25.of(2), Dfp25.of(5), Dfp25.of(3) }, { Dfp25.of(.5d), Dfp25.of(-2.5d), Dfp25.of(6.5d) }, { Dfp25.of(0.5d), Dfp25.of(0.2d), Dfp25.of(.2d) } };
        luDataLUDecomposition = new Dfp[][] { { Dfp25.of(6), Dfp25.of(9), Dfp25.of(8) }, { Dfp25.of(0), Dfp25.of(5), Dfp25.of(7) }, { Dfp25.of(0.33333333333333), Dfp25.of(0), Dfp25.of(0.33333333333333) } };
        subTestData = new Dfp[][] { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(1.5), Dfp25.of(2.5), Dfp25.of(3.5), Dfp25.of(4.5) }, { Dfp25.of(2), Dfp25.of(4), Dfp25.of(6), Dfp25.of(8) }, { Dfp25.of(4), Dfp25.of(5), Dfp25.of(6), Dfp25.of(7) } };
        subRows31Cols31 = new Dfp[][] { { Dfp25.of(7), Dfp25.of(5) }, { Dfp25.of(4.5), Dfp25.of(2.5) } };
        subRows01Cols23 = new Dfp[][] { { Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(3.5), Dfp25.of(4.5) } };
        subColumn1 = new Dfp[][] { { Dfp25.of(2) }, { Dfp25.of(2.5) }, { Dfp25.of(4) }, { Dfp25.of(5) } };
        subColumn3 = new Dfp[][] { { Dfp25.of(4) }, { Dfp25.of(4.5) }, { Dfp25.of(8) }, { Dfp25.of(7) } };
    }

    private Dfp[][] d3 = new Dfp[][] { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(5), Dfp25.of(6), Dfp25.of(7), Dfp25.of(8) } };

    private Dfp[][] d4 = new Dfp[][] { { Dfp25.of(1) }, { Dfp25.of(2) }, { Dfp25.of(3) }, { Dfp25.of(4) } };

    private Dfp[][] d5 = new Dfp[][] { { Dfp25.of(30) }, { Dfp25.of(70) } };

    private FieldVector<Dfp> columnToVector(Dfp[][] column) {
        Dfp[] data = new Dfp[column.length];
        for (int i = 0; i < data.length; ++i) {
            data[i] = column[i][0];
        }
        return new ArrayFieldVector<>(data, false);
    }

    protected void assertClose(String msg, FieldMatrix<Dfp> m, FieldMatrix<Dfp> n, double tolerance) {
        for (int i = 0; i < m.getRowDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                Assert.assertEquals(msg, m.getEntry(i, j).toDouble(), n.getEntry(i, j).toDouble(), tolerance);
            }
        }
    }

    protected void assertClose(String msg, Dfp[] m, Dfp[] n, double tolerance) {
        if (m.length != n.length) {
            Assert.fail("vectors not same length");
        }
        for (int i = 0; i < m.length; i++) {
            Assert.assertEquals(msg + " " + i + " elements differ", m[i].toDouble(), n[i].toDouble(), tolerance);
        }
    }

    private SparseFieldMatrix<Dfp> createSparseMatrix(Dfp[][] data) {
        SparseFieldMatrix<Dfp> matrix = new SparseFieldMatrix<>(field, data.length, data[0].length);
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                matrix.setEntry(row, col, data[row][col]);
            }
        }
        return matrix;
    }

    @Test
    public void testDimensions_1_testMerged_1() {
        SparseFieldMatrix<Dfp> m = createSparseMatrix(testData);
        Assert.assertEquals("testData row dimension", 3, m.getRowDimension());
        Assert.assertEquals("testData column dimension", 3, m.getColumnDimension());
        Assert.assertTrue("testData is square", m.isSquare());
    }

    @Test
    public void testDimensions_4_testMerged_2() {
        SparseFieldMatrix<Dfp> m2 = createSparseMatrix(testData2);
        Assert.assertEquals("testData2 row dimension", m2.getRowDimension(), 2);
        Assert.assertEquals("testData2 column dimension", m2.getColumnDimension(), 3);
        Assert.assertFalse("testData2 is not square", m2.isSquare());
    }
}
