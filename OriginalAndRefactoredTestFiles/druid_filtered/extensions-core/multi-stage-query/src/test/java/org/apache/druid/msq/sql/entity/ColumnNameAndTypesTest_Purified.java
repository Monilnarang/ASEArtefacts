package org.apache.druid.msq.sql.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class ColumnNameAndTypesTest_Purified {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static final ColumnNameAndTypes COLUMN_NAME_AND_TYPES = new ColumnNameAndTypes("test", "test1", "test2");

    public static final String JSON_STRING = "{\"name\":\"test\",\"type\":\"test1\",\"nativeType\":\"test2\"}";

    @Test
    public void sanityTest_1() throws JsonProcessingException {
        Assert.assertEquals(JSON_STRING, MAPPER.writeValueAsString(COLUMN_NAME_AND_TYPES));
    }

    @Test
    public void sanityTest_2() throws JsonProcessingException {
        Assert.assertEquals(COLUMN_NAME_AND_TYPES, MAPPER.readValue(MAPPER.writeValueAsString(COLUMN_NAME_AND_TYPES), ColumnNameAndTypes.class));
    }

    @Test
    public void sanityTest_3() throws JsonProcessingException {
        Assert.assertEquals(COLUMN_NAME_AND_TYPES.hashCode(), MAPPER.readValue(MAPPER.writeValueAsString(COLUMN_NAME_AND_TYPES), ColumnNameAndTypes.class).hashCode());
    }

    @Test
    public void sanityTest_4() throws JsonProcessingException {
        Assert.assertEquals("ColumnNameAndTypes{colName='test', sqlTypeName='test1', nativeTypeName='test2'}", COLUMN_NAME_AND_TYPES.toString());
    }
}
