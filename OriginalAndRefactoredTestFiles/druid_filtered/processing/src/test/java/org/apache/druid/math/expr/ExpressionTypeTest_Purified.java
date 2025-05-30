package org.apache.druid.math.expr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.ValueType;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExpressionTypeTest_Purified {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final ExpressionType SOME_COMPLEX = new ExpressionType(ExprType.COMPLEX, "foo", null);

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSerde_1() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.STRING, MAPPER.readValue(MAPPER.writeValueAsString(ExpressionType.STRING), ExpressionType.class));
    }

    @Test
    public void testSerde_2() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.LONG, MAPPER.readValue(MAPPER.writeValueAsString(ExpressionType.LONG), ExpressionType.class));
    }

    @Test
    public void testSerde_3() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE, MAPPER.readValue(MAPPER.writeValueAsString(ExpressionType.DOUBLE), ExpressionType.class));
    }

    @Test
    public void testSerde_4() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, MAPPER.readValue(MAPPER.writeValueAsString(ExpressionType.STRING_ARRAY), ExpressionType.class));
    }

    @Test
    public void testSerde_5() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, MAPPER.readValue(MAPPER.writeValueAsString(ExpressionType.LONG_ARRAY), ExpressionType.class));
    }

    @Test
    public void testSerde_6() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, MAPPER.readValue(MAPPER.writeValueAsString(ExpressionType.DOUBLE_ARRAY), ExpressionType.class));
    }

    @Test
    public void testSerde_7() throws JsonProcessingException {
        Assert.assertEquals(SOME_COMPLEX, MAPPER.readValue(MAPPER.writeValueAsString(SOME_COMPLEX), ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_1() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.STRING, MAPPER.readValue("\"STRING\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_2() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.LONG, MAPPER.readValue("\"LONG\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_3() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE, MAPPER.readValue("\"DOUBLE\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_4() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, MAPPER.readValue("\"ARRAY<STRING>\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_5() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, MAPPER.readValue("\"ARRAY<LONG>\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_6() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, MAPPER.readValue("\"ARRAY<DOUBLE>\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_7() throws JsonProcessingException {
        ExpressionType whatHaveIdone = new ExpressionType(ExprType.ARRAY, null, new ExpressionType(ExprType.ARRAY, null, SOME_COMPLEX));
        Assert.assertEquals(whatHaveIdone, MAPPER.readValue("\"ARRAY<ARRAY<COMPLEX<foo>>>\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_8() throws JsonProcessingException {
        Assert.assertEquals(SOME_COMPLEX, MAPPER.readValue("\"COMPLEX<foo>\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_9() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.STRING, MAPPER.readValue("\"string\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_10() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.LONG, MAPPER.readValue("\"long\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_11() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE, MAPPER.readValue("\"double\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_12() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, MAPPER.readValue("\"STRING_ARRAY\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_13() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, MAPPER.readValue("\"LONG_ARRAY\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_14() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, MAPPER.readValue("\"DOUBLE_ARRAY\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_15() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, MAPPER.readValue("\"string_array\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_16() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, MAPPER.readValue("\"long_array\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_17() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, MAPPER.readValue("\"double_array\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_18() throws JsonProcessingException {
        Assert.assertNotEquals(ExpressionType.STRING_ARRAY, MAPPER.readValue("\"array<string>\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_19() throws JsonProcessingException {
        Assert.assertNotEquals(ExpressionType.LONG_ARRAY, MAPPER.readValue("\"array<LONG>\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_20() throws JsonProcessingException {
        Assert.assertNotEquals(SOME_COMPLEX, MAPPER.readValue("\"COMPLEX<FOO>\"", ExpressionType.class));
    }

    @Test
    public void testSerdeFromString_21() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, MAPPER.readValue("\"ARRAY<double>\"", ExpressionType.class));
    }

    @Test
    public void testFutureProof_1() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.STRING, MAPPER.readValue("{\"type\":\"STRING\"}", ExpressionType.class));
    }

    @Test
    public void testFutureProof_2() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.LONG, MAPPER.readValue("{\"type\":\"LONG\"}", ExpressionType.class));
    }

    @Test
    public void testFutureProof_3() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE, MAPPER.readValue("{\"type\":\"DOUBLE\"}", ExpressionType.class));
    }

    @Test
    public void testFutureProof_4() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, MAPPER.readValue("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"STRING\"}}", ExpressionType.class));
    }

    @Test
    public void testFutureProof_5() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, MAPPER.readValue("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"LONG\"}}", ExpressionType.class));
    }

    @Test
    public void testFutureProof_6() throws JsonProcessingException {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, MAPPER.readValue("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"DOUBLE\"}}", ExpressionType.class));
    }

    @Test
    public void testFutureProof_7() throws JsonProcessingException {
        Assert.assertEquals(SOME_COMPLEX, MAPPER.readValue("{\"type\":\"COMPLEX\", \"complexTypeName\":\"foo\"}", ExpressionType.class));
    }

    @Test
    public void testFutureProof_8() throws JsonProcessingException {
        ExpressionType whatHaveIdone = new ExpressionType(ExprType.ARRAY, null, new ExpressionType(ExprType.ARRAY, null, SOME_COMPLEX));
        Assert.assertEquals(whatHaveIdone, MAPPER.readValue("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"COMPLEX\", \"complexTypeName\":\"foo\"}}}", ExpressionType.class));
    }

    @Test
    public void testConvertFromColumnType_1() {
        Assert.assertNull(ExpressionType.fromColumnType(null));
    }

    @Test
    public void testConvertFromColumnType_2() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionType.fromColumnType(ColumnType.LONG));
    }

    @Test
    public void testConvertFromColumnType_3() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionType.fromColumnType(ColumnType.FLOAT));
    }

    @Test
    public void testConvertFromColumnType_4() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionType.fromColumnType(ColumnType.DOUBLE));
    }

    @Test
    public void testConvertFromColumnType_5() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionType.fromColumnType(ColumnType.STRING));
    }

    @Test
    public void testConvertFromColumnType_6() {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, ExpressionType.fromColumnType(ColumnType.LONG_ARRAY));
    }

    @Test
    public void testConvertFromColumnType_7() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionType.fromColumnType(ColumnType.DOUBLE_ARRAY));
    }

    @Test
    public void testConvertFromColumnType_8() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionType.fromColumnType(ColumnType.STRING_ARRAY));
    }

    @Test
    public void testConvertFromColumnType_9() {
        Assert.assertEquals(SOME_COMPLEX, ExpressionType.fromColumnType(ColumnType.ofComplex(SOME_COMPLEX.getComplexTypeName())));
    }

    @Test
    public void testConvertFromColumnType_10() {
        ExpressionType complexArray = new ExpressionType(ExprType.ARRAY, null, new ExpressionType(ExprType.ARRAY, null, SOME_COMPLEX));
        ColumnType complexArrayColumn = new ColumnType(ValueType.ARRAY, null, new ColumnType(ValueType.ARRAY, null, ColumnType.ofComplex(SOME_COMPLEX.getComplexTypeName())));
        Assert.assertEquals(complexArray, ExpressionType.fromColumnType(complexArrayColumn));
    }

    @Test
    public void testConvertFromColumnTypeStrict_1() {
        Assert.assertEquals(ExpressionType.LONG, ExpressionType.fromColumnTypeStrict(ColumnType.LONG));
    }

    @Test
    public void testConvertFromColumnTypeStrict_2() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionType.fromColumnTypeStrict(ColumnType.FLOAT));
    }

    @Test
    public void testConvertFromColumnTypeStrict_3() {
        Assert.assertEquals(ExpressionType.DOUBLE, ExpressionType.fromColumnTypeStrict(ColumnType.DOUBLE));
    }

    @Test
    public void testConvertFromColumnTypeStrict_4() {
        Assert.assertEquals(ExpressionType.STRING, ExpressionType.fromColumnTypeStrict(ColumnType.STRING));
    }

    @Test
    public void testConvertFromColumnTypeStrict_5() {
        Assert.assertEquals(ExpressionType.LONG_ARRAY, ExpressionType.fromColumnTypeStrict(ColumnType.LONG_ARRAY));
    }

    @Test
    public void testConvertFromColumnTypeStrict_6() {
        Assert.assertEquals(ExpressionType.DOUBLE_ARRAY, ExpressionType.fromColumnTypeStrict(ColumnType.DOUBLE_ARRAY));
    }

    @Test
    public void testConvertFromColumnTypeStrict_7() {
        Assert.assertEquals(ExpressionType.STRING_ARRAY, ExpressionType.fromColumnTypeStrict(ColumnType.STRING_ARRAY));
    }

    @Test
    public void testConvertFromColumnTypeStrict_8() {
        Assert.assertEquals(SOME_COMPLEX, ExpressionType.fromColumnTypeStrict(ColumnType.ofComplex(SOME_COMPLEX.getComplexTypeName())));
    }

    @Test
    public void testConvertFromColumnTypeStrict_9() {
        ExpressionType complexArray = new ExpressionType(ExprType.ARRAY, null, new ExpressionType(ExprType.ARRAY, null, SOME_COMPLEX));
        ColumnType complexArrayColumn = new ColumnType(ValueType.ARRAY, null, new ColumnType(ValueType.ARRAY, null, ColumnType.ofComplex(SOME_COMPLEX.getComplexTypeName())));
        Assert.assertEquals(complexArray, ExpressionType.fromColumnTypeStrict(complexArrayColumn));
    }

    @Test
    public void testConvertToColumnType_1() {
        Assert.assertEquals(ColumnType.LONG, ExpressionType.toColumnType(ExpressionType.LONG));
    }

    @Test
    public void testConvertToColumnType_2() {
        Assert.assertEquals(ColumnType.DOUBLE, ExpressionType.toColumnType(ExpressionType.DOUBLE));
    }

    @Test
    public void testConvertToColumnType_3() {
        Assert.assertEquals(ColumnType.STRING, ExpressionType.toColumnType(ExpressionType.STRING));
    }

    @Test
    public void testConvertToColumnType_4() {
        Assert.assertEquals(ColumnType.LONG_ARRAY, ExpressionType.toColumnType(ExpressionType.LONG_ARRAY));
    }

    @Test
    public void testConvertToColumnType_5() {
        Assert.assertEquals(ColumnType.DOUBLE_ARRAY, ExpressionType.toColumnType(ExpressionType.DOUBLE_ARRAY));
    }

    @Test
    public void testConvertToColumnType_6() {
        Assert.assertEquals(ColumnType.STRING_ARRAY, ExpressionType.toColumnType(ExpressionType.STRING_ARRAY));
    }

    @Test
    public void testConvertToColumnType_7() {
        Assert.assertEquals(ColumnType.ofComplex(SOME_COMPLEX.getComplexTypeName()), ExpressionType.toColumnType(SOME_COMPLEX));
    }

    @Test
    public void testConvertToColumnType_8() {
        ExpressionType complexArray = new ExpressionType(ExprType.ARRAY, null, new ExpressionType(ExprType.ARRAY, null, SOME_COMPLEX));
        ColumnType complexArrayColumn = new ColumnType(ValueType.ARRAY, null, new ColumnType(ValueType.ARRAY, null, ColumnType.ofComplex(SOME_COMPLEX.getComplexTypeName())));
        Assert.assertEquals(complexArrayColumn, ExpressionType.toColumnType(complexArray));
    }
}
