package org.apache.druid.segment.column;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import javax.annotation.Nullable;

public class ColumnTypeTest_Purified {

    public static final ColumnType SOME_COMPLEX = new ColumnType(ValueType.COMPLEX, "foo", null);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static class SomeOtherTypeSignature extends BaseTypeSignature<ValueType> {

        public SomeOtherTypeSignature(ValueType valueType, @Nullable String complexTypeName, @Nullable TypeSignature<ValueType> elementType) {
            super(ColumnTypeFactory.getInstance(), valueType, complexTypeName, elementType);
        }

        @Override
        public <T> TypeStrategy<T> getStrategy() {
            throw new UnsupportedOperationException("nope");
        }
    }

    @Test
    public void testSerde_1() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING, MAPPER.readValue(MAPPER.writeValueAsString(ColumnType.STRING), ColumnType.class));
    }

    @Test
    public void testSerde_2() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG, MAPPER.readValue(MAPPER.writeValueAsString(ColumnType.LONG), ColumnType.class));
    }

    @Test
    public void testSerde_3() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE, MAPPER.readValue(MAPPER.writeValueAsString(ColumnType.DOUBLE), ColumnType.class));
    }

    @Test
    public void testSerde_4() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.FLOAT, MAPPER.readValue(MAPPER.writeValueAsString(ColumnType.FLOAT), ColumnType.class));
    }

    @Test
    public void testSerde_5() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING_ARRAY, MAPPER.readValue(MAPPER.writeValueAsString(ColumnType.STRING_ARRAY), ColumnType.class));
    }

    @Test
    public void testSerde_6() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG_ARRAY, MAPPER.readValue(MAPPER.writeValueAsString(ColumnType.LONG_ARRAY), ColumnType.class));
    }

    @Test
    public void testSerde_7() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE_ARRAY, MAPPER.readValue(MAPPER.writeValueAsString(ColumnType.DOUBLE_ARRAY), ColumnType.class));
    }

    @Test
    public void testSerde_8() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.UNKNOWN_COMPLEX, MAPPER.readValue(MAPPER.writeValueAsString(ColumnType.UNKNOWN_COMPLEX), ColumnType.class));
    }

    @Test
    public void testSerde_9() throws JsonProcessingException {
        Assert.assertEquals(SOME_COMPLEX, MAPPER.readValue(MAPPER.writeValueAsString(SOME_COMPLEX), ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_1() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING, MAPPER.readValue("\"STRING\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_2() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG, MAPPER.readValue("\"LONG\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_3() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE, MAPPER.readValue("\"DOUBLE\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_4() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING_ARRAY, MAPPER.readValue("\"ARRAY<STRING>\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_5() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG_ARRAY, MAPPER.readValue("\"ARRAY<LONG>\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_6() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE_ARRAY, MAPPER.readValue("\"ARRAY<DOUBLE>\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_7() throws JsonProcessingException {
        ColumnType whatHaveIdone = new ColumnType(ValueType.ARRAY, null, new ColumnType(ValueType.ARRAY, null, SOME_COMPLEX));
        Assert.assertEquals(whatHaveIdone, MAPPER.readValue("\"ARRAY<ARRAY<COMPLEX<foo>>>\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_8() throws JsonProcessingException {
        Assert.assertEquals(SOME_COMPLEX, MAPPER.readValue("\"COMPLEX<foo>\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_9() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING, MAPPER.readValue("\"string\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_10() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG, MAPPER.readValue("\"long\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_11() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE, MAPPER.readValue("\"double\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_12() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING_ARRAY, MAPPER.readValue("\"STRING_ARRAY\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_13() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG_ARRAY, MAPPER.readValue("\"LONG_ARRAY\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_14() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE_ARRAY, MAPPER.readValue("\"DOUBLE_ARRAY\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_15() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING_ARRAY, MAPPER.readValue("\"string_array\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_16() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG_ARRAY, MAPPER.readValue("\"long_array\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_17() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE_ARRAY, MAPPER.readValue("\"double_array\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_18() throws JsonProcessingException {
        Assert.assertNotEquals(ColumnType.STRING_ARRAY, MAPPER.readValue("\"array<string>\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_19() throws JsonProcessingException {
        Assert.assertNotEquals(ColumnType.LONG_ARRAY, MAPPER.readValue("\"array<LONG>\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_20() throws JsonProcessingException {
        Assert.assertNotEquals(SOME_COMPLEX, MAPPER.readValue("\"COMPLEX<FOO>\"", ColumnType.class));
    }

    @Test
    public void testSerdeLegacy_21() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE_ARRAY, MAPPER.readValue("\"ARRAY<double>\"", ColumnType.class));
    }

    @Test
    public void testFutureProof_1() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING, MAPPER.readValue("{\"type\":\"STRING\"}", ColumnType.class));
    }

    @Test
    public void testFutureProof_2() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG, MAPPER.readValue("{\"type\":\"LONG\"}", ColumnType.class));
    }

    @Test
    public void testFutureProof_3() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE, MAPPER.readValue("{\"type\":\"DOUBLE\"}", ColumnType.class));
    }

    @Test
    public void testFutureProof_4() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING_ARRAY, MAPPER.readValue("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"STRING\"}}", ColumnType.class));
    }

    @Test
    public void testFutureProof_5() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG_ARRAY, MAPPER.readValue("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"LONG\"}}", ColumnType.class));
    }

    @Test
    public void testFutureProof_6() throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE_ARRAY, MAPPER.readValue("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"DOUBLE\"}}", ColumnType.class));
    }

    @Test
    public void testFutureProof_7() throws JsonProcessingException {
        Assert.assertEquals(SOME_COMPLEX, MAPPER.readValue("{\"type\":\"COMPLEX\", \"complexTypeName\":\"foo\"}", ColumnType.class));
    }

    @Test
    public void testFutureProof_8() throws JsonProcessingException {
        ColumnType whatHaveIdone = new ColumnType(ValueType.ARRAY, null, new ColumnType(ValueType.ARRAY, null, SOME_COMPLEX));
        Assert.assertEquals(whatHaveIdone, MAPPER.readValue("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"COMPLEX\", \"complexTypeName\":\"foo\"}}}", ColumnType.class));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_1() {
        Assert.assertEquals(ColumnType.LONG, ColumnTypeFactory.ofType(new SomeOtherTypeSignature(ValueType.LONG, null, null)));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_2() {
        Assert.assertEquals(ColumnType.LONG, ColumnTypeFactory.ofValueType(ValueType.LONG));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_3() {
        Assert.assertEquals(ColumnType.FLOAT, ColumnTypeFactory.ofType(new SomeOtherTypeSignature(ValueType.FLOAT, null, null)));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_4() {
        Assert.assertEquals(ColumnType.FLOAT, ColumnTypeFactory.ofValueType(ValueType.FLOAT));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_5() {
        Assert.assertEquals(ColumnType.DOUBLE, ColumnTypeFactory.ofType(new SomeOtherTypeSignature(ValueType.DOUBLE, null, null)));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_6() {
        Assert.assertEquals(ColumnType.DOUBLE, ColumnTypeFactory.ofValueType(ValueType.DOUBLE));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_7() {
        Assert.assertEquals(ColumnType.STRING, ColumnTypeFactory.ofType(new SomeOtherTypeSignature(ValueType.STRING, null, null)));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_8() {
        Assert.assertEquals(ColumnType.STRING, ColumnTypeFactory.ofValueType(ValueType.STRING));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_9() {
        Assert.assertEquals(ColumnType.LONG_ARRAY, ColumnTypeFactory.ofType(new SomeOtherTypeSignature(ValueType.ARRAY, null, new SomeOtherTypeSignature(ValueType.LONG, null, null))));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_10() {
        Assert.assertEquals(ColumnType.DOUBLE_ARRAY, ColumnTypeFactory.ofType(new SomeOtherTypeSignature(ValueType.ARRAY, null, new SomeOtherTypeSignature(ValueType.DOUBLE, null, null))));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_11() {
        Assert.assertEquals(ColumnType.STRING_ARRAY, ColumnTypeFactory.ofType(new SomeOtherTypeSignature(ValueType.ARRAY, null, new SomeOtherTypeSignature(ValueType.STRING, null, null))));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_12() {
        Assert.assertEquals(ColumnType.UNKNOWN_COMPLEX, ColumnTypeFactory.ofType(new SomeOtherTypeSignature(ValueType.COMPLEX, null, null)));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_13() {
        Assert.assertEquals(ColumnType.UNKNOWN_COMPLEX, ColumnTypeFactory.ofValueType(ValueType.COMPLEX));
    }

    @Test
    public void testFactoryFromOtherTypeSignatures_14() {
        Assert.assertEquals(SOME_COMPLEX, ColumnTypeFactory.ofType(new SomeOtherTypeSignature(ValueType.COMPLEX, SOME_COMPLEX.getComplexTypeName(), null)));
    }
}
