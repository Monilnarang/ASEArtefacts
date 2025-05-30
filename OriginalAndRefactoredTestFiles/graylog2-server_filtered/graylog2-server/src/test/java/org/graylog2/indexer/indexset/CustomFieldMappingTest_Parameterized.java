package org.graylog2.indexer.indexset;

import org.graylog2.indexer.fieldtypes.FieldTypeDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CustomFieldMappingTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testToPhysicalTypeReturnsProperPhysicalStringRepresentations_1_1to2_2")
    void testToPhysicalTypeReturnsProperPhysicalStringRepresentations_1_1to2_2(String param1, String param2, String param3) {
        assertEquals(param1, new CustomFieldMapping(param2, param3).toPhysicalType());
    }

    static public Stream<Arguments> Provider_testToPhysicalTypeReturnsProperPhysicalStringRepresentations_1_1to2_2() {
        return Stream.of(arguments("keyword", "field_name", "string"), arguments("text", "field_name", "string_fts"), arguments("long", "field_name", "long"), arguments("double", "field_name", "double"));
    }
}
