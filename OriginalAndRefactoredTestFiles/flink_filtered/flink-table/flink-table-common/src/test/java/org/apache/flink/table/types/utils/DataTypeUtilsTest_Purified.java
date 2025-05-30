package org.apache.flink.table.types.utils;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.catalog.Column;
import org.apache.flink.table.catalog.ObjectIdentifier;
import org.apache.flink.table.catalog.ResolvedSchema;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.test.DataTypeConditions;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.FieldsDataType;
import org.apache.flink.table.types.logical.DistinctType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.StructuredType;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import static org.apache.flink.table.api.DataTypes.BIGINT;
import static org.apache.flink.table.api.DataTypes.BOOLEAN;
import static org.apache.flink.table.api.DataTypes.DOUBLE;
import static org.apache.flink.table.api.DataTypes.FIELD;
import static org.apache.flink.table.api.DataTypes.INT;
import static org.apache.flink.table.api.DataTypes.ROW;
import static org.apache.flink.table.api.DataTypes.STRING;
import static org.apache.flink.table.api.DataTypes.TIMESTAMP;
import static org.apache.flink.table.test.TableAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataTypeUtilsTest_Purified {

    @Test
    void testAppendRowFields_1() {
        assertThat(DataTypeUtils.appendRowFields(ROW(FIELD("a0", BOOLEAN()), FIELD("a1", DOUBLE()), FIELD("a2", INT())), Arrays.asList(FIELD("a3", BIGINT()), FIELD("a4", TIMESTAMP(3))))).isEqualTo(ROW(FIELD("a0", BOOLEAN()), FIELD("a1", DOUBLE()), FIELD("a2", INT()), FIELD("a3", BIGINT()), FIELD("a4", TIMESTAMP(3))));
    }

    @Test
    void testAppendRowFields_2() {
        assertThat(DataTypeUtils.appendRowFields(ROW(), Arrays.asList(FIELD("a", BOOLEAN()), FIELD("b", INT())))).isEqualTo(ROW(FIELD("a", BOOLEAN()), FIELD("b", INT())));
    }

    @Test
    void testIsInternalClass_1() {
        assertThat(DataTypes.INT()).is(DataTypeConditions.INTERNAL);
    }

    @Test
    void testIsInternalClass_2() {
        assertThat(DataTypes.INT().notNull().bridgedTo(int.class)).is(DataTypeConditions.INTERNAL);
    }

    @Test
    void testIsInternalClass_3() {
        assertThat(DataTypes.ROW().bridgedTo(RowData.class)).is(DataTypeConditions.INTERNAL);
    }

    @Test
    void testIsInternalClass_4() {
        assertThat(DataTypes.ROW()).isNot(DataTypeConditions.INTERNAL);
    }

    @Test
    void testFlattenToDataTypes_1() {
        assertThat(DataTypeUtils.flattenToDataTypes(INT())).containsOnly(INT());
    }

    @Test
    void testFlattenToDataTypes_2() {
        assertThat(DataTypeUtils.flattenToDataTypes(ROW(FIELD("a", INT()), FIELD("b", BOOLEAN())))).containsExactly(INT(), BOOLEAN());
    }

    @Test
    void testFlattenToNames_1() {
        assertThat(DataTypeUtils.flattenToNames(INT(), Collections.emptyList())).containsOnly("f0");
    }

    @Test
    void testFlattenToNames_2() {
        assertThat(DataTypeUtils.flattenToNames(INT(), Collections.singletonList("f0"))).containsOnly("f0_0");
    }

    @Test
    void testFlattenToNames_3() {
        assertThat(DataTypeUtils.flattenToNames(ROW(FIELD("a", INT()), FIELD("b", BOOLEAN())), Collections.emptyList())).containsExactly("a", "b");
    }
}
