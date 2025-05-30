package org.wikidata.wdtk.datamodel.implementation;

import org.wikidata.wdtk.datamodel.helpers.ToString;
import org.wikidata.wdtk.datamodel.interfaces.UnsupportedValue;
import org.wikidata.wdtk.datamodel.interfaces.Value;
import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UnsupportedValueImplTest_Purified {

    private final ObjectMapper mapper = new ObjectMapper();

    private final String JSON_UNSUPPORTED_VALUE_1 = "{\"type\":\"funky\",\"value\":\"groovy\"}";

    private final String JSON_UNSUPPORTED_VALUE_2 = "{\"type\":\"shiny\",\"number\":42}";

    private UnsupportedValue firstValue, secondValue;

    @Before
    public void deserializeFirstValue() throws IOException {
        firstValue = mapper.readValue(JSON_UNSUPPORTED_VALUE_1, UnsupportedValueImpl.class);
        secondValue = mapper.readValue(JSON_UNSUPPORTED_VALUE_2, UnsupportedValueImpl.class);
    }

    @Test
    public void testToString_1() {
        assertEquals(ToString.toString(firstValue), firstValue.toString());
    }

    @Test
    public void testToString_2() {
        assertEquals(ToString.toString(secondValue), secondValue.toString());
    }

    @Test
    public void testGetTypeString_1() {
        assertEquals("funky", firstValue.getTypeJsonString());
    }

    @Test
    public void testGetTypeString_2() {
        assertEquals("shiny", secondValue.getTypeJsonString());
    }
}
