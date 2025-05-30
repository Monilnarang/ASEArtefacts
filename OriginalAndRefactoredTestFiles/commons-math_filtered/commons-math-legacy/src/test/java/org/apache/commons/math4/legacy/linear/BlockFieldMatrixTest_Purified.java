package org.apache.commons.math4.legacy.linear;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.NoDataException;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.apache.commons.math4.legacy.core.dfp.Dfp;

public final class BlockFieldMatrixTest_Purified {

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

    private Dfp[][] d3 = new Dfp[][] { { Dfp25.of(1), Dfp25.of(2), Dfp25.of(3), Dfp25.of(4) }, { Dfp25.of(5), Dfp25.of(6), Dfp25.of(7), Dfp25.of(8) } };

    private Dfp[][] d4 = new Dfp[][] { { Dfp25.of(1) }, { Dfp25.of(2) }, { Dfp25.of(3) }, { Dfp25.of(4) } };

    private Dfp[][] d5 = new Dfp[][] { { Dfp25.of(30) }, { Dfp25.of(70) } };

    private void checkGetSubMatrix(FieldMatrix<Dfp> m, Dfp[][] reference, int startRow, int endRow, int startColumn, int endColumn) {
        try {
            FieldMatrix<Dfp> sub = m.getSubMatrix(startRow, endRow, startColumn, endColumn);
            if (reference != null) {
                Assert.assertEquals(new BlockFieldMatrix<>(reference), sub);
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
                Assert.assertEquals(new BlockFieldMatrix<>(reference), sub);
            } else {
                Assert.fail("Expecting OutOfRangeException");
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
                Assert.assertEquals(new BlockFieldMatrix<>(reference), new BlockFieldMatrix<>(sub));
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
                Assert.assertEquals(new BlockFieldMatrix<>(reference), new BlockFieldMatrix<>(sub));
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
            return Dfp25.of(i * 11 + j, 11);
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
            Assert.assertEquals(Dfp25.of(i * 11 + j, 11), value);
        }

        public int getCount() {
            return count;
        }
    }

    private BlockFieldMatrix<Dfp> createRandomMatrix(Random r, int rows, int columns) {
        BlockFieldMatrix<Dfp> m = new BlockFieldMatrix<>(Dfp25.getField(), rows, columns);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int p = r.nextInt(20) - 10;
                int q = r.nextInt(20) - 10;
                if (q == 0) {
                    q = 1;
                }
                m.setEntry(i, j, Dfp25.of(p, q));
            }
        }
        return m;
    }

    @Test
    public void testDimensions_1_testMerged_1() {
        BlockFieldMatrix<Dfp> m = new BlockFieldMatrix<>(testData);
        Assert.assertEquals("testData row dimension", 3, m.getRowDimension());
        Assert.assertEquals("testData column dimension", 3, m.getColumnDimension());
        Assert.assertTrue("testData is square", m.isSquare());
    }

    @Test
    public void testDimensions_4_testMerged_2() {
        BlockFieldMatrix<Dfp> m2 = new BlockFieldMatrix<>(testData2);
        Assert.assertEquals("testData2 row dimension", m2.getRowDimension(), 2);
        Assert.assertEquals("testData2 column dimension", m2.getColumnDimension(), 3);
        Assert.assertFalse("testData2 is not square", m2.isSquare());
    }

    @Test
    public void testCopyFunctions_1() {
        Random r = new Random(66636328996002L);
        BlockFieldMatrix<Dfp> m1 = createRandomMatrix(r, 47, 83);
        BlockFieldMatrix<Dfp> m2 = new BlockFieldMatrix<>(m1.getData());
        Assert.assertEquals(m1, m2);
    }

    @Test
    public void testCopyFunctions_2() {
        BlockFieldMatrix<Dfp> m3 = new BlockFieldMatrix<>(testData);
        BlockFieldMatrix<Dfp> m4 = new BlockFieldMatrix<>(m3.getData());
        Assert.assertEquals(m3, m4);
    }
}
