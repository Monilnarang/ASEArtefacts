package org.apache.flink.table.planner.typeutils;

import org.apache.flink.table.expressions.TimeIntervalUnit;
import org.apache.flink.table.planner.typeutils.SymbolUtil.SerializableSymbol;
import org.apache.flink.table.utils.DateTimeUtils;
import org.apache.calcite.avatica.util.TimeUnitRange;
import org.apache.calcite.sql.SqlJsonQueryEmptyOrErrorBehavior;
import org.apache.calcite.sql.fun.SqlTrimFunction;
import org.junit.jupiter.api.Test;
import static org.apache.flink.table.planner.typeutils.SymbolUtil.calciteToCommon;
import static org.apache.flink.table.planner.typeutils.SymbolUtil.calciteToSerializable;
import static org.apache.flink.table.planner.typeutils.SymbolUtil.commonToCalcite;
import static org.apache.flink.table.planner.typeutils.SymbolUtil.serializableToCalcite;
import static org.assertj.core.api.Assertions.assertThat;

class SymbolUtilTest_Purified {

    @Test
    void testCommonToCalcite_1() {
        assertThat(commonToCalcite(TimeIntervalUnit.QUARTER)).isEqualTo(TimeUnitRange.QUARTER);
    }

    @Test
    void testCommonToCalcite_2() {
        assertThat(calciteToCommon(TimeUnitRange.QUARTER, false)).isEqualTo(TimeIntervalUnit.QUARTER);
    }

    @Test
    void testCommonToCalcite_3() {
        assertThat(commonToCalcite(DateTimeUtils.TimeUnitRange.QUARTER)).isEqualTo(TimeUnitRange.QUARTER);
    }

    @Test
    void testCommonToCalcite_4() {
        assertThat(calciteToCommon(TimeUnitRange.QUARTER, true)).isEqualTo(DateTimeUtils.TimeUnitRange.QUARTER);
    }
}
