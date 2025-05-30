package org.apache.druid.segment.column;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TypesTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testIs_1() {
        Assert.assertTrue(Types.is(ColumnType.LONG, ValueType.LONG));
    }

    @Test
    public void testIs_2() {
        Assert.assertTrue(Types.is(ColumnType.DOUBLE, ValueType.DOUBLE));
    }

    @Test
    public void testIs_3() {
        Assert.assertTrue(Types.is(ColumnType.FLOAT, ValueType.FLOAT));
    }

    @Test
    public void testIs_4() {
        Assert.assertTrue(Types.is(ColumnType.STRING, ValueType.STRING));
    }

    @Test
    public void testIs_5() {
        Assert.assertTrue(Types.is(ColumnType.LONG_ARRAY, ValueType.ARRAY));
    }

    @Test
    public void testIs_6() {
        Assert.assertTrue(Types.is(ColumnType.LONG_ARRAY.getElementType(), ValueType.LONG));
    }

    @Test
    public void testIs_7() {
        Assert.assertTrue(Types.is(ColumnType.DOUBLE_ARRAY, ValueType.ARRAY));
    }

    @Test
    public void testIs_8() {
        Assert.assertTrue(Types.is(ColumnType.DOUBLE_ARRAY.getElementType(), ValueType.DOUBLE));
    }

    @Test
    public void testIs_9() {
        Assert.assertTrue(Types.is(ColumnType.STRING_ARRAY, ValueType.ARRAY));
    }

    @Test
    public void testIs_10() {
        Assert.assertTrue(Types.is(ColumnType.STRING_ARRAY.getElementType(), ValueType.STRING));
    }

    @Test
    public void testIs_11() {
        Assert.assertTrue(Types.is(TypeStrategiesTest.NULLABLE_TEST_PAIR_TYPE, ValueType.COMPLEX));
    }

    @Test
    public void testIs_12() {
        Assert.assertFalse(Types.is(ColumnType.LONG, ValueType.DOUBLE));
    }

    @Test
    public void testIs_13() {
        Assert.assertFalse(Types.is(ColumnType.DOUBLE, ValueType.FLOAT));
    }

    @Test
    public void testIs_14() {
        Assert.assertFalse(Types.is(null, ValueType.STRING));
    }

    @Test
    public void testIs_15() {
        Assert.assertTrue(Types.isNullOr(null, ValueType.STRING));
    }

    @Test
    public void testNullOrAnyOf_1() {
        Assert.assertTrue(Types.isNullOrAnyOf(ColumnType.LONG, ValueType.STRING, ValueType.LONG, ValueType.DOUBLE));
    }

    @Test
    public void testNullOrAnyOf_2() {
        Assert.assertFalse(Types.isNullOrAnyOf(ColumnType.DOUBLE, ValueType.STRING, ValueType.LONG, ValueType.FLOAT));
    }

    @Test
    public void testNullOrAnyOf_3() {
        Assert.assertTrue(Types.isNullOrAnyOf(null, ValueType.STRING, ValueType.LONG, ValueType.FLOAT));
    }

    @Test
    public void testEither_1() {
        Assert.assertTrue(Types.either(ColumnType.LONG, ColumnType.DOUBLE, ValueType.DOUBLE));
    }

    @Test
    public void testEither_2() {
        Assert.assertFalse(Types.either(ColumnType.LONG, ColumnType.STRING, ValueType.DOUBLE));
    }
}
