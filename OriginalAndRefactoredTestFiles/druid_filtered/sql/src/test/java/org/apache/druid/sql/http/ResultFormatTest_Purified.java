package org.apache.druid.sql.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.EnumSet;

public class ResultFormatTest_Purified {

    private final ObjectMapper jsonMapper = new DefaultObjectMapper();

    public static Object[] provideResultFormats() {
        return EnumSet.allOf(ResultFormat.class).stream().map(format -> new Object[] { format }).toArray(Object[]::new);
    }

    @Test
    public void testDeserializeWithDifferentCase_1() throws JsonProcessingException {
        Assert.assertEquals(ResultFormat.OBJECTLINES, jsonMapper.readValue("\"OBJECTLINES\"", ResultFormat.class));
    }

    @Test
    public void testDeserializeWithDifferentCase_2() throws JsonProcessingException {
        Assert.assertEquals(ResultFormat.OBJECTLINES, jsonMapper.readValue("\"objectLines\"", ResultFormat.class));
    }

    @Test
    public void testDeserializeWithDifferentCase_3() throws JsonProcessingException {
        Assert.assertEquals(ResultFormat.OBJECTLINES, jsonMapper.readValue("\"objectlines\"", ResultFormat.class));
    }

    @Test
    public void testDeserializeWithDifferentCase_4() throws JsonProcessingException {
        Assert.assertEquals(ResultFormat.OBJECTLINES, jsonMapper.readValue("\"oBjEcTlInEs\"", ResultFormat.class));
    }
}
