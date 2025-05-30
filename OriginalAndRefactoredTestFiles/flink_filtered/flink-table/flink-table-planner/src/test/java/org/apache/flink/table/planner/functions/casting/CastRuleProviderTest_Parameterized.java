package org.apache.flink.table.planner.functions.casting;

import org.apache.flink.table.catalog.ObjectIdentifier;
import org.apache.flink.table.types.logical.ArrayType;
import org.apache.flink.table.types.logical.CharType;
import org.apache.flink.table.types.logical.DistinctType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.VarCharType;
import org.junit.jupiter.api.Test;
import static org.apache.flink.table.api.DataTypes.BIGINT;
import static org.apache.flink.table.api.DataTypes.INT;
import static org.apache.flink.table.api.DataTypes.ROW;
import static org.apache.flink.table.api.DataTypes.STRING;
import static org.apache.flink.table.api.DataTypes.TIME;
import static org.apache.flink.table.api.DataTypes.TINYINT;
import static org.apache.flink.table.types.logical.VarCharType.STRING_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CastRuleProviderTest_Parameterized {

    private static final LogicalType DISTINCT_INT = DistinctType.newBuilder(ObjectIdentifier.of("a", "b", "c"), INT().getLogicalType()).build();

    private static final LogicalType DISTINCT_BIG_INT = DistinctType.newBuilder(ObjectIdentifier.of("a", "b", "c"), BIGINT().getLogicalType()).build();

    private static final LogicalType INT = INT().getLogicalType();

    private static final LogicalType TINYINT = TINYINT().getLogicalType();

    @Test
    void testResolveDistinctTypeToIdentityCastRule_1() {
        assertThat(CastRuleProvider.resolve(DISTINCT_INT, INT)).isSameAs(IdentityCastRule.INSTANCE);
    }

    @Test
    void testResolveDistinctTypeToIdentityCastRule_2() {
        assertThat(CastRuleProvider.resolve(INT, DISTINCT_INT)).isSameAs(IdentityCastRule.INSTANCE);
    }

    @Test
    void testResolveDistinctTypeToIdentityCastRule_3() {
        assertThat(CastRuleProvider.resolve(DISTINCT_INT, DISTINCT_INT)).isSameAs(IdentityCastRule.INSTANCE);
    }

    @Test
    void testResolvePredefinedToString_3() {
        assertThat(CastRuleProvider.resolve(INT, STRING_TYPE)).isSameAs(NumericToStringCastRule.INSTANCE);
    }

    @Test
    void testCanFail_1() {
        assertThat(CastRuleProvider.canFail(TINYINT, INT)).isFalse();
    }

    @Test
    void testCanFail_2() {
        assertThat(CastRuleProvider.canFail(STRING_TYPE, TIME().getLogicalType())).isTrue();
    }

    @Test
    void testCanFail_3() {
        assertThat(CastRuleProvider.canFail(STRING_TYPE, STRING_TYPE)).isFalse();
    }

    @Test
    void testCanFail_4_testMerged_4() {
        LogicalType inputType = ROW(TINYINT(), STRING()).getLogicalType();
        assertThat(CastRuleProvider.canFail(inputType, ROW(INT(), TIME()).getLogicalType())).isTrue();
        assertThat(CastRuleProvider.canFail(inputType, ROW(INT(), STRING()).getLogicalType())).isFalse();
    }

    @ParameterizedTest
    @MethodSource("Provider_testResolvePredefinedToString_1to2")
    void testResolvePredefinedToString_1to2(int param1) {
        assertThat(CastRuleProvider.resolve(INT, new VarCharType(param1))).isSameAs(CharVarCharTrimPadCastRule.INSTANCE);
    }

    static public Stream<Arguments> Provider_testResolvePredefinedToString_1to2() {
        return Stream.of(arguments(10), arguments(10));
    }
}
