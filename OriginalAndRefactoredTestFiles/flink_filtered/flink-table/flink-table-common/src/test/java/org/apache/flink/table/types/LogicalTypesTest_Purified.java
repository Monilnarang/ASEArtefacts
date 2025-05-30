package org.apache.flink.table.types;

import org.apache.flink.api.common.serialization.SerializerConfigImpl;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.catalog.ObjectIdentifier;
import org.apache.flink.table.catalog.UnresolvedIdentifier;
import org.apache.flink.table.expressions.TimeIntervalUnit;
import org.apache.flink.table.legacy.types.logical.TypeInformationRawType;
import org.apache.flink.table.types.logical.ArrayType;
import org.apache.flink.table.types.logical.BigIntType;
import org.apache.flink.table.types.logical.BinaryType;
import org.apache.flink.table.types.logical.BooleanType;
import org.apache.flink.table.types.logical.CharType;
import org.apache.flink.table.types.logical.DateType;
import org.apache.flink.table.types.logical.DayTimeIntervalType;
import org.apache.flink.table.types.logical.DecimalType;
import org.apache.flink.table.types.logical.DistinctType;
import org.apache.flink.table.types.logical.DoubleType;
import org.apache.flink.table.types.logical.FloatType;
import org.apache.flink.table.types.logical.IntType;
import org.apache.flink.table.types.logical.LocalZonedTimestampType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.MapType;
import org.apache.flink.table.types.logical.MultisetType;
import org.apache.flink.table.types.logical.NullType;
import org.apache.flink.table.types.logical.RawType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.table.types.logical.SmallIntType;
import org.apache.flink.table.types.logical.StructuredType;
import org.apache.flink.table.types.logical.SymbolType;
import org.apache.flink.table.types.logical.TimeType;
import org.apache.flink.table.types.logical.TimestampKind;
import org.apache.flink.table.types.logical.TimestampType;
import org.apache.flink.table.types.logical.TinyIntType;
import org.apache.flink.table.types.logical.UnresolvedUserDefinedType;
import org.apache.flink.table.types.logical.VarBinaryType;
import org.apache.flink.table.types.logical.VarCharType;
import org.apache.flink.table.types.logical.YearMonthIntervalType;
import org.apache.flink.table.types.logical.ZonedTimestampType;
import org.apache.flink.types.Row;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static org.apache.flink.table.test.TableAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LogicalTypesTest_Purified {

    private static ThrowingConsumer<LogicalType> baseAssertions(String serializableString, String summaryString, Class<?>[] supportedInputClasses, Class<?>[] supportedOutputClasses, LogicalType[] children, LogicalType otherType) {
        return nullableType -> assertThat(nullableType).satisfies(nonEqualityCheckWithOtherType(otherType)).satisfies(LogicalTypesTest::nullability).isJavaSerializable().hasSerializableString(serializableString).hasSummaryString(summaryString).satisfies(conversions(supportedInputClasses, supportedOutputClasses)).hasExactlyChildren(children);
    }

    private static ThrowingConsumer<LogicalType> nonEqualityCheckWithOtherType(LogicalType otherType) {
        return nullableType -> {
            assertThat(nullableType).isNullable().isEqualTo(nullableType).isEqualTo(nullableType.copy()).isNotEqualTo(otherType);
            assertThat(nullableType.hashCode()).isEqualTo(nullableType.hashCode()).isNotEqualTo(otherType.hashCode());
        };
    }

    private static void nullability(LogicalType nullableType) {
        final LogicalType notNullInstance = nullableType.copy(false);
        assertThat(notNullInstance).isNotNullable();
        assertThat(nullableType).isNotEqualTo(notNullInstance);
    }

    private static ThrowingConsumer<LogicalType> conversions(Class<?>[] inputs, Class<?>[] outputs) {
        return type -> {
            assertThat(type).supportsInputConversion(type.getDefaultConversion()).supportsOutputConversion(type.getDefaultConversion()).doesNotSupportInputConversion(LogicalTypesTest.class).doesNotSupportOutputConversion(LogicalTypesTest.class);
            for (Class<?> clazz : inputs) {
                assertThat(type).supportsInputConversion(clazz);
            }
            for (Class<?> clazz : outputs) {
                assertThat(type).supportsOutputConversion(clazz);
            }
        };
    }

    private DistinctType createDistinctType(String typeName) {
        return DistinctType.newBuilder(ObjectIdentifier.of("cat", "db", typeName), new DecimalType(10, 2)).description("Money type desc.").build();
    }

    private static final LogicalType UDT_NAME_TYPE = new VarCharType();

    private static final LogicalType UDT_SETTING_TYPE = new IntType();

    private static final LogicalType UDT_TIMESTAMP_TYPE = new TimestampType();

    private StructuredType createHumanType(boolean useDifferentImplementation) {
        return StructuredType.newBuilder(ObjectIdentifier.of("cat", "db", "Human"), useDifferentImplementation ? SpecialHuman.class : Human.class).attributes(Collections.singletonList(new StructuredType.StructuredAttribute("name", UDT_NAME_TYPE, "Description."))).description("Human type desc.").setFinal(false).setInstantiable(false).build();
    }

    private StructuredType createUserType(boolean isRegistered, boolean isFinal) {
        final StructuredType.Builder builder;
        if (isRegistered) {
            builder = StructuredType.newBuilder(ObjectIdentifier.of("cat", "db", "User"), User.class);
        } else {
            builder = StructuredType.newBuilder(User.class);
        }
        return builder.attributes(Arrays.asList(new StructuredType.StructuredAttribute("setting", UDT_SETTING_TYPE), new StructuredType.StructuredAttribute("timestamp", UDT_TIMESTAMP_TYPE))).description("User type desc.").setFinal(isFinal).setInstantiable(true).superType(createHumanType(false)).build();
    }

    private abstract static class SpecialHuman {

        public String name;
    }

    private abstract static class Human {

        public String name;
    }

    private static final class User extends Human {

        public int setting;

        public LocalDateTime timestamp;
    }

    @Test
    void testEmptyStringLiterals_1_testMerged_1() {
        final CharType charType = CharType.ofEmptyLiteral();
        assertThat(charType.copy(true)).satisfies(nonEqualityCheckWithOtherType(new CharType(1)));
        assertThat(charType).hasSummaryString("CHAR(0) NOT NULL");
        assertThat(charType).hasNoSerializableString();
    }

    @Test
    void testEmptyStringLiterals_2_testMerged_2() {
        final VarCharType varcharType = VarCharType.ofEmptyLiteral();
        assertThat(varcharType.copy(true)).satisfies(nonEqualityCheckWithOtherType(new VarCharType(1)));
        assertThat(varcharType).hasSummaryString("VARCHAR(0) NOT NULL");
        assertThat(varcharType).hasNoSerializableString();
    }

    @Test
    void testEmptyStringLiterals_3_testMerged_3() {
        final BinaryType binaryType = BinaryType.ofEmptyLiteral();
        assertThat(binaryType.copy(true)).satisfies(nonEqualityCheckWithOtherType(new BinaryType(1)));
        assertThat(binaryType).hasSummaryString("BINARY(0) NOT NULL");
        assertThat(binaryType).hasNoSerializableString();
    }

    @Test
    void testEmptyStringLiterals_4_testMerged_4() {
        final VarBinaryType varBinaryType = VarBinaryType.ofEmptyLiteral();
        assertThat(varBinaryType.copy(true)).satisfies(nonEqualityCheckWithOtherType(new VarBinaryType(1)));
        assertThat(varBinaryType).hasSummaryString("VARBINARY(0) NOT NULL");
        assertThat(varBinaryType).hasNoSerializableString();
    }
}
