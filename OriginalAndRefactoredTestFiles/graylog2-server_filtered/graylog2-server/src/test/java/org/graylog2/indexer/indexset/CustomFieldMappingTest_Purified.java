package org.graylog2.indexer.indexset;

import org.graylog2.indexer.fieldtypes.FieldTypeDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomFieldMappingTest_Purified {

    @Test
    void testToPhysicalTypeReturnsProperPhysicalStringRepresentations_1() {
        assertEquals("keyword", new CustomFieldMapping("field_name", "string").toPhysicalType());
    }

    @Test
    void testToPhysicalTypeReturnsProperPhysicalStringRepresentations_2() {
        assertEquals("text", new CustomFieldMapping("field_name", "string_fts").toPhysicalType());
    }

    @Test
    void testToPhysicalTypeReturnsProperPhysicalNumericRepresentations_1() {
        assertEquals("long", new CustomFieldMapping("field_name", "long").toPhysicalType());
    }

    @Test
    void testToPhysicalTypeReturnsProperPhysicalNumericRepresentations_2() {
        assertEquals("double", new CustomFieldMapping("field_name", "double").toPhysicalType());
    }
}
