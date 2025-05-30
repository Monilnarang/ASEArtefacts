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
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ResultFormatTest_Parameterized {

    private final ObjectMapper jsonMapper = new DefaultObjectMapper();

    public static Object[] provideResultFormats() {
        return EnumSet.allOf(ResultFormat.class).stream().map(format -> new Object[] { format }).toArray(Object[]::new);
    }

    @ParameterizedTest
    @MethodSource("Provider_testDeserializeWithDifferentCase_1to4")
    public void testDeserializeWithDifferentCase_1to4(String param1) throws JsonProcessingException {
        Assert.assertEquals(ResultFormat.OBJECTLINES, jsonMapper.readValue(param1, ResultFormat.class));
    }

    static public Stream<Arguments> Provider_testDeserializeWithDifferentCase_1to4() {
        return Stream.of(arguments("\"OBJECTLINES\""), arguments("\"objectLines\""), arguments("\"objectlines\""), arguments("\"oBjEcTlInEs\""));
    }
}
