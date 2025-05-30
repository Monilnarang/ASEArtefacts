package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.helpers.ToString;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.UnsupportedEntityIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Value;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UnsupportedEntityIdValueTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://www.wikidata.org/entity/");

    private final String JSON_UNSUPPORTED_VALUE_1 = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"funky\",\"id\":\"Z343\"}}";

    private final String JSON_UNSUPPORTED_VALUE_2 = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"shiny\",\"id\":\"R8989\",\"foo\":\"bar\"}}";

    private final String JSON_UNSUPPORTED_VALUE_NO_TYPE = "{\"type\":\"wikibase-entityid\",\"value\":{\"id\":\"Z343\"}}";

    private UnsupportedEntityIdValue firstValue, secondValue, noType;

    @Before
    public void deserializeValues() throws IOException {
        firstValue = mapper.readValue(JSON_UNSUPPORTED_VALUE_1, UnsupportedEntityIdValueImpl.class);
        secondValue = mapper.readValue(JSON_UNSUPPORTED_VALUE_2, UnsupportedEntityIdValueImpl.class);
        noType = mapper.readValue(JSON_UNSUPPORTED_VALUE_NO_TYPE, UnsupportedEntityIdValueImpl.class);
    }

    @Test
    public void testEquals_1_testMerged_1() throws IOException {
        Value otherValue = mapper.readValue(JSON_UNSUPPORTED_VALUE_1, ValueImpl.class);
        assertEquals(firstValue, otherValue);
        assertNotEquals(secondValue, otherValue);
    }

    @Test
    public void testEquals_3() throws IOException {
        assertNotEquals(firstValue, noType);
    }

    @Test
    public void testEquals_4() throws IOException {
        assertNotEquals(noType, secondValue);
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
        assertEquals("funky", firstValue.getEntityTypeJsonString());
    }

    @Test
    public void testGetTypeString_2() {
        assertEquals("shiny", secondValue.getEntityTypeJsonString());
    }

    @Test
    public void testGetIri_1() {
        assertEquals("http://www.wikidata.org/entity/Z343", firstValue.getIri());
    }

    @Test
    public void testGetIri_2() {
        assertEquals("http://www.wikidata.org/entity/R8989", secondValue.getIri());
    }

    @Test
    public void testGetIri_3() {
        assertEquals("http://www.wikidata.org/entity/Z343", noType.getIri());
    }

    @Test
    public void testGetId_1() {
        assertEquals("Z343", firstValue.getId());
    }

    @Test
    public void testGetId_2() {
        assertEquals("R8989", secondValue.getId());
    }

    @Test
    public void testGetId_3() {
        assertEquals("Z343", noType.getId());
    }

    @Test
    public void testGetEntityType_1() {
        assertEquals("http://www.wikidata.org/ontology#Funky", firstValue.getEntityType());
    }

    @Test
    public void testGetEntityType_2() {
        assertEquals("http://www.wikidata.org/ontology#Shiny", secondValue.getEntityType());
    }

    @Test
    public void testGetEntityType_3() {
        assertEquals(EntityIdValue.ET_UNSUPPORTED, noType.getEntityType());
    }

    @Test
    public void testGetEntityTypeString_1() {
        assertEquals("funky", firstValue.getEntityTypeJsonString());
    }

    @Test
    public void testGetEntityTypeString_2() {
        assertEquals("shiny", secondValue.getEntityTypeJsonString());
    }

    @Test
    public void testGetEntityTypeString_3() {
        assertNull(noType.getEntityTypeJsonString());
    }
}
