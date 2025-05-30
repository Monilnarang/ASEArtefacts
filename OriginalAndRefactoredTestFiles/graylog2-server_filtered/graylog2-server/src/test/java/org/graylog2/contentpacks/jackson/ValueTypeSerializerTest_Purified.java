package org.graylog2.contentpacks.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.graylog2.contentpacks.model.entities.references.ValueType;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ValueTypeSerializerTest_Purified {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void serialize_1() throws JsonProcessingException {
        assertThat(objectMapper.writeValueAsString(ValueType.BOOLEAN)).isEqualTo("\"boolean\"");
    }

    @Test
    public void serialize_2() throws JsonProcessingException {
        assertThat(objectMapper.writeValueAsString(ValueType.DOUBLE)).isEqualTo("\"double\"");
    }

    @Test
    public void serialize_3() throws JsonProcessingException {
        assertThat(objectMapper.writeValueAsString(ValueType.FLOAT)).isEqualTo("\"float\"");
    }

    @Test
    public void serialize_4() throws JsonProcessingException {
        assertThat(objectMapper.writeValueAsString(ValueType.INTEGER)).isEqualTo("\"integer\"");
    }

    @Test
    public void serialize_5() throws JsonProcessingException {
        assertThat(objectMapper.writeValueAsString(ValueType.LONG)).isEqualTo("\"long\"");
    }

    @Test
    public void serialize_6() throws JsonProcessingException {
        assertThat(objectMapper.writeValueAsString(ValueType.STRING)).isEqualTo("\"string\"");
    }

    @Test
    public void serialize_7() throws JsonProcessingException {
        assertThat(objectMapper.writeValueAsString(ValueType.PARAMETER)).isEqualTo("\"parameter\"");
    }
}
