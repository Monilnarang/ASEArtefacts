package org.apache.druid.data.input.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class DimensionSchemaTest_Purified {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testStringDimensionSchemaSerde_1() throws Exception {
        final StringDimensionSchema schema1 = new StringDimensionSchema("foo");
        Assert.assertEquals(schema1, OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(schema1), DimensionSchema.class));
    }

    @Test
    public void testStringDimensionSchemaSerde_2() throws Exception {
        final StringDimensionSchema schema2 = new StringDimensionSchema("foo", DimensionSchema.MultiValueHandling.ARRAY, false);
        Assert.assertEquals(schema2, OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(schema2), DimensionSchema.class));
    }
}
