package org.apache.flink.table.types;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.data.ArrayData;
import org.apache.flink.table.data.MapData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.StringData;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.apache.flink.table.api.DataTypes.ARRAY;
import static org.apache.flink.table.api.DataTypes.BIGINT;
import static org.apache.flink.table.api.DataTypes.BOOLEAN;
import static org.apache.flink.table.api.DataTypes.CHAR;
import static org.apache.flink.table.api.DataTypes.DOUBLE;
import static org.apache.flink.table.api.DataTypes.FIELD;
import static org.apache.flink.table.api.DataTypes.INT;
import static org.apache.flink.table.api.DataTypes.INTERVAL;
import static org.apache.flink.table.api.DataTypes.MAP;
import static org.apache.flink.table.api.DataTypes.MONTH;
import static org.apache.flink.table.api.DataTypes.MULTISET;
import static org.apache.flink.table.api.DataTypes.ROW;
import static org.apache.flink.table.api.DataTypes.STRING;
import static org.apache.flink.table.api.DataTypes.STRUCTURED;
import static org.apache.flink.table.api.DataTypes.TIMESTAMP;
import static org.apache.flink.table.api.DataTypes.YEAR;
import static org.apache.flink.table.test.TableAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataTypeTest_Purified {

    @Test
    void testNullability_1() {
        assertThat(BIGINT().nullable()).isNullable();
    }

    @Test
    void testNullability_2() {
        assertThat(BIGINT().notNull()).isNotNullable();
    }

    @Test
    void testNullability_3() {
        assertThat(BIGINT().notNull().nullable()).isNullable();
    }

    @Test
    void testGetFieldNames_1() {
        assertThat(DataType.getFieldNames(ROW(FIELD("c0", BOOLEAN()), FIELD("c1", DOUBLE()), FIELD("c2", INT())))).containsExactly("c0", "c1", "c2");
    }

    @Test
    void testGetFieldNames_2() {
        assertThat(DataType.getFieldNames(STRUCTURED(DataTypesTest.SimplePojo.class, FIELD("name", STRING()), FIELD("count", INT().notNull().bridgedTo(int.class))))).containsExactly("name", "count");
    }

    @Test
    void testGetFieldNames_3() {
        assertThat(DataType.getFieldNames(ARRAY(INT()))).isEmpty();
    }

    @Test
    void testGetFieldNames_4() {
        assertThat(DataType.getFieldNames(INT())).isEmpty();
    }

    @Test
    void testGetFieldDataTypes_1() {
        assertThat(DataType.getFieldDataTypes(ROW(FIELD("c0", BOOLEAN()), FIELD("c1", DOUBLE()), FIELD("c2", INT())))).containsExactly(BOOLEAN(), DOUBLE(), INT());
    }

    @Test
    void testGetFieldDataTypes_2() {
        assertThat(DataType.getFieldDataTypes(STRUCTURED(DataTypesTest.SimplePojo.class, FIELD("name", STRING()), FIELD("count", INT().notNull().bridgedTo(int.class))))).containsExactly(STRING(), INT().notNull().bridgedTo(int.class));
    }

    @Test
    void testGetFieldDataTypes_3() {
        assertThat(DataType.getFieldDataTypes(ARRAY(INT()))).isEmpty();
    }

    @Test
    void testGetFieldDataTypes_4() {
        assertThat(DataType.getFieldDataTypes(INT())).isEmpty();
    }

    @Test
    void testGetFieldCount_1() {
        assertThat(DataType.getFieldCount(ROW(FIELD("c0", BOOLEAN()), FIELD("c1", DOUBLE()), FIELD("c2", INT())))).isEqualTo(3);
    }

    @Test
    void testGetFieldCount_2() {
        assertThat(DataType.getFieldCount(STRUCTURED(DataTypesTest.SimplePojo.class, FIELD("name", STRING()), FIELD("count", INT().notNull().bridgedTo(int.class))))).isEqualTo(2);
    }

    @Test
    void testGetFieldCount_3() {
        assertThat(DataType.getFieldCount(ARRAY(INT()))).isZero();
    }

    @Test
    void testGetFieldCount_4() {
        assertThat(DataType.getFieldCount(INT())).isZero();
    }

    @Test
    void testGetFields_1() {
        assertThat(DataType.getFields(ROW(FIELD("c0", BOOLEAN()), FIELD("c1", DOUBLE()), FIELD("c2", INT())))).containsExactly(FIELD("c0", BOOLEAN()), FIELD("c1", DOUBLE()), FIELD("c2", INT()));
    }

    @Test
    void testGetFields_2() {
        assertThat(DataType.getFields(STRUCTURED(DataTypesTest.SimplePojo.class, FIELD("name", STRING()), FIELD("count", INT().notNull().bridgedTo(int.class))))).containsExactly(FIELD("name", STRING()), FIELD("count", INT().notNull().bridgedTo(int.class)));
    }

    @Test
    void testGetFields_3() {
        assertThat(DataType.getFields(ARRAY(INT()))).isEmpty();
    }

    @Test
    void testGetFields_4() {
        assertThat(DataType.getFields(INT())).isEmpty();
    }
}
