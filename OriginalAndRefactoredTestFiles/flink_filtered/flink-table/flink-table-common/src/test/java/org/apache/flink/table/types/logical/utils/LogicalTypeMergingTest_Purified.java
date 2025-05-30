package org.apache.flink.table.types.logical.utils;

import org.apache.flink.table.types.logical.DecimalType;
import org.apache.flink.table.types.logical.LogicalType;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.apache.flink.table.test.TableAssertions.assertThat;

class LogicalTypeMergingTest_Purified {

    private static final DecimalType decimal(int precision, int scale) {
        return new DecimalType(false, precision, scale);
    }

    @Test
    void testFindDivisionDecimalType_1() {
        assertThat(LogicalTypeMerging.findDivisionDecimalType(32, 8, 38, 8)).hasPrecisionAndScale(38, 6);
    }

    @Test
    void testFindDivisionDecimalType_2() {
        assertThat(LogicalTypeMerging.findDivisionDecimalType(30, 20, 30, 20)).hasPrecisionAndScale(38, 8);
    }

    @Test
    void testFindMultiplicationDecimalType_1() {
        assertThat(LogicalTypeMerging.findMultiplicationDecimalType(30, 10, 30, 10)).hasPrecisionAndScale(38, 6);
    }

    @Test
    void testFindMultiplicationDecimalType_2() {
        assertThat(LogicalTypeMerging.findMultiplicationDecimalType(30, 20, 30, 20)).hasPrecisionAndScale(38, 17);
    }

    @Test
    void testFindMultiplicationDecimalType_3() {
        assertThat(LogicalTypeMerging.findMultiplicationDecimalType(38, 2, 38, 3)).hasPrecisionAndScale(38, 5);
    }

    @Test
    void testFindModuloDecimalType_1() {
        assertThat(LogicalTypeMerging.findModuloDecimalType(30, 10, 30, 10)).hasPrecisionAndScale(30, 10);
    }

    @Test
    void testFindModuloDecimalType_2() {
        assertThat(LogicalTypeMerging.findModuloDecimalType(30, 20, 25, 20)).hasPrecisionAndScale(25, 20);
    }

    @Test
    void testFindModuloDecimalType_3() {
        assertThat(LogicalTypeMerging.findModuloDecimalType(10, 10, 10, 10)).hasPrecisionAndScale(10, 10);
    }

    @Test
    void testFindAdditionDecimalType_1() {
        assertThat(LogicalTypeMerging.findAdditionDecimalType(38, 8, 32, 8)).hasPrecisionAndScale(38, 7);
    }

    @Test
    void testFindAdditionDecimalType_2() {
        assertThat(LogicalTypeMerging.findAdditionDecimalType(32, 8, 38, 8)).hasPrecisionAndScale(38, 7);
    }

    @Test
    void testFindAdditionDecimalType_3() {
        assertThat(LogicalTypeMerging.findAdditionDecimalType(30, 20, 28, 20)).hasPrecisionAndScale(31, 20);
    }

    @Test
    void testFindAdditionDecimalType_4() {
        assertThat(LogicalTypeMerging.findAdditionDecimalType(10, 10, 10, 10)).hasPrecisionAndScale(11, 10);
    }

    @Test
    void testFindAdditionDecimalType_5() {
        assertThat(LogicalTypeMerging.findAdditionDecimalType(38, 5, 38, 4)).hasPrecisionAndScale(38, 5);
    }

    @Test
    void testFindRoundingDecimalType_1() {
        assertThat(LogicalTypeMerging.findRoundDecimalType(32, 8, 5)).hasPrecisionAndScale(30, 5);
    }

    @Test
    void testFindRoundingDecimalType_2() {
        assertThat(LogicalTypeMerging.findRoundDecimalType(32, 8, 10)).hasPrecisionAndScale(32, 8);
    }

    @Test
    void testFindRoundingDecimalType_3() {
        assertThat(LogicalTypeMerging.findRoundDecimalType(30, 20, 18)).hasPrecisionAndScale(29, 18);
    }

    @Test
    void testFindRoundingDecimalType_4() {
        assertThat(LogicalTypeMerging.findRoundDecimalType(10, 10, 2)).hasPrecisionAndScale(3, 2);
    }

    @Test
    void testFindAvgAggType_1() {
    }

    @Test
    void testFindAvgAggType_2() {
    }

    @Test
    void testFindAvgAggType_3() {
    }

    @Test
    void testFindAvgAggType_4() {
    }

    @Test
    void testFindAvgAggType_5() {
    }
}
