package org.wikidata.wdtk.datamodel.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import java.io.IOException;
import org.junit.Test;
import org.wikidata.wdtk.datamodel.helpers.DatamodelMapper;
import org.wikidata.wdtk.datamodel.interfaces.EntityIdValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SenseIdValueImplTest_Purified {

    private final ObjectMapper mapper = new DatamodelMapper("http://www.wikidata.org/entity/");

    private final SenseIdValueImpl sense1 = new SenseIdValueImpl("L42-S1", "http://www.wikidata.org/entity/");

    private final SenseIdValueImpl sense2 = new SenseIdValueImpl("L42-S1", "http://www.wikidata.org/entity/");

    private final SenseIdValueImpl sense3 = new SenseIdValueImpl("L57-S2", "http://www.wikidata.org/entity/");

    private final SenseIdValueImpl sense4 = new SenseIdValueImpl("L42-S1", "http://www.example.org/entity/");

    private final String JSON_SENSE_ID_VALUE = "{\"type\":\"wikibase-entityid\",\"value\":{\"entity-type\":\"sense\",\"id\":\"L42-S1\"}}";

    private final String JSON_SENSE_ID_VALUE_WITHOUT_TYPE = "{\"type\":\"wikibase-entityid\",\"value\":{\"id\":\"L42-S1\"}}";

    @Test
    public void iriIsCorrect_1() {
        assertEquals(sense1.getIri(), "http://www.wikidata.org/entity/L42-S1");
    }

    @Test
    public void iriIsCorrect_2() {
        assertEquals(sense4.getIri(), "http://www.example.org/entity/L42-S1");
    }

    @Test
    public void equalityBasedOnContent_1() {
        assertEquals(sense1, sense1);
    }

    @Test
    public void equalityBasedOnContent_2() {
        assertEquals(sense1, sense2);
    }

    @Test
    public void equalityBasedOnContent_3() {
        assertNotEquals(sense1, sense3);
    }

    @Test
    public void equalityBasedOnContent_4() {
        assertNotEquals(sense1, sense4);
    }

    @Test
    public void equalityBasedOnContent_5() {
        assertNotEquals(sense1, null);
    }

    @Test
    public void equalityBasedOnContent_6() {
        assertNotEquals(sense1, this);
    }
}
