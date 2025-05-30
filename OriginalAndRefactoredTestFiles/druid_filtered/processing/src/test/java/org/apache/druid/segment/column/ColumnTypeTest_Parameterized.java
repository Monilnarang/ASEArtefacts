package org.apache.druid.segment.column;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import javax.annotation.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ColumnTypeTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testSerdeLegacy_1_1_9")
    public void testSerdeLegacy_1_1_9(String param1) throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING, MAPPER.readValue(param1, ColumnType.class));
    }

    static public Stream<Arguments> Provider_testSerdeLegacy_1_1_9() {
        return Stream.of(arguments("\"STRING\""), arguments("\"string\""), arguments("{\"type\":\"STRING\"}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSerdeLegacy_2_2_10")
    public void testSerdeLegacy_2_2_10(String param1) throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG, MAPPER.readValue(param1, ColumnType.class));
    }

    static public Stream<Arguments> Provider_testSerdeLegacy_2_2_10() {
        return Stream.of(arguments("\"LONG\""), arguments("\"long\""), arguments("{\"type\":\"LONG\"}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSerdeLegacy_3_3_11")
    public void testSerdeLegacy_3_3_11(String param1) throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE, MAPPER.readValue(param1, ColumnType.class));
    }

    static public Stream<Arguments> Provider_testSerdeLegacy_3_3_11() {
        return Stream.of(arguments("\"DOUBLE\""), arguments("\"double\""), arguments("{\"type\":\"DOUBLE\"}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSerdeLegacy_4_4_12_15")
    public void testSerdeLegacy_4_4_12_15(String param1) throws JsonProcessingException {
        Assert.assertEquals(ColumnType.STRING_ARRAY, MAPPER.readValue(param1, ColumnType.class));
    }

    static public Stream<Arguments> Provider_testSerdeLegacy_4_4_12_15() {
        return Stream.of(arguments("\"ARRAY<STRING>\""), arguments("\"STRING_ARRAY\""), arguments("\"string_array\""), arguments("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"STRING\"}}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSerdeLegacy_5_5_13_16")
    public void testSerdeLegacy_5_5_13_16(String param1) throws JsonProcessingException {
        Assert.assertEquals(ColumnType.LONG_ARRAY, MAPPER.readValue(param1, ColumnType.class));
    }

    static public Stream<Arguments> Provider_testSerdeLegacy_5_5_13_16() {
        return Stream.of(arguments("\"ARRAY<LONG>\""), arguments("\"LONG_ARRAY\""), arguments("\"long_array\""), arguments("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"LONG\"}}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSerdeLegacy_6_6_14_17_21")
    public void testSerdeLegacy_6_6_14_17_21(String param1) throws JsonProcessingException {
        Assert.assertEquals(ColumnType.DOUBLE_ARRAY, MAPPER.readValue(param1, ColumnType.class));
    }

    static public Stream<Arguments> Provider_testSerdeLegacy_6_6_14_17_21() {
        return Stream.of(arguments("\"ARRAY<DOUBLE>\""), arguments("\"DOUBLE_ARRAY\""), arguments("\"double_array\""), arguments("\"ARRAY<double>\""), arguments("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"DOUBLE\"}}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSerdeLegacy_7to8")
    public void testSerdeLegacy_7to8(String param1) throws JsonProcessingException {
        ColumnType whatHaveIdone = new ColumnType(ValueType.ARRAY, null, new ColumnType(ValueType.ARRAY, null, SOME_COMPLEX));
        Assert.assertEquals(whatHaveIdone, MAPPER.readValue(param1, ColumnType.class));
    }

    static public Stream<Arguments> Provider_testSerdeLegacy_7to8() {
        return Stream.of(arguments("\"ARRAY<ARRAY<COMPLEX<foo>>>\""), arguments("{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"ARRAY\", \"elementType\":{\"type\":\"COMPLEX\", \"complexTypeName\":\"foo\"}}}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSerdeLegacy_7to8")
    public void testSerdeLegacy_7to8(String param1) throws JsonProcessingException {
        Assert.assertEquals(SOME_COMPLEX, MAPPER.readValue(param1, ColumnType.class));
    }

    static public Stream<Arguments> Provider_testSerdeLegacy_7to8() {
        return Stream.of(arguments("\"COMPLEX<foo>\""), arguments("{\"type\":\"COMPLEX\", \"complexTypeName\":\"foo\"}"));
    }
}
