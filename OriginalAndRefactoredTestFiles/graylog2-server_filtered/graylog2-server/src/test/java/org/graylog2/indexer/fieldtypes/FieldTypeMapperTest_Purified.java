package org.graylog2.indexer.fieldtypes;

import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
import static com.google.common.collect.ImmutableSet.copyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.graylog2.indexer.fieldtypes.FieldTypes.Type.createType;

public class FieldTypeMapperTest_Purified {

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
    public void mappings_1() {
        assertMapping("text", "string", "full-text-search");
    }

    @Test
    public void mappings_2() {
        assertMapping(textWithFielddata, "string", "full-text-search", "enumerable");
    }

    @Test
    public void mappings_3() {
        assertMapping("keyword", "string", "enumerable");
    }

    @Test
    public void mappings_4() {
        assertMapping("long", "long", "numeric", "enumerable");
    }

    @Test
    public void mappings_5() {
        assertMapping("integer", "int", "numeric", "enumerable");
    }

    @Test
    public void mappings_6() {
        assertMapping("short", "short", "numeric", "enumerable");
    }

    @Test
    public void mappings_7() {
        assertMapping("byte", "byte", "numeric", "enumerable");
    }

    @Test
    public void mappings_8() {
        assertMapping("double", "double", "numeric", "enumerable");
    }

    @Test
    public void mappings_9() {
        assertMapping("float", "float", "numeric", "enumerable");
    }

    @Test
    public void mappings_10() {
        assertMapping("half_float", "float", "numeric", "enumerable");
    }

    @Test
    public void mappings_11() {
        assertMapping("scaled_float", "float", "numeric", "enumerable");
    }

    @Test
    public void mappings_12() {
        assertMapping("date", "date", "enumerable");
    }

    @Test
    public void mappings_13() {
        assertMapping("boolean", "boolean", "enumerable");
    }

    @Test
    public void mappings_14() {
        assertMapping("binary", "binary");
    }

    @Test
    public void mappings_15() {
        assertMapping("geo_point", "geo-point");
    }

    @Test
    public void mappings_16() {
        assertMapping("ip", "ip", "enumerable");
    }
}
