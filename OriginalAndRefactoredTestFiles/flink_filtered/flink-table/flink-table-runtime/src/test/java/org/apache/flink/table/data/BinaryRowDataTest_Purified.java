package org.apache.flink.table.data;

import org.apache.flink.api.common.serialization.SerializerConfigImpl;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.base.LocalDateSerializer;
import org.apache.flink.api.common.typeutils.base.LocalDateTimeSerializer;
import org.apache.flink.api.common.typeutils.base.LocalTimeSerializer;
import org.apache.flink.api.common.typeutils.base.SqlDateSerializer;
import org.apache.flink.api.common.typeutils.base.SqlTimeSerializer;
import org.apache.flink.api.common.typeutils.base.SqlTimestampSerializer;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.api.java.typeutils.GenericTypeInfo;
import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer;
import org.apache.flink.core.memory.MemorySegment;
import org.apache.flink.core.memory.MemorySegmentFactory;
import org.apache.flink.runtime.io.disk.RandomAccessInputView;
import org.apache.flink.runtime.io.disk.RandomAccessOutputView;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.data.binary.BinaryArrayData;
import org.apache.flink.table.data.binary.BinaryMapData;
import org.apache.flink.table.data.binary.BinaryRowData;
import org.apache.flink.table.data.binary.BinaryStringData;
import org.apache.flink.table.data.util.DataFormatTestUtil;
import org.apache.flink.table.data.writer.BinaryArrayWriter;
import org.apache.flink.table.data.writer.BinaryRowWriter;
import org.apache.flink.table.runtime.typeutils.ArrayDataSerializer;
import org.apache.flink.table.runtime.typeutils.BinaryRowDataSerializer;
import org.apache.flink.table.runtime.typeutils.InternalSerializers;
import org.apache.flink.table.runtime.typeutils.MapDataSerializer;
import org.apache.flink.table.runtime.typeutils.RawValueDataSerializer;
import org.apache.flink.table.runtime.typeutils.RowDataSerializer;
import org.apache.flink.table.types.logical.IntType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.table.types.logical.VarCharType;
import org.apache.flink.types.RowKind;
import org.junit.jupiter.api.Test;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import static org.apache.flink.table.data.StringData.fromBytes;
import static org.apache.flink.table.data.StringData.fromString;
import static org.apache.flink.table.data.util.DataFormatTestUtil.MyObj;
import static org.apache.flink.table.data.util.MapDataUtil.convertToJavaMap;
import static org.apache.flink.table.utils.RawValueDataAsserter.equivalent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.HamcrestCondition.matching;

class BinaryRowDataTest_Purified {

    private void assertTestWriterRow(BinaryRowData row) {
        assertThat(row.getString(0).toString()).isEqualTo("1");
        assertThat(row.getInt(8)).isEqualTo(88);
        assertThat(row.getShort(11)).isEqualTo((short) 292);
        assertThat(row.getLong(10)).isEqualTo(284);
        assertThat(row.getByte(2)).isEqualTo((byte) 99);
        assertThat(row.getDouble(6)).isEqualTo(87.1d);
        assertThat(row.getFloat(7)).isEqualTo(26.1f);
        assertThat(row.getBoolean(1)).isTrue();
        assertThat(row.getString(3).toString()).isEqualTo("1234567");
        assertThat(row.getString(5).toString()).isEqualTo("12345678");
        assertThat(row.getString(9).toString()).isEqualTo("啦啦啦啦啦我是快乐的粉刷匠");
        assertThat(row.getString(9).hashCode()).isEqualTo(fromString("啦啦啦啦啦我是快乐的粉刷匠").hashCode());
        assertThat(row.isNullAt(12)).isTrue();
    }

    private void assertTestGenericObjectRow(BinaryRowData row, TypeSerializer<MyObj> serializer) {
        assertThat(row.getInt(0)).isEqualTo(0);
        RawValueData<MyObj> rawValue1 = row.getRawValue(1);
        RawValueData<MyObj> rawValue2 = row.getRawValue(2);
        RawValueData<MyObj> rawValue3 = row.getRawValue(3);
        assertThat(rawValue1.toObject(serializer)).isEqualTo(new MyObj(0, 1));
        assertThat(rawValue2.toObject(serializer)).isEqualTo(new MyObj(123, 5.0));
        assertThat(rawValue3.toObject(serializer)).isEqualTo(new MyObj(1, 1));
    }

    @Test
    void testHeaderSize_1() {
        assertThat(BinaryRowData.calculateBitSetWidthInBytes(56)).isEqualTo(8);
    }

    @Test
    void testHeaderSize_2() {
        assertThat(BinaryRowData.calculateBitSetWidthInBytes(57)).isEqualTo(16);
    }

    @Test
    void testHeaderSize_3() {
        assertThat(BinaryRowData.calculateBitSetWidthInBytes(120)).isEqualTo(16);
    }

    @Test
    void testHeaderSize_4() {
        assertThat(BinaryRowData.calculateBitSetWidthInBytes(121)).isEqualTo(24);
    }
}
