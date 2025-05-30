package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.interfaces.StringValue;
import java.io.IOException;

public class StringValueImplTest_Purified {

    private final ObjectMapper mapper = new ObjectMapper();

    private final StringValue s1 = new StringValueImpl("some string");

    private final StringValue s2 = new StringValueImpl("some string");

    private final String JSON_STRING_VALUE = "{\"type\":\"string\",\"value\":\"some string\"}";

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(s1, s1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(s1, s2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        StringValue s3 = new StringValueImpl("another string");
        assertNotEquals(s1, s3);
    }

    @Test
    public void equalityBasedOnContent_4() {
        assertNotEquals(s1, null);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(s1, this);
    }
}
