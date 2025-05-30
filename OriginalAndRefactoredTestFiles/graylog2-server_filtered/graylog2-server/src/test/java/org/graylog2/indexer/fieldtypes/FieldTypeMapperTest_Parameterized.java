package org.graylog2.indexer.fieldtypes;

import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
import static com.google.common.collect.ImmutableSet.copyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.graylog2.indexer.fieldtypes.FieldTypes.Type.createType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FieldTypeMapperTest_Parameterized {

    private FieldTypeMapper mapper;

    private static final FieldTypeDTO textWithFielddata = FieldTypeDTO.builder().physicalType("text").fieldName("test").properties(Collections.singleton(FieldTypeDTO.Properties.FIELDDATA)).build();

    @Before
    public void setUp() throws Exception {
        this.mapper = new FieldTypeMapper();
    }

    private void assertMapping(String esType, String glType, String... properties) {
        assertMapping(FieldTypeDTO.builder().fieldName("test").physicalType(esType).build(), glType, properties);
    }

    private void assertMapping(FieldTypeDTO esType, String glType, String... properties) {
        assertThat(mapper.mapType(esType)).isPresent().get().isEqualTo(createType(glType, copyOf(properties)));
    }

    @Test
    public void mappings_2() {
        assertMapping(textWithFielddata, "string", "full-text-search", "enumerable");
    }

    @ParameterizedTest
    @MethodSource("Provider_mappings_1_3_12to13_16")
    public void mappings_1_3_12to13_16(String param1, String param2, String param3) {
        assertMapping(param1, param2, param3);
    }

    static public Stream<Arguments> Provider_mappings_1_3_12to13_16() {
        return Stream.of(arguments("text", "string", "full-text-search"), arguments("keyword", "string", "enumerable"), arguments("date", "date", "enumerable"), arguments("boolean", "boolean", "enumerable"), arguments("ip", "ip", "enumerable"));
    }

    @ParameterizedTest
    @MethodSource("Provider_mappings_4to11")
    public void mappings_4to11(String param1, String param2, String param3, String param4) {
        assertMapping(param1, param2, param3, param4);
    }

    static public Stream<Arguments> Provider_mappings_4to11() {
        return Stream.of(arguments("long", "long", "numeric", "enumerable"), arguments("integer", "int", "numeric", "enumerable"), arguments("short", "short", "numeric", "enumerable"), arguments("byte", "byte", "numeric", "enumerable"), arguments("double", "double", "numeric", "enumerable"), arguments("float", "float", "numeric", "enumerable"), arguments("half_float", "float", "numeric", "enumerable"), arguments("scaled_float", "float", "numeric", "enumerable"));
    }

    @ParameterizedTest
    @MethodSource("Provider_mappings_14to15")
    public void mappings_14to15(String param1, String param2) {
        assertMapping(param1, param2);
    }

    static public Stream<Arguments> Provider_mappings_14to15() {
        return Stream.of(arguments("binary", "binary"), arguments("geo_point", "geo-point"));
    }
}
