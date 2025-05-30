package org.graylog2.audit.jersey;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

class ResponseEntityConverterTest_Purified {

    private ResponseEntityConverter toTest;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        toTest = new ResponseEntityConverter(objectMapper);
    }

    public static class SimpleEntity {

        @JsonProperty
        private String text;

        @JsonProperty
        private int number;

        public SimpleEntity(String text, int number) {
            this.text = text;
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        public String getText() {
            return text;
        }
    }

    @Test
    public void returnsNullOnVoidEntityClass_1() {
        assertNull(toTest.convertValue(new Object(), Void.class));
    }

    @Test
    public void returnsNullOnVoidEntityClass_2() {
        assertNull(toTest.convertValue("Lalala", void.class));
    }
}
